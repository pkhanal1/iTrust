/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsChildbirthVisit;

/**
 * @author pkhanal
 *
 */
public class ObstetricsChildBirthVistValidator {

	public boolean vlidateObstetricsChildBirthVisit(ObstetricsChildbirthVisit data){
		if(data.getMID() <= 0 || data.getPreferredDelivery() == null || data.getPitocin() < 0 ||
				data.getNitrousOxide() < 0 || data.getPethidine() < 0 ||
				data.getEpiduralAnaesthesia() < 0 || data.getMagnesiumSulfate() < 0 || 
				data.getRhImmuneGlobulin() < 0) {
			return false;
		}
		
		return true;
	}
}
