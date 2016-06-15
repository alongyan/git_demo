package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe 改方法用于处理业务申请基本信息内容
 * @author Sun
 *
 */
public class HandleApplyInfo {
	private Logger logger = Logger.getLogger(HandleApplyInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String sOrgID = "";//机构编号
	private String sUserID = "";//用户编号
	private String sBusinessType ="";//业务品种
	
	private String sStatus = "";//数据获取状态
	private String sOrgName = "";//机构名称
	private String sUserName = "";//用户名称
	private String sFlowNo = "";//流程编号
	private String sFlowName = "";//流程名称
	private String sPhaseNo = "";//阶段编号
	private String sPhaseName = "";//阶段名称
	private String sCustomerID = "";//客户编号
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
	 * @describe 该方法用于处理业务申请信息模块内容
	 */
	public void handleApplyInfoMessage() throws Exception{
		sBusinessType =Tools.getObjectToString(hashMap.get("Type").toString());
		getFlowInfo();//获取审批流程信息
		insertApplyInfo();//处理业务申请信息
	}
	
//	/**
//	 * @throws Exception 
//	 * @describe 该方法用于获取业务品种
//	 */
//	private void getBusinessType() throws Exception {
//		String sSql =" select bt.typeno as TypeNo from business_type bt where bt.attribute24 ='"+this.sOrgID+"' and bt.TypeName='"+Tools.getObjectToString(hashMap.get("Type").toString())+"' ";
//		logger.info("查询业务品种执行脚本 = "+sSql);
//		try {
//			ResultSet rs = sqlQuery.getResultSet(sSql);
//			if(rs.next()){
//				sBusinessType = rs.getString("TypeNo");
//			}
//			rs.getStatement().close();
//		} catch (Exception e) {
//			logger.info("查询业务品种执行脚本错误");
//			e.printStackTrace();
//			throw e;
//		}
//	}
	/**
	 * @describe 该方法用于在业务申请表中插入业务数据
	 * @return SerialNo
	 * @throws Exception 
	 */
	private String insertApplyInfo() throws Exception{
		String sSerialNo = "";//业务申请编号
		try {
			sSerialNo = DBFunction.getSerialNo("Business_Apply", "SerialNo");//获取申请阶段业务申请编号
			
			String sSql = "insert into Business_apply (SerialNo,JimuID,InputUserId,InputOrgID,InputDate,"+
			"ThirdPartySerialNo,BusinessType,BusinessSum,Month,TermMonth,Purpose,OtherPurpose,CreditCity,"+
			"CustomerSort,SalesCanal,SalesNo,SalesName,OccurDate,CustomerID,CustomerName,OccurType,BusinessCurrency,"+
			"ApplyType,TempSaveFlag,BD,BelongOrg,FLAG4,Operateorgid,Operateuserid) values(:SerialNo,:JimuID,:InputUserId,:InputOrgID,:InputDate,"+
			":ThirdPartySerialNo,:BusinessType,:BusinessSum,:Month,:TermMonth,:Purpose,:OtherPurpose,:CreditCity,"+
			":CustomerSort,:SalesCanal,:SalesNo,:SalesName,:OccurDate,:CustomerID,:CustomerName,:OccurType,:BusinessCurrency,"+
			":ApplyType,:TempSaveFlag,:BD,:BelongOrg,:FLAG4,:Operateorgid,:Operateuserid)";
			
			SQLHandleInfo sSqlHandle = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sSerialNo)//申请业务编号
			.setParameter("JimuID", sJMID)//JMID
			.setParameter("InputUserId",this.sUserID)//录入人员编号
			.setParameter("InputOrgID", this.sOrgID)//录入机构编号
			.setParameter("InputDate", Tools.getToday(2))//录入日期
			.setParameter("ThirdPartySerialNo", Tools.getObjectToString(hashMap.get("ProjectNo")))//第三方机构编号
			.setParameter("BusinessType", this.sBusinessType)//业务品种
			.setParameter("BusinessSum", Double.parseDouble(hashMap.get("FinancingAmount").toString()))//业务申请金额
			.setParameter("Month", Tools.getObjectToString(hashMap.get("TermMonth").toString()))//贷款期限
			.setParameter("TermMonth", Integer.parseInt(hashMap.get("TermMonth").toString()))//贷款期限
			.setParameter("Purpose", Tools.getCodeItemNo("Purpose1", Tools.getObjectToString(hashMap.get("DebtUsage")), sqlQuery))
			.setParameter("OtherPurpose", Tools.getObjectToString(hashMap.get("Purpose")))//借款用途描述
			.setParameter("CreditCity", Tools.getObjectToString(hashMap.get("ApplyCity")))//业务申请城市
			.setParameter("CustomerSort", Tools.getCodeItemNo("CustomerSort",("".equals(Tools.getObjectToString(hashMap.get("CustomerSort")))||Tools.getObjectToString(hashMap.get("CustomerSort"))==null)?"一般人群":Tools.getObjectToString(hashMap.get("CustomerSort")),sqlQuery))//客群分类
			.setParameter("SalesCanal", Tools.getCodeItemNo("SalesCanal", Tools.getObjectToString(hashMap.get("CaleChanel")), sqlQuery))//营销渠道
			.setParameter("SalesNo", Tools.getObjectToString(hashMap.get("SaleCarID")))//营销人员身份证号
			.setParameter("SalesName", Tools.getObjectToString(hashMap.get("SaleName")))
			.setParameter("OccurDate", Tools.getToday(2))//获取发生日期
			.setParameter("CustomerID", this.sCustomerID)//客户编号
			.setParameter("CustomerName", Tools.getObjectToString(hashMap.get("ChineseName")))//客户名称
			.setParameter("OccurType", "010")//发生类型
			.setParameter("BusinessCurrency", "01")//币种
			.setParameter("ApplyType", "RetailDependent")//状态
			.setParameter("TempSaveFlag", "2")//保存状态
			.setParameter("BD", this.sUserID)//BD编号
			.setParameter("BelongOrg", "100007")//所属机构
			.setParameter("FLAG4", "2")//是否特价
			.setParameter("Operateorgid", this.sOrgID)//操作机构
			.setParameter("Operateuserid", this.sUserID);//操作人
			
			
			
			logger.info("获取业务信息执行脚本"+sSqlHandle.getSql());
			sqlQuery.execute(sSqlHandle.getSql());//执行脚本
			
			sSql = "insert into flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"OrgID,OrgName,UserID,UserName,InputDate) values (:ObjectType,:ObjectNo,:PhaseType,:ApplyType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,"+
			":OrgID,:OrgName,:UserID,:UserName,:InputDate)";
			
			SQLHandleInfo sSqlHandleFlow = new SQLHandleInfo(sSql)
			.setParameter("ObjectType", "CreditApply")//类型
			.setParameter("ObjectNo", sSerialNo)//业务申请编号
			.setParameter("PhaseType", "1010")//流程阶段默认为第一岗位
			.setParameter("ApplyType", "RetailDependent")//零售业务申请类型默认值
			.setParameter("FlowNo", this.sFlowNo)//申请流程编号
			.setParameter("FlowName", this.sFlowName)//流程名称
			.setParameter("PhaseNo", this.sPhaseNo)//阶段编号
			.setParameter("PhaseName", this.sPhaseName)//阶段名称
			.setParameter("OrgID", this.sOrgID)//机构号
			.setParameter("OrgName", this.sOrgName)//机构名称
			.setParameter("UserID", this.sUserID)//用户编号
			.setParameter("UserName", this.sUserName)//用户名称
			.setParameter("InputDate", Tools.getToday(2));//获取当前日期
			
			logger.info("业务流程脚本语句 "+sSqlHandleFlow.getSql());
			this.sqlQuery.execute(sSqlHandleFlow.getSql());
			
			String sTaskSerialNo = DBFunction.getSerialNo("Flow_task", "SerialNo");//获取流程编号
			
			sSql = "insert into flow_task (SerialNo,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"PhaseType,ApplyType,UserID,UserName,OrgID,OrgName,BeginTime) values (:SerialNo,:ObjectNo,"+
			":ObjectType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,:PhaseType,:ApplyType,:UserID,:UserName,"+
			":OrgID,:OrgName,:BeginTime)";
			
			SQLHandleInfo sSqlHandleFlowTask = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sTaskSerialNo)//审批流程TaskNo
			.setParameter("ObjectNo", sSerialNo)//业务申请编号
			.setParameter("ObjectType", "CreditApply")//业务类型
			.setParameter("FlowNo", this.sFlowNo)//审批流程编号
			.setParameter("FlowName", this.sFlowName)//审批流程名称
			.setParameter("PhaseNo", this.sPhaseNo)//流程节点编号
			.setParameter("PhaseName", this.sPhaseName)//流程节点名称
			.setParameter("PhaseType", "1010")//设置默认为第一岗位
			.setParameter("ApplyType", "RetailDependent")//业务类型
			.setParameter("UserID", this.sUserID)//申请人编号
			.setParameter("UserName", this.sUserName)//申请人名称
			.setParameter("OrgID", this.sOrgID)//机构编号
			.setParameter("OrgName", this.sOrgName)//机构名称
			.setParameter("BeginTime", Tools.getDate());
			
			logger.info("业务流程脚本语句 "+sSqlHandleFlowTask.getSql());
			this.sqlQuery.execute(sSqlHandleFlowTask.getSql());
			//插入关联额度信息
			insertlCInfo(sSerialNo,Double.parseDouble(hashMap.get("FinancingAmount").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取业务申请编号出现错误");
			e.printStackTrace();
			sSerialNo = "Fail";
			throw e;
		}
		return sSerialNo;
	}
	/**
	 * @descirbe 改方法用于获取审批流程信息
	 */
	private void getFlowInfo(){
		HashMap hashMap = Tools.getFlowInfo(this.sOrgID, this.sUserID,this.sBusinessType , sqlQuery);
		if("0000".equals(hashMap.get("Status"))){//审批流程信息获取正常
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
	 * @describe 改方法用于处理关联额度信息
	 * @param sApplySerialNo
	 * @param dBusinessSum
	 * @return
	 * @throws Exception 
	 */
	private void insertlCInfo(String sApplySerialNo,double dApplySum) throws Exception{//业务申请编号、申请金额
		double dYSSum=0;//预审项目总金额
		double dSuming=0;//在途项目总金额
		double dVouchSum=0;//在保项目总金额 
		double dUseSum=0;//可用金额
		double dBusinessSum = 0;
		String sBSerialNo = "";//关联额度合同编号
		String sSerialNo = "";//申请编号
		String sObjectNo ="";//关联额度编号
		String sSql = "";
		
		try {
			sSerialNo = DBFunction.getSerialNo("CL_OCCUPY", "SerialNo");
			//获取关联额度编号
			String sLineCustomerID = this.sqlQuery.getString("select RelativeCustomerID from Org_Info where orgID='100007'");
			sObjectNo = this.sqlQuery.getString("select SerialNo from BUSINESS_CONTRACT where CustomerID = '"+sLineCustomerID+"' and BusinessType = '3020' and PutOutDate <= '"+Tools.getToday(2)+"' and Maturity >= '"+Tools.getToday(2)+"' and (FinishDate is null or FinishDate = ' ') and PigeonholeDate is not null and (PigeonholeDate<>'' or PigeonholeDate<>' ') and FreezeFlag in ('1','3') ");
			//额度金额
			dBusinessSum = this.sqlQuery.getDouble("select nvl(BusinessSum,0) as BusinessSum from Business_Contract where SerialNo='"+sObjectNo+"'");
			//获取预审金额
			sSql = "select sum(nvl(BusinessSum,0)) as BusinessSum from Business_Apply where BelongOrg='"+sOrgID+"' and (YSSerialNo is not null or YSSerialNo<>' ')";
			dYSSum =  this.sqlQuery.getDouble(sSql);
			//在途项目总金额
			sSql = "select sum(nvl(ba.BusinessSum,0)) as BusinessSum from Business_Apply ba ,Flow_Object fO,CL_OCCUPY cl where  ba.SerialNo=fo.ObjectNo and fo.phaseNo<>'0010' and fo.phaseNo<>'1000' and fo.phaseNo<>'8000' and fo.objecttype='CreditApply' and ba.BusinessType not like '3%'  and cl.ObjectNo=ba.SerialNo and cl.ObjectType='CreditApply' and cl.RelativeSerialNo='"+sObjectNo+"'";
			dSuming = this.sqlQuery.getDouble(sSql);
			//在保项目总金额 未放款金额+已放款余额
			sSql = "select sum(nvl(bc.BusinessSum,0)+nvl(bc.Balance,0)-nvl((select nvl(bd.businesssum,0) from Business_Duebill bd where bd.relativeserialno2 = bc.SerialNo),0)) as Balance from Business_Contract bc,CL_OCCUPY cl where  bc.SerialNo=cl.ObjectNo and cl.ObjectType='BusinessContract' and cl.RelativeSerialNo='"+sObjectNo+"'";
			dVouchSum = this.sqlQuery.getDouble(sSql);
			//可用金额 = 额度金额 - 在途项目金额-已放余额-未放金额 
			dUseSum = dBusinessSum - dSuming - dVouchSum;
			
			sSql = "Insert into CL_OCCUPY(SerialNo,ObjectType,ObjectNo,RelativeSerialNo,BusinessSum,Account2,Account3,Account4,Account5,Account6) "+
					"values(:SerialNo,:ObjectType,:ObjectNo,:RelativeSerialNo,:BusinessSum,:Account2,:Account3,:Account4,:Account5,:Account6)";
			SQLHandleInfo handleInfoCl = new SQLHandleInfo(sSql)
			.setParameter("SerialNo",sSerialNo )//额度信息流水号
			.setParameter("ObjectType", "CreditApply")//额度类型
			.setParameter("ObjectNo", sApplySerialNo)//业务申请流水号
			.setParameter("RelativeSerialNo", sObjectNo)//关联额度编号
			.setParameter("BusinessSum", dBusinessSum)//
			.setParameter("Account2", dYSSum)//
			.setParameter("Account3", dSuming)//
			.setParameter("Account4", dVouchSum)//
			.setParameter("Account5", dApplySum)//申请金额
			.setParameter("Account6", dUseSum);//
			sSql = handleInfoCl.getSql();
			this.sqlQuery.execute(sSql);
			logger.info("关联额度信息==="+sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------执行插入额度信息时出错----");
			throw e;
		}
	}
	
}
