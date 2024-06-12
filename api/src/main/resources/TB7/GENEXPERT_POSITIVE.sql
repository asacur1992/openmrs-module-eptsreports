select p.patient_id
   from patient p
inner join encounter e on e.patient_id=p.patient_id
inner join obs o on o.encounter_id=e.encounter_id
  where p.voided=0 and  e.voided=0 and e.encounter_type in (6,13,90) and o.concept_id=23723 and o.value_coded=703 and o.voided=0 
        and  e.location_id=:location and e.encounter_datetime between :endDate and CURDATE()

union        

select p.patient_id
   from patient p
inner join encounter e on e.patient_id=p.patient_id
inner join obs o on o.encounter_id=e.encounter_id
  where p.voided=0 and  e.voided=0 and e.encounter_type = 13 and o.concept_id=165189 and o.value_coded=1065 and o.voided=0 
        and  e.location_id=:location and e.encounter_datetime between :endDate and CURDATE()