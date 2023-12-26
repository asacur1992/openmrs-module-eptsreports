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
import org.openmrs.module.eptsreports.reporting.library.queries.TXTBMontlyCascadeReportQueries;
import org.openmrs.module.eptsreports.reporting.library.queries.TXTBMontlyCascadeReportQueries.QUERY.DiagnosticTestTypes;
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
public class TXTBDenominatorForTBMontlyCascadeQueries {

  @Autowired private TbMetadata tbMetadata;

  @Autowired private HivMetadata hivMetadata;

  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private TXTBCohortQueries txtbCohortQueries;

  @Autowired private TxCurrCohortQueries txCurrCohortQueries;

  private String generalParameterMapping =
      "startDate=${endDate-6m+1d},endDate=${endDate},location=${location}";

  @DocumentedDefinition(value = "TxTBDenominatorPositiveScreening")
  public CohortDefinition getTxTBDenominatorAndPositiveScreening() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();

    definition.setName("TxTB - Denominator Positive Screening");
    definition.addSearch(
        "denominator",
        EptsReportUtils.map(
            this.txtbCohortQueries.getDenominator(Boolean.FALSE), this.generalParameterMapping));

    definition.addSearch(
        "positive-screening",
        EptsReportUtils.map(this.getTxTBPPositiveScreening(), this.generalParameterMapping));
    this.addGeneralParameters(definition);
    definition.setCompositionString("denominator AND positive-screening");
    return definition;
  }

  @DocumentedDefinition(value = "TxTBDenominatorNegativeScreening")
  public CohortDefinition getTxTBDenominatorAndNegativeScreening() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB - Denominator  Negative Screening");

    definition.addSearch(
        "denominator",
        EptsReportUtils.map(
            this.txtbCohortQueries.getDenominator(Boolean.FALSE), this.generalParameterMapping));

    definition.addSearch(
        "positive-screening",
        EptsReportUtils.map(this.getTxTBPPositiveScreening(), this.generalParameterMapping));

    definition.addSearch(
        "new-on-art", EptsReportUtils.map(this.getNewOnArt(), this.generalParameterMapping));

    this.addGeneralParameters(definition);
    definition.setCompositionString("denominator NOT positive-screening");
    return definition;
  }

  @DocumentedDefinition(value = "get Specimen Sent")
  public CohortDefinition getSpecimenSentCohortDefinition() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    this.addGeneralParameters(definition);
    definition.setName("TxTB -specimen-sent");

    final CohortDefinition applicationForLaboratoryResearchDataset =
        this.genericCohortQueries.generalSql(
            "applicationForLaboratoryResearch",
            TXTBQueries.dateObsForEncounterAndQuestionAndAnswers(
                this.hivMetadata.getAdultoSeguimentoEncounterType().getEncounterTypeId(),
                Arrays.asList(
                    this.hivMetadata.getApplicationForLaboratoryResearch().getConceptId()),
                Arrays.asList(
                    this.tbMetadata.getTbGenexpertTest().getConceptId(),
                    this.tbMetadata.getCultureTest().getConceptId(),
                    this.tbMetadata.getTbLam().getConceptId(),
                    this.tbMetadata.getSputumForAcidFastBacilli().getConceptId())));

    this.addGeneralParameters(applicationForLaboratoryResearchDataset);

    definition.addSearch(
        "application-for-laboratory-research",
        EptsReportUtils.map(applicationForLaboratoryResearchDataset, this.generalParameterMapping));
    definition.addSearch(
        "tb-genexpert-culture-lam-bk-test",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbGenExpertORCultureTestOrTbLamOrBk(),
            this.generalParameterMapping));
    definition.addSearch(
        "lab-results",
        EptsReportUtils.map(this.getResultsOnFichaLaboratorio(), this.generalParameterMapping));

    definition.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.txtbCohortQueries.getDenominator(Boolean.FALSE), this.generalParameterMapping));

    definition.setCompositionString(
        "(application-for-laboratory-research OR tb-genexpert-culture-lam-bk-test OR lab-results) AND DENOMINATOR");

    return definition;
  }

  @DocumentedDefinition(value = "getTxTBPPositiveScreening")
  private CohortDefinition getTxTBPPositiveScreening() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();

    cd.setName("TxTB - positiveScreening");

    cd.addSearch(
        "A",
        EptsReportUtils.map(
            this.txtbCohortQueries.codedYesTbScreening(),
            "onOrAfter=${endDate-6m+1d},onOrBefore=${endDate},locationList=${location}"));

    cd.addSearch(
        "B",
        EptsReportUtils.map(
            this.txtbCohortQueries.positiveInvestigationResultComposition(),
            this.generalParameterMapping));
    cd.addSearch(
        "C",
        EptsReportUtils.map(
            this.txtbCohortQueries
                .negativeInvestigationResultAndAnyResultForTBScreeningComposition(),
            this.generalParameterMapping));
    cd.addSearch(
        "D",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbDrugTreatmentStartDateWithinReportingDate(),
            this.generalParameterMapping));
    cd.addSearch(
        "E",
        EptsReportUtils.map(this.txtbCohortQueries.getInTBProgram(), this.generalParameterMapping));
    cd.addSearch(
        "F",
        EptsReportUtils.map(
            this.txtbCohortQueries.getPulmonaryTBWithinReportingDate(),
            this.generalParameterMapping));
    cd.addSearch(
        "G",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTuberculosisTreatmentPlanWithinReportingDate(),
            this.generalParameterMapping));
    cd.addSearch(
        "H",
        EptsReportUtils.map(
            this.txtbCohortQueries.getAllTBSymptomsForDisaggregationComposition(),
            this.generalParameterMapping));
    cd.addSearch(
        "I",
        EptsReportUtils.map(
            this.txtbCohortQueries.getSputumForAcidFastBacilliWithinReportingDate(),
            this.generalParameterMapping));

    cd.setCompositionString("A OR B OR C OR D OR E OR F OR G OR H OR I");
    this.addGeneralParameters(cd);
    return cd;
  }

  private CohortDefinition getNumeratorPreviosPeriod() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("TxTB - txTbNumerator Previous Period");

    final CohortDefinition i =
        this.genericCohortQueries.generalSql(
            "onTbTreatment-Previous Reporting Period",
            TXTBQueries.dateObsForPreviousReportingPeriod(
                this.tbMetadata.getTBDrugTreatmentStartDate().getConceptId(),
                Arrays.asList(
                    this.hivMetadata.getAdultoSeguimentoEncounterType().getId(),
                    this.hivMetadata.getARVPediatriaSeguimentoEncounterType().getId()),
                true));

    final CohortDefinition ii = this.txtbCohortQueries.getInTBProgramPreviousPeriod();

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
            this.txtbCohortQueries.getTuberculosisTreatmentPlanWithinPreviousReportingDate(),
            this.generalParameterMapping));

    cd.addSearch(
        "started-tb-treatment-previous-period",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbDrugTreatmentStartDateWithinReportingDate(),
            "startDate=${endDate-18m},endDate=${endDate-12m-1d},location=${location}"));

    cd.setCompositionString("(i OR ii OR iii OR iv) NOT started-tb-treatment-previous-period");
    this.addGeneralParameters(cd);
    return cd;
  }

  @DocumentedDefinition(value = "get All TB Symptoms for Denominator")
  private CohortDefinition getAllTBSymptomsForDemoninatorComposition() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    this.addGeneralParameters(definition);
    definition.setName("TxTB - All TB Symptoms for Denominator");

    definition.addSearch(
        "tuberculosis-symptoms",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTuberculosisSymptoms(
                this.hivMetadata.getYesConcept().getConceptId(),
                this.hivMetadata.getNoConcept().getConceptId()),
            this.generalParameterMapping));

    definition.addSearch(
        "active-tuberculosis",
        EptsReportUtils.map(
            this.txtbCohortQueries.getActiveTuberculosis(), this.generalParameterMapping));

    definition.addSearch(
        "tb-observations",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbObservations(), this.generalParameterMapping));

    definition.addSearch(
        "application-for-laboratory-research",
        EptsReportUtils.map(
            this.txtbCohortQueries.getApplicationForLaboratoryResearch(),
            this.generalParameterMapping));

    definition.addSearch(
        "tb-genexpert-or-culture-test-or-lam-or-bk-test",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbGenExpertORCultureTestOrTbLamOrBk(),
            this.generalParameterMapping));

    definition.addSearch(
        "tb-raioxtorax",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbRaioXTorax(), this.generalParameterMapping));

    definition.setCompositionString(
        "tuberculosis-symptoms OR active-tuberculosis OR tb-observations OR application-for-laboratory-research OR tb-genexpert-or-culture-test-or-lam-or-bk-test OR tb-raioxtorax");

    return definition;
  }

  @DocumentedDefinition(value = "get Diagnóstico Laboratorial para TB")
  private CohortDefinition getResultsOnFichaLaboratorio() {

    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    this.addGeneralParameters(definition);
    definition.setName("TxTB -Diagnóstico Laboratorial para TB");

    definition.addSearch(
        "sputum-for-acid-fast-bacilli",
        EptsReportUtils.map(
            this.txtbCohortQueries.getSputumForAcidFastBacilliWithinReportingDate(),
            this.generalParameterMapping));

    definition.addSearch(
        "genexpert-culture",
        EptsReportUtils.map(
            this.txtbCohortQueries.getGenExpertOrCulturaOnFichaLaboratorio(),
            this.generalParameterMapping));

    definition.addSearch(
        "tblam",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbLamOnFichaLaboratorio(), this.generalParameterMapping));

    definition.addSearch(
        "xpert-mtb",
        EptsReportUtils.map(
            this.txtbCohortQueries.getXpertMTBOnFichaLaboratorio(), this.generalParameterMapping));

    definition.setCompositionString(
        "sputum-for-acid-fast-bacilli OR genexpert-culture OR tblam OR xpert-mtb");

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

  @DocumentedDefinition(value = "GenExpertTests")
  public CohortDefinition getGenExpertTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("GeneXpert MTB/RIF");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "genexperts",
        EptsReportUtils.map(
            this.findDiagnostiTests(DiagnosticTestTypes.GENEXPERT), this.generalParameterMapping));
    definition.setCompositionString("genexperts");
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
        "genexpertsNegativeTestResults OR baciloscopiaNegativeTestResults OR tblamNegativeTestResults OR otherAditionalNegativeTestResults ");
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

    definition.setCompositionString("negativeGenexperts");
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
            this.findDiagnostiTests(DiagnosticTestTypes.BACILOSCOPIA),
            this.generalParameterMapping));

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
        "baciloscopia",
        EptsReportUtils.map(
            this.findDiagnosticPositiveTestResults(DiagnosticTestTypes.BACILOSCOPIA),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.setCompositionString("baciloscopia NOT genexperts");
    return definition;
  }

  @DocumentedDefinition(value = "getBaciloscopiaNegativeTestResults")
  public CohortDefinition getBaciloscopiaNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Smear microscopy only Negative Test Results");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "negativeBaciloscopia",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.BACILOSCOPIA),
            this.generalParameterMapping));

    definition.addSearch(
        "negativeGenexpert",
        EptsReportUtils.map(this.getGenExpertNegativeTestResults(), this.generalParameterMapping));

    definition.setCompositionString("negativeBaciloscopia NOT negativeGenexpert");
    return definition;
  }

  @DocumentedDefinition(value = "TBLAM")
  public CohortDefinition getTBLAMTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "tblam",
        EptsReportUtils.map(
            this.findDiagnostiTests(DiagnosticTestTypes.TBLAM), this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));

    definition.setCompositionString("tblam NOT (genexperts OR baciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMPositiveTestResults")
  public CohortDefinition getTBLAMPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Positive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "tblam",
        EptsReportUtils.map(
            this.findDiagnosticPositiveTestResults(DiagnosticTestTypes.TBLAM),
            this.generalParameterMapping));

    definition.addSearch(
        "genexperts", EptsReportUtils.map(this.getGenExpertTests(), this.generalParameterMapping));
    definition.addSearch(
        "baciloscopia",
        EptsReportUtils.map(this.getBaciloscopiaTests(), this.generalParameterMapping));

    definition.setCompositionString("tblam NOT (genexperts OR baciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "getTBLAMNegativeTestResults")
  public CohortDefinition getTBLAMNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Negative Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "negativeTblam",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.TBLAM),
            this.generalParameterMapping));

    definition.addSearch(
        "negativeGenexpert",
        EptsReportUtils.map(this.getGenExpertNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "negativeBaciloscopia",
        EptsReportUtils.map(
            this.getBaciloscopiaNegativeTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "negativeTblam NOT (negativeGenexpert OR negativeBaciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "AdditionalTests")
  public CohortDefinition getAdditionalTests() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Additional test other than GeneXpert");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "additionalTests",
        EptsReportUtils.map(
            this.findDiagnostiTests(DiagnosticTestTypes.CULTURA), this.generalParameterMapping));

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

  @DocumentedDefinition(value = "getAdditionalPositiveTestResults")
  public CohortDefinition getAdditionalPositiveTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("Additional test other than GeneXpert Positive Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "additionalTests",
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

    definition.setCompositionString("additionalTests NOT (genexperts OR baciloscopia OR tblam)");
    return definition;
  }

  @DocumentedDefinition(value = "getAdditionalNegativeTestResults")
  public CohortDefinition getAdditionalNegativeTestResults() {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TB LAM Negative Test Results");
    this.addGeneralParameters(definition);
    definition.addSearch(
        "negativeAdditionalTests",
        EptsReportUtils.map(
            this.findDiagnosticNegativeTestResults(DiagnosticTestTypes.CULTURA),
            this.generalParameterMapping));

    definition.addSearch(
        "negativeTBLAM",
        EptsReportUtils.map(this.getTBLAMNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "negativeGenexpert",
        EptsReportUtils.map(this.getGenExpertNegativeTestResults(), this.generalParameterMapping));

    definition.addSearch(
        "negativeBaciloscopia",
        EptsReportUtils.map(
            this.getBaciloscopiaNegativeTestResults(), this.generalParameterMapping));

    definition.setCompositionString(
        "negativeAdditionalTests NOT (negativeTBLAM OR negativeGenexpert OR negativeBaciloscopia)");
    return definition;
  }

  @DocumentedDefinition(value = "txTbNumerator")
  public CohortDefinition txTbNumerator() {
    final CompositionCohortDefinition cd = new CompositionCohortDefinition();
    cd.setName("TxTB - txTbNumerator");
    final CohortDefinition A = this.txTbNumeratorA();
    cd.addSearch("A", EptsReportUtils.map(A, this.generalParameterMapping));

    cd.addSearch(
        "started-tb-treatment-previous-period",
        EptsReportUtils.map(
            this.txtbCohortQueries.getTbDrugTreatmentStartDateWithinReportingDate(),
            "startDate=${endDate-12m+1d},endDate=${endDate-6m+1d},location=${location}"));

    cd.addSearch(
        "A-PREVIOUS-PERIOD",
        EptsReportUtils.map(this.getNumeratorPreviosPeriod(), this.generalParameterMapping));

    cd.addSearch(
        "art-started-by-end-previous-reporting-period",
        EptsReportUtils.map(
            this.genericCohortQueries.getStartedArtBeforeDate(false),
            "onOrBefore=${endDate-6m-1d},location=${location}"));

    cd.setCompositionString(
        "A NOT (started-tb-treatment-previous-period OR (A-PREVIOUS-PERIOD AND art-started-by-end-previous-reporting-period ))");

    this.addGeneralParameters(cd);
    return cd;
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

  @DocumentedDefinition(value = "findDiagnostiTests")
  private CohortDefinition findDiagnostiTests(final DiagnosticTestTypes diagnosticTestType) {
    final SqlCohortDefinition definition = new SqlCohortDefinition();

    definition.setName("findDiagnostiTests");
    definition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    definition.addParameter(new Parameter("endDate", "End Date", Date.class));
    definition.addParameter(new Parameter("location", "location", Location.class));
    definition.setQuery(
        TXTBMontlyCascadeReportQueries.QUERY.findDiagnosticTests(diagnosticTestType));

    return definition;
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
        TXTBMontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithPositiveTestResults(
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
        TXTBMontlyCascadeReportQueries.QUERY.findDiagnosticTestsWithNegativeTestResults(
            diagnosticTestType));

    return definition;
  }

  @DocumentedDefinition(value = "PatientsWithPositiveResultWhoStartedTBTreatment")
  public CohortDefinition getPositiveResultAndTXTBNumerator(final Boolean isCommunity) {
    final CompositionCohortDefinition definition = new CompositionCohortDefinition();
    definition.setName("TxTB -Denominator Positive Results Who Started TB Treatment");
    this.addGeneralParameters(definition);

    definition.addSearch(
        "posetiveResults",
        EptsReportUtils.map(
            this.txtbCohortQueries.getPositiveResultsForTXTBMontlyCascadeCohortDefinition(
                this.txtbCohortQueries.getDenominator(isCommunity), this.generalParameterMapping),
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
        EptsReportUtils.map(
            this.txtbCohortQueries.getDenominator(Boolean.FALSE), this.generalParameterMapping));

    cd.addSearch(
        "all-negative-test-results",
        EptsReportUtils.map(this.getAllNegativeTestResults(), this.generalParameterMapping));

    cd.setCompositionString("DENOMINATOR AND all-negative-test-results ");

    return cd;
  }

  private void addGeneralParameters(final CohortDefinition cd) {
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.addParameter(new Parameter("location", "Location", Location.class));
  }
}
