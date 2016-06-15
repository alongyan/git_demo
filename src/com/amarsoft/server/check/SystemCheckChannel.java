package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.config.ChannelConfig;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
/**
 * ������������õĽ��׺ţ��ж������Ƿ�֧��������Ľ���
 * @author Administrator
 *
 */
public class SystemCheckChannel extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		try {
			String actionId = (String) map.get(ServerTranConfig.getInstance().getTranIDLabel());
			Map<String, Object> srcMap = map;
			String channel = (String) srcMap.get("Channel");
			ChannelConfig cc = ServerActionConfig.getInstance().getChannelConfigById(channel);
			return cc != null && cc.isContainAction(actionId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}

}
