package com.amarsoft.server.handle;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.DataCryptUtils;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷�����������ͻ�������Ϣ
 * @author yhwang 20150917
 *
 */
public class HandleCustomerInfo {
	private Logger logger = Logger.getLogger(HandleCustomerInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String  sSql ="";
	private String sUserID ="";
	private String sOrgID ="";
	private SQLHandleInfo handleSql ;
	
	private String sCustomerName ="";//�ͻ�����
	private String sCertID ="";//֤������
	private String sSex ="";//�Ա�
	private String sBirthday ="";//��������
	private String sMarriage ="";//����״��
	private String sEduexperience ="";//���ѧ��
	private String sBankName ="";//��������
	private String sBankCarID ="";//�������п���
	private String sFamilystatus ="";//��ס״��
	private double dMonthrent =0.0;//�����/��
	private int iSupportpeopleno =0;//�����˿�
	private String sSubordinatecar ="";//�Ƿ�����������
	private String sFamilyAdd ="";//��ס��ַ����
	private String sFamilyadddetails ="";//��ס��ַ��ϸ��Ϣ
	private String sPone ="";//�ֻ�����
	private String sFamilyTel ="";//סլ�绰
	private String sEmailAdd ="";//��������
	private String QQ ="";//QQ
	private String WeiXin ="";
	private String sStartDate ="";//��ʼ��סʱ��
	private String sCommAdd ="";//�˵��ʼĵ�ַ
	private String sOtherAddress ="";//������ַ
	private String sLocalDate ="";//������������ʱ��
	private String sCompanyName ="";//������λ
	private String sCompanyNature ="";//��λ����
	private String sWorkAdd ="";//��λ��ַ����
	private String sWorkAddDetails ="";//��λ��ַ��ϸ��Ϣ
	private String sDepartment ="";//���ڲ���
	private String sUnitKind ="";//��λ������ҵ
	private String sHeadShip ="";//ְ��
	private String sPosition ="";//ְ��
	private String sMainbuziness ="";//��Ӫҵ��
	private String sCompanyTel ="";//��λ����
	private String sWorktel1 ="";//�ֻ�
	private String sWorkBeginDate ="";//����λ������ʼʱ��
	private String sPayDayWay ="";//��н��ʽ
	private String sSecuritytype ="";//�籣��ʽ
	private String sSelfmonthincome ="";//ÿ��н��
	private int iWorkYear =0;//�ܹ���
	private String sHouseType ="";//��������
	private int iHouseCount =0;//��������
	private String sAnjxcount1 ="";//���а�������
	private String sPurchasedate1 ="";//��������
	private double dBuildprice1 =0.0;//�����
	private double dRealtyarea1 =0.0;//���
	private String sRealtyadd ="";//����ͬ��ַ
	private String sHouseaddress ="";//����ͬ����סַ
	private int iVehiclecount =0;//��������
	private int iAnjxcount2 =0;//���а�������
	private String  sPurchasedate2 ="";//��������
	private String sVehiclesituation ="";//����״��
	private String sVehiclebrand ="";//����
	private String sVehiclelicense ="";//����
	//add by yhwang 20151121
	private String sPaydayDate ="";//ÿ�·�н��  
	private String sLastWorkName ="";//ǰ������λ����
	private String sLastStartDate ="";//ǰ������λ��ʼʱ��
	private String sLastPosition ="";//ǰ����ְ��
			
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleCustomerInfo(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sJMID,String UserID,String OrgID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sJMID = sJMID;
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
			//����ind_info
			updateIndInfo();
			//�жϵ�ǰ�û���Ȩ��
			if(!isCurrentUser(sCustomerID)){
				//��Customer_Belong����Ȩ����Ϣ
				insertCustomerBelong(sCustomerID,"Y");
			}
			
		}else{
			//��ȡ�ͻ����
			sCustomerID  = DBFunction.getSerialNo("Customer_Info", "CustomerID");//��ȡ�ͻ����
			//��Customer_info�в�����Ϣ
			insertCustomerInfo(sCustomerID);
			//��Ind_info�в�����Ϣ
			insertIndInfo(sCustomerID);
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
				if(rs.getString("CountNum") != "0"){
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
	 * @describe ����ind_info��Ϣ
	 * @throws Exception
	 */
	private void updateIndInfo() throws Exception {
		sSql = " update ind_info set " + " UpdateDate=:UpdateDate,"
				+ " FullName=:FullName," + " Sex=:Sex,"
				+ " Birthday=:Birthday," + " Marriage=:Marriage,"
				+ " Eduexperience=:Eduexperience,"
				+ " PayAccountBank=:PayAccountBank,"
				+ " PayAccount=:PayAccount," + " Familystatus=:Familystatus,"
				+ " Monthrent=:Monthrent,"
				+ " Supportpeopleno=:Supportpeopleno,"
				+ " Subordinatecar=:Subordinatecar," + " FamilyAdd=:FamilyAdd,"
				+ " Familyadddetails=:Familyadddetails,"
				+ " MobileTelephone=:MobileTelephone,"
				+ " FamilyTel=:FamilyTel," + " EmailAdd=:EmailAdd,"
				+ " Qq=:Qq," + " Weixin=:Weixin," + " Startdate=:Startdate,"
				+ " CommAdd=:CommAdd," + " OtherAddress=:OtherAddress,"
				+ " Localdate=:Localdate," + " WorkCorp=:WorkCorp,"
				+ " UnitNature=:UnitNature," + " WorkAdd=:WorkAdd,"
				+ " Workadddetails=:Workadddetails,"
				+ " Department=:Department ," + " UnitKind=:UnitKind ,"
				+ " HeadShip=:HeadShip," + " Position=:Position,"
				+ " Mainbuziness=:Mainbuziness," + " WorkTel=:WorkTel,"
				+ " WorkTel1=:WorkTel1," + " WorkBeginDate=:WorkBeginDate,"
				+ " Paydayway=:Paydayway," + " Securitytype=:Securitytype,"
				+ " Selfmonthincome=:Selfmonthincome,"
				+ " TotalWorkAge=:TotalWorkAge," + " Housetype=:Housetype,"
				+ " Housecount=:Housecount," + " Anjxcount1=:Anjxcount1,"
				+ " Purchasedate1=:Purchasedate1,"
				+ " Buildprice1=:Buildprice1," + " Realtyarea1=:Realtyarea1,"
				+ " Realtyadd=:Realtyadd," + " Houseaddress=:Houseaddress,"
				+ " Vehiclecount=:Vehiclecount," + " Anjxcount2=:Anjxcount2,"
				+ " Purchasedate2=:Purchasedate2,"
				+ " Vehiclesituation=:Vehiclesituation,"
				+ " Vehiclebrand=:Vehiclebrand,"
				+ " Vehiclelicense=:Vehiclelicense, "
				//add by yhwang 20151123
				+ " PayDayDate=:PayDayDate, "
				+ " LastWorkName=:LastWorkName, "
				+ " LastStartDate=:LastStartDate, "
				+ " Position1=:Position1 "
				+ " where CertID =:CertID and CertType ='Ind01' ";
		handleSql = new SQLHandleInfo(sSql);
		handleSql
				.setParameter("UpdateDate", Tools.getToday(2))
				.setParameter("FullName", sCustomerName)
				.setParameter(
						"Sex",
						Tools.getCodeItemNo("Sex",
								Tools.getObjectToString(sSex), sqlQuery))
				.setParameter("Birthday", sBirthday)
				.setParameter(
						"Marriage",
						Tools.getCodeItemNo("Marriage",
								Tools.getObjectToString(sMarriage), sqlQuery))
				.setParameter(
						"Eduexperience",
						Tools.getCodeItemNo("Educ",
								Tools.getObjectToString(sEduexperience),
								sqlQuery))
				.setParameter("PayAccountBank", sBankName)
				.setParameter("PayAccount", sBankCarID)
				.setParameter(
						"Familystatus",
						Tools.getCodeItemNo("FamilyState1",
								Tools.getObjectToString(sFamilystatus),
								sqlQuery))
				.setParameter("Monthrent", dMonthrent)
				.setParameter("Supportpeopleno", iSupportpeopleno)
				.setParameter(
						"Subordinatecar",
						Tools.getCodeItemNo("YesNo",
								Tools.getObjectToString(sSubordinatecar),
								sqlQuery))
				.setParameter("FamilyAdd", sFamilyAdd)
				.setParameter("Familyadddetails", sFamilyadddetails)
				.setParameter("MobileTelephone", sPone)
				.setParameter("FamilyTel", sFamilyTel)
				.setParameter("EmailAdd", sEmailAdd)
				.setParameter("Qq", QQ)
				.setParameter("Weixin", WeiXin)
				.setParameter("Startdate", sStartDate)
				.setParameter("CommAdd",sCommAdd)
				.setParameter("OtherAddress", sOtherAddress)
				.setParameter("Localdate", sLocalDate)
				.setParameter("WorkCorp", sCompanyName)
				.setParameter(
						"UnitNature",
						Tools.getCodeItemNo("UNITNATURE",
								Tools.getObjectToString(sCompanyNature),
								sqlQuery))
				.setParameter("WorkAdd", sWorkAdd)
				.setParameter("Workadddetails", sWorkAddDetails)
				.setParameter("Department", sDepartment)
				.setParameter("UnitKind", sUnitKind)
				.setParameter(
						"HeadShip",getCodeItemNo("HeadShip",Tools.getObjectToString(sHeadShip),"2", sqlQuery))
				.setParameter("Position", sPosition)
				.setParameter("Mainbuziness", sMainbuziness)
				.setParameter("WorkTel", sCompanyTel)
				.setParameter("WorkTel1", sWorktel1)
				.setParameter("WorkBeginDate", sWorkBeginDate)
				.setParameter(
						"Paydayway",
						Tools.getCodeItemNo("PayWay",
								Tools.getObjectToString(sPayDayWay), sqlQuery))
				.setParameter(
						"Securitytype",
						Tools.getCodeItemNo("SECURITYTYPE",
								Tools.getObjectToString(sSecuritytype),
								sqlQuery))
				.setParameter("Selfmonthincome", sSelfmonthincome)
				.setParameter("TotalWorkAge", iWorkYear)
				.setParameter(
						"Housetype",
						Tools.getCodeItemNo("FamilyState2",
								Tools.getObjectToString(sHouseType), sqlQuery))
				.setParameter("Housecount", iHouseCount)
				.setParameter("Anjxcount1", sAnjxcount1)
				.setParameter("Purchasedate1", sPurchasedate1)
				.setParameter("Buildprice1", dBuildprice1)
				.setParameter("Realtyarea1", dRealtyarea1)
				.setParameter(
						"Realtyadd",
						Tools.getCodeItemNo("Y/N",
								Tools.getObjectToString(sRealtyadd), sqlQuery))
				.setParameter("Houseaddress", sHouseaddress)
				.setParameter("Vehiclecount", iVehiclecount)
				.setParameter("Anjxcount2", iAnjxcount2)
				.setParameter("Purchasedate2", sPurchasedate2)
				.setParameter(
						"Vehiclesituation",
						Tools.getCodeItemNo("BuyState",
								Tools.getObjectToString(sVehiclesituation),
								sqlQuery))
				.setParameter("Vehiclebrand", sVehiclebrand)
				.setParameter("Vehiclelicense", sVehiclelicense)
				.setParameter("CertID", sCertID)
				//add by yhwang 20151123
				.setParameter("PayDayDate",sPaydayDate)
				.setParameter("LastWorkName",sLastWorkName)
				.setParameter("LastStartDate",sLastStartDate)
				.setParameter("Position1",sLastPosition);
		
		sSql = handleSql.getSql();
		logger.info("---����ind_info����Ϣ�ű�---" + sSql);
		try {
			sqlQuery.execute(sSql);
			sSql = "";
		} catch (Exception e) {
			logger.info("---����ind_infoʱ����---");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @describe ����Customer_info��Ϣ
	 * @throws Exception
	 */
	private void updateCustomerInfo() throws Exception{
		sSql ="update Customer_info ci set  ci.customername =:CustomerName ,ci.status ='01' "
		+" where ci.certid =:CertID and (ci.certtype ='Ind01' or ci.certtype ='Ind08')" ;
		handleSql= new SQLHandleInfo(sSql);
		handleSql.setParameter("CustomerName", sCustomerName)
		.setParameter("CertID", sCertID);
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
			sCustomerName = (String) hashMap.get("ChineseName");
			sCertID = DataCryptUtils.encrypt((String) hashMap.get("IdentityNumber"));
			sSex = (String) hashMap.get("Sex");
			sBirthday = (String) hashMap.get("Birthday");
			sMarriage = (String) hashMap.get("Marriage");
			sEduexperience = (String) hashMap.get("Eduexperience");
			sBankName = (String) hashMap.get("BankName");
			sBankCarID = (String) hashMap.get("BankCarID");
			sFamilystatus = (String) hashMap.get("Familystatus");
			dMonthrent = (Tools.isDoubleNull(hashMap.get("Monthrent")))?0.0:Double.parseDouble(hashMap.get("Monthrent")+"");
			iSupportpeopleno = (Tools.isDoubleNull(hashMap.get("Supportpeopleno")))?0:Integer.parseInt(hashMap.get("Supportpeopleno")+"");
			sSubordinatecar = (String) hashMap.get("Subordinatecar");
			sFamilyAdd = (String) hashMap.get("FamilyAdd");
			sFamilyadddetails = DataCryptUtils.encrypt((String) hashMap.get("Familyadddetails"));
			sPone = DataCryptUtils.encrypt((String) hashMap.get("Phone"));
			sFamilyTel = DataCryptUtils.encrypt((String) hashMap.get("FamilyTel"));
			sEmailAdd = DataCryptUtils.encrypt((String) hashMap.get("EmailAdd"));
			QQ = (String) hashMap.get("Qq");
			WeiXin = (String) hashMap.get("Weixin");
			sStartDate = (String) hashMap.get("Startdate");
			sCommAdd = DataCryptUtils.encrypt(Tools.getCodeItemNo("MailAddress", (String) hashMap.get("CommAdd"), sqlQuery));
			sOtherAddress = (String) hashMap.get("OtherAddress");
			sLocalDate = (String) hashMap.get("Localdate");
			sCompanyName = (String) hashMap.get("CompanyName");
			sCompanyNature = (String) hashMap.get("CompanyNature");
			sWorkAdd = (String) hashMap.get("WorkAdd");
			sWorkAddDetails = (String) hashMap.get("Workadddetails");
			sDepartment = (String) hashMap.get("Department");
			sUnitKind = (String) hashMap.get("UnitKind");
			sHeadShip = (String) hashMap.get("HeadShip");
			sPosition = (String) hashMap.get("Position");
			sMainbuziness = (String) hashMap.get("Mainbuziness");
			sCompanyTel = (String) hashMap.get("CompanyTel");
			sWorktel1 = (String) hashMap.get("WorkTel1");
			sWorkBeginDate = (String) hashMap.get("WorkBeginDate");
			sPayDayWay = (String) hashMap.get("Paydayway");
			sSecuritytype = (String) hashMap.get("Securitytype");
			sSelfmonthincome = hashMap.get("Selfmonthincome")+"";
			iWorkYear = (Tools.isDoubleNull(hashMap.get("WorkYears")))?0:Integer.parseInt( hashMap.get("WorkYears")+"");
			
			sHouseType = (String) hashMap.get("Housetype");
			iHouseCount = (Tools.isDoubleNull(hashMap.get("Housecount")))?0:Integer.parseInt( hashMap.get("Housecount")+"");
			sAnjxcount1 = hashMap.get("Anjxcount1")+"";
			sPurchasedate1 = (String) hashMap.get("Purchasedate1");
			dBuildprice1 = (Tools.isDoubleNull(hashMap.get("Buildprice1")))?0.0:Double.parseDouble(hashMap.get("Buildprice1")+"");
			dRealtyarea1 = (Tools.isDoubleNull(hashMap.get("Realtyarea1")))?0.0:Double.parseDouble(hashMap.get("Realtyarea1")+"");
			sRealtyadd =  hashMap.get("Realtyadd")+"";
			sHouseaddress = (String) hashMap.get("Houseaddress");
			iVehiclecount = (Tools.isDoubleNull(hashMap.get("Vehiclecount")))?0:Integer.parseInt( hashMap.get("Vehiclecount")+"");
			iAnjxcount2 = (Tools.isDoubleNull(hashMap.get("Anjxcount2")))?0:Integer.parseInt( hashMap.get("Anjxcount2")+"");
			sPurchasedate2 = (String) hashMap.get("Purchasedate2");
			sVehiclesituation = (String) hashMap.get("Vehiclesituation");
			sVehiclebrand = (String) hashMap.get("Vehiclebrand");
			sVehiclelicense = (String) hashMap.get("Vehiclelicense");
			
			//add by yhwang 20151123
		    sPaydayDate = (String) hashMap.get("PaydayDate");//ÿ�·�н��  
			sLastWorkName = (String) hashMap.get("LastWorkName");//ǰ������λ����
			sLastStartDate = (String) hashMap.get("LastStartDate");//ǰ������λ��ʼʱ��
			sLastPosition = (String) hashMap.get("LastPosition");//ǰ����ְ��
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
		sSql ="select CustomerType,CustomerID from customer_info ii where ii.certid ='"+sCertID+"' and (ii.certtype ='Ind01' or ii.certtype ='Ind08') ";
		String sCustomerID ="";
		String sCustomerType ="";
		try {
			logger.info("��ѯcustomer_info = "+sSql);
			rs = sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sCustomerType = rs.getString("CustomerType");
				sCustomerID = rs.getString("CustomerID");
				if(!("0395").equals(sCustomerType)){
					updateCustomerType();
				}
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
	 * @throws Exception 
	 * @describe �÷������ڸ���customer_info�еĿͻ�����
	 */
	private void updateCustomerType() throws Exception {
		sSql ="update customer_info ii set CustomerType='0310' where ii.certid ='"+sCertID+"' and (ii.certtype ='Ind01' or ii.certtype ='Ind08') ";
		sqlQuery.execute(sSql);
		sSql = "";
	}
	
	/**
	 * @describe �÷���������Customer_info�в�������
	 * @throws Exception 
	 */
	private void insertCustomerInfo(String customerID) throws Exception{
		try {
            sSql ="insert into Customer_Info (CustomerID,CustomerName,CustomerType,CertType,CertID,InputOrgID,InputUserID,InputDate,Status,NationCode)"
            		+" values(:CustomerID,:CustomerName,:CustomerType,:CertType,:CertID,:InputOrgID,:InputUserID,:InputDate,:Status,:NationCode)";
            handleSql = new SQLHandleInfo(sSql);
            handleSql.setParameter("CustomerID", customerID)
                     .setParameter("CustomerName", sCustomerName)
                     .setParameter("CustomerType","0395")
                     .setParameter("CertType","Ind01")
                     .setParameter("CertID",sCertID)
                     .setParameter("InputOrgID",sOrgID)
                     .setParameter("InputUserID",sUserID)
                     .setParameter("InputDate",Tools.getToday(2))
                     .setParameter("Status", "01")
                     .setParameter("NationCode", "CHN");
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
	 * @describe �÷���������ind_info�в�������
	 * @return SerialNo
	 * @throws Exception 
	 */
	private void insertIndInfo(String CustomerID) throws Exception{
		try {
			sSql=" insert into ind_info (CustomerID,InputOrgID,InputUserID,InputDate,TempSaveFlag,FullName,"
			+" CertID,CertType,Sex,Birthday,Marriage,Eduexperience,PayAccountBank,PayAccount,Familystatus,Monthrent,"
			+" Supportpeopleno,Subordinatecar,FamilyAdd,Familyadddetails,MobileTelephone,FamilyTel,EmailAdd,Qq,Weixin,Startdate,CommAdd,OtherAddress,Localdate,WorkCorp,"
			+" UnitNature,WorkAdd,Workadddetails,Department,UnitKind,HeadShip,Position,Mainbuziness,WorkTel,WorkTel1,WorkBeginDate,Paydayway,Securitytype,Selfmonthincome,"
			+" TotalWorkAge,Housetype,Housecount,Anjxcount1,Purchasedate1,Buildprice1,Realtyarea1,Realtyadd,Houseaddress,Vehiclecount,Anjxcount2,Purchasedate2,Vehiclesituation,"
			+" Vehiclebrand,Vehiclelicense,PayDayDate,LastWorkName,LastStartDate,Position1) values (:CustomerID,:InputOrgID,:InputUserID,:InputDate,:TempSaveFlag,:FullName,:CertID,:CertType,:Sex,:Birthday,:Marriage,:Eduexperience,:PayAccountBank,:PayAccount,:Familystatus,:Monthrent,"
			+" :Supportpeopleno,:Subordinatecar,:FamilyAdd,:Familyadddetails,:MobileTelephone,:FamilyTel,:EmailAdd,:Qq,:Weixin,:Startdate,:CommAdd,:OtherAddress,:Localdate,:WorkCorp,"
			+" :UnitNature,:WorkAdd,:Workadddetails,:Department,:UnitKind,:HeadShip,:Position,:Mainbuziness,:WorkTel,:WorksTel,:WorkBeginDate,:Paydayway,:Securitytype,:Selfmonthincome,"
			+" :TotalWorkAge,:Housetype,:Housecount,:Anjxcount1,:Purchasedate1,:Buildprice1,:Realtyarea1,:Realtyadd,:Houseaddress,:Vehiclecount,:Anjxcount2,:Purchasedate2,:Vehiclesituation,"
			+" :Vehiclebrand,:Vehiclelicense,:PayDayDate,:LastWorkName,:LastStartDate,:Position1)";
			handleSql = new SQLHandleInfo(sSql);
			handleSql.setParameter("CustomerID",CustomerID)
			.setParameter("InputOrgID",sOrgID)
			.setParameter("InputUserID",sUserID)
			.setParameter("InputDate",Tools.getToday(2))
			.setParameter("TempSaveFlag","2")
			.setParameter("FullName",sCustomerName)
			.setParameter("CertID",sCertID)
			.setParameter("CertType","Ind01")
			.setParameter("Sex",Tools.getCodeItemNo("Sex", Tools.getObjectToString(sSex), sqlQuery))
			.setParameter("Birthday",sBirthday)
			.setParameter("Marriage",Tools.getCodeItemNo("Marriage", Tools.getObjectToString(sMarriage), sqlQuery))
			.setParameter("Eduexperience",Tools.getCodeItemNo("Educ", Tools.getObjectToString(sEduexperience), sqlQuery))
			.setParameter("PayAccountBank",sBankName)
			.setParameter("PayAccount",sBankCarID)
			.setParameter("Familystatus",Tools.getCodeItemNo("FamilyState1", Tools.getObjectToString(sFamilystatus), sqlQuery))
			.setParameter("Monthrent",dMonthrent)
			.setParameter("Supportpeopleno",iSupportpeopleno)
			.setParameter("Subordinatecar",Tools.getCodeItemNo("YesNo", Tools.getObjectToString(sSubordinatecar), sqlQuery))
			.setParameter("FamilyAdd",sFamilyAdd)
			.setParameter("Familyadddetails",sFamilyadddetails)
			.setParameter("MobileTelephone",sPone)
			.setParameter("FamilyTel",sFamilyTel)
			.setParameter("EmailAdd",sEmailAdd)
			.setParameter("Qq",QQ)
			.setParameter("Weixin",WeiXin)
			.setParameter("Startdate",sStartDate)
			.setParameter("CommAdd",sCommAdd)
			.setParameter("OtherAddress",sOtherAddress)
			.setParameter("Localdate",sLocalDate)
			.setParameter("WorkCorp",sCompanyName)
			.setParameter("UnitNature",Tools.getCodeItemNo("UNITNATURE", Tools.getObjectToString(sCompanyNature), sqlQuery))
			.setParameter("WorkAdd",sWorkAdd)
			.setParameter("Workadddetails",sWorkAddDetails)
			.setParameter("Department",sDepartment)
			.setParameter("UnitKind",sUnitKind)
			.setParameter("HeadShip",getCodeItemNo("HeadShip",Tools.getObjectToString(sHeadShip),"2", sqlQuery))
			.setParameter("Position",sPosition)
			.setParameter("Mainbuziness",sMainbuziness)
			.setParameter("WorkTel",sCompanyTel)
			.setParameter("WorksTel",sWorktel1)
			.setParameter("WorkBeginDate",sWorkBeginDate)
			.setParameter("Paydayway",Tools.getCodeItemNo("PayWay", Tools.getObjectToString(sPayDayWay), sqlQuery))
			.setParameter("Securitytype",Tools.getCodeItemNo("SECURITYTYPE", Tools.getObjectToString(sSecuritytype), sqlQuery))
			.setParameter("Selfmonthincome",sSelfmonthincome)
			.setParameter("TotalWorkAge",iWorkYear)
			.setParameter("Housetype",Tools.getCodeItemNo("FamilyState2", Tools.getObjectToString(sHouseType), sqlQuery))
			.setParameter("Housecount",iHouseCount)
			.setParameter("Anjxcount1",sAnjxcount1)
			.setParameter("Purchasedate1",sPurchasedate1)
			.setParameter("Buildprice1",dBuildprice1)
			.setParameter("Realtyarea1",dRealtyarea1)
			.setParameter("Realtyadd",Tools.getCodeItemNo("Y/N", Tools.getObjectToString(sRealtyadd), sqlQuery))
			.setParameter("Houseaddress",sHouseaddress)
			.setParameter("Vehiclecount",iVehiclecount)
			.setParameter("Anjxcount2",iAnjxcount2)
			.setParameter("Purchasedate2",sPurchasedate2)
			.setParameter("Vehiclesituation",Tools.getCodeItemNo("BuyState", Tools.getObjectToString(sVehiclesituation), sqlQuery))
			.setParameter("Vehiclebrand",sVehiclebrand)
			.setParameter("Vehiclelicense",sVehiclelicense)
			//add by yhwang 20151123
			.setParameter("PayDayDate",sPaydayDate)
			.setParameter("LastWorkName",sLastWorkName)
			.setParameter("LastStartDate",sLastStartDate)
			.setParameter("Position1",sLastPosition);
			sSql = handleSql.getSql();
			logger.info("---��ind_info�������Ϣ�ű�---"+sSql);
			sqlQuery.execute(sSql);
			sSql ="";
		} catch (Exception e) {
			logger.info("---����ind_infoʱ����---");
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
		if(("Y").equals(Flag)){
			handleSql.setParameter("CustomerID", CustomerID)
	         .setParameter("OrgID", sOrgID)
	         .setParameter("UserID", sUserID)
	         .setParameter("Belongattribute", "0")
	         .setParameter("Belongattribute1", "1")
	         .setParameter("Belongattribute2", "0")
	         .setParameter("Belongattribute3", "0")
	         .setParameter("Belongattribute4", "0")
	         .setParameter("Inputuserid", sUserID)
	         .setParameter("Inputorgid", sOrgID)
	         .setParameter("Inputdate", Tools.getToday(2));
		}else{
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
	
	/**
	 * @describe �ķ������ڻ�ȡ����������Ϣ����
	 * @param sCodeNo
	 * @param sItemName
	 * @param sqlQuery
	 * @return
	 */
	public static String getCodeItemNo(String sCodeNo,String sItemName,String sIsInuse,SQLQuery sqlQuery){
		String sItemNo = "";
		String sSql = "select itemno from code_library where codeno = '"+sCodeNo+"' and trim(itemname) = '"+sItemName.trim()+"'  and isinuse='"+sIsInuse+"'";
		try {
			sItemNo = sqlQuery.getString(sSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sItemNo;
	}
}
