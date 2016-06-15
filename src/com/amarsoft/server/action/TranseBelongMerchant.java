package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类根据店员工号返回门店编号
 * @author yhwang
 *
 */
public class TranseBelongMerchant extends Action {
	private Logger logger = Logger.getLogger(TranseBelongMerchant.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sUserID = Tools.getObjectToString(requestMap.get("UserID"));//获取店员工号
		if(sUserID == null) sUserID = "";
		String sbelongMerchant = "";//门店编号
		String sMerchantName = "";//门店名称
		
		String sSql = "select belongMerchant from user_info where EmployeeType='01' and userid = '"+sUserID+"'";
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sbelongMerchant = Tools.getObjectToString(rs.getString(1));
		}
		sSql = "select MerchantsName from merchants_info where MerchantsNo = '"+sbelongMerchant+"'";
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sMerchantName = Tools.getObjectToString(rs.getString(1));
		}
		rs.getStatement().close();
		
		//根据参数返回用户状态
		responseMap.put("Status", "Success");
		responseMap.put("MerchantNo", sbelongMerchant);
		responseMap.put("MerchantsName", sMerchantName);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
