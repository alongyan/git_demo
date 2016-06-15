package com.amarsoft.server.check;
/**
 * �������ͻ����ʽ������Ϊ3000
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Arith;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMDXBusinessSum extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			double dBusinessSum = Double.parseDouble((String) requestMap.get("FinancingAmount"));//�J����~
			return dBusinessSum <= 3000.00 ;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
