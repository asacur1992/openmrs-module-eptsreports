package org.openmrs.module.eptsreports.reporting.library.datasets.midatasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P2CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mi.MICategory13P4CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P4Dataset extends MQAbstractDataSet {

  @Autowired private MICategory13P4CohortQueries miCategory13P4CohortQueries;
  @Autowired private MICategory13P2CohortQueries mICategory13P2CohortQueries;

  public void constructTMiDatset(
      CohortIndicatorDataSetDefinition dataSetDefinition, String mappings) {

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVInFirstConsultationTARVNUMINATOR",
        "13.16: % Mulheres elegíveis a CV com registo de pedido de CV feito pelo clínico na primeira CPN (MG que entraram em TARV na CPN) Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVInFirstConsultationTARVCategory13P2Numerator(),
                "CAT13P2PregnantWithCVInFirstConsultationTARVNUMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVInFirstConsultationTARVDENOMINATOR",
        "13.16: % Mulheres Gravidas elegíveis a CV com registo de pedido de "
            + "CV feito pelo clínico na primeira CPN (MG que entraram em TARV na CPN) Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVInFirstConsultationTARVCategory13P2Denumerator(),
                "CAT13P2PregnantWithCVInFirstConsultationTARVDENOMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVIn33DaysAfterInclusionDateTARVNUMINATOR",
        "13.17: % Mulheres Gravidas que receberam o resultado da Carga Viral dentro de 33 dias após pedido Numerador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVIn33DaysAfterInclusionDateTARVCategory13P2Numerator(),
                "CAT13P2PregnantWithCVIn33DaysAfterInclusionDateTARVNUMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P2PregnantWithCVIn33DaysAfterInclusionDateTARVDENOMINATOR",
        "13.17: % Mulheres Gravidas que receberam o resultado da Carga Viral dentro de 33 dias após pedido Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.mICategory13P2CohortQueries
                    .findPatientsWhoArePregnantWithCVIn33DaysAfterInclusionDateTARVCategory13P2Denumerator(),
                "CAT13P2PregnantWithCVIn33DaysAfterInclusionDateTARVDENOMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P4PregnantNUMINATOR",
        "13.18: % Mulheres gravidas na 1ª linha de TARV com registo de pedido de CV entre o 3º e o 4º "
            + "mês após terem recebido  o último resultado de CVCV ≥1000",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPatientsWhoPregnantReceivedResultMoreThan1000CVCategory13P4Numerator(),
                "CAT13P4PregnantNUMINATOR",
                mappings),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "CAT13P4PregnantDENUMINATOR",
        "13.18: % Mulheres Gravidas na 1ª linha de TARV com registo de pedido de CV entre o 3º e o 4º "
            + "mês após terem recebido  o último resultado de CV ≥ 1000 Denominador",
        EptsReportUtils.map(
            this.setIndicatorWithAllParameters(
                this.miCategory13P4CohortQueries
                    .findPregnantWhoHaveRequestedCVCategory13P4Denumerator(),
                "CAT13P4PregnantDENUMINATOR",
                mappings),
            mappings),
        "");
  }
}
