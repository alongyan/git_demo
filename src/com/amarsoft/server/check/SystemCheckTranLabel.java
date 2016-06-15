package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
/**
 * 检查接收到的报文是否与XML中配置的交易属性是否一致
 * @author Administrator
 *
 */
public class SystemCheckTranLabel extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> srcMap, Map<String, Object> errorMap, ErrorConfig ec) {
		// TODO Auto-generated method stub
		try {
			String actionId = (String) srcMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			Map<String, Object> requestMapModel = ServerTranConfig.getInstance().getTranConfig(tranId).getXmlModelMap();
			for(Iterator<String> it = requestMapModel.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				if(!srcMap.containsKey(key)){
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "缺少要素:"+key+"");
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
