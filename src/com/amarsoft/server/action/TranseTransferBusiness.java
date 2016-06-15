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
 * @describe ���������ϴ��ſ�ƾ֤  
 * @author jxsun
 */
public class TranseTransferBusiness extends Action {
	private String sUserID ="";
	private String sUserName ="";
	private String sOrgID ="";
	private String sOrgName ="";
	private String sContentType ="";//�ļ�����
	private String sDocNo ="";
	private SQLHandleInfo handleInfo  ;
	private ArrayList<String> filePathList =null;//�ļ�·��
	private ArrayList<String> FullPathList =null;//�ļ�ȫ·��
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
		
		//��ʼ��������Ϣ
		initBaseParam(sSerialNo,sqlQuery);
		//�����ļ���Ϣ
		insertDocLibrary(sqlQuery);
		//���������Ϣ
		insertDocRelative(sDocNo,sSerialNo,sqlQuery);
		//���븽����Ϣ
		inertDocattacment(sFileNameList,sqlQuery);
		String sSql = "";
		
		rs = sqlQuery.getResultSet(sSql);
		if (rs.next()) {
			sSerialNo = Tools.getObjectToString(rs.getString(1));
		}
		rs.getStatement().close();
		
		//���ݲ��������û�״̬
		responseMap.put("Status", "Success");
		responseMap.put("Message", "�ϴ��ɹ�");
		}catch(Exception e){
			responseMap.put("Status", "Fail");
			responseMap.put("Error", "ִ�д���");
		}
		return responseMap;
	}
	
	/**
	 * @describe ���븽����Ϣ
	 * @param sFileNameList
	 * @param sqlQuery
	 * @throws Exception 
	 */
	private void inertDocattacment(Object sFileNameList, SQLQuery sqlQuery) throws Exception {
        String sAttNo = ""; //�������
        String sFilePath ="";//�ļ�·��
        String sFullPath  ="";//�ļ��ϴ�ȫ·����
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
				logger.info("���븽����Ϣ����");
				throw e;
			} 
        }
	}

	/**
	 * @describe ���������Ϣ
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
			logger.info("�����ļ�������Ϣ����");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @describe �����ļ���Ϣ
	 * @param sFileName
	 * @param sqlQuery
	 * @throws Exception 
	 */
	private void insertDocLibrary(SQLQuery sqlQuery) throws Exception {
		try {
			//�ļ����
			sDocNo =DBFunction.getSerialNo("DOC_LIBRARY", "DOCNO");
			String sSql =" insert  into doc_library(DocNo , DocTitle, DocImportance, DocSource,"
			        +" DocDate, UserID, UserName, OrgID, OrgName, InputTime, DocFlag) values(:DocNo , :DocTitle,"
					+" :DocImportance, :DocSource, :DocDate, :UserID, :UserName, :OrgID, :OrgName, :InputTime, :DocFlag)";
			handleInfo = new SQLHandleInfo(sSql);
		    handleInfo.setParameter("DocNo", sDocNo)
		              .setParameter("DocTitle", "����ƾ֤"+sDocNo.substring(sDocNo.length()-3, sDocNo.length()))
		              .setParameter("DocImportance", "01")
		              .setParameter("DocSource", "��Χϵͳ")
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
			logger.info("�����ĵ���Ϣdoc_library����");
			throw e;
		}
	}

	/*
	 * ��ʼ��������Ϣ
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
