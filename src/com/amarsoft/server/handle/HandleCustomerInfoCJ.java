package com.amarsoft.server.handle;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.are.security.DESEncrypt;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.DataCryptUtils;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷�����������ͻ�������Ϣ
 * @author jxsun 2016��3��17��16:03:12
 *
 */
public class HandleCustomerInfoCJ {
	private Logger logger = Logger.getLogger(HandleCustomerInfoCJ.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String  sSql ="";
	private String sUserID ="";
	private String sOrgID ="";
	private SQLHandleInfo handleSql ;
	
	private String sCustomerID ="";//�ͻ����
	private String sCustomerName ="";//����
	private String sCustomerPhoneno ="";//�ֻ���
	private String sCustomerPassword ="";//��������
	private String sReservePhoneno ="";//����Ԥ���ֻ���
	private String sCertType ="";//֤������
	private String sCertID ="";//���֤��
	private String sBirthDay ="";//����
	private String sSex ="";//�Ա�
	private String sIdentity = "";//���
	private String sResidence ="";//��������
	private String sPaybackAccount ="";//�����˺�
	private String sEcomMerceNo ="";//���̺�
	private String sCompanyName ="";//��λ����
	private String sUnitIndustry ="";//��λ��ҵ
	private String sNearbyBuilding1 ="";//��������
	private String sDepartMent ="";//��ְ����
	private String sJob ="";//ְ��
	private String sCompanyPhone ="";//��λ�绰
	private String sCompanyPhoneSub ="";//�绰�ֻ�
	private String sCompanyProperty ="";//��λ����
	private String sEstablishMentPeriod ="";//��������
	private String sUnitSize ="";//��λ��ģ
	private String sHaveSocialSecurity ="";//�Ƿ������籣
	private String sWorkYear ="";//����λ�������
	private String sWorkLife ="";//��������
	private String sIncomePermonth ="";//������(˰ǰ)
	private String sNowaDdress ="";//�־ӵ�ַ
	private String sNearbyBuilding ="";//��������
	private String sNowAddressTown ="";//�������־ӣ�
	private String sNowAddressStreet ="";//�ֵ�/�壨�־ӣ�
	private String sNowAddressHouse ="";//С��/¥�̣��־ӣ�
	private String sNowAddressRoom ="";//��/��Ԫ/����ţ��־ӣ�
	private String sResidentAddress ="";//������ַ
	private String sResidentAddress1 ="";//������ַ��ϸ
	private String sCompanyAddress ="";//��λ��ַ
	private String sCompanyAddress1 ="";//��λ��ַ��ϸ
	private String sEmailAddress ="";//�˵��ʼĵ�ַ
	private String sEmailAddress1 ="";//�˵��ʼĵ�ַ��ϸ
	private String sHousingStatus ="";//ס��״��
	private String sLiveYear ="";//�����о�ס���
	private String sEducationLevel ="";//�����̶�
	private String sEducationType ="";//��������
	private String sGraduationSchool ="";//��ҵԺУ
	private String sSchoolAddress ="";//ѧУ��ַ
	private String sProfessional ="";//רҵ
	private String sEnrollmentYear ="";//��ѧ���
	private String sGraduationYear ="";//��ҵ���
	private String sEmail ="";//��������
	private String sHouse ="";//����
	private String sHouseAddress ="";//������ַ
	private String sHouseAddress1 ="";//������ַ��ϸ
	private String sCar ="";//����
	private String sCarNo ="";//���ƺ�
	private String sMaritalStatus ="";//����״��
	private String sSpouseName ="";//��ż����
	private String sSpouseCertid ="";//��ż���֤��
	private String sSpousePhoneNo ="";//��ż�ֻ���
	private String sSpouseLiveAddress ="";//��ż�־�ס��ַ
	private String sSpouseCompanyName ="";//��ż��λ����
	private String sSpouseCompanyPhone ="";//��ż��λ�绰
	private String sSupportStatus ="";//����״��
	private String sChildCount ="";//��Ů��Ŀ
	private String sName1 ="";//��Ů����
	private String sPhoneNo1 ="";//��Ů�ֻ�����
	private String sFamilyMemberName ="";//��ͥ��Ա����
	private String sRelationShip ="";//�������˹�ϵ
	private String sOtherName ="";//������ϵ������
	private String sOtherRelative ="";//�������˹�ϵ
	private String sOtherPhoeNo ="";//������ϵ���ֻ�����
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleCustomerInfoCJ(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String UserID,String OrgID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = UserID;
		this.sOrgID = OrgID;
	}
	/**
	 * @throws Exception 
	 * @describe �÷������ڴ���ͻ�������Ϣģ��
	 */
	public String handleCustomerInfoMessage() throws Exception{
		String sCustomerID ="";
		//��ȡhashMap�а����ͻ���Ϣ������ֵ
		getCustotomerInfo();
		sCustomerID = isExistCustomer();
		if(sCustomerID !="" && sCustomerID != null){
			//����Customer_info
			updateCustomerInfo();
			//�жϵ�ǰ�û���Ȩ��
			if(!isCurrentUser(sCustomerID)){
				//��Customer_Belong����Ȩ����Ϣ
				insertCustomerBelong(sCustomerID,"Y");
			}
			
		}else{
			//��ȡ�ͻ����
			sCustomerID  = "CUR"+DBFunction.getSerialNo("CUSTOMER_INFO", "CUSTOMERID");//��ȡ�ͻ����
			//��Customer_info�в�����Ϣ
			insertCustomerInfo(sCustomerID);
			//��Customer_Belong�в���Ȩ����Ϣ
			insertCustomerBelong(sCustomerID,"N");
		}
		return sCustomerID;
	}
	
	/**
	 * @describe �жϵ�ǰ�ͻ��Ƿ��е�ǰ�û���Ȩ��
	 * @throws Exception
	 */
	private boolean isCurrentUser(String sCustomerID) throws Exception {
		ResultSet  rs = null;
		sSql ="select count(1) as CountNum from Customer_Belong ii where ii.CustomerID ='"+sCustomerID+"' and ii.OrgID ='"+sOrgID+"' and ii.UserID ='"+sUserID+"' ";
		try {
			logger.info("��ѯCustomer_belong��ǰ�û���Ȩ���Ƿ���� = "+sSql);
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				if(rs.getDouble("CountNum") != 0){
					return true;
				}
			}
			
		} catch (Exception e) {
			logger.info("---��ѯCustomer_Belong�Ƿ���ڳ���---");
			e.printStackTrace();
			throw e;
		}finally{
			if(rs !=null){
				rs.getStatement().close();
			}
		}
		return false;
	}
	
	
	/**
	 * @describe ����Customer_info��Ϣ
	 * @throws Exception
	 */
	private void updateCustomerInfo() throws Exception{
		sSql ="update Customer_info set"
				+ " CustomerName=:CustomerName," + " CustomerPhoneno=:CustomerPhoneno,"
				+ " CustomerPassword=:CustomerPassword," + " ReservePhoneno=:ReservePhoneno,"
				+ " CertType=:CertType,"+ " CertID=:CertID,"
				+ " BirthDay=:BirthDay," + " Sex=:Sex,"
				+ " Identity=:Identity,"+ " Residence=:Residence,"
				+ " PaybackAccount=:PaybackAccount," + " EcomMerceNo=:EcomMerceNo,"
				+ " CompanyName=:CompanyName,"+ " UnitIndustry=:UnitIndustry,"
				+ " NearbyBuilding1=:NearbyBuilding1," + " DepartMent=:DepartMent,"
				+ " Job=:Job," + " CompanyPhone=:CompanyPhone," + " CompanyPhoneSub=:CompanyPhoneSub,"
				+ " CompanyProperty=:CompanyProperty," + " EstablishMentPeriod=:EstablishMentPeriod,"
				+ " UnitSize=:UnitSize," + " HaveSocialSecurity=:HaveSocialSecurity,"
				+ " WorkYear=:WorkYear," + " WorkLife=:WorkLife,"
				+ " IncomePermonth=:IncomePermonth,"
				+ " NowaDdress=:NowaDdress ," + " NearbyBuilding=:NearbyBuilding ,"
				+ " NowAddressTown=:NowAddressTown," + " NowAddressStreet=:NowAddressStreet,"
				+ " NowAddressHouse=:NowAddressHouse," + " NowAddressRoom=:NowAddressRoom,"
				+ " ResidentAddress=:ResidentAddress," + " ResidentAddress1=:ResidentAddress1,"
				+ " CompanyAddress=:CompanyAddress," + " CompanyAddress1=:CompanyAddress1,"
				+ " EmailAddress=:EmailAddress,"
				+ " EmailAddress1=:EmailAddress1," + " HousingStatus=:HousingStatus,"
				+ " LiveYear=:LiveYear," + " EducationLevel=:EducationLevel,"
				+ " EducationType=:EducationType,"+ " GraduationSchool=:GraduationSchool," + " SchoolAddress=:SchoolAddress,"
				+ " Professional=:Professional," + " EnrollmentYear=:EnrollmentYear,"
				+ " GraduationYear=:GraduationYear," + " Email=:Email,"
				+ " House=:House,"+ " HouseAddress=:HouseAddress,"+ " HouseAddress1=:HouseAddress1,"
				+ " Car=:Car, "+ " CarNo=:CarNo, "+ " MaritalStatus=:MaritalStatus, "
				+ " SpouseName=:SpouseName, "+ " SpouseCertid=:SpouseCertid, "
				+ " SpousePhoneNo=:SpousePhoneNo, "+ " SpouseLiveAddress=:SpouseLiveAddress, "
				+ " SpouseCompanyName=:SpouseCompanyName, "+ " SpouseCompanyPhone=:SpouseCompanyPhone, "
				+ " SupportStatus=:SupportStatus, "+ " ChildCount=:ChildCount, "
				+ " Name1=:Name1, "+ " PhoneNo1=:PhoneNo1, "
				+ " FamilyMemberName=:FamilyMemberName, "+ " RelationShip=:RelationShip, "
				+ " OtherName=:OtherName, "+ " OtherRelative=:OtherRelative, "
				+ " OtherPhoeNo=:OtherPhoeNo "
				+ " where CertID =:CertID ";
		handleSql= new SQLHandleInfo(sSql);
		handleSql.setParameter("CustomerName", sCustomerName).setParameter("CustomerPhoneno", sCustomerPhoneno)
		.setParameter("CustomerPassword", DESEncrypt.encrypt(sCustomerPassword, "JiMuHezi")).setParameter("ReservePhoneno", DESEncrypt.encrypt(sReservePhoneno, "JiMuHezi"))
		.setParameter("CertType", Tools.getCodeItemNo("CertType",Tools.getObjectToString(sCertType), sqlQuery)).setParameter("CertID", DESEncrypt.encrypt(sCertID, "JiMuHezi"))
		.setParameter("BirthDay", sBirthDay).setParameter("Sex",Tools.getCodeItemNo("Sex",Tools.getObjectToString(sSex), sqlQuery))
		.setParameter("Identity",Tools.getCodeItemNo("Identity",Tools.getObjectToString(sIdentity), sqlQuery))
		.setParameter("Residence", Tools.getCodeItemNo("Residence",Tools.getObjectToString(sResidence), sqlQuery))
		.setParameter("PaybackAccount", sPaybackAccount).setParameter("EcomMerceNo", sEcomMerceNo)
		.setParameter("CompanyName", sCompanyName).setParameter("UnitIndustry", sUnitIndustry)
		.setParameter("NearbyBuilding1", Tools.getCodeItemNo("NearbyBuilding",Tools.getObjectToString(sNearbyBuilding1), sqlQuery)).setParameter("DepartMent", sDepartMent)
		.setParameter("Job", sJob).setParameter("CompanyPhone", sCompanyPhone)
		.setParameter("CompanyPhoneSub", sCompanyPhoneSub).setParameter("CompanyProperty", Tools.getCodeItemNo("CompanyProperty",Tools.getObjectToString(sCompanyProperty), sqlQuery))
		.setParameter("EstablishMentPeriod", sEstablishMentPeriod).setParameter("UnitSize", sUnitSize)
		.setParameter("HaveSocialSecurity", Tools.getCodeItemNo("YesNo",Tools.getObjectToString(sHaveSocialSecurity), sqlQuery)).setParameter("WorkYear", Tools.getCodeItemNo("WorkYear",Tools.getObjectToString(sWorkYear), sqlQuery))
		.setParameter("WorkLife", sWorkLife).setParameter("IncomePermonth", sIncomePermonth)
		.setParameter("NowaDdress", sNowaDdress).setParameter("NearbyBuilding", Tools.getCodeItemNo("NearbyBuilding",Tools.getObjectToString(sNearbyBuilding), sqlQuery))
		.setParameter("NowAddressTown", sNowAddressTown).setParameter("NowAddressStreet", sNowAddressStreet)
		.setParameter("NowAddressHouse", sNowAddressHouse).setParameter("NowAddressRoom", sNowAddressRoom)
		.setParameter("ResidentAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sResidentAddress), sqlQuery)).setParameter("ResidentAddress1", sResidentAddress1)
		.setParameter("CompanyAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sCompanyAddress), sqlQuery)).setParameter("CompanyAddress1", sCompanyAddress1)
		.setParameter("EmailAddress", sEmailAddress).setParameter("EmailAddress1", sEmailAddress1)
		.setParameter("HousingStatus", sHousingStatus).setParameter("LiveYear", Tools.getCodeItemNo("WorkYear",Tools.getObjectToString(sLiveYear), sqlQuery))
		.setParameter("EducationLevel", Tools.getCodeItemNo("EducationLevel",Tools.getObjectToString(sEducationLevel), sqlQuery)).setParameter("EducationType", Tools.getCodeItemNo("EducationType",Tools.getObjectToString(sEducationType), sqlQuery))
		.setParameter("GraduationSchool", sGraduationSchool).setParameter("SchoolAddress", sSchoolAddress)
		.setParameter("Professional", sProfessional).setParameter("EnrollmentYear", sEnrollmentYear)
		.setParameter("GraduationYear", sGraduationYear).setParameter("Email", sEmail)
		.setParameter("House", sHouse).setParameter("HouseAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sHouseAddress), sqlQuery))
		.setParameter("HouseAddress1", sHouseAddress1).setParameter("Car", sCar)
		.setParameter("CarNo", sCarNo).setParameter("MaritalStatus", Tools.getCodeItemNo("MaritalStatus",Tools.getObjectToString(sMaritalStatus), sqlQuery))
		.setParameter("SpouseName", sSpouseName).setParameter("SpouseCertid", sSpouseCertid)
		.setParameter("SpousePhoneNo", sSpousePhoneNo).setParameter("SpouseLiveAddress", sSpouseLiveAddress)
		.setParameter("SpouseCompanyName", sSpouseCompanyName).setParameter("SpouseCompanyPhone", sSpouseCompanyPhone)
		.setParameter("SupportStatus", sSupportStatus).setParameter("ChildCount", sChildCount)
		.setParameter("Name1", sName1).setParameter("PhoneNo1", sPhoneNo1)
		.setParameter("FamilyMemberName", sFamilyMemberName).setParameter("RelationShip", Tools.getCodeItemNo("Relationship",Tools.getObjectToString(sRelationShip), sqlQuery))
		.setParameter("OtherName", sOtherName).setParameter("OtherRelative", Tools.getCodeItemNo("OtherRelative",Tools.getObjectToString(sOtherRelative), sqlQuery))
		.setParameter("OtherPhoeNo", sOtherPhoeNo).setParameter("CertID", sCertID);
		sSql = handleSql.getSql();
		logger.info("����Customer_Info�Ľű�Ϊ= "+sSql);
		//ִ��sSql
		try {
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			logger.info("---����CustomerInfo����---");
			e.printStackTrace();
			throw e;
		}
		sSql ="";
	}
	
	/**
	 * @describe ��hashMap�л�ȡ��ϢҪ��
	 * @throws Exception
	 */
	private void getCustotomerInfo() throws Exception {
		try {
			sCustomerID = (String) hashMap.get("CustomerID");
			sCustomerName = (String) hashMap.get("CustomerName");
			sCustomerPhoneno = (String) hashMap.get("CustomerPhoneno");
			sCustomerPassword = (String) hashMap.get("CustomerPassword");
			sReservePhoneno = (String) hashMap.get("ReservePhoneno");
			sCertType = (String) hashMap.get("CertType");
			sCertID = (String) hashMap.get("CertID");
			sBirthDay = (String) hashMap.get("BirthDay");
			sSex = (String) hashMap.get("Sex");
			sIdentity = (String) hashMap.get("Identity");
			sResidence = (String) hashMap.get("Residence");
			sPaybackAccount = (String) hashMap.get("PaybackAccount");
			sEcomMerceNo = (String) hashMap.get("EcomMerceNo");
			sCompanyName = (String) hashMap.get("CompanyName");
			sUnitIndustry = (String) hashMap.get("UnitIndustry");
			sNearbyBuilding1 = (String) hashMap.get("NearbyBuilding1");
			sDepartMent = (String) hashMap.get("DepartMent");
			sJob = (String) hashMap.get("Job");
			sCompanyPhone = (String) hashMap.get("CompanyPhone");
			sCompanyPhoneSub = (String) hashMap.get("CompanyPhoneSub");
			sCompanyProperty = (String) hashMap.get("CompanyProperty");
			sEstablishMentPeriod = (String) hashMap.get("EstablishMentPeriod");
			sUnitSize = (String) hashMap.get("UnitSize");
			sHaveSocialSecurity = (String) hashMap.get("HaveSocialSecurity");
			sWorkYear = (String) hashMap.get("WorkYear");
			sWorkLife = (String) hashMap.get("WorkLife");
			sIncomePermonth = (String) hashMap.get("IncomePermonth");
			sNowaDdress = (String) hashMap.get("NowaDdress");
			sNearbyBuilding = (String) hashMap.get("NearbyBuilding");
			sNowAddressTown = (String) hashMap.get("NowAddressTown");
			sNowAddressStreet = (String) hashMap.get("NowAddressStreet");
			sNowAddressHouse = (String) hashMap.get("NowAddressHouse");
			sNowAddressRoom = (String) hashMap.get("NowAddressRoom");
			sResidentAddress = (String) hashMap.get("ResidentAddress");
			sResidentAddress1 = (String) hashMap.get("ResidentAddress1");
			sCompanyAddress = (String) hashMap.get("CompanyAddress");
			sCompanyAddress1 = (String) hashMap.get("CompanyAddress1");
			sEmailAddress = (String) hashMap.get("EmailAddress");
			sEmailAddress1 = (String) hashMap.get("EmailAddress1");
			sHousingStatus = (String) hashMap.get("HousingStatus");
			sLiveYear = (String) hashMap.get("LiveYear");
			sEducationLevel = (String) hashMap.get("EducationLevel");
			sEducationType = (String) hashMap.get("EducationType");
			sGraduationSchool = (String) hashMap.get("GraduationSchool");
			sSchoolAddress = (String) hashMap.get("SchoolAddress");
			sProfessional = (String) hashMap.get("Professional");
			sEnrollmentYear = (String) hashMap.get("EnrollmentYear");
			sGraduationYear = (String) hashMap.get("GraduationYear");
			sEmail = (String) hashMap.get("Email");
			sHouse = (String) hashMap.get("House");
			sHouseAddress = (String) hashMap.get("HouseAddress");
			sHouseAddress1 = (String) hashMap.get("HouseAddress1");
			sCar = (String) hashMap.get("Car");
			sCarNo = (String) hashMap.get("CarNo");
			sMaritalStatus = (String) hashMap.get("MaritalStatus");
			sSpouseName = (String) hashMap.get("SpouseName");
			sSpouseCertid = (String) hashMap.get("SpouseCertid");
			sSpousePhoneNo = (String) hashMap.get("SpousePhoneNo");
			sSpouseLiveAddress = (String) hashMap.get("SpouseLiveAddress");
			sSpouseCompanyName = (String) hashMap.get("SpouseCompanyName");
			sSpouseCompanyPhone = (String) hashMap.get("SpouseCompanyPhone");
			sSupportStatus = (String) hashMap.get("SupportStatus");
			sChildCount = (String) hashMap.get("ChildCount");
			sName1 = (String) hashMap.get("Name1");
			sPhoneNo1 = (String) hashMap.get("PhoneNo1");
			sFamilyMemberName = (String) hashMap.get("FamilyMemberName");
			sRelationShip = (String) hashMap.get("RelationShip");
			sOtherName = (String) hashMap.get("OtherName");
			sOtherRelative = (String) hashMap.get("OtherRelative");
			sOtherPhoeNo = (String) hashMap.get("OtherPhoeNo");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("��ȡ�����쳣");
			throw e;
		}
	}
	
	
	/**
	 * @throws Exception 
	 * @describe �ж��Ƿ���ڸÿͻ�
	 * @return CustomerID
	 */
	public String  isExistCustomer() throws Exception{
		ResultSet  rs = null;
		String sCustomerID ="";
		sSql ="select CustomerID from customer_info ii where ii.certid ='"+sCertID+ "'";
		try {
			logger.info("��ѯcustomer_info = "+sSql);
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sCustomerID = rs.getString("CustomerID");
			}
			if(rs != null){
				rs.getStatement().close();
			}
		} catch (Exception e) {
			logger.info("---��ѯCustomer_info����---");
			e.printStackTrace();
			throw e;
		}
		return sCustomerID;
	}
	
	
	/**
	 * @describe �÷���������Customer_info�в�������
	 * @throws Exception 
	 */
	private void insertCustomerInfo(String customerID) throws Exception{
		try {
            sSql ="insert into Customer_Info (CustomerID,CustomerName,CustomerPhoneno,CustomerPassword,ReservePhoneno,CertType,CertID,BirthDay,Sex,Identity,Residence,PaybackAccount,EcomMerceNo,CompanyName,UnitIndustry,NearbyBuilding1,DepartMent,Job,CompanyPhone,CompanyPhoneSub,CompanyProperty,EstablishMentPeriod,UnitSize,HaveSocialSecurity,WorkYear,WorkLife,IncomePermonth,NowaDdress,NearbyBuilding,NowAddressTown,NowAddressStreet,NowAddressHouse,NowAddressRoom,ResidentAddress,ResidentAddress1,CompanyAddress,CompanyAddress1,EmailAddress,EmailAddress1,HousingStatus,LiveYear,EducationLevel,EducationType,GraduationSchool,SchoolAddress,Professional,EnrollmentYear,GraduationYear,Email,House,HouseAddress,HouseAddress1,Car,CarNo,MaritalStatus,SpouseName,SpouseCertid,SpousePhoneNo,SpouseLiveAddress,SpouseCompanyName,SpouseCompanyPhone,SupportStatus,ChildCount,Name1,PhoneNo1,FamilyMemberName,RelationShip,OtherName,OtherRelative,OtherPhoeNo)"
            		+" values(:CustomerID,:CustomerName,:CustomerPhoneno,:CustomerPassword,:ReservePhoneno,:CertType,:CertID,:BirthDay,:Sex,:Identity,:Residence,:PaybackAccount,:EcomMerceNo,:CompanyName,:UnitIndustry,:NearbyBuilding1,:DepartMent,:Job,:CompanyPhone,:CompanyPhoneSub,:CompanyProperty,:EstablishMentPeriod,:UnitSize,:HaveSocialSecurity,:WorkYear,:WorkLife,:IncomePermonth,:NowaDdress,:NearbyBuilding,:NowAddressTown,:NowAddressStreet,:NowAddressHouse,:NowAddressRoom,:ResidentAddress,:ResidentAddress1,:CompanyAddress,:CompanyAddress1,:EmailAddress,:EmailAddress1,:HousingStatus,:LiveYear,:EducationLevel,:EducationType,:GraduationSchool,:SchoolAddress,:Professional,:EnrollmentYear,:GraduationYear,:Email,:House,:HouseAddress,:HouseAddress1,:Car,:CarNo,:MaritalStatus,:SpouseName,:SpouseCertid,:SpousePhoneNo,:SpouseLiveAddress,:SpouseCompanyName,:SpouseCompanyPhone,:SupportStatus,:ChildCount,:Name1,:PhoneNo1,:FamilyMemberName,:RelationShip,:OtherName,:OtherRelative,:OtherPhoeNo)";
            handleSql = new SQLHandleInfo(sSql);
            handleSql.setParameter("CustomerID", customerID)
    		.setParameter("CustomerName", sCustomerName).setParameter("CustomerPhoneno", DESEncrypt.encrypt(sCustomerPhoneno, "JiMuHezi"))
    		.setParameter("CustomerPassword", sCustomerPassword).setParameter("ReservePhoneno", DESEncrypt.encrypt(sReservePhoneno, "JiMuHezi"))
    		.setParameter("CertType", Tools.getCodeItemNo("CertType",Tools.getObjectToString(sCertType), sqlQuery)).setParameter("CertID", DESEncrypt.encrypt(sCertID, "JiMuHezi"))
    		.setParameter("BirthDay", sBirthDay).setParameter("Sex",Tools.getCodeItemNo("Sex",Tools.getObjectToString(sSex), sqlQuery))
    		.setParameter("Identity",Tools.getCodeItemNo("Identity",Tools.getObjectToString(sIdentity), sqlQuery))
    		.setParameter("Residence", Tools.getCodeItemNo("Residence",Tools.getObjectToString(sResidence), sqlQuery))
    		.setParameter("PaybackAccount", DESEncrypt.encrypt(sPaybackAccount, "JiMuHezi")).setParameter("EcomMerceNo", sEcomMerceNo)
    		.setParameter("CompanyName", sCompanyName).setParameter("UnitIndustry", sUnitIndustry)
    		.setParameter("NearbyBuilding1", Tools.getCodeItemNo("NearbyBuilding",Tools.getObjectToString(sNearbyBuilding1), sqlQuery)).setParameter("DepartMent", sDepartMent)
    		.setParameter("Job", sJob).setParameter("CompanyPhone", sCompanyPhone)
    		.setParameter("CompanyPhoneSub", sCompanyPhoneSub).setParameter("CompanyProperty", Tools.getCodeItemNo("CompanyProperty",Tools.getObjectToString(sCompanyProperty), sqlQuery))
    		.setParameter("EstablishMentPeriod", sEstablishMentPeriod).setParameter("UnitSize", sUnitSize)
    		.setParameter("HaveSocialSecurity", Tools.getCodeItemNo("YesNo",Tools.getObjectToString(sHaveSocialSecurity), sqlQuery)).setParameter("WorkYear", Tools.getCodeItemNo("WorkYear",Tools.getObjectToString(sWorkYear), sqlQuery))
    		.setParameter("WorkLife", sWorkLife).setParameter("IncomePermonth", sIncomePermonth)
    		.setParameter("NowaDdress", sNowaDdress).setParameter("NearbyBuilding", Tools.getCodeItemNo("NearbyBuilding",Tools.getObjectToString(sNearbyBuilding), sqlQuery))
    		.setParameter("NowAddressTown", sNowAddressTown).setParameter("NowAddressStreet", sNowAddressStreet)
    		.setParameter("NowAddressHouse", sNowAddressHouse).setParameter("NowAddressRoom", sNowAddressRoom)
    		.setParameter("ResidentAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sResidentAddress), sqlQuery)).setParameter("ResidentAddress1", sResidentAddress1)
    		.setParameter("CompanyAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sCompanyAddress), sqlQuery)).setParameter("CompanyAddress1", sCompanyAddress1)
    		.setParameter("EmailAddress", sEmailAddress).setParameter("EmailAddress1", sEmailAddress1)
    		.setParameter("HousingStatus", sHousingStatus).setParameter("LiveYear", Tools.getCodeItemNo("WorkYear",Tools.getObjectToString(sLiveYear), sqlQuery))
    		.setParameter("EducationLevel", Tools.getCodeItemNo("EducationLevel",Tools.getObjectToString(sEducationLevel), sqlQuery)).setParameter("EducationType", Tools.getCodeItemNo("EducationType",Tools.getObjectToString(sEducationType), sqlQuery))
    		.setParameter("GraduationSchool", sGraduationSchool).setParameter("SchoolAddress", sSchoolAddress)
    		.setParameter("Professional", sProfessional).setParameter("EnrollmentYear", sEnrollmentYear)
    		.setParameter("GraduationYear", sGraduationYear).setParameter("Email", sEmail)
    		.setParameter("House", sHouse).setParameter("HouseAddress", Tools.getCodeItemNo("CustomerAddress",Tools.getObjectToString(sHouseAddress), sqlQuery))
    		.setParameter("HouseAddress1", sHouseAddress1).setParameter("Car", sCar)
    		.setParameter("CarNo", sCarNo).setParameter("MaritalStatus", Tools.getCodeItemNo("MaritalStatus",Tools.getObjectToString(sMaritalStatus), sqlQuery))
    		.setParameter("SpouseName", sSpouseName).setParameter("SpouseCertid", sSpouseCertid)
    		.setParameter("SpousePhoneNo", sSpousePhoneNo).setParameter("SpouseLiveAddress", sSpouseLiveAddress)
    		.setParameter("SpouseCompanyName", sSpouseCompanyName).setParameter("SpouseCompanyPhone", sSpouseCompanyPhone)
    		.setParameter("SupportStatus", sSupportStatus).setParameter("ChildCount", sChildCount)
    		.setParameter("Name1", sName1).setParameter("PhoneNo1", sPhoneNo1)
    		.setParameter("FamilyMemberName", sFamilyMemberName).setParameter("RelationShip", Tools.getCodeItemNo("Relationship",Tools.getObjectToString(sRelationShip), sqlQuery))
    		.setParameter("OtherName", sOtherName).setParameter("OtherRelative", Tools.getCodeItemNo("OtherRelative",Tools.getObjectToString(sOtherRelative), sqlQuery))
    		.setParameter("OtherPhoeNo", sOtherPhoeNo);
            sSql=handleSql.getSql();
            logger.info("��Customer_Info�������Ϣ�ű� = "+sSql);
            sqlQuery.execute(sSql);
            sSql ="";
		} catch (Exception e) {
			logger.info("����Customer_infoʱ����");
			e.printStackTrace();
			throw e;
		}
	}

	
	/**
	 * @throws Exception 
	 * @describe �÷���������Customer_Belong�в�������
	 */
	private void insertCustomerBelong(String CustomerID,String Flag) throws Exception{
		sSql=" insert into Customer_Belong (CustomerID,OrgID,UserID,Belongattribute,Belongattribute1,Belongattribute2,Belongattribute3,Belongattribute4,Inputuserid,Inputorgid,Inputdate) "
       +" values(:CustomerID,:OrgID,:UserID,:Belongattribute,:Belongattribute1,:Belongattribute2,:Belongattribute3,:Belongattribute4,:Inputuserid,:Inputorgid,:Inputdate) ";
		handleSql = new SQLHandleInfo(sSql);
		if(!("Y").equals(Flag)){
			handleSql.setParameter("CustomerID", CustomerID)
	         .setParameter("OrgID", sOrgID)
	         .setParameter("UserID", sUserID)
	         .setParameter("Belongattribute", "1")
	         .setParameter("Belongattribute1", "1")
	         .setParameter("Belongattribute2", "1")
	         .setParameter("Belongattribute3", "1")
	         .setParameter("Belongattribute4", "1")
	         .setParameter("Inputuserid", sUserID)
	         .setParameter("Inputorgid", sOrgID)
	         .setParameter("Inputdate", Tools.getToday(2));
		}
		
		sSql = handleSql.getSql();
		logger.info("��Customer_Belong�в�����Ϣ�Ľű�Ϊ ="+sSql);
		try {
			sqlQuery.execute(sSql);
			sSql ="";
		} catch (Exception e) {
			logger.info("---��Customer_Bekong�в������ݱ���---");
			e.printStackTrace();
			throw e;
		}
	}
}
