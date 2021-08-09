package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoAreDeadCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoAreLTFUGreatherThan3MonthsCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoAreLTFULessThan3MonthsCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoAreTransferedOutCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoMissedNextApointmentCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.txml.TxMLPatientsWhoRefusedOrStoppedTreatmentCalculation;
import org.openmrs.module.eptsreports.reporting.cohort.definition.BaseFghCalculationCohortDefinition;
import org.openmrs.module.eptsreports.reporting.library.queries.TxCurrQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.TxMlQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** All queries needed for TxMl report needed for EPTS project */
@Component
public class TxMlCohortQueries {

  @Autowired private HivMetadata hivMetadata;

  @Autowired private GenericCohortQueries genericCohortQueries;

  public CohortDefinition getAllPatientsWhoMissedNextAppointment() {
    return this.genericCohortQueries.generalSql(
        "Missed Next appointment",
        TxMlQueries.getPatientsWhoMissedAppointment(
            30,
            183,
            this.hivMetadata.getReturnVisitDateForArvDrugConcept().getConceptId(),
            this.hivMetadata.getReturnVisitDateConcept().getConceptId(),
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId()));
  }

  public CohortDefinition getTransferOutPatients() {
    return this.genericCohortQueries.generalSql(
        "Transfer out",
        TxMlQueries.getTransferredOutPatients(
            this.hivMetadata.getARTProgram().getProgramId(),
            this.hivMetadata
                .getTransferredOutToAnotherHealthFacilityWorkflowState()
                .getProgramWorkflowStateId()));
  }

