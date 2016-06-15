package com.amarsoft.server.object;

import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.JMMMLoanObject;

public class JMMMLoanSet {
	/**
	 * @describe �÷�������ʵ����������ʵ����
	 * @param requestMap
	 * @return
	 * @xlsun 2015-07-20
	 */
	public JMMMLoanObject setJMMMLoanObject(Map<String, Object> requestMap){
		JMMMLoanObject jo = new JMMMLoanObject();
		jo.setChineseName((String)requestMap.get("ChineseName"));//����������
		jo.setIdentityNumber((String)requestMap.get("IdentityNumber"));//���֤
		jo.setSex((String)requestMap.get("Sex"));//�Ա�
		jo.setPhone((String)requestMap.get("Phone"));//�绰
		jo.setMaritalStatus((String)requestMap.get("MaritalStatus"));//�������
		jo.setCompanyName((String)requestMap.get("CompanyName"));//������λ
		jo.setCompanyNature((String)requestMap.get("CompanyNature"));//��������
		jo.setCompanyTel((String)requestMap.get("CompanyTel"));//������ַ�绰
		jo.setRelaName((String)requestMap.get("RelaName"));//����
		jo.setRelaPhone((String)requestMap.get("RelaPhone"));//��ϵ�绰
		jo.setRelationship((String)requestMap.get("Relationship"));//��ϵ
		jo.setHasBadRecord((String)requestMap.get("HasBadRecord"));//���޲�����¼
		jo.setProductType((String)requestMap.get("ProductType"));//��Ʒ����
		jo.setBatch((String)requestMap.get("Batch"));//������
		jo.setUserName((String)requestMap.get("UserName"));//�����û���
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) requestMap.get("FinancingAmount"))));//���ʽ��
		jo.setLenderAmount(Double.parseDouble(toDouble((String) requestMap.get("LenderAmount"))));//������ʵ�ս��
		jo.setHJXD_RepaymentByMonth(Double.parseDouble(toDouble((String) requestMap.get("HJXD_RepaymentByMonth"))));//�³�����Ϣ����
		jo.setRepaymentDay((String)requestMap.get("RepaymentDay"));//������
		jo.setRepaymentStartDate((String)requestMap.get("RepaymentStartDate"));//����������
		jo.setRepaymentEndDate((String)requestMap.get("RepaymentEndDate"));//����ֹ����
		jo.setHJXD_LastMonthAmount(Double.parseDouble(toDouble((String) requestMap.get("HJXD_LastMonthAmount"))));//���һ�ڻ���Ϣ����
		jo.setApplyCity((String)requestMap.get("ApplyCity"));//�����ύ����
		jo.setProductName((String)requestMap.get("ProductName"));//��Ʒ����
		jo.setSalePrice(Double.parseDouble(toDouble((String) requestMap.get("SalePrice"))));//���ۼ۸�
		jo.setDownPaymentAmount(Double.parseDouble(toDouble((String) requestMap.get("DownPaymentAmount"))));//�׸����
		jo.setAltName((String)requestMap.get("AltName"));//����
		jo.setAltID((String)requestMap.get("AltID"));//���֤����
		jo.setAltRelation((String)requestMap.get("AltRelation"));//��ϵ
		jo.setBankName((String)requestMap.get("BankName"));//������������
		jo.setAccountNo((String)requestMap.get("AccountNo"));//���������˺�
		jo.setMerchantName((String)requestMap.get("MerchantName"));//�����̻�����
		jo.setMerchantProvince((String)requestMap.get("MerchantProvince"));//�̻�ʡ��
		jo.setMerchantCity((String)requestMap.get("MerchantCity"));//�̻�����
		jo.setSPID((String)requestMap.get("SPID"));//������̖
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
