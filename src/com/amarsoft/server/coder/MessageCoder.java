package com.amarsoft.server.coder;

import java.util.Map;

public interface MessageCoder {
	//
	public Map<String, Object> decode(String strMsg) throws Exception;
	
	public String encode(Map<String, Object> map) throws Exception;
}
