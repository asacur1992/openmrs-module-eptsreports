/**
 *
 */
package org.openmrs.module.eptsreports.reporting.library.queries;

/**
 * @author Stélio Moiane
 *
 */
public interface SearchPatientsQueries {

	public class QUERY {
		public static final String findFoundPatients = "SELECT patient_id FROM (\n" +
				"	SELECT patient_id, MAX(visit_date) last_visit FROM(\n" +
				"		SELECT p.patient_id, MAX(e.encounter_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON o.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND encounter_type = 21 AND o.concept_id IN (2016, 2157, 2158)\n"
				+
				"					AND e.location_id = :location AND e.encounter_datetime BETWEEN :startDate AND :endDate \n" +
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_found ON obs_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6254 AND obs_found.concept_id IN (2016, 2157, 2158)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate \n"
				+
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_found ON obs_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6255 AND obs_found.concept_id IN (2016, 2157, 2158)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate \n"
				+
				"						GROUP BY p.patient_id\n" +
				"						\n" +
				"	)patient_last_visit GROUP BY patient_id\n" +
				")patients_found";

		public static final String findFoundPatientsByAbsenseReason = "SELECT patient_id FROM (\n" +
				"	SELECT patient_id, MAX(visit_date) last_visit FROM(\n" +
				"		SELECT p.patient_id, MAX(e.encounter_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON o.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND encounter_type = 21 AND o.concept_id IN (2016, 2157, 2158)\n"
				+
				"				AND o.value_coded = :reason\n" +
				"					AND e.location_id = :location AND e.encounter_datetime BETWEEN :startDate AND :endDate\n" +
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_found ON obs_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6254 AND obs_found.concept_id IN (2016, 2157, 2158)\n" +
				"					AND obs_found.value_coded = :reason\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate\n"
				+
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_found ON obs_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6255 AND obs_found.concept_id IN (2016, 2157, 2158)\n" +
				"					AND obs_found.value_coded = :reason\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate \n"
				+
				"						GROUP BY p.patient_id\n" +
				"						\n" +
				"	)patient_last_visit GROUP BY patient_id\n" +
				"	\n" +
				")patients_found";

		public static final String findPatientNotFoundInSearch = "\n" +
				"SELECT patient_id FROM (\n" +
				"	SELECT patient_id, MAX(visit_date) last_visit FROM(\n" +
				"		SELECT p.patient_id, MAX(e.encounter_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON o.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND encounter_type = 21 \n" +
				"					AND o.concept_id IN (2031, 23944, 23945)\n" +
				"					AND e.location_id = :location AND e.encounter_datetime BETWEEN :startDate AND :endDate\n" +
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_not_found ON obs_not_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_not_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6254 AND obs_not_found.concept_id IN (2031, 23944, 23945)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate\n"
				+
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_not_found ON obs_not_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_not_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6255 AND obs_not_found.concept_id IN (2031, 23944, 23945)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate\n"
				+
				"						GROUP BY p.patient_id\n" +
				"						\n" +
				"	)patient_last_visit GROUP BY patient_id\n" +
				"	\n" +
				")patients_not_found";

		public static final String findPatientNotFoundInSearchByReason = "SELECT patient_id FROM (\n" +
				"	SELECT patient_id, MAX(visit_date) last_visit FROM(\n" +
				"		SELECT p.patient_id, MAX(e.encounter_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON o.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND encounter_type = 21 \n" +
				"					AND o.concept_id IN (2031, 23944, 23945)\n" +
				"					AND o.value_coded = :reason\n" +
				"					AND e.location_id = :location AND e.encounter_datetime BETWEEN :startDate AND :endDate\n" +
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_not_found ON obs_not_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_not_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6254 AND obs_not_found.concept_id IN (2031, 23944, 23945)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate\n"
				+
				"					AND obs_not_found.value_coded = :reason\n" +
				"						GROUP BY p.patient_id\n" +
				"	\n" +
				"		UNION\n" +
				"	\n" +
				"		SELECT p.patient_id, MAX(o.value_datetime) visit_date FROM patient p\n" +
				"			INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"			INNER JOIN obs o ON e.encounter_id = o.encounter_id\n" +
				"			INNER JOIN obs obs_not_found ON obs_not_found.encounter_id = e.encounter_id\n" +
				"				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND obs_not_found.voided = 0\n" +
				"					AND encounter_type = 21 AND o.concept_id = 6255 AND obs_not_found.concept_id IN (2031, 23944, 23945)\n" +
				"					AND o.value_datetime IS NOT NULL AND e.location_id = :location AND o.value_datetime BETWEEN :startDate AND :endDate\n"
				+
				"					AND obs_not_found.value_coded = :reason\n" +
				"						GROUP BY p.patient_id\n" +
				"						\n" +
				"	)patient_last_visit GROUP BY patient_id\n" +
				"	\n" +
				")patients_not_found";
	}
}
