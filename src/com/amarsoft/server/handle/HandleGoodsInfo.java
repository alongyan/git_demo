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
 * @describe 该方法用来处理商品信息
 * @author jxsun
 *
 */
public class HandleGoodsInfo {
	private Logger logger = Logger.getLogger(HandleGoodsInfo.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//接收报文
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//机构编号
	private String sUserID = "";//用户编号
	private String sObjectNo = "";//关联申请流水号
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
	 * @describe 该方法用于处理业务申请信息模块内容
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
	 * @describe 改方法用于处理客户关联人信息内内容
	 */
	private void insertGoodsInformation(String sJsonStr) throws Exception{
		JSONObject jsonObject = JSONObject.fromObject(sJsonStr);
		System.out.println("===================商品========"+sJsonStr);
		String sSerialNo = "";
		try {
			sSerialNo = DBFunction.getSerialNo("GOODS_RELATIVE", "SerialNo");
			String sSql = " insert into GOODS_RELATIVE (serialno,objecttype,objectno,goodsname,goodstype,goodsprice,goodscount,goodssum) "+
			"values(:serialno,:objecttype,:objectno,:goodsname,:goodstype,:goodsprice,:goodscount,:goodssum)";
						SQLHandleInfo sSqlHandleGoods = new SQLHandleInfo(sSql)
						.setParameter("serialno", sSerialNo)//流水号
						.setParameter("objecttype", "CreditLoanApply")//关联关系
						.setParameter("objectno", sObjectNo)//关联流水号
						.setParameter("goodsname", Tools.getObjectToString(jsonObject.get("GoodsName")))//商品名称
						.setParameter("goodstype", Tools.getObjectToString(jsonObject.get("GoodsType")))//商品型号
						.setParameter("goodsprice", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsPrice"))))//商品价格
						.setParameter("goodscount", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsCount"))))//商品数量
						.setParameter("goodssum", Double.parseDouble(Tools.getObjectToString(jsonObject.get("GoodsSum"))));//商品总额
						
						logger.info("关联人执行脚本======"+sSqlHandleGoods.getSql());
						this.sqlQuery.execute(sSqlHandleGoods.getSql());
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("-------执行插入关联人信息时错误-----");
			throw e;
		}
		
	}
	
}
