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
 * @describe 该方法用于获取为IN系统准备数据内容
 * @author amar
 * history： 调整字段级注释. by cfxu.2015.07.23.
 * @history:调整获取Agent对应的信贷产品内容 
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
					"bc.CustomerName," +//融资人姓名
					"bc.oldlcno,"+//外围系统项目编号
					"bc.SerialNo," +//合同编号
					"ii.certid as CertID," +//融资身份证号码
					"bc.JimuID," +//积木编号
					"'禅融' as VouchCompany," +//项目名称
					"bc.customername as ProjectName," +//项目名称
					"bc.ItemStatus," +//状态
					"bc.ArtificialNo as ArtificialNo,"+//借款协议编号
					"bc.BusinessSum," +//融资金额
					"bc.termmonth as TermMonth," +//期限
					"bc.FirstPaymentDate as FirstPaymentDate," +//首次还款日
					"bc.dueDate as ContractDate," +//合同还款日
					"bc.monthRepayment as MonthPayment," +//每月还款日
					"nvl(bc.TermSum,0) as TermSum,"+//融资人实收金额
					"nvl(bc.monthpayment,0) as MONTHPAYMENTSUM,"+//每月还款额
					"bc.Customername as FINANNCENAME,"+//融资用户名
					"bc.CardNo as CardNo,"+//开户银行账号
					"(select itemname from code_library where codeno = 'BankName' and itemno = bc.BankName) as BankName,"+//开户银行名称
					"getItemname('AreaCode',bc.BankProvice) as BankProvice,"+//开户银行省份
					"getItemname('AreaCode',bc.BankArea) as BankArea,"+//开户银行区
					"bc.BankPoint as BankPoint,"+//开户支行
					"getItemName('Purpose1',bc.Purpose) as Purpose,"+//信贷系统中淘金家没这个字段
					"getbusinessname(bc.BusinessType) as BusinessType,"+//产品类型
					"getItemName('EducationExperience',ii.EduExperience) as EduExperience,"+//学历
					"bc.FOUNDYEAR as FOUNDYEAR,"+//成立年限
					"nvl((select max(Grossmargin) from Buziness_situation bs where bs.customerid = bc.customerid),0) as MRATE,"+//毛利率
					"nvl(bc.BACKABLITY,0) as BACKABLITY,"+//还款能力
					"nvl(((select max(MAINBUZINESS) from Buziness_situation bs where bs.customerid = bc.customerid)),'') as BUSINESSMAN,"+//主营业务描述
					"getItemName('Marriage',ii.Marriage) as Marriage,"+//婚姻状况
					"getItemName('UnitNature',ii.UNITNATURE) as UNITNATURE,"+//企业性质
					"ii.TOTALWORKAGE as TOTALWORKAGE,"+//总共玲
					"getitemname('AreaCode',bc.CreditCity) as CreditCity,"+//贷款提交城市
					"ii.Selfmonthincome,"+//经核实月收入
					"getItemName('YesNo',ii.Realestate2) as Realestate2,"+//是否有房
					"getItemName('YesNo',ii.Subordinatecar2) as Subordinatecar2,"+//是否有车
					"nvl(bc.paymentchanel,'0') as PaymentChanel,"+//支付方式
					" bc.FIELD as FIELD,"+//是否实地认证
					"bc.serviceratio as MonthComprehensiveRate ,"+//融资综合费率
					"getSimpleOrgName(bc.inputorgid) as InputOrgName, "+//登记机构
					"getSimpleOrgName(substr(bc.inputorgid,0,6)) as FaOrgName,"+//上级机构
					"getitemname('CustomerSort',bc.CustomerSort) as CustomerSort,"+//客群分类
					"bc.investRatio as investRatio,"+//给投资人利率
					"getbusinessapplicant(BC.SerialNO, 'BusinessContract') as Applicant"+//共同借款人模块
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
				subData.put("artificialNo", StrUtil.notNull(rs.getString("ArtificialNo")).replaceAll(" ", ""));//借款协议编号
				subData.put("status", "Submitted");                                                     //add by cfxu.2015.07.23.
				subData.put("amount", rs.getDouble("BusinessSum"));
				subData.put("firstRepaymentDateStr", StrUtil.notNull(rs.getString("FirstPaymentDate")).replaceAll("/", "-"));
				subData.put("repaymentDateStr", StrUtil.notNull(rs.getString("ContractDate")).replaceAll("/", "-"));             //  repaymentDay  --->repaymentDateStr
				subData.put("payDateEveryMonth", rs.getDouble("MonthPayment"));               //每月还款日 系统中是string
				subData.put("payToBorrowerAmount", Double.parseDouble(StrUtil.notNull((rs.getString("TermSum").replaceAll(" ", "")).replace(",", ""))));
				subData.put("repaymentAmountByMonth", Double.parseDouble(StrUtil.notNull((rs.getString("MONTHPAYMENTSUM")).replace(",", ""))));	 //月还款能力
				subData.put("finaceUserName", StrUtil.notNull(rs.getString("FINANNCENAME")).replaceAll(" ", ""));
				subData.put("accountNo", DataCryptUtils.decrypt(StrUtil.notNull(rs.getString("CardNo"))));
				//subData.put("accountNo", StrUtil.notNull(rs.getString("CardNo")));
				subData.put("bankName", StrUtil.notNull(rs.getString("BankName")).replaceAll(" ", ""));
				subData.put("provence", StrUtil.notNull(rs.getString("BankProvice")).replaceAll(" ", ""));  
				subData.put("area", (StrUtil.notNull(rs.getString("BankArea")).replaceAll(" ", "")).replace(StrUtil.notNull(rs.getString("BankProvice")).replaceAll(" ", ""), ""));            //开户银行城市名称
				subData.put("subbranchBank", StrUtil.notNull(rs.getString("BankPoint")).replaceAll(" ", ""));
				subData.put("debtUsage", StrUtil.notNull(rs.getString("Purpose")).replaceAll(" ", ""));                        //贷款用途         
				subData.put("type", StrUtil.notNull(rs.getString("BusinessType")));                        // 产品类型  
				subData.put("academic", StrUtil.notNull(rs.getString("EduExperience")));                   //学历		
				subData.put("foundYears", rs.getDouble("FOUNDYEAR"));               //成立年限
				subData.put("grossMargin", Double.parseDouble(StrUtil.notNull(rs.getString("MRATE"))));                             //毛利率
				subData.put("repaymentAbility", Double.parseDouble(StrUtil.notNull(rs.getString("BACKABLITY")).replace(",", "")));           //还款能力
				subData.put("mainBusinessDescrib", StrUtil.notNull(rs.getString("BUSINESSMAN")).replaceAll(" ", ""));     //主营业务描述
				
				//此处缺 : accountLocation    户口所在地
				//此处缺 : operatingAreaProperty    经营场所产权情况
				
				subData.put("maritalStatus", StrUtil.notNull(rs.getString("Marriage")));            //婚姻状况
				subData.put("companyNature", StrUtil.notNull(rs.getString("UNITNATURE")).replaceAll(" ", ""));         //单位性质      
				subData.put("workYears", rs.getDouble("TOTALWORKAGE"));                             //总工龄
				subData.put("applyCity", StrUtil.notNull(rs.getString("CreditCity")).replaceAll(" ", ""));             //贷款提交城市
				subData.put("checkedIncome", rs.getDouble("Selfmonthincome"));                //经核实月收入
				subData.put("hasHouse", StrUtil.notNull(rs.getString("Realestate2")).replaceAll(" ", ""));              //是否有房
				subData.put("hasCar", StrUtil.notNull(rs.getString("Subordinatecar2")).replaceAll(" ", ""));           //是否有车
				subData.put("fieldCertification", StrUtil.notNull(rs.getString("FIELD")).replaceAll(" ", ""));           //实地认证
				subData.put("monthComprehensiveRate", StrUtil.notNull(rs.getString("MonthComprehensiveRate")).replaceAll(" ", ""));         //融资综合费率
				subData.put("orgName", StrUtil.notNull(rs.getString("FaOrgName")+"-"+rs.getString("InputOrgName")).replaceAll(" ", ""));           //登记机构
				subData.put("customerSort", StrUtil.notNull(rs.getString("CustomerSort")).replaceAll(" ", ""));           //客群分类
				
															
				sApplicant = getStringArray(StrUtil.notNull(rs.getString("Applicant")));                 
				subData.put("coborrowerOne", sApplicant[0][0]);
				subData.put("coborrowerOneID", sApplicant[0][1]);
				subData.put("coborrowerTwo", sApplicant[1][0]);
				subData.put("coborrowerTwoID", sApplicant[1][1]);
				subData.put("coborrowerThree", sApplicant[2][0]);
				subData.put("coborrowerThreeID", sApplicant[2][1]);
				subData.put("coborrowerFour", sApplicant[3][0]);
				subData.put("coborrowerFourID", sApplicant[3][1]);
				
				subData.put("borrowerName", StrUtil.notNull(rs.getString("CustomerName")));    //融资人姓名
				subData.put("projectNo", StrUtil.notNull(rs.getString("oldlcno")));           //项目编号.如果是外围系统推送方,则此字段为外围系统项目唯一标识，字符串类型。用于推送项目排重.
				subData.put("borrowerIdCard", DataCryptUtils.decrypt(StrUtil.notNull(rs.getString("CertID"))));			 //融资融身份证号 
				//subData.put("borrowerIdCard", StrUtil.notNull(rs.getString("CertID")));			 //融资融身份证号 
				//subData.put("EnumProjectStatus", StrUtil.notNull(rs.getString("ItemStatus")));	//项目状态	作废. bycfxu. 2015.07/23.	
				subData.put("financingMaturity", rs.getInt("TermMonth"));				 	 //融资期限		融资期限=合同还款 月份-首次还款月+1	
				subData.put("contractNo", StrUtil.notNull(rs.getString("SerialNo")));				 	
			
				subData.put("paymentChannel", StrUtil.notNull(rs.getString("PaymentChanel")));	//支付方式

				subData.put("investRatio", Double.parseDouble(StrUtil.notNull(rs.getString("investRatio")))/100);	//给投资人利率
				
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
		if("SP".equals(sAgentOrg)){//若传递AgentOrg为JMSD则对积木时代禅融-工薪贷、禅融-融业贷
			sBusinessType = "'1007010220','1007010230'";
		}else if("JMSDZHIXIAO".equals(sAgentOrg)){//若传递AgentOrg为JMSDZHIXIAO则对应积木时代直销
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
