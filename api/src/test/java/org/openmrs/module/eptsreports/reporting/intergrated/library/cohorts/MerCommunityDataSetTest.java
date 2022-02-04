/** */
package org.openmrs.module.eptsreports.reporting.intergrated.library.cohorts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.datasets.IndicatorType;
import org.openmrs.module.eptsreports.reporting.library.datasets.TxRttDataset;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

/** @author Stélio Moiane */
public class MerCommunityDataSetTest extends DefinitionsFGHLiveTest {

	@Autowired
	private TxRttDataset dataset;

	private Map<Parameter, Object> parameters;

	@Before
	public void setup() {
		final Location location = Context.getLocationService().getLocation(305);
		final Date startDate = DateUtil.getDateTime(2021, 10, 21);
		final Date endDate = DateUtil.getDateTime(2021, 12, 20);

		this.parameters = new HashMap<>();
		this.parameters.put(new Parameter("startDate", "Start Date", Date.class), startDate);
		this.parameters.put(new Parameter("endDate", "End Date", Date.class), endDate);
		this.parameters.put(new Parameter("location", "Location", Location.class), location);
	}

	@Test
	public void shoulEvaluateDataSet() throws EvaluationException {

		final DataSetDefinition numeratorDataset = this.dataset.indicatorType(IndicatorType.COMMUNITY).constructTxRttDataset();

		final DataSet evaluateDatasetDefinition = this.evaluateDatasetDefinition(numeratorDataset, this.parameters);

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
