/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author pkhanal
 *
 */
public class UltrasoundRecord {

	/* The MID of the patient associated with this office visit. */
	private long MID;
	/* The date that this office visit took place on. */
	private LocalDate offVisitDate;
	private int whichBaby;
	private float crownRumpLength;
	private float biparietalDiameter;
	private float headCircumference;
	private float femurLength;
	private float occipitofrontalDiameter;
	private float abdominalCircumference;
	private float humerusLength;
	private float estimatedFetalWeight;
	private String filetype;
	private long officeVisitID;
	
	private InputStream ultraImage;
	
	

	/**
	 * @param mID
	 * @param offVisitDate
	 * @param whichBaby
	 * @param crownRumpLength
	 * @param biparietalDiameter
	 * @param headCircumference
	 * @param femurLength
	 * @param occipitofrontalDiameter
	 * @param abdominalCircumference
	 * @param humerusLength
	 * @param estimatedFetalWeight
	 */
	public UltrasoundRecord(long mID, LocalDate offVisitDate, int whichBaby, float crownRumpLength,
			float biparietalDiameter, float headCircumference, float femurLength, float occipitofrontalDiameter,
			float abdominalCircumference, float humerusLength, float estimatedFetalWeight) {
		
		MID = mID;
		this.offVisitDate = offVisitDate;
		this.whichBaby = whichBaby;
		this.crownRumpLength = crownRumpLength;
		this.biparietalDiameter = biparietalDiameter;
		this.headCircumference = headCircumference;
		this.femurLength = femurLength;
		this.occipitofrontalDiameter = occipitofrontalDiameter;
		this.abdominalCircumference = abdominalCircumference;
		this.humerusLength = humerusLength;
		this.estimatedFetalWeight = estimatedFetalWeight;
	}
	
	/**
	 * @param mID
	 * @param whichBaby
	 * @param crownRumpLength
	 * @param biparietalDiameter
	 * @param headCircumference
	 * @param femurLength
	 * @param occipitofrontalDiameter
	 * @param abdominalCircumference
	 * @param humerusLength
	 * @param estimatedFetalWeight
	 */
	public UltrasoundRecord(long mID, int whichBaby, float crownRumpLength,
			float biparietalDiameter, float headCircumference, float femurLength, float occipitofrontalDiameter,
			float abdominalCircumference, float humerusLength, float estimatedFetalWeight) {
		this(mID, LocalDate.now(), whichBaby, crownRumpLength, biparietalDiameter, headCircumference, femurLength,
				occipitofrontalDiameter, abdominalCircumference, humerusLength, estimatedFetalWeight);
	}
	
	
	
	/**
	 * @param mID
	 * @param offVisitDate
	 * @param whichBaby
	 * @param crownRumpLength
	 * @param biparietalDiameter
	 * @param headCircumference
	 * @param femurLength
	 * @param occipitofrontalDiameter
	 * @param abdominalCircumference
	 * @param humerusLength
	 * @param estimatedFetalWeight
	 */
	public UltrasoundRecord(long mID, LocalDate offVisitDate, int whichBaby, float crownRumpLength,
			float biparietalDiameter, float headCircumference, float femurLength, float occipitofrontalDiameter,
			float abdominalCircumference, float humerusLength, float estimatedFetalWeight, InputStream image, String filetype) {
		
		MID = mID;
		this.offVisitDate = offVisitDate;
		this.whichBaby = whichBaby;
		this.crownRumpLength = crownRumpLength;
		this.biparietalDiameter = biparietalDiameter;
		this.headCircumference = headCircumference;
		this.femurLength = femurLength;
		this.occipitofrontalDiameter = occipitofrontalDiameter;
		this.abdominalCircumference = abdominalCircumference;
		this.humerusLength = humerusLength;
		this.estimatedFetalWeight = estimatedFetalWeight;
		this.ultraImage = image;
		this.filetype = filetype;
	}
	
	
	
	/**
	 * @return the filetype
	 */
	public String getFiletype() {
		return filetype;
	}

