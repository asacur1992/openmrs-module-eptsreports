        select distinct elegivelAColheitaCV.patient_id
            from 
            ( 
			select distinct txCurr.patient_id,
            		  consultaLevantamento.data_proxima_consulta,
            		  inicio_real.data_inicio 
			from
				(
			   select patient_id                                                                                                   
             from                                                                                                                    
             (select inicio_fila_seg_prox.*,     
             	    GREATEST(COALESCE(data_proximo_lev,data_recepcao_levantou30), COALESCE(data_recepcao_levantou30,data_proximo_lev)) data_usar 
             from                                                                                                                        
                 (select     inicio_fila_seg.*,                                                                                          
                 max(obs_fila.value_datetime) data_proximo_lev,                                                                          
                 date_add(data_recepcao_levantou, interval 30 day) data_recepcao_levantou30                                              
              from                                                                                                                           
            (select inicio.*,                                                                                                                    
                 saida.data_estado,                                                                                                          
                 max_fila.data_fila,
                 max_recepcao.data_recepcao_levantou                                                                                         
             from                                                                                                                                
             (   
						select patient_id,data_inicio 
						from ( 
							select patient_id,data_inicio from 
							(
								  select patient_id, min(data_inicio) data_inicio 
								  from 
										(
											  select p.patient_id, min(e.encounter_datetime) data_inicio 
											  from patient p 
													inner join encounter e on p.patient_id=e.patient_id 
													inner join obs o on o.encounter_id=e.encounter_id 
											  where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type in (18,6,9) 
													and o.concept_id=1255 and o.value_coded=1256 and e.encounter_datetime <= :startDate and e.location_id=:location 
													group by p.patient_id 
											  union 
											  
											  select p.patient_id, min(value_datetime) data_inicio 
											  from patient p 
													inner join encounter e on p.patient_id=e.patient_id 
													inner join obs o on e.encounter_id=o.encounter_id 
											  where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) 
													and o.concept_id=1190 and o.value_datetime is not null and o.value_datetime<=:startDate and e.location_id=:location 
													group by p.patient_id 
											  
											  union 
								   
											  select pg.patient_id, min(date_enrolled) data_inicio 
											  from patient p 
													inner join patient_program pg on p.patient_id=pg.patient_id 
											  where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:startDate and location_id=:location 
													group by pg.patient_id 
											  
											  union 
							
											  select e.patient_id, min(e.encounter_datetime) as data_inicio 
											  from patient p 
													inner join encounter e on p.patient_id=e.patient_id 
											  where p.voided=0 and e.encounter_type=18 and e.voided=0 and e.encounter_datetime<=:startDate and e.location_id=:location 
													group by p.patient_id 
											  
											  union 
								   
											  select p.patient_id, min(value_datetime) data_inicio 
											  from patient p 
													inner join encounter e on p.patient_id=e.patient_id 
													inner join obs o on e.encounter_id=o.encounter_id 
											  where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 
													and o.concept_id=23866 and o.value_datetime is not null and o.value_datetime<=:startDate and e.location_id=:location 
													group by p.patient_id
										) 
								  art_start group by patient_id 
							) tx_new where data_inicio <= :startDate and data_inicio < '2023-12-21'
							union
							select tx_new.patient_id, tx_new.data_inicio
							from 
							(
								  select tx_new.patient_id, tx_new.data_inicio 
								  from
								  ( 
										select patient_id, data_inicio from 
										(
											  select patient_id, min(data_inicio) data_inicio 
											  from 
													(
														  select e.patient_id, min(e.encounter_datetime) as data_inicio 
														  from patient p 
																inner join encounter e on p.patient_id=e.patient_id 
														  where p.voided=0 and e.encounter_type=18 and e.voided=0 and e.encounter_datetime<=:startDate and e.location_id=:location 
																group by p.patient_id 
														  
														  union 
											   
														  select p.patient_id, min(value_datetime) data_inicio 
														  from patient p 
																inner join encounter e on p.patient_id=e.patient_id 
																inner join obs o on e.encounter_id=o.encounter_id 
														  where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 
																and o.concept_id=23866 and o.value_datetime is not null and o.value_datetime<=:startDate and e.location_id=:location 
																group by p.patient_id
													) 
											  art_start group by patient_id 
										) tx_new where data_inicio <= :startDate and data_inicio >= '2023-12-21'
								  ) tx_new
								  left join
								  (
										select patient_id from 
										(
											  select patient_id, min(data_inicio) data_inicio 
											  from 
													(
														  select p.patient_id, min(e.encounter_datetime) data_inicio 
														  from patient p 
																inner join encounter e on p.patient_id=e.patient_id 
																inner join obs o on o.encounter_id=e.encounter_id 
														  where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type in (18,6,9) 
																and o.concept_id=1255 and o.value_coded=1256 and e.encounter_datetime<=:startDate and e.location_id=:location 
																group by p.patient_id 
														  union 
														  
														  select p.patient_id, min(value_datetime) data_inicio 
														  from patient p 
																inner join encounter e on p.patient_id=e.patient_id 
																inner join obs o on e.encounter_id=o.encounter_id 
														  where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) 
																and o.concept_id=1190 and o.value_datetime is not null and o.value_datetime<=:startDate and e.location_id=:location 
																group by p.patient_id 
														  
														  union 
											   
														  select pg.patient_id, min(date_enrolled) data_inicio 
														  from patient p 
																inner join patient_program pg on p.patient_id=pg.patient_id 
														  where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:startDate and location_id=:location 
																group by pg.patient_id 
													) 
											  art_start group by patient_id 
										) tx_new where data_inicio < '2023-12-21'
								  ) tx_new_period_anterior on tx_new.patient_id = tx_new_period_anterior.patient_id
								   where tx_new_period_anterior.patient_id is null
							) tx_new
						) inicio
              )inicio                                                                                                                                    
              left join                                                                                                                                  
             ( 
                select patient_id,max(data_estado) data_estado                                                                                              
                     from                                                                                                                                        
                         (                                                                                                                                       
                             select distinct max_estado.patient_id, max_estado.data_estado from (                                                                
                                 select  pg.patient_id,                                                                                                          
                                         max(ps.start_date) data_estado                                                                                          
                                 from    patient p                                                                                                               
                                     inner join patient_program pg on p.patient_id = pg.patient_id                                                               
                                     inner join patient_state ps on pg.patient_program_id = ps.patient_program_id                                                
                                 where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id = 2                                        
                                     and ps.start_date<= :startDate and pg.location_id =:location group by pg.patient_id                                           
                             ) max_estado                                                                                                                        
                                 inner join patient_program pp on pp.patient_id = max_estado.patient_id                                                          
                                 inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado         
                             where pp.program_id = 2 and ps.state = 8 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location                 
                             
							 union                                                                                                                               
                             
							 select dead_state.patient_id, dead_state.data_estado 
							 from (
											select patient_id,max(data_estado) data_estado                                                                                              
											from (  
											
											select distinct max_estado.patient_id, max_estado.data_estado 
											from (                                                                
														select  pg.patient_id,                                                                                                          
															max(ps.start_date) data_estado                                                                                          
														from patient p                                                                                                               
															inner join patient_program pg on p.patient_id = pg.patient_id                                                               
															inner join patient_state ps on pg.patient_program_id = ps.patient_program_id                                                
														where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id = 2                                        
															and ps.start_date<= :startDate and pg.location_id =:location group by pg.patient_id                                           
											) max_estado                                                                                                                        
												inner join patient_program pp on pp.patient_id = max_estado.patient_id                                                          
												inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado         
											where pp.program_id = 2 and ps.state = 10 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location  
											 union
											select  p.patient_id,                                                                                                               
												max(o.obs_datetime) data_estado                                                                                             
											from patient p                                                                                                                   
												inner join encounter e on p.patient_id=e.patient_id                                                                         
												inner join obs  o on e.encounter_id=o.encounter_id                                                                          
											where e.voided=0 and o.voided=0 and p.voided=0                                                                
												and e.encounter_type in (53,6) and o.concept_id in (6272,6273) and o.value_coded = 1366                         
												and o.obs_datetime<=:startDate and e.location_id=:location                                                                        
												group by p.patient_id                                                                                                               
											union                                                                                                                               
											select person_id as patient_id,death_date as data_estado                                                                            
											from person                                                                                                                         
											where dead=1 and voided = 0 and death_date is not null and death_date<=:startDate 
											union                                                                                                                               
											select  p.patient_id,                                                                                                               
												max(obsObito.obs_datetime) data_estado                                                                                      
											from patient p                                                                                                                   
												inner join encounter e on p.patient_id=e.patient_id                                                                         
												inner join obs obsObito on e.encounter_id=obsObito.encounter_id                                                             
											where e.voided=0 and p.voided=0 and obsObito.voided=0                                                        
												and e.encounter_type in (21,36,37) and  e.encounter_datetime<=:startDate and  e.location_id=:location                          
												and obsObito.concept_id in (2031,23944,23945) and obsObito.value_coded=1366                                                     
											group by p.patient_id                                                                                                               
									) dead_state group by dead_state.patient_id  
							) dead_state 
							inner join
							(
									select fila_seguimento.patient_id,max(fila_seguimento.data_encountro) data_encountro
									from(
												select p.patient_id,max(encounter_datetime) data_encountro                                                                                                
												from    patient p                                                                                                                                   
														inner join encounter e on e.patient_id=p.patient_id                                                                                         
												where   p.voided=0 and e.voided=0 and e.encounter_type=18                                                                      
														and e.location_id=:location and e.encounter_datetime<=:startDate                                                                                  
														group by p.patient_id  
												union
												
												select  p.patient_id,max(encounter_datetime) data_encountro                                                                                    
												from patient p                                                                                                                                   
													inner join encounter e on e.patient_id=p.patient_id                                                                                         
												where   p.voided=0 and e.voided=0 and e.encounter_type in (6,9)                                                                
													and e.location_id=:location and e.encounter_datetime<=:startDate                                                                                  
													group by p.patient_id   
									) fila_seguimento	group by fila_seguimento.patient_id  
							 ) fila_seguimento on dead_state.patient_id = fila_seguimento.patient_id
								where fila_seguimento.data_encountro is null or  fila_seguimento.data_encountro <= dead_state.data_estado
							 
							 union
							 
							 select  p.patient_id,                                                                                                               
                                     max(o.obs_datetime) data_estado                                                                                             
                             from    patient p                                                                                                                   
                                     inner join encounter e on p.patient_id=e.patient_id                                                                         
                                     inner join obs  o on e.encounter_id=o.encounter_id                                                                          
                             where   e.voided=0 and o.voided=0 and p.voided=0 and                                                              
                                     e.encounter_type in (53,6) and o.concept_id in (6272,6273) and o.value_coded = 1709 and                        
                                     o.obs_datetime<=:startDate and e.location_id=:location                                                                        
                             group by p.patient_id                                                                                                               
                             union
 					         select saidas_por_transferencia.patient_id, data_estado 
	                         from
	                        	(
		                            select saidas_por_transferencia.patient_id, max(data_estado) data_estado
		                            from
		                                (
			                           	select distinct max_estado.patient_id, max_estado.data_estado 
			                           	from 
			                           		(                                                                
				                                 select pg.patient_id, max(ps.start_date) data_estado                                                                                          
				                                 from patient p                                                                                                               
				                                 		inner join patient_program pg on p.patient_id = pg.patient_id                                                               
				                                     	inner join patient_state ps on pg.patient_program_id = ps.patient_program_id                                                
				                                 where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id = 2                                        
				                                 		and ps.start_date<= :startDate and pg.location_id =:location group by pg.patient_id                                           
			                             		) max_estado                                                                                                                        
			                                 		inner join patient_program pp on pp.patient_id = max_estado.patient_id                                                          
			                                 		inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado         
			                             		where pp.program_id = 2 and ps.state = 7 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location                 
			                             
			                             		union                                                                                                                               
			                             		
			                             		select  p.patient_id,max(o.obs_datetime) data_estado                                                                                             
			                             		from patient p                                                                                                                   
			                                     	inner join encounter e on p.patient_id=e.patient_id                                                                         
			                                     	inner join obs o on e.encounter_id=o.encounter_id                                                                          
			                             		where e.voided=0 and o.voided=0 and p.voided=0                                                                
			                                   	and e.encounter_type in (53,6) and o.concept_id in (6272,6273) and o.value_coded = 1706                         
			                                     	and o.obs_datetime<=:startDate and e.location_id=:location                                                                        
			                             			group by p.patient_id                                                                                                               
			                             		
			                             	     union                                                                                                                               
				                             
				                             	select ultimaBusca.patient_id, ultimaBusca.data_estado                                                                              
				                             	from (                                                                                                                              
				                                     select p.patient_id,max(e.encounter_datetime) data_estado                                                                   
				                                     from patient p                                                                                                              
				                                         inner join encounter e on p.patient_id=e.patient_id                                                                     
				                                         inner join obs o on o.encounter_id=e.encounter_id                                                                       
				                                     where e.voided=0 and p.voided=0 and e.encounter_datetime<= :startDate                                       
				                                         and e.encounter_type = 21 and  e.location_id= :location                                                                 
				                                         group by p.patient_id                                                                                                   
				                                 ) ultimaBusca                                                                                                                   
				                                     inner join encounter e on e.patient_id = ultimaBusca.patient_id                                                             
				                                     inner join obs o on o.encounter_id = e.encounter_id                                                                         
				                                where e.encounter_type = 21 and o.voided=0 and o.concept_id=2016 and o.value_coded in (1706,23863) and ultimaBusca.data_estado = e.encounter_datetime and e.location_id = :location 
		                                ) saidas_por_transferencia 
	                                	group by patient_id 
                               	) saidas_por_transferencia
                                left join
			                 	(  
				                    select patient_id, max(data_ultimo_levantamento)  data_ultimo_levantamento    
				                    from
				                    (
		                        		select p.patient_id, date_add(max(o.value_datetime), interval 1 day) data_ultimo_levantamento                                                                                            
										from patient p                                                                                                                                   
											inner join encounter e on e.patient_id= p.patient_id 
											inner join obs o on o.encounter_id = e.encounter_id                                                                                        
										where p.voided= 0 and e.voided=0 and o.voided = 0 and e.encounter_type=18 and o.concept_id = 5096                                                                  
											and e.location_id=:location and e.encounter_datetime <= :startDate                                                                               
											group by p.patient_id 
		                        
		                        		union
		                        
			                        	select p.patient_id, date_add(max(value_datetime), interval 31 day) data_ultimo_levantamento                                                                                     
			                        	from patient p                                                                                                                                   
			                         	  inner join encounter e on p.patient_id=e.patient_id                                                                                         
			                              inner join obs o on e.encounter_id=o.encounter_id                                                                                           
			                        	where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52                                                       
			                              and o.concept_id=23866 and o.value_datetime is not null and e.location_id=:location and o.value_datetime <= :startDate                                                                                        
			                        	group by p.patient_id
				                    	) ultimo_levantamento group by patient_id
			                		) ultimo_levantamento on saidas_por_transferencia.patient_id = ultimo_levantamento.patient_id 
			               		where ultimo_levantamento.data_ultimo_levantamento <= :startDate
				                                                                                                                                                                         
                         	) allSaida                                                                                                                                      
                 				group by patient_id 
                
              		) saida on inicio.patient_id = saida.patient_id                                                                                                      
             left join                                                                                                                                           
              ( select p.patient_id,max(encounter_datetime) data_fila                                                                                                
             from    patient p                                                                                                                                   
                     inner join encounter e on e.patient_id=p.patient_id                                                                                         
             where   p.voided=0 and e.voided=0 and e.encounter_type=18 and                                                                     
                     e.location_id=:location and e.encounter_datetime<=:startDate                                                                                  
             group by p.patient_id                                                                                                                               
             ) max_fila on inicio.patient_id=max_fila.patient_id  
              left join                                                                                                                                          
              (                                                                                                                                                  
             select  p.patient_id,max(value_datetime) data_recepcao_levantou                                                                                     
             from    patient p                                                                                                                                   
                     inner join encounter e on p.patient_id=e.patient_id                                                                                         
                     inner join obs o on e.encounter_id=o.encounter_id                                                                                           
             where   p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and                                                      
                     o.concept_id=23866 and o.value_datetime is not null and                                                                                     
                     o.value_datetime<=:startDate and e.location_id=:location                                                                                      
             group by p.patient_id                                                                                                                               
              ) max_recepcao on inicio.patient_id=max_recepcao.patient_id                                                                                        
              group by inicio.patient_id                                                                                                                         
             ) inicio_fila_seg
             left join                                                                                                                                          
              encounter ultimo_fila_data_criacao on ultimo_fila_data_criacao.patient_id=inicio_fila_seg.patient_id                                                                                      
             	and ultimo_fila_data_criacao.voided=0                                     
               	and ultimo_fila_data_criacao.encounter_type = 18  
              	and ultimo_fila_data_criacao.encounter_datetime = inicio_fila_seg.data_fila                                                                                            
              	and ultimo_fila_data_criacao.location_id=:location                     
              left join                                                                                                                                          
              obs obs_fila on obs_fila.person_id=inicio_fila_seg.patient_id                                                                                      
               	and obs_fila.voided=0                                                                                                                             
              	and (obs_fila.obs_datetime=inicio_fila_seg.data_fila  or (ultimo_fila_data_criacao.date_created = obs_fila.date_created and ultimo_fila_data_criacao.encounter_id = obs_fila.encounter_id ))                                                                                                       
              	and obs_fila.concept_id=5096                                                                                                                       
              	and obs_fila.location_id=:location                                                                                                                 
             group by inicio_fila_seg.patient_id                                                                                                                 
             ) inicio_fila_seg_prox                                                                                                                              
             group by patient_id                                                                                                                                 
             ) coorte12meses_final                                                                                                                               
             where (data_estado is null or (data_estado is not null and  data_fila > data_estado)) and date_add(data_usar, interval 28 day) >=:startDate
            	)txCurr
            	inner join
			(
            	select 	consultaLevantamento.patient_id, 
            			consultaLevantamento.data_proxima_consulta
            	from 
			( 
            		select patient_id,data_consulta,max(data_proxima_consulta) data_proxima_consulta 
            		from 
            		( 
            			Select 	ultimavisita.patient_id,ultimavisita.encounter_datetime data_consulta ,o.value_datetime data_proxima_consulta 
            			from 
            				(	select 	p.patient_id,max(encounter_datetime) as encounter_datetime 
            					from 	encounter e 
            							inner join patient p on p.patient_id=e.patient_id 
            					where 	e.voided=0 and p.voided=0 and e.encounter_type=18 and e.location_id=:location and e.encounter_datetime<=:startDate 
            					group by p.patient_id 
            				) ultimavisita 
            				inner join encounter e on e.patient_id=ultimavisita.patient_id 
            				inner join obs o on o.encounter_id=e.encounter_id 
            			where 	o.concept_id=5096 and o.voided=0 and e.encounter_datetime=ultimavisita.encounter_datetime and 
            					e.encounter_type=18 and e.location_id=:location and o.value_datetime between :startDate and :endDate 
            			union 

            			select patient_id, data_levantamento, max(data_levantamento + INTERVAL 30 day) data_proximo_levantamento from (
            			Select 	p.patient_id,value_datetime data_levantamento
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and 
            					o.concept_id=23866 and o.value_datetime is not null and 
            					o.value_datetime<= :startDate and e.location_id=:location 
            					order by p.patient_id, value_datetime desc
            					) maxFicha where (data_levantamento + INTERVAL 30 day) between  :startDate and :endDate 
            					group by maxFicha.patient_id
            			
            			union 
            			Select 	ultimavisita.patient_id,ultimavisita.encounter_datetime,o.value_datetime 
            			from 
            				(	select 	p.patient_id,max(encounter_datetime) as encounter_datetime 
            					from 	encounter e 
            							inner join patient p on p.patient_id=e.patient_id 
            					where 	e.voided=0 and p.voided=0 and e.encounter_type in (6,9) and e.location_id=:location and e.encounter_datetime<=:startDate 
            					group by p.patient_id 
            				) ultimavisita 
            				inner join encounter e on e.patient_id=ultimavisita.patient_id 
            				left join obs o on o.encounter_id=e.encounter_id and o.concept_id=1410 and o.voided=0 
            			where  	e.encounter_datetime=ultimavisita.encounter_datetime and 
            					e.encounter_type in (6,9) and e.location_id=:location and o.value_datetime between :startDate and :endDate 
            			group by ultimavisita.patient_id 
            		) consultaRecepcao 
            		group by patient_id 
            	) consultaLevantamento 
            	) consultaLevantamento on consultaLevantamento.patient_id = txCurr.patient_id
            	inner join 
            	(	Select patient_id,min(data_inicio) data_inicio 
            		from 
            			( 
            				Select 	p.patient_id,min(e.encounter_datetime) data_inicio 
            				from 	patient p 
            						inner join encounter e on p.patient_id=e.patient_id 
            						inner join obs o on o.encounter_id=e.encounter_id 
            				where 	e.voided=0 and o.voided=0 and p.voided=0 and 
            						e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and 
            						e.encounter_datetime<=:startDate and e.location_id=:location 
            				group by p.patient_id 
            				union 
            				Select 	p.patient_id,min(value_datetime) data_inicio 
            				from 	patient p 
            						inner join encounter e on p.patient_id=e.patient_id 
            						inner join obs o on e.encounter_id=o.encounter_id 
            				where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and 
            						o.concept_id=1190 and o.value_datetime is not null and 
            						o.value_datetime<=:startDate and e.location_id=:location 
            				group by p.patient_id 
            				union 
            				select 	pg.patient_id,min(date_enrolled) data_inicio 
            				from 	patient p inner join patient_program pg on p.patient_id=pg.patient_id 
            				where 	pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:startDate and location_id=:location 
            				group by pg.patient_id 
            				union 
            				  SELECT 	e.patient_id, MIN(e.encounter_datetime) AS data_inicio 
            				  FROM 		patient p 
            							inner join encounter e on p.patient_id=e.patient_id 
            				  WHERE		p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:startDate and e.location_id=:location 
            				  GROUP BY 	p.patient_id 
            				union 
            				Select 	p.patient_id,min(value_datetime) data_inicio 
            				from 	patient p 
            						inner join encounter e on p.patient_id=e.patient_id 
            						inner join obs o on e.encounter_id=o.encounter_id 
            				where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and 
            						o.concept_id=23866 and o.value_datetime is not null and 
            						o.value_datetime<=:startDate and e.location_id=:location 
            				group by p.patient_id 
            			) inicio 
            		group by patient_id 
            	) inicio_real on txCurr.patient_id=inicio_real.patient_id 
            	inner join(
                   select maxNextConsultaOrLevantamento.patient_id from(
            		select patient_id,max(data_proxima_consulta) data_proxima_consulta 
            		from 
            		( 
            			Select 	ultimavisita.patient_id,ultimavisita.encounter_datetime data_consulta ,o.value_datetime data_proxima_consulta 
            			from 
            				(	select 	p.patient_id,max(encounter_datetime) as encounter_datetime 
            					from 	encounter e 
            							inner join patient p on p.patient_id=e.patient_id 
            					where 	e.voided=0 and p.voided=0 and e.encounter_type=18 and e.location_id=:location and e.encounter_datetime<=:startDate 
            					group by p.patient_id 
            				) ultimavisita 
            				inner join encounter e on e.patient_id=ultimavisita.patient_id 
            				inner join obs o on o.encounter_id=e.encounter_id 
            			where 	o.concept_id=5096 and o.voided=0 and e.encounter_datetime=ultimavisita.encounter_datetime and 
            					e.encounter_type=18 and e.location_id=:location and o.value_datetime between :startDate and :endDate 
            			union 

            			select patient_id, data_levantamento, max(data_levantamento + INTERVAL 30 day) data_proximo_levantamento from (
            			Select 	p.patient_id,value_datetime data_levantamento
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and 
            					o.concept_id=23866 and o.value_datetime is not null and 
            					o.value_datetime<= :startDate and e.location_id=:location 
            					order by p.patient_id, value_datetime desc
            					) maxFicha where (data_levantamento + INTERVAL 30 day) between  :startDate and :endDate 
            					group by maxFicha.patient_id
            			
            			union 
            			Select 	ultimavisita.patient_id,ultimavisita.encounter_datetime,o.value_datetime 
            			from 
            				(	select 	p.patient_id,max(encounter_datetime) as encounter_datetime 
            					from 	encounter e 
            							inner join patient p on p.patient_id=e.patient_id 
            					where 	e.voided=0 and p.voided=0 and e.encounter_type in (6,9) and e.location_id=:location and e.encounter_datetime<=:startDate 
            					group by p.patient_id 
            				) ultimavisita 
            				inner join encounter e on e.patient_id=ultimavisita.patient_id 
            				left join obs o on o.encounter_id=e.encounter_id and o.concept_id=1410 and o.voided=0 
            			where  	e.encounter_datetime=ultimavisita.encounter_datetime and 
            					e.encounter_type in (6,9) and e.location_id=:location and o.value_datetime between :startDate and :endDate 
            			group by ultimavisita.patient_id 
            		) consultaRecepcao 
            		group by patient_id 
              )maxNextConsultaOrLevantamento
	left join 
            	( 
            			select maxCarga.patient_id,maxCarga.data_carga,o.concept_id,if(o.concept_id=856,o.value_numeric,'Indetectavel') valor_carga 
            		from 
            		( 
            			Select 	p.patient_id,max(date(o.obs_datetime)) data_carga
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and 
            					o.concept_id in (1305,856) and date(o.obs_datetime) <=:endDate and e.location_id=:location 
            			group by p.patient_id 
            		)maxCarga 
            		inner join encounter e on maxCarga.patient_id=e.patient_id 
            		inner join obs o on o.encounter_id=e.encounter_id 
            		where 	date(o.obs_datetime)=maxCarga.data_carga and 
            				o.concept_id in (1305,856) and e.encounter_type in (13,6,9,53,51) and e.location_id=:location and 
            				e.voided=0 and o.voided=0 
            	) carga_viral on maxNextConsultaOrLevantamento.patient_id=carga_viral.patient_id
				where
            		( 
            			(carga_viral.concept_id=1305 and date(carga_viral.data_carga) < date_add(maxNextConsultaOrLevantamento.data_proxima_consulta, interval -12 MONTH)) 
            			or 
            			(carga_viral.concept_id=856 and carga_viral.valor_carga<1000  and date(carga_viral.data_carga) < date_add(maxNextConsultaOrLevantamento.data_proxima_consulta, interval -12 MONTH)) 
            			or 
            			(carga_viral.concept_id=856 and carga_viral.valor_carga>=1000  and date(carga_viral.data_carga) < date_add(maxNextConsultaOrLevantamento.data_proxima_consulta, interval -3 MONTH)) 
            		) group by maxNextConsultaOrLevantamento.patient_id
            		
            		union

              select patient_id from patient p where p.voided = 0 and p.patient_id not in (
			Select 	p.patient_id 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and 
            					o.concept_id in (856,1305) and 
            					date(o.obs_datetime)<=:endDate and e.location_id=:location 
            					) 
            	) cargaViral on cargaViral.patient_id = txCurr.patient_id
            	left join 
            	( 
            		select patient_id,min(data_gravida) dataGravidaLactante 
            		from 
            		( 
            			Select 	p.patient_id,e.encounter_datetime data_gravida 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1982 and value_coded=1065 and 
            					e.encounter_type in (5,6) and e.encounter_datetime  between date_add(:endDate, interval -9 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id,e.encounter_datetime data_gravida 
            			from 	patient p inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1279 and 
            					e.encounter_type in (5,6) and e.encounter_datetime between date_add(:endDate, interval -9 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id,e.encounter_datetime data_gravida 
            			from 	patient p inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1600 and 
            					e.encounter_type in (5,6) and e.encounter_datetime between date_add(:endDate, interval -9 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id,e.encounter_datetime data_gravida 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6331 and 
            					e.encounter_type in (5,6) and e.encounter_datetime between date_add(:endDate, interval -9 month) AND :endDate and e.location_id=:location 
            			union 
            			select 	pp.patient_id,pp.date_enrolled data_gravida 
            			from 	patient_program pp 
            			where 	pp.program_id=8 and pp.voided=0 and 
            					pp.date_enrolled between date_add(:endDate, interval -9 month) AND :endDate and pp.location_id=:location 
            			union 
            			Select 	p.patient_id,obsART.value_datetime data_gravida 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            					inner join obs obsART on e.encounter_id=obsART.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=1982 and o.value_coded=1065 and 
            					e.encounter_type=53 and obsART.value_datetime between date_add(:endDate, interval -9 month) AND :endDate and e.location_id=:location and 
            					obsART.concept_id=1190 and obsART.voided=0 
            			union 
            			select p.patient_id,data_colheita.value_datetime data_gravida from patient p 
            				inner join encounter e on p.patient_id=e.patient_id 
            				inner join obs o on e.encounter_id=o.encounter_id 
            				inner join obs data_colheita on data_colheita.encounter_id = e.encounter_id 
            			where p.voided=0 and e.voided=0 and o.voided=0 and data_colheita.voided = 0 and o.concept_id=1982 and o.value_coded = 1065 and  e.encounter_type=51 
            			  and data_colheita.concept_id =23821 and data_colheita.value_datetime between date_add(:endDate, interval -18 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id,o.value_datetime data_parto 
            			from 	patient p inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=5599 and 
            					e.encounter_type in (5,6) and o.value_datetime between date_add(:endDate, interval -18 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id, e.encounter_datetime data_parto 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6332 and value_coded=1065 and 
            					e.encounter_type=6 and e.encounter_datetime between date_add(:endDate, interval -18 month) AND :endDate and e.location_id=:location 
            			union 
            			Select 	p.patient_id, obsART.value_datetime data_parto 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            					inner join obs obsART on e.encounter_id=obsART.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=6332 and o.value_coded=1065 and 
            					e.encounter_type=53 and e.location_id=:location and 
            					obsART.value_datetime between date_add(:endDate, interval -18 month) AND :endDate and 
            					obsART.concept_id=1190 and obsART.voided=0 
            			union 
            			Select 	p.patient_id, e.encounter_datetime data_parto 
            			from 	patient p 
            					inner join encounter e on p.patient_id=e.patient_id 
            					inner join obs o on e.encounter_id=o.encounter_id 
            			where 	p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6332 and 
            					e.encounter_type in (5,6) and e.encounter_datetime between date_add(:endDate, interval -18 month) AND :endDate and e.location_id=:location 
            			union 
            			select 	pg.patient_id,ps.start_date data_parto 
            			from 	patient p 
            					inner join patient_program pg on p.patient_id=pg.patient_id 
            					inner join patient_state ps on pg.patient_program_id=ps.patient_program_id 
            			where 	pg.voided=0 and ps.voided=0 and p.voided=0 and 
            					pg.program_id=8 and ps.state=27 and 
            					ps.start_date between date_add(:endDate, interval -18 month) AND :endDate and location_id=:location 
            		     union 
                           select p.patient_id,data_colheita.value_datetime data_parto from patient p 
            				inner join encounter e on p.patient_id=e.patient_id 
            				inner join obs o on e.encounter_id=o.encounter_id 
            				inner join obs data_colheita on data_colheita.encounter_id = e.encounter_id 
            			where p.voided=0 and e.voided=0 and o.voided=0 and data_colheita.voided = 0 and o.concept_id=6332 and o.value_coded = 1065 and  e.encounter_type=51 
            						  and data_colheita.concept_id =23821 and data_colheita.value_datetime between date_add(:endDate, interval -18 month) AND :endDate and e.location_id=:location 
            		) lactante_real 
            		inner join person pe on pe.person_id=lactante_real.patient_id and pe.voided=0 and pe.gender='F' 
            		group by lactante_real.patient_id 
            	) gravida on txCurr.patient_id=gravida.patient_id 
            	where gravida.patient_id is null and timestampdiff(month,inicio_real.data_inicio,consultaLevantamento.data_proxima_consulta)>= 6 and consultaLevantamento.data_proxima_consulta between :startDate and :endDate
            ) elegivelAColheitaCV 
            group by elegivelAColheitaCV.patient_id