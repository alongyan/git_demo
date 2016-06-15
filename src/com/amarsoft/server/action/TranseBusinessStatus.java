package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用于处理业务状态信息。
 * @author yhwang
 *
 */
public class TranseBusinessStatus extends Action {
	private Logger logger = Logger.getLogger(TranseBusinessStatus.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sJMID = Tools.getObjectToString(requestMap.get("JMID"));//获取JMID
		if(sJMID == null) sJMID = "";
		logger.info("获取报文中的JMID为"+sJMID);
		String sItemStatus = "";//业务状态
		//根据JMID获取业务状态
		sItemStatus = getItemStatus(requestMap,sqlQuery,sJMID);
		responseMap.put("Status", "Success");
		responseMap.put("ItemStatus", sItemStatus);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}

	/**
	 * @throws Exception 
	 * @describe  该方法用来获取业务状态
	 */
	private String getItemStatus(Map<String, Object> resquestMap,SQLQuery sqlQuery,String JMID) throws Exception {
		String sItemStatus ="";
		String sSql ="";
		String sPhaseType ="";
		sSql ="select  getitemname('ItemStatus',bc.ItemStatus) as ItemStatus  from Business_Contract bc where bc.Jimuid ='"+JMID+"'";
		try {
			ResultSet rs = sqlQuery.getResultSet(sSql);
			if (rs.next()) {
				sItemStatus = Tools.getObjectToString(rs
						.getString("ItemStatus"));
			}
			rs.getStatement().close();
			if(sItemStatus == null) sItemStatus ="";
			if(sItemStatus != ""){
				if(("待处理").equals(sItemStatus))sItemStatus="签约待处理";
			}else {
				sSql =" select fl.phasetype as PhaseType  from flow_object fl where "
			+" fl.objectno =(select serialNo from business_apply ba  where ba.jimuid ='"+JMID+"') ";
				ResultSet rs1 = sqlQuery.getResultSet(sSql);
				if(rs1.next()){
					sPhaseType = rs1.getString("PhaseType");
				}
				rs1.getStatement().close();
				if("1010".equals(sPhaseType)){
					sItemStatus = "业务人员待处理";
				}else if("1020".equals(sPhaseType)){
					sItemStatus = "审批中";
				}else if("1030".equals(sPhaseType)){
					sItemStatus = "退回补充材料";
				}else if("1050".equals(sPhaseType)){
					sItemStatus = "已否决";
				}
			}
		} catch (Exception e) {
			logger.info("--------获取业务状态信息出错-----");
			e.printStackTrace();
			throw e;
		}
		return sItemStatus ;
	}
}
