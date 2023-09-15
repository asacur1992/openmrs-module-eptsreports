package org.openmrs.module.eptsreports.reporting.library.cohorts.mq;

import java.util.Date;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQCategory7CohortQueries {

  @Autowired private MQCohortQueries mQCohortQueries;

  @DocumentedDefinition(
      value =
          "findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndStartTPIAndElegibleTPTCategory7RF23Denominator")
  public CohortDefinition
      findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndStartTPIAndElegibleTPTCategory7RF23Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndStartTPIAndElegibleTPTCategory7RF23Denominator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(this.mQCohortQueries.findPatientsWhoTransferedOutRF07(), mappings));

    definition.addSearch(
        "TB-ACTIVE-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-SCREENING-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-TREATMENT-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingForMQCat7AndMQCat12(), mappings));

    definition.setCompositionString(
        "(PREGNANT AND START-ART)  NOT (TB-ACTIVE-CAT7 OR TB-SCREENING-CAT7 OR TB-TREATMENT-CAT7 OR TRANSFERED-IN OR TRANSFERED-OUT OR BREASTFEEDING)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPregnantNewlyEnrolledOnARTInInclusionPeriodAndStartTPIAndElegibleTPTDuringInclusionPeriodCategory7RF24Numerator(")
  public CohortDefinition
      findPregnantNewlyEnrolledOnARTInInclusionPeriodAndStartTPIAndElegibleTPTDuringInclusionPeriodCategory7RF24Numerator() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPregnantNewlyEnrolledOnARTInInclusionPeriodAndStartTPIAndElegibleTPTDuringInclusionPeriodCategory7RF24Numerator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF23-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndStartTPIAndElegibleTPTCategory7RF23Denominator(),
            mappings));

    definition.addSearch(
        "START-TPI-INH",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPINHDuringPeriodCategory7(), mappings));

    definition.setCompositionString("RF23-DENOMINATOR AND START-TPI-INH");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF25Denominator")
  public CohortDefinition
      findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF25Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF25Denominator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "TB-ACTIVE-CAT7-RF11",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-SCREENING-CAT7-RF11",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-TREATMENT-CAT7-RF11",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "TB-ACTIVE-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsDiagnosedWithActiveTBDuring9MonthsAfterInitiatedTPICategory7(),
            mappings));

    definition.addSearch(
        "TB-SCREENING-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWithPositiveTBScreeningDuring9MonthsAfterInitiatedINHCategory7(),
            mappings));

    definition.addSearch(
        "TB-TREATMENT-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .finPatientsWhoHadTBTreatmentDuring9MonthsAfterInitiatedINHCategory7(),
            mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(this.mQCohortQueries.findPatientsWhoTransferedOutRF07(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingForMQCat7AndMQCat12(), mappings));

    definition.addSearch(
        "START-TPI-INH",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPINHDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "START-TPI-3HP",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPI3HPDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBActiveASixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.addSearch(
        "B",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBScreeningSixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.addSearch(
        "C",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBTretmantSixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.setCompositionString(
        "((START-ART NOT (TB-ACTIVE-CAT7-RF11 OR TB-SCREENING-CAT7-RF11 OR TB-TREATMENT-CAT7-RF11)) AND (START-TPI-INH OR START-TPI-3HP)) "
            + "NOT (TB-ACTIVE-CAT7  "
            + "OR TB-SCREENING-CAT7 "
            + "OR TB-TREATMENT-CAT7 "
            + "OR TRANSFERED-IN "
            + "OR TRANSFERED-OUT "
            + "OR PREGNANT "
            + "OR BREASTFEEDING "
            + "OR A OR B OR C )");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF26Numerator(")
  public CohortDefinition
      findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF26Numerator() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF26Numerator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF25-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF25Denominator(),
            mappings));

    definition.addSearch(
        "END-TPI-INH-G",
        EptsReportUtils.map(
            this.mQCohortQueries
                .finPatientsWhoCompletedINHBetween170And297DaysAfterInitiatedTreatment(),
            mappings));

    definition.addSearch(
        "END-TPI-3HP-G",
        EptsReportUtils.map(
            this.mQCohortQueries
                .finPatientsWhoCompleted3HPBetween80And190DaysAfterInitiatedTreatment(),
            mappings));

    definition.setCompositionString("RF25-DENOMINATOR AND (END-TPI-INH-G OR END-TPI-3HP-G)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPragnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF29Denominator")
  public CohortDefinition
      findPragnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF29Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPragnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF29Denominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingForMQCat7AndMQCat12(), mappings));

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "START-TPI-INH",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPINHDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "START-TPI-3HP",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPI3HPDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(this.mQCohortQueries.findPatientsWhoTransferedOutRF07(), mappings));

    definition.addSearch(
        "TB-ACTIVE-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsDiagnosedWithActiveTBDuring9MonthsAfterInitiatedTPICategory7(),
            mappings));

    definition.addSearch(
        "TB-SCREENING-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWithPositiveTBScreeningDuring9MonthsAfterInitiatedINHCategory7(),
            mappings));

    definition.addSearch(
        "TB-TREATMENT-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries
                .finPatientsWhoHadTBTreatmentDuring9MonthsAfterInitiatedINHCategory7(),
            mappings));

    definition.addSearch(
        "A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBActiveASixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.addSearch(
        "B",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBScreeningSixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.addSearch(
        "C",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoAreTBTretmantSixMonthfterLastConsultationRF13_1(),
            mappings));

    definition.setCompositionString(
        "((PREGNANT AND START-ART) AND (START-TPI-INH)) NOT (TB-ACTIVE-CAT7 OR TB-SCREENING-CAT7 OR TB-TREATMENT-CAT7 OR TRANSFERED-IN OR TRANSFERED-OUT OR A OR B OR C OR BREASTFEEDING)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF30Numerator(")
  public CohortDefinition
      findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF30Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF30Numerator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF29-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPragnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF29Denominator(),
            mappings));

    definition.addSearch(
        "END-TPI-INH",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoCompleteTPIINHCategory7(), mappings));

    definition.setCompositionString("RF29-DENOMINATOR AND END-TPI-INH");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientWhoAreNewlyEnrolledOnARTDuringRevisionPeriodAndStartTPIAndElegibleTPTCategory7RF19Denominator")
  public CohortDefinition
      findPatientWhoAreNewlyEnrolledOnARTDuringRevisionPeriodAndStartTPIAndElegibleTPTCategory7RF19Denominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientWhoAreNewlyEnrolledOnARTDuringRevisionPeriodAndStartTPIAndElegibleTPTCategory7RF19Denominator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappings));

    definition.addSearch(
        "TB-ACTIVE-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-SCREENING-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappings));

    definition.addSearch(
        "TB-TREATMENT-CAT7",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(this.mQCohortQueries.findPatientsWhoTransferedOutRF07(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingForMQCat7AndMQCat12(), mappings));

    definition.setCompositionString(
        "START-ART NOT (TB-ACTIVE-CAT7 OR TB-SCREENING-CAT7 OR TB-TREATMENT-CAT7 OR TRANSFERED-IN OR TRANSFERED-OUT OR PREGNANT OR BREASTFEEDING)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsNewlyEnrolledOnARTInInclusionPeriodAndHaveINHDuringPeriodCategory7RF20Numerator(")
  public CohortDefinition
      findPatientsNewlyEnrolledOnARTInInclusionPeriodAndHaveINHDuringPeriodCategory7RF20Numerator() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientsNewlyEnrolledOnARTInInclusionPeriodAndHaveINHDuringPeriodCategory7RF20Numerator");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF19-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPatientWhoAreNewlyEnrolledOnARTDuringRevisionPeriodAndStartTPIAndElegibleTPTCategory7RF19Denominator(),
            mappings));

    definition.addSearch(
        "START-TPI-INH",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPINHDuringPeriodCategory7(), mappings));

    definition.addSearch(
        "START-TPI-3HP",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPI3HPDuringPeriodCategory7(), mappings));

    definition.setCompositionString("RF19-DENOMINATOR AND (START-TPI-INH OR START-TPI-3HP)");

    return definition;
  }
}
