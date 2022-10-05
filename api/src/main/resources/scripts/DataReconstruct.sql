-- ----------------------------
-- Table structure for temp_location
-- this table stores the location Ids electected to Data Reconstruction
-- ----------------------------
DROP TABLE IF EXISTS `temp_location`;
CREATE TABLE `temp_location` (
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`)
);

-- ----------------------------
-- Procedure structure for FillTEMPLOCATION
-- ----------------------------
DROP PROCEDURE IF EXISTS `FillTEMPLOCATION`;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FillTEMPLOCATION`()
    READS SQL DATA
BEGIN

DECLARE eptsLocations varchar(200);

truncate table temp_location;

select property_value into eptsLocations  
from global_property 
where property='eptsreports.datareconstructionlocationids';

WHILE LOCATE(',',eptsLocations) > 0 DO
    INSERT INTO temp_location(location_id) SELECT SUBSTRING_INDEX(eptsLocations,',',1);
    SET eptsLocations = REPLACE (eptsLocations, (SELECT LEFT(eptsLocations,LOCATE(',',eptsLocations))),'');
END WHILE;
IF eptsLocations <> '' THEN
    INSERT INTO temp_location(location_id) VALUES(eptsLocations);
END IF;

END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `DataRecontructMDS`;
DELIMITER ;;
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
	
END;;
DELIMITER ;


DROP PROCEDURE IF EXISTS DataRecontructTPTFichaClinica;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE DataRecontructTPTFichaClinica()
    READS SQL DATA
BEGIN

DECLARE no_info, no_info1, no_info2 INT;

DECLARE obsId INT(11);
DECLARE obsIdOutrasPrescricoes3HP INT(11);
DECLARE personId INT(11);
DECLARE conceptId INT(11);
DECLARE encounterId INT(11);
DECLARE obsDatetime DATETIME;
DECLARE locationId INT(11);
DECLARE valueCoded INT(11);
DECLARE creatorId INT(11);
DECLARE dateCreated DATETIME;
DECLARE valueDate DATETIME;
DECLARE obsOnDate INT(11);

DECLARE obsCommentsCreated varchar(50);
DECLARE obsCommentsUpdated varchar(50);

SET obsCommentsCreated = 'DATA RECONSTRUCTED - CREATED';
SET obsCommentsUpdated = 'DATA RECONSTRUCTED - UPDATED';

	BEGIN
	 /*
		Profilaxia com Isoniazida (6122)  – Answers (1256 – Inicio, 1257 – Continua, 1267 – Fim) , combinado com Outras prescrições (1719), Answer - 3HP (23954);
	 */

		DECLARE cur_isoniaZidaWith3HP CURSOR FOR
			select 	izoniazida.obs_id,
					outrasPrescricoes3HP.obs_id,
					e.encounter_id				
			from 	patient p 
					inner join encounter e on p.patient_id=e.patient_id
					inner join temp_location on temp_location.location_id = e.location_id
					inner join obs izoniazida on e.encounter_id=izoniazida.encounter_id
					inner join obs outrasPrescricoes3HP on e.encounter_id= outrasPrescricoes3HP.encounter_id
			where 	e.encounter_type=6 and e.voided=0 and izoniazida.voided=0 and outrasPrescricoes3HP.voided =0 
					and izoniazida.concept_id=6122 and izoniazida.value_coded in (1256,1257,1267)
					and outrasPrescricoes3HP.concept_id = 1719 and outrasPrescricoes3HP.value_coded =23954;

			DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info=1;

			SET no_info = 0;

			OPEN cur_isoniaZidaWith3HP;
			cur_loop_isoniaZidaWith3HP: WHILE(no_info = 0) DO

			    FETCH cur_isoniaZidaWith3HP INTO obsId, obsIdOutrasPrescricoes3HP,encounterId;

			    IF no_info = 1 THEN
					LEAVE cur_loop_isoniaZidaWith3HP;
				END IF;	

				/**
				 *   Outras prescrições 3HP passa a ser regime TPT e o estado da Isoniazida passa a ser estado do regime TPT (3HP)
				 *  
				 * */
				
				update obs set concept_id= 23985, comments = obsCommentsUpdated where obs_id=obsIdOutrasPrescricoes3HP and encounter_id =encounterId ;
				update obs set concept_id=165308, comments = obsCommentsUpdated where obs_id=obsId and encounter_id =encounterId ;

				
			END WHILE cur_loop_isoniaZidaWith3HP;
			CLOSE cur_isoniaZidaWith3HP;

			SET no_info = 0;		
	END;


	BEGIN
		/* 	
			Profilaxia com Insoniazida (6122) – Answers (1256 – Inicio, 1257 – Continua, 1267 – Fim) sem outras Prescriçoes 3HP
		*/
		DECLARE cur_inhcoded CURSOR FOR
			select 	o.obs_id,
					o.person_id,
					o.concept_id ,
					o.encounter_id,
					e.encounter_datetime,
					e.location_id,
					o.value_coded,
					o.creator,
					o.date_created				
			from 	patient p 
					inner join encounter e on p.patient_id=e.patient_id
					inner join temp_location on temp_location.location_id = e.location_id
					inner join obs o on e.encounter_id=o.encounter_id
					left join obs outrasPrescricoes3HP on (e.encounter_id= outrasPrescricoes3HP.encounter_id and outrasPrescricoes3HP.voided =0 and outrasPrescricoes3HP.concept_id = 1719 and outrasPrescricoes3HP.value_coded =23954)
			 where 	e.encounter_type=6 and e.voided=0 and o.voided=0 and 
					o.concept_id=6122 and o.value_coded in (1256,1257,1267) and outrasPrescricoes3HP.obs_id is null;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info1 = 1;

		SET no_info1 = 0;


		OPEN cur_inhcoded;
		cur_simINH: WHILE(no_info1 = 0) DO

	    	FETCH cur_inhcoded INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueCoded,creatorId,dateCreated;

		    IF no_info1 = 1 THEN
				LEAVE cur_simINH;
			END IF;	
		
			/*
				1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH)
			*/
			
			select count(*) into obsOnDate
			from obs 
			where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
			
			if obsOnDate=0 then
				insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
				values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);
			end if;		
			
			/*
				2.	Actualizar a observação 6122 para o Estado da Profilaxia (165308) 
			*/		
			update obs set concept_id=165308,comments = obsCommentsUpdated where obs_id=obsId;	

		END WHILE cur_simINH;
		CLOSE cur_inhcoded;

		SET no_info1=0;

	END;


	BEGIN
		/*
			Tratamento Prescrito (1719) – Answers (23954 – 3HP)
		*/

		DECLARE cur_3hp CURSOR FOR
			select 	o.obs_id,
					o.encounter_id	
			from 	patient p 
					inner join encounter e on p.patient_id=e.patient_id
					inner join temp_location on temp_location.location_id = e.location_id
					inner join obs o on e.encounter_id=o.encounter_id
					left join obs izoniazida on (e.encounter_id=izoniazida.encounter_id and izoniazida.voided = 0 and izoniazida.concept_id=6122 and izoniazida.value_coded in (1256,1257,1267))
			where 	e.encounter_type=6 and e.voided=0 and o.voided=0 and 
					o.concept_id=1719 and o.value_coded=23954 and izoniazida.obs_id is  null;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info2=1;

		SET no_info2 = 0;


		OPEN cur_3hp;
		cur_loop: WHILE(no_info2 = 0) DO

		    FETCH cur_3hp INTO obsId, encounterId;

		    IF no_info2 = 1 THEN
				LEAVE cur_loop;
			END IF;	

			/*
			   Actualizar a observação 1719 para o Profilaxia TPT (23985) e a resposta mantém-se 3HP (23954)
			*/	
			update obs set concept_id=23985, comments = obsCommentsUpdated where obs_id=obsId and encounter_id = encounterId;
			
		END WHILE cur_loop;
		CLOSE cur_3hp;

		SET no_info2 = 0;

	END;

