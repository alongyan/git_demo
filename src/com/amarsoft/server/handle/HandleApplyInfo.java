package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �ķ������ڴ���ҵ�����������Ϣ����
 * @author Sun
 *
 */
public class HandleApplyInfo {
	private Logger logger = Logger.getLogger(HandleApplyInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String sOrgID = "";//�������
	private String sUserID = "";//�û����
	private String sBusinessType ="";//ҵ��Ʒ��
	
	private String sStatus = "";//���ݻ�ȡ״̬
	private String sOrgName = "";//��������
	private String sUserName = "";//�û�����
	private String sFlowNo = "";//���̱��
	private String sFlowName = "";//��������
	private String sPhaseNo = "";//�׶α��
	private String sPhaseName = "";//�׶�����
	private String sCustomerID = "";//�ͻ����
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleApplyInfo(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sJMID,String sOrgID,String sUserID,String sCustomerID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sJMID = sJMID;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sCustomerID = sCustomerID;
	}
	/**
	 * @throws Exception 
	 * @describe �÷������ڴ���ҵ��������Ϣģ������
	 */
	public void handleApplyInfoMessage() throws Exception{
		sBusinessType =Tools.getObjectToString(hashMap.get("Type").toString());
		getFlowInfo();//��ȡ����������Ϣ
		insertApplyInfo();//����ҵ��������Ϣ
	}
	
//	/**
//	 * @throws Exception 
//	 * @describe �÷������ڻ�ȡҵ��Ʒ��
//	 */
//	private void getBusinessType() throws Exception {
//		String sSql =" select bt.typeno as TypeNo from business_type bt where bt.attribute24 ='"+this.sOrgID+"' and bt.TypeName='"+Tools.getObjectToString(hashMap.get("Type").toString())+"' ";
//		logger.info("��ѯҵ��Ʒ��ִ�нű� = "+sSql);
//		try {
//			ResultSet rs = sqlQuery.getResultSet(sSql);
//			if(rs.next()){
//				sBusinessType = rs.getString("TypeNo");
//			}
//			rs.getStatement().close();
//		} catch (Exception e) {
//			logger.info("��ѯҵ��Ʒ��ִ�нű�����");
//			e.printStackTrace();
//			throw e;
//		}
//	}
	/**
	 * @describe �÷���������ҵ��������в���ҵ������
	 * @return SerialNo
	 * @throws Exception 
	 */
	private String insertApplyInfo() throws Exception{
		String sSerialNo = "";//ҵ��������
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply", "SerialNo");//��ȡ����׶�ҵ��������
			
			String sSql = "insert into Business_apply (SerialNo,JimuID,InputUserId,InputOrgID,InputDate,"+
			"ThirdPartySerialNo,BusinessType,BusinessSum,Month,TermMonth,Purpose,OtherPurpose,CreditCity,"+
			"CustomerSort,SalesCanal,SalesNo,SalesName,OccurDate,CustomerID,CustomerName,OccurType,BusinessCurrency,"+
			"ApplyType,TempSaveFlag,BD,BelongOrg,FLAG4,Operateorgid,Operateuserid) values(:SerialNo,:JimuID,:InputUserId,:InputOrgID,:InputDate,"+
			":ThirdPartySerialNo,:BusinessType,:BusinessSum,:Month,:TermMonth,:Purpose,:OtherPurpose,:CreditCity,"+
			":CustomerSort,:SalesCanal,:SalesNo,:SalesName,:OccurDate,:CustomerID,:CustomerName,:OccurType,:BusinessCurrency,"+
			":ApplyType,:TempSaveFlag,:BD,:BelongOrg,:FLAG4,:Operateorgid,:Operateuserid)";
			
			SQLHandleInfo sSqlHandle = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sSerialNo)//����ҵ����
			.setParameter("JimuID", sJMID)//JMID
			.setParameter("InputUserId",this.sUserID)//¼����Ա���
			.setParameter("InputOrgID", this.sOrgID)//¼��������
			.setParameter("InputDate", Tools.getToday(2))//¼������
			.setParameter("ThirdPartySerialNo", Tools.getObjectToString(hashMap.get("ProjectNo")))//�������������
			.setParameter("BusinessType", this.sBusinessType)//ҵ��Ʒ��
			.setParameter("BusinessSum", Double.parseDouble(hashMap.get("FinancingAmount").toString()))//ҵ��������
			.setParameter("Month", Tools.getObjectToString(hashMap.get("TermMonth").toString()))//��������
			.setParameter("TermMonth", Integer.parseInt(hashMap.get("TermMonth").toString()))//��������
			.setParameter("Purpose", Tools.getCodeItemNo("Purpose1", Tools.getObjectToString(hashMap.get("DebtUsage")), sqlQuery))
			.setParameter("OtherPurpose", Tools.getObjectToString(hashMap.get("Purpose")))//�����;����
			.setParameter("CreditCity", Tools.getObjectToString(hashMap.get("ApplyCity")))//ҵ���������
			.setParameter("CustomerSort", Tools.getCodeItemNo("CustomerSort",("".equals(Tools.getObjectToString(hashMap.get("CustomerSort")))||Tools.getObjectToString(hashMap.get("CustomerSort"))==null)?"һ����Ⱥ":Tools.getObjectToString(hashMap.get("CustomerSort")),sqlQuery))//��Ⱥ����
			.setParameter("SalesCanal", Tools.getCodeItemNo("SalesCanal", Tools.getObjectToString(hashMap.get("CaleChanel")), sqlQuery))//Ӫ������
			.setParameter("SalesNo", Tools.getObjectToString(hashMap.get("SaleCarID")))//Ӫ����Ա���֤��
			.setParameter("SalesName", Tools.getObjectToString(hashMap.get("SaleName")))
			.setParameter("OccurDate", Tools.getToday(2))//��ȡ��������
			.setParameter("CustomerID", this.sCustomerID)//�ͻ����
			.setParameter("CustomerName", Tools.getObjectToString(hashMap.get("ChineseName")))//�ͻ�����
			.setParameter("OccurType", "010")//��������
			.setParameter("BusinessCurrency", "01")//����
			.setParameter("ApplyType", "RetailDependent")//״̬
			.setParameter("TempSaveFlag", "2")//����״̬
			.setParameter("BD", this.sUserID)//BD���
			.setParameter("BelongOrg", "100007")//��������
			.setParameter("FLAG4", "2")//�Ƿ��ؼ�
			.setParameter("Operateorgid", this.sOrgID)//��������
			.setParameter("Operateuserid", this.sUserID);//������
			
			
			
			logger.info("��ȡҵ����Ϣִ�нű�"+sSqlHandle.getSql());
			sqlQuery.execute(sSqlHandle.getSql());//ִ�нű�
			
			sSql = "insert into flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"OrgID,OrgName,UserID,UserName,InputDate) values (:ObjectType,:ObjectNo,:PhaseType,:ApplyType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,"+
			":OrgID,:OrgName,:UserID,:UserName,:InputDate)";
			
			SQLHandleInfo sSqlHandleFlow = new SQLHandleInfo(sSql)
			.setParameter("ObjectType", "CreditApply")//����
			.setParameter("ObjectNo", sSerialNo)//ҵ��������
			.setParameter("PhaseType", "1010")//���̽׶�Ĭ��Ϊ��һ��λ
			.setParameter("ApplyType", "RetailDependent")//����ҵ����������Ĭ��ֵ
			.setParameter("FlowNo", this.sFlowNo)//�������̱��
			.setParameter("FlowName", this.sFlowName)//��������
			.setParameter("PhaseNo", this.sPhaseNo)//�׶α��
			.setParameter("PhaseName", this.sPhaseName)//�׶�����
			.setParameter("OrgID", this.sOrgID)//������
			.setParameter("OrgName", this.sOrgName)//��������
			.setParameter("UserID", this.sUserID)//�û����
			.setParameter("UserName", this.sUserName)//�û�����
			.setParameter("InputDate", Tools.getToday(2));//��ȡ��ǰ����
			
			logger.info("ҵ�����̽ű���� "+sSqlHandleFlow.getSql());
			this.sqlQuery.execute(sSqlHandleFlow.getSql());
			
			String sTaskSerialNo = DBFunction.getSerialNo("Flow_task", "SerialNo");//��ȡ���̱��
			
			sSql = "insert into flow_task (SerialNo,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime) values (:SerialNo,:ObjectNo,"+
			":ObjectType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,:PhaseType,:ApplyType,:UserID,:UserName,"+
			":OrgID,:OrgName,:BeginTime)";
			
			SQLHandleInfo sSqlHandleFlowTask = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sTaskSerialNo)//��������TaskNo
			.setParameter("ObjectNo", sSerialNo)//ҵ��������
			.setParameter("ObjectType", "CreditApply")//ҵ������
			.setParameter("FlowNo", this.sFlowNo)//�������̱��
			.setParameter("FlowName", this.sFlowName)//������������
			.setParameter("PhaseNo", this.sPhaseNo)//���̽ڵ���
			.setParameter("PhaseName", this.sPhaseName)//���̽ڵ�����
			.setParameter("PhaseType", "1010")//����Ĭ��Ϊ��һ��λ
			.setParameter("ApplyType", "RetailDependent")//ҵ������
			.setParameter("UserID", this.sUserID)//�����˱��
			.setParameter("UserName", this.sUserName)//����������
			.setParameter("OrgID", this.sOrgID)//�������
			.setParameter("OrgName", this.sOrgName)//��������
			.setParameter("BeginTime", Tools.getDate());
			
