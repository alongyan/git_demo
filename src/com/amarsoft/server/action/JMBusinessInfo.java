package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.Tools;

public class JMBusinessInfo extends Action {
	private String sJMID = "";//积木编号
	private String sBusinessTypeY = "";//产品
	private static final Logger logger = Logger.getLogger(JMBusinessInfo.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {  
		// TODO Auto-generated method stub
		sJMID = (String) requestMap.get("JMID");
		ArrayList al = new ArrayList();
		String sSql = "select ba.serialno as BusinessApplyID,ba.jimuid as JMID,ba.customername as CustomerName,ba.customerid as CustomerID,getAttribute6('OccurType',ba.occurtype) as OccurType,"+
					  "ba.businesstype as Busines, (select attribute14 from business_type where typeno = ba.businesstype) as BusinessType,ba.businesssum as BusinessSum,getAttribute6('Month',ba.month) as Month, ba.occurdate as OccurDate,ba.purpose as LoanPurpose,ba.OtherPurpose as OtherPurpose,"+
					  "ba.salesno as MarketerID,ba.salesname as MarketerName,ba.remark as Remark, (select nvl(getattribute6('PayType1',PayType),'') from business_type bt  where bt.typeno = ba.businesstype) as serviceChargesPayMode,ba.inputorgid as InputOrgID from business_apply ba where ba.jimuid = '"+sJMID+"'";
		
		logger.info("获取业务数据信息脚本="+sSql);
		
		ResultSet rs = null;
			try {
				rs = sqlQuery.getResultSet(sSql);
				if(rs.next()){
					String sBusinessApplyID=rs.getString("BusinessApplyID");            if(sBusinessApplyID       == null ) sBusinessApplyID     = "";     
					String sJMID=rs.getString("JMID");                                  if(sJMID                  == null ) sJMID                = "";
					String sCustomerName=rs.getString("CustomerName");                  if(sCustomerName          == null ) sCustomerName        = "";
					String sCustomerID=rs.getString("CustomerID");                      if(sCustomerID            == null ) sCustomerID          = "";
					String sOccurType=rs.getString("OccurType");                        if(sOccurType             == null ) sOccurType           = "";
					sBusinessTypeY = rs.getString("Busines");
					String sBusinessType=rs.getString("BusinessType");                  if(sBusinessType          == null ) sBusinessType        = "";
					String sBusinessSum=rs.getString("BusinessSum");                    if(sBusinessSum           == null ) sBusinessSum         = "";
					String sMonth=rs.getString("Month");                                if(sMonth                 == null ) sMonth               = "";
					String sOccurDate= rs.getString("OccurDate");                        if(sOccurDate             == null ) sOccurDate           = "";
					String sLoanPurpose=rs.getString("LoanPurpose");                    if(sLoanPurpose           == null ) sLoanPurpose         = "";
					String sMarketerID=rs.getString("MarketerID");                      if(sMarketerID            == null ) sMarketerID          = "";
					String sMarketerName=rs.getString("MarketerName");                  if(sMarketerName          == null ) sMarketerName        = "";
					String sRemark=rs.getString("Remark");                              if(sRemark                == null ) sRemark              = "";
					String sserviceChargesPayMode = rs.getString("serviceChargesPayMode"); if(sserviceChargesPayMode == null) sserviceChargesPayMode = "";
					String sInputOrgID = rs.getString("InputOrgID");    				if(sInputOrgID  == null)  sInputOrgID = "";//录入机构号
					String sOtherPurpose  = rs.getString("OtherPurpose"); if(sOtherPurpose == null) sOtherPurpose = "";//贷款其他用途
					
					sLoanPurpose = getItemValue(sBusinessTypeY,sLoanPurpose,sqlQuery);
					
					if("".equals(sMarketerID) || sMarketerID.length()<=0) sMarketerID = sInputOrgID;//若对应的营销人员编号为空则把对应业务的录入机构编号传递给积木时代系统
					
					
					responseMap.put("BusinessApplyID",sBusinessApplyID);
					responseMap.put("JMID",sJMID);
					responseMap.put("CustomerName",sCustomerName);
					responseMap.put("CustomerID",sCustomerID);
					responseMap.put("OccurType",sOccurType);
					responseMap.put("BusinessType",sBusinessType);
					responseMap.put("BusinessSum",sBusinessSum);
					responseMap.put("Month",sMonth);
					responseMap.put("OccurDate",Tools.getStringDate("1", sOccurDate));
					responseMap.put("LoanPurpose",sLoanPurpose);
					responseMap.put("MarketerID",sMarketerID);
					responseMap.put("MarketerName",sMarketerName);
					responseMap.put("Remark",sRemark);
					responseMap.put("serviceChargesPayMode", sserviceChargesPayMode);
					responseMap.put("Purpose", sOtherPurpose);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
				
				logger.info("-----------------------------------------------------------------------------------------");
				//批复信息
				sSql = "select BusinessSum as ApproveBusinessSum, CONDITION as LoanConditions,"+
				" PayDate as RepaymentDate, RefuseReason as RefuseLoan  from flow_opinion fo where fo.serialno in (select max(serialno) from flow_task ft where ft.objectno in (select ba.serialno from business_apply ba where ba.jimuid = '"+sJMID+"') "+
				" and ft.objecttype = 'CreditApply' and ft.phaseno <> '1000' and ft.phaseno <> '5000')";
				
				logger.info("获取业务批复信息脚本="+sSql);
				rs= sqlQuery.getResultSet(sSql);
				
				if(rs.next()){
					String sApproveBusinessSum=rs.getString("ApproveBusinessSum");      if(sApproveBusinessSum    == null ) sApproveBusinessSum  = "";
					String sLoanConditions=rs.getString("LoanConditions");              if(sLoanConditions        == null ) sLoanConditions      = "";
					String sRepaymentDate=rs.getString("RepaymentDate");                if(sRepaymentDate         == null ) sRepaymentDate       = "";
					String sRefuseLoan=rs.getString("RefuseLoan");                      if(sRefuseLoan            == null ) sRefuseLoan          = "";
					
					responseMap.put("ApproveBusinessSum",sApproveBusinessSum);
					responseMap.put("LoanConditions",sLoanConditions);
					responseMap.put("RepaymentDate",sRepaymentDate);
					responseMap.put("RefuseLoan",sRefuseLoan);
					
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
				
				//从合同表中取合同批复信息 add by yhwang
				String  sTermMonth ="";//批复期限
				String  sCustomerSort ="";//客群分类
				String sPreferFlag ="";//是否特价人群
				
				sSql = " select getAttribute6('CustomerSort',CustomerSort) as GuestType,bc.CustomerSort as CustomerSort,bc.flag4 as PreferFlag, "
                    +" getAttribute6('Month', bc.Termmonth) as ApproveMonth,bc.TermMonth as TermMonth,bc.investratio as Investratio, "
                    +" bc.Serviceratio as Serviceratio,bc.Consulrate as Consulrate,bc.ActivityRate as ActivityRate "
                    +" from business_contract bc where bc.jimuid = '"+sJMID+"' ";
  
				logger.info("获取合同信息脚本="+sSql);
				rs= sqlQuery.getResultSet(sSql);
				
				if(rs.next()){
					String sGuestType = rs.getString("GuestType");						 if(sGuestType            == null ) sGuestType          = "";
					sCustomerSort=rs.getString("CustomerSort");                  if(sCustomerSort          == null ) sCustomerSort        = "";
					sPreferFlag = rs.getString("PreferFlag");					 if(sPreferFlag            == null ) sPreferFlag          = "";
					String sApproveMonth=rs.getString("ApproveMonth");                  if(sApproveMonth          == null ) sApproveMonth        = "";
					sTermMonth=rs.getString("TermMonth");                  if(sTermMonth          == null ) sTermMonth        = "";
					String sInvestRatio = rs.getString("InvestRatio");					 if(sInvestRatio            == null ) sInvestRatio          = "0.0";
					String sServiceRatio = rs.getString("ServiceRatio");				 if(sServiceRatio            == null ) sServiceRatio          = "0.0";
					String sConsulRate = rs.getString("ConsulRate");					 if(sConsulRate            == null ) sConsulRate          = "0.0";
					String sActivityRate = rs.getDouble("ActivityRate")+"";					 if(sActivityRate            == null ) sActivityRate          = "0.0";
					
					responseMap.put("GuestType",sGuestType);
					responseMap.put("PreferFlag",sPreferFlag);
					responseMap.put("ApproveMonth",sApproveMonth);
					responseMap.put("InvestRatio",sInvestRatio);
					responseMap.put("ServiceRatio",sServiceRatio);
					responseMap.put("ConsulRate",sConsulRate);
					responseMap.put("ActivityRate",sActivityRate);//活动费率
					
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
				
				//获取渠道服务费率，融资服务费率，技术服务费率  add by  yhwang 20151117
				sSql ="select bc.channelrate as ChannelRate,bc.creditserversum as FinanceServiceRatio,bc.technologyrate as TechnicalRatio "
				+" from business_contract bc  where bc.jimuid ='"+sJMID+"'";
				logger.info("获取渠道服务费率，融资服务费率，技术服务费率的脚本="+sSql);
				rs= sqlQuery.getResultSet(sSql);
				
				if(rs.next()){
					String sChannelRate=rs.getString("ChannelRate");      if(sChannelRate == null ) sChannelRate  = "0.0"; //渠道服务费率
					String sFinanceServiceRatio=rs.getString("FinanceServiceRatio");      if(sFinanceServiceRatio == null ) sFinanceServiceRatio  = "0.0"; //融资服务费率
					String sTechnicalRatio=rs.getString("TechnicalRatio");  if(sTechnicalRatio == null ) sTechnicalRatio = "0.0"; //技术服务费率
					
					responseMap.put("ChannelRate",sChannelRate);
					responseMap.put("FinanceServiceRatio",sFinanceServiceRatio);
					responseMap.put("TechnicalRatio",sTechnicalRatio);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
				
				//共同借款人信息
				sSql = "select ba.ApplicantName as BorrowerCustomerName,  '1' as BorrowerCertType, getAttribute6('Relationship',ba.RelationShip) as  BorrowerRelative,ba.certid as BorrowerCertID,ba.Address as BorrowerAdd,ba.Telephone as BorrowerTel,ba.remark as BorrowerRemark , '' as WORKUNITS, '' as WORKADDRESS   from BUSINESS_APPLICANT ba "+
						" where ba.objecttype = 'CreditApply' and ba.objectno in (select ba.serialno from business_apply ba where ba.jimuid = '"+sJMID+"')";

				if(sBusinessTypeY.startsWith("1007000")){//若为积木时代自营业务
					sSql = " select bti.name as BorrowerCustomerName, '1' as BorrowerCertType,getAttribute6('CustomerRelationShip',bti.relation) as  BorrowerRelative, bti.idcard as BorrowerCertID, bti.ADDRESS  as BorrowerAdd,bti.phone as BorrowerTel,bti.remark as BorrowerRemark,bti.workunits as WORKUNITS,bti.workaddress as WORKADDRESS    from BORROWER_TOGETHER_INFO  bti   where bti.objectno in ("+
							"select ba.serialno from business_apply ba where ba.jimuid = '"+sJMID+"') and phone is not null";
							
				}
				
				logger.info("获取共同借款人数据信息脚本="+sSql);
				
				rs = sqlQuery.getResultSet(sSql);
				while(rs.next()){
					HashMap hm = new HashMap();
					String sBorrowerCustomerName = rs.getString("BorrowerCustomerName"); if(sBorrowerCustomerName == null) sBorrowerCustomerName = "";//借款人姓名
					String sBorrowerCertType = rs.getString("BorrowerCertType"); if(sBorrowerCertType == null) sBorrowerCertType = "";//证件类型
					String sBorrowerCertID = rs.getString("BorrowerCertID"); if(sBorrowerCertID == null) sBorrowerCertID = "";//证件编号
					String sBorrowerRelative = rs.getString("BorrowerRelative"); if(sBorrowerRelative == null) sBorrowerRelative = "";//关联关系
					String sBorrowerTel = rs.getString("BorrowerTel") ; if(sBorrowerTel == null) sBorrowerTel = "";//电话号码
					String sBorrowerAdd = rs.getString("BorrowerAdd"); if(sBorrowerAdd == null)  sBorrowerAdd = "";//地址
					String sBorrowerRemark = rs.getString("BorrowerRemark"); if(sBorrowerRemark == null) sBorrowerRemark = "";//备注
					String sWORKUNITS = rs.getString("WORKUNITS"); if(sWORKUNITS == null) sWORKUNITS = "";//备注
					String sWORKADDRESS = rs.getString("WORKADDRESS"); if(sWORKADDRESS == null) sWORKADDRESS = "";//备注
					
					hm.put("BorrowerCustomerName", sBorrowerCustomerName);
					hm.put("BorrowerCertType", sBorrowerCertType);
					hm.put("BorrowerCertID", sBorrowerCertID);
					hm.put("BorrowerRelative", sBorrowerRelative);
					hm.put("BorrowerTel", sBorrowerTel);
					hm.put("BorrowerAdd", sBorrowerAdd);
					hm.put("WORKUNITS", sWORKUNITS);
					hm.put("WORKADDRESS", sWORKADDRESS);
					hm.put("BorrowerRemark", sBorrowerRemark);
					al.add(hm);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);


				//获取信贷员身份证号  add by weilijiao
				sSql = "select getRoleIdCardNO('"+sJMID+"','信贷员') as certid from dual";

				logger.info("获取信贷员身份证号="+sSql);
				rs= sqlQuery.getResultSet(sSql);

				if(rs.next()){
					String certid =rs.getString("certid");      if(certid == null ) certid  = ""; //信贷员身份证号

					responseMap.put("certid",certid);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);

				//该笔业务状态是审批通过或否决 推送积木时代 add by weilijiao

				sSql = "select fo.phaseno as phaseno from flow_object fo,business_apply ba where fo.objectno = ba.serialno and fo.objecttype = 'CreditApply' and ba.jimuid = '"+sJMID+"'";
				String state = "";
				logger.info("该笔业务状态是审批通过或否决="+sSql);
				rs= sqlQuery.getResultSet(sSql);
				if(rs.next()){
					String phaseno =rs.getString("phaseno");      if(phaseno == null ) phaseno  = ""; //业务状态

					if("1000".equals(phaseno)){
						state = "5";
					}else if("8000".equals(phaseno)){
						state = "6";
					}

					responseMap.put("status",state);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);

				//如果是否决状态的业务，则传拒贷原因
				if("6".equals(state)){
					sSql =	" select getItemName('EnterpriseRefuse',fo.refusereason) as refusereason ,getItemName('EnterpriseRefuse',fo.refusereason1) as refusereason1, " +
							" getItemName('EnterpriseRefuse',fo.refusereason2) as refusereason2, getAttribute6('CustomerSort',fo.customersort) as CustomerSort, fo.isspecial from flow_opinion fo "+
							" where  fo.serialno in (select max(relativeserialno) from flow_task ft where ft.objectno in (select ba.serialno from business_apply ba where ba.jimuid = '"+sJMID+"') and ft.objecttype = 'CreditApply')";
					logger.info("如果是否决状态的业务，则传拒贷原因,客群分类和是否特价="+sSql);
					rs= sqlQuery.getResultSet(sSql);
					if(rs.next()){
						String refusereason =rs.getString("refusereason");      if(refusereason == null ) refusereason  = ""; //拒贷原因1
						String refusereason1 =rs.getString("refusereason1");      if(refusereason1 != null ) refusereason  += ","+refusereason1; //拒贷原因2
						String refusereason2 =rs.getString("refusereason2");      if(refusereason2 != null ) refusereason  += ","+refusereason2; //拒贷原因3
						sCustomerSort=rs.getString("CustomerSort");                  if(sCustomerSort == null ) sCustomerSort        = "";//客群分类
						sPreferFlag = rs.getString("isspecial");					 if(sPreferFlag == null ) sPreferFlag          = "";//是否特价人群

						responseMap.put("refusereason",refusereason);
						responseMap.put("GuestType",sCustomerSort);
						responseMap.put("PreferFlag",sPreferFlag);
					}
					DBCPManager.getInstance().free(rs, rs.getStatement(), null);
				}

			} catch (Exception e) {
				printLog(e);
				throw e;
			} finally {
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
			}
		responseMap.put("CommonBorrower", al);


		return responseMap;
	}
	 
	
	/**
	 * @describe 该方法用户根据产品获取用途字典码
	 * @param sBusinessType
	 * @param sPurpose
	 * @param sqlQuery
	 * @return
	 */
	public String getItemValue(String sBusinessType,String sPurpose,SQLQuery sqlQuery) throws Exception{
		String sSql = "";
		if("1007000180".equals(sBusinessType)){//若为企业贷 则
			sSql = "select nvl(attribute6,'') as purpose from code_library where codeno = 'EntPurpose' and itemno = '"+sPurpose+"'";
		}else {
			sSql = "select nvl(attribute6,'') as purpose from code_library where codeno = 'Purpose1' and itemno = '"+sPurpose+"'";
		}
		return sqlQuery.getString(sSql);
	}

}
