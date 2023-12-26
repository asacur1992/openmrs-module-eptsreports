package org.openmrs.module.eptsreports.reporting.library.cohorts;

import static org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils.map;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.queries.TxNewQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsQuerysUtils;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.LevelOfPositivity;
import org.openmrs.module.eptsreports.reporting.utils.TypePTV;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TB7AdvancedDiseaseAndTBCohortQueries {

  private static final String PREGNANT_OR_BREASTEFEEDING = "TB7/PREGNANT_OR_BREASTFEEDING.sql";
  private static final String HIGH_VIRAL_LOAD = "TB7/HIGH_VIRAL_LOAD.sql";
  private static final String REINITIATED_ART = "TB7/REINITIATED_ART.sql";
  private static final String CD4_RESULT = "TB7/CD4_RESULT.sql";
  private static final String CD4_RESULT_2 = "TB7/CD4_RESULT_2.sql";
  private static final String CD4_RESULT_3 = "TB7/CD4_RESULT_3.sql";
  private static final String TB_LAM_1 = "TB7/TB_LAM_1.sql";
  private static final String TR_OUT_CURR_DATE = "TB7/TR_OUT_CURR_DATE.sql";
  private static final String DEAD_CURR_DATE = "TB7/DEAD_CURR_DATE.sql";
  private static final String CD4_CURR_DATE = "TB7/CD4_CURR_DATE.sql";
  private static final String TB_LAM_CURR_DATE = "TB7/TB_LAM_CURR_DATE.sql";
  private static final String TB_LAM_POSITIVE_OR_NEGATIVE = "TB7/TB_LAM_POSITIVE_OR_NEGATIVE.sql";
  private static final String TB_LAM_LEVEL_OF_POSITIVITY = "TB7/TB_LAM_LEVEL_OF_POSITIVITY.sql";
  private static final String GENEXPERT = "TB7/GENEXPERT_CURR_DATE.sql";
  private static final String GENEXPERT_POSITIVE = "TB7/GENEXPERT_POSITIVE.sql";
  private static final String TB = "TB7/TB_CUUR_DATE.sql";

  private static int TB_LAM_POSITIVE = 703;
  private static int TB_LAM_NEGATIVE = 664;

  @Autowired private GenericCohortQueries genericCohortQueries;

  public static String findPregnantOrBreatfeeding(TypePTV typePTV) {
    String query = EptsQuerysUtils.loadQuery(PREGNANT_OR_BREASTEFEEDING);

    switch (typePTV) {
      case PREGNANT:
        query = query + "where final.decisao = 1 ";
        break;

      case BREASTFEEDING:
        query = query + "where final.decisao = 2 ";
        break;
    }
    return query;
  }

  public static String findLevelOfPosotivity(LevelOfPositivity levelOfPositivity) {
    String query = EptsQuerysUtils.loadQuery(TB_LAM_LEVEL_OF_POSITIVITY);

    switch (levelOfPositivity) {
      case Grade_1:
        query = query + "where f.value_coded = 165186 ";
        break;

      case Grade_2:
        query = query + "where f.value_coded = 165187 ";
        break;

      case Grade_3:
        query = query + "where f.value_coded = 165188 ";
        break;
      case Grade_4:
        query = query + "where f.value_coded = 165348 ";
        break;

      case Blank:
        query = query + "where f.value_coded is null ";
        break;
    }
    return query;
  }

  public static String findPatientWithHighViralLoad() {
    String query = EptsQuerysUtils.loadQuery(HIGH_VIRAL_LOAD);
    return query;
  }

  public static String findPatientWhoReinitiatedART() {
    String query = EptsQuerysUtils.loadQuery(REINITIATED_ART);
    return query;
  }

  public static String findPatientWhoHaveCd4Result() {
    String query = EptsQuerysUtils.loadQuery(CD4_RESULT);
    return query;
  }

  public static String findPatientWhoHaveCd4Result2() {
    String query = EptsQuerysUtils.loadQuery(CD4_RESULT_2);
    return query;
  }

  public static String findPatientWhoHaveCd4Result3() {
    String query = EptsQuerysUtils.loadQuery(CD4_RESULT_3);
    return query;
  }

  public static String findPatientWhoHaveTbLamResul1() {
    String query = EptsQuerysUtils.loadQuery(TB_LAM_1);
    return query;
  }

  public static String findPatientWhoHaveTbLamResulUntilCurrentDate() {
    String query = EptsQuerysUtils.loadQuery(TB_LAM_CURR_DATE);
    return query;
  }

  public static String findPatientWhoAreTransferedOut() {
    String query = EptsQuerysUtils.loadQuery(TR_OUT_CURR_DATE);
    return query;
  }

  public static String findPatientWhoAreDead() {
    String query = EptsQuerysUtils.loadQuery(DEAD_CURR_DATE);
    return query;
  }

  public static String findPatientWhoHaveCd4UntilCurrentDate() {
    String query = EptsQuerysUtils.loadQuery(CD4_CURR_DATE);
    return query;
  }

  public static String findPatientWhoHaveTBLamPositiveOrNegative() {
    String query = EptsQuerysUtils.loadQuery(TB_LAM_POSITIVE_OR_NEGATIVE);
    return query;
  }

  public static String findPatientWhoHaveTBLamLevelOfPositivity() {
    String query = EptsQuerysUtils.loadQuery(TB_LAM_LEVEL_OF_POSITIVITY);
    return query;
  }

  public static String findPatientWhoHaveGenexpert() {
    String query = EptsQuerysUtils.loadQuery(GENEXPERT);
    return query;
  }

  public static String findPatientWhoHavePositiveGenexpert() {
    String query = EptsQuerysUtils.loadQuery(GENEXPERT_POSITIVE);
    return query;
  }

  public static String findPatientWhoHavePositiveGenexpertTb() {
    String query = EptsQuerysUtils.loadQuery(TB);
    return query;
  }

  public CohortDefinition
      getNumberOfClientsEligibleForCD4CountRequestDuringInclusionPeriodIndicator1() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    definition.addSearch(
        "TXNEW",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "findPatientsWhoAreNewlyEnrolledOnART",
                TxNewQueries.QUERY.findPatientsWhoAreNewlyEnrolledOnART),
            mappings));

    definition.addSearch(
        "PREGNANT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "PREGNANT", findPregnantOrBreatfeeding(TypePTV.PREGNANT)),
            mappings));

    definition.addSearch(
        "HIGHVIRALLOAD",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("HIGHVIRALLOAD", findPatientWithHighViralLoad()),
            mappings));

    definition.addSearch(
        "REINITIATEDART",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("REINITIATEDART", findPatientWhoReinitiatedART()),
            mappings));

    definition.addSearch(
        "TROUT" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("TROUT", findPatientWhoAreTransferedOut()),
            mappings));

    definition.addSearch(
        "DEAD" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("DEAD", findPatientWhoAreDead()), mappings));

    definition.setCompositionString(
        "(TXNEW OR PREGNANT OR HIGHVIRALLOAD OR REINITIATEDART) NOT (TROUT OR DEAD)");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator2() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE",
        map(
            this.getNumberOfClientsEligibleForCD4CountRequestDuringInclusionPeriodIndicator1(),
            mappingsDen));

    definition.addSearch(
        "CD4RESULT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("CD4RESULT", findPatientWhoHaveCd4Result()),
            mappings));

    definition.setCompositionString("ELEGIBLE AND CD4RESULT");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE2",
        map(this.getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator2(), mappingsDen));

    definition.addSearch(
        "CD4RESULT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("CD4RESULT", findPatientWhoHaveCd4Result2()),
            mappings));

    definition.setCompositionString("ELEGIBLE2 AND CD4RESULT");
    return definition;
  }

  public CohortDefinition
      getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithTBLam() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE3",
        map(this.getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3(), mappingsDen));

    definition.addSearch(
        "TBLAM",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "TBLAM", findPatientWhoHaveTbLamResulUntilCurrentDate()),
            mappings));

    definition.setCompositionString("ELEGIBLE3 AND TBLAM");
    return definition;
  }

  public CohortDefinition
      getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE3",
        map(this.getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator3(), mappingsDen));

    definition.addSearch(
        "TBLAM",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "TBLAM", findPatientWhoHaveTbLamResulUntilCurrentDate()),
            mappings));

    definition.setCompositionString("ELEGIBLE3 NOT TBLAM");
    return definition;
  }

  public CohortDefinition getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE2",
        map(this.getNumberOfClientsWithCd4ResultDuringInclusionPeriodIndicator2(), mappingsDen));

    definition.addSearch(
        "CD4RESULT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("CD4RESULT", findPatientWhoHaveCd4Result3()),
            mappings));

    definition.setCompositionString("ELEGIBLE2 AND CD4RESULT");
    return definition;
  }

  public CohortDefinition
      getumberOfClientsWithCD4CountDuringInclusionPeriodWithoutSevereImmunodepression() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "WITHTBLAM",
        map(
            this.getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithTBLam(),
            mappings));

    definition.addSearch(
        "WITHOUTTBLAM",
        map(
            this.getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam(),
            mappings));

    definition.setCompositionString("WITHTBLAM OR WITHOUTTBLAM");
    return definition;
  }

  public CohortDefinition
      getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithTBLam() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE3",
        map(this.getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3(), mappingsDen));

    definition.addSearch(
        "TBLAM",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "TBLAM", findPatientWhoHaveTbLamResulUntilCurrentDate()),
            mappings));

    definition.setCompositionString("ELEGIBLE3 AND TBLAM");
    return definition;
  }

  public CohortDefinition
      getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3WithoutTBLam() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "ELEGIBLE3",
        map(this.getNumberOfClientsWithoutCd4ResultDuringInclusionPeriodIndicator3(), mappingsDen));

    definition.addSearch(
        "TBLAM",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "TBLAM", findPatientWhoHaveTbLamResulUntilCurrentDate()),
            mappings));

    definition.setCompositionString("ELEGIBLE3 NOT TBLAM");
    return definition;
  }

  public CohortDefinition
      getNumberOfClientsWithoutCD4CountButWithTBLAMResultsDuringTheInclusionPeriodIncludingClientsWhoWereNotEligibleForCD4() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "startDate=${endDate-2m+1d},endDate=${endDate-1m},location=${location}";

    definition.addSearch(
        "TBLAM" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("TBLAM", findPatientWhoHaveTbLamResul1()),
            mappings));

    definition.addSearch(
        "CD4" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("CD4", findPatientWhoHaveCd4Result()), mappings));

    definition.addSearch(
        "TROUT" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("TROUT", findPatientWhoAreTransferedOut()),
            mappings));

    definition.addSearch(
        "DEAD" + "",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("DEAD", findPatientWhoAreDead()), mappings));

    definition.setCompositionString("TBLAM NOT(CD4 OR TROUT OR DEAD)");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithTBLAMResultsByReportGenerationDate() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";

    definition.addSearch(
        "TBLAM",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "TBLAM", findPatientWhoHaveTbLamResulUntilCurrentDate()),
            mappings));

    definition.addSearch(
        "TROUT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("TROUT", findPatientWhoAreTransferedOut()),
            mappings));

    definition.addSearch(
        "DEAD",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("DEAD", findPatientWhoAreDead()), mappings));

    definition.setCompositionString("TBLAM NOT (TROUT OR DEAD)");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAM", map(this.getNumberOfClientsWithTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "POSITIVE",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "POSITIVE",
                String.format(findPatientWhoHaveTBLamPositiveOrNegative(), TB_LAM_POSITIVE)),
            mappings));

    definition.setCompositionString("TBLAM AND POSITIVE");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithNegativeTBLAMResultsByReportGenerationDate() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAM", map(this.getNumberOfClientsWithTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "NEGATIVE",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "NEGATIVE",
                String.format(findPatientWhoHaveTBLamPositiveOrNegative(), TB_LAM_NEGATIVE)),
            mappings));

    definition.setCompositionString("TBLAM AND NEGATIVE");

    return definition;
  }

  public CohortDefinition getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(
      LevelOfPositivity levelOfPositivity) {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "GRADE",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("GRADE", findLevelOfPosotivity(levelOfPositivity)),
            mappings));

    definition.setCompositionString("TBLAMPOSITIVE AND GRADE");

    return definition;
  }

  public CohortDefinition getClientsWithoutPositiveTBLAMButNotTestedWithGeneXpert() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("GENEXPERT", findPatientWhoHaveGenexpert()),
            mappings));

    definition.setCompositionString("TBLAMPOSITIVE NOT GENEXPERT");

    return definition;
  }

  public CohortDefinition getClientsWithPositiveTBLAMAndAlsoTestedWithGeneXpert() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("GENEXPERT", findPatientWhoHaveGenexpert()),
            mappings));

    definition.setCompositionString("TBLAMPOSITIVE AND GENEXPERT");

    return definition;
  }

  public CohortDefinition getClientsWithPositiveTBLAMButNotTestedWithGeneXpert() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getNumberOfClientsWithPositiveTBLAMResultsByReportGenerationDate(), mappingsDen));

    definition.addSearch(
        "GENEXPERT",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("GENEXPERT", findPatientWhoHaveGenexpert()),
            mappings));

    definition.setCompositionString("TBLAMPOSITIVE NOT GENEXPERT");

    return definition;
  }

  public CohortDefinition getClientsWithPositiveTBLAMAndGeneXpertPositiveforTB() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getClientsWithPositiveTBLAMAndAlsoTestedWithGeneXpert(), mappingsDen));

    definition.addSearch(
        "GENEXPERTPOSITIVE",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "GENEXPERTPOSITIVE", findPatientWhoHavePositiveGenexpert()),
            mappings));

    definition.setCompositionString("TBLAMPOSITIVE AND GENEXPERTPOSITIVE");

    return definition;
  }

  public CohortDefinition getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTB() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "TBLAMPOSITIVE",
        map(this.getClientsWithPositiveTBLAMAndAlsoTestedWithGeneXpert(), mappingsDen));

    definition.addSearch(
        "TBLAMNOTGENEXPERT",
        map(this.getClientsWithPositiveTBLAMButNotTestedWithGeneXpert(), mappingsDen));

    definition.addSearch(
        "TB",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("TB", findPatientWhoHavePositiveGenexpertTb()),
            mappings));

    definition.setCompositionString("(TBLAMPOSITIVE OR TBLAMNOTGENEXPERT) AND TB");

    return definition;
  }

  public CohortDefinition
      getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTBGradeDesagregation(
          LevelOfPositivity levelOfPositivity) {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB7");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate-2m+1d},location=${location}";
    final String mappingsDen = "startDate=${startDate},endDate=${endDate},location=${location}";

    definition.addSearch(
        "DENOMINATOR",
        map(this.getClientsWithPositiveTBLAMButNotTestedWithGeneXpertForTB(), mappingsDen));

    definition.addSearch(
        "GRADE",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql("GRADE", findLevelOfPosotivity(levelOfPositivity)),
            mappings));

    definition.setCompositionString("DENOMINATOR AND GRADE");

    return definition;
  }
}
