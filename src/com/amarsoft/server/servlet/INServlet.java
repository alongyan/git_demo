package com.amarsoft.server.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.amarsoft.server.action.Action;
import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.config.ActionConfig;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;



public class INServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
//		ServletOutputStream out = resp.getOutputStream();
		Map<String, Object> requestMap = new HashMap<String, Object>();
		System.out.println(req.getContextPath());
		//��ȡ�����http����
		Enumeration enumPara = req.getParameterNames();
		while(enumPara.hasMoreElements()){
			String key = (String)enumPara.nextElement();
			String value = req.getParameter(key);
			requestMap.put(key, value);
		}
		Connection conn = null;
		Map<String, Object> errorMap = new TreeMap<String, Object>();
		MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
		//��ֹ�������ʲô�������أ��ʼ���һ��try catch
		try {
			String strMsg = "";
			//2������system���
//			SystemCheckAction systemCheckAction = new SystemCheckAction();
//			systemCheckAction.action(strMsg, errorMap);
			String responseStr = "";
//			if(errorMap.size() > 0){
//				responseStr = messageCoder.encode(errorMap);
////				session.write(responseStr);
//				logger.info("���ر��ģ�" + responseStr);
//				return;
//			}
			
			//3����������̖
			String reqURI = req.getRequestURI();
			reqURI = reqURI.substring(reqURI.indexOf("/", 2));
			//String actionId = (String) systemCheckAction.getRequestMap().get(ServerTranConfig.getInstance().getTranIDLabel());
			String actionId = ServerActionConfig.getInstance().getActionConfigByPath(reqURI).getId();
			requestMap.put(ServerTranConfig.getInstance().getTranIDLabel(), actionId);
			Action action = getAction(actionId);
//			
//			//  ��ȡ���ݿ�����
			conn = DBCPManager.getInstance().getConnection();
			SQLQuery sqlQuery = new SQLQuery(conn);
			sqlQuery.setLoggerModel(Boolean.parseBoolean(NIOProperty.getProperty("sqlLog")));
			Map<String, Object> responseMap = action.excute(requestMap, errorMap, sqlQuery);
			//  ���ڼ�鲻ͬ�����򷵻ش�����Ϣ
			if(errorMap.size() > 0){
				responseStr = messageCoder.encode(getErrorMap(errorMap));
				logger.info("���ر��ģ�" + responseStr);
				out.print(responseStr);
				out.flush();
				return;
			}
			ErrorConfig ec = ServerActionConfig.getSuccessConfig();
//			Map<String, Object> successMap = new TreeMap<String, Object>();
//			successMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
//			successMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
//			successMap.putAll(responseMap);
			//responseStr = XMLUtil.getXMLFromMap(errorMap, actionId);
			responseStr = messageCoder.encode(responseMap);
			logger.info("���ر��ģ�" + responseStr);
			out.print(responseStr);
			out.flush();
			String parentPath = NIOProperty.getProperty("filepath");
			String today = sqlQuery.getToday().replaceAll("/", "");
			File parentFile = new File(parentPath + "/" + today.substring(0, 6));
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(parentFile.getAbsolutePath() + "/" + requestMap.get("TranNo") + requestMap.get("Channel") + UUID.randomUUID() + ".xml"));
			bw.write(strMsg);
			bw.close();
			bw = new BufferedWriter(new FileWriter(parentFile.getAbsolutePath() + "/" + requestMap.get("TranNo") + requestMap.get("Channel") + UUID.randomUUID() + ".xml"));
			bw.write(responseStr);
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
			logger.error(e.toString());
			String responseStr = "";
			//  ���ڼ�鲻ͬ�����򷵻ش�����Ϣ
			try {
				if(errorMap.size() > 0){
					responseStr = messageCoder.encode(getErrorMap(errorMap));
					out.print(responseStr);
					logger.info("���ر��ģ�" + responseStr);
					return;
				} else {
					ErrorConfig ec = ServerActionConfig.getFailConfig();
					errorMap = new TreeMap<String, Object>();
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
					responseStr = messageCoder.encode(getErrorMap(errorMap));
					logger.info("���ر��ģ�" + responseStr);
					out.print(responseStr);
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


	private Map<String, Object> getErrorMap(Map<String, Object> errorMap) {
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("Data", new ArrayList());
		error.put("TotalCount", 0);
		error.put("DropCount", 0);
		ArrayList errorList = new ArrayList();
		Map<String, Object> e = new HashMap();
		e.put("JMID", errorMap.get(ServerTranConfig.getInstance().getResponseDescribeLabel()));
		errorList.add(e);
		error.put("Errors", errorList);
		return error;
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Override
	public void init() throws ServletException {
		logger = Logger.getLogger(INServlet.class);
		logger.info("**********************************InitNIOServlet Start*********************************");
		String sServerPath = getServletContext().getRealPath("/");
		String sActionConfigFile = sServerPath + "WEB-INF/etc/ServerActionConfig.xml";
		String sTranConfigFile = sServerPath + "WEB-INF/etc/ServerTranConfig.xml";
		String sProPertyConfigFile = sServerPath + "WEB-INF/etc/nio.properties";
		logger.info("���������ļ�==" + sActionConfigFile);
		logger.info("���������ļ�==" + sTranConfigFile);
		logger.info("���������ļ�==" + sProPertyConfigFile);
        ServerActionConfig.configFile = sActionConfigFile;
        ServerTranConfig.configFile = sTranConfigFile;
        NIOProperty.configFile = sProPertyConfigFile;
        try {
        	logger.info("�������� ��ʼ......");
            ServerTranConfig.getInstance();
        	ServerActionConfig.getInstance();
        	DBCPManager.getInstance();
        	NIOProperty.getInstance();
			logger.info("�������� ���......");
		} catch (Exception e1) {
			logger.error("�������� ʧ��......" + e1.toString());
			logger.info("�������� ʧ��......" + e1.toString());
			e1.printStackTrace();
		}
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
