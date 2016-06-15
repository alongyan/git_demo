package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author ywhang
 * @describe 该类用来检查字段类型
 * @return boolean
 */

public class ErrorCheckSRItemType extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			
			sqlQuery.execute("delete from Error_RetailMessage where projectno='"+sProjectNo+"'");//清除错误信息记录
			
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			Object sRequestType ="";
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				System.out.println(key);
				FiledConfig fc = requestFiledMap.get(key);
				if("String".equals(fc.getType())){
					//判断String类型
					if(!isNull((Object)requestMap.get(key))){
						sRequestType = requestMap.get(key);
						System.out.println("sRequestType====="+sRequestType);
						if(!(sRequestType instanceof String)){
							Tools.insertErrorMessage(key, key+"值类型不正确", sProjectNo, sqlQuery);
							return false;
						}
					}
				}else if("Integer".equals(fc.getType())){
					//判断Integer类型
					if(!isNull((Object)requestMap.get(key))){
						sRequestType = requestMap.get(key);
						System.out.println("sRequestType====="+sRequestType);
						if(!(sRequestType instanceof Integer)){
							Tools.insertErrorMessage(key, key+"值类型不正确", sProjectNo, sqlQuery);
							return false;
						}
					}
				}else if("Double".equals(fc.getType())){
					//判断Double类型
					if(!isNull((Object)requestMap.get(key))){
						sRequestType = requestMap.get(key);
						System.out.println("sRequestType====="+sRequestType);
						if(!(sRequestType instanceof Double)){
							if(!(sRequestType instanceof Integer)){
								Tools.insertErrorMessage(key, key+"值类型不正确", sProjectNo, sqlQuery);
								return false;
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
	
	/**
	 * 判断是否为空或""
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str){
		return null == str || "".equals(str);
	}
}
