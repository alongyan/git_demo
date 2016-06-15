package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.DataCryptUtils;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该方法用于获取客户信息内容
 * @author xlsun
 *
 */
public class JMCustomerInfo extends Action {
	private static final Logger logger = Logger.getLogger(JMCustomerInfo.class);
	private String sJMID = "";
	ArrayList al = new ArrayList();
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {      
		// TODO Auto-generated method stub
		ResultSet rs;
		String sInputOrgID = "";//业务录入机构号
		String sSalesNo = "";//营销人员编号
		String sBusinessType = "";//
		//根据JMID获取用于身份证号
		sJMID = (String) requestMap.get("JMID");
		String sSql = "select ba.businesstype as BusinessType,ba.salesno as SalesNo,ba.inputorgid as InputOrgID from business_apply ba where ba.jimuid = '"+sJMID+"'";
		rs = sqlQuery.getResultSet(sSql);
		if(rs.next()){
			sInputOrgID = rs.getString("InputOrgID"); if(sInputOrgID == null) sInputOrgID = "";
			sSalesNo = rs.getString("SalesNo"); 	if(sSalesNo == null)  sSalesNo = "";
			sBusinessType = rs.getString("BusinessType");  if(sBusinessType == null) sBusinessType = "";
		}
		rs.close();
		
		if(sBusinessType.startsWith("1007010") || sBusinessType.startsWith("1007020") || sBusinessType.startsWith("1007040") )  sSalesNo = sInputOrgID;
		
		
		//根据JMID获取客户信息内容
		sSql = "select ci.CustomerID as CustomerID,getAttribute6('CertType',ci.CertType) as CertType,ci.CertID as CertID,(select nvl(idexpiry,'') from customer_cert where certtype = 'Ind01' and customerid = ci.customerid) as Vaild,"+
				  "ci.fullname as CustomerName, getAttribute6('Sex',ci.Sex) as Sex, ci.birthday as BirthDay,ci.mobiletelephone  as Mobile,ci.emailadd as Email, ci.ISBLACK as BlackListFlag,ci.VISAPERIOD as DeniedLoan,"+
				  "ci.remark as Remark,getAttribute6('CustomerType',i.customertype) as CustomerType , ci.NativeAdd as NativePlace, getAttribute6('Nationality',ci.Nationality) as Nationality,getAttribute6('Marriage',ci.marriage) as Marriage,"+
				  "getAttribute6('PoliticalFace',ci.politicalface) as PoliticalFace,getAttribute6('EducationExperience',ci.eduexperience) as EduExperience,getAttribute6('FamilyStatus',ci.familystatus) as FamilyStatus,nvl(getitemname('AreaCode',ci.familyadd),ci.familyadd) as FamilyAdd,nvl(ci.familyadddetails,'') as familyadddetails,ci.familyzip as  FamilyZIP ,ci.FamilyTel as FamilyTel,"+
				  "ci.STARTDATE as StartLivingTime, ci.qq as qq,ci.weixin as WeiXin, ci.localdate as LocalTime, '"+sSalesNo+"' as operatorCN  from ind_info ci,customer_info i where ci.customerid = i.customerid  and ci.customerid in (select customerid from business_apply where jimuid = '"+sJMID+"')";
		
		logger.info("获取客户信息基础内容  ="+sSql);
	
	try {
		rs = sqlQuery.getResultSet(sSql);
		if(rs.next()){
			String sCustomerID       = rs.getString("CustomerID");        if(sCustomerID       == null  )        sCustomerID      =  "";//客户编号
			String sCertType         = rs.getString("CertType");          if(sCertType         == null  )        sCertType        =  "";//证件类型
			String sCertID           = rs.getString("CertID");            if(sCertID           == null  )        sCertID          =  "";//证件编号
			String sVaild            = rs.getString("Vaild");             if(sVaild            == null  )        sVaild           =  "";//有效期
			String sCustomerName     = rs.getString("CustomerName");      if(sCustomerName     == null  )        sCustomerName    =  "";//客户名称
			String sSex              = rs.getString("Sex");               if(sSex              == null  )        sSex             =  "";//性别
			String sBirthDay         = Tools.getStringDate("1", rs.getString("BirthDay"));          if(sBirthDay         == null  )        sBirthDay        =  "";//生日
			String sMobile           = rs.getString("Mobile");            if(sMobile           == null  )        sMobile          =  "";// 电话
			String sEmail            = rs.getString("Email");             if(sEmail            == null  )        sEmail           =  "";//邮箱
			String sBlackListFlag    = rs.getString("BlackListFlag");     if(sBlackListFlag    == null  )        sBlackListFlag   =  "";//是否黑名单
			String sDeniedLoan       = rs.getString("DeniedLoan");        if(sDeniedLoan       == null  )        sDeniedLoan      =  "";//拒贷期
			String sRemark           = rs.getString("Remark");            if(sRemark           == null  )        sRemark          =  "";//备注
			String sCustomerType     = rs.getString("CustomerType");      if(sCustomerType     == null  )        sCustomerType    =  "";//客户类型
			String sNativePlace      = rs.getString("NativePlace");       if(sNativePlace      == null  )        sNativePlace     =  "";//户籍地址
			String sNationality      = rs.getString("Nationality");       if(sNationality      == null  )        sNationality     =  "";//民族
			String sMarriage         = rs.getString("Marriage");          if(sMarriage         == null  )        sMarriage        =  "";//婚框
			String sPoliticalFace    = rs.getString("PoliticalFace");     if(sPoliticalFace    == null  )        sPoliticalFace   =  "";//政治面貌
			String sEduExperience    = rs.getString("EduExperience");     if(sEduExperience    == null  )        sEduExperience   =  "";//最高学历
			String sFamilyStatus     = rs.getString("FamilyStatus");      if(sFamilyStatus     == null  )        sFamilyStatus    =  "";//居住状况
			String sFamilyAdd        = rs.getString("FamilyAdd");         if(sFamilyAdd        == null  )        sFamilyAdd       =  "";//居住地址
			String sFamilyZIP        = rs.getString("FamilyZIP");         if(sFamilyZIP        == null  )        sFamilyZIP       =  "";//居住邮编
			String sStartLivingTime  = rs.getString("StartLivingTime");   if(sStartLivingTime  == null  )        sStartLivingTime =  "";//居住开始日期
			String sqq               = rs.getString("qq");                if(sqq               == null  )        sqq              =  "";//qq
			String sWeiXin           = rs.getString("WeiXin");            if(sWeiXin           == null  )        sWeiXin          =  "";//微信
			String sLocalTime        = Tools.getStringDate("1", (rs.getString("LocalTime") == null? "":rs.getString("LocalTime")));         if(sLocalTime        == null  )        sLocalTime       =  "";//申请人来本地时间
			String soperatorCN       = rs.getString("operatorCN");
			String sFamilyTel = rs.getString("FamilyTel");
			String sFamilyadddetails = rs.getString("familyadddetails"); if(sFamilyadddetails == null) sFamilyadddetails = "";//详细地址
			
			//对证件号码进行解密操作
			sCertID = DataCryptUtils.decrypt(sCertID);//对证件号码进行解密
			sMobile = DataCryptUtils.decrypt(sMobile);//对电话号码进行解密
			sFamilyTel = DataCryptUtils.decrypt(sFamilyTel);//住宅电话
			sEmail = DataCryptUtils.decrypt(sEmail);//邮箱
			sFamilyAdd = DataCryptUtils.decrypt(sFamilyAdd);//居住地址
			sFamilyadddetails = DataCryptUtils.decrypt(sFamilyadddetails);//居住详细地址
			
			responseMap.put("CustomerID",sCustomerID);
			responseMap.put("CertType",sCertType);
			responseMap.put("CertID",DataCryptUtils.decrypt(sCertID));
			responseMap.put("Vaild",Tools.getStringDate("1", sVaild));
			responseMap.put("CustomerName",sCustomerName);
			responseMap.put("Sex",sSex);
			responseMap.put("BirthDay",Tools.getStringDate("1",sBirthDay));
			responseMap.put("Mobile",DataCryptUtils.decrypt(sMobile));
			responseMap.put("Email",DataCryptUtils.decrypt(sEmail));
			responseMap.put("BlackListFlag",sBlackListFlag);
			responseMap.put("DeniedLoan",Tools.getStringDate("1", sDeniedLoan));
			responseMap.put("Remark",sRemark);
			responseMap.put("CustomerType",sCustomerType);
			responseMap.put("NativePlace",sNativePlace);
			responseMap.put("Nationality",sNationality);
			responseMap.put("Marriage",sMarriage);
			responseMap.put("PoliticalFace",sPoliticalFace);
			responseMap.put("EduExperience",sEduExperience);
			responseMap.put("FamilyStatus",sFamilyStatus);
			responseMap.put("FamilyAdd",sFamilyAdd+sFamilyadddetails);
			responseMap.put("FamilyZIP",sFamilyZIP);
			responseMap.put("StartLivingTime",Tools.getStringDate("1",sStartLivingTime));
			responseMap.put("qq",sqq);
			responseMap.put("WeiXin",sWeiXin);
			responseMap.put("LocalTime",sLocalTime);
			responseMap.put("operatorCN", soperatorCN);
			responseMap.put("FamilyTel", DataCryptUtils.decrypt(sFamilyTel));
		}
		DBCPManager.getInstance().free(rs, rs.getStatement(), null);
		
		
		logger.info("-----------------------------获取关联人--------------------------------------------------------");
		sSql = " select RelationName,Sex,getAttribute6('RelationShip',Relationship) as Relationship,RelationTel,RelationFixTel,Familyadd,WorkUnit,KnowLoan,DEPARTMENT,POSITION from CUSTOMER_CONTACT  where customerid in (select customerid from business_apply where jimuid = '"+sJMID+"')";
		logger.info("关联人脚本语句   = "+sSql);
		
			rs = sqlQuery.getResultSet(sSql);
				while(rs.next()){
					HashMap hm = new HashMap();
					String sRelationName = rs.getString("RelationName"); if(sRelationName == null) sRelationName = "";//借款人姓名
					String sSex = rs.getString("Sex"); if(sSex == null) sSex = "";//性别
					String sRelationship = rs.getString("Relationship"); if(sRelationship == null) sRelationship = "";//证件编号
					String sRelationTel = rs.getString("RelationTel"); if(sRelationTel == null) sRelationTel = "";//关联关系
					String sRelationFixTel = rs.getString("RelationFixTel") ; if(sRelationFixTel == null) sRelationFixTel = "";//电话号码
					String sFamilyadd = rs.getString("Familyadd"); if(sFamilyadd == null)  sFamilyadd = "";//地址
					String sWorkUnit = rs.getString("WorkUnit"); if(sWorkUnit == null) sWorkUnit = "";//备注
					String sKnowLoan = rs.getString("KnowLoan"); if(sKnowLoan == null) sKnowLoan = "";
					String sDEPARTMENT = rs.getString("DEPARTMENT"); if(sDEPARTMENT == null) sDEPARTMENT = "";
					String sPOSITION = rs.getString("POSITION"); if(sPOSITION == null) sPOSITION = "";
					
					
					hm.put("RelationName", sRelationName);
					hm.put("Sex", sSex);
					hm.put("Relationship", sRelationship);
					hm.put("RelationTel", DataCryptUtils.decrypt(sRelationTel));
					hm.put("RelationFixTel", DataCryptUtils.decrypt(sRelationFixTel));
					hm.put("Familyadd", DataCryptUtils.decrypt(sFamilyadd));
					hm.put("WorkUnit", sWorkUnit);
					hm.put("KnowLoan", sKnowLoan);
					hm.put("DEPARTMENT", sDEPARTMENT);
					hm.put("POSITION", sPOSITION);
					
					al.add(hm);
				}
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
			responseMap.put("Customercontact", al);
	
		} catch (Exception e) {
			printLog(e);
			throw e;
		}finally{
			if(rs != null){
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
			}
		}
		return responseMap;
	}
}
