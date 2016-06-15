package com.amarsoft.server.check;

import java.util.Map;

import com.amarsoft.server.coder.MessageCoder;
import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.ServerActionConfig;
/**
 * �����յı����Ƿ�Ϊ��׼��xml����
 * @author Administrator
 *
 */
public class SystemCheckTranType extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		// TODO Auto-generated method stub
		try {
			MessageCoder messageCoder = ServerActionConfig.getInstance().getMessageCoder();
			//��ֹ�������ʲô�������أ��ʼ���һ��try catch
			Map<String, Object> srcMap = messageCoder.decode(srcMsg);
			map.putAll(srcMap);
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
}
