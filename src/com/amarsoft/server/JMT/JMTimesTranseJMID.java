package com.amarsoft.server.JMT;


import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.GetJMTest;
import com.amarsoft.server.util.JMTimesEncrypt;

import net.sf.json.JSONObject;
 
/**
 * @describe 该方法用于根据JMID获取积木时代系统合同阶段业务信息编号
 * @author xlsun 
 * @date 2015-07-30
 *
 */
public class JMTimesTranseJMID{
	private static final Logger logger = Logger.getLogger(JMTimesTranseJMID.class);
	public void getJMIDInfo(SQLQuery sqlQuery,String sJMID) throws Exception{
		//创建请求Map数组
		HashMap requestMap = new HashMap();
		//获取基础数据中配置参数内容
		HashMap mapItem = getCodeItemNo(sqlQuery);
		//根据配置参数拼接参数报文
			String sJMIDDes = getDES(sJMID,(String)mapItem.get("100"));//根据传递的JMID进行加密处理
			requestMap.put("soleNumber", sJMIDDes);//把JMID放置于请求报文数组中
			String sToken = JMTimesEncrypt.initFingerprint((String)mapItem.get("090"), requestMap);//对所有数据进行加密
			String sParam = "soleNumber="+URLEncoder.encode(sJMIDDes, "UTF-8")+
					"&key="+sToken;//拼接传递参数内容
			
			logger.info("积木时代获取业务合同阶段信息--sParam =="+sParam);
			
			String sMessage = GetJMTest.doPost((String)mapItem.get("080"), sParam);//调用Post方法，发送消息给积木时代系统，并获取反馈报文
			
			System.out.println("sMessage =="+sMessage);//打印获取报文内容
			logger.info("积木时代获取业务合同阶段信息--sMessage =="+sMessage);
			
			//转换获取内容为JSON格式
			JSONObject json = JSONObject.fromObject(sMessage);
			//在报文中获取resultInfo 字段的值
			JSONObject json2 = (JSONObject) json.get("resultInfo");
			//调用类对获取到的JSON 字符串进行处理，插入数据库
			JMTimesGetBusinessInfo jb = new JMTimesGetBusinessInfo();
			jb.updateBusinessInfo(json2, sqlQuery, sJMID);
		
	}
	
	/**
	 * @describe 该方法用于获取数据库中配置的链接内容以及参数
	 * @param Sqlca
	 * @return
	 */
	private HashMap getCodeItemNo(SQLQuery sqlQuery) throws Exception{
		HashMap map = new HashMap();
		ResultSet rs = null;
		String sSql = "select itemno,itemdescribe from code_library  where codeno = 'JMTimes'";
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()){
			String sItemNo = rs.getString("itemno"); if(sItemNo == null)  sItemNo = "";//编号
			String sItemDescribe = rs.getString("itemdescribe"); if(sItemDescribe == null)  sItemDescribe = "";//字段内容
			map.put(sItemNo, sItemDescribe);//把获取到的数据放置于Map中
		}
		DBCPManager.getInstance().free(rs, rs.getStatement(), null);
		
		return map;
	}
	
	/**
	 * @describe 该方法用于对数据进行DES加密处理
	 * @param sStr
	 * @return
	 * @throws Exception
	 */
	private String getDES(String sStr,String sKey) throws Exception{
		JMTimesEncrypt je = new JMTimesEncrypt();
		return je.encryptBASE64(sStr,sKey.getBytes());
	}
}
