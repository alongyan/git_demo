package com.amarsoft.server.util;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

public class DataConvert {
	
	/**
	 * ���Ӷ�nullֵ���ж�
	 * @param data
	 * @return
	 */
	public static String toString(String data) {
		if (data == null) {
			return "";
		} else {
			return data;
		}
	}
	
	/**
	 * �����̶������Ŀո�
	 * @param size
	 * @return
	 */
	public static String complementSpace(int size) {
		String spaceString = "";
		for (int i = 0; i < size; i++) {
			spaceString = spaceString + " ";
		}
		return spaceString;
	}
	
	/**
	 * �ڸ������ַ�������С�ڸ����ĳ���ʱ,���ұ��Կո���
	 * @param str
	 * @param length
	 * @param encoding
	 * @return
	 */
	public static String complementSpace(String str, int length , String encoding) {

		int size = getBytes(str , encoding).length;
		for (int i = size; i < length; i++) {
			str = str + " ";
		}
		return str;
	}
	
	/**
	 * ���ݳ��ȴﲻ��ָ������ʱ��߲���
	 * @param str
	 * @param length
	 * @param spe
	 * @param encoding
	 * @return
	 */
	public static String complementZero(String str,int length,String encoding)
	{
		int size = getBytes(str , encoding).length;
		for (int i = size; i < length; i++) {
			str = "0"+str;
		}
		return str;
	}

	public static byte[] getBytes(String str , String encoding) {
		try {
			return str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			return str.getBytes();
		}
	}
	
	/**
	 * 
	 * @param info
	 * @return
	 */
	public static StringBuilder Map2String(Map<String,String> info)
	{
		StringBuilder sbuilder = new StringBuilder(50);
		
		Iterator it = info.keySet().iterator();
		
		while(it.hasNext())
		{
			String key = (String) it.next();
			
			sbuilder.append(info.get(key));
		}
		
		return sbuilder;
	}
	
	/**
	 * 
	 * @param info
	 * @return
	 */
	public static String Map2String2(Map<String,String> info)
	{
		StringBuilder sbuilder = new StringBuilder(50);
		Iterator it = info.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String) it.next();
			
			sbuilder.append("<"+key+">"+info.get(key)+"</>");
		}
		return sbuilder.toString();
	}
	
	/**
	 * ��ʽ������
	 * @param ileng
	 * @return
	 */
	public static String formatLength(int ileng)
	{
		return formatLength(ileng,"00000000");
	}
	
	public static String formatLength(long ileng)
	{
		return formatLength(Integer.parseInt(ileng+""));
	}
	
	public static String formatLength(int ilen,String format)
	{
		java.text.DecimalFormat dFormat = new DecimalFormat();
		dFormat.applyPattern(format);
		String temp = dFormat.format(ilen);
		dFormat = null;
		return temp;
	}
	
	/**
	 * ��ȡһ���ַ���ָ�����뷽ʽ�µĳ���
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String formatLength(String str,String encoding)
	{
		byte[] temp = DataConvert.getBytes(str, encoding);
		
		int i = temp.length;
		
		temp = null;
		
		return formatLength(i);
	}
	
	/**
	 * ��ȡһ���ַ���ָ�����뷽ʽ�µĳ���
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String formatLength(String str,String encoding,String format)
	{
		byte[] temp = DataConvert.getBytes(str, encoding);
		int i = temp.length;
		temp = null;
		return formatLength(i,format);
	}

}
