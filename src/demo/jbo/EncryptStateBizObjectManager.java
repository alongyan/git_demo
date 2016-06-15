package demo.jbo;

import java.util.Arrays;

import com.amarsoft.are.jbo.BizObjectManager;
import com.amarsoft.are.jbo.BizObjectQuery;
import com.amarsoft.are.jbo.JBOException;
import com.amarsoft.are.jbo.impl.ALSBizObjectManager;
import com.amarsoft.are.lang.DataElement;
import com.amarsoft.are.lang.StringX;
import com.amarsoft.are.security.DESEncrypt;
import com.amarsoft.are.sql.Query;

public class EncryptStateBizObjectManager extends ALSBizObjectManager {

	private String encryptAttributes = null;
	private String[] encryptAttribute = null;
	
	/* (non-Javadoc)
	 * @see com.amarsoft.are.jbo.impl.StateBizObjectManager#createBasicQuery(com.amarsoft.are.jbo.BizObjectManager, java.lang.String)
	 */
	protected BizObjectQuery createBasicQuery(BizObjectManager manager, String query) throws JBOException {
		return new EncryptBizObjectQuery(this,query);
	}

	/* (non-Javadoc)
	 * @see com.amarsoft.are.jbo.impl.StateBizObjectManager#setParameter(com.amarsoft.are.sql.Query, com.amarsoft.are.lang.DataElement[])
	 */
	protected void setParameter(Query query, DataElement[] param) throws JBOException {
		for(int i=0;i<param.length;i++){
			if(param[i].getType()==DataElement.STRING && isEncyptAttribute(param[i].getName())){
				param[i].setValue(encrypt(param[i].getString()));  //替换数据为加密后的数据
			}
		}
		super.setParameter(query, param);
	}
	
	
	/**
	 * 定义加密的属性
	 * @param encryptAttributes 属性列表
	 * @return 
	 */
	public void setEncryptAttributes(String encryptAttributes) {
		String s = encryptAttributes;
		if(s!=null) s = s.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
		if(s.length()==0) s = null;
		this.encryptAttributes = s;
	}
	
	/**
	 * 判断是否加密字段
	 * @param attr
	 * @return
	 */
	public boolean isEncyptAttribute(String attr){
		if(StringX.isSpace(attr)) return false;
		if(encryptAttribute==null){
			if(StringX.isSpace(encryptAttributes)) {
				encryptAttribute = new String[0];
			}else{
				String[] ea = encryptAttributes.split(",");
				encryptAttribute = new String[ea.length];
				for(int i=0;i<ea.length;i++) encryptAttribute[i] = ea[i].toUpperCase();
				Arrays.sort(encryptAttribute);
			}
		}
		return Arrays.binarySearch(encryptAttribute, attr.toUpperCase())>=0;
	}

	/**
	 * 加密字符串
	 * @param src 明文
	 * @return 密文
	 */
	public String encrypt(String src){
		//demo使用ARE的DES加密算法，请自行更换
		return DESEncrypt.encrypt(src,"JiMuHezi");
	}
	
	/**
	 * 解密字符串
	 * @param sec 密文
	 * @return 明文
	 */
	public String decrypt(String sec){
		//demo使用ARE的DES加密算法，请自行更换
		return DESEncrypt.decrypt(sec,"JiMuHezi");
	}
}
