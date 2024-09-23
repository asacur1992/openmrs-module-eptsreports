package org.openmrs.module.eptsreports.reporting.library.cohorts.mi;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory9CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.mq.MICategory9QueriesInterface;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.TypePTV;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory9CohortQueries {

  @Autowired private MQCohortQueries mQCohortQueries;
  @Autowired private MQCategory9CohortQueries mQCategory9CohortQueries;

  @DocumentedDefinition(value = "findPatientsFirstConsultationOnInclusionDate")
  public CohortDefinition findPatientsFirstConsultationOnInclusionDate() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query = MICategory9QueriesInterface.QUERY.findPatientsFirstConsultationOnInclusionDate;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWhoArePregnantDuringInclusionPeriod")
  public CohortDefinition findPatientsWhoArePregnantDuringInclusionPeriod() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoArePregnantDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY.getPatientsWhoArePregnantOrBreastfeeding(
            TypePTV.PREGNANT);

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWhoAreBreastfeedingInFirstConsultation")
  public CohortDefinition findPatientsWhoAreBreastfeedingInFirstConsultation() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingInFirstConsultation");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY.getPatientsWhoArePregnantOrBreastfeeding(
            TypePTV.BREASTFEEDING);

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWhoAreBreastfeedingDuringInclusionPeriod")
  public CohortDefinition findPatientsWhoAreBreastfeedingDuringInclusionPeriod() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY.getPatientsWhoArePregnantOrBreastfeeding(
            TypePTV.BREASTFEEDING);

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9")
  public CohortDefinition
      findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY
            .findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPregnantWomanWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9")
  public CohortDefinition
      findPregnantWomanWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY
            .findPregnantWomanWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsWhithCD4On33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9")
  public CohortDefinition
      findPatientsWhithCD4On33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY
            .findPatientsWhithCD4On33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9")
  public CohortDefinition
      findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY
            .findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPregnantWomanWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9")
  public CohortDefinition
      findPregnantWomanWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoAreBreastfeedingDuringInclusionPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MICategory9QueriesInterface.QUERY
            .findPregnantWomanWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWhoArePregnantDuringPreviousPeriod")
  public CohortDefinition findPatientsWhoArePregnantDuringPreviousPeriod() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findPatientsWhoArePregnantDuringPreviousPeriod");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query = MICategory9QueriesInterface.QUERY.findPatientsWhoArePregnantDuringPreviousPeriod;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "FIRST-CONSULTATION",
        EptsReportUtils.map(this.findPatientsFirstConsultationOnInclusionDate(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappings));

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(this.findPatientsWhoArePregnantDuringInclusionPeriod(), mappings));

    definition.setCompositionString(
        "FIRST-CONSULTATION NOT (PREGNANT OR TRANSFERED-IN OR PREGNANT-INCLUSION-DATE)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_2")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_2() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_2");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsBackThreeMonths =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "FIRST-CONSULTATION",
        EptsReportUtils.map(
            this.findPatientsFirstConsultationOnInclusionDate(), mappingsBackThreeMonths));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappings));

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(
            this.findPatientsWhoArePregnantDuringInclusionPeriod(), mappingsBackThreeMonths));

    definition.setCompositionString(
        "FIRST-CONSULTATION NOT (PREGNANT OR TRANSFERED-IN OR PREGNANT-INCLUSION-DATE)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3")
  public CohortDefinition
      findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsBackThreeMonths =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate-2m},location=${location}";

    definition.addSearch(
        "REINICIO",
        EptsReportUtils.map(
            mQCategory9CohortQueries.findPatientsWhoReinitiatedTreatmentCat9RF29(),
            mappingsBackThreeMonths));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries
                .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06(),
            mappings));

    definition.setCompositionString("(REINICIO NOT TRANSFERED-IN)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioAfterAbandonedTreatmentNumerator9_3")
  public CohortDefinition
      findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioAfterAbandonedTreatmentNumerator9_3() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioAfterAbandonedTreatmentNumerator9_3");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsBackThreeMonths =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate-2m},location=${location}";

    definition.addSearch(
        "DENOMINATOR-9-3",
        EptsReportUtils.map(
            this
                .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
            mappings));

    definition.addSearch(
        "REINICIO-PEDIDO-CD4",
        EptsReportUtils.map(
            mQCategory9CohortQueries.findPatientsWithReinicioAndPedidoDeCD4InTheSameFichaClinica(),
            mappingsBackThreeMonths));

    definition.setCompositionString("(DENOMINATOR-9-3 AND REINICIO-PEDIDO-CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findAdultPatientsWithCD4Result33DaysAfterClinicalConsultationMarkedWithReinicioARTAndPedidoCd4Numerator9_4")
  public CohortDefinition
      findAdultPatientsWithCD4Result33DaysAfterClinicalConsultationMarkedWithReinicioARTAndPedidoCd4Numerator9_4() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findAdultPatientsWithCD4Result33DaysAfterClinicalConsultationMarkedWithReinicioARTAndPedidoCd4Numerator9_4");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsBackThreeMonths =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate-2m},location=${location}";

    definition.addSearch(
        "DENOMINATOR-9-4",
        EptsReportUtils.map(
            this
                .findAdultPatientsWithRequestCD4InTheSameClinicalConsultationMarkedAsReinicioDenominator9_3(),
            mappings));

    definition.addSearch(
        "CD4-RESULT",
        EptsReportUtils.map(
            mQCategory9CohortQueries
                .findPatientsWhoReceivedCD4ResultIn33DaysAfterTheClinicalConsultationMarkedAsReinicio(),
            mappingsBackThreeMonths));

    definition.setCompositionString("DENOMINATOR-9-4 AND CD4-RESULT");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_5")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_5() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_5");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "FIRST-CONSULTATION",
        EptsReportUtils.map(this.findPatientsFirstConsultationOnInclusionDate(), mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(),
            mappingsFirstConsultation));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(),
            mappingsFirstConsultation));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappingsFirstConsultation));

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(this.findPatientsWhoArePregnantDuringInclusionPeriod(), mappings));

    definition.addSearch(
        "BREASTFEEDING-INCLUSION-DATE",
        EptsReportUtils.map(this.findPatientsWhoAreBreastfeedingDuringInclusionPeriod(), mappings));

    definition.setCompositionString(
        "FIRST-CONSULTATION NOT (PREGNANT OR BREASTFEEDING OR TRANSFERED-IN OR PREGNANT-INCLUSION-DATE OR BREASTFEEDING-INCLUSION-DATE)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsBackThreeMonths =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "FIRST-CONSULTATION",
        EptsReportUtils.map(
            this.findPatientsFirstConsultationOnInclusionDate(), mappingsBackThreeMonths));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoArePregnantInclusionDateRF08(),
            mappingsFirstConsultation));

    definition.addSearch(
        "BREASTFEEDING",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoAreBreastfeedingInclusionDateRF09(),
            mappingsFirstConsultation));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappingsFirstConsultation));

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(
            this.findPatientsWhoArePregnantDuringInclusionPeriod(), mappingsBackThreeMonths));

    definition.addSearch(
        "BREASTFEEDING-INCLUSION-DATE",
        EptsReportUtils.map(
            this.findPatientsWhoAreBreastfeedingDuringInclusionPeriod(), mappingsBackThreeMonths));

    definition.setCompositionString(
        "FIRST-CONSULTATION NOT (PREGNANT OR BREASTFEEDING OR TRANSFERED-IN OR PREGNANT-INCLUSION-DATE OR BREASTFEEDING-INCLUSION-DATE)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_5")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_5() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_5");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_5(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_1")
  public CohortDefinition
      findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_1() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_1");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPragnantWomanWhoARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_9")
  public CohortDefinition
      findPragnantWomanWhoARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_9() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPragnantWomanWhoARTWhoHaveAreFirstConsultationNumeratorAdultCategory9_9_9");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsForDenominator =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9(),
            mappingsForDenominator));

    definition.addSearch(
        "CD4",
        EptsReportUtils.map(
            this
                .findPregnantWomanWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPregnantWomanInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9Section9_1")
  public CohortDefinition
      findPregnantWomanInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9Section9_1() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9Section9_1");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_1(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_2")
  public CohortDefinition
      findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_2() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_2");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_2(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4-33-DAYS",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4-33-DAYS)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_6")
  public CohortDefinition
      findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_6() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_6");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4-33-DAYS",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4-33-DAYS)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_4_Numerator")
  public CohortDefinition
      findPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_4_Numerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPatientsInARTWhoHaveAreFirstConsultationNumeratorAdultCategory9Section9_1");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endInclusionDate=${endRevisionDate-1m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsInARTWhoHaveAreFirstConsultationDenominatorAdultCategory9_9_6(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4-33-DAYS",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4-33-DAYS)");

    return definition;
  }

  @DocumentedDefinition(
      value =
          "findPregnantWomanPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_10")
  public CohortDefinition
      findPregnantWomanPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_10() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName(
        "findPregnantWomanPatientsWhoHaveAreFirstConsultationAndHaveNumeratorAdultCategory9_9_10");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsForDenominator =
        "startInclusionDate=${endRevisionDate},endInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_10(),
            mappingsForDenominator));

    definition.addSearch(
        "CD4-33-DAYS",
        EptsReportUtils.map(
            this
                .findPregnantWomanWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4-33-DAYS)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9")
  public CohortDefinition findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(this.findPatientsWhoArePregnantDuringPreviousPeriod(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappings));

    definition.setCompositionString("PREGNANT-INCLUSION-DATE NOT TRANSFERED-IN ");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_10")
  public CohortDefinition findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_10() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_10");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PREGNANT-INCLUSION-DATE",
        EptsReportUtils.map(this.findPatientsWhoArePregnantDuringPreviousPeriod(), mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoWhereMarkedAsTransferedInOnMasterCardRF5Category9(),
            mappings));

    definition.setCompositionString("PREGNANT-INCLUSION-DATE NOT TRANSFERED-IN ");

    return definition;
  }

  @DocumentedDefinition(value = "findPragnantWomanWhoHaveAreFirstConsultationNumeratorCategory9")
  public CohortDefinition findPragnantWomanWhoHaveAreFirstConsultationNumeratorCategory9() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPragnantWomanWhoHaveAreFirstConsultationNumeratorCategory9");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4)");

    return definition;
  }

  @DocumentedDefinition(
      value = "findPragnantWomanWhoHaveAreCd433DaysAfterFirstConsultationNumeratorCategory9")
  public CohortDefinition
      findPragnantWomanWhoHaveAreCd433DaysAfterFirstConsultationNumeratorCategory9() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPragnantWomanWhoHaveAreFirstConsultationNumeratorCategory9");

    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappingsFirstConsultation =
        "endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPragnantWomanWhoHaveAreFirstConsultationDenominatorCategory9_9_9(),
            mappingsFirstConsultation));

    definition.addSearch(
        "CD4-33-DAYS",
        EptsReportUtils.map(
            this
                .findPatientsWhithCD4On33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9(),
            mappings));

    definition.setCompositionString("(DENOMINATOR AND CD4-33-DAYS)");

    return definition;
  }
}
