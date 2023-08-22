SELECT patient_id,art_start_date FROM 
          (SELECT patient_id, MIN(art_start_date) art_start_date FROM 
          ( 
          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM patient p 
          INNER JOIN encounter e ON p.patient_id=e.patient_id 
          WHERE e.voided=0 AND  p.voided=0 AND e.encounter_type in (18) 
          AND e.location_id  in(:location) 
          AND e.encounter_datetime between date_sub(STR_TO_DATE(concat('21/12/',:year), '%d/%m/%Y'), interval 12 month)

          GROUP BY p.patient_id 
          UNION 
          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p 
          INNER JOIN encounter e ON p.patient_id=e.patient_id 
          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
          AND o.concept_id=23866 
          AND e.location_id  in(:location) 
          AND o.value_datetime between date_sub(STR_TO_DATE(concat('21/12/',:year), '%d/%m/%Y'), interval 12 month)
          GROUP BY p.patient_id 
          ) 
          art_start GROUP BY patient_id 
          ) tx_new 
