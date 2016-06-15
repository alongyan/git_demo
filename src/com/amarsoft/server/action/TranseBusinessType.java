package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @describe 该类用来查询产品列表
 * @author jxsun
 * 
 */
public class TranseBusinessType extends Action {
	private Logger logger = Logger.getLogger(TranseBusinessType.class);

	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
		try {
			String sTypeName = "";// 产品名称
            ArrayList<String> typeList =  new ArrayList<String>();
			String sSql = "select TypeName from Business_type  where isinuse ='1' ";
			rs = sqlQuery.getResultSet(sSql);

			while (rs.next()) {
				sTypeName = Tools.getObjectToString(rs.getString(1));
				typeList.add(sTypeName);

			}
			rs.getStatement().close();

			// 根据参数返回用户状态
			responseMap.put("Status", "Success");
			responseMap.put("Param", typeList);
		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
			throw  e;
		}
		return responseMap;
	}
}
