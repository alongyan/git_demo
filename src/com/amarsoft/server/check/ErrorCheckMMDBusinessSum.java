package com.amarsoft.server.check;
/**
 * 么么贷：同一客户在贷金额不可大于2万，如同一人在么么贷有多笔未结清，金额之和不能大于2万
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Arith;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMMDBusinessSum extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			double dBusinessSum = Double.parseDouble((String) requestMap.get("FinancingAmount"));//貸款金額
			String sSPID = (String)requestMap.get("SPID");
			String sCustomerID = sqlQuery.getString("select CustomerID from CUSTOMER_INFO where CertType='Ind01' and CertID='"+(String)requestMap.get("IdentityNumber")+"'");//身份證
			if(StrUtil.isNull(sSPID)){
				throw new Exception("SPID不正确");
			}
			if(StrUtil.isNull(sCustomerID)){
				return true;
			}
			//查詢該客戶通過么么貸渠道進行的合同，餘額沒有取金額
			double sumBusinessSum = sqlQuery.getDouble("select sum(nvl(Balance,BusinessSum)) from BUSINESS_CONTRACT where CustomerID='"+sCustomerID+"' and SPID='"+sSPID+"'");
			return Arith.add(dBusinessSum, sumBusinessSum) <= 20000.00 ;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
