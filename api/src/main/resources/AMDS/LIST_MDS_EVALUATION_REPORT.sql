    select (@cnt := @cnt + 1) as ID, f.* from 
    (
     select 
           coorteFinal.patient_id,
           concat(coorteFinal.tipo_coorte,' ','meses') as tipo_coorte,
           p.gender as sexo, 
           floor(datediff(coorteFinal.art_start_date,p.birthdate)/365) as idade,
           coorteFinal.art_start_date,
           if(tpt.data_tb is null,'Sim','Não') as elegibilidade_tpt,
           tptFinal.dataInicioTPI data_inicio_tpt,
           
           DATE_FORMAT(DATE(resultadoCd4Inicial.data_cd4_12_meses), '%d-%m-%Y')  as data_resultado_cd4,
           resultadoCd4Inicial.resultado_cd4_12_meses as resultado_cd4_12_meses,

           -- COLUNAS 12 MESES
           DATE_FORMAT(DATE(primeiroPedidoCV.data_primeiro_pedido_cv_12_meses),'%d-%m-%Y') data_primeiro_pedido_cv_12_meses,
           DATE_FORMAT(DATE(primeiroResultadoPedidoCV.data_primeiro_resultado_cv_12_meses),'%d-%m-%Y') as data_primeiro_resultado_cv_12_meses,
           resultadoCVPrimeiro.resultado_cv_12_meses,
           DATE_FORMAT(DATE(segundoCd4.data_segundo_cd4_12_meses),'%d-%m-%Y') as data_segundo_cd4_12_meses,
           adesaoApss.nivel_adesao nivel_adesao_12_meses,
           if(p.gender='F', if((gravidaLactante.gravida_lactante is null or gravidaLactante.gravida_lactante='') ,'Não', 'Sim'), null) gravida_lactante_12_meses,
           if(tb.data_tb is not null,'Sim','Não') tuberculose_12_meses, 
           DATE_FORMAT(DATE(primeiroMdc.data_registo_primeiro_mdc),'%d-%m-%Y') as data_registo_primeiro_mdc_12_meses,
           -- inicioMds.data_inicio_mds data_inicio_mds_12_meses,
           -- minMds.data__inicio_mds_real  data__inicio_mds_real_12_meses,
           primeiroMds12Meses.INICIO_MDS1 INICIO_MDS1_12_MESES,
           primeiroMds12Meses.INICIO_MDS2 INICIO_MDS2_12_MESES,
           primeiroMds12Meses.INICIO_MDS3 INICIO_MDS3_12_MESES,
           primeiroMds12Meses.INICIO_MDS4 INICIO_MDS4_12_MESES,
           primeiroMds12Meses.INICIO_MDS5 INICIO_MDS5_12_MESES,
           
           DATE_FORMAT(DATE(primeiroMds12Meses.DATA_INICIO_MDS1),'%d-%m-%Y') DATA_INICIO_MDS1_12_MESES,
           DATE_FORMAT(DATE(primeiroMds12Meses.DATA_INICIO_MDS2),'%d-%m-%Y') DATA_INICIO_MDS2_12_MESES,
           DATE_FORMAT(DATE(primeiroMds12Meses.DATA_INICIO_MDS3),'%d-%m-%Y') DATA_INICIO_MDS3_12_MESES,
           DATE_FORMAT(DATE(primeiroMds12Meses.DATA_INICIO_MDS4),'%d-%m-%Y') DATA_INICIO_MDS4_12_MESES,
           DATE_FORMAT(DATE(primeiroMds12Meses.DATA_INICIO_MDS5),'%d-%m-%Y') DATA_INICIO_MDS5_12_MESES,
           
           
           primeiroMds12Meses.FIM_MDS1 FIM_MDS1_12_MESES,
           primeiroMds12Meses.FIM_MDS2 FIM_MDS2_12_MESES,
           primeiroMds12Meses.FIM_MDS3 FIM_MDS3_12_MESES,
           primeiroMds12Meses.FIM_MDS4 FIM_MDS4_12_MESES,
           primeiroMds12Meses.FIM_MDS5 FIM_MDS5_12_MESES,
          
            DATE_FORMAT(DATE(primeiroMds12Meses.DATA_FIM_MDS1),'%d-%m-%Y') DATA_FIM_MDS1_12_MESES,
            DATE_FORMAT(DATE(primeiroMds12Meses.DATA_FIM_MDS2),'%d-%m-%Y') DATA_FIM_MDS2_12_MESES,
            DATE_FORMAT(DATE(primeiroMds12Meses.DATA_FIM_MDS3),'%d-%m-%Y') DATA_FIM_MDS3_12_MESES,
            DATE_FORMAT(DATE(primeiroMds12Meses.DATA_FIM_MDS4),'%d-%m-%Y') DATA_FIM_MDS4_12_MESES,
            DATE_FORMAT(DATE( primeiroMds12Meses.DATA_FIM_MDS5),'%d-%m-%Y') DATA_FIM_MDS5_12_MESES,
           if(tbSinthoms.mdc_simtomas_tb_12_meses is null, 'Não', 'Sim') mdc_Simtomas_tb_12_meses,
           if(pbImc.consultas_pb_imc is null or pbImc.consultas_pb_imc='', 'Não', 'Sim') mds_consultas_pb_imc_12_meses,
           if(todasConsultasFichaClinica.total_fc is not null, todasConsultasFichaClinica.total_fc, '0') total_consultas_fc_12_meses,
           if(todasConsultasFichaApss.total_apss is not null, todasConsultasFichaApss.total_apss,'0')  total_consultas_apss_12_meses,
             case 
             when estadioDePermanencia.tipo_saida = 1 then 'ABANDONO' 
             when estadioDePermanencia.tipo_saida = 2 then 'OBITO' 
             when estadioDePermanencia.tipo_saida = 3 then 'SUSPENSO' 
             when estadioDePermanencia.tipo_saida = 4 then 'TRANSFERIDO PARA' 
             when (ISNULL(estadioDePermanencia36Meses.tipo_saida) OR estadioDePermanencia36Meses.tipo_saida='') then 'ACTIVO' 
             end as estado_permanencia_12_meses, 

           -- COLUNAS 24 MESES
           primeiroPedidoCV24Meses.data_primeiro_pedido_cv_24_meses data_primeiro_vc_24_meses,
           primeiroResultadoPedidoCV24Meses.data_primeiro_resultado_cv_24_meses data_primeiro_resultado_cv_24_meses,
           -- resultadoCVPrimeiro24Meses.data_primeiro_resultado_cv_24_meses,
           resultadoCVPrimeiro24Meses.resultado_cv_24_meses resultado_cv_24_meses ,
           primeiroCd424Meses.primeiro_resultado_cd4_24_meses,
           if(adesaoApss24Meses.adesao is not null,'Sim','Não') nivel_adesao_apss_24_meses,
           if(gravidaLactante24Meses.answer=1065,'Sim', 'Não')  gravida_lactante_24_meses,
           if(tb24Meses.data_tb_24_meses is null or tb24Meses.data_tb_24_meses='' ,'Não','Sim') tb_24_meses,
           -- tb24Meses.tb_24_meses tb_24_meses,
           primeiroMdc24Meses.data_registo_primeiro_mdc_24_meses,
           inicioMds24Meses.data_inicio_mds_24_meses,

           primeiroMds24Meses.INICIO_MDS1_24_MESES INICIO_MDS1_24_MESES,
           primeiroMds24Meses.INICIO_MDS2_24_MESES INICIO_MDS2_24_MESES,
           primeiroMds24Meses.INICIO_MDS3_24_MESES INICIO_MDS3_24_MESES,
           primeiroMds24Meses.INICIO_MDS4_24_MESES INICIO_MDS4_24_MESES,
           primeiroMds24Meses.INICIO_MDS5_24_MESES INICIO_MDS5_24_MESES,

           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_INICIO_MDS1_24_MESES),'%d-%m-%Y') DATA_INICIO_MDS1_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_INICIO_MDS2_24_MESES),'%d-%m-%Y') DATA_INICIO_MDS2_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_INICIO_MDS3_24_MESES),'%d-%m-%Y') DATA_INICIO_MDS3_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_INICIO_MDS4_24_MESES),'%d-%m-%Y') DATA_INICIO_MDS4_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_INICIO_MDS5_24_MESES),'%d-%m-%Y') DATA_INICIO_MDS5_24_MESES,

           primeiroMds24Meses.FIM_MDS1_24_MESES FIM_MDS1_24_MESES,
           primeiroMds24Meses.FIM_MDS2_24_MESES FIM_MDS2_24_MESES,
           primeiroMds24Meses.FIM_MDS3_24_MESES FIM_MDS3_24_MESES,
           primeiroMds24Meses.FIM_MDS4_24_MESES FIM_MDS4_24_MESES,                 
                       
           primeiroMds24Meses.FIM_MDS5_24_MESES FIM_MDS5_24_MESES,

           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_FIM_MDS1_24_MESES),'%d-%m-%Y') DATA_FIM_MDS1_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_FIM_MDS2_24_MESES),'%d-%m-%Y') DATA_FIM_MDS2_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_FIM_MDS3_24_MESES),'%d-%m-%Y') DATA_FIM_MDS3_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_FIM_MDS4_24_MESES),'%d-%m-%Y') DATA_FIM_MDS4_24_MESES,
           DATE_FORMAT(DATE(primeiroMds24Meses.DATA_FIM_MDS5_24_MESES),'%d-%m-%Y') DATA_FIM_MDS5_24_MESES,
          
           if(tbSinthoms24Meses.mdc_simtomas_tb_24_meses is not null,'Sim','Não') mdc_simtomas_tb_24_meses,
           if(pbImc24Meses.consultas_pb_imc_24_meses is not null, 'Não', 'Sim') mds_consultas_pb_imc_24_meses,
           if(todasConsultasFichaClinica24Meses.total_fc_24_meses is not null, todasConsultasFichaClinica24Meses.total_fc_24_meses, '0') total_consultas_fc_24_meses,
           if(todasConsultasFichaApss24Meses.total_apss_24_meses is not null, todasConsultasFichaApss24Meses.total_apss_24_meses,'0')  total_consultas_apss_24_meses,

             case 
             when estadioDePermanencia.tipo_saida = 1 then 'ABANDONO' 
             when estadioDePermanencia.tipo_saida = 2 then 'OBITO' 
             when estadioDePermanencia.tipo_saida = 3 then 'SUSPENSO' 
             when estadioDePermanencia.tipo_saida = 4 then 'TRANSFERIDO PARA' 
             when (ISNULL(estadioDePermanencia36Meses.tipo_saida) OR estadioDePermanencia36Meses.tipo_saida='') then 'ACTIVO' 
             else 'ACTIVO'
             end as estado_permanencia_24_meses, 

          -- COLUNAS 36 MESES
           DATE_FORMAT(DATE(primeiroPedidoCV36Meses.data_primeiro_pedido_cv_36_meses),'%d-%m-%Y') data_primeiro_vc_36_meses,
           DATE_FORMAT(DATE(primeiroResultadoPedidoCV36Meses.data_primeiro_resultado_cv_36_meses),'%d-%m-%Y') data_primeiro_resultado_cv_36_meses,
           resultadoCVPrimeiro36Meses.resultado_cv_36_meses resultado_cv_36_meses ,
           DATE_FORMAT(DATE(primeiroCd436Meses.data_primeiro_cd4_36_meses),'%d-%m-%Y') data_primeiro_cd4_36_meses,
           primeiroCd436Meses.primeiro_resultado_cd4_36_meses primeiro_resultado_cd4_36_meses,
           if(adesaoApss36Meses.adesao is not null,'Sim','Não') nivel_adesao_apss_36_meses,
           if(gravidaLactante36Meses.answer=1065,'Sim', 'Não')  gravida_lactante_36_meses,
           if(tb36Meses.data_tb_36_meses is null or tb36Meses.data_tb_36_meses='' ,'Não','Sim') tb_36_meses,
           DATE_FORMAT(DATE(primeiroMdc36Meses.data_registo_primeiro_mdc_36_meses),'%d-%m-%Y') data_registo_primeiro_mdc_36_meses,
           inicioMds36Meses.data_inicio_mds_36_meses,

           primeiroMds36Meses.INICIO_MDS1_36_MESES INICIO_MDS1_36_MESES,
           primeiroMds36Meses.INICIO_MDS2_36_MESES INICIO_MDS2_36_MESES,
           primeiroMds36Meses.INICIO_MDS3_36_MESES INICIO_MDS3_36_MESES,
           primeiroMds36Meses.INICIO_MDS4_36_MESES INICIO_MDS4_36_MESES,
           primeiroMds36Meses.INICIO_MDS5_36_MESES INICIO_MDS5_36_MESES,

           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_INICIO_MDS1_36_MESES),'%d-%m-%Y') DATA_INICIO_MDS1_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_INICIO_MDS2_36_MESES),'%d-%m-%Y') DATA_INICIO_MDS2_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_INICIO_MDS3_36_MESES),'%d-%m-%Y') DATA_INICIO_MDS3_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_INICIO_MDS4_36_MESES),'%d-%m-%Y') DATA_INICIO_MDS4_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_INICIO_MDS5_36_MESES),'%d-%m-%Y') DATA_INICIO_MDS5_36_MESES,

           primeiroMds36Meses.FIM_MDS1_36_MESES FIM_MDS1_36_MESES,
           primeiroMds36Meses.FIM_MDS2_36_MESES FIM_MDS2_36_MESES,
           primeiroMds36Meses.FIM_MDS3_36_MESES FIM_MDS3_36_MESES,
           primeiroMds36Meses.FIM_MDS4_36_MESES FIM_MDS4_36_MESES,
           primeiroMds36Meses.FIM_MDS5_36_MESES FIM_MDS5_36_MESES,

           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_FIM_MDS1_36_MESES),'%d-%m-%Y') DATA_FIM_MDS1_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_FIM_MDS2_36_MESES),'%d-%m-%Y') DATA_FIM_MDS2_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_FIM_MDS3_36_MESES),'%d-%m-%Y') DATA_FIM_MDS3_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_FIM_MDS4_36_MESES),'%d-%m-%Y') DATA_FIM_MDS4_36_MESES,
           DATE_FORMAT(DATE(primeiroMds36Meses.DATA_FIM_MDS5_36_MESES),'%d-%m-%Y') DATA_FIM_MDS5_36_MESES,
          
           if(tbSinthoms36Meses.mdc_simtomas_tb_36_meses is not null,'Sim','Não') mdc_simtomas_tb_36_meses,
           pbImc36Meses.consultas_pb_imc_36_meses  mds_consultas_pb_imc_36_meses,
           if(todasConsultasFichaApss36Meses.total_apss_36_meses is not null, todasConsultasFichaApss36Meses.total_apss_36_meses,'0')  total_consultas_fc_36_meses,
           if(todasConsultasFichaApss36Meses.total_apss_36_meses is not null, todasConsultasFichaApss36Meses.total_apss_36_meses,'0')  total_consultas_apss_36_meses,


             case 
             when estadioDePermanencia36Meses.tipo_saida = 1 then 'ABANDONO' 
             when estadioDePermanencia36Meses.tipo_saida = 2 then 'OBITO' 
             when estadioDePermanencia36Meses.tipo_saida = 3 then 'SUSPENSO' 
             when estadioDePermanencia36Meses.tipo_saida = 4 then 'TRANSFERIDO PARA' 
             when (ISNULL(estadioDePermanencia36Meses.tipo_saida) OR estadioDePermanencia36Meses.tipo_saida='') then 'ACTIVO' 
             else 'ACTIVO'
             end as estado_permanencia_36_meses


      from 
        (
        select coorte12Meses.patient_id,coorte12Meses.art_start_date, trasferedIn.data_transferencia,  '12' tipo_coorte   
         from 
         (
         SELECT tx_new.patient_id,tx_new.art_start_date
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
            )coorte12Meses
            left join
        (
          SELECT tr.patient_id, tr.data_transferencia from  
              (
                SELECT p.patient_id, obsData.value_datetime as data_transferencia from patient p  
                INNER JOIN encounter e ON p.patient_id=e.patient_id  
                INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 
                INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 
                INNER JOIN obs obsData ON e.encounter_id=obsData.encounter_id AND obsData.voided=0 AND obsData.concept_id=23891 
                WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 
                AND e.location_id=:location GROUP BY p.patient_id 
                union
                select final.patient_id,final.minStateDate  as data_transferencia from  ( 
                select states.patient_id,states.patient_program_id,min(states.minStateDate) as minStateDate,states.program_id,states.state from  
                ( 
                SELECT p.patient_id, pg.patient_program_id, ps.start_date as minStateDate, pg.program_id, ps.state  FROM patient p   
                inner join patient_program pg on p.patient_id=pg.patient_id  
                inner join patient_state ps on pg.patient_program_id=ps.patient_program_id  
                WHERE pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and location_id=:location  
                )states 
                group by states.patient_id 
                order by states.minStateDate asc  
                ) final 
                inner join patient_state ps on ps.patient_program_id=final.patient_program_id  
                where ps.start_date=final.minStateDate and ps.state=29 and ps.voided=0 

        ) tr GROUP BY tr.patient_id 
        )trasferedIn on coorte12Meses.patient_id=trasferedIn.patient_id
         where trasferedIn.data_transferencia is null 
         
         union

         select coorte24Meses.patient_id,coorte24Meses.art_start_date, trasferedIn.data_transferencia, 24 tipo_coorte  
         from 
         (
         SELECT tx_new.patient_id,tx_new.art_start_date
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
            )coorte24Meses
            left join
        (
          SELECT tr.patient_id, tr.data_transferencia from  
              (
                SELECT p.patient_id, obsData.value_datetime as data_transferencia from patient p  
                INNER JOIN encounter e ON p.patient_id=e.patient_id  
                INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 
                INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 
                INNER JOIN obs obsData ON e.encounter_id=obsData.encounter_id AND obsData.voided=0 AND obsData.concept_id=23891 
                WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 
                AND e.location_id=:location GROUP BY p.patient_id 
                union
                select final.patient_id,final.minStateDate  as data_transferencia from  ( 
                select states.patient_id,states.patient_program_id,min(states.minStateDate) as minStateDate,states.program_id,states.state from  
                ( 
                SELECT p.patient_id, pg.patient_program_id, ps.start_date as minStateDate, pg.program_id, ps.state  FROM patient p   
                inner join patient_program pg on p.patient_id=pg.patient_id  
                inner join patient_state ps on pg.patient_program_id=ps.patient_program_id  
                WHERE pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and location_id=:location  
                )states 
                group by states.patient_id 
                order by states.minStateDate asc  
                ) final 
                inner join patient_state ps on ps.patient_program_id=final.patient_program_id  
                where ps.start_date=final.minStateDate and ps.state=29 and ps.voided=0 

              ) tr GROUP BY tr.patient_id 
        )trasferedIn on coorte24Meses.patient_id=trasferedIn.patient_id
          where trasferedIn.data_transferencia is null

          union
         
         select coorte36Meses.patient_id,coorte36Meses.art_start_date, trasferedIn.data_transferencia, 36 tipo_coorte  
         from 
         (
         SELECT tx_new.patient_id,tx_new.art_start_date
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
            )coorte36Meses
            left join
        (
          SELECT tr.patient_id, tr.data_transferencia from  
              (
                SELECT p.patient_id, obsData.value_datetime as data_transferencia from patient p  
                INNER JOIN encounter e ON p.patient_id=e.patient_id  
                INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 
                INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 
                INNER JOIN obs obsData ON e.encounter_id=obsData.encounter_id AND obsData.voided=0 AND obsData.concept_id=23891 
                WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 
                AND e.location_id=:location GROUP BY p.patient_id 
                union
                select final.patient_id,final.minStateDate  as data_transferencia from  ( 
                select states.patient_id,states.patient_program_id,min(states.minStateDate) as minStateDate,states.program_id,states.state from  
                ( 
                SELECT p.patient_id, pg.patient_program_id, ps.start_date as minStateDate, pg.program_id, ps.state  FROM patient p   
                inner join patient_program pg on p.patient_id=pg.patient_id  
                inner join patient_state ps on pg.patient_program_id=ps.patient_program_id  
                WHERE pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and location_id=:location  
                )states 
                group by states.patient_id 
                order by states.minStateDate asc  
                ) final 
                inner join patient_state ps on ps.patient_program_id=final.patient_program_id  
                where ps.start_date=final.minStateDate and ps.state=29 and ps.voided=0 

              ) tr GROUP BY tr.patient_id 
        )trasferedIn on coorte36Meses.patient_id=trasferedIn.patient_id
              where trasferedIn.data_transferencia is null
      )coorteFinal
       inner join person p on p.person_id=coorteFinal.patient_id
       left join
       (
            select tpt.patient_id,tpt.data_tb from 
              ( 
              select p.patient_id,e.encounter_datetime data_tb from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type=6 and  e.location_id=:location and e.voided=0 and p.voided=0 and  o.concept_id in(23761,23758)  and o.value_coded=1065
              union
              select p.patient_id,e.encounter_datetime data_tb from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type=6 and  e.location_id=:location and e.voided=0 and p.voided=0 and  o.concept_id=1766  and o.value_coded in(1763,1764,1762,1760,23760,1765)
              union
              select p.patient_id,e.encounter_datetime data_tb from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type=6 and  e.location_id=:location and e.voided=0 and p.voided=0 and  o.concept_id=1268  and o.value_coded in(1256,1257,1267)
              union
              select p.patient_id,o.obs_datetime data_tb from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type=53 and  e.location_id=:location and e.voided=0 and p.voided=0 and  o.concept_id=42 
              union
              select p.patient_id,e.encounter_datetime data_tb from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type=6 and  e.location_id=:location and e.voided=0 and p.voided=0 and  o.concept_id=1406  and o.value_coded =42
                        
              )tpt 
       )tpt on tpt.patient_id=coorteFinal.patient_id and tpt.data_tb BETWEEN coorteFinal.art_start_date and date_add(coorteFinal.art_start_date, interval 33 day)
      
       left join
       (
           SELECT tx_new.patient_id,tx_new.art_start_date,tptFinal.dataInicioTPI
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start
              GROUP BY patient_id 
          ) tx_new
          
          left join 
           (
             select tptFinal.patient_id,tptFinal.dataInicioTPI  from ( 
              select p.patient_id,estadoProfilaxia.obs_datetime dataInicioTPI  
              from patient p  
              inner join encounter e on p.patient_id = e.patient_id  
              inner join obs profilaxiaINH on profilaxiaINH.encounter_id = e.encounter_id  
              inner join obs estadoProfilaxia on estadoProfilaxia.encounter_id = e.encounter_id 
              where p.voided = 0 and e.voided = 0  and profilaxiaINH.voided = 0 and estadoProfilaxia.voided = 0   
              and  profilaxiaINH.concept_id = 23985  and profilaxiaINH.value_coded in (656,23954,165306) 
              and estadoProfilaxia.concept_id = 165308 and estadoProfilaxia.value_coded = 1256  
              and e.encounter_type in (6,9) and e.location_id=:location 
              union
              select p.patient_id, profilaxiaTpt.obs_datetime dataInicioTPI   
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs profilaxiaTpt on profilaxiaTpt.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and profilaxiaTpt.voided = 0     
              and  profilaxiaTpt.concept_id = 1719  and profilaxiaTpt.value_coded=165307
              and e.encounter_type=6 and e.location_id=:location 
              union

              select p.patient_id,estadoProfilaxia.obs_datetime dataInicioTPI  
              from patient p  
              inner join encounter e on p.patient_id = e.patient_id  
              inner join obs profilaxiaINH on profilaxiaINH.encounter_id = e.encounter_id  
              inner join obs estadoProfilaxia on estadoProfilaxia.encounter_id = e.encounter_id 
              where p.voided = 0 and e.voided = 0  and profilaxiaINH.voided = 0 and estadoProfilaxia.voided = 0   
              and  profilaxiaINH.concept_id = 23985  and profilaxiaINH.value_coded in (23954,656,165305,165306) 
              and estadoProfilaxia.concept_id = 165308 and estadoProfilaxia.value_coded = 1256  
              and e.encounter_type=53 and e.location_id=:location 
              ) tptFinal  
              )tptFinal  on tptFinal.patient_id=tx_new.patient_id
              WHERE ((tx_new.art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)) 
                        OR (tx_new.art_start_date BETWEEN date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH))
                        OR (tx_new.art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)))
                        and tptFinal.dataInicioTPI BETWEEN tx_new.art_start_date and date_add(tx_new.art_start_date, interval 33 day)
              GROUP BY tx_new.patient_id 
       )tptFinal on tptFinal.patient_id=coorteFinal.patient_id 
       left join
       (
         select p.patient_id,o.obs_datetime data_cd4_12_meses, o.value_numeric resultado_cd4_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 1695 and o.value_numeric is not null
              and e.encounter_type=6 and e.location_id=:location 
        )resultadoCd4Inicial  on resultadoCd4Inicial.patient_id=coorteFinal.patient_id and resultadoCd4Inicial.data_cd4_12_meses BETWEEN coorteFinal.art_start_date AND  date_add(coorteFinal.art_start_date, interval 33 day)

       left join
       (
         select p.patient_id,max(e.encounter_datetime) data_primeiro_pedido_cv_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 23722 and o.value_coded=856
              and e.encounter_type=6 and e.location_id=:location 
              group by p.patient_id
        )primeiroPedidoCV on primeiroPedidoCV.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
         and (primeiroPedidoCV.data_primeiro_pedido_cv_12_meses>=coorteFinal.art_start_date and primeiroPedidoCV.data_primeiro_pedido_cv_12_meses<=date_add(coorteFinal.art_start_date, interval 12 month))

      left join
       (
         select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id in(1305,856)
              and e.encounter_type=6 and e.location_id=:location 
              group by p.patient_id
        )primeiroResultadoPedidoCV on primeiroResultadoPedidoCV.patient_id=coorteFinal.patient_id  and coorteFinal.tipo_coorte=12 
       and (primeiroResultadoPedidoCV.data_primeiro_resultado_cv_12_meses>=coorteFinal.art_start_date and primeiroResultadoPedidoCV.data_primeiro_resultado_cv_12_meses<=date_add(coorteFinal.art_start_date, interval 12 month))

        left join
       (
        select cv.patient_id,max(cv.data_primeiro_resultado_cv_12_meses) data_primeiro_resultado_cv_12_meses ,cv.resultado_cv_12_meses
        from 
        (
        select * from 
        (
        select f.patient_id,f.data_primeiro_resultado_cv_12_meses  data_primeiro_resultado_cv_12_meses,
        case o.value_coded 
              when 23814  then 'CARGA VIRAL INDETECTAVEL'
              when 165331 then 'MENOR QUE'
              when 1306   then 'NIVEL BAIXO DE DETECÇÃO'
              when 1304   then 'MA QUALIDADE DA AMOSTRA'
              when 23905  then 'MENOR QUE 10 COPIAS/ML'
              when 23906  then 'MENOR QUE 20 COPIAS/ML'
              when 23907  then 'MENOR QUE 40 COPIAS/ML'
              when 23908  then 'MENOR QUE 400 COPIAS/ML'
              when 23904  then 'MENOR QUE 839 COPIAS/ML'
              when 165331 then CONCAT('MENOR QUE', ' ',o.comments)
              else null end as resultado_cv_12_meses
              
               from 
              (
              select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id=1305
              and e.encounter_type=6 and e.location_id=:location 
              group by p.patient_id
              )f
              inner join encounter e on e.patient_id = f.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id 
              where o.concept_id=1305 and e.encounter_type=6 and o.location_id=:location and o.obs_datetime=f.data_primeiro_resultado_cv_12_meses
              
              union
              
              select  f.patient_id, data_primeiro_resultado_cv_12_meses, o.value_numeric as resultado_cv_12_meses from
              (
              select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0 
              and  o.concept_id=856
              and e.encounter_type=6 and e.location_id=:location 
              GROUP by p.patient_id
              )f
              inner join encounter e on e.patient_id=f.patient_id
              inner join obs o on  o.encounter_id = e.encounter_id
              where o.concept_id=856 and o.voided=0 and o.location_id=:location and e.voided=0 
              and e.encounter_type=6 and f.data_primeiro_resultado_cv_12_meses=o.obs_datetime
              ) f order by f.data_primeiro_resultado_cv_12_meses
              )cv 
              group by cv.patient_id
        )resultadoCVPrimeiro on resultadoCVPrimeiro.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
         and (resultadoCVPrimeiro.data_primeiro_resultado_cv_12_meses>=coorteFinal.art_start_date and resultadoCVPrimeiro.data_primeiro_resultado_cv_12_meses<=date_add(coorteFinal.art_start_date, interval 12 month))
      
       left join
       (
         select primeiroCd4.patient_id,primeiroCd4.data_primeiro_cd4_12_meses,primeiroCd4.primeiro_resultado_cd4_12_meses,min(segundoCd4.resultado_segundo_cd4_12_meses) data_segundo_cd4_12_meses,segundoCd4.resultado_segundo_cd4_12_meses  
      from 
         (
            select p.patient_id,min(o.obs_datetime) data_primeiro_cd4_12_meses, o.value_numeric primeiro_resultado_cd4_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 1695 and o.value_numeric is not null
              and e.encounter_type=6 and e.location_id=:location
              group by p.patient_id
      )primeiroCd4
          inner join
          (
          select p.patient_id,o.obs_datetime data_segundo_cd4_12_meses, o.value_numeric resultado_segundo_cd4_12_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 1695 and o.value_numeric is not null
              and e.encounter_type=6 and e.location_id=:location
          )segundoCd4 on primeiroCd4.patient_id=segundoCd4.patient_id and segundoCd4.data_segundo_cd4_12_meses>primeiroCd4.data_primeiro_cd4_12_meses
          group by segundoCd4.patient_id
       )segundoCd4  on segundoCd4.patient_id=coorteFinal.patient_id 
       and segundoCd4.data_segundo_cd4_12_meses BETWEEN date_add(coorteFinal.art_start_date, interval 33 day) and date_add(coorteFinal.art_start_date, interval 12 month) and coorteFinal.tipo_coorte=12 
       
       left join
       (
              select adesaoApss.patient_id,adesaoApss.encounter_datetime, if(totalApss.A=adesaoApss.B, 'Sim', 'Não') nivel_adesao from  
              (
              select p.patient_id,e.encounter_datetime , count(e.encounter_id) A
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              where p.voided = 0 and e.voided = 0  
              and e.encounter_type=35 
              and e.location_id=:location
              group by p.patient_id
              order by p.patient_id,e.encounter_datetime
                )totalApss
              left join 
              (
             
              select p.patient_id,e.encounter_datetime,count(e.encounter_id) B
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0  
              and  o.concept_id=6223   
              and e.encounter_type=35 
              and e.location_id=:location
              group by p.patient_id
              order by p.patient_id,e.encounter_datetime
              )adesaoApss on totalApss.patient_id=adesaoApss.patient_id 
       )adesaoApss on adesaoApss.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
       and adesaoApss.encounter_datetime BETWEEN date_add(coorteFinal.art_start_date, interval 33 day) and date_add(coorteFinal.art_start_date, interval 3 month)
      
      left join
      (
       select gravidaLactante.patient_id,gravidaLactante.encounter_datetime data_gravida_lactante,if(gravidaLactante.value_coded=1065,'Sim','Não') gravida_lactante,p.gender,p.birthdate

           from  
              (
              select p.patient_id,e.encounter_datetime,o.value_coded 
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id in(1982,6332)   
              and e.encounter_type=6 and e.location_id=:location
              order by p.patient_id,e.encounter_datetime
              )gravidaLactante
              inner join person p on p.person_id=gravidaLactante.patient_id 
       )gravidaLactante on gravidaLactante.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
       and gravidaLactante.data_gravida_lactante BETWEEN date_add(coorteFinal.art_start_date, interval 3 month) and date_add(coorteFinal.art_start_date, interval 9 month)
       and floor(datediff(coorteFinal.art_start_date,gravidaLactante.birthdate)/365)>9 
       
       left join
       (
       select p.person_id, tb.data_tb as data_tb  
       from person p
       left join 
       (
       select tb.patient_id,tb.data_tb from 
                    (
                select p.patient_id,e.encounter_datetime as data_tb  from patient p 
                    left join encounter e on p.patient_id=e.patient_id 
                    left join obs o on o.encounter_id=e.encounter_id 
                    where e.encounter_type in(6) and o.concept_id=23761 and o.value_coded=1065  and e.location_id=:location 
                    and e.voided=0 and p.voided=0 and o.voided=0 
                    union
                    select p.patient_id,e.encounter_datetime as data_tb  from patient p 
                    left join encounter e on p.patient_id=e.patient_id 
                    left join obs o on o.encounter_id=e.encounter_id 
                    where e.encounter_type in(6) and o.concept_id=1268 and o.value_coded in(1256,1257)  and e.location_id=:location 
                    and e.voided=0 and p.voided=0 and o.voided=0 
                    )tb
                    )tb on tb.patient_id=p.person_id
       ) tb on tb.person_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
       and tb.data_tb BETWEEN date_add(coorteFinal.art_start_date, interval 3 month) and date_add(coorteFinal.art_start_date, interval 9 month)
       
       left join
       (
          SELECT tx_new.patient_id,tx_new.art_start_date, min(primeiroMdc.data_registo_primeiro_mdc) data_registo_primeiro_mdc
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new 
          inner join
          (
      
      select primeiroMdc.patient_id,primeiroMdc.data_mdc data_registo_primeiro_mdc from 
           (
          select p.patient_id,e.encounter_datetime as data_mdc  from patient p 
              
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
              
              )primeiroMdc

              )primeiroMdc on primeiroMdc.patient_id=tx_new.patient_id
              WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              and primeiroMdc.data_registo_primeiro_mdc BETWEEN tx_new.art_start_date and date_add(tx_new.art_start_date, interval 12 month)  
              group by primeiroMdc.patient_id
       )primeiroMdc on primeiroMdc.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
      
       left join

       (  select p.patient_id,e.encounter_datetime data_inicio_mds
              from patient p 
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
       )inicioMds on inicioMds.patient_id=coorteFinal.patient_id and  coorteFinal.tipo_coorte=12 
       and primeiroMdc.data_registo_primeiro_mdc BETWEEN date_add(coorteFinal.art_start_date, interval 3 month) and date_add(coorteFinal.art_start_date, interval 9 month)
       
       left join

       (
    select inicioFimTxNew.patient_id,
                    inicioFimTxNew.data_inicio_mds,
                    @num_first_mds := 1 + LENGTH(firstMds) - LENGTH(REPLACE(inicioFimTxNew.firstMds, ',', '')) AS num_first_mds ,
                   SUBSTRING_INDEX(inicioFimTxNew.firstMds, ',', 1) AS INICIO_MDS1,
                   IF(@num_first_mds > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds, ',', 2), ',', -1), '') AS INICIO_MDS2,
                   IF(@num_first_mds > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds, ',', 3), ',', -1), '') AS INICIO_MDS3, 
                   IF(@num_first_mds > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds, ',', 4), ',', -1), '') AS INICIO_MDS4,
                   IF(@num_first_mds > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds, ',', 5), ',', -1), '') AS INICIO_MDS5,
                  
                   @num_datas := 1 + LENGTH(data_inicio_mds) - LENGTH(REPLACE(inicioFimTxNew.data_inicio_mds, ',', '')) AS num_datas ,
                   SUBSTRING_INDEX(inicioFimTxNew.data_inicio_mds, ',', 1) AS DATA_INICIO_MDS1,
                   IF(@num_datas > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_inicio_mds, ',', 2), ',', -1), '') AS DATA_INICIO_MDS2,
                   IF(@num_datas > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_inicio_mds, ',', 3), ',', -1), '') AS DATA_INICIO_MDS3, 
                   IF(@num_datas > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_inicio_mds, ',', 4), ',', -1), '') AS DATA_INICIO_MDS4,
                   IF(@num_datas > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_inicio_mds, ',', 5), ',', -1), '') AS DATA_INICIO_MDS5,
      
                   @num_first_mds_fim := 1 + LENGTH(firstMds) - LENGTH(REPLACE(inicioFimTxNew.lastMds, ',', '')) AS num_first_mds_fim ,
                   SUBSTRING_INDEX(inicioFimTxNew.lastMds, ',', 1) AS FIM_MDS1,      
                   IF(@num_first_mds_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds, ',', 2), ',', -1), '') AS FIM_MDS2,
                   IF(@num_first_mds_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds, ',', 3), ',', -1), '') AS FIM_MDS3, 
                   IF(@num_first_mds_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds, ',', 4), ',', -1), '') AS FIM_MDS4,
                   IF(@num_first_mds_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds, ',', 5), ',', -1), '') AS FIM_MDS5,
                   data_fim_mds,
                   @num_datas_fim := 1 + LENGTH(data_fim_mds) - LENGTH(REPLACE(inicioFimTxNew.data_fim_mds, ',', '')) AS num_datas_fim ,
                   SUBSTRING_INDEX(inicioFimTxNew.data_fim_mds, ',', 1) AS DATA_FIM_MDS1,
                   IF(@num_datas_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_fim_mds, ',', 2), ',', -1), '') AS DATA_FIM_MDS2,
                   IF(@num_datas_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_fim_mds, ',', 3), ',', -1), '') AS DATA_FIM_MDS3, 
                   IF(@num_datas_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_fim_mds, ',', 4), ',', -1), '') AS DATA_FIM_MDS4,
                   IF(@num_datas_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.data_fim_mds, ',', 5), ',', -1), '') AS DATA_FIM_MDS5

                 from (
            select inicioFimTxNew.patient_id,group_concat(inicioFimTxNew.data_inicio_mds ORDER BY inicioFimTxNew.data_inicio_mds ASC) data_inicio_mds, group_concat(inicioFimTxNew.firstMds ORDER BY inicioFimTxNew.data_inicio_mds) firstMds,group_concat(inicioFimTxNew.data_fim_mds ORDER BY inicioFimTxNew.data_fim_mds) data_fim_mds ,group_concat(inicioFimTxNew.lastMds ORDER BY inicioFimTxNew.data_fim_mds) lastMds 
            from 
            (
            select inicioFimTxNew.patient_id,inicioFimTxNew.data_inicio_mds,inicioFimTxNew.firstMds,if(inicioFimTxNew.data_fim_mds is null,null,inicioFimTxNew.data_fim_mds ) data_fim_mds,if(inicioFimTxNew.lastMds is null, null,inicioFimTxNew.lastMds) lastMds 
            from 
            (
            select inicioFim.patient_id, 
                   inicioFim.data_inicio_mds, 
                   inicioFim.firstMds,
                   if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 12 month),inicioFim.data_fim_mds,null ) data_fim_mds,
                   if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 12 month),inicioFim.lastMds ,null ) lastMds, 
                   tx_new.art_start_date, 
                   date_add(tx_new.art_start_date, interval 33 day),
                   date_add(tx_new.art_start_date, interval 12 month)
                   from 
            (
            select inicioMds.patient_id,inicioMds.data_inicio_mds,inicioMds.firstMds,fimMds.data_fim_mds,fimMds.lastMds 

            from    
             (  
                  select p.patient_id,date(e.encounter_datetime) data_inicio_mds,
                  case o.value_coded
                  when  165340 then 'DB'
                  when  23730 then 'DT' 
                  when  165314 then 'DA' 
                  when  23888 then 'DS' 
                  when  23724 then 'GA'
                  when  23726 then 'CA'
                  when  165317 then 'TB' 
                  when  165320 then 'SMI' 
                  when  165321 then 'DAH' 
                  when  165178 then 'DCP' 
                  when  165179 then 'DCA' 
                  when  165318 then 'CT' 
                  when  165315 then 'DD' 
                  when  165264 then 'BM' 
                  when  165265 then 'CM'
                  when  23725  then 'AF'
                  when  23729  then 'FR'
                  when  165176 then 'EH' 
                  when  165319 then 'SAAJ'
                  when  23727  then 'PU'
                  when  165177 then 'FARMAC/Farmácia Privada'
                  when  23732  then 'OUTRO'
                  end AS firstMds
                  from patient p 
                  join encounter e on p.patient_id=e.patient_id 
                  join obs grupo on grupo.encounter_id=e.encounter_id 
                  join obs o on o.encounter_id=e.encounter_id 
                  join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                  where  e.encounter_type in(6) 
                  and e.location_id=:location 
                  and o.concept_id=165174  
                  and o.voided=0 
                  and grupo.concept_id=165323  
                  and grupo.voided=0 
                  and obsEstado.concept_id=165322  
                  and obsEstado.value_coded in(1256) 
                  and obsEstado.voided=0  
                  and grupo.voided=0 
                  and grupo.obs_id=o.obs_group_id 
                  and grupo.obs_id=obsEstado.obs_group_id 
                  order by date(e.encounter_datetime) ASC
                  )inicioMds
                left join
                (
                  select p.patient_id,date(e.encounter_datetime) data_fim_mds,
                  case o.value_coded
                  when  165340 then 'DB'
                  when  23730 then 'DT' 
                  when  165314 then 'DA' 
                  when  23888 then 'DS' 
                  when  23724 then 'GA'
                  when  23726 then 'CA'
                  when  165317 then 'TB' 
                  when  165320 then 'SMI' 
                  when  165321 then 'DAH' 
                  when  165178 then 'DCP' 
                  when  165179 then 'DCA' 
                  when  165318 then 'CT' 
                  when  165315 then 'DD' 
                  when  165264 then 'BM' 
                  when  165265 then 'CM'
                  when  23725  then 'AF'
                  when  23729  then 'FR'
                  when  165176 then 'EH' 
                  when  165319 then 'SAAJ'
                  when  23727  then 'PU'
                  when  165177 then 'FARMAC/Farmácia Privada'
                  when  23732  then 'OUTRO'
                  end AS lastMds
                  from patient p 
                  join encounter e on p.patient_id=e.patient_id 
                  join obs grupo on grupo.encounter_id=e.encounter_id 
                  join obs o on o.encounter_id=e.encounter_id 
                  join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                  where  e.encounter_type in(6) 
                  and e.location_id=:location 
                  and o.concept_id=165174  
                  and o.voided=0 
                  and grupo.concept_id=165323  
                  and grupo.voided=0 
                  and obsEstado.concept_id=165322  
                  and obsEstado.value_coded in(1267) 
                  and obsEstado.voided=0  
                  and grupo.voided=0 
                  and grupo.obs_id=o.obs_group_id 
                  and grupo.obs_id=obsEstado.obs_group_id
                  order by  date(e.encounter_datetime) asc 
                ) fimMds on inicioMds.patient_id=fimMds.patient_id and fimMds.lastMds=inicioMds.firstMds
                )inicioFim
                left join 
               (
                  SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                  ( 
                  SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                  WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                  AND e.location_id=:location 
                  AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
                  GROUP BY p.patient_id 
                  UNION 
                  SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                  WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                  AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                  AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
                  AND e.location_id=:location 
                  GROUP BY p.patient_id 
                  ) 
                  art_start GROUP BY patient_id 
               )tx_new on tx_new.patient_id=inicioFim.patient_id
               WHERE tx_new.art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
               and  inicioFim.data_inicio_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 12 month)
               order by inicioFim.patient_id,inicioFim.data_inicio_mds
               )inicioFimTxNew
               order by inicioFimTxNew.data_inicio_mds,inicioFimTxNew.data_fim_mds asc
               )inicioFimTxNew
               GROUP BY inicioFimTxNew.patient_id
               )inicioFimTxNew  
          )primeiroMds12Meses on primeiroMds12Meses.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
          
          left join
            (
             select tbSinthoms.patient_id,tbSinthoms.mdc_simtomas_tb_12_meses mdc_simtomas_tb_12_meses,mds.art_start_date,mds.data_registo_primeiro_mdc  from 
             (
             select p.patient_id,e.encounter_datetime as mdc_simtomas_tb_12_meses  from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=23758  and e.location_id=:location and o.value_coded in(1065,1066) 
              and e.voided=0 and p.voided=0 and o.voided=0 

              )tbSinthoms
             inner join
             (
             select mds.patient_id,mds.art_start_date,mds.data_registo_primeiro_mdc from
             (
            SELECT tx_new.patient_id,tx_new.art_start_date, min(primeiroMdc.data_registo_primeiro_mdc) data_registo_primeiro_mdc
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new 
          inner join
          (
      
           select primeiroMdc.patient_id,primeiroMdc.data_mdc data_registo_primeiro_mdc from 
           (
          select p.patient_id,e.encounter_datetime as data_mdc  from patient p 
              
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
              
              )primeiroMdc

              )primeiroMdc on primeiroMdc.patient_id=tx_new.patient_id
              WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-20')), interval 12 MONTH)
              and primeiroMdc.data_registo_primeiro_mdc BETWEEN date_add(tx_new.art_start_date, interval 3 month) and date_add(tx_new.art_start_date, interval 9 month)  
              group by primeiroMdc.patient_id
             )mds
             )mds on tbSinthoms.patient_id=mds.patient_id
              WHERE  tbSinthoms.mdc_simtomas_tb_12_meses BETWEEN mds.data_registo_primeiro_mdc  and date_add(mds.art_start_date, interval 12 month)
            )tbSinthoms on tbSinthoms.patient_id=coorteFinal.patient_id  and coorteFinal.tipo_coorte=12 
          
          
            left join
            (
             select mds.patient_id,mds.data__inicio_mds_real from
              (
              select  mds.patient_id,min(mds.data_inicio_mds) data__inicio_mds_real from
              (
              select p.patient_id,e.encounter_datetime data_inicio_mds
              from patient p 
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
              order by p.patient_id, e.encounter_datetime
              )mds
              group by mds.patient_id
              )mds
            )minMds on minMds.patient_id=coorteFinal.patient_id
            left join
            (
              select p.patient_id,e.encounter_datetime consultas_pb_imc,o.concept_id  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              left join obs o on e.encounter_id=o.encounter_id and o.concept_id in (1342,1343) and o.voided=0
              where e.encounter_type=6   and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0 and o.concept_id is not null
              order by p.patient_id
            )pbImc on  pbImc.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
            and pbImc.consultas_pb_imc BETWEEN primeiroMds12Meses.data_inicio_mds  and date_add(coorteFinal.art_start_date, interval 12 month)
            left join
            (
            SELECT consultas12Meses.patient_id, count(consultas12Meses.todas_consultas_fc_12_meses) total_fc
             FROM 
                   
              (
              select p.patient_id,e.encounter_datetime todas_consultas_fc_12_meses  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              where e.encounter_type=6 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
              order by p.patient_id
              )consultas12Meses
              inner join 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
             ) tx_new  on consultas12Meses.patient_id=tx_new.patient_id
        where  art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
        and consultas12Meses.todas_consultas_fc_12_meses BETWEEN date_add(tx_new.art_start_date, interval 6 month)  and date_add(tx_new.art_start_date, interval 12 month)
        group by consultas12Meses.patient_id
        )todasConsultasFichaClinica on todasConsultasFichaClinica.patient_id=coorteFinal.patient_id and coorteFinal.tipo_coorte=12 
        left join
          ( 
            SELECT consultas12Meses.patient_id, count(consultas12Meses.todas_consultas_fc_12_meses) total_apss
           FROM 
                   
              (
              select p.patient_id,e.encounter_datetime todas_consultas_fc_12_meses  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              where e.encounter_type=35 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
              order by p.patient_id
              )consultas12Meses
              inner join 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
             ) tx_new  on consultas12Meses.patient_id=tx_new.patient_id

        where  art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 24 MONTH) and  date_sub(date(concat(:year,'-06','-21')), interval 12 MONTH)
        and consultas12Meses.todas_consultas_fc_12_meses BETWEEN date_add(tx_new.art_start_date, interval 6 month)  and date_add(tx_new.art_start_date, interval 12 month)
        group by consultas12Meses.patient_id
        )todasConsultasFichaApss on todasConsultasFichaApss.patient_id=coorteFinal.patient_id and  coorteFinal.tipo_coorte=12

        left join
        (
       Select B7.patient_id, 1 tipo_saida from 
                     (
                      select maxFilaRecepcao.patient_id,max(data_levantamento) data_levantamento,max(data_proximo_levantamento) data_proximo_levantamento, date_add(max(data_proximo_levantamento), INTERVAL 59 day) data_proximo_levantamento60, date_add(tx_new.art_start_date,  interval 12 month) as endDate 
                      from
                      (
                      SELECT tx_new.patient_id,tx_new.art_start_date
                           FROM 
                              (
                              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                              ( 
                              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                              AND e.location_id=:location 
                              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                              GROUP BY p.patient_id 
                              UNION 
                              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                              AND e.location_id=:location 
                              GROUP BY p.patient_id 
                              ) 
                              art_start GROUP BY patient_id 
                          ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                        )tx_new
                     inner join 
                      (
                      select p.patient_id,o.value_datetime data_levantamento, date_add(o.value_datetime, INTERVAL 30 day)  data_proximo_levantamento 
                      from patient p inner 
                      join encounter e on p.patient_id = e.patient_id 
                      inner join obs o on o.encounter_id = e.encounter_id 
                      inner join obs obsLevantou on obsLevantou.encounter_id= e.encounter_id 
                      where  e.voided = 0 and p.voided = 0 
                      and o.voided = 0 and o.concept_id = 23866 and obsLevantou.concept_id=23865 
                      and obsLevantou.value_coded=1065 and obsLevantou.voided=0 and e.encounter_type=52 and e.location_id=:location 
                      union 
                      select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from 
                      ( 
                      select p.patient_id,e.encounter_datetime as data_levantamento from patient p 
                      inner join encounter e on p.patient_id=e.patient_id where encounter_type=18 
                      and e.location_id=:location 
                      and e.voided=0 and p.voided=0 
                      )fila 
                      inner join obs obs_fila on obs_fila.person_id=fila.patient_id 
                      where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime 
                      ) maxFilaRecepcao on maxFilaRecepcao.patient_id=tx_new.patient_id
                      WHERE maxFilaRecepcao.data_levantamento<=date_add(tx_new.art_start_date,  interval 12 month) 
                      group by patient_id
                      having date_add(max(data_proximo_levantamento), INTERVAL 59 day )< endDate   
                      )B7 
                      where B7.patient_id not in
                      (
                               select tx_new.patient_id 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            and pg.location_id=:location 
                            group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state in (7,8,10)
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded in(1366,1709,1706) and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded in(1366,1709,1706) and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            --group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 12 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 12 month)  group by obito.patient_id

                      )
                      
                             union

                            
                               select tx_new.patient_id, 2 tipo_saida 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            and pg.location_id=:location 
                            group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state=10 
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded=1366 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded=1366 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            --group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 12 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 12 month)  group by obito.patient_id

                           union
   
                            select tx_new.patient_id,  3 tipo_saida 
                            from 
                            (
                            SELECT tx_new.patient_id,tx_new.art_start_date
                              FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) 
                                 art_start GROUP BY patient_id 
                             ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 24 month)
                           )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_suspencao) data_suspencao from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_suspencao from 
                            ( 
                            select pg.patient_id,ps.start_date data_suspencao from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW() 
                            and pg.location_id=:location 
                            --group by p.patient_id  
                            )maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
                            union 
                             select p.patient_id,o.obs_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6272 
                            and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union 
                            select  p.patient_id,e.encounter_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where  e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 
                            and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id  
                            ) suspenso 
                            group by patient_id 
                            ) suspenso1 on suspenso1.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                             SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) art_start GROUP BY patient_id 
                                    ) tx_new
                                 inner join 
                                 (
                                 select p.patient_id,e.encounter_datetime encounter_datetime 
                                 from patient p 
                                 inner join encounter e on e.patient_id = p.patient_id 
                                 where p.voided = 0 
                                 and e.voided = 0 
                                 and e.location_id =:location 
                                 and e.encounter_type=18 
                                 )lev on tx_new.patient_id=lev.patient_id
                                 where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 12 month)
                                 group by lev.patient_id
                                   ) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
                                  where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao and suspenso1.data_suspencao <= date_add(tx_new.art_start_date, interval 12 month)  group by suspenso1.patient_id
                                  
                             union
         
                             select transferidopara.patient_id, 4 tipo_saida 
                               from 
                               (
                               select transferidopara.patient_id,transferidopara.data_transferidopara
                               from
                               (
                               select transferido.patient_id,max(transferido.data_transferidopara) data_transferidopara
                                  from
                                  (
                                     select maxEstado.patient_id,maxEstado.data_transferidopara
                                     from
                                     (
                                        select
                                        pg.patient_id,
                                        ps.start_date data_transferidopara
                                        from patient p
                                        inner join patient_program pg on p.patient_id = pg.patient_id
                                        inner join patient_state ps on pg.patient_program_id = ps.patient_program_id
                                        where pg.voided = 0
                                        and ps.voided = 0
                                        and p.voided = 0
                                        and pg.program_id = 2
                                        --and ps.start_date <= NOW()
                                        and pg.location_id =:location                               
                                     )maxEstado
                                     inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id
                                     inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id
                                     where pg2.voided = 0
                                     and ps2.voided = 0
                                     and pg2.program_id = 2
                                     and ps2.start_date = maxEstado.data_transferidopara
                                     and pg2.location_id =:location
                                     and ps2.state = 7
                                     union
                                     select
                                     p.patient_id,o.obs_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and o.obs_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6272
                                     and o.value_coded = 1706
                                     and e.encounter_type = 53
                                     and e.location_id =:location
                                     --group by p.patient_id
                                     union
                                     select
                                     p.patient_id,
                                     e.encounter_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and e.encounter_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6273
                                     and o.value_coded = 1706
                                     and e.encounter_type = 6
                                     and e.location_id =:location
                                     --group by p.patient_id
                                  )transferido
         
                                  inner join 
                                  (
         
                                  SELECT tx_new.patient_id,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                      ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                                    )tx_new on tx_new.patient_id=transferido.patient_id
                                    where transferido.data_transferidopara <= date_add(tx_new.art_start_date, interval 12 month)
                                     group by transferido.patient_id
                                    )transferidopara
                                    inner join 
                                    (
                                     SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                          ) tx_new
                                       inner join 
                                       (
                                       select p.patient_id,e.encounter_datetime encounter_datetime 
                                       from patient p 
                                       inner join encounter e on e.patient_id = p.patient_id 
                                       where p.voided = 0 
                                       and e.voided = 0 
                                       and e.location_id =:location 
                                       and e.encounter_type=18 
                                       )lev on tx_new.patient_id=lev.patient_id
                                       where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 30 month)
                                       group by lev.patient_id
                                     )lev where lev.encounter_datetime<=transferidopara.data_transferidopara
                                  )transferidopara
                                  inner join
                                  (
                                    select final.patient_id,max(final.data_lev) as data_lev,final.data_ultimo_levantamento,tx_new.art_start_date from
                                     (
                                        select patient_id, data_lev,data_ultimo_levantamento data_ultimo_levantamento from
                                        (
                                           select ultimo_fila.patient_id,ultimo_fila.data_fila data_lev,date_add(obs_fila.value_datetime , interval 1 day ) data_ultimo_levantamento from
                                           (
                                              select
                                              p.patient_id, encounter_datetime data_fila
                                              from patient p
                                              inner join person pe on pe.person_id = p.patient_id
                                              inner join encounter e on e.patient_id = p.patient_id
                                              where p.voided = 0
                                              and pe.voided = 0
                                              and e.voided = 0
                                              and e.encounter_type = 18
                                              and e.location_id =:location
                                           )ultimo_fila
                                    left join encounter ultimo_fila_data_criacao on ultimo_fila_data_criacao.patient_id=ultimo_fila.patient_id 
                                     and ultimo_fila_data_criacao.voided=0 
                                     and ultimo_fila_data_criacao.encounter_type = 18 
                                     and date(ultimo_fila_data_criacao.encounter_datetime) = date(ultimo_fila.data_fila) 
                                     and ultimo_fila_data_criacao.location_id=:location 
                                     left join 
                                     obs obs_fila on obs_fila.person_id=ultimo_fila.patient_id 
                                     and obs_fila.voided=0 
                                     and (date(obs_fila.obs_datetime)=date(ultimo_fila.data_fila)  or (date(ultimo_fila_data_criacao.date_created) = date(obs_fila.date_created) and ultimo_fila_data_criacao.encounter_id = obs_fila.encounter_id )) 
                                     and obs_fila.concept_id=5096 
                                     and obs_fila.location_id=:location 
                                           union
                                           select p.patient_id,value_datetime data_lev,date_add(value_datetime,interval 31 day ) data_ultimo_levantamento
                                           from patient p
                                           inner join person pe on pe.person_id = p.patient_id
                                           inner join encounter e on p.patient_id = e.patient_id
                                           inner join obs o on e.encounter_id = o.encounter_id
                                           where p.voided = 0
                                           and pe.voided = 0
                                           and e.voided = 0
                                           and o.voided = 0
                                           and e.encounter_type = 52
                                           and o.concept_id = 23866
                                           and o.value_datetime is not null
                                           and e.location_id =:location
                                        )ultimo_levantamento
                                        --group by ultimo_levantamento.patient_id
                                        order by ultimo_levantamento.data_lev desc
                                     )final
                                     inner join
                                 (
                                 SELECT tx_new.patient_id,tx_new.art_start_date
                                                FROM 
                                                   (
                                                   SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                                   ( 
                                                   SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                                   WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                                   AND e.location_id=:location 
                                                   AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                                   GROUP BY p.patient_id 
                                                   UNION 
                                                   SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                                   WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                                   AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                                   AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 12 month)
                                                   AND e.location_id=:location 
                                                   GROUP BY p.patient_id 
                                                   ) art_start GROUP BY patient_id 
                                               ) tx_new 
                                             )tx_new on tx_new.patient_id=final.patient_id
                                              where final.data_lev <= date_add(tx_new.art_start_date, interval 12 month) 
                                              --and final.data_ultimo_levantamento<=date_add(tx_new.art_start_date, interval 12 month)
                                              group by final.patient_id                                       
                                         )final on transferidopara.patient_id=final.patient_id
          ) estadioDePermanencia on  estadioDePermanencia.patient_id=coorteFinal.patient_id


           -- Nesta Seccao Vamos colocar as variaveis de 24 Meses

          left join 
          (
          SELECT tx_new.patient_id,tx_new.art_start_date, primeiroPedidoCV24Meses.data_primeiro_pedido_cv_24_meses
                       FROM 
                          (
                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                          ( 
                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                          patient p 
                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                          AND e.location_id=:location
                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                          GROUP BY p.patient_id 
                          UNION 
                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                          patient p 
                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                          AND e.location_id=:location 
                          GROUP BY p.patient_id 
                          ) 
                          art_start GROUP BY patient_id 
                      ) tx_new 
                        
                        inner join
                        (
                        select primeiroPedidoCV24Meses.patient_id,primeiroPedidoCV24Meses.data_primeiro_pedido_cv_24_meses data_primeiro_pedido_cv_24_meses
                        from   
                        (
                           select p.patient_id,e.encounter_datetime data_primeiro_pedido_cv_24_meses, e.encounter_id
                                from patient p   
                                inner join encounter e on p.patient_id = e.patient_id   
                                inner join obs o on o.encounter_id = e.encounter_id     
                                where p.voided = 0 and e.voided = 0  and o.voided = 0
                                and  o.concept_id = 23722 and o.value_coded=856 
                                and e.encounter_type=6 and e.location_id=:location 
                          )primeiroPedidoCV24Meses
                           group by primeiroPedidoCV24Meses.patient_id
                          )primeiroPedidoCV24Meses on primeiroPedidoCV24Meses.patient_id=tx_new.patient_id
                           where (primeiroPedidoCV24Meses.data_primeiro_pedido_cv_24_meses BETWEEN date_add(tx_new.art_start_date, interval 12 month) and  date_add(tx_new.art_start_date, interval 24 month))
          )primeiroPedidoCV24Meses on primeiroPedidoCV24Meses.patient_id=coorteFinal.patient_id 

          left join
         (
           select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_24_meses
                from patient p   
                inner join encounter e on p.patient_id = e.patient_id   
                inner join obs o on o.encounter_id = e.encounter_id   
                where p.voided = 0 and e.voided = 0  and o.voided = 0     
                and  o.concept_id in(1305,856)
                and e.encounter_type=6 and e.location_id=:location 
                group by p.patient_id
         )primeiroResultadoPedidoCV24Meses on primeiroResultadoPedidoCV24Meses.patient_id=coorteFinal.patient_id 
         and (primeiroResultadoPedidoCV24Meses.data_primeiro_resultado_cv_24_meses>=date_add(coorteFinal.art_start_date, interval 12 month)) 
         and (primeiroResultadoPedidoCV24Meses.data_primeiro_resultado_cv_24_meses<=date_add(coorteFinal.art_start_date, interval 24 month))


       left join
       (
        select cv.patient_id,max(cv.data_primeiro_resultado_cv_24_meses) data_primeiro_resultado_cv_24_meses ,cv.resultado_cv_24_meses
        from 
        (
        select * from 
        (
        select p.patient_id,o.obs_datetime  data_primeiro_resultado_cv_24_meses,
        case o.value_coded 
              when 23814  then 'CARGA VIRAL INDETECTAVEL'
              when 165331 then 'MENOR QUE'
              when 1306   then 'NIVEL BAIXO DE DETECÇÃO'
              when 1304   then 'MA QUALIDADE DA AMOSTRA'
              when 23905  then 'MENOR QUE 10 COPIAS/ML'
              when 23906  then 'MENOR QUE 20 COPIAS/ML'
              when 23907  then 'MENOR QUE 40 COPIAS/ML'
              when 23908  then 'MENOR QUE 400 COPIAS/ML'
              when 23904  then 'MENOR QUE 839 COPIAS/ML'
              when 165331 then CONCAT('MENOR QUE', ' ',o.comments)
              else null end as resultado_cv_24_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id=1305
              and e.encounter_type=6 and e.location_id=:location 
              
              union
              
              select p.patient_id,o.obs_datetime data_primeiro_resultado_cv_24_meses, o.value_numeric as resultado_cv_24_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0
              and  o.concept_id=856
              and e.encounter_type=6 and e.location_id=:location 
              ) f order by f.data_primeiro_resultado_cv_24_meses
              )cv 
              group by cv.patient_id
        )resultadoCVPrimeiro24Meses on resultadoCVPrimeiro24Meses.patient_id=coorteFinal.patient_id 
         and (resultadoCVPrimeiro24Meses.data_primeiro_resultado_cv_24_meses>=date_add(coorteFinal.art_start_date, interval 12 month)) 
         and (resultadoCVPrimeiro24Meses.data_primeiro_resultado_cv_24_meses<=date_add(coorteFinal.art_start_date, interval 24 month))
      
       left join
       (
            select p.patient_id,min(o.obs_datetime) data_primeiro_cd4_24_meses,o.value_numeric primeiro_resultado_cd4_24_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 1695 and o.value_numeric is not null
              and e.encounter_type=6 and e.location_id=:location
              group by p.patient_id
         
       )primeiroCd424Meses on primeiroCd424Meses.patient_id=coorteFinal.patient_id 
       and  primeiroCd424Meses.primeiro_resultado_cd4_24_meses BETWEEN date_add(coorteFinal.art_start_date, interval 12 month) and date_add(coorteFinal.art_start_date, interval 24 month)
       
       left join
       (
              select final.patient_id,final.data_adesao_apss_24_meses_1,final.encounter_1 encounter_1 , finalADesao1.data_adesao_apss_24_meses_2,finalADesao1.encounter_2 encounter_2,if(final.encounter_1=finalADesao1.encounter_2, 'Sim','Não') adesao
                   from 
                   (
                     select tx_new.patient_id,tx_new.art_start_date, totalApss.data_adesao_apss_24_meses_1, count(totalApss.encounter_1) encounter_1
          from 
          (
                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                    ( 
                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                    patient p 
                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                    AND e.location_id=:location 
                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                    GROUP BY p.patient_id 
                    UNION 
                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                    patient p 
                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                    AND e.location_id=:location 
                    GROUP BY p.patient_id 
                    ) tx_new
                     group by tx_new.patient_id
                    )tx_new
                    inner join
                    (
                    select p.patient_id,e.encounter_datetime as data_adesao_apss_24_meses_1, e.encounter_id encounter_1
                    from patient p   
                    inner join encounter e on p.patient_id = e.patient_id   
                    where p.voided = 0 and e.voided = 0  
                    and e.encounter_type=35 
                    and e.location_id=:location
                    order by p.patient_id,e.encounter_datetime
                    )totalApss on totalApss.patient_id=tx_new.patient_id
                    WHERE totalApss.data_adesao_apss_24_meses_1 BETWEEN date_add(tx_new.art_start_date, interval 12 month) and date_add(tx_new.art_start_date, interval 24 month) 
                    group by tx_new.patient_id
                    )final
                    left join 
                    (
                       select finalADesao1.patient_id,finalADesao1.data_adesao_apss_24_meses_2, COUNT(finalADesao1.encounter_2) encounter_2
                       from 
                       (
                         select tx_new.patient_id,tx_new.art_start_date, adesaoApss.data_adesao_apss_24_meses_2,adesaoApss.encounter_2  
                        from 
                        (
                        SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                        ( 
                        SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                        patient p 
                        INNER JOIN encounter e ON p.patient_id=e.patient_id 
                        INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                        WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                        AND e.location_id=:location 
                        AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                        GROUP BY p.patient_id 
                        UNION 
                        SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                        patient p 
                        INNER JOIN encounter e ON p.patient_id=e.patient_id 
                        INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                        WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                        AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                        AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                        AND e.location_id=:location 
                        GROUP BY p.patient_id 
                        ) tx_new
                         group by tx_new.patient_id
                        )tx_new
                        inner join
                        (
                         select p.patient_id,e.encounter_datetime as data_adesao_apss_24_meses_2, e.encounter_id as encounter_2
                          from patient p   
                          inner join encounter e on p.patient_id = e.patient_id   
                          inner join obs o on o.encounter_id = e.encounter_id   
                          where p.voided = 0 and e.voided = 0  and o.voided = 0  
                          and  o.concept_id=6223   
                          and e.encounter_type=35 
                          and e.location_id=:location
                          and o.value_coded=1383
                          order by p.patient_id,e.encounter_datetime  
                      )adesaoApss on tx_new.patient_id=adesaoApss.patient_id
                           WHERE adesaoApss.data_adesao_apss_24_meses_2 BETWEEN date_add(tx_new.art_start_date, interval 12 month) and date_add(tx_new.art_start_date, interval 24 month) 
                        )finalADesao1
                        group by finalADesao1.patient_id
                        )finalADesao1 on finalADesao1.patient_id=final.patient_id
                        group by final.patient_id   
        )adesaoApss24Meses on adesaoApss24Meses.patient_id=coorteFinal.patient_id  
       
       left join
      (
      select gravidaLactante24Meses.patient_id,gravidaLactante24Meses.encounter_datetime data_gravida_lactante_24_meses,p.gender,p.birthdate, gravidaLactante24Meses.answer

           from  
              (
              select p.patient_id,e.encounter_datetime,o.value_coded answer
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0 
              and  o.concept_id in(1982,6332)   
              and e.encounter_type=6 and e.location_id=:location
              order by p.patient_id,e.encounter_datetime
              )gravidaLactante24Meses
              inner join person p on p.person_id=gravidaLactante24Meses.patient_id and p.gender='F' 
       )gravidaLactante24Meses on gravidaLactante24Meses.patient_id=coorteFinal.patient_id  
       and gravidaLactante24Meses.data_gravida_lactante_24_meses BETWEEN date_add(coorteFinal.art_start_date, interval 12 month) and date_add(coorteFinal.art_start_date, interval 24 month)
       and floor(datediff(coorteFinal.art_start_date,gravidaLactante24Meses.birthdate)/365)>9 
       
       left join
       (
        select tb24Meses.patient_id,tb24Meses.data_tb_24_meses,tb24Meses.data_tb_24_meses  tb_24_meses from 
              (
          select p.patient_id,e.encounter_datetime as data_tb_24_meses  
          from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=23761  and e.location_id=1065 
              and e.voided=0 and p.voided=0 and o.voided=0 
              union
              select p.patient_id,e.encounter_datetime as data_tb_24_meses  from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=1268 and o.value_coded in(1256,1257)  and e.location_id=:location 
              and e.voided=0 and p.voided=0 and o.voided=0 
              )tb24Meses
       ) tb24Meses on tb24Meses.patient_id=coorteFinal.patient_id  
            and tb24Meses.data_tb_24_meses BETWEEN date_add(coorteFinal.art_start_date, interval 12 month) and date_add(coorteFinal.art_start_date, interval 24 month)
       
       left join
       (
             SELECT tx_new.patient_id,tx_new.art_start_date, min(primeiroMdc.data_registo_primeiro_mdc) data_registo_primeiro_mdc_24_meses
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new 
          inner join
          (
      
      select primeiroMdc.patient_id,primeiroMdc.data_mdc data_registo_primeiro_mdc from 
           (
          select p.patient_id,e.encounter_datetime as data_mdc  from patient p 
              
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
              
              )primeiroMdc

              )primeiroMdc on primeiroMdc.patient_id=tx_new.patient_id
              WHERE  primeiroMdc.data_registo_primeiro_mdc BETWEEN date_add(tx_new.art_start_date, interval 12 month) and date_add(tx_new.art_start_date, interval 24 month)  
              group by primeiroMdc.patient_id
       )primeiroMdc24Meses on primeiroMdc24Meses.patient_id=coorteFinal.patient_id
       
       left join

       (
              select p.patient_id,e.encounter_datetime data_inicio_mds_24_meses
              from patient p 
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
       )inicioMds24Meses on inicioMds24Meses.patient_id=coorteFinal.patient_id
       and inicioMds24Meses.data_inicio_mds_24_meses BETWEEN date_add(coorteFinal.art_start_date, interval 12 month) and date_add(coorteFinal.art_start_date, interval 24 month)
       
       left join
       (
                       select inicioFimTxNew.patient_id,inicioFimTxNew.data_inicio_mds_24_meses,
               @num_first_mds := 1 + LENGTH(firstMds_24_meses) - LENGTH(REPLACE(inicioFimTxNew.firstMds_24_meses, ',', '')) AS num_first_mds ,
               SUBSTRING_INDEX(inicioFimTxNew.firstMds_24_meses, ',', 1) AS INICIO_MDS1_24_MESES,
               IF(@num_first_mds > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_24_meses, ',', 2), ',', -1), '') AS INICIO_MDS2_24_MESES,
               IF(@num_first_mds > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_24_meses, ',', 3), ',', -1), '') AS INICIO_MDS3_24_MESES, 
               IF(@num_first_mds > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_24_meses, ',', 4), ',', -1), '') AS INICIO_MDS4_24_MESES,
               IF(@num_first_mds > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_24_meses, ',', 5), ',', -1), '') AS INICIO_MDS5_24_MESES,
              
               @num_datas := 1 + LENGTH(inicioFimTxNew.data_inicio_mds_24_meses) - LENGTH(REPLACE(inicioFimTxNew.data_inicio_mds_24_meses, ',', '')) AS num_datas ,
               SUBSTRING_INDEX(data_inicio_mds_24_meses, ',', 1) AS DATA_INICIO_MDS1_24_MESES,
               IF(@num_datas > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_24_meses, ',', 2), ',', -1), '') AS DATA_INICIO_MDS2_24_MESES,
               IF(@num_datas > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_24_meses, ',', 3), ',', -1), '') AS DATA_INICIO_MDS3_24_MESES, 
               IF(@num_datas > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_24_meses, ',', 4), ',', -1), '') AS DATA_INICIO_MDS4_24_MESES,
               IF(@num_datas > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_24_meses, ',', 5), ',', -1), '') AS DATA_INICIO_MDS5_24_MESES,

               @num_first_mds_fim := 1 + LENGTH(lastMds_24_meses) - LENGTH(REPLACE(inicioFimTxNew.lastMds_24_meses, ',', '')) AS num_first_mds_fim ,
               SUBSTRING_INDEX(inicioFimTxNew.lastMds_24_meses, ',', 1) AS FIM_MDS1_24_MESES,      
               IF(@num_first_mds_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_24_meses, ',', 2), ',', -1), '') AS FIM_MDS2_24_MESES,
               IF(@num_first_mds_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_24_meses, ',', 3), ',', -1), '') AS FIM_MDS3_24_MESES, 
               IF(@num_first_mds_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_24_meses, ',', 4), ',', -1), '') AS FIM_MDS4_24_MESES,
               IF(@num_first_mds_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_24_meses, ',', 5), ',', -1), '') AS FIM_MDS5_24_MESES,
               data_fim_mds_24_meses,
               @num_datas_fim := 1 + LENGTH(data_fim_mds_24_meses) - LENGTH(REPLACE(inicioFimTxNew.data_fim_mds_24_meses, ',', '')) AS num_datas_fim ,
               SUBSTRING_INDEX(data_fim_mds_24_meses, ',', 1) AS DATA_FIM_MDS1_24_MESES,
               IF(@num_datas_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_24_meses, ',', 2), ',', -1), '') AS DATA_FIM_MDS2_24_MESES,
               IF(@num_datas_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_24_meses, ',', 3), ',', -1), '') AS DATA_FIM_MDS3_24_MESES, 
               IF(@num_datas_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_24_meses, ',', 4), ',', -1), '') AS DATA_FIM_MDS4_24_MESES,
               IF(@num_datas_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_24_meses, ',', 5), ',', -1), '') AS DATA_FIM_MDS5_24_MESES
                from 
               (
                 select inicioFimTxNew.patient_id,group_concat(inicioFimTxNew.data_inicio_mds  ORDER BY inicioFimTxNew.data_inicio_mds ASC) data_inicio_mds_24_meses, group_concat(inicioFimTxNew.firstMds ORDER BY inicioFimTxNew.data_inicio_mds) firstMds_24_meses,group_concat(inicioFimTxNew.data_fim_mds ORDER BY  inicioFimTxNew.data_fim_mds) data_fim_mds_24_meses ,group_concat(inicioFimTxNew.lastMds ORDER BY inicioFimTxNew.data_fim_mds) lastMds_24_meses from 
                (
                select inicioFimTxNew.patient_id,inicioFimTxNew.data_inicio_mds,inicioFimTxNew.firstMds,if(inicioFimTxNew.data_fim_mds is null,null,inicioFimTxNew.data_fim_mds ) data_fim_mds,if(inicioFimTxNew.lastMds is null, null,inicioFimTxNew.lastMds) lastMds 
                from 
                (
                select inicioFim.patient_id, 
                       inicioFim.data_inicio_mds, 
                       inicioFim.firstMds,
                       if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 24 month),inicioFim.data_fim_mds,null ) data_fim_mds,
                       if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 24 month),inicioFim.lastMds ,null ) lastMds, 
                       tx_new.art_start_date, 
                       date_add(tx_new.art_start_date, interval 24 month)
                       from 
                (
                select inicioMds.patient_id,inicioMds.data_inicio_mds,inicioMds.firstMds,fimMds.data_fim_mds,fimMds.lastMds 

                from    
                 (  
                select p.patient_id,date(e.encounter_datetime) data_inicio_mds,
                case o.value_coded
                when  165340 then 'DB'
                when  23730 then 'DT' 
                when  165314 then 'DA' 
                when  23888 then 'DS' 
                when  23724 then 'GA'
                when  23726 then 'CA'
                when  165317 then 'TB' 
                when  165320 then 'SMI' 
                when  165321 then 'DAH' 
                when  165178 then 'DCP' 
                when  165179 then 'DCA' 
                when  165318 then 'CT' 
                when  165315 then 'DD' 
                when  165264 then 'BM' 
                when  165265 then 'CM'
                when  23725  then 'AF'
                when  23729  then 'FR'
                when  165176 then 'EH' 
                when  165319 then 'SAAJ'
                when  23727  then 'PU'
                when  165177 then 'FARMAC/Farmácia Privada'
                when  23732  then 'OUTRO'
                end AS firstMds
                from patient p 
                join encounter e on p.patient_id=e.patient_id 
                join obs grupo on grupo.encounter_id=e.encounter_id 
                join obs o on o.encounter_id=e.encounter_id 
                join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                where  e.encounter_type in(6) 
                and e.location_id=:location 
                and o.concept_id=165174  
                and o.voided=0 
                and grupo.concept_id=165323  
                and grupo.voided=0 
                and obsEstado.concept_id=165322  
                and obsEstado.value_coded in(1256) 
                and obsEstado.voided=0  
                and grupo.voided=0 
                and grupo.obs_id=o.obs_group_id 
                and grupo.obs_id=obsEstado.obs_group_id 
                order by date(e.encounter_datetime) ASC
                )inicioMds
              left join
              (
                select p.patient_id,date(e.encounter_datetime) data_fim_mds,
                case o.value_coded
                when  165340 then 'DB'
                when  23730 then 'DT' 
                when  165314 then 'DA' 
                when  23888 then 'DS' 
                when  23724 then 'GA'
                when  23726 then 'CA'
                when  165317 then 'TB' 
                when  165320 then 'SMI' 
                when  165321 then 'DAH' 
                when  165178 then 'DCP' 
                when  165179 then 'DCA' 
                when  165318 then 'CT' 
                when  165315 then 'DD' 
                when  165264 then 'BM' 
                when  165265 then 'CM'
                when  23725  then 'AF'
                when  23729  then 'FR'
                when  165176 then 'EH' 
                when  165319 then 'SAAJ'
                when  23727  then 'PU'
                when  165177 then 'FARMAC/Farmácia Privada'
                when  23732  then 'OUTRO'
                end AS lastMds
                from patient p 
                join encounter e on p.patient_id=e.patient_id 
                join obs grupo on grupo.encounter_id=e.encounter_id 
                join obs o on o.encounter_id=e.encounter_id 
                join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                where  e.encounter_type in(6) 
                and e.location_id=:location 
                and o.concept_id=165174  
                and o.voided=0 
                and grupo.concept_id=165323  
                and grupo.voided=0 
                and obsEstado.concept_id=165322  
                and obsEstado.value_coded in(1267) 
                and obsEstado.voided=0  
                and grupo.voided=0 
                and grupo.obs_id=o.obs_group_id 
                and grupo.obs_id=obsEstado.obs_group_id
                order by  date(e.encounter_datetime) asc 
              ) fimMds on inicioMds.patient_id=fimMds.patient_id and fimMds.lastMds=inicioMds.firstMds
              )inicioFim
              left join 
             (
                SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                ( 
                SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                AND e.location_id=:location 
                AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                GROUP BY p.patient_id 
                UNION 
                SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                AND e.location_id=:location 
                GROUP BY p.patient_id 
                 ) 
              art_start GROUP BY patient_id 
           )tx_new on tx_new.patient_id=inicioFim.patient_id
           WHERE  inicioFim.data_inicio_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 24 month)
           order by inicioFim.patient_id,inicioFim.data_inicio_mds
           )inicioFimTxNew
           )inicioFimTxNew
           GROUP BY inicioFimTxNew.patient_id
           )inicioFimTxNew
          )primeiroMds24Meses on primeiroMds24Meses.patient_id=coorteFinal.patient_id
            left join

            (
             select p.patient_id,e.encounter_datetime as mdc_simtomas_tb_24_meses  from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=23758  and e.location_id=:location and o.value_coded in(1065,1066) 
              and e.voided=0 and p.voided=0 and o.voided=0 
            )tbSinthoms24Meses on tbSinthoms24Meses.patient_id=coorteFinal.patient_id 
            and tbSinthoms24Meses.mdc_simtomas_tb_24_meses BETWEEN date_add(coorteFinal.art_start_date, interval 12 month)  and date_add(coorteFinal.art_start_date, interval 24 month)
            
            
            left join
            (
             select final.patient_id, if(final.A=final.B,'Sim', 'Não') consultas_pb_imc_24_meses from 
              (
             select pbImc24Meses.patient_id,pbImc24Meses.consultas_pb_imc_24_meses consultas_pb_imc_24_meses, pbImc24Meses.concept_id, count(pbImc24Meses.patient_id) A, count(pbImc24Meses.concept_id) B  from 
                 (
                  select  p.patient_id,e.encounter_datetime consultas_pb_imc_24_meses, o.concept_id from 
                  patient p 
                  left join encounter e on p.patient_id=e.patient_id 
                  left join obs o on e.encounter_id=o.encounter_id and o.concept_id in (1342,1343) and o.voided=0
                  where e.encounter_type=6   and e.location_id=:location and e.voided=0 
                  and p.voided=0 and e.voided=0 
                  order by e.encounter_datetime
                  )pbImc24Meses
             
              inner join 
                  (
                  SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                  ( 
                  SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                  WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                  AND e.location_id=:location 
                  AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                  GROUP BY p.patient_id 
                  UNION 
                  SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                  WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                  AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                  AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                  AND e.location_id=:location 
                  GROUP BY p.patient_id 
                  ) 
                  art_start 
                  GROUP BY patient_id 
                 ) tx_new  on pbImc24Meses.patient_id=tx_new.patient_id
                  where  pbImc24Meses.consultas_pb_imc_24_meses BETWEEN  date_add(tx_new.art_start_date, interval 12 month)  and date_add(tx_new.art_start_date, interval 24 month)
                  group by   pbImc24Meses.patient_id        
              )final
              group by final.patient_id
            )pbImc24Meses on  pbImc24Meses.patient_id=coorteFinal.patient_id 
            
            
            left join
            (
            SELECT consultas24Meses.patient_id, count(consultas24Meses.todas_consultas_fc_24_meses) total_fc_24_meses
              FROM 
                   
               (
              select p.patient_id,e.encounter_datetime todas_consultas_fc_24_meses  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              where e.encounter_type=6 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
              order by p.patient_id
              )consultas24Meses
              inner join 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
             ) tx_new  on consultas24Meses.patient_id=tx_new.patient_id
              where  consultas24Meses.todas_consultas_fc_24_meses BETWEEN date_add(tx_new.art_start_date, interval 12 month)  and date_add(tx_new.art_start_date, interval 24 month)
              group by consultas24Meses.patient_id
       
        )todasConsultasFichaClinica24Meses on todasConsultasFichaClinica24Meses.patient_id=coorteFinal.patient_id 
        
        left join
          ( 
             SELECT consultas24Meses.patient_id, count(consultas24Meses.todas_consultas_fc_24_meses) total_apss_24_meses
           FROM 
                   
               (
              select p.patient_id,e.encounter_datetime todas_consultas_fc_24_meses  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              where e.encounter_type=35 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
              order by p.patient_id
              )consultas24Meses
              inner join 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
             ) tx_new  on consultas24Meses.patient_id=tx_new.patient_id

        where  consultas24Meses.todas_consultas_fc_24_meses BETWEEN date_add(tx_new.art_start_date, interval 12 month)  and date_add(tx_new.art_start_date, interval 24 month)
        group by consultas24Meses.patient_id
        )todasConsultasFichaApss24Meses on todasConsultasFichaApss24Meses.patient_id=coorteFinal.patient_id

        left join
        (       
       Select B7.patient_id, 1 tipo_saida from 
                     (
                      select maxFilaRecepcao.patient_id,max(data_levantamento) data_levantamento,max(data_proximo_levantamento) data_proximo_levantamento, date_add(max(data_proximo_levantamento), INTERVAL 59 day) data_proximo_levantamento60, date_add(tx_new.art_start_date,  interval 24 month) as endDate 
                      from
                      (
                      SELECT tx_new.patient_id,tx_new.art_start_date
                           FROM 
                              (
                              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                              ( 
                              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                              AND e.location_id=:location 
                              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                              GROUP BY p.patient_id 
                              UNION 
                              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                              AND e.location_id=:location 
                              GROUP BY p.patient_id 
                              ) 
                              art_start GROUP BY patient_id 
                          ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 24 month)
                        )tx_new
                     inner join 
                      (
                      select p.patient_id,o.value_datetime data_levantamento, date_add(o.value_datetime, INTERVAL 30 day)  data_proximo_levantamento 
                      from patient p inner 
                      join encounter e on p.patient_id = e.patient_id 
                      inner join obs o on o.encounter_id = e.encounter_id 
                      inner join obs obsLevantou on obsLevantou.encounter_id= e.encounter_id 
                      where  e.voided = 0 and p.voided = 0 
                      and o.voided = 0 and o.concept_id = 23866 and obsLevantou.concept_id=23865 
                      and obsLevantou.value_coded=1065 and obsLevantou.voided=0 and e.encounter_type=52 and e.location_id=:location 
                      union 
                      select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from 
                      ( 
                      select p.patient_id,e.encounter_datetime as data_levantamento from patient p 
                      inner join encounter e on p.patient_id=e.patient_id where encounter_type=18 
                      and e.location_id=:location 
                      and e.voided=0 and p.voided=0 
                      )fila 
                      inner join obs obs_fila on obs_fila.person_id=fila.patient_id 
                      where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime 
                      ) maxFilaRecepcao on maxFilaRecepcao.patient_id=tx_new.patient_id
                      WHERE maxFilaRecepcao.data_levantamento<=date_add(tx_new.art_start_date,  interval 24 month) 
                      group by patient_id
                      having date_add(max(data_proximo_levantamento), INTERVAL 59 day )< endDate   
                      )B7 
                       where B7.patient_id not in
                      (
                               select tx_new.patient_id 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            and pg.location_id=:location 
                            group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state in (7,8,10)
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded in(1366,1709,1706) and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded in(1366,1709,1706) and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            --group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 24 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 24 month)  group by obito.patient_id

                      )
                            union

                            
                               select tx_new.patient_id, 2 tipo_saida 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 24 month)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            and pg.location_id=:location 
                            --group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state=10 
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded=1366 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded=1366 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 24 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 24 month) 
                            group by obito.patient_id

                           union
   
                       select tx_new.patient_id,  3 tipo_saida 
                            from 
                            (
                            SELECT tx_new.patient_id,tx_new.art_start_date
                              FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) 
                                 art_start GROUP BY patient_id 
                             ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 24 month)
                           )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_suspencao) data_suspencao from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_suspencao from 
                            ( 
                            select pg.patient_id,ps.start_date data_suspencao from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW() 
                            and pg.location_id=:location 
                            --group by p.patient_id  
                            )maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
                            union 
                             select p.patient_id,o.obs_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6272 
                            and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union 
                            select  p.patient_id,e.encounter_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where  e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 
                            and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id  
                            ) suspenso 
                            group by patient_id 
                            ) suspenso1 on suspenso1.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                             SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) art_start GROUP BY patient_id 
                                    ) tx_new
                                 inner join 
                                 (
                                 select p.patient_id,e.encounter_datetime encounter_datetime 
                                 from patient p 
                                 inner join encounter e on e.patient_id = p.patient_id 
                                 where p.voided = 0 
                                 and e.voided = 0 
                                 and e.location_id =:location 
                                 and e.encounter_type=18 
                                 )lev on tx_new.patient_id=lev.patient_id
                                 where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 24 month)
                                 group by lev.patient_id
                                   ) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
                                  where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao and suspenso1.data_suspencao <= date_add(tx_new.art_start_date, interval 24 month)  group by suspenso1.patient_id
                                  
                             union
         
                             select transferidopara.patient_id, 4 tipo_saida 
                               from 
                               (
                               select transferidopara.patient_id,transferidopara.data_transferidopara
                               from
                               (
                               select transferido.patient_id,max(transferido.data_transferidopara) data_transferidopara
                                  from
                                  (
                                     select maxEstado.patient_id,maxEstado.data_transferidopara
                                     from
                                     (
                                        select
                                        pg.patient_id,
                                        ps.start_date data_transferidopara
                                        from patient p
                                        inner join patient_program pg on p.patient_id = pg.patient_id
                                        inner join patient_state ps on pg.patient_program_id = ps.patient_program_id
                                        where pg.voided = 0
                                        and ps.voided = 0
                                        and p.voided = 0
                                        and pg.program_id = 2
                                        --and ps.start_date <= NOW()
                                        and pg.location_id =:location                               
                                     )maxEstado
                                     inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id
                                     inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id
                                     where pg2.voided = 0
                                     and ps2.voided = 0
                                     and pg2.program_id = 2
                                     and ps2.start_date = maxEstado.data_transferidopara
                                     and pg2.location_id =:location
                                     and ps2.state = 7
                                     union
                                     select
                                     p.patient_id,o.obs_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and o.obs_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6272
                                     and o.value_coded = 1706
                                     and e.encounter_type = 53
                                     and e.location_id =:location
                                     --group by p.patient_id
                                     union
                                     select
                                     p.patient_id,
                                     e.encounter_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and e.encounter_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6273
                                     and o.value_coded = 1706
                                     and e.encounter_type = 6
                                     and e.location_id =:location
                                     --group by p.patient_id
                                  )transferido
         
                                  inner join 
                                  (
         
                                  SELECT tx_new.patient_id,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                      ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 24 month)
                                    )tx_new on tx_new.patient_id=transferido.patient_id
                                    where transferido.data_transferidopara <= date_add(tx_new.art_start_date, interval 24 month)
                                     group by transferido.patient_id
                                    )transferidopara
                                    inner join 
                                    (
                                     SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                          ) tx_new
                                       inner join 
                                       (
                                       select p.patient_id,e.encounter_datetime encounter_datetime 
                                       from patient p 
                                       inner join encounter e on e.patient_id = p.patient_id 
                                       where p.voided = 0 
                                       and e.voided = 0 
                                       and e.location_id =:location 
                                       and e.encounter_type=18 
                                       )lev on tx_new.patient_id=lev.patient_id
                                       where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 30 month)
                                       group by lev.patient_id
                                     )lev where lev.encounter_datetime<=transferidopara.data_transferidopara
                                  )transferidopara
                                  inner join
                                  (
                                    select final.patient_id,max(final.data_lev) as data_lev,final.data_ultimo_levantamento,tx_new.art_start_date from
                                     (
                                        select patient_id, data_lev,data_ultimo_levantamento data_ultimo_levantamento from
                                        (
                                           select ultimo_fila.patient_id,ultimo_fila.data_fila data_lev,date_add(obs_fila.value_datetime , interval 1 day ) data_ultimo_levantamento from
                                           (
                                              select
                                              p.patient_id, encounter_datetime data_fila
                                              from patient p
                                              inner join person pe on pe.person_id = p.patient_id
                                              inner join encounter e on e.patient_id = p.patient_id
                                              where p.voided = 0
                                              and pe.voided = 0
                                              and e.voided = 0
                                              and e.encounter_type = 18
                                              and e.location_id =:location
                                           )ultimo_fila
                                    left join encounter ultimo_fila_data_criacao on ultimo_fila_data_criacao.patient_id=ultimo_fila.patient_id 
                                     and ultimo_fila_data_criacao.voided=0 
                                     and ultimo_fila_data_criacao.encounter_type = 18 
                                     and date(ultimo_fila_data_criacao.encounter_datetime) = date(ultimo_fila.data_fila) 
                                     and ultimo_fila_data_criacao.location_id=:location 
                                     left join 
                                     obs obs_fila on obs_fila.person_id=ultimo_fila.patient_id 
                                     and obs_fila.voided=0 
                                     and (date(obs_fila.obs_datetime)=date(ultimo_fila.data_fila)  or (date(ultimo_fila_data_criacao.date_created) = date(obs_fila.date_created) and ultimo_fila_data_criacao.encounter_id = obs_fila.encounter_id )) 
                                     and obs_fila.concept_id=5096 
                                     and obs_fila.location_id=:location 
                                           union
                                           select p.patient_id,value_datetime data_lev,date_add(value_datetime,interval 31 day ) data_ultimo_levantamento
                                           from patient p
                                           inner join person pe on pe.person_id = p.patient_id
                                           inner join encounter e on p.patient_id = e.patient_id
                                           inner join obs o on e.encounter_id = o.encounter_id
                                           where p.voided = 0
                                           and pe.voided = 0
                                           and e.voided = 0
                                           and o.voided = 0
                                           and e.encounter_type = 52
                                           and o.concept_id = 23866
                                           and o.value_datetime is not null
                                           and e.location_id =:location
                                        )ultimo_levantamento
                                        --group by ultimo_levantamento.patient_id
                                        order by ultimo_levantamento.data_lev desc
                                     )final
                                     inner join
                                 (
                                 SELECT tx_new.patient_id,tx_new.art_start_date
                                                FROM 
                                                   (
                                                   SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                                   ( 
                                                   SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                                   WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                                   AND e.location_id=:location 
                                                   AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                                   GROUP BY p.patient_id 
                                                   UNION 
                                                   SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                                   WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                                   AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                                   AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 month)
                                                   AND e.location_id=:location 
                                                   GROUP BY p.patient_id 
                                                   ) art_start GROUP BY patient_id 
                                               ) tx_new 
                                             )tx_new on tx_new.patient_id=final.patient_id
                                              where final.data_lev <= date_add(tx_new.art_start_date, interval 24 month) 
                                              --and final.data_ultimo_levantamento<=date_add(tx_new.art_start_date, interval 24 month)
                                              group by final.patient_id                                       
                                         )final on transferidopara.patient_id=final.patient_id
                                        ) estadioDePermanencia24Meses on  estadioDePermanencia24Meses.patient_id=coorteFinal.patient_id



               -- Nesta Seccao Vamos colocar as variaveis de 36 Meses
          left join 
          (
               SELECT tx_new.patient_id,tx_new.art_start_date, primeiroPedidoCV36Meses.data_primeiro_pedido_cv_36_meses
             FROM 
                (
                SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                ( 
                SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                AND e.location_id=:location
                AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                GROUP BY p.patient_id 
                UNION 
                SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                AND o.concept_id=23866 AND o.value_datetime is NOT NULL
                AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 24 MONTH)
                AND e.location_id=:location 
                GROUP BY p.patient_id 
                ) 
                art_start GROUP BY patient_id 
            ) tx_new 
              
              inner join
              (
              select primeiroPedidoCV36Meses.patient_id,primeiroPedidoCV36Meses.data_primeiro_pedido_cv_36_meses data_primeiro_pedido_cv_36_meses
              from   
              (
                 select p.patient_id,e.encounter_datetime data_primeiro_pedido_cv_36_meses, e.encounter_id
                      from patient p   
                      inner join encounter e on p.patient_id = e.patient_id   
                      inner join obs o on o.encounter_id = e.encounter_id   
                      where p.voided = 0 and e.voided = 0  and o.voided = 0
                      and  o.concept_id = 23722 and o.value_coded=856
                      and e.encounter_type=6 and e.location_id=:location 
                )primeiroPedidoCV36Meses
                 group by primeiroPedidoCV36Meses.patient_id
                )primeiroPedidoCV36Meses on primeiroPedidoCV36Meses.patient_id=tx_new.patient_id
                 where (primeiroPedidoCV36Meses.data_primeiro_pedido_cv_36_meses BETWEEN date_add(tx_new.art_start_date, interval 24 month) and  date_add(tx_new.art_start_date, interval 36 month))
          )primeiroPedidoCV36Meses on primeiroPedidoCV36Meses.patient_id=coorteFinal.patient_id
               
          left join
         (
           select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_36_meses
                from patient p   
                inner join encounter e on p.patient_id = e.patient_id   
                inner join obs o on o.encounter_id = e.encounter_id   
                where p.voided = 0 and e.voided = 0  and o.voided = 0     
                and  o.concept_id in(1305,856)
                and e.encounter_type=6 and e.location_id=:location 
                group by p.patient_id
         )primeiroResultadoPedidoCV36Meses on primeiroResultadoPedidoCV36Meses.patient_id=coorteFinal.patient_id
         and primeiroResultadoPedidoCV36Meses.data_primeiro_resultado_cv_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and  date_add(coorteFinal.art_start_date, interval 36 month)

       left join
       (
       select cv.patient_id,max(cv.data_primeiro_resultado_cv_36_meses) data_primeiro_resultado_cv_36_meses ,cv.resultado_cv_36_meses
        from 
        (
        select * from 
        (
        select f.patient_id,f.data_primeiro_resultado_cv_36_meses  data_primeiro_resultado_cv_36_meses,
        case o.value_coded 
              when 23814  then 'CARGA VIRAL INDETECTAVEL'
              when 165331 then 'MENOR QUE'
              when 1306   then 'NIVEL BAIXO DE DETECÇÃO'
              when 1304   then 'MA QUALIDADE DA AMOSTRA'
              when 23905  then 'MENOR QUE 10 COPIAS/ML'
              when 23906  then 'MENOR QUE 20 COPIAS/ML'
              when 23907  then 'MENOR QUE 40 COPIAS/ML'
              when 23908  then 'MENOR QUE 400 COPIAS/ML'
              when 23904  then 'MENOR QUE 839 COPIAS/ML'
              when 165331 then CONCAT('MENOR QUE', ' ',o.comments)
              else null end as resultado_cv_36_meses
              
               from 
              (
              select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_36_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id=1305
              and e.encounter_type=6 and e.location_id=:location 
              group by p.patient_id
              )f
              inner join encounter e on e.patient_id = f.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id 
              where o.concept_id=1305 and e.encounter_type=6 and o.location_id=:location and o.obs_datetime=f.data_primeiro_resultado_cv_36_meses
              
              union
              
              select  f.patient_id, data_primeiro_resultado_cv_36_meses, o.value_numeric as resultado_cv_36_meses from
              (
              select p.patient_id,max(e.encounter_datetime) data_primeiro_resultado_cv_36_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0
              and  o.concept_id=856
              and e.encounter_type=6 and e.location_id=:location 
              GROUP by p.patient_id
              )f
              inner join encounter e on e.patient_id=f.patient_id
              inner join obs o on  o.encounter_id = e.encounter_id
              where o.concept_id=856 and o.voided=0 and o.location_id=:location and e.voided=0 
              and e.encounter_type=6 and f.data_primeiro_resultado_cv_36_meses=o.obs_datetime
              ) f order by f.data_primeiro_resultado_cv_36_meses
              )cv 
              group by cv.patient_id
        )resultadoCVPrimeiro36Meses on resultadoCVPrimeiro36Meses.patient_id=coorteFinal.patient_id
         and (resultadoCVPrimeiro36Meses.data_primeiro_resultado_cv_36_meses>date_add(coorteFinal.art_start_date, interval 24 month)) 
         and (resultadoCVPrimeiro36Meses.data_primeiro_resultado_cv_36_meses<=date_add(coorteFinal.art_start_date, interval 36 month))
       left join
       (
            select p.patient_id,min(o.obs_datetime) data_primeiro_cd4_36_meses,o.value_numeric primeiro_resultado_cd4_36_meses
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0     
              and  o.concept_id = 1695 and o.value_numeric is not null
              and e.encounter_type=6 and e.location_id=:location
              group by p.patient_id
         
       )primeiroCd436Meses on primeiroCd436Meses.patient_id=coorteFinal.patient_id 
       and primeiroCd436Meses.primeiro_resultado_cd4_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month)
       
       left join
       (
                   select final.patient_id,final.data_adesao_apss_36_meses_1,final.encounter_1 encounter_1 , finalADesao1.data_adesao_apss_36_meses_2,finalADesao1.encounter_2 encounter_2,if(final.encounter_1=finalADesao1.encounter_2, 'Sim','Não') adesao
                   from 
                   (
                     select tx_new.patient_id,tx_new.art_start_date, totalApss.data_adesao_apss_36_meses_1, count(totalApss.encounter_1) encounter_1
          from 
          (
                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                    ( 
                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                    patient p 
                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                    AND e.location_id=:location 
                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                    GROUP BY p.patient_id 
                    UNION 
                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                    patient p 
                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                    AND e.location_id=:location 
                    GROUP BY p.patient_id 
                    ) tx_new
                     group by tx_new.patient_id
                    )tx_new
                    inner join
                    (
                    select p.patient_id,e.encounter_datetime as data_adesao_apss_36_meses_1, e.encounter_id encounter_1
                    from patient p   
                    inner join encounter e on p.patient_id = e.patient_id   
                    where p.voided = 0 and e.voided = 0  
                    and e.encounter_type=35 
                    and e.location_id=:location
                    order by p.patient_id,e.encounter_datetime
                    )totalApss on totalApss.patient_id=tx_new.patient_id
                    WHERE totalApss.data_adesao_apss_36_meses_1 BETWEEN date_add(tx_new.art_start_date, interval 24 month) and date_add(tx_new.art_start_date, interval 36 month) 
                    group by tx_new.patient_id
                    )final
                    left join 
                    (
                       select finalADesao1.patient_id,finalADesao1.data_adesao_apss_36_meses_2, COUNT(finalADesao1.encounter_2) encounter_2
                       from 
                       (
                         select tx_new.patient_id,tx_new.art_start_date, adesaoApss.data_adesao_apss_36_meses_2,adesaoApss.encounter_2  
                        from 
                        (
                        SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                        ( 
                        SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                        patient p 
                        INNER JOIN encounter e ON p.patient_id=e.patient_id 
                        INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                        WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                        AND e.location_id=:location 
                        AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                        GROUP BY p.patient_id 
                        UNION 
                        SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                        patient p 
                        INNER JOIN encounter e ON p.patient_id=e.patient_id 
                        INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                        WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                        AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                        AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                        AND e.location_id=:location 
                        GROUP BY p.patient_id 
                        ) tx_new
                         group by tx_new.patient_id
                        )tx_new
                        inner join
                        (
                         select p.patient_id,e.encounter_datetime as data_adesao_apss_36_meses_2, e.encounter_id as encounter_2
                          from patient p   
                          inner join encounter e on p.patient_id = e.patient_id   
                          inner join obs o on o.encounter_id = e.encounter_id   
                          where p.voided = 0 and e.voided = 0  and o.voided = 0  
                          and  o.concept_id=6223   
                          and e.encounter_type=35 
                          and e.location_id=:location
                          and o.value_coded=1383
                          order by p.patient_id,e.encounter_datetime
                      )adesaoApss on tx_new.patient_id=adesaoApss.patient_id
                           WHERE adesaoApss.data_adesao_apss_36_meses_2 BETWEEN date_add(tx_new.art_start_date, interval 24 month) and date_add(tx_new.art_start_date, interval 36 month) 
                        )finalADesao1
                        group by finalADesao1.patient_id
                        )finalADesao1 on finalADesao1.patient_id=final.patient_id
                        group by final.patient_id
        )adesaoApss36Meses on adesaoApss36Meses.patient_id=coorteFinal.patient_id
       and adesaoApss36Meses.data_adesao_apss_36_meses_1 BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month) 
       and adesaoApss36Meses.data_adesao_apss_36_meses_2 BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month) 
       
       left join
      (
        select gravidaLactante36Meses.patient_id,gravidaLactante36Meses.encounter_datetime data_gravida_lactante_36_meses,p.gender,p.birthdate, gravidaLactante36Meses.answer

           from  
              (
              select p.patient_id,e.encounter_datetime,o.value_coded answer
              from patient p   
              inner join encounter e on p.patient_id = e.patient_id   
              inner join obs o on o.encounter_id = e.encounter_id   
              where p.voided = 0 and e.voided = 0  and o.voided = 0 
              and  o.concept_id in(1982,6332)   
              and e.encounter_type=6 and e.location_id=:location
              order by p.patient_id,e.encounter_datetime
              )gravidaLactante36Meses
              inner join person p on p.person_id=gravidaLactante36Meses.patient_id and p.gender='F'  
       )gravidaLactante36Meses on gravidaLactante36Meses.patient_id=coorteFinal.patient_id 
       and gravidaLactante36Meses.data_gravida_lactante_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month)
       and floor(datediff(coorteFinal.art_start_date,gravidaLactante36Meses.birthdate)/365)>9 
       
       left join
       (
        select tb36Meses.patient_id,tb36Meses.data_tb_36_meses,tb36Meses.data_tb_36_meses  tb_36_meses from 
              (
          select p.patient_id,e.encounter_datetime as data_tb_36_meses  
          from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=23761  and e.location_id=1065 
              and e.voided=0 and p.voided=0 and o.voided=0 
              union
              select p.patient_id,e.encounter_datetime as data_tb_24_meses  from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=1268 and o.value_coded in(1256,1257)  and e.location_id=:location 
              and e.voided=0 and p.voided=0 and o.voided=0 
              )tb36Meses
       ) tb36Meses on tb36Meses.patient_id=coorteFinal.patient_id
            and tb36Meses.data_tb_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month)
       
        left join
       (
             SELECT tx_new.patient_id,tx_new.art_start_date, min(primeiroMdc.data_registo_primeiro_mdc) data_registo_primeiro_mdc_36_meses
           FROM 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start GROUP BY patient_id 
          ) tx_new 
          inner join
          (
      
      select primeiroMdc.patient_id,primeiroMdc.data_mdc data_registo_primeiro_mdc from 
           (
          select p.patient_id,e.encounter_datetime as data_mdc  from patient p 
              
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
              )primeiroMdc
              )primeiroMdc on primeiroMdc.patient_id=tx_new.patient_id
              WHERE primeiroMdc.data_registo_primeiro_mdc BETWEEN date_add(tx_new.art_start_date, interval 24 month) and date_add(tx_new.art_start_date, interval 36 month)  
              group by primeiroMdc.patient_id
       )primeiroMdc36Meses on primeiroMdc36Meses.patient_id=coorteFinal.patient_id 
       
       left join

       (
              select p.patient_id,e.encounter_datetime data_inicio_mds_36_meses
              from patient p 
              join encounter e on p.patient_id=e.patient_id 
              join obs grupo on grupo.encounter_id=e.encounter_id 
              join obs o on o.encounter_id=e.encounter_id 
              join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
              where  e.encounter_type in(6) 
              and e.location_id=:location 
              and o.concept_id=165174  
              and o.voided=0 
              and grupo.concept_id=165323  
              and grupo.voided=0 
              and obsEstado.concept_id=165322  
              and obsEstado.value_coded in(1256) 
              and obsEstado.voided=0  
              and grupo.voided=0 
              and grupo.obs_id=o.obs_group_id 
              and grupo.obs_id=obsEstado.obs_group_id 
       )inicioMds36Meses on inicioMds36Meses.patient_id=coorteFinal.patient_id
       and inicioMds36Meses.data_inicio_mds_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month) and date_add(coorteFinal.art_start_date, interval 36 month)
       
       left join
       (
              select inicioFimTxNew.patient_id,inicioFimTxNew.data_inicio_mds_36_meses,
               @num_first_mds := 1 + LENGTH(firstMds_36_meses) - LENGTH(REPLACE(inicioFimTxNew.firstMds_36_meses, ',', '')) AS num_first_mds ,
               SUBSTRING_INDEX(inicioFimTxNew.firstMds_36_meses, ',', 1) AS INICIO_MDS1_36_MESES,
               IF(@num_first_mds > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_36_meses, ',', 2), ',', -1), '') AS INICIO_MDS2_36_MESES,
               IF(@num_first_mds > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_36_meses, ',', 3), ',', -1), '') AS INICIO_MDS3_36_MESES, 
               IF(@num_first_mds > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_36_meses, ',', 4), ',', -1), '') AS INICIO_MDS4_36_MESES,
               IF(@num_first_mds > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.firstMds_36_meses, ',', 5), ',', -1), '') AS INICIO_MDS5_36_MESES,
              
               @num_datas := 1 + LENGTH(inicioFimTxNew.data_inicio_mds_36_meses) - LENGTH(REPLACE(inicioFimTxNew.data_inicio_mds_36_meses, ',', '')) AS num_datas ,
               SUBSTRING_INDEX(data_inicio_mds_36_meses, ',', 1) AS DATA_INICIO_MDS1_36_MESES,
               IF(@num_datas > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_36_meses, ',', 2), ',', -1), '') AS DATA_INICIO_MDS2_36_MESES,
               IF(@num_datas > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_36_meses, ',', 3), ',', -1), '') AS DATA_INICIO_MDS3_36_MESES, 
               IF(@num_datas > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_36_meses, ',', 4), ',', -1), '') AS DATA_INICIO_MDS4_36_MESES,
               IF(@num_datas > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(data_inicio_mds_36_meses, ',', 5), ',', -1), '') AS DATA_INICIO_MDS5_36_MESES,

               @num_first_mds_fim := 1 + LENGTH(lastMds_36_meses) - LENGTH(REPLACE(inicioFimTxNew.lastMds_36_meses, ',', '')) AS num_first_mds_fim ,
               SUBSTRING_INDEX(inicioFimTxNew.lastMds_36_meses, ',', 1) AS FIM_MDS1_36_MESES,      
               IF(@num_first_mds_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_36_meses, ',', 2), ',', -1), '') AS FIM_MDS2_36_MESES,
               IF(@num_first_mds_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_36_meses, ',', 3), ',', -1), '') AS FIM_MDS3_36_MESES, 
               IF(@num_first_mds_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_36_meses, ',', 4), ',', -1), '') AS FIM_MDS4_36_MESES,
               IF(@num_first_mds_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(inicioFimTxNew.lastMds_36_meses, ',', 5), ',', -1), '') AS FIM_MDS5_36_MESES,
               data_fim_mds_36_meses,
               @num_datas_fim := 1 + LENGTH(data_fim_mds_36_meses) - LENGTH(REPLACE(inicioFimTxNew.data_fim_mds_36_meses, ',', '')) AS num_datas_fim ,
               SUBSTRING_INDEX(data_fim_mds_36_meses, ',', 1) AS DATA_FIM_MDS1_36_MESES,
               IF(@num_datas_fim > 1, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_36_meses, ',', 2), ',', -1), '') AS DATA_FIM_MDS2_36_MESES,
               IF(@num_datas_fim > 2, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_36_meses, ',', 3), ',', -1), '') AS DATA_FIM_MDS3_36_MESES, 
               IF(@num_datas_fim > 3, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_36_meses, ',', 4), ',', -1), '') AS DATA_FIM_MDS4_36_MESES,
               IF(@num_datas_fim > 4, SUBSTRING_INDEX(SUBSTRING_INDEX(data_fim_mds_36_meses, ',', 5), ',', -1), '') AS DATA_FIM_MDS5_36_MESES
               from 
               (
                  select inicioFimTxNew.patient_id,group_concat(inicioFimTxNew.data_inicio_mds ORDER BY inicioFimTxNew.data_inicio_mds ASC) data_inicio_mds_36_meses, group_concat(inicioFimTxNew.firstMds ORDER BY inicioFimTxNew.data_inicio_mds) firstMds_36_meses,group_concat(inicioFimTxNew.data_fim_mds ORDER BY inicioFimTxNew.data_fim_mds) data_fim_mds_36_meses ,group_concat(inicioFimTxNew.lastMds ORDER BY inicioFimTxNew.data_fim_mds) lastMds_36_meses 
                  from 
                  (
                  select inicioFimTxNew.patient_id,inicioFimTxNew.data_inicio_mds,inicioFimTxNew.firstMds,if(inicioFimTxNew.data_fim_mds is null,null,inicioFimTxNew.data_fim_mds ) data_fim_mds,if(inicioFimTxNew.lastMds is null, null,inicioFimTxNew.lastMds) lastMds 
                  from 
                  (
                  select inicioFim.patient_id, 
                         inicioFim.data_inicio_mds, 
                         inicioFim.firstMds,
                         if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 36 month),inicioFim.data_fim_mds,null ) data_fim_mds,
                         if(inicioFim.data_fim_mds is not null and inicioFim.data_fim_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 36 month),inicioFim.lastMds ,null ) lastMds, 
                         tx_new.art_start_date                         from 
          (
          select inicioMds.patient_id,inicioMds.data_inicio_mds,inicioMds.firstMds,fimMds.data_fim_mds,fimMds.lastMds 

          from    
           (  
                select p.patient_id,date(e.encounter_datetime) data_inicio_mds,
                case o.value_coded
                when  165340 then 'DB'
                when  23730 then 'DT' 
                when  165314 then 'DA' 
                when  23888 then 'DS' 
                when  23724 then 'GA'
                when  23726 then 'CA'
                when  165317 then 'TB' 
                when  165320 then 'SMI' 
                when  165321 then 'DAH' 
                when  165178 then 'DCP' 
                when  165179 then 'DCA' 
                when  165318 then 'CT' 
                when  165315 then 'DD' 
                when  165264 then 'BM' 
                when  165265 then 'CM'
                when  23725  then 'AF'
                when  23729  then 'FR'
                when  165176 then 'EH' 
                when  165319 then 'SAAJ'
                when  23727  then 'PU'
                when  165177 then 'FARMAC/Farmácia Privada'
                when  23732  then 'OUTRO'
                end AS firstMds
                from patient p 
                join encounter e on p.patient_id=e.patient_id 
                join obs grupo on grupo.encounter_id=e.encounter_id 
                join obs o on o.encounter_id=e.encounter_id 
                join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                where  e.encounter_type in(6) 
                and e.location_id=:location 
                and o.concept_id=165174  
                and o.voided=0 
                and grupo.concept_id=165323  
                and grupo.voided=0 
                and obsEstado.concept_id=165322  
                and obsEstado.value_coded in(1256) 
                and obsEstado.voided=0  
                and grupo.voided=0 
                and grupo.obs_id=o.obs_group_id 
                and grupo.obs_id=obsEstado.obs_group_id 
                order by date(e.encounter_datetime) ASC
                )inicioMds
              left join
              (
                select p.patient_id,date(e.encounter_datetime) data_fim_mds,
                case o.value_coded
                when  165340 then 'DB'
                when  23730 then 'DT' 
                when  165314 then 'DA' 
                when  23888 then 'DS' 
                when  23724 then 'GA'
                when  23726 then 'CA'
                when  165317 then 'TB' 
                when  165320 then 'SMI' 
                when  165321 then 'DAH' 
                when  165178 then 'DCP' 
                when  165179 then 'DCA' 
                when  165318 then 'CT' 
                when  165315 then 'DD' 
                when  165264 then 'BM' 
                when  165265 then 'CM'
                when  23725  then 'AF'
                when  23729  then 'FR'
                when  165176 then 'EH' 
                when  165319 then 'SAAJ'
                when  23727  then 'PU'
                when  165177 then 'FARMAC/Farmácia Privada'
                when  23732  then 'OUTRO'
                end AS lastMds
                from patient p 
                join encounter e on p.patient_id=e.patient_id 
                join obs grupo on grupo.encounter_id=e.encounter_id 
                join obs o on o.encounter_id=e.encounter_id 
                join obs obsEstado on obsEstado.encounter_id=e.encounter_id 
                where  e.encounter_type in(6) 
                and e.location_id=:location 
                and o.concept_id=165174  
                and o.voided=0 
                and grupo.concept_id=165323  
                and grupo.voided=0 
                and obsEstado.concept_id=165322  
                and obsEstado.value_coded in(1267) 
                and obsEstado.voided=0  
                and grupo.voided=0 
                and grupo.obs_id=o.obs_group_id 
                and grupo.obs_id=obsEstado.obs_group_id
                order by  date(e.encounter_datetime) asc 
              ) fimMds on inicioMds.patient_id=fimMds.patient_id and fimMds.lastMds=inicioMds.firstMds
              )inicioFim
              left join 
             (
                SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                ( 
                SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                AND e.location_id=:location 
                AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                GROUP BY p.patient_id 
                UNION 
                SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id 
                INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                AND e.location_id=:location 
                GROUP BY p.patient_id 
                   ) 
              art_start GROUP BY patient_id 
           )tx_new on tx_new.patient_id=inicioFim.patient_id
           WHERE  inicioFim.data_inicio_mds BETWEEN tx_new.art_start_date  and date_add(tx_new.art_start_date, interval 36 month)
           order by inicioFim.patient_id,inicioFim.data_inicio_mds
           )inicioFimTxNew
           )inicioFimTxNew
           GROUP BY inicioFimTxNew.patient_id
           )inicioFimTxNew      
          )primeiroMds36Meses on primeiroMds36Meses.patient_id=coorteFinal.patient_id

            left join

            (
             select p.patient_id,e.encounter_datetime as mdc_simtomas_tb_36_meses  from patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              inner join obs o on o.encounter_id=e.encounter_id 
              where e.encounter_type in(6) and o.concept_id=23758  and e.location_id=:location and o.value_coded in(1065,1066) 
              and e.voided=0 and p.voided=0 and o.voided=0 
            )tbSinthoms36Meses on tbSinthoms36Meses.patient_id=coorteFinal.patient_id 
            and tbSinthoms36Meses.mdc_simtomas_tb_36_meses BETWEEN date_add(coorteFinal.art_start_date, interval 24 month)  and date_add(coorteFinal.art_start_date, interval 36 month)
            
            
            left join
            (
              select final.patient_id, if(final.value_numeric is not null and (final.A=final.B),'Sim', 'Não') consultas_pb_imc_36_meses from 
              (
             select pbImc36Meses.patient_id,pbImc36Meses.consultas_pb_imc_36_meses consultas_pb_imc_36_meses, pbImc36Meses.concept_id, count(pbImc36Meses.patient_id) A, count(pbImc36Meses.concept_id) B, pbImc36Meses.value_numeric  from 
                 (
                  select  p.patient_id,e.encounter_datetime consultas_pb_imc_36_meses, o.concept_id, e.encounter_id, o.value_numeric from 
                  patient p 
                  left join encounter e on p.patient_id=e.patient_id 
                  left join obs o on e.encounter_id=o.encounter_id and o.concept_id in (1342,1343) and o.voided=0
                  where e.encounter_type=6   and e.location_id=:location and e.voided=0 
                  and p.voided=0 and e.voided=0  and o.voided=0 
                  order by e.encounter_datetime
                  )pbImc36Meses
             
              inner join 
                  (
                  SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                  ( 
                  SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                  WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                  AND e.location_id=:location 
                  AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                  GROUP BY p.patient_id 
                  UNION 
                  SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                  patient p 
                  INNER JOIN encounter e ON p.patient_id=e.patient_id 
                  INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                  WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                  AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                  AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                  AND e.location_id=:location 
                  GROUP BY p.patient_id 
                  ) 
                  art_start 
                  GROUP BY patient_id 
                 ) tx_new  on pbImc36Meses.patient_id=tx_new.patient_id
                  where   pbImc36Meses.consultas_pb_imc_36_meses BETWEEN date_add(tx_new.art_start_date, interval 24 month)  and date_add(tx_new.art_start_date, interval 36 month)
                  group by   pbImc36Meses.patient_id        
              )final
              group by final.patient_id
            )pbImc36Meses on  pbImc36Meses.patient_id=coorteFinal.patient_id

            left join
            (
            SELECT consultas36Meses.patient_id, count(consultas36Meses.todas_consultas_fc_36_meses) total_fc_36_meses
              FROM 
                   
               (
              select p.patient_id,e.encounter_datetime todas_consultas_fc_36_meses  from 
              patient p 
              inner join encounter e on p.patient_id=e.patient_id 
              where e.encounter_type=6 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
              order by p.patient_id
              )consultas36Meses
              inner join 
              (
              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
              ( 
              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
              AND e.location_id=:location 
              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
              GROUP BY p.patient_id 
              UNION 
              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
              patient p 
              INNER JOIN encounter e ON p.patient_id=e.patient_id 
              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
              AND e.location_id=:location 
              GROUP BY p.patient_id 
              ) 
              art_start 
              GROUP BY patient_id 
             ) tx_new  on consultas36Meses.patient_id=tx_new.patient_id

        where  consultas36Meses.todas_consultas_fc_36_meses BETWEEN date_add(tx_new.art_start_date, interval 24 month)  and date_add(tx_new.art_start_date, interval 36 month)
        group by consultas36Meses.patient_id
        )todasConsultasFichaClinica36Meses on todasConsultasFichaClinica36Meses.patient_id=coorteFinal.patient_id
        
                left join
                  ( 
                     SELECT consultas36Meses.patient_id, count(consultas36Meses.todas_consultas_fc_36_meses) total_apss_36_meses
                   FROM 
                           
                       (
                      select p.patient_id,e.encounter_datetime todas_consultas_fc_36_meses  from 
                      patient p 
                      inner join encounter e on p.patient_id=e.patient_id 
                      where e.encounter_type=35 and e.location_id=:location and e.voided=0 and p.voided=0 and e.voided=0
                      order by p.patient_id
                      )consultas36Meses
                      inner join 
                      (
                      SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                      ( 
                      SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                      patient p 
                      INNER JOIN encounter e ON p.patient_id=e.patient_id 
                      INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                      WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                      AND e.location_id=:location 
                      AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                      GROUP BY p.patient_id 
                      UNION 
                      SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                      patient p 
                      INNER JOIN encounter e ON p.patient_id=e.patient_id 
                      INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                      WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                      AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                      AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                      AND e.location_id=:location 
                      GROUP BY p.patient_id 
                      ) 
                      art_start GROUP BY patient_id 
                     ) tx_new  on consultas36Meses.patient_id=tx_new.patient_id

                where consultas36Meses.todas_consultas_fc_36_meses BETWEEN date_add(tx_new.art_start_date, interval 24 month)  and date_add(tx_new.art_start_date, interval 36 month)
                group by consultas36Meses.patient_id
                )todasConsultasFichaApss36Meses on todasConsultasFichaApss36Meses.patient_id=coorteFinal.patient_id and  coorteFinal.tipo_coorte=36

                  left join
                  (
                           Select B7.patient_id, 1 tipo_saida from 
                     (
                      select maxFilaRecepcao.patient_id,max(data_levantamento) data_levantamento,max(data_proximo_levantamento) data_proximo_levantamento, date_add(max(data_proximo_levantamento), INTERVAL 59 day) data_proximo_levantamento60, date_add(tx_new.art_start_date,  interval 36 month) as endDate 
                      from
                      (
                      SELECT tx_new.patient_id,tx_new.art_start_date
                           FROM 
                              (
                              SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                              ( 
                              SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                              WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                              AND e.location_id=:location 
                              AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                              GROUP BY p.patient_id 
                              UNION 
                              SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                              patient p 
                              INNER JOIN encounter e ON p.patient_id=e.patient_id 
                              INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                              WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                              AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                              AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                              AND e.location_id=:location 
                              GROUP BY p.patient_id 
                              ) 
                              art_start GROUP BY patient_id 
                          ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH)
                        )tx_new
                     inner join 
                      (
                      select p.patient_id,o.value_datetime data_levantamento, date_add(o.value_datetime, INTERVAL 30 day)  data_proximo_levantamento 
                      from patient p inner 
                      join encounter e on p.patient_id = e.patient_id 
                      inner join obs o on o.encounter_id = e.encounter_id 
                      inner join obs obsLevantou on obsLevantou.encounter_id= e.encounter_id 
                      where  e.voided = 0 and p.voided = 0 
                      and o.voided = 0 and o.concept_id = 23866 and obsLevantou.concept_id=23865 
                      and obsLevantou.value_coded=1065 and obsLevantou.voided=0 and e.encounter_type=52 and e.location_id=:location 
                      union 
                      select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from 
                      ( 
                      select p.patient_id,e.encounter_datetime as data_levantamento from patient p 
                      inner join encounter e on p.patient_id=e.patient_id where encounter_type=18 
                      and e.location_id=:location 
                      and e.voided=0 and p.voided=0 
                      )fila 
                      inner join obs obs_fila on obs_fila.person_id=fila.patient_id 
                      where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime 
                      ) maxFilaRecepcao on maxFilaRecepcao.patient_id=tx_new.patient_id
                      WHERE maxFilaRecepcao.data_levantamento<=date_add(tx_new.art_start_date,  interval 36 month) 
                      group by patient_id
                      having date_add(max(data_proximo_levantamento), INTERVAL 59 day )< endDate   
                      )B7 
                                   where B7.patient_id not in
                      (
                               select tx_new.patient_id 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 month)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 month)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 12 month)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            and pg.location_id=:location 
                            group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state in (7,8,10)
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded in(1366,1709,1706) and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded in(1366,1709,1706) and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            --group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 month)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 month)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 36 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 36 month)  
                            group by obito.patient_id

                      )
                            union

                               select tx_new.patient_id, 2 tipo_saida 
                               from 
                               (
                               SELECT tx_new.patient_id,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) 
                                    art_start GROUP BY patient_id 
                                ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH)
                            )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_obito) data_obito from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_obito from  
                            ( 
                            select pg.patient_id,ps.start_date data_obito from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW()  
                            --and pg.location_id=:location 
                            group by p.patient_id  
                            ) maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id 
                            where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_obito and pg2.location_id=:location and ps2.state=10 
                            union 
                            select p.patient_id,o.obs_datetime data_obito from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW()  
                            and o.voided=0 and o.concept_id=6272 and o.value_coded=1366 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            select p.patient_id,e.encounter_datetime data_obito from patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 and o.value_coded=1366 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id 
                            union  
                            Select person_id,death_date from person p where p.dead=1 
                            --and p.death_date<=NOW()  
                            )transferido 
                            --group by patient_id 
                            ) obito on obito.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                            select patient_id,encounter_datetime encounter_datetime from  
                            ( 
                            SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) art_start GROUP BY patient_id 
                                 ) tx_new
                              inner join 
                              (
                              select p.patient_id,e.encounter_datetime encounter_datetime 
                              from patient p 
                              inner join encounter e on e.patient_id = p.patient_id 
                              where p.voided = 0 
                              and e.voided = 0 
                              and e.location_id =:location 
                              and e.encounter_type=18 
                              )lev on tx_new.patient_id=lev.patient_id
                              where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 30 month)
                              group by lev.patient_id
                            ) consultaLev 
                            group by patient_id  
                            ) consultaOuARV on obito.patient_id=consultaOuARV.patient_id 
                            where consultaOuARV.encounter_datetime<=obito.data_obito and obito.data_obito <= date_add(tx_new.art_start_date, interval 36 month) group by obito.patient_id

                           union
   
                       select tx_new.patient_id,  3 tipo_saida 
                            from 
                            (
                            SELECT tx_new.patient_id,tx_new.art_start_date
                              FROM 
                                 (
                                 SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                 ( 
                                 SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                 WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                 AND e.location_id=:location 
                                 AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                 GROUP BY p.patient_id 
                                 UNION 
                                 SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                 patient p 
                                 INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                 INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                 WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                 AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                 AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                 AND e.location_id=:location 
                                 GROUP BY p.patient_id 
                                 ) 
                                 art_start GROUP BY patient_id 
                             ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH)
                           )tx_new
                            inner join
                            ( 
                            select patient_id,max(data_suspencao) data_suspencao from  
                            ( 
                            select maxEstado.patient_id,maxEstado.data_suspencao from 
                            ( 
                            select pg.patient_id,ps.start_date data_suspencao from patient p 
                            inner join patient_program pg on p.patient_id=pg.patient_id 
                            inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
                            where pg.voided=0 and ps.voided=0 and p.voided=0 and 
                            pg.program_id=2 
                            --and ps.start_date<=NOW() 
                            and pg.location_id=:location 
                            --group by p.patient_id  
                            )maxEstado 
                            inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id 
                            inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and 
                            ps2.start_date=maxEstado.data_suspencao and pg2.location_id=:location and ps2.state=8 
                            union 
                             select p.patient_id,o.obs_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where e.voided=0 and p.voided=0 
                            --and o.obs_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6272 
                            and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location 
                            --group by p.patient_id 
                            union 
                            select  p.patient_id,e.encounter_datetime data_suspencao from  patient p 
                            inner join encounter e on p.patient_id=e.patient_id 
                            inner join obs o on o.encounter_id=e.encounter_id 
                            where  e.voided=0 and p.voided=0 
                            --and e.encounter_datetime<=NOW() 
                            and o.voided=0 and o.concept_id=6273 
                            and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location 
                            --group by p.patient_id  
                            ) suspenso 
                            --group by patient_id 
                            ) suspenso1 on suspenso1.patient_id=tx_new.patient_id
                            inner join 
                            ( 
                             SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                 FROM 
                                    (
                                    SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                    ( 
                                    SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                    AND e.location_id=:location 
                                    AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                    GROUP BY p.patient_id 
                                    UNION 
                                    SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                    patient p 
                                    INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                    INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                    AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                    AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                    AND e.location_id=:location 
                                    GROUP BY p.patient_id 
                                    ) art_start GROUP BY patient_id 
                                    ) tx_new
                                 inner join 
                                 (
                                 select p.patient_id,e.encounter_datetime encounter_datetime 
                                 from patient p 
                                 inner join encounter e on e.patient_id = p.patient_id 
                                 where p.voided = 0 
                                 and e.voided = 0 
                                 and e.location_id =:location 
                                 and e.encounter_type=18 
                                 )lev on tx_new.patient_id=lev.patient_id
                                 where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 36 month)
                                 group by lev.patient_id
                                   ) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id 
                                  where consultaOuARV.encounter_datetime<=suspenso1.data_suspencao and suspenso1.data_suspencao <= date_add(tx_new.art_start_date, interval 36 month)  group by suspenso1.patient_id
                                  
                             union
         
                             select transferidopara.patient_id, 4 tipo_saida 
                               from 
                               (
                               select transferidopara.patient_id,transferidopara.data_transferidopara
                               from
                               (
                               select transferido.patient_id,max(transferido.data_transferidopara) data_transferidopara
                                  from
                                  (
                                     select maxEstado.patient_id,maxEstado.data_transferidopara
                                     from
                                     (
                                        select
                                        pg.patient_id,
                                        ps.start_date data_transferidopara
                                        from patient p
                                        inner join patient_program pg on p.patient_id = pg.patient_id
                                        inner join patient_state ps on pg.patient_program_id = ps.patient_program_id
                                        where pg.voided = 0
                                        and ps.voided = 0
                                        and p.voided = 0
                                        and pg.program_id = 2
                                        --and ps.start_date <= NOW()
                                        and pg.location_id =:location                               
                                     )maxEstado
                                     inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id
                                     inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id
                                     where pg2.voided = 0
                                     and ps2.voided = 0
                                     and pg2.program_id = 2
                                     and ps2.start_date = maxEstado.data_transferidopara
                                     and pg2.location_id =:location
                                     and ps2.state = 7
                                     union
                                     select
                                     p.patient_id,o.obs_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and o.obs_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6272
                                     and o.value_coded = 1706
                                     and e.encounter_type = 53
                                     and e.location_id =:location
                                     --group by p.patient_id
                                     union
                                     select
                                     p.patient_id,
                                     e.encounter_datetime data_transferidopara
                                     from patient p
                                     inner join encounter e on p.patient_id = e.patient_id
                                     inner join obs o on o.encounter_id = e.encounter_id
                                     where e.voided = 0
                                     and p.voided = 0
                                     --and e.encounter_datetime <=NOW()
                                     and o.voided = 0
                                     and o.concept_id = 6273
                                     and o.value_coded = 1706
                                     and e.encounter_type = 6
                                     and e.location_id =:location
                                     --group by p.patient_id
                                  )transferido
         
                                  inner join 
                                  (
         
                                  SELECT tx_new.patient_id,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                      ) tx_new --WHERE art_start_date BETWEEN  date_sub(date(concat(:year,'-12','-21')), interval 48 MONTH) and   date_sub(date(concat(:year,'-12','-21')), interval 36 MONTH)
                                    )tx_new on tx_new.patient_id=transferido.patient_id
                                    where transferido.data_transferidopara <= date_add(tx_new.art_start_date, interval 36 month)
                                     group by transferido.patient_id
                                    )transferidopara
                                    inner join 
                                    (
                                     SELECT tx_new.patient_id,max(lev.encounter_datetime) encounter_datetime,tx_new.art_start_date
                                       FROM 
                                          (
                                          SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                          ( 
                                          SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                          WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                          AND e.location_id=:location 
                                          AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                          GROUP BY p.patient_id 
                                          UNION 
                                          SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                          patient p 
                                          INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                          INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                          WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                          AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                          AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                          AND e.location_id=:location 
                                          GROUP BY p.patient_id 
                                          ) art_start GROUP BY patient_id 
                                          ) tx_new
                                       inner join 
                                       (
                                       select p.patient_id,e.encounter_datetime encounter_datetime 
                                       from patient p 
                                       inner join encounter e on e.patient_id = p.patient_id 
                                       where p.voided = 0 
                                       and e.voided = 0 
                                       and e.location_id =:location 
                                       and e.encounter_type=18 
                                       )lev on tx_new.patient_id=lev.patient_id
                                       where lev.encounter_datetime<=date_add(tx_new.art_start_date, interval 30 month)
                                       group by lev.patient_id
                                     )lev where lev.encounter_datetime<=transferidopara.data_transferidopara
                                  )transferidopara
                                  inner join
                                  (
                                    select final.patient_id,max(final.data_lev) as data_lev,final.data_ultimo_levantamento,tx_new.art_start_date from
                                     (
                                        select patient_id, data_lev,data_ultimo_levantamento data_ultimo_levantamento from
                                        (
                                           select ultimo_fila.patient_id,ultimo_fila.data_fila data_lev,date_add(obs_fila.value_datetime , interval 1 day ) data_ultimo_levantamento from
                                           (
                                              select
                                              p.patient_id, encounter_datetime data_fila
                                              from patient p
                                              inner join person pe on pe.person_id = p.patient_id
                                              inner join encounter e on e.patient_id = p.patient_id
                                              where p.voided = 0
                                              and pe.voided = 0
                                              and e.voided = 0
                                              and e.encounter_type = 18
                                              and e.location_id =:location
                                           )ultimo_fila
                                    left join encounter ultimo_fila_data_criacao on ultimo_fila_data_criacao.patient_id=ultimo_fila.patient_id 
                                     and ultimo_fila_data_criacao.voided=0 
                                     and ultimo_fila_data_criacao.encounter_type = 18 
                                     and date(ultimo_fila_data_criacao.encounter_datetime) = date(ultimo_fila.data_fila) 
                                     and ultimo_fila_data_criacao.location_id=:location 
                                     left join 
                                     obs obs_fila on obs_fila.person_id=ultimo_fila.patient_id 
                                     and obs_fila.voided=0 
                                     and (date(obs_fila.obs_datetime)=date(ultimo_fila.data_fila)  or (date(ultimo_fila_data_criacao.date_created) = date(obs_fila.date_created) and ultimo_fila_data_criacao.encounter_id = obs_fila.encounter_id )) 
                                     and obs_fila.concept_id=5096 
                                     and obs_fila.location_id=:location 
                                           union
                                           select p.patient_id,value_datetime data_lev,date_add(value_datetime,interval 31 day ) data_ultimo_levantamento
                                           from patient p
                                           inner join person pe on pe.person_id = p.patient_id
                                           inner join encounter e on p.patient_id = e.patient_id
                                           inner join obs o on e.encounter_id = o.encounter_id
                                           where p.voided = 0
                                           and pe.voided = 0
                                           and e.voided = 0
                                           and o.voided = 0
                                           and e.encounter_type = 52
                                           and o.concept_id = 23866
                                           and o.value_datetime is not null
                                           and e.location_id =:location
                                        )ultimo_levantamento
                                        --group by ultimo_levantamento.patient_id
                                        order by ultimo_levantamento.data_lev desc
                                     )final
                                     inner join
                                 (
                                 SELECT tx_new.patient_id,tx_new.art_start_date
                                                FROM 
                                                   (
                                                   SELECT patient_id, MIN(art_start_date) art_start_date FROM 
                                                   ( 
                                                   SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                                                   WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18) 
                                                   AND e.location_id=:location 
                                                   AND e.encounter_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                                   GROUP BY p.patient_id 
                                                   UNION 
                                                   SELECT p.patient_id, MIN(value_datetime) art_start_date FROM 
                                                   patient p 
                                                   INNER JOIN encounter e ON p.patient_id=e.patient_id 
                                                   INNER JOIN obs o ON e.encounter_id=o.encounter_id 
                                                   WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                                                   AND o.concept_id=23866 AND o.value_datetime is NOT NULL 
                                                   AND o.value_datetime <=date_sub(date(concat(:year,'-06','-20')), interval 36 MONTH)
                                                   AND e.location_id=:location 
                                                   GROUP BY p.patient_id 
                                                   ) art_start GROUP BY patient_id 
                                               ) tx_new 
                                             )tx_new on tx_new.patient_id=final.patient_id
                                              where final.data_lev <= date_add(tx_new.art_start_date, interval 36 month) 
                                              --and final.data_ultimo_levantamento<=date_add(tx_new.art_start_date, interval 36 month)
                                              group by final.patient_id                                       
                                         )final on transferidopara.patient_id=final.patient_id

                  ) estadioDePermanencia36Meses on  estadioDePermanencia36Meses.patient_id=coorteFinal.patient_id
            group by coorteFinal.patient_id 
        ) f cross join(SELECT@cnt := 0)demmy