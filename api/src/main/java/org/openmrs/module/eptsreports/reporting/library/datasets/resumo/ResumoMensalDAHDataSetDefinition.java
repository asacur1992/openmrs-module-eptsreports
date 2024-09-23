package org.openmrs.module.eptsreports.reporting.library.datasets.resumo;

import static org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils.map;
import static org.openmrs.module.reporting.evaluation.parameter.Mapped.mapStraightThrough;

import org.openmrs.module.eptsreports.reporting.library.cohorts.ResumoMensalDAHCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.disaggregations.ResumoMensalDAHDisaggregations;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.library.queries.ResumoMensalDAHQueries.ARTSituation;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ResumoMensalDAHDataSetDefinition extends BaseDataSet {

  private EptsCommonDimension eptsCommonDimension;

  private EptsGeneralIndicator eptsGeneralIndicator;

  private ResumoMensalDAHCohortQueries resumoMensalDAHCohortQueries;

  private ResumoMensalDAHDisaggregations resumoMensalDAHDisaggregations;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  @Autowired
  public ResumoMensalDAHDataSetDefinition(
      EptsCommonDimension eptsCommonDimension,
      EptsGeneralIndicator eptsGeneralIndicator,
      ResumoMensalDAHCohortQueries resumoMensalDAHCohortQueries,
      ResumoMensalDAHDisaggregations resumoMensalDAHDisaggregations) {
    this.eptsCommonDimension = eptsCommonDimension;
    this.eptsGeneralIndicator = eptsGeneralIndicator;
    this.resumoMensalDAHCohortQueries = resumoMensalDAHCohortQueries;
    this.resumoMensalDAHDisaggregations = resumoMensalDAHDisaggregations;
  }

  public DataSetDefinition constructResumoMensalDAHDataSet() {
    CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

    dsd.setName("Resumo Mensal DAH");
    dsd.addParameters(getParameters());

    dsd.addDimension("gender", map(eptsCommonDimension.gender(), ""));
    dsd.addDimension(
        "age", map(eptsCommonDimension.age(ageDimensionCohort), "effectiveDate=${endDate}"));

    addRow(
        dsd,
        "DAHI0",
        "Total de pacientes activos em DAH - I0",
        getNumberOfPatientsActiveInDAHWhoAreInTARVByEndOfPreviousMonthIndicator0(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI0T",
        "Total de pacientes activos em DAH - I0",
        getNumberOfPatientsActiveInDAHWhoAreInTARVByEndOfPreviousMonthIndicator0(),
        "");

    addRow(
        dsd,
        "DAHI1",
        "Utentes novos inícios em TARV - I1",
        getNumberOfPatientsNewEnrolledInARTWhoInitiatedDAHDuringReportPeriodI1(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI1T",
        "Total utentes novos inícios em TARV - I1",
        getNumberOfPatientsNewEnrolledInARTWhoInitiatedDAHDuringReportPeriodI1(),
        "");

    addRow(
        dsd,
        "DAHI2",
        "Utentes reinícios em TARV - I2",
        getNumberOfPatientsWhoReinitiatedARTWhoInitiatedDAHDuringReportPeriodI2(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI2T",
        "Total utentes reinícios em TARV - I2",
        getNumberOfPatientsWhoReinitiatedARTWhoInitiatedDAHDuringReportPeriodI2(),
        "");

    addRow(
        dsd,
        "DAHI3",
        "Utentes activos em TARV - I3",
        getNumberOfPatientsCurrentInARTWhoInitiatedDAHDuringReportPeriodI3(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI3T",
        "Total utentes activos em TARV - I3",
        getNumberOfPatientsCurrentInARTWhoInitiatedDAHDuringReportPeriodI3(),
        "");

    addRow(
        dsd,
        "DAHI4",
        "Utentes activos em TARV - I4",
        getNumberOfPatientsWhoLeftDAHDuringReportPeriodI4(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI4T",
        "Total utentes activos em TARV - I4",
        getNumberOfPatientsWhoLeftDAHDuringReportPeriodI4(),
        "");

    addRow(
        dsd,
        "DAHI5",
        "Utentes activos em TARV - I5",
        getTotalNumberOfPatientsInDAHAtTheEndOfReportingPeriodI5(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI5T",
        "Total utentes activos em TARV - I5",
        getTotalNumberOfPatientsInDAHAtTheEndOfReportingPeriodI5(),
        "");

    addRow(
        dsd,
        "DAHI6",
        "Número total de óbitos - I6",
        getTotalNumberOfPatientsWhoAreDeadWithDAHDiagnosticI6(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI6T",
        "Número total de óbitos - I6",
        getTotalNumberOfPatientsWhoAreDeadWithDAHDiagnosticI6(),
        "");

    addRow(
        dsd,
        "DAHI7",
        "Número total de novos inscritos - I7",
        getTotalNumberPatientsInDAHSixMonthsCoorteI7(),
        resumoMensalDAHDisaggregations.getChildrenAndAdultsColumns());

    dsd.addColumn(
        "DAHI7T",
        "Número total de novos inscritos - I7",
        getTotalNumberPatientsInDAHSixMonthsCoorteI7(),
        "");

    // Indicador 8

    addRow(
        dsd,
        "DAHI8-NEW",
        "Número de utentes com pedido de CD4 de rastreio durante o mês - I8",
        getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI8-RESTART",
        "Número de utentes com pedido de CD4 de rastreio durante o mês - I8",
        getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI8-ACTIVE",
        "Número de utentes com pedido de CD4 de rastreio durante o mês - I8",
        getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI8-PREGNANT",
        "Número de utentes com pedido de CD4 de rastreio durante o mês - I8",
        getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(ARTSituation.PREGNANT),
        "");

    // Indicador 9

    addRow(
        dsd,
        "DAHI9-NEW",
        "Número de utentes com resultado de CD4 de rastreio disponivel  - I9",
        getNumberOfPatientWithCD4ResultDuringThePeriodI9(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI9-RESTART",
        "Número de utentes com resultado de CD4 de rastreio disponivel - I9",
        getNumberOfPatientWithCD4ResultDuringThePeriodI9(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI9-ACTIVE",
        "Número de utentes com resultado de CD4 de rastreio disponivel - I9",
        getNumberOfPatientWithCD4ResultDuringThePeriodI9(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI9-PREGNANT",
        "Número de utentes com resultado de CD4 de rastreio disponivel  - I9",
        getNumberOfPatientWithCD4ResultDuringThePeriodI9(ARTSituation.PREGNANT),
        "");

    // Indicador 10

    addRow(
        dsd,
        "DAHI10-NEW",
        "Número de utentes com resultado de CD4 baixo* durante o mês  - I10",
        getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI10-RESTART",
        "Número de utentes com resultado de CD4 baixo* durante o mês - I10",
        getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI10-ACTIVE",
        "Número de utentes com resultado de CD4 baixo* durante o mês - I10",
        getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI10-PREGNANT",
        "Número de utentes com resultado de CD4 baixo* durante o mês  - I10",
        getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI10-SEGUIMENTO",
        "Número de utentes com resultado de CD4 baixo* durante o mês  - I10",
        getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 11

    addRow(
        dsd,
        "DAHI11-NEW",
        "Número de utentes elegíveis a pedido de TB-LAM com resultado de TB-LAM durante o mês  - I11",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
            ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI11-RESTART",
        "Número de utentes elegíveis a pedido de TB-LAM com resultado de TB-LAM durante o mês - I11",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
            ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI11-ACTIVE",
        "Número de utentes elegíveis a pedido de TB-LAM com resultado de TB-LAM durante o mês - I11",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
            ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI11-PREGNANT",
        "Número de utentes elegíveis a pedido de TB-LAM com resultado de TB-LAM durante o mês  - I11",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
            ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI11-SEGUIMENTO",
        "Número de utentes elegíveis a pedido de TB-LAM com resultado de TB-LAM durante o mês  - I11",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 12

    addRow(
        dsd,
        "DAHI12-NEW",
        "Número de utentes com resultado de TB-LAM positivo durante o mês(Novos)  - I12",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
            ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI12-RESTART",
        "Número de utentes com resultado de TB-LAM positivo durante o mês(Reinicios) - I12",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
            ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI12-ACTIVE",
        "Número de utentes com resultado de TB-LAM positivo durante o mês(Activos) - I12",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
            ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI12-PREGNANT",
        "Número de utentes com resultado de TB-LAM positivo durante o mês(Gravidas)  - I12",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
            ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI12-SEGUIMENTO",
        "Número de utentes com resultado de TB-LAM positivo durante o mês(Seguimento)  - I12",
        getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 13

    addRow(
        dsd,
        "DAHI13-NEW",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico durante o mês(Novos)  - I13",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI13-RESTART",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico durante o mês - I13",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI13-ACTIVE",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico durante o mês(Activos) - I13",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI13-PREGNANT",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico durante o mês(Gravidas)  - I13",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI13-SEGUIMENTO",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico durante o mês(Seguimento)  - I13",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 14

    addRow(
        dsd,
        "DAHI14-NEW",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico positivo durante o mês(Novos)  - I14",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI14-RESTART",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico positivo durante o mês(Reinicios) - I14",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI14-ACTIVE",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico positivo durante o mês(Activos) - I14",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI14-PREGNANT",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico positivo durante o mês(Gravidas)  - I14",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI14-SEGUIMENTO",
        "Número de utentes com CD4 baixo* com resultado de CrAg sérico positivo durante o mês(Seguimento)  - I14",
        getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 15

    addRow(
        dsd,
        "DAHI15-NEW",
        "Número de utentes com CrAg sérico positivo e com o resultado de CrAg no LCR registrado durante o mês(Novos)  - I15",
        getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI15-RESTART",
        "Número de utentes com CrAg sérico positivo e com o resultado de CrAg no LCR registrado durante o mês(Reinicios) - I15",
        getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI15-ACTIVE",
        "Número de utentes com CrAg sérico positivo e com o resultado de CrAg no LCR registrado durante o mês(Activos) - I15",
        getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI15-PREGNANT",
        "Número de utentes com CrAg sérico positivo e com o resultado de CrAg no LCR registrado durante o mês(Gravidas)  - I15",
        getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI15-SEGUIMENTO",
        "Número de utentes com CrAg sérico positivo e com o resultado de CrAg no LCR registrado durante o mês(Seguimento)  - I15",
        getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 16

    addRow(
        dsd,
        "DAHI16-NEW",
        "Número de utentes com CrAg sérico positivo que iniciaram tratamento preventivo de MCC durante o mês(Novos)  - I16",
        getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(
            ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI16-RESTART",
        "Número de utentes com CrAg sérico positivo que iniciaram tratamento preventivo de MCC durante o mês(Reinicios) - I16",
        getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI16-ACTIVE",
        "Número de utentes com CrAg sérico positivo que iniciaram tratamento preventivo de MCC durante o mês(Activos) - I16",
        getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI16-PREGNANT",
        "Número de utentes com CrAg sérico positivo que iniciaram tratamento preventivo de MCC durante o mês(Gravidas)  - I16",
        getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI16-SEGUIMENTO",
        "Número de utentes com CrAg sérico positivo que iniciaram tratamento preventivo de MCC durante o mês(Seguimento)  - I16",
        getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 17

    addRow(
        dsd,
        "DAHI17-NEW",
        "Número de utentes com CrAg no LCR positivo que iniciaram tratamento de 1a escolha de MCC durante o mês(Novos)  - I17",
        getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
            ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI17-RESTART",
        "Número de utentes com CrAg no LCR positivo que iniciaram tratamento de 1a escolha de MCC durante o mês(Reinicios) - I17",
        getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
            ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI17-ACTIVE",
        "Número de utentes com CrAg no LCR positivo que iniciaram tratamento de 1a escolha de MCC durante o mês(Activos) - I17",
        getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
            ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI17-PREGNANT",
        "Número de utentes com CrAg no LCR positivo que iniciaram tratamento de 1a escolha de MCC durante o mês(Gravidas)  - I17",
        getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
            ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI17-SEGUIMENTO",
        "Número de utentes com CrAg no LCR positivo que iniciaram tratamento de 1a escolha de MCC durante o mês(Seguimento)  - I17",
        getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 18

    addRow(
        dsd,
        "DAHI18-NEW",
        "Número de utentes com novo diagnóstico de sarcoma de kaposi e com indicação para quimioterapia durante o mês(Novos)  - I18",
        getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
            ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI18-RESTART",
        "Número de utentes com novo diagnóstico de sarcoma de kaposi e com indicação para quimioterapia durante o mês(Reinicios) - I18",
        getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
            ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI18-ACTIVE",
        "Número de utentes com novo diagnóstico de sarcoma de kaposi e com indicação para quimioterapia durante o mês(Activos) - I18",
        getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
            ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI18-PREGNANT",
        "Número de utentes com novo diagnóstico de sarcoma de kaposi e com indicação para quimioterapia durante o mês(Gravidas)  - I18",
        getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
            ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI18-SEGUIMENTO",
        "Número de utentes com novo diagnóstico de sarcoma de kaposi e com indicação para quimioterapia durante o mês(Seguimento)  - I18",
        getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
            ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    // Indicador 19

    addRow(
        dsd,
        "DAHI19-NEW",
        "Número de utentes com sarcoma de kaposi que iniciaram Ciclo 1 de quimioterapia durante o mês (Novos)  - I19",
        getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(ARTSituation.NEW_ENROLLED),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI19-RESTART",
        "Número de utentes com sarcoma de kaposi que iniciaram Ciclo 1 de quimioterapia durante o mês (Reinicios) - I19",
        getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(ARTSituation.RESTART),
        resumoMensalDAHDisaggregations.disAggForE());

    addRow(
        dsd,
        "DAHI19-ACTIVE",
        "Número de utentes com sarcoma de kaposi que iniciaram Ciclo 1 de quimioterapia durante o mês (Activos) - I19",
        getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(ARTSituation.ACTIVE),
        resumoMensalDAHDisaggregations.disAggForE());

    dsd.addColumn(
        "DAHI19-PREGNANT",
        "Número de utentes com sarcoma de kaposi que iniciaram Ciclo 1 de quimioterapia durante o mês (Gravidas)  - I19",
        getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(ARTSituation.PREGNANT),
        "");

    addRow(
        dsd,
        "DAHI19-SEGUIMENTO",
        "Número de utentes com sarcoma de kaposi que iniciaram Ciclo 1 de quimioterapia durante o mês (Seguimento)  - I19",
        getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(ARTSituation.SEGUIMENTO_DAH),
        resumoMensalDAHDisaggregations.disAggForE());

    return dsd;
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsActiveInDAHWhoAreInTARVByEndOfPreviousMonthIndicator0() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I0",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsActiveInDAHWhoAreInTARVByEndOfPreviousMonthIndicatorI0())));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsNewEnrolledInARTWhoInitiatedDAHDuringReportPeriodI1() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I1",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsNewEnrolledInARTWhoInitiatedDAHDuringReportPeriodI1())));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsWhoReinitiatedARTWhoInitiatedDAHDuringReportPeriodI2() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I2",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWhoReinitiatedARTWhoInitiatedDAHDuringReportPeriodI2())));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsCurrentInARTWhoInitiatedDAHDuringReportPeriodI3() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I3",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsCurrentInARTWhoInitiatedDAHDuringReportPeriodI3())));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWhoLeftDAHDuringReportPeriodI4() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I4",
            mapStraightThrough(
                resumoMensalDAHCohortQueries.getNumberOfPatientsWhoLeftDAHDuringReportPeriodI4())));
  }

  private Mapped<CohortIndicator> getTotalNumberOfPatientsInDAHAtTheEndOfReportingPeriodI5() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I5",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsActiveInDAHWhoAreInTARVByEndOfMonthIndicatorI5())));
  }

  private Mapped<CohortIndicator> getTotalNumberOfPatientsWhoAreDeadWithDAHDiagnosticI6() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I6",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getTotalNumberOfPatientsWhoAreDeadWithDAHDiagnosticI6())));
  }

  private Mapped<CohortIndicator> getTotalNumberPatientsInDAHSixMonthsCoorteI7() {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I7",
            mapStraightThrough(
                resumoMensalDAHCohortQueries.getTotalNumberPatientsInDAHSixMonthsCoorteI7())));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I8",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsRequestingCD4ScreeningDuringThePeriodI8(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientWithCD4ResultDuringThePeriodI9(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I9",
            mapStraightThrough(
                resumoMensalDAHCohortQueries.getNumberOfPatientWithCD4ResultDuringThePeriodI9(
                    disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I10",
            mapStraightThrough(
                resumoMensalDAHCohortQueries.getNumberOfPatientWithLowCD4ResultDuringThePeriodI10(
                    disagregationType))));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
          ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I11",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsElegibleToTBLAMWithTBLamResultDuringReportingPeriodI11(
                        disagregationType))));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
          ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I12",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsElegibleToTBLAMWithTBLamResultPOSDuringReportingPeriod12(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I13",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI13(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I14",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithCragSericoResultDuringReportingPeriodI14(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I15",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithCragLCRResultDuringReportingPeriodI15(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I16",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithMCCTreatmentResultDuringReportingPeriodI16(
                        disagregationType))));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
          ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I17",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithCragLcrPosInitiatedMCCTreatmentResultDuringReportingPeriodI17(
                        disagregationType))));
  }

  private Mapped<CohortIndicator>
      getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
          ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I18",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithNewSKDiagnosticAndQuimiotheraphyIndicationDuringPeriodI18(
                        disagregationType))));
  }

  private Mapped<CohortIndicator> getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(
      ARTSituation disagregationType) {
    return mapStraightThrough(
        eptsGeneralIndicator.getIndicator(
            "I19",
            mapStraightThrough(
                resumoMensalDAHCohortQueries
                    .getNumberOfPatientsWithQuimiotheraphyCicleOneDuringPeriodI19(
                        disagregationType))));
  }
}
