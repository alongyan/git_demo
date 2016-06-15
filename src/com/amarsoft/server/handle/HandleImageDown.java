package com.amarsoft.server.handle;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.ImageUtil;

/**
 * @describe 该方法用于获取影像资料
 * @author Sun
 *
 */
public class HandleImageDown {
	private Logger logger = Logger.getLogger(HandleImageDown.class);
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID
	
	private String strUrl = "";//路径
	private String sFlile = "";//本地路径
	private String fileName = "";//文件名称
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleImageDown(SQLQuery sqlQuery,String sJMID){
		this.sqlQuery=sqlQuery;
		this.sJMID = sJMID;
	}
	/**
	 * @describe 该方法用于下载图片内容
	 * @throws Exception
	 */
	public void  handleImageDownMessage () throws Exception{
		ResultSet rs =null;
		try {
			
			String sSql = "select ri.imagepath as sFile , ri.imagename as fileName,ri.urlpath as Url from RETAIL_IMAGEMESSAGE ri where jmid = '"+sJMID+"'";
			rs = this.sqlQuery.getResultSet(sSql);
			while(rs.next()){
				strUrl = rs.getString("Url"); if(strUrl == null)  strUrl = "";
				sFlile = rs.getString("sFile"); if(sFlile == null)  sFlile = "";
				fileName = rs.getString("fileName");  if(fileName == null)  fileName = "";
				sFlile = sFlile.replace(fileName, "");
				ImageUtil.writeFile(strUrl,sFlile, fileName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("循环下载图片错误");
			throw e;
		}finally{
			if(rs != null){
				rs.getStatement().close();
			}
		}
	}
}
