/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsOfficeVisit;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.UltrasoundRecord;

/**
 * @author pkhanal
 *
 */
public class UltrasoundRecordTest {

	private UltrasoundRecord record;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		record = new UltrasoundRecord(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}

	@Test
	public void test() {
		assertNotNull(record);
		
		assertEquals(1, record.getMID());
		//assertEquals(1, record.getOffVisitDate())
		assertEquals((int) 1, (int)record.getCrownRumpLength());
		assertEquals((int) 1, (int)record.getBiparietalDiameter());
		assertEquals((int) 1, (int)record.getHeadCircumference());
		assertEquals((int) 1, (int)record.getFemurLength());
		assertEquals((int) 1, (int)record.getOccipitofrontalDiameter());
		assertEquals((int) 1, (int)record.getAbdominalCircumference());
		assertEquals((int) 1, (int)record.getHumerusLength());
		assertEquals((int) 1, (int)record.getEstimatedFetalWeight());
		
		LocalDate testdate = LocalDate.now();
		record.setMID(2);
		record.setOffVisitDate(testdate);
		record.setCrownRumpLength(2);
		record.setBiparietalDiameter(2);
		record.setHeadCircumference(2);
		record.setFemurLength(2);
		record.setOccipitofrontalDiameter(2);
		record.setAbdominalCircumference(2);
		record.setHumerusLength(2);
		record.setEstimatedFetalWeight(2);

		
		assertEquals(2, record.getMID());
		assertEquals(testdate, record.getOffVisitDate());
		assertEquals((int) 2, (int)record.getCrownRumpLength());
		assertEquals((int) 2, (int)record.getBiparietalDiameter());
		assertEquals((int) 2, (int)record.getHeadCircumference());
		assertEquals((int) 2, (int)record.getFemurLength());
		assertEquals((int) 2, (int)record.getOccipitofrontalDiameter());
		assertEquals((int) 2, (int)record.getAbdominalCircumference());
		assertEquals((int) 2, (int)record.getHumerusLength());
		assertEquals((int) 2, (int)record.getEstimatedFetalWeight());
		
		
	}

}
