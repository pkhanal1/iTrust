package edu.ncsu.csc.itrust.unit.model.patientPicture;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.patientPicture.PatientPicture;
import edu.ncsu.csc.itrust.model.patientPicture.PatientPictureMySQL;
/**
 * @author pkhanal
 *
 */
public class PatientPictureTest {

	private PatientPicture pic, testPic, testUploadPic, tmp;
	private InputStream is, testIs;
	
	private PatientPictureMySQL database;
	private DataSource ds;
	
	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		database = new PatientPictureMySQL(ds);
		is = new FileInputStream(new File("WebRoot/image/icons/profile.png"));
		testIs = new FileInputStream(new File("WebRoot/image/icons/survey.png"));
		
		
		
	}

	@Test
	public void test() throws IOException {
		
		try{
			pic = database.getPatientPicture(1); 
		} catch (DBException e){
			e.printStackTrace();
		} 
		if(pic == null){
			pic = new PatientPicture(1, is);
		}
		
		
		assertEquals(1, (int) pic.getMid());
		//assertEquals(this.is, pic.getPatientPicture());
		
		boolean added = false;
		try {
			if((tmp = database.getPatientPicture(1)) == null){
			added = database.insertPatientPicture(pic);	
			}
		} catch (DBException e){
			e.printStackTrace();
		}
		if(tmp == null){
			assertEquals(true, added);
		}
		
		try {
			testPic = database.getPatientPicture(1);
		} catch (DBException e){
			e.printStackTrace();
		}
		
		
		assertEquals((int) testPic.getMid(), 1);
		//assertEquals(inp, testPic.getPatientPicture());
		
		boolean uploaded = false;
		testPic.setPatientPicture(testIs);
		try {
			uploaded = database.updatePatientPicture(testPic);
			testUploadPic = database.getPatientPicture(1);
		} catch (DBException e){
			e.printStackTrace();
		}
		
		assertEquals(true, uploaded);
		assertEquals(1, (int) testUploadPic.getMid());
		testUploadPic.setMid(2);
		try{
			database.insertPatientPicture(testUploadPic);
		} catch (DBException e){
			e.printStackTrace();
		}
		//assertEquals(this.testIs, testPic.getPatientPicture());
	}

}
