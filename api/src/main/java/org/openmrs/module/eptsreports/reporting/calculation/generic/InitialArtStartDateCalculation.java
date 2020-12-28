/*
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.calculation.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.eptsreports.metadata.CommonMetadata;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.calculation.AbstractPatientCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.common.EPTSCalculationService;
import org.openmrs.module.eptsreports.reporting.utils.EptsCalculationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Calculates the date on which a patient first started ART
 *
 * @return a CulculationResultMap
 */
@Component
public class InitialArtStartDateCalculation extends AbstractPatientCalculation {

  @Autowired private HivMetadata hivMetadata;

  @Autowired private CommonMetadata commonMetadata;

  @Autowired private EPTSCalculationService ePTSCalculationService;

  /**
   * @should return null for patients who have not started ART
   * @should return start date for patients who have started ART
   * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
   *     java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
   */
  @Override
  public CalculationResultMap evaluate(
      final Collection<Integer> cohort,
      final Map<String, Object> parameterValues,
      final PatientCalculationContext context) {

    final CalculationResultMap map = new CalculationResultMap();
    final Location location = (Location) context.getFromCache("location");

    final boolean considerTransferredIn =
        this.getBooleanParameter(parameterValues, "considerTransferredIn");
    final boolean considerPharmacyEncounter =
        this.getBooleanParameter(parameterValues, "considerPharmacyEncounter");

    final Program treatmentProgram = this.hivMetadata.getARTProgram();
    final Concept arvPlan = this.hivMetadata.getARVPlanConcept();
    final Concept startDrugsConcept = this.hivMetadata.getStartDrugsConcept();
    final Concept transferInConcept = this.hivMetadata.getTransferFromOtherFacilityConcept();
    final Concept historicalStartConcept = this.commonMetadata.getHistoricalDrugStartDateConcept();
    final Concept artDatePickupConcept = this.hivMetadata.getArtDatePickup();
    final EncounterType encounterTypePharmacy = this.hivMetadata.getARVPharmaciaEncounterType();

    final List<EncounterType> encounterTypes =
        Arrays.asList(
            encounterTypePharmacy,
            this.hivMetadata.getAdultoSeguimentoEncounterType(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType());

    final CalculationResultMap inProgramMap =
        this.ePTSCalculationService.firstPatientProgram(
            treatmentProgram, location, cohort, context);
    final CalculationResultMap startDrugMap =
        this.ePTSCalculationService.firstObs(
            arvPlan,
            startDrugsConcept,
            location,
            true,
            null,
            null,
            encounterTypes,
            cohort,
            context);

    final CalculationResultMap historicalMap =
        this.ePTSCalculationService.firstObs(
            historicalStartConcept,
            null,
            location,
            false,
            null,
            null,
            Arrays.asList(
                this.hivMetadata.getAdultoSeguimentoEncounterType(),
                this.hivMetadata.getARVPediatriaSeguimentoEncounterType(),
                this.hivMetadata.getMasterCardEncounterType()),
            cohort,
            context);

    final CalculationResultMap historicalDrugPickupInReceptionMap =
        this.ePTSCalculationService.firstObs(
            artDatePickupConcept,
            null,
            location,
            false,
            null,
            null,
            Arrays.asList(this.hivMetadata.getMasterCardDrugPickupEncounterType()),
            cohort,
            context);

    final CalculationResultMap pharmacyEncounterMap =
        this.ePTSCalculationService.firstEncounter(
            Arrays.asList(encounterTypePharmacy), cohort, location, context);
    final CalculationResultMap transferInMap =
        this.ePTSCalculationService.firstObs(
            arvPlan, transferInConcept, location, true, null, null, null, cohort, context);

    for (final Integer pId : cohort) {
      Date requiredDate = null;
      final List<Date> enrollmentDates = new ArrayList<Date>();
      final SimpleResult result = (SimpleResult) inProgramMap.get(pId);
      if (result != null) {
        final PatientProgram patientProgram = (PatientProgram) result.getValue();
        enrollmentDates.add(patientProgram.getDateEnrolled());
      }
      final Obs startDrugsObs = EptsCalculationUtils.resultForPatient(startDrugMap, pId);
      if (startDrugsObs != null) {
        enrollmentDates.add(startDrugsObs.getEncounter().getEncounterDatetime());
      }
      final Obs historicalDateObs = EptsCalculationUtils.resultForPatient(historicalMap, pId);
      if (historicalDateObs != null) {
        enrollmentDates.add(historicalDateObs.getValueDatetime());
      }

      final Obs historicalDrugPickupInReceptionDateObs =
          EptsCalculationUtils.resultForPatient(historicalDrugPickupInReceptionMap, pId);
      if (historicalDrugPickupInReceptionDateObs != null) {
        enrollmentDates.add(historicalDrugPickupInReceptionDateObs.getValueDatetime());
      }

      if (considerPharmacyEncounter) {
        final Encounter pharmacyEncounter =
            EptsCalculationUtils.resultForPatient(pharmacyEncounterMap, pId);
        if (pharmacyEncounter != null) {
          enrollmentDates.add(pharmacyEncounter.getEncounterDatetime());
        }
      }
      if (considerTransferredIn) {
        final Obs transferInObs = EptsCalculationUtils.resultForPatient(transferInMap, pId);
        if (transferInObs != null) {
          enrollmentDates.add(transferInObs.getObsDatetime());
        }
      }
      enrollmentDates.removeAll(Collections.singleton(null));
      if (enrollmentDates.size() > 0) {
        Collections.sort(enrollmentDates);
        requiredDate = enrollmentDates.get(0);
      }
      map.put(pId, new SimpleResult(requiredDate, this));
    }
    return map;
  }

  private boolean getBooleanParameter(
      final Map<String, Object> parameterValues, final String parameterName) {
    Boolean parameterValue = null;
    if (parameterValues != null) {
      parameterValue = (Boolean) parameterValues.get(parameterName);
    }
    if (parameterValue == null) {
      parameterValue = true;
    }
    return parameterValue;
  }

  public static Date getArtStartDate(
      final Integer patientId, final CalculationResultMap artStartDates) {
    final CalculationResult calculationResult = artStartDates.get(patientId);
    if (calculationResult != null) {
      return (Date) calculationResult.getValue();
    }
    return null;
  }
}
