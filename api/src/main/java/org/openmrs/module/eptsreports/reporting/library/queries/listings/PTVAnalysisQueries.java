/** */
package org.openmrs.module.eptsreports.reporting.library.queries.listings;

/** @author Stélio Moiane */
public interface PTVAnalysisQueries {

	class QUERY {
		public static final String findPTVAnalysisPatients = "SELECT p.patient_id, pi.identifier, pe.gender, (YEAR( :endDate) - YEAR(pe.birthdate)) age, pe.birthdate, \n"
				+
				"    art_start_date, last_viral_load_date, IF(last_viral_load_value IS NOT NUll, last_viral_load_value,  CASE ROUND(last_viral_load_value_coded)\n"
				+
				"    WHEN 1306 THEN 'Nivel baixo de detecção' WHEN 23905 THEN 'Menor que 10 copias/ml' WHEN 23906 THEN 'Menor que 20 copias/ml'\n" +
				"    WHEN 23907 THEN 'Menor que 40 copias/ml' WHEN 23908 THEN 'Menor que 400 copias/ml' WHEN 23904 THEN 'Menor que 839 copias/ml' WHEN 23814 THEN 'Indetectável' \n"
				+
				"    END) last_viral_load_value, occupation.value_text, education.degree, master_card.open_date, kp.key_population_value, wp.weak_population_value, \n"
				+
				"    IF(pickups.pickups_value > 0, 'SIM', 'NAO')pickup_drug, pickups.pickups_value, apss.apss_value, support_groups.support_group FROM patient p\n"
				+
				"    INNER JOIN patient_identifier pi ON p.patient_id = pi.patient_id\n" +
				"	INNER JOIN person_name pn ON pn.person_id = p.patient_id\n" +
				"	INNER JOIN person pe ON pe.person_id = p.patient_id\n" +
				"    INNER JOIN\n" +
				"    (\n" +
				"        SELECT patient_id, MIN(art_start_date) art_start_date FROM\n" +
				"            (	\n" +
				"				SELECT p.patient_id,MIN(e.encounter_datetime) art_start_date FROM patient p \n" +
				"					INNER JOIN encounter e ON p.patient_id=e.patient_id	\n" +
				"					INNER JOIN obs o ON o.encounter_id=e.encounter_id \n" +
				"						WHERE e.voided=0 AND o.voided=0 AND p.voided=0 \n" +
				"						AND e.encounter_type IN (18,6,9) AND o.concept_id=1255 AND o.value_coded=1256 \n" +
				"						AND e.encounter_datetime <= :endDate AND e.location_id = :location\n" +
				"							GROUP BY p.patient_id\n" +
				"				UNION\n" +
				"		\n" +
				"				SELECT p.patient_id,MIN(value_datetime) art_start_date FROM patient p\n" +
				"					INNER JOIN encounter e ON p.patient_id=e.patient_id\n" +
				"					INNER JOIN obs o ON e.encounter_id=o.encounter_id\n" +
				"						WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type in (18,6,9,53) \n" +
				"						AND o.concept_id=1190 AND o.value_datetime is NOT NULL \n" +
				"						AND o.value_datetime <= :endDate AND e.location_id = :location\n" +
				"							GROUP BY p.patient_id\n" +
				"				\n" +
				"				UNION\n" +
				"\n" +
				"				SELECT pg.patient_id,MIN(date_enrolled) art_start_date FROM patient p \n" +
				"					INNER JOIN patient_program pg ON p.patient_id=pg.patient_id\n" +
				"						WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled <= :endDate AND pg.location_id = :location\n"
				+
				"							GROUP BY pg.patient_id\n" +
				"				\n" +
				"				UNION\n" +
				"				\n" +
				"				SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p\n" +
				"					INNER JOIN encounter e ON p.patient_id=e.patient_id\n" +
				"				  		WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime <= :endDate AND e.location_id = :location\n"
				+
				"				  			GROUP BY p.patient_id\n" +
				"			  \n" +
				"				UNION\n" +
				"				\n" +
				"				SELECT p.patient_id,MIN(value_datetime) art_start_date FROM patient p\n" +
				"					INNER JOIN encounter e ON p.patient_id=e.patient_id\n" +
				"					INNER JOIN obs o ON e.encounter_id=o.encounter_id\n" +
				"						WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 \n" +
				"						AND o.concept_id=23866 and o.value_datetime is NOT NULL \n" +
				"						AND o.value_datetime <= :endDate AND e.location_id = :location\n" +
				"							GROUP BY p.patient_id	\n" +
				"	\n" +
				"		)min_art_start_date GROUP BY min_art_start_date.patient_id\n" +
				"	)start_art ON start_art.patient_id = p.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT patient_id, last_encounter_date, o.value_text FROM \n" +
				"        (\n" +
				"            SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p\n" +
				"                INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"                WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"                    GROUP BY p.patient_id\n" +
				"        )last_encounter\n" +
				"        INNER JOIN obs o ON o.obs_datetime = last_encounter.last_encounter_date AND o.person_id = last_encounter.patient_id\n" +
				"            WHERE o.voided = 0 AND o.concept_id = 1459\n" +
				"            GROUP BY patient_id        \n" +
				"    ) occupation ON occupation.patient_id = p.patient_id\n" +
				"    LEFT JOIN \n" +
				"    (\n" +
				"        SELECT patient_id, last_encounter_date, CASE o.value_coded\n" +
				"									WHEN 1445 THEN 'NENHUMA EDUCAÇÃO FORMAL'\n" +
				"									WHEN 1446 THEN 'PRIMARIO'\n" +
				"									WHEN 1447 THEN 'SECUNDÁRIO'\n" +
				"									ELSE 'UNIVERSITARIO' END degree FROM (\n" +
				"        SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"                    GROUP BY p.patient_id\n" +
				"        )last_encounter\n" +
				"        INNER JOIN obs o ON o.obs_datetime = last_encounter.last_encounter_date AND o.person_id = last_encounter.patient_id\n" +
				"            WHERE o.voided = 0 AND o.concept_id = 1443\n" +
				"            GROUP BY patient_id\n" +
				"    )education ON education.patient_id = p.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT patient_id, CASE o.value_coded\n" +
				"									WHEN 1377 THEN 'HSH'\n" +
				"									WHEN 20454 THEN 'PID'\n" +
				"									WHEN 20426 THEN 'REC'\n" +
				"									WHEN 1901 THEN 'MTS'\n" +
				"									ELSE 'OUTRO' END key_population_value FROM (\n" +
				"        SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"                    GROUP BY p.patient_id\n" +
				"        )key_population\n" +
				"        INNER JOIN obs o ON o.obs_datetime = key_population.last_encounter_date AND o.person_id = key_population.patient_id\n" +
				"            WHERE o.voided = 0 AND o.concept_id = 23703\n" +
				"            GROUP BY patient_id\n" +
				"    )kp ON kp.patient_id = p.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT patient_id, CASE o.value_coded WHEN 1995 THEN 'Casais serodiscordantes'\n" +
				"							   WHEN 1908 THEN 'Mineiro'\n" +
				"							   WHEN 1903 THEN 'Motorista'\n" +
				"							   WHEN 23712 THEN 'Mulher jovem entre 15-24 anos'\n" +
				"							   WHEN 1174 THEN 'Orfão'\n" +
				"							   WHEN 1977 THEN 'PVHS'\n" +
				"							   WHEN 23713 THEN 'Pessoa com deficiência'\n" +
				"							   WHEN 23711 THEN 'Rapariga entre 10-14 anos'\n" +
				"							   WHEN 1904 THEN 'Trabalhador Migrante'\n" +
				"							   ELSE 'Trabalhadores Sazonais' END weak_population_value FROM (\n" +
				"        SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"                    GROUP BY p.patient_id\n" +
				"        )weak_population\n" +
				"            INNER JOIN obs o ON o.obs_datetime = weak_population.last_encounter_date AND o.person_id = weak_population.patient_id\n"
				+
				"                WHERE o.voided = 0 AND o.concept_id = 23710\n" +
				"                GROUP BY patient_id\n" +
				"    )wp ON wp.patient_id = p.patient_id\n" +
				"    LEFT JOIN \n" +
				"    (\n" +
				"        SELECT p.patient_id, MIN(e.encounter_datetime) open_date FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"            AND e.encounter_type = 53\n" +
				"                GROUP BY p.patient_id\n" +
				"    )master_card ON master_card.patient_id = p.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT p.patient_id, COUNT(e.encounter_id) pickups_value FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"            AND e.encounter_type = 18\n" +
				"                GROUP BY p.patient_id\n" +
				"    )pickups ON p.patient_id = pickups.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT p.patient_id, COUNT(e.encounter_id) apss_value FROM patient p\n" +
				"            INNER JOIN encounter e ON e.patient_id = p.patient_id\n" +
				"            WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"            AND e.encounter_type = 35\n" +
				"                GROUP BY p.patient_id\n" +
				"    )apss ON apss.patient_id = p.patient_id\n" +
				"    LEFT JOIN\n" +
				"    (\n" +
				"        SELECT patient_id, last_encounter_date, CASE o.concept_id WHEN 23753 THEN 'CR'  \n" +
				"        WHEN 23757 THEN 'AR' WHEN 23755 THEN 'PC' WHEN 23759 THEN 'MPM' WHEN 23772 THEN o.value_text END support_group FROM \n" +
				"        (\n" +
				"            SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p\n" +
				"                INNER JOIN encounter e ON p.patient_id = e.patient_id\n" +
				"                WHERE p.voided = 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location\n"
				+
				"                GROUP BY p.patient_id\n" +
				"        )last_encounter INNER JOIN obs o ON o.obs_datetime = last_encounter.last_encounter_date AND last_encounter.patient_id = o.person_id\n"
				+
				"            WHERE o.voided = 0 AND o.concept_id IN (23753,23757,23755,23759, 23772) AND (o.value_coded IN (1256, 1257) OR o.value_coded IS NULL)\n"
				+
				"    )support_groups ON support_groups.patient_id = p.patient_id\n" +
				"    LEFT JOIN \n" +
				"    (  \n" +
				"        SELECT last_viral_load_date.patient_id,last_viral_load_date, last_viral_load_value, last_viral_load_value_coded FROM\n" +
				"        (\n" +
				"            SELECT o.person_id patient_id, MAX(o.obs_datetime) last_viral_load_date FROM obs o\n" +
				"	            WHERE o.voided = 0 AND o.concept_id IN (856,1305)\n" +
				"                    GROUP BY o.person_id\n" +
				"        )last_viral_load_date \n" +
				"        LEFT JOIN\n" +
				"        (\n" +
				"            SELECT o.person_id patient_id, o.obs_datetime ,o.value_numeric last_viral_load_value, o.value_coded last_viral_load_value_coded FROM obs o\n"
				+
				"	            WHERE o.voided = 0 AND o.concept_id IN (856,1305)\n" +
				"        )last_viral_load_value ON last_viral_load_date.patient_id = last_viral_load_value.patient_id AND last_viral_load_value.obs_datetime = last_viral_load_date\n"
				+
				"    )last_viral_load_date_value ON last_viral_load_date_value.patient_id = p.patient_id AND last_viral_load_date BETWEEN art_start_date AND :endDate\n"
				+
				"    GROUP BY p.patient_id\n" +
				"    ORDER BY pi.identifier";
	}
}