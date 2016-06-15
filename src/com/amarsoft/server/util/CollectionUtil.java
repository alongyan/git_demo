package com.amarsoft.server.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

public class CollectionUtil {


	private CollectionUtil(){
		
	}
	/**
	 * 用於map類型轉換
	 * @param fromMap
	 * @param toMap
	 * @return
	 */
	public static Map getChangeMap(Map fromMap, Map toMap){
		for(Iterator<String> it = fromMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			toMap.put(key, fromMap.get(key));
		}
		return toMap;
	}
	/**
	 * 将map对象中的Key转为大小写不敏感的map
	 * @param map
	 * @return
	 */
	public static Map getCaseInsensitiveMap(Map map){
		CaseInsensitiveMap caseInMap = new CaseInsensitiveMap();
		for(Iterator<String> it = map.keySet().iterator(); it.hasNext();){
			String key = it.next();
			caseInMap.put(key, map.get(key));
		}
		return caseInMap;
	}
	
	public static void main(String[] args){
		Map<String, Object> result = new HashMap(); 
		result = getCaseInsensitiveMap(result);
		  result.put("aaa", "ok");
		  System.out.println(result.get("aaa"));
		  System.out.println(result.get("aAa"));
		  System.out.println(result.get("AAa"));
		  System.out.println(result.get("AAA"));

	}
}
