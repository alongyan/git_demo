package com.amarsoft.server.action;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import org.apache.log4j.Logger;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;
/**
 * @describe 该类用来上传放款凭证  
 * @author jxsun
 */
public class TranseTransferBusiness extends Action {
	private String sUserID ="";
	private String sUserName ="";
	private String sOrgID ="";
	private String sOrgName ="";
	private String sContentType ="";//文件类型
	private String sDocNo ="";
	private SQLHandleInfo handleInfo  ;
	private ArrayList<String> filePathList =null;//文件路径
	private ArrayList<String> FullPathList =null;//文件全路径
	private Logger logger = Logger.getLogger(TranseTransferBusiness.class);
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
	try{
		String sSerialNo = Tools.getObjectToString(requestMap.get("SerialNo"));
		if(sSerialNo == null) sSerialNo = "";
		Object sFileNameList = requestMap.get("FileNameList");
		if(sFileNameList == null) sFileNameList = "";
		
		//初始化基础信息
		initBaseParam(sSerialNo,sqlQuery);
		//插入文件信息
		insertDocLibrary(sqlQuery);
		//插入关联信息
		insertDocRelative(sDocNo,sSerialNo,sqlQuery);
		//插入附件信息
		inertDocattacment(sFileNameList,sqlQuery);
		String sSql = "";
		
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sSerialNo = Tools.getObjectToString(rs.getString(1));
		}
		rs.getStatement().close();
		
