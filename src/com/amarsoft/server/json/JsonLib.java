package com.amarsoft.server.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonLib {
	
	public static void main(String[] args){
		test1();
	}
	public static void test1() {
	       /**
	        * 解析 HashMap
	        */
	       Map<String, Object> map = new HashMap<String, Object>();
	       map.put("name", "Tom");
	       map.put("age", 33);
	       JSONObject jsonObject = JSONObject.fromObject(map);
	       System.out.println(jsonObject);

//	       /**
//	        * 解析 JavaBean
//	        */
//	       Person person = new Person("A001", "Jack");
//	       jsonObject = jsonObject.fromObject(person);
//	       System.out.println(jsonObject);

	       /**
	        * 解析嵌套的对象
//	        */
//	       map.put("person", person);
//	       jsonObject = jsonObject.fromObject(map);
//	       System.out.println(jsonObject);
	   }

	public static void test2() {
		/**
		 * 将 Array 解析成 Json 串
		 */
		String[] str = { "Jack", "Tom", "90", "true" };
		JSONArray json = JSONArray.fromObject(str);
		System.err.println(json);

		/**
		 * 对像数组，注意数字和布而值
		 */
		Object[] o = { "北京", "上海", 89, true, 90.87 };
		json = JSONArray.fromObject(o);
		System.err.println(json);

		/**
		 * 使用集合类
		 */
		List<String> list = new ArrayList<String>();
		list.add("Jack");
		list.add("Rose");
		json = JSONArray.fromObject(list);
		System.err.println(json);

		/**
		 * 使用 set 集
		 */
		Set<Object> set = new HashSet<Object>();
		set.add("Hello");
		set.add(true);
		set.add(99);
		json = JSONArray.fromObject(set);
		System.err.println(json);
	}
}