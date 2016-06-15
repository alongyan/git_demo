package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe ��������ֻ����ж��Ƿ�sa���Ա
 * @author jxsun
 *
 */
public class TranseYesNoSA extends Action {
	private Logger logger = Logger.getLogger(TranseYesNoSA.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sCompanyTel = Tools.getObjectToString(requestMap.get("CompanyTel"));//�ֻ�����
		if(sCompanyTel == null) sCompanyTel = "";
		String sUserName = "";//����
		String sCertID = "";//���֤��
		String sStatus = "";//�ж�״̬
		
		String sSql = "SELECT CASE WHEN EmployeeType = '01' THEN 'true'  ELSE 'false' END,username,certid  "
		+" FROM user_info where CompanyTel ='"+sCompanyTel+"'";
		
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sStatus = Tools.getObjectToString(rs.getString(1));
			sUserName = Tools.getObjectToString(rs.getString(2));
			sCertID  = Tools.getObjectToString(rs.getString(3));
		}
		rs.getStatement().close();
		
		//���ݲ��������û�״̬
		if("true".equals(sStatus)){
			responseMap.put("Status", "Success");
			responseMap.put("Param", "���û�ΪSA");
			responseMap.put("UserName", sUserName);
			responseMap.put("CertID", sCertID);
		}else if("false".equals(sStatus)){
			responseMap.put("Status", "Success");
			responseMap.put("Param", "���û�Ϊ��Ա");
			responseMap.put("UserName", sUserName);
			responseMap.put("CertID", sCertID);
		}else{
			responseMap.put("Status", "Success");
			responseMap.put("Param", "����SA���Ա");
		}
		
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
