package edu.ncsu.csc.itrust.model.patientPicture;

import java.io.InputStream;

/**
 * @author pkhanal
 *
 */
public class PatientPicture {

	private long mid;
	private InputStream patientPicture;
	
	/**
	 * @param mid
	 * @param patientPicture
	 */
	public PatientPicture(long mid, InputStream patientPicture) {
		this.mid = mid;
		this.patientPicture = patientPicture;
	}
	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	/**
	 * @return the patientPicture
	 */
	public InputStream getPatientPicture() {
		return patientPicture;
	}
	/**
	 * @param patientPicture the patientPicture to set
	 */
	public void setPatientPicture(InputStream patientPicture) {
		this.patientPicture = patientPicture;
	}
	
}
