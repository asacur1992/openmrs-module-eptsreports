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
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EriCohortQueries {

  @Autowired private TxNewCohortQueries txNewCohortQueries;

  @Autowired private HivMetadata hivMetadata;

  @Autowired private GenericCohortQueries genericCohortQueries;

  /**
   * Get all patients who initiated ART 2 months from ART initiation less transfer ins return the
   * patient who initiated ART A and B
   *
   * @retrun CohortDefinition
   */
  public CohortDefinition getAllPatientsWhoInitiatedArt() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("All patients who initiated ART less transfer ins");
    cd.addParameter(new Parameter("cohortStartDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("cohortEndDate", "End Date", Date.class));
    cd.addParameter(new Parameter("reportingEndDate", "Reporting End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "initiatedArt",
        EptsReportUtils.map(
            this.genericCohortQueries.getStartedArtOnPeriod(false, true),
            "onOrAfter=${cohortStartDate},onOrBefore=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "transferIns",
        EptsReportUtils.map(
            this.genericCohortQueries.getPatientsBasedOnPatientStates(
                this.hivMetadata.getARTProgram().getProgramId(),
                this.hivMetadata
                    .getTransferredFromOtherHealthFacilityWorkflowState()
                    .getProgramWorkflowStateId()),
            "startDate=${cohortStartDate},endDate=${cohortEndDate},location=${location}"));
    cd.setCompositionString("initiatedArt AND NOT transferIns");
    return cd;
  }

  /**
   * Get pregnant women who have more than 2 months retention on ART
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPregnantWomenRetainedOnArt() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Pregnant women retain on ART for more than 2 months from ART initiation date");
    cd.addParameter(new Parameter("cohortStartDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("cohortEndDate", "End Date", Date.class));
    cd.addParameter(new Parameter("reportingEndDate", "Reporting End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "initiatedART",
        EptsReportUtils.map(
            this.getAllPatientsWhoInitiatedArt(),
            "cohortStartDate=${cohortStartDate},cohortEndDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "pregnant",
        EptsReportUtils.map(
            this.txNewCohortQueries.getPatientsPregnantEnrolledOnART(),
            "startDate=${cohortStartDate},endDate=${cohortEndDate},location=${location}"));
    cd.setCompositionString("initiatedART AND pregnant");
    return cd;
  }

  /**
   * Get breastfeeding women who have more than 2 months ART retention
   *
   * @return CohortDefinition
   */
  public CohortDefinition getBreastfeedingWomenRetained() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Breastfeeding women retain on ART for more than 2 months from ART initiation date");
    cd.addParameter(new Parameter("cohortStartDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("cohortEndDate", "End Date", Date.class));
    cd.addParameter(new Parameter("reportingEndDate", "Reporting End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "initiatedART",
        EptsReportUtils.map(
            this.getAllPatientsWhoInitiatedArt(),
            "cohortStartDate=${cohortStartDate},cohortEndDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "breastfeeding",
        EptsReportUtils.map(
            this.txNewCohortQueries.getTxNewBreastfeedingComposition(),
            "onOrAfter=${cohortStartDate},onOrBefore=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "pregnant",
        EptsReportUtils.map(
            this.getPregnantWomenRetainedOnArt(),
            "cohortStartDate=${cohortStartDate},cohortEndDate=${cohortEndDate},reportingEndDate=${reportingEndDate},location=${location}"));
    cd.setCompositionString("initiatedART AND (breastfeeding NOT pregnant)");
    return cd;
  }

  /**
   * Get Children (0-14, excluding pregnant and breastfeeding women)
   *
   * @return CohortDefinition
   */
  public CohortDefinition getChildrenRetained() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Children having ART retention for than 2 months");
    cd.addParameter(new Parameter("cohortStartDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("cohortEndDate", "End Date", Date.class));
    cd.addParameter(new Parameter("reportingEndDate", "Reporting End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "initiatedART",
        EptsReportUtils.map(
            this.getAllPatientsWhoInitiatedArt(),
            "cohortStartDate=${cohortStartDate},cohortEndDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "children",
        EptsReportUtils.map(
            this.genericCohortQueries.getAgeOnArtStartDate(null, 14), "location=${location}"));
    cd.addSearch(
        "pregnant",
        EptsReportUtils.map(
            this.txNewCohortQueries.getPatientsPregnantEnrolledOnART(),
            "startDate=${cohortStartDate},endDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "breastfeeding",
        EptsReportUtils.map(
            this.txNewCohortQueries.getTxNewBreastfeedingComposition(),
            "onOrAfter=${cohortStartDate},onOrBefore=${cohortEndDate},location=${location}"));
    cd.setCompositionString("(initiatedART AND children) AND NOT (pregnant OR breastfeeding)");
    return cd;
  }

  /**
   * Get Adults (14+, excluding pregnant and breastfeeding women)
   *
   * @return CohortDefinition
   */
  public CohortDefinition getAdultsRetained() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Adults having ART retention for than 2 months");
    cd.addParameter(new Parameter("cohortStartDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("cohortEndDate", "End Date", Date.class));
    cd.addParameter(new Parameter("reportingEndDate", "Reporting End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "initiatedART",
        EptsReportUtils.map(
            this.getAllPatientsWhoInitiatedArt(),
            "cohortStartDate=${cohortStartDate},cohortEndDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "adults",
        EptsReportUtils.map(
            this.genericCohortQueries.getAgeOnArtStartDate(15, null), "location=${location}"));
    cd.addSearch(
        "pregnant",
        EptsReportUtils.map(
            this.txNewCohortQueries.getPatientsPregnantEnrolledOnART(),
            "startDate=${cohortStartDate},endDate=${cohortEndDate},location=${location}"));
    cd.addSearch(
        "breastfeeding",
        EptsReportUtils.map(
            this.txNewCohortQueries.getTxNewBreastfeedingComposition(),
            "onOrAfter=${cohortStartDate},onOrBefore=${cohortEndDate},location=${location}"));
    cd.setCompositionString("(initiatedART AND adults) AND NOT (pregnant OR breastfeeding)");
    return cd;
  }
}
