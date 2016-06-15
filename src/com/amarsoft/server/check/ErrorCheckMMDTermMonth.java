package com.amarsoft.server.check;
/**
 * 么么贷：期限不可大于12期
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class ErrorCheckMMDTermMonth extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sBatch = (String) requestMap.get("Batch");
			return Integer.parseInt(sBatch) <= 12;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
