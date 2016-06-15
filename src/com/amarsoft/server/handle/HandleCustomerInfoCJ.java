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
 * @describe 该方法用来处理客户基本信息
 * @author jxsun 2016年3月17日16:03:12
 *
 */
public class HandleCustomerInfoCJ {
	private Logger logger = Logger.getLogger(HandleCustomerInfoCJ.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String  sSql ="";
	private String sUserID ="";
	private String sOrgID ="";
	private SQLHandleInfo handleSql ;
	
	private String sCustomerID ="";//客户编号
	private String sCustomerName ="";//姓名
	private String sCustomerPhoneno ="";//手机号
	private String sCustomerPassword ="";//服务密码
	private String sReservePhoneno ="";//银行预留手机号
	private String sCertType ="";//证件类型
	private String sCertID ="";//身份证号
	private String sBirthDay ="";//生日
	private String sSex ="";//性别
	private String sIdentity = "";//身份
	private String sResidence ="";//户口性质
	private String sPaybackAccount ="";//还款账号
	private String sEcomMerceNo ="";//电商号
	private String sCompanyName ="";//单位名称
	private String sUnitIndustry ="";//单位行业
	private String sNearbyBuilding1 ="";//附近建筑
	private String sDepartMent ="";//任职部门
	private String sJob ="";//职务
	private String sCompanyPhone ="";//单位电话
	private String sCompanyPhoneSub ="";//电话分机
	private String sCompanyProperty ="";//单位性质
	private String sEstablishMentPeriod ="";//成立年限
	private String sUnitSize ="";//单位规模
	private String sHaveSocialSecurity ="";//是否购买了社保
	private String sWorkYear ="";//本单位工作多久
	private String sWorkLife ="";//工作年限
	private String sIncomePermonth ="";//月收入(税前)
	private String sNowaDdress ="";//现居地址
	private String sNearbyBuilding ="";//附近建筑
	private String sNowAddressTown ="";//区县镇（现居）
	private String sNowAddressStreet ="";//街道/村（现居）
	private String sNowAddressHouse ="";//小区/楼盘（现居）
	private String sNowAddressRoom ="";//栋/单元/房间号（现居）
	private String sResidentAddress ="";//户籍地址
	private String sResidentAddress1 ="";//户籍地址详细
	private String sCompanyAddress ="";//单位地址
	private String sCompanyAddress1 ="";//单位地址详细
	private String sEmailAddress ="";//账单邮寄地址
	private String sEmailAddress1 ="";//账单邮寄地址详细
	private String sHousingStatus ="";//住房状况
	private String sLiveYear ="";//本城市居住多久
	private String sEducationLevel ="";//教育程度
	private String sEducationType ="";//教育类型
	private String sGraduationSchool ="";//毕业院校
	private String sSchoolAddress ="";//学校地址
	private String sProfessional ="";//专业
	private String sEnrollmentYear ="";//入学年份
	private String sGraduationYear ="";//毕业年份
	private String sEmail ="";//电子邮箱
	private String sHouse ="";//房产
	private String sHouseAddress ="";//房产地址
	private String sHouseAddress1 ="";//房产地址详细
	private String sCar ="";//车辆
	private String sCarNo ="";//车牌号
	private String sMaritalStatus ="";//婚姻状况
	private String sSpouseName ="";//配偶姓名
	private String sSpouseCertid ="";//配偶身份证号
	private String sSpousePhoneNo ="";//配偶手机号
	private String sSpouseLiveAddress ="";//配偶现居住地址
	private String sSpouseCompanyName ="";//配偶单位名称
	private String sSpouseCompanyPhone ="";//配偶单位电话
	private String sSupportStatus ="";//供养状况
	private String sChildCount ="";//子女数目
	private String sName1 ="";//子女姓名
	private String sPhoneNo1 ="";//子女手机号码
	private String sFamilyMemberName ="";//家庭成员姓名
	private String sRelationShip ="";//与申请人关系
	private String sOtherName ="";//其他联系人姓名
	private String sOtherRelative ="";//与申请人关系
	private String sOtherPhoeNo ="";//其他联系人手机号码
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
	 * @describe 该方法用于处理客户基本信息模块
	 */
	public String handleCustomerInfoMessage() throws Exception{
		String sCustomerID ="";
		//获取hashMap中包含客户信息的所有值
		getCustotomerInfo();
		sCustomerID = isExistCustomer();
		if(sCustomerID !="" && sCustomerID != null){
			//更新Customer_info
			updateCustomerInfo();
			//判断当前用户的权限
			if(!isCurrentUser(sCustomerID)){
				//在Customer_Belong插入权限信息
				insertCustomerBelong(sCustomerID,"Y");
			}
			
		}else{
			//获取客户编号
			sCustomerID  = "CUR"+DBFunction.getSerialNo("CUSTOMER_INFO", "CUSTOMERID");//获取客户编号
			//在Customer_info中插入信息
			insertCustomerInfo(sCustomerID);
			//在Customer_Belong中插入权限信息
			insertCustomerBelong(sCustomerID,"N");
		}
		return sCustomerID;
	}
	
	/**
	 * @describe 判断当前客户是否有当前用户的权限
	 * @throws Exception
	 */
	private boolean isCurrentUser(String sCustomerID) throws Exception {
		ResultSet  rs = null;
		sSql ="select count(1) as CountNum from Customer_Belong ii where ii.CustomerID ='"+sCustomerID+"' and ii.OrgID ='"+sOrgID+"' and ii.UserID ='"+sUserID+"' ";
		try {
			logger.info("查询Customer_belong当前用户的权限是否存在 = "+sSql);
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				if(rs.getDouble("CountNum") != 0){
					return true;
				}
			}
			
		} catch (Exception e) {
			logger.info("---查询Customer_Belong是否存在出错---");
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
	 * @describe 更新Customer_info信息
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
		logger.info("更新Customer_Info的脚本为= "+sSql);
		//执行sSql
		try {
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			logger.info("---更新CustomerInfo出错---");
			e.printStackTrace();
			throw e;
		}
		sSql ="";
	}
	
	/**
	 * @describe 从hashMap中获取信息要素
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
			logger.info("获取数据异常");
			throw e;
		}
	}
	
	
	/**
	 * @throws Exception 
	 * @describe 判断是否存在该客户
	 * @return CustomerID
	 */
	public String  isExistCustomer() throws Exception{
		ResultSet  rs = null;
		String sCustomerID ="";
		sSql ="select CustomerID from customer_info ii where ii.certid ='"+sCertID+ "'";
		try {
			logger.info("查询customer_info = "+sSql);
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sCustomerID = rs.getString("CustomerID");
			}
			if(rs != null){
				rs.getStatement().close();
			}
		} catch (Exception e) {
			logger.info("---查询Customer_info出错---");
			e.printStackTrace();
			throw e;
		}
		return sCustomerID;
	}
	
	
	/**
	 * @describe 该方法用于在Customer_info中插入数据
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
            logger.info("向Customer_Info中添加信息脚本 = "+sSql);
            sqlQuery.execute(sSql);
            sSql ="";
		} catch (Exception e) {
			logger.info("插入Customer_info时出错");
			e.printStackTrace();
			throw e;
		}
	}

	
	/**
	 * @throws Exception 
	 * @describe 该方法用于在Customer_Belong中插入数据
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
		logger.info("向Customer_Belong中插入信息的脚本为 ="+sSql);
		try {
			sqlQuery.execute(sSql);
			sSql ="";
		} catch (Exception e) {
			logger.info("---向Customer_Bekong中插入数据报错---");
			e.printStackTrace();
			throw e;
		}
	}
}
