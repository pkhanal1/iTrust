/**
 * 
 */
package edu.ncsu.csc.itrust.model.patientPicture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class PatientPictureMySQL {

	@Resource(name = "jdbc/itrust")
	private PatientPictureSQLLoader loader;
	private DataSource ds;
	
	/**
	 * @throws DBException 
	 * 
	 */
	public PatientPictureMySQL() throws DBException {
		loader = new PatientPictureSQLLoader();
		try{
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
	} 
	
	public PatientPictureMySQL(DataSource ds){
		this.ds = ds;
		loader = new PatientPictureSQLLoader();
	}
	
	public PatientPicture getPatientPicture(long MID) throws DBException{
		Connection conn = null;
		PreparedStatement stmt = null;
		PatientPicture data = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM PatientPicture WHERE MID=?");
				stmt.setLong(1, MID);
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
	
	public boolean insertPatientPicture(PatientPicture data) throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if(data.getMid() < 1){
			return false;
		}
		try {
			conn = ds.getConnection();
			stmt = loader.loadInsert(conn, stmt, data);
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
	
	public boolean updatePatientPicture(PatientPicture data) throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if(data.getMid() < 1){
			return false;
		}
		try {
			conn = ds.getConnection();
			stmt = loader.loadUpdate(conn, stmt, data);
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
	
}
