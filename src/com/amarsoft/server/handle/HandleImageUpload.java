package com.amarsoft.server.handle;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DataCryptUtils;
import com.amarsoft.server.util.ImageUpload;
import com.amarsoft.server.util.Tools;

/**
 * @describe 该方法用于获取影像资料
 * @author Sun
 * 
 */
public class HandleImageUpload {
	private Logger logger = Logger.getLogger(HandleImageUpload.class);
	private SQLQuery sqlQuery = null;
	private String sJMID = "";// JMID

	private String sImageType = "";//
	private String sParentPath =  HandleImageUpload.class.getResource("/").getPath().replace("classes/", "")+NIOProperty.getProperty("saveFilePath");

	private Map<String, Object> requestMap = null;// 申请报文
	private String sUserID = "";// 操作人编号
	private String sOrgID = "";// 操作人所属机构
	private String sUserName = "";// 操作人名称
	private String sOrgName = "";// 所在机构名称
	private String sBusinessType = "";// 业务品种
	private String sCustomerType = "";// 客户类型
	private String sCertID = "";// 证件编号

	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleImageUpload(Map<String, Object> requestMap, SQLQuery sqlQuery,
			String sJMID, String sOrgID, String sUserID) {
		this.sqlQuery = sqlQuery;
		this.sJMID = sJMID;
		this.requestMap = requestMap;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
	}

	/**
	 * @describe 该方法用于下载图片内容
	 * @throws Exception
	 */
	public void handleImageUploadMessage() throws Exception {
		ResultSet rs =null;
		try {

			sUserName = this.sqlQuery
					.getString("select username from user_info where userid = '"
							+ this.sUserID + "'");
			if (sUserName == null)
				sUserName = "";

			sOrgName = this.sqlQuery
					.getString("select orgname from org_info where orgid = '"
							+ this.sOrgID + "'");
			if (sOrgName == null)
				sOrgName = "";
			sCertID = Tools.getObjectToString(requestMap.get("IdentityNumber")
					.toString());
			sCustomerType = this.sqlQuery
					.getString("select customertype from customer_info where certid = '"
							+ DataCryptUtils.encrypt(sCertID) + "'");
			sBusinessType = Tools.getObjectToString(requestMap.get("Type").toString());
			String sSql = "select ri.imagetype as imageType from RETAIL_IMAGEMESSAGE ri where jmid = '"+ sJMID + "' group by imageType ";
			rs = this.sqlQuery.getResultSet(sSql);
			while (rs.next()) {
				sImageType = rs.getString("imageType");
				if (sImageType == null)
					sImageType = "";
				HashMap hashMap = uploadImageMessage(sImageType);
				ImageUpload imageUpload = new ImageUpload(sJMID,sImageType,hashMap, sqlQuery);
				imageUpload.imageUploadMessage();
			}
			// 删除数据库图像路径信息
			deleteImageMessage();
			// 删除服务器图片信息
			String sImagePath = sParentPath + "//" + this.sJMID;
			deleteServerImage(sImagePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("循环下载图片错误");
			throw e;
		}finally{
			if(rs!=null){
				rs.getStatement().close();
			}
		}

	}

	/**
	 * @describe 该方法用于清理影像资料信息
	 * @throws Exception
	 */
	private void deleteImageMessage() throws Exception {
		try {
			String sSql = "delete from RETAIL_IMAGEMESSAGE where JMID = '"
					+ this.sJMID + "'";
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			logger.info("清理影像资料记录错误");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @describe 该方法用于删除服务器上图片信息
	 * 
	 */
	private void deleteServerImage(String folderPath) throws Exception {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			logger.info("删除服务器影像资料出错");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @throws Exception
	 * @describe 删除指定文件夹下所有文件
	 */
	private boolean delAllFile(String path) throws Exception {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				try {
					deleteServerImage(path + "/" + tempList[i]);
				} catch (Exception e) {
					logger.info("删除影像信息出错");
					e.printStackTrace();
					throw e;
				}// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @describe 该方法用于处理参数数据
	 * @param sImageType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HashMap uploadImageMessage(String sImageType) {
		HashMap hashMap = new HashMap();
		hashMap.put("BatchType", sImageType);// 图拍类型
		hashMap.put("UserID", this.sUserID);// 用户编号
		hashMap.put("OrgID", this.sOrgID);// 机构编号
		hashMap.put("OrgName", this.sOrgName);// 机构名称
		hashMap.put("UserName", this.sUserName);// 用户名称
		hashMap.put("BusinessType", sBusinessType);// 业务品种
		hashMap.put("CertID", sCertID);// 证件编号
		hashMap.put("CustomerName", Tools.getObjectToString(requestMap.get(
				"ChineseName").toString()));// 客户名称
		hashMap.put("CustomerType", sCustomerType);// 客户类型
		return hashMap;
	}

}
