package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来处理用户状态，存在用户 ture 否则 false
 * @author yhwang
 *
 */
public class TranseUserStatus extends Action {
	private Logger logger = Logger.getLogger(TranseUserStatus.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sLoginID = Tools.getObjectToString(requestMap.get("LoginID"));//登录帐号
		if(sLoginID == null) sLoginID = "";
		String sPassWord = Tools.getObjectToString(requestMap.get("Password"));//密码
		if(sPassWord == null) sPassWord = "";
		String sUserName = "";//用户名称
		String sEmployeeType ="";//用户类型
		
		String sSql = " SELECT UserName,getItemName('EmployeeType',EmployeeType) as EmployeeType "
		+" FROM user_info  ii WHERE ii.loginid ='"+sLoginID+"'  AND "
	    +" ii.password = '"+sPassWord+"' ";
		logger.info("查看用户信息 ： "+sSql);
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sUserName = Tools.getObjectToString(rs.getString("UserName"));
			sEmployeeType = Tools.getObjectToString(rs.getString("EmployeeType"));
		}
		rs.getStatement().close();
		
		//根据参数返回用户状态
		responseMap.put("Status", "Success");
		responseMap.put("UserName", sUserName);
		responseMap.put("EmployeeType", sEmployeeType);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
