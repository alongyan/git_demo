package com.amarsoft.server.dao;
//账单代偿
public class JMXinYongBaoObject {
	private String ChineseName     		 	  = "";//中文姓名                             
	private String Age                   	  = "";//年龄                                 
	private String Sex                   	  = "";//性别                                 
	private String Phone                 	  = "";//手机号码                             
	private String IdentityNumber        	  = "";//证件号码                             
	private String PostAddress           	  = "";//通讯地址                             
	private String AccountLocation       	  = "";//户籍                                 
	private String MaritalStatus         	  = "";//婚姻状况                             
	private String ThirdPartyScore       	  = "";//第三方评级                           
	private String Birthday              	  = "";//出生日期                             
	private String SpouseName            	  = "";//配偶姓名                             
	private String SpouseID              	  = "";//配偶身份证号                         
	private String SpousePhone           	  = "";//配偶手机号码                         
	private String SpouseWorkUnit        	  = "";//配偶工作单位名称                     
	private String SpouseJob             	  = "";//配偶职务                             
	private String HasCar                	  = "";//家庭汽车情况                         
	private String HasHouse              	  = "";//有房产                               
	private String CompanyName           	  = "";//工作单位                             
	private String CompanyNature         	  = "";//单位性质                             
	private String CompanyTel            	  = "";//工作单位座机                         
	private String CompanyAddress        	  = "";//单位地址                             
	private String WorkStartDate         	  = "";//本工作开始日期                       
	private String Position             	  = "";//职务                                 
	private String WorkYears             	  = "";//总工龄                               
	private String Academic              	  = "";//学历                                 
	private Double CheckedAmount              = 0.0d;//经核实的月收入                       
	private String CheckedLoanInfo3Month      = "";//近3个月贷款审批征信查询次数          
	private String WorstRepaymentLastTwoYears = "";//过去2年中最差还款状态                
	private String TotalOverDueLastTwoYears   = "";//过去2年中累计逾期次数                
	private String OperatEntity               = "";//经营主体名称                         
	private String OperatEntityType           = "";//经营主体类型                         
	private String FoundYears                 = "";//成立年限                             
	private String OperationStartDate         = "";//实际经营起始时间                     
	private String CurrentOperatAddr          = "";//当前经营地址                         
	private Double ShareHolderRate            = 0.0d;//融资人持股比例                       
	private String EmployeeCount              = "";//员工数量                           
    private Double AccountInFlowPerMonth      = 0.0d;//经营实体账户月均流入金额             
    private String Type                       = "";//产品类型                             
    private Double FinancingAmount            = 0.0d;//融资金额（元）                       
    private String Batch                      = "";//贷款期限（月）                       
    private String DebtUsage                  = "";//贷款用途                             
    private Double LenderAmount               = 0.0d;//融资人实收金额                       
    private String HouseType                  = "";//房产类型                             
    private Double RepaymentAmountByMonth     = 0.0d;//月偿还本息数额                       
    private String RepaymentDay               = "";//还款日                               
    private String RepaymentStartDate         = "";//还款起日期                           
    private String RepaymentEndDate           = "";//还款止日期                           
    private Double HJXD_LastMonthAmount       = 0.0d;//最后一期还本息数额                   
    private String ApplyCity                  = "";//贷款提交城市                         
    private String EstateType                 = "";//房产类型                             
    private String RealEstateLocations        = "";//房产所在地                           
    private Double ConstructionArea           = 0.0d;//建筑面积                             
    private String PurchaseDate               = "";//购买时间                             
    private Double HouseTotalValue            = 0.0d;//房产总价                             
    private String DebteeRelation             = "";//关系                                 
    private String DebteeName                 = "";//姓名                                 
    private String DebteeID                   = "";//身份证号                             
    private String DebteeMobile               = "";//手机号码                             
    private String DebteeMailingAddress       = "";//通讯地址                             
    private String AccountNo                  = "";//开户银行账号                         
    private String BankName                   = "";//开户银行名称                         
    private String Provence                   = "";//开户银行省份                         
    private String Area                       = "";//开户银行地区                         
    private String SubbranchBank              = "";//开户银行支行
    
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
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
	public String getIdentityNumber() {
		return IdentityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		IdentityNumber = identityNumber;
	}
	public String getPostAddress() {
		return PostAddress;
	}
	public void setPostAddress(String postAddress) {
		PostAddress = postAddress;
	}
	public String getAccountLocation() {
		return AccountLocation;
	}
	public void setAccountLocation(String accountLocation) {
		AccountLocation = accountLocation;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getThirdPartyScore() {
		return ThirdPartyScore;
	}
	public void setThirdPartyScore(String thirdPartyScore) {
		ThirdPartyScore = thirdPartyScore;
	}
	public String getBirthday() {
		return Birthday;
	}
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}
	public String getSpouseName() {
		return SpouseName;
	}
	public void setSpouseName(String spouseName) {
		SpouseName = spouseName;
	}
	public String getSpouseID() {
		return SpouseID;
	}
	public void setSpouseID(String spouseID) {
		SpouseID = spouseID;
	}
	public String getSpousePhone() {
		return SpousePhone;
	}
	public void setSpousePhone(String spousePhone) {
		SpousePhone = spousePhone;
	}
	public String getSpouseWorkUnit() {
		return SpouseWorkUnit;
	}
	public void setSpouseWorkUnit(String spouseWorkUnit) {
		SpouseWorkUnit = spouseWorkUnit;
	}
	public String getSpouseJob() {
		return SpouseJob;
	}
	public void setSpouseJob(String spouseJob) {
		SpouseJob = spouseJob;
	}
	public String getHasCar() {
		return HasCar;
	}
	public void setHasCar(String hasCar) {
		HasCar = hasCar;
	}
	public String getHasHouse() {
		return HasHouse;
	}
	public void setHasHouse(String hasHouse) {
		HasHouse = hasHouse;
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
	public String getCompanyAddress() {
		return CompanyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		CompanyAddress = companyAddress;
	}
	public String getWorkStartDate() {
		return WorkStartDate;
	}
	public void setWorkStartDate(String workStartDate) {
		WorkStartDate = workStartDate;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getWorkYears() {
		return WorkYears;
	}
	public void setWorkYears(String workYears) {
		WorkYears = workYears;
	}
	public String getAcademic() {
		return Academic;
	}
	public void setAcademic(String academic) {
		Academic = academic;
	}
	public Double getCheckedAmount() {
		return CheckedAmount;
	}
	public void setCheckedAmount(Double checkedAmount) {
		CheckedAmount = checkedAmount;
	}
	public String getCheckedLoanInfo3Month() {
		return CheckedLoanInfo3Month;
	}
	public void setCheckedLoanInfo3Month(String checkedLoanInfo3Month) {
		CheckedLoanInfo3Month = checkedLoanInfo3Month;
	}
	public String getWorstRepaymentLastTwoYears() {
		return WorstRepaymentLastTwoYears;
	}
	public void setWorstRepaymentLastTwoYears(String worstRepaymentLastTwoYears) {
		WorstRepaymentLastTwoYears = worstRepaymentLastTwoYears;
	}
	public String getTotalOverDueLastTwoYears() {
		return TotalOverDueLastTwoYears;
	}
	public void setTotalOverDueLastTwoYears(String totalOverDueLastTwoYears) {
		TotalOverDueLastTwoYears = totalOverDueLastTwoYears;
	}
	public String getOperatEntity() {
		return OperatEntity;
	}
	public void setOperatEntity(String operatEntity) {
		OperatEntity = operatEntity;
	}
	public String getOperatEntityType() {
		return OperatEntityType;
	}
	public void setOperatEntityType(String operatEntityType) {
		OperatEntityType = operatEntityType;
	}
	public String getFoundYears() {
		return FoundYears;
	}
	public void setFoundYears(String foundYears) {
		FoundYears = foundYears;
	}
	public String getOperationStartDate() {
		return OperationStartDate;
	}
	public void setOperationStartDate(String operationStartDate) {
		OperationStartDate = operationStartDate;
	}
	public String getCurrentOperatAddr() {
		return CurrentOperatAddr;
	}
	public void setCurrentOperatAddr(String currentOperatAddr) {
		CurrentOperatAddr = currentOperatAddr;
	}
	public Double getShareHolderRate() {
		return ShareHolderRate;
	}
	public void setShareHolderRate(Double shareHolderRate) {
		ShareHolderRate = shareHolderRate;
	}
	public String getEmployeeCount() {
		return EmployeeCount;
	}
	public void setEmployeeCount(String employeeCount) {
		EmployeeCount = employeeCount;
	}
	public Double getAccountInFlowPerMonth() {
		return AccountInFlowPerMonth;
	}
	public void setAccountInFlowPerMonth(Double accountInFlowPerMonth) {
		AccountInFlowPerMonth = accountInFlowPerMonth;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Double getFinancingAmount() {
		return FinancingAmount;
	}
	public void setFinancingAmount(Double financingAmount) {
		FinancingAmount = financingAmount;
	}
	public String getBatch() {
		return Batch;
	}
	public void setBatch(String batch) {
		Batch = batch;
	}
	public String getDebtUsage() {
		return DebtUsage;
	}
	public void setDebtUsage(String debtUsage) {
		DebtUsage = debtUsage;
	}
	public Double getLenderAmount() {
		return LenderAmount;
	}
	public void setLenderAmount(Double lenderAmount) {
		LenderAmount = lenderAmount;
	}
	public String getHouseType() {
		return HouseType;
	}
	public void setHouseType(String houseType) {
		HouseType = houseType;
	}
	public Double getRepaymentAmountByMonth() {
		return RepaymentAmountByMonth;
	}
	public void setRepaymentAmountByMonth(Double repaymentAmountByMonth) {
		RepaymentAmountByMonth = repaymentAmountByMonth;
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
	public String getEstateType() {
		return EstateType;
	}
	public void setEstateType(String estateType) {
		EstateType = estateType;
	}
	public String getRealEstateLocations() {
		return RealEstateLocations;
	}
	public void setRealEstateLocations(String realEstateLocations) {
		RealEstateLocations = realEstateLocations;
	}
	public Double getConstructionArea() {
		return ConstructionArea;
	}
	public void setConstructionArea(Double constructionArea) {
		ConstructionArea = constructionArea;
	}
	public String getPurchaseDate() {
		return PurchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		PurchaseDate = purchaseDate;
	}
	public Double getHouseTotalValue() {
		return HouseTotalValue;
	}
	public void setHouseTotalValue(Double houseTotalValue) {
		HouseTotalValue = houseTotalValue;
	}
	public String getDebteeRelation() {
		return DebteeRelation;
	}
	public void setDebteeRelation(String debteeRelation) {
		DebteeRelation = debteeRelation;
	}
	public String getDebteeName() {
		return DebteeName;
	}
	public void setDebteeName(String debteeName) {
		DebteeName = debteeName;
	}
	public String getDebteeID() {
		return DebteeID;
	}
	public void setDebteeID(String debteeID) {
		DebteeID = debteeID;
	}
	public String getDebteeMobile() {
		return DebteeMobile;
	}
	public void setDebteeMobile(String debteeMobile) {
		DebteeMobile = debteeMobile;
	}
	public String getDebteeMailingAddress() {
		return DebteeMailingAddress;
	}
	public void setDebteeMailingAddress(String debteeMailingAddress) {
		DebteeMailingAddress = debteeMailingAddress;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getProvence() {
		return Provence;
	}
	public void setProvence(String provence) {
		Provence = provence;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getSubbranchBank() {
		return SubbranchBank;
	}
	public void setSubbranchBank(String subbranchBank) {
		SubbranchBank = subbranchBank;
	}
      
}