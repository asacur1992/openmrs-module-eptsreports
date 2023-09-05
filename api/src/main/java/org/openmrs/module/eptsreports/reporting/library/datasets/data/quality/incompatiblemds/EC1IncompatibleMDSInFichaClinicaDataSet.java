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
package org.openmrs.module.eptsreports.reporting.library.datasets.data.quality.incompatiblemds;

import java.util.List;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.library.queries.data.quality.incompatiblemds.EC1IncompatibleMDSInFichaClinicaQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class EC1IncompatibleMDSInFichaClinicaDataSet extends BaseDataSet {

  public DataSetDefinition eC1IncompatibleMDSInFichaClinicaDataSet(List<Parameter> parameterList) {
    SqlDataSetDefinition dsd = new SqlDataSetDefinition();
    dsd.setName("EC1");
    dsd.addParameters(parameterList);
    dsd.setSqlQuery(
        EC1IncompatibleMDSInFichaClinicaQueries.QUERY
            .findPatiendsWithIncompatibleMDSInFichaClinica);

    return dsd;
  }

  public CohortDefinition getEC1Total() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();
    definition.setName("EC1");
    definition.addParameter(new Parameter("location", "Location", Location.class));
    definition.setQuery(EC1IncompatibleMDSInFichaClinicaQueries.QUERY.getEc1Total);

    return definition;
  }
}
