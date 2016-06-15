package com.amarsoft.server.action;

import java.sql.ResultSet;
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
public class TranseCheckSerialNo extends Action {
	private Logger logger = Logger.getLogger(TranseCheckSerialNo.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		
		String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));//交易流水号（订单号）
		String sBusinessType = "";//产品类型
		
		if(!"".equals(sSerialNo)){
			//根据参数返回用户状态
			responseMap.put("Status", "Sucess");
			responseMap.put("Param", "请看详情");
		}
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
