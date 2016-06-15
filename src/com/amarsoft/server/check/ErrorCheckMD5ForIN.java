package com.amarsoft.server.check;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.JMTimesEncrypt;

public class ErrorCheckMD5ForIN extends ErrorCheck {
	
	private String sUserKey = "";//用户加密秘钥
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		getUserKey(sqlQuery);
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			String sAgentOrg = (String) requestMap.get("AgentOrg");
			String sSPID = (String) requestMap.get("SPID");
			String token = sAgentOrg + sEndTime +sSPID + sStartTime + sUserKey;
			EncryptUtil eu = new EncryptUtil();
	        token = eu.md5Digest(token);
			logger.info("token(url)=" + requestMap.get("Token") + "\t" + "token(加密)=" + token);
			return token.equals(requestMap.get("Token"));
		} catch (Exception e) {
			printLog(e);
			return false;
		}
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
	private void getUserKey(SQLQuery sqlQuery){
		String sSql = "select itemdescribe from code_library where codeno = 'INSystem' and itemno = '010'";
		try {
			sUserKey =  sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
