package com.amarsoft.server.object;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.JMMMLoanObject;
import com.amarsoft.server.dao.JMTimesObject001;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.GetJMID;
/**
 * @describe �÷������ڴ���������Ʒ����
 * @author xlsun Date 2015-07-20
 *
 */
public class JMMMLoanHandle {
	private String sUserID = "";//�û����
	private String sCustomerID = "";//�ͻ����
	private String sSerialNo = "";//ҵ����
	private String sFlowNo = "";//���̱��
	private String sFlowName = "";//��������
	private String sPhaseName = "";//�׶�����
	private String sJMID = "";//��ȡJMID
	
	public String jMMMloanSet(Map<String, Object> requestMap,SQLQuery sqlQuery) throws Exception{
		JMMMLoanSet js = new JMMMLoanSet();//������ʵ����ʵ�廯
		JMMMLoanObject jo = js.setJMMMLoanObject(requestMap);
		try {
			sJMID = GetJMID.getJMID(sqlQuery);//��ȡ��ľ���
			System.out.println("sJMID   ====== =" + sJMID + "----------");
			sCustomerID = handleCustomer(sqlQuery, jo);//��ȡ�ͻ����
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return sJMID;
	}
	
	
	/**
	 * @describe �÷���������ҵ�������в�������
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMMMLoanObject jm001,HashMap useOrg){
		String sFlowTaskNo = "";//���̱��
		//��ȡ������Ϣ
		getFlowMessage(jm001.getProductName(),sqlQuery);
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
			String sOrderNo = DBFunction.getSerialNo("ORDER_INFO","OrderID");//��ȡ������Ϣ���
			
			//ִ�в���Business_Apply
			String sSql = "insert into Business_Apply (SerialNo,CustomerID,CustomerName,BusinessType,termmonth,BusinessSum,TermSum,"+
						  "MONTHPAYMENT,MONTHREPAYMENT,FIRSTPAYMENTDATE,DUEDATE,LastRepayment,CreditCity,DaiPayeeName,DaiCardNo,DaiRelationShip,"+
				          "BankName,CardNo,MerchantName,MerchantProvince,MerchantCity,JIMUID,InputOrgID,InputDate,InputUserID,occurdate,occurtype,SPID) values "+
						"('"+sSerialNo+"','"+sCustomerID+"','"+jm001.getChineseName()+"','"+getBusinessType(jm001.getProductName(),sqlQuery)+"','"+jm001.getBatch()+"',"+jm001.getFinancingAmount()+","+jm001.getLenderAmount()
						+","+jm001.getHJXD_RepaymentByMonth()+",'"+jm001.getRepaymentDay()+"','"+jm001.getRepaymentStartDate()+"','"+jm001.getRepaymentEndDate()+"',"+jm001.getHJXD_LastMonthAmount()+",'"+jm001.getApplyCity()+"','"+jm001.getAltName()+"','"+jm001.getAltID()+"','"+jm001.getAltRelation()+"','"+
						jm001.getBankName()+"','"+jm001.getAccountNo()+"','"+jm001.getMerchantName()+"','"+jm001.getMerchantProvince()+"','"+jm001.getMerchantCity()+"','"+sJMID+"','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','"+getDate()+"','010','"+jm001.getSPID()+"')";
			sqlQuery.execute(sSql);
			//������Ϣ����
			sSql = "insert into ORDER_INFO (OrderID,PRODUCTTYPE,SALESPRICE,PAYMENT) values "+
					"('"+sOrderNo+"','"+jm001.getProductType()+"',"+jm001.getSalePrice()+","+jm001.getDownPaymentAmount()+")";
			sqlQuery.execute(sSql);
			//ִ�в���Flow_object��
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+getDate()+"')";
			sqlQuery.execute(sSql);
			//ִ�в���Flow_Task��
			sSql = "insert into Flow_Task (SerialNO,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime,FlowState,Taskstate) values ('"+
					sFlowTaskNo+"','"+sSerialNo+"','CreditApply','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','1010','CompanyFlow2','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+
					getDateTime()+"','START','0')";
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getBusinessType(String sBusinessName ,SQLQuery sqlQuery){
		String sSql = "select typeno from business_type where typename='"+sBusinessName+"'";
		String sTypeNo="";
		try {
			sTypeNo = sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sTypeNo;
	}
	/**
	 * @describe �÷������ڻ�ȡ����������
	 * @param sBusinessType
	 * @param sqlQuery
	 */
	public void getFlowMessage(String sBusinessType,SQLQuery sqlQuery){
		String sSql = "select fc.flowno as FlowNo,fc.flowname as FlowName from flow_catalog fc where fc.flowno in (select bt.attribute9  from business_type bt where bt.typename = '"+sBusinessType+"' and bt.attribute24 = '100011' )";
		ResultSet rs = null;
		try {
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sFlowNo = rs.getString("FlowNo"); if(sFlowNo == null) sFlowNo = "";//���̱��
				sFlowName = rs.getString("FlowName"); if(sFlowName == null) sFlowName = "";//��������
			}
			sSql = "select fm.phaseno,fm.phasename as PhaseName from flow_model fm where fm.flowno = '"+sFlowNo+"' and fm.phasetype = '1010'";
			rs.close();
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sPhaseName = rs.getString("PhaseName"); if(sPhaseName == null) sPhaseName = "";//��ȡ�׶�����
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @describe �÷������ڸ��¿ͻ���Ϣ
	 * @param sqlQuery
	 * @param jo
	 * @return
	 */
	public String handleCustomer(SQLQuery sqlQuery,JMMMLoanObject jo){
		String sCustomerID = "";//�ͻ����
		HashMap useOrg = getUserOrgInfo(sqlQuery,"100011");//��ȡ��Ӧ��¼�����Լ�������
		try {
			sCustomerID = sqlQuery.getString("select customerid from customer_info where certid = '"+jo.getIdentityNumber()+"'");
			if(sCustomerID == null || sCustomerID.length() <=0 ){
				sCustomerID = insertCustomerInfo(sqlQuery,jo,useOrg);
			}else{
				sCustomerID = updateCustomerInfo(sqlQuery,jo,sCustomerID,useOrg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ø÷�����ҵ���в�������
		insertBusinessInfo(sqlQuery,jo,useOrg);
		return sCustomerID;
	}
	 
	/**
	 * @describe �÷������ڲ���ͻ���Ϣ
	 * @param sqlQuery
	 * @param jm001
	 */
	public String insertCustomerInfo(SQLQuery sqlQuery,JMMMLoanObject jm001,HashMap useOrg){
		String sCustomerID = "";
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			//ִ�в���Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertType,CertID,InputOrgID,InputDate,InputUserID) values "+
						"('"+sCustomerID+"','"+jm001.getChineseName()+"','03130','Ind01','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
						getDate()+"','"+useOrg.get("UserID")+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//ִ�в���ind_info
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,Sex,MobileTelephone,Marriage,WorkCorp,unitnature,WorkTel,HasBadRecord,InputOrgID,InputDate,InputUserID) values ('"+
					sCustomerID+"','"+jm001.getChineseName()+"','Ind01','"+jm001.getIdentityNumber()+"','"+getCodeItemNo(sqlQuery,jm001.getSex(),"Sex")+"','"+jm001.getPhone()+"','"+getCodeItemNo(sqlQuery,jm001.getMaritalStatus(),"Marriage")+"','"+
					jm001.getCompanyName()+"','"+getCodeItemNo(sqlQuery,((String)jm001.getCompanyNature()),"UnitNature")+"','"+jm001.getCompanyTel()+"','"+jm001.getHasBadRecord()+"','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//ִ�в���Customer_Belong��
			sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,InputOrgID,InputDate,InputUserID) values ('"+
					sCustomerID+"','"+useOrg.get("OrgID")+"','"+useOrg.get("UserID")+"','1','1','1','1','1','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sCustomerID;
	}
	/**
	 * @describe �÷������ڸ��¿ͻ���Ϣ������
	 * @param sqlQuery
	 * @param jm001
	 */
	public String updateCustomerInfo(SQLQuery sqlQuery,JMMMLoanObject jm001,String sCustomerID,HashMap useOrg){
		String sSql = "";
		try {
			//���¿ͻ���Ϣ
			sSql = "update ind_info set Fullname = '"+jm001.getChineseName()+"',MobileTelephone = '"+jm001.getPhone()+"',Marriage = '"+getCodeItemNo(sqlQuery,jm001.getMaritalStatus(),"Marriage")+"',"+
			" WorkCorp = '"+jm001.getCompanyName()+"',UnitKind = '"+jm001.getCompanyNature()+"',WorkTel = '"+jm001.getCompanyTel()+"',"+
			"HasBadRecord = '"+jm001.getHasBadRecord()+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			//���´���ͻ�Ȩ�ޱ�
			sSql = "select count(*) as iCount from Customer_belong where customerid = '"+sCustomerID+"' and userid = '"+useOrg.get("UserID")+"'";
			System.out.println(sSql);
			String iCount = sqlQuery.getString(sSql);
			if("0".equals(iCount)){
				//ִ�в���Customer_Belong��
				sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,InputOrgID,InputDate,InputUserID) values ('"+
						sCustomerID+"','"+useOrg.get("OrgID")+"','"+useOrg.get("UserID")+"','2','1','1','1','1','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"')";
					System.out.println(sSql);
				sqlQuery.execute(sSql);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sCustomerID;
	}
	/**
	 * @describe �÷������ڻ�ȡָ����ʽ������
	 * @return
	 */
	public String getDate(){
		Date date = new Date();
		String pattern="YYYY/MM/dd";
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		String currentDateString = format.format(date);
		return currentDateString;
	}
	/**
	 * @describe �÷������ڻ�ȡ��Ӧ�����������������Լ�BD��Ա
	 * @param sqlQuery
	 * @param sChannel
	 * @return
	 */
	public HashMap getUserOrgInfo(SQLQuery sqlQuery,String sChannel){
		HashMap hmp = new HashMap();
		String sStates = "01";
		ResultSet rs;
		String sSql = "select ui.userid as UserID,ui.username as UserName,oi.orgid as OrgID,oi.orgname as OrgName from user_info ui ,org_info oi where oi.orgid = ui.belongorg and oi.orgid = '"+sChannel+"' and ui.userid in (select userid from User_Role where roleid = '280')";
		try {
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				String sUserID = rs.getString("UserID");        if(sUserID == null)  sUserID = "";//�û����
				String sUserName = rs.getString("UserName");    if(sUserName == null) sUserName = "";//�û�����
				String sOrgID = rs.getString("OrgID");          if(sOrgID == null) sOrgID = "";//�������
				String sOrgName = rs.getString("OrgName");      if(sOrgName == null) sOrgName = "";//��������
				//��ȡ�û��Լ���������
				hmp.put("UserID", sUserID);
				hmp.put("UserName", sUserName);
				hmp.put("OrgID", sOrgID);
				hmp.put("OrgName", sOrgName);
				sStates = "00";
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sSql = "select ui.userid as UserID,ui.username as UserName,oi.orgid as OrgID,oi.orgname as OrgName from user_info ui ,org_info oi where oi.orgid = ui.belongorg and oi.orgid = '' and ui.userid in (select userid from User_Role where roleid = '280')";
		
		
		hmp.put("States", sStates);
		return hmp;
	}
	/**
	 * @describe �÷������ڻ�ȡCode_library ��ItemNo�ֶ�
	 * @param sqlQuery
	 * @param sItemName
	 * @param sCodeNo
	 * @return
	 */
	public String getCodeItemNo(SQLQuery sqlQuery,String sItemName,String sCodeNo){
		String sSql = "select itemno from code_library where codeno = '"+sCodeNo+"' and itemname = '"+sItemName+"'";
		String sItemNo = "";
		try {
			sItemNo = sqlQuery.getString(sSql); if(sItemNo == null) sItemNo = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sItemNo;
	}
	
	/**
	 * @descirbe �÷������ڻ�ȡָ����ʽʱ��
	 * @return
	 */
	public static String getDateTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time=   sdf.format( new  Date());
		return time;
	}
}
