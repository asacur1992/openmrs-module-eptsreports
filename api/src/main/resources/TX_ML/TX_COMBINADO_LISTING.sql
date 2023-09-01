
SELECT p.patient_id, SHA2(p.patient_id, 256) AS encrypted_id, pi.identifier, CONCAT(pn.given_name, ' ', COALESCE(pn.middle_name, ''), pn.family_name) name, pe.gender, (YEAR( :endDate) - YEAR(pe.birthdate)) age, 
       jan_encounter_date, feb_encounter_date, mar_encounter_date, apr_encounter_date, may_encounter_date, jun_encounter_date, 
       jul_encounter_date, aug_encounter_date, sep_encounter_date, oct_encounter_date, nov_encounter_date, dec_encounter_date FROM patient p
	INNER JOIN patient_identifier pi ON p.patient_id = pi.patient_id
	INNER JOIN person_name pn ON pn.person_id = p.patient_id
	INNER JOIN person pe ON pe.person_id = p.patient_id
	LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) jan_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 1 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) jan_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 1 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id
	)jan_encounter ON jan_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) feb_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 2 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) feb_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 2 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)feb_encounter ON feb_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) mar_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 3 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) mar_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 3 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)mar_encounter ON mar_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) apr_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 4 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) apr_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 4 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)apr_encounter ON apr_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) may_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 5 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) may_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 5 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)may_encounter ON may_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) jun_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 6 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) jun_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 6 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)jun_encounter ON jun_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) jul_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 7 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) jul_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 7 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)jul_encounter ON jul_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) aug_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 6 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) aug_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 8 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)aug_encounter ON aug_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) sep_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 9 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) sep_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 9 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)sep_encounter ON sep_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) oct_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 10 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) oct_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 10 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)oct_encounter ON oct_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) nov_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 11 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) nov_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 11 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)nov_encounter ON nov_encounter.patient_id = p.patient_id
    LEFT JOIN 
	(
        SELECT max_encounter_date.patient_id, MAX(last_encounter_date) dec_encounter_date FROM
            (
                SELECT p.patient_id, MAX(e.encounter_datetime) last_encounter_date FROM patient p
                    INNER JOIN encounter e ON p.patient_id=e.patient_id
                            WHERE p.voided= 0 AND e.voided= 0 AND e.encounter_type in (18,6,9,53) 
                                AND MONTH(e.encounter_datetime) = 12 AND e.encounter_datetime >= :startDate AND e.encounter_datetime <= :endDate AND e.location_id = :location
                                    GROUP BY p.patient_id
                UNION
                
                SELECT o.person_id patient_id, MAX(o.value_datetime) dec_encounter_date FROM obs o
                                INNER JOIN encounter e ON e.encounter_id = o.encounter_id
                                    WHERE o.concept_id = 23866 AND e.encounter_type = 52 
                                    AND o.voided = 0 AND e.voided = 0 
                                    AND MONTH(o.value_datetime) = 12 AND o.value_datetime >= :startDate AND o.value_datetime <= :endDate AND e.location_id = :location
                                        GROUP BY o.person_id
            )max_encounter_date GROUP BY patient_id 
	)dec_encounter ON dec_encounter.patient_id = p.patient_id
        WHERE p.patient_id IN ( :patientIds) GROUP BY p.patient_id