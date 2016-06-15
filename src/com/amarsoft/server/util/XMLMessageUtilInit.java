package com.amarsoft.server.util;


/**
 * @author ymwu 2013/09/27
 * @describe 初始化xml生成工具类,该类采用单例模式,该类会自己提供自己的实例
 * @param
 * @return
 * @history
 * */
import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.amarsoft.server.config.XmlUtitTest;

public class XMLMessageUtilInit {

	public static HashMap xmlCfgMap = new HashMap();
	public static String sFilePath = XmlUtitTest.configpaht;
	private File myConfigFile = null;
	/*最后一次修改配置文件的时间*/
	private static long lastModifiedTime = 0;

	private static XMLMessageUtilInit xmlMessageUtilInit = new XMLMessageUtilInit();

	/**
	 * @describe 私有化构造方法
	 * **/
	private XMLMessageUtilInit() {
		System.out.println("==========="+sFilePath);
		myConfigFile = new File(sFilePath);
		lastModifiedTime = myConfigFile.lastModified();
		if (lastModifiedTime == 0) {
			System.out.println(" XmlMessageConfig.xml file not exist");
		}
		getXmlMessageConfig();
	}

	/**
	 * @describe 获得该类的唯一实例
	 * @return 返回xmlMessageUtilInit类的唯一实例
	 * */
	synchronized public static XMLMessageUtilInit getInstance() {
		return xmlMessageUtilInit;
	}

	/**
	 * @describe 获取xmlMessage的配置信息并将其值放到一个静态map中
	 * @exception XmlMessageConfig配置值文件初始化错误
	 * **/
	 private  void   getXmlMessageConfig() {
		 SAXReader saxReader = new SAXReader();
			Document document;
			try {
				System.out.println(sFilePath);
			document = saxReader.read(sFilePath);
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			for (Element element : list) {
				xmlCfgMap.put(element.attributeValue("name"), element);
			}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	}
	 

	/**
	 * @describe 获得生成xml文件的配置信息,当修改配置文件后会自动重新加载
	 * @return 返回具有配置信息的map
	 * **/
	public  HashMap getXmlCfgmap() {
		long newTime = myConfigFile.lastModified();
		if(newTime>lastModifiedTime){
			xmlCfgMap.clear();
			getXmlMessageConfig();
			lastModifiedTime =newTime;
		}
		return xmlCfgMap;
	}

}

