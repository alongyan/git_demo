package com.amarsoft.server.check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amarsoft.server.config.CheckConfig;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;



public class SystemCheckAction {
	private Map<String, Object> map = new HashMap<String, Object>();
	
	
	/**
	 * ����ϵͳ��飬���˳�����������ļ��е�systemchecks�еĴ��ϵ���˳��
	 * @param strMsg
	 * @param errorMap
	 */
	public void action(String strMsg, Map<String, Object> errorMap) {
		List<String> systemList = ServerActionConfig.getInstance().getSystemCheckList();
		for(int i = 0; i < systemList.size(); i ++){
			String systemCheckId = systemList.get(i);
			CheckConfig cc = ServerActionConfig.getInstance().getSystemCheckConfig(systemCheckId);
			ErrorConfig ec = ServerActionConfig.getInstance().getErrorConfig(cc.getErrorCode());
			SystemCheck sc = null;
			try {
				sc = (SystemCheck) Class.forName(cc.getClassName()).newInstance();
				if(!sc.excute(strMsg, map, errorMap, ec)){
					if(errorMap.size() == 0){
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ec.getDescribute());
					}
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}
	
	/**
	 * ���������ĵ�map����
	 * @return
	 */
	public Map<String, Object> getRequestMap(){
		return map;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.map = requestMap;
	}
}
