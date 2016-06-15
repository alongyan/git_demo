package com.amarsoft.server.util;

import java.util.Properties;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

public class DBCPManager {
	
	private static final Logger logger = Logger.getLogger(DBCPManager.class);
	static private DBCPManager instance; // 唯一实例
	static private DataSource ds;
	private DBCPManager() {
		ds = setupDataSource();
	}
	

	private static DataSource setupDataSource() {
		try {
			logger.info("开始 初始化数据库连接池......");
			Properties prop = new Properties();
			InputStream inputStream = DBCPManager.class.getClassLoader().getResourceAsStream("dbcp.properties");
			prop.load(inputStream);
			ds = BasicDataSourceFactory.createDataSource(prop);
			logger.info("完成 初始化数据库连接池......");
		} catch (Exception e) {
			logger.error(e.toString());
			logger.info(e.toString());
//			System.exit(0);
		}
		return ds;
	}

	static synchronized public DBCPManager getInstance() {
		if (instance == null) {
			instance = new DBCPManager();
		}
		return instance;
	}

	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.info("关闭ResultSet出错");
			logger.info(e.toString());
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.info("关闭Statement出错");
				logger.info(e.toString());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.info("关闭Connection出错");
					logger.info(e.toString());
				}
			}
		}
	}
	
	public void free(ResultSet rs, PreparedStatement psmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.info("关闭ResultSet出错");
			logger.info(e.toString());
		} finally {
			try {
				if (psmt != null)
					psmt.close();
			} catch (SQLException e) {
				logger.info("关闭PreparedStatement出错");
				logger.info(e.toString());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.info("关闭Connection出错");
					logger.info(e.toString());
				}
			}
		}
	}

	public synchronized Connection getConnection() {
		if (ds != null) {
			try {
				Connection conn = ds.getConnection();
				return conn;
			} catch (SQLException e) {
				logger.info("获取Connection出错");
				logger.info(e.toString());
			}
		}
		return null;
	}

	// 测试代码
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		DBCPManager db = DBCPManager.getInstance();

		conn = db.getConnection();
		if (conn == null)
			System.out.println("conn==null");
		try {
			//System.out.println("Creating connection.");
			conn = db.getConnection();
			//System.out.println("Creating statement.");
			stmt = conn.createStatement();
			//System.out.println("Executing statement.");
			String sql = "select * from user_info";
			rset = stmt.executeQuery(sql);
			//System.out.println("Results:");
			int numcols = rset.getMetaData().getColumnCount();
			while (rset.next()) {
				for (int i = 1; i <= numcols; i++) {
					System.out.print("\t" + rset.getString(i));
				}
				System.out.println("");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.free(rset, stmt, conn);
		}

	}
}