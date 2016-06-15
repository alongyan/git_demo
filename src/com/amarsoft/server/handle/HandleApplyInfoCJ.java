package com.amarsoft.server.handle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amarsoft.are.security.DESEncrypt;
import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBFunction;
import com.amarsoft.server.util.SQLHandleInfo;
import com.amarsoft.server.util.Tools;

/**
 * @describe �÷������ڴ���ҵ�����������Ϣ����
 * @author jxsun
 *
 */
public class HandleApplyInfoCJ {
	private Logger logger = Logger.getLogger(HandleApplyInfoCJ.class);
	private HashMap<String,Object> hashMap = new HashMap<String,Object>();//���ձ���
	private SQLQuery sqlQuery = null;
	private String sOrgID = "";//�������
	private String sUserID = "";//�û����
	private String sBusinessTypeName ="";//��Ʒ����
	private Double sGoodsprice = 0.0;//��Ʒ���
	private Double sFirstPaySum = 0.0;//�׸����
	private Double sFirstPayPercent = 0.0;//�׸�����
	private Double sBusinessSum = 0.0;//�����
	private Double sCreditNum = 0.0;//��������
	private String sCustomerID = "";//�ͻ����
	private String sCustomerName = "";//�ͻ�����
	private Double sRepaySumPermonth = 0.0;//ÿ�»����
	private String sRepayDatePermonth = "";//ÿ�»�����
	private String sIsInsure = "";//�Ƿ�Ͷ��
	private String sGoodsCount = "";//��Ʒ����
	private String sRepayMode = "";//���ʽ
	private String sLoanAccountNo = "";//����/�ſ��˺�
	private String sShopNumber = "";//�ŵ���
	private String sSaleShop = "";//�����ŵ�
	private String sSalePerson = "";//���۴���
	private String sSaleManager = "";//���۾���
	private String sSalesMan = "";//�����̴���Ա
	private String sSalesManPhoneno = "";//��ϵ�绰
	private String sInputDate = "";//¼������
	private String sUpdateDate = "";//��������
	private String sInputUserID = "";//�Ǽ���
	private String sInputOrgID = "";//�Ǽǻ���
	private String sRate = "";//����
	private String sARate = "";//A����
	private String sBRate = "";//B����
	private String sCRate = "";//C����
	private String sInsurance = "";//���շ�
	private String sDRate = "";//�·���׼������
	
