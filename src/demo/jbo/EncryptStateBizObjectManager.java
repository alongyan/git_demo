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
				param[i].setValue(encrypt(param[i].getString()));  //�滻����Ϊ���ܺ������
			}
		}
		super.setParameter(query, param);
	}
	
	
	/**
	 * ������ܵ�����
	 * @param encryptAttributes �����б�
	 * @return 
	 */
	public void setEncryptAttributes(String encryptAttributes) {
		String s = encryptAttributes;
		if(s!=null) s = s.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
		if(s.length()==0) s = null;
		this.encryptAttributes = s;
	}
	
	/**
	 * �ж��Ƿ�����ֶ�
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
	 * �����ַ���
	 * @param src ����
	 * @return ����
	 */
	public String encrypt(String src){
		//demoʹ��ARE��DES�����㷨�������и���
		return DESEncrypt.encrypt(src,"JiMuHezi");
	}
	
	/**
	 * �����ַ���
	 * @param sec ����
	 * @return ����
	 */
	public String decrypt(String sec){
		//demoʹ��ARE��DES�����㷨�������и���
		return DESEncrypt.decrypt(sec,"JiMuHezi");
	}
}
