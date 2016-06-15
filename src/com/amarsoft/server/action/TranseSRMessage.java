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
 * @describe 该类用于对禅融系统推送的业务信息进行处理。
 * @author yhwang
 *
 */ 
public class TranseSRMessage extends Action{
	private String sErrorMessage ="执行错误";//返回错误数据

	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
	ResultSet  rs=null;
	try{
		
		String sOrgID = Tools.getObjectToString(requestMap.get("OrgID"));//获取机构编号
		if(sOrgID == null) sOrgID = "";
		String sUserID = Tools.getObjectToString(requestMap.get("UserID"));//获取登录账号
		if(sUserID == null) sUserID = "";
		
		sqlQuery.setAutoCommit(false);
		String sThirdPartySerialNo = (Tools.getObjectToString(requestMap.get("ProjectNo")));
		//获取jmid ,imagestatus(图片保存状态)
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
		
		//判断是否业务存在，但是影像资料下载失败
		if(sJMID !="" && sJMID!=null && !"Success".equals(sImageStatus)){
			//获取产融系统影像资料信息
			HandleImageInfo handleImage = new HandleImageInfo(requestMap,sqlQuery,sJMID);
			handleImage.handleImageInfoMessage();
			//调用影像上传下载
			imageAction(sJMID,sOrgID,sUserID,requestMap,sqlQuery);
			sqlQuery.commit();
		}else{
			sJMID = GetJMID.getJMID(sqlQuery);
			logger.info("禅融系统推送数据出入数据库获取JMID"+sJMID);
			////处理客户基本信息
			HandleCustomerInfo handleCus = new HandleCustomerInfo(requestMap, sqlQuery, sJMID, sUserID, sOrgID);
			String sCustomerID = handleCus.handleCustomerInfoMessage();
			//处理关联人信息
			HandleCustomerRelation handleRelation = new HandleCustomerRelation(requestMap,sqlQuery,sOrgID,sUserID,sCustomerID);
			handleRelation.handleCustomerRelation();
			//处理个人债务信息
			HandleCustomerDebt handleDebt = new HandleCustomerDebt(requestMap,sqlQuery,sOrgID,sUserID,sCustomerID);
			handleDebt.handleCustomerDebt();
			//处理业务申请信息
			HandleApplyInfo handle = new HandleApplyInfo(requestMap,sqlQuery,sJMID,sOrgID,sUserID,sCustomerID);
			handle.handleApplyInfoMessage();
			//获取产融系统影像资料信息
			HandleImageInfo handleImage = new HandleImageInfo(requestMap,sqlQuery,sJMID);
			handleImage.handleImageInfoMessage();
			sqlQuery.commit();
			//调用影像上传下载
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
	 * 图片上传和下载
	 * @throws Exception 
	 */
	private void imageAction(String sJMID,String sOrgID,String sUserID,Map<String, Object> requestMap,SQLQuery sqlQuery) throws Exception{
		//处理下载上传影像
		String sImageStatus ="";
		SQLQuery ql = null;
		try {
			//下载影像图片处理
			HandleImageDown handleDown = new HandleImageDown(sqlQuery, sJMID);
			handleDown.handleImageDownMessage();
			//上传影像图片处理
			HandleImageUpload handleUpload = new HandleImageUpload(requestMap,
					sqlQuery, sJMID, sOrgID, sUserID);
			handleUpload.handleImageUploadMessage();
			//更新影像信息字段
			sImageStatus ="Success";
			sqlQuery.execute("update business_apply ba set ba.imagestaus ='"+sImageStatus+"' where ba.jimuid ='"+sJMID+"'");
		} catch (Exception e) {
			e.printStackTrace();
			sErrorMessage ="影像信息保存错误";
			//更新影像信息状态字段
			ql = new SQLQuery();
			ql.execute("update business_apply ba set ba.imagestaus ='Fail' where ba.jimuid ='"+sJMID+"'");
			//删除影像资料信息
			ql.execute("delete from RETAIL_IMAGEMESSAGE where JMID ='"+sJMID+"'");
			throw e;
		}finally{
			if(ql!=null){
				ql.close();
			}
		}
	}
}
