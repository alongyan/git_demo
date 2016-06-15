package com.amarsoft.server.config;

import java.util.ArrayList;

import com.amarsoft.server.util.StrUtil;

public class ChannelConfig {
	private String id;
	private String actionItems;
	private String describute;
	private String dd;
	private ArrayList<String> actionList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@SuppressWarnings("unchecked")
	public void setActionItems(String actionItems) {
		this.actionItems = actionItems;
		actionList = StrUtil.getListFromArr(actionItems);
	}
	public String getActionItems() {
		return actionItems;
	}
	public void setActionList(ArrayList<String> actionList) {
		this.actionList = actionList;
	}
	public ArrayList<String> getActionList() {
		return actionList;
	}
	public String getDescribute() {
		return describute;
	}
	public void setDescribute(String describute) {
		this.describute = describute;
	}
	/**
	 * 判断渠道是否包含所请求的action
	 * @param action
	 * @return
	 */
	public boolean isContainAction(String action){
		return actionList.contains(action);
	}
	public static void main(String[] args){
		ChannelConfig cc = new ChannelConfig();
		cc.setActionItems("ATM,WW,TT");
		System.out.println(cc.getActionList());
		System.out.println(cc.isContainAction("ATM"));
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getDd() {
		return dd;
	}
}
