package com.amarsoft.server.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class NIOProperty {
	public static String configFile = "";
	private Properties p = new Properties();
	private static NIOProperty niop = null;
	private NIOProperty(){
		Logger logger = Logger.getLogger(NIOProperty.class);
        try {
        	logger.info("��ʼ ��ȡNIO����������Ϣ......");
			p.load(new FileInputStream(new File(configFile)));
			logger.info("��� ��ȡNIO����������Ϣ......");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("ʧ�� ��ȡNIO����������Ϣ......" + e.getMessage());
//			System.exit(0);
		}
	}
	
	public static synchronized NIOProperty getInstance(){
		if(niop == null){
			niop = new NIOProperty();
		}
		return niop;
	}
	
	public static String getProperty(String key){
		return NIOProperty.getInstance().p.getProperty(key);
	}
	public static Properties getProperties(){
		return NIOProperty.getInstance().p;
	}
	
	public static void main(String[] args){
		Properties p = NIOProperty.getProperties();
		for(Enumeration<?> enu = p.elements(); enu.hasMoreElements();){
			System.out.println(enu.nextElement());
		}
	}
}
