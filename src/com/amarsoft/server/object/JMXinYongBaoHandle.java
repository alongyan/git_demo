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
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	private String sRelativeID = "";//关联人编号
	private String sCustomerType = "";//客户类型
	private String sSerialNo = "";//业务编号
	private String sSerialNo1 = "";//房产信息编号
	private String sBusinessType = "";//业务品种
	private String sFlowNo = "";//流程编号
	private String sFlowName = "";//流程名称
	private String sPhaseName = "";//阶段名称
	private String sJMID = "";//获取JMID
	private Logger logger;
		
	public String jMXinYongBaoSet(HashMap map,SQLQuery sqlQuery){
		JMXinYongBaoSet js = new JMXinYongBaoSet();//信用宝实体类实体化
		JMXinYongBaoObject jo = js.setJMXinYongBaoObject(map);
//		sJMID = GetJMID.getJMID(sqlQuery);//获取积木编号
		sJMID = "10021";//获取积木编号
		handleCustomer(sqlQuery,jo);//获取客户编号
		
		return sJMID;
	}
	
	/**
	 * @describe 该方法用于向业务数据中插入数据
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,HashMap useOrg){
		String sFlowTaskNo = "";//流程编号
		//获取流程信息
		getFlowMessage(jm001.getType(),sqlQuery);
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
			
			//执行插入Business_Apply
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
			
			//执行插入Flow_object表
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+getDate()+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Flow_Task表
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
	 * @describe 该方法用于获取流程中内容
	 * @param sBusinessType
	 * @param sqlQuery
	 */
	public void getFlowMessage(String sBusinessType,SQLQuery sqlQuery){
		String sSql = "select fc.flowno as FlowNo,fc.flowname as FlowName from flow_catalog fc where fc.flowno in (select bt.attribute9  from business_type bt where bt.typename = '"+sBusinessType+"')";
		ResultSet rs = null;
		try {
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sFlowNo = rs.getString("FlowNo"); if(sFlowNo == null) sFlowNo = "";//流程编号
				sFlowName = rs.getString("FlowName"); if(sFlowName == null) sFlowName = "";//流程名称
			}
			sSql = "select fm.phaseno,fm.phasename as PhaseName from flow_model fm where fm.flowno = '"+sFlowNo+"' and fm.phasetype = '1010'";
			rs.close();
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sPhaseName = rs.getString("PhaseName"); if(sPhaseName == null) sPhaseName = "";//获取阶段名称
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
	 * @describe 该方法用于更新客户信息
	 * @param sqlQuery
	 * @param jo
	 * @return
	 */
	public String handleCustomer(SQLQuery sqlQuery,JMXinYongBaoObject jo){
		ResultSet rs = null;
		HashMap useOrg = getUserOrgInfo(sqlQuery,"100011");//获取对应的录入人以及审批人
		String sql = "select CustomerID,CustomerType from customer_info where certid = '"+jo.getIdentityNumber()+"'";
		try {
			rs = sqlQuery.getResultSet(sql);
			if(rs.next()){
				sCustomerID = rs.getString("CustomerID");	  if(sCustomerID == null) sCustomerID = "";	//客户编号
				sCustomerType = rs.getString("CustomerType"); if(sCustomerType == null) sCustomerType = ""; //客户类型
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
	 * @describe 该方法用于插入客户信息
	 * @param sqlQuery
	 * @param jm001
	 */
	public String insertCustomerInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,HashMap useOrg){
		String sCustomerID = "";
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			sRelativeID = DBFunction.getSerialNo("Customer_Relative","RelativeID");
			sSerialNo1 = DBFunction.getSerialNo("Customer_Realty","SerialNo");
			
			//执行插入Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertID,InputOrgID,InputDate,InputUserID) values "+
						  "('"+sCustomerID+"','"+jm001.getChineseName()+"','"+sCustomerType+"','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
						  getDate()+"','"+useOrg.get("UserID")+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入ind_info Remark作为总工龄字段
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,InputOrgID,InputDate,InputUserID,Sex,Birthday,NativeAdd,Marriage,CREDITLEVEL," +
					"WorkCorp,UnitKind,WorkTel,WorkAdd,WorkBeginDate,HeadShip,Remark,EduExperience,Selfmonthincome) values ('"+
					sCustomerID+"','"+jm001.getChineseName()+"','Ind01','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
					getDate()+"','"+useOrg.get("UserID")+"','"+jm001.getSex()+"','"+jm001.getBirthday()+"','"+jm001.getAccountLocation()+"','"+jm001.getMaritalStatus()+"','"+jm001.getThirdPartyScore()+"'," +
					"'"+jm001.getCompanyName()+"','"+jm001.getCompanyNature()+"','"+jm001.getCompanyTel()+"','"+jm001.getCompanyAddress()+"','"+jm001.getWorkStartDate()+"'" +
					"'"+jm001.getPosition()+"','"+jm001.getWorkYears()+"','"+jm001.getAcademic()+"','"+jm001.getCheckedAmount()+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入生意贷
			if("生意贷???".equals(sBusinessType)){
				sSql = "insert into xxx () values ()";
				System.out.println(sSql);
				sqlQuery.execute(sSql);
			}
			
			//执行插入Customer_Realty表
			sSql = "insert into Customer_Realty(CUSTOMERID,sSerialNo1,REALTYADD,REALTYAREA,PURCHASEDATE,BUILDPRICE,InputOrgID,InputDate,InputUserID) values(" +
				   "'"+sCustomerID+"','"+sSerialNo1+"','"+jm001.getRealEstateLocations()+"','"+jm001.getConstructionArea()+"'," +
				   "'"+jm001.getPurchaseDate()+"','"+jm001.getHouseTotalValue()+"','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"'";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Customer_Cert表
			sSql = "insert into Customer_Cert(CUSTOMERID,ISSUECOUNTRY,CERTTYPE,CERTID,STATUS,MAINFLAG) values (" +
				   "'"+sCustomerID+"'+'中国'+'Ind01','"+jm001.getIdentityNumber()+"','1','1'')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Customer_Credit表
			sSql = "insert into Customer_Credit(RelativeCertID2,ApproveCount1,backStatus1,OverTimes1) values (" +
				   "'"+sCustomerID+"','"+jm001.getCheckedLoanInfo3Month()+"','"+jm001.getWorstRepaymentLastTwoYears()+"','"+jm001.getTotalOverDueLastTwoYears()+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
			
			//执行插入Customer_Relative表
			sSql = "insert into Customer_Relative(CustomerID,RelativeID,RelationShip,CustomerName,CertID,InputOrgID,InputUserID,InputDate) values ("+
			       "'"+sCustomerID+"','"+sRelativeID+"','0301','"+jm001.getSpouseName()+"','"+jm001.getSpouseID()+"','"+useOrg.get("OrgID")+"'," +
			       "'"+useOrg.get("UserID")+"','"+getDate()+"')";
				   sqlQuery.execute(sSql);
				   System.out.println(sSql);
			
			//执行插入Customer_Contract表
			sSql = "insert into CUSTOMER_CONTACT (Customerid,RelationName,RelationTel,Relationship,inputdate,Remark,Familyadd) values ('"+
					sCustomerID+"','"+jm001.getDebteeName()+"','"+jm001.getDebteeMobile()+"','"+jm001.getDebteeRelation()+"','"+getDate()+"'," +
				   "'"+jm001.getDebteeID()+"','"+jm001.getDebteeMailingAddress()+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
			
			//执行插入Customer_Belong表
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
	 * @describe 该方法用于更新客户信息表内容
	 * @param sqlQuery
	 * @param jm001
	 */
	public String updateCustomerInfo(SQLQuery sqlQuery,JMXinYongBaoObject jm001,String sCustomerID,HashMap useOrg){
		try {
			
			//更新客户信息
			String sSql = "update Customer_Info set CustomerName='"+jm001.getChineseName()+"',CustomerType='"+sCustomerType+"'," +
					      "CertID='"+jm001.getIdentityNumber()+"',InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"'," +
					      "InputUserID='"+useOrg.get("UserID")+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新ind_info Remark作为总工龄字段
			sSql = "update ind_info set Fullname='"+jm001.getChineseName()+"',CertType='Ind01',CertID='"+jm001.getIdentityNumber()+"'," +
				   "InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"',InputUserID='"+useOrg.get("UserID")+"',Sex='"+jm001.getSex()+"'," +
				   "Birthday='"+jm001.getBirthday()+"',NativeAdd='"+jm001.getAccountLocation()+"',Marriage='"+jm001.getMaritalStatus()+"',CREDITLEVEL='"+jm001.getThirdPartyScore()+"'," +
				   "WorkCorp='"+jm001.getCompanyName()+"',UnitKind='"+jm001.getCompanyNature()+"',WorkTel='"+jm001.getCompanyTel()+"',WorkAdd='"+jm001.getCompanyAddress()+"'," +
				   "WorkBeginDate='"+jm001.getWorkStartDate()+"',HeadShip='"+jm001.getPosition()+"',Remark='"+jm001.getWorkYears()+"',EduExperience='"+jm001.getAcademic()+"'," +
				   "Selfmonthincome='"+jm001.getCheckedAmount()+"' where Customerid='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新生意贷
			if("生意贷???".equals(sBusinessType)){
				sSql = "update xxx set ";
				System.out.println(sSql);
				sqlQuery.executeUpdate(sSql);
			}
			
			//执行插入Customer_Realty表
			sSql = "update Customer_Realty set REALTYADD='"+jm001.getRealEstateLocations()+"',REALTYAREA='"+jm001.getConstructionArea()+"'," +
				   "PURCHASEDATE='"+jm001.getPurchaseDate()+"',BUILDPRICE='"+jm001.getHouseTotalValue()+"',InputOrgID='"+useOrg.get("OrgID")+"'," +
				   "InputDate='"+getDate()+"',InputUserID='"+useOrg.get("UserID")+"' where CUSTOMERID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新Customer_Cert表
			sSql = "update Customer_Cert set ISSUECOUNTRY='中国',CERTTYPE='Ind01',CERTID='"+jm001.getIdentityNumber()+"',STATUS='1',MAINFLAG='1' where CUSTOMERID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新Customer_Credit表
			sSql = "update Customer_Credit set ApproveCount1='"+jm001.getCheckedLoanInfo3Month()+"',backStatus1='"+jm001.getWorstRepaymentLastTwoYears()+"'," +
				   "OverTimes1='"+jm001.getTotalOverDueLastTwoYears()+"' where RelativeCertID2='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新Customer_Relative表
			sSql = "update Customer_Relative set RelationShip='0301',CustomerName='"+jm001.getSpouseName()+"',CertID='"+jm001.getSpouseID()+"'," +
				   "InputOrgID='"+useOrg.get("OrgID")+"',InputUserID='"+useOrg.get("UserID")+"',InputDate='"+getDate()+"' where CustomerID='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新Customer_Contact表  Remark作为联系人证件号码
			sSql = "update CUSTOMER_CONTACT set RelationName='"+jm001.getDebteeName()+"',RelationTel='"+jm001.getDebteeMobile()+"'," +
				   "Relationship='"+jm001.getDebteeRelation()+"',inputdate='"+getDate()+"',Remark='"+jm001.getDebteeID()+"'," +
				   "Familyadd='"+jm001.getDebteeMailingAddress()+"' where Customerid='"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新处理客户权限表
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
	 * @describe 该方法用于获取对应零售渠道的审批人以及BD人员
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
				String sUserID = rs.getString("UserID");        if(sUserID == null)  sUserID = "";//用户编号
				String sUserName = rs.getString("UserName");    if(sUserName == null) sUserName = "";//用户名称
				String sOrgID = rs.getString("OrgID");          if(sOrgID == null) sOrgID = "";//机构编号
				String sOrgName = rs.getString("OrgName");      if(sOrgName == null) sOrgName = "";//机构名称
				//获取用户以及机构名称
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
	 * @describe 该方法用于获取指定格式的日期
	 * @return
	 */
	public String getDate(){
		Date date = new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		String currentDateString = format.format(date);
		return currentDateString;
	}
	
	/**
	 * @descirbe 该方法用于获取指定格式时间
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
