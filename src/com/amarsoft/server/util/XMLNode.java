package com.amarsoft.server.util;


public class XMLNode {
	private String parentElement ="";//��Ԫ��
	private String resourceType ="";//����Դ����
	private String resource ="";//����Դ
	private String ElementName ="";//Ԫ������
	private String path ="";//��Ԫ��·��
	private String where ="";
	private String sString ="";
	private String NodeID ="";
	
	public String getNodeID() {
		return NodeID;
	}

	public void setNodeID(String nodeID) {
		NodeID = nodeID;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getsString() {
		return sString;
	}

	public void setsString(String sString) {
		this.sString = sString;
	}

	public String getPath() {
		return path;
	}

	public String getElementName() {
		return ElementName;
	}

	public XMLNode(String sparentElement ,String sresourceType,String sresource,String sElementName,String spath,String nodeid ){
		parentElement =sparentElement;
		resourceType =sresourceType;
		resource =sresource;
		ElementName = sElementName;
		path = spath;
		NodeID =nodeid;
	}
	
	public String getParentElement() {
		return parentElement;
	}
	public String getResourceType() {
		return resourceType;
	}
	public String getResource() {
		return resource;
	}
}
