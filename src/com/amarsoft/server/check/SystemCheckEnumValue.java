package com.amarsoft.server.check;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.util.StrUtil;
/**
 * ����Ҫ��ֵ�Ƿ���enumValue�е�һ��
 * @author Administrator
 *
 */
public class SystemCheckEnumValue extends SystemCheck {

	public boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec) {
		try {
			String actionId = (String) map.get(ServerTranConfig.getInstance().getTranIDLabel());
			Map<String, Object> srcMap = map;
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
						list = (ArrayList) srcMap.get(fc.getName());
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
							if(!isEnumValue(subSrcMap.get(subKey), subFiledMap.get(subKey))){
								errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
								errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+" ���ݷǷ���");
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
					ArrayList list = null;
					try {
						list = (ArrayList) srcMap.get(fc.getName());
					} catch (Exception e) {
						e.printStackTrace();
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+"�����ڻ���������Ϊ�գ�");
						return false;
					}
					for(int index = 0; index < list.size(); index ++){
						if(!isEnumValue((String)list.get(index), subFc)){
							errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
							errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+" ���ݷǷ���");
							return false;
						}
					}
				}else{
					String value = (String)srcMap.get(key);
					if(!isEnumValue(value, fc)){
						errorMap.put(ServerTranConfig.getInstance().getResponseCodeLabel(), ec.getId());
						errorMap.put(ServerTranConfig.getInstance().getResponseDescribeLabel(), "��ǩҪ��:"+key+" ���ݷǷ���");
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
	
	private boolean isEnumValue(String value, FiledConfig fc){
		if(StrUtil.isNull(fc.getEnumValue())){
			return true;
		}
		return fc.isEnumValue(value);
	}
}
