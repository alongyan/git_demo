package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author jxsun
 * @describe 该类用来判断是否是门店店员
 * @return boolean
 */

public class ErrorCheckIsSA extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sUserID = Tools.getObjectToString(requestMap.get("UserID"));
			String sSql = "select CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END from user_info where EmployeeType='01' and userid = '"+sUserID+"'";
			String sStatus = sqlQuery.getString(sSql);
			if("false".equals(sStatus)){
				Tools.insertErrorMessage("UserID","该用户不是门店店员",sProjectNo,sqlQuery);
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
