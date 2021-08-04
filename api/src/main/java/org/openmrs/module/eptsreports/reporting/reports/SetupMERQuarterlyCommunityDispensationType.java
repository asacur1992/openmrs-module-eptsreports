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
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxNewCommunityDispensationTypeDataset;
import org.openmrs.module.eptsreports.reporting.library.queries.BaseQueries;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.eptsreports.reporting.utils.CommunityType;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupMERQuarterlyCommunityDispensationType extends EptsDataExportManager {

  @Autowired private TxNewCommunityDispensationTypeDataset txNewCommunityDataset;

  @Autowired protected GenericCohortQueries genericCohortQueries;

  @Override
  public String getVersion() {
    return "1.0-SNAPSHOT";
  }

  @Override
  public String getUuid() {
    return "5066c272-79d2-4177-a96c-d38e7ec0ae8c";
  }

  @Override
  public String getExcelDesignUuid() {
    return "253c7921-506a-4acf-b425-05675f344c0d";
  }

  @Override
  public String getName() {
    return "TX NEW - Comunitario - Modelos de Levantamento";
  }

  @Override
  public String getDescription() {
    return "TX NEW Comunitario Modelos de Levantamento";
  }

  @Override
  public ReportDefinition constructReportDefinition() {
    final ReportDefinition reportDefinition = new ReportDefinition();

    reportDefinition.setUuid(this.getUuid());
    reportDefinition.setName(this.getName());
    reportDefinition.setDescription(this.getDescription());
    reportDefinition.addParameter(new Parameter("startDate", "Data Inicial", Date.class));
    reportDefinition.addParameter(new Parameter("endDate", "Data Final", Date.class));
    reportDefinition.addParameter(new Parameter("location", "Location", Location.class));

    reportDefinition.addDataSetDefinition(
        "A",
        Mapped.mapStraightThrough(this.txNewCommunityDataset.constructTxNewCommunityAllDataset()));

    this.txNewCommunityDataset.setPrefix("N");
    reportDefinition.addDataSetDefinition(
        "N",
        Mapped.mapStraightThrough(
            this.txNewCommunityDataset.constructTxNewCommunityTypeDataset(CommunityType.NORMAL)));

    this.txNewCommunityDataset.setPrefix("O");
    reportDefinition.addDataSetDefinition(
        "O",
        Mapped.mapStraightThrough(
            this.txNewCommunityDataset.constructTxNewCommunityTypeDataset(
                CommunityType.OUT_OF_TIME)));

    this.txNewCommunityDataset.setPrefix("F");
    reportDefinition.addDataSetDefinition(
        "F",
        Mapped.mapStraightThrough(
            this.txNewCommunityDataset.constructTxNewCommunityTypeDataset(
                CommunityType.FARMAC_PRIVATE_PHARMACY)));

    this.txNewCommunityDataset.setPrefix("CDP");
    reportDefinition.addDataSetDefinition(
        "CDP",
        Mapped.mapStraightThrough(
            this.txNewCommunityDataset.constructTxNewCommunityTypeDataset(
                CommunityType.COMMUNITY_DISPENSE_PROVIDER)));

    this.txNewCommunityDataset.setPrefix("APE");
    reportDefinition.addDataSetDefinition(
        "APE",
        Mapped.mapStraightThrough(
            this.txNewCommunityDataset.constructTxNewCommunityTypeDataset(
                CommunityType.COMMUNITY_DISPENSE_APE)));

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
              "TXNEW_Comunitario_Modelos_Levantamento.xls",
              "TX NEW Comunitario Modelos de Levantamento ",
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
