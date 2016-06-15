package com.amarsoft.server.action;

import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class JMTestAction extends Action {

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		requestMap.get("");
		responseMap.put("name", "уехЩ");
		responseMap.put("age", 23);
		return responseMap;
	}

}
