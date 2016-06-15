package com.amarsoft.server.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.NIOProperty;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.config.TranConfig;


public class XMLUtil {
	public static final String CHARSET = NIOProperty.getInstance().getProperty("charset");
	private XMLUtil() {
	}

	/**
	 * 将String形式的xml数据转化为Map形式，此方法只支持简单的xml属性形式，对于嵌套的不支持
	 * 
	 * @param strMsg
	 * @return
	 * @throws DocumentException
	 */
	public static Map getMapFromXML(String strMsg) throws DocumentException {
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
	 * 将String形式的xml数据转化为Map形式，嵌套的支持
	 * @param strMsg
	 * @return
	 * @throws DocumentException
	 */
	public static Map getMapFromXML(String strMsg, String actionId) throws DocumentException {
		String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
		TranConfig tc = ServerTranConfig.getInstance().getTranConfig(tranId);
		ArrayList requestIndex = tc.getFiledMapIndex();
		int length = requestIndex.size();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(strMsg));
		Element root = document.getRootElement();
		
		Map requestMap = new HashMap();
		
		for(int i = 0; i < length; i ++){
			String key = (String) requestIndex.get(i);
			FiledConfig fc = tc.getFiledConfigByName(key);
			if ("Map".equalsIgnoreCase(fc.getType())) {
				ArrayList list = new ArrayList();
				List elements = root.elements(fc.getName());
				for(int k = 0; k < elements.size(); k ++){
					List eleList = ((Element)elements.get(k)).elements();
					Map fcMap = fc.getFiledConfigMap();
					Map subMap = new HashMap();
					for(int j = 0; j < eleList.size(); j ++){
						Element ele = (Element) eleList.get(j);
						String filedName = ele.getName();
						String filedValue = ele.getTextTrim();
						if(fcMap.containsKey(filedName)){
							subMap.put(filedName, filedValue);
						}
					}
					list.add(subMap);
				}
				if(list.size() > 0 )
					requestMap.put(fc.getName(), list);
			} else if ("List".equalsIgnoreCase(fc.getType())) {
				ArrayList list = new ArrayList();
				List elements = root.elements(fc.getName());
				for(int k = 0; k < elements.size(); k ++){
					List eleList = ((Element)elements.get(k)).elements();
					Map fcMap = fc.getFiledConfigMap();
					Map subMap = new HashMap();
					for(int j = 0; j < eleList.size(); j ++){
						Element ele = (Element) eleList.get(j);
						String filedValue = ele.getTextTrim();
						list.add(filedValue);
					}
				}
				if(list.size() > 0)
					requestMap.put(fc.getName(), list);
			} else {
				Element element = root.element(fc.getName());
				if(element != null)	
					requestMap.put(element.getName(), element.getTextTrim());
			}
		}
		return requestMap;
	}
	
	/**
	 * 将Map对象转化为xml形式，此方法只支持简单的键值对Map形式，对于嵌套的不支持
	 * @param responseMap
	 * @return
	 */
	public static String getXMLFromMap(Map<String, Object> responseMap) {
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

	public static void main(String[] args) throws Exception {
//		Map<String, Object> responseMap = new TreeMap<String, Object>();
//		responseMap.put("Code", "0000");
//		responseMap.put("Describe", "成功");
//		//System.out.println(XMLUtil.getXMLFromMap(responseMap));
		String str = "<?xml version=\"1.0\" encoding=\""+CHARSET+"\"?><Request><TranNo>CMS0001</TranNo><Channel>ATM</Channel><AccountNo>1234567890</AccountNo><List><Address>北京</Address><Address>上海</Address></List></Request>";
		String tranID = "requestEDuBalance";
		System.out.print(XMLUtil.getMapFromXML(str, tranID));
	}
	
	/**
	 * 将Map对象转化为xml形式，支持嵌套复杂的Map对象
	 * @param responseMap
	 * @param actionId
	 * @return
	 */
	public static String getXMLFromMap(Map<String, Object> responseMap, String actionId) {
		String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getResponse();
		TranConfig tc = ServerTranConfig.getInstance().getTranConfig(tranId);
		ArrayList responseIndex = tc.getFiledMapIndex();
		int length = responseIndex.size();
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\""+CHARSET+"\"?><Response>");
		for (int i = 0; i < length; i ++) {
			String key = (String) responseIndex.get(i);
			FiledConfig fc = tc.getFiledConfigByName(key);
			if (fc == null || "String".equalsIgnoreCase(fc.getType())) {
				sb.append("<").append(key).append(">").append(
						responseMap.get(key)).append("</").append(key).append(
						">");
			} else if ("Map".equalsIgnoreCase(fc.getType())) {
				Map<String, FiledConfig> subFiledMap = fc.getFiledConfigMap();
				FiledConfig subFC = null;
				ArrayList list = (ArrayList) responseMap.get(key);
				int size = list.size();
				if (size == 0) {
					sb.append("<").append(key).append(">").append("</").append(key).append(">");
				} else {
					for (int ii = 0; ii < size; ii++) {
						sb.append("<").append(key).append(">");
						Map subMap = (Map) list.get(ii);
						for (Iterator<String> subit = subMap.keySet().iterator(); subit.hasNext();) {
							String subKey = subit.next();
							subFC = subFiledMap.get(subKey);
							sb.append("<").append(subKey).append(">").append(FiledUtil.getStrValue(subMap.get(subKey).toString(), subFC.getType(), subFC.getLength())).append("</").append(subKey).append(">");
						}
						sb.append("</").append(key).append(">");
					}
				}
			} else if ("List".equalsIgnoreCase(fc.getType())) {
				ArrayList list = (ArrayList) responseMap.get(key);
				int size = list.size();
				if (size == 0) {
					sb.append("<").append(key).append(">").append("</").append(key).append(">");
				} else {
					Map<String, FiledConfig> subFiledMap = fc.getFiledConfigMap();
					Iterator<String> subFiledIt = subFiledMap.keySet().iterator();
					String subFiledString = null;
					FiledConfig subFC = null;
					if (subFiledIt.hasNext()) {
						subFC = subFiledMap.get(subFiledIt.next());
						subFiledString = subFC.getName();
					}
					sb.append("<").append(key).append(">");
					for (int ii = 0; ii < size; ii++) {
						sb.append("<").append(subFiledString).append(">").append(FiledUtil.getStrValue(list.get(ii).toString(), subFC.getType(), subFC.getLength())).append("</").append(subFiledString).append(">");
					}
					sb.append("</").append(key).append(">");
				}
			} else {
				sb.append("<").append(key).append(">").append(
						FiledUtil.getStrValue(responseMap.get(key).toString(), fc.getType(), fc.getLength())).append("</").append(key).append(
						">");
			}
		}
		sb.append("</Response>");
		return sb.toString();
	}

}
