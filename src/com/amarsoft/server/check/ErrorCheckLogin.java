package com.amarsoft.server.check;
/** 
 * �������Ƿ������������
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class ErrorCheckLogin extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}

}
