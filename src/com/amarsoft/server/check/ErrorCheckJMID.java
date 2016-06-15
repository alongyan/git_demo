package com.amarsoft.server.check;

import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

public class ErrorCheckJMID extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sJMID = Tools.getObjectToString(requestMap.get("JMID"));
			String sSql = "select count(*) from business_apply where jimuid = '"+sJMID+"'";
			int iCount = sqlQuery.getInt(sSql);
			
			if(iCount==0){
				Tools.insertErrorMessage("JMID","不存在该笔业务，请核实",sJMID+"ItemStatus",sqlQuery);
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
