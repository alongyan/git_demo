package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe ����������ǰ������Ϣ
 * @author jxsun
 *
 */
public class TranseAdvanceRefund extends Action {
	private Logger logger = Logger.getLogger(TranseAdvanceRefund.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));//������ˮ�ţ������ţ�
		String sCertID = Tools.getObjectToString(requestMap.get("CertID"));//֤������
		String sCustomerName = Tools.getObjectToString(requestMap.get("CustomerName"));//�ͻ�����
		String sContractStatus = "";//��Ʒ����
		String sSql = " SELECT contractstatus from business_contract where SerialNo = '"+sSerialNo+"'"+
		" and  CertID = '"+sCertID+"' and CustomerName = '"+sCustomerName+"'";
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sContractStatus = Tools.getObjectToString(rs.getString("contractstatus"));
		}
		rs.getStatement().close();
			//���ݲ��������û�״̬
			responseMap.put("Status", "Sucess");
			responseMap.put("Param", sContractStatus);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}