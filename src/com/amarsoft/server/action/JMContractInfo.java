package com.amarsoft.server.action;

import java.sql.Connection;
import java.util.Map;

import com.amarsoft.server.JMT.JMTimesTranseJMID;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷������ڻ�ȡ��ľʱ��ϵͳ���ں�ͬǩ��״̬������
 * @author Administrator
 *
 */
public class JMContractInfo extends Action {
	private String sJMID = "";//��ľID
	private String sContractFlag = "";//��ͬ״̬
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		// TODO Auto-generated method stub
		sJMID = (String) requestMap.get("JMID");//JMID
		sContractFlag = (String) requestMap.get("ContractFlag");//��ͬ״̬
		String sContractState = "";
		Connection conn = sqlQuery.getConnection();
		try {
			conn.setAutoCommit(false);
			//����JMID��ȡ��ͬ��ˮ��
			String sSql = "select SerialNo from Business_contract where jimuid = '"+sJMID+"'";
			String sSerialNo = sqlQuery.getString(sSql);
			if("4".equals(sContractFlag)){//ǩԼ�ɹ�
				sContractState = "05";
			}else if("9".equals(sContractFlag)){//���
				sContractState = "00";
			}
			
			if("00".equals(sContractState)){//�����״̬
				sSql = "update Business_contract set ItemStatus = '"+sContractState+"' , finishdate = '"+Tools.getToday(2)+"' where jimuid = '"+sJMID+"'";
			}else{//���ɹ�״̬
				sSql = "update Business_contract set ItemStatus = '"+sContractState+"' where jimuid = '"+sJMID+"'";
			}
			//����JMID���º�ͬ״̬
				sqlQuery.executeUpdate(sSql);
			//����ľʱ��������ϢΪ���״̬
			//���÷�������������Ϣ����
			if("00".equals(sContractState)){
				updateFlowMessage(sqlQuery,sSerialNo);
			}
			//�ѻ�ȡ�������ݷ�������ľʱ��ϵͳ
			responseMap.put("JMID", sJMID);
			responseMap.put("ContractID", sSerialNo);
			//add by xlsun ����ǩԼ���״̬���л�ȡ��ͬ��Ϣ�ӿڵ���
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
	 * @describe �÷������ڸ��º�ͬ��������
	 * @param sqlQuery
	 * @param sSerialNo
	 */
	public void updateFlowMessage(SQLQuery sqlQuery, String sSerialNo) throws Exception{
		String sSql = "select count(*) as iCount from flow_object where objectno = '"+sSerialNo+"' and ObjectType = 'BusinessContract'";
			String sICount = sqlQuery.getString(sSql);
			if(!"0".equals(sICount)){//�ж������ں�ͬ��������Ҫ�޸����̽׶�״̬
				sSql = "update flow_object set phaseno = '8000' ,phasename = '�ѷ��',phaseType='1050' where  objectno = '"+sSerialNo+"' and ObjectType = 'BusinessContract'";
				sqlQuery.executeUpdate(sSql);
			}
	}
}
