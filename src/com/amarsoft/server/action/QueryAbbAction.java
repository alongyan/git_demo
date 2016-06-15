package com.amarsoft.server.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class QueryAbbAction extends Action {

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		System.out.println(requestMap.get("StartTime"));
		responseMap.put("abc", 13123);
		responseMap.put("nnn", 45);
		ArrayList list = new ArrayList();
		list.add("A");
		list.add("B");
		list.add("C");
		ArrayList list2 = new ArrayList();
		
		Map map = new HashMap();
		map.put("UU", "aDDD");
		map.put("BB", 123);
		list2.add(map);
		map = new HashMap();
		map.put("UU", "NNN");
		map.put("BB", 456);
		list2.add(map);
		
		responseMap.put("MaturityList", list);
		responseMap.put("MaturityMap", list2);
		return responseMap;
	}

}
