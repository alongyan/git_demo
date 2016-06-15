package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来获得订单数据对象
 * @author jxsun
 *
 */
public class TranseSelectMerchants extends Action {
	private Logger logger = Logger.getLogger(TranseSelectMerchants.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sUserID = Tools.getObjectToString(requestMap.get("UserID"));//SA
		String sMerChantsName,MerchantsNo= "";//商户名称
		
		ArrayList<Map> list = new ArrayList<Map>();
		String sSql = "select MerchantsNo,MerChantsName from merchants_info where ApplySA = '"+sUserID+"'";
		System.out.println(sSql);
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			MerchantsNo = Tools.getObjectToString(rs.getString(1));
			sMerChantsName = Tools.getObjectToString(rs.getString(2));
			map.put(MerchantsNo, sMerChantsName);
			list.add(map);
		}
		rs.getStatement().close();
		
		//根据参数返回用户状态
		responseMap.put("Status", "Sucess");
		responseMap.put("MerchantsList", list);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
