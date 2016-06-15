package demo.jbo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amarsoft.are.jbo.BizObject;
import com.amarsoft.are.jbo.BizObjectQuery;
import com.amarsoft.are.jbo.JBOException;
import com.amarsoft.are.jbo.impl.BasicSQLQuery;
import com.amarsoft.are.lang.DataElement;

public class EncryptBizObjectQuery extends BasicSQLQuery {
	EncryptStateBizObjectManager manager = null;
	
	protected EncryptBizObjectQuery(EncryptStateBizObjectManager manager, String queryString) throws JBOException {
		super(manager, queryString);
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see com.amarsoft.are.jbo.impl.BasicSQLQuery#wrapResultSet(java.sql.ResultSet)
	 */
	protected BizObject wrapResultSet(ResultSet rs) throws SQLException {
		BizObject bo =  super.wrapResultSet(rs);
		DataElement[] de = bo.getAttributes();
		for(int i=0;i<de.length;i++){
			if(de[i].getType()==DataElement.STRING && manager.isEncyptAttribute(de[i].getName())){
				de[i].setValue(manager.decrypt(de[i].getString()));  //替换数据为解密后的数据
			}
		}
		return bo;
	}

	/* (non-Javadoc)
	 * @see com.amarsoft.are.jbo.AbstractBizObjectQuery#setParameter(com.amarsoft.are.lang.DataElement)
	 */
	public BizObjectQuery setParameter(DataElement param) {
		if(param.getType()==DataElement.STRING && manager.isEncyptAttribute(param.getName())){
			param.setValue(manager.encrypt(param.getString()));  //替换参数为加密后的数据
		}
		return super.setParameter(param);
	}

	/* (non-Javadoc)
	 * @see com.amarsoft.are.jbo.AbstractBizObjectQuery#setParameter(java.lang.String, java.lang.String)
	 */
	public BizObjectQuery setParameter(String name, String value) {
		return super.setParameter(name, manager.isEncyptAttribute(name)?manager.encrypt(value):value);
	}
	
}
