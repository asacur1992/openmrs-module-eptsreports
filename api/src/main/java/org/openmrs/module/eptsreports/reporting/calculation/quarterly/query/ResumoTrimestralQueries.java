package org.openmrs.module.eptsreports.reporting.calculation.quarterly.query;

import java.util.List;

import org.openmrs.module.eptsreports.reporting.calculation.quarterly.MonthlyDateRange;
import org.openmrs.module.eptsreports.reporting.library.queries.BaseQueries;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;

public class ResumoTrimestralQueries {

	public static List<Integer> findPatientsWhoAreNewlyEnrolledOnART(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();

		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWhoAreNewlyEnrolledOnART);
		qb.addParameter("startDate", monthlyDateRange.getStartDate());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoWhereMarkedAsTransferreInByMinTransferredDate(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(
				IResumoTrimestralQueries.QUERY.findPatientsWhoWhereMarkedAsTransferreInByMinTransferredDate);
		qb.addParameter("startDate", monthlyDateRange.getStartDate());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientesWhoreMarkedAsTransferredOutInArtProgramOrInFichaClinicaOrInFichaResumo(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(
				IResumoTrimestralQueries.QUERY.findPatientesWhoreMarkedAsTransferredOutInArtProgramOrInFichaClinicaOrInFichaResumo);
		qb.addParameter("startDate", monthlyDateRange.getStartDate());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoWereSuspendTreatment(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWhoWereSuspendTreatment);
		qb.addParameter("startDate", monthlyDateRange.getStartDate());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoDiedDuringTreatment(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWhoDiedDuringTreatment);
		qb.addParameter("startDate", monthlyDateRange.getStartDate());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoAbandonedArtTreatment(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWhoAbandonedArtTreatment);
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoStillInFirstLine(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.getPatientsWhoStillInFirstLine);
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWhoAreInSecondLine(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWhoAreInSecondLine);
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> findPatientsWithRegisteredViralLoad(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(IResumoTrimestralQueries.QUERY.findPatientsWithRegisteredViralLoad);
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}

	public static List<Integer> getBaseCohort(
			final EvaluationContext context, final MonthlyDateRange monthlyDateRange) {
		final SqlQueryBuilder qb = new SqlQueryBuilder();
		qb.append(BaseQueries.getBaseCohortQuery());
		qb.addParameter("endDate", monthlyDateRange.getEndDate());
		qb.addParameter("location", (Location) context.getParameterValue("location"));

		return Context.getRegisteredComponents(EvaluationService.class)
				.get(0)
				.evaluateToList(qb, Integer.class, context);
	}
}