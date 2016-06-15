package com.amarsoft.server.coder;

import java.util.Map;

import net.sf.json.JSONObject;

public class JSONMessageCoder implements MessageCoder {

	
	public Map<String, Object> decode(String strMsg) {
		JSONObject jsonObject = JSONObject.fromObject(strMsg);
		Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		return map;
	}

	public String encode(Map<String, Object> map) {
		String retValue = "";
		JSONObject jsonObject = JSONObject.fromObject(map);
		retValue = jsonObject.toString();
		return retValue;
	}

}
