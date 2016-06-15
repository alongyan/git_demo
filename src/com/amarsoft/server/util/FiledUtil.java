package com.amarsoft.server.util;

import java.text.DecimalFormat;

public class FiledUtil {
	
	private FiledUtil(){};
	public static boolean checkType(String value, String type, String length) {
		if("String".equalsIgnoreCase(type)){
			if(value.getBytes().length > Integer.parseInt(length)){
				return false;
			}
		}else if("Double".equalsIgnoreCase(type)){
			try {
				if(value == null){
					return false;
				}
				Double.parseDouble(value);
				String[] valueArr = value.split("\\.");
				String[] lengthArr = length.split(",");
				if(valueArr.length != 2 || lengthArr.length != 2){
					return false;
				}
				if(valueArr[0].length() > Integer.parseInt(lengthArr[0]) || valueArr[1].length() > Integer.parseInt(lengthArr[1])){
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}else if("Integer".equalsIgnoreCase(type)){
			try {
				if(value == null){
					return false;
				}
				Integer.parseInt(value);
				if(value.length() > Integer.parseInt(length)){
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	public static String getStrValue(String value, String type, String length){
		String retValue = value;
		if("Double".equalsIgnoreCase(type)){
			try {
				double dValue = Double.parseDouble(value);
				String[] valueArr = value.split("\\.");
				String[] lengthArr = length.split(",");
				String format = "#0." + getReStr("0", Integer.parseInt(lengthArr[1]));
				DecimalFormat df = new DecimalFormat(format);
				return df.format(dValue);
			} catch (NumberFormatException e) {
				return retValue;
			}
		}
		return retValue;
	}
	private static String getReStr(String string, int length) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < length; i ++){
			sb.append(string);
		}
		return sb.toString();
	}
	public static void main(String[] args){
		System.out.println(FiledUtil.getStrValue("334.99", "double", "3,4"));
	}
}
