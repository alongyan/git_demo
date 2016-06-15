package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.amarsoft.server.dao.JMTimesObject001;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.JMTimesEncrypt;

public class JMTest2Action extends Action {
	
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	private String sSerialNo = "";//业务编号
	private String sFlowNo = "";//流程编号
	private String sFlowName = "";//流程名称
	private String sPhaseName = "";//阶段名称
			
	

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		String sKey = "jyT50haQiUBkol1s1KEnLGdN";
		try {
			JMTimesObject001 jm001 = initJMTimesObject(requestMap);
			insertRequestMap(jm001,sqlQuery);
		} catch (Exception e) {
			e.printStackTrace();
			printLog(e);
		} 
		responseMap.put("CustomerID", sCustomerID);
		responseMap.put("BusinessApplyID", sSerialNo);
		
		return responseMap;
	}
	
	public String checkMD5(Map<String, String> requestMap,String sKey) throws Exception{
		JMTimesEncrypt je = new JMTimesEncrypt();
		return je.initFingerprint("a2l7Eqi9Q46f2dsXFF3BRB66dZeFRk19",requestMap);
	}
	
	public HashMap checkRequestMap(Map<String, Object> requestMap,String sKey){
		HashMap hmp = new HashMap();
		JMTimesEncrypt je = new JMTimesEncrypt();
		Set keySet = requestMap.keySet();//返回键的集合 
		Iterator it = keySet.iterator(); 
		String sActualValue = "";

		while(it.hasNext())      //第一种迭代方式取键值 
		  { 
			Object key = it.next();
			String sValue = (String) requestMap.get(key);
			try {
				sActualValue = je.decryptFromBASE64(sKey, sValue.getBytes());
				System.out.println("sActualValue   "+sActualValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hmp.put(sKey, sActualValue);
		  } 
		  return hmp;
	}
	
	/**
	 * @descirbe 该方法用于初始化积木时代类
	 * @param requestMap
	 */
	public JMTimesObject001 initJMTimesObject(Map<String, Object> requestMap){
		JMTimesObject001 jm001 = new JMTimesObject001();
		jm001.setsCertType((String)requestMap.get("CertType"));//获取证件类型
		jm001.setsCertID((String)requestMap.get("CertID"));//获取证件编号
		jm001.setsCustomerName((String)requestMap.get("CustomerName"));//客户名称
		jm001.setsOrgID((String)requestMap.get("OrgID"));//机构编号
		jm001.setsBusinessType((String)requestMap.get("BusinessType"));//业务品种
		jm001.setsMarketerID((String)requestMap.get("MarketerID"));//营销人员编号
		jm001.setsMarketerName((String)requestMap.get("MarketerName"));//营销人员名称
		jm001.setsUserCertID((String)requestMap.get("UserCertID"));//录入人证件号码
		jm001.setsJMID((String)requestMap.get("JMID"));//JMID
		jm001.setsOccurType((String)requestMap.get("OccurType"));//发生类型
		jm001.setsCustomerType((String)requestMap.get("CustomerType"));//客户类型
		return jm001;
	}
	/**
	 * @describe 该方法用于判断数据
	 * @param jm001
	 * @param sqlQuery
	 * @return
	 */
	public Map<String,String> insertRequestMap(JMTimesObject001 jm001,SQLQuery sqlQuery){
		int i = 0 ;//判断该数据是否存在
		Map<String,String> map = new HashMap<String,String>();
		boolean customerFlag = checkMessage("Customer_Info","CertID ='"+jm001.getsCertID()+"'",sqlQuery);
		boolean businessFlag = checkMessage("Business_apply","jimuid ='"+jm001.getsJMID()+"'",sqlQuery);
		sUserID = getOrgUserID(jm001.getsUserCertID(),jm001.getsOrgID(),sqlQuery);//获取该笔业务的商务专员编号
		
		if(customerFlag == true){
			updateCustomerInfo(sqlQuery,jm001);
		}else {
			insertCustomerInfo(sqlQuery,jm001);
		}
		if(businessFlag == true){
			updateBusinessInfo(sqlQuery,jm001);
		}else{
			insertBusinessInfo(sqlQuery,jm001);
		}
		return map;
	}
	
	
	
	/**
	 * @describe 该方法用于向业务数据中插入数据
	 * @param sqlQuery
	 * @param jm001
	 */
	public void insertBusinessInfo(SQLQuery sqlQuery,JMTimesObject001 jm001){
		String sFlowTaskNo = "";//流程编号
		//获取流程信息
		getFlowMessage(jm001.getsBusinessType(),sqlQuery);
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply","SerialNo");
			sFlowTaskNo = DBFunction.getSerialNo("Flow_Task","SerialNo");
			String sUsrID = getOrgUserID(jm001.getsUserCertID(),jm001.getsOrgID(),sqlQuery);
			
			//执行插入Business_Apply
			String sSql = "insert into Business_Apply (SerialNo,CustomerID,CustomerName,BusinessType,SALESNO,SALESNAME,JIMUID,InputOrgID,InputDate,InputUserID,occurdate,occurtype) values "+
						"('"+sSerialNo+"','"+sCustomerID+"','"+jm001.getsCustomerName()+"','"+jm001.getsBusinessType()+"','"+jm001.getsMarketerID()+"','"+jm001.getsMarketerName()+"','"+jm001.getsJMID()+"','"+jm001.getsOrgID()+"','"+
						getDate()+"','"+sUsrID+"','"+getDate()+"','"+jm001.getsOccurType()+"')";
			sqlQuery.execute(sSql);
			//执行插入Flow_object表
			sSql = "insert into Flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,Phaseno,PhaseName,OrgID,OrgName,UserID,UserName,InputDate) values ('"+
			        "CreditApply','"+sSerialNo+"','1010','RetailDependent','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','"+jm001.getsOrgID()+"','"+getOrgorUserName(sqlQuery,"Org_Info","OrgID='"+jm001.getsOrgID()+"'","OrgName")+"','"+sUserID+"','"+getOrgorUserName(sqlQuery,"User_Info","Userid='"+sUserID+"'","UserName")+"','"+getDate()+"')";
			sqlQuery.execute(sSql);
			//执行插入Flow_Task表
			sSql = "insert into Flow_Task (SerialNO,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime,FlowState,Taskstate) values ('"+
					sFlowTaskNo+"','"+sSerialNo+"','CreditApply','"+sFlowNo+"','"+sFlowName+"','0010','"+sPhaseName+"','1010','RetailDependent','"+sUserID+"','"+getOrgorUserName(sqlQuery,"User_Info","Userid='"+sUserID+"'","UserName")+"','"+jm001.getsOrgID()+"','"+getOrgorUserName(sqlQuery,"Org_Info","OrgID='"+jm001.getsOrgID()+"'","OrgName")+"','"+
					getDateTime()+"','START','0')";
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @describe 该方法用于更新业务信息内容
	 * @param sqlQuery
	 * @param jm001
	 */
	public void updateBusinessInfo(SQLQuery sqlQuery,JMTimesObject001 jm001){
		String sSql = "select SerialNo from Business_apply where JIMUID = '"+jm001.getsJMID()+"'";
		System.out.println(sSql);
			//获取客户编号
		try {
			sSerialNo = sqlQuery.getString(sSql);
			sSql = "update business_apply set SALESNO = '"+jm001.getsMarketerID()+"' ,SALESNAME = '"+jm001.getsMarketerName()+"' where JIMUID = '"+jm001.getsJMID()+"'";

			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			
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
			e.printStackTrace();
		}
	}
	/**
	 * @describe 该方法用于插入客户信息
	 * @param sqlQuery
	 * @param jm001
	 */
	public String insertCustomerInfo(SQLQuery sqlQuery,JMTimesObject001 jm001){
		try {
			sCustomerID = DBFunction.getSerialNo("Customer_Info","CustomerID");
			//执行插入Customer_Info
			String sSql = "insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertType,CertID,InputOrgID,InputDate,InputUserID) values "+
						"('"+sCustomerID+"','"+jm001.getsCustomerName()+"','"+jm001.getsCustomerType()+"','"+jm001.getsCertType()+"','"+jm001.getsCertID()+"','"+jm001.getsOrgID()+"','"+
						getDate()+"','"+sUserID+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//执行插入ind_info
			sSql = "insert into ind_info (Customerid,Fullname,CertType,CertID,InputOrgID,InputDate,InputUserID) values ('"+
					sCustomerID+"','"+jm001.getsCustomerName()+"','"+jm001.getsCertType()+"','"+jm001.getsCertID()+"','"+jm001.getsOrgID()+"','"+
					getDate()+"','"+sUserID+"')";
			System.out.println(sSql);
			sqlQuery.execute(sSql);
			//执行插入Customer_Belong表
			sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,inputdate) values ('"+
					sCustomerID+"','"+jm001.getsOrgID()+"','"+sUserID+"','1','1','1','1','1','"+getDate()+"')";
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
	public void updateCustomerInfo(SQLQuery sqlQuery,JMTimesObject001 jm001){
		String sSql = "select customerid from customer_info where certid = '"+jm001.getsCertID()+"'";
		System.out.println(sSql);
		try {
			//获取客户编号
			sCustomerID = sqlQuery.getString(sSql);
			//更新客户信息
			sSql = "update Customer_Info set CustomerName = '"+jm001.getsCustomerName()+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			//更新客户信息
			sSql = "update ind_info set Fullname = '"+jm001.getsCustomerName()+"' where customerid = '"+sCustomerID+"'";
			System.out.println(sSql);
			sqlQuery.executeUpdate(sSql);
			//更新处理客户权限表
			sSql = "select count(*) as iCount from Customer_belong where customerid = '"+sCustomerID+"' and userid = '"+sUserID+"'";
			System.out.println(sSql);
			String iCount = sqlQuery.getString(sSql);
			if("0".equals(iCount)){
				sSql = "insert into Customer_Belong (Customerid,OrgID,UserID,belongattribute,belongattribute1,belongattribute2,belongattribute3,belongattribute4,inputdate) values ('"+
						sCustomerID+"','"+jm001.getsOrgID()+"','"+sUserID+"','2','1','1','1','1','"+getDate()+"')";
				System.out.println(sSql);
				sqlQuery.execute(sSql);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @describe 该方法用于获取指定格式的日期
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
	 * @descirbe 该方法用于获取指定格式时间
	 * @return
	 */
	public static String getDateTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time=   sdf.format( new  Date());
		return time;
	}
	/**
	 * @describe 该方法用于获取用于表中的用户编号
	 * @param UserCertID
	 * @param OrgID
	 * @param sqlQuery
	 * @return
	 */
	public String getOrgUserID (String UserCertID,String OrgID,SQLQuery sqlQuery){
		String sUserID = "";
		String sSql = "select UserID from user_info where CertID = '"+UserCertID+"'";
		System.out.println(sSql);
		try {
			sUserID = sqlQuery.getString(sSql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sUserID;
	}
	/**
	 * @describe 该方法用于获取指定表中是否存在数据
	 * @param sTable
	 * @param sWhere
	 * @param sqlQuery
	 * @return
	 */
	public boolean checkMessage(String sTable,String sWhere,SQLQuery sqlQuery){
		boolean returnFlag = true;
		String sReturn = "";
		String sSql = "select count(*) as iCount from "+sTable+" where "+sWhere;
		System.out.println(sSql);
		try {
			sReturn = sqlQuery.getString(sSql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("0".equals(sReturn)) returnFlag = false;
		return returnFlag;
	}
	/**
	 * @describe 该方法用于获取指定表中的字段值
	 * @param sqlQuery
	 * @param sTable
	 * @param sWhere
	 * @param sColumn
	 * @return
	 */
	public String getOrgorUserName(SQLQuery sqlQuery,String sTable,String sWhere,String sColumn){
		String sName = "";
		String sSql = "select "+sColumn+" from "+sTable+" where "+sWhere;
		System.out.println(sSql);
		try {
			sName = sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sName;
	}

}
