/**
 * 
 */
package edu.ncsu.csc.itrust.model.patientPicture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

/**
 * @author pkhanal
 *
 */
public class PatientPictureSQLLoader {

	public PatientPictureSQLLoader(){
		
	}
	
	public PatientPicture loadData(ResultSet results) throws NumberFormatException, SQLException {	
		long mid = Long.parseLong(results.getString("MID"));
		PatientPicture data = new PatientPicture(mid, results.getBinaryStream("picture"));
		return data;
	}
	
	public PreparedStatement loadInsert(Connection conn, PreparedStatement ps, PatientPicture data) throws SQLException{
		String stmt = "";
		stmt = "INSERT INTO patientPicture (MID, picture) VALUES(?,?);";
		
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, data.getMid());
		ps.setBinaryStream(2, data.getPatientPicture());
		return ps;
		
	}
	
	public PreparedStatement loadUpdate(Connection conn, PreparedStatement ps, PatientPicture data) throws SQLException{
		String stmt = "";
		long mid = data.getMid();
		stmt = "UPDATE patientPicture SET picture=? WHERE MID=" + mid + ";";
		
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setBinaryStream(1, data.getPatientPicture());
		return ps;
		
	}
}
