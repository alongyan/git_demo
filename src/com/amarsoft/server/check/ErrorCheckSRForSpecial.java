package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @author ywhang
 * @describe ��������������ڽӿ��е�������У��
 * @return boolean
 */

public class ErrorCheckSRForSpecial extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			Object sRequestType ="";
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				System.out.println(key);
				FiledConfig fc = requestFiledMap.get(key);
				//����ס״��Ϊ����ʱ��������
				if("Familystatus".equals(fc.getName())){
					if(("����").equals((String)requestMap.get(key))){
						System.out.println("Familystatus====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("Monthrent"))){
							Tools.insertErrorMessage(key, "FamilystatusֵΪ����ʱMonthrent������ֵ", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
				//�˵��ʼĵ�ַΪ����ʱ������ַ����
				if("CommAdd".equals(fc.getName())){
					if(("����").equals((String)requestMap.get(key))){
						System.out.println("CommAdd====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("OtherAddress"))){
							Tools.insertErrorMessage(key, "CommAddֵΪ����ʱOtherAddress������ֵ", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
				//��������;Ϊ����ʱ������;��������
				if("DebtUsage".equals(fc.getName())){
					if(("����").equals((String)requestMap.get(key))){
						System.out.println("DebtUsage====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("Purpose"))){
							Tools.insertErrorMessage(key, "DebtUsageֵΪ����ʱPurpose������ֵ", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			printLog(e);
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ�Ϊ�ջ�""
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str){
		return null == str || "".equals(str);
	}
}
