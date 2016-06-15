package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @describe 该方法用于插入个人债务信息
 * @author yhwang
 *
 */
public class HandleCustomerDebt {
	private Logger logger = Logger.getLogger(HandleCustomerDebt.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//机构编号
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleCustomerDebt(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sOrgID,String sUserID,String sCustomerID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sCustomerID = sCustomerID;
	}
	
	/**
	 * @describe 该方法用于处理个人债务信息模块
	 */
	public void handleCustomerDebt() throws Exception{
		String message = this.hashMap.get("DebtInfoReportsPersonlMortage")+"";
		message = "{\"Debt\":"+message+"}";
		JSONObject json = JSONObject.fromObject(message);
		JSONArray arrayJson = json.getJSONArray("Debt");
		deleteCustomerRelation();
		for(int i=0;i<arrayJson.size();i++){
			insertCustomerDebt(arrayJson.get(i).toString());
		}
	}
	
	/**
	 * @throws Exception 
	 * @describe 改方法用于清除个人债务信息
	 */
	private void deleteCustomerRelation() throws Exception{
		String sSql = "delete from debt_info where CUSTOMERID = '"+this.sCustomerID+"'";
		try {
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-----------------删除债务信息出错-----------");
			throw e;
		}
	}
	/**
	 * @describe 该方法用于插入个人债务信息
	 */
	private void insertCustomerDebt(String debtInfo) throws Exception{
		JSONObject json = JSONObject.fromObject(debtInfo);
		
		System.out.println("+++++++"+Double.parseDouble(Tools.getObjectToString(json.get("Borrowingamount"))));
		String sSql ="";
		String sSerialNo = "";
		try {
			sSerialNo = DBFunction.getSerialNo("DEBT_INFO", "DebtID");
		    sSql = "insert into debt_info (DebtID,CustomerID,InputUserID,InputOrgID,InputDate,Creditors ,Debttype ,Borrowingamount ,Bostartdate ,Boenddate ,Isroll ,Bobalance ,"
		    	+" Repaytype ,Credentialstype ,Guaranteestatus ,Ensuretype ,Loantype ,Mortgaged ,Debtinfo ,Remark)" 
                +" values(:DebtID,:CustomerID,:InputUserID,:InputOrgID,:InputDate,:Creditors ,:Debttype ,:Borrowingamount ," 
                +":Bostartdate ,:Boenddate ,:Isroll ,:Bobalance ,:Repaytype ,:Credentialstype ,:Guaranteestatus ,:Ensuretype ,:Loantype ,"
		        +":Mortgaged ,:Debtinfo ,:Remark) ";
						SQLHandleInfo sSqlHandleRelation = new SQLHandleInfo(sSql)
						.setParameter("DebtID", sSerialNo)//流水号
						.setParameter("CustomerID", this.sCustomerID)//客户编号
						.setParameter("InputUserID", this.sUserID)
						.setParameter("InputOrgID", this.sOrgID)
						.setParameter("InputDate", Tools.getToday(2))
						.setParameter("Creditors",Tools.getObjectToString(json.get("Creditors")))//债权人
						.setParameter("Debttype",Tools.getCodeItemNo("DebtType",Tools.getObjectToString(json.get("Debttype")), sqlQuery))//债务类型
						.setParameter("Borrowingamount",Double.parseDouble(Tools.getObjectToString(json.get("Borrowingamount"))))//借款金额
						.setParameter("Bostartdate",Tools.getObjectToString(json.get("Bostartdate")))//借款开始日期
						.setParameter("Boenddate",Tools.getObjectToString(json.get("Boenddate")))//借款截止日期
						.setParameter("Isroll",Tools.getCodeItemNo("YesNo",Tools.getObjectToString(json.get("Isroll")), sqlQuery))//是否展期
						.setParameter("Bobalance",Double.parseDouble(Tools.getObjectToString(json.get("Bobalance"))))//借款余额
						.setParameter("Repaytype",Tools.getCodeItemNo("Repaytype1",Tools.getObjectToString(json.get("Repaytype")), sqlQuery))//偿还 方式
						.setParameter("Credentialstype",Tools.getCodeItemNo("CredentialsType",Tools.getObjectToString(json.get("Credentialstype")), sqlQuery))//凭证方式
						.setParameter("Guaranteestatus",Tools.getCodeItemNo("GuaranteeStatus1",Tools.getObjectToString(json.get("Guaranteestatus")), sqlQuery))//担保情况
						.setParameter("Ensuretype",Tools.getCodeItemNo("GuaranteeStatus",Tools.getObjectToString(json.get("Ensuretype")), sqlQuery))//保证方式
						.setParameter("Loantype",Tools.getCodeItemNo("loanType1",Tools.getObjectToString(json.get("Loantype")), sqlQuery))//贷款类别
						.setParameter("Mortgaged",Tools.getObjectToString(json.get("Mortgaged")))//抵押物或者保证人
						.setParameter("Debtinfo",Tools.getObjectToString(json.get("Debtinfo")))//以往债务情况说明
						.setParameter("Remark",Tools.getObjectToString(json.get("Remark")));//备注
						logger.info("执行个人债务信息 "+sSqlHandleRelation.getSql());
						this.sqlQuery.execute(sSqlHandleRelation.getSql());
		} catch (Exception e) {
			logger.info("执行个人债务信息");
			e.printStackTrace();
			throw e;
		}
	}
}
