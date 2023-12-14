package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.utils.EptsQuerysUtils;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** All queries needed for TxMl report needed for EPTS project */
@Component
public class TxMlCohortQueries {

  private static final String FIND_PATIENTS_WHO_STARTED_ART = "TX_ML/PATIENTS_WHO_STARTED_ART.sql";

  private static final String FIND_PATIENTS_WHO_WHERE_TRANSFERRED_OUT =
      "TX_ML/PATIENTS_WHO_WHERE_TRANSFERRED_OUT.sql";

  private static final String FIND_PATIENTS_WHO_ARE_DEAD = "TX_ML/PATIENTS_WHO_ARE_DEAD.sql";

  private static final String FIND_PATIENTS_WHO_ARE_IIT = "TX_ML/PATIENTS_WHO_ARE_IIT.sql";

  private static final String FIND_PATIENTS_WHO_STOPPED_REFUSED_TREATMENT =
      "TX_ML/PATIENTS_WHO_STOPPED_REFUSED_TREATMENT.sql";

  @Autowired private GenericCohortQueries genericCohorts;

  private String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

  public CohortDefinition getPatientstotalIIT() {
    CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("Get patients who are IIT (Totals)");
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));

    cd.addSearch("ART-START", EptsReportUtils.map(this.getPatientsWhOStartedART(), mappings));

    String query = EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_ARE_IIT);

    cd.addSearch(
        "IIT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql("Finding IIT Patients", query), mappings));

    cd.addSearch("DEAD", EptsReportUtils.map(this.getPatientsMarkedAsDead(), mappings));
    cd.addSearch(
        "TRANSFERREDOUT", EptsReportUtils.map(this.getPatientsWhoAreTransferedOut(), mappings));
    cd.addSearch(
        "STOPPED-TREATMENT",
        EptsReportUtils.map(this.getPatientsWhoRefusedOrStoppedTreatment(), mappings));

    cd.setCompositionString(
        "(ART-START AND (IIT OR STOPPED-TREATMENT)) NOT (DEAD OR TRANSFERREDOUT)");

    return cd;
  }

  public CohortDefinition getPatientsWhoAreIITLessThan3Months() {
    return getIITCohortDefinition(" IIT patients < 90 days", " where intervaloIIT < 90 ");
  }

  public CohortDefinition getPatientsWhoAreIITBetween3And5Months() {
    return getIITCohortDefinition(
        " IIT patients  >= 90 and  < 180  days",
        " where intervaloIIT >= 90 and intervaloIIT < 180");
  }

  public CohortDefinition getPatientsWhoAreIITGreaterOrEqual6Months() {
    return getIITCohortDefinition(" IIT patients >= 180 days", "where intervaloIIT  >= 180");
  }

  @DocumentedDefinition(value = "patientsWhoMissedNextApointment")
  public CohortDefinition getPatientsWhoMissedNextApointment() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("PatientsWhoMissedNextApointment");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    definition.addSearch("DEAD", EptsReportUtils.map(this.getPatientsMarkedAsDead(), mappings));
    definition.addSearch(
        "TRANSFERREDOUT", EptsReportUtils.map(this.getPatientsWhoAreTransferedOut(), mappings));
    definition.addSearch("IIT", EptsReportUtils.map(this.getPatientstotalIIT(), mappings));

    definition.setCompositionString("DEAD OR TRANSFERREDOUT OR IIT");

    return definition;
  }

  @DocumentedDefinition(value = "patientsWhoStartedArt")
  public CohortDefinition getPatientsWhOStartedART() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("patientsWhoStartedArt");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    definition.addSearch(
        "ART-START",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding patients who Initiated ART",
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_STARTED_ART)),
            mappings));

    definition.setCompositionString("ART-START");

    return definition;
  }

  @DocumentedDefinition(value = "patientsMarkedAsDead")
  public CohortDefinition getPatientsMarkedAsDead() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("patientsMarkedAsDead");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    definition.addSearch(
        "ART-START", EptsReportUtils.map(this.getPatientsWhOStartedART(), mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding patients who are marked as dead",
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_ARE_DEAD)),
            mappings));

    definition.setCompositionString("ART-START and DEAD");

    return definition;
  }

  @DocumentedDefinition(value = "PatientsWhoStoppedTreatment")
  public CohortDefinition getPatientsWhoRefusedOrStoppedTreatment() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("PatientsWhoStoppedTreatment");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    definition.addSearch(
        "ART-START", EptsReportUtils.map(this.getPatientsWhOStartedART(), mappings));

    definition.addSearch(
        "STOPPED-TREATMENT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding patients who refused/Stopped Treatment",
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_STOPPED_REFUSED_TREATMENT)),
            mappings));

    definition.setCompositionString("ART-START and STOPPED-TREATMENT");

    return definition;
  }

  @DocumentedDefinition(value = "patientsWhoAreTransferedOut")
  public CohortDefinition getPatientsWhoAreTransferedOut() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("patientsMarkedAsDead");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    definition.addSearch(
        "ART-START", EptsReportUtils.map(this.getPatientsWhOStartedART(), mappings));

    definition.addSearch(
        "TRANSFERREDOUT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding patients who are transferred out",
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_WHERE_TRANSFERRED_OUT)),
            mappings));

    definition.setCompositionString("ART-START AND TRANSFERREDOUT");

    return definition;
  }

  private CohortDefinition getIITCohortDefinition(String intervalLabel, String interval) {
    final CompositionCohortDefinition composition = new CompositionCohortDefinition();

    composition.setName("IIT -" + intervalLabel);
    composition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    composition.addParameter(new Parameter("endDate", "End Date", Date.class));
    composition.addParameter(new Parameter("location", "location", Location.class));

    composition.addSearch("IIT", EptsReportUtils.map(this.getPatientstotalIIT(), mappings));

    composition.addSearch(
        "IIT-INTERVAL",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding " + intervalLabel,
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_ARE_IIT) + interval),
            mappings));

    composition.addSearch(
        "STOPPED-REFUSED-TREATMENT",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "Finding " + intervalLabel,
                EptsQuerysUtils.loadQuery(FIND_PATIENTS_WHO_STOPPED_REFUSED_TREATMENT) + interval),
            mappings));

    composition.setCompositionString("IIT AND (IIT-INTERVAL or STOPPED-REFUSED-TREATMENT)");

    return composition;
  }
}