	/**
	 * @param filetype the filetype to set
	 */
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	/**
	 * @return the officeVisitID
	 */
	public long getOfficeVisitID() {
		return officeVisitID;
	}

	/**
	 * @param officeVisitID the officeVisitID to set
	 */
	public void setOfficeVisitID(long officeVisitID) {
		this.officeVisitID = officeVisitID;
	}

	public InputStream getUltraImage() {
		return ultraImage;
	}

	public void setUltraImage(InputStream ultraImage) {
		this.ultraImage = ultraImage;
	}
	
	/**
	 * @return the mID
	 */
	public long getMID() {
		return MID;
	}
	/**
	 * @param mID the mID to set
	 */
	public void setMID(long mID) {
		MID = mID;
	}
	/**
	 * @return the offVisitDate
	 */
	public LocalDate getOffVisitDate() {
		return offVisitDate;
	}
	/**
	 * @param offVisitDate the offVisitDate to set
	 */
	public void setOffVisitDate(LocalDate offVisitDate) {
		this.offVisitDate = offVisitDate;
	}
	/**
	 * @return the whichBaby
	 */
	public int getWhichBaby() {
		return whichBaby;
	}
	/**
	 * @param whichBaby the whichBaby to set
	 */
	public void setWhichBaby(int whichBaby) {
		this.whichBaby = whichBaby;
	}
	/**
	 * @return the crownRumpLength
	 */
	public float getCrownRumpLength() {
		return crownRumpLength;
	}
	/**
	 * @param crownRumpLength the crownRumpLength to set
	 */
	public void setCrownRumpLength(float crownRumpLength) {
		this.crownRumpLength = crownRumpLength;
	}
	/**
	 * @return the biparietalDiameter
	 */
	public float getBiparietalDiameter() {
		return biparietalDiameter;
	}
	/**
	 * @param biparietalDiameter the biparietalDiameter to set
	 */
	public void setBiparietalDiameter(float biparietalDiameter) {
		this.biparietalDiameter = biparietalDiameter;
	}
	/**
	 * @return the headCircumference
	 */
	public float getHeadCircumference() {
		return headCircumference;
	}
	/**
	 * @param headCircumference the headCircumference to set
	 */
	public void setHeadCircumference(float headCircumference) {
		this.headCircumference = headCircumference;
	}
	/**
	 * @return the femurLength
	 */
	public float getFemurLength() {
		return femurLength;
	}
	/**
	 * @param femurLength the femurLength to set
	 */
	public void setFemurLength(float femurLength) {
		this.femurLength = femurLength;
	}
	/**
	 * @return the occipitofrontalDiameter
	 */
	public float getOccipitofrontalDiameter() {
		return occipitofrontalDiameter;
	}
	/**
	 * @param occipitofrontalDiameter the occipitofrontalDiameter to set
	 */
	public void setOccipitofrontalDiameter(float occipitofrontalDiameter) {
		this.occipitofrontalDiameter = occipitofrontalDiameter;
	}
	/**
	 * @return the abdominalCircumference
	 */
	public float getAbdominalCircumference() {
		return abdominalCircumference;
	}
	/**
	 * @param abdominalCircumference the abdominalCircumference to set
	 */
	public void setAbdominalCircumference(float abdominalCircumference) {
		this.abdominalCircumference = abdominalCircumference;
	}
	/**
	 * @return the humerusLength
	 */
	public float getHumerusLength() {
		return humerusLength;
	}
	/**
	 * @param humerusLength the humerusLength to set
	 */
	public void setHumerusLength(float humerusLength) {
		this.humerusLength = humerusLength;
	}
	/**
	 * @return the estimatedFetalWeight
	 */
	public float getEstimatedFetalWeight() {
		return estimatedFetalWeight;
	}
	/**
	 * @param estimatedFetalWeight the estimatedFetalWeight to set
	 */
	public void setEstimatedFetalWeight(float estimatedFetalWeight) {
		this.estimatedFetalWeight = estimatedFetalWeight;
	}
}
