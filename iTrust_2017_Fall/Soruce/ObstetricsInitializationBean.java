/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetrics;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author pkhanal
 *
 */

@ManagedBean
@SessionScoped
public class ObstetricsInitializationBean extends iTrustController {

	//private long mid = -1;
	
	private DAOFactory factory;
	private SessionUtils midFromWeb;
	private PatientInitializationMySQL database;
	private PriorPregnancyMySQL database2;
	private ObstetricsInfo viewInfo;
	public ObstetricsInitializationBean(){
		midFromWeb = SessionUtils.getInstance();
		factory = DAOFactory.getProductionInstance();
		try {
			database = new PatientInitializationMySQL();
			database2 = new PriorPregnancyMySQL();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ObstetricsInitializationBean(DataSource ds) {
		database = new PatientInitializationMySQL(ds);
		database2 = new PriorPregnancyMySQL(ds);
	}
	
	public boolean isObstetricsPatient() {
		//return DAOFactory.getProductionInstance().getPatientDAO().getPatient(Long.parseLong(midFromWeb.getCurrentPatientMID())).getObstetsEligible();
		return checkPatientObstetrics(midFromWeb.getCurrentPatientMID());
	}
	
	public boolean checkPatientObstetrics(String pid){
		boolean ret = false;
		long tempMid = -1;
		if((pid != null) && (ValidationFormat.NPMID.getRegex().matcher(pid).matches())){
			tempMid = Long.parseLong(pid);
			try {
				//String gender = DAOFactory.getProductionInstance().getPatientDAO().getPatient(tempMid).getGender().toString();
				boolean obsentric = DAOFactory.getProductionInstance().getPatientDAO().getPatient(tempMid).getObstetsEligible();
				if(obsentric){
					ret = true;
				}
			} catch (Exception e) {
				
			}
		}
		return ret;
	}
	

	public List<ObstetricsInfo> getObstetricsForCurrentPatient(){
		return getObstetricsForPatient(midFromWeb.getCurrentPatientMID());
		
	}
	
	public List<ObstetricsInfo> getObstetricsForPatient(String pid){
		List<ObstetricsInfo> d = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				d = database.getObstetricsInfoList(mid).stream().sorted((o1, o2) -> {
					return o2.getInitializationDate().compareTo(o1.getInitializationDate());
				}).collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return d;
		
	}
	
	public List<PriorPregnancy> getPregnanciesForPatient(String pid) {
		List<PriorPregnancy> d = Collections.emptyList();
		long mid = -1;
		if ((pid != null) && ValidationFormat.NPMID.getRegex().matcher(pid).matches()) {
			mid = Long.parseLong(pid);
			try {
				d = database2.getAllPregnancies(mid).stream().sorted((o1, o2) -> {
					return o2.getYearOfConcept().compareTo(o2.getYearOfConcept());
				}).collect(Collectors.toList());
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < d.size(); i++) {
			if (Integer.parseInt(d.get(i).getYearOfConcept()) >= viewInfo.getInitializationDate().getYear()) {
				d.remove(d.get(i));
			}
		}
		
		return d;
	}
	
	
	public void makePatientObstetricsButton(){
		makePatientObstetrics(midFromWeb.getCurrentPatientMID(), midFromWeb.getSessionLoggedInMID());
	}
	
	
	public void makePatientObstetrics(String pid, String hcpid){
		long tempMid = -1;
		//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$I was here");
		if((pid != null) && (ValidationFormat.NPMID.getRegex().matcher(pid).matches())){
			tempMid = Long.parseLong(pid);
			//System.out.println(tempMid);
			try {
				PatientBean patient = factory.getPatientDAO().getPatient(tempMid);
				patient.setObstetsEligible(true);
				if((hcpid != null) && (ValidationFormat.HCPMID.getRegex().matcher(hcpid).matches())){
					//System.out.println(hcpid);
					factory.getPatientDAO().editPatient(patient, Long.parseLong(hcpid));
				}
				
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void makePatientObstetrics(String pid, String hcpid, DAOFactory factory) {
		this.factory = factory;
		this.makePatientObstetrics(pid, hcpid);
	}
	
	/**
	 * @param patientID
	 *            Patient MID
	 * @return true if selected patient MID has at least 1 office visit, false
	 *         otherwise
	 */
	public boolean hasPatientObstetrics(String patientID) {
		boolean ret = false;
		if ((patientID != null) && (ValidationFormat.NPMID.getRegex().matcher(patientID).matches())) {
			if (getObstetricsForPatient(patientID).size() > 0) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @return true if patient selected in HCP session has at least 1 office
	 *         visit, false if otherwise
	 */
	public boolean currentPatientHasObstetricsList() {
		return hasPatientObstetrics(midFromWeb.getCurrentPatientMID());
	}
	
	public boolean validOBGYNhcp(){
		return checkIfValidOBGYNhcp(midFromWeb.getSessionLoggedInMID());
		
	}

	public boolean checkIfValidOBGYNhcp(String hcpID){
		boolean ret = false;
		long tempMid = -1;
		if((hcpID != null) && (ValidationFormat.HCPMID.getRegex().matcher(hcpID).matches())){
			tempMid = Long.parseLong(hcpID);
			try {
				//String gender = DAOFactory.getProductionInstance().getPatientDAO().getPatient(tempMid).getGender().toString();
				 
				if(DAOFactory.getProductionInstance().getPersonnelDAO().getPersonnel(tempMid).getSpecialty().equalsIgnoreCase("OB/GYN")){
					ret = true;
				}
			} catch (Exception e) {
				
			}
		}
		return ret;
		
	}
	
	public String outcome(ObstetricsInfo obst) {
		this.viewInfo = obst;
		logTransaction(TransactionType.VIEW_INITIAL_OBSTETRIC_RECORD, viewInfo.getEDD().toString());
		return "viewObstetricsInfo";
	}
	
	public String outcome2(ObstetricsInfo obst) {
		this.viewInfo = obst;
		return "viewChildbirthVisit";
	}

	public ObstetricsInfo getViewInfo() {
		return viewInfo;
	}

	public void setViewInfo(ObstetricsInfo viewInfo) {
		this.viewInfo = viewInfo;
	}
	
}
