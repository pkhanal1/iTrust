/**
 *  To complete this part, I used the primeface. I also used the site to help me to code this. 
 *  the site is: http://www.itcuties.com/j2ee/jsf-2-read-and-write-images-from-sql-database/
 */
package edu.ncsu.csc.itrust.model.patientPicture;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.primefaces.model.UploadedFile;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author pkhanal
 *
 */
@ManagedBean
public class PatientPictureBean {

	private UploadedFile picture;

	private PatientPicture pic;
	private PatientPictureMySQL database;
	private SessionUtils session;
	private long MID;
	
	public PatientPictureBean(){
		try {
			database = new PatientPictureMySQL();
			session = SessionUtils.getInstance();
			if(session.getCurrentPatientMIDLong() != null){
				MID = session.getCurrentPatientMIDLong();
			}
			
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	public PatientPictureBean(DataSource ds, long mid){
		database = new PatientPictureMySQL(ds);
		MID = mid;
	}
	
	/**
	 * @return the picture
	 */
	public UploadedFile getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(UploadedFile picture) {
		this.picture = picture;
	}
	
	public String upload() throws IOException{
		FacesContext context = FacesContext.getCurrentInstance();
		if(picture != null){
			String filename = picture.getFileName().toLowerCase();
			if(filename != null && (filename.endsWith(".jpg") || filename.endsWith(".png"))){
				
				pic = new PatientPicture(this.MID, this.picture.getInputstream());
				try {
					if(checkIfPictureExist()){
						database.updatePatientPicture(pic);
					} else {
						database.insertPatientPicture(pic);
					}
				} catch (DBException e1){
					e1.printStackTrace();
				}
				FacesMessage errorMessage = new FacesMessage("A picture has been uploaded.");
				errorMessage.setSeverity(FacesMessage.SEVERITY_INFO);
				context.addMessage(null, errorMessage);
			} else {
				FacesMessage errorMessage = new FacesMessage("Please upload a valid file.(.jpg/.png files only.)");
				errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, errorMessage);
			}
		}
		return "";
	}
	
	public boolean checkIfPictureExist(){
		return checkingPicture(session.getCurrentPatientMID());	
	}
	
	public boolean checkingPicture(String mid){
		boolean ret = false;
		if ((mid != null) && (ValidationFormat.NPMID.getRegex().matcher(mid).matches())) {
			try{
				long tmpMid = Long.parseLong(mid);
				PatientPicture checking = database.getPatientPicture(tmpMid);
				if(checking != null){
					ret = true;
				}
			} catch (DBException e){
				e.printStackTrace();
			}
		}
		return ret;	
	}
}
