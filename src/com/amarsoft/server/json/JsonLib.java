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
	        * ���� HashMap
	        */
	       Map<String, Object> map = new HashMap<String, Object>();
	       map.put("name", "Tom");
	       map.put("age", 33);
	       JSONObject jsonObject = JSONObject.fromObject(map);
	       System.out.println(jsonObject);

//	       /**
//	        * ���� JavaBean
//	        */
//	       Person person = new Person("A001", "Jack");
//	       jsonObject = jsonObject.fromObject(person);
//	       System.out.println(jsonObject);

	       /**
	        * ����Ƕ�׵Ķ���
//	        */
//	       map.put("person", person);
//	       jsonObject = jsonObject.fromObject(map);
//	       System.out.println(jsonObject);
	   }

	public static void test2() {
		/**
		 * �� Array ������ Json ��
		 */
		String[] str = { "Jack", "Tom", "90", "true" };
		JSONArray json = JSONArray.fromObject(str);
		System.err.println(json);

		/**
		 * �������飬ע�����ֺͲ���ֵ
		 */
		Object[] o = { "����", "�Ϻ�", 89, true, 90.87 };
		json = JSONArray.fromObject(o);
		System.err.println(json);

		/**
		 * ʹ�ü�����
		 */
		List<String> list = new ArrayList<String>();
		list.add("Jack");
		list.add("Rose");
		json = JSONArray.fromObject(list);
		System.err.println(json);

		/**
		 * ʹ�� set ��
		 */
		Set<Object> set = new HashSet<Object>();
		set.add("Hello");
		set.add(true);
		set.add(99);
		json = JSONArray.fromObject(set);
		System.err.println(json);
	}
}