package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来门店编号返回SA列表
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
		String sSaNo,sSaName = "";//商户名称
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
		
		//根据参数返回用户状态
		responseMap.put("Status", "Sucess");
		responseMap.put("SAList", list);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
