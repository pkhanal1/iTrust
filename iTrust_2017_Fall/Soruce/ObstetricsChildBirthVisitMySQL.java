/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit;

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
public class ObstetricsChildBirthVisitMySQL {
	@Resource(name = "jdbc/itrust")
	private ObstetricsChildBirthVisitSQLLoader loader;
	private DataSource ds;
	private ObstetricsChildBirthVistValidator validator;
	
	public ObstetricsChildBirthVisitMySQL() throws DBException{
		loader = new ObstetricsChildBirthVisitSQLLoader();
		validator = new ObstetricsChildBirthVistValidator();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}
	
	public ObstetricsChildBirthVisitMySQL(DataSource ds){
		this.ds = ds;
		loader = new ObstetricsChildBirthVisitSQLLoader();
		validator = new ObstetricsChildBirthVistValidator();
	}
	
	public ObstetricsChildbirthVisit getChildBirthVisit(long MID, String date) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ObstetricsChildbirthVisit data = null;
		ResultSet results =null;
		if(ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()){
			try{
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM childBirthVisit WHERE MID =? AND hosVisitDate=?");
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
	
	public boolean insertChildBirthVisit(ObstetricsChildbirthVisit data) throws DBException{
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		if (!validator.vlidateObstetricsChildBirthVisit(data)) {
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
	
	public boolean updateChildBirthVisit(ObstetricsChildbirthVisit data) throws DBException {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int results;
		
		if (!validator.vlidateObstetricsChildBirthVisit(data)) {
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
	
	public List<ObstetricsChildbirthVisit> getChildBirthVisitList(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<ObstetricsChildbirthVisit> data = null;
		ResultSet results = null;
		
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement("SELECT * FROM childBirthVisit WHERE MID=?");
				
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
