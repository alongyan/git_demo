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
 * @describe 该方法用来处理客户基本信息
 * @author yhwang 20150917
 *
 */
public class HandleCustomerInfo {
	private Logger logger = Logger.getLogger(HandleCustomerInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String  sSql ="";
	private String sUserID ="";
	private String sOrgID ="";
	private SQLHandleInfo handleSql ;
	
	private String sCustomerName ="";//客户名称
	private String sCertID ="";//证件类型
	private String sSex ="";//性别
	private String sBirthday ="";//出生日期
	private String sMarriage ="";//婚姻状况
	private String sEduexperience ="";//最高学历
	private String sBankName ="";//开户银行
	private String sBankCarID ="";//提现银行卡号
	private String sFamilystatus ="";//居住状况
	private double dMonthrent =0.0;//月租金/月
	private int iSupportpeopleno =0;//供养人口
	private String sSubordinatecar ="";//是否有所属车辆
	private String sFamilyAdd ="";//居住地址期限
	private String sFamilyadddetails ="";//居住地址详细信息
	private String sPone ="";//手机号码
	private String sFamilyTel ="";//住宅电话
	private String sEmailAdd ="";//电子邮箱
	private String QQ ="";//QQ
	private String WeiXin ="";
	private String sStartDate ="";//起始居住时间
	private String sCommAdd ="";//账单邮寄地址
	private String sOtherAddress ="";//其他地址
	private String sLocalDate ="";//申请人来本地时间
	private String sCompanyName ="";//工作单位
	private String sCompanyNature ="";//单位性质
	private String sWorkAdd ="";//单位地址区县
	private String sWorkAddDetails ="";//单位地址详细信息
	private String sDepartment ="";//所在部门
	private String sUnitKind ="";//单位所属行业
	private String sHeadShip ="";//职级
	private String sPosition ="";//职务
	private String sMainbuziness ="";//主营业务
	private String sCompanyTel ="";//单位座机
	private String sWorktel1 ="";//分机
	private String sWorkBeginDate ="";//本单位工作起始时间
	private String sPayDayWay ="";//发薪方式
	private String sSecuritytype ="";//社保方式
	private String sSelfmonthincome ="";//每月薪金
	private int iWorkYear =0;//总工龄
	private String sHouseType ="";//房产类型
	private int iHouseCount =0;//房产数量
	private String sAnjxcount1 ="";//其中按揭数量
	private String sPurchasedate1 ="";//购买日期
	private double dBuildprice1 =0.0;//购买价
	private double dRealtyarea1 =0.0;//面积
	private String sRealtyadd ="";//房产同地址
	private String sHouseaddress ="";//若不同房产住址
	private int iVehiclecount =0;//车辆数量
	private int iAnjxcount2 =0;//其中按揭数量
	private String  sPurchasedate2 ="";//购买日期
	private String sVehiclesituation ="";//购买状况
	private String sVehiclebrand ="";//车型
	private String sVehiclelicense ="";//车牌
	//add by yhwang 20151121
	private String sPaydayDate ="";//每月发薪日  
	private String sLastWorkName ="";//前工作单位名称
	private String sLastStartDate ="";//前工作单位起始时间
	private String sLastPosition ="";//前工作职务
			
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
			//更新ind_info
			updateIndInfo();
			//判断当前用户的权限
			if(!isCurrentUser(sCustomerID)){
				//在Customer_Belong插入权限信息
				insertCustomerBelong(sCustomerID,"Y");
			}
			
		}else{
			//获取客户编号
			sCustomerID  = DBFunction.getSerialNo("Customer_Info", "CustomerID");//获取客户编号
			//在Customer_info中插入信息
			insertCustomerInfo(sCustomerID);
			//在Ind_info中插入信息
			insertIndInfo(sCustomerID);
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
				if(rs.getString("CountNum") != "0"){
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
	 * @describe 更新ind_info信息
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
		logger.info("---更新ind_info中信息脚本---" + sSql);
		try {
			sqlQuery.execute(sSql);
			sSql = "";
		} catch (Exception e) {
			logger.info("---更新ind_info时出错---");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @describe 更新Customer_info信息
	 * @throws Exception
	 */
	private void updateCustomerInfo() throws Exception{
		sSql ="update Customer_info ci set  ci.customername =:CustomerName ,ci.status ='01' "
		+" where ci.certid =:CertID and (ci.certtype ='Ind01' or ci.certtype ='Ind08')" ;
		handleSql= new SQLHandleInfo(sSql);
		handleSql.setParameter("CustomerName", sCustomerName)
		.setParameter("CertID", sCertID);
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
		    sPaydayDate = (String) hashMap.get("PaydayDate");//每月发薪日  
			sLastWorkName = (String) hashMap.get("LastWorkName");//前工作单位名称
			sLastStartDate = (String) hashMap.get("LastStartDate");//前工作单位起始时间
			sLastPosition = (String) hashMap.get("LastPosition");//前工作职务
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
		sSql ="select CustomerType,CustomerID from customer_info ii where ii.certid ='"+sCertID+"' and (ii.certtype ='Ind01' or ii.certtype ='Ind08') ";
		String sCustomerID ="";
		String sCustomerType ="";
		try {
			logger.info("查询customer_info = "+sSql);
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
			logger.info("---查询Customer_info出错---");
			e.printStackTrace();
			throw e;
		}
		return sCustomerID;
	}
	
	/**
	 * @throws Exception 
	 * @describe 该方法用于更新customer_info中的客户类型
	 */
	private void updateCustomerType() throws Exception {
		sSql ="update customer_info ii set CustomerType='0310' where ii.certid ='"+sCertID+"' and (ii.certtype ='Ind01' or ii.certtype ='Ind08') ";
		sqlQuery.execute(sSql);
		sSql = "";
	}
	
	/**
	 * @describe 该方法用于在Customer_info中插入数据
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
	 * @describe 该方法用于在ind_info中插入数据
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
			logger.info("---向ind_info中添加信息脚本---"+sSql);
			sqlQuery.execute(sSql);
			sSql ="";
		} catch (Exception e) {
			logger.info("---插入ind_info时出错---");
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
	
	/**
	 * @describe 改方法用于获取基础配置信息内容
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
