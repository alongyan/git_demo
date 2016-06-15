package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class ErrorCheckSRRequiredValue extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				System.out.println(key);
				FiledConfig fc = requestFiledMap.get(key);
				
				if("String".equals(fc.getType())){
					if("Y".equals(fc.getRequire()) && StrUtil.isNull((String)requestMap.get(key))){
						Tools.insertErrorMessage(key, key+"数据缺少", sProjectNo, sqlQuery);
						return false;
					}
				}else{
					if("Y".equals(fc.getRequire()) && Tools.isObjectNull(requestMap.get(key))){
						Tools.insertErrorMessage(key, key+"数据缺少", sProjectNo, sqlQuery);
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
