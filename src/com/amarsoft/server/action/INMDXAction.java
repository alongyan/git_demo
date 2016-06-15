package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.StrUtil;

public class INMDXAction extends Action {

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			String sAgent = (String) requestMap.get("AgentOrg");
			sStartTime = sStartTime.replaceAll("-", "/");
			sEndTime = sEndTime.replaceAll("-", "/");
			String sStatus = "11";
			
			String sql = "select bc.jimuid as financingProjectID , '买单侠' as source ,bc.customername as ProjectName,'Submitted' as Status," 
				+ "nvl(bc.businesssum,0) as amount ,bc.firstpaymentdate as firstDateStr,bc.dueDate as repaymentDateStr,bc.MONTHREPAYMENT as payDateEveryMonth, " 
				+ "bc.DaiPayeeName as relatedName,bc.daicardno as relatedID, (select getitemname('Marriage',ii.marriage) from ind_info ii where ii.customerid = bc.customerid) as maritalStatus,"
				+ "bc.CreditCity as applyCity, bc.customername as borrowerName,bc.oldlcno as projectNo,(select certid from customer_info where customerid = bc.customerid) as borrowerIdCard,"
				+ "bc.termmonth as financingMaturity, bc.serialno as contractNo , (select  oi.productinfo from ORDER_INFO oi where  oi.objectno = bc.serialno ) as productName,"
				+ "(select getItemName('UnitNature',UNITNATURE) from ind_info where customerid=bc.customerid) as companyNature from business_contract bc "
				+ " where bc.occurdate >= '"+sStartTime+"' and bc.occurdate <= '"+sEndTime
				//+ "' and bc.serialNO='2015072800000002"
				+ "' and bc.businesstype in("+getBusinessType(sAgent)+")"
				+ " and nvl(itemstatus, ' ')='"+sStatus+"'";
			rs = sqlQuery.getResultSet(sql);
			System.out.println(rs.getMetaData().getColumnCount());
			ArrayList data = new ArrayList();
			int iTotalCount = 0;
			int iDropCount = 0;
			while(rs.next()){
				Map subData = new HashMap();
				subData.put("financingProjectID", StrUtil.notNull(rs.getString("financingProjectID")));
				subData.put("source", StrUtil.notNull(rs.getString("source")));
				subData.put("projectName", StrUtil.notNull(rs.getString("ProjectName")));
				subData.put("status", StrUtil.notNull(rs.getString("Status")));
				subData.put("amount", rs.getDouble("amount"));  //融资金额
				subData.put("firstRepaymentDateStr", StrUtil.notNull(rs.getString("firstDateStr")).replaceAll("/", "-"));   //首次还款日
				subData.put("repaymentDateStr", StrUtil.notNull(rs.getString("repaymentDateStr")).replaceAll("/", "-"));    //合同还款日
				subData.put("payDateEveryMonth", rs.getDouble("payDateEveryMonth"));                          //每月还款日
				subData.put("relatedName", StrUtil.notNull(rs.getString("relatedName")));
				subData.put("relatedID", StrUtil.notNull(rs.getString("relatedID")));
				subData.put("maritalStatus", StrUtil.notNull(rs.getString("maritalStatus")));
				subData.put("productName", StrUtil.notNull(rs.getString("productName")));
				subData.put("companyNature", StrUtil.notNull(rs.getString("companyNature")));
				subData.put("applyCity", StrUtil.notNull(rs.getString("applyCity")));
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
	public String getBusinessType(String sAgent){
		String sSql = "select bt.typeno from business_type bt where bt.attribute11 in (select cl.itemno as ItemNo from code_library cl where cl.codeno = 'InSystemMap' and cl.bankno = '"+sAgent+"')";
		return sSql;
	}
}