end;;
DELIMITER ;


DROP PROCEDURE IF EXISTS DataRecontructTPTFichaResumo;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE DataRecontructTPTFichaResumo()
    READS SQL DATA
BEGIN

DECLARE no_info, no_info1, no_info2, no_info3 INT;

DECLARE obsId INT(11);
DECLARE personId INT(11);
DECLARE conceptId INT(11);
DECLARE encounterId INT(11);
DECLARE obsDatetime DATETIME;
DECLARE locationId INT(11);
DECLARE valueCoded INT(11);
DECLARE creatorId INT(11);
DECLARE dateCreated DATETIME;
DECLARE valueDate DATETIME;
DECLARE obsOnDate INT(11);

DECLARE obsCommentsCreated varchar(50);
DECLARE obsCommentsUpdated varchar(50);

SET obsCommentsCreated = 'DATA RECONSTRUCTED - CREATED';
SET obsCommentsUpdated = 'DATA RECONSTRUCTED - UPDATED';

BEGIN

	/* 	
		Data Inicio ConceptID=6128
		Nota: Aqui estamos a tratar a versao inicial da Ficha Resumo. Data Inicio sem ter Profilaxia TPT
	*/
	DECLARE cur_DataInicioResumo CURSOR FOR
		
		select 
			obs_id,
			person_id,
			concept_id ,
			encounter_id,
			encounter_datetime,
			location_id,
			value_datetime,
			creator,
			date_created
		from 
			(
				select 	o.obs_id,
						o.person_id,
						o.concept_id ,
						o.encounter_id,
						e.encounter_datetime,
						e.location_id,
						o.value_datetime,
						o.creator,
						o.date_created,
						obsProfilaxia.obs_id as obsIdProfilaxia
				from 	patient p 
						inner join encounter e on p.patient_id=e.patient_id
						inner join temp_location on temp_location.location_id = e.location_id
						inner join obs o on e.encounter_id=o.encounter_id
						left join obs obsProfilaxia on obsProfilaxia.encounter_id=e.encounter_id and 
									obsProfilaxia.voided=0 and obsProfilaxia.concept_id=23985
				where 	e.encounter_type=53 and e.voided=0 and o.voided=0 and 
						o.concept_id=6128 and o.value_datetime is not null
			) inicio
		where inicio.obsIdProfilaxia is null;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info=1;

	SET no_info = 0;

	OPEN cur_DataInicioResumo;
	cur_loop: WHILE(no_info = 0) DO

	    FETCH cur_DataInicioResumo INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info = 1 THEN
			LEAVE cur_loop;
		END IF;	
		/*
			1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH);
			1.1. Tem que verificar se na mesma consulta nao existe esta obs. 
				Garantir que o parceiro pode correr varias vezes sem criar problemas
		*/
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
		
		if obsOnDate=0 then
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
			values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);
		end if;	
		/*
			2.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1256 – Iniciar) e a obsDatetime será o value_datetime do conceito 6128
			3.	Eliminar a observação 6128
		*/	
		update obs set concept_id=165308, value_coded=1256,obs_datetime=valueDate,value_datetime=null, comments = obsCommentsUpdated where obs_id=obsId;
		
		/*
			Verificar se na mesma consulta tem data_final
		*/	
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=6129;
		
		if obsOnDate>0 then 	
		
			select obs_id,value_datetime into obsOnDate,valueDate 
			from obs 
			where voided=0 and encounter_id=encounterId and concept_id=6129 and value_datetime is not null
			limit 1;	
				
			update obs set concept_id=165308, value_coded=1267,obs_datetime=valueDate,value_datetime=null, comments = obsCommentsUpdated where obs_id=obsOnDate;
			
		end if;
		
	END WHILE cur_loop;
	CLOSE cur_DataInicioResumo;

	SET no_info = 0;

