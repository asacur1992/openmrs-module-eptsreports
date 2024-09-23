package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P1_1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P4CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P1_1DataSet extends MQAbstractDataSet {

  @Autowired private MICategory13P1_1CohortQueries mICategory13P1_1CohortQueries;
  @Autowired private MICategory13P3CohortQueries mICategory13P3CohortQueries;
  @Autowired private MICategory13P4CohortQueries miCategory13P4CohortQueries;
  private final boolean EXCLUDE_TB_ACTIVE_DIAGNOSTIC = true;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT13CV15PLUSNUMERATOR",
        "13.1:  # Adultos (15/+anos) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findFinalNumeratorCategory13SectionIC(),
                "CAT13CV15PLUSNUMERATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13CV15PLUSDENOMINATOR",
        "13.1:  # Adultos (15/+anos) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findDenominatorCategory13SectionIB(
                    EXCLUDE_TB_ACTIVE_DIAGNOSTIC),
                "CAT13CV15PLUSDENOMINATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13CV13_1PLUS13_4NUMERATOR",
        "13.1: % de adultos (15/+anos)  em TARV na 1ª(13.1) ou 2ª(13.4) linha com consulta clínica no período de avaliação, elegíveis ao pedido de CV Numerador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries
                    .findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Numerator(),
                "CAT13CV13_1PLUS13_4NUMERATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13CV13_1PLUS13_4DENOMINATOR",
        "13.1: % de adultos (15/+anos)  em TARV na 1ª(13.1) ou 2ª(13.4) linha com consulta clínica no período de avaliação, elegíveis ao pedido de CV Denominador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries
                    .findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Denominator(
                        EXCLUDE_TB_ACTIVE_DIAGNOSTIC),
                "CAT13CV13_1PLUS13_4DENOMINATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_2_NUMERATOR",
        "13.2: # Adultos (15/+anos) na 1a linha de TARV que receberam o resultado da CV entre o sexto e o nono mês após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Numerador(),
                "CAT13_PART_3_13_2_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_2_DENOMINATOR",
        "13.2: # Adultos (15/+anos) na 1a linha de TARV que receberam o resultado da CV entre o sexto e o nono mês após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Denominador(
                        EXCLUDE_TB_ACTIVE_DIAGNOSTIC),
                "CAT13_PART_3_13_2_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");

    dataSetDefinition.addColumn(
        "CAT13_CV_13-2_13-5_NUMERATOR",
        "13.2: % de adultos (15/+anos) em TARV (1a ou 2ª linha) 13.2-1ª Linha + 13.5-2ª Linha Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Numerator(),
                "CAT13_CV_13-2_13-5_NUMERATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13_CV_13-2_13-5_DENOMINATOR",
        "13.2: % de adultos (15/+anos) em TARV (1a ou 2ª linha) 13.2-1ª Linha + 13.5-2ª Linha Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Denominator(),
                "CAT13_CV_13-2_13-5_DENOMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P4AdultNUMINATOR",
        "13.3: Adultos (15/+anos) na 1ª linha de TARV com registo de pedido de CV entre o 3º e o 4º mês "
            + "após terem recebido o último resultado de CV ≥1000 cps/ml",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPatientsWhoReceivedResultMoreThan1000CVCategory13P3Numerator(),
                "CAT13P4AdultNUMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");

    dataSetDefinition.addColumn(
        "CAT13P4AdultDENUMINATOR",
        "13.3: Adultos (15/+anos) na 1ª linha de TARV com registo resultado de CV acima de 1000 ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPatietsOnARTStartedExcludingPregantAndBreastfeedingAndTransferredInTRANSFEREDOUTWITH1000CVCategory11DenominatorAdult(),
                "CAT13P4AdultDENUMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");
  }
}
