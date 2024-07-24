/*
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.cohorts;

import java.util.Arrays;
import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.metadata.TbMetadata;
import org.openmrs.module.eptsreports.reporting.library.queries.TB4MontlyCascadeReportQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.TB4MontlyCascadeReportQueries.QUERY.DiagnosticTestTypes;
import org.openmrs.module.eptsreports.reporting.library.queries.TB4MontlyCascadeReportQueries.QUERY.EnrollmentPeriod;
import org.openmrs.module.eptsreports.reporting.library.queries.TXTBQueries;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TB4MontlyCascadeCohortQueries {

  @Autowired private TbMetadata tbMetadata;

  @Autowired private HivMetadata hivMetadata;

  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private TXTBCohortQueries txtbCohortQueries;

  @Autowired private TxCurrCohortQueries txCurrCohortQueries;

  private String generalParameterMapping =
      "startDate=${endDate-6m+1d},endDate=${endDate},location=${location}";

  @DocumentedDefinition(value = "TxTBDenominatorNegativeScreening")
  public CohortDefinition getTxTBDenominatorAndNegativeScreening() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB - Denominator  Negative Screening");

    definition.addSearch(
        "denominator",
        EptsReportUtils.map(this.txtbCohortQueries.getDenominator(), this.generalParameterMapping));

    definition.addSearch(
        "positive-screening",
        EptsReportUtils.map(
            this.txtbCohortQueries.findPatientWhoAreTBPositiveScreening(),
            this.generalParameterMapping));

    definition.addSearch(
        "new-on-art", EptsReportUtils.map(this.getNewOnArt(), this.generalParameterMapping));

    this.addGeneralParameters(definition);
    definition.setCompositionString("denominator NOT positive-screening");
    return definition;
  }

  @DocumentedDefinition(value = "get New on Art")
  public CohortDefinition getNewOnArt() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB - New on ART");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "started-on-period",
        EptsReportUtils.map(
            this.genericCohortQueries.getStartedArtOnPeriod(false, true),
            "onOrAfter=${endDate-6m+1d},onOrBefore=${endDate},location=${location}"));
    definition.setCompositionString("started-on-period");
    return definition;
  }

  @DocumentedDefinition(value = "getGenExpertPositiveTestResults")
  public CohortDefinition getGenExpertPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("GeneXpert MTB/RIF Posetive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "genexperts",
        EptsReportUtils.map(
            this.findDiagnosticPositiveTestResults(DiagnosticTestTypes.GENEXPERT),
            this.generalParameterMapping));
    definition.setCompositionString("genexperts");
    return definition;
  }

  @DocumentedDefinition(value = "allNegativeTestResults")
  public CohortDefinition getAllNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Posetive Test Results");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "genexpertsNegativeTestResults",
        EptsReportUtils.map(this.getGenExpertNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "baciloscopiaNegativeTestResults",
        EptsReportUtils.map(
            this.getBaciloscopiaNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "tblamNegativeTestResults",
        EptsReportUtils.map(this.getTBLAMNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "otherAditionalNegativeTestResults",
        EptsReportUtils.map(this.getAdditionalNegativeTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "genexpertsNegativeTestResults OR baciloscopiaNegativeTestResults OR tblamNegativeTestResults OR otherAditionalNegativeTestResults");
    return definition;
  }

  @DocumentedDefinition(value = "getGenExpertNegativeTestResults")
  public CohortDefinition getGenExpertNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("GeneXpert MTB/RIF Negative Test Results");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "negativeGenexperts",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.GENEXPERT),
            this.generalParameterMapping));

    definition.addSearch(
        "genexpertsPositiveTestResults",
        EptsReportUtils.map(this.getGenExpertPositiveTestResults(), this.generalParameterMapping));

    definition.setCompositionString("negativeGenexperts NOT genexpertsPositiveTestResults");
    return definition;
  }

  @DocumentedDefinition(value = "GenExpertTests")
  public CohortDefinition getGenExpertTests() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("GeneXpert MTB/RIF");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(TXTBQueries.findPatientWhoAreDiagnosticTestMWRD());

    return definition;
  }

  @DocumentedDefinition(value = "BaciloscopiaTests")
  public CohortDefinition getBaciloscopiaTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Smear microscopy only");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(
            this.getDiagnosticTestSmearMicroscopyOnly(), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));

    definition.setCompositionString("baciloscopia NOT genexperts");
    return definition;
  }

  @DocumentedDefinition(value = "getBaciloscopiaPositiveTestResults")
  public CohortDefinition getBaciloscopiaPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Smear microscopy only Positive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "baciloscopia-positive-test-results",
        EptsReportUtils.map(
            this.findDiagnosticPositiveTestResults(DiagnosticTestTypes.BACILOSCOPIA),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts-tests",
        EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));

    definition.setCompositionString("baciloscopia-positive-test-results NOT genexperts-tests");
    return definition;
  }

  @DocumentedDefinition(value = "getBaciloscopiaNegativeTestResults")
  public CohortDefinition getBaciloscopiaNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Smear microscopy only Negative Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "baciloscopia-negative-test-results",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.BACILOSCOPIA),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts-tests",
        EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));

    definition.addSearch(
        "baciloscopia-positive-test-results",
        EptsReportUtils.map(
            this.getBaciloscopiaPositiveTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "baciloscopia-negative-test-results NOT (genexperts-tests OR baciloscopia-positive-test-results)");
    return definition;
  }

  @DocumentedDefinition(value = "TBLAM")
  public CohortDefinition getTBLAMTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "tblam", EptsReportUtils.map(this.getTBLAMTest(), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));

    definition.setCompositionString("tblam NOT (genexperts OR baciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "allPositiveTestResults")
  public CohortDefinition getAllPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Posetive Test Results");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "genexpertsPositiveTestResults",
        EptsReportUtils.map(this.getGenExpertPositiveTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "baciloscopiaPositiveTestResults",
        EptsReportUtils.map(
            this.getBaciloscopiaPositiveTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "tblamPositiveTestResults",
        EptsReportUtils.map(this.getTBLAMPositiveTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "otherAditionalPositiveTestResults",
        EptsReportUtils.map(this.getAdditionalPositiveTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "genexpertsPositiveTestResults OR baciloscopiaPositiveTestResults OR tblamPositiveTestResults OR otherAditionalPositiveTestResults ");
    return definition;
  }

  @DocumentedDefinition(value = "getDenominatorAndPositiveOrNegativeResults")
  public CohortDefinition getDenominatorAndPositiveOrNegativeCohort(
      final CohortDefinition positiveOrNegativeCohort, final String mapping) {

    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    this.addGeneralParameters(cd);
    cd.setName("TxTB - Positive Results");

    cd.addSearch(
        "DENOMINATOR", EptsReportUtils.map(this.txtbCohortQueries.getDenominator(), mapping));

    cd.addSearch("POS-NEG-COHORT", EptsReportUtils.map(positiveOrNegativeCohort, mapping));

    cd.setCompositionString("DENOMINATOR AND POS-NEG-COHORT");

    return cd;
  }

  @DocumentedDefinition(value = "getDiagnosticTestSmearMicroscopyOnly")
  private CohortDefinition getDiagnosticTestSmearMicroscopyOnly() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("Smear microscopy only");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(TXTBQueries.findPatientWhoAreDiagnosticTestSmearMicroscopyOnly());

    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMTest")
  private CohortDefinition getTBLAMTest() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("TB LAM Tests");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(TXTBQueries.findPatientWhoHaveTBLAMDiagnosticTest());

    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMPositiveTestResults")
  public CohortDefinition getTBLAMPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Positive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "tblam",
        EptsReportUtils.map(this.getTBLAMPositiveTestsResults(), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));

    definition.setCompositionString("tblam NOT (genexperts OR baciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMPositiveTestsResults")
  private CohortDefinition getTBLAMPositiveTestsResults() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("B LAM Positive Test Results");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(
        TB4MontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithPositiveTestResultsTBLAM());

    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMNegativeTestResults")
  public CohortDefinition getTBLAMNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Negative Test Results");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "negative-tblam",
        EptsReportUtils.map(this.getTBLAMNegativeTestsResults(), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));

    definition.addSearch(
        "tblam-positive-results",
        EptsReportUtils.map(this.getTBLAMPositiveTestsResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "negative-tblam NOT (genexperts OR baciloscopia OR tblam-positive-results)");
    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMNegativeTestsResults")
  private CohortDefinition getTBLAMNegativeTestsResults() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("B LAM Negative Test Results");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(
        TB4MontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithNegativeTestResultsTBLAM());

    return definition;
  }

  @DocumentedDefinition(value = "AdditionalTests")
  public CohortDefinition getAdditionalTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Additional test other than mWRD");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "additionalTests",
        EptsReportUtils.map(this.getAdditionalTestsCulture(), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));
    definition.addSearch(
        "tblam", EptsReportUtils.map(this.getTBLAMTests(), this.generalParameterMapping));

    definition.setCompositionString("additionalTests NOT (genexperts OR baciloscopia OR tblam)");
    return definition;
  }

  @DocumentedDefinition(value = "getAdditionalTestsCulture")
  private CohortDefinition getAdditionalTestsCulture() {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("Additional test other than mWRD");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(TXTBQueries.findPatientWhoAreDiagnosticTestOtherCulture());

    return definition;
  }

  @DocumentedDefinition(value = "getAdditionalPositiveTestResults")
  public CohortDefinition getAdditionalPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Additional test other than GeneXpert Positive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "additional-tests",
        EptsReportUtils.map(
            this.findDiagnosticPositiveTestResults(DiagnosticTestTypes.CULTURA),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));
    definition.addSearch(
        "tblam", EptsReportUtils.map(this.getTBLAMTests(), this.generalParameterMapping));

    definition.setCompositionString("additional-tests NOT (genexperts OR baciloscopia OR tblam)");
    return definition;
  }

  @DocumentedDefinition(value = "getAdditionalNegativeTestResults")
  public CohortDefinition getAdditionalNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Negative Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "negative-additional-tests",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.CULTURA),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));
    definition.addSearch(
        "tblam", EptsReportUtils.map(this.getTBLAMTests(), this.generalParameterMapping));

    definition.addSearch(
        "posetive-additional-tests",
        EptsReportUtils.map(this.getAdditionalPositiveTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "negative-additional-tests NOT (genexperts OR baciloscopia OR tblam OR posetive-additional-tests)");
    return definition;
  }

  @DocumentedDefinition(value = "txTbNumeratorA")
  private CohortDefinition txTbNumeratorA() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("TxTB - txTbNumeratorA");
    final CohortDefinition i =
        this.genericCohortQueries.generalSql(
            "onTbTreatment",
            TXTBQueries.dateObs(
                this.tbMetadata.getTBDrugTreatmentStartDate().getConceptId(),
                Arrays.asList(
                    this.hivMetadata.getAdultoSeguimentoEncounterType().getId(),
                    this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getId()),
                true));
    final CohortDefinition ii = this.txtbCohortQueries.getInTBProgram();

    this.addGeneralParameters(i);

    cd.addSearch("i", EptsReportUtils.map(i, this.generalParameterMapping));
    cd.addSearch("ii", EptsReportUtils.map(ii, this.generalParameterMapping));
    cd.addSearch(
        "iii",
        EptsReportUtils.map(
            this.txtbCohortQueries.getPulmonaryTBWithinReportingDate(),
            this.generalParameterMapping));
    cd.addSearch(
        "iv",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTuberculosisTreatmentPlanWithinReportingDate(),
            this.generalParameterMapping));

    final CohortDefinition artList = this.txtbCohortQueries.artList();
    cd.addSearch("artList", EptsReportUtils.map(artList, this.generalParameterMapping));
    cd.setCompositionString("(i OR ii OR iii OR iv) AND artList");
    this.addGeneralParameters(cd);
    return cd;
  }

  @DocumentedDefinition(value = "findDiagnosticPositiveTestResults")
  private CohortDefinition findDiagnosticPositiveTestResults(
      final DiagnosticTestTypes diagnosticTestType) {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findDiagnosticPositiveTestResults");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(
        TB4MontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithPositiveTestResults(
            diagnosticTestType));

    return definition;
  }

  @DocumentedDefinition(value = "findDiagnosticNegativeTestResults")
  private CohortDefinition findDiagnosticNegativeTestResults(
      final DiagnosticTestTypes diagnosticTestType) {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findDiagnosticNegativeTestResults");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(
        TB4MontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithNegativeTestResults(
            diagnosticTestType));

    return definition;
  }

  @DocumentedDefinition(value = "PatientsWithPositiveResultWhoStartedTBTreatment")
  public CohortDefinition getPositiveResultAndTXTBNumerator() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB -Denominator Positive Results Who Started TB Treatment");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "posetiveResults",
        EptsReportUtils.map(
            this.getDenominatorAndPositiveOrNegativeCohort(
                this.getAllPositiveTestResults(), this.generalParameterMapping),
            this.generalParameterMapping));

    definition.addSearch(
        "txtbNumerator",
        EptsReportUtils.map(this.txtbCohortQueries.txTbNumerator(), this.generalParameterMapping));

    definition.setCompositionString("posetiveResults AND txtbNumerator");
    return definition;
  }

  @DocumentedDefinition(value = "ScreenedPatientsWhoStartedTBTreatmentAndTXCurr")
  public CohortDefinition getScreenedPatientsWhoStartedTBTreatmentAndTXCurr() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB - Screened Patients Who Started TB Treatment and Are TX_CURR");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "txTB",
        EptsReportUtils.map(this.txtbCohortQueries.txTbNumerator(), this.generalParameterMapping));
    definition.addSearch(
        "txCurr",
        EptsReportUtils.map(
            this.txCurrCohortQueries.findPatientsWhoAreActiveOnART(),
            "endDate=${endDate},location=${location}"));

    definition.setCompositionString("txTB AND txCurr");
    return definition;
  }

  @DocumentedDefinition(value = "get Negative Results")
  public CohortDefinition getNegativeResultCohortDefinition() {

    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    this.addGeneralParameters(cd);
    cd.setName("TxTB -Denominator Negative Results");

    cd.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(this.txtbCohortQueries.getDenominator(), this.generalParameterMapping));

    cd.addSearch(
        "all-negative-test-results",
        EptsReportUtils.map(this.getAllNegativeTestResults(), this.generalParameterMapping));

    cd.setCompositionString("DENOMINATOR AND all-negative-test-results ");

    return cd;
  }

  @DocumentedDefinition(value = "TBDenominatorAndTxCurr")
  public CohortDefinition getTxBTDenominatorAndTxCurr() {
    final CompositionCohortDefinition composiiton = new CompositionCohortDefinition();

    composiiton.setName("TX_TB denominator in the last 6 months and TX_CURR ");
    composiiton.addParameter(new Parameter("endDate", "End Date", Date.class));
    composiiton.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate},location=${location}";

    composiiton.addSearch(
        "TX-CURR",
        EptsReportUtils.map(this.txCurrCohortQueries.findPatientsWhoAreActiveOnART(), mappings));

    composiiton.addSearch(
        "TX-TB-DENOMINATOR",
        EptsReportUtils.map(
            this.txtbCohortQueries.getDenominator(),
            "startDate=${endDate-6m+1d},endDate=${endDate},location=${location}"));

    composiiton.setCompositionString("TX-CURR and TX-TB-DENOMINATOR ");

    return composiiton;
  }

  @DocumentedDefinition(value = "patientsWhoAreCurrentyEnrolledOnArtInTheLastSixMonths")
  public CohortDefinition getPatientsEnrollendOnARTForTheLastSixMonths() {
    final CompositionCohortDefinition composiiton = new CompositionCohortDefinition();

    composiiton.setName("Patient On ART in the last 6 Months ");
    composiiton.addParameter(new Parameter("endDate", "End Date", Date.class));
    composiiton.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate},location=${location}";

    composiiton.addSearch(
        "newlyOnArt",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "PatientsWithClinicalConsultationIntheLastSixMonths",
                TB4MontlyCascadeReportQueries.QUERY
                    .findPatientsWhoAreCurrentlyEnrolledOnARTByPeriod(EnrollmentPeriod.NEWLY)),
            mappings));

    composiiton.setCompositionString("newlyOnArt");
    return composiiton;
  }

  @DocumentedDefinition(value = "patientsWhoAreCurrentlyEnrolledOnARTInForMoreThanSixMonths")
  public CohortDefinition getPatientsEnrolledOnArtForMoreThanSixMonths() {
    final CompositionCohortDefinition composiiton = new CompositionCohortDefinition();

    composiiton.setName("Patient On ART For More Than 6 Months");
    composiiton.addParameter(new Parameter("endDate", "End Date", Date.class));
    composiiton.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate},location=${location}";

    composiiton.addSearch(
        "previouslyOnArt",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "patientsWhoAreCurrentlyEnrolledOnARTInForMoreThanSixMonths",
                TB4MontlyCascadeReportQueries.QUERY
                    .findPatientsWhoAreCurrentlyEnrolledOnARTByPeriod(EnrollmentPeriod.PREVIOUSLY)),
            mappings));

    composiiton.addSearch(
        "newlyOnArt",
        EptsReportUtils.map(this.getPatientsEnrollendOnARTForTheLastSixMonths(), mappings));

    composiiton.setCompositionString("previouslyOnArt NOT newlyOnArt");

    return composiiton;
  }

  @DocumentedDefinition(value = "patientsWithClinicalConsultationsInTheLastSixMonths")
  public CohortDefinition getClinicalConsultationsInLastSixMonths() {
    final CompositionCohortDefinition composiiton = new CompositionCohortDefinition();

    composiiton.setName("Patients with Clinical Consultations in the last 6 months");
    composiiton.addParameter(new Parameter("endDate", "End Date", Date.class));
    composiiton.addParameter(new Parameter("location", "location", Location.class));

    final String mappings = "endDate=${endDate},location=${location}";

    composiiton.addSearch(
        "consultationsLast6Months",
        EptsReportUtils.map(
            this.genericCohortQueries.generalSql(
                "PatientsWithClinicalConsultationIntheLastSixMonths",
                TB4MontlyCascadeReportQueries.QUERY
                    .findPatientsWithClinicalConsultationIntheLastSixMonths),
            mappings));

    composiiton.setCompositionString("consultationsLast6Months");

    return composiiton;
  }

  private void addGeneralParameters(final CohortDefinition cd) {
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
  }
}
