package org.openmrs.module.eptsreports.reporting.library.cohorts.mi;

import java.util.Date;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory13Section1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory13P1_1CohortQueries {

  @Autowired private MQCategory13Section1CohortQueries MQCategory13Section1CohortQueries;
  @Autowired private MQCohortQueries mQCohortQueries;
  @Autowired private MICategory13P1_2CohortQueries mICategory13P1_2CohortQueries;
  @Autowired private MQCategory13Section1CohortQueries mQCategory13Section1CohortQueries;

  @DocumentedDefinition(value = "findDenominatorCategory13SectionIB")
  public CohortDefinition findDenominatorCategory13SectionIB(boolean excludeDiagnostivTbActiva) {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findDenominatorCategory13SectionIB");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate-1m},location=${location}";

    final String mappingsReinitiated =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate-1m},location=${location}";

    definition.addSearch(
        "B1",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationDenominatorB1(),
            mappingsMI));

    definition.addSearch(
        "B2NEW",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreNotInFistLineDenominatorB2NEW(),
            mappings));

    definition.addSearch(
        "B3",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreInLinhaAlternativaDenominatorB3(),
            mappingsMI));

    definition.addSearch(
        "B3E",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreDiferentFirstLineLinhaAternativaDenominatorB3E(),
            mappingsMI));

    definition.addSearch(
        "B5E",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithRequestCVInTheLast12MonthsBeforeLastConsultation(),
            mappingsMI));

    definition.addSearch(
        "C",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries.findPatientsWhoArePregnantCAT13Part1(), mappingsMI));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            mQCohortQueries
                .findAllPatientsWhoDroppedOutARTDuringTheLastSixMonthsBeforeLastClinicalConsultation(),
            mappings));

    definition.addSearch(
        "REINITIATED-ART",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsMarkedAsReinitiatedARTForAtLeastSixMonthsBeforeLastClinicalConsultation(),
            mappingsReinitiated));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    if (excludeDiagnostivTbActiva)
      definition.setCompositionString(
          "(B1 AND (B2NEW OR REINITIATED-ART OR (B3 NOT B3E ))) NOT (B5E OR C OR DROPPEDOUT OR TRANSFERED-IN OR TB-ACTIVA)");
    else
      definition.setCompositionString(
          "(B1 AND (B2NEW OR REINITIATED-ART OR (B3 NOT B3E ))) NOT (B5E OR C OR DROPPEDOUT OR TRANSFERED-IN)");

    return definition;
  }

  public CohortDefinition findDenominatorCategory13SectionIBChildrens() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findDenominatorCategory13SectionIB");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate-1m},location=${location}";

    final String mappingsReinitiated =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate-1m},location=${location}";

    definition.addSearch(
        "B1",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationDenominatorB1(),
            mappingsMI));

    definition.addSearch(
        "B2NEW",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreNotInFistLineDenominatorB2NEW(),
            mappings));

    definition.addSearch(
        "B3",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreInLinhaAlternativaDenominatorB3(),
            mappingsMI));

    definition.addSearch(
        "B3E",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithLastClinicalConsultationwhoAreDiferentFirstLineLinhaAternativaDenominatorB3E(),
            mappingsMI));

    definition.addSearch(
        "B5E",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsWithRequestCVInTheLast12MonthsBeforeLastConsultation(),
            mappingsMI));

    definition.addSearch(
        "C",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries.findPatientsWhoArePregnantCAT13Part1(), mappingsMI));

    definition.addSearch(
        "D",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries.findPatientsWhoAreBreastfeedingCAT13Part1(),
            mappingsMI));

    definition.addSearch(
        "DROPPEDOUT",
        EptsReportUtils.map(
            mQCohortQueries
                .findAllPatientsWhoDroppedOutARTDuringTheLastSixMonthsBeforeLastClinicalConsultation(),
            mappings));

    definition.addSearch(
        "REINITIATED-ART",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries
                .findPatientsMarkedAsReinitiatedARTForAtLeastSixMonthsBeforeLastClinicalConsultation(),
            mappingsReinitiated));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TB-ACTIVA",
        EptsReportUtils.map(
            mQCategory13Section1CohortQueries
                .findPatientsWithDiagnosticoTBAtivaDuringRevisionPeriod(),
            mappings));

    definition.setCompositionString(
        "(B1 AND (B2NEW OR REINITIATED-ART OR (B3 NOT B3E ))) NOT (B5E OR C OR D OR DROPPEDOUT OR TRANSFERED-IN OR TB-ACTIVA)");

    return definition;
  }

  @DocumentedDefinition(value = "findFinalNumeratorCategory13SectionIC")
  public CohortDefinition findFinalNumeratorCategory13SectionIC() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findFinalNumeratorCategory13SectionIC");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate-1m},location=${location}";

    definition.addSearch(
        "Denominator",
        EptsReportUtils.map(this.findDenominatorCategory13SectionIB(true), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries.findNumeratorCategory13Section1C(), mappingsMI));

    definition.setCompositionString("(Denominator AND G)");

    return definition;
  }

  @DocumentedDefinition(value = "findFinalNumeratorCategory13SectionICChildrens")
  public CohortDefinition findFinalNumeratorCategory13SectionICChildrens() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findFinalNumeratorCategory13SectionIC");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate-1m},location=${location}";

    definition.addSearch(
        "Denominator",
        EptsReportUtils.map(this.findDenominatorCategory13SectionIBChildrens(), mappings));

    definition.addSearch(
        "G",
        EptsReportUtils.map(
            MQCategory13Section1CohortQueries.findNumeratorCategory13Section1C(), mappingsMI));

    definition.setCompositionString("(Denominator AND G)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Denominator")
  public CohortDefinition
      findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Denominator(
          boolean excludeTbActivaDiagnostic) {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Denominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR-13-1",
        EptsReportUtils.map(
            this.findDenominatorCategory13SectionIB(excludeTbActivaDiagnostic), mappings));

    definition.addSearch(
        "DENOMINATOR-13-4",
        EptsReportUtils.map(
            this.mICategory13P1_2CohortQueries.findDenominatorCategory13SectionIIB(
                excludeTbActivaDiagnostic),
            mappings));

    definition.setCompositionString("DENOMINATOR-13-1 OR DENOMINATOR-13-4");

    return definition;
  }

  @DocumentedDefinition(
      value = "findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Numerator")
  public CohortDefinition
      findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAdultsPatientsInFirstOrSecondLineTARVDenominator13_1Plus13_4Numerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "NUMERATOR-13-1",
        EptsReportUtils.map(this.findFinalNumeratorCategory13SectionIC(), mappings));

    definition.addSearch(
        "NUMERATOR-13-4",
        EptsReportUtils.map(
            this.mICategory13P1_2CohortQueries.findFinalNumeratorCategory13SectionIIC(), mappings));

    definition.setCompositionString("NUMERATOR-13-1 OR NUMERATOR-13-4");

    return definition;
  }

  @DocumentedDefinition(
      value = "findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Denominator")
  public CohortDefinition findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Denominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR-13-8",
        EptsReportUtils.map(this.findDenominatorCategory13SectionIBChildrens(), mappings));

    definition.addSearch(
        "DENOMINATOR-13-13",
        EptsReportUtils.map(
            this.mICategory13P1_2CohortQueries.findDenominatorCategory13SectionIIB(true),
            mappings));

    definition.setCompositionString("DENOMINATOR-13-8 OR DENOMINATOR-13-13");

    return definition;
  }

  @DocumentedDefinition(value = "findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Numerator")
  public CohortDefinition findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findChildrenPatientsOnFirstOrSecondLineARV13_8Plus13_13Numerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "NUMERATOR-13-8",
        EptsReportUtils.map(this.findFinalNumeratorCategory13SectionICChildrens(), mappings));

    definition.addSearch(
        "NUMERATOR-13-13",
        EptsReportUtils.map(
            this.mICategory13P1_2CohortQueries.findFinalNumeratorCategory13SectionIIC(), mappings));

    definition.setCompositionString("NUMERATOR-13-8 OR NUMERATOR-13-13");

    return definition;
  }
}
