package com.amarsoft.server.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.amarsoft.server.dao.SQLQuery;

/**
 * @author ygwang
 *
 */
public class Tools {
	/**
	 * ������תΪ�ַ��������ѧ�������ĳ���
	 * 
	 * @return String
	 */
	public static String numberFormat(double d,int integerDigits,int fractionDigits) {
		String sTemp="";
		if(fractionDigits>0){
			sTemp+=".0";
			for(int i=0;i<fractionDigits-1;i++){
				sTemp+="0";
			}
		}
		for(int i=0;i<integerDigits;i++){
			sTemp="0"+sTemp;
		}
		DecimalFormat df = new DecimalFormat(sTemp);
		return df.format(d);
	}
	/**
	 * �õ���ǰ��ʱ��*********************************** ����ֵ: HHMMSS
	 * 
	 * @return String
	 */
	public static String getTime(int i) {
		String prev = new java.sql.Time(System.currentTimeMillis()).toString().trim();
		if (i == 1) prev = prev.substring(0, 2) + prev.substring(3, 5) + prev.substring(6, 8);
		if (i == 2) prev = prev.substring(0, 2) + ":" + prev.substring(3, 5) + ":" + prev.substring(6, 8);
		return prev;
	}

	public static String replaceAll(String s1, String s2, String s3) {
		while (s1.indexOf(s2) > 0) {

			s1 = s1.substring(0, s1.indexOf(s2)) + s3 + s1.substring(s1.indexOf(s2) + s2.length());
		}
		return s1;
	}

	public static double getBaseRate(Connection connection, String sBusinessType, String sBusinessSubType, int iLoanTerm, String sEffDate) throws SQLException {
		// �������
		String sSql = "";// Sql���
		String sAdjustDate = "";// ��������
		double dMinLoanTerm = 0.0;// ��С������������
		double dMonthRate = 0.0;// ������
		ResultSet rs = null;

		try {
			// ��ȡ���ĵ�������
			sSql = " select max(AdjustDate) as AdjustDate " + " from LOANRATE_LIST " + " where BusinessType like '%" + sBusinessType + "%' "
					+ " and BusinessSubType = '" + sBusinessSubType + "' " + " and Term >= " + iLoanTerm + " " + " and AdjustDate <= '" + sEffDate + "'";
			rs = connection.createStatement().executeQuery(sSql);
			if (rs.next()) sAdjustDate = rs.getString("AdjustDate");
			rs.getStatement().close();
			if (sAdjustDate == null) sAdjustDate = "";

			// ��ȡ��С������
			sSql = " select min(Term) as Term " + " from LOANRATE_LIST " + " where BusinessType like '%" + sBusinessType + "%' " + " and BusinessSubType = '"
					+ sBusinessSubType + "' " + " and Term >= " + iLoanTerm + " " + " and AdjustDate = '" + sAdjustDate + "' ";
			rs = connection.createStatement().executeQuery(sSql);
			if (rs.next()) dMinLoanTerm = rs.getDouble("Term");
			rs.getStatement().close();

			// ��ȡ��Ч�Ļ�׼����
			sSql = " select nvl(MonthRate,0) as MonthRate " + " from LOANRATE_LIST " + " where BusinessType like '%" + sBusinessType + "%' "
					+ " and BusinessSubType = '" + sBusinessSubType + "' " + " and Term = " + dMinLoanTerm + " " + " and AdjustDate = '" + sAdjustDate + "' ";
			rs = connection.createStatement().executeQuery(sSql);
			if (rs.next()) dMonthRate = rs.getDouble("MonthRate");
			rs.getStatement().close();
		}
		catch (Exception exception) {
			throw new SQLException("��ȡ��׼���ʴ���" + exception.toString());
		}
		return dMonthRate;
	}

	static public String getRelativeMonth(String sDate, int i) throws ParseException {
		if (i == 0) return sDate;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		cal.setTime(formatter.parse(sDate));
		cal.add(Calendar.MONTH, i);
		return formatter.format(cal.getTime());
	}

