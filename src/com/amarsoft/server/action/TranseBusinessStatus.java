package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe �������ڴ���ҵ��״̬��Ϣ��
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
		String sJMID = Tools.getObjectToString(requestMap.get("JMID"));//��ȡJMID
		if(sJMID == null) sJMID = "";
		logger.info("��ȡ�����е�JMIDΪ"+sJMID);
		String sItemStatus = "";//ҵ��״̬
		//����JMID��ȡҵ��״̬
		sItemStatus = getItemStatus(requestMap,sqlQuery,sJMID);
		responseMap.put("Status", "Success");
		responseMap.put("ItemStatus", sItemStatus);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}

	/**
	 * @throws Exception 
	 * @describe  �÷���������ȡҵ��״̬
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
				if(("������").equals(sItemStatus))sItemStatus="ǩԼ������";
			}else {
				sSql =" select fl.phasetype as PhaseType  from flow_object fl where "
			+" fl.objectno =(select serialNo from business_apply ba  where ba.jimuid ='"+JMID+"') ";
				ResultSet rs1 = sqlQuery.getResultSet(sSql);
				if(rs1.next()){
					sPhaseType = rs1.getString("PhaseType");
				}
				rs1.getStatement().close();
				if("1010".equals(sPhaseType)){
					sItemStatus = "ҵ����Ա������";
				}else if("1020".equals(sPhaseType)){
					sItemStatus = "������";
				}else if("1030".equals(sPhaseType)){
					sItemStatus = "�˻ز������";
				}else if("1050".equals(sPhaseType)){
					sItemStatus = "�ѷ��";
				}
			}
		} catch (Exception e) {
			logger.info("--------��ȡҵ��״̬��Ϣ����-----");
			e.printStackTrace();
			throw e;
		}
		return sItemStatus ;
	}
}
