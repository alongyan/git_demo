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
		requestMap = CollectionUtil.getCaseInsensitiveMap(requestMap);//忽略key大小写
		Connection conn = null;
		logger.info("reqURI2：" + map);
		Map<String, Object> errorMap = new TreeMap<String, Object>();
		MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
		//防止服务出错什么都不返回，故加上一层try catch
		try {
			String strMsg = "";
			String responseStr = "";
			logger.info("Ticket" + req.getHeader("Ticket"));
			//3、解析交易號
			String reqURI = req.getRequestURI();
			logger.info("reqURI3：" + reqURI);
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
//			//  获取数据库连接
			System.out.println(requestMap.size());
			conn = DBCPManager.getInstance().getConnection();
			SQLQuery sqlQuery = new SQLQuery(conn);
			sqlQuery.setLoggerModel(Boolean.parseBoolean(NIOProperty.getProperty("sqlLog")));
			Map<String, Object> responseMap = action.excute(requestMap, errorMap, sqlQuery);
			//  存在检查不同过，则返回错误信息
			if(errorMap.size() > 0){
				responseStr = messageCoder.encode(getErrorMap(errorMap));
				logger.info("返回报文：" + responseStr);
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
			logger.info("返回报文：" + responseStr);
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
			//  存在检查不同过，则返回错误信息
			try {
				if(errorMap.size() > 0){
					responseStr = messageCoder.encode(getErrorMap(errorMap));
					out.print(responseStr);
					logger.info("返回报文：" + responseStr);
					return;
				} else {
					ErrorConfig ec = ServerActionConfig.getFailConfig();
					errorMap = new TreeMap<String, Object>();
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
					responseStr = messageCoder.encode(getErrorMap(errorMap));
					logger.info("返回报文：" + responseStr);
					out.print(responseStr);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// 释放数据库资源
			DBCPManager.getInstance().free(null, null, conn);
		}
	}
	/**
	 * @describe 该方法用于解析HTTP传递的报文内容
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public TreeMap getHTTPMessage(HttpServletRequest request) throws IOException{
		//创建HashMap数组
		TreeMap hm1 = new TreeMap();
		String sStates = "";//反馈内容状态
		String sItemName = "";//对应字段名称
		String sItemValue = "";//对应字段值
		
		final int MXA_SEGSIZE = 1000 * 1024 * 10;//每批最大的数据量 10M
		String contentType = request.getContentType();// 请求消息类型
		String boundary = ""; // 分界符
		String lastboundary = ""; // 结束符
		if(contentType == null) contentType = "";
		int pos = contentType.indexOf("boundary=");
		if (pos != -1) { // 取得分界符和结束符
			pos += "boundary=".length();
			boundary = "--" + contentType.substring(pos);
			lastboundary = boundary + "--";
		}
		// 得到数据输入流reqbuf
			DataInputStream in = new DataInputStream(request.getInputStream());
			// 将请求消息的实体送到b变量中
			int totalBytes = request.getContentLength();
			String message = "";
			if (totalBytes > MXA_SEGSIZE) {//每批大于10m时
				message = "Each batch of data can not be larger than " + MXA_SEGSIZE / (1000 * 1024)
						+ "M";
				return null;
			}
			byte[] b = new byte[totalBytes];
			in.readFully(b);
			in.close();
		//  获取反馈报文内容 ；转码为UTF-8 格式
			String reqContent = new String(b, "UTF-8");
			System.out.println("报文开始：\n" + reqContent + "\n保温结束");
			logger.info("报文开始：\n" + reqContent + "\n保温结束");
			//根据 boundary 进行分组
			String [] array = reqContent.split(boundary);
			for(int i=0;i<array.length;i++){
				HashMap hm2 =  getItemMessage(array[i]);//调用方法获取传递内容值
				sStates = (String) hm2.get("States");
				sItemName = (String) hm2.get("ItemName");//获取对应的名称
				sItemValue = (String) hm2.get("ItemValue");//获取对应的值
				if(!"3".equals(sStates)){
					hm1.put(sItemName, sItemValue);
				}
			}
				
		return hm1;
	}
	/**
	 * @该方法用于解析传递的数组中内容
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
		//获取常规类Name值
		if(sStr.indexOf("name=\"") >=0){
			i = sStr.indexOf("name=\"");//获取传递内容
			i += "name=\"".length();//获取 name= 的值
			System.out.println("i==="+i);
			String sMessage2 = sStr.substring(i,sStr.length()); // 获取截取后的内容
			j = sMessage2.indexOf("\"");//获取第一个
			System.out.println("sMessage2   "+sMessage2);
			sItemName = sMessage2.substring(0,j);
			sItemValue = sMessage2.substring(j+1,sMessage2.length());
			sStates = "1";
		}else if(sStr.indexOf("filename=\"") >= 0){//获取文件类型名称
			i = sStr.indexOf("filename=\"");
			i += "filename=\"".length();
			String sMessage2 = sStr.substring(i,sStr.length());
			j = sMessage2.indexOf("\"");
			sItemName = sMessage2.substring(0,j);
			sItemValue = sMessage2.substring(j+1,sMessage2.length());
			sStates = "2";
		}else {//若内容为空
			sStates = "3";
		}
		//把获取到的信息放置于Map中
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
	 * 获取交易的实体类对象，以处理逻辑，以ServerActionConfig中的action的className进行初始化
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
