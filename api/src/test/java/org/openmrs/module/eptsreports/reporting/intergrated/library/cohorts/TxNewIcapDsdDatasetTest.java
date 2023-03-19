/** */
package org.openmrs.module.eptsreports.reporting.intergrated.library.cohorts;

import java.util.Date;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxNewIcapDsdDataset;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

/** @author Stélio Moiane */
public class TxNewIcapDsdDatasetTest extends DefinitionsFGHLiveTest {

  @Autowired private TxNewIcapDsdDataset txNewIcapDsdDataset;

  private HashMap<Parameter, Object> parameters;

  @Before
  public void setup() {
    final Location location = Context.getLocationService().getLocation(270);
    final Date startDate = DateUtil.getDateTime(2022, 12, 21);
    final Date endDate = DateUtil.getDateTime(2023, 01, 20);

    this.parameters = new HashMap<>();

    this.parameters.put(new Parameter("startDate", "Start Date", Date.class), startDate);
    this.parameters.put(new Parameter("endDate", "End Date", Date.class), endDate);

    this.parameters.put(new Parameter("location", "Location", Location.class), location);
  }

  @Test
  public void shouldEvaluateTxNewIcapMdsDataSet() throws EvaluationException {

    final DataSetDefinition txNewDataset = this.txNewIcapDsdDataset.constructTxNewDataset();

    final DataSet evaluateDatasetDefinition =
        this.evaluateDatasetDefinition(txNewDataset, this.parameters);

    Assert.assertNotNull(evaluateDatasetDefinition.getMetaData().getColumns());
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
