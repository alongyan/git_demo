package com.amarsoft.server.check;
/**
 * 买单侠：客户单笔金额上限为3000
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Arith;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMDXBusinessSum extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			double dBusinessSum = Double.parseDouble((String) requestMap.get("FinancingAmount"));//貸款金額
			return dBusinessSum <= 3000.00 ;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
