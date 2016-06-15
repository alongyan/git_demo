package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author jxsun
 * @describe 该类用来通过手机号检查用户是否存在
 * @return boolean
 */

public class ErrorCheckUserIsExist extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sCompanyTel = Tools.getObjectToString(requestMap.get("CompanyTel"));
			String sSql = "SELECT CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END  "
					+" FROM user_info where CompanyTel ='"+sCompanyTel+"'";
			String sStatus = sqlQuery.getString(sSql);
			System.out.println(sCompanyTel+"-----------------------"+sCompanyTel.length());
			if(11==sCompanyTel.length()){
				if("false".equals(sStatus)){
					Tools.insertErrorMessage("CompanyTel","没有手机号所对应的店员或者SA信息",sProjectNo,sqlQuery);
					return false;
				}else{
					return true;
				}
			}else{
				Tools.insertErrorMessage("CompanyTel","手机号错误",sProjectNo,sqlQuery);
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
