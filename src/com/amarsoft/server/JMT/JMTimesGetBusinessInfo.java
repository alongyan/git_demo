package com.amarsoft.server.JMT;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.Tools;

/**
 * @describe 该方法用于对获取的数据进行拆分并组装Sql语句
 * @author xlsun
 * @date 2015-07-30
 *
 */
public class JMTimesGetBusinessInfo {
	/**
	 * @describe 该方法用于拆分获取的数据并组装Sql语句
	 * @param requestMap
	 * @param sqlQuery
	 * @param sJMID
	 */
	private static final Logger logger = Logger.getLogger(JMTimesTranseJMID.class);
	public void updateBusinessInfo(JSONObject requestMap,SQLQuery sqlQuery,String sJMID) throws Exception{
		 
		String sCardNo = (String)requestMap.get("CardNo");//开户银行卡号
		String sBankName = (String)requestMap.get("BankName");//客户银行名称
		String sBankArea = (String)requestMap.get("BankArea");//开户银行所在地区
		String sBankPoint = (String)requestMap.get("BankPoint");//开户银行支行名称
		String sSignDate = getStringDate("2",(String)requestMap.get("SignDate"));//签约日期
		String sArtificialNo = (String)requestMap.get("ArtificialNo");//借款协议编号
		double dBusinessSum = Double.parseDouble((String)requestMap.get("BusinessSum"));//融资金额
		String sFirstPaymentDate = Tools.getStringDate("2", (String)requestMap.get("FirstPaymentDate")) ;//首次还款日
		String sContractDate = Tools.getStringDate("2", (String)requestMap.get("ContractDate"));//合同还款日
		double dTermSum = Double.parseDouble((String)requestMap.get("TermSum"));//融资人实收金额
		double dMonthpayment = Double.parseDouble((String)requestMap.get("Monthpayment"));//每月还款额
		double dBackablity = Double.parseDouble((String)requestMap.get("Backablity"));//还款能力
		String sProvice = (sBankArea==null|| sBankArea.length() <=0 )?"": getProvice(sBankArea,sqlQuery);//省份
		sBankArea = getArea(sBankArea,sqlQuery);//获取市区编码
		//拼接更新合同表语句
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
		
		logger.info("获取积木时代业务合同数据 更新语句为 "+sSql);
		
		sqlQuery.executeUpdate(sSql);
	}
	/**
	 * @describe 该方法用于根据获取的首次还款日截取日期
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
	 * @describe 该方法用于获取
	 * @param sArea
	 * @param sqlQuery
	 * @return
	 * @throws Exception 
	 */
	private String getArea(String sArea,SQLQuery sqlQuery) throws Exception{
		String sCreditArea = "";//地址国标信贷码
		String sSql = "select cl.itemno as ItemNo from code_library cl where cl.codeno= 'AreaCode' and cl.sortno = '"+sArea+"'";
		sCreditArea = sqlQuery.getString(sSql);
		
		return sCreditArea;
	}
	
	
	/**
	 * @describe 该方法用于获取开户银行所在省份
	 * @param sArea
	 * @param sqlQuery
	 * @return
	 * @throws Exception 
	 */
	private String getProvice(String sArea,SQLQuery sqlQuery) throws Exception{
		String sProvice = "";
			if(sArea.length() == 4 || sArea.length() == 6){//该地址内容为区域地址
				String sSql = "select cl.itemno as ItemNo from code_library cl where cl.codeno= 'AreaCode' and cl.sortno = '"+sArea.substring(0, 2)+"'";
				sProvice = sqlQuery.getString(sSql);
			}else{
				sProvice = "";
			}
			if(sProvice == null) sProvice = "";
		
		return sProvice;
	}
	
	/**
	 * @describe 该方法根据传入的Flag不同进行不同的替换
	 * @param sFlag
	 * @param sDate
	 * @return
	 */
	private  String getStringDate(String sFlag ,String sDate){
		String sReturnDate = "";//处理后日期
		if(sDate.length() <=0 || "".equals(sDate) || sDate == null){//若传递的日期格式为空则不进行处理
			sReturnDate = "";
		}else {//若传入的日期存在值
			if("1".equals(sFlag)){//若需要yyyy/mm/dd 转换为 yyyy-mm-dd
				sReturnDate = sDate.replace("/", "-");
			}else if("2".equals(sFlag)){//若需要 yyyy-mm-dd 转换为 yyyy/mm/dd
				sReturnDate = sDate.replace("-", "/");
			}
		}
		return sReturnDate;
	}
}
