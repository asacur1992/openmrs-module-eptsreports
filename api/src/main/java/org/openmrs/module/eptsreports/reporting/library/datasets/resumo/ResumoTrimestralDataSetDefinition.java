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
package org.openmrs.module.eptsreports.reporting.library.datasets.resumo;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.MonthlyDateRange.Month;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.ResumoTrimestralMonthPeriodCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.ResumoTrimestralUtil.QUARTERLIES;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryA;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryB;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryC;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryE;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryF;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryG;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryI;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryJ;
import org.openmrs.module.eptsreports.reporting.calculation.quarterly.factory.ResumoTrimestralIndicatorFactoryL;
import org.openmrs.module.eptsreports.reporting.library.cohorts.ResumoTrimestralCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.BaseDataSet;
import org.openmrs.module.eptsreports.reporting.reports.SetupResumoTrimestralReport;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.ListMap;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResumoTrimestralDataSetDefinition extends BaseDataSet {

	private final ResumoTrimestralCohortQueries resumoTrimestralCohortQueries;

	@Autowired
	public ResumoTrimestralDataSetDefinition(
			final ResumoTrimestralCohortQueries resumoTrimestralCohortQueries) {
		this.resumoTrimestralCohortQueries = resumoTrimestralCohortQueries;
	}

	public DataSetDefinition constructResumoTrimestralDataset() {

		final CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Resumo Trimestral DataSet");
		dsd.addParameters(SetupResumoTrimestralReport.getDataParameters());

		final String mappings = "year=${year},quarter=${quarter},location=${location}";

		final ResumoTrimestralIndicatorFactoryA factoryA =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryA.class).get(0);
		final ResumoTrimestralIndicatorFactoryB factoryB =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryB.class).get(0);
		final ResumoTrimestralIndicatorFactoryC factoryC =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryC.class).get(0);
		final ResumoTrimestralIndicatorFactoryE factoryE =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryE.class).get(0);
		final ResumoTrimestralIndicatorFactoryF factoryF =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryF.class).get(0);
		final ResumoTrimestralIndicatorFactoryG factoryG =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryG.class).get(0);
		final ResumoTrimestralIndicatorFactoryI factoryI =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryI.class).get(0);
		final ResumoTrimestralIndicatorFactoryJ factoryJ =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryJ.class).get(0);
		final ResumoTrimestralIndicatorFactoryL factoryL =
				Context.getRegisteredComponents(ResumoTrimestralIndicatorFactoryL.class).get(0);

		final ListMap<String, CohortDefinition> listMap = new ListMap<>();

		int iteration = 1;
		for (final Month month : Month.values()) {
			final ResumoTrimestralMonthPeriodCalculation calculatorA =
					factoryA.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorB =
					factoryB.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorC =
					factoryC.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorE =
					factoryE.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorF =
					factoryF.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorG =
					factoryG.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorI =
					factoryI.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorJ =
					factoryJ.getResumoTrimestralCalculator(month);
			final ResumoTrimestralMonthPeriodCalculation calculatorL =
					factoryL.getResumoTrimestralCalculator(month);

			final CohortDefinition definitionForSectionA =
					this.resumoTrimestralCohortQueries.getPatientsForMonthlyCohort(month, calculatorA);

			this.addColumnForMonth(
					dsd,
					definitionForSectionA,
					mappings,
					month,
					iteration,
					"A-month-",
					"A: Iniciaram TARV Cohort Original - ");
			listMap.putInList("A", definitionForSectionA);

			final CohortDefinition definitionForSectionB =
					this.resumoTrimestralCohortQueries.getPatientsForMonthlyCohort(month, calculatorB);
			this.addColumnForMonth(
					dsd,
					definitionForSectionB,
					mappings,
					month,
					iteration,
					"B-month-",
					"B: Transferidos de - ");
			listMap.putInList("B", definitionForSectionB);

			final CohortDefinition definitionForSectionC =
					this.resumoTrimestralCohortQueries.getPatientsForMonthlyCohort(month, calculatorC);
			this.addColumnForMonth(
					dsd,
					definitionForSectionC,
					mappings,
					month,
					iteration,
					"C-month-",
					"C: Transferidos para - ");
			listMap.putInList("C", definitionForSectionC);

			final CohortDefinition definitionForSectionD =
					this.resumoTrimestralCohortQueries.getPatientsForCurrentCohort(
							month, definitionForSectionA, definitionForSectionB, definitionForSectionC);
			this.addColumnForMonth(
					dsd,
					definitionForSectionD,
					mappings,
					month,
					iteration,
					"D-month-",
					"D: Coorte Actual - ");
			listMap.putInList("D", definitionForSectionD);

			final CohortDefinition definitionForSectionL =
					this.resumoTrimestralCohortQueries.getPatientsWhoWereRegisteredAsDead(
							month, calculatorL, definitionForSectionD);
			this.addColumnForMonth(
					dsd,
					definitionForSectionL,
					mappings,
					month,
					iteration,
					"L-month-",
					"L: Coorte actual - Suspensões ao completar 12 meses - ");
			listMap.putInList("L", definitionForSectionL);

			final CohortDefinition definitionForSectionI =
					this.resumoTrimestralCohortQueries.findPatientsWhoHaveSuspendedTreatment(
							month, calculatorE, calculatorI, definitionForSectionD, definitionForSectionL);
			this.addColumnForMonth(
					dsd,
					definitionForSectionI,
					mappings,
					month,
					iteration,
					"I-month-",
					"I: Coorte actual - Suspensões ao completar 12 meses - ");
			listMap.putInList("I", definitionForSectionI);

			final CohortDefinition definitionForSectionJ =
					this.resumoTrimestralCohortQueries.getPatientsWhoAbandonedArtTreatment(
							month,
							calculatorJ,
							definitionForSectionD,
							definitionForSectionI,
							definitionForSectionL);
			this.addColumnForMonth(
					dsd,
					definitionForSectionJ,
					mappings,
					month,
					iteration,
					"J-month-",
					"J: Coorte actual - Abandonos ao completar 12 meses - ");
			listMap.putInList("J", definitionForSectionJ);

			final CohortDefinition definitionForSectionE =
					this.resumoTrimestralCohortQueries.getPatientsWhoStillInFirstTerapeuticLine(
							month,
							calculatorE,
							definitionForSectionD,
							definitionForSectionI,
							definitionForSectionJ,
							definitionForSectionL);
			this.addColumnForMonth(
					dsd,
					definitionForSectionE,
					mappings,
					month,
					iteration,
					"E-month-",
					"E: Coorte actual - Continuam na 1 Linha  - ");
			listMap.putInList("E", definitionForSectionE);

			final CohortDefinition definitionForSectionF =
					this.resumoTrimestralCohortQueries
					.getPatientsWhoStillInFirstTerapeuticLineWithViralLoadResultRegistered(
							month, calculatorF, definitionForSectionE);
			this.addColumnForMonth(
					dsd,
					definitionForSectionF,
					mappings,
					month,
					iteration,
					"F-month-",
					"F: Coorte actual - Continuam na 1 Linha E Receberam Resultado de Carga viral  - ");
			listMap.putInList("F", definitionForSectionF);

			final CohortDefinition definitionForSectionG =
					this.resumoTrimestralCohortQueries.getPatientsWhoAreInSecondTerapeuticLine(
							month,
							calculatorG,
							definitionForSectionD,
							definitionForSectionI,
							definitionForSectionJ,
							definitionForSectionL);
			this.addColumnForMonth(
					dsd,
					definitionForSectionG,
					mappings,
					month,
					iteration,
					"G-month-",
					"G: Coorte actual - Na segunda Linha  - ");
			listMap.putInList("G", definitionForSectionG);

			final CohortDefinition definitionForSectionH =
					this.resumoTrimestralCohortQueries
					.getPatientsWhoAreInSecondTerapeuticLineWithViralLoadResultRegistered(
							month, calculatorF, definitionForSectionG);
			this.addColumnForMonth(
					dsd,
					definitionForSectionH,
					mappings,
					month,
					iteration,
					"H-month-",
					"H: Coorte actual - Na segunda Linha  e receberam resultados da carga viral ");
			listMap.putInList("H", definitionForSectionH);

			iteration++;
		}

		iteration = 1;
		for (final QUARTERLIES quarter : QUARTERLIES.values()) {

			final CohortDefinition definitionForSectionA =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("A"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionA,
					mappings,
					quarter,
					iteration,
					"A-quarter-",
					"A: Total Quarter - ");

			final CohortDefinition definitionForSectionB =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("B"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionB,
					mappings,
					quarter,
					iteration,
					"B-quarter-",
					"B: Total Quarter - ");

			final CohortDefinition definitionForSectionC =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("C"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionC,
					mappings,
					quarter,
					iteration,
					"C-quarter-",
					"C: Total Quarter - ");

			final CohortDefinition definitionForSectionD =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("D"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionD,
					mappings,
					quarter,
					iteration,
					"D-quarter-",
					"D: Total Quarter - ");

			final CohortDefinition definitionForSectionE =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("E"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionE,
					mappings,
					quarter,
					iteration,
					"E-quarter-",
					"E: Total Quarter - ");

			final CohortDefinition definitionForSectionF =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("F"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionF,
					mappings,
					quarter,
					iteration,
					"F-quarter-",
					"F: Total Quarter - ");

			final CohortDefinition definitionForSectionG =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("G"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionG,
					mappings,
					quarter,
					iteration,
					"G-quarter-",
					"G: Total Quarter - ");

			final CohortDefinition definitionForSectionH =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("H"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionH,
					mappings,
					quarter,
					iteration,
					"H-quarter-",
					"H: Total Quarter - ");

			final CohortDefinition definitionForSectionI =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("I"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionI,
					mappings,
					quarter,
					iteration,
					"I-quarter-",
					"I: Total Quarter - ");

			final CohortDefinition definitionForSectionL =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("L"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionL,
					mappings,
					quarter,
					iteration,
					"L-quarter-",
					"L: Total Quarter - ");

			final CohortDefinition definitionForSectionJ =
					this.resumoTrimestralCohortQueries.getTotalPatientsQuarterly(
							quarter, this.getCohortsByQuarter(listMap.get("J"), quarter));
			this.addColumnForMonth(
					dsd,
					definitionForSectionJ,
					mappings,
					quarter,
					iteration,
					"J-quarter-",
					"J: Total Quarter - ");

			iteration++;
		}
		return dsd;
	}

	private void addColumnForMonth(
			final CohortIndicatorDataSetDefinition dsd,
			final CohortDefinition definitionForSection,
			final String mappings,
			final Object period,
			final int iteration,
			final String prefixColumnName,
			final String label) {

		final CohortIndicator indicatorForSection =
				this.getIndicator(label + period, EptsReportUtils.map(definitionForSection, mappings));

		dsd.addColumn(
				prefixColumnName + StringUtils.leftPad(StringUtils.EMPTY + iteration, 2, "0"),
				label + period,
				EptsReportUtils.map(indicatorForSection, mappings),
				StringUtils.EMPTY);
	}

	private List<CohortDefinition> getCohortsByQuarter(
			final List<CohortDefinition> cohortDefinitions, final QUARTERLIES quarter) {

		if (quarter.getCode() == 1) {
			return Arrays.asList(
					cohortDefinitions.get(0), cohortDefinitions.get(1), cohortDefinitions.get(2));
		}
		if (quarter.getCode() == 2) {
			return Arrays.asList(
					cohortDefinitions.get(3), cohortDefinitions.get(4), cohortDefinitions.get(5));
		}
		if (quarter.getCode() == 3) {
			return Arrays.asList(
					cohortDefinitions.get(6), cohortDefinitions.get(7), cohortDefinitions.get(8));
		}
		return Arrays.asList(
				cohortDefinitions.get(9), cohortDefinitions.get(10), cohortDefinitions.get(11));
	}

	private CohortIndicator getIndicator(final String name, final Mapped<CohortDefinition> cohort) {

		final CohortIndicator indicator = new CohortIndicator(name);
		indicator.addParameter(new Parameter("year", "Year", String.class));
		indicator.addParameter(new Parameter("quarter", "Quarter", String.class));
		indicator.addParameter(new Parameter("location", "Facility", Location.class));
		indicator.setCohortDefinition(cohort);

		return indicator;
	}
}