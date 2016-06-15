package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类根据手机号判断是否sa或店员
 * @author jxsun
 *
 */
public class TranseYesNoSA extends Action {
	private Logger logger = Logger.getLogger(TranseYesNoSA.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sCompanyTel = Tools.getObjectToString(requestMap.get("CompanyTel"));//手机号码
		if(sCompanyTel == null) sCompanyTel = "";
		String sUserName = "";//名称
		String sCertID = "";//身份证号
		String sStatus = "";//判断状态
		
		String sSql = "SELECT CASE WHEN EmployeeType = '01' THEN 'true'  ELSE 'false' END,username,certid  "
		+" FROM user_info where CompanyTel ='"+sCompanyTel+"'";
		
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sStatus = Tools.getObjectToString(rs.getString(1));
			sUserName = Tools.getObjectToString(rs.getString(2));
			sCertID  = Tools.getObjectToString(rs.getString(3));
		}
		rs.getStatement().close();
		
		//根据参数返回用户状态
		if("true".equals(sStatus)){
			responseMap.put("Status", "Success");
			responseMap.put("Param", "该用户为SA");
			responseMap.put("UserName", sUserName);
			responseMap.put("CertID", sCertID);
		}else if("false".equals(sStatus)){
			responseMap.put("Status", "Success");
			responseMap.put("Param", "该用户为店员");
			responseMap.put("UserName", sUserName);
			responseMap.put("CertID", sCertID);
		}else{
			responseMap.put("Status", "Success");
			responseMap.put("Param", "不是SA或店员");
		}
		
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
