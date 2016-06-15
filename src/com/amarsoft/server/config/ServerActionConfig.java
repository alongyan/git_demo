package com.amarsoft.server.config;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester3.Digester;
import org.apache.log4j.Logger;

import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.util.StrUtil;

public class ServerActionConfig {
	public static String configFile="etc/ServerActionConfig.xml";
	private static Logger logger = Logger.getLogger(ServerActionConfig.class);
	private static Map<String, PropertyConfig> propertyMap = new HashMap<String, PropertyConfig>();
	private static Map<String, ErrorConfig> successMap = new HashMap<String, ErrorConfig>();
	private static Map<String, ErrorConfig> failMap = new HashMap<String, ErrorConfig>();
	private static Map<String, ErrorConfig> errorMap = new HashMap<String, ErrorConfig>();
	private static Map<String, CheckConfig> errorCheckMap = new HashMap<String, CheckConfig>();
	private static Map<String, CheckConfig> systemCheckMap = new HashMap<String, CheckConfig>();
	private static Map<String, ActionConfig> actionMap = new HashMap<String, ActionConfig>();
	private static Map<String, ActionConfig> actionMapPathCathe = new HashMap<String, ActionConfig>();
	private static Map<String, ChannelConfig> channelMap = new HashMap<String, ChannelConfig>();
	private static List<String> systemCheckIndex = new ArrayList<String>();
	private static ServerActionConfig sac = null;
	private static MessageCoder messageCoder = null;
	private ServerActionConfig(){
		try {
			initConfig();
			checkTranID();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void initConfig() throws Exception{
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addObjectCreate("config/properties/property",PropertyConfig.class);
		digester.addSetProperties("config/properties/property");
		digester.addObjectCreate("config/errors/error",ErrorConfig.class);
		digester.addSetProperties("config/errors/error");
		digester.addObjectCreate("config/systemchecks/check",CheckConfig.class);
		digester.addSetProperties("config/systemchecks/check");
		digester.addObjectCreate("config/errorchecks/check",CheckConfig.class);
		digester.addSetProperties("config/errorchecks/check");
		digester.addObjectCreate("config/actions/action",ActionConfig.class);
		digester.addSetProperties("config/actions/action");
		digester.addObjectCreate("config/channels/channel",ChannelConfig.class);
		digester.addSetProperties("config/channels/channel");
		digester.addSetNext("config/properties/property","addPropertyConfig");
		digester.addSetNext("config/errors/error", "addErrorConfig");
		digester.addSetNext("config/actions/action", "addActionConfig");
		digester.addSetNext("config/systemchecks/check", "addSystemCheckConfig");
		digester.addSetNext("config/errorchecks/check", "addErrorCheckConfig");
		digester.addSetNext("config/channels/channel", "addChannelConfig");
		digester.parse(new FileInputStream(configFile));
	}
	public void addErrorConfig(ErrorConfig error){
//		if("system".equalsIgnoreCase(error.getType())){
//			systemMap.put(error.getId(), error);
//			systemList.add(error);
//		}else 
		if("success".equalsIgnoreCase(error.getType())){
			successMap.put(error.getId(), error);
		} else if("fail".equalsIgnoreCase(error.getType())){
			failMap.put(error.getId(), error);
		} else {
			errorMap.put(error.getId(), error);
		}
	}
	public void addPropertyConfig(PropertyConfig pc){
		replacePropertyValue(pc);
		propertyMap.put(pc.getName(), pc);
	}
	private void replacePropertyValue(PropertyConfig pc) {
		boolean isReplaced = true;
		do{
			String value = pc.getValue();
			String replaceName = StrUtil.getSubStr(value, "{", "}");
			if(replaceName != null && replaceName.trim().length() > 0){
				String replaceValue = propertyMap.get(replaceName).getValue();
				String replaceSrc = "{" + replaceName + "}";
				int startIndex = value.indexOf(replaceSrc);
				int length = replaceSrc.length();
				int endIndex = startIndex + length;
				value = value.substring(0, startIndex) + replaceValue + value.substring(endIndex);
				pc.setValue(value);
			}else{
				isReplaced = false;
			}
		}while(isReplaced);
	}

	public void addActionConfig(ActionConfig action){
		actionMap.put(action.getId(), action);
		actionMapPathCathe.put(action.getPath(), action);
	}
	
	public void addSystemCheckConfig(CheckConfig check){
		systemCheckMap.put(check.getId(), check);
		systemCheckIndex.add(check.getId());
	}
	public void addErrorCheckConfig(CheckConfig check){
		errorCheckMap.put(check.getId(), check);
	}
	
	public void addChannelConfig(ChannelConfig channel){
		channelMap.put(channel.getId(), channel);
	}
	
	public List<String> getSystemCheckList(){
		return systemCheckIndex;
	}
	
	/**
	 * 获取配置文件中属性标签的值
	 * @param name
	 * @return
	 */
	public static String getProperty(String name){
		return ServerActionConfig.getInstance().propertyMap.get(name).getValue();
	}
	
	/**
	 * 检查交易中的配置的交易编号是否已配置
	 */
	private void checkTranID(){
		logger.info("检查Action的配置交易报文 开始......");
		ActionConfig ac = null;
		Map<String, TranConfig> tranMap = ServerTranConfig.getInstance().getTranMap();
		for(Iterator<String> it = actionMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			ac = actionMap.get(key);
			if(!tranMap.containsKey(ac.getRequest())){
				logger.info("Action:" + ac.getId() + " 配置的 request："+ ac.getRequest() +" 不正确!");
				logger.error("Action:" + ac.getId() + " 配置的 request："+ ac.getRequest() +" 不正确!");
				//System.exit(0);
			}
			if(!tranMap.containsKey(ac.getResponse())){
				logger.info("Action:" + ac.getId() + " 配置的 response："+ ac.getResponse() +" 不正确!");
				logger.error("Action:" + ac.getId() + " 配置的 response："+ ac.getResponse() +" 不正确!");
				//System.exit(0);
			}
		}
		logger.info("检查Action的配置交易报文 完成......");
	}
	
	/**
	 * 检查交易中的配置的交易编号是否已配置
	 */
	private void checkMessageCoder(){
		logger.info("检查Action的配置属性messageCoder 开始......");
		String messageCoderName = (String)ServerActionConfig.getInstance().getProperty("messageCoder");
		if(null == messageCoderName || messageCoderName.trim().length() == 0){
			logger.info("Action配置的 messageCoder没有配置!");
			logger.error("Action配置的 messageCoder没有配置!");
			//System.exit(0);
		}else{
			try {
				messageCoder = (MessageCoder) Class.forName(messageCoderName).newInstance();
			} catch (Exception e) {
				logger.info("Action配置的 messageCoder:"+messageCoderName+"不能实例化!" + e.toString());
				logger.error("Action配置的 messageCoder:"+messageCoderName+"不能实例化!" + e.toString());
				//System.exit(0);
			}
		}
		logger.info("检查Action的配置属性messageCoder 完成......");
	}
	
	
	

	public static synchronized ServerActionConfig getInstance(){
		if(sac == null){
			sac = new ServerActionConfig();
			sac.checkMessageCoder();
		}
		return sac;
	}
	public ActionConfig getActionConfig(String actionId){
		return actionMap.get(actionId);
	}
	
	public CheckConfig getErrorCheckConfig(String checkId){
		return errorCheckMap.get(checkId);
	}
	public CheckConfig getSystemCheckConfig(String checkId){
		return systemCheckMap.get(checkId);
	}
	public CheckConfig getCheckConfigByErrorCode(String error){
		for(Iterator<String> it = errorCheckMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			CheckConfig cc = errorCheckMap.get(key);
			if(error.equals(cc.getErrorCode())){
				return cc;
			}
		}
		return null;
	}
	public ErrorConfig getErrorConfig(String errorId){
		return errorMap.get(errorId);
	}
	public static Map<String, ErrorConfig> getErrorMap() {
		return errorMap;
	}
	
	public static ErrorConfig getSuccessConfig() {
		for(Iterator<String> it = successMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			return successMap.get(key);
		}
		return null;
	}
	
	public static ErrorConfig getFailConfig() {
		// TODO Auto-generated method stub
		for(Iterator<String> it = failMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			return failMap.get(key);
		}
		return null;
	}

	
	
	public ChannelConfig getChannelConfigById(String channel) {
		return channelMap.get(channel);
	}
	
	public static void main(String[] args){
		ServerActionConfig sac = ServerActionConfig.getInstance();
		ActionConfig action = sac.getActionConfig("CMS0001");
		System.out.println(action.getClassName());
		CheckConfig check = sac.getErrorCheckConfig("CheckEDu");
		System.out.println(check.getErrorCode());
		System.out.println(ServerActionConfig.getInstance().propertyMap);
		for(Iterator it = ServerActionConfig.getInstance().propertyMap.keySet().iterator(); it.hasNext();){
			String key = (String) it.next();
			System.out.println(key + ":" + ServerActionConfig.getProperty(key));
		}
	}

	public MessageCoder getMessageCoder() {
		// TODO Auto-generated method stub
		return this.messageCoder;
	}

	public ActionConfig getActionConfigByPath(String path) {
		return actionMapPathCathe.get(path);
	}

	
	
}
