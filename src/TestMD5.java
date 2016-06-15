import com.amarsoft.server.util.EncryptUtil;


public class TestMD5 {
	public static void main(String[] args) throws Exception{
		String token = "高中、中专11262银联标准卡62146510107564753000.00邓超双虎家私商业/贸易30412965808@qq.com10300.008000.0002015-02-06500384198904242415身份证2015-04-027760.00未知重庆市重庆市15923740100汉102015-06-17159237401002基层服务人员重庆市南川市南城街道办事处大星村8组未知2015042510067210借记卡412965808王忠1592366623412008成功C8000.000.00100.00信用卡代偿08000.000.008f09ddc6c36d44cf8873eb58fc82664a";
		EncryptUtil eu = new EncryptUtil();
        System.out.println(eu.md5Digest(token));
	}
}
