        select obito.patient_id from ( 
            select patient_id,max(data_obito) data_obito from
            ( 
            select maxEstado.patient_id,maxEstado.data_obito from 
            ( 
            select pg.patient_id,max(ps.start_date) data_obito from patient p 
            inner join patient_program pg on p.patient_id=pg.patient_id 
            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
            pg.program_id=2 and ps.start_date<=CURDATE()
             and pg.location_id=:location 
            group by p.patient_id  
            ) maxEstado 
            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state=10 
            union 
            select p.patient_id,max(o.obs_datetime) data_obito from	patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on o.encounter_id=e.encounter_id 
            where e.voided=0 and p.voided=0 and o.obs_datetime<=CURDATE()
            and o.voided=0 and o.concept_id=6272 and o.value_coded=1366 and e.encounter_type=53 
            and  e.location_id=:location 
            group by p.patient_id 
            union  
            select p.patient_id,max(e.encounter_datetime) data_obito from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
            and e.encounter_datetime<=CURDATE()
            and o.voided=0 and o.concept_id=6273 and o.value_coded=1366 and e.encounter_type=6 
            and  e.location_id=:location 
            group by p.patient_id 
            union  
            Select person_id,death_date from person p where p.dead=1 
            and p.death_date<=CURDATE()
            )transferido 
            group by patient_id 
            ) obito 
            inner join 
            ( 
            select patient_id,max(encounter_datetime) encounter_datetime from
            ( 
            select p.patient_id,max(e.encounter_datetime) encounter_datetime from patient p 
            inner join encounter e on e.patient_id=p.patient_id 
            where p.voided=0 and e.voided=0 and e.encounter_datetime<=CURDATE()
            and e.location_id=:location and e.encounter_type in (18,6,9) 
            group by p.patient_id 
            union 
            select p.patient_id,max(value_datetime) encounter_datetime from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and 
            o.concept_id=23866 and o.value_datetime is not null and o.value_datetime<=CURDATE()
            and e.location_id=:location 
            group by p.patient_id  
            ) consultaLev 
            group by patient_id 
            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
            where consultaOuARV.encounter_datetime<=obito.data_obito 
            and obito.data_obito <=CURDATE();
