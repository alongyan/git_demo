package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来查询字典数据
 * @author jxsun
 *
 */
public class TranseCodeLibrary extends Action {
	private Logger logger = Logger.getLogger(TranseCodeLibrary.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs,rs1= null;
	try{
		String sCodeName = Tools.getObjectToString(requestMap.get("CodeName"));//获取产品编号
		if(sCodeName == null) sCodeName = "";
		String sCodeNo = "";
		String sItemName = "";//字典数据名称
		String sItemNo = "";//编号
		
		ArrayList<Map> list = new ArrayList<Map>();
		
		String sSql = "select CodeNo from Code_catalog where CodeName = '"+sCodeName+"'";
		System.out.println(sSql);
		
		rs = sqlQuery.getResultSet(sSql);
		while(rs.next()){
			sCodeNo = Tools.getObjectToString(rs.getString(1));
		}
		rs.getStatement().close();
		
		sSql = "select ItemNo,ItemName from Code_library where CodeNo = '"+sCodeNo+"'";
		System.out.println(sSql);
		
		rs1 = sqlQuery.getResultSet(sSql);
		while(rs1.next()){
			Map<String, Object> map = new HashMap<String, Object>();
			sItemNo = Tools.getObjectToString(rs1.getString(1));
			sItemName = Tools.getObjectToString(rs1.getString(2));
			System.out.println(sItemNo+"&&"+sItemName);
			
			map.put("SortNo", sItemNo);
			map.put("TypeName", sItemName);
			list.add(map);
		}
		rs1.getStatement().close();
		
		responseMap.put("Status", "Sucess");
		responseMap.put("CodeNameList", list);
		
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}finally{
		}
		return responseMap;
	}
}
