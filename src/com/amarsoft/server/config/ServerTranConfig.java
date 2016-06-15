package com.amarsoft.server.config;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.digester3.Digester;
import org.apache.log4j.Logger;

public class ServerTranConfig {
	public static Logger logger = Logger.getLogger(ServerTranConfig.class);
	public static String configFile="E:/WorkSpaces/AmarHTTPServer/WebContent/WEB-INF/etc/ServerTranConfig.xml";
	private static Map<String, TranConfig> tranMap = new HashMap<String, TranConfig>();
	private static Map<String, PropertyConfig> propertyMap = new HashMap<String, PropertyConfig>();
	private static ServerTranConfig stc = null;

	private ServerTranConfig(){
		try {
			logger.info("初始化Check开始........");
			initConfig();
			initTranXMLModel();
			initExtendFiled();
			logger.info("初始化Check完成........");
		} catch (Exception e) {
			logger.error("初始化Check失败........" + e.toString());
			logger.info("初始化Check失败........" + e.toString());
			e.printStackTrace();
//			System.exit(0);
		}
	}
	
	public String getTranIDLabel(){
		return getPropertyConfig("TranID").getValue();
	}
	public String getResponseCodeLabel(){
		return getPropertyConfig("ResponseCode").getValue();
	}
	public String getResponseDescribeLabel(){
		return getPropertyConfig("ResponseDescribe").getValue();
	}

	private void initTranXMLModel() {
		for(Iterator<String> it = tranMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			tranMap.get(key).initXMLModelMap();
		}
	}
	
	private void initExtendFiled(){
		for(Iterator<String> it = tranMap.keySet().iterator(); it.hasNext(); ){
			String key = it.next();
			TranConfig tc = tranMap.get(key);
			String extend = tc.getExtend();
			if(null != extend && extend.trim().length() > 0){
				TranConfig extendTC = tranMap.get(tc.getExtend());
				tc.getFiledMap().putAll(extendTC.getFiledMap());
				ArrayList<String> filedMapIndex = new ArrayList<String>();
				filedMapIndex.addAll(extendTC.getFiledMapIndex());
				filedMapIndex.addAll(tc.getFiledMapIndex());
				tc.setFiledMapIndex(filedMapIndex);
			}
		}
	}
	private void initConfig() throws Exception{
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addObjectCreate("trans/properties/property",PropertyConfig.class);
		digester.addSetProperties("trans/properties/property");
		digester.addObjectCreate("trans/tran",TranConfig.class);
		digester.addSetProperties("trans/tran");
		digester.addObjectCreate("trans/tran/filed",FiledConfig.class);
		digester.addSetProperties("trans/tran/filed");
		digester.addObjectCreate("trans/tran/filed/filed",FiledConfig.class);
		digester.addSetProperties("trans/tran/filed/filed");
		digester.addSetNext("trans/properties/property","addPropertyConfig");
		digester.addSetNext("trans/tran", "addTranConfig");
		digester.addSetNext("trans/tran/filed", "addFiledConfig");
		digester.addSetNext("trans/tran/filed/filed", "addFiledConfig");
		digester.parse(new FileInputStream(configFile));
	}
	
	public void addTranConfig(TranConfig tran){
		tranMap.put(tran.getId(), tran);
	}
	public void addPropertyConfig(PropertyConfig pc){
		propertyMap.put(pc.getName(), pc);
	}

	public static synchronized ServerTranConfig getInstance(){
		if(stc == null){
			stc = new ServerTranConfig();
		}
		return stc;
	}
	public TranConfig getTranConfig(String tranId){
		return tranMap.get(tranId);
	}
	
	public PropertyConfig getPropertyConfig(String name){
		return propertyMap.get(name);
	}
	
	public Map<String, TranConfig> getTranMap(){
		return tranMap;
	}
	
	public static void main(String[] args){
		ServerTranConfig sac = ServerTranConfig.getInstance();
		String value = sac.getTranConfig("requestIN100").getFiledConfigByName("AgentOrg").getMappingValue("中國");
		System.out.println(value);
//		Map map = (sac.getTranConfig("requestIN100").getFiledMap());
//		TranConfig tc = sac.getTranConfig("requestEDuBalance");
//		ArrayList list = tc.getFiledMapIndex();
//		for(int i = 0; i < list.size(); i ++){
//			System.out.println(list.get(i));
//		}
	}
	
	
	
	
}
