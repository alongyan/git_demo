package com.amarsoft.server.config;
/*
 * 实体交易的配置类，对应ServerActionConfig 中的 check
 */
public class CheckConfig {
	private String id;
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	private String errorCode;
}
