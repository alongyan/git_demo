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
		String sBusinessType = "";//��Ʒ����
		String sBusinessTypeName = "";//��Ʒ����
		double dSaleSum = 0.0;//���ۼ۸�
		double dFirstSaleSum = 0.0;//�׸����
		String sStartTime = (String) requestMap.get("StartTime");//��ʼʱ��
		String sEndTime = (String) requestMap.get("EndTime");//����ʱ��
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
