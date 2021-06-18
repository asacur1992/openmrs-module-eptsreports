package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.GenericMICohortQueryCategory12;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory12P1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory12P1Dataset extends MQAbstractDataSet {

  @Autowired private MICategory12P1CohortQueries miCategory12P1CohortQueries;
  @Autowired private GenericMICohortQueryCategory12 genericMICohortQueryCategory12;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT12ADULTDENOMINADOR33DAYS",
        "12.1: Adultos (15/+anos) em TARV  que retornaram para 2ª consulta clínica ou levantamento de "
            + "ARVs dentro de 33 dias após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_1(),
                "CAT12ADULTDENOMINADOR33DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_3_MONTHS_15+");

    dataSetDefinition.addColumn(
        "CAT12ADULTNUMERATOR33DAYS",
        "12.1: Adultos (15/+anos) em TARV  que retornaram para 2ª consulta clínica ou levantamento de "
            + "ARVs dentro de 33 dias após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation33DaysAfterAtartingARTCategory12Line62ColumnDInTheTemplateNumerator1(),
                "CAT12ADULTNUMERATOR33DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_3_MONTHS_15+");

    dataSetDefinition.addColumn(
        "CAT12ADULTDENOMINADOR99DAYS",
        "12.2: Adultos (15/+anos) em TARV que tiveram no mínimo 3 consultas clínicas ou levantamento de "
            + "ARVs dentro de 99 dias (nos primeiros 3 meses) após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_2(),
                "CAT12ADULTDENOMINADOR99DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_5_MONTHS_15+");

    dataSetDefinition.addColumn(
        "CAT12ADULTNUMERATOR99DAYS",
        "12.2: Adultos (15/+anos) em TARV que tiveram no mínimo 3 consultas clínicas ou levantamento de "
            + "ARVs dentro de 99 dias (nos primeiros 3 meses) após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation99DaysAfterAtartingARTCategory12Line63ColumnDInTheTemplateNumerator2(),
                "CAT12ADULTNUMERATOR99DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_5_MONTHS_15+");

    dataSetDefinition.addColumn(
        "CAT12CHILDRENDENOMINADOR33DAYS",
        "12.5: Crianças (0-14 anos) em TARV  que retornaram para 2ª consulta clínica ou levantamento de "
            + "ARVs dentro de 33 dias após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_5(),
                "CAT12CHILDRENDENOMINADOR33DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_3_MONTHS_15-");

    dataSetDefinition.addColumn(
        "CAT12CHILDRENNUMERATOR33DAYS",
        "12.5: Crianças (0-14 anos) em TARV  que retornaram para 2ª consulta clínica ou levantamento de "
            + "ARVs dentro de 33 dias após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation33DaysAfterAtartingARTCategory12Line62ColumnDInTheTemplateNumerator1(),
                "CAT12CHILDRENNUMERATOR33DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_3_MONTHS_15-");

    dataSetDefinition.addColumn(
        "CAT12CHILDRENDENOMINADOR99DAYS",
        "12.6: Crianças em TARV (0-14 anos) com consultas clínicas mensais ou levantamento de ARVs dentro de "
            + "99 dias (nos primeiros 3 meses) após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_2(),
                "CAT12CHILDRENDENOMINADOR99DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_5_MONTHS_15-");

    dataSetDefinition.addColumn(
        "CAT12CHILDRENNUMERATOR99DAYS",
        "12.6: Crianças em TARV (0-14 anos) com consultas clínicas mensais ou levantamento de ARVs dentro de "
            + "99 dias (nos primeiros 3 meses) após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation99DaysAfterAtartingARTCategory12Line63ColumnDInTheTemplateNumerator2(),
                "CAT12CHILDRENNUMERATOR99DAYS",
                mappings),
            mappings),
        "ageMiNewART=LESS_5_MONTHS_15-");

    dataSetDefinition.addColumn(
        "CAT12PREGNANTDENOMINADOR33DAYS",
        "12.9: Mulheres grávidas HIV+ que iniciaram TARV na CPN e que retornaram para "
            + "2ª consulta clínica ou levantamento de ARVs dentro de 33 dias após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientPregnantOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_9(),
                "CAT12PREGNANTDENOMINADOR33DAYS",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT12PREGNANTNUMERATOR33DAYS",
        "12.9: Mulheres grávidas HIV+ que iniciaram TARV na CPN e que retornaram para "
            + "2ª consulta clínica ou levantamento de ARVs dentro de 33 dias após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPregnantWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation33DaysAfterAtartingARTCategory12Line71ColumnDInTheTemplateNumerator5(),
                "CAT12PREGNANTNUMERATOR33DAYS",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT12PREGNANTENOMINADOR99DAYS",
        "12.10: Mulheres grávidas HIV+ que iniciaram TARV na CPN e tiveram "
            + "3 consultas mensais/levantamentos de ARVs dentro de 99 dias (nos primeiros 3 meses) após início do TARV Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.genericMICohortQueryCategory12
                    .findPatientPregnantOnARTdExcludingPregantAndBreastfeedingAndTransferredInTransferredOutMICategory12Section12_10(),
                "CAT12PREGNANTENOMINADOR99DAYS",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT12PREGNANTNUMERATOR99DAYS",
        "12.10: Mulheres grávidas HIV+ que iniciaram TARV na CPN e tiveram "
            + "3 consultas mensais/levantamentos de ARVs dentro de 99 dias (nos primeiros 3 meses) após início do TARV Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory12P1CohortQueries
                    .findPregnantWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation99DaysAfterAtartingARTCategory12Line72ColumnDInTheTemplateNumerator6(),
                "CAT12PREGNANTNUMERATOR99DAYS",
                mappings),
            mappings),
        "");
  }
}
