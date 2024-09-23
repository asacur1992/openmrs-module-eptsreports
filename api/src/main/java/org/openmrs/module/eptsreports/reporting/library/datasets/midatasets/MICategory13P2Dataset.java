package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P1_2CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P2CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P2Dataset extends MQAbstractDataSet {

  @Autowired private MICategory13P2CohortQueries mICategory13P2CohortQueries;
  @Autowired private MICategory13P1_2CohortQueries mICategory13P1_2CohortQueries;
  @Autowired private MICategory13P3CohortQueries mICategory13P3CohortQueries;
  private final boolean EXCLUDE_TB_ACTIVE_DIAGNOSTIC = true;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT13CV24NUMERATOR_SECTION1_2",
        "13.13: # Crianças na 2a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerator",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries.findFinalNumeratorCategory13SectionIIC(),
                "CAT13CV24NUMERATOR_SECTION1_2",
                mappings),
            mappings),
        "age=2-14");

    dataSetDefinition.addColumn(
        "CAT13CV24DENOMINATOR_SECTION1_2",
        "13.13: # Crianças na 2a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries.findDenominatorCategory13SectionIIB(
                    EXCLUDE_TB_ACTIVE_DIAGNOSTIC),
                "CAT13CV24DENOMINATOR_SECTION1_2",
                mappings),
            mappings),
        "age=2-14");

    dataSetDefinition.addColumn(
        "CAT13TB_HIV_ADULTS_13_13NUMERATOR",
        "13.13: % de crianças (0-14 anos) coinfectados TB/HIV com consulta clínica no período de avaliação, elegíveis ao pedido de CV e com registo de pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_4(),
                "CAT13TB_HIV_ADULTS_13_13NUMERATOR",
                mappings),
            mappings),
        "age=0-14");

    dataSetDefinition.addColumn(
        "CAT13TB_HIV_ADULTS_13_13DENOMINATOR",
        "13.13: % de crianças (0-14 anos) coinfectados TB/HIV com consulta clínica no período de avaliação, elegíveis ao pedido de CV e com registo de pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_4(),
                "CAT13TB_HIV_ADULTS_13_13DENOMINATOR",
                mappings),
            mappings),
        "age=0-14");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_14_NUMERATOR",
        "13.14: # Crianças  na 2a linha de TARV que receberam o resultado da Carga Viral entre o "
            + "sexto e o nono mês após o início da 2a linha de TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5(),
                "CAT13_PART_3_13_14_NUMERATOR",
                mappings),
            mappings),
        "ageOnB2NEW=2-14-10MONTHS");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_14_DENOMINATOR",
        "13.14: # Crianças  na 2a linha de TARV que receberam o resultado da Carga Viral entre o "
            + "sexto e o nono mês após o início da 2a linha de TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5(),
                "CAT13_PART_3_13_14_DENOMINATOR",
                mappings),
            mappings),
        "ageOnB2NEW=2-14-10MONTHS");

    dataSetDefinition.addColumn(
        "CAT13_TB_HIV_13_14_NUMERATOR",
        "13.14: % de crianças (0-14 anos) coinfectados TB/HIV com resultado de CV registado na FM Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_5(),
                "CAT13_TB_HIV_13_14_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=0-14");

    dataSetDefinition.addColumn(
        "CAT13_TB_HIV_13_14_DENOMINATOR",
        "13.14: % de crianças (0-14 anos) coinfectados TB/HIV com resultado de CV registado na FM Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5(),
                "CAT13_TB_HIV_13_14_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=0-14");

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVInTARVNUMINATOR",
        "13.15: % Mulheres Gravidas elegíveis a CV com registo de pedido de CV feito pelo clínico (MG que iniciaram TARV na CPN) Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVInTARVCategory13P2Numerator(),
                "CAT13P2PregnantWithCVInTARVNUMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVInTARVDENOMINATOR",
        "13.15: % Mulheres Gravidas elegíveis a CV com registo de pedido de CV feito pelo clínico (MG que iniciaram TARV na CPN) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVInTARVCategory13P2Denumerator(),
                "CAT13P2PregnantWithCVInTARVDENOMINATOR",
                mappings),
            mappings),
        "");
  }
}
