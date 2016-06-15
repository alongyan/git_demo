package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe ���������ŵ��ŷ���SA�б�
 * @author jxsun
 *
 */
public class TranseSelectSA extends Action {
	private Logger logger = Logger.getLogger(TranseSelectSA.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));//SA
		String sSaNo,sSaName = "";//�̻�����
		ArrayList<Map> list = new ArrayList<Map>();
		String sSql = "select SaNo,SaName from sa_merchants where MerchantsNo = '"+sMerchantsNo+"'";
		
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			sSaNo = Tools.getObjectToString(rs.getString(1));
			sSaName = Tools.getObjectToString(rs.getString(2));
			map.put(sSaNo, sSaName);
			list.add(map);
		}
		rs.getStatement().close();
		
		//���ݲ��������û�״̬
		responseMap.put("Status", "Sucess");
		responseMap.put("SAList", list);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