END;

BEGIN
	/*
		Data Fim ConceptID=6129
		Nota: Aqui estamos a tratar a versao inicial da Ficha Resumo. Data Inicio sem ter Profilaxia TPT
	*/

	DECLARE cur_DataFimResumo CURSOR FOR	
		select 
			obs_id,
			person_id,
			concept_id ,
			encounter_id,
			encounter_datetime,
			location_id,
			value_datetime,
			creator,
			date_created
		from 
			(
				select 	o.obs_id,
						o.person_id,
						o.concept_id ,
						o.encounter_id,
						e.encounter_datetime,
						e.location_id,
						o.value_datetime,
						o.creator,
						o.date_created,
						obsProfilaxia.obs_id as obsIdProfilaxia
				from 	patient p 
						inner join encounter e on p.patient_id=e.patient_id
						inner join temp_location on temp_location.location_id = e.location_id
						inner join obs o on e.encounter_id=o.encounter_id
						left join obs obsProfilaxia on obsProfilaxia.encounter_id=e.encounter_id and 
									obsProfilaxia.voided=0 and obsProfilaxia.concept_id=23985
				where 	e.encounter_type=53 and e.voided=0 and o.voided=0 and 
						o.concept_id=6129 and o.value_datetime is not null
			) inicio
		where inicio.obsIdProfilaxia is null;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info1=1;

	SET no_info1 = 0;

	OPEN cur_DataFimResumo;
	cur_loopFim: WHILE(no_info1 = 0) DO

	    FETCH cur_DataFimResumo INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info1 = 1 THEN
			LEAVE cur_loopFim;
		END IF;	
		/*
			1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH);
			1.1. Tem que verificar se na mesma consulta nao existe esta obs. 
				Garantir que o parceiro pode correr varias vezes sem criar problemas
		*/
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
		
		if obsOnDate=0 then
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
			values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);		
		end if;	
		/*
			2.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1267 – Fim) e a obsDatetime será o value_datetime do conceito 6129
			3.	Eliminar a observação 6129
		*/	
		update obs set concept_id=165308, value_coded=1267,obs_datetime=valueDate,value_datetime=null,comments = obsCommentsUpdated where obs_id=obsId;
		
	END WHILE cur_loopFim;
	CLOSE cur_DataFimResumo;
	SET no_info1 = 0;

