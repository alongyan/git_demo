package com.amarsoft.server.dao;
//�˵�����
public class JMXinYongBaoObject {
	private String ChineseName     		 	  = "";//��������                             
	private String Age                   	  = "";//����                                 
	private String Sex                   	  = "";//�Ա�                                 
	private String Phone                 	  = "";//�ֻ�����                             
	private String IdentityNumber        	  = "";//֤������                             
	private String PostAddress           	  = "";//ͨѶ��ַ                             
	private String AccountLocation       	  = "";//����                                 
	private String MaritalStatus         	  = "";//����״��                             
	private String ThirdPartyScore       	  = "";//����������                           
	private String Birthday              	  = "";//��������                             
	private String SpouseName            	  = "";//��ż����                             
	private String SpouseID              	  = "";//��ż���֤��                         
	private String SpousePhone           	  = "";//��ż�ֻ�����                         
	private String SpouseWorkUnit        	  = "";//��ż������λ����                     
	private String SpouseJob             	  = "";//��żְ��                             
	private String HasCar                	  = "";//��ͥ�������                         
	private String HasHouse              	  = "";//�з���                               
	private String CompanyName           	  = "";//������λ                             
	private String CompanyNature         	  = "";//��λ����                             
	private String CompanyTel            	  = "";//������λ����                         
	private String CompanyAddress        	  = "";//��λ��ַ                             
	private String WorkStartDate         	  = "";//��������ʼ����                       
	private String Position             	  = "";//ְ��                                 
	private String WorkYears             	  = "";//�ܹ���                               
	private String Academic              	  = "";//ѧ��                                 
	private Double CheckedAmount              = 0.0d;//����ʵ��������                       
	private String CheckedLoanInfo3Month      = "";//��3���´����������Ų�ѯ����          
	private String WorstRepaymentLastTwoYears = "";//��ȥ2��������״̬                
	private String TotalOverDueLastTwoYears   = "";//��ȥ2�����ۼ����ڴ���                
	private String OperatEntity               = "";//��Ӫ��������                         
	private String OperatEntityType           = "";//��Ӫ��������                         
	private String FoundYears                 = "";//��������                             
	private String OperationStartDate         = "";//ʵ�ʾ�Ӫ��ʼʱ��                     
	private String CurrentOperatAddr          = "";//��ǰ��Ӫ��ַ                         
	private Double ShareHolderRate            = 0.0d;//�����˳ֹɱ���                       
	private String EmployeeCount              = "";//Ա������                           
    private Double AccountInFlowPerMonth      = 0.0d;//��Ӫʵ���˻��¾�������             
    private String Type                       = "";//��Ʒ����                             
    private Double FinancingAmount            = 0.0d;//���ʽ�Ԫ��                       
    private String Batch                      = "";//�������ޣ��£�                       
    private String DebtUsage                  = "";//������;                             
    private Double LenderAmount               = 0.0d;//������ʵ�ս��                       
    private String HouseType                  = "";//��������                             
    private Double RepaymentAmountByMonth     = 0.0d;//�³�����Ϣ����                       
    private String RepaymentDay               = "";//������                               
    private String RepaymentStartDate         = "";//����������                           
    private String RepaymentEndDate           = "";//����ֹ����                           
    private Double HJXD_LastMonthAmount       = 0.0d;//���һ�ڻ���Ϣ����                   
    private String ApplyCity                  = "";//�����ύ����                         
    private String EstateType                 = "";//��������                             
    private String RealEstateLocations        = "";//�������ڵ�                           
    private Double ConstructionArea           = 0.0d;//�������                             
    private String PurchaseDate               = "";//����ʱ��                             
    private Double HouseTotalValue            = 0.0d;//�����ܼ�                             
    private String DebteeRelation             = "";//��ϵ                                 
    private String DebteeName                 = "";//����                                 
    private String DebteeID                   = "";//���֤��                             
    private String DebteeMobile               = "";//�ֻ�����                             
    private String DebteeMailingAddress       = "";//ͨѶ��ַ                             
    private String AccountNo                  = "";//���������˺�                         
    private String BankName                   = "";//������������                         
    private String Provence                   = "";//��������ʡ��                         
    private String Area                       = "";//�������е���                         
    private String SubbranchBank              = "";//��������֧��
    
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