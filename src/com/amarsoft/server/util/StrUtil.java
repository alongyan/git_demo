package com.amarsoft.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	private StrUtil(){}
	/**
	 * 获取子字符串，在src字符串中查找从fromStr 到 toStr 之间的字符串
	 * @param src
	 * @param fromStr
	 * @param toStr
	 * @return
	 */
	public static String getSubStr(String src, String fromStr, String toStr){
		int startIndex = src.indexOf(fromStr);
		int length = fromStr.length();
		
		if(startIndex < 0)
			return null;
		int endIndex = src.indexOf(toStr, startIndex + length);
		return src.substring(startIndex + length, endIndex);
	}
	
	/**
	 * 判断是否为空或""
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		return null == str || "".equals(str.trim());
	}
	
	/**
	 * 将str用","分割，并填充到ArrayList中，返回ArrayList
	 * @param str
	 * @return
	 */
	public static ArrayList getListFromArr(String str){
		ArrayList srcList = null;
		if(null != str && str.trim().length() > 0){
			String[] strArr = str.split(",");
			srcList = new ArrayList();
			for(int i = 0; i < strArr.length; i ++){
				srcList.add(strArr[i].trim());
			}
		}
		return srcList;
	}
	
	public static String notNull(String str){
		return str == null?"":str;
	}
	
	public static String getStringForMap(Map map, String strArr){
		StringBuffer sb = new StringBuffer("");
		for(Iterator<String> it = map.keySet().iterator(); it.hasNext();){
			String key = it.next();
			if(strArr == null || strArr.indexOf(key + ",") < 0){
				sb.append(notNull(String.valueOf(map.get(key))));
			}
		}
		return sb.toString();
	}
	
	public static Map<String, String> getMapForString(String strArr, String[] splitArr){
		if(strArr == null){
			return null;
		}
		Map<String, String> valueMap = new HashMap<String, String>();
		String[] strArr1 = strArr.split(splitArr[0]);
		String[] strArrTemp = null;
		for(int i = 0; i < strArr1.length; i ++){
			strArrTemp = strArr1[i].split(splitArr[1]);
			valueMap.put(strArrTemp[0].trim(), strArrTemp[1].trim());
		}
		return valueMap;
	}
	
	/**
    *
    * @param str
    *         需要过滤的字符串
    * @return
    * @Description:过滤数字以外的字符
    */
	public static String filterUnNumber(String str) {
		// 只允数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);//替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
   }
	/**
	 * 生成数据库的查询语句
	 * @param table 插入的表
	 * @param valueMap  插入的列和值，key为列，value为值
	 * @return
	 */
	public static String getInsertSQL(String table, Map<String, Object> valueMap){
		StringBuffer sbKey = new StringBuffer("insert into ");
		sbKey.append(table).append("( ");
		StringBuffer sbValue = new StringBuffer(") values (");
		for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			sbKey.append(key).append(", ");
			sbValue.append(getStringValue(valueMap.get(key))).append(", ");
		}
		
		sbKey.delete(sbKey.length()-2, sbKey.length()-1);
		sbValue.delete(sbValue.length()-2, sbValue.length()-1);
		sbValue.append(")");
		return sbKey.append(sbValue).toString();
	}
	
	private static Object getStringValue(Object obj) {
		if(obj instanceof Double || obj instanceof Integer){
			return String.valueOf(obj);
		}else{
			return "'" + String.valueOf(obj) + "'";
		}
	}
	
	public static void main(String[] args){
		Map map = new HashMap();
		map.put("SerialNo", "2015098");
		map.put("BusinessSum", 1000.00);
		map.put("TermMonth", 12);
		System.out.println(getInsertSQL("BUSINESS_APPLY", map));
	}
}
