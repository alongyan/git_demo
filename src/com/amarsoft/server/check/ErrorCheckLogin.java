package com.amarsoft.server.check;
/** 
 * 检查个贷是否在晚间批量中
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
