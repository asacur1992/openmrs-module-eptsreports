package org.openmrs.module.eptsreports.reporting.library.cohorts.mi;

import java.util.Date;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory13Section1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.mqdatasets.MQAbstractDataSet;
import org.openmrs.module.eptsreports.reporting.library.dimensions.MIAgeDimentions;
import org.openmrs.module.eptsreports.reporting.library.dimensions.MQAgeDimensions;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.ReportType;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P3CohortQueries extends MQAbstractDataSet {

  @Autowired private MQCohortQueries mQCohortQueries;
  @Autowired private MQCategory13P3CohortQueries mQCategory13P3CohortQueries;
  @Autowired private MQAgeDimensions mQAgeDimensions;
  @Autowired private MIAgeDimentions mIAgeDimentions;
  @Autowired private MQCategory13Section1CohortQueries mQCategory13Section1CohortQueries;

  // Implementação de DENOMINADORES Categoria13.3
  // ------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * 13.2. % de adultos (15/+anos) na 1a linha de TARV que receberam o resultado da CV entre o sexto
   * e o nono mês após início do TARV Denominator: # de adultos que iniciaram TARV e novo regime no
   * período de inclusão (Line 77, Column F in the Template)
   */
  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Denominador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Denominador(
          boolean excludeTbActiveDiagnostic) {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3 DENOMINATOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    if (excludeTbActiveDiagnostic)
      definition.setCompositionString(
          "((START-ART NOT PREGNANT) OR (BI1 NOT B1E)) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT OR TB-ACTIVA)");
    else
      definition.setCompositionString(
          "((START-ART NOT PREGNANT) OR (BI1 NOT B1E)) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Denominador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Denominador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3 DENOMINATOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "((START-ART NOT (PREGNANT OR BREASTFEEDING)) OR (BI1 NOT B1E)) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Denominador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Denominador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3 DENOMINATOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "((START-ART NOT (PREGNANT OR BREASTFEEDING)) OR (BI1 NOT B1E)) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Denominador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Denominador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3 DENOMINATOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "((START-ART NOT (PREGNANT OR BREASTFEEDING)) OR (BI1 NOT B1E)) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  /**
   * 13.5.% de adultos (15/+anos) na 2a linha de TARV que receberam o resultado da CV entre o sexto
   * e o nono mês após o início da 2a linha de TARV Denominator: # de adultos (15/+ anos) que
   * iniciaram a 2a linha do TARV no período de inclusão (Line 80, Column F in the Template)
   */
  @DocumentedDefinition(
      value =
          "findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5")
  public CohortDefinition
      findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("Category13_3_Denominador_13_5");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "B2",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodCategory13P3B2NEWDenominator(),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "B2 NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  // Implementação de NUMERADORES Categoria13.3
  // ------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * 13.2. % de adultos (15/+anos) na 1a linha de TARV que receberam o resultado da CV entre o sexto
   * e o nono mês após início do TARV (Line 77 in the template) Numerator (Column E in the Template)
   */
  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Numerador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Numerador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3_NUMERADOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_G(),
            mappings));

    definition.addSearch(
        "H",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_H(),
            mappings));

    definition.addSearch(
        "J",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationWithViralChargeBetweenSixAndNineMonthsAfterARTStartDateCategory13_3_J_Numerator(),
            mappings));

    definition.addSearch(
        "K",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationAndEncounterDateTimeBetweenAlternativeFirstLineDateCategory13_3_K_Numerator(),
            mappings));

    definition.addSearch(
        "DD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(((START-ART AND (G OR J)) NOT (PREGNANT OR DD)) OR ((BI1 NOT B1E) AND (H OR K ))) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Numerador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Numerador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3_NUMERADOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_G(),
            mappings));

    definition.addSearch(
        "H",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_H(),
            mappings));

    definition.addSearch(
        "J",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationWithViralChargeBetweenSixAndNineMonthsAfterARTStartDateCategory13_3_J_Numerator(),
            mappings));

    definition.addSearch(
        "K",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationAndEncounterDateTimeBetweenAlternativeFirstLineDateCategory13_3_K_Numerator(),
            mappings));

    definition.addSearch(
        "DD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(((START-ART AND (G OR J)) NOT (PREGNANT OR BREASTFEEDING OR DD)) OR ((BI1 NOT B1E) AND (H OR K ))) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Numerador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_10Numerador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3_NUMERADOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_G(),
            mappings));

    definition.addSearch(
        "H",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_H(),
            mappings));

    definition.addSearch(
        "J",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationWithViralChargeBetweenSixAndNineMonthsAfterARTStartDateCategory13_3_J_Numerator(),
            mappings));

    definition.addSearch(
        "K",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationAndEncounterDateTimeBetweenAlternativeFirstLineDateCategory13_3_K_Numerator(),
            mappings));

    definition.addSearch(
        "DD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(((START-ART AND (G OR J)) NOT (PREGNANT OR BREASTFEEDING OR DD)) OR ((BI1 NOT B1E) AND (H OR K ))) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Numerador")
  public CohortDefinition
      findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_9Numerador() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("CAT13_3_NUMERADOR_13_2");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_G(),
            mappings));

    definition.addSearch(
        "H",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeCategory13_3_H(),
            mappings));

    definition.addSearch(
        "J",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationWithViralChargeBetweenSixAndNineMonthsAfterARTStartDateCategory13_3_J_Numerator(),
            mappings));

    definition.addSearch(
        "K",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationAndEncounterDateTimeBetweenAlternativeFirstLineDateCategory13_3_K_Numerator(),
            mappings));

    definition.addSearch(
        "DD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "BI1",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoAreInAlternativeLineFirstLineCategory13_3_BI1_Denominator(),
            mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHasTherapeuthicLineDiferentThanFirstLineFromConsultationClinicalCategory13_3_B1E_Denominator(),
            mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(((START-ART AND (G OR J)) NOT (PREGNANT OR BREASTFEEDING OR DD)) OR ((BI1 NOT B1E) AND (H OR K ))) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DD OR DROPPEDOUT OR TB-ACTIVA)");

    return definition;
  }

  /**
   * 13.5.% de adultos (15/+anos) na 2a linha de TARV que receberam o resultado da CV entre o sexto
   * e o nono mês após o início da 2a linha de TARV (Line 80 in the template) Numerator (Column E in
   * the Template)
   */
  @DocumentedDefinition(
      value =
          "findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5")
  public CohortDefinition
      findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("Category13_3_Numerador_13_5");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR-B2",
        EptsReportUtils.map(
            this
                .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5(),
            mappings));

    definition.addSearch(
        "I",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsFromClinicalConsultationWhoHaveViralChargeSecondLineDateCategory13_3_I(),
            mappings));

    definition.addSearch(
        "L",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveClinicalConsultationAndEncounterDateTimeBetweenSecondTherapheuticLineDateCategory13_3_L_Numerator(),
            mappings));

    definition.setCompositionString("DENOMINATOR-B2 AND (I OR L)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Denominator")
  public CohortDefinition
      findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Denominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR-13-2",
        EptsReportUtils.map(
            this
                .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Denominador(
                    true),
            mappings));

    definition.addSearch(
        "ONENDREVISIONDATE-15-PLUS",
        EptsReportUtils.map(
            mQAgeDimensions.calculateDefaulteAgeBiggerThanEndRevisionDate(15), mappings));

    definition.addSearch(
        "DENOMINATOR-13-5",
        EptsReportUtils.map(
            this
                .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5(),
            mappings));

    definition.addSearch(
        "ONSECONDLINE-15-PLUS",
        EptsReportUtils.map(
            mIAgeDimentions
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodP3B2NEWCalculeteAgeBiggerThan(
                    15),
            mappings));

    definition.setCompositionString(
        "(DENOMINATOR-13-2 AND ONENDREVISIONDATE-15-PLUS) OR (DENOMINATOR-13-5 AND ONSECONDLINE-15-PLUS)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_Numerator")
  public CohortDefinition
      findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_2Plus13_5Numerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "NUMERATOR-13-2",
        EptsReportUtils.map(
            this
                .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Numerador(),
            mappings));

    definition.addSearch(
        "ONENDREVISIONDATE-15-PLUS",
        EptsReportUtils.map(
            mQAgeDimensions.calculateDefaulteAgeBiggerThanEndRevisionDate(15), mappings));

    definition.addSearch(
        "NUMERATOR-13-5",
        EptsReportUtils.map(
            this
                .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5(),
            mappings));

    definition.addSearch(
        "ONSECONDLINE-15-PLUS",
        EptsReportUtils.map(
            mIAgeDimentions
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodP3B2NEWCalculeteAgeBiggerThan(
                    15),
            mappings));

    definition.setCompositionString(
        "(NUMERATOR-13-2 AND ONENDREVISIONDATE-15-PLUS) OR (NUMERATOR-13-5 AND ONSECONDLINE-15-PLUS)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Denominator")
  public CohortDefinition
      findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Denominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR-13-11",
        EptsReportUtils.map(
            this
                .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Denominador(),
            mappings));

    definition.addSearch(
        "ONENDREVISIONDATE-10-14",
        EptsReportUtils.map(mQAgeDimensions.findPatientsAgeRangeEndRevisionDate(2, 14), mappings));

    definition.addSearch(
        "DENOMINATOR-13-14",
        EptsReportUtils.map(
            this
                .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Denominador_13_5(),
            mappings));

    definition.addSearch(
        "ONSECONDLINE-2-14",
        EptsReportUtils.map(
            mIAgeDimentions
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodCategory13P3B2NEWDenominatorAgeRange(
                    2, 14),
            mappings));

    definition.setCompositionString(
        "(DENOMINATOR-13-11 AND ONENDREVISIONDATE-10-14) OR (DENOMINATOR-13-14 AND ONSECONDLINE-2-14)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Numerator")
  public CohortDefinition
      findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultsPatientsWithViralLoadResultInFirstOrSecondLineTARVDenominator13_11Plus13_14Numerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "NUMERATOR-13-11",
        EptsReportUtils.map(
            this
                .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_11Numerador(),
            mappings));

    definition.addSearch(
        "ONENDREVISIONDATE-10-14",
        EptsReportUtils.map(mQAgeDimensions.findPatientsAgeRangeEndRevisionDate(10, 14), mappings));

    definition.addSearch(
        "NUMERATOR-13-14",
        EptsReportUtils.map(
            this
                .findPatientsInSecondLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13_3_Numerador_13_5(),
            mappings));

    definition.addSearch(
        "ONSECONDLINE-2-14",
        EptsReportUtils.map(
            mIAgeDimentions
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodCategory13P3B2NEWDenominatorAgeRange(
                    2, 14),
            mappings));

    definition.setCompositionString(
        "(NUMERATOR-13-11 AND ONENDREVISIONDATE-10-14) OR (NUMERATOR-13-14 AND ONSECONDLINE-2-14)");

    return definition;
  }

  @DocumentedDefinition(value = "findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5")
  public CohortDefinition findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "B1E",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findAllPatientsWhoHaveTherapheuticLineSecondLineDuringInclusionPeriodCategory13P3B2NEWDenominator(),
            mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries.findAllPatientWhoAreDeadByEndOfRevisonPeriod(), mappings));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findAllPatientsWhoDroppedOutARTInFirstSixMonthsBeforeInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(((START-ART NOT PREGNANT) OR B1E) AND TB-ACTIVA) NOT (TRANSFERED-IN OR TRANSFERED-OUT OR DEAD OR DROPPEDOUT)");

    return definition;
  }

  @DocumentedDefinition(value = "findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_5")
  public CohortDefinition findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_5() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findCoinfectedWithTBAndHIVAdultsPatientsNumerator13_5");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsPeriodoAvaliacao =
        "startInclusionDate=${endRevisionDate-11m+1d},endInclusionDate=${endRevisionDate-10m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "TBHIV-DENOMINATOR-13-5",
        EptsReportUtils.map(
            this.findCoinfectedWithTBAndHIVAdultsPatientsDenominator13_5(), mappings));

    definition.addSearch(
        "CV-RESULT-AFTER-ART",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHaveViralLoadResultInFichaClinicaAndFichaResumoBetween6To9MonthsAfterInitiatedART(),
            mappingsPeriodoAvaliacao));

    definition.addSearch(
        "CV-RESULT-AFTER-SECOND-LINE",
        EptsReportUtils.map(
            mQCategory13P3CohortQueries
                .findPatientsWhoHaveViralLoadResultInFichaClinicaAndFichaResumoBetween6To9MonthsAfterSecondLineARTRegimen(),
            mappingsPeriodoAvaliacao));

    definition.setCompositionString(
        "TBHIV-DENOMINATOR-13-5 AND (CV-RESULT-AFTER-ART OR CV-RESULT-AFTER-SECOND-LINE)");

    return definition;
  }
}