END;

BEGIN	
	/*
		Ultima Profilaxia TPT (23985) – Answers (656 – INH, 23954 – 3HP, 165305 – 1HP, 165306 – LFX)
		Data Inicio ConceptID=6128
	*/
	DECLARE cur_DataInicioResumoCoded CURSOR FOR
	select 	o.obs_id,
			o.person_id,
			o.concept_id ,
			o.encounter_id,
			e.encounter_datetime,
			e.location_id,
			o.value_datetime,
			o.creator,
			o.date_created
	from 	patient p 
			inner join encounter e on p.patient_id=e.patient_id
			inner join temp_location on temp_location.location_id = e.location_id
			inner join obs o on e.encounter_id=o.encounter_id
			inner join obs obsProfilaxia on obsProfilaxia.encounter_id=e.encounter_id 						
	where 	e.encounter_type=53 and e.voided=0 and o.voided=0 and 
			o.concept_id=6128 and obsProfilaxia.voided=0 and obsProfilaxia.concept_id=23985 and o.value_datetime is not null;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info2=1;

	SET no_info2 = 0;

	OPEN cur_DataInicioResumoCoded;
	cur_loopInicioCoded: WHILE(no_info2 = 0) DO

	    FETCH cur_DataInicioResumoCoded INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info2 = 1 THEN
			LEAVE cur_loopInicioCoded;
		END IF;	
		
		/*
			1.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1256 – Iniciar) e 
				a obsDatetime será o value_datetime do conceito 6128
		*/	
		update obs set concept_id=165308, value_coded=1256,obs_datetime=valueDate,value_datetime=null,comments = obsCommentsUpdated where obs_id=obsId;
		
		END WHILE cur_loopInicioCoded;
	CLOSE cur_DataInicioResumoCoded;
	SET no_info2 = 0;

END;


BEGIN
	
	/*
		Ultima Profilaxia TPT (23985) – Answers (656 – INH, 23954 – 3HP, 165305 – 1HP, 165306 – LFX)
		Data Fim ConceptID=6129
	*/
	DECLARE cur_DataFimResumoCoded CURSOR FOR
	select 	o.obs_id,
			o.person_id,
			o.concept_id ,
			o.encounter_id,
			e.encounter_datetime,
			e.location_id,
			o.value_datetime,
			o.creator,
			o.date_created
	from 	patient p 
			inner join encounter e on p.patient_id=e.patient_id
			inner join temp_location on temp_location.location_id = e.location_id
			inner join obs o on e.encounter_id=o.encounter_id
			inner join obs obsProfilaxia on obsProfilaxia.encounter_id=e.encounter_id 						
	where 	e.encounter_type=53 and e.voided=0 and o.voided=0 and 
			o.concept_id=6129 and obsProfilaxia.voided=0 and obsProfilaxia.concept_id=23985 and o.value_datetime is not null;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info3=1;
	SET no_info3 = 0;

	OPEN cur_DataFimResumoCoded;
	cur_loopFimCoded: WHILE(no_info3 = 0) DO

	    FETCH cur_DataFimResumoCoded INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info3 = 1 THEN
			LEAVE cur_loopFimCoded;
		END IF;	
		
		/*
			1.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1267 – Fim) e 
				a obsDatetime será o value_datetime do conceito 6129
		*/	
		update obs set concept_id=165308, value_coded=1267,obs_datetime=valueDate,value_datetime=null,comments = obsCommentsUpdated where obs_id=obsId;
		
	END WHILE cur_loopFimCoded;
	CLOSE cur_DataFimResumoCoded;
	SET no_info3 = 0;

