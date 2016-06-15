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
 * @describe �÷������ڻ�ȡ�ͻ���Ϣ����
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
		String sInputOrgID = "";//ҵ��¼�������
		String sSalesNo = "";//Ӫ����Ա���
		String sBusinessType = "";//
		//����JMID��ȡ�������֤��
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
		
		
		//����JMID��ȡ�ͻ���Ϣ����
		sSql = "select ci.CustomerID as CustomerID,getAttribute6('CertType',ci.CertType) as CertType,ci.CertID as CertID,(select nvl(idexpiry,'') from customer_cert where certtype = 'Ind01' and customerid = ci.customerid) as Vaild,"+
				  "ci.fullname as CustomerName, getAttribute6('Sex',ci.Sex) as Sex, ci.birthday as BirthDay,ci.mobiletelephone  as Mobile,ci.emailadd as Email, ci.ISBLACK as BlackListFlag,ci.VISAPERIOD as DeniedLoan,"+
				  "ci.remark as Remark,getAttribute6('CustomerType',i.customertype) as CustomerType , ci.NativeAdd as NativePlace, getAttribute6('Nationality',ci.Nationality) as Nationality,getAttribute6('Marriage',ci.marriage) as Marriage,"+
				  "getAttribute6('PoliticalFace',ci.politicalface) as PoliticalFace,getAttribute6('EducationExperience',ci.eduexperience) as EduExperience,getAttribute6('FamilyStatus',ci.familystatus) as FamilyStatus,nvl(getitemname('AreaCode',ci.familyadd),ci.familyadd) as FamilyAdd,nvl(ci.familyadddetails,'') as familyadddetails,ci.familyzip as  FamilyZIP ,ci.FamilyTel as FamilyTel,"+
				  "ci.STARTDATE as StartLivingTime, ci.qq as qq,ci.weixin as WeiXin, ci.localdate as LocalTime, '"+sSalesNo+"' as operatorCN  from ind_info ci,customer_info i where ci.customerid = i.customerid  and ci.customerid in (select customerid from business_apply where jimuid = '"+sJMID+"')";
		
		logger.info("��ȡ�ͻ���Ϣ��������  ="+sSql);
	
	try {
		rs = sqlQuery.getResultSet(sSql);
		if(rs.next()){
			String sCustomerID       = rs.getString("CustomerID");        if(sCustomerID       == null  )        sCustomerID      =  "";//�ͻ����
			String sCertType         = rs.getString("CertType");          if(sCertType         == null  )        sCertType        =  "";//֤������
			String sCertID           = rs.getString("CertID");            if(sCertID           == null  )        sCertID          =  "";//֤�����
			String sVaild            = rs.getString("Vaild");             if(sVaild            == null  )        sVaild           =  "";//��Ч��
			String sCustomerName     = rs.getString("CustomerName");      if(sCustomerName     == null  )        sCustomerName    =  "";//�ͻ�����
			String sSex              = rs.getString("Sex");               if(sSex              == null  )        sSex             =  "";//�Ա�
			String sBirthDay         = Tools.getStringDate("1", rs.getString("BirthDay"));          if(sBirthDay         == null  )        sBirthDay        =  "";//����
			String sMobile           = rs.getString("Mobile");            if(sMobile           == null  )        sMobile          =  "";// �绰
			String sEmail            = rs.getString("Email");             if(sEmail            == null  )        sEmail           =  "";//����
			String sBlackListFlag    = rs.getString("BlackListFlag");     if(sBlackListFlag    == null  )        sBlackListFlag   =  "";//�Ƿ������
			String sDeniedLoan       = rs.getString("DeniedLoan");        if(sDeniedLoan       == null  )        sDeniedLoan      =  "";//�ܴ���
			String sRemark           = rs.getString("Remark");            if(sRemark           == null  )        sRemark          =  "";//��ע
			String sCustomerType     = rs.getString("CustomerType");      if(sCustomerType     == null  )        sCustomerType    =  "";//�ͻ�����
			String sNativePlace      = rs.getString("NativePlace");       if(sNativePlace      == null  )        sNativePlace     =  "";//������ַ
			String sNationality      = rs.getString("Nationality");       if(sNationality      == null  )        sNationality     =  "";//����
			String sMarriage         = rs.getString("Marriage");          if(sMarriage         == null  )        sMarriage        =  "";//���
			String sPoliticalFace    = rs.getString("PoliticalFace");     if(sPoliticalFace    == null  )        sPoliticalFace   =  "";//������ò
			String sEduExperience    = rs.getString("EduExperience");     if(sEduExperience    == null  )        sEduExperience   =  "";//���ѧ��
			String sFamilyStatus     = rs.getString("FamilyStatus");      if(sFamilyStatus     == null  )        sFamilyStatus    =  "";//��ס״��
			String sFamilyAdd        = rs.getString("FamilyAdd");         if(sFamilyAdd        == null  )        sFamilyAdd       =  "";//��ס��ַ
			String sFamilyZIP        = rs.getString("FamilyZIP");         if(sFamilyZIP        == null  )        sFamilyZIP       =  "";//��ס�ʱ�
			String sStartLivingTime  = rs.getString("StartLivingTime");   if(sStartLivingTime  == null  )        sStartLivingTime =  "";//��ס��ʼ����
			String sqq               = rs.getString("qq");                if(sqq               == null  )        sqq              =  "";//qq
			String sWeiXin           = rs.getString("WeiXin");            if(sWeiXin           == null  )        sWeiXin          =  "";//΢��
			String sLocalTime        = Tools.getStringDate("1", (rs.getString("LocalTime") == null? "":rs.getString("LocalTime")));         if(sLocalTime        == null  )        sLocalTime       =  "";//������������ʱ��
			String soperatorCN       = rs.getString("operatorCN");
			String sFamilyTel = rs.getString("FamilyTel");
			String sFamilyadddetails = rs.getString("familyadddetails"); if(sFamilyadddetails == null) sFamilyadddetails = "";//��ϸ��ַ
			
			//��֤��������н��ܲ���
			sCertID = DataCryptUtils.decrypt(sCertID);//��֤��������н���
			sMobile = DataCryptUtils.decrypt(sMobile);//�Ե绰������н���
			sFamilyTel = DataCryptUtils.decrypt(sFamilyTel);//סլ�绰
			sEmail = DataCryptUtils.decrypt(sEmail);//����
			sFamilyAdd = DataCryptUtils.decrypt(sFamilyAdd);//��ס��ַ
			sFamilyadddetails = DataCryptUtils.decrypt(sFamilyadddetails);//��ס��ϸ��ַ
			
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
		
		
		logger.info("-----------------------------��ȡ������--------------------------------------------------------");
		sSql = " select RelationName,Sex,getAttribute6('RelationShip',Relationship) as Relationship,RelationTel,RelationFixTel,Familyadd,WorkUnit,KnowLoan,DEPARTMENT,POSITION from CUSTOMER_CONTACT  where customerid in (select customerid from business_apply where jimuid = '"+sJMID+"')";
		logger.info("�����˽ű����   = "+sSql);
		
			rs = sqlQuery.getResultSet(sSql);
				while(rs.next()){
					HashMap hm = new HashMap();
					String sRelationName = rs.getString("RelationName"); if(sRelationName == null) sRelationName = "";//���������
					String sSex = rs.getString("Sex"); if(sSex == null) sSex = "";//�Ա�
					String sRelationship = rs.getString("Relationship"); if(sRelationship == null) sRelationship = "";//֤�����
					String sRelationTel = rs.getString("RelationTel"); if(sRelationTel == null) sRelationTel = "";//������ϵ
					String sRelationFixTel = rs.getString("RelationFixTel") ; if(sRelationFixTel == null) sRelationFixTel = "";//�绰����
					String sFamilyadd = rs.getString("Familyadd"); if(sFamilyadd == null)  sFamilyadd = "";//��ַ
					String sWorkUnit = rs.getString("WorkUnit"); if(sWorkUnit == null) sWorkUnit = "";//��ע
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
