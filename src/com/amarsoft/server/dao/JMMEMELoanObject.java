package com.amarsoft.server.dao;
//�˵�����
public class JMMEMELoanObject {
	private String  ChineseName                 = "";//��������          
	private String  National                    = "";//����          
	private String  Birthday                    = "";//��������              
	private String  Age                         = "";//����          
	private String  Sex                         = "";//�Ա�          
	private String  IdentityNumber              = "";//֤������          
	private String  IdentityType                = "";//֤������         
	private String  MaritalStatus               = "";//����״��      
	private String  Email                       = "";//��������              
	private String  Phone                       = "";//�ֻ�����         
	private String  PostAddress                 = "";//ͨѶ��ַ              
	private String  PostCode                    = "";//��������      
	private String  ThirdPartyScore             = "";//����������          
	private String  Academic                    = "";//ѧ��    
	private Double  CheckedAmount               = 0.0d;//����ʵ��������        
	private String  CompanyName                 = "";//������λ          
	private String  CompanyNature               = "";//��λ����   
	private String  Position                    = "";//ְ��    
	private String  College                     = "";//Ժϵ            
	private String  DegreeCategory              = "";//ѧλ���        
	private String  GraduateDate                = "";//��ҵʱ��        
	private String  InSchoolID                  = "";//ѧ��
	private String  SchoolName                  = "";//ѧУ����      
	private String  Specialty                   = "";//רҵ          
	private String  SchoolEntranceDate          = "";//��ѧʱ��          
	private String  SchoolYears                 = "";//����          
	private String  Mobile                      = "";//�ֻ�����              
	private String  QQ                          = "";//QQ        
	private String  RoomAddress                 = "";//�����ַ            
	private String  WeChat                      = "";//΢��     
	private String  RelationName                = "";//����      
	private String  RelationPhone               = "";//�ֻ�      
	private String  Relationship                = "";//��ϵ          
	private String	CardType                    = "";//����                      
	private String	Brands                      = "";//��Ʒ��                    
	private String	CardNumber                  = "";//����                      
	private String	DealCount                   = "";//���ױ���                  
	private String	FirstDealDate               = "";//�״ν�������              
	private String  LastDealDate                = "";//���½�������              
	private String  Level                       = "";//���ȼ�                    
	private String	Location                    = "";//���������ڵ�              
	private String	Product                     = "";//����Ʒ                    
	private String	Property                    = "";//������                    
	private String	Status                      = "";//��Ȩ״̬                  
	private Double	TotalInAmt                  = 0.0d;//�����ܽ��գ�          
	private Double  TotalOutAmt                 = 0.0d;//�����ܽ�֧��          
	private String	UnfinishedCount             = "";//δ�������                
	private String  FinishedCount               = "";//�ѽ������                
	private String  ApplyCount                  = "";//�����б���                
	private String  PassedCount                 = "";//ͨ������                  
	private String  DeniedCount                 = "";//�ܾ�����                  
	private String  QueryCount                  = "";//�������ѯ����            
	private String  OtherCount                  = "";//"����/ծȨ/����/��¼/����"
	private String  NFCSDebtCount               = "";//�������                  
	private String  NFCSFirstDebtDate           = "";//�״���                    
	private Double  NFCSMaxCreditAmt            = 0.0d;//������Ŷ��              
	private Double  NFCSTotalAmt                = 0.0d;//�����ܶ�                  
	private Double  NFCSValidAmt                = 0.0d;//�������                  
	private Double  NFCSRepaymentByMonth        = 0.0d;//Э���»���                
	private Double  NFCSTotalOverDueAmt         = 0.0d;//��ǰ�����ܶ�              
	private Double  NFCSMaxOverDueAmt           = 0.0d;//������ڽ��              
	private String  NFCSOverDueCount            = "";//�����������  
	private Double	FinancingAmount             = 0.0d;//������    
	private String	Batch                       = "";//��������    
	private Double	LenderAmount                = 0.0d;//�ſ���    
	private String	RepaymentDay                = "";//ÿ�»�����  
	private String	RepaymentStartDate          = "";//����������  
	private String	RepaymentEndDate            = "";//����ֹ����  
	private String	PayDate                     = "";//�ſ�ʱ��    
	private String	ApplyCity                   = "";//�����ύ����
	private String	Type                        = "";//��Ʒ����    
	private Double	EstimateAmt                 = 0.0d;//Ԥ�����    
	private Double	TotalAmt                    = 0.0d;//���        
	private Double	ValidAmt                    = 0.0d;//���ö��    
	private Double	UsedAmt                     = 0.0d;//���ö��    
	private String  SPID                        = "";//SPID
	private String  Oldlcno                     = "";//�����Ŀ��̖
	
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