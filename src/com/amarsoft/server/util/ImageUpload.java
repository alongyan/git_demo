package com.amarsoft.server.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.XMLMessageUtilInit;
import com.amarsoft.server.handle.HandleImageUpload;


public class ImageUpload {
	
	private HashMap  hashMap = null;
	private SQLQuery sqlQuery = null;
	private String imageType ="";
	private String sJMID="";
	private Logger logger = Logger.getLogger(HandleImageUpload.class);
	public ImageUpload(String JMID,String imageType,HashMap hashMap,SQLQuery sqlQuery){
		this.sJMID =JMID;
		this.imageType = imageType;
		this.hashMap = hashMap;
		this.sqlQuery = sqlQuery;
	}
	
	public void imageUploadMessage() throws Exception{
		String sRequestMessage = createMessage();
		String sUrl = this.sqlQuery.getString("Select ItemDescribe from Code_Library where CodeNo ='ImageConfig' and Isinuse ='1' and Upper(ItemNo) = 'UPLOAD'");
		upLoad(sUrl,sRequestMessage);
	}
 
	
	private String upLoad(String url, String sMessage) throws Exception{
		String sInfo ="";
		GetJMTest.doSRPost(url+"?method=queryForData", sMessage);
		return sInfo;
	}
	

	/**
	 * 
	 * @author
	 * @date 2015-6-16
	 * @describe 将图片转换成二进制字符串
	 * @parameter
	 * @return
	 * @throws Exception 
	 */
	private String imagetoString(String sFileName) throws Exception{
		BASE64Encoder encoder = new BASE64Encoder();
		File file = new File(sFileName);
		if(!file.exists())return "系统找不到指定的"+file+"不存在!";
		BufferedImage bi =null;
		ByteArrayOutputStream baos =null;
		byte [] bytes =null;
		try {
			bi = ImageIO.read(file);
			baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg",baos);
			bytes = baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(bi!=null)bi.flush();
				if(baos!=null)baos.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return encoder.encode(bytes);
	}
	
	/**
	 * 
	 * @author 
	 * @date 2015-6-19
	 * @describe 生成xml报文
	 * @parameter sFileName 需要上传的图片路径，如果有多个图片用'||'分隔
	 * @return
	 * @throws Exception 
	 */
	private String createMessage() throws Exception{
		StringBuffer sf =new StringBuffer(4000);
		XMLMessageUtilInit.getInstance().getXmlCfgmap();
		sf.append("3@").append("operateuserID").append(",").append(hashMap.get("UserID")).append("#")
		.append("batchType").append(",").append(hashMap.get("BatchType")).append("#")
		.append("customerID").append(",").append(hashMap.get("CertID")).append("#")
		.append("customerName").append(",").append(hashMap.get("CustomerName")).append("#")
		.append("customerType").append(",").append(hashMap.get("CustomerType")).append("#")
		.append("version").append(",").append("").append("#")
		.append("businessStatus").append(",").append("1010").append("#")
		.append("operateuserName").append(",").append(hashMap.get("UserName")).append("#")
		.append("operateOrgName").append(",").append(hashMap.get("OrgName")).append("#")
		.append("product").append(",").append(hashMap.get("BusinessType")).append("#")
		.append("systemCode").append(",").append("QLH").append("#")
		.append("operateOrgID").append(",").append(hashMap.get("OrgID")).append("#")
		.append("channelCode").append(",").append("").append("#")
		.append("projectNO").append(",").append(sJMID).append("#");
		String sFile="";
		ArrayList<String> sFileName = new ArrayList<String>();
		String sSql = "select ri.imagepath as sFile  from RETAIL_IMAGEMESSAGE ri where jmid = '"+ sJMID + "'  and  ri.imagetype ='"+imageType+"'";
		ResultSet rs=null;
		try {
			rs = this.sqlQuery.getResultSet(sSql);
			while(rs.next()){
				sFile = rs.getString("sFile");
				sFileName.add(sFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null) rs.getStatement().close();
		}
		
		int count=0;
		for(String s:sFileName){
			String sImageString;
			try {
				sImageString = imagetoString(s);
				if(count>0){
					sf.append("#");
				}
				sf.append("image").append(count).append(",").append(sImageString);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		String sMessge =  XmlMessageUtil.getXMLMessage("ImageUpload", sf.toString(),"UTF-8");
		/*获取交易报文*/
		return sMessge;
	}
}
