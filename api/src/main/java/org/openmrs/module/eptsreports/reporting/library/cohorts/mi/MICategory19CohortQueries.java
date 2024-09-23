package org.openmrs.module.eptsreports.reporting.library.cohorts.mi;

import java.util.Date;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCategory19CohortQueries;
import org.openmrs.module.eptsreports.reporting.library.cohorts.mq.MQCohortQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.ReportType;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MICategory19CohortQueries {
  @Autowired private MQCohortQueries mQCohortQueries;
  @Autowired private MQCategory19CohortQueries mqCategory19CohortQueries;

  @DocumentedDefinition(
      value = "findPatientsWithGeneXpertRequestExcludingTransferedOutMIDenominator")
  public CohortDefinition findPatientsWithGeneXpertRequestExcludingTransferedOutMIDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOut");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endRevisionDate=${endRevisionDate-2m},location=${location}";

    final String mappingsForTrfOut = "endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PRESUNTIVETB",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoarePresumptiveTB(), mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            this.mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI),
            mappingsForTrfOut));

    definition.setCompositionString("PRESUNTIVETB NOT TRANSFERED-OUT");

    return definition;
  }

  @DocumentedDefinition(value = "findPatientsWithGeneXpertRequestExcludingTransferedOutMINumerator")
  public CohortDefinition findPatientsWithGeneXpertRequestExcludingTransferedOutMINumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOutNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappingsDen =
        "startInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endRevisionDate=${endRevisionDate-2m},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsWithGeneXpertRequestExcludingTransferedOutMIDenominator(),
            mappingsDen));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest(),
            mappings));

    definition.setCompositionString("DENOMINATOR AND GENEXPERT");

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
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "PRESUNTIVETB",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoarePresumptiveTB(), mappings));

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

    final String mappingsDen =
        "startInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endInclusionDate=${endRevisionDate-2m},endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsWithGeneXpertRequestExcludingTransferedOutDenominator(), mappingsDen));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest(),
            mappings));

    definition.setCompositionString("DENOMINATOR AND GENEXPERT");

    return definition;
  }

  @DocumentedDefinition(
      value = "findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestMIDenominator")
  public CohortDefinition
      findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestMIDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findPatientsWithGeneXpertRequestExcludingTransferedOut");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endRevisionDate=${endRevisionDate-2m},location=${location}";

    definition.addSearch(
        "GENEXPERTREQUEST",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoHaveGeneXpertRequest(), mappings));
    definition.addSearch(
        "GENEXPERTRESULT",
        EptsReportUtils.map(
            mqCategory19CohortQueries
                .findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequest(),
            mappings));
    definition.addSearch(
        "TROUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI), mappings));

    definition.setCompositionString("(GENEXPERTREQUEST AND GENEXPERTRESULT) NOT TROUT");

    return definition;
  }

  @DocumentedDefinition(
      value = "findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestMINumerator")
  public CohortDefinition
      findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestMINumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequestNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappingsDen =
        "startInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappings =
        "startInclusionDate=${endRevisionDate-3m+1d},endRevisionDate=${endRevisionDate-2m},location=${location}";
    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findPatientsWithGeneXpertRequestExcludingTransferedOutMIDenominator(),
            mappingsDen));

    definition.addSearch(
        "GENEXPERTRESULT",
        EptsReportUtils.map(
            mqCategory19CohortQueries
                .findAllPatientsWithGeneXpertResultAfterSevenDaysPresuntiveResult(),
            mappings));

    definition.setCompositionString("DENOMINATOR AND GENEXPERTRESULT");

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveTBDiagnosticActiveMIDenominator")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActiveMIDenominator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWhoHaveTBDiagnosticActiveDenominator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endRevisionDate=${endRevisionDate-1m},location=${location}";

    final String mappingsForTrfOut = "endRevisionDate=${endRevisionDate},location=${location}";

    definition.addSearch(
        "TBDIAGNOSTIC",
        EptsReportUtils.map(
            mqCategory19CohortQueries.findAllPatientsWhoHaveTBDiagnosticActive(), mappings));

    definition.addSearch(
        "TROUT",
        EptsReportUtils.map(
            mQCohortQueries.findPatientsWhoTransferedOutRF07Category7(ReportType.MI),
            mappingsForTrfOut));

    definition.setCompositionString("TBDIAGNOSTIC NOT TROUT");

    return definition;
  }

  @DocumentedDefinition(value = "findAllPatientsWhoHaveTBDiagnosticActiveMINumerator")
  public CohortDefinition findAllPatientsWhoHaveTBDiagnosticActiveMINumerator() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("findAllPatientsWhoHaveTBDiagnosticActiveNumerator");
    definition.addParameter(
        new Parameter("startInclusionDate", "Data Inicio Inclusão", Date.class));
    definition.addParameter(new Parameter("endInclusionDate", "Data Fim Inclusão", Date.class));
    definition.addParameter(new Parameter("endRevisionDate", "Data Fim Revisão", Date.class));
    definition.addParameter(new Parameter("location", "location", Date.class));

    final String mappingsDen =
        "startInclusionDate=${endRevisionDate},endRevisionDate=${endRevisionDate},location=${location}";
    final String mappings =
        "startInclusionDate=${endRevisionDate-2m+1d},endRevisionDate=${endRevisionDate-1m},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.findAllPatientsWhoHaveTBDiagnosticActiveMIDenominator(), mappingsDen));

    definition.addSearch(
        "TB",
        EptsReportUtils.map(
            mqCategory19CohortQueries
                .findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment(),
            mappings));

    definition.setCompositionString("DENOMINATOR AND TB");

    return definition;
  }
}
