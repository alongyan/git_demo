package com.amarsoft.server.object;

import java.util.HashMap;

import com.amarsoft.server.dao.JMTaoJinJiaObject;

/**
 * @describe 实例化淘金家实体类
 * @param map
 * @author yhwang 20150721
 * @return
 */
public class JMTaoJinJiaSet {
	
	/*
	 * 给淘金家实体类赋值
	 */
	public JMTaoJinJiaObject setJMTaoJinJiaObject(HashMap map){
		JMTaoJinJiaObject jo = new JMTaoJinJiaObject();
		jo.setProjectNo((String)map.get("ProjectNo"));//项目编号
		jo.setType((String)map.get("Type"));//产品类型
		jo.setFinancingAmount(Double.parseDouble(toDouble((String) map.get("FinancingAmount"))));//意向融资金额
		jo.setBatch((String)map.get("Batch"));//期限
		jo.setApplyCity((String)map.get("ApplyCity"));//贷款提交城市
		jo.setChineseName((String)map.get("ChineseName"));//姓名
		jo.setIdentityNumber((String)map.get("IdentityNumber"));//身份证号码
		jo.setSex((String)map.get("Sex"));//性别
		jo.setMaritalStatus((String)map.get("MaritalStatus"));//婚姻状况
		jo.setAge((String)map.get("Age"));//年龄
		jo.setCompanyName((String)map.get("CompanyName"));//工作单位
		jo.setAcademic((String)map.get("Academic"));//学历
		jo.setCompanyNature((String)map.get("CompanyNature"));//单位性质
		jo.setPosition((String)map.get("Position"));//职务
		jo.setWorkYears((String)map.get("WorkYears"));//总工龄
		jo.setDebteeName((String)map.get("DebteeName"));//债权人姓名
		jo.setDebteeID((String)map.get("DebteeID"));//债券人身份证号
		jo.setDebteeRelation((String)map.get("DebteeRelation"));//与借款人关系
		jo.setUserName((String)map.get("UserName"));//债权人积木盒子用户名
		jo.setAccountNo((String)map.get("AccountNo"));//债权人银行卡号
		jo.setRepaymentStartDate((String)map.get("RepaymentStartDate"));//贷款起始日
		jo.setRepaymentEndDate((String)map.get("RepaymentEndDate"));//贷款到期日
		jo.setRepaymentDay((String)map.get("RepaymentDay"));//每月还息日
		jo.setSPID((String)map.get("SPID"));//渠道編號
		return jo;
	}
	
	private String toDouble(String b) {
		return (b == null||b.equals("")) ? "0" : b;
	}
}
