package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;

public class LoaningCarAction extends Action {

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		//定义变量
		ResultSet rs = null;
		String sSql = "";
		
		String sCustomerName = "";//融资人姓名
		String sContractSerialNo = "";//项目编号
		String sCertID = "";//融资人身份证号
		String sJMID = "";//积木编号
		String sGuarantyName = "";//担保公司
		String sProjectName = "";//项目名称
		String sProjectStatus = "";//项目状态
		double dProjectBusiness = 0.0;//融资金额
		String sProjectMonth = "";//融资期限
		String sBusinessType = "";//产品类型
		String sBusinessTypeName = "";//产品名称
		double dSaleSum = 0.0;//销售价格
		double dFirstSaleSum = 0.0;//首付金额
		String sStartTime = (String) requestMap.get("StartTime");//开始时间
		String sEndTime = (String) requestMap.get("EndTime");//结束时间
		/*sStartTime = "2014/05/22";
		sEndTime = "2014/05/24";*/
		
		try {
			sSql = "select CustomerName,SerialNo,CertID,JimuID,VouchCompany,ProjectName,ItemStatus,BusinessSum,TermMonth,"+
				   "ProductType,ProductName,SalePrice,FirstPayment from Business_Contract where occurdate > '"+sStartTime+"' and occurdate < '"+sEndTime+"' ";
			rs = sqlQuery.getResultSet(sSql);
			while(rs.next()){
				HashMap map = new HashMap();
				
				sCustomerName = toString(rs.getString("CustomerName"));
				sContractSerialNo = toString(rs.getString("SerialNo"));
				sCertID = toString(rs.getString("CertID"));
				sJMID = toString(rs.getString("JimuID"));
				sGuarantyName = toString(rs.getString("VouchCompany"));
				sProjectName = toString(rs.getString("ProjectName"));
				sProjectStatus = toString(rs.getString("ItemStatus"));
				dProjectBusiness = rs.getDouble("BusinessSum");
				sProjectMonth = toString(rs.getString("TermMonth"));
				sBusinessType = toString(rs.getString("ProductType"));
				sBusinessTypeName = toString(rs.getString("ProductName"));
				dSaleSum = rs.getDouble("SalePrice");
				dFirstSaleSum = rs.getDouble("FirstPayment");
				
				map.put("CustomerName", sCustomerName);
				map.put("ContractSerialNo", sContractSerialNo);
				map.put("CertID", sCertID);
				map.put("JMID", sJMID);
				map.put("GuarantyName", sGuarantyName);
				map.put("ProjectName", sProjectName);
				map.put("ProjectStatus", sProjectStatus);
				map.put("ProjectBusiness", dProjectBusiness);
				map.put("ProjectMonth", sProjectMonth);
				map.put("BusinessType", sBusinessType);
				map.put("BusinessTypeName", sBusinessTypeName);
				map.put("SaleSum", dSaleSum);
				map.put("FirstSaleSum", dFirstSaleSum);
				
				responseMap.put(sContractSerialNo, map);
				
			}
			
		} catch (Exception e) {
			throw new Exception(e+"车垫资出现异常");
		}finally{
			if(rs != null){
				rs.getStatement().close();
			}
		}
		return responseMap;
	}
	
	public String toString(String s){
		return s == null ? "" : s;
	}

}
