package com.amarsoft.server.util;

public class DataUtil {
	private DataUtil(){
		
	}
	/**
	 * ��ȡ��������֮����·���
	 * @param fromYM  ��ʼ����
	 * @param toYM   ��ֹ����
	 * @return
	 */
	public static int getMonthBetweenMonths(String fromYM, String toYM){
		fromYM = StrUtil.filterUnNumber(fromYM);
		toYM = StrUtil.filterUnNumber(toYM);
		int iFromMonths = Integer.parseInt(fromYM.substring(0, 4)) * 12 + Integer.parseInt(fromYM.substring(4, 6));
		int iToMonths = Integer.parseInt(toYM.substring(0, 4)) * 12 + Integer.parseInt(toYM.substring(4, 6));
		return iToMonths - iFromMonths;
	}
	
	
	
	public static void main(String[] args){
		System.out.println(getMonthBetweenMonths("2015/02", "2016/02"));
	}
}
