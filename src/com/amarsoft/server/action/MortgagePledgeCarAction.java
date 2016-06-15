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
		//�������
		ResultSet rs = null;
		String sSql = "";
		
		String sCustomerName = "";//����������
		String sContractSerialNo = "";//��Ŀ���
		String sCertID = "";//���������֤��
		String sJMID = "";//��ľ���
		String sGuarantyName = "";//������˾
		String sProjectName = "";//��Ŀ����
		String sProjectStatus = "";//��Ŀ״̬
		double dProjectBusiness = 0.0;//���ʽ��
		String sProjectMonth = "";//��������
		String sCompanyName = "";//��˾����
		String sContractRepayDate = "";//��ͬ������
		String sOnCardDate = "";//��������
		double dEvaluation = 0.0;//�����۸�
		String sCarType = "";//����Ʒ���ͺ�
		String sCarColor = "";//������ɫ
		String sCarAdd = "";//����ע���
		String sCarTransType = "";//����������
		double dLength = 0.0;//�������
		String sArea = "";//��������ʡ��
		double dInterestRate = 0.0;//�������ۺ�����
		String sStartTime = (String) requestMap.get("StartTime");//��ʼʱ��
		String sEndTime = (String) requestMap.get("EndTime");//����ʱ��
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
			throw new Exception(e+"�����ʳ����쳣");
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
