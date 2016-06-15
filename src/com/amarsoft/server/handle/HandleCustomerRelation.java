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
 * @describe 该方法用于插入客户关联信息
 * @author Sun
 *
 */
public class HandleCustomerRelation {
	private Logger logger = Logger.getLogger(HandleCustomerRelation.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//机构编号
	private String sUserID = "";//用户编号
	private String sCustomerID = "";//客户编号
	
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
	 * @describe 该方法用于处理业务申请信息模块内容
	 */
	public void handleCustomerRelation() throws Exception{
		String message = this.hashMap.get("ContactInformation")+"";
		message = "{\"Relation\":"+message+"}";
		JSONObject json = JSONObject.fromObject(message);
		JSONArray arrayJson = json.getJSONArray("Relation");
		deleteCustomerRelation();//清理关联表信息内容
		for(int i=0;i<arrayJson.size();i++){
			insertCustomerRelation(arrayJson.get(i).toString());
		}
	}
	/**
	 * @throws Exception 
	 * @describe 改方法用于清除关联人信息内容
	 */
	private void deleteCustomerRelation() throws Exception{
		String sSql = "delete from CUSTOMER_CONTACT where CUSTOMERID = '"+this.sCustomerID+"'";
		try {
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----删除关联人信息错误----");
			throw e;
		}
	}
	/**
	 * @throws Exception 
	 * @describe 改方法用于处理客户关联人信息内内容
	 */
	private void insertCustomerRelation(String sJsonStr) throws Exception{
		JSONObject jsonObject = JSONObject.fromObject(sJsonStr);
		System.out.println("===================关联人========"+sJsonStr);
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
						.setParameter("CUSTOMERID", this.sCustomerID)//客户编号
						.setParameter("RELATIONSHIP", Tools.getCodeItemNo("RelationShip",Tools.getObjectToString(jsonObject.get("ContactRelation")), sqlQuery))//关联关系
						.setParameter("RELATIONNAME", Tools.getObjectToString(jsonObject.get("ContactName")))//姓名
						.setParameter("RELATIONTEL", Tools.getObjectToString(jsonObject.get("ContactTel")))//联系电话
						.setParameter("RELATIONFIXTEL", Tools.getObjectToString(jsonObject.get("ContactAddTel")))//固定电话
						.setParameter("FAMILYADD", Tools.getObjectToString(jsonObject.get("ContactAdd")))//住址
						.setParameter("DESCRIBE", "")
						.setParameter("REMARK", "")//备注
						.setParameter("INPUTORGID", this.sOrgID)//录入机构
						.setParameter("INPUTUSERID", this.sUserID)//录入人员
						.setParameter("INPUTDATE", Tools.getToday(2))//录入日期
						.setParameter("UPDATEDATE", "")//更新日期
						.setParameter("SERIALNO", sSerialNo)//流水号
						.setParameter("WORKUNIT", Tools.getObjectToString(jsonObject.get("ContactWork")))//工作单位
						.setParameter("KNOWLOAN", Tools.getCodeItemNo("YesNo", Tools.getObjectToString(jsonObject.get("ContactFlag")), sqlQuery))//是否可知贷款
						.setParameter("SEX", Tools.getCodeItemNo("Sex", Tools.getObjectToString(jsonObject.get("ContactSex")), sqlQuery))//性别
						.setParameter("DEPARTMENT", Tools.getObjectToString(jsonObject.get("ContactPart")))//所在部门
						.setParameter("POSITION", Tools.getObjectToString(jsonObject.get("ContactPost")))//职务
						.setParameter("RELATIONSORT", "");
						
						logger.info("关联人执行脚本======"+sSqlHandleRelation.getSql());
						this.sqlQuery.execute(sSqlHandleRelation.getSql());
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------执行插入关联人信息时错误-----");
			throw e;
		}
		
	}
	
}