END;

END;;
DELIMITER ;


DROP PROCEDURE IF EXISTS DataRecontructTPTSeguimento;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE DataRecontructTPTSeguimento()
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
DECLARE valueDate DATETIME;
DECLARE obsOnDate INT(11);
DECLARE endObsDatetime DATETIME;
DECLARE yesObsDatetime DATETIME;
DECLARE yesObsId INT(11);

DECLARE obsCommentsCreated varchar(50);
DECLARE obsCommentsUpdated varchar(50);

SET obsCommentsCreated = 'DATA RECONSTRUCTED - CREATED';
SET obsCommentsUpdated = 'DATA RECONSTRUCTED - UPDATED';

BEGIN
	/*
		Todos inicio de TPT na Ficha Seguimento, Adulto ou Pediatria
	*/

	DECLARE cur_DataInicioSeguimento CURSOR FOR
		select 	obs_id,
				person_id,
				concept_id ,
				o.encounter_id,
				e.encounter_datetime,
				e.location_id,
				value_datetime,
				o.creator,
				o.date_created				
		from 	patient p 
				inner join encounter e on p.patient_id=e.patient_id
				inner join temp_location on temp_location.location_id = e.location_id
				inner join obs o on e.encounter_id=o.encounter_id
		where 	e.encounter_type in (6,9) and e.voided=0 and o.voided=0 and 
				o.concept_id=6128 and value_datetime is not null
		order by obs_id;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info=1;

	SET no_info = 0;

	OPEN cur_DataInicioSeguimento;
	cur_loop: WHILE(no_info = 0) DO

	    FETCH cur_DataInicioSeguimento INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info = 1 THEN
			LEAVE cur_loop;
		END IF;	
		/*
			1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH)
			1.1. Tem que verificar se na mesma consulta nao existe esta obs. 
				Garantir que o parceiro pode correr varias vezes sem criar problemas
		*/
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
		
		if obsOnDate=0 then
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
			values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);
		end if;	
		/*
			2.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1256 – Iniciar) e a obsDatetime será o value_datetime do conceito 6128
			3.	Eliminar a observação 6128
		*/	
		update obs set concept_id=165308, value_coded=1256,obs_datetime=valueDate,value_datetime=null,comments = obsCommentsUpdated where obs_id=obsId;
		
		/*
			Verificar se na mesma consulta tem data_final
		*/	
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=6129;
		
		if obsOnDate>0 then 	
		
			select obs_id,value_datetime into obsOnDate,endObsDatetime 
			from obs 
			where voided=0 and encounter_id=encounterId and concept_id=6129 and value_datetime is not null
			limit 1;	
				
			update obs set concept_id=165308, value_coded=1267,obs_datetime=endObsDatetime,value_datetime=null, comments = obsCommentsUpdated where obs_id=obsOnDate;
			
		end if;
		
		/*
			Verificar se na mesma consulta tem Sim
		*/	
		
		select count(*) into obsOnDate 
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=6122 and value_coded=1065;
		
		if obsOnDate>0 then	

			select obs_id, obs_datetime into yesObsId, yesObsDatetime
			from obs 
			where voided=0 and encounter_id=encounterId and concept_id=6122 and value_coded=1065 limit 1;

			if date(yesObsDatetime)=date(valueDate) then

				delete from obs where obs_id = yesObsId;
			else
				update obs set concept_id=165308, value_coded=1257,comments = obsCommentsUpdated 
				where obs_id = yesObsId;
			
			end if;
		end if;
	
	END WHILE cur_loop;
	CLOSE cur_DataInicioSeguimento;
	SET no_info = 0;

END;

