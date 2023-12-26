           select final.patient_id from
           (
            select 
            pe.person_id as patient_id,
            pe.gender, 
            gravida_real.data_gravida,
            lactante_real.data_parto,
            if(lactante_real.data_parto is null,1, if(gravida_real.data_gravida is null,2, if(gravida_real.data_gravida>=lactante_real.data_parto,1,2))) decisao 
            from person pe
            left join 
            (
            select gravida_real.patient_id,max(gravida_real.data_gravida) data_gravida  from  
            ( 
            Select p.patient_id,e.encounter_datetime data_gravida from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1982 and value_coded=1065 and e.encounter_type in (5,6) and e.encounter_datetime  between :startDate and :endDate and e.location_id=:location
            union 
            Select p.patient_id,e.encounter_datetime data_gravida from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1279 and e.encounter_type in (5,6) and e.encounter_datetime between :startDate and :endDate and e.location_id=:location
            union 
            Select  p.patient_id,o.value_datetime data_gravida from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1600 and e.encounter_type in (5,6) and e.encounter_datetime between :startDate and :endDate and e.location_id=:location
            union 
            Select p.patient_id,e.encounter_datetime data_gravida from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6331 and e.encounter_type in (5,6) and e.encounter_datetime between :startDate and :endDate and e.location_id=:location
            union 
            select pp.patient_id,pp.date_enrolled data_gravida from patient_program pp 
            where pp.program_id=8 and pp.voided=0 and pp.date_enrolled between :startDate and :endDate and pp.location_id=:location
            union 
            Select p.patient_id,obsART.value_datetime data_gravida from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            inner join obs obsART on e.encounter_id=obsART.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=1982 and o.value_coded=1065 and 
            e.encounter_type=53 and obsART.value_datetime between :startDate and :endDate and e.location_id=:location and obsART.concept_id=1190 and obsART.voided=0 
            union 
            select p.patient_id,data_colheita.value_datetime data_gravida from patient p                                                                                                                                                                        
                inner join encounter e on p.patient_id=e.patient_id                                                                                                                                                                                                 
                inner join obs esta_gravida on e.encounter_id=esta_gravida.encounter_id
                inner join obs data_colheita on e.encounter_id=data_colheita.encounter_id                                                                                                                                                                           
            where p.voided=0 and e.voided=0 and esta_gravida.voided=0 and data_colheita.voided = 0 and esta_gravida.concept_id=1982                                                                                                                                 
                and esta_gravida.value_coded = 1065 and e.encounter_type=51                                                                                                                                                                                         
                and data_colheita.concept_id =23821 and data_colheita.value_datetime between :startDate and :endDate and e.location_id= 398                                                       
            ) gravida_real 
            group by gravida_real.patient_id
            ) gravida_real on gravida_real.patient_id=pe.person_id 
            left join
            (
            select lactante_real.patient_id,max(lactante_real.data_parto) data_parto  from
            ( 
            Select p.patient_id,o.value_datetime data_parto from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=5599 and 
            e.encounter_type in (5,6) and o.value_datetime between :startDate and :endDate and e.location_id=:location
            union 
            Select p.patient_id, e.encounter_datetime data_parto from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6332 and value_coded=1065 and e.encounter_type=6 and e.encounter_datetime between :startDate and :endDate and e.location_id=:location
            union 
            Select p.patient_id, obsART.value_datetime data_parto from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            inner join obs obsART on e.encounter_id=obsART.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=6332 and o.value_coded=1065 and e.encounter_type=53 and e.location_id=:location and 
            obsART.value_datetime between :startDate and :endDate and obsART.concept_id=1190 and obsART.voided=0 
            union 
            Select p.patient_id, e.encounter_datetime data_parto from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6332 and 
            e.encounter_type in (5,6) and e.encounter_datetime between :startDate and :endDate and e.location_id=:location
            union 
            select pg.patient_id,ps.start_date data_parto from patient p 
            inner join patient_program pg on p.patient_id=pg.patient_id 
            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
            where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=8 and ps.state=27  and ps.start_date between :startDate and :endDate and location_id=:location
            ) lactante_real 
            group by lactante_real.patient_id
            )lactante_real on lactante_real.patient_id=pe.person_id 
            where (lactante_real.data_parto is not null or gravida_real.data_gravida is not null ) and pe.gender='F'
            group by pe.person_id
            )final