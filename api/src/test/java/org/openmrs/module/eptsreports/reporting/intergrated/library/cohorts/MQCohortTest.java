package org.openmrs.module.eptsreports.reporting.intergrated.library.cohorts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory13P3CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory13Section1CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportConstants;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

public class MQCohortTest extends DefinitionsFGHLiveTest {

  @Autowired private MQCohortQueries mQCohortQueries;
  @Autowired private MQCategory13Section1CohortQueries mQCategory13Section1CohortQueries;
  @Autowired private MQCategory13P3CohortQueries mQCategory13P3CohortQueries;

  @Test
  public void shouldFindPatientsNewlyEnrolledInART() throws EvaluationException {

    final Location location = Context.getLocationService().getLocation(398);
    final Date startInclusionDate = DateUtil.getDateTime(2020, 1, 21);
    final Date endInclusionDate = DateUtil.getDateTime(2020, 4, 20);

    final Date revisionDate = DateUtil.getDateTime(2021, 1, 20);

    final Map<Parameter, Object> parameters = new HashMap<>();

    parameters.put(new Parameter("location", "Location", Location.class), location);
    parameters.put(
        new Parameter(EptsReportConstants.START_INCULSION_DATE, "Start Date", Date.class),
        startInclusionDate);
    parameters.put(
        new Parameter(EptsReportConstants.END_INCLUSION_DATE, "End Date", Date.class),
        endInclusionDate);
    parameters.put(
        new Parameter(EptsReportConstants.END_REVISION_DATE, "End Date", Date.class), revisionDate);

    CohortDefinition cohortDefinition =
        mQCategory13P3CohortQueries
            .findPatientsInFirstLineTherapheuticWhoReceivedViralChargeBetweenSixthAndNinthMonthAfterARTStartCategory13Denominador();

    final EvaluatedCohort evaluateCohortDefinition =
        this.evaluateCohortDefinition(cohortDefinition, parameters);

    System.out.println(evaluateCohortDefinition.getMemberIds().size());

    for (int t : evaluateCohortDefinition.getMemberIds()) {
      System.out.println(t);
    }
  }

  @Override
  protected String username() {
    return "admin";
  }

  @Override
  protected String password() {
    return "H!$fGH0Mr$";
  }
}
