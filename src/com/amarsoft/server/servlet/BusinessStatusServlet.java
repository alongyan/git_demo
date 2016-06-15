package com.amarsoft.server.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.amarsoft.server.action.Action;
import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.config.ActionConfig;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.config.XmlUtitTest;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.Tools;
import com.amarsoft.server.util.XMLMessageUtilInit;

/**
 * @author yhwang
 * @describe 该类用来处理外围系统通过http协议推送的报文，调用方法反馈业务状态
 */
public class BusinessStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		//获取请求的http参数
		ServletInputStream  sis = req.getInputStream();
		Map<String, Object> requestMap =  getInputStreamToString(sis);
		requestMap.put("Ticket", req.getHeader("Ticket"));
		Connection conn = null;
		Map<String, Object> errorMap = new TreeMap<String, Object>();
		MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
		SQLQuery sqlQuery = null;
		//防止服务出错什么都不返回，故加上一层try catch
		try {
			String strMsg = "";
			//2、进行system检查
//			SystemCheckAction systemCheckAction = new SystemCheckAction();
//			systemCheckAction.action(strMsg, errorMap);
			String responseStr = "";
//			if(errorMap.size() > 0){
//				responseStr = messageCoder.encode(errorMap);
////				session.write(responseStr);
//				logger.info("返回报文：" + responseStr);
//				return;
//			}
			
			//3、解析交易號
			String reqURI = req.getRequestURI();
			reqURI = reqURI.substring(reqURI.indexOf("/", 2));
			String actionId = ServerActionConfig.getInstance().getActionConfigByPath(reqURI).getId();
			requestMap.put(ServerTranConfig.getInstance().getTranIDLabel(), actionId);
			Action action = getAction(actionId);
//			
//			//  获取数据库连接
			conn = DBCPManager.getInstance().getConnection();
			sqlQuery = new SQLQuery(conn);
			sqlQuery.setLoggerModel(Boolean.parseBoolean(NIOProperty.getProperty("sqlLog")));
			Map<String, Object> responseMap = action.excute(requestMap, errorMap, sqlQuery);
			//  存在检查不同过，则返回错误信息
			if(errorMap.size() > 0){
				responseStr = messageCoder.encode(getErrorMap(errorMap,sqlQuery, requestMap));
				logger.info("返回报文：" + responseStr);
				out.print(responseStr);
				out.flush();
				return;
			}
			ErrorConfig ec = ServerActionConfig.getSuccessConfig();
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
					responseStr = messageCoder.encode(getErrorMap(errorMap,sqlQuery,requestMap));
					out.print(responseStr);
					logger.info("返回报文：" + responseStr);
					return;
				} else {
					ErrorConfig ec = ServerActionConfig.getFailConfig();
					errorMap = new TreeMap<String, Object>();
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
					responseStr = messageCoder.encode(getErrorMap(errorMap,sqlQuery,requestMap));
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
	 * @describe 改方法用于获取禅融渠道推送的报文信息转换为Map
	 * @param sis
	 * @return
	 */
	private Map<String, Object> getInputStreamToString(ServletInputStream  sis){
		Map<String, Object> map = new HashMap<String, Object>();//反馈Map数组
		String sJsonStr = "";//JSON字符串内容
		try {
			if(sis !=null){
				BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(sis,"UTF-8"));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
					while ((sTempOneLine = tBufferedReader.readLine()) != null){
						tStringBuffer.append(sTempOneLine);
					}
				sJsonStr = tStringBuffer.toString();
				logger.info("收到的报文为==" + sJsonStr);
				JSONObject json = JSONObject.fromObject(sJsonStr);
				Iterator it = json.keys();  
			    // 遍历jsonObject数据，添加到Map对象  
				while (it.hasNext())  
				{  
		           String key = String.valueOf(it.next());  
		           Object value =  json.get(key);  
		           map.put(key, value);  
				}  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map ;
	}
	/**
	 * @describe 改方法用于反馈错误报文
	 * @param errorMap
	 * @return
	 */
	private Map<String, Object> getErrorMap(Map<String, Object> errorMap,SQLQuery sqlQuery,Map<String,Object> requestMap) {
		Map<String, Object> error = new HashMap<String, Object>();
		String sSql = "select er.keymessage as ErrorMessage from Error_RetailMessage er where er.projectno = '"+(Tools.getObjectToString(requestMap.get("JMID"))+"ItemStatus")+"'";
		String sErrorMes = "";
		try{
			sErrorMes = sqlQuery.getString(sSql);
			}catch(Exception e){
				sErrorMes = "获取错误信息失败";
		}finally{
			error.put("Status", "Fail");
			error.put("Error", sErrorMes);
		}
		return error;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Override
	public void init() throws ServletException {
		logger = Logger.getLogger(BusinessStatusServlet.class);
		logger.info("**********************************InitNIOServlet Start*********************************");
		String sServerPath = getServletContext().getRealPath("/");
		String sActionConfigFile = sServerPath + "WEB-INF/etc/ServerActionConfig.xml";
		String sTranConfigFile = sServerPath + "WEB-INF/etc/ServerTranConfig.xml";
		String sProPertyConfigFile = sServerPath + "WEB-INF/etc/nio.properties";
		String sXMLUtilConfigFile = sServerPath + "WEB-INF/etc/XmlMessageConfig.xml";
		logger.info("交易配置文件==" + sActionConfigFile);
		logger.info("报文配置文件==" + sTranConfigFile);
		logger.info("属性配置文件==" + sProPertyConfigFile);
		logger.info("报文配置文件==" + sXMLUtilConfigFile);
        ServerActionConfig.configFile = sActionConfigFile;
        ServerTranConfig.configFile = sTranConfigFile;
        NIOProperty.configFile = sProPertyConfigFile;
        XmlUtitTest.configpaht = sXMLUtilConfigFile;
//        XMLMessageUtilInit.configFile = sProPertyConfigFile;
        try {
        	logger.info("启动服务 开始......");
            ServerTranConfig.getInstance();
        	ServerActionConfig.getInstance();
        	DBCPManager.getInstance();
        	NIOProperty.getInstance();
			logger.info("启动服务 完成......");
		} catch (Exception e1) {
			logger.error("启动服务 失败......" + e1.toString());
			logger.info("启动服务 失败......" + e1.toString());
			e1.printStackTrace();
		}
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
