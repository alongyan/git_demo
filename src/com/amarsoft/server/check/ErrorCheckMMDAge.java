package com.amarsoft.server.check;
/**
 * ����20-50��
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DataUtil;

public class ErrorCheckMMDAge extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sIdentityNumber = (String) requestMap.get("IdentityNumber");
			String sYearMonth = sIdentityNumber.substring(6, 14);
			String sToday = sqlQuery.getToday();
			int iYears = DataUtil.getMonthBetweenMonths(sYearMonth, sToday)/12;
			return iYears >= 20 && iYears <= 50;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
