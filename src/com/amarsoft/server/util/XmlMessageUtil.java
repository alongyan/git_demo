package com.amarsoft.server.util;
/**
 * @author ymwu 2013/09/27
 * @describe 根据配置文件XmlMessageConfig.xml和输入的数据生成xml子符串
 * @param
 * @return
 * @history
 * **/
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;



public class XmlMessageUtil {

	/**
	 * @describe 根据数据的格式和插入生成xml字符串
	 * @param msgcfgname XmlMessageConfig.xml文件中配置messageList的名称
	 * @param sparam 要生成xml文件的节点名称及内容
	 * @param 指定生成xml的编码格式
	 * @return xml格式的String
	 * @exception 生成xml文件异常
	 * */
	public static String getXMLMessage(String msgcfgname,String sparam,String Encoding){
		
		Map<String,String> sParamMap = new HashMap<String,String>();
		String Message = "";//拼接报文返回报文信息
		String [] sresource = sparam.split("@");//多个节点的参数用@分隔
		if(sresource.length%2>0){
			return "";	
		}
		for(int i=0;i<sresource.length/2;i++){
			sParamMap.put(sresource[i*2],sresource[i*2+1]);
		}
		try {
			OutputFormat format = new OutputFormat("", true);
			StringWriter out = new StringWriter();
			format.setEncoding(Encoding);
			XMLWriter xml = new XMLWriter(out,format);
			xml.write(createXml(msgcfgname,sParamMap));
			xml.flush();
			Message = out.toString();
			Message = Message.replaceAll("[\\t\\n\\r]", "");
			return Message;//返回生成的xml报文
		} catch (Exception e) {
			e.printStackTrace();
			return " Error！";
		}
		
	}
	
	/**
	 * @describe 
	 * @param msgcfgname  XmlMessageConfig.xml文件中配置messageList的名称
	 * @param sParamMap
	 * **/
	private static  Document createXml(String msgcfgname,Map <String,String>  sParamMap){
		List<XMLNode> ls = getXMLNode(msgcfgname);//获得配置的xml
		Element root =null;
		Document document = DocumentHelper.createDocument();  
		for(XMLNode node:ls){
			if(node.getParentElement().length()==0){//创建根元素
				root = document.addElement(node.getElementName());
			} else if (node.getPath().length() == 0) {
				getXML(root, node, sParamMap);
			} else {
				List<Element> list1 = root.selectNodes(node.getPath());
				for (Element e : list1) {
					if (e.getName().equals(node.getParentElement())) {
						getXML(e, node, sParamMap);
					}
				}
			}
		}
		return document;
	}

	
	/**
	 * @describe
	 * @param msgcfgname XmlMessageConfig.xml文件中配置messageList的名称
	 * **/
	private static List<XMLNode> getXMLNode(String msgcfgname){
		Element ele =(Element)XMLMessageUtilInit.getInstance().getXmlCfgmap().get(msgcfgname);
		List<Element> list = ele.elements();
		List<XMLNode> listNode = new ArrayList<XMLNode>();
		for (Element es : list) {
			String parentElement = es.attribute("parentElement").getValue();
			String resourceType = es.attribute("resourceType").getValue();
			String resource = es.attribute("resource").getValue();
			String path = es.attribute("path").getValue();
			String nodeid = es.attribute("nodeid").getValue();
			listNode.add(new XMLNode(parentElement, resourceType, resource, es
					.getText(), path, nodeid));
		}
		return listNode;
	}

	/**
	 * @describe
	 * @param el
	 * @param node
	 * @param sParamMap
	 * **/
	private static void getXML(Element el, XMLNode node, Map<String,String> sParamMap){
		Element ele =null;
		if(node.getElementName().length()>0&&node.getResourceType().length()==0){//
			ele = el.addElement(node.getElementName());	
		}else{
			ele =el;
		}
		if ("db".equals(node.getResourceType())) {
			if (node.getResource().length() > 0) {//
				String sql = node.getResource();
				String sResource = sql.substring(
						sql.toUpperCase().indexOf("SELECT") + 6,
						sql.toUpperCase().indexOf("FROM")).trim();
				String str2[] = sResource.split(" as ");
				String str3 = "";
				for (int i = 1; i < str2.length; i++) {
					String s[] = str2[i].split(",");
					str3 = str3 + s[0] + ",";
				}
				String[] str = str3.replace(" ", "").split(",");
				List<Map<String,String>> listmap = getValue(sql + " "+ sParamMap.get(node.getNodeID()), str);// 循环次数
				addNodes(listmap, ele, node);
			}
		} else if ("ndb".equals(node.getResourceType())) {
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			String sStr = sParamMap.get(node.getNodeID()).toString();
			if (sStr.length() > 0) {
				if(sStr.endsWith(","))sStr = sStr+" ";
				String str[] = sStr.split("#");// 如果同一个节点有多条记录用#分隔
				for (int i = 0; i < str.length; i++) {
					Map<String,String> map = new HashMap<String,String>();
					String sr[] = str[i].split(",");
					for (int j = 0; j < sr.length/2; j++) {
						map.put(sr[j*2], sr[j*2 + 1]);
					}
					list.add(map);
				}
				addNodes(list, ele, node);
			}
		}
	}

	/**
	 * @describe  从数据库中获取文本节点的内容并将结果以<key><value>的形式放到map中
	 * @param sql 获取数据的sql语句
	 * @param str sql语句的字段名称也就是生成xml的标签名称
	 * **/
	private static List<Map<String,String>> getValue(String sql,String [] str){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			conn = DBCPManager.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int k = 0;
			while (rs.next()) {
				k = rs.getRow();
				Map<String,String> map = new HashMap<String,String>();
				for (int i = 0; i < str.length; i++) {
					String colvalue = rs.getString(str[i]);
					if (colvalue == null)
						colvalue = "";
					map.put(str[i], colvalue);
				}
				list.add(map);
			}
			if (k == 0) {
				Map <String,String>map = new HashMap<String,String>();
				for (int i = 0; i < str.length; i++) {
					map.put(str[i], "");
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * @describe 在指定的节点上生成文本节点及内容
	 * @param listmap 拥有文本节点内容的map
	 * @param ele 在那个节点上添加文本节点
	 * @param node XmlMessageConfig.xml中配置的层次
	 * @param str 所要生成文本节点的名称
	 * */
	private static void addNodes(List<Map<String,String>> listMap,Element ele ,XMLNode node){
		for(Map<String,String> map1 :listMap){
			Element element =null;
			if(node.getElementName().length()>0){
				element = ele.addElement(node.getElementName());
			} else {
				element = ele;
			}
			Iterator it = map1.keySet().iterator();
			while(it.hasNext()){
				String Key = it.next().toString();
				element.addElement(Key).setText(map1.get(Key).toString());
			}
		}
	}
}
