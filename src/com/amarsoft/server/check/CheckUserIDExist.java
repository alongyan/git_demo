package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.MD5;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class CheckUserIDExist extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sUserID = Tools.getObjectToString(requestMap.get("UserID"));
			String smg = "";
			String count = sqlQuery.getString("select * from user_info where UserID = '"+sUserID+"'");
			if(count!=null){
				String count1 = sqlQuery.getString("select * from merchants_info where ApplySa = '"+sUserID+"'");
				if(count1!=null){
					return true;
				}else{
					Tools.insertErrorMessage("Error", "该SA工号下无门店！",sProjectNo , sqlQuery);
					return false;
				}
			}else{
				Tools.insertErrorMessage("Error", "该SA工号不存在！",sProjectNo , sqlQuery);
				return false;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