  public CohortDefinition getNonConsentedPatients() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Not Consented and Not dead");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "nonConsented",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "Non Consented patients",
                TxMlQueries.getNonConsentedPatients(
                    this.hivMetadata
                        .getPrevencaoPositivaInicialEncounterType()
                        .getEncounterTypeId(),
                    this.hivMetadata
                        .getPrevencaoPositivaSeguimentoEncounterType()
                        .getEncounterTypeId(),
                    this.hivMetadata.getAcceptContactConcept().getConceptId(),
                    this.hivMetadata.getNoConcept().getConceptId())),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.genericCohortQueries.getDeceasedPatientsBeforeDate(),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "homeVisitCardDead",
        EptsReportUtils.map(
            this.getPatientsMarkedAsDeadInHomeVisitCard(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.setCompositionString("nonConsented AND NOT (dead OR homeVisitCardDead)");
    return cd;
  }

  // a and b
  public CohortDefinition getPatientsWhoMissedNextAppointmentAndNotTransferredOut() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who missed appointment and are NOT transferred out");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "missedAppointment",
        EptsReportUtils.map(
            this.getAllPatientsWhoMissedNextAppointment(),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "transferOut",
        EptsReportUtils.map(
            this.getTransferOutPatients(), "endDate=${endDate},location=${location}"));
    cd.setCompositionString("missedAppointment AND NOT transferOut");
    return cd;
  }

  // a and b and died
  public CohortDefinition
      getPatientsWhoMissedNextAppointmentAndNotTransferredOutButDiedDuringReportingPeriod() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName(
        "Get patients who missed appointment and are NOT transferred out, but died during reporting period");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "missedAppointmentLessTransfers",
        EptsReportUtils.map(
            this.getPatientsWhoMissedNextAppointmentAndNotTransferredOut(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.genericCohortQueries.getDeceasedPatientsBeforeDate(),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "homeVisitCardDead",
        EptsReportUtils.map(
            this.getPatientsMarkedAsDeadInHomeVisitCard(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.setCompositionString("missedAppointmentLessTransfers AND (dead OR homeVisitCardDead)");
    return cd;
  }

  // All Patients marked as dead in Patient Home Visit Card
  private CohortDefinition getPatientsMarkedAsDeadInHomeVisitCard() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("Get patients marked as dead in Patient Home Visit Card");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(
        TxMlQueries.getPatientsMarkedDeadInHomeVisitCard(
            this.hivMetadata.getBuscaActivaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteAEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteBEncounterType().getEncounterTypeId(),
            this.hivMetadata.getReasonPatientNotFound().getConceptId(),
            this.hivMetadata.getPatientIsDead().getConceptId()));

    return sqlCohortDefinition;
  }

  // a and b and Not Consented
  public CohortDefinition
      getPatientsWhoMissedNextAppointmentAndNotTransferredOutAndNotConsentedDuringReportingPeriod() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName(
        "Get patients who missed appointment and are NOT transferred out, and NOT Consented during reporting period");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "missedAppointmentLessTransfers",
        EptsReportUtils.map(
            this.getPatientsWhoMissedNextAppointmentAndNotTransferredOut(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "notConsented",
        EptsReportUtils.map(
            this.getNonConsentedPatients(), "endDate=${endDate},location=${location}"));
    cd.setCompositionString("missedAppointmentLessTransfers AND notConsented");
    return cd;
  }

  // Patients Traced Not Found
  private CohortDefinition getPatientsTracedAndNotFound() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("Get patients traced (Unable to locate) and Not Found");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(
        TxMlQueries.getPatientsTracedWithVisitCard(
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getReturnVisitDateForArvDrugConcept().getConceptId(),
            this.hivMetadata.getReturnVisitDateConcept().getConceptId(),
            this.hivMetadata.getBuscaActivaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteAEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteBEncounterType().getEncounterTypeId(),
            this.hivMetadata.getTypeOfVisitConcept().getConceptId(),
            this.hivMetadata.getBuscaConcept().getConceptId(),
            this.hivMetadata.getPatientFoundConcept().getConceptId(),
            this.hivMetadata.getNoConcept().getConceptId()));

    return sqlCohortDefinition;
  }

  // Patients Traced and Found.
  private CohortDefinition getPatientTracedAndFound() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("Get patients traced (Unable to locate) and Found");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(
        TxMlQueries.getPatientsTracedWithVisitCard(
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getReturnVisitDateForArvDrugConcept().getConceptId(),
            this.hivMetadata.getReturnVisitDateConcept().getConceptId(),
            this.hivMetadata.getBuscaActivaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteAEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteBEncounterType().getEncounterTypeId(),
            this.hivMetadata.getTypeOfVisitConcept().getConceptId(),
            this.hivMetadata.getBuscaConcept().getConceptId(),
            this.hivMetadata.getPatientFoundConcept().getConceptId(),
            this.hivMetadata.getPatientFoundYesConcept().getConceptId()));

    return sqlCohortDefinition;
  }

  // a and b and Traced (Unable to locate)
  public CohortDefinition getPatientsWhoMissedNextAppointmentAndNotTransferredOutAndTraced() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();

    cd.setName(
        "Get patients who missed next appointment, not transferred out and traced (Unable to locate)");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "missedAppointmentLessTransfers",
        EptsReportUtils.map(
            this.getPatientsWhoMissedNextAppointmentAndNotTransferredOut(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "patientsNotFound",
        EptsReportUtils.map(
            this.getPatientsTracedAndNotFound(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "patientsFound",
        EptsReportUtils.map(
            this.getPatientTracedAndFound(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.genericCohortQueries.getDeceasedPatientsBeforeDate(),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "homeVisitCardDead",
        EptsReportUtils.map(
            this.getPatientsMarkedAsDeadInHomeVisitCard(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));

    cd.setCompositionString(
        "missedAppointmentLessTransfers AND (patientsNotFound AND NOT patientsFound) AND NOT (dead OR homeVisitCardDead)");

    return cd;
  }

  /*
   * a and b and Untraced Patients
   */
  public CohortDefinition getPatientsWhoMissedNextAppointmentAndNotTransferredOutAndUntraced() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();

    cd.setName("Get patients who missed next appointment, not transferred out and untraced");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "missedAppointmentLessTransfers",
        EptsReportUtils.map(
            this.getPatientsWhoMissedNextAppointmentAndNotTransferredOut(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "withVisitCard",
        EptsReportUtils.map(
            this.getPatientsWithVisitCardRegisteredBtwnLastAppointmentOrDrugPickupAndEnddate(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "withVisitCardandWithoutObs",
        EptsReportUtils.map(
            this.getPatientsWithVisitCardAndWithoutObs(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.genericCohortQueries.getDeceasedPatientsBeforeDate(),
            "endDate=${endDate},location=${location}"));
    cd.addSearch(
        "homeVisitCardDead",
        EptsReportUtils.map(
            this.getPatientsMarkedAsDeadInHomeVisitCard(),
            "startDate=${startDate},endDate=${endDate},location=${location}"));

    cd.setCompositionString(
        "missedAppointmentLessTransfers AND (NOT withVisitCard OR withVisitCardandWithoutObs) AND NOT (dead OR homeVisitCardDead)");

    return cd;
  }

  /*
   * Untraced Patients Criteria 2 Patients with a set of observations
   */
  public CohortDefinition getPatientsWithVisitCardAndWithoutObs() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("Get patients without Visit Card but with a set of observations");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(
        TxMlQueries.getPatientsWithVisitCardAndWithoutObs(
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getReturnVisitDateForArvDrugConcept().getConceptId(),
            this.hivMetadata.getReturnVisitDateConcept().getConceptId(),
            this.hivMetadata.getBuscaActivaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteAEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteBEncounterType().getEncounterTypeId(),
            this.hivMetadata.getTypeOfVisitConcept().getConceptId(),
            this.hivMetadata.getBuscaConcept().getConceptId(),
            this.hivMetadata.getSecondAttemptConcept().getConceptId(),
            this.hivMetadata.getThirdAttemptConcept().getConceptId(),
            this.hivMetadata.getPatientFoundConcept().getConceptId(),
            this.hivMetadata.getDefaultingMotiveConcept().getConceptId(),
            this.hivMetadata.getReportOfVisitSupportConcept().getConceptId(),
            this.hivMetadata.getPatientHadDifficultyConcept().getConceptId(),
            this.hivMetadata.getPatientFoundForwardedConcept().getConceptId(),
            this.hivMetadata.getReasonPatientNotFound().getConceptId(),
            this.hivMetadata.getWhoGaveInformationConcept().getConceptId(),
            this.hivMetadata.getCardDeliveryDateConcept().getConceptId()));

    return sqlCohortDefinition;
  }

  /*
   * Untraced Patients Criteria 2 All Patients without “Patient Visit Card” registered between the last scheduled
   * appointment or drugs pick up by reporting end date and the reporting end date
   */
  public CohortDefinition
      getPatientsWithVisitCardRegisteredBtwnLastAppointmentOrDrugPickupAndEnddate() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName(
        "Get patients without Visit Card registered between the last scheduled appointment or drugs pick up by reporting end date and the reporting end date");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(
        TxMlQueries.getPatientsWithVisitCardRegisteredBtwnLastAppointmentOrDrugPickupAndEnddate(
            this.hivMetadata.getARVPharmaciaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getReturnVisitDateForArvDrugConcept().getConceptId(),
            this.hivMetadata.getReturnVisitDateConcept().getConceptId(),
            this.hivMetadata.getBuscaActivaEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteAEncounterType().getEncounterTypeId(),
            this.hivMetadata.getVisitaApoioReintegracaoParteBEncounterType().getEncounterTypeId()));

    return sqlCohortDefinition;
  }

  public CohortDefinition getPatientsWhoAreLTFULessThan3Months() {
    final String mapping = "startDate=${startDate},endDate=${endDate},location=${location}";
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who are LTFU less than 3 months");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "missedAppointmentLess3Month",
        EptsReportUtils.map(this.getPatientsWhoAreLTFULessThan3MonthsCalculation(), mapping));
    cd.addSearch("dead", EptsReportUtils.map(this.getPatientsMarkedAsDead(), mapping));
    cd.addSearch(
        "transferedOut", EptsReportUtils.map(this.getPatientsWhoAreTransferedOut(), mapping));
    cd.addSearch(
        "refusedTreatment",
        EptsReportUtils.map(this.getPatientsWhoRefusedOrStoppedTreatment(), mapping));
    cd.addSearch(
        "numerator", EptsReportUtils.map(this.getPatientsWhoMissedNextApointment(), mapping));

    cd.setCompositionString(
        "(numerator AND missedAppointmentLess3Month) NOT (dead OR transferedOut OR refusedTreatment)");
    return cd;
  }

  public CohortDefinition getCommunityPatientsWhoAreLTFULessThan3Months() {
    final String mapping = "startDate=${startDate},endDate=${endDate},location=${location}";

    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who are LTFU less than 3 months");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "PWHLFUT3M", EptsReportUtils.map(this.getPatientsWhoAreLTFULessThan3Months(), mapping));

    cd.addSearch(
        "COMMUNITY-DISPENSATION",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findCommunityPatientsDispensation",
                TxCurrQueries.QUERY.findCommunityPatientsDispensation),
            mapping));

    cd.setCompositionString("PWHLFUT3M AND COMMUNITY-DISPENSATION");

    return cd;
  }

  public CohortDefinition getPatientsWhoAreLTFUGreaterThan3Months() {
    final String mapping = "startDate=${startDate},endDate=${endDate},location=${location}";
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who are LTFU less than 3 months");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "missedAppointmentGreater3Month",
        EptsReportUtils.map(this.getPatientsWhoAreLTFUGreatherThan3MonthsCalculation(), mapping));
    cd.addSearch("dead", EptsReportUtils.map(this.getPatientsMarkedAsDead(), mapping));
    cd.addSearch(
        "transferedOut", EptsReportUtils.map(this.getPatientsWhoAreTransferedOut(), mapping));
    cd.addSearch(
        "refusedTreatment",
        EptsReportUtils.map(this.getPatientsWhoRefusedOrStoppedTreatment(), mapping));
    cd.addSearch(
        "numerator", EptsReportUtils.map(this.getPatientsWhoMissedNextApointment(), mapping));

    cd.setCompositionString(
        "(numerator AND missedAppointmentGreater3Month) NOT (dead OR transferedOut OR refusedTreatment)");
    return cd;
  }

  public CohortDefinition getCommunityPatientsWhoAreLTFUGreaterThan3Months() {
    final String mapping = "startDate=${startDate},endDate=${endDate},location=${location}";
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who are LTFU less than 3 months");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch(
        "PWLFUGT3M", EptsReportUtils.map(this.getPatientsWhoAreLTFUGreaterThan3Months(), mapping));

    cd.addSearch(
        "COMMUNITY-DISPENSATION",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findCommunityPatientsDispensation",
                TxCurrQueries.QUERY.findCommunityPatientsDispensation),
            mapping));

    cd.setCompositionString("PWLFUGT3M AND COMMUNITY-DISPENSATION");
    return cd;
  }

  @DocumentedDefinition(value = "patientsWhoMissedNextApointment")
  public CohortDefinition getPatientsWhoMissedNextApointment() {
    final BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "txMLPatientsWhoMissedNextApointmentCalculation",
            Context.getRegisteredComponents(TxMLPatientsWhoMissedNextApointmentCalculation.class)
                .get(0));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }

  @DocumentedDefinition(value = "communityPatientsWhoMissedNextApointment")
  public CohortDefinition getCommunityPatientsWhoMissedNextApointment() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "end Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "CPWMNA", EptsReportUtils.map(this.getPatientsWhoMissedNextApointment(), mappings));

    definition.addSearch(
        "COMMUNITY-DISPENSATION",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findCommunityPatientsDispensation",
                TxCurrQueries.QUERY.findCommunityPatientsDispensation),
            mappings));

    definition.setCompositionString("CPWMNA AND COMMUNITY-DISPENSATION");

    return definition;
  }

  @DocumentedDefinition(value = "patientsMarkedAsDead")
  public CohortDefinition getPatientsMarkedAsDead() {
    final BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "patientsMarkedAsDeadCalculation",
            Context.getRegisteredComponents(TxMLPatientsWhoAreDeadCalculation.class).get(0));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }

  @DocumentedDefinition(value = "patientsWhoAreTransferedOut")
  public CohortDefinition getPatientsWhoAreTransferedOut() {
    final BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "patientsWhoAreTransferedOutCalculation",
            Context.getRegisteredComponents(TxMLPatientsWhoAreTransferedOutCalculation.class)
                .get(0));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }

  @DocumentedDefinition(value = "patientsWhoRefusedStoppedTreatmentCalculation")
  public CohortDefinition getPatientsWhoRefusedOrStoppedTreatment() {
    final BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "patientsWhoRefusedStoppedTreatmentCalculation",
            Context.getRegisteredComponents(
                    TxMLPatientsWhoRefusedOrStoppedTreatmentCalculation.class)
                .get(0));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }

  @DocumentedDefinition(value = "patientsWhoAreLTFULessThan3MonthsCalculation")
  private CohortDefinition getPatientsWhoAreLTFULessThan3MonthsCalculation() {
    final BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "patientsWhoAreLTFULessThan3MonthsCalculation",
            Context.getRegisteredComponents(TxMLPatientsWhoAreLTFULessThan3MonthsCalculation.class)
                .get(0));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }

  @DocumentedDefinition(value = "patientsWhoAreLTFUGreatherThan3MonthsCalculation")
  public CohortDefinition getPatientsWhoAreLTFUGreatherThan3MonthsCalculation() {

    BaseFghCalculationCohortDefinition cd =
        new BaseFghCalculationCohortDefinition(
            "patientsWhoAreLTFUGreatherThan3MonthsCalculation",
            Context.getRegisteredComponents(
                    TxMLPatientsWhoAreLTFUGreatherThan3MonthsCalculation.class)
                .get(0));
    cd.setName("getPatientsWhoAreLTFUGreatherThan3MonthsCalculation");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "end Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    return cd;
  }
}
