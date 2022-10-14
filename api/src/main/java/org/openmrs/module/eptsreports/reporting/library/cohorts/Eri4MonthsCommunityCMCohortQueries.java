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
package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.library.queries.BreastfeedingQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.Eri2MonthsQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.Eri2MonthsQueriesInterface;
import org.openmrs.module.eptsreports.reporting.library.queries.Eri4MonthsQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.ErimType;
import org.openmrs.module.eptsreports.reporting.library.queries.PregnantQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.TxNewQueries;
import org.openmrs.module.eptsreports.reporting.utils.AgeRange;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Eri4MonthsCommunityCMCohortQueries {

  @Autowired private HivMetadata hivMetadata;

  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private GenericCohortQueries genericCohorts;

  @Autowired private TxNewCohortQueries txNewCohortQueries;

  /**
   * C
   *
   * @return
   */
  public CohortDefinition
      getAllPatientsWhoReturnedFor2ndConsultationOR2ndDrugsPickUpWithin33Days() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patients who picked up drugs in 33 days");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setQuery(
        Eri2MonthsQueries.getAllPatientsWhoReturnedFor2ndConsultationOR2ndDrugsPickUpWithin33Days(
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPlanConcept().getConceptId(),
            this.hivMetadata.getStartDrugsConcept().getConceptId(),
            this.hivMetadata.getHistoricalDrugStartDateConcept().getConceptId(),
            this.hivMetadata.getARTProgram().getProgramId()));
    return cd;
  }

  public CohortDefinition
      getPatientsStartedArtLastMonthWith2PickupsOrConsultationWithin33DaysExcludingDeadAndTransferedOutAndIn() {
    return null;
  }

  public CohortDefinition getEri2MonthsPregnatCompositionCohort(final String cohortName) {
    final CompositionCohortDefinition eri2MonthsCompositionCohort =
        new CompositionCohortDefinition();

    eri2MonthsCompositionCohort.setName(cohortName);
    eri2MonthsCompositionCohort.addParameter(new Parameter("startDate", "Start Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("endDate", "End Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    final String cohortMappings =
        "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    eri2MonthsCompositionCohort.addSearch(
        "START-ART-2-MONTHS",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "All patients",
                Eri2MonthsQueriesInterface.QUERY
                    .findPatientsWhoHaveEitherClinicalConsultationOrDrugsPickup33DaysForASpecificPatientType(
                        ErimType.TOTAL)),
            mappings));

    eri2MonthsCompositionCohort.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsThatAreFemaleAndWereMarkedAsPregnantInTheInitialConsultationOrFollowUpConsultationAndMasterCard",
                PregnantQueries.findPatientsWhoArePregnantInAPeriod()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "COMMUNITY-DISPENSATION-CM",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findCommunityPatientsDispensation",
                TxNewQueries.QUERY.findPatientsInComunnityCMDispensation),
            mappings));

    eri2MonthsCompositionCohort.setCompositionString(
        "START-ART-2-MONTHS AND PREGNANT AND COMMUNITY-DISPENSATION-CM");

    return eri2MonthsCompositionCohort;
  }

  public CohortDefinition getEri2MonthsBrestfeetingCompositionCohort(final String cohortName) {
    final CompositionCohortDefinition eri2MonthsCompositionCohort =
        new CompositionCohortDefinition();

    eri2MonthsCompositionCohort.setName(cohortName);
    eri2MonthsCompositionCohort.addParameter(new Parameter("startDate", "Start Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("endDate", "End Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    final String cohortMappings =
        "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    eri2MonthsCompositionCohort.addSearch(
        "START-ART-2-MONTHS",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "All patients",
                Eri2MonthsQueriesInterface.QUERY
                    .findPatientsWhoHaveEitherClinicalConsultationOrDrugsPickup33DaysForASpecificPatientType(
                        ErimType.TOTAL)),
            mappings));

    eri2MonthsCompositionCohort.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsWhoAreBreasfeedingInAPeriod",
                BreastfeedingQueries.findPatientsWhoAreBreastfeeding()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "COMMUNITY-DISPENSATION-CM",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findCommunityPatientsDispensation",
                TxNewQueries.QUERY.findPatientsInComunnityCMDispensation),
            mappings));

    eri2MonthsCompositionCohort.setCompositionString(
        "START-ART-2-MONTHS AND BREASTFEEDING AND COMMUNITY-DISPENSATION-CM");

    return eri2MonthsCompositionCohort;
  }

  public CohortDefinition getEri2MonthsAdultCompositionCohort(final String cohortName) {
    final CompositionCohortDefinition eri2MonthsCompositionCohort =
        new CompositionCohortDefinition();

    eri2MonthsCompositionCohort.setName(cohortName);
    eri2MonthsCompositionCohort.addParameter(new Parameter("startDate", "Start Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("endDate", "End Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    final String cohortMappings =
        "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    eri2MonthsCompositionCohort.addSearch(
        "START-ART-2-MONTHS",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "All patients",
                Eri2MonthsQueriesInterface.QUERY
                    .findPatientsWhoHaveEitherClinicalConsultationOrDrugsPickup33DaysForASpecificPatientType(
                        ErimType.TOTAL)),
            mappings));

    eri2MonthsCompositionCohort.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsWhoAreBreasfeedingInAPeriod",
                BreastfeedingQueries.findPatientsWhoAreBreastfeeding()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsWhoArePregnantInAPeriod",
                PregnantQueries.findPatientsWhoArePregnantInAPeriod()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "ADULT",
        EptsReportUtils.map(
            this.txNewCohortQueries.findPatientsWhoAreNewlyEnrolledOnArtByAgeRange(AgeRange.ADULT),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "COMMUNITY-DISPENSATION-CM",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findCommunityPatientsDispensation",
                TxNewQueries.QUERY.findPatientsInComunnityCMDispensation),
            mappings));

    eri2MonthsCompositionCohort.setCompositionString(
        "((START-ART-2-MONTHS AND ADULT) NOT (PREGNANT OR BREASTFEEDING)) AND COMMUNITY-DISPENSATION-CM ");

    return eri2MonthsCompositionCohort;
  }

  public CohortDefinition getEri2MonthsChildCompositionCohort(final String cohortName) {
    final CompositionCohortDefinition eri2MonthsCompositionCohort =
        new CompositionCohortDefinition();

    eri2MonthsCompositionCohort.setName(cohortName);
    eri2MonthsCompositionCohort.addParameter(new Parameter("startDate", "Start Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("endDate", "End Date", Date.class));
    eri2MonthsCompositionCohort.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    final String cohortMappings =
        "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    eri2MonthsCompositionCohort.addSearch(
        "START-ART-2-MONTHS",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "All patients",
                Eri2MonthsQueriesInterface.QUERY
                    .findPatientsWhoHaveEitherClinicalConsultationOrDrugsPickup33DaysForASpecificPatientType(
                        ErimType.TOTAL)),
            mappings));

    eri2MonthsCompositionCohort.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsWhoAreBreasfeedingInAPeriod",
                BreastfeedingQueries.findPatientsWhoAreBreastfeeding()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "patientsWhoArePregnantInAPeriod",
                PregnantQueries.findPatientsWhoArePregnantInAPeriod()),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "CHILDREN",
        EptsReportUtils.map(
            this.txNewCohortQueries.findPatientsWhoAreNewlyEnrolledOnArtByAgeRange(
                AgeRange.CHILDREN),
            cohortMappings));

    eri2MonthsCompositionCohort.addSearch(
        "COMMUNITY-DISPENSATION-CM",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findCommunityPatientsDispensation",
                TxNewQueries.QUERY.findPatientsInComunnityCMDispensation),
            mappings));

    eri2MonthsCompositionCohort.setCompositionString(
        "((START-ART-2-MONTHS AND CHILDREN) NOT (PREGNANT OR BREASTFEEDING)) AND COMMUNITY-DISPENSATION-CM");

    return eri2MonthsCompositionCohort;
  }
  // CM TOTAL
  public CohortDefinition getEri4MonthsTotalCompositionCohort(
      final String cohortName, final ErimType erim) {
    final CompositionCohortDefinition eri4MonthsCompositionCohort =
        new CompositionCohortDefinition();

    eri4MonthsCompositionCohort.setName(cohortName);
    eri4MonthsCompositionCohort.addParameter(new Parameter("startDate", "Start Date", Date.class));
    eri4MonthsCompositionCohort.addParameter(new Parameter("endDate", "End Date", Date.class));
    eri4MonthsCompositionCohort.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    eri4MonthsCompositionCohort.addSearch(
        "START-ART-4-MONTHS",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "All patients " + erim.toString(),
                Eri4MonthsQueries
                    .findPatientsWhoHaveEitherClinicalConsultationOrDrugsPickupBetween61And120ForASpecificPatientType(
                        erim)),
            mappings));

    eri4MonthsCompositionCohort.addSearch(
        "COMMUNITY-DISPENSATION-CM",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findCommunityPatientsDispensation",
                TxNewQueries.QUERY.findPatientsInComunnityCMDispensation),
            mappings));

    eri4MonthsCompositionCohort.setCompositionString(
        "START-ART-4-MONTHS AND COMMUNITY-DISPENSATION-CM");

    return eri4MonthsCompositionCohort;
  }
}
