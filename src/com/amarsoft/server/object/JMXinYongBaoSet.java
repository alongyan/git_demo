package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMXinYongBaoObject;


public class JMXinYongBaoSet {
	/**
	 * @describe 该方法用于实例化信用宝实体类
	 * @param map
	 * @return
	 */ 			
	public JMXinYongBaoObject setJMXinYongBaoObject(HashMap map){
		JMXinYongBaoObject jo = new JMXinYongBaoObject();
		jo.setChineseName((String)map.get("ChineseName"));//中文姓名 
		jo.setAge((String)map.get("Age"));//年龄
		jo.setSex((String)map.get("Sex"));//性别
		jo.setAge((String)map.get("Age"));//年龄
		jo.setPhone((String)map.get("Phone"));//手机号码
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//证件号码
		jo.setPostAddress((String)map.get("PostAddress"));//通讯地址
		jo.setAccountLocation((String)map.get("AccountLocation"));//户籍
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//婚姻状况
		jo.setThirdPartyScore((String)map.get("ThirdPartyScore"));//第三方评级
		jo.setBirthday((String)map.get("Birthday"));//出生日期
		jo.setSpouseName((String)map.get("SpouseName"));//配偶姓名
		jo.setSpouseID((String)map.get("SpouseID"));//配偶省份证号
		jo.setSpousePhone((String) map.get("SpousePhone"));//配偶手机号
		jo.setSpouseWorkUnit((String)map.get("SpouseWorkUnit"));//配偶工作单位名称
		jo.setSpouseJob((String)map.get("SpouseJob"));//配偶职务
		jo.setHasCar((String)map.get("HasCar"));//家庭汽车情况
		jo.setHasHouse((String)map.get("HasHouse"));//有房产
		jo.setCompanyName((String)map.get("CompanyName"));//工作单位
		jo.setCompanyNature((String)map.get("CompanyNature"));//单位性质
		jo.setCompanyTel((String)map.get("CompanyTel"));//单位座机
		jo.setCompanyAddress((String)map.get("CompanyAddress"));//单位地址
		jo.setWorkStartDate((String)map.get("WorkStartDate"));//本工作起始日期
		jo.setPosition((String)map.get("Position"));//职务
		jo.setWorkYears((String)map.get("WorkYears"));//总工龄
		jo.setAcademic((String)map.get("Academic"));//学历
		jo.setCheckedAmount(Double.parseDouble(toDouble((String)map.get("CheckedAmount"))));//经核实的月收入
		jo.setCheckedLoanInfo3Month((String)map.get("CheckedLoanInfo3Month"));//近3个月贷款审批征信查询次数
		jo.setWorstRepaymentLastTwoYears((String)map.get("WorstRepaymentLastTwoYears"));//过去2年中最差还款状态 
		jo.setTotalOverDueLastTwoYears((String)map.get("TotalOverDueLastTwoYears"));//过去2年中累计逾期次数
		jo.setOperatEntity((String)map.get("OperatEntity"));//经营主体名称
		jo.setOperatEntityType((String)map.get("OperatEntityType"));//经营主体类型 
		jo.setFoundYears((String)map.get("FoundYears"));//成立年限
		jo.setOperationStartDate((String)map.get("OperationStartDate"));//实际经营起始时间 
		jo.setCurrentOperatAddr((String)map.get("CurrentOperatAddr"));//当前经营地址
		jo.setShareHolderRate(Double.parseDouble(toDouble((String)map.get("ShareHolderRate"))));//融资人持股比例
		jo.setEmployeeCount((String)map.get("EmployeeCount"));//员工数量
		jo.setAccountInFlowPerMonth(Double.parseDouble(toDouble((String)map.get("AccountInFlowPerMonth"))));//经营实体账户月均流入金额
		jo.setType((String)map.get("Type"));//产品类型
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//融资金额（元）
		jo.setBatch((String)map.get("Batch"));//贷款期限（月）
		jo.setDebtUsage((String)map.get("DebtUsage"));//贷款用途
		jo.setLenderAmount(Double.parseDouble(toDouble(toDouble((String)map.get("LenderAmount")))));//融资人实收金额
		jo.setHouseType((String) map.get("HouseType"));//房产类型
		jo.setRepaymentAmountByMonth(Double.parseDouble(toDouble((String) map.get("RepaymentAmountByMonth"))));//月偿还本息数额
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//还款日
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//还款起日期
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//还款止日期
		jo.setHJXD_LastMonthAmount(Double.parseDouble(toDouble((String)map.get("HJXD_LastMonthAmount"))));//最后一期还本息数额
		jo.setApplyCity((String)map.get("ApplyCity"));//贷款提交城市
		jo.setEstateType((String)map.get("EstateType"));//房产类型
		jo.setRealEstateLocations((String)map.get("RealEstateLocations"));//房产所在地
		jo.setConstructionArea(Double.parseDouble(toDouble((String)map.get("ConstructionArea"))));//建筑面积
		jo.setPurchaseDate((String)map.get("PurchaseDate"));//购买时间
		jo.setHouseTotalValue(Double.parseDouble(toDouble((String) map.get("HouseTotalValue"))));//房产总价 
		jo.setDebteeRelation((String) map.get("DebteeRelation"));//关系
		jo.setDebteeName((String) map.get("DebteeName"));//姓名
		jo.setDebteeID((String) map.get("DebteeID"));//身份证号
		jo.setDebteeMobile((String) map.get("DebteeMobile"));//手机号码
		jo.setDebteeMailingAddress((String) map.get("DebteeMailingAddress"));//通讯地址
		jo.setAccountNo((String)map.get("AccountNo"));//开户银行账号
		jo.setBankName((String) map.get("BankName"));//开户银行名称
		jo.setProvence((String)map.get("Provence"));//开户银行省份
		jo.setArea((String) map.get("Area"));//开户银行地区
		jo.setSubbranchBank((String)map.get("SubbranchBank"));//开户银行支行
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
