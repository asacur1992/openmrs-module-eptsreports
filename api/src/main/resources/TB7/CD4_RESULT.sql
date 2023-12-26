select p.patient_id
   from patient p
inner join encounter e on e.patient_id=p.patient_id
inner join obs o on o.encounter_id=e.encounter_id
  where e.voided=0 and e.encounter_type in (6,13,53,51,90) and o.concept_id=1695  and o.voided=0 and o.value_numeric is not null
        and  e.location_id=:location and date(o.obs_datetime) between :startDate and :endDate