BEGIN
	/* 
		Todos Fim de TPT na Ficha Seguimento, Adulto ou Pediatria
	*/
	DECLARE cur_DataFimSeguimento CURSOR FOR
		select 	obs_id,
				person_id,
				concept_id ,
				o.encounter_id,
				e.encounter_datetime,
				e.location_id,
				value_datetime,
				o.creator,
				o.date_created				
		from 	patient p 
				inner join encounter e on p.patient_id=e.patient_id
				inner join temp_location on temp_location.location_id = e.location_id
				inner join obs o on e.encounter_id=o.encounter_id
		where 	e.encounter_type in (6,9) and e.voided=0 and o.voided=0 and 
				o.concept_id=6129 and value_datetime is not null;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info1=1;

	SET no_info1 = 0;

	OPEN cur_DataFimSeguimento;
	cur_dataFim: WHILE(no_info1 = 0) DO

	    FETCH cur_DataFimSeguimento INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueDate,creatorId,dateCreated;

	    IF no_info1 = 1 THEN
			LEAVE cur_dataFim;
		END IF;	
		/*
			1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH)
		*/
		
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
		
		if obsOnDate=0 then
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
			values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);
		end if;		
		
		/*
			2.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1267 – Fim) e a obsDatetime será o value_datetime do conceito 6129
			3.	Eliminar a observação 6129
		*/
		update obs set concept_id=165308, value_coded=1267,obs_datetime=valueDate,value_datetime=null,comments = obsCommentsUpdated where obs_id=obsId;

		/*
			Verificar se na mesma consulta tem Sim
		*/	
		
		select count(*) into obsOnDate 
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=6122 and value_coded=1065;
		
		if obsOnDate>0 then	

			select obs_id, obs_datetime into yesObsId, yesObsDatetime
			from obs 
			where voided=0 and encounter_id=encounterId and concept_id=6122 and value_coded=1065 limit 1;

			if date(yesObsDatetime)=date(valueDate) then

				delete from obs where obs_id = yesObsId;
			else
				update obs set concept_id=165308, value_coded=1257,comments = obsCommentsUpdated 
				where obs_id = yesObsId;
			end if;
		end if;
		
	END WHILE cur_dataFim;
	CLOSE cur_DataFimSeguimento;

	SET no_info1 = 0;

END;


BEGIN				
	/* 
		Todos INH=SIM na Ficha Seguimento, Adulto ou Pediatria
	*/
	DECLARE cur_INHSim CURSOR FOR
		select 	obs_id,
				person_id,
				concept_id ,
				o.encounter_id,
				e.encounter_datetime,
				e.location_id,
				value_coded,
				o.creator,
				o.date_created				
		from 	patient p 
				inner join encounter e on p.patient_id=e.patient_id
				inner join temp_location on temp_location.location_id = e.location_id
				inner join obs o on e.encounter_id=o.encounter_id
		where 	e.encounter_type in (6,9) and e.voided=0 and o.voided=0 and 
				o.concept_id=6122 and value_coded=1065;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info2=1;

	SET no_info2 = 0;

	OPEN cur_INHSim;
	cur_simINH: WHILE(no_info2 = 0) DO

	    FETCH cur_INHSim INTO obsId, personId,conceptId,encounterId,obsDatetime,locationId,valueCoded,creatorId,dateCreated;

	    IF no_info2 = 1 THEN
			LEAVE cur_simINH;
		END IF;	
		
		/*
			1. Criar uma observação Profilaxia TPT (23985) que corresponde ao Regime TPT com a resposta (656 – INH)
		*/
		
		select count(*) into obsOnDate
		from obs 
		where voided=0 and encounter_id=encounterId and concept_id=23985 and value_coded=656;
		
		if obsOnDate=0 then
			insert into obs (person_id,concept_id,encounter_id,obs_datetime,location_id,creator,date_created,status,uuid,value_coded,comments) 
			values (personId,23985,encounterId,obsDatetime,locationId,creatorId,dateCreated,'FINAL',uuid(),656,obsCommentsCreated);
		end if;		
		
		/*
			2.	Criar uma observação Estado da Profilaxia (165308) com a resposta (1257 – Continua)
			3.	Eliminar a observação 6122
		*/		
		update obs set concept_id=165308, value_coded=1257,comments = obsCommentsUpdated where obs_id=obsId;	

	END WHILE cur_simINH;
	CLOSE cur_INHSim;

	SET no_info2=0;

END;

