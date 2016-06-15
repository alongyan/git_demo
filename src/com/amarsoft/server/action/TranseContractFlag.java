package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来获得打款状态
 * @author jxsun
 *
 */
public class TranseContractFlag extends Action {
	private Logger logger = Logger.getLogger(TranseContractFlag.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));//交易流水号（订单号）
		String sCertID = Tools.getObjectToString(requestMap.get("CertID"));//证件号码
		String sContractStatus = "";//产品类型
		String sSql = " SELECT contractstatus from business_contract where SerialNo = '"+sSerialNo+"'"+
		" and  CertID = '"+sCertID+"'";
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sContractStatus = Tools.getObjectToString(rs.getString("contractstatus"));
		}
		rs.getStatement().close();
			//根据参数返回用户状态
			responseMap.put("Status", "Sucess");
			responseMap.put("Param", sContractStatus);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
