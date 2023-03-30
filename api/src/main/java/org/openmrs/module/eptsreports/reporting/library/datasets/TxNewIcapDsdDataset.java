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

import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ABOVE_FIFTY;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ABOVE_SIXTY_FIVE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIFTEEN_TO_NINETEEN;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIFTY_FIVE_TO_FIFTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIFTY_TO_FIFTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIVE_TO_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FORTY_FIVE_TO_FORTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FORTY_TO_FORTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ONE_TO_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.SIXTY_TO_SIXTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TEN_TO_FOURTEEN;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.THIRTY_FIVE_TO_THIRTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.THIRTY_TO_THRITY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TWENTY_FIVE_TO_TWENTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TWENTY_TO_TWENTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.UNDER_ONE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.UNKNOWN;

import java.util.Arrays;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxNewCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.dimensions.IcapDsdDimensions;
import org.openmrs.module.eptsreports.reporting.library.dimensions.KeyPopulationDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.library.queries.DSDQueriesInterface.QUERY.DSDModeTypeLevel1;
import org.openmrs.module.eptsreports.reporting.utils.AgeRange;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.Gender;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** @author Stélio Moiane */
@Component
public class TxNewIcapDsdDataset extends BaseDataSet {

  @Autowired private TxNewCohortQueries txNewCohortQueries;

  @Autowired private EptsCommonDimension eptsCommonDimension;

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired
  @Qualifier("txNewAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  @Autowired private KeyPopulationDimension keyPopulationDimension;

  @Autowired private IcapDsdDimensions icapDsdDimensions;

  public DataSetDefinition constructTxNewDataset() {

    final CohortIndicatorDataSetDefinition dataSetDefinition =
        new CohortIndicatorDataSetDefinition();

    dataSetDefinition.setName("TX_NEW Data Set");
    dataSetDefinition.addParameters(this.getParameters());

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    final CohortDefinition patientEnrolledInART =
        this.txNewCohortQueries.getTxNewCompositionCohort("patientEnrolledInART");

    final CohortIndicator patientEnrolledInHIVStartedARTIndicator =
        this.eptsGeneralIndicator.getIndicator(
            "patientNewlyEnrolledInHIVIndicator",
            EptsReportUtils.map(patientEnrolledInART, mappings));

    dataSetDefinition.addDimension(
        "breastfeeding",
        EptsReportUtils.map(this.eptsCommonDimension.findPatientsWhoAreBreastfeeding(), mappings));

    this.addDimensions(
        dataSetDefinition,
        mappings,
        UNDER_ONE,
        ONE_TO_FOUR,
        FIVE_TO_NINE,
        TEN_TO_FOURTEEN,
        FIFTEEN_TO_NINETEEN,
        TWENTY_TO_TWENTY_FOUR,
        TWENTY_FIVE_TO_TWENTY_NINE,
        THIRTY_TO_THRITY_FOUR,
        THIRTY_FIVE_TO_THIRTY_NINE,
        FORTY_TO_FORTY_FOUR,
        FORTY_FIVE_TO_FORTY_NINE,
        ABOVE_FIFTY,
        FIFTY_TO_FIFTY_FOUR,
        FIFTY_FIVE_TO_FIFTY_NINE,
        SIXTY_TO_SIXTY_FOUR,
        ABOVE_SIXTY_FIVE);

    dataSetDefinition.addDimension(
        "gender", EptsReportUtils.map(this.eptsCommonDimension.gender(), ""));

    dataSetDefinition.addDimension(
        this.getColumnName(AgeRange.UNKNOWN, Gender.MALE),
        EptsReportUtils.map(
            this.eptsCommonDimension.findPatientsWithUnknownAgeByGender(
                this.getColumnName(AgeRange.UNKNOWN, Gender.MALE), Gender.MALE),
            ""));

    dataSetDefinition.addDimension(
        this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE),
        EptsReportUtils.map(
            this.eptsCommonDimension.findPatientsWithUnknownAgeByGender(
                this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE), Gender.FEMALE),
            ""));

