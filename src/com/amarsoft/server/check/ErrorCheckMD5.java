package com.amarsoft.server.check;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.JMTimesEncrypt;

public class ErrorCheckMD5 extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			Map<String, String> md5Map = getMD5Map(requestMap);
			JMTimesEncrypt je = new JMTimesEncrypt();
			String keyMD5j = je.initFingerprint(getItemName(sqlQuery),md5Map);
			String keyMD5 = (String)requestMap.get("key");
			logger.info(keyMD5 + "\n" + keyMD5j);
			return keyMD5.equals(keyMD5j);
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
	/**
	 * @describe 该方法用于获取加密字符串
	 * @param sqlQuery
	 * @return
	 */
	private String getItemName(SQLQuery sqlQuery){
		String sItemDescribe = "";
		String sSql = "select  itemdescribe from code_library  where codeno = 'JMTimesService' and itemno = '020'";
		try {
			sItemDescribe = sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sItemDescribe;
	}
	private Map<String, String> getMD5Map(Map<String, Object> requestMap){
		Map<String, String> retMap = new HashMap<String, String>();
		for(Iterator<String> it = requestMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			if("Channel".equalsIgnoreCase(key) || "TradCode".equalsIgnoreCase(key) || "key".equalsIgnoreCase(key) ){
				continue;
			}
			retMap.put(key, (requestMap.get(key) + "").replaceAll(" ", "+"));
			System.out.println("||||"+key+"="+retMap.get(key));
		}
		return retMap;
	}
}
