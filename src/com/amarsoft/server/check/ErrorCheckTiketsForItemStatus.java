package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.MD5;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class ErrorCheckTiketsForItemStatus extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("JMID"))+"ItemStatus";
			sqlQuery.execute("delete from Error_RetailMessage where projectno='"+sProjectNo+"'");//清除错误信息记录
			for(Iterator<String> it = requestMap.keySet().iterator(); it.hasNext();){
				String key = it.next();
				System.out.println("{" + key + ":" + requestMap.get(key));
			}
			String sUserKey = sqlQuery.getString("select itemattribute from code_library where codeno = 'JMMRetail' and itemdescribe = '"+requestMap.get("SPID")+"'");
			String srcStr = Tools.getObjectToString(requestMap.get("SPID")) + sUserKey;
//			String token = EncryptUtil.md5Digest(srcStr);
			String token = MD5.getMD5Code(srcStr).toUpperCase();
			logger.info("token(src)=" + srcStr);
			logger.info("token(url)=" + requestMap.get("Ticket") + "\t" + "token(加密)=" + token);
			
			if(token.equals(requestMap.get("Ticket"))){
				return true;
			}else{
				Tools.insertErrorMessage("Ticket", "加密内容不通过",sProjectNo , sqlQuery);
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
