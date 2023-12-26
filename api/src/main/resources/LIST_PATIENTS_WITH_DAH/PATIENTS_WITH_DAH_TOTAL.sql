select inicioDAH.patient_id
     from ( 
     select inicioDAH.patient_id from( 
    select p.patient_id from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    where e.voided=0 and p.voided=0 and  e.encounter_type =90 
    and e.encounter_datetime between :startDate and :endDate and e.location_id=:location 
    union 
       select distinct max_cd4.patient_id  from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)>=5 and o.concept_id = 1695 and o.value_numeric<200 and o.location_id=:location 
        union 
       select distinct max_cd4.patient_id  from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)>=1 and timestampdiff(year,per.birthdate,:endDate)<5 and o.concept_id = 1695 and o.value_numeric<500 and o.location_id=:location 
            union 
       select distinct max_cd4.patient_id  from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)<1 and o.concept_id = 1695 and o.value_numeric<750 and o.location_id=:location 
    union 
    select patient_id from ( 
    select p.patient_id,min(e.encounter_datetime) encounter_datetime from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.encounter_type = 6 and e.voided=0 and o.voided=0 and p.voided=0 and o.concept_id=1406 
    and o.value_coded in (5018, 5945, 42, 3, 43, 60, 126, 6783, 5334, 14656, 7180, 6990, 5344, 5340, 1294, 5042, 507, 1570, 60)and e.location_id=:location 
    and o.obs_datetime between :startDate and :endDate 
    group by p.patient_id 
    ) estadio 
    ) inicioDAH 
    left join( 
        select p.patient_id from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    where e.voided=0 and p.voided=0 and  e.encounter_type =90 
    and e.encounter_datetime < :startDate and e.location_id=:location 
    union 
    select transferidopara.patient_id  from ( 
    select patient_id,max(data_transferidopara) data_transferidopara from ( 
    select maxEstado.patient_id,maxEstado.data_transferidopara from ( 
    select pg.patient_id,max(ps.start_date) data_transferidopara 
    from patient p 
    inner join patient_program pg on p.patient_id=pg.patient_id 
    inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
    where pg.voided=0 and ps.voided=0 and p.voided=0 and 
    pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location group by p.patient_id 
    ) maxEstado 
    inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
    inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
    where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
    ps2.start_date=maxEstado.data_transferidopara and pg2.location_id=:location and ps2.state=7 
    union 
    select p.patient_id,max(o.obs_datetime) data_transferidopara from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 and o.value_coded=1706 and e.encounter_type=53 
    and  e.location_id=:location group by p.patient_id 
    union 
    select p.patient_id,max(e.encounter_datetime) data_transferidopara from  patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and 
    o.voided=0 and o.concept_id=6273 and o.value_coded=1706 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id 
    ) transferido group by patient_id 
    ) transferidopara 
    inner join( 
    select patient_id,max(encounter_datetime) encounter_datetime from 
    ( 
    select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
    inner join encounter e on e.patient_id=p.patient_id where  p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location and e.encounter_type in (18,6) 
    group by p.patient_id 
    ) consultaLev group by patient_id 
    ) consultaOuARV on transferidopara.patient_id=consultaOuARV.patient_id 
    where consultaOuARV.encounter_datetime<=transferidopara.data_transferidopara 
    ) transferidoPara on transferidoPara.patient_id = inicioDAH.patient_id 
    where transferidoPara.patient_id is null 
    )inicioDAH 
           inner join person p on p.person_id=inicioDAH.patient_id 
            left join 
            (   select pad1.* 
                from person_address pad1 
                inner join 
                ( 
                    select person_id,min(person_address_id) id 
                    from person_address 
                    where voided=0 
                    group by person_id 
                ) pad2 
                where pad1.person_id=pad2.person_id and pad1.person_address_id=pad2.id 
            ) pad3 on pad3.person_id=inicioDAH.patient_id 
            left join 
            (   select pn1.* 
                from person_name pn1 
                inner join 
                ( 
                    select person_id,min(person_name_id) id 
                    from person_name 
                    where voided=0 
                    group by person_id 
                ) pn2 
                where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id 
            ) pn on pn.person_id=inicioDAH.patient_id 
            left join 
            (   select pid1.* 
                from patient_identifier pid1 
                inner join 
                ( 
                    select patient_id,min(patient_identifier_id) id 
                    from patient_identifier 
                    where voided=0 and identifier_type = 2 
                    group by patient_id 
                ) pid2 
                where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id 
            ) pid on pid.patient_id=inicioDAH.patient_id 
            left join person_attribute pat on pat.person_id=inicioDAH.patient_id and pat.person_attribute_type_id=9 and pat.value is not null and pat.value <> '' and pat.voided=0 
            left join 
            ( 
        select patient_id, min(data_inicio) data_inicio from( 
         SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location GROUP BY p.patient_id 
        union 
         Select p.patient_id,min(value_datetime) data_inicio from patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on e.encounter_id=o.encounter_id 
        where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null 
        and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id 
        ) inicioTARV group by patient_id 
        ) inicioTarv ON inicioTarv.patient_id = inicioDAH.patient_id -- Column G 
         left join 
            ( 
         SELECT e.patient_id, max(e.encounter_datetime) AS data_levantamento  FROM patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location GROUP BY p.patient_id 
        ) ultimoLevantamentoTarv ON ultimoLevantamentoTarv.patient_id = inicioDAH.patient_id -- Column H 
        left join ( 
         select distinct min_estado.patient_id, min_estado.data_estado dataTransferido from ( 
             select  pg.patient_id, 
                     min(ps.start_date) data_estado, pg.program_id, ps.state
             from    patient p 
                 inner join person pe on pe.person_id = p.patient_id 
                 inner join patient_program pg on p.patient_id = pg.patient_id 
                 inner join patient_state ps on pg.patient_program_id = ps.patient_program_id 
             where pg.voided=0 and ps.voided=0 and p.voided=0 and pe.voided = 0 and pg.program_id in (1,2) 
                 and ps.start_date<= :endDate and pg.location_id =:location group by pg.patient_id 
         ) min_estado 
             inner join patient_program pp on pp.patient_id = min_estado.patient_id 
             inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = min_estado.data_estado 
         where (pp.program_id=1 and ps.state=28) or (pp.program_id=2 and ps.state=29) and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location 
        union 
        SELECT p.patient_id,MIN(obsData.value_datetime) AS initialDate  FROM patient p 
        INNER JOIN encounter e  ON e.patient_id=p.patient_id 
        INNER JOIN obs o on o.encounter_id=e.encounter_id 
        INNER JOIN obs obsPretarv on e.encounter_id=obsPretarv.encounter_id 
        INNER JOIN obs obsData on e.encounter_id=obsData.encounter_id 
        WHERE e.voided=0 AND o.voided=0  AND e.encounter_type=53  AND obsPretarv.voided=0  AND obsPretarv.concept_id=6300  AND obsPretarv.value_coded in(6275,6276) AND obsData.concept_id=23891 
        AND obsData.voided=0 AND e.location_id=:location   AND o.concept_id=1369  AND o.value_coded=1065  AND obsData.value_datetime<= :endDate GROUP BY p.patient_id 
        ) transferredIn on transferredIn.patient_id = inicioDAH.patient_id 
        left join 
        ( 
        select patient_id, max(estadoPermanencia.data_proximo_levantamento) data_estado_permanencia, RespostaEstadoPermanencia from ( 
        select abandono.patient_id,abandono.data_proximo_levantamento, 1 as RespostaEstadoPermanencia from ( 
        select patient_id, data_proximo_levantamento from ( 
        select patient_id, max(data_proximo_levantamento) data_proximo_levantamento from ( 
        select p.patient_id,max(o.value_datetime) data_levantamento, date_add(max(o.value_datetime), INTERVAL 30 day)  data_proximo_levantamento  from patient p 
        inner join encounter e on p.patient_id = e.patient_id 
        inner join obs o on o.encounter_id = e.encounter_id 
        inner join obs obsLevantou on obsLevantou.encounter_id=e.encounter_id 
        where  e.voided = 0 and p.voided = 0 and o.value_datetime <=:endDate and o.voided = 0 
        and obsLevantou.voided=0 and obsLevantou.concept_id=23865 and obsLevantou.value_coded = 1065 
        and o.concept_id = 23866 and e.encounter_type=52 and e.location_id=:location 
        group by p.patient_id 
        union 
        select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from ( 
        select p.patient_id,max(e.encounter_datetime) as data_levantamento from patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        where encounter_type=18 and e.encounter_datetime <=:endDate and e.location_id=:location and e.voided=0 and p.voided=0 
        group by p.patient_id 
        )fila 
        inner join obs obs_fila on obs_fila.person_id=fila.patient_id 
        where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime 
        ) abandono group by patient_id 
        )abandono where DATE_ADD(abandono.data_proximo_levantamento, INTERVAL 60 DAY) < :endDate 
        ) abandono 
            left join( 
        select p.patient_id from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    where e.voided=0 and p.voided=0 and  e.encounter_type =90 
    and e.encounter_datetime < :startDate and e.location_id=:location 
    union 
    select transferidopara.patient_id  from ( 
    select patient_id,max(data_transferidopara) data_transferidopara from ( 
    select maxEstado.patient_id,maxEstado.data_transferidopara from ( 
    select pg.patient_id,max(ps.start_date) data_transferidopara 
    from patient p 
    inner join patient_program pg on p.patient_id=pg.patient_id 
    inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
    where pg.voided=0 and ps.voided=0 and p.voided=0 and 
    pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location group by p.patient_id 
    ) maxEstado 
    inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
    inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
    where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
    ps2.start_date=maxEstado.data_transferidopara and pg2.location_id=:location and ps2.state=7 
    union 
    select p.patient_id,max(o.obs_datetime) data_transferidopara from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 and o.value_coded=1706 and e.encounter_type=53 
    and  e.location_id=:location group by p.patient_id 
    union 
    select p.patient_id,max(e.encounter_datetime) data_transferidopara from  patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and 
    o.voided=0 and o.concept_id=6273 and o.value_coded=1706 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id 
    ) transferido group by patient_id 
    ) transferidopara 
    inner join( 
    select patient_id,max(encounter_datetime) encounter_datetime from 
    ( 
    select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
    inner join encounter e on e.patient_id=p.patient_id where  p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location and e.encounter_type in (18,6) 
    group by p.patient_id 
    ) consultaLev group by patient_id 
    ) consultaOuARV on transferidopara.patient_id=consultaOuARV.patient_id 
    where consultaOuARV.encounter_datetime<=transferidopara.data_transferidopara 
    ) transferidoPara on transferidoPara.patient_id = abandono.patient_id 
    left join( 
        select suspenso1.patient_id, consultaOuARV.encounter_datetime, data_suspencao  from ( 
        select patient_id,max(data_suspencao) data_suspencao from ( 
        select maxEstado.patient_id,maxEstado.data_suspencao from( 
        select pg.patient_id,max(ps.start_date) data_suspencao from patient p 
        inner join patient_program pg on p.patient_id=pg.patient_id 
        inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
        where pg.voided=0 and ps.voided=0 and p.voided=0 and 
        pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location 
        group by p.patient_id )maxEstado 
        inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
        inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
        ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
        union 
        select p.patient_id,max(o.obs_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 
        and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id 
        union 
        select  p.patient_id,max(e.encounter_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=6273 
        and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id ) suspenso group by patient_id) suspenso1 
        inner join 
        ( 
        select patient_id,max(encounter_datetime) encounter_datetime from 
        ( 
        select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
        inner join encounter e on e.patient_id=p.patient_id where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and 
        e.location_id=:location and e.encounter_type=18 group by p.patient_id 
        ) consultaLev group by patient_id) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
        where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao 
    )suspenso on suspenso.patient_id = abandono.patient_id 
    left join ( 
      select obito.patient_id from ( 
 select patient_id, max(data_obito) data_obito from ( 
 select maxEstado.patient_id,maxEstado.data_obito from ( 
 select pg.patient_id, max(ps.start_date) data_obito from patient p 
 inner join patient_program pg on p.patient_id = pg.patient_id 
 inner join patient_state ps on pg.patient_program_id = ps.patient_program_id 
 where pg.voided = 0 and ps.voided = 0 and p.voided = 0 and 
 pg.program_id = 2 and DATE(ps.start_date) <= :endDate and pg.location_id =:location 
 group by p.patient_id ) maxEstado 
 inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id 
 inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id 
 where pg2.voided = 0 and ps2.voided = 0 and pg2.program_id = 2 and 
 ps2.start_date = maxEstado.data_obito and pg2.location_id =:location and ps2.state = 10 
 UNION 
 select p.patient_id, max(o.obs_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id 
 where e.voided = 0 and p.voided = 0 and DATE(o.obs_datetime) <= :endDate and 
 o.voided = 0 and o.concept_id = 6272 and o.value_coded = 1366 and e.encounter_type = 53 and  e.location_id =:location 
 group by p.patient_id 
 union 
 select p.patient_id, max(e.encounter_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id where e.voided = 0 and p.voided = 0 and DATE(e.encounter_datetime) <= :endDate 
 and o.voided = 0 and o.concept_id = 6273 and o.value_coded = 1366 and e.encounter_type = 6 and  e.location_id =:location 
 group by p.patient_id 
 union 
 Select person_id, death_date from person p where p.dead = 1 and DATE(p.death_date) <= :endDate) transferido 
 group by patient_id) obito 
 inner join 
( 
 select patient_id, max(encounter_datetime) encounter_datetime from ( 
 select p.patient_id, max(e.encounter_datetime) encounter_datetime from patient p 
 inner join encounter e on e.patient_id = p.patient_id 
 where p.voided = 0 and e.voided = 0 and e.encounter_type in (18,6,9)  and DATE(e.encounter_datetime) <= :endDate and e.location_id =:location 
 group by p.patient_id 
) consultaLev 
 group by patient_id 
) 
 consultaOuARV on obito.patient_id = consultaOuARV.patient_id 
 where consultaOuARV.encounter_datetime <= obito.data_obito ) 
 obito on obito.patient_id = abandono.patient_id 
    where transferidoPara.patient_id is null and suspenso.patient_id is null and obito.patient_id is null 
    union 
      select obito.patient_id,obito.data_obito, 2 as RespostaEstadoPermanencia from ( 
 select patient_id, max(data_obito) data_obito from ( 
 select maxEstado.patient_id,maxEstado.data_obito from ( 
 select pg.patient_id, max(ps.start_date) data_obito from patient p 
 inner join patient_program pg on p.patient_id = pg.patient_id 
 inner join patient_state ps on pg.patient_program_id = ps.patient_program_id 
 where pg.voided = 0 and ps.voided = 0 and p.voided = 0 and 
 pg.program_id = 2 and DATE(ps.start_date) <= :endDate and pg.location_id =:location 
 group by p.patient_id ) maxEstado 
 inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id 
 inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id 
 where pg2.voided = 0 and ps2.voided = 0 and pg2.program_id = 2 and 
 ps2.start_date = maxEstado.data_obito and pg2.location_id =:location and ps2.state = 10 
 UNION 
 select p.patient_id, max(o.obs_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id 
 where e.voided = 0 and p.voided = 0 and DATE(o.obs_datetime) <= :endDate and 
 o.voided = 0 and o.concept_id = 6272 and o.value_coded = 1366 and e.encounter_type = 53 and  e.location_id =:location 
 group by p.patient_id 
 union 
 select p.patient_id, max(e.encounter_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id where e.voided = 0 and p.voided = 0 and DATE(e.encounter_datetime) <= :endDate 
 and o.voided = 0 and o.concept_id = 6273 and o.value_coded = 1366 and e.encounter_type = 6 and  e.location_id =:location 
 group by p.patient_id 
 union 
 Select person_id, death_date from person p where p.dead = 1 and DATE(p.death_date) <= :endDate) transferido 
 group by patient_id) obito 
 inner join 
( 
 select patient_id, max(encounter_datetime) encounter_datetime from ( 
 select p.patient_id, max(e.encounter_datetime) encounter_datetime from patient p 
 inner join encounter e on e.patient_id = p.patient_id 
 where p.voided = 0 and e.voided = 0 and e.encounter_type in (18,6,9)  and DATE(e.encounter_datetime) <= :endDate and e.location_id =:location 
 group by p.patient_id 
) consultaLev 
 group by patient_id 
) 
 consultaOuARV on obito.patient_id = consultaOuARV.patient_id 
 where consultaOuARV.encounter_datetime <= obito.data_obito 
 union 
        select suspenso1.patient_id,suspenso1.data_suspencao, 3 as RespostaEstadoPermanencia  from ( 
        select patient_id,max(data_suspencao) data_suspencao from ( 
        select maxEstado.patient_id,maxEstado.data_suspencao from( 
        select pg.patient_id,max(ps.start_date) data_suspencao from patient p 
        inner join patient_program pg on p.patient_id=pg.patient_id 
        inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
        where pg.voided=0 and ps.voided=0 and p.voided=0 and 
        pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location 
        group by p.patient_id )maxEstado 
        inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
        inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
        ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
        union 
        select p.patient_id,max(o.obs_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 
        and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id 
        union 
        select  p.patient_id,max(e.encounter_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=6273 
        and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id ) suspenso group by patient_id) suspenso1 
        inner join 
        ( 
        select patient_id,max(encounter_datetime) encounter_datetime from 
        ( 
        select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
        inner join encounter e on e.patient_id=p.patient_id where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and 
        e.location_id=:location and e.encounter_type=18 group by p.patient_id 
        ) consultaLev group by patient_id) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
        where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao 
        union 
                select activo.patient_id,activo.data_inicio, 4 as RespostaEstadoPermanencia from ( 
                select patient_id, min(data_inicio) data_inicio from( 
         SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location GROUP BY p.patient_id 
        union 
         Select p.patient_id,min(value_datetime) data_inicio from patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on e.encounter_id=o.encounter_id 
        where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null 
        and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id 
        ) inicioTARV group by patient_id 
        ) activo left join ( 
            select patient_id,max(data_transferidopara) data_transferidopara from ( 
    select maxEstado.patient_id,maxEstado.data_transferidopara from ( 
    select pg.patient_id,max(ps.start_date) data_transferidopara 
    from patient p 
    inner join patient_program pg on p.patient_id=pg.patient_id 
    inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
    where pg.voided=0 and ps.voided=0 and p.voided=0 and 
    pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location group by p.patient_id 
    ) maxEstado 
    inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
    inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
    where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
    ps2.start_date=maxEstado.data_transferidopara and pg2.location_id=:location and ps2.state=7 
    union 
    select p.patient_id,max(o.obs_datetime) data_transferidopara from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 and o.value_coded=1706 and e.encounter_type=53 
    and  e.location_id=:location group by p.patient_id 
    union 
    select p.patient_id,max(e.encounter_datetime) data_transferidopara from  patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and 
    o.voided=0 and o.concept_id=6273 and o.value_coded=1706 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id 
    ) transferido group by patient_id 
        )transferidoPara on transferidoPara.patient_id = activo.patient_id 
        left join( 
                select suspenso1.patient_id, consultaOuARV.encounter_datetime, data_suspencao  from ( 
        select patient_id,max(data_suspencao) data_suspencao from ( 
        select maxEstado.patient_id,maxEstado.data_suspencao from( 
        select pg.patient_id,max(ps.start_date) data_suspencao from patient p 
        inner join patient_program pg on p.patient_id=pg.patient_id 
        inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
        where pg.voided=0 and ps.voided=0 and p.voided=0 and 
        pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location 
        group by p.patient_id )maxEstado 
        inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
        inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
        ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
        union 
        select p.patient_id,max(o.obs_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 
        and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id 
        union 
        select  p.patient_id,max(e.encounter_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=6273 
        and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id ) suspenso group by patient_id) suspenso1 
        inner join 
        ( 
        select patient_id,max(encounter_datetime) encounter_datetime from 
        ( 
        select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
        inner join encounter e on e.patient_id=p.patient_id where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and 
        e.location_id=:location and e.encounter_type=18 group by p.patient_id 
        ) consultaLev group by patient_id) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
        where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao 
        )suspenso on suspenso.patient_id = activo.patient_id 
        left join ( 
                      select obito.patient_id from ( 
            select patient_id, max(data_obito) data_obito from ( 
            select maxEstado.patient_id,maxEstado.data_obito from ( 
            select pg.patient_id, max(ps.start_date) data_obito from patient p 
            inner join patient_program pg on p.patient_id = pg.patient_id 
            inner join patient_state ps on pg.patient_program_id = ps.patient_program_id 
            where pg.voided = 0 and ps.voided = 0 and p.voided = 0 and 
            pg.program_id = 2 and DATE(ps.start_date) <= :endDate and pg.location_id =:location 
            group by p.patient_id ) maxEstado 
            inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id 
            inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id 
            where pg2.voided = 0 and ps2.voided = 0 and pg2.program_id = 2 and 
            ps2.start_date = maxEstado.data_obito and pg2.location_id =:location and ps2.state = 10 
            UNION 
            select p.patient_id, max(o.obs_datetime) data_obito from patient p 
            inner join encounter e on p.patient_id = e.patient_id 
            inner join obs o on o.encounter_id = e.encounter_id 
            where e.voided = 0 and p.voided = 0 and DATE(o.obs_datetime) <= :endDate and 
            o.voided = 0 and o.concept_id = 6272 and o.value_coded = 1366 and e.encounter_type = 53 and  e.location_id =:location 
            group by p.patient_id 
            union 
            select p.patient_id, max(e.encounter_datetime) data_obito from patient p 
            inner join encounter e on p.patient_id = e.patient_id 
            inner join obs o on o.encounter_id = e.encounter_id where e.voided = 0 and p.voided = 0 and DATE(e.encounter_datetime) <= :endDate 
            and o.voided = 0 and o.concept_id = 6273 and o.value_coded = 1366 and e.encounter_type = 6 and  e.location_id =:location 
            group by p.patient_id 
            union 
            Select person_id, death_date from person p where p.dead = 1 and DATE(p.death_date) <= :endDate) transferido 
            group by patient_id) obito 
            inner join 
            ( 
            select patient_id, max(encounter_datetime) encounter_datetime from ( 
            select p.patient_id, max(e.encounter_datetime) encounter_datetime from patient p 
            inner join encounter e on e.patient_id = p.patient_id 
            where p.voided = 0 and e.voided = 0 and e.encounter_type in (18,6,9)  and DATE(e.encounter_datetime) <= :endDate and e.location_id =:location 
            group by p.patient_id 
            ) consultaLev 
            group by patient_id 
            ) 
            consultaOuARV on obito.patient_id = consultaOuARV.patient_id 
            where consultaOuARV.encounter_datetime <= obito.data_obito 
        )obito on obito.patient_id = activo.patient_id 
        left join ( 
                select abandono.patient_id, 1 as RespostaEstadoPermanencia from ( 
        select patient_id, data_proximo_levantamento from ( 
        select patient_id, max(data_proximo_levantamento) data_proximo_levantamento from ( 
        select p.patient_id,max(o.value_datetime) data_levantamento, date_add(max(o.value_datetime), INTERVAL 30 day)  data_proximo_levantamento  from patient p 
        inner join encounter e on p.patient_id = e.patient_id 
        inner join obs o on o.encounter_id = e.encounter_id 
        inner join obs obsLevantou on obsLevantou.encounter_id=e.encounter_id 
        where  e.voided = 0 and p.voided = 0 and o.value_datetime <=:endDate and o.voided = 0 
        and obsLevantou.voided=0 and obsLevantou.concept_id=23865 and obsLevantou.value_coded = 1065 
        and o.concept_id = 23866 and e.encounter_type=52 and e.location_id=:location 
        group by p.patient_id 
        union 
        select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from ( 
        select p.patient_id,max(e.encounter_datetime) as data_levantamento from patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        where encounter_type=18 and e.encounter_datetime <=:endDate and e.location_id=:location and e.voided=0 and p.voided=0 
        group by p.patient_id 
        )fila 
        inner join obs obs_fila on obs_fila.person_id=fila.patient_id 
        where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime 
        ) abandono group by patient_id 
        )abandono where DATE_ADD(abandono.data_proximo_levantamento, INTERVAL 60 DAY) < :endDate 
        ) abandono 
            left join( 
        select p.patient_id from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    where e.voided=0 and p.voided=0 and  e.encounter_type =90 
    and e.encounter_datetime < :startDate and e.location_id=:location 
    union 
    select transferidopara.patient_id  from ( 
    select patient_id,max(data_transferidopara) data_transferidopara from ( 
    select maxEstado.patient_id,maxEstado.data_transferidopara from ( 
    select pg.patient_id,max(ps.start_date) data_transferidopara 
    from patient p 
    inner join patient_program pg on p.patient_id=pg.patient_id 
    inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
    where pg.voided=0 and ps.voided=0 and p.voided=0 and 
    pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location group by p.patient_id 
    ) maxEstado 
    inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
    inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
    where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
    ps2.start_date=maxEstado.data_transferidopara and pg2.location_id=:location and ps2.state=7 
    union 
    select p.patient_id,max(o.obs_datetime) data_transferidopara from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 and o.value_coded=1706 and e.encounter_type=53 
    and  e.location_id=:location group by p.patient_id 
    union 
    select p.patient_id,max(e.encounter_datetime) data_transferidopara from  patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and 
    o.voided=0 and o.concept_id=6273 and o.value_coded=1706 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id 
    ) transferido group by patient_id 
    ) transferidopara 
    inner join( 
    select patient_id,max(encounter_datetime) encounter_datetime from 
    ( 
    select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
    inner join encounter e on e.patient_id=p.patient_id where  p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location and e.encounter_type in (18,6) 
    group by p.patient_id 
    ) consultaLev group by patient_id 
    ) consultaOuARV on transferidopara.patient_id=consultaOuARV.patient_id 
    where consultaOuARV.encounter_datetime<=transferidopara.data_transferidopara 
    ) transferidoPara on transferidoPara.patient_id = abandono.patient_id 
    left join( 
        select suspenso1.patient_id, consultaOuARV.encounter_datetime, data_suspencao  from ( 
        select patient_id,max(data_suspencao) data_suspencao from ( 
        select maxEstado.patient_id,maxEstado.data_suspencao from( 
        select pg.patient_id,max(ps.start_date) data_suspencao from patient p 
        inner join patient_program pg on p.patient_id=pg.patient_id 
        inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
        where pg.voided=0 and ps.voided=0 and p.voided=0 and 
        pg.program_id=2 and ps.start_date<=:endDate and pg.location_id=:location 
        group by p.patient_id )maxEstado 
        inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
        inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
        ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
        union 
        select p.patient_id,max(o.obs_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 
        and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id 
        union 
        select  p.patient_id,max(e.encounter_datetime) data_suspencao from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=6273 
        and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id ) suspenso group by patient_id) suspenso1 
        inner join 
        ( 
        select patient_id,max(encounter_datetime) encounter_datetime from 
        ( 
        select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p 
        inner join encounter e on e.patient_id=p.patient_id where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and 
        e.location_id=:location and e.encounter_type=18 group by p.patient_id 
        ) consultaLev group by patient_id) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
        where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao 
    )suspenso on suspenso.patient_id = abandono.patient_id 
    left join ( 
      select obito.patient_id from ( 
 select patient_id, max(data_obito) data_obito from ( 
 select maxEstado.patient_id,maxEstado.data_obito from ( 
 select pg.patient_id, max(ps.start_date) data_obito from patient p 
 inner join patient_program pg on p.patient_id = pg.patient_id 
 inner join patient_state ps on pg.patient_program_id = ps.patient_program_id 
 where pg.voided = 0 and ps.voided = 0 and p.voided = 0 and 
 pg.program_id = 2 and DATE(ps.start_date) <= :endDate and pg.location_id =:location 
 group by p.patient_id ) maxEstado 
 inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id 
 inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id 
 where pg2.voided = 0 and ps2.voided = 0 and pg2.program_id = 2 and 
 ps2.start_date = maxEstado.data_obito and pg2.location_id =:location and ps2.state = 10 
 UNION 
 select p.patient_id, max(o.obs_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id 
 where e.voided = 0 and p.voided = 0 and DATE(o.obs_datetime) <= :endDate and 
 o.voided = 0 and o.concept_id = 6272 and o.value_coded = 1366 and e.encounter_type = 53 and  e.location_id =:location 
 group by p.patient_id 
 union 
 select p.patient_id, max(e.encounter_datetime) data_obito from patient p 
 inner join encounter e on p.patient_id = e.patient_id 
 inner join obs o on o.encounter_id = e.encounter_id where e.voided = 0 and p.voided = 0 and DATE(e.encounter_datetime) <= :endDate 
 and o.voided = 0 and o.concept_id = 6273 and o.value_coded = 1366 and e.encounter_type = 6 and  e.location_id =:location 
 group by p.patient_id 
 union 
 Select person_id, death_date from person p where p.dead = 1 and DATE(p.death_date) <= :endDate) transferido 
 group by patient_id) obito 
 inner join 
( 
 select patient_id, max(encounter_datetime) encounter_datetime from ( 
 select p.patient_id, max(e.encounter_datetime) encounter_datetime from patient p 
 inner join encounter e on e.patient_id = p.patient_id 
 where p.voided = 0 and e.voided = 0 and e.encounter_type in (18,6,9)  and DATE(e.encounter_datetime) <= :endDate and e.location_id =:location 
 group by p.patient_id 
) consultaLev 
 group by patient_id 
) 
 consultaOuARV on obito.patient_id = consultaOuARV.patient_id 
 where consultaOuARV.encounter_datetime <= obito.data_obito ) 
 obito on obito.patient_id = abandono.patient_id 
    where transferidoPara.patient_id is null and suspenso.patient_id is null and obito.patient_id is null 
        )abandono on abandono.patient_id = activo.patient_id 
        where transferidoPara.patient_id is null or suspenso.patient_id is null or obito.patient_id is null or abandono.patient_id is null 
        ) estadoPermanencia group by estadoPermanencia.patient_id 
        )estadoPermanencia on estadoPermanencia.patient_id = inicioDAH.patient_id 
    left join( 
            select  p.patient_id,max(e.encounter_datetime) data_suspencao, o.value_coded from  patient p 
        inner join encounter e on p.patient_id=e.patient_id 
        inner join obs o on o.encounter_id=e.encounter_id 
        where  e.voided=0 and p.voided=0 and e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=1255 
        and o.value_coded in (1256, 1705, 6276, 6275) and e.encounter_type=90 and  e.location_id=:location group by p.patient_id 
    )situacaoTARVnoDAH on situacaoTARVnoDAH.patient_id = inicioDAH.patient_id 
    left join ( 
    select p.patient_id, max(e.encounter_datetime) encounter_datetime from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    where e.voided=0 and p.voided=0 and  e.encounter_type =90 
    and e.encounter_datetime between :startDate and :endDate and e.location_id=:location 
    group by patient_id 
    )seguimentoDAH on seguimentoDAH.patient_id = inicioDAH.patient_id 
    left join ( 
    select patient_id,max(max_data_cd4) max_data_cd4, value_numeric from ( 
           select distinct max_cd4.patient_id, max_data_cd4,o.value_numeric from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)>=5 and o.concept_id = 1695 and o.value_numeric<200 and o.location_id=:location 
        union 
       select distinct max_cd4.patient_id,max_data_cd4,o.value_numeric from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)>=1 and timestampdiff(year,per.birthdate,:endDate)<5 and o.concept_id = 1695 and o.value_numeric<500 and o.location_id=:location 
            union 
       select distinct max_cd4.patient_id,max_data_cd4,o.value_numeric from ( 
    Select p.patient_id,max(o.obs_datetime) max_data_cd4  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime between :startDate and :endDate and e.location_id=:location 
    group by p.patient_id 
    )max_cd4 
    inner join person per on per.person_id=max_cd4.patient_id 
    inner join obs o on o.person_id=max_cd4.patient_id and max_cd4.max_data_cd4=o.obs_datetime and o.voided=0 and 
    per.voided=0 and timestampdiff(year,per.birthdate,:endDate)<1 and o.concept_id = 1695 and o.value_numeric<750 and o.location_id=:location 
    ) cd4 group by patient_id 
    ) cd4EligibilidadeMDSDAH on cd4EligibilidadeMDSDAH.patient_id = inicioDAH.patient_id 
    left join( 
    select patient_id, encounter_datetime, group_concat(motivoEstadioClinico) motivoEstadio, tipoEstadio from ( 
        select estadiamentoClinico.patient_id, encounter_datetime encounter_datetime, case estadiamentoClinico.value_coded 
     when 14656 then 'Caquexia' 
     when 7180 then 'Toxoplasmose' 
     when 6990 then 'Doença pelo HIV resultando encefalopatia' 
     when 5344 then 'Herpes simples> 1 mês ou viisceral' 
     when 5340 then 'Candidíase esofágica' 
     when 1294 then 'Miningite cryptococal' 
     when 5042 then 'Tuberculose extrapulmonar' 
     when 507 then 'Sarcoma de Kaposi (SK)' 
     when 1570 then 'Cancro do colo do útero' 
     when 60 then 'Menigite, NSA' 
     when 5018 then 'Diarreia Crónica' 
     when 5945 then 'Febre' 
     when 42 then 'Tuberculose Pulmonar' 
     when 3 then 'Anemia' 
     when 43 then 'Pneumonia' 
     when 126 then 'Gengivite' 
     when 6783 then 'Estomatite ulcerativa necrotizante' 
     when 5334 then 'Candidíase oral' 
     when 14656 then 'Caquexia' 
     when 7180 then 'Toxoplasmose' 
     when 6990 then 'Doença pelo HIV resultando encefalopatia' 
     when 5344 then 'Herpes simples> 1 mês ou viisceral' 
     when 5340 then 'Candidíase esofágica' 
     when 1294 then 'Miningite cryptococal' 
     when 5042 then 'Tuberculose extrapulmonar' 
     when 507 then 'Sarcoma de Kaposi' 
     when 1570 then 'Cancro do colo do útero' 
     when 60 then 'Menigite, NSA' 
     when 5018 then 'Diarréia Crónica > 1 Mês' 
     when 5945 then 'Febre' 
     when 42 then 'Tuberculose Pulmonar' 
     when 3 then 'Anemia' 
     when 43 then 'Pneumonia' 
     when 126 then 'Gengivite' 
     when 6783 then 'Estomatite ulcerativa necrotizante' 
     when 5334 then 'Candidíase oral' 
     end as motivoEstadioClinico, tipoEstadio from ( 
        select estadio4.patient_id, estadio4.encounter_datetime,o.value_coded, 4 as tipoEstadio from ( 
        select p.patient_id,min(e.encounter_datetime) encounter_datetime from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.encounter_type = 6 and e.voided=0 and o.voided=0 and p.voided=0 and o.concept_id=1406 and e.location_id=:location 
    and o.obs_datetime <= :endDate 
    group by p.patient_id 
    ) estadio4 
    inner join encounter e on e.patient_id = estadio4.patient_id 
    inner join obs o on o.encounter_id = e.encounter_id and o.obs_datetime = estadio4.encounter_datetime 
    where e.voided = 0 and o.voided = 0 and o.value_coded in (14656, 7180, 6990, 5344, 5340, 1294, 5042, 507, 1570, 60) 
    union 
     select estadio3.patient_id, estadio3.encounter_datetime,o.value_coded, 3 as tipoEstadio from ( 
        select p.patient_id,min(e.encounter_datetime) encounter_datetime from patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on o.encounter_id=e.encounter_id 
    where e.encounter_type = 6 and e.voided=0 and o.voided=0 and p.voided=0 and o.concept_id=1406 and e.location_id=:location 
    and o.obs_datetime <= :endDate 
    group by p.patient_id 
    ) estadio3 
    inner join encounter e on e.patient_id = estadio3.patient_id 
    inner join obs o on o.encounter_id = e.encounter_id and o.obs_datetime = estadio3.encounter_datetime 
    where e.voided = 0 and o.voided = 0 and o.value_coded in (5018, 5945, 42, 3, 43, 60, 126, 6783, 5334) 
    )estadiamentoClinico order by estadiamentoClinico.patient_id, estadiamentoClinico.encounter_datetime 
    )estadiamentoClinico group by estadiamentoClinico.patient_id 
    )estadiamentoClinico on estadiamentoClinico.patient_id = inicioDAH.patient_id 
    left join ( 
    select patient_id, max(data_cd4) max_data_cd4, value_numeric from ( 
    Select p.patient_id,o.obs_datetime data_cd4, o.value_numeric  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    order by p.patient_id, o.obs_datetime desc 
    ) maxCD4 group by patient_id 
    ) maxCD4 on maxCD4.patient_id = inicioDAH.patient_id 
    left join( 
     select ultimoCd4.patient_id, max(penultimoCd4.obs_datetime) dataCd4Anterior, penultimoCd4.value_numeric from ( 
    select patient_id, max(data_cd4) max_data_cd4, value_numeric from ( 
    Select p.patient_id,o.obs_datetime data_cd4, o.value_numeric  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    order by p.patient_id, o.obs_datetime desc 
    ) maxCD4 group by patient_id 
    ) ultimoCd4 inner join ( 
        Select p.patient_id,o.obs_datetime , o.value_numeric  From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 1695 and  e.encounter_type in (6,51,13,53,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    )penultimoCd4 on penultimoCd4.patient_id = ultimoCd4.patient_id 
    and date(penultimoCd4.obs_datetime) < ultimoCd4.max_data_cd4 
    group by patient_id 
    )penultimoCd4 on penultimoCd4.patient_id = inicioDAH.patient_id 
    left join ( 
    select patient_id, max(data_carga) data_carga, resultadoCV, comments from (
    select patient_id, data_carga, resultadoCV, comments from (
    select ultima_carga.patient_id,ultima_carga.data_carga, IF(ISNULL(obs.value_numeric), IF(ISNULL(obs.value_coded), 'N/A', 
 case 
                                    obs.value_coded 
                                    when 
                                       1306
                                    then 
                                       'Nivel baixo de detecção' 
                                    when 
                                       1304 
                                    then 
                                       'MA QUALIDADE DA AMOSTRA' 
                                    when 
                                       23814 
                                    then 
                                       'Indetectável' 
                                    when 
                                       23905 
                                    then 
                                       'MENOR QUE 10 COPIAS/ML' 
                                    when 
                                       23906 
                                    then 
                                       'MENOR QUE 20 COPIAS/ML' 
                                    when 
                                       23907 
                                    then 
                                       'MENOR QUE 40 COPIAS/ML' 
                                    when 
                                       23908 
                                    then 
                                       'MENOR QUE 400 COPIAS/ML' 
                                    when 
                                       23904 
                                    then 
                                       'MENOR QUE 839 COPIAS/ML' 
                                    when 
                                       165331 
                                    then 
                                       CONCAT('MENOR QUE', ' ',obs.comments) 
                                    else 
                                       null 
                                 end), obs.value_numeric) AS resultadoCV , obs.comments from ( 
            Select p.patient_id,max(o.obs_datetime) data_carga, e.encounter_type from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53) 
            and  o.concept_id in (856,1305) and  date(o.obs_datetime) <= :endDate and e.location_id=:location group by p.patient_id) ultima_carga 
            inner join obs on obs.person_id=ultima_carga.patient_id and date(obs.obs_datetime)=date(ultima_carga.data_carga) 
            where obs.voided=0 and ((obs.concept_id=856 and obs.value_numeric is not null) 
            or (obs.concept_id=1305 and obs.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and obs.location_id=:location 
            union
                select ultima_carga.patient_id,ultima_carga.data_carga, IF(ISNULL(obs.value_numeric), IF(ISNULL(obs.value_coded), 'N/A', 
 case 
                                    obs.value_coded 
                                    when 
                                       1306
                                    then 
                                       'Nivel de detecção baixo' 
                                    when 
                                       1304 
                                    then 
                                       'MA QUALIDADE DA AMOSTRA' 
                                    when 
                                       23814 
                                    then 
                                       'Indetectável' 
                                    when 
                                       23905 
                                    then 
                                       'MENOR QUE 10 COPIAS/ML' 
                                    when 
                                       23906 
                                    then 
                                       'MENOR QUE 20 COPIAS/ML' 
                                    when 
                                       23907 
                                    then 
                                       'MENOR QUE 40 COPIAS/ML' 
                                    when 
                                       23908 
                                    then 
                                       'MENOR QUE 400 COPIAS/ML' 
                                    when 
                                       23904 
                                    then 
                                       'MENOR QUE 839 COPIAS/ML' 
                                    when 
                                       165331 
                                    then 
                                       CONCAT('MENOR QUE', ' ',obs.comments) 
                                    else 
                                       null 
                                 end), obs.value_numeric) AS resultadoCV , obs.comments from ( 
            Select p.patient_id,max(o.obs_datetime) data_carga, e.encounter_type from patient p 
            inner join encounter e on p.patient_id=e.patient_id 
            inner join obs o on e.encounter_id=o.encounter_id 
            where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type = 51 
            and  o.concept_id in (856,1305) and  date(o.obs_datetime) <= :endDate and e.location_id=:location group by p.patient_id) ultima_carga 
            inner join obs on obs.person_id=ultima_carga.patient_id and date(obs.obs_datetime)=date(ultima_carga.data_carga) 
            where obs.voided=0 and ((obs.concept_id=856 and obs.value_numeric is not null) 
            or (obs.concept_id=1305 and obs.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and obs.location_id=:location 
            ) ultimaCV order by ultimaCV.data_carga desc
            ) ultimaCV group by ultimaCV.patient_id
    )ultimaCV on ultimaCV.patient_id = inicioDAH.patient_id 
    left join( 
    select patient_id,max(penultimaDataCarga) penultimaDataCarga, resultadoCV from ( 
    select patient_id, penultimaDataCarga, resultadoCV from (
    select * from (
    select e.patient_id, date(o.obs_datetime) penultimaDataCarga,IF(ISNULL(o.value_numeric), IF(ISNULL(o.value_coded), 'N/A', 
 case 
                                    o.value_coded 
                                    when 
                                       1306 
                                    then 
                                       'Nivel baixo de detecção' 
                                    when 
                                       1304 
                                    then 
                                       'MA QUALIDADE DA AMOSTRA' 
                                    when 
                                       23814 
                                    then 
                                       'Indetectável' 
                                    when 
                                       23905 
                                    then 
                                       'MENOR QUE 10 COPIAS/ML' 
                                    when 
                                       23906 
                                    then 
                                       'MENOR QUE 20 COPIAS/ML' 
                                    when 
                                       23907 
                                    then 
                                       'MENOR QUE 40 COPIAS/ML' 
                                    when 
                                       23908 
                                    then 
                                       'MENOR QUE 400 COPIAS/ML' 
                                    when 
                                       23904 
                                    then 
                                       'MENOR QUE 839 COPIAS/ML' 
                                    when 
                                       165331 
                                    then 
                                        CONCAT('MENOR QUE', ' ',o.comments) 
                                 end), o.value_numeric) AS resultadoCV, data_carga 
                                 from (select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga,obs.concept_id,obs.value_coded from ( 
                Select p.patient_id,max(o.obs_datetime) data_carga from patient p 
                inner join encounter e on p.patient_id=e.patient_id 
                inner join obs o on e.encounter_id=o.encounter_id 
                where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) 
                and  o.concept_id in (856,1305) and  date(o.obs_datetime) <= :endDate and e.location_id=:location group by p.patient_id) ultima_carga 
                inner join obs on obs.person_id=ultima_carga.patient_id and date(obs.obs_datetime)=date(ultima_carga.data_carga) 
                where obs.voided=0 and ((obs.concept_id=856 and obs.value_numeric is not null) 
                or (obs.concept_id=1305 and obs.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and obs.location_id=:location 
    ) ultimaCV inner join encounter e on e.patient_id = ultimaCV.patient_id 
    inner join obs o on o.encounter_id = e.encounter_id 
    where  ((o.concept_id=856 and o.value_numeric is not null) 
     or (o.concept_id=1305 and o.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and e.location_id =:location and o.voided = 0 and e.voided = 0 and e.encounter_type in (13,6,9,53) 
    and date(o.obs_datetime) < date(ultimaCV.data_carga) order by date(o.obs_datetime) desc 
    ) primeira
    union
    select e.patient_id, date(o.obs_datetime) penultimaDataCarga,IF(ISNULL(o.value_numeric), IF(ISNULL(o.value_coded), 'N/A', 
                      case 
                                    o.value_coded 
                                    when 
                                       1306 
                                    then 
                                       'Nivel de detecção baixo' 
                                    when 
                                       1304 
                                    then 
                                       'MA QUALIDADE DA AMOSTRA' 
                                    when 
                                       23814 
                                    then 
                                       'Indetectável' 
                                    when 
                                       23905 
                                    then 
                                       'MENOR QUE 10 COPIAS/ML' 
                                    when 
                                       23906 
                                    then 
                                       'MENOR QUE 20 COPIAS/ML' 
                                    when 
                                       23907 
                                    then 
                                       'MENOR QUE 40 COPIAS/ML' 
                                    when 
                                       23908 
                                    then 
                                       'MENOR QUE 400 COPIAS/ML' 
                                    when 
                                       23904 
                                    then 
                                       'MENOR QUE 839 COPIAS/ML' 
                                    when 
                                       165331 
                                    then 
                                        CONCAT('MENOR QUE', ' ',o.comments) 
                                 end), o.value_numeric) AS resultadoCV, data_carga 
                                 from (select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga,obs.concept_id,obs.value_coded from ( 
                Select p.patient_id,max(o.obs_datetime) data_carga from patient p 
                inner join encounter e on p.patient_id=e.patient_id 
                inner join obs o on e.encounter_id=o.encounter_id 
                where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) 
                and  o.concept_id in (856,1305) and  date(o.obs_datetime) <= :endDate and e.location_id=:location group by p.patient_id) ultima_carga 
                inner join obs on obs.person_id=ultima_carga.patient_id and date(obs.obs_datetime)=date(ultima_carga.data_carga) 
                where obs.voided=0 and ((obs.concept_id=856 and obs.value_numeric is not null) 
                or (obs.concept_id=1305 and obs.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and obs.location_id=:location 
    ) ultimaCV inner join encounter e on e.patient_id = ultimaCV.patient_id 
    inner join obs o on o.encounter_id = e.encounter_id 
    where  ((o.concept_id=856 and o.value_numeric is not null) 
     or (o.concept_id=1305 and o.value_coded in (1306,23814,23905,23906,23907,23908,23904,165331)))  and e.location_id =:location and o.voided = 0 and e.voided = 0 and e.encounter_type = 51
    and date(o.obs_datetime) < date(ultimaCV.data_carga) order by date(penultimaDataCarga) desc 
    ) penultimaCV order by penultimaCV.penultimaDataCarga desc
    ) penultimaCV group by penultimaCV.patient_id 
    )penultimaCV on penultimaCV.patient_id = inicioDAH.patient_id 
    left join( 
     Select p.patient_id,max(o.obs_datetime) max_tblam, 
     case o.value_coded 
      when 703 then 'Positivo' 
      when 664 then 'Negativo' 
      when 21233 then 'N/A' 
      when 1118 then 'NF' 
      end as resultadoTbLam From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 23951 and  e.encounter_type in (6,13,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    group by p.patient_id 
    )ultimoTbLam on ultimoTbLam.patient_id = inicioDAH.patient_id 
    left join ( 
     Select p.patient_id,max(o.obs_datetime) max_cragSoro, 
     case o.value_coded 
     when 703 then 'Positivo' 
      when 664 then 'Negativo' 
      when 21233 then 'N/A' 
      when 1118 then 'NF' 
      end as resultadoCragSoro 
      From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 23952 and  e.encounter_type in (6,13,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    group by p.patient_id 
    )cragSoro on cragSoro.patient_id = inicioDAH.patient_id 
    left join ( 
         Select p.patient_id,max(o.obs_datetime) max_cragLcr, 
         case o.value_coded 
          when 703 then 'Positivo' 
            when 664 then 'Negativo' 
            when 21233 then 'N/A' 
            when 1118 then 'NF' 
            end as resultadoCragLcr 
            From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 165362 and  e.encounter_type in (6,13,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    group by p.patient_id 
    )cragLcr on cragLcr.patient_id = inicioDAH.patient_id 
    left join ( 
             Select p.patient_id,max(o.obs_datetime) max_cacu, 
             case o.value_coded 
              when 703 then 'Via Positivo' 
                 when 664 then 'Via Negativo' 
                 end as resultadoCacu From patient p 
    inner join encounter e on p.patient_id=e.patient_id 
    inner join obs o on e.encounter_id=o.encounter_id 
    where p.voided=0 and e.voided=0 and o.voided=0 and concept_id = 2094 and  e.encounter_type in (6,53,90) 
    and o.obs_datetime <= :endDate and e.location_id=:location 
    group by p.patient_id 
    )ultimoCacu on ultimoCacu.patient_id =  inicioDAH.patient_id 
    group by inicioDAH.patient_id 