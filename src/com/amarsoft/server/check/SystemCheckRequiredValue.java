package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.util.StrUtil;
/**
 * 报文必输项检查
 * @author Administrator
 *
 */
public class SystemCheckRequiredValue extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		try {
			String actionId = (String) map.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				FiledConfig fc = requestFiledMap.get(key);

				if("Y".equals(fc.getRequire()) && StrUtil.isNull((String)map.get(key))){
					errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
					errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "标签要素:"+key+" 没有值，请确认！");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
			logger.error(e.toString());
			return false;
		}
	}
}
