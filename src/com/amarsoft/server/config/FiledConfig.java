package com.amarsoft.server.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.amarsoft.server.util.StrUtil;

public class FiledConfig {
	private String name;
	private String type;
	private String defaultValue;
	private String length;
	private String require;
	private int lengthValue;
	private String describe;
	private String enumValue;
	private String mapping;
	private String style;
	private String codeNo;
	private Map<String, String> valueMap = null;
	private Set<String> enumValueSet = new HashSet<String>();
	private Map<String, FiledConfig> filedConfigMap = new TreeMap<String, FiledConfig>();
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getCodeNo() {
		return codeNo;
	}
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}
	public Map<String, FiledConfig> getFiledConfigMap() {
		return filedConfigMap;
	}
	public void setFiledConfigMap(Map<String, FiledConfig> filedConfigMap) {
		this.filedConfigMap = filedConfigMap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setLength(String length) {
		this.length = length;
		String[] lengArr = length.split(",");
		for(int i = 0; i < lengArr.length; i ++){
			lengthValue += Integer.parseInt(lengArr[i]);
		}
	}
	public String getLength(){
		return this.length;
	}
	public void setLengthValue(int lengthValue) {
		this.lengthValue = lengthValue;
	}
	public int getLengthValue() {
		return lengthValue;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public void setRequire(String require) {
		this.require = require;
	}
	public String getRequire() {
		return require;
	}
	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
		if(!StrUtil.isNull(enumValue)){
			String[] valueArr = enumValue.split(",");
			for(int i = 0; i < valueArr.length; i ++){
				enumValueSet.add(valueArr[i]);
			}
		}
	}
	public String getEnumValue() {
		return enumValue;
	}
	
	public boolean isEnumValue(String enumValue){
		if(StrUtil.isNull(enumValue)){
			return false;
		}
		return enumValueSet.contains(enumValue);
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
		valueMap = StrUtil.getMapForString(mapping, new String[]{",",":"});
	}
	public String getMapping() {
		return mapping;
	}
	public void addFiledConfig(FiledConfig filedConfig){
		filedConfigMap.put(filedConfig.getName(), filedConfig);
	}
	
	/**
	 * 获取该属性对应配置文件的mapping值，通过key
	 * @param key
	 * @return
	 */
	public String getMappingValue(String key){
		return valueMap.get(key);
	}
}
