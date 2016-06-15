package com.amarsoft.server.config;
/*
 * 实体交易的配置类，对应ServerActionConfig 中的 action
 */
public class ActionConfig {
	private String id;
	private String path;
	private String className;
	private String checkList;
	private String request;
	private String response;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCheckList() {
		return checkList;
	}
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getRequest() {
		return request;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
}
