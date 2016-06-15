package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;

public class MortgagePledgeCarAction extends Action {

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
		String sCompanyName = "";//公司名称
		String sContractRepayDate = "";//合同还款日
		String sOnCardDate = "";//上牌日期
		double dEvaluation = 0.0;//评估价格
		String sCarType = "";//车辆品牌型号
		String sCarColor = "";//车辆颜色
		String sCarAdd = "";//车辆注册地
		String sCarTransType = "";//变速箱类型
		double dLength = 0.0;//表征里程
		String sArea = "";//所属区域（省）
		double dInterestRate = 0.0;//融资人综合利率
		String sStartTime = (String) requestMap.get("StartTime");//开始时间
		String sEndTime = (String) requestMap.get("EndTime");//结束时间
		sStartTime = "2014/05/22";
		sEndTime = "2014/05/24";
		
		try {
			sSql = "select CustomerName,SerialNo,CertID,JimuID,VouchCompany,ProjectName,ItemStatus,BusinessSum,"+
				   "TermMonth,CompanyName,ContractDate,RegistrationDate,PlanPrice,BrandModels,VehicleColor,"+
				   "VehicleRegistered,GearBoxType,Milage,Distric,FinanceRate from Business_Contract "+
				   "where occurdate > '"+sStartTime+"' and occurdate < '"+sEndTime+"' ";
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
				sCompanyName = toString(rs.getString("CompanyName"));
				sContractRepayDate = toString(rs.getString("ContractDate"));
				sOnCardDate = toString(rs.getString("RegistrationDate"));
				dEvaluation = rs.getDouble("PlanPrice");
				sCarType = toString(rs.getString("BrandModels"));
				sCarColor = toString(rs.getString("VehicleColor"));
				sCarAdd = toString(rs.getString("VehicleRegistered"));
				sCarTransType = toString(rs.getString("GearBoxType"));
				dLength = rs.getDouble("Milage");
				sArea = toString(rs.getString("Distric"));
				dInterestRate = rs.getDouble("FinanceRate");
				
				map.put("CustomerName", sCustomerName);
				map.put("ContractSerialNo", sContractSerialNo);
				map.put("CertID", sCertID);
				map.put("JMID", sJMID);
				map.put("GuarantyName", sGuarantyName);
				map.put("ProjectName", sProjectName);
				map.put("ProjectStatus", sProjectStatus);
				map.put("ProjectBusiness", dProjectBusiness);
				map.put("ProjectMonth", sProjectMonth);
				map.put("CompanyName", sCompanyName);
				map.put("ContractRepayDate", sContractRepayDate);
				map.put("OnCardDate", sOnCardDate);
				map.put("Evaluation", dEvaluation);
				map.put("CarType", sCarType);
				map.put("CarColor", sCarColor);
				map.put("CarAdd", sCarAdd);
				map.put("CarTransType", sCarTransType);
				map.put("Length", dLength);
				map.put("Area", sArea);
				map.put("InterestRate", dInterestRate);
				
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
