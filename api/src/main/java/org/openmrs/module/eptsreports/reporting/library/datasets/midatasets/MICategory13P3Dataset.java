package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P4CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P3Dataset extends MQAbstractDataSet {

  @Autowired private MICategory13P3CohortQueries mICategory13P3CohortQueries;
  @Autowired private MICategory13P4CohortQueries miCategory13P4CohortQueries;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_9_NUMERATOR",
        "13.9: % Crianças  (0-4 anos de idade) na 1a linha de TARV que receberam o resultado da "
            + "Carga Viral entre o sexto e o nono mês após o início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Numerador(),
                "CAT13_PART_3_13_9_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=0-4");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_9_DENOMINATOR",
        "13.9: % Crianças  (0-4 anos de idade) na 1a linha de TARV que receberam o resultado da "
            + "Carga Viral entre o sexto e o nono mês após o início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Denominador(),
                "CAT13_PART_3_13_9_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=0-4");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_10_NUMERATOR",
        "13.10: % Crianças  (5-9 anos de idade) na 1a linha de TARV que receberam o resultado da "
            + "Carga Viral entre o sexto e o nono mês após o início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Numerador(),
                "CAT13_PART_3_13_10_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=5-9");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_10_DENOMINATOR",
        "13.10: % Crianças  (5-9 anos de idade) na 1a linha de TARV que receberam o resultado da "
            + "Carga Viral entre o sexto e o nono mês após o início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Denominador(),
                "CAT13_PART_3_13_10_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=5-9");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_11_NUMERATOR",
        "13.11: # Crianças  (10-14 anos de idade) na 1a linha de TARV que receberam o resultado da"
            + " Carga Viral entre o sexto e o nono mês após o início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Numerador(),
                "CAT13_PART_3_13_11_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=10-14");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_11_DENOMINATOR",
        "13.11: # Crianças  (10-14 anos de idade) na 1a linha de TARV que receberam o resultado da "
            + "Carga Viral entre o sexto e o nono mês após o início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Denominador(),
                "CAT13_PART_3_13_11_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=10-14");

    dataSetDefinition.addColumn(
        "CAT13_CV_13-11_13-14_NUMERATOR",
        "13.11: % de crianças (10-14 anos de idade) na 1ª(13.11) ou 2ª(13.14) linha de TARV que recebeu o resultado da Carga Viral entre o sexto e o nono mês após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Numerator(),
                "CAT13_CV_13-11_13-14_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=10-14");

    dataSetDefinition.addColumn(
        "CAT13_CV_13-11_13-14_DENOMINATOR",
        "13.11: % de crianças (10-14 anos de idade) na 1ª(13.11) ou 2ª(13.14) linha de TARV que recebeu o resultado da Carga Viral entre o sexto e o nono mês após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Denominator(),
                "CAT13_CV_13-11_13-14_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=10-14");

    dataSetDefinition.addColumn(
        "CAT13P4ChildrenNUMINATOR",
        "13.12: % Crianças (>2 anos de idade) na 1ª linha de TARV com registo de pedido de CV entre o 3º e o 4º mês "
            + "após terem recebido  o último resultado de CV ≥1000 Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPatientsWhoReceivedResultMoreThan1000CVCategory13P4Numerator(),
                "CAT13P4ChildrenNUMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=2-14");

    dataSetDefinition.addColumn(
        "CAT13P4ChildrenDENUMINATOR",
        "13.12: % Crianças (>2 anos de idade) na 1ª linha de TARV com registo de resultado de CV ≥1000 ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPatietsOnARTStartedExcludingPregantAndBreastfeedingAndTransferredInTRANSFEREDOUTWITH1000CVCategory11Denominator(),
                "CAT13P4ChildrenDENUMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=2-14");
  }
}
