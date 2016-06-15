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
		//  初始化返回Map对象
		initResponseMap();
		//1、初始化检查对象并执行
		initAndExcuteCheckList(requestMap, errorMap, sqlQuery);
		if(errorMap.size() > 0)
			return null;
		//2、将请求中的map的key转为大写
//		requestMap = getCaseInsensitiveMap(requestMap);
		//3、执行业务逻辑
		return action(requestMap, errorMap, sqlQuery);
		
	}
	
	/**
	 * 对responseMap进行深拷贝，防止map模型进行变更，引起数据混乱
	 */
	private void initResponseMap() {
		// TODO Auto-generated method stub
		Map<String, Object> responseMapModel = ServerTranConfig.getInstance().getTranConfig(actionConfig.getResponse()).getXmlModelMap();
		responseMap.putAll(responseMapModel);
		//System.out.println(responseMap.size());
	}
	protected abstract Map<String, Object> action(Map<String, Object> requestMap, Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception;
	
	/**
	 * 初始化Action配置的检查类，如果相应的检查出现错误，则将相应的检查配置放置到errorMap中
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
	 * 打印错误日志
	 * @param e
	 */
	protected void printLog(Exception e){
		e.printStackTrace();
		logger.info(e.getMessage());
		logger.error(e.getMessage());
	}
}
