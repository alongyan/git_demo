package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMMEMELoanObject;

public class JMMEMELoanSet {
	/**
	 * @describe �÷�������ʵ����ôô��ʵ����
	 * @param map
	 * @return
	 */ 			//     (String)map.get("")
	public JMMEMELoanObject setJMMEMELoanObject(HashMap map){
		JMMEMELoanObject jo = new JMMEMELoanObject();
		jo.setChineseName((String)map.get("ChineseName"));//����������
		jo.setNational((String)map.get("National"));//����
		jo.setBirthday((String)map.get("Birthday"));//��������
		jo.setAge((String)map.get("Age"));//����
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//֤������
		jo.setIdentityType((String)map.get("IdentityType"));//֤������
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//����״��
		jo.setEmail((String)map.get("Email"));//��������
		jo.setPhone((String)map.get("Phone"));//�ֻ�����
		jo.setPostAddress((String)map.get("PostAddress"));//ͨѶ��ַ
		jo.setPostCode((String)map.get("PostCode"));//��������
		jo.setThirdPartyScore((String)map.get("ThirdPartyScore"));//����������
		jo.setAcademic((String)map.get("Academic"));//ѧ��
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("CheckedAmount"))));//����ʵ��������
		jo.setCompanyName((String)map.get("CompanyName"));//������λ
		jo.setCompanyNature((String)map.get("CompanyNature"));//��λ����
		jo.setPosition((String)map.get("Position"));//ְ��
		jo.setCollege((String)map.get("College"));//Ժϵ
		jo.setDegreeCategory((String)map.get("DegreeCategory"));//ѧλ���
		jo.setGraduateDate((String)map.get("GraduateDate"));//��ҵʱ��
		jo.setInSchoolID((String)map.get("InSchoolID"));//ѧ��
		jo.setSchoolName((String)map.get("SchoolName"));//ѧУ����
		jo.setSpecialty((String)map.get("Specialty"));//רҵ
		jo.setSchoolEntranceDate((String)map.get("SchoolEntranceDate"));//��ѧʱ��
		jo.setSchoolYears((String)map.get("SchoolYears"));//����
		jo.setMobile((String)map.get("Mobile"));//�ֻ�����
		jo.setQQ((String)map.get("QQ"));//QQ
		jo.setRoomAddress((String)map.get("RoomAddress"));//�����ַ
		jo.setWeChat((String)map.get("WeChat"));//WeChat
		jo.setRelationName((String)map.get("RelationName"));//����
		jo.setRelationPhone((String)map.get("RelationPhone"));//�ֻ�
		jo.setRelationship((String)map.get("Relationship"));//��ϵ
		jo.setCardType((String)map.get("CardType"));//����
		jo.setBrands((String)map.get("Brands"));//��Ʒ��
		jo.setCardNumber((String)map.get("CardNumber"));//����
		jo.setDealCount((String)map.get("DealCount"));//���ױ���
		jo.setFirstDealDate((String)map.get("FirstDealDate"));//�ν�������
		jo.setLastDealDate((String)map.get("LastDealDate"));//���½�������
		jo.setLevel((String)map.get("Level"));//���ȼ�
		jo.setLocation((String)map.get("Location"));//���������ڵ�
		jo.setProduct((String)map.get("Product"));//����Ʒ
		jo.setProperty((String)map.get("Property"));//������
		jo.setStatus((String)map.get("Status"));//��Ȩ״̬
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("TotalInAmt"))));//�����ܽ��գ�
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("TotalOutAmt"))));//�����ܽ�֧��
		jo.setUnfinishedCount((String)map.get("UnfinishedCount"));//δ�������
		jo.setFinishedCount((String)map.get("FinishedCount"));//�ѽ������
		jo.setApplyCount((String)map.get("ApplyCount"));//�����б���
		jo.setPassedCount((String)map.get("PassedCount"));//ͨ������
		jo.setDeniedCount((String)map.get("DeniedCount"));//�ܾ�����
		jo.setQueryCount((String)map.get("QueryCount"));//�������ѯ����
		jo.setOtherCount((String)map.get("OtherCount"));//"����/ծȨ/����/��¼/����"
		jo.setNFCSDebtCount((String)map.get("NFCSDebtCount"));//�������
		jo.setNFCSFirstDebtDate((String)map.get("NFCSFirstDebtDate"));//�״���
		jo.setNFCSMaxCreditAmt(Double.parseDouble(toDouble((String) map.get("NFCSMaxCreditAmt"))));//������Ŷ��
		jo.setNFCSTotalAmt(Double.parseDouble(toDouble((String) map.get("NFCSTotalAmt"))));//�����ܶ�
		jo.setNFCSValidAmt(Double.parseDouble(toDouble((String) map.get("NFCSValidAmt"))));//�������
		jo.setNFCSRepaymentByMonth(Double.parseDouble(toDouble((String) map.get("NFCSRepaymentByMonth"))));//Э���»���
		jo.setNFCSTotalOverDueAmt(Double.parseDouble(toDouble((String) map.get("NFCSTotalOverDueAmt"))));//��ǰ�����ܶ�
		jo.setNFCSMaxOverDueAmt(Double.parseDouble(toDouble((String) map.get("NFCSMaxOverDueAmt"))));//������ڽ��
		jo.setNFCSOverDueCount((String)map.get("NFCSOverDueCount"));//�����������
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//������
		jo.setBatch((String)map.get("Batch"));//��������
		jo.setLenderAmount(Double.parseDouble(toDouble((String) map.get("LenderAmount"))));//�ſ���
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//ÿ�»�����
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//����������
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//����ֹ����
		jo.setPayDate((String)map.get("PayDate"));//�ſ�ʱ��
		jo.setApplyCity((String)map.get("ApplyCity"));//�����ύ����
		jo.setType((String)map.get("Type"));//��Ʒ����
		jo.setEstimateAmt(Double.parseDouble(toDouble((String) map.get("EstimateAmt"))));//Ԥ�����
		jo.setTotalAmt(Double.parseDouble(toDouble((String) map.get("TotalAmt"))));//���
		jo.setValidAmt(Double.parseDouble(toDouble((String) map.get("ValidAmt"))));//ValidAmt
		jo.setUsedAmt(Double.parseDouble(toDouble((String) map.get("UsedAmt"))));//���ö��
		jo.setSPID((String)map.get("SPID"));//SPID
		jo.setOldlcno((String)map.get("ProjectNo"));//�����Ŀ��̖Ψһ���Rreturn jo;
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
