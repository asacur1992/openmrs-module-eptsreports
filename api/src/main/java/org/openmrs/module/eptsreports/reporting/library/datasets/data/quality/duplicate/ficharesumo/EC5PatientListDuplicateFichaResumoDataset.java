/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.datasets.data.quality.duplicate.ficharesumo;

import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.queries.data.quality.duplicate.ficharesumo.EC5DuplicateFichaResumoQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class EC5PatientListDuplicateFichaResumoDataset extends BaseDataSet {

  public DataSetDefinition ec5PatientWithDuplicatedFichaResumoListDataset(
      List<Parameter> parameterList) {
    SqlDataSetDefinition dsd = new SqlDataSetDefinition();
    dsd.setName("ECD5");
    dsd.addParameters(parameterList);
    dsd.setSqlQuery(
        EC5DuplicateFichaResumoQueries.QUERY.findPatientsWithMoreThanOneFRLInTheSameDate);

    return dsd;
  }

  public CohortDefinition getEC5Total(List<Parameter> parameterList) {

    final SqlCohortDefinition definition = new SqlCohortDefinition();
    definition.setName("ECD5");
    definition.addParameters(parameterList);
    definition.setQuery(EC5DuplicateFichaResumoQueries.QUERY.getEc5Total);

    return definition;
  }
}
