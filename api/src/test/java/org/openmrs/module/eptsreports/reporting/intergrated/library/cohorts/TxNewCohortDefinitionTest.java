package org.openmrs.module.eptsreports.reporting.intergrated.library.cohorts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.cohorts.TxNewCohortQueries;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

/** @author Stélio Moiane */
public class TxNewCohortDefinitionTest extends DefinitionsFGHLiveTest {

  @Autowired private TxNewCohortQueries txNewCohortQueries;

  @Test
  public void shouldFindPatientsNewlyEnrolledInART() throws EvaluationException {

    final Location location = Context.getLocationService().getLocation(269);
    final Date startDate = DateUtil.getDateTime(2021, 03, 21);
    final Date endDate = DateUtil.getDateTime(2021, 06, 20);

    final Map<Parameter, Object> parameters = new HashMap<>();

    parameters.put(new Parameter("startDate", "Start Date", Date.class), startDate);
    parameters.put(new Parameter("endDate", "End Date", Date.class), endDate);
    parameters.put(new Parameter("location", "Location", Location.class), location);

    final CohortDefinition txNewCompositionCohort =
        this.txNewCohortQueries.getTxNewCommunityCompositionCohort("TX_NEW");

    final EvaluatedCohort evaluateCohortDefinition =
        this.evaluateCohortDefinition(txNewCompositionCohort, parameters);

    Assert.assertFalse(evaluateCohortDefinition.getMemberIds().isEmpty());

    for (final int t : evaluateCohortDefinition.getMemberIds()) {
      System.out.println(t);
    }
  }

  @Override
  protected String username() {
    return "admin";
  }

  @Override
  protected String password() {
    return "Ic@pSIS2021";
  }
}