		//根据参数返回用户状态
		responseMap.put("Status", "Success");
		responseMap.put("Message", "上传成功");
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "执行错误");
		}
		return responseMap;
	}
	
	/**
	 * @describe 插入附件信息
	 * @param sFileNameList
	 * @param sqlQuery
	 * @throws Exception 
	 */
	private void inertDocattacment(Object sFileNameList, SQLQuery sqlQuery) throws Exception {
        String sAttNo = ""; //附件编号
        String sFilePath ="";//文件路径
        String sFullPath  ="";//文件上传全路径、
        String sSql ="";
		for(String fileName:(ArrayList<String>)sFileNameList){
	        try {
	        	File  file =  new File(fileName);
				sAttNo = DBFunction.getSerialNo("DOC_ATTACHMENT","ATTACHMENTNO");
				sFilePath = Tools.getToday(2)+"/"+sDocNo+"_"+sAttNo+"_"+fileName;
				sFullPath = "/tmp/als/Upload/"+sFilePath;
				sSql =" insert into Doc_attachment (DOCNO, ATTACHMENTNO, FILENAME,"
				+" CONTENTTYPE, CONTENTLENGTH, BEGINTIME, ENDTIME, INPUTUSER, INPUTORG,"
				+" FILEPATH, FULLPATH, FILESAVEMODE)values (:DOCNO, :ATTACHMENTNO, :FILENAME,"
				+" :CONTENTTYPE, :CONTENTLENGTH, :BEGINTIME, :ENDTIME, :INPUTUSER, :INPUTORG, "
				+" :FILEPATH, :FULLPATH, :FILESAVEMODE)";
				handleInfo =  new SQLHandleInfo(sSql);
				handleInfo.setParameter("DOCNO", sDocNo)
				          .setParameter("ATTACHMENTNO", sAttNo)
				          .setParameter("FILENAME", fileName)
				          .setParameter("CONTENTTYPE", new MimetypesFileTypeMap().getContentType(file))
				          .setParameter("CONTENTLENGTH", "")
				          .setParameter("BEGINTIME", Tools.getToday(2))
				          .setParameter("ENDTIME", Tools.getToday(2))
				          .setParameter("INPUTUSER",sUserID )
				          .setParameter("INPUTORG", sOrgID)
				          .setParameter("FILEPATH", sFilePath)
				          .setParameter("FULLPATH", sFullPath)
				          .setParameter("FILESAVEMODE","Disk")
				          .setParameter("FILENAME", fileName);
				sSql = handleInfo.getSql();
				sqlQuery.execute(sSql);
				filePathList.add(sFilePath);
				FullPathList.add(sFullPath);
	        } catch (Exception e) {
				e.printStackTrace();
				logger.info("插入附件信息出错");
				throw e;
			} 
        }
	}

	/**
	 * @describe 插入关联信息
	 * @param sDocNo2
	 * @param sSerialNo
	 * @throws Exception 
	 */
	private void insertDocRelative(String sDocNo, String sSerialNo,SQLQuery sqlQuery) throws Exception {
		String sSql = "insert into DOC_RELATIVE(DocNo,ObjectType,ObjectNo) values (:DocNo,:ObjectType,:ObjectNo)";
		handleInfo =  new SQLHandleInfo(sSql);
		handleInfo.setParameter("DocNo", sDocNo)
		          .setParameter("ObjectType", "CreditApply")
		          .setParameter("ObjectNo", sSerialNo);
		sSql = handleInfo.getSql();
		sqlQuery.execute(sSql);
		try {
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			logger.info("插入文件关联信息出错");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @describe 插入文件信息
	 * @param sFileName
	 * @param sqlQuery
	 * @throws Exception 
	 */
	private void insertDocLibrary(SQLQuery sqlQuery) throws Exception {
		try {
			//文件编号
			sDocNo =DBFunction.getSerialNo("DOC_LIBRARY", "DOCNO");
			String sSql =" insert  into doc_library(DocNo , DocTitle, DocImportance, DocSource,"
			        +" DocDate, UserID, UserName, OrgID, OrgName, InputTime, DocFlag) values(:DocNo , :DocTitle,"
					+" :DocImportance, :DocSource, :DocDate, :UserID, :UserName, :OrgID, :OrgName, :InputTime, :DocFlag)";
			handleInfo = new SQLHandleInfo(sSql);
		    handleInfo.setParameter("DocNo", sDocNo)
		              .setParameter("DocTitle", "贷款凭证"+sDocNo.substring(sDocNo.length()-3, sDocNo.length()))
		              .setParameter("DocImportance", "01")
		              .setParameter("DocSource", "外围系统")
		              .setParameter("DocDate", Tools.getToday(2))
		              .setParameter("UserID", sUserID)
		              .setParameter("UserName", sUserName)
		              .setParameter("OrgID", sOrgID)
		              .setParameter("OrgName", sOrgName)
		              .setParameter("InputTime",Tools.getToday(2))
		              .setParameter("DocFlag", "1");
			sSql=handleInfo.getSql();
			sqlQuery.execute(sSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("插入文档信息doc_library报错");
			throw e;
		}
	}

	/*
	 * 初始化基础信息
	 */
	private void initBaseParam(String sSerialNo,SQLQuery sqlQuery) throws Exception {
		String sSql ="SELECT InputUserID ,getUserName(InputUserID) AS InputUserName,InputOrgID,getOrgName(InputOrgID) FROM  business_apply  WHERE serialno ='"+sSerialNo+"' ";
	    try {
			ResultSet  rs =  sqlQuery.getResultSet(sSql);
			if(rs.next()){
				sUserID = rs.getString("InputUserID");
				sUserName = rs.getString("InputUserName");
				sOrgID = rs.getString("InputOrgID");
				sOrgName = rs.getString("sOrgName");
			}
			rs.getStatement().close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) {
		String  sPath = "/tmp/als/Upload/2016/03/21/2016032100000001_20160321002_test.txt";
		System.out.println(sPath.split("_")[1]);
		System.out.println(sPath.substring(sPath.length()-2, sPath.length()));
		
		ArrayList<String> list =  new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		Map map1 = new HashMap();
		map1.put("List", list);
		
		Object  ssString = map1.get("List");
		System.out.println("-------"+Tools.getObjectToString(ssString));
		for(String  s:(ArrayList<String>)ssString){
			System.out.println("key====="+s);
		}
		
		String llString ="test.txt";
		System.out.println(llString.substring(0, llString.indexOf(".")));
		
		System.out.println(Tools.getToday(1));
		
		
		File f =   new File("tt.txt");
		System.out.println("Mime Type of " + f.getName() + " is " + new MimetypesFileTypeMap().getContentType(f));
	}
}
