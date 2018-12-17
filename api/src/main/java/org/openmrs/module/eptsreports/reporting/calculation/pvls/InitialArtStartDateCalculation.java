package org.openmrs.module.eptsreports.reporting.calculation.pvls;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.eptsreports.reporting.calculation.AbstractPatientCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.EptsCalculations;
import org.openmrs.module.eptsreports.reporting.utils.EptsCalculationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Calculates the date on which a patient first started ART
 * 
 * @return a CulculationResultMap
 */
public class InitialArtStartDateCalculation extends AbstractPatientCalculation {
	
	/**
	 * @should return null for patients who have not started ART
	 * @should return start date for patients who have started ART
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		
		// Get calculation map date for the first program enrollment
		CalculationResultMap map = new CalculationResultMap();
		// only get patients who are alive
		Set<Integer> alivePatients = EptsCalculationUtils.patientsThatPass(EptsCalculations.alive(cohort, context));
		ConceptService conceptService = Context.getConceptService();
		Concept arvPlan = conceptService.getConceptByUuid("e1d9ee10-1d5f-11e0-b929-000c29ad1d07");
		Concept drugStartDate = conceptService.getConceptByUuid("e1d8f690-1d5f-11e0-b929-000c29ad1d07");
		Concept startDrugs = conceptService.getConceptByUuid("e1d9ef28-1d5f-11e0-b929-000c29ad1d07");
		
		EncounterType encounterTypePharmacy = Context.getEncounterService()
		        .getEncounterTypeByUuid("e279133c-1d5f-11e0-b929-000c29ad1d07");
		EncounterType adultoSeguimento = Context.getEncounterService().getEncounterTypeByUuid("e278f956-1d5f-11e0-b929-000c29ad1d07");
		EncounterType arvPaed = Context.getEncounterService().getEncounterTypeByUuid("e278fce4-1d5f-11e0-b929-000c29ad1d07");
		
		CalculationResultMap inProgramMap = calculate(new InHivProgramEnrollmentCalculation(), cohort, context);
		CalculationResultMap startDrugMap = EptsCalculations.firstObs(arvPlan, cohort, context);
		CalculationResultMap historicalMap = EptsCalculations.firstObs(drugStartDate, cohort, context);
		CalculationResultMap pharmacyEncounterMap = EptsCalculations.firstEncounter(encounterTypePharmacy, cohort, context);
		
		for (Integer pId : cohort) {
			Date dateEnrolledIntoProgram = null;
			Date dateStartedDrugs;
			Date historicalDate;
			Date pharmacyDate;
			Date requiredDate = null;
			List<Date> enrollmentDates = new ArrayList<>();
			if (alivePatients.contains(pId)) {
				SimpleResult result = (SimpleResult) inProgramMap.get(pId);
				if (result != null) {
					dateEnrolledIntoProgram = (Date) result.getValue();
					enrollmentDates.add(dateEnrolledIntoProgram);
				}
				Obs startDateObsResults = EptsCalculationUtils.obsResultForPatient(startDrugMap, pId);
				if (startDateObsResults != null && startDateObsResults.getValueCoded().equals(startDrugs)) {
					if (startDateObsResults.getEncounter().getEncounterType().equals(encounterTypePharmacy)
					        || startDateObsResults.getEncounter().getEncounterType().equals(adultoSeguimento)
					        || startDateObsResults.getEncounter().getEncounterType().equals(arvPaed)) {
						dateStartedDrugs = startDateObsResults.getObsDatetime();
						enrollmentDates.add(dateStartedDrugs);
					}
				}
				
				Obs historicalDateValue = EptsCalculationUtils.obsResultForPatient(historicalMap, pId);
				if (historicalDateValue != null && historicalDateValue.getEncounter() != null
				        && historicalDateValue.getEncounter().getEncounterType() != null
				        && historicalDateValue.getValueDatetime() != null) {
					
					if (historicalDateValue.getEncounter().getEncounterType().equals(encounterTypePharmacy)
					        || historicalDateValue.getEncounter().getEncounterType().equals(adultoSeguimento)
					        || historicalDateValue.getEncounter().getEncounterType().equals(arvPaed)) {
						historicalDate = historicalDateValue.getValueDatetime();
						enrollmentDates.add(historicalDate);
					}
					
					Encounter pharmacyEncounter = EptsCalculationUtils.encounterResultForPatient(pharmacyEncounterMap, pId);
					if (pharmacyEncounter != null) {
						pharmacyDate = pharmacyEncounter.getEncounterDatetime();
						enrollmentDates.add(pharmacyDate);
					}
					
					if (enrollmentDates.size() > 0) {
						if (enrollmentDates.size() == 1) {
							requiredDate = enrollmentDates.get(0);
						} else if (enrollmentDates.size() == 2) {
							requiredDate = EptsCalculationUtils.earliest(enrollmentDates.get(0), enrollmentDates.get(1));
						} else if (enrollmentDates.size() == 3) {
							Date tempDate = EptsCalculationUtils.earliest(enrollmentDates.get(0), enrollmentDates.get(1));
							requiredDate = EptsCalculationUtils.earliest(enrollmentDates.get(2), tempDate);
						} else if (enrollmentDates.size() == 4) {
							Date tempDate1 = EptsCalculationUtils.earliest(enrollmentDates.get(0), enrollmentDates.get(1));
							Date tempDate2 = EptsCalculationUtils.earliest(enrollmentDates.get(2), enrollmentDates.get(3));
							requiredDate = EptsCalculationUtils.earliest(tempDate1, tempDate2);
						}
					}
					
					map.put(pId, new SimpleResult(requiredDate, this));
				}
			}
			
		}
		return map;
	}
}
