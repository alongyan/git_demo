package com.amarsoft.server.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amarsoft.server.dao.SQLQuery;
import com.amarsoft.server.util.DBCPManager;
import com.amarsoft.server.util.DataCryptUtils;
import com.amarsoft.server.util.StrUtil;


/**
 * @describe �÷������ڻ�ȡΪINϵͳ׼����������
 * @author amar
 * history�� �����ֶμ�ע��. by cfxu.2015.07.23.
 * @history:������ȡAgent��Ӧ���Ŵ���Ʒ���� 
 *
 */
public class INXiaoEXinYongDaiRongZiSP extends Action {
	//http://url/api/fqdk/GetProjects?SPID=12001&=2014-01-01&EndTime=2014-02-05&Token=
	@Override
	protected Map<String, Object> action(Map<String, Object> requestMap,
			Map<String, Object> errorMap, SQLQuery sqlQuery) throws Exception {
		ResultSet rs = null;
		try {
			String sStartTime = (String) requestMap.get("StartTime");
			String sEndTime = (String) requestMap.get("EndTime");
			String sAgentOrg = (String) requestMap.get("AgentOrg");//TJJ,JMSD,DIANXIAO,R360
			sStartTime = sStartTime.replaceAll("-", "/");
			sEndTime = sEndTime.replaceAll("-", "/");
			String sStatus = "11";
			String sBusinessType = getBusinessType(sAgentOrg);
			
			String sql = "select " +
					"bc.CustomerName," +//����������
					"bc.oldlcno,"+//��Χϵͳ��Ŀ���
					"bc.SerialNo," +//��ͬ���
					"ii.certid as CertID," +//�������֤����
					"bc.JimuID," +//��ľ���
					"'����' as VouchCompany," +//��Ŀ����
					"bc.customername as ProjectName," +//��Ŀ����
					"bc.ItemStatus," +//״̬
					"bc.ArtificialNo as ArtificialNo,"+//���Э����
					"bc.BusinessSum," +//���ʽ��
					"bc.termmonth as TermMonth," +//����
					"bc.FirstPaymentDate as FirstPaymentDate," +//�״λ�����
					"bc.dueDate as ContractDate," +//��ͬ������
					"bc.monthRepayment as MonthPayment," +//ÿ�»�����
					"nvl(bc.TermSum,0) as TermSum,"+//������ʵ�ս��
					"nvl(bc.monthpayment,0) as MONTHPAYMENTSUM,"+//ÿ�»����
					"bc.Customername as FINANNCENAME,"+//�����û���
					"bc.CardNo as CardNo,"+//���������˺�
					"(select itemname from code_library where codeno = 'BankName' and itemno = bc.BankName) as BankName,"+//������������
					"getItemname('AreaCode',bc.BankProvice) as BankProvice,"+//��������ʡ��
					"getItemname('AreaCode',bc.BankArea) as BankArea,"+//����������
					"bc.BankPoint as BankPoint,"+//����֧��
					"getItemName('Purpose1',bc.Purpose) as Purpose,"+//�Ŵ�ϵͳ���Խ��û����ֶ�
					"getbusinessname(bc.BusinessType) as BusinessType,"+//��Ʒ����
					"getItemName('EducationExperience',ii.EduExperience) as EduExperience,"+//ѧ��
					"bc.FOUNDYEAR as FOUNDYEAR,"+//��������
					"nvl((select max(Grossmargin) from Buziness_situation bs where bs.customerid = bc.customerid),0) as MRATE,"+//ë����
					"nvl(bc.BACKABLITY,0) as BACKABLITY,"+//��������
					"nvl(((select max(MAINBUZINESS) from Buziness_situation bs where bs.customerid = bc.customerid)),'') as BUSINESSMAN,"+//��Ӫҵ������
					"getItemName('Marriage',ii.Marriage) as Marriage,"+//����״��
					"getItemName('UnitNature',ii.UNITNATURE) as UNITNATURE,"+//��ҵ����
					"ii.TOTALWORKAGE as TOTALWORKAGE,"+//�ܹ���
					"getitemname('AreaCode',bc.CreditCity) as CreditCity,"+//�����ύ����
					"ii.Selfmonthincome,"+//����ʵ������
					"getItemName('YesNo',ii.Realestate2) as Realestate2,"+//�Ƿ��з�
					"getItemName('YesNo',ii.Subordinatecar2) as Subordinatecar2,"+//�Ƿ��г�
					"nvl(bc.paymentchanel,'0') as PaymentChanel,"+//֧����ʽ
					" bc.FIELD as FIELD,"+//�Ƿ�ʵ����֤
					"bc.serviceratio as MonthComprehensiveRate ,"+//�����ۺϷ���
					"getSimpleOrgName(bc.inputorgid) as InputOrgName, "+//�Ǽǻ���
					"getSimpleOrgName(substr(bc.inputorgid,0,6)) as FaOrgName,"+//�ϼ�����
					"getitemname('CustomerSort',bc.CustomerSort) as CustomerSort,"+//��Ⱥ����
					"bc.investRatio as investRatio,"+//��Ͷ��������
					"getbusinessapplicant(BC.SerialNO, 'BusinessContract') as Applicant"+//��ͬ�����ģ��
				  " from BUSINESS_CONTRACT bc, IND_INFO ii,FLOW_TASK ft"
				+ " where BC.CustomerID = II.CustomerID and BC.JimuID is not null and bc.serialno = ft.objectno and ft.objecttype = 'BusinessContract'  and ft.phaseno = '1000' and  SUBSTR(ft.endtime,0,10) >= '"+sStartTime+"' and SUBSTR(ft.endtime,0,10) <= '"+sEndTime
				+ "' and bc.businesstype in("+sBusinessType+")"
				//+"and bc.serialno='2015072400000008'";
				+ " and nvl(itemstatus, ' ')='"+sStatus+"'";
			
			rs = sqlQuery.getResultSet(sql);
			System.out.println(rs.getMetaData().getColumnCount());
			ArrayList data = new ArrayList();
			int iTotalCount = 0;
			int iDropCount = 0;
			String[][] sApplicant = {{}};
			while(rs.next()){
				Map subData = new HashMap();
				subData.put("financingProjectID", StrUtil.notNull(rs.getString("JimuID")));
				subData.put("source", StrUtil.notNull(rs.getString("VouchCompany")).replaceAll(" ", ""));                   //-----------A
				subData.put("projectName", StrUtil.notNull(rs.getString("ProjectName")).replaceAll(" ", ""));
				subData.put("artificialNo", StrUtil.notNull(rs.getString("ArtificialNo")).replaceAll(" ", ""));//���Э����
				subData.put("status", "Submitted");                                                     //add by cfxu.2015.07.23.
				subData.put("amount", rs.getDouble("BusinessSum"));
				subData.put("firstRepaymentDateStr", StrUtil.notNull(rs.getString("FirstPaymentDate")).replaceAll("/", "-"));
				subData.put("repaymentDateStr", StrUtil.notNull(rs.getString("ContractDate")).replaceAll("/", "-"));             //  repaymentDay  --->repaymentDateStr
				subData.put("payDateEveryMonth", rs.getDouble("MonthPayment"));               //ÿ�»����� ϵͳ����string
				subData.put("payToBorrowerAmount", Double.parseDouble(StrUtil.notNull((rs.getString("TermSum").replaceAll(" ", "")).replace(",", ""))));
				subData.put("repaymentAmountByMonth", Double.parseDouble(StrUtil.notNull((rs.getString("MONTHPAYMENTSUM")).replace(",", ""))));	 //�»�������
				subData.put("finaceUserName", StrUtil.notNull(rs.getString("FINANNCENAME")).replaceAll(" ", ""));
				subData.put("accountNo", DataCryptUtils.decrypt(StrUtil.notNull(rs.getString("CardNo"))));
				//subData.put("accountNo", StrUtil.notNull(rs.getString("CardNo")));
				subData.put("bankName", StrUtil.notNull(rs.getString("BankName")).replaceAll(" ", ""));
				subData.put("provence", StrUtil.notNull(rs.getString("BankProvice")).replaceAll(" ", ""));  
				subData.put("area", (StrUtil.notNull(rs.getString("BankArea")).replaceAll(" ", "")).replace(StrUtil.notNull(rs.getString("BankProvice")).replaceAll(" ", ""), ""));            //�������г�������
				subData.put("subbranchBank", StrUtil.notNull(rs.getString("BankPoint")).replaceAll(" ", ""));
				subData.put("debtUsage", StrUtil.notNull(rs.getString("Purpose")).replaceAll(" ", ""));                        //������;         
				subData.put("type", StrUtil.notNull(rs.getString("BusinessType")));                        // ��Ʒ����  
				subData.put("academic", StrUtil.notNull(rs.getString("EduExperience")));                   //ѧ��		
				subData.put("foundYears", rs.getDouble("FOUNDYEAR"));               //��������
				subData.put("grossMargin", Double.parseDouble(StrUtil.notNull(rs.getString("MRATE"))));                             //ë����
				subData.put("repaymentAbility", Double.parseDouble(StrUtil.notNull(rs.getString("BACKABLITY")).replace(",", "")));           //��������
				subData.put("mainBusinessDescrib", StrUtil.notNull(rs.getString("BUSINESSMAN")).replaceAll(" ", ""));     //��Ӫҵ������
				
				//�˴�ȱ : accountLocation    �������ڵ�
				//�˴�ȱ : operatingAreaProperty    ��Ӫ������Ȩ���
				
				subData.put("maritalStatus", StrUtil.notNull(rs.getString("Marriage")));            //����״��
				subData.put("companyNature", StrUtil.notNull(rs.getString("UNITNATURE")).replaceAll(" ", ""));         //��λ����      
				subData.put("workYears", rs.getDouble("TOTALWORKAGE"));                             //�ܹ���
				subData.put("applyCity", StrUtil.notNull(rs.getString("CreditCity")).replaceAll(" ", ""));             //�����ύ����
				subData.put("checkedIncome", rs.getDouble("Selfmonthincome"));                //����ʵ������
				subData.put("hasHouse", StrUtil.notNull(rs.getString("Realestate2")).replaceAll(" ", ""));              //�Ƿ��з�
				subData.put("hasCar", StrUtil.notNull(rs.getString("Subordinatecar2")).replaceAll(" ", ""));           //�Ƿ��г�
				subData.put("fieldCertification", StrUtil.notNull(rs.getString("FIELD")).replaceAll(" ", ""));           //ʵ����֤
				subData.put("monthComprehensiveRate", StrUtil.notNull(rs.getString("MonthComprehensiveRate")).replaceAll(" ", ""));         //�����ۺϷ���
				subData.put("orgName", StrUtil.notNull(rs.getString("FaOrgName")+"-"+rs.getString("InputOrgName")).replaceAll(" ", ""));           //�Ǽǻ���
				subData.put("customerSort", StrUtil.notNull(rs.getString("CustomerSort")).replaceAll(" ", ""));           //��Ⱥ����
				
															
				sApplicant = getStringArray(StrUtil.notNull(rs.getString("Applicant")));                 
				subData.put("coborrowerOne", sApplicant[0][0]);
				subData.put("coborrowerOneID", sApplicant[0][1]);
				subData.put("coborrowerTwo", sApplicant[1][0]);
				subData.put("coborrowerTwoID", sApplicant[1][1]);
				subData.put("coborrowerThree", sApplicant[2][0]);
				subData.put("coborrowerThreeID", sApplicant[2][1]);
				subData.put("coborrowerFour", sApplicant[3][0]);
				subData.put("coborrowerFourID", sApplicant[3][1]);
				
				subData.put("borrowerName", StrUtil.notNull(rs.getString("CustomerName")));    //����������
				subData.put("projectNo", StrUtil.notNull(rs.getString("oldlcno")));           //��Ŀ���.�������Χϵͳ���ͷ�,����ֶ�Ϊ��Χϵͳ��ĿΨһ��ʶ���ַ������͡�����������Ŀ����.
				subData.put("borrowerIdCard", DataCryptUtils.decrypt(StrUtil.notNull(rs.getString("CertID"))));			 //���������֤�� 
				//subData.put("borrowerIdCard", StrUtil.notNull(rs.getString("CertID")));			 //���������֤�� 
				//subData.put("EnumProjectStatus", StrUtil.notNull(rs.getString("ItemStatus")));	//��Ŀ״̬	����. bycfxu. 2015.07/23.	
				subData.put("financingMaturity", rs.getInt("TermMonth"));				 	 //��������		��������=��ͬ���� �·�-�״λ�����+1	
				subData.put("contractNo", StrUtil.notNull(rs.getString("SerialNo")));				 	
			
				subData.put("paymentChannel", StrUtil.notNull(rs.getString("PaymentChanel")));	//֧����ʽ

				subData.put("investRatio", Double.parseDouble(StrUtil.notNull(rs.getString("investRatio")))/100);	//��Ͷ��������
				
				data.add(subData);
				iTotalCount ++;
			}
			responseMap.put("Data", data);
			responseMap.put("TotalCount", iTotalCount);
			responseMap.put("DropCount", iDropCount);
			responseMap.put("Errors", new ArrayList());
		} catch (Exception e) {
			printLog(e);
			throw e;
		} finally{
			if(rs != null){
				DBCPManager.getInstance().free(rs, rs.getStatement(), null);
			}
		}
		return responseMap;
	}

	private String getBusinessType(String sAgentOrg) {
		////JMSD
		String sBusinessType = "";
		if("SP".equals(sAgentOrg)){//������AgentOrgΪJMSD��Ի�ľʱ������-��н��������-��ҵ��
			sBusinessType = "'1007010220','1007010230'";
		}else if("JMSDZHIXIAO".equals(sAgentOrg)){//������AgentOrgΪJMSDZHIXIAO���Ӧ��ľʱ��ֱ��
			sBusinessType = "'1007000402'";
		}
		return sBusinessType;
	}

	private String[][] getStringArray(String str) {
		String[][] strArr = {{"", ""}, {"", ""}, {"", ""}, {"", ""}};
		if(str != null && str.trim().length() > 0){
			String[] strArrSub = str.split("@");
			for(int i = 0; i < strArrSub.length; i ++){
				String[] strArrSubSub = strArrSub[i].split("#");
				for(int j = 0; j < strArrSubSub.length; j ++){
					strArr[i][j] = strArrSubSub[j];
				}
			}
		}
		return strArr;
	}

}
