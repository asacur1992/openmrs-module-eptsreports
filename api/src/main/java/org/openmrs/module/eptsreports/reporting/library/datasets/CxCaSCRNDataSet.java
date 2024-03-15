package org.openmrs.module.eptsreports.reporting.library.datasets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.CxCaSCRNCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.CxCxSRNPositiveCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CxCaSCRNDataSet extends BaseDataSet {

  @Autowired private EptsCommonDimension eptsCommonDimension;
  @Autowired private CxCaSCRNCohortQueries cxCaSCRNCohortQueries;
  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;
  @Autowired private CxCxSRNPositiveCohortQueries CxCxSRNPositiveCohortQueries;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  public DataSetDefinition constructDatset() {
    final CohortIndicatorDataSetDefinition dataSetDefinition =
        new CohortIndicatorDataSetDefinition();
    dataSetDefinition.setParameters(getParameters());

    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    dataSetDefinition.addDimension(
        "age",
        EptsReportUtils.map(
            eptsCommonDimension.ageCX(ageDimensionCohort), "effectiveDate=${endDate}"));

    dataSetDefinition.addDimension("gender", EptsReportUtils.map(eptsCommonDimension.gender(), ""));

    this.constructCxCaDataSet(dataSetDefinition, mappings);
    this.constructPositiveDataSet(dataSetDefinition, mappings);

    return dataSetDefinition;
  }

  private void constructCxCaDataSet(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {
    dataSetDefinition.addColumn(
        "CXTOTAL",
        "CXCA_SCRN: Number of individual HIV-positive women on ART who received a screening test for cervical cancer",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "CXCA_SCRN: Number of individual HIV-positive women on ART who received a screening test for cervical cancer",
                EptsReportUtils.map(this.cxCaSCRNCohortQueries.getTotalNumerator(), mappings)),
            mappings),
        "gender=F|age=15+");

    dataSetDefinition.addColumn(
        "CXFRT",
        "CXCA_SCRN: 1st time screened  total",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "1st time screened  total",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries.getTotalNumeratorFirstScreening(), mappings)),
            mappings),
        "gender=F|age=15+");

    addRow(
        dataSetDefinition,
        "CXN",
        "CXCA_SCRN: 1st time screened (Negative)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "1st time screened (Negative)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries.getTotalNumeratorFirstScreeningNegative(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "CXP",
        "CXCA_SCRN: 1st time screened (Positive)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "1st time screened (Positive)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries.getTotalNumeratorFirstScreeningPositive(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "CXS",
        "CXCA_SCRN: 1st time screened (Suspect Cancer)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "1st time screened (Suspect Cancer)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries.getTotalNumeratorFirstScreeningSuspectCancer(),
                    mappings)),
            mappings),
        getCXColumns());

    dataSetDefinition.addColumn(
        "CXRNT",
        "CXCA_SCRN: Rescreened after previous negative (Total)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Rescreened after previous negative (Total)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousNegativeTotal(),
                    mappings)),
            mappings),
        "gender=F|age=15+");

    addRow(
        dataSetDefinition,
        "RNN",
        "CXCA_SCRN: Rescreened after previous negative (Negative)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Rescreened after previous negative (Negative)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousNegativeNegative(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "RNP",
        "CXCA_SCRN: Rescreened after previous negative (Positive)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Rescreened after previous negative (Positive)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousNegativePositive(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "RNS",
        "CXCA_SCRN: Rescreened after previous negative (Suspect Cancer)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Suspect Cancer Numerator",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousNegativeSuspectCancer(),
                    mappings)),
            mappings),
        getCXColumns());

    dataSetDefinition.addColumn(
        "CXPTT",
        "CXCA_SCRN: Post-treatment follow-up (Total)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Total)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorfindpatientwithScreeningTypeVisitAsPostTreatmentFollowUpTotal(),
                    mappings)),
            mappings),
        "gender=F|age=15+");

    addRow(
        dataSetDefinition,
        "PTN",
        "CXCA_SCRN: Post-treatment follow-up (Negative)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Negative)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorfindpatientwithScreeningTypeVisitAsPostTreatmentFollowUpNegative(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "PTP",
        "CXCA_SCRN: Post-treatment follow-up (Positive)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Positive)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorfindpatientwithScreeningTypeVisitAsPostTreatmentFollowUpPositive(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "PTS",
        "CXCA_SCRN: Post-treatment follow-up (Suspect Cancer)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Suspect Cancer)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorfindpatientwithScreeningTypeVisitAsPostTreatmentFollowUpSuspectCancer(),
                    mappings)),
            mappings),
        getCXColumns());

    //    Recreated after screening positive

    dataSetDefinition.addColumn(
        "CXRPT",
        "CXCA_SCRN: Rescreened after previous positive (Total)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Total)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousPositiveTotal(),
                    mappings)),
            mappings),
        "gender=F|age=15+");

    addRow(
        dataSetDefinition,
        "RSPN",
        "CXCA_SCRN: Rescreened after previous positive (Negative)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Rescreened after previous positive (Negative)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousPositiveNegative(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "RSPP",
        "CXCA_SCRN: Rescreened after previous positive (Positive)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Positive)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousPositivePositive(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "RSPS",
        "CXCA_SCRN: Rescreened after previous positive (Suspect Cancer)",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up (Suspect Cancer)",
                EptsReportUtils.map(
                    this.cxCaSCRNCohortQueries
                        .getTotalNumeratorRescreenedAfterPreviousPositiveSuspectCancer(),
                    mappings)),
            mappings),
        getCXColumns());
  }

  private void constructPositiveDataSet(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {
    dataSetDefinition.addColumn(
        "CXPTTL",
        "CXCA_SCRN_POS: Number of HIV-positive women on ART screened for cervical cancer with a positive result",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "CXCA_SCRN_POS: Number of HIV-positive women on ART screened for cervical cancer with a positive result",
                EptsReportUtils.map(
                    this.CxCxSRNPositiveCohortQueries.findpatientwithCxCaPositiveTotal(),
                    mappings)),
            mappings),
        "gender=F|age=15+");

    addRow(
        dataSetDefinition,
        "CXPFR",
        "CXCA_SCRN_POS: 1st time screened ",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "1st time screened positive",
                EptsReportUtils.map(
                    this.CxCxSRNPositiveCohortQueries.findpatientwithCxCaPositiveFirstScreen(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "CXPRN",
        "CXCA_SCRN_POS: Rescreened after previous negative",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Rescreened after previous negative",
                EptsReportUtils.map(
                    this.CxCxSRNPositiveCohortQueries
                        .findpatientwithCxCaRescreenedAfterPreviousNegative(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "CXPPF",
        "CXCA_SCRN_POS: Post-treatment follow-up",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up",
                EptsReportUtils.map(
                    this.CxCxSRNPositiveCohortQueries
                        .findpatientwithCxPositivePostTreatmentFollowUp(),
                    mappings)),
            mappings),
        getCXColumns());

    addRow(
        dataSetDefinition,
        "CXPRSP",
        "CXCA_SCRN_POS: Rescreened after previous positive",
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                "Post-treatment follow-up",
                EptsReportUtils.map(
                    this.CxCxSRNPositiveCohortQueries
                        .findpatientwithCxPositiveRescreenedAfterPreviousPositive(),
                    mappings)),
            mappings),
        getCXColumns());
  }

  public List<Parameter> getParameters() {
    List<Parameter> parameters = new ArrayList<Parameter>();
    parameters.add(ReportingConstants.START_DATE_PARAMETER);
    parameters.add(ReportingConstants.END_DATE_PARAMETER);
    parameters.add(ReportingConstants.LOCATION_PARAMETER);
    return parameters;
  }

  private List<ColumnParameters> getCXColumns() {

    ColumnParameters a1 = new ColumnParameters("15-19", "15-19", "gender=F|age=15-19", "01");
    ColumnParameters a2 =
        new ColumnParameters("20-24", "20-24 years female", "gender=F|age=20-24", "02");
    ColumnParameters a3 =
        new ColumnParameters("25-29", "25-29 years female", "gender=F|age=25-29", "03");
    ColumnParameters a4 = new ColumnParameters("30-34", "30-34 female", "gender=F|age=30-34", "04");
    ColumnParameters a5 = new ColumnParameters("35-39", "35-39 female", "gender=F|age=35-39", "05");
    ColumnParameters a6 = new ColumnParameters("40-44", "40-44 female", "gender=F|age=40-44", "06");
    ColumnParameters a7 = new ColumnParameters("45-49", "45-49 female", "gender=F|age=45-49", "07");
    ColumnParameters a8 = new ColumnParameters("50-54", "50-54 female", "gender=F|age=50-54", "08");
    ColumnParameters a9 = new ColumnParameters("55-59", "55-59 female", "gender=F|age=55-59", "09");
    ColumnParameters a10 =
        new ColumnParameters("60-64", "60-64 female", "gender=F|age=60-64", "10");
    ColumnParameters a11 = new ColumnParameters("65+", "65+ female", "gender=F|age=65+", "11");
    ColumnParameters unknownF =
        new ColumnParameters("unknownF", "Unknown age", "gender=F|age=UK", "12");

    ColumnParameters a13 = new ColumnParameters("subTotal", "subTotal", "gender=F|age=15+", "13");

    return Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, unknownF, a9, a10, a11, a13);
  }
}
