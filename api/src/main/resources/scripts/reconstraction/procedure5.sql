DROP PROCEDURE IF EXISTS DataRecontructTPTSeguimento;
#
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

END
#