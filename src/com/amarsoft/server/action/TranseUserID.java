package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe �������������ŵ��ŷ��ص�Ա�б�
 * @author jxsun
 *
 */
public class TranseUserID extends Action {
	private Logger logger = Logger.getLogger(TranseUserID.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));//�ŵ���
		String sSANo = "";//��Ա���
		String sSAName = "";//��Ա����
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Map> UserList =  new ArrayList<Map>();
		String sSql = "select UserID,UserName from User_info where belongMerchant = '"+sMerchantsNo+"'";
		
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()) {
			sSANo = Tools.getObjectToString(rs.getString(1));
			sSAName = Tools.getObjectToString(rs.getString(2));
			
			map.put(sSANo, sSAName);
			UserList.add(map);
		}
		rs.getStatement().close();
		
		//���ݲ��������û�״̬
		responseMap.put("Status", "Sucess");
		responseMap.put("List<people>", UserList);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
}
