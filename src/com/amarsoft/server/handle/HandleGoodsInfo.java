package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷�������������Ʒ��Ϣ
 * @author jxsun
 *
 */
public class HandleGoodsInfo {
	private Logger logger = Logger.getLogger(HandleGoodsInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//�������
	private String sUserID = "";//�û����
	private String sObjectNo = "";//����������ˮ��
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleGoodsInfo(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sOrgID,String sUserID,String sObjectNo){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sObjectNo = sObjectNo;
	}
	/**
	 * @throws Exception 
	 * @describe �÷������ڴ���ҵ��������Ϣģ������
	 */
	public void handleGoodsInfo() throws Exception{
		String message = this.hashMap.get("GoodsInformation")+"";
		message = "{\"Goods\":"+message+"}";
		JSONObject json = JSONObject.fromObject(message);
		JSONArray arrayJson = json.getJSONArray("Goods");
		for(int i=0;i<arrayJson.size();i++){
			insertGoodsInformation(arrayJson.get(i).toString());
		}
	}
	/**
	 * @throws Exception 
	 * @describe �ķ������ڴ���ͻ���������Ϣ������
	 */
	private void insertGoodsInformation(String sJsonStr) throws Exception{
		JSONObject jsonObject = JSONObject.fromObject(sJsonStr);
		System.out.println("===================��Ʒ========"+sJsonStr);
		String sSerialNo = "";
		try {
			sSerialNo = DBFunction.getSerialNo("GOODS_RELATIVE", "SerialNo");
			String sSql = " insert into GOODS_RELATIVE (serialno,objecttype,objectno,goodsname,goodstype,goodsprice,goodscount,goodssum) "+
			"values(:serialno,:objecttype,:objectno,:goodsname,:goodstype,:goodsprice,:goodscount,:goodssum)";
						SQLHandleInfo sSqlHandleGoods = new SQLHandleInfo(sSql)
						.setParameter("serialno", sSerialNo)//��ˮ��
						.setParameter("objecttype", "CreditLoanApply")//������ϵ
						.setParameter("objectno", sObjectNo)//������ˮ��
						.setParameter("goodsname", Tools.getObjectToString(jsonObject.get("GoodsName")))//��Ʒ����
						.setParameter("goodstype", Tools.getObjectToString(jsonObject.get("GoodsType")))//��Ʒ�ͺ�
						.setParameter("goodsprice", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsPrice"))))//��Ʒ�۸�
						.setParameter("goodscount", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsCount"))))//��Ʒ����
						.setParameter("goodssum", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsSum"))));//��Ʒ�ܶ�
						
						logger.info("������ִ�нű�======"+sSqlHandleGoods.getSql());
						this.sqlQuery.execute(sSqlHandleGoods.getSql());
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------ִ�в����������Ϣʱ����-----");
			throw e;
		}
		
	}
	
}
