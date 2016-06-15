package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.handle.HandleApplyInfo;
import com.amarsoft.server.handle.HandleCustomerDebt;
import com.amarsoft.server.handle.HandleCustomerInfo;
import com.amarsoft.server.handle.HandleCustomerRelation;
import com.amarsoft.server.handle.HandleImageDown;
import com.amarsoft.server.handle.HandleImageInfo;
import com.amarsoft.server.handle.HandleImageUpload;
import com.amarsoft.server.util.GetJMID;
import com.amarsoft.server.util.Tools;
/**
 * @describe �������ڶ�����ϵͳ���͵�ҵ����Ϣ���д���
 * @author yhwang
 *
 */ 
public class TranseSRMessage extends Action{
	private String sErrorMessage ="ִ�д���";//���ش�������

	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
	ResultSet  rs=null;
	try{
		
		String sOrgID = Tools.getObjectToString(requestMap.get("OrgID"));//��ȡ�������
		if(sOrgID == null) sOrgID = "";
		String sUserID = Tools.getObjectToString(requestMap.get("UserID"));//��ȡ��¼�˺�
		if(sUserID == null) sUserID = "";
		
		sqlQuery.setAutoCommit(false);
		String sThirdPartySerialNo = (Tools.getObjectToString(requestMap.get("ProjectNo")));
		//��ȡjmid ,imagestatus(ͼƬ����״̬)
		String sSql = "select jimuid,imagestaus from business_apply where ThirdPartySerialNo = '"+sThirdPartySerialNo+"'";
		String sJMID = "";
		String sImageStatus ="";
		rs = sqlQuery.getResultSet(sSql);
		if(rs.next()){
			sJMID = rs.getString("jimuid");
			sImageStatus = rs.getString("imagestaus");
		}
		if(rs!=null){
			rs.getStatement().close();
		}
		
		//�ж��Ƿ�ҵ����ڣ�����Ӱ����������ʧ��
		if(sJMID !="" && sJMID!=null && !"Success".equals(sImageStatus)){
			//��ȡ����ϵͳӰ��������Ϣ
			HandleImageInfo handleImage = new HandleImageInfo(requestMap,sqlQuery,sJMID);
			handleImage.handleImageInfoMessage();
			//����Ӱ���ϴ�����
			imageAction(sJMID,sOrgID,sUserID,requestMap,sqlQuery);
			sqlQuery.commit();
		}else{
			sJMID = GetJMID.getJMID(sqlQuery);
			logger.info("����ϵͳ�������ݳ������ݿ��ȡJMID"+sJMID);
			////����ͻ�������Ϣ
			HandleCustomerInfo handleCus = new HandleCustomerInfo(requestMap, sqlQuery, sJMID, sUserID, sOrgID);
			String sCustomerID = handleCus.handleCustomerInfoMessage();
			//�����������Ϣ
			HandleCustomerRelation handleRelation = new HandleCustomerRelation(requestMap,sqlQuery,sOrgID,sUserID,sCustomerID);
			handleRelation.handleCustomerRelation();
			//�������ծ����Ϣ
			HandleCustomerDebt handleDebt = new HandleCustomerDebt(requestMap,sqlQuery,sOrgID,sUserID,sCustomerID);
			handleDebt.handleCustomerDebt();
			//����ҵ��������Ϣ
			HandleApplyInfo handle = new HandleApplyInfo(requestMap,sqlQuery,sJMID,sOrgID,sUserID,sCustomerID);
			handle.handleApplyInfoMessage();
			//��ȡ����ϵͳӰ��������Ϣ
			HandleImageInfo handleImage = new HandleImageInfo(requestMap,sqlQuery,sJMID);
			handleImage.handleImageInfoMessage();
			sqlQuery.commit();
			//����Ӱ���ϴ�����
			imageAction(sJMID,sOrgID,sUserID,requestMap,sqlQuery);
			sqlQuery.commit();
		}
		responseMap.put("Status", "Success");
		responseMap.put("JMID", sJMID);
		}catch(Exception e){
			sqlQuery.rollback();
			responseMap.put("Status", "Fail");
			responseMap.put("Error", sErrorMessage);
		} finally{
			if(sqlQuery.getConnection()!=null){
				sqlQuery.close();
			}
		}
		return responseMap;
	}
	
	/**
	 * ͼƬ�ϴ�������
	 * @throws Exception 
	 */
	private void imageAction(String sJMID,String sOrgID,String sUserID,Map<String, Object> requestMap,SQLQuery sqlQuery) throws Exception{
		//���������ϴ�Ӱ��
		String sImageStatus ="";
		SQLQuery ql = null;
		try {
			//����Ӱ��ͼƬ����
			HandleImageDown handleDown = new HandleImageDown(sqlQuery, sJMID);
			handleDown.handleImageDownMessage();
			//�ϴ�Ӱ��ͼƬ����
			HandleImageUpload handleUpload = new HandleImageUpload(requestMap,
					sqlQuery, sJMID, sOrgID, sUserID);
			handleUpload.handleImageUploadMessage();
			//����Ӱ����Ϣ�ֶ�
			sImageStatus ="Success";
			sqlQuery.execute("update business_apply ba set ba.imagestaus ='"+sImageStatus+"' where ba.jimuid ='"+sJMID+"'");
		} catch (Exception e) {
			e.printStackTrace();
			sErrorMessage ="Ӱ����Ϣ�������";
			//����Ӱ����Ϣ״̬�ֶ�
			ql = new SQLQuery();
			ql.execute("update business_apply ba set ba.imagestaus ='Fail' where ba.jimuid ='"+sJMID+"'");
			//ɾ��Ӱ��������Ϣ
			ql.execute("delete from RETAIL_IMAGEMESSAGE where JMID ='"+sJMID+"'");
			throw e;
		}finally{
			if(ql!=null){
				ql.close();
			}
		}
	}
}
