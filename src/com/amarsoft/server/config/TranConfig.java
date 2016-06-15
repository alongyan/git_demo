package com.amarsoft.server.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class TranConfig {
	private String id;
	private String describe;
	private String extend;
	private Map<String, FiledConfig> filedMap = new TreeMap<String, FiledConfig>();
	private Map<String, Object> xmlModelMap = new TreeMap<String, Object>();
	private ArrayList<String> filedMapIndex = new ArrayList<String>();
	private ArrayList<String> xmlModelMapIndex = new ArrayList<String>();
	
	public Map<String, Object> getXmlModelMap() {
		return xmlModelMap;
	}

	public void addFiledConfig(FiledConfig filed){
		filedMap.put(filed.getName(), filed);
		filedMapIndex.add(filed.getName());
	}
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Map<String, FiledConfig> getFiledMap() {
		return filedMap;
	}
	public void setFiledMap(Map<String, FiledConfig> filedMap) {
		this.filedMap = filedMap;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getExtend() {
		return extend;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public ArrayList<String> getFiledMapIndex() {
		return filedMapIndex;
	}
	public void setFiledMapIndex(ArrayList<String> filedMapIndex) {
		this.filedMapIndex = filedMapIndex;
	}


	public ArrayList<String> getXmlModelMapIndex() {
		return xmlModelMapIndex;
	}

	
	public void initXMLModelMap() {
		for(Iterator<String> it = filedMap.keySet().iterator(); it.hasNext(); ){
			String key = (String)it.next();
			FiledConfig fc = filedMap.get(key);
			if("Map".equalsIgnoreCase(fc.getType())){
				Map<String, FiledConfig> filedConfigMap = fc.getFiledConfigMap();
				Map<String, Object> xmlModelSubMap = new TreeMap<String, Object>();
				for(Iterator<String> fcmit = filedConfigMap.keySet().iterator(); fcmit.hasNext(); ){
					String fcmkey = fcmit.next();
					xmlModelSubMap.put(fcmkey, filedConfigMap.get(fcmkey).getDefaultValue());
				}
				xmlModelMap.put(fc.getName(), new ArrayList().add(xmlModelSubMap));
			}else if("List".equalsIgnoreCase(fc.getType())){
				Map<String, FiledConfig> filedConfigMap = fc.getFiledConfigMap();
				Map<String, Object> xmlModelSubMap = new TreeMap<String, Object>();
				for(Iterator<String> fcmit = filedConfigMap.keySet().iterator(); fcmit.hasNext(); ){
					String fcmkey = fcmit.next();
					xmlModelSubMap.put(fcmkey, filedConfigMap.get(fcmkey).getDefaultValue());
				}
				xmlModelMap.put(fc.getName(), new ArrayList().add(xmlModelSubMap));
			}else{
				xmlModelMap.put(fc.getName(), fc.getDefaultValue());
			} 
		}
	}
	
	public FiledConfig getFiledConfigByName(String filedName){
		if(filedMap.containsKey(filedName)){
			return filedMap.get(filedName);
		}
		return null;
	}

}
