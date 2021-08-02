/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.eptsreports.reporting.library.datasets;

import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ABOVE_FIFTY;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIFTEEN_TO_NINETEEN;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FIVE_TO_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FORTY_FIVE_TO_FORTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.FORTY_TO_FORTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.ONE_TO_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TEN_TO_FOURTEEN;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.THIRTY_FIVE_TO_THIRTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.THIRTY_TO_THRITY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TWENTY_FIVE_TO_TWENTY_NINE;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.TWENTY_TO_TWENTY_FOUR;
import static org.openmrs.module.eptsreports.reporting.utils.AgeRange.UNDER_ONE;

import java.util.Date;
import org.openmrs.Location;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.dimensions.TxCombinadoDimensions;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.library.queries.ErimType;
import org.openmrs.module.eptsreports.reporting.library.queries.TxCombinadoQueries;
import org.openmrs.module.eptsreports.reporting.utils.AgeRange;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.Gender;
import org.openmrs.module.eptsreports.reporting.utils.TxCombinadoType;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TxCombinadoNumeratorDataset extends BaseDataSet {

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private EptsCommonDimension eptsCommonDimension;

  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private TxCombinadoDimensions txCombinadoDimensions;

  public DataSetDefinition constructTxCombinadoNumeratorDataset() {

    final String mappings =
        "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

    final CohortIndicatorDataSetDefinition definition = new CohortIndicatorDataSetDefinition();

    definition.setName("TX_COMBINADO NUMERATOR Data Set");
    definition.addParameters(this.getParameters());
    definition.addParameter(new Parameter("months", "Months", Integer.class));

    this.addDimensions(
        definition,
        "endDate=${endDate}",
        UNDER_ONE,
        ONE_TO_FOUR,
        FIVE_TO_NINE,
        TEN_TO_FOURTEEN,
        FIFTEEN_TO_NINETEEN,
        TWENTY_TO_TWENTY_FOUR,
        TWENTY_FIVE_TO_TWENTY_NINE,
        THIRTY_TO_THRITY_FOUR,
        THIRTY_FIVE_TO_THIRTY_NINE,
        FORTY_TO_FORTY_FOUR,
        FORTY_FIVE_TO_FORTY_NINE,
        ABOVE_FIFTY);

    definition.addDimension(
        this.getColumnName(AgeRange.UNKNOWN, Gender.MALE),
        EptsReportUtils.map(
            this.eptsCommonDimension.findPatientsWithUnknownAgeByGender(
                this.getColumnName(AgeRange.UNKNOWN, Gender.MALE), Gender.MALE),
            ""));

    definition.addDimension(
        this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE),
        EptsReportUtils.map(
            this.eptsCommonDimension.findPatientsWithUnknownAgeByGender(
                this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE), Gender.FEMALE),
            ""));

    definition.addDimension(
        "pregnant",
        EptsReportUtils.map(
            this.txCombinadoDimensions.findPatientsWhoArePregnant(TxCombinadoType.NUMERATOR),
            mappings));

    definition.addDimension(
        "breastfeeding",
        EptsReportUtils.map(
            this.txCombinadoDimensions.findPatientsWhoAreBreastFeeding(TxCombinadoType.NUMERATOR),
            mappings));

    final CohortIndicator numeratorIndicator =
        this.getIndicator(
            this.eptsGeneralIndicator.getIndicator(
                "Tx Combinado NUMERATOR",
                EptsReportUtils.map(this.numeratorCohortDefinition(), mappings)));

    definition.addColumn(
        "CB_NUM_ALL",
        "Tx Combinado numerator total",
        EptsReportUtils.map(numeratorIndicator, mappings),
        "");

    definition.addColumn(
        "CB_NUM_PREGNANT",
        "Pregnants",
        EptsReportUtils.map(numeratorIndicator, mappings),
        "pregnant=pregnant");

    definition.addColumn(
        "CB_NUM_BREASTFEEDING",
        "Breastfeeding",
        EptsReportUtils.map(numeratorIndicator, mappings),
        "breastfeeding=breastfeeding");

    definition.addColumn(
        "CB_NUM_DEAD",
        "Numerator Dead",
        EptsReportUtils.map(
            this.getIndicator(
                this.eptsGeneralIndicator.getIndicator(
                    "Dead",
                    EptsReportUtils.map(
                        this.patientsWhoWhereTransferredOutDeathOrSuspendedTreatment(ErimType.DEAD),
                        mappings))),
            mappings),
        "");

    definition.addColumn(
        "CB_NUM_TRANFERED_OUT",
        "Numerator Tranfered out",
        EptsReportUtils.map(
            this.getIndicator(
                this.eptsGeneralIndicator.getIndicator(
                    "Tranfered out",
                    EptsReportUtils.map(
                        this.patientsWhoWhereTransferredOutDeathOrSuspendedTreatment(
                            ErimType.TRANFERED_OUT),
                        mappings))),
            mappings),
        "");

    definition.addColumn(
        "CB_NUM_SPTOPPED_TREATMENT",
        "Numerator Stopped Treatment",
        EptsReportUtils.map(
            this.getIndicator(
                this.eptsGeneralIndicator.getIndicator(
                    "Stopped Treatment",
                    EptsReportUtils.map(
                        this.patientsWhoWhereTransferredOutDeathOrSuspendedTreatment(
                            ErimType.SPTOPPED_TREATMENT),
                        mappings))),
            mappings),
        "");

    this.addColums(
        definition,
        mappings,
        numeratorIndicator,
        UNDER_ONE,
        ONE_TO_FOUR,
        FIVE_TO_NINE,
        TEN_TO_FOURTEEN,
        FIFTEEN_TO_NINETEEN,
        TWENTY_TO_TWENTY_FOUR,
        TWENTY_FIVE_TO_TWENTY_NINE,
        THIRTY_TO_THRITY_FOUR,
        THIRTY_FIVE_TO_THIRTY_NINE,
        FORTY_TO_FORTY_FOUR,
        FORTY_FIVE_TO_FORTY_NINE,
        ABOVE_FIFTY);

    definition.addColumn(
        "NR-malesUnknownM",
        "unknownM",
        EptsReportUtils.map(numeratorIndicator, mappings),
        this.getColumnName(AgeRange.UNKNOWN, Gender.MALE)
            + "="
            + this.getColumnName(AgeRange.UNKNOWN, Gender.MALE));

    definition.addColumn(
        "NR-femalesUnknownF",
        "unknownF",
        EptsReportUtils.map(numeratorIndicator, mappings),
        this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE)
            + "="
            + this.getColumnName(AgeRange.UNKNOWN, Gender.FEMALE));

    return definition;
  }

  private CohortIndicator getIndicator(final CohortIndicator cohortIndicator) {
    cohortIndicator.addParameter(new Parameter("months", "Months", Integer.class));
    return cohortIndicator;
  }

  private CohortDefinition addParameters(final CohortDefinition cohortDefinition) {

    cohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
    cohortDefinition.addParameter(new Parameter("location", "location", Location.class));
    cohortDefinition.addParameter(new Parameter("months", "Months", Integer.class));

    return cohortDefinition;
  }

  public CohortDefinition numeratorCohortDefinition() {

    final CompositionCohortDefinition numeratorCompositionCohort =
        new CompositionCohortDefinition();
    numeratorCompositionCohort.setName("NUMERATOR TOTAL");
    this.addParameters(numeratorCompositionCohort);

    final String mappings =
        "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

    numeratorCompositionCohort.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "Patients with consultation or drug pickup - D",
                    TxCombinadoQueries.QUERY
                        .findPatientsWithConsultationOrPickUpDrugsInaSpecifiedPeriod(
                            TxCombinadoType.DENOMINATOR))),
            mappings));

    numeratorCompositionCohort.addSearch(
        "NUMERATOR",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "Patients with consultation or drug pickup - N",
                    TxCombinadoQueries.QUERY
                        .findPatientsWithConsultationOrPickUpDrugsInaSpecifiedPeriod(
                            TxCombinadoType.NUMERATOR))),
            mappings));

    numeratorCompositionCohort.setCompositionString("DENOMINATOR AND NUMERATOR");

    return numeratorCompositionCohort;
  }

  public CohortDefinition patientsWhoWhereTransferredOutDeathOrSuspendedTreatment(
      final ErimType erimType) {
    final CompositionCohortDefinition compositionCohort = new CompositionCohortDefinition();
    compositionCohort.setName("EXISTS");
    this.addParameters(compositionCohort);

    final String mappings =
        "startDate=${startDate},endDate=${endDate},location=${location},months=${months}";

    compositionCohort.addSearch(
        "DENOMINATOR",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "Patients with consultation or drug pickup - D",
                    TxCombinadoQueries.QUERY
                        .findPatientsWithConsultationOrPickUpDrugsInaSpecifiedPeriod(
                            TxCombinadoType.DENOMINATOR))),
            mappings));

    compositionCohort.addSearch(
        "EXIT-TYPE",
        EptsReportUtils.map(
            this.addParameters(
                this.genericCohortQueries.generalSql(
                    "Patients Who Where Transferred OutDeath Or SuspendedTreatment",
                    TxCombinadoQueries.QUERY
                        .findPatientsWhoWhereTransferredOutDeathOrSuspendedTreatmentInSpecificPeriodAndStage(
                            erimType))),
            "startDate=${endDate+1d},endDate=${startDate-1d},location=${location},months=${months}"));

    compositionCohort.setCompositionString("DENOMINATOR AND EXIT-TYPE");

    return compositionCohort;
  }

  private void addDimensions(
      final CohortIndicatorDataSetDefinition cohortIndicatorDataSetDefinition,
      final String mappings,
      final AgeRange... ranges) {

    for (final AgeRange range : ranges) {

      cohortIndicatorDataSetDefinition.addDimension(
          this.getColumnName(range, Gender.MALE),
          EptsReportUtils.map(
              this.eptsCommonDimension.findPatientsByGenderAndRange(
                  this.getColumnName(range, Gender.MALE), range, Gender.MALE),
              mappings));

      cohortIndicatorDataSetDefinition.addDimension(
          this.getColumnName(range, Gender.FEMALE),
          EptsReportUtils.map(
              this.eptsCommonDimension.findPatientsByGenderAndRange(
                  this.getColumnName(range, Gender.FEMALE), range, Gender.FEMALE),
              mappings));
    }
  }

  private String getColumnName(final AgeRange range, final Gender gender) {
    return range.getDesagregationColumnName("NR", gender);
  }

  public void setGenericCohortQueries(final GenericCohortQueries genericCohortQueries) {
    this.genericCohortQueries = genericCohortQueries;
  }

  private void addColums(
      final CohortIndicatorDataSetDefinition dataSetDefinition,
      final String mappings,
      final CohortIndicator cohortIndicator,
      final AgeRange... rannges) {

    for (final AgeRange range : rannges) {

      final String maleName = this.getColumnName(range, Gender.MALE);
      final String femaleName = this.getColumnName(range, Gender.FEMALE);

      dataSetDefinition.addColumn(
          maleName,
          maleName.replace("-", " "),
          EptsReportUtils.map(cohortIndicator, mappings),
          maleName + "=" + maleName);

      dataSetDefinition.addColumn(
          femaleName,
          femaleName.replace("-", " "),
          EptsReportUtils.map(cohortIndicator, mappings),
          femaleName + "=" + femaleName);
    }
  }
}
