package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @describe �÷������ڻ�ȡӰ������
 * @author Sun
 *
 */
public class HandleImageInfo {
	private Logger logger = Logger.getLogger(HandleImageInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String sParentPath = HandleImageInfo.class.getResource("/").getPath().replace("classes/", "")+NIOProperty.getProperty("saveFilePath");
	private String sStatus = "";//���ݻ�ȡ״̬
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleImageInfo(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sJMID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sJMID = sJMID;
	}
	/**
	 * @describe �÷������ڴ���Ӱ��������Ϣ
	 */
	public void handleImageInfoMessage()throws Exception{
		try{
			deleteImageMessage();
			String sJsonStr = "{\"Image\":"+hashMap.get("attachment")+"}";
			JSONObject jsonObject = JSONObject.fromObject(sJsonStr);//��ͼƬģ�����ת��
			JSONArray jsonArray = jsonObject.getJSONArray("Image");
			
			for (int i=0;i<jsonArray.size();i++){
				JSONObject jsonstr = (JSONObject) jsonArray.get(i);
				handleImageArray(jsonstr);
			}
		}catch (Exception e){
			logger.info("����ҵ��");
			e.printStackTrace();
			throw e;
		}
	}
	
	private void handleImageArray(JSONObject jsonstr) throws Exception{
		
		Iterator it = jsonstr.keys();  
	    // ����jsonObject���ݣ���ӵ�Map����  
		while (it.hasNext())  
		{  
		   int k =0;
           String key = String.valueOf(it.next());  
           JSONArray jsonArray =  (JSONArray) jsonstr.get(key);  
           String sImagePaht = sParentPath+"//"+this.sJMID+"//"+key+"//";
           for(int i=0;i<jsonArray.size();i++){
        	   k++;
        	   String sImageName = k+".jpg";
        	   String sURLpath = jsonArray.getString(i);
        	   insertImageMessage (key, sImagePaht+sImageName,sImageName,sURLpath);
           }
           
		}  
	}
	
	/**
	 * @describe �÷�����������Ӱ��������Ϣ
	 * @throws Exception
	 */
	private void deleteImageMessage()throws Exception{
		try{
			String sSql = "delete from RETAIL_IMAGEMESSAGE where JMID = '"+this.sJMID+"'";
			this.sqlQuery.execute(sSql);
		}catch(Exception e){
			logger.info("����Ӱ�����ϼ�¼����");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * @describe �÷������ڲ���Ӱ��������Ϣ
	 * @param sImagePath
	 * @param sImageName
	 * @param sURLpath
	 * @throws Exception
	 */
	private void insertImageMessage(String sImageType, String sImagePath,String sImageName,String sURLpath)throws Exception{
		String sSerialNo = "";
		try{
			sSerialNo = DBFunction.getSerialNo("RETAIL_IMAGEMESSAGE", "SERIALNO");//���ֶ����ڻ�ȡӰ��������Ϣ
			String sSql = "insert into RETAIL_IMAGEMESSAGE(SERIALNO,JMID,IMAGEPATH,IMAGENAME,URLPATH,INPUTDATE,IMAGETYPE) "+
			" values(:SERIALNO,:JMID,:IMAGEPATH,:IMAGENAME,:URLPATH,:INPUTDATE,:IMAGETYPE)";
			
			SQLHandleInfo sSqlHandleImage = new SQLHandleInfo(sSql)
			.setParameter("SERIALNO", sSerialNo)//������ˮ��
			.setParameter("JMID", this.sJMID)//��ľ���
			.setParameter("IMAGEPATH", sImagePath)//ͼƬ·��
			.setParameter("IMAGENAME", sImageName)//ͼƬ����
			.setParameter("URLPATH", sURLpath)//ͼƬ��ַ
			.setParameter("INPUTDATE", Tools.getToday(2))//��������
			.setParameter("IMAGETYPE", sImageType);//ͼƬ����
			
			logger.info("ִ�в���Ӱ��������Ϣ"+sSqlHandleImage.getSql());
			this.sqlQuery.execute(sSqlHandleImage.getSql());
			
		}catch(Exception e){
			logger.info("����Ӱ�����ϴ���");
			e.printStackTrace();
			throw e;
		}
	}
	
}
