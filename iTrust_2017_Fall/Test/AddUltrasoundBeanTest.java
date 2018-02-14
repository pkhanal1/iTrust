/**
 * 
 */
package edu.ncsu.csc.itrust.unit.model.obstetricsOfficeVisit;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.AddUltrasoundBean;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.UltrasoundRecord;
import edu.ncsu.csc.itrust.webutils.LocalDateConverter;

/**
 * @author pkhanal
 *
 */
public class AddUltrasoundBeanTest {

	private AddUltrasoundBean test = new AddUltrasoundBean(ConverterDAO.getDataSource());

	@Test
	public void test() {
		
		test.setMID(1);
		LocalDate dt = LocalDate.now();
		test.setOffVisitDate(dt);
		test.setWhichBaby(1);
		test.setAbdominalCircumference(1);
		test.setCrownRumpLength(1);
		test.setBiparietalDiameter(1);
		test.setHeadCircumference(1);
		test.setFemurLength(1);
		test.setOccipitofrontalDiameter(1);
		test.setHumerusLength(1);
		test.setEstimatedFetalWeight(1);
		test.setFiletype("jpg");
		
		assertEquals(1, test.getMID());
		assertEquals(dt, test.getOffVisitDate());
		assertEquals((int) 1, test.getWhichBaby());
		assertEquals((int) 1, (int)test.getCrownRumpLength());
		assertEquals((int) 1, (int)test.getBiparietalDiameter());
		assertEquals((int) 1, (int)test.getHeadCircumference());
		assertEquals((int) 1, (int)test.getFemurLength());
		assertEquals((int) 1, (int)test.getOccipitofrontalDiameter());
		assertEquals((int) 1, (int)test.getAbdominalCircumference());
		assertEquals((int) 1, (int)test.getHumerusLength());
		assertEquals((int) 1, (int)test.getEstimatedFetalWeight());
		assertEquals("jpg", test.getFiletype());

		
	}

}
