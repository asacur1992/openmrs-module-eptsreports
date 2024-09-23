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
import org.openmrs.module.eptsreports.reporting.library.queries.PMTCTEIDQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PMTCTEIDCohortQueries {

  @Autowired private GenericCohortQueries genericCohorts;

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTest() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCTEID");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "getNumberOfInfantsWhoHadVirologicHIVTest",
                PMTCTEIDQueries.QUERY
                    .findNumberOfInfantesWhoHadVirologicHIVTestBy12MonthsOfAgeDuringReportingPeriod),
            mappings));

    composition.setCompositionString("NUMERATOR");

    return composition;
  }

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTestWithFistTest() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCTEID - First Test");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(this.getNumberOfInfantsWhoHadVirologicHIVTest(), mappings));

    composition.addSearch(
        "FIRST-TEST",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "getNumberOfInfantsWhoHadVirologicHIVTestWithFistTest",
                String.format(
                    PMTCTEIDQueries.QUERY.findRestulTestByFirstOrSencodTest, 165503, 165507)),
            mappings));

    composition.setCompositionString("NUMERATOR and FIRST-TEST");

    return composition;
  }

  public CohortDefinition getNumberOfInfantsWhoHadVirologicHIVTestWithSecondTest() {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("PMTCTEID - Second Test");
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    composition.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(this.getNumberOfInfantsWhoHadVirologicHIVTest(), mappings));

    composition.addSearch(
        "SECOND-TEST",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "getNumberOfInfantsWhoHadVirologicHIVTestWithSecondTest",
                String.format(
                    PMTCTEIDQueries.QUERY.findRestulTestByFirstOrSencodTest, 165506, 165510)),
            mappings));

    composition.setCompositionString("NUMERATOR and SECOND-TEST");

    return composition;
  }
}