END;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `SWAPCONCEPTSANDDATA`;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SWAPCONCEPTSANDDATA`()
    READS SQL DATA
BEGIN

DECLARE uuidFound varchar(200);
DECLARE concept165325UUID varchar(200);

SET concept165325UUID = 'b856b79b-2e8e-4764-ae8b-c8b509cdda76';


BEGIN
    
    select uuid into uuidFound from concept where concept_id = 165325;

    IF uuidFound <> concept165325UUID THEN

            -- Fix de conceitos 1.x que foram mapeados de forma errada
        insert into concept (concept_id,datatype_id,class_id,creator,date_created,uuid) VALUES (200000,4,4,1,now(),uuid());


        -- swap de concepts 165322 e 165324

        update concept_name set concept_id = 200000 where concept_id = 165324;
        update concept_name set concept_id = 165324 where concept_id = 165322;
        update concept_name set concept_id = 165322 where concept_id = 200000;

        update concept_description set concept_id = 200000 where concept_id = 165324;
        update concept_description set concept_id = 165324 where concept_id = 165322;   
        update concept_description set concept_id = 165322 where concept_id = 200000;

        update concept_answer set concept_id = 200000 where concept_id = 165324;
        update concept_answer set concept_id = 165324 where concept_id = 165322;    
        update concept_answer set concept_id = 165322 where concept_id = 200000;

        update concept_set set concept_id = 165322 where concept_id =165324;

        update concept set uuid = 'fef178f2-d4c9-4035-9989-11c9afe81ea3#' where concept_id =165324;
        update concept set uuid = 'fef178f2-d4c9-4035-9989-11c9afe81ea3' where concept_id =165322;
        update concept set uuid = '4387180e-695f-4c99-8182-33e51907062a' where concept_id =165324;  

        -- swap de concepts 165323 e 165325
        update concept_name set concept_id = 200000 where concept_id = 165325;
        update concept_name set concept_id = 165325 where concept_id = 165323;
        update concept_name set concept_id = 165323 where concept_id = 200000;

        update concept_description set concept_id = 200000 where concept_id = 165325;
        update concept_description set concept_id = 165325 where concept_id = 165323;   
        update concept_description set concept_id = 165323 where concept_id = 200000;

        update concept_answer set concept_id = 165325 where concept_id = 165323;

        update concept_set set concept_set = 165323 where concept_set =165325;
        update concept_set set concept_id = 165322 where concept_id =165324;

        update concept set uuid = 'bebcfbe3-bb5b-4c5c-a41e-808fc4457fc3#' where concept_id =165325;
        update concept set uuid = 'bebcfbe3-bb5b-4c5c-a41e-808fc4457fc3' where concept_id =165323;
        update concept set uuid = 'b856b79b-2e8e-4764-ae8b-c8b509cdda76' where concept_id =165325;  

        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165322;
        update concept set class_id =10, datatype_id =4, is_set =1 where concept_id =165323;
        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165324;
        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165325;

        -- actualizar obs
        update obs set concept_id = 200000 where concept_id = 165324;
        update obs set concept_id = 165324 where concept_id = 165322;
        update obs set concept_id = 165322 where concept_id = 200000;

        update obs set concept_id = 200000 where concept_id = 165325;
        update obs set concept_id = 165325 where concept_id = 165323;
        update obs set concept_id = 165323 where concept_id = 200000;

        update obs set value_coded = 200000 where value_coded = 165324;
        update obs set value_coded = 165324 where value_coded = 165322;
        update obs set value_coded = 165322 where value_coded = 200000;

        update obs set value_coded = 200000 where value_coded = 165325;
        update obs set value_coded = 165325 where value_coded = 165323;
        update obs set value_coded = 165323 where value_coded = 200000;

        -- remover conceito de swap
        delete from concept where concept_id = 200000;
    
    END IF;
END;

END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for DataReconstruct
-- ----------------------------
DROP PROCEDURE IF EXISTS `DataReconstruct`;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DataReconstruct`()
    READS SQL DATA
BEGIN

CALL SWAPCONCEPTSANDDATA();
CALL FillTEMPLOCATION();
CALL DataRecontructMDS();
CALL DataRecontructTPTFichaClinica();
CALL DataRecontructTPTFichaResumo();
CALL DataRecontructTPTSeguimento();

end;;
-- DELIMITER ;