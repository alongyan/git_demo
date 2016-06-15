package com.amarsoft.server.dao;
//账单代偿
public class JMMEMELoanObject {
	private String  ChineseName                 = "";//中文姓名          
	private String  National                    = "";//民族          
	private String  Birthday                    = "";//出生日期              
	private String  Age                         = "";//年龄          
	private String  Sex                         = "";//性别          
	private String  IdentityNumber              = "";//证件号码          
	private String  IdentityType                = "";//证件类型         
	private String  MaritalStatus               = "";//婚姻状况      
	private String  Email                       = "";//电子邮箱              
	private String  Phone                       = "";//手机号码         
	private String  PostAddress                 = "";//通讯地址              
	private String  PostCode                    = "";//邮政编码      
	private String  ThirdPartyScore             = "";//第三方评级          
	private String  Academic                    = "";//学历    
	private Double  CheckedAmount               = 0.0d;//经核实的月收入        
	private String  CompanyName                 = "";//工作单位          
	private String  CompanyNature               = "";//单位性质   
	private String  Position                    = "";//职务    
	private String  College                     = "";//院系            
	private String  DegreeCategory              = "";//学位类别        
	private String  GraduateDate                = "";//毕业时间        
	private String  InSchoolID                  = "";//学号
	private String  SchoolName                  = "";//学校名称      
	private String  Specialty                   = "";//专业          
	private String  SchoolEntranceDate          = "";//入学时间          
	private String  SchoolYears                 = "";//年限          
	private String  Mobile                      = "";//手机号码              
	private String  QQ                          = "";//QQ        
	private String  RoomAddress                 = "";//宿舍地址            
	private String  WeChat                      = "";//微信     
	private String  RelationName                = "";//姓名      
	private String  RelationPhone               = "";//手机      
	private String  Relationship                = "";//关系          
	private String	CardType                    = "";//卡种                      
	private String	Brands                      = "";//卡品牌                    
	private String	CardNumber                  = "";//卡号                      
	private String	DealCount                   = "";//交易笔数                  
	private String	FirstDealDate               = "";//首次交易日期              
	private String  LastDealDate                = "";//最新交易日期              
	private String  Level                       = "";//卡等级                    
	private String	Location                    = "";//开户行所在地              
	private String	Product                     = "";//卡产品                    
	private String	Property                    = "";//卡性质                    
	private String	Status                      = "";//鉴权状态                  
	private Double	TotalInAmt                  = 0.0d;//交易总金额（收）          
	private Double  TotalOutAmt                 = 0.0d;//交易总金额（支）          
	private String	UnfinishedCount             = "";//未结清笔数                
	private String  FinishedCount               = "";//已结清笔数                
	private String  ApplyCount                  = "";//申请中笔数                
	private String  PassedCount                 = "";//通过笔数                  
	private String  DeniedCount                 = "";//拒绝笔数                  
	private String  QueryCount                  = "";//近两年查询次数            
	private String  OtherCount                  = "";//"申请/债权/逾期/补录/不良"
	private String  NFCSDebtCount               = "";//贷款笔数                  
	private String  NFCSFirstDebtDate           = "";//首贷日                    
	private Double  NFCSMaxCreditAmt            = 0.0d;//最大授信额度              
	private Double  NFCSTotalAmt                = 0.0d;//贷款总额                  
	private Double  NFCSValidAmt                = 0.0d;//贷款余额                  
	private Double  NFCSRepaymentByMonth        = 0.0d;//协定月还款                
	private Double  NFCSTotalOverDueAmt         = 0.0d;//当前逾期总额              
	private Double  NFCSMaxOverDueAmt           = 0.0d;//最高逾期金额              
	private String  NFCSOverDueCount            = "";//最高逾期期数  
	private Double	FinancingAmount             = 0.0d;//贷款金额    
	private String	Batch                       = "";//贷款期限    
	private Double	LenderAmount                = 0.0d;//放款金额    
	private String	RepaymentDay                = "";//每月还款日  
	private String	RepaymentStartDate          = "";//还款起日期  
	private String	RepaymentEndDate            = "";//还款止日期  
	private String	PayDate                     = "";//放款时间    
	private String	ApplyCity                   = "";//贷款提交城市
	private String	Type                        = "";//产品名称    
	private Double	EstimateAmt                 = 0.0d;//预估额度    
	private Double	TotalAmt                    = 0.0d;//额度        
	private Double	ValidAmt                    = 0.0d;//可用额度    
	private Double	UsedAmt                     = 0.0d;//已用额度    
	private String  SPID                        = "";//SPID
	private String  Oldlcno                     = "";//渠道項目編號
	
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String sPID) {
		SPID = sPID;
	}
	public String getOldlcno() {
		return Oldlcno;
	}
	public void setOldlcno(String oldlcno) {
		Oldlcno = oldlcno;
	}
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public String getNational() {
		return National;
	}
	public void setNational(String national) {
		National = national;
	}
	public String getBirthday() {
		return Birthday;
	}
	public void setBirthday(String birthday) {
		Birthday = birthday;
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
	public String getIdentityNumber() {
		return IdentityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		IdentityNumber = identityNumber;
	}
	public String getIdentityType() {
		return IdentityType;
	}
	public void setIdentityType(String identityType) {
		IdentityType = identityType;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getPostAddress() {
		return PostAddress;
	}
	public void setPostAddress(String postAddress) {
		PostAddress = postAddress;
	}
	public String getPostCode() {
		return PostCode;
	}
	public void setPostCode(String postCode) {
		PostCode = postCode;
	}
	public String getThirdPartyScore() {
		return ThirdPartyScore;
	}
	public void setThirdPartyScore(String thirdPartyScore) {
		ThirdPartyScore = thirdPartyScore;
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
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getCollege() {
		return College;
	}
	public void setCollege(String college) {
		College = college;
	}
	public String getDegreeCategory() {
		return DegreeCategory;
	}
	public void setDegreeCategory(String degreeCategory) {
		DegreeCategory = degreeCategory;
	}
	public String getGraduateDate() {
		return GraduateDate;
	}
	public void setGraduateDate(String graduateDate) {
		GraduateDate = graduateDate;
	}
	public String getInSchoolID() {
		return InSchoolID;
	}
	public void setInSchoolID(String inSchoolID) {
		InSchoolID = inSchoolID;
	}
	public String getSchoolName() {
		return SchoolName;
	}
	public void setSchoolName(String schoolName) {
		SchoolName = schoolName;
	}
	public String getSpecialty() {
		return Specialty;
	}
	public void setSpecialty(String specialty) {
		Specialty = specialty;
	}
	public String getSchoolEntranceDate() {
		return SchoolEntranceDate;
	}
	public void setSchoolEntranceDate(String schoolEntranceDate) {
		SchoolEntranceDate = schoolEntranceDate;
	}
	public String getSchoolYears() {
		return SchoolYears;
	}
	public void setSchoolYears(String schoolYears) {
		SchoolYears = schoolYears;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getRoomAddress() {
		return RoomAddress;
	}
	public void setRoomAddress(String roomAddress) {
		RoomAddress = roomAddress;
	}
	public String getWeChat() {
		return WeChat;
	}
	public void setWeChat(String weChat) {
		WeChat = weChat;
	}
	public String getRelationName() {
		return RelationName;
	}
	public void setRelationName(String relationName) {
		RelationName = relationName;
	}
	public String getRelationPhone() {
		return RelationPhone;
	}
	public void setRelationPhone(String relationPhone) {
		RelationPhone = relationPhone;
	}
	public String getRelationship() {
		return Relationship;
	}
	public void setRelationship(String relationship) {
		Relationship = relationship;
	}
	public String getCardType() {
		return CardType;
	}
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	public String getBrands() {
		return Brands;
	}
	public void setBrands(String brands) {
		Brands = brands;
	}
	public String getCardNumber() {
		return CardNumber;
	}
	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	public String getDealCount() {
		return DealCount;
	}
	public void setDealCount(String dealCount) {
		DealCount = dealCount;
	}
	public String getFirstDealDate() {
		return FirstDealDate;
	}
	public void setFirstDealDate(String firstDealDate) {
		FirstDealDate = firstDealDate;
	}
	public String getLastDealDate() {
		return LastDealDate;
	}
	public void setLastDealDate(String lastDealDate) {
		LastDealDate = lastDealDate;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}
	public String getProperty() {
		return Property;
	}
	public void setProperty(String property) {
		Property = property;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Double getTotalInAmt() {
		return TotalInAmt;
	}
	public void setTotalInAmt(Double totalInAmt) {
		TotalInAmt = totalInAmt;
	}
	public Double getTotalOutAmt() {
		return TotalOutAmt;
	}
	public void setTotalOutAmt(Double totalOutAmt) {
		TotalOutAmt = totalOutAmt;
	}
	public String getUnfinishedCount() {
		return UnfinishedCount;
	}
	public void setUnfinishedCount(String unfinishedCount) {
		UnfinishedCount = unfinishedCount;
	}
	public String getFinishedCount() {
		return FinishedCount;
	}
	public void setFinishedCount(String finishedCount) {
		FinishedCount = finishedCount;
	}
	public String getApplyCount() {
		return ApplyCount;
	}
	public void setApplyCount(String applyCount) {
		ApplyCount = applyCount;
	}
	public String getPassedCount() {
		return PassedCount;
	}
	public void setPassedCount(String passedCount) {
		PassedCount = passedCount;
	}
	public String getDeniedCount() {
		return DeniedCount;
	}
	public void setDeniedCount(String deniedCount) {
		DeniedCount = deniedCount;
	}
	public String getQueryCount() {
		return QueryCount;
	}
	public void setQueryCount(String queryCount) {
		QueryCount = queryCount;
	}
	public String getOtherCount() {
		return OtherCount;
	}
	public void setOtherCount(String otherCount) {
		OtherCount = otherCount;
	}
	public String getNFCSDebtCount() {
		return NFCSDebtCount;
	}
	public void setNFCSDebtCount(String nFCSDebtCount) {
		NFCSDebtCount = nFCSDebtCount;
	}
	public String getNFCSFirstDebtDate() {
		return NFCSFirstDebtDate;
	}
	public void setNFCSFirstDebtDate(String nFCSFirstDebtDate) {
		NFCSFirstDebtDate = nFCSFirstDebtDate;
	}
	public Double getNFCSMaxCreditAmt() {
		return NFCSMaxCreditAmt;
	}
	public void setNFCSMaxCreditAmt(Double nFCSMaxCreditAmt) {
		NFCSMaxCreditAmt = nFCSMaxCreditAmt;
	}
	public Double getNFCSTotalAmt() {
		return NFCSTotalAmt;
	}
	public void setNFCSTotalAmt(Double nFCSTotalAmt) {
		NFCSTotalAmt = nFCSTotalAmt;
	}
	public Double getNFCSValidAmt() {
		return NFCSValidAmt;
	}
	public void setNFCSValidAmt(Double nFCSValidAmt) {
		NFCSValidAmt = nFCSValidAmt;
	}
	public Double getNFCSRepaymentByMonth() {
		return NFCSRepaymentByMonth;
	}
	public void setNFCSRepaymentByMonth(Double nFCSRepaymentByMonth) {
		NFCSRepaymentByMonth = nFCSRepaymentByMonth;
	}
	public Double getNFCSTotalOverDueAmt() {
		return NFCSTotalOverDueAmt;
	}
	public void setNFCSTotalOverDueAmt(Double nFCSTotalOverDueAmt) {
		NFCSTotalOverDueAmt = nFCSTotalOverDueAmt;
	}
	public Double getNFCSMaxOverDueAmt() {
		return NFCSMaxOverDueAmt;
	}
	public void setNFCSMaxOverDueAmt(Double nFCSMaxOverDueAmt) {
		NFCSMaxOverDueAmt = nFCSMaxOverDueAmt;
	}
	public String getNFCSOverDueCount() {
		return NFCSOverDueCount;
	}
	public void setNFCSOverDueCount(String nFCSOverDueCount) {
		NFCSOverDueCount = nFCSOverDueCount;
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
	public Double getLenderAmount() {
		return LenderAmount;
	}
	public void setLenderAmount(Double lenderAmount) {
		LenderAmount = lenderAmount;
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
	public String getPayDate() {
		return PayDate;
	}
	public void setPayDate(String payDate) {
		PayDate = payDate;
	}
	public String getApplyCity() {
		return ApplyCity;
	}
	public void setApplyCity(String applyCity) {
		ApplyCity = applyCity;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Double getEstimateAmt() {
		return EstimateAmt;
	}
	public void setEstimateAmt(Double estimateAmt) {
		EstimateAmt = estimateAmt;
	}
	public Double getTotalAmt() {
		return TotalAmt;
	}
	public void setTotalAmt(Double totalAmt) {
		TotalAmt = totalAmt;
	}
	public Double getValidAmt() {
		return ValidAmt;
	}
	public void setValidAmt(Double validAmt) {
		ValidAmt = validAmt;
	}
	public Double getUsedAmt() {
		return UsedAmt;
	}
	public void setUsedAmt(Double usedAmt) {
		UsedAmt = usedAmt;
	}

		
}