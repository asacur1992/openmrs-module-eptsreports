select f.patient_id
from 
(
select p.patient_id, o.obs_datetime,o.value_numeric, e.encounter_type,e.encounter_id
   from patient p
inner join encounter e on e.patient_id=p.patient_id
inner join obs o on o.encounter_id=e.encounter_id
  where (p.voided=0 and  e.voided=0 and e.encounter_type in (6,13,53,51,90) and o.concept_id=1695  and o.voided=0 and o.value_numeric is not null)
        and  e.location_id=:location and o.obs_datetime between :startDate and :endDate 
)f 

inner join person pe on pe.person_id=f.patient_id
WHERE       (TIMESTAMPDIFF(year,pe.birthdate,:endDate)>=5 and f.value_numeric<200 ) 
        OR  ((TIMESTAMPDIFF(year,pe.birthdate,:endDate) between 1 and 4) and f.value_numeric<500 )
        OR  (TIMESTAMPDIFF(year,pe.birthdate,:endDate)<1 and f.value_numeric<750 )
GROUP BY f.patient_id