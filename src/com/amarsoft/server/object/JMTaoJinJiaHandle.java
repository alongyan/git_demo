package com.amarsoft.server.object;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.amarsoft.server.dao.JMMMLoanObject;
import com.amarsoft.server.dao.JMTaoJinJiaObject;
import com.amarsoft.server.dao.JMTimesObject001;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.GetJMID;
/**
 * @describe 该方法用于处理买单侠产品内容
 * @author xlsun Date 2015-07-20
 *
 */
public class JMTaoJinJiaHandle {
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	private String sSerialNo = "";//业务编号
	private String sFlowNo = "";//流程编号
	private String sFlowName = "";//流程名称
	private String sPhaseName = "";//阶段名称
	private String sJMID = "";//获取JMID
	
	public String jMTaoJinJiaSet(HashMap map,SQLQuery sqlQuery) throws Exception{
		JMTaoJinJiaSet js = new JMTaoJinJiaSet();//淘金家实体实例化
		JMTaoJinJiaObject jo = js.setJMTaoJinJiaObject(map);
		sJMID = GetJMID.getJMID(sqlQuery);//获取积木编号
		sCustomerID = handleCustomer(sqlQuery,jo);//获取客户编号
		return sJMID;
	}
	
	
	/**
	 * @describe 该方法用于向业务数据中插入数据
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMTaoJinJiaObject jm001,HashMap useOrg) throws Exception{
		String sFlowTaskNo = "";//流程编号
		//获取流程信息
		getFlowMessage(this.getBusinessTypeNo(jm001.getType(), sqlQuery),sqlQuery);
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
//			String sOrderNo = DBFunction.getSerialNo("ORDER_INFO","");//获取订单信息编号
			
			//执行插入Business_Apply
			String sSql = "insert into Business_Apply (SerialNo,CustomerID,CustomerName,OLDLCNO,BusinessType,BusinessSum,TermSum,termmonth,CreditCity,Creditorname,"+
						  "Creditorsidcard,DAIRELATIONSHIP,Jmboxregistername,CardNo,FIRSTPAYMENTDATE,DUEDATE,MONTHREPAYMENT,InputOrgID,InputDate,InputUserID,occurdate,occurtype,SPID) values "+
						"('"+sSerialNo+"','"+sCustomerID+"','"+jm001.getChineseName()+"','"+jm001.getProjectNo()+"','"+jm001.getType()+"',"+jm001.getFinancingAmount()+","+jm001.getFinancingAmount()+","+jm001.getBatch()
						+",'"+jm001.getApplyCity()+"','"+jm001.getDebteeName()+"','"+jm001.getDebteeID()+"','"+jm001.getDebteeRelation()+"',"+jm001.getUserName()+",'"+jm001.getAccountNo()+"','"+jm001.getRepaymentStartDate()+"','"+jm001.getRepaymentEndDate()+"','"+jm001.getRepaymentDay()
						+"','"+useOrg.get("OrgID")+"','"+sqlQuery.getToday()+"','"+useOrg.get("UserID")+"','"+sqlQuery.getToday()+"','010','"+jm001.getSPID()+"')";
			sqlQuery.execute(sSql);
			//执行插入Flow_object表
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+sqlQuery.getToday()+"')";
			sqlQuery.execute(sSql);
			//执行插入Flow_Task表
			sSql = "insert into Flow_Task (SerialNO,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime,FlowState,Taskstate) values ('"+
					sFlowTaskNo+"','"+sSerialNo+"','CreditApply','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','1010','CompanyFlow2','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+
					getDateTime()+"','START','0')";
			sqlQuery.execute(sSql);
		
	}
	
	/**
	 * @describe 将接口中传入的产品名称装换为对应的产品编号
	 * @param sBusinessName
	 * @return sBusinessTypeNo
	 */
	public String  getBusinessTypeNo(String sBusinessName,SQLQuery sqlQuery) throws Exception{
		String sSql = " select TypeNo from business_Type  where TypeName = '"+sBusinessName+"'";
		ResultSet rs = null;
		String sBusinessTypeNo = "";
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sBusinessTypeNo = rs.getString("TypeNo");
			}
			rs.close();
		