	/**
	 * �õ���ǰ������*********************************** ����ֵ: type=1--YYYYMMDD type=2--YYYY/MM/DD type=3--YYMMDD
	 * 
	 * @param type
	 *            int
	 * @return String
	 */
	public static String getToday(int type) {
		String prev = new java.sql.Date(System.currentTimeMillis()).toString().trim();
		prev = prev.substring(0, 4) + "/" + prev.substring(5, 7) + "/" + prev.substring(8, 10);
		return getToday(type, prev);
	}

	public static String getToday(int type, String date) {
		if (type == 1) {
			return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		}
		else if (type == 2) {
			return date;
		}
		else if (type == 3) {
			return date.substring(2, 4) + date.substring(5, 7) + date.substring(8, 10);
		}
		else if (type == 4) {
			return date.substring(5, 7) + date.substring(8, 10);
		}
		else {
			return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		}
	}

	public static String getSerialNo(String s, Connection conn) throws Exception {
		int i = 0;
		int maxSerialNo = 0, minSerialNo = 0;
		ResultSet resultSet;
		for (resultSet = conn.createStatement().executeQuery("select SerialNo,MaxSerialNo,MinSerialNo from org_info where OrgID = '" + s + "'"); resultSet
				.next();) {
			i = resultSet.getInt("SerialNo");
			maxSerialNo = resultSet.getInt("MaxSerialNo");
			minSerialNo = resultSet.getInt("MinSerialNo");
		}
		resultSet.getStatement().close();
		int j = i + 1;
		if (maxSerialNo < i || minSerialNo > i) {
			throw new Exception("������ˮ�ų�����Χ����ǰ��ˮ��=" + j);
		}
		conn.createStatement().execute("update org_info set SerialNo = '" + j + "' where OrgID = '" + s + "'");
		//conn.createStatement().execute("update UserTest.org_info set SerialNo = '" + j + "' where OrgID = '" + s + "'");
		return Integer.toString(i);
	}

	// �ַ�������
	public static String StringReverse(String string) {
		byte[] sReturn = new byte[string.length()];
		byte[] bytearray = string.getBytes();

		for (int i = bytearray.length - 1; i >= 0; i--)
			sReturn[bytearray.length - i - 1] = bytearray[i];

		return new String(sReturn);
	}

	// ������ת����ʮ������
	public static String BitToHex(String value) {
		// ���Ϊ4��������
		String STRING_VALUE = value;
		String sReturn = "";
		while (STRING_VALUE.length() % 4 != 0) {
			STRING_VALUE = "0" + STRING_VALUE;
		}
		/*
		 * 4λ4λת��
		 */
		byte[] BYTE_VALUE_ARRAY = STRING_VALUE.getBytes();
		for (int i = 0; i < STRING_VALUE.length(); i = i + 4) {
			String Tmp = new String(BYTE_VALUE_ARRAY, i, 4);
			sReturn += DToHex(BitToD(Tmp));
		}
		return sReturn;
	}

	// ʮ����ת��ʮ������
	public static String DToHex(int value) {
		if (value < 10) return "" + value;
		else {
			String string = "ABCDEF";
			return string.substring(value - 10, value - 9);
		}
	}

	// ������ת��ʮ����
	public static int BitToD(String value) {
		int sReturn = 0;
		for (int i = value.length() - 1; i >= 0; i--) {

			if (value.substring(i, i + 1).equals("1")) sReturn += (int) Math.pow(2, value.length() - i - 1);
		}

		return sReturn;
	}

	/*
	 * ��ú͸�������sDate���Days�������
	 */
	public static String diffDay(String sDate, int Days) throws Exception {
		if (Days == 0) return sDate;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		cal.setTime(formatter.parse(sDate));
		cal.add(Calendar.DATE, Days);
		return formatter.format(cal.getTime());
	}

	public static boolean monthEnd(String date) {
		int i = Integer.parseInt(date.substring(0, 4));
		int j = Integer.parseInt(date.substring(5, 7));
		int k = Integer.parseInt(date.substring(8, 10));
		if (k == 31) return true;
		if (k == 30 && (j == 4 || j == 6 || j == 9 || j == 11)) return true;
		if (k == 29 && j == 2) return true;
		if (k == 28 && j == 2 && i % 4 != 0) return true;
		return false;
	}
	
