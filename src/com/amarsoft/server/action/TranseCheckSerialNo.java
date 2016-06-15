package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe ����������ö������ݶ���
 * @author jxsun
 *
 */
public class TranseCheckSerialNo extends Action {
	private Logger logger = Logger.getLogger(TranseCheckSerialNo.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));//������ˮ�ţ������ţ�
		String sBusinessType = "";//��Ʒ����
		
		if(!"".equals(sSerialNo)){
			//���ݲ��������û�״̬
			responseMap.put("Status", "Sucess");
			responseMap.put("Param", "�뿴����");
		}
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
