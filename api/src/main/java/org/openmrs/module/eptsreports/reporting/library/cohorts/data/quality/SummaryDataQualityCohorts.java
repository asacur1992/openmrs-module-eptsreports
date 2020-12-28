package org.openmrs.module.eptsreports.reporting.library.cohorts.data.quality;

import java.util.Date;
import java.util.List;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.library.queries.BaseQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.data.quality.SummaryQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryDataQualityCohorts {

  private final HivMetadata hivMetadata;

  @Autowired
  public SummaryDataQualityCohorts(final HivMetadata hivMetadata) {
    this.hivMetadata = hivMetadata;
  }

  /**
   * Get pregnant male patients
   *
   * @return Cohort Definition
   */
  public CohortDefinition getPregnantMalePatients() {

    final SqlCohortDefinition pCd = new SqlCohortDefinition();

    pCd.setName("Pregnant patients recorded in the system and are male ");
    pCd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    pCd.addParameter(new Parameter("endDate", "End Date", Date.class));
    pCd.addParameter(new Parameter("location", "Location", Location.class));

    pCd.setQuery(
        SummaryQueries.getPregnantPatients(
            this.hivMetadata.getPregnantConcept().getConceptId(),
            this.hivMetadata.getYesConcept().getConceptId(),
            this.hivMetadata.getNumberOfWeeksPregnant().getConceptId(),
            this.hivMetadata.getPregnancyDueDate().getConceptId(),
            this.hivMetadata.getARVAdultInitialEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
            this.hivMetadata.getPtvEtvProgram().getProgramId()));
    return pCd;
  }

  /**
   * Get Breastfeeding male patients
   *
   * @return Cohort Definition
   */
  public CohortDefinition getBreastfeedingMalePatients() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Get male breastfeeding patients recorded in the system");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setQuery(
        SummaryQueries.getBreastfeedingMalePatients(
            this.hivMetadata.getPriorDeliveryDateConcept().getConceptId(),
            this.hivMetadata.getCriteriaForArtStart().getConceptId(),
            this.hivMetadata.getBreastfeeding().getConceptId(),
            this.hivMetadata.getBreastfeeding().getConceptId(),
            this.hivMetadata.getYesConcept().getConceptId(),
            this.hivMetadata.getPtvEtvProgram().getProgramId(),
            this.hivMetadata.getPatientIsBreastfeedingWorkflowState().getProgramWorkflowStateId(),
            this.hivMetadata.getARVAdultInitialEncounterType().getEncounterTypeId(),
            this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId()));

    return cd;
  }

  /**
   * Note:<all, active&transferred in, abandoned, transferred out>
   *
   * @return CohortDefinition
   */
  public CohortDefinition getQualityDataReportBaseCohort() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patient States");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Facilities", Location.class, List.class, null));
    cd.addParameter(
        EptsReportUtils.getProgramConfigurableParameter(this.hivMetadata.getARTProgram()));
    cd.setQuery(
        BaseQueries.getBaseQueryForDataQuality(this.hivMetadata.getARTProgram().getProgramId()));
    return cd;
  }

  /**
   * Get patients with states and a list of encounters
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWithStatesAndEncounters(
      final int programId, final int stateId, final List<Integer> encounterList) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patients who have state that is before an encounter");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setQuery(
        SummaryQueries.getPatientsWithStateThatIsBeforeAnEncounter(
            programId, stateId, encounterList));
    return cd;
  }

  public CohortDefinition getPatientsWithStatesAndEncountersEC10() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patients who have state that is before an encounter");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.setQuery(SummaryQueries.getPatientsWithStateThatIsBeforeAnEncounterEC10());
    return cd;
  }

  /**
   * Get patients with states and a list of encounters
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWithStatesAndEncountersEC11(
      final int programId,
      final int stateId,
      final int labEncounterType,
      final int fsrLabEncounterType,
      final int sampleCollectionDateConceptId,
      final int requestLaboratoryDateConceptId) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patients who have state that is before an encounter - EC11");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setQuery(
        SummaryQueries.getPatientsWithStateThatIsBeforeAnEncounterEC11(
            programId,
            stateId,
            labEncounterType,
            fsrLabEncounterType,
            sampleCollectionDateConceptId,
            requestLaboratoryDateConceptId));
    return cd;
  }

  /**
   * Get patients with states and a list of encounters
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWithStatesAndEncountersEC4(
      final int programId,
      final int stateId,
      final int adultFollowUp,
      final int childFollowUp,
      final int fichaResumo,
      final int stateOfStayPriorArtPatient,
      final int stateOfStayOfArtPatient,
      final int patientHasDiedConcept,
      final List<Integer> encounterList) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("Patients who have state that is before an encounter");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.setQuery(
        SummaryQueries.getPatientsWithStateThatIsBeforeAnEncounterEC4(
            programId,
            stateId,
            adultFollowUp,
            childFollowUp,
            fichaResumo,
            stateOfStayPriorArtPatient,
            stateOfStayOfArtPatient,
            patientHasDiedConcept));
    return cd;
  }

  /**
   * Patients with a birth date that is before 1920 The patient’s date of birth, estimated date of
   * birth or entered age indicate the patient was born before 1920
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWhoseBirthdateIsBeforeYear(final int year) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("EC12");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setName("Patients with a birth date that is before " + year);
    cd.setQuery(SummaryQueries.getPatientsWhoseYearOfBirthIsBeforeYear(year));
    return cd;
  }

  /**
   * The patients date of birth, estimated date of birth or age is negative
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWithNegativeAge() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setName("The patients date of birth, estimated date of birth or age is negative");
    cd.setQuery(SummaryQueries.getPatientsWithNegativeBirthDates());
    return cd;
  }

  /**
   * The patients birth, estimated date of birth or age indicates they are > years of age
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWithAgeHigherThanXyears(final int years) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setName("The patients birth, estimated date of birth or age indicates they are > " + years);
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.setQuery(SummaryQueries.getPatientsWithMoreThanXyears(years));
    return cd;
  }

  public CohortDefinition getCountPatientsWithExceptionConsultation() {
    final SqlCohortDefinition cd = new SqlCohortDefinition();

    cd.setName(
        "The value of a date field registered on any form, with the exception of consultation date, is before 1985 ");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.setQuery(SummaryQueries.getCountPatientsEC14());
    return cd;
  }

  /**
   * The patient’s date of birth is after any drug pick up date
   *
   * @return CohortDefinition
   */
  public CohortDefinition getPatientsWhoseBirthDatesAreAfterEncounterDate(
      final List<Integer> encounterList) {
    final SqlCohortDefinition cd = new SqlCohortDefinition();
    cd.setName("The patient’s date of birth is after any drug pick up date");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.setQuery(SummaryQueries.getPatientsWhoseBirthdateIsAfterDrugPickup(encounterList));
    return cd;
  }

  /**
   * The patients who are marked as dead in the patient states or those marked as deceased in the
   * person object
   *
   * @return CohortDefinition
   */
  public CohortDefinition getDeadOrDeceasedPatientsHavingEncountersAfter(
      final int programId, final int stateId, final List<Integer> encounterList) {
    final SqlCohortDefinition sql = new SqlCohortDefinition();
    sql.setName("Deceased and have encounters after deceased date");
    sql.addParameter(new Parameter("location", "Location", Location.class));
    sql.setQuery(SummaryQueries.getPatientsMarkedAsDeceasedAndHaveAnEncounter(encounterList));

    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Dead or deceased patients");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.getPatientsWithStatesAndEncounters(programId, stateId, encounterList),
            "location=${location}"));
    cd.addSearch("deceased", EptsReportUtils.map(sql, "location=${location}"));
    cd.setCompositionString("dead OR deceased");
    return cd;
  }

  public CohortDefinition getDeadOrDeceasedPatientsHavingEncountersAfterEC3() {
    final SqlCohortDefinition sql = new SqlCohortDefinition();
    sql.setName("Deceased and have encounters after deceased date");
    sql.addParameter(new Parameter("location", "Location", Location.class));
    sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sql.addParameter(new Parameter("endDate", "End Date", Date.class));
    sql.setQuery(SummaryQueries.getPatientsMarkedAsDeceasedAndHaveAnEncounterEC3());
    return sql;
  }

  /**
   * The patients who are marked as dead in the patient states or those marked as deceased in the
   * person object
   *
   * @return CohortDefinition
   */
  public CohortDefinition getDeadOrDeceasedPatientsHavingEncountersAfterEC4(
      final int programId,
      final int stateId,
      final int adultFollowUp,
      final int childFollowUp,
      final int fichaResumo,
      final int stateOfStayPriorArtPatient,
      final int stateOfStayOfArtPatient,
      final int patientHasDiedConcept,
      final List<Integer> encounterList) {
    final SqlCohortDefinition sql = new SqlCohortDefinition();
    sql.setName("Deceased and have encounters after deceased date");
    sql.addParameter(new Parameter("location", "Location", Location.class));
    sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sql.addParameter(new Parameter("endDate", "End Date", Date.class));
    sql.setQuery(
        SummaryQueries.getPatientsWithStateThatIsBeforeAnEncounterEC4(
            programId,
            stateId,
            adultFollowUp,
            childFollowUp,
            fichaResumo,
            stateOfStayPriorArtPatient,
            stateOfStayOfArtPatient,
            patientHasDiedConcept));

    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Dead or deceased patients");
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addSearch(
        "dead",
        EptsReportUtils.map(
            this.getPatientsWithStatesAndEncountersEC4(
                programId,
                stateId,
                adultFollowUp,
                childFollowUp,
                fichaResumo,
                stateOfStayPriorArtPatient,
                stateOfStayOfArtPatient,
                patientHasDiedConcept,
                encounterList),
            "location=${location},startDate=${startDate},endDate=${endDate}"));
    cd.addSearch(
        "deceased",
        EptsReportUtils.map(sql, "location=${location},startDate=${startDate},endDate=${endDate}"));
    cd.setCompositionString("dead OR deceased");
    return cd;
  }

  /**
   * The patients whose date of drug pick up is before 1985 The date of clinical consultation is
   * before 1985
   *
   * @param encounterList
   * @return
   */
  public CohortDefinition getPatientsWhoseEncounterIsBefore1985(final List<Integer> encounterList) {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();
    sqlCohortDefinition.setName("patients whose date of drug pick up is before 1985");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));
    sqlCohortDefinition.setQuery(
        SummaryQueries.getPatientsWhoseEncounterIsBefore1985(encounterList));

    return sqlCohortDefinition;
  }

  public CohortDefinition getPatientsWhoseEncounterIsBeforeEC18() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("patients whose date of drug pick up is before 1985");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setQuery(SummaryQueries.getPatientsWhoseEncounterIsBeforeEC18());

    return sqlCohortDefinition;
  }

  public CohortDefinition getPatientsSexNotDefinedEC21() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setName("The Patient’s sex is not defined");
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));
    sqlCohortDefinition.setQuery(SummaryQueries.getPatientsSexNotDefinedEC21());

    return sqlCohortDefinition;
  }

  public CohortDefinition getPatientsSexNotDefinedEC22() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setName("The Patient’s date of birth is not defined");
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));
    sqlCohortDefinition.setQuery(SummaryQueries.getPatientsBirthNotDefinedEC22());

    return sqlCohortDefinition;
  }

  public CohortDefinition getPatientsWhoseEncounterIsBeforeEC17() {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));

    sqlCohortDefinition.setName("patients whose date of drug pick up is before 1985");
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));
    sqlCohortDefinition.setQuery(SummaryQueries.getPatientsWhoseEncounterIsBeforeEC17());
    return sqlCohortDefinition;
  }

  /**
   * The patients whose date of drug pick up is before 1985 The date of clinical consultation is
   * before 1985
   *
   * @param encounterList
   * @return
   */
  public CohortDefinition getPatientsWhoseEncounterIsBefore1985EC19(
      final int programId,
      final int labEncounterType,
      final int FSREncounterType,
      final int masterCardEncounterType,
      final int adultoSeguimentoEncounterType,
      final int aRVPediatriaSeguimentoEncounterType) {
    final SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();

    sqlCohortDefinition.setName("patients whose date of drug pick up is before 1985");
    sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    sqlCohortDefinition.addParameter(new Parameter("location", "Location", Location.class));
    sqlCohortDefinition.setQuery(
        SummaryQueries.getPatientsWhoseEncounterIsBefore1985EC19(
            programId,
            labEncounterType,
            FSREncounterType,
            masterCardEncounterType,
            adultoSeguimentoEncounterType,
            aRVPediatriaSeguimentoEncounterType));

    return sqlCohortDefinition;
  }
}
