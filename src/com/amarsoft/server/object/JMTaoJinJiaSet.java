package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMTaoJinJiaObject;

/**
 * @describe ʵ�����Խ��ʵ����
 * @param map
 * @author yhwang 20150721
 * @return
 */
public class JMTaoJinJiaSet {
	
	/*
	 * ���Խ��ʵ���ำֵ
	 */
	public JMTaoJinJiaObject setJMTaoJinJiaObject(HashMap map){
		JMTaoJinJiaObject jo = new JMTaoJinJiaObject();
		jo.setProjectNo((String)map.get("ProjectNo"));//��Ŀ���
		jo.setType((String)map.get("Type"));//��Ʒ����
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//�������ʽ��
		jo.setBatch((String)map.get("Batch"));//����
		jo.setApplyCity((String)map.get("ApplyCity"));//�����ύ����
		jo.setChineseName((String)map.get("ChineseName"));//����
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//���֤����
		jo.setSex((String)map.get("Sex"));//�Ա�
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//����״��
		jo.setAge((String)map.get("Age"));//����
		jo.setCompanyName((String)map.get("CompanyName"));//������λ
		jo.setAcademic((String)map.get("Academic"));//ѧ��
		jo.setCompanyNature((String)map.get("CompanyNature"));//��λ����
		jo.setPosition((String)map.get("Position"));//ְ��
		jo.setWorkYears((String)map.get("WorkYears"));//�ܹ���
		jo.setDebteeName((String)map.get("DebteeName"));//ծȨ������
		jo.setDebteeID((String)map.get("DebteeID"));//ծȯ�����֤��
		jo.setDebteeRelation((String)map.get("DebteeRelation"));//�����˹�ϵ
		jo.setUserName((String)map.get("UserName"));//ծȨ�˻�ľ�����û���
		jo.setAccountNo((String)map.get("AccountNo"));//ծȨ�����п���
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//������ʼ��
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//�������
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//ÿ�»�Ϣ��
		jo.setSPID((String)map.get("SPID"));//������̖
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
