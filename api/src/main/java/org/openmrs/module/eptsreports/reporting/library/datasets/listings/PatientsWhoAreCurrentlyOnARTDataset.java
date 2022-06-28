/** */
package org.openmrs.module.eptsreports.reporting.library.datasets.listings;

import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.queries.listings.PatientsWhoAreCurrentlyOnArtQueries;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

/** @author Stélio Moiane */
@Component
public class PatientsWhoAreCurrentlyOnARTDataset extends BaseDataSet {

  public DataSetDefinition constructPatientsWhoAreCurrentlyOnARTDataset(
      final List<Parameter> parameters) {
    final SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();

    sqlDataSetDefinition.setName("PATIENTS WHO ARE CURRENTLY ON ART LIST");
    sqlDataSetDefinition.setParameters(parameters);
    sqlDataSetDefinition.setSqlQuery(
        PatientsWhoAreCurrentlyOnArtQueries.QUERY.patientsWhoAreCurrentlyOnArtList);

    return sqlDataSetDefinition;
  }
}