		return sBusinessTypeNo;
	}
	
	/**
	 * @describe 该方法用于获取流程中内容
	 * @param sBusinessType
	 * @param sqlQuery
	 * @throws Exception 
	 */
	public void getFlowMessage(String sBusinessType,SQLQuery sqlQuery) throws Exception{
		String sSql = "select fc.flowno as FlowNo,fc.flowname as FlowName from flow_catalog fc where fc.flowno in (select bt.attribute9  from business_type bt where bt.typeno = '"+sBusinessType+"' )";
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
			throw e;
		}
	}
	/**
	 * @describe 该方法用于更新客户信息
	 * @param sqlQuery
	 * @param jo
	 * @return
	 * @throws Exception 
	 */
	public String handleCustomer(SQLQuery sqlQuery,JMTaoJinJiaObject jo) throws Exception{
		String sCustomerID = "";//客户编号
		HashMap useOrg = getUserOrgInfo(sqlQuery,"100011");//获取对应的录入人以及审批人
		try {
			sCustomerID = sqlQuery.getString("select customerid from customer_info where certid = '"+jo.getIdentityNumber()+"'");
			if(sCustomerID == null || sCustomerID.length() <=0 ){
				sCustomerID = insertCustomerInfo(sqlQuery,jo,useOrg);
			}else{
				sCustomerID = updateCustomerInfo(sqlQuery,jo,sCustomerID,useOrg);
			}
			
		} catch (Exception e) {
			throw e;
		}
		//调用该方法向业务中插入数据
		insertBusinessInfo(sqlQuery,jo,useOrg);
		return sCustomerID;
	}
	 
	/**
	 * @describe 该方法用于插入客户信息
	 * @param sqlQuery
	 * @param jm001
	 * @throws Exception 
	 */
	public String insertCustomerInfo(SQLQuery sqlQuery,JMTaoJinJiaObject jm001,HashMap useOrg) throws Exception{
		String sCustomerID = "";
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			//执行插入Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertType,CertID,InputOrgID,InputDate,InputUserID) values "+
						"('"+sCustomerID+"','"+jm001.getChineseName()+"','0310','Ind01','"+jm001.getIdentityNumber()+"','','"+
						sqlQuery.getToday()+"','')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//执行插入ind_info
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,Sex,Marriage,Birthday,WorkCorp,EduExperience,UnitNature,HeadShip,Worknumber,InputOrgID,InputDate,InputUserID) values ('"+
					sCustomerID+"','"+jm001.getChineseName()+"','Ind01','"+jm001.getIdentityNumber()+"','"+getCodeItemNo(sqlQuery,jm001.getSex(),"Sex")+"','"+getCodeItemNo(sqlQuery,jm001.getMaritalStatus(),"Marriage")+"','"+this.getBirthDay(jm001.getIdentityNumber())+"','"+
					jm001.getCompanyName()+"','"+getCodeItemNo(sqlQuery,jm001.getAcademic(),"EducationExperience")+"','"+getCodeItemNo(sqlQuery,jm001.getCompanyNature(),"UnitNature")+"','"+getCodeItemNo(sqlQuery,jm001.getPosition(),"HeadShip")+"','"+jm001.getWorkYears()+"','"+useOrg.get("OrgID")+"','"+sqlQuery.getToday()+"','"+useOrg.get("UserID")+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//执行插入Customer_Belong表
			sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,InputOrgID,InputDate,InputUserID) values ('"+
					sCustomerID+"','','','1','1','1','1','1','"+useOrg.get("OrgID")+"','"+sqlQuery.getToday()+"','"+useOrg.get("UserID")+"')";
			sqlQuery.execute(sSql);
			System.out.println(sSql);
		} catch (Exception e) {
			throw e;
		}
		return sCustomerID;
	}
	/**
	 * @describe 该方法用于更新客户信息表内容
	 * @param sqlQuery
	 * @param jm001
	 * @throws Exception 
	 */
	public String updateCustomerInfo(SQLQuery sqlQuery,JMTaoJinJiaObject jm001,String sCustomerID,HashMap useOrg) throws Exception{
		String sSql = "";
		try {
			//更新客户信息
			sSql = "update ind_info set Fullname = '"+jm001.getChineseName()+"',Marriage = '"+getCodeItemNo(sqlQuery,jm001.getMaritalStatus(),"Marriage")+"',"+
			" WorkCorp = '"+jm001.getCompanyName()+"',EduExperience = '"+getCodeItemNo(sqlQuery,jm001.getAcademic(),"EducationExperience")+"',UnitNature = '"+getCodeItemNo(sqlQuery,jm001.getCompanyNature(),"UnitNature")+"',"+
			"HeadShip = '"+getCodeItemNo(sqlQuery,jm001.getPosition(),"HeadShip")+ "',Worknumber = '"+jm001.getWorkYears()+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			//更新处理客户权限表
			sSql = "select count(*) as iCount from Customer_belong where customerid = '"+sCustomerID+"' and userid = '"+sUserID+"'";
			System.out.println(sSql);
			String iCount = sqlQuery.getString(sSql);
			if("0".equals(iCount)){
				sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,InputOrgID,InputDate,InputUserID) values ('"+
						sCustomerID+"','','','2','1','1','1','1','"+useOrg.get("OrgID")+"','"+sqlQuery.getToday()+"','"+useOrg.get("UserID")+"')";
				System.out.println(sSql);
				sqlQuery.execute(sSql);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return sCustomerID;
	}
	/**
	 * @describe 该方法用于获取指定格式的日期
	 * @return
	 */
	public String getDated(){
		Date date = new Date();
		String pattern="YYYY/MM/dd";
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		String currentDateString = format.format(date);
		return currentDateString;
	}
	/**
	 * @describe 该方法用于获取对应零售渠道的审批人以及BD人员
	 * @param sqlQuery
	 * @param sChannel
	 * @return
	 * @throws Exception 
	 */
	public HashMap getUserOrgInfo(SQLQuery sqlQuery,String sChannel) throws Exception{
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
			throw e;
		}
		sSql = "select ui.userid as UserID,ui.username as UserName,oi.orgid as OrgID,oi.orgname as OrgName from user_info ui ,org_info oi where oi.orgid = ui.belongorg and oi.orgid = '' and ui.userid in (select userid from User_Role where roleid = '280')";
		
		
		hmp.put("States", sStates);
		return hmp;
	}
	/**
	 * @describe 该方法用于获取Code_library 中ItemNo字段
	 * @param sqlQuery
	 * @param sItemName
	 * @param sCodeNo
	 * @return
	 * @throws Exception 
	 */
	public String getCodeItemNo(SQLQuery sqlQuery,String sItemName,String sCodeNo) throws Exception{
		String sSql = "select itemno from code_library where codeno = '"+sCodeNo+"' and itemname = '"+sItemName+"'";
		String sItemNo = "";
		try {
			sItemNo = sqlQuery.getString(sSql); if(sItemNo == null) sItemNo = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return sItemNo;
	}
	
	/**
	 * @descirbe 该方法用于获取指定格式时间
	 * @return
	 */
	public static String getDateTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time=   sdf.format( new  Date());
		return time;
	}
	
	/**
	 * @describe 根据身份证号获取出生日期
	 * @parameter sCertID
	 * @return sBirthDay
	 */
	private  String getBirthDay(String sCertID){
		String sBirthDay = "";
		if(sCertID.length() == 15){
			sBirthDay ="19"+sCertID.substring(6,12);
		}else if(sCertID.length() == 18){
			sBirthDay = sCertID.substring(6, 14);
		}else{
			sBirthDay = "1900/01/01";
		}
		sBirthDay = sBirthDay.substring(0,4)+"/"+sBirthDay.substring(4,6)+"/"+sBirthDay.substring(6,8);
		return sBirthDay;
	}
}
