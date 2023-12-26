package org.openmrs.module.eptsreports.reporting.library.datasets;

import java.util.Arrays;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TB7AdvancedDiseaseAndTBCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.LevelOfPositivity;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TB7AdvancedDiseaseAndTBDataSet extends BaseDataSet {

  @Autowired private TB7AdvancedDiseaseAndTBCohortQueries tb7AdvancedDiseaseAndTBCohortQueries;
  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  @Autowired private EptsCommonDimension eptsCommonDimension;

  public DataSetDefinition constructDataset() {

    final CohortIndicatorDataSetDefinition dataSetDefinition =
        new CohortIndicatorDataSetDefinition();
    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    dataSetDefinition.setName("TX_PVLS Data Set");

    dataSetDefinition.addParameters(this.getParameters());

    dataSetDefinition.addDimension("gender", EptsReportUtils.map(eptsCommonDimension.gender(), ""));

    dataSetDefinition.addDimension(
        "age",
        EptsReportUtils.map(
            eptsCommonDimension.age(ageDimensionCohort), "effectiveDate=${endDate}"));

    dataSetDefinition.setName("TB7");
    dataSetDefinition.addParameters(this.getParameters());

    final CohortDefinition tb7CohortIndicator =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsEligibleForCD4CountRequestDuringInclusionPeriodIndicator1();

    final CohortDefinition tb7CohortIndicator2 =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator2();

    final CohortDefinition tb7CohortIndicator3 =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3();

    final CohortDefinition tb7CohortIndicator3TbLam =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithTBLam();

    final CohortDefinition tb7CohortIndicator3WithoutTbLam =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam();

    final CohortDefinition tb7CohortIndicator3WithoutCd4WithTbLam =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getumberOfClientsWithCD4CountDuringInclusionPeriodWithoutSevereImmunodepression();

    final CohortDefinition tb7CohortIndicator3WithoutCd4WithTbLamTotal =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithTBLam();

    final CohortDefinition tb7CohortIndicator3WithoutCd4WithoutTbLamTotal =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam();

    final CohortDefinition patienWithoutCD4CountButWithTBLAMResults =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithoutCD4CountButWithTBLAMResultsDuringTheInclusionPeriodIncludingClientsWhoWereNotEligibleForCD4();

    final CohortDefinition numberOfClientsWithTBLAMResultsByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithTBLAMResultsByReportGenerationDate();

    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate();

    final CohortDefinition numberOfClientsWithNegativeTBLAMResultsByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithNegativeTBLAMResultsByReportGenerationDate();

    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsGrade4ByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
                LevelOfPositivity.Grade_4);
    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsGrade3ByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
                LevelOfPositivity.Grade_3);
    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsGrade2ByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
                LevelOfPositivity.Grade_2);

    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsGrade1ByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
                LevelOfPositivity.Grade_1);

    final CohortDefinition numberOfClientsWithPositiveTBLAMResultsGradeBlankByReportGenerationDate =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
                LevelOfPositivity.Blank);

    final CohortDefinition clientsWithoutPositiveTBLAMButNotTestedWithGeneXpert =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getClientsWithoutPositiveTBLAMButNotTestedWithGeneXpert();

    final CohortDefinition clientsWithPositiveTBLAMButNotTestedWithGeneXpert =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getClientsWithPositiveTBLAMAndAlsoTestedWithGeneXpert();

    final CohortDefinition clientsWithPositiveTBLAMWithGeneXpertAndTb =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTB();

    final CohortDefinition clientsWithPositiveTBLAMAndGeneXpertPositiveforTb =
        this.tb7AdvancedDiseaseAndTBCohortQueries
            .getClientsWithPositiveTBLAMAndGeneXpertPositiveforTB();
    final CohortDefinition
        clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade4ByReportGenerationDate =
            this.tb7AdvancedDiseaseAndTBCohortQueries
                .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
                    LevelOfPositivity.Grade_4);
    final CohortDefinition
        clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade3ByReportGenerationDate =
            this.tb7AdvancedDiseaseAndTBCohortQueries
                .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
                    LevelOfPositivity.Grade_3);
    final CohortDefinition
        clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade2ByReportGenerationDate =
            this.tb7AdvancedDiseaseAndTBCohortQueries
                .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
                    LevelOfPositivity.Grade_2);

    final CohortDefinition
        clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade1ByReportGenerationDate =
            this.tb7AdvancedDiseaseAndTBCohortQueries
                .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
                    LevelOfPositivity.Grade_1);

    final CohortDefinition
        clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGradeBlankByReportGenerationDate =
            this.tb7AdvancedDiseaseAndTBCohortQueries
                .getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
                    LevelOfPositivity.Blank);

    final CohortIndicator tb7Indicator1 =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator, mappings));

    final CohortIndicator tb7Indicator2 =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator2, mappings));

    final CohortIndicator tb7Indicator3 =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3, mappings));

    final CohortIndicator tb7Indicator3TbLam =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3TbLam, mappings));

    final CohortIndicator tb7Indicator3WithoutTbLam =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3WithoutTbLam, mappings));

    final CohortIndicator tb7Indicator3WithoutCd4WithTbLam =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3WithoutCd4WithTbLam, mappings));

    final CohortIndicator tb7Indicator3WithoutCd4WithTbLamTotal =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3WithoutCd4WithTbLamTotal, mappings));

    final CohortIndicator tb7Indicator3WithoutCd4WithoutTbLamTotal =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(tb7CohortIndicator3WithoutCd4WithoutTbLamTotal, mappings));

    final CohortIndicator tb7IndicatorpatienWithoutCD4CountButWithTBLAMResults =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(patienWithoutCD4CountButWithTBLAMResults, mappings));

    final CohortIndicator tb7IndicatorNumberOfClientsWithTBLAMResultsByReportGenerationDate =
        this.eptsGeneralIndicator.getIndicator(
            "TB7",
            EptsReportUtils.map(numberOfClientsWithTBLAMResultsByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithNegativeTBLAMResultsByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithNegativeTBLAMResultsByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade4ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsGrade4ByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade3ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsGrade3ByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade2ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsGrade2ByReportGenerationDate, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade1ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsGrade1ByReportGenerationDate, mappings));

    final CohortIndicator tb7IndicatorclientsWithoutPositiveTBLAMButNotTestedWithGeneXpert =
        this.eptsGeneralIndicator.getIndicator(
            "TB7",
            EptsReportUtils.map(clientsWithoutPositiveTBLAMButNotTestedWithGeneXpert, mappings));

    final CohortIndicator tb7IndicatorclientsWithPositiveTBLAMButNotTestedWithGeneXpert =
        this.eptsGeneralIndicator.getIndicator(
            "TB7",
            EptsReportUtils.map(clientsWithPositiveTBLAMButNotTestedWithGeneXpert, mappings));

    final CohortIndicator tb7IndicatorClientsWithPositiveTBLAMWithGeneXpertAndTb =
        this.eptsGeneralIndicator.getIndicator(
            "TB7", EptsReportUtils.map(clientsWithPositiveTBLAMWithGeneXpertAndTb, mappings));

    final CohortIndicator tb7IndicatorClientsWithPositiveTBLAMAndGeneXpertPositiveforTb =
        this.eptsGeneralIndicator.getIndicator(
            "TB7",
            EptsReportUtils.map(clientsWithPositiveTBLAMAndGeneXpertPositiveforTb, mappings));

    final CohortIndicator
        tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGradeBlankByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    numberOfClientsWithPositiveTBLAMResultsGradeBlankByReportGenerationDate,
                    mappings));

    final CohortIndicator
        tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade4ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade4ByReportGenerationDate,
                    mappings));

    final CohortIndicator
        tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade3ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade3ByReportGenerationDate,
                    mappings));

    final CohortIndicator
        tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade2ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade2ByReportGenerationDate,
                    mappings));

    final CohortIndicator
        tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade1ByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade1ByReportGenerationDate,
                    mappings));

    final CohortIndicator
        tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGradeBlankByReportGenerationDate =
            this.eptsGeneralIndicator.getIndicator(
                "TB7",
                EptsReportUtils.map(
                    clientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGradeBlankByReportGenerationDate,
                    mappings));

    dataSetDefinition.addColumn(
        "TI1",
        "TI1-Number of clients eligible for CD4 count during the inclusion period",
        EptsReportUtils.map(tb7Indicator1, mappings),
        "");

    addRow(
        dataSetDefinition,
        "EL",
        "Number of clients eligible for CD4 count during the inclusion period",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients eligible for CD4 count during the inclusion period",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsEligibleForCD4CountRequestDuringInclusionPeriodIndicator1(),
                    mappings)),
            mappings),
        getColumns());

    dataSetDefinition.addColumn(
        "TI2",
        "TI2-Number of clients eligible for CD4 count who have a CD4 count result during the inclusion period",
        EptsReportUtils.map(tb7Indicator2, mappings),
        "");

    addRow(
        dataSetDefinition,
        "RL",
        "Number of clients eligible for CD4 count who have a CD4 count result during the inclusion period",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients eligible for CD4 count who have a CD4 count result during the inclusion period",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator2(),
                    mappings)),
            mappings),
        getColumns());

    dataSetDefinition.addColumn(
        "TI3",
        "TI3-Number of clients with CD4 count during inclusion period showing severe immunodepression",
        EptsReportUtils.map(tb7Indicator3, mappings),
        "");
    dataSetDefinition.addColumn(
        "TITBLAM3",
        "TITBLAM3-Number of clients with CD4 count during inclusion period showing severe immunodepressio(With TBLam)",
        EptsReportUtils.map(tb7Indicator3TbLam, mappings),
        "");
    dataSetDefinition.addColumn(
        "TINTBLAM3",
        "TINTBLAM3-Number of clients with CD4 count during inclusion period showing severe immunodepressio(Without TBLam)",
        EptsReportUtils.map(tb7Indicator3WithoutTbLam, mappings),
        "");

    addRow(
        dataSetDefinition,
        "RL2",
        "Number of clients with CD4 count during inclusion period showing severe immunodepression  "
            + "(CD4 count < 200/mm3  for patients > =5 , <500/mm3 for children 1-4, <750/mm3 for children under 12 months) ",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients with CD4 count during inclusion period showing severe immunodepression  "
                    + "(CD4 count < 200/mm3  for patients > =5 , <500/mm3 for children 1-4, <750/mm3 for children under 12 months) ",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithTBLam(),
                    mappings)),
            mappings),
        getColumns());

    addRow(
        dataSetDefinition,
        "NRL2",
        "Number of clients with CD4 count during inclusion period showing severe immunodepression  "
            + "(CD4 count < 200/mm3  for patients > =5 , <500/mm3 for children 1-4, <750/mm3 for children under 12 months) ",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients with CD4 count during inclusion period showing severe immunodepression  "
                    + "(CD4 count < 200/mm3  for patients > =5 , <500/mm3 for children 1-4, <750/mm3 for children under 12 months) ",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam(),
                    mappings)),
            mappings),
        getColumns());

    dataSetDefinition.addColumn(
        "TINCD4TBLAM3",
        "TINCD4TBLAM3 - Indicator 3 without cd4 with TBLAM",
        EptsReportUtils.map(tb7Indicator3WithoutCd4WithTbLam, mappings),
        "");

    dataSetDefinition.addColumn(
        "TINOTCD4TBLAM",
        "TINOTCD4TBLAM - Indicator 3 without cd4 with TBLAM(Total)",
        EptsReportUtils.map(tb7Indicator3WithoutCd4WithTbLamTotal, mappings),
        "");

    dataSetDefinition.addColumn(
        "TINOTCD4NOTTBLAM",
        "TINOTCD4NOTTBLAM - Indicator 3 without cd4 without TBLAM(Total)",
        EptsReportUtils.map(tb7Indicator3WithoutCd4WithoutTbLamTotal, mappings),
        "");

    addRow(
        dataSetDefinition,
        "CDNRL2",
        "Number of clients with CD4 count during inclusion period without severe immunodepression  (CD4 count > = 200/mm3 for patients >5, > = 500 for children 1-5, > =750/mm3 for children <12 months)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients with CD4 count during inclusion period without severe immunodepression  (CD4 count > = 200/mm3 for patients >5, > = 500 for children 1-5, > =750/mm3 for children <12 months)",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithTBLam(),
                    mappings)),
            mappings),
        getColumns());

    dataSetDefinition.addColumn(
        "TBLAM1T",
        "TBLAM1T - Number of clients without CD4 count but with TB LAM results during the inclusion period (including clients who were not eligible for CD4(Total)",
        EptsReportUtils.map(tb7IndicatorpatienWithoutCD4CountButWithTBLAMResults, mappings),
        "");

    addRow(
        dataSetDefinition,
        "TBLAM1",
        "Number of clients without CD4 count but with TB LAM results during the inclusion period (including clients who were not eligible for CD4) ",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Number of clients without CD4 count but with TB LAM results during the inclusion period (including clients who were not eligible for CD4) ",
                EptsReportUtils.map(
                    tb7AdvancedDiseaseAndTBCohortQueries
                        .getNumberOfClientsWithoutCD4CountButWithTBLAMResultsDuringTheInclusionPeriodIncludingClientsWhoWereNotEligibleForCD4(),
                    mappings)),
            mappings),
        getColumns());

    dataSetDefinition.addColumn(
        "TBLAM2T",
        "TBLAM2T - Number of clients with TBLAM Results By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithTBLAMResultsByReportGenerationDate, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP2T",
        "TBLAMP2T - Number of clients with Positive TBLAM Results By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMN2T",
        "TBLAMN2T - Number of clients with Negative TBLAM Results By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithNegativeTBLAMResultsByReportGenerationDate, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP2L4T",
        "TBLAMP2L4T - Number of clients with Positive TBLAM Results Grade 4 By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade4ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP2L3T",
        "TBLAMP2L3T - Number of clients with Positive TBLAM Results Grade 3 By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade3ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP2L2T",
        "TBLAMP2L2T - Number of clients with Positive TBLAM Results Grade 2 By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade2ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP2L1T",
        "TBLAMP2L1T - Number of clients with Positive TBLAM Results Grade 1 By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGrade1ByReportGenerationDate,
            mappings),
        "");
    dataSetDefinition.addColumn(
        "TBLAMP2LBT",
        "TBLAMP2LBT - Number of clients with Positive TBLAM Results Grade Blank By Report Generation Date",
        EptsReportUtils.map(
            tb7IndicatorNumberOfClientsWithPositiveTBLAMResultsGradeBlankByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP3T",
        "TBLAMP3T - Number of clients with positive TB LAM but NOT tested with GeneXpert ",
        EptsReportUtils.map(
            tb7IndicatorclientsWithoutPositiveTBLAMButNotTestedWithGeneXpert, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP4T",
        "TBLAMP4T - Number of clients with positive TB LAM and also tested with GeneXpert",
        EptsReportUtils.map(
            tb7IndicatorclientsWithPositiveTBLAMButNotTestedWithGeneXpert, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP5T",
        "TBLAMP5T - Number of clients with positive TB LAM and GeneXpert positive for TB",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMAndGeneXpertPositiveforTb, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP6T",
        "TBLAMP6T - Number of clients with positive TB LAM and on TB treatment by report generation date",
        EptsReportUtils.map(tb7IndicatorClientsWithPositiveTBLAMWithGeneXpertAndTb, mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP7T",
        "TBLAMP7T - Number of clients with Positive TBLAM but not tested with genexpert for TB grade 4 by report generation date",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade4ByReportGenerationDate,
            mappings),
        "");
    dataSetDefinition.addColumn(
        "TBLAMP8T",
        "TBLAMP8T - Number of clients with Positive TBLAM but not tested with genexpert for TB grade 3 by report generation date",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade3ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP9T",
        "TBLAMP9T - Number of clients with Positive TBLAM but not tested with genexpert for TB grade 2 by report generation date",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade2ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP10T",
        "TBLAMP10T - Number of clients with Positive TBLAM but not tested with genexpert for TB grade 1 by report generation date",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGrade1ByReportGenerationDate,
            mappings),
        "");

    dataSetDefinition.addColumn(
        "TBLAMP11T",
        "TBLAMP11T - Number of clients with Positive TBLAM but not tested with genexpert for TB grade blank by report generation date",
        EptsReportUtils.map(
            tb7IndicatorClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeGradeBlankByReportGenerationDate,
            mappings),
        "");

    return dataSetDefinition;
  }

  private List<ColumnParameters> getColumns() {

    ColumnParameters a1 = new ColumnParameters("<1", "<1", "gender=F|age=<1", "01");
    ColumnParameters a2 = new ColumnParameters("1-4", "1-4 years female", "gender=F|age=1-4", "02");
    ColumnParameters a3 = new ColumnParameters("5-9", "5-9 years female", "gender=F|age=5-9", "03");
    ColumnParameters a4 = new ColumnParameters("10-14", "10-14 female", "gender=F|age=10-14", "04");
    ColumnParameters a5 = new ColumnParameters("15-19", "15-19 female", "gender=F|age=15-19", "05");
    ColumnParameters a6 = new ColumnParameters("20+", "20+ female", "gender=F|age=20+", "06");
    ColumnParameters unknownF =
        new ColumnParameters("unknownF", "Unknown age", "gender=F|age=UK", "07");

    ColumnParameters a7 = new ColumnParameters("<1", "<1 male", "gender=M|age=<1", "08");
    ColumnParameters a8 = new ColumnParameters("1-4", "1-4 years male", "gender=M|age=1-4", "09");
    ColumnParameters a9 = new ColumnParameters("5-9", "5-9 years male", "gender=M|age=5-9", "10");
    ColumnParameters a10 = new ColumnParameters("10-14", "10-14 male", "gender=M|age=10-14", "11");
    ColumnParameters a11 = new ColumnParameters("15-19", "15-19 male", "gender=M|age=15-19", "12");
    ColumnParameters a12 = new ColumnParameters("20+", "20+ male", "gender=M|age=20+", "13");
    ColumnParameters unknownM =
        new ColumnParameters("unknownF", "Unknown age", "gender=M|age=UK", "14");

    return Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, unknownF, a9, a10, a11, a12, unknownM);
  }
}
