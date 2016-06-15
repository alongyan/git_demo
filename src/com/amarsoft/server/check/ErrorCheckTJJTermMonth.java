package com.amarsoft.server.check;
/**
 * 淘金家：期限不可大于6期
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class ErrorCheckTJJTermMonth extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sBatch = (String) requestMap.get("Batch");
			return Integer.parseInt(sBatch) <= 6;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
