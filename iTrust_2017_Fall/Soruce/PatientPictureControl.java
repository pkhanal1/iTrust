/**
 *  To complete this part, I used the primeface. I also used the site to help me to code this. 
 *  the site is: http://www.itcuties.com/j2ee/jsf-2-read-and-write-images-from-sql-database/
 */
package edu.ncsu.csc.itrust.model.patientPicture;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import edu.ncsu.csc.itrust.exception.DBException;

/**
 * @author pkhanal
 *
 */
@ManagedBean
@RequestScoped
public class PatientPictureControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientPictureMySQL database;
	
	private StreamedContent image = null;
	
	
	/**
	 * @param image the image to set
	 */
	public void setImage(StreamedContent image) {
		this.image = image;
	}


	public StreamedContent getImage() throws NumberFormatException, DBException{
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			
	        return new DefaultStreamedContent();
	    } else {
	    	String mid = context.getExternalContext().getRequestParameterMap().get("pid");
	    	database = new PatientPictureMySQL();
	    	PatientPicture patImg = database.getPatientPicture(Long.valueOf(mid));
	    	return new DefaultStreamedContent(patImg.getPatientPicture());
	    }
	}
	
}
