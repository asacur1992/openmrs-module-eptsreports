package org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory14CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MQCategory14DataSet extends MQAbstractDataSet {

  @Autowired private MQCategory14CohortQueries mqCohortQueryCategory14;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  public void constructTMqDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT14_MENOR_1_INDICATOR_DENOMINATOR",
        "14.1: % de utentes (<1 ano) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_MENOR_1_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=<1");

    dataSetDefinition.addColumn(
        "CAT14_1_4_INDICATOR_DENOMINATOR",
        "14.2: % de utentes (1- 4 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_1_4_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=1-4");

    dataSetDefinition.addColumn(
        "CAT14_5_9_INDICATOR_DENOMINATOR",
        "14.3: % de utentes (5 - 9 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_5_9_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=5-9");

    dataSetDefinition.addColumn(
        "CAT14_10_14_INDICATOR_DENOMINATOR",
        "14.4: % de utentes (10 - 14 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_10_14_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=10-14");

    dataSetDefinition.addColumn(
        "CAT14_15_19_INDICATOR_DENOMINATOR",
        "14.4: % de utentes (15 -19 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_15_19_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=15-19");

    dataSetDefinition.addColumn(
        "CAT14_20PLUS_INDICATOR_DENOMINATOR",
        "14.4: % de utentes (20/+ anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getDenominatorCategory14Indicator(),
                "CAT14_20PLUS_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=20+");

    dataSetDefinition.addColumn(
        "CAT14_MG_INDICATOR_DENOMINATOR",
        "14.13:Mulher Gravida em TARV com supressão viral Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getPregnantDenominatorCategory14Indicator(),
                "CAT14_MG_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT14_ML_INDICATOR_DENOMINATOR",
        "14.4: Mulher Lactante em TARV com supressão viral Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getBreastfeedingDenominatorCategory14Indicator(),
                "CAT14_ML_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT14_DSD_INDICATOR_DENOMINATOR",
        "15.16: % de utentes inscritos em MDS (para pacientes estáveis) com supressão viral Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14
                    .getPatientsInDSDWithViralSupressionDenominatorCategory14Indicator(),
                "CAT14_DSD_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "");

    // Numerator

    dataSetDefinition.addColumn(
        "CAT14_MENOR_1_INDICATOR_NUMERATOR",
        "14.1: % de utentes (<1 ano) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_MENOR_1_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "ageMq=<1");

    dataSetDefinition.addColumn(
        "CAT14_1_4_INDICATOR_NUMERATOR",
        "14.2: % de utentes (1- 4 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_1_4_INDICATOR_DENOMINATOR",
                mappings),
            mappings),
        "ageMq=1-4");

    dataSetDefinition.addColumn(
        "CAT14_5_9_INDICATOR_NUMERATOR",
        "14.3: % de utentes (5 - 9 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_5_9_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "ageMq=5-9");

    dataSetDefinition.addColumn(
        "CAT14_10_14_INDICATOR_NUMERATOR",
        "14.4: % de utentes (10 - 14 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_10_14_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "ageMq=10-14");

    dataSetDefinition.addColumn(
        "CAT14_15_19_INDICATOR_DENOMINATOR",
        "14.4: % de utentes (15 -19 anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_15_19_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "ageMq=15-19");

    dataSetDefinition.addColumn(
        "CAT14_20PLUS_INDICATOR_NUMERATOR",
        "14.4: % de utentes (20/+ anos) em TARV com supressão viral (CV<1000 Cps/ml) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getNumeratorCategory14Indicator(),
                "CAT14_20PLUS_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "ageMq=20+");

    dataSetDefinition.addColumn(
        "CAT14_MG_INDICATOR_NUMERATOR",
        "14.13:Mulher Gravida em TARV com supressão viral Numerator",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getPregnantNumeratorCategory14Indicator(),
                "CAT14_MG_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT14_ML_INDICATOR_NUMERATOR",
        "14.4: Mulher Lactante em TARV com supressão viral Numerator",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14.getBreastfeedingNumeratorCategory14Indicator(),
                "CAT14_ML_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT14_DSD_INDICATOR_NUMERATOR",
        "15.16: % de utentes inscritos em MDS (para pacientes estáveis) com supressão viral Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mqCohortQueryCategory14
                    .getPatientsInDSDWithViralSupressionNumeratorCategory14Indicator(),
                "CAT14_DSD_INDICATOR_NUMERATOR",
                mappings),
            mappings),
        "");
  }
}
