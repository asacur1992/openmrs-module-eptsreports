package org.openmrs.module.eptsreports.reporting.library.datasets.data.quality.duplicate.ficharesumo;

import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryDataQualityDuplicateFichaResumoDataset extends BaseDataSet {

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired
  private EC1PatientListDuplicateFichaResumoDataset eC1PatientListDuplicateFichaResumoDataset;

  @Autowired
  private EC2PatientListDuplicateFichaResumoDataset eC2PatientListDuplicateFichaResumoDataset;

  @Autowired
  private EC3PatientListDuplicateFichaResumoDataset eC3PatientListDuplicateFichaResumoDataset;

  @Autowired
  private EC4PatientListDuplicateFichaResumoDataset eC4PatientListDuplicateFichaResumoDataset;

  @Autowired
  private EC5PatientListDuplicateFichaResumoDataset eC5PatientListDuplicateFichaResumoDataset;

  @Autowired
  private EC6PatientListDuplicateFichaResumoDataset eC6PatientListDuplicateFichaResumoDataset;

  public DataSetDefinition constructSummaryDataQualityDatset(List<Parameter> parameterList) {
    CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
    dsd.setName("Data Quality Duplicated Ficha Resumo Summary Dataset");
    final String mappings = "location=${location}";

    final CohortDefinition summaryCohortQueryEC1 =
        eC1PatientListDuplicateFichaResumoDataset.getEC1Total(parameterList);

    final CohortDefinition summaryCohortQueryEC2 =
        eC2PatientListDuplicateFichaResumoDataset.getEC2Total(parameterList);

    final CohortDefinition summaryCohortQueryEC3 =
        eC3PatientListDuplicateFichaResumoDataset.getEC3Total(parameterList);

    final CohortDefinition summaryCohortQueryEC4 =
        eC4PatientListDuplicateFichaResumoDataset.getEC4Total(parameterList);

    final CohortDefinition summaryCohortQueryEC5 =
        eC5PatientListDuplicateFichaResumoDataset.getEC5Total(parameterList);

    final CohortDefinition summaryCohortQueryEC6 =
        eC6PatientListDuplicateFichaResumoDataset.getEC6Total(parameterList);

    dsd.addParameters(parameterList);
    dsd.addColumn(
        "EC1D-TOTAL",
        "EC1D: patients with more than on Ficha Resumo",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC1Indicator",
                EptsReportUtils.map(summaryCohortQueryEC1, mappings)),
            mappings),
        "");

    dsd.addColumn(
        "EC2D-TOTAL",
        "EC2D: The patient has clinical consultations or drug pick-ups registered but no Ficha Resumo",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC2Indicator",
                EptsReportUtils.map(summaryCohortQueryEC2, mappings)),
            mappings),
        "");

    dsd.addColumn(
        "EC3D-TOTAL",
        "EC3D: The patient has more than one Ficha Clínica registered in SESP on the same date and at the same Health Facility. ",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC3Indicator",
                EptsReportUtils.map(summaryCohortQueryEC3, mappings)),
            mappings),
        "");

    dsd.addColumn(
        "EC4D-TOTAL",
        "EC4D: The patient has more than one Fila registered in SESP on the same date and at the same Health Facility. ",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC3Indicator",
                EptsReportUtils.map(summaryCohortQueryEC4, mappings)),
            mappings),
        "");

    dsd.addColumn(
        "EC5D-TOTAL",
        "EC5D: The patient has more than one Ficha Recepcao Levantou registered in SESP on the same date and at the same Health Facility. ",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC5Indicator",
                EptsReportUtils.map(summaryCohortQueryEC5, mappings)),
            mappings),
        "");

    dsd.addColumn(
        "EC6D-TOTAL",
        "EC6D: The patient has more than one Ficha Recepcao Levantou registered in SESP on the same date and at the same Health Facility. ",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "summaryCohortQueryEC6Indicator",
                EptsReportUtils.map(summaryCohortQueryEC6, mappings)),
            mappings),
        "");

    return dsd;
  }
}
