package com.amarsoft.server.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.JMXinYongBaoObject;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.servlet.JMMettingServlet;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.GetJMID;

public class JMXinYongBaoHandle {
	private String sUserID = "";//�û����
	private String sCustomerID = "";//�ͻ����
	private String sRelativeID = "";//�����˱��
	private String sCustomerType = "";//�ͻ�����
	private String sSerialNo = "";//ҵ����
	private String sSerialNo1 = "";//������Ϣ���
	private String sBusinessType = "";//ҵ��Ʒ��
	private String sFlowNo = "";//���̱��
	private String sFlowName = "";//��������
	private String sPhaseName = "";//�׶�����
	private String sJMID = "";//��ȡJMID
	private Logger logger;
		
	public String jMXinYongBaoSet(HashMap map,SQLQuery sqlQuery){
		JMXinYongBaoSet js = new JMXinYongBaoSet();//���ñ�ʵ����ʵ�廯
		JMXinYongBaoObject jo = js.setJMXinYongBaoObject(map);
//		sJMID = GetJMID.getJMID(sqlQuery);//��ȡ��ľ���
		sJMID = "10021";//��ȡ��ľ���
		handleCustomer(sqlQuery,jo);//��ȡ�ͻ����
		
		return sJMID;
	}
	
	/**
	 * @describe �÷���������ҵ�������в�������
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,HashMap useOrg){
		String sFlowTaskNo = "";//���̱��
		//��ȡ������Ϣ
		getFlowMessage(jm001.getType(),sqlQuery);
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
			
			//ִ�в���Business_Apply
			String sSql = "insert into Business_Apply (SerialNo,CustomerID,CustomerName,BusinessType,BUSINESSSUM,TermMonth,Purpose,TERMSUM,JimuID," +
						  "MONTHPAYMENT,MONTHREPAYMENT,PUTOUTDATE,MATURITY,LASTREPAYMENT,TERMCITY,CARDNO,BANKNAME,BANKPROVICE,BANKAREA,BANKPOINT," +
						  "InputOrgID,InputDate,InputUserID,occurdate,occurtype) values "+
						  "('"+sSerialNo+"','"+sCustomerID+"','"+jm001.getChineseName()+"','"+getBusinessType(jm001.getType(), sqlQuery)+"','"+jm001.getFinancingAmount()+"'," +
						  "'"+jm001.getBatch()+"','"+jm001.getDebtUsage()+"','"+jm001.getLenderAmount()+"','"+sJMID+"','"+jm001.getRepaymentAmountByMonth()+"'," +
						  "'"+jm001.getRepaymentDay()+"','"+jm001.getRepaymentStartDate()+"','"+jm001.getRepaymentEndDate()+"','"+jm001.getHJXD_LastMonthAmount()+"','"+jm001.getApplyCity()+"',"+
						  "'"+jm001.getAccountNo()+"','"+jm001.getBankName()+"','"+jm001.getProvence()+"','"+jm001.getArea()+"','"+jm001.getSubbranchBank()+"'," +
						  "'"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','"+getDate()+"','010')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в���Flow_object��
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+getDate()+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в���Flow_Task��
			sSql = "insert into Flow_Task (SerialNO,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime,FlowState,Taskstate) values ('"+
					sFlowTaskNo+"','"+sSerialNo+"','CreditApply','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','1010','RetailDependent','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+
					getDate()+"','START','0')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @describe �÷������ڻ�ȡ����������
	 * @param sBusinessType
	 * @param sqlQuery
	 */
	public void getFlowMessage(String sBusinessType,SQLQuery sqlQuery){
		String sSql = "select fc.flowno as FlowNo,fc.flowname as FlowName from flow_catalog fc where fc.flowno in (select bt.attribute9  from business_type bt where bt.typename = '"+sBusinessType+"')";
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
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
	/**
	 * @describe �÷������ڸ��¿ͻ���Ϣ
	 * @param sqlQuery
	 * @param jo
	 * @return
	 */
	public String handleCustomer(SQLQuery sqlQuery,JMXinYongBaoObject jo){
		ResultSet rs = null;
		HashMap useOrg = getUserOrgInfo(sqlQuery,"100011");//��ȡ��Ӧ��¼�����Լ�������
		String sql = "select CustomerID,CustomerType from customer_info where certid = '"+jo.getIdentityNumber()+"'";
		try {
			rs = sqlQuery.getResultSet(sql);
			if(rs.next()){
				sCustomerID = rs.getString("CustomerID");	  if(sCustomerID == null) sCustomerID = "";	//�ͻ����
				sCustomerType = rs.getString("CustomerType"); if(sCustomerType == null) sCustomerType = ""; //�ͻ�����
			}
			rs.close();
			if("".equals(sCustomerID) || sCustomerID.length() <=0 ){
				sCustomerID = insertCustomerInfo(sqlQuery,jo,useOrg);
			}else{
				sCustomerID = updateCustomerInfo(sqlQuery,jo,sCustomerID,useOrg);
			}
			insertBusinessInfo(sqlQuery,jo,useOrg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sCustomerID;
	}
	 
	/**
	 * @describe �÷������ڲ���ͻ���Ϣ
	 * @param sqlQuery
	 * @param jm001
	 */
	public String insertCustomerInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,HashMap useOrg){
		String sCustomerID = "";
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			sRelativeID = DBFunction.getSerialNo("Customer_Relative","RelativeID");
			sSerialNo1 = DBFunction.getSerialNo("Customer_Realty","SerialNo");
			
			//ִ�в���Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertID,InputOrgID,InputDate,InputUserID) values "+
						  "('"+sCustomerID+"','"+jm001.getChineseName()+"','"+sCustomerType+"','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
						  getDate()+"','"+useOrg.get("UserID")+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в���ind_info Remark��Ϊ�ܹ����ֶ�
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,InputOrgID,InputDate,InputUserID,Sex,Birthday,NativeAdd,Marriage,CREDITLEVEL," +
					"WorkCorp,UnitKind,WorkTel,WorkAdd,WorkBeginDate,HeadShip,Remark,EduExperience,Selfmonthincome) values ('"+
					sCustomerID+"','"+jm001.getChineseName()+"','Ind01','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
					getDate()+"','"+useOrg.get("UserID")+"','"+jm001.getSex()+"','"+jm001.getBirthday()+"','"+jm001.getAccountLocation()+"','"+jm001.getMaritalStatus()+"','"+jm001.getThirdPartyScore()+"'," +
					"'"+jm001.getCompanyName()+"','"+jm001.getCompanyNature()+"','"+jm001.getCompanyTel()+"','"+jm001.getCompanyAddress()+"','"+jm001.getWorkStartDate()+"'" +
					"'"+jm001.getPosition()+"','"+jm001.getWorkYears()+"','"+jm001.getAcademic()+"','"+jm001.getCheckedAmount()+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в��������
			if("�����???".equals(sBusinessType)){
				sSql = "insert into xxx () values ()";
				System.out.println(sSql);
				sqlQuery.execute(sSql);
			}
			
			//ִ�в���Customer_Realty��
			sSql = "insert into Customer_Realty(CUSTOMERID,sSerialNo1,REALTYADD,REALTYAREA,PURCHASEDATE,BUILDPRICE,InputOrgID,InputDate,InputUserID) values(" +
				   "'"+sCustomerID+"','"+sSerialNo1+"','"+jm001.getRealEstateLocations()+"','"+jm001.getConstructionArea()+"'," +
				   "'"+jm001.getPurchaseDate()+"','"+jm001.getHouseTotalValue()+"','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"'";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в���Customer_Cert��
			sSql = "insert into Customer_Cert(CUSTOMERID,ISSUECOUNTRY,CERTTYPE,CERTID,STATUS,MAINFLAG) values (" +
				   "'"+sCustomerID+"'+'�й�'+'Ind01','"+jm001.getIdentityNumber()+"','1','1'')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//ִ�в���Customer_Credit��
			sSql = "insert into Customer_Credit(RelativeCertID2,ApproveCount1,backStatus1,OverTimes1) values (" +
				   "'"+sCustomerID+"','"+jm001.getCheckedLoanInfo3Month()+"','"+jm001.getWorstRepaymentLastTwoYears()+"','"+jm001.getTotalOverDueLastTwoYears()+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
			
			//ִ�в���Customer_Relative��
			sSql = "insert into Customer_Relative(CustomerID,RelativeID,RelationShip,CustomerName,CertID,InputOrgID,InputUserID,InputDate) values ("+
			       "'"+sCustomerID+"','"+sRelativeID+"','0301','"+jm001.getSpouseName()+"','"+jm001.getSpouseID()+"','"+useOrg.get("OrgID")+"'," +
			       "'"+useOrg.get("UserID")+"','"+getDate()+"')";
				   sqlQuery.execute(sSql);
				   System.out.println(sSql);
			
			//ִ�в���Customer_Contract��
			sSql = "insert into CUSTOMER_CONTACT (Customerid,RelationName,RelationTel,Relationship,inputdate,Remark,Familyadd) values ('"+
					sCustomerID+"','"+jm001.getDebteeName()+"','"+jm001.getDebteeMobile()+"','"+jm001.getDebteeRelation()+"','"+getDate()+"'," +
				   "'"+jm001.getDebteeID()+"','"+jm001.getDebteeMailingAddress()+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
			
			//ִ�в���Customer_Belong��
			sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,inputdate) values ('"+
					sCustomerID+"','"+useOrg.get("OrgID")+"','"+useOrg.get("UserID")+"','1','1','1','1','1','"+getDate()+"')";
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
	public String updateCustomerInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,String sCustomerID,HashMap useOrg){
		try {
			
			//���¿ͻ���Ϣ
			String sSql = "update Customer_Info set CustomerName='"+jm001.getChineseName()+"',CustomerType='"+sCustomerType+"'," +
					      "CertID='"+jm001.getIdentityNumber()+"',InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"'," +
					      "InputUserID='"+useOrg.get("UserID")+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//����ind_info Remark��Ϊ�ܹ����ֶ�
			sSql = "update ind_info set Fullname='"+jm001.getChineseName()+"',CertType='Ind01',CertID='"+jm001.getIdentityNumber()+"'," +
				   "InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"',InputUserID='"+useOrg.get("UserID")+"',Sex='"+jm001.getSex()+"'," +
				   "Birthday='"+jm001.getBirthday()+"',NativeAdd='"+jm001.getAccountLocation()+"',Marriage='"+jm001.getMaritalStatus()+"',CREDITLEVEL='"+jm001.getThirdPartyScore()+"'," +
				   "WorkCorp='"+jm001.getCompanyName()+"',UnitKind='"+jm001.getCompanyNature()+"',WorkTel='"+jm001.getCompanyTel()+"',WorkAdd='"+jm001.getCompanyAddress()+"'," +
				   "WorkBeginDate='"+jm001.getWorkStartDate()+"',HeadShip='"+jm001.getPosition()+"',Remark='"+jm001.getWorkYears()+"',EduExperience='"+jm001.getAcademic()+"'," +
				   "Selfmonthincome='"+jm001.getCheckedAmount()+"' where Customerid='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//���������
			if("�����???".equals(sBusinessType)){
				sSql = "update xxx set ";
				System.out.println(sSql);
				sqlQuery.executeUpdate(sSql);
			}
			
			//ִ�в���Customer_Realty��
			sSql = "update Customer_Realty set REALTYADD='"+jm001.getRealEstateLocations()+"',REALTYAREA='"+jm001.getConstructionArea()+"'," +
				   "PURCHASEDATE='"+jm001.getPurchaseDate()+"',BUILDPRICE='"+jm001.getHouseTotalValue()+"',InputOrgID='"+useOrg.get("OrgID")+"'," +
				   "InputDate='"+getDate()+"',InputUserID='"+useOrg.get("UserID")+"' where CUSTOMERID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//����Customer_Cert��
			sSql = "update Customer_Cert set ISSUECOUNTRY='�й�',CERTTYPE='Ind01',CERTID='"+jm001.getIdentityNumber()+"',STATUS='1',MAINFLAG='1' where CUSTOMERID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//����Customer_Credit��
			sSql = "update Customer_Credit set ApproveCount1='"+jm001.getCheckedLoanInfo3Month()+"',backStatus1='"+jm001.getWorstRepaymentLastTwoYears()+"'," +
				   "OverTimes1='"+jm001.getTotalOverDueLastTwoYears()+"' where RelativeCertID2='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//����Customer_Relative��
			sSql = "update Customer_Relative set RelationShip='0301',CustomerName='"+jm001.getSpouseName()+"',CertID='"+jm001.getSpouseID()+"'," +
				   "InputOrgID='"+useOrg.get("OrgID")+"',InputUserID='"+useOrg.get("UserID")+"',InputDate='"+getDate()+"' where CustomerID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//����Customer_Contact��  Remark��Ϊ��ϵ��֤������
			sSql = "update CUSTOMER_CONTACT set RelationName='"+jm001.getDebteeName()+"',RelationTel='"+jm001.getDebteeMobile()+"'," +
				   "Relationship='"+jm001.getDebteeRelation()+"',inputdate='"+getDate()+"',Remark='"+jm001.getDebteeID()+"'," +
				   "Familyadd='"+jm001.getDebteeMailingAddress()+"' where Customerid='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//���´���ͻ�Ȩ�ޱ�
			sSql = "select count(*) as iCount from Customer_belong where customerid = '"+sCustomerID+"' and userid = '"+useOrg.get("UserID")+"'";
			System.out.println(sSql);
			String iCount = sqlQuery.getString(sSql);
			if(!"0".equals(iCount)){
				sSql = "update Customer_Belong set OrgID='"+useOrg.get("OrgID")+"',UserID='"+useOrg.get("UserID")+"',belongattribute='',belongattribute1=''," +
					   "belongattribute2='',belongattribute3='',belongattribute4='',inputdate='"+getDate()+"' where Customerid='"+sCustomerID+"'";
				System.out.println(sSql);
				sqlQuery.executeUpdate(sSql);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sCustomerID;
	}
	
	public void init() throws ServletException {
		logger = Logger.getLogger(JMXinYongBaoHandle.class);
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
	 * @describe �÷������ڻ�ȡָ����ʽ������
	 * @return
	 */
	public String getDate(){
		Date date = new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		String currentDateString = format.format(date);
		return currentDateString;
	}
	
	/**
	 * @descirbe �÷������ڻ�ȡָ����ʽʱ��
	 * @return
	 */
	public static String getDateTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format( new  Date());
		return time;
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
}
