package com.amarsoft.server.util;
/*
 * @describer �ķ������ڷ���JBO���д���SQL���
 * 
 */
public class SQLHandleInfo {
	private String sSql = "";
	//��ʼ��
	public SQLHandleInfo(String sSql){
		this.sSql = sSql;
	}
	
	/**
	 * @describe �ķ������ڴ���SQL������ַ�����������
	 * @param name
	 * @param value
	 * @return
	 */
	 public SQLHandleInfo setParameter(String name, String value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, "'"+value+"'");
		 return this;
	  }
	 /**
	  * @describe �÷������ڴ���double ��������
	  * @param name
	  * @param value
	  * @return
	  */
	 public SQLHandleInfo setParameter(String name, double value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, value+"");
		 return this;
	  } 
	 /**
	  * @describe �÷������ڴ���int ��������
	  * @param name
	  * @param value
	  * @return
	  */
	 public SQLHandleInfo setParameter(String name, int value) {
		 this.sSql = this.sSql.replaceFirst(":"+name, value+"");
		 return this;
	  } 
	 /**
	  * @descirbe �ķ������ڷ���������ɵ�SQL���
	  * @return
	  */
	 public String getSql(){
		 return this.sSql;
	 }
}
