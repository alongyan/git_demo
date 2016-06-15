package com.amarsoft.server.check;

import java.sql.ResultSet;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author yhwang
 * @describe ������������û����û��������ȷ��
 * @return boolean
 */

public class ErrorCheckUserStatus extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sUserID = Tools.getObjectToString(requestMap.get("UserID"));
			String sPassWord = Tools.getObjectToString(requestMap.get("Password"));
            
			String sSql =" SELECT  COUNT(1)  FROM user_info WHERE loginid = '"+sUserID+"' ";
			ResultSet rs =  sqlQuery.getResultSet(sSql);
			int count =0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			rs.getStatement().close();
			
			if(count != 0){
				sSql =" SELECT  COUNT(1)  FROM user_info WHERE loginid = '"+sUserID+"'  and password ='"+sPassWord+"' ";
				ResultSet rs1 =  sqlQuery.getResultSet(sSql);
				int count1 =0;
				if(rs.next()){
					count1 = rs1.getInt(1);
				}
				rs1.getStatement().close();
				if(count1 != 0){
					return true;
				}else{
					Tools.insertErrorMessage("Password","�������",sProjectNo,sqlQuery);
					return  false;
				}
			}else{
				Tools.insertErrorMessage("UserID","�����ڸ��û�ID",sProjectNo,sqlQuery);
				return  false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
