package com.amarsoft.server.check;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import com.amarsoft.server.config.FiledConfig;
import com.amarsoft.server.config.ServerActionConfig;
import com.amarsoft.server.config.ServerTranConfig;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

/**
 * @author ywhang
 * @describe 该类用来处理报文中的下拉框是否符合规范
 * @return boolean
 */

public class ErrorCheckSRSelectValue extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String actionId = (String) requestMap.get(ServerTranConfig.getInstance().getTranIDLabel());
			String tranId = ServerActionConfig.getInstance().getActionConfig(actionId).getRequest();
			String sProjectNo = (Tools.getObjectToString(requestMap.get("OrgID"))+Tools.getObjectToString(requestMap.get("ProjectNo")));
			Map<String, FiledConfig> requestFiledMap = ServerTranConfig.getInstance().getTranConfig(tranId).getFiledMap();
			String sCodeNo ="";
			String sItemNo ="";
			for(Iterator<String> it = requestFiledMap.keySet().iterator(); it.hasNext(); ){
				String key = it.next();
				System.out.println(key);
				FiledConfig fc = requestFiledMap.get(key);
				if("Select".equals(fc.getStyle())){
					if(!StrUtil.isNull((String)requestMap.get(key))){
						sCodeNo =fc.getCodeNo();
						System.out.println("sCodeNo===="+sCodeNo);
						sItemNo = getItemNo(sCodeNo,(String)requestMap.get(key),"ItemName",sqlQuery);
						System.out.println("sItemNo====="+sItemNo);
						if(sItemNo ==""){
							Tools.insertErrorMessage(key, key+"下拉字典不符合规范", sProjectNo, sqlQuery);
							return false;
						}
					}
				}else if("SelectItemNo".equals(fc.getStyle())){
					if(!StrUtil.isNull((String)requestMap.get(key))){
						sCodeNo =fc.getCodeNo();
						System.out.println("sCodeNo===="+sCodeNo);
						sItemNo = getItemNo(sCodeNo,(String)requestMap.get(key),"ItemNo",sqlQuery);
						System.out.println("sItemNo====="+sItemNo);
						if(sItemNo ==""){
							Tools.insertErrorMessage(key, key+"下拉字典不符合规范", sProjectNo, sqlQuery);
							return false;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}

	private String getItemNo(String codeNo, String codeValue, String sFlag,SQLQuery sqlQuery) throws Exception {
		String sItemNo="";
		String sSql="";
		if(sFlag=="ItemName"){
			sSql ="select itemNo  from Code_library  where codeno='"+codeNo+"' and itemName ='"+codeValue+"' ";
		}else if(sFlag=="ItemNo"){
			sSql ="select itemNo  from Code_library  where codeno='"+codeNo+"' and itemNo ='"+codeValue+"' ";
		}
		try {
			ResultSet rs = sqlQuery.getResultSet(sSql);
			if (rs.next()) {
				sItemNo = StrUtil.notNull(rs.getString("itemNo"));
			}
			rs.getStatement().close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e ;
		}
		return sItemNo;
	}
	
}
