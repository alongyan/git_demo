package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMD5ForMMD extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String srcStr = StrUtil.getStringForMap(requestMap, "Ticket,TradCode,files,file,");
			for(Iterator<String> it = requestMap.keySet().iterator(); it.hasNext();){
				String key = it.next();
				System.out.println("{" + key + ":" + requestMap.get(key));
			}
			String sUserKey = sqlQuery.getString("select itemattribute from code_library where codeno = 'JMMRetail' and itemdescribe = '"+requestMap.get("SPID")+"'");
			srcStr = srcStr + sUserKey;
			String token = EncryptUtil.md5Digest(srcStr);
			logger.info("token(src)=" + srcStr);
			logger.info("token(url)=" + requestMap.get("Ticket") + "\t" + "token(º”√‹)=" + token);
			return token.equals(requestMap.get("Ticket"));
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
