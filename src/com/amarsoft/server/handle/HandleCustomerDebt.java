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
 * @describe �÷������ڲ������ծ����Ϣ
 * @author yhwang
 *
 */
public class HandleCustomerDebt {
	private Logger logger = Logger.getLogger(HandleCustomerDebt.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//�������
	private String sUserID = "";//�û����
	private String sCustomerID = "";//�ͻ����
	
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
	 * @describe �÷������ڴ������ծ����Ϣģ��
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
	 * @describe �ķ��������������ծ����Ϣ
	 */
	private void deleteCustomerRelation() throws Exception{
		String sSql = "delete from debt_info where CUSTOMERID = '"+this.sCustomerID+"'";
		try {
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-----------------ɾ��ծ����Ϣ����-----------");
			throw e;
		}
	}
	/**
	 * @describe �÷������ڲ������ծ����Ϣ
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
						.setParameter("DebtID", sSerialNo)//��ˮ��
						.setParameter("CustomerID", this.sCustomerID)//�ͻ����
						.setParameter("InputUserID", this.sUserID)
						.setParameter("InputOrgID", this.sOrgID)
						.setParameter("InputDate", Tools.getToday(2))
						.setParameter("Creditors",Tools.getObjectToString(json.get("Creditors")))//ծȨ��
						.setParameter("Debttype",Tools.getCodeItemNo("DebtType",Tools.getObjectToString(json.get("Debttype")), sqlQuery))//ծ������
						.setParameter("Borrowingamount",Double.parseDouble(Tools.getObjectToString(json.get("Borrowingamount"))))//�����
						.setParameter("Bostartdate",Tools.getObjectToString(json.get("Bostartdate")))//��ʼ����
						.setParameter("Boenddate",Tools.getObjectToString(json.get("Boenddate")))//����ֹ����
						.setParameter("Isroll",Tools.getCodeItemNo("YesNo",Tools.getObjectToString(json.get("Isroll")), sqlQuery))//�Ƿ�չ��
						.setParameter("Bobalance",Double.parseDouble(Tools.getObjectToString(json.get("Bobalance"))))//������
						.setParameter("Repaytype",Tools.getCodeItemNo("Repaytype1",Tools.getObjectToString(json.get("Repaytype")), sqlQuery))//���� ��ʽ
						.setParameter("Credentialstype",Tools.getCodeItemNo("CredentialsType",Tools.getObjectToString(json.get("Credentialstype")), sqlQuery))//ƾ֤��ʽ
						.setParameter("Guaranteestatus",Tools.getCodeItemNo("GuaranteeStatus1",Tools.getObjectToString(json.get("Guaranteestatus")), sqlQuery))//�������
						.setParameter("Ensuretype",Tools.getCodeItemNo("GuaranteeStatus",Tools.getObjectToString(json.get("Ensuretype")), sqlQuery))//��֤��ʽ
						.setParameter("Loantype",Tools.getCodeItemNo("loanType1",Tools.getObjectToString(json.get("Loantype")), sqlQuery))//�������
						.setParameter("Mortgaged",Tools.getObjectToString(json.get("Mortgaged")))//��Ѻ����߱�֤��
						.setParameter("Debtinfo",Tools.getObjectToString(json.get("Debtinfo")))//����ծ�����˵��
						.setParameter("Remark",Tools.getObjectToString(json.get("Remark")));//��ע
						logger.info("ִ�и���ծ����Ϣ "+sSqlHandleRelation.getSql());
						this.sqlQuery.execute(sSqlHandleRelation.getSql());
		} catch (Exception e) {
			logger.info("ִ�и���ծ����Ϣ");
			e.printStackTrace();
			throw e;
		}
	}
}
