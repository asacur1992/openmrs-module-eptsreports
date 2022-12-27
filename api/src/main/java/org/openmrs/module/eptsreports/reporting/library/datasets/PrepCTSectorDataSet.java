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

import org.openmrs.module.eptsreports.reporting.library.cohorts.PrepCtCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.PrepNewKeyPopType;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author Abdul Sacur */
@Component
public class PrepCTSectorDataSet extends BaseDataSet {

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private PrepCtCohortQueries prepCT;

  /**
   * @param nomeSector
   * @param conceitokeypop
   * @return
   */
  public DataSetDefinition constructDatset() {
    CohortIndicatorDataSetDefinition definition = new CohortIndicatorDataSetDefinition();
    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    definition.setName("PrEP CT Data Set and Key Population");
    definition.addParameters(getParameters());

    definition.addColumn(
        "MG",
        "PrEP_CT_MG",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_MG",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.PREGNANT),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "ML",
        "PrEP_CT_ML",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_ML",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.LACTATION),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "AJ",
        "PrEP_CT_AJ",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_AJ",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(
                        PrepNewKeyPopType.ADOLESCENTS_YOUTH_RISK),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "CS",
        "PrEP_CT_CS",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_CS",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(
                        PrepNewKeyPopType.CASAIS_SERODISCORDANTE),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "HM",
        "PrEP_CT_HM",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_HM",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.MILITARY),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "HMO",
        "PrEP_CT_HMO",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_HMO",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.MINER),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "HC",
        "PrEP_CT_HC",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_HC",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.DRIVER),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "RE",
        "PrEP_CT_RE",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_RE",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.PRISIONER),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "HSH",
        "PrEP_CT_HSH",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_HSH",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.HOMOSEXUAL),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "HT",
        "PrEP_CT_HT",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_HT",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.TRANSGENDER),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "TS",
        "PrEP_CT_TS",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_TS",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.SEXWORKER),
                    mappings)),
            mappings),
        "");

    definition.addColumn(
        "PID",
        "PrEP_CT_PID",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "PrEP_CT_PID",
                EptsReportUtils.map(
                    prepCT.getClientsEnrolledInPrepBySubpopulation(PrepNewKeyPopType.DRUGUSER),
                    mappings)),
            mappings),
        "");

    return definition;
  }
}
