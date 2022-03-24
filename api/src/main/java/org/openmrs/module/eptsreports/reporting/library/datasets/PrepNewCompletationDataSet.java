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
package org.openmrs.module.eptsreports.reporting.library.datasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.PrepNewCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.PrepNewKeyPopType;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrepNewCompletationDataSet extends BaseDataSet {

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private PrepNewCohortQueries prepNew;

  public DataSetDefinition constructDatset(final String nomeSector, final Integer conceitoSector) {
    CohortIndicatorDataSetDefinition definition = new CohortIndicatorDataSetDefinition();
    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    definition.setName("PrEP NEW Data Set Start Sector and Key Population");
    definition.addParameters(getParameters());

    definition.addColumn(
        nomeSector + ".CS",
        "PrEP_NEW_CPN_"
            + PrepNewKeyPopType.CASAIS_SERODISCORDANTE.toString()
            + ""
            + ""
            + ": Number of clients new on PrEP at CPN "
            + PrepNewKeyPopType.CASAIS_SERODISCORDANTE.toString(),
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_NEW_CPN_Sero_Discordante: Number of clients new on PrEP at CPN Sero Discordante",
                EptsReportUtils.map(
                    prepNew.getSectorClientsNewlyEnrolledInPrep(
                        conceitoSector, 165196, PrepNewKeyPopType.CASAIS_SERODISCORDANTE),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        nomeSector + ".MG",
        "PrEP_NEW_CPN_GRAVIDA: Number of clients new on PrEP at CPN Sero Discordante",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "TX_CURR: Number of patients currently receiving ART",
                EptsReportUtils.map(
                    prepNew.getSectorClientsNewlyEnrolledInPrep(
                        conceitoSector, 165196, PrepNewKeyPopType.PREGNANT),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        nomeSector + "All",
        "PrEP_NEW_",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PREP NEW", EptsReportUtils.map(prepNew.getClientsNewlyEnrolledInPrep(), mappings)),
            mappings),
        "");

    return definition;
  }
}
