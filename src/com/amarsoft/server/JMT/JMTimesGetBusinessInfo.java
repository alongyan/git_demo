package com.amarsoft.server.JMT;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷������ڶԻ�ȡ�����ݽ��в�ֲ���װSql���
 * @author xlsun
 * @date 2015-07-30
 *
 */
public class JMTimesGetBusinessInfo {
	/**
	 * @describe �÷������ڲ�ֻ�ȡ�����ݲ���װSql���
	 * @param requestMap
	 * @param sqlQuery
	 * @param sJMID
	 */
	private static final Logger logger = Logger.getLogger(JMTimesTranseJMID.class);
	public void updateBusinessInfo(JSONObject requestMap,SQLQuery sqlQuery,String sJMID) throws Exception{
		 
		String sCardNo = (String)requestMap.get("CardNo");//�������п���
		String sBankName = (String)requestMap.get("BankName");//�ͻ���������
		String sBankArea = (String)requestMap.get("BankArea");//�����������ڵ���
		String sBankPoint = (String)requestMap.get("BankPoint");//��������֧������
		String sSignDate = getStringDate("2",(String)requestMap.get("SignDate"));//ǩԼ����
		String sArtificialNo = (String)requestMap.get("ArtificialNo");//���Э����
		double dBusinessSum = Double.parseDouble((String)requestMap.get("BusinessSum"));//���ʽ��
		String sFirstPaymentDate = Tools.getStringDate("2", (String)requestMap.get("FirstPaymentDate")) ;//�״λ�����
		String sContractDate = Tools.getStringDate("2", (String)requestMap.get("ContractDate"));//��ͬ������
		double dTermSum = Double.parseDouble((String)requestMap.get("TermSum"));//������ʵ�ս��
		double dMonthpayment = Double.parseDouble((String)requestMap.get("Monthpayment"));//ÿ�»����
		double dBackablity = Double.parseDouble((String)requestMap.get("Backablity"));//��������
		String sProvice = (sBankArea==null|| sBankArea.length() <=0 )?"": getProvice(sBankArea,sqlQuery);//ʡ��
		sBankArea = getArea(sBankArea,sqlQuery);//��ȡ��������
		//ƴ�Ӹ��º�ͬ�����
		String sSql = "update business_contract set cardno = '"+sCardNo+"'"+
						",bankname = '"+sBankName+"'"+
						",BankProvice = '"+sProvice+"'"+
						",BankArea = '"+sBankArea+"'"+
						",BankPoint = '"+sBankPoint+"'"+
						",BusinessSum = "+dBusinessSum+
						",FirstPaymentDate = '"+sFirstPaymentDate+"'"+
						",monthRepayment = '"+getmonthRepayment(sFirstPaymentDate)+"'"+
						",dueDate = '"+sContractDate+"'"+
						",TermSum = "+dTermSum+
						",SignDate ='"+sSignDate+"' "+
						",ArtificialNo ='"+sArtificialNo+"'"+
						",Monthpayment = "+dMonthpayment+
						",Backablity ="+dBackablity+
						",tempsaveflag = '2' "+
						" where jimuid = '"+sJMID+"'";
		
		logger.info("��ȡ��ľʱ��ҵ���ͬ���� �������Ϊ "+sSql);
		
		sqlQuery.executeUpdate(sSql);
	}
	/**
	 * @describe �÷������ڸ��ݻ�ȡ���״λ����ս�ȡ����
	 * @param sFirstPaymentDate
	 * @return
	 */
	private String getmonthRepayment(String sFirstPaymentDate){
		String smonthRepayment = "";
		if("".equals(sFirstPaymentDate)){
			smonthRepayment = "1";
		}else {
			smonthRepayment =sFirstPaymentDate.substring(8, 10); 
			if(smonthRepayment.startsWith("0")) smonthRepayment = smonthRepayment.substring(1,2);
		}
		
		return smonthRepayment;
	}
	/**
	 * @describe �÷������ڻ�ȡ
	 * @param sArea
	 * @param sqlQuery
	 * @return
	 * @throws Exception 
	 */
	private String getArea(String sArea,SQLQuery sqlQuery) throws Exception{
		String sCreditArea = "";//��ַ�����Ŵ���
		String sSql = "select cl.itemno as ItemNo from code_library cl where cl.codeno= 'AreaCode' and cl.sortno = '"+sArea+"'";
		sCreditArea = sqlQuery.getString(sSql);
		
		return sCreditArea;
	}
	
	
	/**
	 * @describe �÷������ڻ�ȡ������������ʡ��
	 * @param sArea
	 * @param sqlQuery
	 * @return
	 * @throws Exception 
	 */
	private String getProvice(String sArea,SQLQuery sqlQuery) throws Exception{
		String sProvice = "";
			if(sArea.length() == 4 || sArea.length() == 6){//�õ�ַ����Ϊ�����ַ
				String sSql = "select cl.itemno as ItemNo from code_library cl where cl.codeno= 'AreaCode' and cl.sortno = '"+sArea.substring(0, 2)+"'";
				sProvice = sqlQuery.getString(sSql);
			}else{
				sProvice = "";
			}
			if(sProvice == null) sProvice = "";
		
		return sProvice;
	}
	
	/**
	 * @describe �÷������ݴ����Flag��ͬ���в�ͬ���滻
	 * @param sFlag
	 * @param sDate
	 * @return
	 */
	private  String getStringDate(String sFlag ,String sDate){
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
}
