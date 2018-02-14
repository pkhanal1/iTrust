/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsOfficeVisit;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.validate.bean.AssertTrueClientValidationConstraint;
import static org.junit.Assert.*;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricOfficeVisit;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitMySQL;

/**
 * @author pkhanal
 *
 */
public class ObstetricsOfficeVisitMySQLTest {

	private DataSource ds;
	private ObstetricsOfficeVisitMySQL database;
	private ObstetricOfficeVisit data, testData;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		database = new ObstetricsOfficeVisitMySQL(ds);
		//data = new ObstetricOfficeVisit(1, "12-0", 130, "130/90", 20, 1, "Yes");
	}

	@Test
	public void test() {
		//// Makes sure visit is actually initialized.
		//assertNotNull(data);
		/* Try to get something from an empty database.*/
		try {
			data = database.getObstetricsOfficeVisit(1, LocalDateTime.now().toString());
			//database.insertObstetricsOfficeVisit(data);
		} catch (DBException e1){
			e1.printStackTrace();
		} finally {
			/* if the database is empty then initialize the Obstetric office visit */
			if (data == null) {
				data = new ObstetricOfficeVisit(1,LocalDateTime.now(), "12-0", 130, "130/90", 20, 1, "Yes");
			}
		}
		LocalDateTime dateTest = data.getVisitDate();
		
		boolean added = false;
		try{
			/* add the office visit into the database */
			added = database.insertObstetricsOfficeVisit(data);
		} catch (DBException e1){
			e1.printStackTrace();
		}
		
		assertEquals(true, added);
		
		try {
			/* get the office visit from the database */
			testData = database.getObstetricsOfficeVisit(1, data.getVisitDate().toString());
		} catch (DBException e1){
			e1.printStackTrace();
		}
		
		/** check the values from the database to the values that was inserted */
		assertEquals(1, testData.getMID());
		assertEquals(dateTest.toString(), testData.getVisitDate().toString());
		assertEquals("12-0", testData.getWeeksPregnant());
		assertEquals(130, testData.getWeight());
		assertEquals("130/90", testData.getBloodPressure());
		assertEquals(20, testData.getFHR());
		assertEquals(1, testData.getBabies());
		assertEquals("Yes", testData.getLowPlacenta());
		
		/** change the values of the office visit */
		testData.setMID(1);
		testData.setVisitDate(dateTest);
		testData.setWeeksPregnant("13-0");
		testData.setWeight(120);
		testData.setBloodPressure("120/80");
		testData.setFHR(10);
		testData.setBabies(2);
		testData.setLowPlacenta("no");
		
		try{
			/** update the office visit values into database */ 
			added = database.updateObstetricsOfficeVisit(testData);
			testData = database.getObstetricsOfficeVisit(1, data.getVisitDate().toString());
		} catch (DBException e1){
			e1.printStackTrace();
		}
		/** check the uploaded value that you get from the database to edited value */
		assertEquals(1, testData.getMID());
		assertEquals(dateTest.toString(), testData.getVisitDate().toString());
		assertEquals("13-0", testData.getWeeksPregnant());
		assertEquals(120, testData.getWeight());
		assertEquals("120/80", testData.getBloodPressure());
		assertEquals(10, testData.getFHR());
		assertEquals(2, testData.getBabies());
		assertEquals("no", testData.getLowPlacenta());
		
		data.setMID(-1);
		
		try {
			added = database.insertObstetricsOfficeVisit(data);
			assertEquals(false, added);
		} catch (DBException e1){
			e1.printStackTrace();
		}
		
		try {
			added = database.updateObstetricsOfficeVisit(data);
			assertEquals(false, added);
			data.setMID(2);
			added = database.updateObstetricsOfficeVisit(data);
			assertEquals(false, added);
			testData = database.getObstetricsOfficeVisit(-1, data.getVisitDate().toString());
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
