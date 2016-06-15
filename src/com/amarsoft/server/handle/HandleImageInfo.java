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
 * @describe 该方法用于获取影像资料
 * @author Sun
 *
 */
public class HandleImageInfo {
	private Logger logger = Logger.getLogger(HandleImageInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID 
	private String sParentPath = HandleImageInfo.class.getResource("/").getPath().replace("classes/", "")+NIOProperty.getProperty("saveFilePath");
	private String sStatus = "";//数据获取状态
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
	 * @describe 该方法用于处理影像资料信息
	 */
	public void handleImageInfoMessage()throws Exception{
		try{
			deleteImageMessage();
			String sJsonStr = "{\"Image\":"+hashMap.get("attachment")+"}";
			JSONObject jsonObject = JSONObject.fromObject(sJsonStr);//对图片模块进行转换
			JSONArray jsonArray = jsonObject.getJSONArray("Image");
			
			for (int i=0;i<jsonArray.size();i++){
				JSONObject jsonstr = (JSONObject) jsonArray.get(i);
				handleImageArray(jsonstr);
			}
		}catch (Exception e){
			logger.info("处理业务");
			e.printStackTrace();
			throw e;
		}
	}
	
	private void handleImageArray(JSONObject jsonstr) throws Exception{
		
		Iterator it = jsonstr.keys();  
	    // 遍历jsonObject数据，添加到Map对象  
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
	 * @describe 该方法用于清理影像资料信息
	 * @throws Exception
	 */
	private void deleteImageMessage()throws Exception{
		try{
			String sSql = "delete from RETAIL_IMAGEMESSAGE where JMID = '"+this.sJMID+"'";
			this.sqlQuery.execute(sSql);
		}catch(Exception e){
			logger.info("清理影像资料记录错误");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * @describe 该方法用于插入影响资料信息
	 * @param sImagePath
	 * @param sImageName
	 * @param sURLpath
	 * @throws Exception
	 */
	private void insertImageMessage(String sImageType, String sImagePath,String sImageName,String sURLpath)throws Exception{
		String sSerialNo = "";
		try{
			sSerialNo = DBFunction.getSerialNo("RETAIL_IMAGEMESSAGE", "SERIALNO");//该字段用于获取影像资料信息
			String sSql = "insert into RETAIL_IMAGEMESSAGE(SERIALNO,JMID,IMAGEPATH,IMAGENAME,URLPATH,INPUTDATE,IMAGETYPE) "+
			" values(:SERIALNO,:JMID,:IMAGEPATH,:IMAGENAME,:URLPATH,:INPUTDATE,:IMAGETYPE)";
			
			SQLHandleInfo sSqlHandleImage = new SQLHandleInfo(sSql)
			.setParameter("SERIALNO", sSerialNo)//申请流水号
			.setParameter("JMID", this.sJMID)//积木编号
			.setParameter("IMAGEPATH", sImagePath)//图片路径
			.setParameter("IMAGENAME", sImageName)//图片名称
			.setParameter("URLPATH", sURLpath)//图片地址
			.setParameter("INPUTDATE", Tools.getToday(2))//处理日期
			.setParameter("IMAGETYPE", sImageType);//图片类型
			
			logger.info("执行插入影像资料信息"+sSqlHandleImage.getSql());
			this.sqlQuery.execute(sSqlHandleImage.getSql());
			
		}catch(Exception e){
			logger.info("插入影像资料错误");
			e.printStackTrace();
			throw e;
		}
	}
	
}
