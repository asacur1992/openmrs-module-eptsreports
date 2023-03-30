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
package org.openmrs.module.eptsreports.reporting.library.datasets;

import java.util.Arrays;
import java.util.List;
import org.openmrs.module.eptsreports.reporting.library.cohorts.PvlsCommunityCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.dimensions.AgeDimensionCohortInterface;
import org.openmrs.module.eptsreports.reporting.library.dimensions.EptsCommonDimension;
import org.openmrs.module.eptsreports.reporting.library.dimensions.KeyPopulationDimension;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TxPvlsCommunityDataset extends BaseDataSet {

  @Autowired private EptsCommonDimension eptsCommonDimension;

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private PvlsCommunityCohortQueries pvlsCommunityCohortQueries;

  @Autowired private KeyPopulationDimension keyPopulationDimension;

  @Autowired
  @Qualifier("commonAgeDimensionCohort")
  private AgeDimensionCohortInterface ageDimensionCohort;

  public DataSetDefinition constructTxPvlsDatset() {

    final CohortIndicatorDataSetDefinition dataSetDefinition =
        new CohortIndicatorDataSetDefinition();

    final String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";

    dataSetDefinition.setName("TX_PVLS Community Data Set");

    dataSetDefinition.addParameters(this.getParameters());

    dataSetDefinition.addDimension(
        "gender", EptsReportUtils.map(this.eptsCommonDimension.gender(), ""));

    dataSetDefinition.addDimension(
        "age",
        EptsReportUtils.map(
            this.eptsCommonDimension.age(this.ageDimensionCohort), "effectiveDate=${endDate}"));

    dataSetDefinition.addColumn(
        "PVLSTOTAL",
        "Total patients with Viral load - Denominator",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "patients with viral load",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12Months(),
                    mappings)),
            mappings),
        "");

    this.addRow(
        dataSetDefinition,
        "DR",
        "Adults & Children Denominator Routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "viral load results on routine adults and children",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        this.getAdultChildrenColumns());

    this.addRow(
        dataSetDefinition,
        "DT",
        "Adults & Children Denominator Routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "viral load results on routine adults and children",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        this.getAdultChildrenColumns());

    dataSetDefinition.addColumn(
        "DPREGROTINE",
        "Pregant routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Pregant routine",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "DPREGTARGET",
        "pregnant target",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Pregant target",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "DBREASROTINE",
        "Breastfeeding routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Breastfeeding routine",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findBreastfeedingWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "DBREASTARGET",
        "Breastfeeding target",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Breastfeeding target",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findBreastfeedingWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "NRTOTAL",
        "Total patients with Viral load - numerator",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "patients with viral load",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12Months(),
                    mappings)),
            mappings),
        "");

    this.addRow(
        dataSetDefinition,
        "NR",
        "Adults & Children Numerator Routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "viral load results on routine adults and children",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        this.getAdultChildrenColumns());

    this.addRow(
        dataSetDefinition,
        "NT",
        "Adults & Children Numerator target",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "viral load results on routine adults and children",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        this.getAdultChildrenColumns());

    dataSetDefinition.addColumn(
        "NPREGROTINE",
        "Pregant routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Pregant routine",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsWithVlMoreThan1000Rotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "NPREGTARGET",
        "Pregant target",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Pregant target",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantWomanWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsWithVlMoreThan1000Target(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "NBREASROTINE",
        "Breastfeeding routine",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Breastfeeding routine",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantBreatsFeedingWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsWithVlMoreThan1000Rotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "NBREASTARGET",
        "Breastfeeding target",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Breastfeeding target",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPregnantBreatsFeedingWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsWithVlMoreThan1000Target(),
                    mappings)),
            mappings),
        "");

    // Add SubTotal Denominator

    dataSetDefinition.addColumn(
        "DRSUBTOTAL",
        "Rotine Sub Total",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Rotine Sub Total",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "DTSUBTOTAL",
        "Target Sub Total",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Target Sub Total",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        "");

    // Add SubTotal Numerator

    dataSetDefinition.addColumn(
        "NRSUBTOTAL",
        "Rotine Numerator Sub Total",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Rotine Numerator Sub Total",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsRotine(),
                    mappings)),
            mappings),
        "");

    dataSetDefinition.addColumn(
        "NTSUBTOTAL",
        "Target Numerator Sub Total",
        EptsReportUtils.map(
            this.eptsGeneralIndicator.getIndicator(
                "Target Numerator Sub Total",
                EptsReportUtils.map(
                    this.pvlsCommunityCohortQueries
                        .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsTarget(),
                    mappings)),
            mappings),
        "");

    // Kay Population dimension

    dataSetDefinition.addDimension(
        "homosexual",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreHomosexual(), mappings));

    dataSetDefinition.addDimension(
        "drug-user",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoUseDrugs(), mappings));

    dataSetDefinition.addDimension(
        "prisioner",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreInPrison(), mappings));

    dataSetDefinition.addDimension(
        "sex-worker",
        EptsReportUtils.map(this.keyPopulationDimension.findPatientsWhoAreSexWorker(), mappings));

    // Key population collumn denominator

    final CohortIndicator rotineDenominator =
        this.eptsGeneralIndicator.getIndicator(
            "rotine",
            EptsReportUtils.map(
                this.pvlsCommunityCohortQueries
                    .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsRotine(),
                mappings));

    final CohortIndicator targetDenominator =
        this.eptsGeneralIndicator.getIndicator(
            "target",
            EptsReportUtils.map(
                this.pvlsCommunityCohortQueries
                    .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12MonthsTarget(),
                mappings));

    dataSetDefinition.addColumn(
        "DRMSM",
        "Homosexual",
        EptsReportUtils.map(rotineDenominator, mappings),
        "gender=M|homosexual=homosexual");

    dataSetDefinition.addColumn(
        "DTMSM",
        "Homosexual",
        EptsReportUtils.map(targetDenominator, mappings),
        "gender=M|homosexual=homosexual");

    dataSetDefinition.addColumn(
        "DRPWID",
        "Drugs User",
        EptsReportUtils.map(rotineDenominator, mappings),
        "drug-user=drug-user");

    dataSetDefinition.addColumn(
        "DTPWID",
        "Drugs User",
        EptsReportUtils.map(targetDenominator, mappings),
        "drug-user=drug-user");

    dataSetDefinition.addColumn(
        "DRPRI",
        "Prisioners",
        EptsReportUtils.map(rotineDenominator, mappings),
        "prisioner=prisioner");

    dataSetDefinition.addColumn(
        "DTPRI",
        "Prisioners",
        EptsReportUtils.map(targetDenominator, mappings),
        "prisioner=prisioner");

    dataSetDefinition.addColumn(
        "DRFSW",
        "Sex Worker",
        EptsReportUtils.map(rotineDenominator, mappings),
        "gender=F|sex-worker=sex-worker");

    dataSetDefinition.addColumn(
        "DTFSW",
        "Sex Worker",
        EptsReportUtils.map(targetDenominator, mappings),
        "gender=F|sex-worker=sex-worker");

    // Key population collumn Numerator

    final CohortIndicator rotineNumerator =
        this.eptsGeneralIndicator.getIndicator(
            "rotine",
            EptsReportUtils.map(
                this.pvlsCommunityCohortQueries
                    .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsRotine(),
                mappings));

    final CohortIndicator targetNumerator =
        this.eptsGeneralIndicator.getIndicator(
            "target",
            EptsReportUtils.map(
                this.pvlsCommunityCohortQueries
                    .findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12MonthsTarget(),
                mappings));

    dataSetDefinition.addColumn(
        "NRPWID",
        "Drugs User",
        EptsReportUtils.map(rotineNumerator, mappings),
        "drug-user=drug-user");

    dataSetDefinition.addColumn(
        "NTPWID",
        "Drugs User",
        EptsReportUtils.map(targetNumerator, mappings),
        "drug-user=drug-user");

    dataSetDefinition.addColumn(
        "NRMSM",
        "Homosexual",
        EptsReportUtils.map(rotineNumerator, mappings),
        "gender=M|homosexual=homosexual");

    dataSetDefinition.addColumn(
        "NTMSM",
        "Homosexual",
        EptsReportUtils.map(targetNumerator, mappings),
        "gender=M|homosexual=homosexual");

    dataSetDefinition.addColumn(
        "NRFSW",
        "Sex Worker",
        EptsReportUtils.map(rotineNumerator, mappings),
        "gender=F|sex-worker=sex-worker");

    dataSetDefinition.addColumn(
        "NTFSW",
        "Sex Worker",
        EptsReportUtils.map(targetNumerator, mappings),
        "gender=F|sex-worker=sex-worker");

    dataSetDefinition.addColumn(
        "NRPRI",
        "Prisioners",
        EptsReportUtils.map(rotineNumerator, mappings),
        "prisioner=prisioner");

    dataSetDefinition.addColumn(
        "NTPRI",
        "Prisioners",
        EptsReportUtils.map(targetNumerator, mappings),
        "prisioner=prisioner");

    return dataSetDefinition;
  }

  private List<ColumnParameters> getAdultChildrenColumns() {
    // Male
    final ColumnParameters under1M =
        new ColumnParameters("under1M", "under 1 year male", "gender=M|age=<1", "01");
    final ColumnParameters oneTo4M =
        new ColumnParameters("oneTo4M", "1 - 4 years male", "gender=M|age=1-4", "02");
    final ColumnParameters fiveTo9M =
        new ColumnParameters("fiveTo9M", "5 - 9 years male", "gender=M|age=5-9", "03");
    final ColumnParameters tenTo14M =
        new ColumnParameters("tenTo14M", "10 - 14 male", "gender=M|age=10-14", "04");
    final ColumnParameters fifteenTo19M =
        new ColumnParameters("fifteenTo19M", "15 - 19 male", "gender=M|age=15-19", "05");
    final ColumnParameters twentyTo24M =
        new ColumnParameters("twentyTo24M", "20 - 24 male", "gender=M|age=20-24", "06");
    final ColumnParameters twenty5To29M =
        new ColumnParameters("twenty4To29M", "25 - 29 male", "gender=M|age=25-29", "07");
    final ColumnParameters thirtyTo34M =
        new ColumnParameters("thirtyTo34M", "30 - 34 male", "gender=M|age=30-34", "08");
    final ColumnParameters thirty5To39M =
        new ColumnParameters("thirty5To39M", "35 - 39 male", "gender=M|age=35-39", "09");
    final ColumnParameters foutyTo44M =
        new ColumnParameters("foutyTo44M", "40 - 44 male", "gender=M|age=40-44", "10");
    final ColumnParameters fouty5To49M =
        new ColumnParameters("fouty5To49M", "45 - 49 male", "gender=M|age=45-49", "11");
    final ColumnParameters fiftyT054M =
        new ColumnParameters("fiftyT054M", "50 - 54 male", "gender=M|age=50-54", "12");
    final ColumnParameters fiftyfiveT059M =
        new ColumnParameters("fiftyfiveT059M", "55 - 59 male", "gender=M|age=55-59", "13");
    final ColumnParameters sixtyT064M =
        new ColumnParameters("sixtyT064M", "60 - 64 male", "gender=M|age=60-64", "14");
    final ColumnParameters above65M =
        new ColumnParameters("above65M", "65+ male", "gender=M|age=65+", "15");
    final ColumnParameters unknownM =
        new ColumnParameters("unknownM", "Unknown age male", "gender=M|age=UK", "16");

    // Female
    final ColumnParameters under1F =
        new ColumnParameters("under1F", "under 1 year female", "gender=F|age=<1", "17");
    final ColumnParameters oneTo4F =
        new ColumnParameters("oneTo4F", "1 - 4 years female", "gender=F|age=1-4", "18");
    final ColumnParameters fiveTo9F =
        new ColumnParameters("fiveTo9F", "5 - 9 years female", "gender=F|age=5-9", "19");
    final ColumnParameters tenTo14F =
        new ColumnParameters("tenTo14F", "10 - 14 female", "gender=F|age=10-14", "20");
    final ColumnParameters fifteenTo19F =
        new ColumnParameters("fifteenTo19F", "15 - 19 female", "gender=F|age=15-19", "21");
    final ColumnParameters twentyTo24F =
        new ColumnParameters("twentyTo24F", "20 - 24 female", "gender=F|age=20-24", "22");
    final ColumnParameters twenty5To29F =
        new ColumnParameters("twenty4To29F", "25 - 29 female", "gender=F|age=25-29", "23");
    final ColumnParameters thirtyTo34F =
        new ColumnParameters("thirtyTo34F", "30 - 34 female", "gender=F|age=30-34", "24");
    final ColumnParameters thirty5To39F =
        new ColumnParameters("thirty5To39F", "35 - 39 female", "gender=F|age=35-39", "25");
    final ColumnParameters foutyTo44F =
        new ColumnParameters("foutyTo44F", "40 - 44 female", "gender=F|age=40-44", "26");
    final ColumnParameters fouty5To49F =
        new ColumnParameters("fouty5To49F", "45 - 49 female", "gender=F|age=45-49", "27");
    final ColumnParameters fiftyT054F =
        new ColumnParameters("fiftyT054F", "50 - 54 female", "gender=F|age=50-54", "28");
    final ColumnParameters fiftyfiveT059F =
        new ColumnParameters("fiftyfiveT059F", "55 - 59 female", "gender=F|age=55-59", "29");
    final ColumnParameters sixtyT064F =
        new ColumnParameters("sixtyT064F", "60 - 64 female", "gender=F|age=60-64", "30");
    final ColumnParameters above65F =
        new ColumnParameters("above65F", "65+ female", "gender=F|age=65+", "31");
    final ColumnParameters unknownF =
        new ColumnParameters("unknownF", "Unknown age female", "gender=F|age=UK", "32");

    return Arrays.asList(
        unknownM,
        under1M,
        oneTo4M,
        fiveTo9M,
        tenTo14M,
        fifteenTo19M,
        twentyTo24M,
        twenty5To29M,
        thirtyTo34M,
        thirty5To39M,
        foutyTo44M,
        fouty5To49M,
        fiftyT054M,
        fiftyfiveT059M,
        sixtyT064M,
        above65M,
        unknownF,
        under1F,
        oneTo4F,
        fiveTo9F,
        tenTo14F,
        fifteenTo19F,
        twentyTo24F,
        twenty5To29F,
        thirtyTo34F,
        thirty5To39F,
        foutyTo44F,
        fouty5To49F,
        fiftyT054F,
        fiftyfiveT059F,
        sixtyT064F,
        above65F);
  }
}