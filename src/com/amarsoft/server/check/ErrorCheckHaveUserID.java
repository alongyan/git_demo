package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author jxsun
 * @describe 该类用来判断该门店下是否存在店员
 * @return boolean
 */

public class ErrorCheckHaveUserID extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));
			String sSql = "select CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END from User_info where belongMerchant = '"+sMerchantsNo+"'";
			String sStatus = sqlQuery.getString(sSql);
			String sIsHave = sqlQuery.getString("select CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END from merchants_info where MerchantsNo = '"+sMerchantsNo+"'");
			if("true".equals(sIsHave)){
				if("false".equals(sStatus)){
					Tools.insertErrorMessage("MerchantsNo","系统中没有该门店下的店员信息",sProjectNo,sqlQuery);
					return false;
				}else{
					return true;
				}
			}else{
				Tools.insertErrorMessage("MerchantsNo","门店编号不存在",sProjectNo,sqlQuery);
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
