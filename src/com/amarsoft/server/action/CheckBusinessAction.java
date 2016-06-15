package com.amarsoft.server.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.object.JMMEMELoanHandle;
import com.amarsoft.server.object.JMMMLoanHandle;
import com.amarsoft.server.object.JMTaoJinJiaHandle;
import com.amarsoft.server.object.JMXinYongBaoHandle;
import com.amarsoft.server.util.CollectionUtil;
import com.amarsoft.server.util.GetJMID;
import com.amarsoft.server.util.MD5;

/**
 * @describe 该类用于接收所有零售系统外
 * @author Administrator
 *
 */
public class CheckBusinessAction extends Action {
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		// TODO Auto-generated method stub
		HashMap res = new HashMap();
		
		JMMEMELoanHandle jMMEMELoanHandle = new JMMEMELoanHandle();
		String sJMID = jMMEMELoanHandle.jMMEMELoanSet((HashMap) CollectionUtil.getChangeMap(requestMap, new HashMap()), sqlQuery);
/*		if(!(checkMapKey(requestMap,"SPID") && checkMapKey(requestMap,"Ticket"))){
			sStatus = "验证失败";//反馈状态
			sMessage = "缺少必须参数或签名验证失败";//反馈信息
		}else {
			String checkResult = checkUserKey((String)requestMap.get("SPID"),(String)requestMap.get("Ticket"),sqlQuery);//通过SPID与Ticket进行校验是否通过。
			if("False".equals(checkResult)){//验证不通过
				sStatus = "验证失败";//反馈状态
				sMessage = "缺少必须参数或签名验证失败";//反馈信息
			}else if("Success".equals(checkResult)){//验证通过
				if(checkProjectNo(sProjectNo,sqlQuery)){// 项目未存在
					if("12001".equals(requestMap.get("SPID"))){//么么贷
						JMMEMELoanHandle jMMEMELoanHandle = new JMMEMELoanHandle();
						jMMEMELoanHandle.jMMEMELoanSet((HashMap) requestMap, sqlQuery);
					}
					if("12011".equals(requestMap.get("SPID"))){//信用宝
						JMXinYongBaoHandle jMXinYongBaoHandle = new JMXinYongBaoHandle();
						jMXinYongBaoHandle.jMXinYongBaoSet((HashMap) requestMap, sqlQuery);
					}
					if("12010".equals(requestMap.get("SPID"))){//买单侠
						JMMMLoanHandle jMMMLoanHandle = new JMMMLoanHandle();
						jMMMLoanHandle.jMMMloanSet((HashMap) requestMap, sqlQuery);
					}
					if("12009".equals(requestMap.get("SPID"))){//淘金家
						JMTaoJinJiaHandle jMTaoJinJiaHandle = new JMTaoJinJiaHandle();
						jMTaoJinJiaHandle.jMTaoJinJiaSet((HashMap) requestMap, sqlQuery);
					}
				}else {//项目已存在
					sStatus = "重复项目";//反馈状态
					sMessage = "按照ProjectNo排重";//反馈信息
				}
			}
		}*/
		res.put("ChineseName", (String)requestMap.get("ChineseName"));
		res.put("IdentityNumber", (String)requestMap.get("IdentityNumber"));
		res.put("ProjectID", sJMID);
		res.put("ProjectNo", (String)requestMap.get("ProjectNo"));
		res.put("Status", "OK");
		res.put("Message", "交易成功");
		return res;
	}
}
