select patient_id from (
		select tbLam.patient_id, nivelDePositividade.value_coded from (
		   select p.patient_id
		from patient p
		inner join encounter e on e.patient_id=p.patient_id
		inner join obs o on o.encounter_id=e.encounter_id
		where p.voided=0 and  e.voided=0 and e.encounter_type in (6,13,90) and o.concept_id=23951 and o.value_coded in(703,664)  and o.voided=0 
        and  e.location_id=:location and e.encounter_datetime between :endDate and CURDATE()
        ) tbLam 
        left join
        (
		select p.patient_id, e.encounter_datetime, o.value_coded
		from patient p
		inner join encounter e on e.patient_id=p.patient_id
		left join obs o on o.encounter_id=e.encounter_id
		where p.voided=0 and  e.voided=0 and e.encounter_type in (6,13,90) and o.concept_id=165185 and o.voided=0 
		   and  e.location_id=:location and e.encounter_datetime between :endDate and CURDATE()    
        )nivelDePositividade on nivelDePositividade.patient_id = tbLam.patient_id
        )f