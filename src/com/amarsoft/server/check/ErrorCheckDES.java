package com.amarsoft.server.check;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.JMTimesEncrypt;

public class ErrorCheckDES extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			JMTimesEncrypt je = new JMTimesEncrypt();
			for(Iterator<String> it = requestMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				String value = (String) requestMap.get(key);
				if("Channel".equalsIgnoreCase(key) || "TradCode".equalsIgnoreCase(key) || "key".equalsIgnoreCase(key) ){
					continue;
				}
				requestMap.put(key, je.decryptFromBASE64(value, (getItemName(sqlQuery)).getBytes()));
				System.out.println("****" + key + "=" + requestMap.get(key));
			}
			return true;
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
		String sSql = "select  itemdescribe from code_library  where codeno = 'JMTimesService' and itemno = '030'";
		try {
			sItemDescribe = sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sItemDescribe;
	}
}
