package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来根据门店编号返回店员列表
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
		
		String sMerchantsNo = Tools.getObjectToString(requestMap.get("MerchantsNo"));//门店编号
		String sSANo = "";//店员编号
		String sSAName = "";//店员名称
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
		
		//根据参数返回用户状态
		responseMap.put("Status", "Sucess");
		responseMap.put("List<people>", UserList);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
