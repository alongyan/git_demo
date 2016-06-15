package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.StrUtil;

public class INMeMeDaiAction extends Action {

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
			String sBusinessType = "10080";
			String sql = "select bc.jimuid as financingProjectID , 'Ã´Ã´´û' as source ,"
			    + " bc.customername  as projectName,'Submitted' as status,"
				+ " bc.businesssum as amount ,"
			    + " bc.FirstPaymentDate as firstRepaymentDateStr,bc.DueDate as repaymentDateStr,"
				+ " bc.monthRepayment as payDateEveryMonth, "
				+ " getItemName('UnitNature',ii.UNITNATURE) as companyNature,bc.creditcity as applyCity , "
				+ " ii.Selfmonthincome as checkedIncome, getItemName('EducationExperience',ii.EduExperience) as academic , "
				+ " (select CardRank from MMCREDIT_INFO where objectno=bc.relativeserialno) as creditCardLevel,"
				+ " (select CreditSum from CUSTOMER_CREDIT where relativecertid2=bc.customerid) as creditCardAmount , "
				+ " bc.customername as borrowerName, bc.oldlcno as projectNo , "
				+ " (select certid from customer_info where customerid = bc.customerid) as borrowerIdCard,"
				+ " bc.termmonth as financingMaturity, bc.serialno as contractNo "
				+ " from business_contract bc ,ind_info ii"
				+ "  where bc.customerid = ii.customerid and bc.occurdate >= '"+sStartTime+"' and bc.occurdate <= '"+sEndTime
				+ "' and bc.businesstype like '"+sBusinessType+"%'";
				//+ " and nvl(itemstatus, ' ')='"+sStatus+"'";
			rs = sqlQuery.getResultSet(sql);
			System.out.println(rs.getMetaData().getColumnCount());
			ArrayList data = new ArrayList();
			int iTotalCount = 0;
			int iDropCount = 0;
			while(rs.next()){
				Map subData = new HashMap();
				subData.put("financingProjectID", StrUtil.notNull(rs.getString("financingProjectID")));
				subData.put("source", StrUtil.notNull(rs.getString("source")));
				subData.put("projectName", StrUtil.notNull(rs.getString("projectName")));
				subData.put("status", StrUtil.notNull(rs.getString("status")));
				subData.put("amount",  rs.getDouble("amount"));
				subData.put("firstRepaymentDateStr", StrUtil.notNull(rs.getString("firstRepaymentDateStr")).replaceAll("/", "-"));
				subData.put("repaymentDateStr", StrUtil.notNull(rs.getString("repaymentDateStr")).replaceAll("/", "-"));
				subData.put("payDateEveryMonth", rs.getDouble("payDateEveryMonth"));
				subData.put("companyNature", StrUtil.notNull(rs.getString("companyNature")));
				subData.put("applyCity", StrUtil.notNull(rs.getString("applyCity")));
				subData.put("checkedIncome", rs.getDouble("checkedIncome"));
				subData.put("academic", StrUtil.notNull(rs.getString("academic")));
				subData.put("creditCardLevel", StrUtil.notNull(rs.getString("creditCardLevel")));
				subData.put("creditCardAmount", rs.getDouble("creditCardAmount"));
				subData.put("borrowerName", StrUtil.notNull(rs.getString("borrowerName")));
				subData.put("projectNo", StrUtil.notNull(rs.getString("projectNo")));
				subData.put("borrowerIdCard", StrUtil.notNull(rs.getString("borrowerIdCard")));
				subData.put("financingMaturity", rs.getDouble("financingMaturity"));
				subData.put("contractNo", StrUtil.notNull(rs.getString("contractNo")));
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
