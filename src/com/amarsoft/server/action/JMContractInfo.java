package com.amarsoft.server.action;

import java.sql.Connection;
import java.util.Map;

import com.amarsoft.server.JMT.JMTimesTranseJMID;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @describe 该方法用于获取积木时代系统对于合同签订状态，更新
 * @author Administrator
 *
 */
public class JMContractInfo extends Action {
	private String sJMID = "";//积木ID
	private String sContractFlag = "";//合同状态
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		// TODO Auto-generated method stub
		sJMID = (String) requestMap.get("JMID");//JMID
		sContractFlag = (String) requestMap.get("ContractFlag");//合同状态
		String sContractState = "";
		Connection conn = sqlQuery.getConnection();
		try {
			conn.setAutoCommit(false);
			//根据JMID获取合同流水号
			String sSql = "select SerialNo from Business_contract where jimuid = '"+sJMID+"'";
			String sSerialNo = sqlQuery.getString(sSql);
			if("4".equals(sContractFlag)){//签约成功
				sContractState = "05";
			}else if("9".equals(sContractFlag)){//否决
				sContractState = "00";
			}
			
			if("00".equals(sContractState)){//若否决状态
				sSql = "update Business_contract set ItemStatus = '"+sContractState+"' , finishdate = '"+Tools.getToday(2)+"' where jimuid = '"+sJMID+"'";
			}else{//若成功状态
				sSql = "update Business_contract set ItemStatus = '"+sContractState+"' where jimuid = '"+sJMID+"'";
			}
			//根据JMID更新合同状态
				sqlQuery.executeUpdate(sSql);
			//若积木时代反馈信息为否决状态
			//调用方法更新流程信息内容
			if("00".equals(sContractState)){
				updateFlowMessage(sqlQuery,sSerialNo);
			}
			//把获取到的数据反馈给积木时代系统
			responseMap.put("JMID", sJMID);
			responseMap.put("ContractID", sSerialNo);
			//add by xlsun 仅在签约完成状态进行获取合同信息接口调用
			if("05".equals(sContractState)){
				JMTimesTranseJMID jt = new JMTimesTranseJMID();
				jt.getJMIDInfo(sqlQuery, sJMID);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			printLog(e);
			throw e;
		} finally{
			conn.setAutoCommit(true);
		}
		return responseMap;
	}
	/**
	 * @describe 该方法用于更新合同申请内容
	 * @param sqlQuery
	 * @param sSerialNo
	 */
	public void updateFlowMessage(SQLQuery sqlQuery, String sSerialNo) throws Exception{
		String sSql = "select count(*) as iCount from flow_object where objectno = '"+sSerialNo+"' and ObjectType = 'BusinessContract'";
			String sICount = sqlQuery.getString(sSql);
			if(!"0".equals(sICount)){//判断若存在合同流程则需要修改流程阶段状态
				sSql = "update flow_object set phaseno = '8000' ,phasename = '已否决',phaseType='1050' where  objectno = '"+sSerialNo+"' and ObjectType = 'BusinessContract'";
				sqlQuery.executeUpdate(sSql);
			}
	}
}