    dataSetDefinition.addDimension(
        "homosexual",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreHomosexual(), mappings));

    dataSetDefinition.addDimension(
        "drug-user",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoUseDrugs(), mappings));

    dataSetDefinition.addDimension(
        "prisioner",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreInPrison(), mappings));

    dataSetDefinition.addDimension(
        "sex-worker",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreSexWorker(), mappings));

    /* Removido *Pedido feito pelo Nacassaco*
        dataSetDefinition.addDimension(
            "dca",
            EptsReportUtils.map(
                this.icapDsdDimensions.findMDS("dca", DSDModeTypeLevel1.DCA_APE), mappings));

        dataSetDefinition.addDimension(
            "dcp",
            EptsReportUtils.map(
                this.icapDsdDimensions.findMDS("dcp", DSDModeTypeLevel1.DCP), mappings));


        dataSetDefinition.addDimension(
             "gaac",
             EptsReportUtils.map(
                    this.icapDsdDimensions.findMDS("gaac", DSDModelTypeLevel2.GAAC), mappings));
    */
    dataSetDefinition.addDimension(
        "bm",
        EptsReportUtils.map(this.icapDsdDimensions.findMDS("bm", DSDModeTypeLevel1.BM), mappings));

    dataSetDefinition.addDimension(
        "cm",
        EptsReportUtils.map(this.icapDsdDimensions.findMDS("cm", DSDModeTypeLevel1.CM), mappings));

    dataSetDefinition.addColumn(
        "1All",
        "TX_NEW: New on ART",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "");

    dataSetDefinition.addColumn(
        "ANC",
        "TX_NEW: Breastfeeding Started ART",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "breastfeeding=breastfeeding");

    //  final List<String> models = Arrays.asList("dca", "dcp", "bm", "cm", "gaac");

    final List<String> models = Arrays.asList("bm", "cm");

    for (final String mds : models) {
      dataSetDefinition.addColumn(
          "N1All-" + mds,
          "TX_NEW: New on ART",
          EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
          mds + "=" + mds);
    }

    this.addColums(
        dataSetDefinition,
        mappings,
        patientEnrolledInHIVStartedARTIndicator,
        UNDER_ONE,
        ONE_TO_FOUR,
        FIVE_TO_NINE,
        TEN_TO_FOURTEEN,
        FIFTEEN_TO_NINETEEN,
        TWENTY_TO_TWENTY_FOUR,
        TWENTY_FIVE_TO_TWENTY_NINE,
        THIRTY_TO_THRITY_FOUR,
        THIRTY_FIVE_TO_THIRTY_NINE,
        FORTY_TO_FORTY_FOUR,
        FORTY_FIVE_TO_FORTY_NINE,
        ABOVE_FIFTY,
        FIFTY_TO_FIFTY_FOUR,
        FIFTY_FIVE_TO_FIFTY_NINE,
        SIXTY_TO_SIXTY_FOUR,
        ABOVE_SIXTY_FIVE);

    this.addColums(dataSetDefinition, "", patientEnrolledInHIVStartedARTIndicator, UNKNOWN);

    dataSetDefinition.addColumn(
        "N-MSM",
        "Homosexual",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "gender=M|homosexual=homosexual");

    dataSetDefinition.addColumn(
        "N-PWID",
        "Drugs User",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "drug-user=drug-user");

    dataSetDefinition.addColumn(
        "N-PRI",
        "Prisioners",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "prisioner=prisioner");

    dataSetDefinition.addColumn(
        "N-FSW",
        "Sex Worker",
        EptsReportUtils.map(patientEnrolledInHIVStartedARTIndicator, mappings),
        "gender=F|sex-worker=sex-worker");

    this.addColums(
        dataSetDefinition,
        mappings,
        patientEnrolledInHIVStartedARTIndicator,
        models,
        UNDER_ONE,
        ONE_TO_FOUR,
        FIVE_TO_NINE,
        TEN_TO_FOURTEEN,
        FIFTEEN_TO_NINETEEN,
        TWENTY_TO_TWENTY_FOUR,
        TWENTY_FIVE_TO_TWENTY_NINE,
        THIRTY_TO_THRITY_FOUR,
        THIRTY_FIVE_TO_THIRTY_NINE,
        FORTY_TO_FORTY_FOUR,
        FORTY_FIVE_TO_FORTY_NINE,
        ABOVE_FIFTY,
        FIFTY_TO_FIFTY_FOUR,
        FIFTY_FIVE_TO_FIFTY_NINE,
        SIXTY_TO_SIXTY_FOUR,
        ABOVE_SIXTY_FIVE,
        UNKNOWN);

    return dataSetDefinition;
  }

  private void addColums(
      final CohortIndicatorDataSetDefinition dataSetDefinition,
      final String mappings,
      final CohortIndicator cohortIndicator,
      final AgeRange... rannges) {

    for (final AgeRange range : rannges) {

      String params = mappings;

      if (UNKNOWN.equals(range)) {
        params = "";
      }

      final String maleName = this.getColumnName(range, Gender.MALE);
      final String femaleName = this.getColumnName(range, Gender.FEMALE);

      dataSetDefinition.addColumn(
          maleName,
          maleName.replace("-", " "),
          EptsReportUtils.map(cohortIndicator, params),
          maleName + "=" + maleName);

      dataSetDefinition.addColumn(
          femaleName,
          femaleName.replace("-", " "),
          EptsReportUtils.map(cohortIndicator, params),
          femaleName + "=" + femaleName);
    }
  }

  private void addDimensions(
      final CohortIndicatorDataSetDefinition cohortIndicatorDataSetDefinition,
      final String mappings,
      final AgeRange... ranges) {

    for (final AgeRange range : ranges) {

      cohortIndicatorDataSetDefinition.addDimension(
          this.getColumnName(range, Gender.MALE),
          EptsReportUtils.map(
              this.eptsCommonDimension.findPatientsWhoAreNewlyEnrolledOnArtByAgeAndGender(
                  this.getColumnName(range, Gender.MALE), range, Gender.MALE.getName()),
              mappings));

      cohortIndicatorDataSetDefinition.addDimension(
          this.getColumnName(range, Gender.FEMALE),
          EptsReportUtils.map(
              this.eptsCommonDimension.findPatientsWhoAreNewlyEnrolledOnArtByAgeAndGender(
                  this.getColumnName(range, Gender.FEMALE), range, Gender.FEMALE.getName()),
              mappings));
    }
  }

  private String getColumnName(final AgeRange range, final Gender gender) {
    return range.getDesagregationColumnName("N", gender);
  }

  private void addColums(
      final CohortIndicatorDataSetDefinition dataSetDefinition,
      final String mappings,
      final CohortIndicator cohortIndicator,
      final List<String> mdsModels,
      final AgeRange... ranges) {

    for (final AgeRange range : ranges) {

      for (final String mds : mdsModels) {

        final String maleName = this.getColumnName(range, Gender.MALE);
        final String femaleName = this.getColumnName(range, Gender.FEMALE);

        dataSetDefinition.addColumn(
            maleName + "-" + mds,
            (maleName + mds).replace("-", " "),
            EptsReportUtils.map(cohortIndicator, mappings),
            maleName + "=" + maleName + "|" + mds + "=" + mds);

        dataSetDefinition.addColumn(
            femaleName + "-" + mds,
            (femaleName + mds).replace("-", " "),
            EptsReportUtils.map(cohortIndicator, mappings),
            femaleName + "=" + femaleName + "|" + mds + "=" + mds);
      }
    }
  }
}