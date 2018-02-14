/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsChildbirthVisit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit.ObstetricsChildBirthVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit.ObstetricsChildbirthVisit;

/**
 * @author pkhanal
 *
 */
public class ObstetricsChildBirthVisitMySQLTest {

	private DataSource ds;
	private ObstetricsChildBirthVisitMySQL database;
	private ObstetricsChildbirthVisit data, testData, newData, badData;
	private List<ObstetricsChildbirthVisit> list, listBadMid;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		database = new ObstetricsChildBirthVisitMySQL(ds);
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit.ObstetricsChildBirthVisitMySQL#ObstetricsChildBirthVisitMySQL()}.
	 */
	@Test
	public void testObstetricsChildBirthVisitMySQL() {
		
		try {
			data = database.getChildBirthVisit(1, LocalDateTime.now().toString());
			badData = database.getChildBirthVisit(-1, LocalDateTime.now().toString());
			list = database.getChildBirthVisitList(1);
			
		}catch (DBException e1){
			e1.printStackTrace();
		} finally {
			/* if the database is empty then initialize the Obstetric office visit */
			if (data == null) {
				data = new ObstetricsChildbirthVisit(1, LocalDateTime.now(), false, "vaginal", 1, 1, 1, 1, 1, 1);
				badData = new ObstetricsChildbirthVisit(1, LocalDateTime.now(), false, "vaginal", -1, 1, 1, 1, 1, 1);
			}
		}

		
		//assertTrue(list.isEmpty());
		LocalDateTime dateTest = data.getVisitDate();
		
		
		boolean added = false;
		boolean addedBad = false;
		try {
			/* add the office visit into the database */
			added = database.insertChildBirthVisit(data);
			addedBad = database.insertChildBirthVisit(badData);
		} catch (DBException e1){
			e1.printStackTrace();
		}
		assertEquals(true, added);
		assertEquals(false, addedBad);
		
		try {
			/* get the office visit from the database */
			testData = database.getChildBirthVisit(1, data.getVisitDate().toString());
		} catch (DBException e1){
			e1.printStackTrace();
		}
		
		assertEquals(1, testData.getMID());
		assertEquals(dateTest.toString(), testData.getVisitDate().toString());
		assertEquals(false, testData.isPreScheduled());
		assertEquals("vaginal", testData.getPreferredDelivery());
		assertEquals((int)1, (int)testData.getPitocin());
		assertEquals((int)1, (int)testData.getNitrousOxide());
		assertEquals((int)1, (int)testData.getPethidine());
		assertEquals((int)1, (int)testData.getEpiduralAnaesthesia());
		assertEquals((int)1, (int)testData.getMagnesiumSulfate());
		
		
		
		
		testData.setMID(1);
		testData.setVisitDate(dateTest);
		testData.setPreScheduled(true);
		testData.setPreferredDelivery("miscarriage");
		testData.setPitocin(2);
		testData.setNitrousOxide(2);
		testData.setPethidine(2);
		testData.setEpiduralAnaesthesia(2);
		testData.setMagnesiumSulfate(2);
		testData.setRhImmuneGlobulin(2);
		
		try{
			/** update the office visit values into database */ 
			added = database.updateChildBirthVisit(testData);
			addedBad = database.updateChildBirthVisit(badData);
			newData = database.getChildBirthVisit(1, data.getVisitDate().toString());
		} catch (DBException e1){
			e1.printStackTrace();
		}
		assertEquals(false, addedBad);
		assertEquals(1, newData.getMID());
		assertEquals(dateTest.toString(), newData.getVisitDate().toString());
		assertEquals(true, newData.isPreScheduled());
		assertEquals("miscarriage", newData.getPreferredDelivery());
		assertEquals((int)2, (int)newData.getPitocin());
		assertEquals((int)2, (int)newData.getNitrousOxide());
		assertEquals((int)2, (int)newData.getPethidine());
		assertEquals((int)2, (int)newData.getEpiduralAnaesthesia());
		assertEquals((int)2, (int)newData.getMagnesiumSulfate());
	
	
		try{
			LocalDateTime da = LocalDateTime.parse("2016-04-02T14:40:20.759");
			newData.setVisitDate(da);
			database.insertChildBirthVisit(newData);
			list = database.getChildBirthVisitList(1);
			listBadMid = database.getChildBirthVisitList(-1);
			
		} catch (DBException e1){
			e1.printStackTrace();
		}
		
		assertTrue(listBadMid == null);
	}
}
