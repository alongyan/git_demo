package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
/**
 * 检查接收的报文是否为标准的xml报文
 * @author Administrator
 *
 */
public class SystemCheckTranType extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		// TODO Auto-generated method stub
		try {
			MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
			//防止服务出错什么都不返回，故加上一层try catch
			Map<String, Object> srcMap = messageCoder.decode(srcMsg);
			map.putAll(srcMap);
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
