package org.openmrs.module.eptsreports.reporting.library.cohorts.mi;

import java.util.Date;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory7CohortQueries {
  @Autowired private MQCohortQueries mQCohortQueries;

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-8m+1d},endInclusionDate=${endRevisionDate-7m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "START-ART-A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappingsMI));

    definition.addSearch(
        "START-TPI-B4",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPIDuringPeriodCategory7(), mappingsMI));

    definition.addSearch(
        "TB-ACTIVE-CAT7-B1",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-SCREENING-CAT7-B2",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-CAT7-B3",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappingsMI));

    definition.addSearch(
        "TB-ACTIVE-TPI-CAT7-H",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBACTIVEAndTPIDuringPeriodCategory7AsH(),
            mappingsMI));

    definition.addSearch(
        "TB-TBSCREENING-TPI-CAT7-I",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBSCREENINGAndTPIDuringPeriodCategory7AsI(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-TPI-CAT7-J",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBTREATMENTAndTPIDuringPeriodCategory7AsJ(),
            mappingsMI));

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
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.setCompositionString(
        "(START-ART-A AND START-TPI-B4) NOT (TB-ACTIVE-CAT7-B1 OR TB-SCREENING-CAT7-B2 OR TB-TREATMENT-CAT7-B3 OR TRANSFERED-IN OR TRANSFERED-OUT OR PREGNANT OR BREASTFEEDING OR TB-ACTIVE-TPI-CAT7-H OR TB-TBSCREENING-TPI-CAT7-I OR TB-TREATMENT-TPI-CAT7-J)");

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-8m+1d},endInclusionDate=${endRevisionDate-7m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF25-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPatientsWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF25Denominator(),
            mappings));

    definition.addSearch(
        "END-TPI-G",
        EptsReportUtils.map(this.mQCohortQueries.findPatientWhoCompleteTPICategory7(), mappingsMI));

    definition.setCompositionString("RF25-DENOMINATOR AND END-TPI-G");

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-8m+1d},endInclusionDate=${endRevisionDate-7m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "START-ART-A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappingsMI));

    definition.addSearch(
        "START-TPI-B4",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPIDuringPeriodCategory7(), mappingsMI));

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
        "TB-ACTIVE-CAT7-B1",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-SCREENING-CAT7-B2",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-CAT7-B3",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappingsMI));

    definition.addSearch(
        "TB-ACTIVE-TPI-CAT7-H",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBACTIVEAndTPIDuringPeriodCategory7AsH(),
            mappingsMI));

    definition.addSearch(
        "TB-TBSCREENING-TPI-CAT7-I",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBSCREENINGAndTPIDuringPeriodCategory7AsI(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-TPI-CAT7-J",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientHaveTBTREATMENTAndTPIDuringPeriodCategory7AsJ(),
            mappingsMI));

    definition.setCompositionString(
        "(PREGNANT AND START-TPI-B4 AND START-ART-A) NOT (TB-ACTIVE-CAT7-B1 OR TB-SCREENING-CAT7-B2 OR TB-TREATMENT-CAT7-B3 OR TRANSFERED-IN OR TRANSFERED-OUT OR TB-ACTIVE-TPI-CAT7-H OR TB-TBSCREENING-TPI-CAT7-I OR TB-TREATMENT-TPI-CAT7-J)");

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-8m+1d},endInclusionDate=${endRevisionDate-7m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF29-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPragnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndEndTPICategory7RF29Denominator(),
            mappings));

    definition.addSearch(
        "END-TPI-G",
        EptsReportUtils.map(this.mQCohortQueries.findPatientWhoCompleteTPICategory7(), mappingsMI));

    definition.setCompositionString("RF29-DENOMINATOR AND END-TPI-G");

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "START-ART-A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappingsMI));

    definition.addSearch(
        "TB-ACTIVE-CAT7-B1",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-SCREENING-CAT7-B2",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-CAT7-B3",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappingsMI));

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
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.setCompositionString(
        "START-ART-A NOT (TB-ACTIVE-CAT7-B1 OR TB-SCREENING-CAT7-B2 OR TB-TREATMENT-CAT7-B3 OR TRANSFERED-IN OR TRANSFERED-OUT OR PREGNANT OR BREASTFEEDING)");

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
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF19-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPatientWhoAreNewlyEnrolledOnARTDuringRevisionPeriodAndStartTPIAndElegibleTPTCategory7RF19Denominator(),
            mappings));

    definition.addSearch(
        "START-TPI-B4",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPIDuringPeriodCategory7(), mappingsMI));

    definition.setCompositionString("RF19-DENOMINATOR AND START-TPI-B4");

    return definition;
  }

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "START-ART-A",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreNewlyEnrolledOnARTRF05(), mappingsMI));

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
        "TB-ACTIVE-CAT7-B1",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-SCREENING-CAT7-B2",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWithPositiveTBScreeningInDurindPeriodCategory7(),
            mappingsMI));

    definition.addSearch(
        "TB-TREATMENT-CAT7-B3",
        EptsReportUtils.map(
            this.mQCohortQueries.finPatientHaveTBTreatmentDuringPeriodCategory7(), mappingsMI));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(), mappings));

    definition.setCompositionString(
        "(PREGNANT AND START-ART-A)  NOT (TB-ACTIVE-CAT7-B1 OR TB-SCREENING-CAT7-B2 OR TB-TREATMENT-CAT7-B3 OR TRANSFERED-IN OR TRANSFERED-OUT OR BREASTFEEDING)");

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

    final String mappingsMI =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";

    final String mappings =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "RF23-DENOMINATOR",
        EptsReportUtils.map(
            this
                .findPregnantWhoAreNewlyEnrolledOnARTDuringInclusionPeriodAndStartTPIAndElegibleTPTCategory7RF23Denominator(),
            mappings));

    definition.addSearch(
        "START-TPI-B4",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientWhoStartTPIDuringPeriodCategory7(), mappingsMI));

    definition.setCompositionString("RF23-DENOMINATOR AND START-TPI-B4");

    return definition;
  }
}
