package com.amarsoft.server.util;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;

import com.amarsoft.server.dao.SQLQuery;

public class GetJMID {
	
	public static String getJMID(SQLQuery sqlQuery) throws Exception{
		String sJMID = "";//获取JMID
		try {
			HashMap hget = getUserKey(sqlQuery);
			sJMID = GetJMTest.sendGet((String) hget.get("Url"),
					(String) hget.get("Params"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return sJMID;
	}
	
	/**
	 * @describe 该方法用于获取传递参数内容
	 * @param Sqlca
	 * @return
	 * @throws Exception 
	 */
	public static HashMap getUserKey(SQLQuery sqlQuery) throws Exception{
		HashMap hReturn = new HashMap();
		HashMap hm = new HashMap();
		String sItemNo = "";
		String sItemDescribe = "";
		String sParams = "";
		ResultSet rs = null;
		String sSql = "select ItemNo,ItemDescribe from code_library where codeno = 'JMID'";
		try {
			rs=sqlQuery.getResultSet(sSql);
			while (rs.next()){
				sItemNo = rs.getString("ItemNo"); if(sItemNo == null) sItemNo = "";
				sItemDescribe = rs.getString("ItemDescribe");  if(sItemDescribe == null) sItemDescribe = "";
				hm.put(sItemNo, sItemDescribe);
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		String smerchantId = (String) hm.get("020");//机构编号
		String sUserKey = (String) hm.get("010");//UserKey
		String sUrl = (String) hm.get("030");//URL 地址
		//获取当前系统日期
		Date date = new Date();
		long timeStamp = date.getTime();
		//获取MD5加密后内容
		String stoken = MD5.getMD5Code(smerchantId+"&"+timeStamp+"&"+sUserKey).toUpperCase();
		sParams = "merchantId="+smerchantId+"&timeStamp="+timeStamp+"&token="+stoken;
		hReturn.put("Url", sUrl);
		hReturn.put("Params", sParams);
		
		return hReturn;
	}
}
