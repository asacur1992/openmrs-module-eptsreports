package org.openmrs.module.eptsreports.reporting.library.cohorts;

import static org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils.map;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.library.queries.KeyPopQueriesInterface;
import org.openmrs.module.eptsreports.reporting.library.queries.ResumoMensalQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyPopCohortQueries {

  @Autowired private GenericCohortQueries genericCohorts;
  @Autowired private ResumoMensalCohortQueries resumoMensalCohortQueries;
  @Autowired private GenericCohortQueries genericCohortQueries;
  @Autowired private HivMetadata hivMetadata;
  @Autowired private TxNewCohortQueries txNewCohortQueries;

  public CohortDefinition findPatientsWhoAreNewlyEnrolledOnArtKeyPop() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "START-ART",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "START-ART",
                KeyPopQueriesInterface.QUERY.findPatientsWhoAreNewlyEnrolledOnArtKeyPop),
            mappings));

    definition.addSearch(
        "TRANSFERED-IN",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "TRANSFERED-IN",
                KeyPopQueriesInterface.QUERY
                    .findPatientsWithAProgramStateMarkedAsTransferedInInAPeriod),
            mappings));

    definition.addSearch(
        "TRANSFERED-IN-AND-IN-ART-MASTER-CARD",
        EptsReportUtils.map(
            this.genericCohorts.generalSql(
                "TRANSFERED-IN-AND-IN-ART-MASTER-CARD",
                KeyPopQueriesInterface.QUERY
                    .findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCard),
            mappings));

    definition.setCompositionString(
        "START-ART NOT (TRANSFERED-IN OR TRANSFERED-IN-AND-IN-ART-MASTER-CARD)");

    return definition;
  }

  public CohortDefinition findPatientsWhoAreNewlyEnrolledOnArtKeyPopPreviousPeriodCoorte12Months() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings =
        "startDate=${startDate-12m},endDate=${endDate-12m},location=${location}";

    final String mappingsEndDate = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "B1",
        map(
            txNewCohortQueries.getTxNewCompositionCohortMISAU(
                "Number of patientes who initiated TARV"),
            mappings));

    definition.addSearch(
        "TRANSFERED-OUT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPatientsWhoWhereMarkedAsTransferedInAndOnARnOutAPeriodOnMasterCardStartDateB2",
                KeyPopQueriesInterface.QUERY.findPatientsWhoWhereMarkedAsTransferedOutAEndDateRF7),
            mappingsEndDate));

    definition.setCompositionString("B1 NOT (TRANSFERED-OUT)");

    return definition;
  }

  public CohortDefinition findPatientsWhoAreCurrentlyEnrolledOnArtPreviousPeriodCoorte12Months() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX CURR KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappingsEndDate =
        "startDate=${startDate-12m},endDate=${endDate-12m},location=${location}";
    final String mappings = "endDate=${endDate},location=${location}";

    definition.addSearch(
        "B1",
        map(
            txNewCohortQueries.getTxNewCompositionCohortMISAU(
                "Number of patientes who initiated TARV"),
            mappingsEndDate));

    definition.addSearch(
        "TX-CURR",
        EptsReportUtils.map(
            resumoMensalCohortQueries.findPatientsWhoAreCurrentlyEnrolledOnArtMOHB13(), mappings));

    definition.setCompositionString("(B1 AND TX-CURR)");

    return definition;
  }

  public CohortDefinition findPatientsWhoAreNewlyEnrolledOnArtKeyPop6MonthsCoorte() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings =
        "startDate=${startDate-7m},endDate=${startDate-4m},location=${location}";

    definition.addSearch(
        "B1",
        map(
            txNewCohortQueries.getTxNewCompositionCohortMISAU(
                "Number of patientes who initiated TARV"),
            mappings));

    definition.setCompositionString("B1");

    return definition;
  }

  public CohortDefinition findPatientsWhoActiveOnArtKeyPop6MonthsCoorte() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings =
        "startDate=${startDate-7m},endDate=${startDate-4m},location=${location}";

    definition.addSearch(
        "TX-CURR-6-MONTHS",
        EptsReportUtils.map(
            resumoMensalCohortQueries.findPatientsWhoAreCurrentlyEnrolledOnArtMOHB13(), mappings));

    definition.setCompositionString("TX-CURR-6-MONTHS");

    return definition;
  }

  public CohortDefinition findPatientsWhoActiveOnArtKeyPop6MonthsCoorteWithViralLoadResult() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    final String mappingsToVL = "startDate=${startDate-7m},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TX-CURR-COORTE-6-MONTHS",
        EptsReportUtils.map(this.findPatientsWhoActiveOnArtKeyPop6MonthsCoorte(), mappings));

    definition.addSearch(
        "VL",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "VL", ResumoMensalQueries.findPatientWithVlResult()),
            mappingsToVL));

    definition.addSearch(
        "Ex2",
        map(
            genericCohortQueries.generalSql("Ex2", ResumoMensalQueries.getE2ExclusionCriteria()),
            mappingsToVL));

    definition.setCompositionString("(TX-CURR-COORTE-6-MONTHS AND VL) NOT Ex2");

    return definition;
  }

  public CohortDefinition
      findPatientsWhoActiveOnArtKeyPop6MonthsCoorteWithViralLoadResultLessThan1000() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TX NEW KEY POP");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    final String mappingsToVL = "startDate=${startDate-7m},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TX-CURR-COORTE-6-MONTHS",
        EptsReportUtils.map(this.findPatientsWhoActiveOnArtKeyPop6MonthsCoorte(), mappings));

    definition.addSearch(
        "VL",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "VL", ResumoMensalQueries.findPatientWithVlResulLessThan1000()),
            mappingsToVL));

    definition.addSearch(
        "Ex3",
        map(
            genericCohortQueries.generalSql("Ex3", ResumoMensalQueries.getE3ExclusionCriteria()),
            mappingsToVL));

    definition.setCompositionString("(TX-CURR-COORTE-6-MONTHS AND VL) NOT Ex3");

    return definition;
  }

  public CohortDefinition findPatientsWhoAreCurrentlyEnrolledOnArtMOHWithRequestForVLE1() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("Tx Curr E1");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    definition.addSearch(
        "B13",
        EptsReportUtils.map(
            resumoMensalCohortQueries.findPatientsWhoAreCurrentlyEnrolledOnArtMOHB13(), mappings));

    definition.addSearch(
        "VL",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "VL", ResumoMensalQueries.findPatientWithVlResult()),
            mappings));

    definition.addSearch(
        "E1x",
        map(
            genericCohortQueries.generalSql(
                "E1x",
                ResumoMensalQueries.getE1ExclusionCriteria(
                    hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
                    hivMetadata.getApplicationForLaboratoryResearch().getConceptId(),
                    hivMetadata.getHivViralLoadConcept().getConceptId())),
            mappings));

    definition.setCompositionString("(B13 AND VL) NOT E1x");

    return definition;
  }
}
