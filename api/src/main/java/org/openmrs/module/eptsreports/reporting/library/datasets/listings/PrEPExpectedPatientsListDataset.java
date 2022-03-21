package org.openmrs.module.eptsreports.reporting.library.datasets.listings;

import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.queries.listings.PrEPPatientExpectedQueries;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

/** @author Abdul Sacur */
@Component
public class PrEPExpectedPatientsListDataset extends BaseDataSet {

  public DataSetDefinition constructPatientExpectedPrEP(final List<Parameter> parameters) {

    final SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
    dataSetDefinition.setName("Patients Expected to PrEP");
    dataSetDefinition.addParameters(parameters);
    dataSetDefinition.setSqlQuery(PrEPPatientExpectedQueries.findPatientsExpectedPrEP);

    return dataSetDefinition;
  }
}
