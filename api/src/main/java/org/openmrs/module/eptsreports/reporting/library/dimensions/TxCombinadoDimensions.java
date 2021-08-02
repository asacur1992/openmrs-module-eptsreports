/** */
package org.openmrs.module.eptsreports.reporting.library.dimensions;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.TxCombinadoQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.TxCombinadoType;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author Stélio Moiane */
@Component
public class TxCombinadoDimensions {

  @Autowired private GenericCohortQueries genericCohortQueries;

  public CohortDefinitionDimension findPatientsWhoArePregnant(final TxCombinadoType combinadoType) {
    final CohortDefinitionDimension dimension = new CohortDefinitionDimension();

    dimension.setName("Pregnant");
    this.addParameters(dimension);

    final String mappings =
        "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

    dimension.addCohortDefinition(
        "pregnant",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "pregnant", TxCombinadoQueries.QUERY.findPatientsWhoPregnant(combinadoType))),
            mappings));

    return dimension;
  }

  public CohortDefinitionDimension findPatientsWhoAreBreastFeeding(
      final TxCombinadoType combinadoType) {
    final CohortDefinitionDimension dimension = new CohortDefinitionDimension();

    dimension.setName("BreastFeeding");
    this.addParameters(dimension);

    final String mappings =
        "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

    dimension.addCohortDefinition(
        "breastfeeding",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "breastfeeding",
                    TxCombinadoQueries.QUERY.findPatientsWhoAreBreastFeeding(combinadoType))),
            mappings));

    return dimension;
  }

  private void addParameters(final CohortDefinitionDimension dimension) {
    dimension.addParameter(new Parameter("startDate", "Start Date", Date.class));
    dimension.addParameter(new Parameter("endDate", "End Date", Date.class));
    dimension.addParameter(new Parameter("location", "location", Location.class));
    dimension.addParameter(new Parameter("months", "Months", Integer.class));
  }

  private CohortDefinition addParameters(final CohortDefinition definition) {
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.addParameter(new Parameter("months", "Months", Integer.class));

    return definition;
  }
}
