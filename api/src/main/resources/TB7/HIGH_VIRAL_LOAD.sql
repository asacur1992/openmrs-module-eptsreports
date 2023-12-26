				select patient_id from (
				select patient_id, max(data_carga2) data_carga2, if(cv.value_numeric>1000,1,null) decisao from (
				select segundaCarga.patient_id,primeiraCarga.data_carga_anterior data_carga2,primeiraCarga.value_numeric from (
                   Select p.patient_id,min(date(o.obs_datetime)) data_carga_2, o.value_numeric as vl2 
                from patient p 
                inner join encounter e on p.patient_id=e.patient_id 
                inner join obs o on e.encounter_id=o.encounter_id 
                where p.voided=0 and e.voided=0 and o.voided=0 
                and e.encounter_type in (13,51) 
                and  o.concept_id in (856) 
                and  date(o.obs_datetime) between :startDate and :endDate 
                and e.location_id=:location 
                and o.value_numeric>1000
                group by p.patient_id 
                ) segundaCarga
                inner join (
                       select cargaAnterior.patient_id,cargaAnterior.data_carga_anterior,o.value_numeric
               from
               (
				select p.patient_id,date(e.encounter_datetime) data_carga_anterior,e.encounter_id 
                  from patient p
                inner join encounter e on e.patient_id=p.patient_id
                where e.voided=0 and e.encounter_type in (13,51)
                and  e.location_id=:location and date(e.encounter_datetime)<:endDate
                )cargaAnterior
		inner join encounter e on cargaAnterior.patient_id=e.patient_id 
		inner join obs o on o.encounter_id=e.encounter_id 
		where date(o.obs_datetime)=cargaAnterior.data_carga_anterior and 
				o.concept_id = 856 and e.encounter_type in (13,51) and e.location_id=:location 
                )primeiraCarga on primeiraCarga.patient_id = segundaCarga.patient_id
                where primeiraCarga.data_carga_anterior < segundaCarga.data_carga_2
                order by primeiraCarga.data_carga_anterior desc
                ) cv group by patient_id
                ) finalCV where finalCV.decisao = 1