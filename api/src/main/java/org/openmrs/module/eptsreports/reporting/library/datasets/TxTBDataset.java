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

import java.util.Arrays;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TXTBCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TxTBDataset extends BaseDataSet {
  @Autowired private EptsCommonDimension eptsCommonDimension;

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private TXTBCohortQueries txTbCohortQueries;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  private Boolean isCommunity;

  public DataSetDefinition constructTxTBDataset(final Boolean isCommunity) {
    this.isCommunity = isCommunity;
    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    final CohortIndicatorDataSetDefinition dataSetDefinition =
        new CohortIndicatorDataSetDefinition();
    dataSetDefinition.setName("TX_TB Data Set");
    dataSetDefinition.addParameters(this.getParameters());

    dataSetDefinition.addDimension(
        "gender", EptsReportUtils.map(this.eptsCommonDimension.gender(), ""));
    dataSetDefinition.addDimension(
        "age",
        EptsReportUtils.map(
            this.eptsCommonDimension.age(this.ageDimensionCohort), "effectiveDate=${endDate}"));
    this.addTXTBNumerator(mappings, dataSetDefinition);

    this.addTXTBDenominator(mappings, dataSetDefinition);

    this.addSpecimenSentDisaggregation(mappings, dataSetDefinition);
    this.addDiagnositcTestDisaggregation(mappings, dataSetDefinition);
    this.addPositiveResultsDisaggregation(mappings, dataSetDefinition);

    return dataSetDefinition;
  }

  private void addTXTBNumerator(
      final String mappings, final CohortIndicatorDataSetDefinition dataSetDefinition) {
    final CohortIndicator numerator =
        this.eptsGeneralIndicator.getIndicator(
            "NUMERATOR", EptsReportUtils.map(this.txTbCohortQueries.txTbNumerator(), mappings));
    final CohortIndicator patientsPreviouslyOnARTNumerator =
        this.eptsGeneralIndicator.getIndicator(
            "patientsPreviouslyOnARTNumerator",
            EptsReportUtils.map(
                this.txTbCohortQueries.patientsPreviouslyOnARTNumerator(), mappings));
    final CohortIndicator patientsNewOnARTNumerator =
        this.eptsGeneralIndicator.getIndicator(
            "patientsNewOnARTNumerator",
            EptsReportUtils.map(this.txTbCohortQueries.patientsNewOnARTNumerator(), mappings));

    dataSetDefinition.addColumn(
        "TXB_NUM", "TX_TB: Numerator total", EptsReportUtils.map(numerator, mappings), "");
    this.addRow(
        dataSetDefinition,
        "TXB_NUM_PREV",
        "Numerator (patientsPreviouslyOnARTNumerator)",
        EptsReportUtils.map(patientsPreviouslyOnARTNumerator, mappings),
        this.dissagregations());
    this.addRow(
        dataSetDefinition,
        "TXB_NUM_NEW",
        "Numerator (patientsNewOnARTNumerator)",
        EptsReportUtils.map(patientsNewOnARTNumerator, mappings),
        this.dissagregations());
  }

  private void addTXTBDenominator(
      final String mappings, final CohortIndicatorDataSetDefinition dataSetDefinition) {
    final CohortIndicator previouslyOnARTPostiveScreening =
        this.eptsGeneralIndicator.getIndicator(
            "previouslyOnARTPositiveScreening",
            EptsReportUtils.map(
                this.txTbCohortQueries.previouslyOnARTPositiveScreening(Boolean.FALSE), mappings));
    final CohortIndicator previouslyOnARTNegativeScreening =
        this.eptsGeneralIndicator.getIndicator(
            "previouslyOnARTNegativeScreening",
            EptsReportUtils.map(
                this.txTbCohortQueries.previouslyOnARTNegativeScreening(Boolean.FALSE), mappings));
    final CohortIndicator newOnARTPositiveScreening =
        this.eptsGeneralIndicator.getIndicator(
            "newOnARTPositiveScreening",
            EptsReportUtils.map(
                this.txTbCohortQueries.newOnARTPositiveScreening(Boolean.FALSE), mappings));
    final CohortIndicator newOnARTNegativeScreening =
        this.eptsGeneralIndicator.getIndicator(
            "newOnARTNegativeScreening",
            EptsReportUtils.map(
                this.txTbCohortQueries.newOnARTNegativeScreening(Boolean.FALSE), mappings));

    dataSetDefinition.addColumn(
        "TXB_DEN",
        "TX_TB: Denominator total",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Denominator Total",
                EptsReportUtils.map(
                    this.txTbCohortQueries.getDenominator(Boolean.FALSE), mappings)),
            mappings),
        "");

    this.addRow(
        dataSetDefinition,
        "TXB_DEN_NEW_POS",
        "Denominator (newOnARTPositiveScreening)",
        EptsReportUtils.map(newOnARTPositiveScreening, mappings),
        this.dissagregations());
    this.addRow(
        dataSetDefinition,
        "TXB_DEN_NEW_NEG",
        "Denominator (newOnARTNegativeScreening)",
        EptsReportUtils.map(newOnARTNegativeScreening, mappings),
        this.dissagregations());
    this.addRow(
        dataSetDefinition,
        "TXB_DEN_PREV_POS",
        "Denominator (previouslyOnARTPositiveScreening)",
        EptsReportUtils.map(previouslyOnARTPostiveScreening, mappings),
        this.dissagregations());
    this.addRow(
        dataSetDefinition,
        "TXB_DEN_PREV_NEG",
        "Denominator (previouslyOnARTNegativeScreening)",
        EptsReportUtils.map(previouslyOnARTNegativeScreening, mappings),
        this.dissagregations());
  }

  private void addSpecimenSentDisaggregation(
      final String mappings, final CohortIndicatorDataSetDefinition dataSetDefinition) {

    final CohortIndicator specimentSent =
        this.eptsGeneralIndicator.getIndicator(
            "SPECIMEN-SENT",
            EptsReportUtils.map(
                this.txTbCohortQueries.getSpecimenSentCohortDefinition(this.isCommunity),
                mappings));

    dataSetDefinition.addColumn(
        "TX_TB_TOTAL_SPECIMEN_SENT",
        "TX_TB: Total Patients With Specimen Sent",
        EptsReportUtils.map(specimentSent, mappings),
        "");
  }

  private void addDiagnositcTestDisaggregation(
      final String mappings, final CohortIndicatorDataSetDefinition dataSetDefinition) {

    final CohortIndicator geneExpert =
        this.eptsGeneralIndicator.getIndicator(
            "GENEXPERT-DIAGNOSTIC-TEST",
            EptsReportUtils.map(
                this.txTbCohortQueries.getGeneXpertMTBDiagnosticTestCohortDefinition(
                    this.isCommunity),
                mappings));

    final CohortIndicator smearOnly =
        this.eptsGeneralIndicator.getIndicator(
            "SMEAR-ONLY-DIAGNOSTIC-TEST",
            EptsReportUtils.map(
                this.txTbCohortQueries.getSmearMicroscopyOnlyDiagnosticTestCohortDefinition(
                    this.isCommunity),
                mappings));
    final CohortIndicator otherNoExpert =
        this.eptsGeneralIndicator.getIndicator(
            "OTHER-NO-EXPERT-DIAGNOSTIC-TEST",
            EptsReportUtils.map(
                this.txTbCohortQueries.getAdditionalOtherThanGenExpertTestCohortDefinition(
                    this.isCommunity),
                mappings));
    dataSetDefinition.addColumn(
        "TX_TB_TOTAL_GENEXPERT_DIAGNOSTIC",
        "TX_TB: Total Gene Xpert MTB/RIF Assay (Diagnostic Test)",
        EptsReportUtils.map(geneExpert, mappings),
        "");
    dataSetDefinition.addColumn(
        "TX_TB_TOTAL_SMEAR_ONLY_DIAGNOSTIC",
        "TX_TB: Total Smear Only (Diagnostic Test)",
        EptsReportUtils.map(smearOnly, mappings),
        "");
    dataSetDefinition.addColumn(
        "TX_TB_TOTAL_OTHER-NO-EXPERT-DIAGNOSTIC",
        "TX_TB: Total Other (No Xpert) (Diagnostic Test)",
        EptsReportUtils.map(otherNoExpert, mappings),
        "");
  }

  private void addPositiveResultsDisaggregation(
      final String mappings, final CohortIndicatorDataSetDefinition dataSetDefinition) {

    final CohortIndicator positiveResults =
        this.eptsGeneralIndicator.getIndicator(
            "POSITIVE-RESULT",
            EptsReportUtils.map(
                this.txTbCohortQueries.getPositiveResultCohortDefinition(this.isCommunity),
                mappings));

    dataSetDefinition.addColumn(
        "TX_TB_TOTAL_POSITIVE_RESULT",
        "TX_TB: Total Patients With Positive Results",
        EptsReportUtils.map(positiveResults, mappings),
        "");
  }

  private List<ColumnParameters> dissagregations() {
    return Arrays.asList(
        new ColumnParameters("<15Females", "<15 anos - Feminino", "gender=F|age=<15", "F1"),
        new ColumnParameters(">=15Females", "15+ anos Feminino", "gender=F|age=15+", "F2"),
        new ColumnParameters("UnknownFemales", "Unknown anos Feminino", "gender=F|age=UK", "F3"),
        new ColumnParameters("<15Males", "<15 anos - Masculino", "gender=M|age=<15", "M1"),
        new ColumnParameters(">=15Males", "15+ anos Masculino", "gender=M|age=15+", "M2"),
        new ColumnParameters("UnknownMales", "Unknown anos Masculino", "gender=M|age=UK", "M3"));
  }
}
