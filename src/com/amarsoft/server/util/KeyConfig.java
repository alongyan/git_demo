package com.amarsoft.server.util;

import java.sql.ResultSet;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;

public class KeyConfig {
	private static String key;
	private static String ivKey;
	private static String salt;
	
	static{
		try {
//			manager=JBOFactory.getFactory().getManager("jbo.sys.CODE_LIBRARY");
//			query=manager.createQuery("CodeNo=:CodeNo and ItemNo=:ItemNo").setParameter("CodeNo", "SaltNo").setParameter("ItemNo", "cs");
//			object=query.getSingleResult();
//			if(object!=null){
//				key=object.getAttribute("attribute1").getString();
//				ivKey=object.getAttribute("attribute2").getString();
//				salt=object.getAttribute("attribute3").getString();
//			}
			SQLQuery sqlQuery = new SQLQuery();
			String sql = "select attribute1 as key, attribute2 as ivKey, attribute3 as salt from code_library where codeno='SaltNo' and itemno='cs'";
			Map map = sqlQuery.getMap(sql);
			sqlQuery.close();
			key = (String)map.get("key");//KeyConfig.getKey();
			ivKey = (String)map.get("ivKey");//KeyConfig.getIvKey();
			salt = (String)map.get("salt");//KeyConfig.getSalt();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String getKey(){
		if(key==null){
			key="";
		}
		return key;
	}
	public static String getIvKey(){
		if(ivKey==null){
			ivKey="";
		}
		return ivKey;
	}
	public static String getSalt(){
		if(salt==null){
			salt="";
		}
		return salt;
	}
	
	public static void main(String[] args){
		
	}
}
