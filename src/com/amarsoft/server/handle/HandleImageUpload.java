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
 * @describe �÷������ڻ�ȡӰ������
 * @author Sun
 * 
 */
public class HandleImageUpload {
	private Logger logger = Logger.getLogger(HandleImageUpload.class);
	private SQLQuery sqlQuery = null;
	private String sJMID = "";// JMID

	private String sImageType = "";//
	private String sParentPath =  HandleImageUpload.class.getResource("/").getPath().replace("classes/", "")+NIOProperty.getProperty("saveFilePath");

	private Map<String, Object> requestMap = null;// ���뱨��
	private String sUserID = "";// �����˱��
	private String sOrgID = "";// ��������������
	private String sUserName = "";// ����������
	private String sOrgName = "";// ���ڻ�������
	private String sBusinessType = "";// ҵ��Ʒ��
	private String sCustomerType = "";// �ͻ�����
	private String sCertID = "";// ֤�����

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
	 * @describe �÷�����������ͼƬ����
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
			// ɾ�����ݿ�ͼ��·����Ϣ
			deleteImageMessage();
			// ɾ��������ͼƬ��Ϣ
			String sImagePath = sParentPath + "//" + this.sJMID;
			deleteServerImage(sImagePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("ѭ������ͼƬ����");
			throw e;
		}finally{
			if(rs!=null){
				rs.getStatement().close();
			}
		}

	}

	/**
	 * @describe �÷�����������Ӱ��������Ϣ
	 * @throws Exception
	 */
	private void deleteImageMessage() throws Exception {
		try {
			String sSql = "delete from RETAIL_IMAGEMESSAGE where JMID = '"
					+ this.sJMID + "'";
			this.sqlQuery.execute(sSql);
		} catch (Exception e) {
			logger.info("����Ӱ�����ϼ�¼����");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @describe �÷�������ɾ����������ͼƬ��Ϣ
	 * 
	 */
	private void deleteServerImage(String folderPath) throws Exception {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			logger.info("ɾ��������Ӱ�����ϳ���");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @throws Exception
	 * @describe ɾ��ָ���ļ����������ļ�
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
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				try {
					deleteServerImage(path + "/" + tempList[i]);
				} catch (Exception e) {
					logger.info("ɾ��Ӱ����Ϣ����");
					e.printStackTrace();
					throw e;
				}// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @describe �÷������ڴ����������
	 * @param sImageType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HashMap uploadImageMessage(String sImageType) {
		HashMap hashMap = new HashMap();
		hashMap.put("BatchType", sImageType);// ͼ������
		hashMap.put("UserID", this.sUserID);// �û����
		hashMap.put("OrgID", this.sOrgID);// �������
		hashMap.put("OrgName", this.sOrgName);// ��������
		hashMap.put("UserName", this.sUserName);// �û�����
		hashMap.put("BusinessType", sBusinessType);// ҵ��Ʒ��
		hashMap.put("CertID", sCertID);// ֤�����
		hashMap.put("CustomerName", Tools.getObjectToString(requestMap.get(
				"ChineseName").toString()));// �ͻ�����
		hashMap.put("CustomerType", sCustomerType);// �ͻ�����
		return hashMap;
	}

}
