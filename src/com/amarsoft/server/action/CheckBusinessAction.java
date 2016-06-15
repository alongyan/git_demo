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
 * @describe �������ڽ�����������ϵͳ��
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
			sStatus = "��֤ʧ��";//����״̬
			sMessage = "ȱ�ٱ��������ǩ����֤ʧ��";//������Ϣ
		}else {
			String checkResult = checkUserKey((String)requestMap.get("SPID"),(String)requestMap.get("Ticket"),sqlQuery);//ͨ��SPID��Ticket����У���Ƿ�ͨ����
			if("False".equals(checkResult)){//��֤��ͨ��
				sStatus = "��֤ʧ��";//����״̬
				sMessage = "ȱ�ٱ��������ǩ����֤ʧ��";//������Ϣ
			}else if("Success".equals(checkResult)){//��֤ͨ��
				if(checkProjectNo(sProjectNo,sqlQuery)){// ��Ŀδ����
					if("12001".equals(requestMap.get("SPID"))){//ôô��
						JMMEMELoanHandle jMMEMELoanHandle = new JMMEMELoanHandle();
						jMMEMELoanHandle.jMMEMELoanSet((HashMap) requestMap, sqlQuery);
					}
					if("12011".equals(requestMap.get("SPID"))){//���ñ�
						JMXinYongBaoHandle jMXinYongBaoHandle = new JMXinYongBaoHandle();
						jMXinYongBaoHandle.jMXinYongBaoSet((HashMap) requestMap, sqlQuery);
					}
					if("12010".equals(requestMap.get("SPID"))){//����
						JMMMLoanHandle jMMMLoanHandle = new JMMMLoanHandle();
						jMMMLoanHandle.jMMMloanSet((HashMap) requestMap, sqlQuery);
					}
					if("12009".equals(requestMap.get("SPID"))){//�Խ��
						JMTaoJinJiaHandle jMTaoJinJiaHandle = new JMTaoJinJiaHandle();
						jMTaoJinJiaHandle.jMTaoJinJiaSet((HashMap) requestMap, sqlQuery);
					}
				}else {//��Ŀ�Ѵ���
					sStatus = "�ظ���Ŀ";//����״̬
					sMessage = "����ProjectNo����";//������Ϣ
				}
			}
		}*/
		res.put("ChineseName", (String)requestMap.get("ChineseName"));
		res.put("IdentityNumber", (String)requestMap.get("IdentityNumber"));
		res.put("ProjectID", sJMID);
		res.put("ProjectNo", (String)requestMap.get("ProjectNo"));
		res.put("Status", "OK");
		res.put("Message", "���׳ɹ�");
		return res;
	}
}
