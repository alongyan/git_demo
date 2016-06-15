package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.are.security.DESEncrypt;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe 该方法用于处理业务申请基本信息内容
 * @author jxsun
 *
 */
public class HandleApplyInfoCJ {
	private Logger logger = Logger.getLogger(HandleApplyInfoCJ.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//机构编号
	private String sUserID = "";//用户编号
	private String sBusinessTypeName ="";//产品名称
	private Double sGoodsprice = 0.0;//商品金额
	private Double sFirstPaySum = 0.0;//首付金额
	private Double sFirstPayPercent = 0.0;//首付比例
	private Double sBusinessSum = 0.0;//贷款本金
	private Double sCreditNum = 0.0;//分期期数
	private String sCustomerID = "";//客户编号
	private String sCustomerName = "";//客户名称
	private Double sRepaySumPermonth = 0.0;//每月还款额
	private String sRepayDatePermonth = "";//每月还款日
	private String sIsInsure = "";//是否投保
	private String sGoodsCount = "";//商品数量
	private String sRepayMode = "";//还款方式
	private String sLoanAccountNo = "";//代扣/放款账号
	private String sShopNumber = "";//门店编号
	private String sSaleShop = "";//销售门店
	private String sSalePerson = "";//销售代表
	private String sSaleManager = "";//销售经理
	private String sSalesMan = "";//零售商促销员
	private String sSalesManPhoneno = "";//联系电话
	private String sInputDate = "";//录单日期
	private String sUpdateDate = "";//更新日期
	private String sInputUserID = "";//登记人
	private String sInputOrgID = "";//登记机构
	private String sRate = "";//利率
	private String sARate = "";//A利率
	private String sBRate = "";//B利率
	private String sCRate = "";//C利率
	private String sInsurance = "";//保险费
	private String sDRate = "";//月风险准备金率
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleApplyInfoCJ(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sOrgID,String sUserID,String sCustomerID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sCustomerID = sCustomerID;
	}
	/**
	 * @throws Exception 
	 * @describe 该方法用于处理业务申请信息模块内容
	 */
	public String handleApplyInfoMessage() throws Exception{
		String sSerialNo = "";
		getApplyInfo();//获取hashMap中包含申请信息的所有值
		sSerialNo = insertApplyInfo();//处理业务申请信息
		return sSerialNo ;
	}
	/**
	 * @describe 从hashMap中获取信息要素
	 * @throws Exception
	 */
	private void getApplyInfo() throws Exception {
		try {
			sBusinessTypeName = (String) hashMap.get("BusinessTypeName");
			sGoodsprice =  Double.parseDouble(hashMap.get("GoodsPrice").toString());
			sFirstPaySum = Double.parseDouble(hashMap.get("FirstPaySum").toString());
			sFirstPayPercent = Double.parseDouble(hashMap.get("FirstPayPercent").toString())/100;
			sBusinessSum = Double.parseDouble(hashMap.get("BusinessSum").toString());
			sCreditNum = Double.parseDouble(hashMap.get("CreditNum").toString());
			sCustomerName = (String) hashMap.get("CustomerName");
			sRepaySumPermonth = Double.parseDouble(hashMap.get("RepaySumPermonth").toString());
			sRepayDatePermonth = (String) hashMap.get("RepayDatePermonth");
			sIsInsure = (String) hashMap.get("IsInsure");
			sGoodsCount = (String) hashMap.get("GoodsCount");
			sRepayMode = (String) hashMap.get("RepayMode");
			sLoanAccountNo = (String) hashMap.get("LoanAccountNo");
			sShopNumber = (String) hashMap.get("ShopNumber");
			sSaleShop = (String) hashMap.get("SaleShop");
			sSalePerson = (String) hashMap.get("SalePerson");
			sSaleManager = (String) hashMap.get("SaleManager");
			sSalesMan = (String) hashMap.get("SalesMan");
			sSalesManPhoneno = (String) hashMap.get("SalesManPhoneno");
			sInputDate = (String) hashMap.get("InputDate");
			sUpdateDate = (String) hashMap.get("UpdateDate");
			sInputUserID = (String) hashMap.get("InputUserID");
			sInputOrgID = (String) hashMap.get("InputOrgID");
			sRate = (String) hashMap.get("Rate");
			sARate = (String) hashMap.get("ARate");
			sBRate = (String) hashMap.get("BRate");
			sCRate = (String) hashMap.get("CRate");
			sInsurance = (String) hashMap.get("Insurance");
			sDRate = (String) hashMap.get("DRate");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取数据异常");
			throw e;
		}
	}
	/**
	 * @describe 该方法用于在业务申请表中插入业务数据
	 * @return SerialNo
	 * @throws Exception 
	 */
	private String insertApplyInfo() throws Exception{
		String sSerialNo = "";//业务申请编号
		try {
			sSerialNo = "BA"+DBFunction.getSerialNo("BUSINESS_APPLY", "SERIALNO");//获取申请阶段业务申请编号
			String sLoanID = "L"+DBFunction.getSerialNo("BUSINESS_APPLY", "LOANID");//获取申请阶段贷款编号
			String sSql = "insert into Business_apply (SerialNo,BusinessTypeName,Goodsprice,FirstPaySum,FirstPayPercent,BusinessSum,CreditNum,CustomerID ,CustomerName,RepaySumPermonth,RepayDatePermonth,IsInsure,GoodsCount,RepayMode,LoanAccountNo,ShopNumber,SaleShop,SalePerson,SaleManager,SalesMan,SalesManPhoneno,InputDate,UpdateDate,InputUserID,InputOrgID,Rate,ARate,BRate,CRate,Insurance,DRate,LoanID)"+
			" values(:SerialNo,:BusinessTypeName,:Goodsprice,:FirstPaySum,:FirstPayPercent,:BusinessSum,:CreditNum,:CustomerID ,:CustomerName,:RepaySumPermonth,:RepayDatePermonth,:IsInsure,:GoodsCount,:RepayMode,:LoanAccountNo,:ShopNumber,:SaleShop,:SalePerson,:SaleManager,:SalesMan,:SalesManPhoneno,:InputDate,:UpdateDate,:InputUserID,:InputOrgID,:Rate,:ARate,:BRate,:CRate,:Insurance,:DRate,:LoanID)";
			
			SQLHandleInfo sSqlHandle = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sSerialNo)//申请业务编号
			.setParameter("BusinessTypeName", sBusinessTypeName).setParameter("Goodsprice",sGoodsprice)
			.setParameter("FirstPaySum",sFirstPaySum)
			.setParameter("FirstPayPercent",sFirstPayPercent)
			.setParameter("BusinessSum",sBusinessSum)
			.setParameter("CreditNum",sCreditNum)
			.setParameter("CustomerID",sCustomerID).setParameter("CustomerName", sCustomerName)
			.setParameter("RepaySumPermonth",sRepaySumPermonth)
			.setParameter("RepayDatePermonth", sRepayDatePermonth)
			.setParameter("IsInsure", Tools.getCodeItemNo("YesNo", Tools.getObjectToString(hashMap.get("IsInsure")), sqlQuery))
			.setParameter("GoodsCount", sGoodsCount)
			.setParameter("RepayMode", Tools.getCodeItemNo("RepayMode", Tools.getObjectToString(hashMap.get("RepayMode")), sqlQuery))
			.setParameter("LoanAccountNo", DESEncrypt.encrypt(sLoanAccountNo, "JiMuHezi"))
			.setParameter("ShopNumber", sShopNumber).setParameter("SaleShop", sSaleShop)
			.setParameter("SalePerson", sSalePerson).setParameter("SaleManager", sSaleManager)
			.setParameter("SalesMan", sSalesMan).setParameter("SalesManPhoneno", sSalesManPhoneno)
			.setParameter("InputDate", sInputDate).setParameter("UpdateDate", sUpdateDate)
			.setParameter("InputUserID", sInputUserID).setParameter("InputOrgID", sInputOrgID)
			.setParameter("Rate", sRate).setParameter("ARate", sARate)
			.setParameter("BRate", sBRate).setParameter("CRate", sCRate)
			.setParameter("Insurance", sInsurance).setParameter("DRate", sDRate).setParameter("LoanID", sLoanID);
			logger.info("获取业务信息执行脚本"+sSqlHandle.getSql());
			sqlQuery.execute(sSqlHandle.getSql());//执行脚本
			
			sSql = "insert into flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"OrgID,UserID,InputDate) values (:ObjectType,:ObjectNo,:PhaseType,:ApplyType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,"+
			":OrgID,:UserID,:InputDate)";
			
			SQLHandleInfo sSqlHandleFlow = new SQLHandleInfo(sSql)
			.setParameter("ObjectType", "CreditLoanApply")//类型
			.setParameter("ObjectNo", sSerialNo)//业务申请编号
			.setParameter("PhaseType", "1010")//流程阶段默认为第一岗位
			.setParameter("ApplyType", "CreditLoanApply")//零售业务申请类型默认值
			.setParameter("FlowNo", "CreditLoanFlow")//申请流程编号
			.setParameter("FlowName", "贷款审批流程")//流程名称
			.setParameter("PhaseNo", "0010")//阶段编号
			.setParameter("PhaseName", "待申请阶段")//阶段名称
			.setParameter("OrgID", this.sOrgID)//机构号
			.setParameter("UserID", this.sUserID)//用户编号
			.setParameter("InputDate", Tools.getToday(2));//获取当前日期
			
			logger.info("业务流程脚本语句 "+sSqlHandleFlow.getSql());
			this.sqlQuery.execute(sSqlHandleFlow.getSql());
			
			String sTaskSerialNo = DBFunction.getSerialNo("Flow_task", "SerialNo");//获取流程编号
			
			sSql = "insert into flow_task (SerialNo,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"PhaseType,ApplyType,UserID,OrgID,BeginTime) values (:SerialNo,:ObjectNo,"+
			":ObjectType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,:PhaseType,:ApplyType,:UserID,"+
			":OrgID,:BeginTime)";
			
			SQLHandleInfo sSqlHandleFlowTask = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sTaskSerialNo)//审批流程TaskNo
			.setParameter("ObjectNo", sSerialNo)//业务申请编号
			.setParameter("ObjectType", "CreditLoanApply")//业务类型
			.setParameter("FlowNo", "CreditLoanFlow")//审批流程编号
			.setParameter("FlowName", "贷款审批流程")//审批流程名称
			.setParameter("PhaseNo", "0010")//流程节点编号
			.setParameter("PhaseName", "待申请阶段")//流程节点名称
			.setParameter("PhaseType", "1010")//设置默认为第一岗位
			.setParameter("ApplyType", "CreditLoanApply")//业务类型
			.setParameter("UserID", this.sUserID)
			.setParameter("OrgID", this.sOrgID)//机构编号
			.setParameter("BeginTime", Tools.getDate());
			
			logger.info("业务流程脚本语句 "+sSqlHandleFlowTask.getSql());
			this.sqlQuery.execute(sSqlHandleFlowTask.getSql());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取业务申请编号出现错误");
			e.printStackTrace();
			sSerialNo = "Fail";
			throw e;
		}
		return sSerialNo;
	}
}
