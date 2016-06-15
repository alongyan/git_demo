package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe ����������ö�������
 * @author jxsun
 *
 */
public class TranseSelectInfo extends Action {
	private Logger logger = Logger.getLogger(TranseSelectInfo.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sBusinessType = Tools.getObjectToString(requestMap.get("BusinessType"));//��Ʒ����
		
		if(!"".equals(sBusinessType)){
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
