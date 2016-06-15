package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;

public class INTaoJinJiaAction extends Action {

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			sStartTime = sStartTime.replaceAll("-", "/");
			sEndTime = sEndTime.replaceAll("-", "/");
			String sStatus = "11";
			String sBusinessType = "1012000410";
			String sql = "select BC.CustomerName,BC.SerialNo,BC.CertID,BC.JimuID,BC.VouchCompany,BC.ProjectName,BC.ItemStatus,BC.BusinessSum,BC.TermMonth,BC.FirstPaymentDate,BC.ContractDate,BC.MonthPayment,BC.SchoolName"
				+ " from BUSINESS_CONTRACT BC"
				+ " where bc.occurdate >= '"+sStartTime+"' and bc.occurdate <= '"+sEndTime
				+ "' and bc.businesstype like '"+sBusinessType+"%'"
				+ " and nvl(status, ' ')='"+sStatus+"'";
			rs = sqlQuery.getResultSet(sql);
			ArrayList data = new ArrayList();
			int iTotalCount = 0;
			int iDropCount = 0;
			while(rs.next()){
				Map subData = new HashMap();
				subData.put("financingProjectID", 0);
				subData.put("source", "");
				subData.put("ProjectName", "");
				subData.put("Status", "Submitted");
				subData.put("amount", 1000000.00);
				subData.put("firstRepaymentDateStr", null);
				subData.put("repaymentDateStr", null);
				subData.put("payDateEveryMonth", null);
				subData.put("payToBorrowerAmount", null);
				subData.put("repaymentAmountByMonth", null);
				subData.put("finaceUserName", "bacd");
				subData.put("accountNo", null);
				subData.put("bankName", null);
				subData.put("provence", null);
				subData.put("area", null);
				subData.put("subbranchBank", null);
				subData.put("debtUsage", null);
				subData.put("type", "abc");
				subData.put("academic", "");
				subData.put("foundYears", null);
				subData.put("grossMargin", null);
				subData.put("repaymentAbility", null);
				subData.put("mainBusinessDescrib", null);
				subData.put("accountLocation", null);
				subData.put("operatingAreaProperty", null);
				subData.put("maritalStatus", "");
				subData.put("companyNature", "");
				subData.put("workYears", 12);
				subData.put("applyCity", null);
				subData.put("checkedIncome", 12000.000000);
				subData.put("hasHouse", null);
				subData.put("hasCar", null);
				subData.put("fieldCertification", null);
				subData.put("coborrowerOne", "");
				subData.put("coborrowerOneID", "123456789012345");
				subData.put("coborrowerTwo", null);
				subData.put("coborrowerTwoID", null);
				subData.put("coborrowerThree", null);
				subData.put("coborrowerThreeID", null);
				subData.put("coborrowerFour", null);
				subData.put("coborrowerFourID", null);
				subData.put("relatedName", "");
				subData.put("relatedID", "11111");
				subData.put("borrowerName", "");
				subData.put("projectNo", "20150402_DB_01");
				subData.put("borrowerIdCard", "532224196312280035");
				subData.put("contractNo", "Y-JM-001096"); 
				subData.put("financingMaturity", 51);
				data.add(subData);
				iTotalCount ++;
			}
			responseMap.put("Data", data);
			responseMap.put("TotalCount", iTotalCount);
			responseMap.put("DropCount", iDropCount);
			responseMap.put("Errors", new ArrayList());
		} catch (Exception e) {
			printLog(e);
			throw e;
		} finally{
			if(rs != null){
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
			}
		}
		return responseMap;
	}

}
