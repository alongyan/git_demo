package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷������ڲ���ͻ�������Ϣ
 * @author Sun
 *
 */
public class HandleCustomerRelation {
	private Logger logger = Logger.getLogger(HandleCustomerRelation.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//�������
	private String sUserID = "";//�û����
	private String sCustomerID = "";//�ͻ����
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleCustomerRelation(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sOrgID,String sUserID,String sCustomerID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sCustomerID = sCustomerID;
	}
	/**
	 * @throws Exception 
	 * @describe �÷������ڴ���ҵ��������Ϣģ������
	 */
	public void handleCustomerRelation() throws Exception{
		String message = this.hashMap.get("ContactInformation")+"";
		message = "{\"Relation\":"+message+"}";
		JSONObject json = JSONObject.fromObject(message);
		JSONArray arrayJson = json.getJSONArray("Relation");
		deleteCustomerRelation();//�����������Ϣ����
		for(int i=0;i<arrayJson.size();i++){
			insertCustomerRelation(arrayJson.get(i).toString());
		}
	}
	/**
	 * @throws Exception 
	 * @describe �ķ������������������Ϣ����
	 */
	private void deleteCustomerRelation() throws Exception{
		String sSql = "delete from CUSTOMER_CONTACT where CUSTOMERID = '"+this.sCustomerID+"'";
		try {
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----ɾ����������Ϣ����----");
			throw e;
		}
	}
	/**
	 * @throws Exception 
	 * @describe �ķ������ڴ���ͻ���������Ϣ������
	 */
	private void insertCustomerRelation(String sJsonStr) throws Exception{
		JSONObject jsonObject = JSONObject.fromObject(sJsonStr);
		System.out.println("===================������========"+sJsonStr);
		System.out.println("============"+jsonObject.get("ContactPost"));
		String sSerialNo = "";
		try {
			sSerialNo = DBFunction.getSerialNo("CUSTOMER_CONTACT", "SerialNo");
			String sSql = " insert into CUSTOMER_CONTACT (CUSTOMERID, RELATIONSHIP, RELATIONNAME, RELATIONTEL, RELATIONFIXTEL,"+
					" FAMILYADD, DESCRIBE, REMARK, INPUTORGID, INPUTUSERID, INPUTDATE, UPDATEDATE, SERIALNO, WORKUNIT, KNOWLOAN, "+
								"SEX, DEPARTMENT, POSITION, RELATIONSORT) values(:CUSTOMERID,:RELATIONSHIP,:RELATIONNAME,:RELATIONTEL,:RELATIONFIXTEL,"+
					":FAMILYADD,:DESCRIBE,:REMARK,:INPUTORGID,:INPUTUSERID,:INPUTDATE,:UPDATEDATE,:SERIALNO,:WORKUNIT,:KNOWLOAN,"+
								":SEX,:DEPARTMENT,:POSITION,:RELATIONSORT)";
						SQLHandleInfo sSqlHandleRelation = new SQLHandleInfo(sSql)
						.setParameter("CUSTOMERID", this.sCustomerID)//�ͻ����
						.setParameter("RELATIONSHIP", Tools.getCodeItemNo("RelationShip",Tools.getObjectToString(jsonObject.get("ContactRelation")), sqlQuery))//������ϵ
						.setParameter("RELATIONNAME", Tools.getObjectToString(jsonObject.get("ContactName")))//����
						.setParameter("RELATIONTEL", Tools.getObjectToString(jsonObject.get("ContactTel")))//��ϵ�绰
						.setParameter("RELATIONFIXTEL", Tools.getObjectToString(jsonObject.get("ContactAddTel")))//�̶��绰
						.setParameter("FAMILYADD", Tools.getObjectToString(jsonObject.get("ContactAdd")))//סַ
						.setParameter("DESCRIBE", "")
						.setParameter("REMARK", "")//��ע
						.setParameter("INPUTORGID", this.sOrgID)//¼�����
						.setParameter("INPUTUSERID", this.sUserID)//¼����Ա
						.setParameter("INPUTDATE", Tools.getToday(2))//¼������
						.setParameter("UPDATEDATE", "")//��������
						.setParameter("SERIALNO", sSerialNo)//��ˮ��
						.setParameter("WORKUNIT", Tools.getObjectToString(jsonObject.get("ContactWork")))//������λ
						.setParameter("KNOWLOAN", Tools.getCodeItemNo("YesNo", Tools.getObjectToString(jsonObject.get("ContactFlag")), sqlQuery))//�Ƿ��֪����
						.setParameter("SEX", Tools.getCodeItemNo("Sex", Tools.getObjectToString(jsonObject.get("ContactSex")), sqlQuery))//�Ա�
						.setParameter("DEPARTMENT", Tools.getObjectToString(jsonObject.get("ContactPart")))//���ڲ���
						.setParameter("POSITION", Tools.getObjectToString(jsonObject.get("ContactPost")))//ְ��
						.setParameter("RELATIONSORT", "");
						
						logger.info("������ִ�нű�======"+sSqlHandleRelation.getSql());
						this.sqlQuery.execute(sSqlHandleRelation.getSql());
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------ִ�в����������Ϣʱ����-----");
			throw e;
		}
		
	}
	
}
