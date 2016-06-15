package com.amarsoft.server.check;
/**
 * �̳м����ʵ�ּ�鷽�������������Ҫ��try catch ���Է�ֹ��������ܼ�ʱ������Ϣ
 */
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.dao.SQLQuery;

public interface Check {
	/**
	 * ϵͳ�����õ�ִ�з���
	 * @param srcMsg �ͻ��˴��͵��ַ�������
	 * @param map  �ͻ��˴��͵�map���ģ�������Ϣ���������ַ������Ľ���ת��
	 * @param errorMap �����Ĵ�ŵ�����
	 * @param ec ����ʵ�������Ӧ�Ĵ�����Ϣ����
	 * @return
	 */
	public abstract boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec);
	
	/**
	 * ҵ������õ�ִ�з���
	 * @param requestMap
	 * @param sqlQuery
	 * @return
	 */
	public abstract boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery);
}
