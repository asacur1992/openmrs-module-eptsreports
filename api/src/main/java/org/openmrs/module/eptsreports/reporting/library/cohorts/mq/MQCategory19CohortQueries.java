package org.openmrs.module.eptsreports.reporting.library.cohorts.mq;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.queries.mq.MQCategory19QueriesInterface;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.ReportType;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQCategory19CohortQueries {

  @Autowired private MQCohortQueries mQCohortQueries;

  @DocumentedDefinition(value = "findAllPatientsWhoarePresumptiveTB")
  public CohortDefinition findAllPatientsWhoarePresumptiveTB() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoarePresumptiveTB");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query = MQCategory19QueriesInterface.QUERY.findAllPatientsWhoarePresumptiveTB;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveGeneXpertRequest")
  public CohortDefinition findAllPatientsWhoHaveGeneXpertRequest() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHaveGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query = MQCategory19QueriesInterface.QUERY.findAllPatientsWhoHaveGeneXpertRequest;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveTBDiagnosticActive")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActive() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHaveTBDiagnosticActive");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query = MQCategory19QueriesInterface.QUERY.findAllPatientsWhoHaveTBDiagnosticActive;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest")
  public CohortDefinition findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY.findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWithGeneXpertResultAfterGeneXpertRequest")
  public CohortDefinition findAllPatientsWithGeneXpertResultAfterGeneXpertRequest() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY.findAllPatientsWithGeneXpertResultAfterGeneXpertRequest;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "v")
  public CohortDefinition findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequest() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY
            .findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequest;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWithGeneXpertResultAfterSevenDaysPresuntiveResult")
  public CohortDefinition findAllPatientsWithGeneXpertResultAfterSevenDaysPresuntiveResult() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY
            .findAllPatientsWithGeneXpertResultAfterSevenDaysPresuntiveResult;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWithGeneXpertResultAfterSevenDaysGeneXpertRequest")
  public CohortDefinition findAllPatientsWithGeneXpertResultAfterSevenDaysGeneXpertRequest() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY
            .findAllPatientsWithGeneXpertResultAfterSevenDaysGeneXpertRequest;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(
      value = "findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment() {

    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest");
    definition.addParameter(new Parameter("startInclusionDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "End Date", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "End Revision Date", Date.class));
    definition.addParameter(new Parameter("location", "Location", Location.class));

    String query =
        MQCategory19QueriesInterface.QUERY
            .findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment;

    definition.setQuery(query);

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWithGeneXpertRequestExcludingTransferedOutDenominator")
  public CohortDefinition findPatientsWithGeneXpertRequestExcludingTransferedOutDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOut");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PRESUNTIVETB", EptsReportUtils.map(this.findAllPatientsWhoarePresumptiveTB(), mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MQ),
            mappings));

    definition.setCompositionString("PRESUNTIVETB NOT TRANSFERED-OUT");

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWithGeneXpertRequestExcludingTransferedOutDenominator")
  public CohortDefinition findPatientsWithGeneXpertRequestExcludingTransferedOutNumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOutNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsWithGeneXpertRequestExcludingTransferedOutDenominator(), mappings));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            this.findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest(), mappings));

    definition.setCompositionString("DENOMINATOR AND GENEXPERT");

    return definition;
  }

  @DocumentedDefinition(
      value = "findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestDenominator")
  public CohortDefinition
      findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOut");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "GENEXPERTREQUEST",
        EptsReportUtils.map(this.findAllPatientsWhoHaveGeneXpertRequest(), mappings));
    definition.addSearch(
        "GENEXPERTRESULT",
        EptsReportUtils.map(
            this.findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequest(), mappings));
    definition.addSearch(
        "TROUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MQ), mappings));

    definition.setCompositionString("(GENEXPERTREQUEST AND GENEXPERTRESULT) NOT TROUT");

    return definition;
  }

  public CohortDefinition
      findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestNumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestDenominator(),
            mappings));

    definition.addSearch(
        "GENEXPERTRESULT",
        EptsReportUtils.map(
            this.findAllPatientsWithGeneXpertResultAfterSevenDaysGeneXpertRequest(), mappings));

    definition.setCompositionString("DENOMINATOR AND GENEXPERTRESULT");

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveTBDiagnosticActiveDenominator")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActiveDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWhoHaveTBDiagnosticActiveDenominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "TBDIAGNOSTIC",
        EptsReportUtils.map(this.findAllPatientsWhoHaveTBDiagnosticActive(), mappings));

    definition.addSearch(
        "TROUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MQ), mappings));

    definition.setCompositionString("TBDIAGNOSTIC NOT TROUT");

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveTBDiagnosticActiveNumerator")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActiveNumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWhoHaveTBDiagnosticActiveNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${startInclusionDate},endInclusionDate=${endInclusionDate},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(this.findAllPatientsWhoHaveTBDiagnosticActiveDenominator(), mappings));

    definition.addSearch(
        "TB",
        EptsReportUtils.map(
            findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment(), mappings));

    definition.setCompositionString("DENOMINATOR AND TB");

    return definition;
  }
}
