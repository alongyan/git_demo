package com.amarsoft.server.util;


/**
 * @author ymwu 2013/09/27
 * @describe ��ʼ��xml���ɹ�����,������õ���ģʽ,������Լ��ṩ�Լ���ʵ��
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
	/*���һ���޸������ļ���ʱ��*/
	private static long lastModifiedTime = 0;

	private static XMLMessageUtilInit xmlMessageUtilInit = new XMLMessageUtilInit();

	/**
	 * @describe ˽�л����췽��
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
	 * @describe ��ø����Ψһʵ��
	 * @return ����xmlMessageUtilInit���Ψһʵ��
	 * */
	synchronized public static XMLMessageUtilInit getInstance() {
		return xmlMessageUtilInit;
	}

	/**
	 * @describe ��ȡxmlMessage��������Ϣ������ֵ�ŵ�һ����̬map��
	 * @exception XmlMessageConfig����ֵ�ļ���ʼ������
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
	 * @describe �������xml�ļ���������Ϣ,���޸������ļ�����Զ����¼���
	 * @return ���ؾ���������Ϣ��map
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

