package com.amarsoft.server.check;

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author yhwang
 * @describe 该类用来检测图片下载的Url是否可用
 */
public class ErrorCheckUrlAvailable extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			String sJsonStr = "{\"Image\":"+requestMap.get("attachment")+"}";
			JSONObject jsonObject = JSONObject.fromObject(sJsonStr);
			JSONArray jsonArray = jsonObject.getJSONArray("Image");
			InputStream in =null;
			URL url = null;
			for (int i=0;i<jsonArray.size();i++){
				JSONObject jsonstr = (JSONObject) jsonArray.get(i);
				Iterator it = jsonstr.keys();  
			    // 遍历jsonObject数据，添加到Map对象  
				while (it.hasNext())  
				{  
		           String key = String.valueOf(it.next());  
		           JSONArray jsonArray1 =  (JSONArray) jsonstr.get(key);  
		           for(int j=0;j<jsonArray1.size();j++){
		        	   String sURLpath = jsonArray1.getString(j);
		        	   try {
						url = new URL(sURLpath);
						in = url.openStream();
					} catch (Exception e) {
						Tools.insertErrorMessage("URL", "该业务包含不可用的URL,业务编号为@"+sProjectNo,sProjectNo , sqlQuery);
						throw e;
					}finally{
						if(in != null){
							in.close();
						}
					}
		           }
				}  
			}
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
	
}
