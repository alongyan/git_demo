package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author jxsun
 * @describe ���������жϸ��ŵ����Ƿ���ڵ�Ա
 * @return boolean
 */

public class ErrorCheckHaveUserID extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));
			String sSql = "select CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END from User_info where belongMerchant = '"+sMerchantsNo+"'";
			String sStatus = sqlQuery.getString(sSql);
			String sIsHave = sqlQuery.getString("select CASE WHEN count(1)>0 THEN 'true'  ELSE 'false' END from merchants_info where MerchantsNo = '"+sMerchantsNo+"'");
			if("true".equals(sIsHave)){
				if("false".equals(sStatus)){
					Tools.insertErrorMessage("MerchantsNo","ϵͳ��û�и��ŵ��µĵ�Ա��Ϣ",sProjectNo,sqlQuery);
					return false;
				}else{
					return true;
				}
			}else{
				Tools.insertErrorMessage("MerchantsNo","�ŵ��Ų�����",sProjectNo,sqlQuery);
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
