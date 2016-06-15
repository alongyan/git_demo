package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
/**
 * ���ϵͳ�Ƿ��ṩ�����͵Ľ��ף������ױ���Ƿ��������ļ��н���������
 * @author Administrator
 *
 */
public class SystemCheckTranId extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> srcMap, Map<String, Object> errorMap, ErrorConfig ec) {
		// TODO Auto-generated method stub
		try {
			String actionId = (String) srcMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			System.out.println(ServerTranConfig.getInstance().getTranIDLabel());
			System.out.println(actionId);
			return !(null == ServerActionConfig.getInstance().getActionConfig(actionId));
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}

}
