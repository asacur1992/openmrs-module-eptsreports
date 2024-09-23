package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P1_1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P1_2CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P1_2Dataset extends MQAbstractDataSet {

  @Autowired private MICategory13P1_1CohortQueries mICategory13P1_1CohortQueries;
  @Autowired private MICategory13P1_2CohortQueries mICategory13P1_2CohortQueries;
  @Autowired private MICategory13P3CohortQueries mICategory13P3CohortQueries;
  private final boolean EXCLUDE_TB_ACTIVE_DIAGNOSTIC = true;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT13CV15PLUSNUMERATOR_SECTION1_2",
        "13.4: # Adultos (15/+anos) na 2a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries.findFinalNumeratorCategory13SectionIIC(),
                "CAT13CV15PLUSNUMERATOR_SECTION1_2",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13CV15PLUSDENOMINATOR_SECTION1_2",
        "13.4: # Adultos (15/+anos) na 2a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries.findDenominatorCategory13SectionIIB(
                    EXCLUDE_TB_ACTIVE_DIAGNOSTIC),
                "CAT13CV15PLUSDENOMINATOR_SECTION1_2",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13TB_HIV_ADULTS_13_4NUMERATOR",
        "13.4: % de adultos (15/+anos) coinfectados TB/HIV com consulta clínica no período de avaliação, elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_4(),
                "CAT13TB_HIV_ADULTS_13_4NUMERATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13TB_HIV_ADULTS_13_4DENOMINATOR",
        "13.4: % de adultos (15/+anos) coinfectados TB/HIV com consulta clínica no período de avaliação, elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_2CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_4(),
                "CAT13TB_HIV_ADULTS_13_4DENOMINATOR",
                mappings),
            mappings),
        "age=15PlusOrBreastfeeding");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_5_NUMERATOR",
        "13.5: # Adultos (15/+anos) na 2a linha de TARV que receberam o resultado da CV entre "
            + "o sexto e o nono mês após o início da 2a linha de TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5(),
                "CAT13_PART_3_13_5_NUMERATOR",
                mappings),
            mappings),
        "ageOnB2NEW=15+10MONTHS");

    dataSetDefinition.addColumn(
        "CAT13_PART_3_13_5_DENOMINATOR",
        "13.5: # Adultos (15/+anos) na 2a linha de TARV que receberam o resultado da CV entre o "
            + "sexto e o nono mês após o início da 2a linha de TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5(),
                "CAT13_PART_3_13_5_DENOMINATOR",
                mappings),
            mappings),
        "ageOnB2NEW=15+10MONTHS");

    dataSetDefinition.addColumn(
        "CAT13_TB_HIV_13_5_NUMERATOR",
        "13.5: % de adultos (15/+anos) coinfectados TB/HIV com resultado de CV registado na FM Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_5(),
                "CAT13_TB_HIV_13_5_NUMERATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");

    dataSetDefinition.addColumn(
        "CAT13_TB_HIV_13_5_DENOMINATOR",
        "13.5: % de adultos (15/+anos) coinfectados TB/HIV com resultado de CV registado na FM Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P3CohortQueries
                    .findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5(),
                "CAT13_TB_HIV_13_5_DENOMINATOR",
                mappings),
            mappings),
        "ageMiEndRevisionDate=15+");

    dataSetDefinition.addColumn(
        "CAT13CV04NUMERATOR",
        "13.6: % Crianças (0-4 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findFinalNumeratorCategory13SectionICChildrens(),
                "CAT13CV04NUMERATOR",
                mappings),
            mappings),
        "age=0-4");

    dataSetDefinition.addColumn(
        "CAT13CV04DENOMINATOR",
        "13.6: % Crianças (0-4 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findDenominatorCategory13SectionIBChildrens(),
                "CAT13CV04DENOMINATOR",
                mappings),
            mappings),
        "age=0-4");

    dataSetDefinition.addColumn(
        "CAT13CV59NUMERATOR",
        "13.7: % Crianças (5-9 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findFinalNumeratorCategory13SectionICChildrens(),
                "CAT13CV59NUMERATOR",
                mappings),
            mappings),
        "age=5-9");

    dataSetDefinition.addColumn(
        "CAT13CV59DENOMINATOR",
        "13.7: % Crianças (5-9 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findDenominatorCategory13SectionIBChildrens(),
                "CAT13CV59DENOMINATOR",
                mappings),
            mappings),
        "age=5-9");

    dataSetDefinition.addColumn(
        "CAT13CV1014NUMERATOR",
        "13.8: # Crianças (10-14 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries.findFinalNumeratorCategory13SectionICChildrens(),
                "CAT13CV1014NUMERATOR",
                mappings),
            mappings),
        "age=10-14");

    dataSetDefinition.addColumn(
        "CAT13CV1014DENOMINATOR",
        "13.8: # Crianças (10-14 anos de idade) na 1a linha de TARV que tiveram consulta clínica no período de revisão e que eram elegíveis ao pedido de CV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                mICategory13P1_1CohortQueries.findDenominatorCategory13SectionIBChildrens(),
                "CAT13CV1014DENOMINATOR",
                mappings),
            mappings),
        "age=10-14");

    dataSetDefinition.addColumn(
        "CAT13CV13_8PLUS13_13NUMERATOR",
        "13.8: % de crianças (10-14 anos de idade) na 1ª(13.8) ou 2ª(13.13) linha de TARV que tiveram consulta clínica no período de avaliação, eram elegíveis ao pedido de CV Numerador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries
                    .findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Numerator(),
                "CAT13CV13_8PLUS13_13NUMERATOR",
                mappings),
            mappings),
        "age=10-14");

    dataSetDefinition.addColumn(
        "CAT13CV13_8PLUS13_13DENOMINATOR",
        "13.8: % de crianças (10-14 anos de idade) na 1ª(13.8) ou 2ª(13.13) linha de TARV que tiveram consulta clínica no período de avaliação, eram elegíveis ao pedido de CV Denominador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P1_1CohortQueries
                    .findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Denominator(),
                "CAT13CV13_8PLUS13_13DENOMINATOR",
                mappings),
            mappings),
        "age=10-14");
  }
}
