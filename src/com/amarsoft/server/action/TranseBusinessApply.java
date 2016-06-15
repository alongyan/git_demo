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
 * @describe 该类用来获得交易流水号（订单号）
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
		String sOrgID = Tools.getObjectToString(requestMap.get("InputOrgID"));//获取机构编号
		if(sOrgID == null) sOrgID = "";
		String sUserID = Tools.getObjectToString(requestMap.get("InputUserID"));//获取登录账号
		if(sUserID == null) sUserID = "";
		String sSerialNo = "";//交易流水号（订单号）
		
		HandleCustomerInfoCJ handlecustomer = new HandleCustomerInfoCJ(requestMap, sqlQuery, sUserID, sOrgID);
		String sCustomerID = handlecustomer.handleCustomerInfoMessage();
		
		HandleApplyInfoCJ handleApplyInfo = new HandleApplyInfoCJ(requestMap, sqlQuery, sOrgID, sUserID, sCustomerID);
		sSerialNo = handleApplyInfo.handleApplyInfoMessage();
		
		HandleGoodsInfo handleGoodsInfo = new HandleGoodsInfo(requestMap, sqlQuery, sOrgID, sUserID, sSerialNo);
		handleGoodsInfo.handleGoodsInfo();
		//根据参数返回用户状态
		responseMap.put("Status", "Success");
		responseMap.put("Param", sSerialNo);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
}