			logger.info("ҵ�����̽ű���� "+sSqlHandleFlowTask.getSql());
			this.sqlQuery.execute(sSqlHandleFlowTask.getSql());
			//������������Ϣ
			insertlCInfo(sSerialNo,Double.parseDouble(hashMap.get("FinancingAmount").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("��ȡҵ�������ų��ִ���");
			e.printStackTrace();
			sSerialNo = "Fail";
			throw e;
		}
		return sSerialNo;
	}
	/**
	 * @descirbe �ķ������ڻ�ȡ����������Ϣ
	 */
	private void getFlowInfo(){
		HashMap hashMap = Tools.getFlowInfo(this.sOrgID, this.sUserID,this.sBusinessType , sqlQuery);
		if("0000".equals(hashMap.get("Status"))){//����������Ϣ��ȡ����
			this.sOrgName = hashMap.get("OrgName").toString();
			this.sUserName = hashMap.get("UserName").toString();
			this.sFlowNo = hashMap.get("FlowNo").toString();
			this.sFlowName = hashMap.get("FlowName").toString();
			this.sPhaseNo = hashMap.get("PhaseNo").toString();
			this.sPhaseName = hashMap.get("PhaseName").toString();
		}else{
			this.sOrgName = "";
			this.sUserName = "";
			this.sFlowNo = "";
			this.sFlowName  = "";
			this.sPhaseNo = "";
			this.sPhaseName = "";
		}
	}
	/**
	 * @describe �ķ������ڴ�����������Ϣ
	 * @param sApplySerialNo
	 * @param dBusinessSum
	 * @return
	 * @throws Exception 
	 */
	private void insertlCInfo(String sApplySerialNo,double dApplySum) throws Exception{//ҵ�������š�������
		double dYSSum=0;//Ԥ����Ŀ�ܽ��
		double dSuming=0;//��;��Ŀ�ܽ��
		double dVouchSum=0;//�ڱ���Ŀ�ܽ�� 
		double dUseSum=0;//���ý��
		double dBusinessSum = 0;
		String sBSerialNo = "";//������Ⱥ�ͬ���
		String sSerialNo = "";//������
		String sObjectNo ="";//������ȱ��
		String sSql = "";
		
		try {
			sSerialNo = DBFunction.getSerialNo("CL_OCCUPY", "SerialNo");
			//��ȡ������ȱ��
			String sLineCustomerID = this.sqlQuery.getString("select RelativeCustomerID from Org_Info where orgID='100007'");
			sObjectNo = this.sqlQuery.getString("select SerialNo from BUSINESS_CONTRACT where CustomerID = '"+sLineCustomerID+"' and BusinessType = '3020' and PutOutDate <= '"+Tools.getToday(2)+"' and Maturity >= '"+Tools.getToday(2)+"' and (FinishDate is null or FinishDate = ' ') and PigeonholeDate is not null and (PigeonholeDate<>'' or PigeonholeDate<>' ') and FreezeFlag in ('1','3') ");
			//��Ƚ��
			dBusinessSum = this.sqlQuery.getDouble("select nvl(BusinessSum,0) as BusinessSum from Business_Contract where SerialNo='"+sObjectNo+"'");
			//��ȡԤ����
			sSql = "select sum(nvl(BusinessSum,0)) as BusinessSum from Business_Apply where BelongOrg='"+sOrgID+"' and (YSSerialNo is not null or YSSerialNo<>' ')";
			dYSSum =  this.sqlQuery.getDouble(sSql);
			//��;��Ŀ�ܽ��
			sSql = "select sum(nvl(ba.BusinessSum,0)) as BusinessSum from Business_Apply ba ,Flow_Object fO,CL_OCCUPY cl where  ba.SerialNo=fo.ObjectNo and fo.phaseNo<>'0010' and fo.phaseNo<>'1000' and fo.phaseNo<>'8000' and fo.objecttype='CreditApply' and ba.BusinessType not like '3%'  and cl.ObjectNo=ba.SerialNo and cl.ObjectType='CreditApply' and cl.RelativeSerialNo='"+sObjectNo+"'";
			dSuming = this.sqlQuery.getDouble(sSql);
			//�ڱ���Ŀ�ܽ�� δ�ſ���+�ѷſ����
			sSql = "select sum(nvl(bc.BusinessSum,0)+nvl(bc.Balance,0)-nvl((select nvl(bd.businesssum,0) from Business_Duebill bd where bd.relativeserialno2 = bc.SerialNo),0)) as Balance from Business_Contract bc,CL_OCCUPY cl where  bc.SerialNo=cl.ObjectNo and cl.ObjectType='BusinessContract' and cl.RelativeSerialNo='"+sObjectNo+"'";
			dVouchSum = this.sqlQuery.getDouble(sSql);
			//���ý�� = ��Ƚ�� - ��;��Ŀ���-�ѷ����-δ�Ž�� 
			dUseSum = dBusinessSum - dSuming - dVouchSum;
			
			sSql = "Insert into CL_OCCUPY(SerialNo,ObjectType,ObjectNo,RelativeSerialNo,BusinessSum,Account2,Account3,Account4,Account5,Account6) "+
					"values(:SerialNo,:ObjectType,:ObjectNo,:RelativeSerialNo,:BusinessSum,:Account2,:Account3,:Account4,:Account5,:Account6)";
			SQLHandleInfo handleInfoCl = new SQLHandleInfo(sSql)
			.setParameter("SerialNo",sSerialNo )//�����Ϣ��ˮ��
			.setParameter("ObjectType", "CreditApply")//�������
			.setParameter("ObjectNo", sApplySerialNo)//ҵ��������ˮ��
			.setParameter("RelativeSerialNo", sObjectNo)//������ȱ��
			.setParameter("BusinessSum", dBusinessSum)//
			.setParameter("Account2", dYSSum)//
			.setParameter("Account3", dSuming)//
			.setParameter("Account4", dVouchSum)//
			.setParameter("Account5", dApplySum)//������
			.setParameter("Account6", dUseSum);//
			sSql = handleInfoCl.getSql();
			this.sqlQuery.execute(sSql);
			logger.info("���������Ϣ==="+sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------ִ�в�������Ϣʱ����----");
			throw e;
		}
	}
	
}
