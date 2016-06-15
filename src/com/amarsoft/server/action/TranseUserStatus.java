package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe �������������û�״̬�������û� ture ���� false
 * @author yhwang
 *
 */
public class TranseUserStatus extends Action {
	private Logger logger = Logger.getLogger(TranseUserStatus.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sLoginID = Tools.getObjectToString(requestMap.get("LoginID"));//��¼�ʺ�
		if(sLoginID == null) sLoginID = "";
		String sPassWord = Tools.getObjectToString(requestMap.get("Password"));//����
		if(sPassWord == null) sPassWord = "";
		String sUserName = "";//�û�����
		String sEmployeeType ="";//�û�����
		
		String sSql = " SELECT UserName,getItemName('EmployeeType',EmployeeType) as EmployeeType "
		+" FROM user_info  ii WHERE ii.loginid ='"+sLoginID+"'  AND "
	    +" ii.password = '"+sPassWord+"' ";
		logger.info("�鿴�û���Ϣ �� "+sSql);
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sUserName = Tools.getObjectToString(rs.getString("UserName"));
			sEmployeeType = Tools.getObjectToString(rs.getString("EmployeeType"));
		}
		rs.getStatement().close();
		
		//���ݲ��������û�״̬
		responseMap.put("Status", "Success");
		responseMap.put("UserName", sUserName);
		responseMap.put("EmployeeType", sEmployeeType);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
