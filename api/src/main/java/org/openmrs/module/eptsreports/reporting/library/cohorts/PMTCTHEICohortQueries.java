/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.queries.PMTCTHEIQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PMTCTHEICohortQueries {

  private static final Integer POSITIVE_RESULT_CONCEPT_ID = 703;
  private static final Integer NEGATIVE_RESULT_CONCEPT_ID = 664;

  @Autowired private GenericCohortQueries genericCohorts;

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTestResults() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCT-HEI");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findNumberOfInfantesWhoHadVirologicHIVTestBy12MonthsOfAgeDuringReportingPeriod",
                PMTCTHEIQueries.QUERY
                    .findNumberOfInfantesWhoHadVirologicHIVTesResulttBy12MonthsOfAgeDuringReportingPeriod),
            mappings));

    composition.setCompositionString("NUMERATOR");

    return composition;
  }

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTestWithPositiveTestResults() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCT-HEI - Positive Test Results");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(this.getNumberOfInfantsWhoHadVirologicHIVTestResults(), mappings));

    composition.addSearch(
        "POSITIVE-RESULTS",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "getNumberOfInfantsWhoHadVirologicHIVTestWithPositiveTestResults",
                String.format(
                    PMTCTHEIQueries.QUERY.findPatientsTestResultsByResultValue,
                    POSITIVE_RESULT_CONCEPT_ID)),
            mappings));

    composition.setCompositionString("NUMERATOR and POSITIVE-RESULTS");

    return composition;
  }

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTestWithNegativeTestResults() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCT-HEI - Negative Test Results");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(this.getNumberOfInfantsWhoHadVirologicHIVTestResults(), mappings));

    composition.addSearch(
        "NEGATIVE-RESULTS",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "getNumberOfInfantsWhoHadVirologicHIVTestWithNegativeTestResults",
                String.format(
                    PMTCTHEIQueries.QUERY.findPatientsTestResultsByResultValue,
                    NEGATIVE_RESULT_CONCEPT_ID)),
            mappings));

    composition.addSearch(
        "POSITIVE-RESULTS",
        EptsReportUtils.map(
            this.getNumberOfInfantsWhoHadVirologicHIVTestWithPositiveTestResults(), mappings));

    composition.setCompositionString("(NUMERATOR and NEGATIVE-RESULTS) not POSITIVE-RESULTS");

    return composition;
  }

  public CohortDefinition
      getNumberOfInfantsWhoHadVirologicHIVTestWithPositiveTestResultsWhoInitatedARTTreatment() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCT-HEI - Positive Test Results and Art Initiated");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "POSITIVE-RESULTS",
        EptsReportUtils.map(
            this.getNumberOfInfantsWhoHadVirologicHIVTestWithPositiveTestResults(), mappings));

    composition.addSearch(
        "ART-INITIATION",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "findPatientsWhoInitiatedART", PMTCTHEIQueries.QUERY.findPatientsWhoInitiatedART),
            mappings));

    composition.setCompositionString("POSITIVE-RESULTS AND ART-INITIATION");

    return composition;
  }
}
