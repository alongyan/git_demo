package com.amarsoft.server.dao;
/**
 * @describe 该类用于对买单侠产品进行实例化
 * @author xlsun 2015-07-20
 *
 */
public class JMMMLoanObject {
	private String  ChineseName                 = "";//中文姓名          
	private String  IdentityNumber              = "";//身份证号          
	private String  Sex                         = "";//性别              
	private String  Phone                       = "";//手机号码          
	private String  MaritalStatus               = "";//婚姻情况          
	private String  CompanyName                 = "";//工作单位          
	private String  CompanyNature               = "";//单位性质          
	private String  CompanyTel                  = "";//工作单位座机      
	private String  RelaName                    = "";//姓名              
	private String  RelaPhone                   = "";//手机号            
	private String  Relationship                = "";//关系              
	private String  HasBadRecord                = "";//有无不良记录      
	private String  ProductType                 = "";//产品类型          
	private String  Batch                       = "";//贷款期限（月）    
	private String  UserName                    = "";//融资用户名        
	private Double  FinancingAmount             = 0.0d;//融资金额          
	private Double  LenderAmount                = 0.0d;//融资人实收金额    
	private Double  HJXD_RepaymentByMonth       = 0.0d;//月偿还本息数额    
	private String  RepaymentDay                = "";//还款日            
	private String  RepaymentStartDate          = "";//还款起日期        
	private String  RepaymentEndDate            = "";//还款止日期        
	private Double  HJXD_LastMonthAmount        = 0.0d;//最后一期还本息数额
	private String  ApplyCity                   = "";//贷款提交城市      
	private String  ProductName                 = "";//产品名称          
	private Double  SalePrice                   = 0.0d;//销售价格          
	private Double  DownPaymentAmount           = 0.0d;//首付金额          
	private String  AltName                     = "";//姓名              
	private String  AltID                       = "";//身份证号码        
	private String  AltRelation                 = "";//关系              
	private String  BankName                    = "";//开户银行名称      
	private String  AccountNo                   = "";//开户银行账号      
	private String  MerchantName                = "";//合作商户名称      
	private String  MerchantProvince            = "";//商户省份          
	private String  MerchantCity                = "";//商户城市          
	private String  SPID                        = "";//渠道編號
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public String getIdentityNumber() {
		return IdentityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		IdentityNumber = identityNumber;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyNature() {
		return CompanyNature;
	}
	public void setCompanyNature(String companyNature) {
		CompanyNature = companyNature;
	}
	public String getCompanyTel() {
		return CompanyTel;
	}
	public void setCompanyTel(String companyTel) {
		CompanyTel = companyTel;
	}
	public String getRelaName() {
		return RelaName;
	}
	public void setRelaName(String relaName) {
		RelaName = relaName;
	}
	public String getRelaPhone() {
		return RelaPhone;
	}
	public void setRelaPhone(String relaPhone) {
		RelaPhone = relaPhone;
	}
	public String getRelationship() {
		return Relationship;
	}
	public void setRelationship(String relationship) {
		Relationship = relationship;
	}
	public String getHasBadRecord() {
		return HasBadRecord;
	}
	public void setHasBadRecord(String hasBadRecord) {
		HasBadRecord = hasBadRecord;
	}
	public String getProductType() {
		return ProductType;
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	public String getBatch() {
		return Batch;
	}
	public void setBatch(String batch) {
		Batch = batch;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Double getFinancingAmount() {
		return FinancingAmount;
	}
	public void setFinancingAmount(Double financingAmount) {
		FinancingAmount = financingAmount;
	}
	public Double getLenderAmount() {
		return LenderAmount;
	}
	public void setLenderAmount(Double lenderAmount) {
		LenderAmount = lenderAmount;
	}
	public Double getHJXD_RepaymentByMonth() {
		return HJXD_RepaymentByMonth;
	}
	public void setHJXD_RepaymentByMonth(Double hJXD_RepaymentByMonth) {
		HJXD_RepaymentByMonth = hJXD_RepaymentByMonth;
	}
	public String getRepaymentDay() {
		return RepaymentDay;
	}
	public void setRepaymentDay(String repaymentDay) {
		RepaymentDay = repaymentDay;
	}
	public String getRepaymentStartDate() {
		return RepaymentStartDate;
	}
	public void setRepaymentStartDate(String repaymentStartDate) {
		RepaymentStartDate = repaymentStartDate;
	}
	public String getRepaymentEndDate() {
		return RepaymentEndDate;
	}
	public void setRepaymentEndDate(String repaymentEndDate) {
		RepaymentEndDate = repaymentEndDate;
	}
	public Double getHJXD_LastMonthAmount() {
		return HJXD_LastMonthAmount;
	}
	public void setHJXD_LastMonthAmount(Double hJXD_LastMonthAmount) {
		HJXD_LastMonthAmount = hJXD_LastMonthAmount;
	}
	public String getApplyCity() {
		return ApplyCity;
	}
	public void setApplyCity(String applyCity) {
		ApplyCity = applyCity;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public Double getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(Double salePrice) {
		SalePrice = salePrice;
	}
	public Double getDownPaymentAmount() {
		return DownPaymentAmount;
	}
	public void setDownPaymentAmount(Double downPaymentAmount) {
		DownPaymentAmount = downPaymentAmount;
	}
	public String getAltName() {
		return AltName;
	}
	public void setAltName(String altName) {
		AltName = altName;
	}
	public String getAltID() {
		return AltID;
	}
	public void setAltID(String altID) {
		AltID = altID;
	}
	public String getAltRelation() {
		return AltRelation;
	}
	public void setAltRelation(String altRelation) {
		AltRelation = altRelation;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getMerchantName() {
		return MerchantName;
	}
	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}
	public String getMerchantProvince() {
		return MerchantProvince;
	}
	public void setMerchantProvince(String merchantProvince) {
		MerchantProvince = merchantProvince;
	}
	public String getMerchantCity() {
		return MerchantCity;
	}
	public void setMerchantCity(String merchantCity) {
		MerchantCity = merchantCity;
	}
	public void setSPID(String sPID) {
		SPID = sPID;
	}
	public String getSPID() {
		return SPID;
	}


}
