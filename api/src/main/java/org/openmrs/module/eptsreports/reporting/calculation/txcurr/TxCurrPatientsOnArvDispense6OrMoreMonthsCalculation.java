package org.openmrs.module.eptsreports.reporting.calculation.txcurr;

import java.util.Arrays;
import java.util.List;

import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.eptsreports.reporting.calculation.BooleanResult;
import org.openmrs.module.eptsreports.reporting.utils.EptsDateUtil;
import org.springframework.stereotype.Component;

@Component
public class TxCurrPatientsOnArvDispense6OrMoreMonthsCalculation
extends TxCurrPatientsOnArtOnArvDispenseIntervalsCalculation {

	private static int DAYS_EQULAS_OR_GREATER_SIX_MONTHS = 173;

	@Override
	protected void evaluateDisaggregatedPatients(
			final Integer patientId,
			final CalculationResultMap resultMap,
			List<PatientDisaggregated> allPatientDisaggregated) {

		allPatientDisaggregated = super.getMaximumPatientDisaggregatedByDate(allPatientDisaggregated);

		if (allPatientDisaggregated.size() > 1) {

			for (final PatientDisaggregated patientDisaggregated : allPatientDisaggregated) {
				if (DisaggregationSourceTypes.FILA.equals(
						patientDisaggregated.getDisaggregationSourceType())) {
					if (this.isInExpectedFilaDisaggregationInterval(
							(FilaPatientDisaggregated) patientDisaggregated)) {
						resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
					}
					return;
				}
			}
			for (final PatientDisaggregated patientDisaggregated : allPatientDisaggregated) {
				if (DisaggregationSourceTypes.DISPENSA_SEMESTRAL.equals(
						patientDisaggregated.getDisaggregationSourceType())) {
					resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
					return;
				}
			}
			for (final PatientDisaggregated patientDisaggregated : allPatientDisaggregated) {

				if (Arrays.asList(
						DisaggregationSourceTypes.DISPENSA_TRIMESTRAL,
						DisaggregationSourceTypes.DISPENSA_MENSAL)
						.contains(patientDisaggregated.getDisaggregationSourceType())) {
					return;
				}
			}
			for (final PatientDisaggregated patientDisaggregated : allPatientDisaggregated) {

				if (DisaggregationSourceTypes.MODELO_DIFERENCIADO_SEMESTRAL.equals(
						patientDisaggregated.getDisaggregationSourceType())) {
					resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
					return;
				}
			}

		} else if (!allPatientDisaggregated.isEmpty()) {

			final PatientDisaggregated maxPatientDisaggregated = allPatientDisaggregated.iterator().next();
			if (maxPatientDisaggregated != null) {
				if (DisaggregationSourceTypes.FILA.equals(
						maxPatientDisaggregated.getDisaggregationSourceType())) {
					if (this.isInExpectedFilaDisaggregationInterval(
							(FilaPatientDisaggregated) maxPatientDisaggregated)) {
						resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
					}
					return;
				}
				if (DisaggregationSourceTypes.DISPENSA_SEMESTRAL.equals(
						maxPatientDisaggregated.getDisaggregationSourceType())) {
					resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
				}

				if (Arrays.asList(
						DisaggregationSourceTypes.DISPENSA_TRIMESTRAL,
						DisaggregationSourceTypes.DISPENSA_MENSAL)
						.contains(maxPatientDisaggregated.getDisaggregationSourceType())) {
					return;
				}

				if (DisaggregationSourceTypes.MODELO_DIFERENCIADO_SEMESTRAL.equals(
						maxPatientDisaggregated.getDisaggregationSourceType())) {
					resultMap.put(patientId, new BooleanResult(Boolean.TRUE, this));
				}
			}
		}
	}

	private boolean isInExpectedFilaDisaggregationInterval(
			final FilaPatientDisaggregated filaDisaggregation) {
		return EptsDateUtil.getDaysBetween(
				filaDisaggregation.getDate(), filaDisaggregation.getNextFila())
				> TxCurrPatientsOnArvDispense6OrMoreMonthsCalculation.DAYS_EQULAS_OR_GREATER_SIX_MONTHS;
	}
}