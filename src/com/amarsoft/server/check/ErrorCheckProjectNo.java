package com.amarsoft.server.check;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.handle.HandleImageDown;
import com.amarsoft.server.handle.HandleImageUpload;
import com.amarsoft.server.util.Tools;

public class ErrorCheckProjectNo extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		ResultSet  rs=null;
		try {
			String sThirdPartySerialNo = (Tools.getObjectToString(requestMap.get("ProjectNo")));
			String sSql = "select jimuid,imagestaus from business_apply where ThirdPartySerialNo = '"+sThirdPartySerialNo+"'";
			String JMID = "";
			String sImageStatus ="";
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				JMID = rs.getString("jimuid");
				sImageStatus = rs.getString("imagestaus");
			}
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			if(JMID!=""&&JMID!=null){
				if("Success".equals(sImageStatus)){
					Tools.insertErrorMessage("ProjectNo", "&该笔业务已存在,请勿重复推送@"+JMID,sProjectNo , sqlQuery);
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		} catch (Exception e) {
			printLog(e);
			return false;
		}finally{
			if(rs!=null){
				try {
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
