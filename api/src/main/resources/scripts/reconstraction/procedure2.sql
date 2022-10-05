DROP PROCEDURE IF EXISTS `DataRecontructMDS`;
#
CREATE DEFINER=`root`@`localhost` PROCEDURE DataRecontructMDS()
    READS SQL DATA
BEGIN

	DECLARE no_info, no_info1, no_info2 INT;

	DECLARE obsId INT(11);
	DECLARE personId INT(11);
	DECLARE conceptId INT(11);
	DECLARE encounterId INT(11);
	DECLARE obsDatetime DATETIME;
	DECLARE locationId INT(11);
	DECLARE valueCoded INT(11);
	DECLARE creatorId INT(11);
	DECLARE dateCreated DATETIME;
	DECLARE valueText varchar(50);

	DECLARE lastObsId INT(11);


	DECLARE obsIdDC INT(11);
	DECLARE obsIdModo INT(11);

	
	BEGIN 
		/* Modelo diferencidado Dispensa Comunitaria, como Modo de Dispensa
			Dispensa Comunitaria - 23731
			Modo de Dispensa - 165174
		*/
		DECLARE cur_dc CURSOR FOR
				select 	obsDC.obs_id obsIdDC1,
						obsModo.obs_id obsIdModo1,
						obsDC.person_id,
						e.encounter_id,
						e.encounter_datetime,
						e.location_id,
						e.creator,
						e.date_created,
						obsDC.value_coded
				from 	patient p 
						inner join encounter e on p.patient_id=e.patient_id
						inner join temp_location on temp_location.location_id = e.location_id
						inner join obs obsDC on e.encounter_id=obsDC.encounter_id
						left join obs obsModo on e.encounter_id=obsModo.encounter_id and obsModo.concept_id=165174 and obsModo.voided=0
				where 	e.encounter_type in (6,35) and e.voided=0 and obsDC.voided=0 and  obsDC.concept_id=23731;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info1=1;

		SET no_info1 = 0;

		OPEN cur_dc;
		cur_loopdc: WHILE(no_info1 = 0) DO

			FETCH cur_dc INTO obsIdDC,obsIdModo, personId,encounterId,obsDatetime,locationId,creatorId,dateCreated,valueCoded;

			IF no_info1 = 1 THEN
				LEAVE cur_loopdc;
			END IF;	
			
			/*
				Para cada observacao que modelo diferenciado DC:
				1. Criar uma observacao de conveset (165323) pois o modelo actual tem conv
				2. Actualizar o modo dispensa para incluir no convset, o resto desta observacao mantem
				3. Actualizar DC alterando para ser estado do modelo
			*/
			IF obsIdModo is not null THEN
			
				insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid) 
				values (personId,165323,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid());

				
				select LAST_INSERT_ID() into lastObsId;		
				
				update obs set obs_group_id=lastObsId where obs_id=obsIdModo;
				
				update obs set obs_group_id=lastObsId,concept_id=165322 where obs_id=obsIdDC;
			ELSE
				
				insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid) 
				values (personId,165323,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid());
			
				select LAST_INSERT_ID() into lastObsId;		
				
				
				update obs set concept_id=165174, value_coded=23731,obs_group_id=lastObsId where obs_id=obsIdDC;
				
				insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,value_coded,obs_group_id,uuid) 
				values (personId,165322,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',valueCoded,lastObsId,uuid());
			
			END IF;

		END WHILE cur_loopdc;
		CLOSE cur_dc;
		SET no_info1 = 0;
	END;


	BEGIN
		/*Todos modelos diferenciados
			Gaac (GA)	23724
			Abordagem Familiar (AF)	23725
			Clubes de Adesao (CA)	23726
			Paragem Unica (PU)	23727
			Fluxo Rapido (FR)	23729
			Dispensa Trimestral (DT)	23730
			Dispensa Semestral (DT)	23888
			FARMACIA PRIVATA 165177
		*/
		DECLARE cur_mdc CURSOR FOR
				select 	obs_id,
						person_id,
						concept_id ,
						o.encounter_id,
						obs_datetime,
						e.location_id,
						value_coded,
						o.creator,
						o.date_created				
				from 	patient p 
						inner join encounter e on p.patient_id=e.patient_id
						inner join temp_location on temp_location.location_id = e.location_id
						inner join obs o on e.encounter_id=o.encounter_id
				where 	e.encounter_type in (6,35) and e.voided=0 and o.voided=0 and 
						o.concept_id in (23724,23725,23726,23727,23729,23730,23888,165177);

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info=1;

		SET no_info = 0;
		OPEN cur_mdc;
		cur_loop: WHILE(no_info = 0) DO

			FETCH cur_mdc INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueCoded,creatorId,dateCreated;

			IF no_info = 1 THEN
				LEAVE cur_loop;
			END IF;	
			/*
				Para cada observacao que modelo diferenciado:
				1. Criar uma observacao de conveset (165323) pois o modelo actual tem conv
				2. Actualizar o modelo o conceito para 165174 e a resposta deste conceito passou a ser o conceito do modelo
				3. Criar observacao estado do modelo, 165322 e a resposta passa a ser o value_coded 
			*/
			


			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid) 
			values (personId,165323,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid());
			
			select LAST_INSERT_ID() into lastObsId;		
			
			
			IF conceptId = 165177 THEN
				update obs set concept_id=165174, value_coded=165315,obs_group_id=lastObsId where obs_id=obsId;
			ELSE
				update obs set concept_id=165174, value_coded=conceptId,obs_group_id=lastObsId where obs_id=obsId;
			END IF;
			
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,value_coded,obs_group_id,uuid) 
			values (personId,165322,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',valueCoded,lastObsId,uuid());

		END WHILE cur_loop;
		CLOSE cur_mdc;
		SET no_info = 0;
	END;


	BEGIN

		/* Modelo diferencidado Outros - 23732 */
		DECLARE cur_other_dc CURSOR FOR
		        select obsOutroMdc.obs_id,
		                 obsOutroMdc.person_id,
		                 obsOutroMdc.concept_id,
		                 obsOutroMdc.value_text,
		                 e.encounter_id,
		                 e.encounter_datetime,
		                 e.location_id,
		                 e.creator,
		                 e.date_created              
		        from     patient p 
		                 inner join encounter e on p.patient_id=e.patient_id
		                 inner join temp_location on temp_location.location_id = e.location_id
		                 inner join obs obsOutroMdc on e.encounter_id=obsOutroMdc.encounter_id
		                where   e.encounter_type in (6,35) and e.voided=0 and obsOutroMdc.voided=0 
		                 and obsOutroMdc.concept_id=23732;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info2=1;

		SET no_info2 = 0;

		OPEN cur_other_dc;
		cur_loopOther: WHILE(no_info2 = 0) DO

		    FETCH cur_other_dc INTO obsId, personId,conceptId, valueText, encounterId, obsDatetime, locationId, creatorId, dateCreated;

		    IF no_info2 = 1 THEN
		        LEAVE cur_loopOther;
		    END IF; 
		    /*
		        Para cada observacao que modelo diferenciado:
		        1. Criar uma observacao de conveset (165323) pois o modelo actual tem conv
		        2. Actualizar o modelo o conceito 23732 para 165174 e a resposta deste conceito passou a ser o conceito 23732(do modelo)
		    */
		    
		    insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid) 
		    values (personId,165323,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid());
		    
		    select LAST_INSERT_ID() into lastObsId;		
		    
		    update obs set concept_id=165174, value_coded=conceptId,obs_group_id=lastObsId, comments = valueText where obs_id=obsId;
		    
		END WHILE cur_loopOther;
		CLOSE cur_other_dc;
		SET no_info2 = 0;

	END;
	
END
#