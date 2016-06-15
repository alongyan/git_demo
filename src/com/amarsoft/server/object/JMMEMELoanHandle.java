package com.amarsoft.server.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.JMMEMELoanObject;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.servlet.JMMettingServlet;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.GetJMID;

public class JMMEMELoanHandle {
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	private String sSerialNo = "";//业务编号
	private String sFlowNo = "";//流程编号
	private String sFlowName = "";//流程名称
	private String sPhaseName = "";//阶段名称
	private String sJMID = "";//获取JMID
	private String sSerialNo1 = "";
	private Logger logger;
		
	public String jMMEMELoanSet(HashMap map,SQLQuery sqlQuery) throws Exception{
		JMMEMELoanSet js = new JMMEMELoanSet();//么么贷实体类实体化
		JMMEMELoanObject jo = js.setJMMEMELoanObject(map);
		try {
			sJMID = GetJMID.getJMID(sqlQuery);//获取积木编号
			//		sJMID = "10021";//获取积木编号
			handleCustomer(sqlQuery, jo);//获取客户编号
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return sJMID;
	}
	
	/**
	 * @describe 该方法用于向业务数据中插入数据
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMMEMELoanObject jm001,HashMap useOrg){
		String sFlowTaskNo = "";//流程编号
		String sObjectNo = "";//业务编号
		//获取流程信息
		getFlowMessage(jm001.getType(),sqlQuery);
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sSerialNo1 = DBFunction.getSerialNo("MMCREDIT_INFO","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
			
			//执行插入Business_Apply
			String sSql = "insert into Business_Apply (SerialNo,CustomerID,CustomerName,BusinessType,BUSINESSSUM,TermMonth,TERMSUM,MONTHREPAYMENT," +
						  "FIRSTPAYMENTDATE,DUEDATE,LOANDATE,TERMCITY,BEHAVIORSCORE,JIMUID,InputOrgID,InputDate,InputUserID,occurdate,occurtype, " +
						  "SPID,Oldlcno)"+
						  "values "+
						  "('"+sSerialNo+"','"+sCustomerID+"','"+jm001.getChineseName()+"','"+getBusinessType(jm001.getType(), sqlQuery)+"','"+jm001.getFinancingAmount()+"'," +
						  "'"+jm001.getBatch()+"','"+jm001.getLenderAmount()+"','"+jm001.getRepaymentDay()+"','"+jm001.getRepaymentStartDate()+"'," +
						  "'"+jm001.getRepaymentEndDate()+"','"+jm001.getPayDate()+"','"+jm001.getApplyCity()+"','"+jm001.getThirdPartyScore()+"','"+sJMID+"','"+useOrg.get("OrgID")+"','"+
						  getDate()+"','"+useOrg.get("UserID")+"','"+getDate()+"','010'," +
						  "'" +jm001.getSPID()+"','"+jm001.getOldlcno()+"')";
//			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入MMCREDIT_INFO银联智慧信息
			sSql = "insert into MMCREDIT_INFO (SerialNo,ObjectNo,CardType,CardBrand,CardNo,DEALCOUNT,FIRSTDATE,NEWDATE,CARDRANK," +
				   "BANKPOINT,CARDPRODUCT,CARDPROPERTY,STATUS,DEALSUM0,DEALSUM1,InputOrgID,InputDate,InputUserID,CreditType) values "+
				   "('"+sSerialNo1+"','"+sSerialNo+"','"+jm001.getCardType()+"','"+jm001.getBrands()+"','"+jm001.getCardNumber()+"','"+jm001.getDealCount()+"'," +
				   "'"+jm001.getFirstDealDate()+"','"+jm001.getLastDealDate()+"','"+jm001.getLevel()+"','"+jm001.getLocation()+"'," +
				   "'"+jm001.getProduct()+"','"+jm001.getProperty()+"','"+jm001.getStatus()+"','"+jm001.getTotalInAmt()+"','"+jm001.getTotalOutAmt()+"'," +
				   "'"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','03')";
//			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入MMCREDIT_INFO安融惠众信息
			sSql =  "insert into MMCREDIT_INFO (SerialNo,ObjectNo,NOSETTLECOUNT,SETTLECOUNT,APPLYCOUNT,PASSCOUNT,"+
					"RefuseCount,Remark,InputOrgID,InputDate,InputUserID,CreditType) values "+
					"('"+sSerialNo1+"','"+sSerialNo+"','"+jm001.getUnfinishedCount()+"','"+jm001.getFinishedCount()+"','"+jm001.getApplyCount()+"'," +
					"'"+jm001.getPassedCount()+"','"+jm001.getDeniedCount()+"','"+jm001.getOtherCount()+"'," +
					"'"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','01')";
//			System.out.println(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入MMCREDIT_INFO NFCS信息
			sSql =  "insert into MMCREDIT_INFO (SerialNo,ObjectNo,CreditCount,CreditDate,MaxCredit,CreditSum,CreditExcess,"+
					"AccardBack,NowOverdue,OverdueSum,OverdueNper,InputOrgID,InputDate,InputUserID,CreditType) values "+
					"('"+sSerialNo1+"','"+sSerialNo+"','"+jm001.getNFCSDebtCount()+"','"+jm001.getNFCSFirstDebtDate()+"','"+jm001.getNFCSMaxCreditAmt()+"'," +
					"'"+jm001.getNFCSTotalAmt()+"','"+jm001.getNFCSValidAmt()+"','"+jm001.getNFCSRepaymentByMonth()+"'," +
					"'"+jm001.getNFCSTotalOverDueAmt()+"','"+jm001.getNFCSMaxOverDueAmt()+"','"+jm001.getNFCSOverDueCount()+"'," +
					"'"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','02')";
////			System.ntln(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入MMCREDIT_INFO额度信息
			sSql = "insert into MMCREDIT_INFO (SerialNo,ObjectNo,ProspectQuota,Quota,CanQuota,UseQuota,InputOrgID,InputDate,InputUserID,CreditType) values "+
				   "('"+sSerialNo1+"','"+sSerialNo+"','"+jm001.getEstimateAmt()+"','"+jm001.getTotalAmt()+"','"+jm001.getValidAmt()+"'," +
				   "'"+jm001.getUsedAmt()+"','"+useOrg.get("OrgID")+"','"+getDate()+"','"+useOrg.get("UserID")+"','04')";
//			System.ntln(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Flow_object表
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+getDate()+"')";
//			System.ntln(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Flow_Task表
			sSql = "insert into Flow_Task (SerialNO,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime,FlowState,Taskstate) values ('"+
					sFlowTaskNo+"','"+sSerialNo+"','CreditApply','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','1010','RetailDependent','"+useOrg.get("UserID")+"','"+useOrg.get("UserName")+"','"+useOrg.get("OrgID")+"','"+useOrg.get("OrgName")+"','"+
					getDate()+"','START','0')";
//			System.ntln(sSql);
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
		String sSql = "select fc.flowno as FlowNo,fc.flowname as FlowName from flow_catalog fc where fc.flowno in (select bt.attribute9  from business_type bt where bt.typename = '"+sBusinessType+"' and bt.attribute24 = '100010' )";
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
	public String handleCustomer(SQLQuery sqlQuery,JMMEMELoanObject jo){
		HashMap useOrg = getUserOrgInfo(sqlQuery,"100011");//获取对应的录入人以及审批人
		try {
			sCustomerID = sqlQuery.getString("select customerid from customer_info where certid = '"+jo.getIdentityNumber()+"'");
			if(sCustomerID == null) sCustomerID = "";	//客户编号
			if("".equals(sCustomerID) || sCustomerID.length() <=0 ){
				sCustomerID = insertCustomerInfo(sqlQuery,jo,useOrg);
			}else{
				sCustomerID = updateCustomerInfo(sqlQuery,jo,sCustomerID,useOrg);
			}
			//插入业务信息
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
	public String insertCustomerInfo(SQLQuery sqlQuery,JMMEMELoanObject jm001,HashMap useOrg){
		String sCustomerID = "";
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			
			//执行插入Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertType,CertID,InputOrgID,InputDate,InputUserID) values "+
						  "('"+sCustomerID+"','"+jm001.getChineseName()+"','0310','Ind01','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
						  getDate()+"','"+useOrg.get("UserID")+"')";
//			System.ntln(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入ind_info InputOrgID  InputUserID
			
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,InputOrgID,InputDate,InputUserID,Sex,Birthday,NATIONALITY,MARRIAGE,MobileTelephone," +
				   "COMMDETAILEDADD,EMAILADD,EDUEXPERIENCE,AVERAGEINCOME,RELATIVECOMPANY,UNITNATURE,POSITION1,DEPARTMENTS,DEGREETYPE,GRADUATEDATE" +
				   "SCHOOLNAME,PROFESSIONAL,ADMISSIONTIME,QQ,DORMITORYADD,WEIXIN) values ('"+
				   sCustomerID+"','"+jm001.getChineseName()+"','Ind01','"+jm001.getIdentityNumber()+"','"+useOrg.get("OrgID")+"','"+
				   getDate()+"','"+useOrg.get("UserID")+"','"+jm001.getSex()+"','"+jm001.getBirthday()+"','"+jm001.getNational()+"','"+jm001.getMaritalStatus()+"','"+jm001.getPhone()+"'," +
				   "'"+jm001.getPostAddress()+"','"+jm001.getEmail()+"','"+jm001.getAcademic()+"'" +
				   "'"+jm001.getCheckedAmount()+"','"+jm001.getCompanyName()+"','"+jm001.getCompanyNature()+"','"+jm001.getPosition()+"'" +
				   "'"+jm001.getCollege()+"','"+jm001.getDegreeCategory()+"','"+jm001.getGraduateDate()+"','"+jm001.getSchoolName()+"','"+jm001.getSpecialty()+"'" +
				   "'"+jm001.getSchoolEntranceDate()+"','"+jm001.getQQ()+"','"+jm001.getRoomAddress()+"','"+jm001.getWeChat()+"')";
//			System.ntln(sSql);
			sqlQuery.execute(sSql);
			
			//执行插入Customer_Relative表
			sSql = "insert into CUSTOMER_CONTACT (Customerid,RelationName,RelationTel,Relationship,inputdate) values ('"+
					sCustomerID+"','"+jm001.getRelationName()+"','"+jm001.getRelationPhone()+"','"+jm001.getRelationship()+"','"+getDate()+"')";
			sqlQuery.execute(sSql);
//			System.ntln(sSql);
			
			//执行插入Customer_Belong表
			sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,inputdate) values ('"+
					sCustomerID+"','"+useOrg.get("OrgID")+"','"+useOrg.get("UserID")+"','1','1','1','1','1','"+getDate()+"')";
			sqlQuery.execute(sSql);
//			System.ntln(sSql);
			
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
	public String updateCustomerInfo(SQLQuery sqlQuery,JMMEMELoanObject jm001,String sCustomerID,HashMap useOrg){
		try {
			//更新客户信息
			String sSql = "update Customer_Info set CustomerName='"+jm001.getChineseName()+"',CustomerType='0310',CertType='Ind01'," +
						  "CertID='"+jm001.getIdentityNumber()+"',InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"'," +
						  "InputUserID='"+useOrg.get("UserID")+"' where customerid = '"+sCustomerID+"'";
//			System.ntln(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新客户信息
			sSql = "update ind_info set Fullname='"+jm001.getChineseName()+"',CertType='Ind01',CertID='"+jm001.getIdentityNumber()+"'," +
				   "InputOrgID='"+useOrg.get("OrgID")+"',InputDate='"+getDate()+"',InputUserID='"+useOrg.get("UserID")+"',Sex='"+jm001.getSex()+"',Birthday='"+jm001.getBirthday()+"',NATIONALITY='"+jm001.getNational()+"'," +
				   "MARRIAGE='"+jm001.getMaritalStatus()+"',MobileTelephone='"+jm001.getPhone()+"',COMMDETAILEDADD='"+jm001.getPostAddress()+"',EMAILADD='"+jm001.getEmail()+"'," +
				   "EDUEXPERIENCE='"+jm001.getAcademic()+"',AVERAGEINCOME='"+jm001.getCheckedAmount()+"'," +
				   "RELATIVECOMPANY='"+jm001.getCompanyName()+"',UNITNATURE='"+jm001.getCompanyNature()+"',POSITION1='"+jm001.getPosition()+"'," +
				   "DEPARTMENTS='"+jm001.getCollege()+"',DEGREETYPE='"+jm001.getDegreeCategory()+"',GRADUATEDATE='"+jm001.getGraduateDate()+"'," +
				   "SCHOOLNAME='"+jm001.getSchoolName()+"',PROFESSIONAL='"+jm001.getSpecialty()+"',ADMISSIONTIME='"+jm001.getSchoolEntranceDate()+"'," +
				   "QQ='"+jm001.getQQ()+"',DORMITORYADD='"+jm001.getRoomAddress()+"',WEIXIN='"+jm001.getWeChat()+"' where Customerid='"+sCustomerID+"'";
//			System.ntln(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新Customer_Contact表
			sSql = "update CUSTOMER_CONTACT set RelationName='"+jm001.getRelationName()+"',RelationTel='"+jm001.getRelationPhone()+"'," +
				   "Relationship='"+jm001.getRelationship()+"',inputdate='"+getDate()+"' where Customerid='"+sCustomerID+"'";
//			System.ntln(sSql);
			sqlQuery.executeUpdate(sSql);
			
			//更新处理客户权限表
			sSql = "select count(*) as iCount from Customer_belong where customerid = '"+sCustomerID+"' and userid = '"+useOrg.get("UserID")+"'";
//			System.ntln(sSql);
			String iCount = sqlQuery.getString(sSql);
			if(!"0".equals(iCount)){
				sSql = "update Customer_Belong set OrgID='"+useOrg.get("OrgID")+"',UserID='"+useOrg.get("UserID")+"',belongattribute='',belongattribute1=''," +
					   "belongattribute2='',belongattribute3='',belongattribute4='',inputdate='"+getDate()+"' where Customerid='"+sCustomerID+"'";
//			System.ntln(sSql);
				sqlQuery.executeUpdate(sSql);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sCustomerID;
	}
	
	public void init() throws ServletException {
		logger = Logger.getLogger(JMMEMELoanHandle.class);
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
		String sSql = "select typeno from business_type where typename='"+sBusinessName+"' and attribute24 = '100010'";
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
