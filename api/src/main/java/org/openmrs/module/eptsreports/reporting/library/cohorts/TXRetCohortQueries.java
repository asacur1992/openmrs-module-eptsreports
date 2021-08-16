package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Arrays;
import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.metadata.CommonMetadata;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.reporting.library.queries.TXRetQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.BaseObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TXRetCohortQueries {

  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private HivMetadata hivMetadata;

  @Autowired private CommonMetadata commonMetadata;

  @Autowired private GenderCohortQueries genderCohorts;

  @Autowired TxNewCohortQueries txNewCohortQueries;

  private String mappings =
      "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

  private void addParameters(CohortDefinition cd) {
    cd.addParameter(new Parameter("startDate", "Data Inicial", Date.class));
    cd.addParameter(new Parameter("endDate", "Data Final", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
    cd.addParameter(new Parameter("months", "Número de Meses (12, 24, 36)", Integer.class));
  }

  private CohortDefinition cohortDefinition(CohortDefinition cohortDefinition) {
    addParameters(cohortDefinition);
    return cohortDefinition;
  }

  private CohortDefinition obitoTwelveMonths() {
    return cohortDefinition(
        genericCohortQueries.generalSql("obito", TXRetQueries.obitoTwelveMonths()));
  }

  private CohortDefinition suspensoTwelveMonths() {
    return cohortDefinition(
        genericCohortQueries.generalSql("suspenso", TXRetQueries.suspensoTwelveMonths()));
  }

  private CohortDefinition initiotArvTwelveMonths() {
    return cohortDefinition(
        genericCohortQueries.generalSql("initiotArv", TXRetQueries.initiotArvTwelveMonths()));
  }

  private CohortDefinition abandonoTwelveMonths() {
    CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("abandono");
    cd.addSearch(
        "NOTIFICADO",
        EptsReportUtils.map(
            cohortDefinition(
                genericCohortQueries.generalSql(
                    "notificado", TXRetQueries.notificadoTwelveMonths())),
            mappings));
    cd.addSearch(
        "NAONOTIFICADO",
        EptsReportUtils.map(
            cohortDefinition(
                genericCohortQueries.generalSql(
                    "naonotificado", TXRetQueries.naonotificadoTwelveMonths())),
            mappings));
    cd.setCompositionString("NOTIFICADO OR NAONOTIFICADO");
    addParameters(cd);
    return cd;
  }

  /** numerator */
  public CohortDefinition inCourtForTwelveMonths() {
    CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("inCourt12Months");
    cd.addSearch("OBITO", EptsReportUtils.map(cohortDefinition(obitoTwelveMonths()), mappings));
    cd.addSearch(
        "SUSPENSO", EptsReportUtils.map(cohortDefinition(suspensoTwelveMonths()), mappings));
    cd.addSearch(
        "INICIOTARV", EptsReportUtils.map(cohortDefinition(initiotArvTwelveMonths()), mappings));
    cd.addSearch(
        "ABANDONO", EptsReportUtils.map(cohortDefinition(abandonoTwelveMonths()), mappings));

    cd.setCompositionString("INICIOTARV NOT (OBITO OR SUSPENSO OR ABANDONO)");
    addParameters(cd);
    return cd;
  }

  /** denominator */
  public CohortDefinition courtNotTransferredTwelveMonths() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "INICIO DE TRATAMENTO ARV - NUM PERIODO: EXCLUI TRANSFERIDOS PARA (SQL)",
            TXRetQueries.courtNotTransferredTwelveMonths()));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition under1YearIncreasedHARTAtARTStartDate() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "under1YearIncreasedHARTAtARTStartDate",
            TXRetQueries.under1YearIncreasedHARTAtARTStartDate()));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition oneTo19WhoStartedTargetAtARTInitiation() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "oneTo19WhoStartedTargetAtARTInitiation",
            TXRetQueries.oneTo19WhoStartedTargetAtARTInitiation()));
  }

  // ICAP
  /** map endDate, location rightly when using this */
  public CohortDefinition oneTo4WhoStartedTargetAtARTInitiation() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "oneTo4WhoStartedTargetAtARTInitiation",
            TXRetQueries.oneTo4WhoStartedTargetAtARTInitiation()));
  }

  // ICAP
  /** map endDate, location rightly when using this */
  public CohortDefinition fiveTo19WhoStartedTargetAtARTInitiation() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "fiveTo19WhoStartedTargetAtARTInitiation",
            TXRetQueries.fiveTo19WhoStartedTargetAtARTInitiation()));
  }

  /** map startDate, endDate, location rightly when using this */
  public CohortDefinition pregnancyEnrolledInART() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "GRAVIDAS INSCRITAS NO SERVIÇO TARV", TXRetQueries.pregnancyEnrolledInART()));
  }

  /**
   * breast feeding
   *
   * @return
   */
  public CohortDefinition possibleRegisteredClinicalProcedureAndFollowupForm() {
    CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setDescription(
        "LACTANTES OU PUERPUERAS (POS-PARTO) REGISTADAS: PROCESSO CLINICO E FICHA DE SEGUIMENTO");
    cd.addParameter(new Parameter("onOrAfter", "onOrAfter", Date.class));
    cd.addParameter(new Parameter("onOrBefore", "onOrBefore", Date.class));
    cd.addParameter(new Parameter("location", "location", Location.class));

    cd.addSearch(
        "DATAPARTO",
        EptsReportUtils.map(
            txNewCohortQueries.getPatientsWithUpdatedDepartureInART(),
            "value1=${onOrAfter},value2=${onOrBefore},locationList=${location}"));
    cd.addSearch(
        "INICIOLACTANTE",
        EptsReportUtils.map(
            genericCohortQueries.hasCodedObs(
                hivMetadata.getCriteriaForArtStart(),
                BaseObsCohortDefinition.TimeModifier.FIRST,
                SetComparator.IN,
                Arrays.asList(hivMetadata.getAdultoSeguimentoEncounterType()),
                Arrays.asList(commonMetadata.getBreastfeeding())),
            "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore},locationList=${location}"));
    cd.addSearch(
        "GRAVIDAS",
        EptsReportUtils.map(
            txNewCohortQueries.getPatientsPregnantEnrolledOnART(),
            "startDate=${onOrAfter},endDate=${onOrBefore},location=${location}"));
    cd.addSearch(
        "LACTANTEPROGRAMA",
        EptsReportUtils.map(
            infantsWhoGaveAwardsTwoYearsBehindReferenceDate(),
            "startDate=${onOrAfter},location=${location}"));
    cd.addSearch("FEMININO", EptsReportUtils.map(genderCohorts.femaleCohort(), ""));
    cd.addSearch(
        "LACTANTE",
        EptsReportUtils.map(
            genericCohortQueries.hasCodedObs(
                commonMetadata.getBreastfeeding(),
                BaseObsCohortDefinition.TimeModifier.LAST,
                SetComparator.IN,
                Arrays.asList(hivMetadata.getAdultoSeguimentoEncounterType()),
                Arrays.asList(commonMetadata.getYesConcept())),
            "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore},locationList=${location}"));

    String compositionString =
        "((DATAPARTO OR INICIOLACTANTE OR LACTANTEPROGRAMA  OR LACTANTE) NOT GRAVIDAS) AND FEMININO";
    cd.setCompositionString(compositionString);
    return cd;
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt10To14() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt10To14", TXRetQueries.genderOnArtXToY("M", 10, 14)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt10To14() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt10To14", TXRetQueries.genderOnArtXToY("F", 10, 14)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt15To19() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt15To19", TXRetQueries.genderOnArtXToY("M", 15, 19)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt15To19() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt15To19", TXRetQueries.genderOnArtXToY("F", 15, 19)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt20To24() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt20To24", TXRetQueries.genderOnArtXToY("M", 20, 24)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt20To24() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt20To24", TXRetQueries.genderOnArtXToY("F", 20, 24)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt25To29() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt25To29", TXRetQueries.genderOnArtXToY("M", 25, 29)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt25To29() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt25To29", TXRetQueries.genderOnArtXToY("F", 25, 29)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt30To34() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt30To34", TXRetQueries.genderOnArtXToY("M", 30, 34)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt30To34() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt30To34", TXRetQueries.genderOnArtXToY("F", 30, 34)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt35To39() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt35To39", TXRetQueries.genderOnArtXToY("M", 35, 39)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt35To39() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt35To39", TXRetQueries.genderOnArtXToY("F", 35, 39)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArt40To49() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt40To49", TXRetQueries.genderOnArtXToY("M", 40, 49)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt40To49() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt40To49", TXRetQueries.genderOnArtXToY("F", 40, 49)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArtAbove50() {
    return cohortDefinition(
        genericCohortQueries.generalSql("menOnArtAbove50", TXRetQueries.genderOnArtAbove50("M")));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArtAbove50() {
    return cohortDefinition(
        genericCohortQueries.generalSql("womenOnArtAbove50", TXRetQueries.genderOnArtAbove50("F")));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition infantsWhoGaveAwardsTwoYearsBehindReferenceDate() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "infantsWhoGaveAwardsTwoYearsBehindReferenceDate",
            TXRetQueries.infantsWhoGaveAwardsTwoYearsBehindReferenceDate()));
  }

  // ICAP

  /** map endDate, location rightly when using this */
  public CohortDefinition menOnArtUnder1() {
    return cohortDefinition(
        genericCohortQueries.generalSql("menOnArtUnder1", TXRetQueries.genderOnArtUnder1("M")));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArtUnder1() {
    return cohortDefinition(
        genericCohortQueries.generalSql("womenOnArtUnder1", TXRetQueries.genderOnArtUnder1("F")));
  }

  public CohortDefinition menOnArt1To4() {
    return cohortDefinition(
        genericCohortQueries.generalSql("menOnArt1To4", TXRetQueries.genderOnArtXToY("M", 1, 4)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt1To4() {
    return cohortDefinition(
        genericCohortQueries.generalSql("womenOnArt1To4", TXRetQueries.genderOnArtXToY("F", 1, 4)));
  }

  public CohortDefinition menOnArt5To9() {
    return cohortDefinition(
        genericCohortQueries.generalSql("menOnArt5To9", TXRetQueries.genderOnArtXToY("M", 5, 9)));
  }

  /** map endDate, location rightly when using this */
  public CohortDefinition womenOnArt5To9() {
    return cohortDefinition(
        genericCohortQueries.generalSql("womenOnArt5To9", TXRetQueries.genderOnArtXToY("F", 5, 9)));
  }

  public CohortDefinition menOnArt40To44() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt40To44", TXRetQueries.genderOnArtXToY("M", 40, 44)));
  }

  public CohortDefinition womenOnArt40To44() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt40To44", TXRetQueries.genderOnArtXToY("F", 40, 44)));
  }
  /*
  public CohortDefinition menOnArt45To49() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "menOnArt45To49", TXRetQueries.genderOnArtXToY("M", 45, 49)));
  }


  public CohortDefinition womenOnArt45To49() {
    return cohortDefinition(
        genericCohortQueries.generalSql(
            "womenOnArt45To49", TXRetQueries.genderOnArtXToY("F", 45, 49)));
  }

  */
}
