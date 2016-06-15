package com.amarsoft.server.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;

import com.amarsoft.server.check.Check;
import com.amarsoft.server.config.ActionConfig;
import com.amarsoft.server.config.CheckConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;

public abstract class Action {
	protected Logger logger = Logger.getLogger(Action.class);
	protected ActionConfig actionConfig;
	protected String message;
	protected String checks;
	protected Map<String, Object> responseMap = new HashMap<String, Object>();
	
	public Map<String, Object> excute(Map<String, Object> requestMap, Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception{
		//  ��ʼ������Map����
		initResponseMap();
		//1����ʼ��������ִ��
		initAndExcuteCheckList(requestMap, errorMap, sqlQuery);
		if(errorMap.size() > 0)
			return null;
		//2���������е�map��keyתΪ��д
//		requestMap = getCaseInsensitiveMap(requestMap);
		//3��ִ��ҵ���߼�
		return action(requestMap, errorMap, sqlQuery);
		
	}
	
	/**
	 * ��responseMap�����������ֹmapģ�ͽ��б�����������ݻ���
	 */
	private void initResponseMap() {
		// TODO Auto-generated method stub
		Map<String, Object> responseMapModel = ServerTranConfig.getInstance().getTranConfig(actionConfig.getResponse()).getXmlModelMap();
		responseMap.putAll(responseMapModel);
		//System.out.println(responseMap.size());
	}
	protected abstract Map<String, Object> action(Map<String, Object> requestMap, Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception;
	
	/**
	 * ��ʼ��Action���õļ���࣬�����Ӧ�ļ����ִ�������Ӧ�ļ�����÷��õ�errorMap��
	 * @param errorMap 
	 * @param Sqlca 
	 * @throws Exception
	 */
	private void initAndExcuteCheckList(Map<String, Object> requestMap, Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		checks = actionConfig.getCheckList();
		if(null == checks || checks.trim().length() == 0){
			return;
		}
		String[] checkArr = checks.split(",");
		for(int i = 0; i < checkArr.length; i ++){
			CheckConfig cc = ServerActionConfig.getInstance().getErrorCheckConfig(checkArr[i]);
			Check check = (Check)Class.forName(cc.getClassName()).newInstance();
			if(errorMap.size() == 0 && !check.excute(requestMap, sqlQuery)){
				errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), cc.getErrorCode());
				errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), ServerActionConfig.getInstance().getErrorConfig(cc.getErrorCode()).getDescribute());
				break;
			}
		}
	}

	public void setActionConfig(ActionConfig ac) {
		// TODO Auto-generated method stub
		this.actionConfig = ac;
	}
	/**
	 * ��ӡ������־
	 * @param e
	 */
	protected void printLog(Exception e){
		e.printStackTrace();
		logger.info(e.getMessage());
		logger.error(e.getMessage());
	}
}
