package com.amarsoft.server.check;

import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;


public abstract class SystemCheck implements Check{
	protected Logger logger = Logger.getLogger(ErrorCheck.class);
	
	
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 子类用于打印日志方法
	 * @param e
	 */
	protected void printLog(Exception e){
		e.printStackTrace();
		logger.info(e.toString());
		logger.error(e.toString());
	}
}
