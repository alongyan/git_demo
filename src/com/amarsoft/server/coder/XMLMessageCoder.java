package com.amarsoft.server.coder;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.amarsoft.server.config.NIOProperty;

public class XMLMessageCoder implements MessageCoder {
	public static final String CHARSET = NIOProperty.getInstance().getProperty("charset");
	
	/**
	 * 将String形式的xml数据转化为Map形式，此方法只支持简单的xml属性形式，对于嵌套的不支持
	 * 
	 * @param strMsg
	 * @return
	 * @throws DocumentException
	 */
	public Map decode(String strMsg) throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(strMsg));
		Element root = document.getRootElement();
		Map map = new HashMap();
		// iterate through child elements of root
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			map.put(element.getName(), element.getTextTrim());
		}
		return map;

	}
	
	/**
	 * 将Map对象转化为xml形式，此方法只支持简单的键值对Map形式，对于嵌套的不支持
	 * @param responseMap
	 * @return
	 */
	public String encode(Map<String, Object> responseMap) throws Exception{
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\""+CHARSET+"\"?><Response>");
		for (Iterator<String> it = responseMap.keySet().iterator(); it
				.hasNext();) {
			String key = it.next();
			sb.append("<").append(key).append(">").append(responseMap.get(key))
					.append("</").append(key).append(">");
		}
		sb.append("</Response>");
		return sb.toString();
	}
}
