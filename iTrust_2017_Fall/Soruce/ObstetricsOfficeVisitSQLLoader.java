/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.obstetrics.ObstetricsInfo;

/**
 * @author pkhanal
 *
 */
public class ObstetricsOfficeVisitSQLLoader {

	public ObstetricsOfficeVisitSQLLoader(){
		
	}
	
	/**
	 * Create a new ObstetricOfficeVisit bean from a ResultSet
	 * @param results
	 * @return
	 * @throws SQLException
	 */
	public ObstetricOfficeVisit loadData(ResultSet results) throws NumberFormatException, SQLException{
		long mid = Long.parseLong(results.getString("MID"));
		LocalDateTime date = LocalDateTime.parse(results.getString("officeVisitDate"));
		ObstetricOfficeVisit data = new ObstetricOfficeVisit(mid, date, results.getString("numWeeksPregnant"),
				results.getInt("weight"), results.getString("bloodPressure"), results.getInt("fetalHeartRate"),
				results.getInt("numOfChildren"), results.getString("lowLyingPlacenta"));
		return data;
	}
	
	/**
	 * Load parameters of PreparedStatements based on inserting data into the database
	 * @param conn
	 * @param ps
	 * @param data
	 * @param newInstance
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement loadInsertParameters(Connection conn, PreparedStatement ps, ObstetricOfficeVisit data) throws SQLException {
		String stmt = "";
		stmt = "INSERT INTO obstetricsOfficeVisit (MID, officeVisitDate, numWeeksPregnant, weight, bloodPressure, fetalHeartRate, numOfChildren, lowLyingPlacenta) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, data.getMID());
		ps.setString(2, data.getVisitDate().toString());
		ps.setString(3, data.getWeeksPregnant());
		ps.setInt(4, data.getWeight());
		ps.setString(5, data.getBloodPressure());
		ps.setInt(6, data.getFHR());
		ps.setInt(7, data.getBabies());
		ps.setString(8, data.getLowPlacenta());
		return ps;
	}
	
	/**
	 * Load parameters of PreparedStatements based on updating data into the database
	 * @param conn
	 * @param ps
	 * @param data
	 * @param newInstance
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement loadUpdateParameters(Connection conn, PreparedStatement ps, ObstetricOfficeVisit data) throws SQLException {
		long mid = data.getMID();
		String date = data.getVisitDate().toString();
		String stmt = "";
		stmt = "UPDATE obstetricsOfficeVisit SET numWeeksPregnant=?,"
				+ "weight=?,"
				+ "bloodPressure=?,"
				+ "fetalHeartRate=?,"
				+ "numOfChildren=?,"
				+ "lowLyingPlacenta=? "
				+ "WHERE MID=" + mid + " AND officeVisitDate='" + date + "';";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, data.getWeeksPregnant());
		ps.setInt(2, data.getWeight());
		ps.setString(3, data.getBloodPressure());
		ps.setInt(4, data.getFHR());
		ps.setInt(5, data.getBabies());
		ps.setString(6, data.getLowPlacenta());
		return ps;
	}
	
	/**
	 * Load the list of obstetrics office visits
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<ObstetricOfficeVisit> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricOfficeVisit> list = new ArrayList<>();
		rs.beforeFirst();
		while (rs.next()){
			list.add(loadData(rs));
		}
		return list;
	}
	
}
