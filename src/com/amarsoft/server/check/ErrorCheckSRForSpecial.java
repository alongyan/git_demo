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
 * @describe 该类用来检查禅融接口中的特殊项校验
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
				//当居住状况为租用时月租金必输
				if("Familystatus".equals(fc.getName())){
					if(("租用").equals((String)requestMap.get(key))){
						System.out.println("Familystatus====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("Monthrent"))){
							Tools.insertErrorMessage(key, "Familystatus值为租用时Monthrent必须有值", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
				//账单邮寄地址为其他时其他地址必填
				if("CommAdd".equals(fc.getName())){
					if(("其他").equals((String)requestMap.get(key))){
						System.out.println("CommAdd====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("OtherAddress"))){
							Tools.insertErrorMessage(key, "CommAdd值为其他时OtherAddress必须有值", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
				//当贷款用途为其他时贷款用途描述必输
				if("DebtUsage".equals(fc.getName())){
					if(("其他").equals((String)requestMap.get(key))){
						System.out.println("DebtUsage====="+(String)requestMap.get(key));
						if(isNull(requestMap.get("Purpose"))){
							Tools.insertErrorMessage(key, "DebtUsage值为其他时Purpose必须有值", sProjectNo, sqlQuery);
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
	 * 判断是否为空或""
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str){
		return null == str || "".equals(str);
	}
}
