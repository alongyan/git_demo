package com.amarsoft.server.JMT;


import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.GetJMTest;
import com.amarsoft.server.util.JMTimesEncrypt;

import net.sf.json.JSONObject;
 
/**
 * @describe �÷������ڸ���JMID��ȡ��ľʱ��ϵͳ��ͬ�׶�ҵ����Ϣ���
 * @author xlsun 
 * @date 2015-07-30
 *
 */
public class JMTimesTranseJMID{
	private static final Logger logger = Logger.getLogger(JMTimesTranseJMID.class);
	public void getJMIDInfo(SQLQuery sqlQuery,String sJMID) throws Exception{
		//��������Map����
		HashMap requestMap = new HashMap();
		//��ȡ�������������ò�������
		HashMap mapItem = getCodeItemNo(sqlQuery);
		//�������ò���ƴ�Ӳ�������
			String sJMIDDes = getDES(sJMID,(String)mapItem.get("100"));//���ݴ��ݵ�JMID���м��ܴ���
			requestMap.put("soleNumber", sJMIDDes);//��JMID������������������
			String sToken = JMTimesEncrypt.initFingerprint((String)mapItem.get("090"), requestMap);//���������ݽ��м���
			String sParam = "soleNumber="+URLEncoder.encode(sJMIDDes, "UTF-8")+
					"&key="+sToken;//ƴ�Ӵ��ݲ�������
			
			logger.info("��ľʱ����ȡҵ���ͬ�׶���Ϣ--sParam =="+sParam);
			
			String sMessage = GetJMTest.doPost((String)mapItem.get("080"), sParam);//����Post������������Ϣ����ľʱ��ϵͳ������ȡ��������
			
			System.out.println("sMessage =="+sMessage);//��ӡ��ȡ��������
			logger.info("��ľʱ����ȡҵ���ͬ�׶���Ϣ--sMessage =="+sMessage);
			
			//ת����ȡ����ΪJSON��ʽ
			JSONObject json = JSONObject.fromObject(sMessage);
			//�ڱ����л�ȡresultInfo �ֶε�ֵ
			JSONObject json2 = (JSONObject) json.get("resultInfo");
			//������Ի�ȡ����JSON �ַ������д����������ݿ�
			JMTimesGetBusinessInfo jb = new JMTimesGetBusinessInfo();
			jb.updateBusinessInfo(json2, sqlQuery, sJMID);
		
	}
	
	/**
	 * @describe �÷������ڻ�ȡ���ݿ������õ����������Լ�����
	 * @param Sqlca
	 * @return
	 */
	private HashMap getCodeItemNo(SQLQuery sqlQuery) throws Exception{
		HashMap map = new HashMap();
		ResultSet rs = null;
		String sSql = "select itemno,itemdescribe from code_library  where codeno = 'JMTimes'";
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()){
			String sItemNo = rs.getString("itemno"); if(sItemNo == null)  sItemNo = "";//���
			String sItemDescribe = rs.getString("itemdescribe"); if(sItemDescribe == null)  sItemDescribe = "";//�ֶ�����
			map.put(sItemNo, sItemDescribe);//�ѻ�ȡ�������ݷ�����Map��
		}
		DBCPManager.getInstance().free(rs, rs.getStatement(), null);
		
		return map;
	}
	
	/**
	 * @describe �÷������ڶ����ݽ���DES���ܴ���
	 * @param sStr
	 * @return
	 * @throws Exception
	 */
	private String getDES(String sStr,String sKey) throws Exception{
		JMTimesEncrypt je = new JMTimesEncrypt();
		return je.encryptBASE64(sStr,sKey.getBytes());
	}
}
