package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMXinYongBaoObject;


public class JMXinYongBaoSet {
	/**
	 * @describe �÷�������ʵ�������ñ�ʵ����
	 * @param map
	 * @return
	 */ 			
	public JMXinYongBaoObject setJMXinYongBaoObject(HashMap map){
		JMXinYongBaoObject jo = new JMXinYongBaoObject();
		jo.setChineseName((String)map.get("ChineseName"));//�������� 
		jo.setAge((String)map.get("Age"));//����
		jo.setSex((String)map.get("Sex"));//�Ա�
		jo.setAge((String)map.get("Age"));//����
		jo.setPhone((String)map.get("Phone"));//�ֻ�����
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//֤������
		jo.setPostAddress((String)map.get("PostAddress"));//ͨѶ��ַ
		jo.setAccountLocation((String)map.get("AccountLocation"));//����
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//����״��
		jo.setThirdPartyScore((String)map.get("ThirdPartyScore"));//����������
		jo.setBirthday((String)map.get("Birthday"));//��������
		jo.setSpouseName((String)map.get("SpouseName"));//��ż����
		jo.setSpouseID((String)map.get("SpouseID"));//��żʡ��֤��
		jo.setSpousePhone((String) map.get("SpousePhone"));//��ż�ֻ���
		jo.setSpouseWorkUnit((String)map.get("SpouseWorkUnit"));//��ż������λ����
		jo.setSpouseJob((String)map.get("SpouseJob"));//��żְ��
		jo.setHasCar((String)map.get("HasCar"));//��ͥ�������
		jo.setHasHouse((String)map.get("HasHouse"));//�з���
		jo.setCompanyName((String)map.get("CompanyName"));//������λ
		jo.setCompanyNature((String)map.get("CompanyNature"));//��λ����
		jo.setCompanyTel((String)map.get("CompanyTel"));//��λ����
		jo.setCompanyAddress((String)map.get("CompanyAddress"));//��λ��ַ
		jo.setWorkStartDate((String)map.get("WorkStartDate"));//��������ʼ����
		jo.setPosition((String)map.get("Position"));//ְ��
		jo.setWorkYears((String)map.get("WorkYears"));//�ܹ���
		jo.setAcademic((String)map.get("Academic"));//ѧ��
		jo.setCheckedAmount(Double.parseDouble(toDouble((String)map.get("CheckedAmount"))));//����ʵ��������
		jo.setCheckedLoanInfo3Month((String)map.get("CheckedLoanInfo3Month"));//��3���´����������Ų�ѯ����
		jo.setWorstRepaymentLastTwoYears((String)map.get("WorstRepaymentLastTwoYears"));//��ȥ2��������״̬ 
		jo.setTotalOverDueLastTwoYears((String)map.get("TotalOverDueLastTwoYears"));//��ȥ2�����ۼ����ڴ���
		jo.setOperatEntity((String)map.get("OperatEntity"));//��Ӫ��������
		jo.setOperatEntityType((String)map.get("OperatEntityType"));//��Ӫ�������� 
		jo.setFoundYears((String)map.get("FoundYears"));//��������
		jo.setOperationStartDate((String)map.get("OperationStartDate"));//ʵ�ʾ�Ӫ��ʼʱ�� 
		jo.setCurrentOperatAddr((String)map.get("CurrentOperatAddr"));//��ǰ��Ӫ��ַ
		jo.setShareHolderRate(Double.parseDouble(toDouble((String)map.get("ShareHolderRate"))));//�����˳ֹɱ���
		jo.setEmployeeCount((String)map.get("EmployeeCount"));//Ա������
		jo.setAccountInFlowPerMonth(Double.parseDouble(toDouble((String)map.get("AccountInFlowPerMonth"))));//��Ӫʵ���˻��¾�������
		jo.setType((String)map.get("Type"));//��Ʒ����
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//���ʽ�Ԫ��
		jo.setBatch((String)map.get("Batch"));//�������ޣ��£�
		jo.setDebtUsage((String)map.get("DebtUsage"));//������;
		jo.setLenderAmount(Double.parseDouble(toDouble(toDouble((String)map.get("LenderAmount")))));//������ʵ�ս��
		jo.setHouseType((String) map.get("HouseType"));//��������
		jo.setRepaymentAmountByMonth(Double.parseDouble(toDouble((String) map.get("RepaymentAmountByMonth"))));//�³�����Ϣ����
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//������
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//����������
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//����ֹ����
		jo.setHJXD_LastMonthAmount(Double.parseDouble(toDouble((String)map.get("HJXD_LastMonthAmount"))));//���һ�ڻ���Ϣ����
		jo.setApplyCity((String)map.get("ApplyCity"));//�����ύ����
		jo.setEstateType((String)map.get("EstateType"));//��������
		jo.setRealEstateLocations((String)map.get("RealEstateLocations"));//�������ڵ�
		jo.setConstructionArea(Double.parseDouble(toDouble((String)map.get("ConstructionArea"))));//�������
		jo.setPurchaseDate((String)map.get("PurchaseDate"));//����ʱ��
		jo.setHouseTotalValue(Double.parseDouble(toDouble((String) map.get("HouseTotalValue"))));//�����ܼ� 
		jo.setDebteeRelation((String) map.get("DebteeRelation"));//��ϵ
		jo.setDebteeName((String) map.get("DebteeName"));//����
		jo.setDebteeID((String) map.get("DebteeID"));//���֤��
		jo.setDebteeMobile((String) map.get("DebteeMobile"));//�ֻ�����
		jo.setDebteeMailingAddress((String) map.get("DebteeMailingAddress"));//ͨѶ��ַ
		jo.setAccountNo((String)map.get("AccountNo"));//���������˺�
		jo.setBankName((String) map.get("BankName"));//������������
		jo.setProvence((String)map.get("Provence"));//��������ʡ��
		jo.setArea((String) map.get("Area"));//�������е���
		jo.setSubbranchBank((String)map.get("SubbranchBank"));//��������֧��
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
