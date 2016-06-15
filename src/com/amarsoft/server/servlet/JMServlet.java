package com.amarsoft.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.amarsoft.server.action.Action;
import com.amarsoft.server.check.SystemCheckAction;
import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.config.ActionConfig;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;

public class JMServlet extends HttpServlet {

	private Logger logger;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		Connection conn = null;
		Map<String, Object> errorMap = new TreeMap<String, Object>();
		MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
		//��ֹ�������ʲô�������أ��ʼ���һ��try catch
		try {
			
//			ServletOutputStream out = resp.getOutputStream();
			Map<String, Object> requestMap = new HashMap<String, Object>();
			//��ȡ�����http����
			Enumeration enumPara = req.getParameterNames();
			while(enumPara.hasMoreElements()){
				String key = (String)enumPara.nextElement();
				String value = req.getParameter(key);
				//requestMap.put(key, value.replaceAll(" ", "+"));
				requestMap.put(key, value);
				System.out.println(key + "="+ value );
			}
			
			//1�����ձ���
			//2������system���
			SystemCheckAction systemCheckAction = new SystemCheckAction();
			systemCheckAction.setRequestMap(requestMap);
			systemCheckAction.action("", errorMap);
			String responseStr = "";
			if(errorMap.size() > 0){
				responseStr = messageCoder.encode(errorMap);
				out.write(responseStr);
				out.flush();
				logger.info("���ر��ģ�" + responseStr);
				return;
			}
			//3����������̖
			//http://url?Channel=JMTIME&TradCode=JM001&CertType=&CertID=&key=md5Value
			String actionId = (String) systemCheckAction.getRequestMap().get(ServerTranConfig.getInstance().getTranIDLabel());
			//  ��ȡAction���׶���
			Action action = getAction(actionId);
			
			//  ��ȡ���ݿ�����
			conn = DBCPManager.getInstance().getConnection();
			SQLQuery sqlQuery = new SQLQuery(conn);
//			SQLQuery sqlQuery = null;
			sqlQuery.setLoggerModel(Boolean.parseBoolean(NIOProperty.getProperty("sqlLog")));
			Map<String, Object> responseMap = action.excute(requestMap, errorMap, sqlQuery);
			//  ���ڼ�鲻ͬ�����򷵻ش�����Ϣ
			if(errorMap.size() > 0){
				responseStr = messageCoder.encode(errorMap);
				out.write(responseStr);
				out.flush();
				logger.info("���ر��ģ�" + responseStr);
				return;
			}
			ErrorConfig ec = ServerActionConfig.getSuccessConfig();
			Map<String, Object> successMap = new TreeMap<String, Object>();
			successMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
			successMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
			successMap.putAll(responseMap);
			//responseStr = XMLUtil.getXMLFromMap(errorMap, actionId);
			responseStr = messageCoder.encode(successMap);
			
			out.write(responseStr);
			out.flush();
			logger.info("���ر��ģ�" + responseStr);
//			String parentPath = NIOProperty.getProperty("filepath");
//			String today = sqlQuery.getToday().replaceAll("/", "");
//			File parentFile = new File(parentPath + "/" + today.substring(0, 6));
//			if(!parentFile.exists()){
//				parentFile.mkdirs();
//			}
//			BufferedWriter bw = new BufferedWriter(new FileWriter(parentFile.getAbsolutePath() + "/" + requestMap.get("TranNo") + requestMap.get("Channel") + UUID.randomUUID() + ".xml"));
//			bw.write(strMsg);
//			bw.close();
//			bw = new BufferedWriter(new FileWriter(parentFile.getAbsolutePath() + "/" + requestMap.get("TranNo") + requestMap.get("Channel") + UUID.randomUUID() + ".xml"));
//			bw.write(responseStr);
//			bw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
			logger.error(e.toString());
			String responseStr = "";
			//  ���ڼ�鲻ͬ�����򷵻ش�����Ϣ
			try {
				if(errorMap.size() > 0){
					responseStr = messageCoder.encode(errorMap);
					out.write(responseStr);
					out.flush();
					logger.info("���ر��ģ�" + responseStr);
					return;
				} else {
					ErrorConfig ec = ServerActionConfig.getFailConfig();
					errorMap = new TreeMap<String, Object>();
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
					responseStr = messageCoder.encode(errorMap);
					logger.info("���ر��ģ�" + responseStr);
					out.write(responseStr);
					out.flush();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// �ͷ����ݿ���Դ
			DBCPManager.getInstance().free(null, null, conn);
		}
		
		
		
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		logger = Logger.getLogger(INServlet.class);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
	/**
	 * ��ȡ���׵�ʵ��������Դ����߼�����ServerActionConfig�е�action��className���г�ʼ��
	 * @param actionId
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private Action getAction(String actionId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		ActionConfig ac = ServerActionConfig.getInstance().getActionConfig(actionId);
		if(ac == null){
			return null;
		}
		Action action = (Action)Class.forName(ac.getClassName()).newInstance();
		action.setActionConfig(ac);
		return action;
	}
	
}
