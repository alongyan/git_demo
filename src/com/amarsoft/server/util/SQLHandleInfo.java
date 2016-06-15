package com.amarsoft.server.util;
/*
 * @describer 改方法用于仿照JBO进行处理SQL语句
 * 
 */
public class SQLHandleInfo {
	private String sSql = "";
	//初始化
	public SQLHandleInfo(String sSql){
		this.sSql = sSql;
	}
	
	/**
	 * @describe 改方法用于处理SQL语句中字符串类型数据
	 * @param name
	 * @param value
	 * @return
	 */
	 public SQLHandleInfo setParameter(String name, String value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, "'"+value+"'");
		 return this;
	  }
	 /**
	  * @describe 该方法用于处理double 数据类型
	  * @param name
	  * @param value
	  * @return
	  */
	 public SQLHandleInfo setParameter(String name, double value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, value+"");
		 return this;
	  } 
	 /**
	  * @describe 该方法用于处理int 数据类型
	  * @param name
	  * @param value
	  * @return
	  */
	 public SQLHandleInfo setParameter(String name, int value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, value+"");
		 return this;
	  } 
	 /**
	  * @descirbe 改方法用于反馈处理完成的SQL语句
	  * @return
	  */
	 public String getSql(){
		 return this.sSql;
	 }
}
