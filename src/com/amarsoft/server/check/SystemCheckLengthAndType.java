package com.amarsoft.server.check;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.util.FiledUtil;
/**
 * ����Ҫ��ֵ�ĳ��ȼ�鼰 ��ֵ���ͼ��
 * @author Administrator
 *
 */
public class SystemCheckLengthAndType extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		try {
			String actionId = (String) map.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			Map<String, FiledConfig> requestFieldMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			for(Iterator<String> it = requestFieldMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				FiledConfig fc = requestFieldMap.get(key);
				if("Map".equalsIgnoreCase(fc.getType())){
					Map<String, FiledConfig> subFiledMap = fc.getFiledConfigMap();
					//1 ����Ƿ����Map������ǩ����
					ArrayList list = null;
					try {
						list = (ArrayList) map.get(fc.getName());
					} catch (Exception e) {
						e.printStackTrace();
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+"�����ڻ���������Ϊ�գ�");
						return false;
					}
					//2������Ӧ�����ɵ�Map�ֶμ��
					for(int index = 0; index < list.size(); index ++){
						Map<String, String> subSrcMap = (Map<String, String>) list.get(index);
						for(Iterator<String> subIt = subFiledMap.keySet().iterator(); subIt.hasNext();){
							String subKey = subIt.next();
							if(!FiledUtil.checkType(subSrcMap.get(subKey), subFiledMap.get(subKey).getType(), subFiledMap.get(subKey).getLength())){
								errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
								errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+subKey+"���Ƴ���Ϊ��" + subFiledMap.get(subKey).getLength() + "�����Ͳ���ȷ��");
								return false;
							}
						}
					}
				}else if("List".equalsIgnoreCase(fc.getType())){
					Map<String, FiledConfig> subFiledMap = fc.getFiledConfigMap();
					FiledConfig subFc = null;
					Boolean flag = true;
					for(Iterator<String> subIt = subFiledMap.keySet().iterator(); subIt.hasNext() && flag; flag = false){
						subFc = subFiledMap.get(subIt.next());
					}
					//1 ����Ƿ����Map������ǩ����
					ArrayList list = null;
					try {
						list = (ArrayList) map.get(fc.getName());
					} catch (Exception e) {
						e.printStackTrace();
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+"�����ڻ���������Ϊ�գ�");
						return false;
					}
					//2������Ӧ�����ɵ�Map�ֶμ��
					for(int index = 0; index < list.size(); index ++){
						if(!FiledUtil.checkType((String)list.get(index), subFc.getType(), subFc.getLength())){
							errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
							errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+subFc.getName()+"���Ƴ���Ϊ��" + subFc.getLength() + "�����Ͳ���ȷ��");
							return false;
						}
					}
				}else{
					String value = (String)map.get(key);
					if(!FiledUtil.checkType(value, requestFieldMap.get(key).getType(), requestFieldMap.get(key).getLength())){
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+"���Ƴ���Ϊ��" + requestFieldMap.get(key).getLength() + "�����Ͳ���ȷ��");
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}

}
