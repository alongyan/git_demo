package com.amarsoft.server.check;
/**
 * �Խ�ң��ͻ���������Ϊ60��
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class ErrorCheckTJJBusinessSum extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			double dBusinessSum = Double.parseDouble((String) requestMap.get("FinancingAmount"));//�J����~
			return dBusinessSum <= 600000.00 ;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
