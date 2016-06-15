package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.MD5;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class CheckMerchantsNoExist extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));
			String count = sqlQuery.getString("select * from sa_merchants where MerchantsNo = '"+sMerchantsNo+"'");
			if(count!=null){
				return true;
			}else{
				Tools.insertErrorMessage("Error", "该门店编号不存在！",sProjectNo , sqlQuery);
				return false;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
