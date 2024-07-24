-- ----------------------------
-- PROCEDURE TO RECONSTRUCT CCU DATA
-- ----------------------------


DROP PROCEDURE IF EXISTS `CCUDataReconstruct`;
#
CREATE DEFINER=`root`@`localhost` PROCEDURE CCUDataReconstruct()
    READS SQL DATA
BEGIN

	DECLARE no_info, no_info1, no_info2 INT;

	DECLARE obsTratamentoId INT(11);
	DECLARE obsDataTratamentoId INT(11);
	DECLARE personId INT(11);
	DECLARE encounterId INT(11);
	DECLARE encounterDatetime DATETIME;
	DECLARE locationId INT(11);
	DECLARE valueDatetime DATETIME;
	DECLARE creatorId INT(11);
	DECLARE dateCreated DATETIME;
	
		
	BEGIN 
		/* Cenário 1. Fichas CCU criadas após 20-09-2023(Ficha actualizada na Release de Setembro/2023), que tem a data de tratamento Informada.
			a. Caso tenha sido informado Tratamento feito (Crioterapia ou Termoablação ):
			  a.1 Actualizar a valor da obs_Datetime do Tratamento feito com o value_date_time da data do Tratamento
			  a.2 anular a obs do conceito Data do Tratamento.

		 	b. Caso nao tenha sido informado o Tratamento feito:
		 	   b1. 
		*/
		DECLARE cur_cenario1 CURSOR FOR
				select 
					tratamento.obs_id,
					data_tratamento.obs_id dataTratamentoId, 
					data_tratamento.value_datetime 
				from encounter e
					inner join obs data_tratamento on e.encounter_id = data_tratamento.encounter_id 
					inner join obs tratamento on e.encounter_id = tratamento.encounter_id  
				where e.encounter_type = 28 and e.date_created >'2023-09-20' 
					and data_tratamento.concept_id =23967 and tratamento.concept_id = 1185 and e.location_id = data_tratamento.location_id  and e.location_id = tratamento.location_id;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info1=1;

		SET no_info1 = 0;

		OPEN cur_cenario1;
		cur_loop_c1: WHILE(no_info1 = 0) DO

			FETCH cur_cenario1 INTO obsTratamentoId,obsDataTratamentoId, valueDatetime;

			IF no_info1 = 1 THEN
				LEAVE cur_loop_c1;
			END IF;	
			
			update obs set obs_datetime=valueDatetime where obs_id=obsTratamentoId;
			update obs set voided = 1, void_reason = 'Anulado na reconstrução de dados CCU - Releade de Março 2024', date_voided = now() where obs_id = obsDataTratamentoId;

		END WHILE cur_loop_c1;
		CLOSE cur_cenario1;
		SET no_info1 = 0;
	END;


	BEGIN
		/* Cenário 2.  Fichas CCU criadas antes de 21-09-2023 (Antes da actualização da release de Setembro 2023), que tem a data da realização da Crioterapia (23967)
			a. Criar Tratamento feito (1185) com resposta Crioterapia  (23974)
			b. Na obs_datetime colocar o encounter_datetime da consulta.
		*/
		DECLARE cur_cenario2 CURSOR FOR
				select 	e.patient_id,
					e.encounter_id,
					e.encounter_datetime,
					e.date_created,
					e.creator,
					e.location_id, 
					crioterapia.obs_id crioterapiaId, 
					data_crioterapia.obs_id dataTratamentoId,
					data_crioterapia.value_datetime 
				from encounter e
					left join obs crioterapia on ( e.encounter_id = crioterapia.encounter_id and crioterapia.concept_id = 2117 and crioterapia.value_coded = 1065  and e.location_id = crioterapia.location_id)
					left join obs data_crioterapia on (e.encounter_id = data_crioterapia.encounter_id and data_crioterapia.concept_id=23967 and e.location_id = data_crioterapia.location_id)
				where e.encounter_type = 28 and e.date_created <='2023-09-20' and (crioterapia.obs_id is not null or data_crioterapia.obs_id is not null);

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_info=1;

		SET no_info = 0;
		OPEN cur_cenario2;
		cur_loop_c2: WHILE(no_info = 0) DO

			FETCH cur_cenario2 INTO personId, encounterId, encounterDatetime, dateCreated, creatorId, locationId, obsTratamentoId, obsDataTratamentoId, valueDatetime;

			IF no_info = 1 THEN
				LEAVE cur_loop_c2;
			END IF;	


			IF obsDataTratamentoId is not null THEN

				insert into obs (person_id, concept_id, encounter_id, obs_datetime, value_coded, location_id, creator, date_created, status, uuid) 
				values (personId, 1185, encounterId, valueDatetime, 23974, locationId, creatorId, dateCreated,'FINAL',uuid());

				update obs set voided = 1, void_reason = 'Anulado na reconstrução de dados CCU - Releade de Março 2024', date_voided = now() where obs_id = obsDataTratamentoId;

			ELSE
					IF obsTratamentoId is not null THEN
			
						insert into obs (person_id, concept_id, encounter_id, obs_datetime, value_coded, location_id, creator, date_created, status, uuid) 
						values (personId, 1185, encounterId, encounterDatetime, 23974, locationId, creatorId, dateCreated,'FINAL',uuid());

					END IF;
			END IF;
			
		END WHILE cur_loop_c2;
		CLOSE cur_cenario2;
		SET no_info = 0;
	END;
	
END
#