package com.amarsoft.server.dao;
/**
 * SQL语句查询对象，每次必须生成一个实例进行相应的sql查询，防止多线程资源冲突
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;

import com.amarsoft.server.util.DBCPManager;

public class SQLQuery {
	private Logger logger = Logger.getLogger(SQLQuery.class);
	private boolean isLogger = true;
	private String today;//进行一下缓存，一次请求多次查询
	private Connection conn;
	public SQLQuery(){
		this(null);
	}
	public SQLQuery(Connection conn){
		this.conn = conn;
	}
	
	public Connection getConnection(){
		if(conn == null){
			conn = DBCPManager.getInstance().getConnection();
		}
		return conn;
	}
	
	/**
	 * 设定连接提交方式
	 */
	public void  setAutoCommit(Boolean boo) throws Exception{
		if(conn != null){
			try {
				conn.setAutoCommit(boo);
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * 提交连接
	 * @throws SQLException 
	 */
	public void commit() throws SQLException{
		if(conn!= null){
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * 回滚连接
	 * @throws SQLException 
	 */
	public void rollback() throws SQLException{
		if(conn != null){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * 设置执行查询时，是否进行日志输出
	 * @param isLogger
	 */
	public void setLoggerModel(boolean isLogger){
		this.isLogger = isLogger;
		if(isLogger && logger == null){
			logger = Logger.getLogger(SQLQuery.class);
		}
	}
	/**
	 * 用于查询sql返回一个单条记录单个字段的查询结果，返回String形式
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String getString(String sql) throws Exception {
		if(isLogger)
			logger.info(sql);
		Statement state = null;
		ResultSet rs = null;
		try {
			state = this.getConnection().createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(rs, state, null);
		}
		return null;
	}
	
	public double getDouble(String sql) throws Exception {
		if(isLogger)
			logger.info(sql);
		Statement state = null;
		ResultSet rs = null;
		double retDou = 0;
		try {
			state = this.getConnection().createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				retDou = rs.getDouble(1);
			}
			return retDou;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(rs, state, null);
		}
	}
	
	/**
	 * 用于查询sql返回一个单条记录单个字段的查询结果，返回Int形式
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int getInt(String sql) throws Exception {
		String str = getString(sql);
		if(str == null || str.trim().length() == 0){
			throw new Exception("未查询结果集");
		}
		return Integer.parseInt(str);
	}	
	
	/**
	 * 查询当天的帐务日期
	 * @return
	 * @throws Exception
	 */
	public String getToday() throws Exception {
//		if(today == null || "".equals(today.trim())){
//			today = getString("select curdeductdate from ploan_setup ");
//		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
		today = df.format(new Date());// new Date()为获取当前系统时间
		return today;
	}
	
	/**
	 * 获取结果集，以字符串数组的形式
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String[] getStringArr(String sql) throws Exception{
		if(isLogger)
			logger.info(sql);
		Statement state = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String[] retArr = null;
		try {
			state = this.getConnection().createStatement();
			rs = state.executeQuery(sql);
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			retArr = new String[columnCount];
			if(rs.next()) {
				for(int i = 0; i < columnCount;){
					retArr[i] = rs.getString(++i);
				}
			}
			return retArr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(rs, state, null);
		}
	}
	
	/**
	 * 获取结果集，以Map的形式
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Map getMap(String sql) throws Exception{
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Map map = new CaseInsensitiveMap();
		try {
			rs = getResultSet(sql);
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if(rs.next()){
				for(int i = 1; i <= columnCount; i ++){
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(rs, rs.getStatement(), null);
		}
	}
	
	/**
	 * 执行SQL更新插入语句
	 * @param sql
	 * @throws Exception
	 */
	public void execute(String sql) throws Exception{
		if(isLogger)
			logger.info(sql);
		Statement state = null;
		try {
			state = this.getConnection().createStatement();
			state.execute(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(null, state, null);
		}
	}
	
	public void executeUpdate(String sql) throws Exception{
		Statement state = null;
		try {
			state = this.getConnection().createStatement();
			state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBCPManager.getInstance().free(null, state, null);
		}
	}

	public ResultSet getResultSet(String sql) throws Exception {
		if(isLogger)
			logger.info(sql);
		Statement state = null;
		try {
			state = this.getConnection().createStatement();
			return state.executeQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
		}
	}
	
	public Map getMapByTwoColumns(String sql) throws Exception{
		Map map = null;
		ResultSet rs = null;
		try {
			rs = getResultSet(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			map = new CaseInsensitiveMap();
			if(rsmd.getColumnCount() == 2){
				while(rs.next()){
					map.put(rs.getString(1), rs.getObject(2));
				}
			}
		} catch (Exception e) {
			throw e;
		} finally{
			DBCPManager.getInstance().free(rs, rs.getStatement(), conn);
		}
		return map;
	}
	public void close(){
		if(this.conn != null){
			DBCPManager.getInstance().free(null, null, conn);
			this.conn = null;
		}
	}
	public static void main(String[] args) throws Exception{
		SQLQuery sqlQuery = new SQLQuery();
		sqlQuery.setLoggerModel(true);
		//String sSql = "select * from (select ui.userid as UserID,ui.username as UserName,oi.orgid as OrgID,oi.orgname as OrgName from user_info ui ,org_info oi where oi.orgid = ui.belongorg and oi.orgid = '100' and ui.userid in (select userid from User_Role where roleid = '487') order by dbms_random.value)  where rownum<=1 ";
		//String sSql = "select * from (select * from user_info order by dbms_random.value)  where rownum<=1 ";
		String sql = "select ItemNo, ItemName from code_library where codeno='Sex' ";
		Map map = sqlQuery.getMapByTwoColumns(sql);
		map.put("States", map.size() == 0? "01":"00");
		System.out.println(map);
	}
}
