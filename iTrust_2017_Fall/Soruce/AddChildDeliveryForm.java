/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author pkhanal
 *
 */
@ManagedBean (name="addChildDeliveryForm")
@SessionScoped
public class AddChildDeliveryForm extends iTrustController {

	private LocalDateTime visitDate;
	private ChildDeliveryMySQL database;
	private  ChildDelivery data;
	private SessionUtils session;
	private ObstetricsChildbirthVisit viewInfo;
	/* The MID of the patient associated with this office visit. */
	private long MID;
	/* The date that this office visit took place on. */
	private LocalDateTime deliveryDate;
	/* The actual delivery method for this baby. */
	private String deliveryType;
	/* Sex of the baby. */
	private String sex;
	/* Keeps track of which baby this is in the pregnancy. */
	private int whichBaby;
	/* Tells whether the time of delivery was estimated or not. */
	private boolean estTime;
	private long logidInMid;
	public AddChildDeliveryForm(){
		try{
			database = new ChildDeliveryMySQL();
			session = SessionUtils.getInstance();
			MID = session.getCurrentPatientMIDLong();
			logidInMid = session.getSessionLoggedInMIDLong();
		} catch (DBException e1){
			e1.printStackTrace();
		}
	}
	
	public AddChildDeliveryForm(DataSource ds) {
		database = new ChildDeliveryMySQL(ds);
		MID = 0;
		logidInMid = 0;
	}
	
	public long getMID() {
		return MID;
	}
	
	public void setMID(long mID) {
		MID = mID;
	}
	
	public String getDeliveryType() {
		return deliveryType;
	}
	
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public int getWhichBaby() {
		return whichBaby;
	}
	
	public void setWhichBaby(int whichBaby) {
		this.whichBaby = whichBaby;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public boolean isEstTime() {
		return estTime;
	}

	public void setEstTime(boolean estTime) {
		this.estTime = estTime;
	}
	
	
	
	public String addChildDelivery(){
		data = new ChildDelivery(MID, deliveryDate, deliveryType, sex, whichBaby, estTime);
		List<ChildDelivery> deliveryList = Collections.emptyList();
		try {
			
			deliveryList = database.getChildDeliveryList(data.getMID());
			boolean found = false;
			for(int i = 0; i < deliveryList.size(); i++){
				if((data.getDeliveryDate().toLocalDate().equals(deliveryList.get(i).getDeliveryDate().toLocalDate())) &&
						(data.getWhichBaby() == deliveryList.get(i).getWhichBaby())){
					found = true;
					break;
				}
			}
			
			if(found){
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage message = new FacesMessage("There already exist a record for this baby.");
	        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        	context.addMessage(null, message);
	        	return "";
			} else {
				
				if(data.getDeliveryDate().toLocalDate().equals(visitDate.toLocalDate())){
					database.insertChildDelivery(data);
					logTransaction(TransactionType.A_BABY_IS_BORN, "");
					try {
						
						logTransaction(TransactionType.CREATE_BABY_RECORD, "" + addChildAsPatient(data.getMID(), logidInMid, data.getSex()));
					} catch (FormValidationException | ITrustException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "childbirthVisit.xhtml?face-redirect=true";
				} else {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage message = new FacesMessage("The delivery date should be same as the child birth visit date.");
		        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
		        	context.addMessage(null, message);
		        	return"";
				}
			}
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @return the visitDate
	 */
	public LocalDateTime getVisitDate() {
		return visitDate;
	}

	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(LocalDateTime visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * @return the viewInfo
	 */
	public ObstetricsChildbirthVisit getViewInfo() {
		return viewInfo;
	}

	/**
	 * @param viewInfo the viewInfo to set
	 */
	public void setViewInfo(ObstetricsChildbirthVisit viewInfo) {
		this.viewInfo = viewInfo;
	}

	public long addChildAsPatient(long mid, long logedInMid, String gender) throws FormValidationException, ITrustException{
		long newMid = 0;
		try{
			PatientBean parentInfo = DAOFactory.getProductionInstance().getPatientDAO().getPatient(mid);
			PatientBean patient = new PatientBean();
			patient.setFirstName("Earl");
			patient.setLastName(parentInfo.getLastName());
			patient.setGenderStr(gender);
			patient.setEmail(parentInfo.getEmail());
			patient.setDateOfBirthStr(this.deliveryDate.toLocalDate().toString());
			
			newMid = new AddPatientAction(DAOFactory.getProductionInstance(), logedInMid).addDependentPatient(patient, mid, logedInMid);
		} catch (Exception e1){
			if(newMid == 0){
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage message = new FacesMessage("The Baby could not be added as a patient.");
	        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        	context.addMessage(null, message);
	        	return -1;
			}
			e1.printStackTrace();
		}
		return newMid;
	}
	
	 public String outcome3(ObstetricsChildbirthVisit cbov, LocalDateTime date) {
	    	this.viewInfo = cbov;
	    	this.visitDate = date;
	    	System.out.println(date.toString()+"***************************************");
	    	return "addChildDelivery.xhtml?faces-redirect=true";
	    }
}
