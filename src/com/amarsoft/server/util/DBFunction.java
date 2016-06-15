package com.amarsoft.server.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBFunction {
	
	public static String getSerialNo(String sTableName, String sColumnName) throws Exception{
		return getSerialNo(sTableName, sColumnName, "yyyyMMdd", "00000000",new java.util.Date());
	}
	/**
	 * 保持与系统原型获取流水号一致，以防止主键冲突
	 * @param sTableName
	 * @param sColumnName
	 * @param sDateFormat
	 * @param sNoFormat
	 * @param dateToday
	 * @return
	 * @throws Exception
	 */
	public static String getSerialNo(String sTableName, String sColumnName,	String sDateFormat, String sNoFormat, Date dateToday) throws Exception {

		// 重新获得一个数据库连接
		Connection conn = DBCPManager.getInstance().getConnection();
		
		int iMaxNo = 0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(sDateFormat);
		DecimalFormat decimalformat = new DecimalFormat(sNoFormat);
		String sDate = simpledateformat.format(dateToday);
		int iDateLen = sDate.length();
		String sNewSerialNo = "";

		sTableName = sTableName.toUpperCase();
		sColumnName = sColumnName.toUpperCase();
		boolean isAutoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement state = null;
		Statement stateSe = null;
		ResultSet rs = null;
		Statement stateUp = null;
		Statement stateIn = null;
		
		try {
			String sOld = "update OBJECT_MAXSN set MaxSerialNo =MaxSerialNo "
					+ " where TableName='" + sTableName + "' and ColumnName='"
					+ sColumnName + "' ";
			state = conn.createStatement();
			state.execute(sOld);
			String sSql = "select MaxSerialNo from OBJECT_MAXSN "
					+ " where TableName='" + sTableName + "' and ColumnName='"
					+ sColumnName + "' and Datefmt ='yyyyMMdd' ";
			stateSe = conn.createStatement();
			rs = stateSe.executeQuery(sSql);
			stateUp = conn.createStatement();
			stateIn = conn.createStatement();
			if (rs.next()) {
				// 如果流水号存在，计算新流水号。
				String sMaxSerialNo = rs.getString(1);
				
				// 如果流水号存在且为同一天，则流水号从当前号向后排列，否则从1开始。
				iMaxNo = 0;
				if (sMaxSerialNo != null && sMaxSerialNo.indexOf(sDate, 0) != -1) {
					iMaxNo = Integer.valueOf(sMaxSerialNo.substring(iDateLen)).intValue();
					sNewSerialNo = sDate + decimalformat.format(iMaxNo + 1);
				} else{ 
					sNewSerialNo = getSerialNoFromDB(sTableName, sColumnName, "", sDateFormat, sNoFormat, dateToday, conn);
				}

				// 保存新流水号
				String s9 = "update OBJECT_MAXSN set MaxSerialNo ='"
						+ sNewSerialNo + "' " + " where TableName='"
						+ sTableName + "' and ColumnName='" + sColumnName
						+ "' and Datefmt ='yyyyMMdd' ";
				stateUp.executeUpdate(s9);
			} else {
				// 如果流水号不存在，则直接从指定的数据表中获得，并在最大流水号表中插入相应记录。
				sNewSerialNo = getSerialNoFromDB(sTableName, sColumnName, "",
						sDateFormat, sNoFormat, dateToday, conn);
				String s8 = "insert into OBJECT_MAXSN (tablename,columnname,maxserialno,datefmt,nofmt) "
						+ " values( '"
						+ sTableName
						+ "','"
						+ sColumnName
						+ "','" 
						+ sNewSerialNo
						+"','"
						+sDateFormat
						+"','"
						+sNoFormat
						+"')";
				stateIn = conn.createStatement();
				stateIn.execute(s8);
			}
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception("getSerialNo...失败！" + e.getMessage());
		} finally {
			conn.setAutoCommit(isAutoCommit);
			stateIn.close();
			stateUp.close();
			stateSe.close();
			DBCPManager.getInstance().free(rs, state, conn);
		}

		//System.out.println("getSerialNo(信管规则)..." + sNewSerialNo);

		return sNewSerialNo;
	}
	
	// 传统的取表的最大流水号
	// 该方法直接从数据表中取得最大流水号，效率较低，在并发数多情况下可能会发生重号，不再推荐使用
	static public String getSerialNoFromDB(String sTableName,
			String sColumnName, String sWhereClause, String sDateFormat,
			String sNoFormat, java.util.Date dateToday, Connection conn)
			throws Exception {
		System.out.println("**********不建议的取流水号的方式(getSerialNoFromDB)**********");

		int iDateLen, iMaxNo = 0;
		String sSql, sMaxSerialNo, sDate, sNewSerialNo;
		SimpleDateFormat sdfTemp;
		DecimalFormat dfTemp;
		ResultSet rsTemp;
		Statement state = conn.createStatement();
		sdfTemp = new SimpleDateFormat(sDateFormat);
		dfTemp = new DecimalFormat(sNoFormat);

		sDate = sdfTemp.format(dateToday);
		iDateLen = sDate.length();

		sSql = "select max(" + sColumnName + ") from " + sTableName + " where "
				+ sColumnName + " like '" + sDate + "%' ";
		if (sWhereClause.length() > 0)
			sSql += " and " + sWhereClause;

		rsTemp = state.executeQuery(sSql);
		if (rsTemp.next()) {
			sMaxSerialNo = rsTemp.getString(1);
			if (sMaxSerialNo != null)
				iMaxNo = Integer.valueOf(sMaxSerialNo.substring(iDateLen)).intValue();
		}

		sNewSerialNo = sDate + dfTemp.format(iMaxNo + 1);
		rsTemp.getStatement().close();
		System.out.println("..." + sNewSerialNo + "...");
		return sNewSerialNo;
	}
	
	public static void main(String[] args) throws Exception{
		String SerialNo = DBFunction.getSerialNo("IND_OASSET", "SERIALNO", "yyyyMMdd", "000000", new java.util.Date());
		System.out.println(SerialNo);
	}
	/**
	 * 获取当天账务日期
	 * @param conn
	 * @return
	 */
	public static String getToday(Connection conn) {
		String sSql = "select curdeductdate from ploan_setup ";
		PreparedStatement psmt = null;
		ResultSet rSet = null;
		String sToday = "";
		try {
			psmt = conn.prepareStatement(sSql);
			rSet = psmt.executeQuery();
			while (rSet.next()) {
				sToday = rSet.getString("curdeductdate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBCPManager.getInstance().free(rSet, psmt, null);
		}
		return sToday;
	}
}
