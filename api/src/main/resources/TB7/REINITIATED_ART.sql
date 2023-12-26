                select patient_id from (
                select p.patient_id, max(o.obs_datetime) data_reinicio, e.encounter_id
   from patient p
inner join encounter e on e.patient_id=p.patient_id
inner join obs o on o.encounter_id=e.encounter_id
  where e.voided=0 and e.encounter_type in (6,53) and o.concept_id in(6272,6273) and o.value_coded=1705 and o.voided=0
        and  e.location_id=:location and o.obs_datetime between :startDate and :endDate
  group by p.patient_id
  ) reinicio