package org.openmrs.module.eptsreports.reporting.library.dimensions;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.PMTCTEIDQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.PMTCTHEIQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PMTCTAgeDimensions {

  @Autowired private GenericCohortQueries genericCohortQueries;

  public CohortDefinitionDimension getPMTCTEIDAgeDimensions() {
    final CohortDefinitionDimension dimension = new CohortDefinitionDimension();
    dimension.setName("pmtctage");
    dimension.addParameter(new Parameter("startDate", "Start Date", Date.class));
    dimension.addParameter(new Parameter("endDate", "End Date", Date.class));
    dimension.addParameter(new Parameter("location", "location", Location.class));

    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    dimension.addCohortDefinition(
        "less2months",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPMTCTpatientsByAgeRange",
                String.format(
                    PMTCTEIDQueries.QUERY.getPMTCTPatienstByAge, "day", " between 0 and 59 ")),
            mappings));

    dimension.addCohortDefinition(
        "betweent2and12months",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPMTCTpatientsByAgeRange",
                String.format(
                    PMTCTEIDQueries.QUERY.getPMTCTPatienstByAge, "day", " between 60 and 365 ")),
            mappings));

    return dimension;
  }

  public CohortDefinitionDimension getPMTCTHEIAgeDimensions() {
    final CohortDefinitionDimension dimension = new CohortDefinitionDimension();
    dimension.setName("pmtctage-hei");
    dimension.addParameter(new Parameter("startDate", "Start Date", Date.class));
    dimension.addParameter(new Parameter("endDate", "End Date", Date.class));
    dimension.addParameter(new Parameter("location", "location", Location.class));

    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    dimension.addCohortDefinition(
        "less2months",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPMTCTpatientsByAgeRange",
                String.format(
                    PMTCTHEIQueries.QUERY.getPMTCTPatienstByAge, "day", " between 0 and 59 ")),
            mappings));

    dimension.addCohortDefinition(
        "betweent2and12months",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPMTCTpatientsByAgeRange",
                String.format(
                    PMTCTHEIQueries.QUERY.getPMTCTPatienstByAge, "day", " between 60 and 365 ")),
            mappings));

    return dimension;
  }
}
