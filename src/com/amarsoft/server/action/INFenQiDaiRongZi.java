package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.StrUtil;

public class INFenQiDaiRongZi extends Action {
	//http://url/api/fqdk/GetProjects?SPID=12001&=2014-01-01&EndTime=2014-02-05&Token=
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
			String sBusinessType = "1040010";
			String sql = "select BC.CustomerName,BC.SerialNo,BC.CertID,BC.JimuID,BC.VouchCompany,BC.ProjectName,BC.ItemStatus,BC.BusinessSum,BC.TermMonth,BC.FirstPaymentDate,BC.ContractDate,BC.MonthPayment,BC.SchoolName"
				+ " from BUSINESS_CONTRACT BC"
				+ " where bc.occurdate >= '"+sStartTime+"' and bc.occurdate < '"+sEndTime
				+ "' and bc.businesstype in('"+sBusinessType+"')"
				+ " and nvl(status, ' ')!='"+sStatus+"'";
			System.out.println(sql);
			rs = sqlQuery.getResultSet(sql);
			ArrayList data = new ArrayList();
			int iTotalCount = 0;
			int iDropCount = 0;
			while(rs.next()){
				Map subData = new HashMap();
				subData.put("CustomerName", StrUtil.notNull(rs.getString("CustomerName")));
				subData.put("ContractSerialNo", StrUtil.notNull(rs.getString("SerialNo")));
				subData.put("CertID", StrUtil.notNull(rs.getString("CertID")));
				subData.put("JMID", StrUtil.notNull(rs.getString("JimuID")));
				subData.put("GuarantyName", StrUtil.notNull(rs.getString("VouchCompany")));
				subData.put("ProjectName", StrUtil.notNull(rs.getString("ProjectName")));
				subData.put("ProjectStatus", StrUtil.notNull(rs.getString("ItemStatus")));
				subData.put("ProjectBusiness", rs.getDouble("BusinessSum"));
				subData.put("ProjectMonth", rs.getInt("TermMonth"));
				subData.put("FirstRepayDate", StrUtil.notNull(rs.getString("FirstPaymentDate")));
				subData.put("ContractRepayDate", StrUtil.notNull(rs.getString("ContractDate")));
				subData.put("RepayDate", StrUtil.notNull(rs.getString("MonthPayment")));
				subData.put("SchoolName", StrUtil.notNull(rs.getString("SchoolName")));
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
