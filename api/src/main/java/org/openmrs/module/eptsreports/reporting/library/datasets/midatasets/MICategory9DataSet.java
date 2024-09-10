package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory9CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory9DataSet extends MQAbstractDataSet {

  @Autowired private MICategory9CohortQueries miCategory9CohortQueries;

  public void constructTMqDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    // Adultos
    dataSetDefinition.addColumn(
        "CAT9ADUL01TNUMERATOR",
        "9.1. % de adultos  (15/+anos) com pedido de CD4 na primeira consulta clínica depois do diagnóstico de HIV+ - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_1(),
                "CAT9ADUL01TNUMERATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15+Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ADUL01TDENOMINATOR",
        "9.1. % de adultos  (15/+anos) com pedido de CD4 na primeira consulta clínica depois do diagnóstico de HIV+ - Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1(),
                "CAT9ADUL01TDENOMINATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15+Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ADUL02TNUMERATOR",
        "9.2.% de adultos  (15/+anos) HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira consulta clínica - Numerador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_2(),
                "CAT9ADUL02TNUMERATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15+Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ADUL02TDENOMINATOR",
        "9.2. % de adultos  (15/+anos) HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira consulta clínica - Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_2(),
                "CAT9ADUL02TDENOMINATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15+Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS93NUMERADOR",
        "9.3 % de adultos (15/+anos) com pedido de CD4 na consulta clínica de reinício do TARV - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioAfterAbandonedTreatmentNumerator9_3(),
                "CAT9ADULTOS93NUMERADOR",
                mappings),
            mappings),
        "ageOnReinicio=15+");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS93DENOMINADOR",
        "9.3 % de adultos (15/+anos) com pedido de CD4 na consulta clínica de reinício do TARV - Denomindaor ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
                "CAT9ADULTOS93DENOMINADOR",
                mappings),
            mappings),
        "ageOnReinicio=15+");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS94NUMERADOR",
        "9.4 % de adultos (15/+anos) que receberam o resultado do CD4 dentro de 33 dias após  consulta clínica de reinício do TARV - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithCD4Result33DaysAfterClinicalConsultationMarkedWithReinicioARTAndPedidoCd4Numerator9_4(),
                "CAT9ADULTOS94NUMERADOR",
                mappings),
            mappings),
        "ageOnReinicio=CD4-15+");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS94DENOMINADOR",
        "9.4 % de adultos (15/+anos) que receberam o resultado do CD4 dentro de 33 dias após consulta clínica de reinício do TARV - Denomindaor ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
                "CAT9ADULTOS94DENOMINADOR",
                mappings),
            mappings),
        "ageOnReinicio=CD4-15+");

    // Criancas

    dataSetDefinition.addColumn(
        "CAT9CHILDREN01TNUMERATOR",
        "9.5. % de crianças  (0-14 anos) com pedido de CD4 na primeira consulta clínica depois do diagnóstico de HIV+. - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_5(),
                "CAT9CHILDREN01TNUMERATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15-Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ACHILDREN01TDENOMINATOR",
        "9.5. % de crianças  (0-14 anos) com pedido de CD4 na primeira consulta clínica depois do diagnóstico de HIV+. - Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_5(),
                "CAT9ACHILDREN01TDENOMINATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15-Back3Months");

    dataSetDefinition.addColumn(
        "CAT9CHILDREN02TNUMERATOR",
        "9.6.% de crianças  (0-14 anos) HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira consulta clínica  - Numerador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_6(),
                "CAT9CHILDREN02TNUMERATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15-Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ACHILDREN02TDENOMINATOR",
        "9.6. % de crianças  (0-14 anos) HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira consulta clínica - Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6(),
                "CAT9ACHILDREN02TDENOMINATOR",
                mappings),
            mappings),
        "ageOnTheFirstConsultationDuringInclusionPeriod=15-Back3Months");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS97NUMERADOR",
        "9.7 % de crianças  (0-14 anos) com pedido de CD4 na primeira consulta clínica de reinício do TARV - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioAfterAbandonedTreatmentNumerator9_3(),
                "CAT9ADULTOS97NUMERADOR",
                mappings),
            mappings),
        "ageOnReinicio=15-");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS97DENOMINADOR",
        "9.7 % de crianças  (0-14 anos) com pedido de CD4 na primeira consulta clínica de reinício do TARV - Denomindaor ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
                "CAT9ADULTOS97DENOMINADOR",
                mappings),
            mappings),
        "ageOnReinicio=15-");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS98NUMERADOR",
        "9.8 % de crianças (0-14 anos) que receberam o resultado do CD4 dentro de 33 dias após consulta clínica de reinício do TARV - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findAdultPatientsWithCD4Result33DaysAfterClinicalConsultationMarkedWithReinicioARTAndPedidoCd4Numerator9_4(),
                "CAT9ADULTOS98NUMERADOR",
                mappings),
            mappings),
        "ageOnReinicio=CD4-15-");

    dataSetDefinition.addColumn(
        "CAT9ADULTOS98DENOMINADOR",
        "9.8 % de crianças (0-14 anos) que receberam o resultado do CD4 dentro de 33 dias após consulta clínica de reinício do TARV - Denomindaor ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
                "CAT9ADULTOS98DENOMINADOR",
                mappings),
            mappings),
        "ageOnReinicio=CD4-15-");

    // Gravidas

    dataSetDefinition.addColumn(
        "CAT9PREGNANT01TNUMERATOR",
        "9.9. % de MG  HIV+ com registo de pedido de CD4 na primeira CPN - Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPragnantWomanWhoARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_9(),
                "CAT9PREGNANT01TNUMERATOR",
                mappings),
            mappings),
        "gender=F");

    dataSetDefinition.addColumn(
        "CAT9PREGNANT01TDENOMINATOR",
        "9.9. % de MG  HIV+ com registo de pedido de CD4 na primeira CPN. - Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9(),
                "CAT9PREGNANT01TDENOMINATOR",
                mappings),
            mappings),
        "gender=F");

    dataSetDefinition.addColumn(
        "CAT9PREGNANT02TNUMERATOR",
        "9.10. % de MG  HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira CPN  - Numerador ",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory9CohortQueries
                    .findPregnantWomanPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_10(),
                "CAT9PREGNANT02TNUMERATOR",
                mappings),
            mappings),
        "gender=F");

    dataSetDefinition.addColumn(
        "CAT9PREGNANT02TDENOMINATOR",
        "9.10. % de MG  HIV+ que receberam o resultado do primeiro CD4 dentro de 33 dias  após a primeira CPN- Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                miCategory9CohortQueries
                    .findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_10(),
                "CAT9PREGNANT02TDENOMINATOR",
                mappings),
            mappings),
        "gender=F");
  }
}
