/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.primefaces.model.UploadedFile;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author pkhanal
 *
 */
@ManagedBean
@SessionScoped
public class AddUltrasoundBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Part image;
	private UploadedFile image;
	private InputStream upFile;
	
	
	/* The MID of the patient associated with this office visit. */
	private long MID;
	/* The date that this office visit took place on. */
	private LocalDate offVisitDate;
	private int whichBaby;
	private double crownRumpLength;
	private double biparietalDiameter;
	private double headCircumference;
	private double femurLength;
	private double occipitofrontalDiameter;
	private double abdominalCircumference;
	private double humerusLength;
	private double estimatedFetalWeight;
	private String filetype;
	
	private UltrasoundRecord record;
	private UltrasoundRecordMySQL database;
	private SessionUtils session;
	private ObstetricOfficeVisit offVisit;
	
	public AddUltrasoundBean(){
		try{
			database = new UltrasoundRecordMySQL();
			session = SessionUtils.getInstance();
			//this.offVisitDate = this.offVisit.getVisitDate().toLocalDate();
			MID = session.getCurrentPatientMIDLong();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	public AddUltrasoundBean(DataSource ds) {
		database = new UltrasoundRecordMySQL(ds);
		MID = 0;
	}
	
	public InputStream getUpFile(){
		return this.upFile;
	}
	public void setUpFile(InputStream strem){
		this.upFile = strem;
		
	}
   /* 
	public Part getImage(){
		return image;
	}
	
	public void setImage(Part image) {
		this.image = image;
	}
	*/
	 public UploadedFile getImage() {
        return image;
    }
 
    public void setImage(UploadedFile image) {
        this.image = image;
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
	public double getCrownRumpLength() {
		return crownRumpLength;
	}
	/**
	 * @param crownRumpLength the crownRumpLength to set
	 */
	public void setCrownRumpLength(double crownRumpLength) {
		this.crownRumpLength = crownRumpLength;
	}
	/**
	 * @return the biparietalDiameter
	 */
	public double getBiparietalDiameter() {
		return biparietalDiameter;
	}
	/**
	 * @param biparietalDiameter the biparietalDiameter to set
	 */
	public void setBiparietalDiameter(double biparietalDiameter) {
		this.biparietalDiameter = biparietalDiameter;
	}
	/**
	 * @return the headCircumference
	 */
	public double getHeadCircumference() {
		return headCircumference;
	}
	/**
	 * @param headCircumference the headCircumference to set
	 */
	public void setHeadCircumference(double headCircumference) {
		this.headCircumference = headCircumference;
	}
	/**
	 * @return the femurLength
	 */
	public double getFemurLength() {
		return femurLength;
	}
	/**
	 * @param femurLength the femurLength to set
	 */
	public void setFemurLength(double femurLength) {
		this.femurLength = femurLength;
	}
	/**
	 * @return the occipitofrontalDiameter
	 */
	public double getOccipitofrontalDiameter() {
		return occipitofrontalDiameter;
	}
	/**
	 * @param occipitofrontalDiameter the occipitofrontalDiameter to set
	 */
	public void setOccipitofrontalDiameter(double occipitofrontalDiameter) {
		this.occipitofrontalDiameter = occipitofrontalDiameter;
	}
	/**
	 * @return the abdominalCircumference
	 */
	public double getAbdominalCircumference() {
		return abdominalCircumference;
	}
	/**
	 * @param abdominalCircumference the abdominalCircumference to set
	 */
	public void setAbdominalCircumference(double abdominalCircumference) {
		this.abdominalCircumference = abdominalCircumference;
	}
	/**
	 * @return the humerusLength
	 */
	public double getHumerusLength() {
		return humerusLength;
	}
	/**
	 * @param humerusLength the humerusLength to set
	 */
	public void setHumerusLength(double humerusLength) {
		this.humerusLength = humerusLength;
	}
	/**
	 * @return the estimatedFetalWeight
	 */
	public double getEstimatedFetalWeight() {
		return estimatedFetalWeight;
	}
	/**
	 * @param estimatedFetalWeight the estimatedFetalWeight to set
	 */
	public void setEstimatedFetalWeight(double estimatedFetalWeight) {
		this.estimatedFetalWeight = estimatedFetalWeight;
	}
	
	public ObstetricOfficeVisit getOffVisit(){
		return this.offVisit;
	}
	
	public void setOffVisit(ObstetricOfficeVisit obst){
		this.offVisit = obst;
	}
	
	public void uploadForWebPage(){
		FacesContext context = FacesContext.getCurrentInstance();
		if(image != null){
			try {
				
				if(image.getFileName().endsWith(".jpg") || image.getFileName().endsWith(".png") || image.getFileName().endsWith(".pdf")){
					this.upFile = image.getInputstream();
			    	FacesMessage errorMessage = new FacesMessage("A valid file has been added.");
					errorMessage.setSeverity(FacesMessage.SEVERITY_INFO);
					context.addMessage(null, errorMessage);
				} else {
					FacesMessage errorMessage = new FacesMessage("Please upload a valid file.(.jpg/.pdf/.png files only.)");
        			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        			context.addMessage(null, errorMessage);
        			
				}
				
			} catch (Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
    
    public String addUltraSound(){
    	offVisitDate = this.offVisit.getVisitDate().toLocalDate();
    	if(this.image == null){
    		record = new UltrasoundRecord( MID, offVisitDate, whichBaby, (float)crownRumpLength, (float)biparietalDiameter, (float)headCircumference, (float)femurLength, (float)occipitofrontalDiameter, (float)abdominalCircumference, (float)humerusLength, (float)estimatedFetalWeight, null, "null");
    	} else {
    		record = new UltrasoundRecord( MID, offVisitDate, whichBaby, (float)crownRumpLength, (float)biparietalDiameter, (float)headCircumference, (float)femurLength, (float)occipitofrontalDiameter, (float)abdominalCircumference, (float)humerusLength, (float)estimatedFetalWeight, this.upFile, this.image.getFileName().trim());
    	}
    	
    	try{
    		if(database.getUltrasoundRecord(record.getMID(), record.getOffVisitDate().toString(), record.getWhichBaby()) == null){
    			database.insertUltrasoundRecord(record);
    		} else {
    			FacesContext context = FacesContext.getCurrentInstance();
    			FacesMessage errorMessage = new FacesMessage("There already exist a record for this baby. If you want to edit, go to obstetricOfficeVisit page.");
    			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
    			context.addMessage(null, errorMessage);
    			return"";
    		}
    		
    	} catch (DBException e1){
    		
    		e1.printStackTrace();
    	}
    	return "obstetricsOfficeVisit.xhtml?faces-redirect=true";
    }
    
    public String editUltraSound() {
    	offVisitDate = this.offVisit.getVisitDate().toLocalDate();
    	if(this.image == null){
    		record = new UltrasoundRecord( MID, offVisitDate, whichBaby, (float)crownRumpLength, (float)biparietalDiameter, (float)headCircumference, (float)femurLength, (float)occipitofrontalDiameter, (float)abdominalCircumference, (float)humerusLength, (float)estimatedFetalWeight, null, "null");
    	} else {
    		record = new UltrasoundRecord( MID, offVisitDate, whichBaby, (float)crownRumpLength, (float)biparietalDiameter, (float)headCircumference, (float)femurLength, (float)occipitofrontalDiameter, (float)abdominalCircumference, (float)humerusLength, (float)estimatedFetalWeight, this.upFile, this.image.getFileName().trim());
    	}
    	
    	try{
    		database.updateUltrasoundRecord(record);    		
    	} catch (DBException e1){
    		
    		e1.printStackTrace();
    	}
    	return "obstetricsOfficeVisit.xhtml?faces-redirect=true";
    }
    
    public String outcome2(ObstetricOfficeVisit obst) {
    	this.offVisit = obst;
    	return "addUltrasound.xhtml?faces-redirect=true";
    }
   
}
