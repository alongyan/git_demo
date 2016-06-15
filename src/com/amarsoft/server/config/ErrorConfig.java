package com.amarsoft.server.config;
/*
 * 实体交易的配置类，对应ServerActionConfig 中的 error
 */
public class ErrorConfig {
	private String id;
	private String type;
	private String describute;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescribute() {
		return describute;
	}
	public void setDescribute(String describute) {
		this.describute = describute;
	}
}
