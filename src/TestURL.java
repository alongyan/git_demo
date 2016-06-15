import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class TestURL {
	public static void main(String args[]){
		try {
			//String sStr ="requestJson={\"service\":\"002001\",\"LoginID\":\"cxy\",\"Password\":\"1CC357616CC513B4896F8FED824B3E191\"}";
			//String sStr ="{\"SPID\":\"201601\"}";
			//String sStr ="{\"SPID\":\"201614\",\"CompanyTel\":\"13212441234\"}";
		//	String sStr ="{\"jsonData\":\"{\"service\":\"002001\",\"appNo\":\"111\",\"productCd\":\"111\",\"name\":\"111\",\"idNo\":\"111\",\"cellphone\":\"111\",\"centralBankCreditAuthFlag\":\"111\",\"terminalType\":\"111\",\"terminalVersion\":\"111\",\"picSerialNo\":\"111\",\"address\":\"111\",\"addressDetail\":\"111\",\"education\":\"111\",\"maritalStatus\":\"111\",\"houseCondition\":\"111\",\"industry\":\"111\",\"profession\":\"111\",\"monthIncome\":\"111\",\"yearIncome\":\"111\",\"organizationName\":\"111\",\"organizationAddress\":\"111\",\"organizationAddressDetail\":\"111\",\"organizationTelephone\":\"111\",\"contactType\":\"111\",\"contactName\":\"111\",\"contactTelephone\":\"111\",\"bankCardNo\":\"111\",\"bankCode\":\"111\",\"bankNameSub\":\"111\",\"bankReservePhone\":\"111\"}\"}";
			HashMap requestHashMap=new HashMap();
			String jsonData="{\"service\":\"002001\",\"appNo\":\"111\",\"productCd\":\"111\",\"name\":\"111\",\"idNo\":\"111\",\"cellphone\":\"111\",\"centralBankCreditAuthFlag\":\"111\",\"terminalType\":\"111\",\"terminalVersion\":\"111\",\"picSerialNo\":\"111\",\"address\":\"111\",\"addressDetail\":\"111\",\"education\":\"111\",\"maritalStatus\":\"111\",\"houseCondition\":\"111\",\"industry\":\"111\",\"profession\":\"111\",\"monthIncome\":\"111\",\"yearIncome\":\"111\",\"organizationName\":\"111\",\"organizationAddress\":\"111\",\"organizationAddressDetail\":\"111\",\"organizationTelephone\":\"111\",\"contactType\":\"111\",\"contactName\":\"111\",\"contactTelephone\":\"111\",\"bankCardNo\":\"111\",\"bankCode\":\"111\",\"bankNameSub\":\"111\",\"bankReservePhone\":\"111\"}";
			requestHashMap.put("jsonData", jsonData);
			
			System.out.println(sStr);
			String sReturn = doPost("http://10.129.143.66:8080/PreCash/facade/post.do",sStr);
			//String sReturn = doPost("http://localhost:9090/AmarHTTPServer/CJ/BT/BusinessType",sStr);
			System.out.println(sReturn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public static String doPost(String url, String param) throws Exception {
		 	PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        int sResponseCode = 0;
	        try {
	        	
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            HttpURLConnection httpcon = (HttpURLConnection) conn;
	            // 设置通用的请求属性
	            httpcon.setRequestMethod("POST");
	            httpcon.setRequestProperty("accept", "*/*");
	            httpcon.setRequestProperty("connection", "Keep-Alive");
//	            httpcon.setRequestProperty("Content-Type", "application/json");
	            httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            httpcon.setRequestProperty("Ticket", "519D86ECC427F35A416E7A2F98DC0782");
	            // 发送POST请求必须设置如下两行
	            httpcon.setDoOutput(true);
	            httpcon.setDoInput(true);
	           
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(new OutputStreamWriter(httpcon.getOutputStream(),"utf-8"));
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            
	            // 定义BufferedReader输入流来读取URL的响应
	            sResponseCode=httpcon.getResponseCode();
	            if(sResponseCode == 200){
	            	 in = new BufferedReader(
	 	                    new InputStreamReader(httpcon.getInputStream(),"utf-8"));
	 	            String line;
	 	            while ((line = in.readLine()) != null) {
	 	                result += line;
	 	            }
	            } 
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        } finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }
}
