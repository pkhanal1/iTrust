/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

/**
 * @author pkhanal
 *
 */
public class UltrasoundRecordValidator {

	public boolean validateUltrasoundRecord(UltrasoundRecord data){
		if (data.getMID() < 0 || data.getOffVisitDate() == null ||
				data.getWhichBaby() <= 0 || data.getAbdominalCircumference() <= 0.0 ||
				data.getBiparietalDiameter() <= 0.0 || data.getCrownRumpLength() <= 0.0 ||
				data.getEstimatedFetalWeight() <= 0.0 || data.getFemurLength() <= 0.0 ||
				data.getHeadCircumference() <= 0.0 || data.getHumerusLength() <= 0.0 ||
				data.getOccipitofrontalDiameter() <= 0.0) {
				return false;
			}
			return true;
	}
}
