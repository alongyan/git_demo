import com.amarsoft.server.util.EncryptUtil;


public class TestMD5 {
	public static void main(String[] args) throws Exception{
		String token = "���С���ר11262������׼��62146510107564753000.00�˳�˫����˽��ҵ/ó��30412965808@qq.com10300.008000.0002015-02-06500384198904242415���֤2015-04-027760.00δ֪������������15923740100��102015-06-17159237401002���������Ա�������ϴ����ϳǽֵ����´����Ǵ�8��δ֪2015042510067210��ǿ�412965808����1592366623412008�ɹ�C8000.000.00100.00���ÿ�����08000.000.008f09ddc6c36d44cf8873eb58fc82664a";
		EncryptUtil eu = new EncryptUtil();
        System.out.println(eu.md5Digest(token));
	}
}
