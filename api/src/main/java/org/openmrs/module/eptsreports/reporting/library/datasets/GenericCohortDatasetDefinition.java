/** */
package org.openmrs.module.eptsreports.reporting.library.datasets;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;

/** @author Stélio Moiane */
public interface GenericCohortDatasetDefinition<T> {

  CohortDefinition getCohortDefinition();

  T indicatorType(IndicatorType indicatorType);
}
