package org.openmrs.module.eptsreports.reporting.intergrated.library.cohorts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxRTTCohortQueries;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.DurationUnit;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

public class TxRTTCalculationTest extends DefinitionsFGHLiveTest {

  @Autowired private TxRTTCohortQueries txRTTCohortQueries;

  @Test
  public void shouldFindPatientsNewlyEnrolledInART() throws EvaluationException {

    final Location location = Context.getLocationService().getLocation(398);

    System.out.println(location.getName());
    final Date startDate = DateUtil.getDateTime(2020, 6, 21);
    final Date endDate = DateUtil.getDateTime(2021, 12, 31);

    System.out.println(startDate);
    System.out.println(endDate);

    final Map<Parameter, Object> parameters = new HashMap<>();
    parameters.put(new Parameter("startDate", "Start Date", Date.class), startDate);
    parameters.put(new Parameter("endDate", "End Date", Date.class), endDate);
    parameters.put(new Parameter("location", "Location", Location.class), location);

    final CohortDefinition definition = this.txRTTCohortQueries.getPatientsOnRTT();

    final EvaluatedCohort evaluateCohortDefinition =
        this.evaluateCohortDefinition(definition, parameters);

    System.out.println(evaluateCohortDefinition.getMemberIds().size());
    Assert.assertFalse(evaluateCohortDefinition.getMemberIds().isEmpty());

    System.out.println("----------------------------------");

    for (final int t : evaluateCohortDefinition.getMemberIds()) {
      System.out.println(t);
    }
  }

  // @Override
  // protected String username() {
  // return "admin";
  // }
  //
  // @Override
  // protected String password() {
  // return "H!$fGH0Mr$";
  // }

  @Override
  protected String username() {
    return "domingos.bernardo";
  }

  @Override
  protected String password() {
    return "dBernardo1";
  }

  public static void main(final String[] args) {

    final Date sstartDate = DateUtil.getDateTime(2018, 11, 14);
    final Date lastDate = DateUtil.adjustDate(sstartDate, 7, DurationUnit.MONTHS);

    System.out.println(lastDate);
  }
}
