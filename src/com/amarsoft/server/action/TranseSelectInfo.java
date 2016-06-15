package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来获得订单详情
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
		String sBusinessType = Tools.getObjectToString(requestMap.get("BusinessType"));//产品类型
		
		if(!"".equals(sBusinessType)){
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
