package com.amarsoft.server.check;

import java.util.Iterator;
import java.util.Map;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.EncryptUtil;
import com.amarsoft.server.util.MD5;
import com.amarsoft.server.util.StrUtil;
import com.amarsoft.server.util.Tools;

public class CheckTypeNoExist extends ErrorCheck {

	public boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery) {
		try {
			String sProjectNo = Tools.getObjectToString(requestMap.get("SPID"));
			String sTypeNo = Tools.getObjectToString(requestMap.get("TypeNo"));
			String count = sqlQuery.getString("select * from Business_Type where TypeNo = '"+sTypeNo+"'");
			if(count!=null){
				String count1 = sqlQuery.getString("SELECT * FROM GOODSTYPE_INFO WHERE belongNo IN (SELECT GoodsTypeNo FROM GOODSTYPE_INFO  WHERE BelongNo IN (SELECT GoodsTypeNo FROM GOODSTYPE_INFO WHERE GoodsTypeName = (SELECT GoodsDomainName FROM business_type WHERE typeno = '"+sTypeNo+"')))");
				if(count1!=null){
					return true;
				}else{
					Tools.insertErrorMessage("Error", "无对应产品信息！",sProjectNo , sqlQuery);
					return false;
				}
			}else{
				Tools.insertErrorMessage("Error", "产品编号不正确！",sProjectNo , sqlQuery);
				return false;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog(e);
			return false;
		}
	}
	
}