	/**
	 * @param sJonsObject
	 * @param sqlQuery
	 */
	public HandleApplyInfoCJ(Map<String, Object> sJonsObject,SQLQuery sqlQuery,String sOrgID,String sUserID,String sCustomerID){
		this.hashMap =  (HashMap<String, Object>) sJonsObject;
		this.sqlQuery=sqlQuery;
		this.sUserID = sUserID;
		this.sOrgID = sOrgID;
		this.sCustomerID = sCustomerID;
	}
	/**
	 * @throws Exception 
	 * @describe �÷������ڴ���ҵ��������Ϣģ������
	 */
	public String handleApplyInfoMessage() throws Exception{
		String sSerialNo = "";
		getApplyInfo();//��ȡhashMap�а���������Ϣ������ֵ
		sSerialNo = insertApplyInfo();//����ҵ��������Ϣ
		return sSerialNo ;
	}
	/**
	 * @describe ��hashMap�л�ȡ��ϢҪ��
	 * @throws Exception
	 */
	private void getApplyInfo() throws Exception {
		try {
			sBusinessTypeName = (String) hashMap.get("BusinessTypeName");
			sGoodsprice =  Double.parseDouble(hashMap.get("GoodsPrice").toString());
			sFirstPaySum = Double.parseDouble(hashMap.get("FirstPaySum").toString());
			sFirstPayPercent = Double.parseDouble(hashMap.get("FirstPayPercent").toString())/100;
			sBusinessSum = Double.parseDouble(hashMap.get("BusinessSum").toString());
			sCreditNum = Double.parseDouble(hashMap.get("CreditNum").toString());
			sCustomerName = (String) hashMap.get("CustomerName");
			sRepaySumPermonth = Double.parseDouble(hashMap.get("RepaySumPermonth").toString());
			sRepayDatePermonth = (String) hashMap.get("RepayDatePermonth");
			sIsInsure = (String) hashMap.get("IsInsure");
			sGoodsCount = (String) hashMap.get("GoodsCount");
			sRepayMode = (String) hashMap.get("RepayMode");
			sLoanAccountNo = (String) hashMap.get("LoanAccountNo");
			sShopNumber = (String) hashMap.get("ShopNumber");
			sSaleShop = (String) hashMap.get("SaleShop");
			sSalePerson = (String) hashMap.get("SalePerson");
			sSaleManager = (String) hashMap.get("SaleManager");
			sSalesMan = (String) hashMap.get("SalesMan");
			sSalesManPhoneno = (String) hashMap.get("SalesManPhoneno");
			sInputDate = (String) hashMap.get("InputDate");
			sUpdateDate = (String) hashMap.get("UpdateDate");
			sInputUserID = (String) hashMap.get("InputUserID");
			sInputOrgID = (String) hashMap.get("InputOrgID");
			sRate = (String) hashMap.get("Rate");
			sARate = (String) hashMap.get("ARate");
			sBRate = (String) hashMap.get("BRate");
			sCRate = (String) hashMap.get("CRate");
			sInsurance = (String) hashMap.get("Insurance");
			sDRate = (String) hashMap.get("DRate");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("��ȡ�����쳣");
			throw e;
		}
	}
	/**
	 * @describe �÷���������ҵ��������в���ҵ������
	 * @return SerialNo
	 * @throws Exception 
	 */
	private String insertApplyInfo() throws Exception{
		String sSerialNo = "";//ҵ��������
		try {
			sSerialNo = "BA"+DBFunction.getSerialNo("BUSINESS_APPLY", "SERIALNO");//��ȡ����׶�ҵ��������
			String sLoanID = "L"+DBFunction.getSerialNo("BUSINESS_APPLY", "LOANID");//��ȡ����׶δ�����
			String sSql = "insert into Business_apply (SerialNo,BusinessTypeName,Goodsprice,FirstPaySum,FirstPayPercent,BusinessSum,CreditNum,CustomerID ,CustomerName,RepaySumPermonth,RepayDatePermonth,IsInsure,GoodsCount,RepayMode,LoanAccountNo,ShopNumber,SaleShop,SalePerson,SaleManager,SalesMan,SalesManPhoneno,InputDate,UpdateDate,InputUserID,InputOrgID,Rate,ARate,BRate,CRate,Insurance,DRate,LoanID)"+
			" values(:SerialNo,:BusinessTypeName,:Goodsprice,:FirstPaySum,:FirstPayPercent,:BusinessSum,:CreditNum,:CustomerID ,:CustomerName,:RepaySumPermonth,:RepayDatePermonth,:IsInsure,:GoodsCount,:RepayMode,:LoanAccountNo,:ShopNumber,:SaleShop,:SalePerson,:SaleManager,:SalesMan,:SalesManPhoneno,:InputDate,:UpdateDate,:InputUserID,:InputOrgID,:Rate,:ARate,:BRate,:CRate,:Insurance,:DRate,:LoanID)";
			
			SQLHandleInfo sSqlHandle = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sSerialNo)//����ҵ����
			.setParameter("BusinessTypeName", sBusinessTypeName).setParameter("Goodsprice",sGoodsprice)
			.setParameter("FirstPaySum",sFirstPaySum)
			.setParameter("FirstPayPercent",sFirstPayPercent)
			.setParameter("BusinessSum",sBusinessSum)
			.setParameter("CreditNum",sCreditNum)
			.setParameter("CustomerID",sCustomerID).setParameter("CustomerName", sCustomerName)
			.setParameter("RepaySumPermonth",sRepaySumPermonth)
			.setParameter("RepayDatePermonth", sRepayDatePermonth)
			.setParameter("IsInsure", Tools.getCodeItemNo("YesNo", Tools.getObjectToString(hashMap.get("IsInsure")), sqlQuery))
			.setParameter("GoodsCount", sGoodsCount)
			.setParameter("RepayMode", Tools.getCodeItemNo("RepayMode", Tools.getObjectToString(hashMap.get("RepayMode")), sqlQuery))
			.setParameter("LoanAccountNo", DESEncrypt.encrypt(sLoanAccountNo, "JiMuHezi"))
			.setParameter("ShopNumber", sShopNumber).setParameter("SaleShop", sSaleShop)
			.setParameter("SalePerson", sSalePerson).setParameter("SaleManager", sSaleManager)
			.setParameter("SalesMan", sSalesMan).setParameter("SalesManPhoneno", sSalesManPhoneno)
			.setParameter("InputDate", sInputDate).setParameter("UpdateDate", sUpdateDate)
			.setParameter("InputUserID", sInputUserID).setParameter("InputOrgID", sInputOrgID)
			.setParameter("Rate", sRate).setParameter("ARate", sARate)
			.setParameter("BRate", sBRate).setParameter("CRate", sCRate)
			.setParameter("Insurance", sInsurance).setParameter("DRate", sDRate).setParameter("LoanID", sLoanID);
			logger.info("��ȡҵ����Ϣִ�нű�"+sSqlHandle.getSql());
			sqlQuery.execute(sSqlHandle.getSql());//ִ�нű�
			
			sSql = "insert into flow_object (ObjectType,ObjectNo,PhaseType,ApplyType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"OrgID,UserID,InputDate) values (:ObjectType,:ObjectNo,:PhaseType,:ApplyType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,"+
			":OrgID,:UserID,:InputDate)";
			
			SQLHandleInfo sSqlHandleFlow = new SQLHandleInfo(sSql)
			.setParameter("ObjectType", "CreditLoanApply")//����
			.setParameter("ObjectNo", sSerialNo)//ҵ��������
			.setParameter("PhaseType", "1010")//���̽׶�Ĭ��Ϊ��һ��λ
			.setParameter("ApplyType", "CreditLoanApply")//����ҵ����������Ĭ��ֵ
			.setParameter("FlowNo", "CreditLoanFlow")//�������̱��
			.setParameter("FlowName", "������������")//��������
			.setParameter("PhaseNo", "0010")//�׶α��
			.setParameter("PhaseName", "������׶�")//�׶�����
			.setParameter("OrgID", this.sOrgID)//������
			.setParameter("UserID", this.sUserID)//�û����
			.setParameter("InputDate", Tools.getToday(2));//��ȡ��ǰ����
			
			logger.info("ҵ�����̽ű���� "+sSqlHandleFlow.getSql());
			this.sqlQuery.execute(sSqlHandleFlow.getSql());
			
			String sTaskSerialNo = DBFunction.getSerialNo("Flow_task", "SerialNo");//��ȡ���̱��
			
			sSql = "insert into flow_task (SerialNo,ObjectNo,ObjectType,FlowNo,FlowName,PhaseNo,PhaseName,"+
			"PhaseType,ApplyType,UserID,OrgID,BeginTime) values (:SerialNo,:ObjectNo,"+
			":ObjectType,:FlowNo,:FlowName,:PhaseNo,:PhaseName,:PhaseType,:ApplyType,:UserID,"+
			":OrgID,:BeginTime)";
			
			SQLHandleInfo sSqlHandleFlowTask = new SQLHandleInfo(sSql)
			.setParameter("SerialNo", sTaskSerialNo)//��������TaskNo
			.setParameter("ObjectNo", sSerialNo)//ҵ��������
			.setParameter("ObjectType", "CreditLoanApply")//ҵ������
			.setParameter("FlowNo", "CreditLoanFlow")//�������̱��
			.setParameter("FlowName", "������������")//������������
			.setParameter("PhaseNo", "0010")//���̽ڵ���
			.setParameter("PhaseName", "������׶�")//���̽ڵ�����
			.setParameter("PhaseType", "1010")//����Ĭ��Ϊ��һ��λ
			.setParameter("ApplyType", "CreditLoanApply")//ҵ������
			.setParameter("UserID", this.sUserID)
			.setParameter("OrgID", this.sOrgID)//�������
			.setParameter("BeginTime", Tools.getDate());
			
			logger.info("ҵ�����̽ű���� "+sSqlHandleFlowTask.getSql());
			this.sqlQuery.execute(sSqlHandleFlowTask.getSql());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("��ȡҵ�������ų��ִ���");
			e.printStackTrace();
			sSerialNo = "Fail";
			throw e;
		}
		return sSerialNo;
	}
}
