/** */
package org.openmrs.module.eptsreports.reporting.library.datasets.listings;

import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.queries.listings.PatientsEligibleToViralLoadQueries;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

/** @author Stélio Moiane */
@Component
public class PatientsEligibleToViralLoadDataset extends BaseDataSet {

  public DataSetDefinition constructPatientsEligibleToViralLoadDataset(
      final List<Parameter> parameters) {

    final SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
    dataSetDefinition.setName("Patients Elibible to Viral Load");
    dataSetDefinition.addParameters(parameters);
    dataSetDefinition.setSqlQuery(
        PatientsEligibleToViralLoadQueries.QUERY.findPatientsEligibleToViralLoad);

    return dataSetDefinition;
  }
}
