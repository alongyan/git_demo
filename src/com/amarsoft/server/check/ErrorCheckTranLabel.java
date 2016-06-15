package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;

public class ErrorCheckTranLabel extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			Map<String, Object> requestMapModel = ServerTranConfig.getInstance().getTranConfig(tranId).getXmlModelMap();
			for(Iterator<String> it = requestMapModel.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				if(!requestMap.containsKey(key)){
					return false;
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
