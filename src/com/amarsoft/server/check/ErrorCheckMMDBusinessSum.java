package com.amarsoft.server.check;
/**
 * ôô����ͬһ�ͻ��ڴ����ɴ���2����ͬһ����ôô���ж��δ���壬���֮�Ͳ��ܴ���2��
 */
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Arith;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMMDBusinessSum extends ErrorCheck {

	@Override
	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			double dBusinessSum = Double.parseDouble((String) requestMap.get("FinancingAmount"));//�J����~
			String sSPID = (String)requestMap.get("SPID");
			String sCustomerID = sqlQuery.getString("select CustomerID from CUSTOMER_INFO where CertType='Ind01' and CertID='"+(String)requestMap.get("IdentityNumber")+"'");//����C
			if(StrUtil.isNull(sSPID)){
				throw new Exception("SPID����ȷ");
			}
			if(StrUtil.isNull(sCustomerID)){
				return true;
			}
			//��ԃԓ�͑�ͨ�^ôô�J�����M�еĺ�ͬ���N�~�]��ȡ���~
			double sumBusinessSum = sqlQuery.getDouble("select sum(nvl(Balance,BusinessSum)) from BUSINESS_CONTRACT where CustomerID='"+sCustomerID+"' and SPID='"+sSPID+"'");
			return Arith.add(dBusinessSum, sumBusinessSum) <= 20000.00 ;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
