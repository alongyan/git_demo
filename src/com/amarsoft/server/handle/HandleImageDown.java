package com.amarsoft.server.handle;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.ImageUtil;

/**
 * @describe �÷������ڻ�ȡӰ������
 * @author Sun
 *
 */
public class HandleImageDown {
	private Logger logger = Logger.getLogger(HandleImageDown.class);
	private SQLQuery sqlQuery = null;
	private String sJMID = "";//JMID
	
	private String strUrl = "";//·��
	private String sFlile = "";//����·��
	private String fileName = "";//�ļ�����
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleImageDown(SQLQuery sqlQuery,String sJMID){
		this.sqlQuery=sqlQuery;
		this.sJMID = sJMID;
	}
	/**
	 * @describe �÷�����������ͼƬ����
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
			logger.info("ѭ������ͼƬ����");
			throw e;
		}finally{
			if(rs != null){
				rs.getStatement().close();
			}
		}
	}
}