	/*
     * ���� �������ֻ᷵��2���ո�
     * @param src
     * @return
     */
	public static int getGBKSpaces(String s) {
		int iCount=0;
		boolean isLastChinese=false;
		for(int i=0;i<s.length();i++){
			String s1=s.substring(i,i+1);
			if(s1.length()!=s1.getBytes().length){
				if(!isLastChinese) iCount+=1;
				isLastChinese=true;
			}
			else{
				isLastChinese=false;
			}
		}
		return iCount;
	}
	
	
	/*
     * ���� �������ֻ�����2���ո�
     * @param src
     * @return
     */
	public static String convertStringToGBK(String s) {
		String returnVlaue="";
		boolean isLastChinese=false;
		for(int i=0;i<s.length();i++){
			String s1=s.substring(i,i+1);
			if(s1.length()!=s1.getBytes().length){
				isLastChinese=true;
			}
			else{
				if(isLastChinese) returnVlaue+="  ";
				isLastChinese=false;
			}
			returnVlaue+=s1;
		}
		return returnVlaue;
	}
	
	/*
     * ���� ɾ���������ֺ��2���ո�
     * @param src
     * @return
     */
	public static String convertGBKToString(String s) {
		String returnVlaue="";
		boolean isLastChinese=false;
		for(int i=0;i<s.length();i++){
			String s1=s.substring(i,i+1);
			if(s1.length()!=s1.getBytes().length){
				isLastChinese=true;
			}
			else if(isLastChinese&&s1.equals(" ")){
				continue;
			}
			else{
				isLastChinese=false;
			}
			returnVlaue+=s1;
		}
		return returnVlaue;
	}
	/**
	 * @describe �÷������ڻ�ȡ�ֵ��Ӧӳ�������
	 * @return
	 */
	public static String getCodeItemAttribute(String sCodeNo,String sItemNo,SQLQuery sqlQuery){
		String sAttribute6 = "";//ӳ������չʾ
		String sSql = "select Attribute6 from code_library where codeno = '"+sCodeNo+"' and itemno = '"+sItemNo+"'";
		try {
			sAttribute6 = sqlQuery.getString(sSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlQuery.close();
		return sAttribute6;
	}
	/**
	 * @describe �÷������ݴ����Flag��ͬ���в�ͬ���滻
	 * @param sFlag
	 * @param sDate
	 * @return
	 */
	public static String getStringDate(String sFlag ,String sDate){
		String sReturnDate = "";//���������
		
		if(sDate.length() <=0 || "".equals(sDate) || sDate == null){//�����ݵ����ڸ�ʽΪ���򲻽��д���
			sReturnDate = "";
		}else {//����������ڴ���ֵ
			if("1".equals(sFlag)){//����Ҫyyyy/mm/dd ת��Ϊ yyyy-mm-dd
				sReturnDate = sDate.replace("/", "-");
			}else if("2".equals(sFlag)){//����Ҫ yyyy-mm-dd ת��Ϊ yyyy/mm/dd
				sReturnDate = sDate.replace("-", "/");
			}
		}
		return sReturnDate;
	}
	
	/**
	 * @describe �ķ������ڶ���Χϵͳ��ȡ���ݽ��д���ת��
	 * @param object
	 * @return
	 */
	public static String getObjectToString(Object object){
		String sStrReturn = "";//��������
		if(object == null || "".equals(object) || "null".equals(object)){
			sStrReturn = "";
		}else{
			sStrReturn = object.toString();
		}
		return sStrReturn;
	}
	/**
	 * @describe �ķ������ڻ�ȡ����������Ϣ����
	 * @param sCodeNo
	 * @param sItemName
	 * @param sqlQuery
	 * @return
	 */
	public static String getCodeItemNo(String sCodeNo,String sItemName,SQLQuery sqlQuery){
		String sItemNo = "";
		String sSql = "select itemno from code_library where codeno = '"+sCodeNo+"' and trim(itemname) = '"+sItemName.trim()+"'";
		try {
			sItemNo = sqlQuery.getString(sSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sItemNo;
	}
	/**
	 * @describe �ķ������ڻ�ȡ��������������������Ϣ
	 * @param sOrgID
	 * @param sUserID
	 * @param sBusinessType
	 * @param sqlQuery
	 * @return
	 */
	public static HashMap<String,String> getFlowInfo(String sOrgID,String sUserID,String sBusinessType,SQLQuery sqlQuery){
		HashMap<String,String> hashMap = new HashMap<String,String>();
		String sStatus = "0000";//���ݻ�ȡ״̬
		String sOrgName = "";//��������
		String sUserName = "";//�û�����
		String sFlowNo = "";//���̱��
		String sFlowName = "";//��������
		String sPhaseNo = "";//�׶α��
		String sPhaseName = "";//�׶�����
		try {
			sOrgName = sqlQuery.getString("select trim(orgname) from org_info where orgid = '"+sOrgID+"'");//��������
			sUserName = sqlQuery.getString("select trim(username) from user_info where userid = '"+sUserID+"'");
			sFlowNo = sqlQuery.getString("select trim(attribute9) from business_type   where  typeno = '"+sBusinessType+"'");
			sFlowName = sqlQuery.getString("select flowname from flow_catalog where flowno = '"+sFlowNo+"'");
			sPhaseNo = sqlQuery.getString("select phaseno from flow_model where flowno = '"+sFlowNo+"' and phasetype ='1010'");
			sPhaseName = sqlQuery.getString("select phasename from flow_model where flowno = '"+sFlowNo+"' and phasetype ='1010'");
			
			if(sOrgName == null) sOrgName = "";
			if(sUserName == null) sUserName = "";
			if(sFlowNo == null) sFlowNo = "";
			if(sFlowName == null) sFlowName = "";
			if(sPhaseNo == null) sPhaseNo = "";
			if(sPhaseName == null) sPhaseName = "";
			hashMap.put("Status", sStatus);
			hashMap.put("OrgID", sOrgID);
			hashMap.put("UserID", sUserID);
			hashMap.put("OrgName", sOrgName);
			hashMap.put("UserName", sUserName);
			hashMap.put("FlowNo", sFlowNo);
			hashMap.put("FlowName", sFlowName);
			hashMap.put("PhaseNo", sPhaseNo);
			hashMap.put("PhaseName", sPhaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sStatus = "9999";
			hashMap.put("Status", sStatus);
		}
		return hashMap;
	}
	/**
	 * @describe �÷������ڻ�ȡϵͳ��ǰʱ�䣬ʱ���ʽΪ yyyy-MM-dd HH:mm:ss
	 * @author xlsun
	 * @return
	 * @date 2015-08-15 15:55:00
	 */
	public static String getDate(){
		String sDate = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		sDate = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		return sDate;
	}
	
	/**
	 * @describe �ķ��������жϴ���ֵ�Ƿ�Ϊ��
	 * @param object
	 * @return
	 */

	public static boolean isObjectNull(Object object){
		return null == object;
	}
	/**
	 * @describe �ķ��������ж�Double�Ƿ�ΪNULl
	 * @param object
	 * @return
	 */
	public static boolean isDoubleNull(Object object){
		return "null".equals(object.toString()) || null==object || "".equals(object);
	}
	/**
	 * @describe �ķ��������ڴ�����Ϣ���в����������
	 * @param sKey
	 * @param sKeyMessage
	 * @param sProjectNo
	 * @param sqlQuery
	 */
	public static void insertErrorMessage(String sKey,String sKeyMessage,String sProjectNo,SQLQuery sqlQuery){
		String sSerialNo = "";
		try {
			sSerialNo = DBFunction.getSerialNo("ERROR_RETAILMESSAGE", "SERIALNO");
			System.out.println(sSerialNo);
			String sSql = "insert into Error_RetailMessage (serialno,projectno,occurdate,keyname,keymessage) "+
					" values (:serialno,:projectno,:occurdate,:keyname,:keymessage)";
			SQLHandleInfo handleSql = new SQLHandleInfo(sSql)
			.setParameter("serialno", sSerialNo)
			.setParameter("projectno", sProjectNo)
			.setParameter("occurdate", Tools.getToday(2))
			.setParameter("keyname", sKey)
			.setParameter("keymessage", sKeyMessage);
			sqlQuery.execute(handleSql.getSql());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}