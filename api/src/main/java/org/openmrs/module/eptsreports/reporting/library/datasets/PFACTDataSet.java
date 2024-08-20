/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.datasets;

import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ABOVE_TWENTY;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TEN_TO_NINETEEN;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.UNDER_TEN;

import java.util.ArrayList;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.DSDCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxCurrCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxNewCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxRTTCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.DSDCommonDimensions;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.AgeRange;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PFACTDataSet extends BaseDataSet {

  private EptsCommonDimension eptsCommonDimension;

  private EptsGeneralIndicator eptsGeneralIndicator;

  private TxNewCohortQueries txNewCohortQueries;

  private TxRTTCohortQueries txRTTCohortQueries;

  private TxCurrCohortQueries txCurrCohortQueries;

  @Autowired
  public PFACTDataSet(
      EptsCommonDimension eptsCommonDimension,
      EptsGeneralIndicator eptsGeneralIndicator,
      DSDCohortQueries dsdCohortQueries,
      DSDCommonDimensions dsdCommonDimensions,
      TxNewCohortQueries txNewCohortQueries,
      TxRTTCohortQueries txRTTCohortQueries,
      TxCurrCohortQueries txCurrCohortQueries) {
    this.eptsCommonDimension = eptsCommonDimension;
    this.eptsGeneralIndicator = eptsGeneralIndicator;
    this.txNewCohortQueries = txNewCohortQueries;
    this.txRTTCohortQueries = txRTTCohortQueries;
    this.txCurrCohortQueries = txCurrCohortQueries;
  }

  public DataSetDefinition constructDSDDataset() {
    CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

    dsd.setName("PFACT Dataset");
    dsd.addParameters(getParameters());

    this.addAgeDimensions(dsd, UNDER_TEN, TEN_TO_NINETEEN, ABOVE_TWENTY);

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    /*
        final CohortDefinition patientsWithCD4LessThan200RTT =
            this.txRTTCohortQueries.getPLHIVGreather12MonthCalculationAndCD4Under200();

        final CohortIndicator patientsWithCD4LessThan200IndicatorRTT =
            this.eptsGeneralIndicator.getIndicator(
                "patientsWithCD4LessThan200",
                EptsReportUtils.map(patientsWithCD4LessThan200RTT, mappings));

        final CohortDefinition patientsWithRTTStage3_4 =
            this.txRTTCohortQueries.getPLHIVGreather12MonthCalculationAndStage3_4();

        final CohortIndicator patientsWithRTTStage3_4IndicatorRTT =
            this.eptsGeneralIndicator.getIndicator(
                "patientsRTTStage3_4", EptsReportUtils.map(patientsWithRTTStage3_4, mappings));
    */
    /*
        final CohortDefinition patientEnrolledInART =
            this.txNewCohortQueries.getTxNewCompositionCohort("patientEnrolledInART");

        final CohortIndicator patientEnrolledInHIVStartedARTIndicator =
            this.eptsGeneralIndicator.getIndicator(
                "patientNewlyEnrolledInHIVIndicator",
                EptsReportUtils.map(patientEnrolledInART, mappings));
    */

    /*
        final CohortDefinition patientWithLessCD4Definition =
            this.txNewCohortQueries.findPatientsWithCD4LessThan200();

        final CohortIndicator patientWithLessCD4Indicator =
            this.eptsGeneralIndicator.getIndicator(
                "patientWithLessCD4Indicator",
                EptsReportUtils.map(patientWithLessCD4Definition, mappings));
    */
    final CohortDefinition patientsWithCD4LessThan200TXCURRTBLAM =
        this.txCurrCohortQueries.findPatientsWithLessThan200CD4WhoAreActiveOnARTTBLAM();

    final CohortIndicator patientsWithCD4LessThan200IndicatorTXCURR =
        this.eptsGeneralIndicator.getIndicator(
            "patientsWithCD4LessThan200TbLam",
            EptsReportUtils.map(patientsWithCD4LessThan200TXCURRTBLAM, mappings));

    final CohortDefinition patientsWithCD4LessThan200TXCURRGenExpert =
        this.txCurrCohortQueries.findPatientsWithLessThan200CD4OnARTANDGenExpert();

    final CohortIndicator patientsWithCD4LessThan200IndicatorTXCURRGenExpert =
        this.eptsGeneralIndicator.getIndicator(
            "patientsWithCD4LessThan200GenExpert",
            EptsReportUtils.map(patientsWithCD4LessThan200TXCURRGenExpert, mappings));

    final CohortDefinition patientsWithCD4LessThan200TXCURRCrag =
        this.txCurrCohortQueries.findPatientsWithLessThan200CD4OnARTANDCrag();

    final CohortIndicator patientsWithCD4LessThan200IndicatorTXCURRCrag =
        this.eptsGeneralIndicator.getIndicator(
            "patientsWithCD4LessThan200GenExpert",
            EptsReportUtils.map(patientsWithCD4LessThan200TXCURRCrag, mappings));
    /*
    this.addColumns(
        "D1",
        "D1: TX_NEW CD4 Less than 200.",
        dsd,
        patientWithLessCD4Indicator,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

    final CohortDefinition patientInStage3Or4Definition =
        this.txNewCohortQueries.getTxNewStage3OR4CompositionCohort();

    final CohortIndicator patientInStage3or4Indicator =
        this.eptsGeneralIndicator.getIndicator(
            "patientWithLessCD4Indicator",
            EptsReportUtils.map(patientInStage3Or4Definition, mappings));

    this.addColumns(
        "D2",
        "D2: TX_NEW Stage 3 OR Stage 4.",
        dsd,
        patientInStage3or4Indicator,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

    this.addColumns(
        "D3",
        "D3: TX_RTT CD4 Less than 200.",
        dsd,
        patientsWithCD4LessThan200IndicatorRTT,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

    this.addColumns(
        "D4",
        "D4: TX_RTT Stage 3 OR Stage 4.",
        dsd,
        patientsWithRTTStage3_4IndicatorRTT,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY); */

    this.addColumns(
        "D5",
        "D5: TX_CURR CD4 Less than 200 AND TB LAM",
        dsd,
        patientsWithCD4LessThan200IndicatorTXCURR,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

    this.addColumns(
        "D6",
        "D6: TX_CURR CD4 Less than 200 AND TB GENEXPERT",
        dsd,
        patientsWithCD4LessThan200IndicatorTXCURRGenExpert,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

    this.addColumns(
        "D7",
        "D7: TX_CURR CD4 Less than 200 AND TB CRAG",
        dsd,
        patientsWithCD4LessThan200IndicatorTXCURRCrag,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);
    /*
    this.addColumns(
        "D1",
        "D1: Número de Pacientes Activos em TARV Elegíveis a MDS para Pacientes Estáveis.",
        dsd,
        patientWithLessCD4Definition,
        !addWonaState,
        mappings,
        UNDER_TEN,
        TEN_TO_NINETEEN,
        ABOVE_TWENTY);

        */
    /*
        this.addColumns(
            "D2",
            "D2: Número de Pacientes Activos em TARV não Elegíveis a MDS para Pacientes Estáveis",
            dsd,
            dsdCohortQueries.getDSDDenominator2(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "D3",
            "D3: Número de Pacientes Activos em TARV",
            dsd,
            this.dsdCohortQueries.getDSDDenominator3(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addBreastfeedingColumns(
            "D4",
            "D4: Número de Pacientes Activos em TARV,  elegíveis para Dispensa Bimestral",
            dsd,
            dsdCohortQueries.getDSDDenominator4(),
            !addWonaState,
            mappings,
            CHILDREN,
            ADULT);

        this.addColumns(
            "N1-E",
            "N1: Número de Pacientes Activos em TARV que se encontram inscritos em pelo menos um DSD para pacientes estáveis  (GA, DT, DS, DA, FR, DCA, DD) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator1(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N1-NE",
            "N1: Número de Pacientes Activos em TARV que se encontram inscritos em pelo menos um DSD para pacientes estáveis  (GA, DT, DS, DA, FR, DCA, DD) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator1(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N1-TOTAL",
            "Total patients Numerator 1",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator1",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator1(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N2-E",
            "N2: Número de Pacientes Activos em TARV que encontram-se inscritos no MDS: Dispensa Trimestral (DT) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator2(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N2-NE",
            "N2: Número de Pacientes Activos em TARV que encontram-se inscritos no MDS: Dispensa Trimestral (DT) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator2(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N2-TOTAL",
            "Total patients Numerator 2",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator2",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator2(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N3-E",
            "N3: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Semestral (DS) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator3(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N3-NE",
            "N3: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Semestral (DS) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator3(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N3-TOTAL",
            "Total patients Numerator 3",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator3",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator3(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N4-E",
            "N4: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Annual (DA) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator4(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N4-NE",
            "N4: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Annual (DA) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator4(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N4-TOTAL",
            "Total patients Numerator 4",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator4",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator4(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N5-E",
            "N5: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Descentralizada (DD) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator5(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N5-NE",
            "N5: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Descentralizada (DD) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator5(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N5-TOTAL",
            "Total patients Numerator 5",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator5",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator5(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N6-E",
            "N6: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Comunitaria atraves do APE (DCA) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator6(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N6-NE",
            "N6:  Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Comunitaria atraves do APE (DCA) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator6(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N6-TOTAL",
            "Total patients Numerator 6",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator6",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator6(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N7-E",
            "N7: Número de Pacientes activos em TARV qu encontram-se inscritos no MDS: Fluxo Rapido (FR) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator7(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N7-NE",
            "N7: Número de Pacientes activos em TARV qu encontram-se inscritos no MDS: Fluxo Rapido (FR) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator7(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N7-TOTAL",
            "Total patients Numerator 7",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator7",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator7(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N8-E",
            "N8: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: GAAC  (GA) - Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator8(),
            !addWonaState,
            mappings,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N8-NE",
            "N8: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: GAAC  (GA) - Não-Elegíveis para MDS Estáveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator8(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N8-TOTAL",
            "Total patients Numerator 8",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator8",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator8(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N9",
            "N9: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Comunitaria pelo Provedor (DCP)",
            dsd,
            dsdCohortQueries.getDSDNumerator9(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N10",
            "N10: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Brigada Movel (BM)",
            dsd,
            dsdCohortQueries.getDSDNumerator10(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addColumns(
            "N11-E",
            "N11: Número de Pacientes activos em TARV qu encontram-se inscritos no MDS: Clinica Movel (CM)",
            dsd,
            dsdCohortQueries.getDSDNumerator11(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 12

        this.addColumns(
            "N12-E",
            "N12:  Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Abordagem Familiar (AF)",
            dsd,
            dsdCohortQueries.getDSDNumerator12(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 13

        this.addColumns(
            "N13-E",
            "N13: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Clube de Adesao (CA)",
            dsd,
            dsdCohortQueries.getDSDNumerator13(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 14

        this.addColumns(
            "N14-E",
            "N14: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Extensao de Horario (EH)",
            dsd,
            dsdCohortQueries.getDSDNumerator14(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 15

        this.addColumns(
            "N15-E",
            "N15: Número de Pacientes activos em TARV qu encontram-se inscritos no MDS: Paragem Unica no Sector de Tuberculose (TB)",
            dsd,
            dsdCohortQueries.getDSDNumerator15(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 16

        this.addColumns(
            "N16-E",
            "N16: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Paragem Unica nos Servicos TARV (CT)",
            dsd,
            dsdCohortQueries.getDSDNumerator16(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 17

        this.addColumns(
            "N17-E",
            "N17: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Paragem Unica nos Servicos Amigos De Adolescentes e Jovens (SAAJ)",
            dsd,
            dsdCohortQueries.getDSDNumerator17(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 18

        this.addColumns(
            "N18-E",
            "N18: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Paragem Unica Saude Materno-Infantil (SMI)",
            dsd,
            dsdCohortQueries.getDSDNumerator18(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        // add Numerato 19

        this.addColumns(
            "N19-E",
            "N19: Número de Pacientes activos em TARV que encontram-se inscritos no MDS: Doenca Avancada por HIV (DAH)",
            dsd,
            dsdCohortQueries.getDSDEligibleNumerator19(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        this.addBreastfeedingColumns(
            "N20-E",
            "N20: Número de pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Bimestral (DB)",
            dsd,
            this.dsdCohortQueries.getDSDEligibleNumerator20(),
            !addWonaState,
            mappings,
            CHILDREN,
            ADULT);

        this.addColumns(
            "N20-NE",
            "N20: Número de pacientes activos em TARV que encontram-se inscritos no MDS: Dispensa Bimestral (DB) - Não Elegíveis",
            dsd,
            dsdCohortQueries.getDSDNotEligibleNumerator20(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);

        dsd.addColumn(
            "N20-TOTAL",
            "Total patients Numerator 20",
            EptsReportUtils.map(
                this.eptsGeneralIndicator.getIndicator(
                    "findTotalPatientsForNumerator20",
                    EptsReportUtils.map(dsdCohortQueries.getDSDTotalNumerator20(), mappings)),
                mappings),
            "");

        this.addColumns(
            "N21",
            "N21: Número de Pacientes activos em TARV que encontram-se inscritosem pelo menos um modelo MDS (DB, DT, DS, DA, DD, DCP, DCA, BM, CM,  AF, FR, GA, CA, EH, TB, C&T, SAAJ, SMI).",
            dsd,
            dsdCohortQueries.getDSDNumerator21(),
            addWonaState,
            mappings,
            UNDER_TWO,
            TWO_TO_FOUR,
            FIVE_TO_NINE,
            TEN_TO_FOURTEEN,
            ADULT);
    */
    return dsd;
  }

  private void addAgeDimensions(
      final CohortIndicatorDataSetDefinition definition, final AgeRange... ranges) {

    for (final AgeRange range : ranges) {
      definition.addDimension(
          range.getName(),
          EptsReportUtils.map(
              this.eptsCommonDimension.findPatientsByRangePFACT(range.getName(), range),
              "endDate=${endDate}"));
    }
  }

  private void addColumns(
      final String name,
      String label,
      final CohortIndicatorDataSetDefinition definition,
      final CohortIndicator cohortIndicator,
      String mappings,
      final AgeRange... ranges) {

    for (final AgeRange range : ranges) {

      String columnName = getColumnNameByRange(name, range);

      definition.addColumn(
          columnName,
          name + " - (" + range.getName() + ")",
          EptsReportUtils.map(cohortIndicator, mappings),
          range.getName() + "=" + range.getName());
    }
  }

  private String getColumnNameByRange(String columnNamePrefix, AgeRange range) {
    StringBuilder sb = new StringBuilder(columnNamePrefix);
    sb.append("-");
    sb.append(range.getName());
    return sb.toString();
  }

  @Override
  public List<Parameter> getParameters() {
    List<Parameter> parameters = new ArrayList<Parameter>();
    parameters.add(ReportingConstants.START_DATE_PARAMETER);
    parameters.add(ReportingConstants.END_DATE_PARAMETER);
    parameters.add(ReportingConstants.LOCATION_PARAMETER);
    return parameters;
  }
}
