package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.StrUtil;

public class ErrorCheckMappingValue extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				FiledConfig fc = requestFiledMap.get(key);

				if(!"".equals(StrUtil.notNull(fc.getMapping()))){
					String srcValue = (String) requestMap.get(fc.getName());
					String mapValue = fc.getMappingValue(srcValue);
					if("".equals(StrUtil.notNull(mapValue))){
						throw new Exception("字段：" + fc.getName() + "没有找到" + srcValue + "对应的Mapping值:{" + fc.getMapping() + "}");
					}
					requestMap.put(fc.getName(), mapValue);
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
