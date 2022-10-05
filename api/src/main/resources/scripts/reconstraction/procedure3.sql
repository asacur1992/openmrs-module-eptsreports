DROP PROCEDURE IF EXISTS DataRecontructTPTFichaClinica;
#
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

END
#