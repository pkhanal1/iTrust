/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
/**
 * @author pkhanal
 *
 */
public class UltrasoundRecordMySQL {

	@Resource(name = "jdbc/itrust")
	private UltrasoundRecordSQLLoader loader;
	private DataSource ds;
	UltrasoundRecordValidator validator;
	
	/**
	 * Constructor for ObstetricsOfficeVisitMySQL
	 * Code taken from OfficeVisitMySQL constructor
	 * @throws DBException
	 */
	public UltrasoundRecordMySQL() throws DBException {
		loader = new UltrasoundRecordSQLLoader();
		validator = new UltrasoundRecordValidator();
		try{
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}
	
	public UltrasoundRecordMySQL(DataSource ds) {
		this.ds = ds;
		loader = new UltrasoundRecordSQLLoader();
		validator = new UltrasoundRecordValidator();
	}
	
	/**
	 * Returns UltrasoundRecord information in a UltrasoundRecord bean for the selected patient and date
	 * @param MID
	 * @param date
	 * @return
	 * @throws DBException
	 */
	public UltrasoundRecord getUltrasoundRecord(long MID, String date, int whichBaby) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		UltrasoundRecord data = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM ultrasoundRecord WHERE MID=? AND offVisitDate=? AND whichBaby=?");
				stmt.setLong(1, MID);
				stmt.setString(2, date);
				stmt.setInt(3, whichBaby);
				
				results = stmt.executeQuery();
				if (results.next() && results.getLong(1) != 0) {
					data = loader.loadData(results);
					return data;
				} else {
					return null;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new DBException(e);
			} finally {
				try {
					if (results != null) {
						results.close();
					}
				} catch (SQLException e) {
					throw new DBException(e);
				} finally {
					DBUtil.closeConnection(conn, stmt);
				}
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Inserts UltrasoundRecord info based on patient id
	 * @param data
	 * @return
	 * @throws DBException
	 */
	public boolean insertUltrasoundRecord(UltrasoundRecord data) throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if (!validator.validateUltrasoundRecord(data)) {
			return false;
		}
		
		try {
			conn = ds.getConnection();
			stmt = loader.loadInsertParameters(conn, stmt, data);
			results = stmt.executeUpdate();
			ret = (results > 0);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
		
		return ret;
	}
	
	/**
	 * Updates UltrasoundRecord info based on patient id and date
	 * @param data
	 * @return
	 * @throws DBException
	 */
	public boolean updateUltrasoundRecord(UltrasoundRecord data)  throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if (!validator.validateUltrasoundRecord(data)) {
			return false;
		}
		
		try {
			conn = ds.getConnection();
			stmt = loader.loadUpdateParameters(conn, stmt, data);
			results = stmt.executeUpdate();
			ret = (results > 0);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
		
		return ret;
	}
	
	/**
	 * Returns PatientInitialization information in a PatientInitialization bean for the selected patient and date
	 * @param MID
	 * @param date
	 * @return
	 * @throws DBException
	 */
	public List<UltrasoundRecord> getUltrasoundRecordList(long MID, String date) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<UltrasoundRecord> data = null;
		ResultSet results = null;
		
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM ultrasoundRecord WHERE MID=? AND offVisitDate=?");
				
				stmt.setLong(1, MID);
				stmt.setString(2, date);

				results = stmt.executeQuery();
				if (results.next() && results.getLong(1) != 0) {
					data = loader.loadList(results);
					return data;
				} else {
					data = Collections.emptyList();
					return data;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new DBException(e);
			} finally {
				try {
					if (results != null) {
						results.close();
					}
				} catch (SQLException e) {
					throw new DBException(e);
				} finally {
					DBUtil.closeConnection(conn, stmt);
				}
			}
		} else {
			return null;
		}
	}
}
