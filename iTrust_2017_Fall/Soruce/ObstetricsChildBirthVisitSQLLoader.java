/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pkhanal
 *
 */
public class ObstetricsChildBirthVisitSQLLoader {

	public ObstetricsChildBirthVisitSQLLoader(){
		
	}
	
	/**
	 * 
	 * @param results
	 * @return
	 * @throws SQLException
	 */
	public ObstetricsChildbirthVisit loadData(ResultSet results) throws SQLException {
		long mid = Long.parseLong(results.getString("MID"));
		LocalDateTime date = LocalDateTime.parse(results.getString("hosVisitDate"));
		ObstetricsChildbirthVisit data = new ObstetricsChildbirthVisit(mid, date,
				results.getBoolean("preSchedule"),
				results.getString("delivaryPreferred"),
				results.getDouble("pitocin"),
				results.getDouble("nitrousOxide"),
				results.getDouble("pethidine"),
				results.getDouble("epiduralAnaesthesia"),
				results.getDouble("magnesiumSulfate"),
				results.getDouble("rhImmuneGlobulin"));
		return data;
	}
	
	/**
	 * 
	 * @param conn
	 * @param ps
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement loadInsertParameters(Connection conn, PreparedStatement ps, ObstetricsChildbirthVisit data) throws SQLException{
		String stmt = "";
		stmt = "INSERT INTO childBirthVisit (MID, hosVisitDate, preSchedule, delivaryPreferred, pitocin, nitrousOxide,"
				+ " pethidine, epiduralAnaesthesia, magnesiumSulfate, rhImmuneGlobulin)" 
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, data.getMID());
		ps.setString(2, data.getVisitDate().toString());
		ps.setBoolean(3, data.isPreScheduled());
		ps.setString(4, data.getPreferredDelivery());
		ps.setDouble(5, data.getPitocin());
		ps.setDouble(6, data.getNitrousOxide());
		ps.setDouble(7, data.getPethidine());
		ps.setDouble(8, data.getEpiduralAnaesthesia());
		ps.setDouble(9, data.getMagnesiumSulfate());
		ps.setDouble(10, data.getRhImmuneGlobulin());
		
		return ps;
	}
	
	/**
	 * 
	 * @param conn
	 * @param ps
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement loadUpdateParameters(Connection conn, PreparedStatement ps, ObstetricsChildbirthVisit data) throws SQLException{
		long mid = data.getMID();
		String date = data.getVisitDate().toString();
		String stmt = "";
		stmt = "UPDATE childBirthVisit SET preSchedule=?," 
				+ "delivaryPreferred=?," 
				+ "pitocin=?," 
				+ "nitrousOxide=?,"
				+ "pethidine=?,"
				+ "epiduralAnaesthesia=?,"
				+ "magnesiumSulfate=?,"
				+ "rhImmuneGlobulin=? "
				+ "WHERE MID=" + mid + " AND hosVisitDate='" + date + "';";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setBoolean(1, data.isPreScheduled());
		ps.setString(2, data.getPreferredDelivery());
		ps.setDouble(3, data.getPitocin());
		ps.setDouble(4, data.getNitrousOxide());
		ps.setDouble(5, data.getPethidine());
		ps.setDouble(6, data.getEpiduralAnaesthesia());
		ps.setDouble(7, data.getMagnesiumSulfate());
		ps.setDouble(8, data.getRhImmuneGlobulin());
		return ps;
	
	}

	public List<ObstetricsChildbirthVisit> loadList(ResultSet rs) throws SQLException{
		ArrayList<ObstetricsChildbirthVisit> list = new ArrayList<ObstetricsChildbirthVisit>();
		rs.beforeFirst();
		while (rs.next()){
			list.add(loadData(rs));
		}
		return list;
		
	}
}