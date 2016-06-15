package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.MD5;

public class ErrorCheckMMDProjectNo extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {

			String sProjectNo = (String) requestMap.get("ProjectNo");
			return 0 == (sqlQuery
					.getInt("select count(OLDLCNO)  from business_apply where Oldlcno = '"
							+ sProjectNo + "' and SPID='"+requestMap.get("SPID")+"'"));
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}

}
