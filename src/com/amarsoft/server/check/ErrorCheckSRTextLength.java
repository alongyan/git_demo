package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author yhwang
 * @describe 该类用来检查字段长度
 * @return boolean
 */

public class ErrorCheckSRTextLength extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			int  fileLength =0;
			int requestLength =0;
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				System.out.println(key);
				FiledConfig fc = requestFiledMap.get(key);
				if("Text".equals(fc.getStyle())){
					if(!isNull(requestMap.get(key))){
						fileLength =Integer.parseInt(fc.getLength());
						System.out.println("fileLength===="+fileLength);
						requestLength =(int)requestMap.get(key).toString().length();
						System.out.println("requestLength====="+requestLength);
						if(requestLength > fileLength){
							Tools.insertErrorMessage(key, key+"字段超长", sProjectNo, sqlQuery);
							return false;
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
	
	/**
	 * 判断是否为空或""
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str){
		return null == str || "".equals(str);
	}
	
}
