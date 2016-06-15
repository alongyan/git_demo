package com.amarsoft.server.servlet;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

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
import com.amarsoft.server.util.CollectionUtil;
import com.amarsoft.server.util.DBCPManager;

public class JMMettingServlet extends HttpServlet {
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
		Map<String, Object> requestMap = new TreeMap<String, Object>();
		Map map = getHTTPMessage(req);
		requestMap.put("Ticket", req.getHeader("Ticket"));
		String reqUrl = req.getRequestURL().toString();
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			requestMap.put((String)entry.getKey(), ((String)entry.getValue()).replace("\r\n", ""));
		}
		requestMap = CollectionUtil.getCaseInsensitiveMap(requestMap);//����key��Сд
		Connection conn = null;
		logger.info("reqURI2��" + map);
		Map<String, Object> errorMap = new TreeMap<String, Object>();
		MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
		//��ֹ�������ʲô�������أ��ʼ���һ��try catch
		try {
			String strMsg = "";
			String responseStr = "";
			logger.info("Ticket" + req.getHeader("Ticket"));
			//3����������̖
			String reqURI = req.getRequestURI();
			logger.info("reqURI3��" + reqURI);
			reqURI = reqURI.substring(reqURI.indexOf("/", 2));
			//String actionId = (String) systemCheckAction.getRequestMap().get(ServerTranConfig.getInstance().getTranIDLabel());
			String actionId = ServerActionConfig.getInstance().getActionConfigByPath(reqURI).getId();
			requestMap.put(ServerTranConfig.getInstance().getTranIDLabel(), actionId);
			Action action = getAction(actionId);
			for(Iterator< String > it = requestMap.keySet().iterator(); it.hasNext();){
				String key = it.next();
				logger.info("{" + key + ":" + requestMap.get(key) + "}");
			}
//			
//			//  ��ȡ���ݿ�����
			System.out.println(requestMap.size());
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
	/**
	 * @describe �÷������ڽ���HTTP���ݵı�������
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public TreeMap getHTTPMessage(HttpServletRequest request) throws IOException{
		//����HashMap����
		TreeMap hm1 = new TreeMap();
		String sStates = "";//��������״̬
		String sItemName = "";//��Ӧ�ֶ�����
		String sItemValue = "";//��Ӧ�ֶ�ֵ
		
		final int MXA_SEGSIZE = 1000 * 1024 * 10;//ÿ������������ 10M
		String contentType = request.getContentType();// ������Ϣ����
		String boundary = ""; // �ֽ��
		String lastboundary = ""; // ������
		if(contentType == null) contentType = "";
		int pos = contentType.indexOf("boundary=");
		if (pos != -1) { // ȡ�÷ֽ���ͽ�����
			pos += "boundary=".length();
			boundary = "--" + contentType.substring(pos);
			lastboundary = boundary + "--";
		}
		// �õ�����������reqbuf
			DataInputStream in = new DataInputStream(request.getInputStream());
			// ��������Ϣ��ʵ���͵�b������
			int totalBytes = request.getContentLength();
			String message = "";
			if (totalBytes > MXA_SEGSIZE) {//ÿ������10mʱ
				message = "Each batch of data can not be larger than " + MXA_SEGSIZE / (1000 * 1024)
						+ "M";
				return null;
			}
			byte[] b = new byte[totalBytes];
			in.readFully(b);
			in.close();
		//  ��ȡ������������ ��ת��ΪUTF-8 ��ʽ
			String reqContent = new String(b, "UTF-8");
			System.out.println("���Ŀ�ʼ��\n" + reqContent + "\n���½���");
			logger.info("���Ŀ�ʼ��\n" + reqContent + "\n���½���");
			//���� boundary ���з���
			String [] array = reqContent.split(boundary);
			for(int i=0;i<array.length;i++){
				HashMap hm2 =  getItemMessage(array[i]);//���÷�����ȡ��������ֵ
				sStates = (String) hm2.get("States");
				sItemName = (String) hm2.get("ItemName");//��ȡ��Ӧ������
				sItemValue = (String) hm2.get("ItemValue");//��ȡ��Ӧ��ֵ
				if(!"3".equals(sStates)){
					hm1.put(sItemName, sItemValue);
				}
			}
				
		return hm1;
	}
	/**
	 * @�÷������ڽ������ݵ�����������
	 * @param sStr
	 * @return
	 */
	public HashMap getItemMessage(String sStr){
		HashMap hm = new HashMap();
		String sMessage = "";
		String sItemName = "";
		String sItemValue = "";
		String sStates = "";
		int i = 0;
		int j=0;
		//��ȡ������Nameֵ
		if(sStr.indexOf("name=\"") >=0){
			i = sStr.indexOf("name=\"");//��ȡ��������
			i += "name=\"".length();//��ȡ name= ��ֵ
			System.out.println("i==="+i);
			String sMessage2 = sStr.substring(i,sStr.length()); // ��ȡ��ȡ�������
			j = sMessage2.indexOf("\"");//��ȡ��һ��
			System.out.println("sMessage2   "+sMessage2);
			sItemName = sMessage2.substring(0,j);
			sItemValue = sMessage2.substring(j+1,sMessage2.length());
			sStates = "1";
		}else if(sStr.indexOf("filename=\"") >= 0){//��ȡ�ļ���������
			i = sStr.indexOf("filename=\"");
			i += "filename=\"".length();
			String sMessage2 = sStr.substring(i,sStr.length());
			j = sMessage2.indexOf("\"");
			sItemName = sMessage2.substring(0,j);
			sItemValue = sMessage2.substring(j+1,sMessage2.length());
			sStates = "2";
		}else {//������Ϊ��
			sStates = "3";
		}
		//�ѻ�ȡ������Ϣ������Map��
		hm.put("States", sStates);
		hm.put("ItemName", sItemName);
		hm.put("ItemValue", sItemValue);
		
		return hm;
	}

	private Map<String, Object> getErrorMap(Map<String, Object> errorMap) {
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("ChineseName", "");
		error.put("IdentityNumber", "");
		error.put("ProjectID", "");
		error.put("ProjectNo", "");
		error.put("Status", errorMap.get(ServerTranConfig.getInstance().getResponseCodeLabel()));
		error.put("Message", errorMap.get(ServerTranConfig.getInstance().getResponseDescribeLabel()));
//		error.put("Data", new ArrayList());
//		error.put("TotalCount", 0);
//		error.put("DropCount", 0);
//		ArrayList errorList = new ArrayList();
//		Map<String, Object> e = new HashMap();
//		e.put("JMID", errorMap.get(ServerTranConfig.getInstance().getResponseDescribeLabel()));
//		errorList.add(e);
//		error.put("Errors", errorList);
		return error;
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Override
	public void init() throws ServletException {
		logger = Logger.getLogger(JMMettingServlet.class);
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
