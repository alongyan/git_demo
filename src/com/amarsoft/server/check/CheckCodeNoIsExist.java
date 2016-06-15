package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.MD5;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class CheckCodeNoIsExist extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sCodeName = Tools.getObjectToString(requestMap.get("CodeName"));
			
			String count = sqlQuery.getString("select CodeName from code_catalog where codeName = '"+sCodeName+"'");
			if(count!=null){
				return true;
			}else{
				Tools.insertErrorMessage("Error", sCodeName+"²»´æÔÚ£¡",sProjectNo , sqlQuery);
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
