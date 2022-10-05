DROP PROCEDURE IF EXISTS DataRecontructTPTFichaResumo;
#
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

END
#