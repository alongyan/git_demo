package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMMEMELoanObject;

public class JMMEMELoanSet {
	/**
	 * @describe 该方法用于实例化么么贷实体类
	 * @param map
	 * @return
	 */ 			//     (String)map.get("")
	public JMMEMELoanObject setJMMEMELoanObject(HashMap map){
		JMMEMELoanObject jo = new JMMEMELoanObject();
		jo.setChineseName((String)map.get("ChineseName"));//融资人名称
		jo.setNational((String)map.get("National"));//民族
		jo.setBirthday((String)map.get("Birthday"));//出生日期
		jo.setAge((String)map.get("Age"));//年龄
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//证件号码
		jo.setIdentityType((String)map.get("IdentityType"));//证件类型
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//婚姻状况
		jo.setEmail((String)map.get("Email"));//电子邮箱
		jo.setPhone((String)map.get("Phone"));//手机号码
		jo.setPostAddress((String)map.get("PostAddress"));//通讯地址
		jo.setPostCode((String)map.get("PostCode"));//邮政编码
		jo.setThirdPartyScore((String)map.get("ThirdPartyScore"));//第三方评级
		jo.setAcademic((String)map.get("Academic"));//学历
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("CheckedAmount"))));//经核实的月收入
		jo.setCompanyName((String)map.get("CompanyName"));//工作单位
		jo.setCompanyNature((String)map.get("CompanyNature"));//单位性质
		jo.setPosition((String)map.get("Position"));//职务
		jo.setCollege((String)map.get("College"));//院系
		jo.setDegreeCategory((String)map.get("DegreeCategory"));//学位类别
		jo.setGraduateDate((String)map.get("GraduateDate"));//毕业时间
		jo.setInSchoolID((String)map.get("InSchoolID"));//学号
		jo.setSchoolName((String)map.get("SchoolName"));//学校名称
		jo.setSpecialty((String)map.get("Specialty"));//专业
		jo.setSchoolEntranceDate((String)map.get("SchoolEntranceDate"));//入学时间
		jo.setSchoolYears((String)map.get("SchoolYears"));//年限
		jo.setMobile((String)map.get("Mobile"));//手机号码
		jo.setQQ((String)map.get("QQ"));//QQ
		jo.setRoomAddress((String)map.get("RoomAddress"));//宿舍地址
		jo.setWeChat((String)map.get("WeChat"));//WeChat
		jo.setRelationName((String)map.get("RelationName"));//姓名
		jo.setRelationPhone((String)map.get("RelationPhone"));//手机
		jo.setRelationship((String)map.get("Relationship"));//关系
		jo.setCardType((String)map.get("CardType"));//卡种
		jo.setBrands((String)map.get("Brands"));//卡品牌
		jo.setCardNumber((String)map.get("CardNumber"));//卡号
		jo.setDealCount((String)map.get("DealCount"));//交易笔数
		jo.setFirstDealDate((String)map.get("FirstDealDate"));//次交易日期
		jo.setLastDealDate((String)map.get("LastDealDate"));//最新交易日期
		jo.setLevel((String)map.get("Level"));//卡等级
		jo.setLocation((String)map.get("Location"));//开户行所在地
		jo.setProduct((String)map.get("Product"));//卡产品
		jo.setProperty((String)map.get("Property"));//卡性质
		jo.setStatus((String)map.get("Status"));//鉴权状态
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("TotalInAmt"))));//交易总金额（收）
		jo.setCheckedAmount(Double.parseDouble(toDouble((String) map.get("TotalOutAmt"))));//交易总金额（支）
		jo.setUnfinishedCount((String)map.get("UnfinishedCount"));//未结清笔数
		jo.setFinishedCount((String)map.get("FinishedCount"));//已结清笔数
		jo.setApplyCount((String)map.get("ApplyCount"));//申请中笔数
		jo.setPassedCount((String)map.get("PassedCount"));//通过笔数
		jo.setDeniedCount((String)map.get("DeniedCount"));//拒绝笔数
		jo.setQueryCount((String)map.get("QueryCount"));//近两年查询次数
		jo.setOtherCount((String)map.get("OtherCount"));//"申请/债权/逾期/补录/不良"
		jo.setNFCSDebtCount((String)map.get("NFCSDebtCount"));//贷款笔数
		jo.setNFCSFirstDebtDate((String)map.get("NFCSFirstDebtDate"));//首贷日
		jo.setNFCSMaxCreditAmt(Double.parseDouble(toDouble((String) map.get("NFCSMaxCreditAmt"))));//最大授信额度
		jo.setNFCSTotalAmt(Double.parseDouble(toDouble((String) map.get("NFCSTotalAmt"))));//贷款总额
		jo.setNFCSValidAmt(Double.parseDouble(toDouble((String) map.get("NFCSValidAmt"))));//贷款余额
		jo.setNFCSRepaymentByMonth(Double.parseDouble(toDouble((String) map.get("NFCSRepaymentByMonth"))));//协定月还款
		jo.setNFCSTotalOverDueAmt(Double.parseDouble(toDouble((String) map.get("NFCSTotalOverDueAmt"))));//当前逾期总额
		jo.setNFCSMaxOverDueAmt(Double.parseDouble(toDouble((String) map.get("NFCSMaxOverDueAmt"))));//最高逾期金额
		jo.setNFCSOverDueCount((String)map.get("NFCSOverDueCount"));//最高逾期期数
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//贷款金额
		jo.setBatch((String)map.get("Batch"));//贷款期限
		jo.setLenderAmount(Double.parseDouble(toDouble((String) map.get("LenderAmount"))));//放款金额
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//每月还款日
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//还款起日期
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//还款止日期
		jo.setPayDate((String)map.get("PayDate"));//放款时间
		jo.setApplyCity((String)map.get("ApplyCity"));//贷款提交城市
		jo.setType((String)map.get("Type"));//产品名称
		jo.setEstimateAmt(Double.parseDouble(toDouble((String) map.get("EstimateAmt"))));//预估额度
		jo.setTotalAmt(Double.parseDouble(toDouble((String) map.get("TotalAmt"))));//额度
		jo.setValidAmt(Double.parseDouble(toDouble((String) map.get("ValidAmt"))));//ValidAmt
		jo.setUsedAmt(Double.parseDouble(toDouble((String) map.get("UsedAmt"))));//已用额度
		jo.setSPID((String)map.get("SPID"));//SPID
		jo.setOldlcno((String)map.get("ProjectNo"));//渠道項目編號唯一標識return jo;
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
