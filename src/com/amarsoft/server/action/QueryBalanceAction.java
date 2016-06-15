package com.amarsoft.server.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

/** @author gqli
 * 授信卡额度查询
 * 描述：根据签约卡号渠道类型查询出总额度、可用额度、授信起始日和到期日
 */
public class QueryBalanceAction extends Action {

	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		try {
			responseMap.put("Count1", 13123);
			responseMap.put("Count2", 45);
			ArrayList list = new ArrayList();
			list.add("test");
			list.add(34);
			list.add(12.56);
			ArrayList list2 = new ArrayList();
			
			Map map = new HashMap();
			map.put("a", "a");
			map.put("b", 123);
			list2.add(map);
			map = new HashMap();
			map.put("a", "b");
			map.put("b", 456);
			list2.add(map);
			
			responseMap.put("MaturityList", list);
			responseMap.put("MaturityMap", list2);
		} catch (Exception e) {
			printLog(e);
			throw e;
		} 
		return responseMap;
	}
		
	
}