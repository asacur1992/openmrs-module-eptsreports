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
package org.openmrs.module.eptsreports.reporting.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.IndicatorType;
import org.openmrs.module.eptsreports.reporting.library.datasets.PrepCtCommunityDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.PrepNewDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TRFINDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxCurrDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxMlCommunityDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxNewDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxPvlsCommunityDataset;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxRttDataset;
import org.openmrs.module.eptsreports.reporting.library.queries.BaseQueries;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupMERQuarterlyCommunity extends EptsDataExportManager {

  @Autowired private TxPvlsCommunityDataset txPvlsCommunityDataset;

  @Autowired private TxNewDataset txNewDataset;

  @Autowired private TxCurrDataset txCurrDataset;

  @Autowired private TxRttDataset txRttDataset;

  @Autowired private TxMlCommunityDataset txMlCommunityDataset;

  @Autowired private TRFINDataset txTfrInDataset;

  @Autowired private PrepNewDataset prepNewDataset;

  @Autowired private PrepCtCommunityDataset prepCommunityDataset;

  @Autowired protected GenericCohortQueries genericCohortQueries;

  @Autowired private DatinCodeDataSet DatinCodeDataSet;

  @Override
  public String getVersion() {
    return "1.0-SNAPSHOT";
  }

  @Override
  public String getUuid() {
    return "1dd7abdc-877c-4014-9376-16414a9462ca";
  }

  @Override
  public String getExcelDesignUuid() {
    return "eb581a7b-d077-48ea-8ca7-b121e2911900";
  }

  @Override
  public String getName() {
    return "PEPFAR MER 2.6 Quarterly - Comunidade";
  }

  @Override
  public String getDescription() {
    return "MER Quarterly Report Comunidade";
  }

  @Override
  public ReportDefinition constructReportDefinition() {
    final ReportDefinition reportDefinition = new ReportDefinition();

    reportDefinition.setUuid(this.getUuid());
    reportDefinition.setName(this.getName());
    reportDefinition.setDescription(this.getDescription());
    reportDefinition.setParameters(this.txRttDataset.getParameters());

    reportDefinition.addDataSetDefinition(
        "N",
        Mapped.mapStraightThrough(
            this.txNewDataset.indicatorType(IndicatorType.COMMUNITY).constructTxNewDataset()));

    reportDefinition.addDataSetDefinition(
        "C",
        Mapped.mapStraightThrough(
            this.txCurrDataset
                .indicatorType(IndicatorType.COMMUNITY)
                .constructTxCurrDataset(true)));

    reportDefinition.addDataSetDefinition(
        "P", Mapped.mapStraightThrough(this.txPvlsCommunityDataset.constructTxPvlsDatset()));

    reportDefinition.addDataSetDefinition(
        "ML", Mapped.mapStraightThrough(this.txMlCommunityDataset.constructtxMlDataset()));

    reportDefinition.addDataSetDefinition(
        "R",
        Mapped.mapStraightThrough(
            this.txRttDataset.indicatorType(IndicatorType.COMMUNITY).constructTxRttDataset()));

    reportDefinition.addDataSetDefinition(
        "TR",
        Mapped.mapStraightThrough(
            this.txTfrInDataset.indicatorType(IndicatorType.COMMUNITY).constructTxTRFIN()));

    reportDefinition.addDataSetDefinition(
        "PREP",
        Mapped.mapStraightThrough(
            this.prepNewDataset.indicatorType(IndicatorType.COMMUNITY).constructPrepNewDataset()));

    reportDefinition.addDataSetDefinition(
        "PrEP_CT", Mapped.mapStraightThrough(this.prepCommunityDataset.constructPrepCtDataset()));
    reportDefinition.addDataSetDefinition(
        "D",
        Mapped.mapStraightThrough(this.DatinCodeDataSet.constructDataset(this.getParameters())));

    reportDefinition.setBaseCohortDefinition(
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "baseCohortQuery", BaseQueries.getBaseCohortQuery()),
            "endDate=${endDate},location=${location}"));

    return reportDefinition;
  }

  @Override
  public List<ReportDesign> constructReportDesigns(final ReportDefinition reportDefinition) {
    ReportDesign reportDesign = null;
    try {
      reportDesign =
          this.createXlsReportDesign(
              reportDefinition,
              "PEPFAR_MER_2.6_QUARTERLY.xls",
              "PEPFAR MER 2.6 Quarterly Report",
              this.getExcelDesignUuid(),
              null);
      final Properties props = new Properties();
      props.put("sortWeight", "5000");
      reportDesign.setProperties(props);
    } catch (final IOException e) {
      throw new ReportingException(e.toString());
    }

    return Arrays.asList(reportDesign);
  }
}
