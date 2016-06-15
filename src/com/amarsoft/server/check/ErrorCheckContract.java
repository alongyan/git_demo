package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author jxsun
 * @describe 该类用来检查贷款信息是否存在
 * @return boolean
 */

public class ErrorCheckContract extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));//交易流水号（订单号）
			String sCertID = Tools.getObjectToString(requestMap.get("CertID"));//证件号码
			String sSql = " SELECT  CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END  from business_contract where SerialNo = '"+sSerialNo+"'"+
			" and  CertID = '"+sCertID+"'";
			String sStatus = sqlQuery.getString(sSql);
			if("false".equals(sStatus)){
				Tools.insertErrorMessage("CompanyTel","传入信息有误，请核实",sProjectNo,sqlQuery);
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
