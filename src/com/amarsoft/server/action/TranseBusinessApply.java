package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.handle.HandleApplyInfoCJ;
import com.amarsoft.server.handle.HandleCustomerInfoCJ;
import com.amarsoft.server.handle.HandleGoodsInfo;
import com.amarsoft.server.util.Tools;
/**
 * @describe ����������ý�����ˮ�ţ������ţ�
 * @author jxsun
 *
 */
public class TranseBusinessApply extends Action {
	private Logger logger = Logger.getLogger(TranseBusinessApply.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sOrgID = Tools.getObjectToString(requestMap.get("InputOrgID"));//��ȡ�������
		if(sOrgID == null) sOrgID = "";
		String sUserID = Tools.getObjectToString(requestMap.get("InputUserID"));//��ȡ��¼�˺�
		if(sUserID == null) sUserID = "";
		String sSerialNo = "";//������ˮ�ţ������ţ�
		
		HandleCustomerInfoCJ handlecustomer = new HandleCustomerInfoCJ(requestMap, sqlQuery, sUserID, sOrgID);
		String sCustomerID = handlecustomer.handleCustomerInfoMessage();
		
		HandleApplyInfoCJ handleApplyInfo = new HandleApplyInfoCJ(requestMap, sqlQuery, sOrgID, sUserID, sCustomerID);
		sSerialNo = handleApplyInfo.handleApplyInfoMessage();
		
		HandleGoodsInfo handleGoodsInfo = new HandleGoodsInfo(requestMap, sqlQuery, sOrgID, sUserID, sSerialNo);
		handleGoodsInfo.handleGoodsInfo();
		//���ݲ��������û�״̬
		responseMap.put("Status", "Success");
		responseMap.put("Param", sSerialNo);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
