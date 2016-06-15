package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckEnumValue extends ErrorCheck{

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			Map<String, Object> srcMap = requestMap;
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			Map<String, FiledConfig> requestFieldMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			for(Iterator<String> it = requestFieldMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				FiledConfig fc = requestFieldMap.get(key);
				
				String value = (String)srcMap.get(key);
				if(!isEnumValue(value, fc)){
					throw new Exception("×Ö¶Î£º" + key + "Öµ£º" + value + ", ·Ç·¨£¡");
				}
			}
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
	
	private boolean isEnumValue(String value, FiledConfig fc){
		if(StrUtil.isNull(fc.getEnumValue())){
			return true;
		}
		return fc.isEnumValue(value);
	}

}
