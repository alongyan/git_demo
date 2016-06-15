package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来查询产品分类
 * @author jxsun
 *
 */
public class TranseBusinessSort extends Action {
	private Logger logger = Logger.getLogger(TranseBusinessSort.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs,rs1,rs2,rs3 = null;
	try{
		String sGoodsDomainName = "";
		String sBelongNo = "";
		String sGoodsTypeNo = "";
		String sGoodsTypeName = "";
		String sTypeNo = Tools.getObjectToString(requestMap.get("TypeNo"));//
		if(sTypeNo==null)sTypeNo = "";
		ArrayList<Map> list = new ArrayList<Map>();
		
		String sSql = "select GoodsDomainName from BUSINESS_TYPE where TypeNo = '"+sTypeNo+"'";
		
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()){
			sGoodsDomainName =Tools.getObjectToString(rs.getString(1));
		}
		rs.getStatement().close();
		
		sSql = "select GoodsTypeNo from GOODSTYPE_INFO where GoodsTypeName = '"+sGoodsDomainName+"'";
		
		rs1 = sqlQuery.getResultSet(sSql);
		while(rs1.next()){
			sBelongNo = Tools.getObjectToString(rs1.getString(1));
			sSql = "select GoodsTypeNo from GOODSTYPE_INFO where BelongNo = '"+sBelongNo+"'";
			
			rs2 = sqlQuery.getResultSet(sSql);
			while(rs2.next()){
				sBelongNo = Tools.getObjectToString(rs2.getString(1));
				sSql = "select GoodsTypeNo,GoodsTypeName from GOODSTYPE_INFO where BelongNo = '"+sBelongNo+"'";
				
				rs3 = sqlQuery.getResultSet(sSql);
				while(rs3.next()){
					HashMap<String, Object> map = new HashMap<String, Object>();
					sGoodsTypeNo = Tools.getObjectToString(rs3.getString(1));
					sGoodsTypeName = Tools.getObjectToString(rs3.getString(2));
					map.put(sGoodsTypeNo, sGoodsTypeName);
					list.add(map);
				}
				rs3.getStatement().close();
			}
			rs2.getStatement().close();
		}
		rs1.getStatement().close();
		
		
		//根据参数返回用户状态
		responseMap.put("Status", "Success");
		responseMap.put("GoodsTypeList", list);
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}finally{
		}
		return responseMap;
	}
}
