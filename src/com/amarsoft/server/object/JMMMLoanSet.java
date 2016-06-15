package com.amarsoft.server.object;

import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.JMMMLoanObject;

public class JMMMLoanSet {
	/**
	 * @describe 该方法用于实例化买单侠贷实体类
	 * @param requestMap
	 * @return
	 * @xlsun 2015-07-20
	 */
	public JMMMLoanObject setJMMMLoanObject(Map<String, Object> requestMap){
		JMMMLoanObject jo = new JMMMLoanObject();
		jo.setChineseName((String)requestMap.get("ChineseName"));//融资人名称
		jo.setIdentityNumber((String)requestMap.get("IdentityNumber"));//身份证
		jo.setSex((String)requestMap.get("Sex"));//性别
		jo.setPhone((String)requestMap.get("Phone"));//电话
		jo.setMaritalStatus((String)requestMap.get("MaritalStatus"));//婚姻情况
		jo.setCompanyName((String)requestMap.get("CompanyName"));//工作单位
		jo.setCompanyNature((String)requestMap.get("CompanyNature"));//工作性质
		jo.setCompanyTel((String)requestMap.get("CompanyTel"));//工作地址电话
		jo.setRelaName((String)requestMap.get("RelaName"));//姓名
		jo.setRelaPhone((String)requestMap.get("RelaPhone"));//联系电话
		jo.setRelationship((String)requestMap.get("Relationship"));//关系
		jo.setHasBadRecord((String)requestMap.get("HasBadRecord"));//有无不良记录
		jo.setProductType((String)requestMap.get("ProductType"));//产品类型
		jo.setBatch((String)requestMap.get("Batch"));//期限月
		jo.setUserName((String)requestMap.get("UserName"));//融资用户名
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) requestMap.get("FinancingAmount"))));//融资金额
		jo.setLenderAmount(Double.parseDouble(toDouble((String) requestMap.get("LenderAmount"))));//融资人实收金额
		jo.setHJXD_RepaymentByMonth(Double.parseDouble(toDouble((String) requestMap.get("HJXD_RepaymentByMonth"))));//月偿还本息数额
		jo.setRepaymentDay((String)requestMap.get("RepaymentDay"));//还款日
		jo.setRepaymentStartDate((String)requestMap.get("RepaymentStartDate"));//还款起日期
		jo.setRepaymentEndDate((String)requestMap.get("RepaymentEndDate"));//还款止日期
		jo.setHJXD_LastMonthAmount(Double.parseDouble(toDouble((String) requestMap.get("HJXD_LastMonthAmount"))));//最后一期还本息数额
		jo.setApplyCity((String)requestMap.get("ApplyCity"));//贷款提交城市
		jo.setProductName((String)requestMap.get("ProductName"));//产品名称
		jo.setSalePrice(Double.parseDouble(toDouble((String) requestMap.get("SalePrice"))));//销售价格
		jo.setDownPaymentAmount(Double.parseDouble(toDouble((String) requestMap.get("DownPaymentAmount"))));//首付金额
		jo.setAltName((String)requestMap.get("AltName"));//姓名
		jo.setAltID((String)requestMap.get("AltID"));//身份证号码
		jo.setAltRelation((String)requestMap.get("AltRelation"));//关系
		jo.setBankName((String)requestMap.get("BankName"));//开户银行名称
		jo.setAccountNo((String)requestMap.get("AccountNo"));//开户银行账号
		jo.setMerchantName((String)requestMap.get("MerchantName"));//合作商户名称
		jo.setMerchantProvince((String)requestMap.get("MerchantProvince"));//商户省份
		jo.setMerchantCity((String)requestMap.get("MerchantCity"));//商户城市
		jo.setSPID((String)requestMap.get("SPID"));//渠道編號
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
