package com.amarsoft.server.check;

import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.config.ErrorConfig;


public abstract class ErrorCheck implements Check {
	protected Logger logger = Logger.getLogger(ErrorCheck.class);
	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		// TODO Auto-generated method stub
		return true;
	}
	
	protected void printLog(Exception e){
		e.printStackTrace();
		logger.info(e.getMessage());
		logger.error(e.getMessage());
	}
}
