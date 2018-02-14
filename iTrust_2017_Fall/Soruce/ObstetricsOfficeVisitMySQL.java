/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetrics.ObstetricsInfo;
/**
 * @author pkhanal
 *
 */
public class ObstetricsOfficeVisitMySQL {

	@Resource(name = "jdbc/itrust")
	private ObstetricsOfficeVisitSQLLoader loader;
	private DataSource ds;
	ObstetricsOfficeVisitValidator validator;
	
	/**
	 * Constructor for ObstetricsOfficeVisitMySQL
	 * Code taken from OfficeVisitMySQL constructor
	 * @throws DBException
	 */
	public ObstetricsOfficeVisitMySQL() throws DBException {
		loader = new ObstetricsOfficeVisitSQLLoader();
		validator = new ObstetricsOfficeVisitValidator();
		try{
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}
	
	public ObstetricsOfficeVisitMySQL(DataSource ds) {
		this.ds = ds;
		loader = new ObstetricsOfficeVisitSQLLoader();
		validator = new ObstetricsOfficeVisitValidator();
	}
	
	/**
	 * Returns ObstetricsOfficeVisit information in a ObstetricsOfficeVisit bean for the selected patient and date
	 * @param MID
	 * @param date
	 * @return
	 * @throws DBException
	 */
	public ObstetricOfficeVisit getObstetricsOfficeVisit(long MID, String date) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ObstetricOfficeVisit data = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM obstetricsOfficeVisit WHERE MID=? AND officeVisitDate=?");
				stmt.setLong(1, MID);
				stmt.setString(2, date);
				
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
	 * Inserts ObstetricOfficeVisit info based on patient id
	 * @param data
	 * @return
	 * @throws DBException
	 */
	public boolean insertObstetricsOfficeVisit(ObstetricOfficeVisit data) throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if (!validator.validateObstetricsOfficeVisit(data)) {
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
	 * Updates PatientInitialization info based on patient id and date
	 * @param data
	 * @return
	 * @throws DBException
	 */
	public boolean updateObstetricsOfficeVisit(ObstetricOfficeVisit data)  throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if (!validator.validateObstetricsOfficeVisit(data)) {
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
	 * Returns a list of obstetrics office visits for a given patient
	 * @param MID
	 * @return
	 * @throws DBException
	 */
	public List<ObstetricOfficeVisit> getObstetricsOfficeVisitList(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<ObstetricOfficeVisit> data = null;
		ResultSet results = null;
		
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM obstetricsOfficeVisit WHERE MID=?");
				
				stmt.setLong(1, MID);
				
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
