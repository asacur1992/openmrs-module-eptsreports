select TbSynthoms.patient_id
         from 
          ( 
            select p.patient_id,e.encounter_datetime as data_tb  from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on o.encounter_id=e.encounter_id 
            where e.encounter_type in(6,9) and o.concept_id=1113  and e.location_id=:location 
            and e.voided=0 and p.voided=0 and o.voided=0 and e.encounter_datetime between  :endDate and CURDATE()
            UNION
            SELECT pg.patient_id, date_enrolled data_tb FROM patient p 
            INNER JOIN patient_program pg ON p.patient_id=pg.patient_id 
            WHERE pg.voided=0 AND p.voided=0 AND program_id=5 AND date_enrolled between  :endDate and CURDATE() AND location_id=:location
            UNION
            select p.patient_id,e.encounter_datetime as data_tb  from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on o.encounter_id=e.encounter_id 
            where e.encounter_type in(53) and o.concept_id=1406 and o.value_coded=42  and e.location_id=:location 
            and e.voided=0 and p.voided=0 and o.voided=0 and e.encounter_datetime between  :endDate and CURDATE()
            UNION
            select p.patient_id,e.encounter_datetime as data_tb  from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on o.encounter_id=e.encounter_id 
            where e.encounter_type in(6) and o.concept_id=1268 and o.value_coded=1256  and e.location_id=:location 
            and e.voided=0 and p.voided=0 and o.voided=0 and e.encounter_datetime between  :endDate and CURDATE()
            )TbSynthoms 