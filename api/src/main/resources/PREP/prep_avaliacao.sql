SELECT 
    (SELECT 
		CASE
			WHEN obs_in.value_coded = 165287 THEN "Adolescentes e Jovens em Risco"
			ELSE obs_in.value_coded
		END _grupo	
		FROM obs obs_in 
		INNER JOIN concept cpt on cpt.concept_id = obs_in.concept_id
		WHERE obs_in.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos    
        ) 
        AND cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd'
		AND obs_in.voided=0
        AND obs_in.value_coded = 165287
    ) AS _GRUPO_ALVO_ADOLESCENTE_JOVEM,
    (SELECT 
		CASE
			WHEN obs_in.value_coded = 1982 THEN "GESTANTE"
			ELSE obs_in.value_coded
		END _grupo	
		FROM obs obs_in 
		INNER JOIN concept cpt on cpt.concept_id = obs_in.concept_id
		WHERE obs_in.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd'
		AND obs_in.voided=0
        AND obs_in.value_coded =1982
    ) AS _GRUPO_ALVO_GESTANTE,
    (SELECT 
		CASE
			WHEN obs_in.value_coded = 6332 THEN "LACTANTE"
			ELSE obs_in.value_coded
		END _grupo	
		FROM obs obs_in 
		INNER JOIN concept cpt on cpt.concept_id = obs_in.concept_id
		WHERE obs_in.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd'
		AND obs_in.voided=0
        AND obs_in.value_coded =6332
    ) AS _GRUPO_ALVO_LACTANTE,
    (SELECT 
		CASE
			/*Keypop CODED*/
			WHEN obs_in.value_coded = 1377 THEN "Homens que fazem sexo com Homens"
			ELSE obs_in.value_coded
		END _keypop	
		FROM obs obs_in 
		INNER JOIN concept cpt on cpt.concept_id = obs_in.concept_id
		WHERE obs_in.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) AND cpt.uuid= 'c7c0b430-bbc1-4042-a365-ad78a13aef56'
		AND obs_in.voided=0
        AND obs_in.value_coded =1377
    ) AS _GRUPO_ALVO_HOMEM_SEXO_HOMEM,
    (SELECT 
		CASE
			/*Keypop CODED*/
			WHEN obs_in.value_coded = 1901 THEN "TRABALHADOR DE SEXO"
			ELSE obs_in.value_coded
		END _keypop	
		FROM obs obs_in 
		INNER JOIN concept cpt on cpt.concept_id = obs_in.concept_id
		WHERE obs_in.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) AND cpt.uuid= 'c7c0b430-bbc1-4042-a365-ad78a13aef56'
		AND obs_in.voided=0
        AND obs_in.value_coded =1901
    ) AS _GRUPO_ALVO_TRABALHADOR_SEXO,
    TIMESTAMPDIFF(YEAR, pe.birthdate, :endDate) AS _IDADE,
	pe.gender AS _SEXO,
	(SELECT 
		CASE
			WHEN obs.value_coded = 1056 THEN "SEPARADO"
			WHEN obs.value_coded = 1057 THEN "SOLTEIRO"
			WHEN obs.value_coded = 1058 THEN "DIVORCIADO"
			WHEN obs.value_coded = 1059 THEN "VIUVO"
			WHEN obs.value_coded = 1060 THEN "UNIÃO DE FACTO"
			WHEN obs.value_coded = 5555 THEN "CASADO"
			WHEN obs.value_coded = 1175 THEN "NÃO APLICAVEL"
			WHEN obs.value_coded = 5622 THEN "OUTRO, NAO CODIFICADO"
		ELSE obs.value_coded
		END 	
		FROM obs 
		INNER JOIN concept cpt ON cpt.concept_id = obs.concept_id
		WHERE obs.voided=0 AND obs.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07' 
	) AS _ESTADO_CIVIL,
    (
		SELECT 
			CASE WHEN 
					(
						SELECT COUNT(*) FROM relationship rel
						INNER JOIN relationship_type relT ON relT.relationship_type_id = rel.relationship
						WHERE relT.uuid = '2f7d5778-0c80-11eb-b335-9f16b42e3b00' AND rel.person_a = pa.patient_id
					) >0 THEN "POSITIVO"
				 ELSE "DESCONHECIDO"
                 END
    ) AS _ESTADO_HIV_DO_PARCEIRO,
    (
		-- Data da consulta de PrEP
		SELECT 
			e.encounter_datetime	AS _data_consulta_prep
		FROM encounter e
		WHERE e.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        )
	) AS _DATA_CONSULTA_INICAL_PREP,
    (
		-- Data de inicio de PrEP
		SELECT 
			obs.obs_datetime
		FROM obs
        INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
		WHERE obs.voided=0 AND obs.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND obs.value_coded=1256
        AND cpt.uuid = 'b22efbd1-902f-408e-9232-158f678731ec'
	) AS _DATA_INICIO_PREP,
    (
		-- Data de Re-inicio de PrEP
		SELECT 
			obs.obs_datetime	AS _data_reinicio_prep
		FROM obs
        INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
		WHERE obs.voided=0 AND obs.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND obs.value_coded=1705
        AND cpt.uuid = 'b22efbd1-902f-408e-9232-158f678731ec'
	) AS _DATA_REINICIO_PREP,
    (
		-- Data do Teste de HIV Negativo
		SELECT 
			obs.value_datetime	AS _data_teste_hiv
		FROM obs
        INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
		WHERE obs.voided=0 AND obs.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND cpt.uuid = 'eef8b142-283a-4f63-9da6-0bb7df2744f9'
	) AS _DATA_TESTE_HIV_NEGATIVO,
    (
		-- Data de levantamento e a mesma que a da consulta de inicio de PrEP se tiver levantado no minimo 1 medicamento nos 3 regimes
		SELECT 
			obs.obs_datetime
		FROM obs
        INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
		WHERE obs.voided=0 AND obs.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
        AND obs.value_coded=1256
        AND cpt.uuid = 'b22efbd1-902f-408e-9232-158f678731ec'
	) AS _DATA_LEVANTAMENTO_ARV,
    (
		-- Numero de frascos levantados TDF_3TC
		SELECT 
			CASE WHEN obs_out.value_coded = 165214 THEN 
				CAST(obs_out.comments as signed integer)
			ELSE 0
			END
        FROM obs obs_out
        INNER JOIN concept cpt on cpt.concept_id = obs_out.concept_id
		WHERE obs_out.voided=0 AND obs_out.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
		) 
		AND cpt.uuid = '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
        AND obs_out.value_coded = 165214
           
    ) AS _NUMERO_FRASCOS_LEVANTADOS_TDF_3TC,
    (
		-- Numero de frascos levantados TDF/FTC
		SELECT 
			CASE WHEN obs_out.value_coded = 165215 THEN 
				CAST(obs_out.comments as signed integer)
			ELSE 0
			END
        FROM obs obs_out
        INNER JOIN concept cpt on cpt.concept_id = obs_out.concept_id
		WHERE obs_out.voided=0 AND obs_out.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				)  AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
		) 
		AND cpt.uuid = '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
        AND obs_out.value_coded = 165215
           
    ) AS _NUMERO_FRASCOS_LEVANTADOS_TDF_FTC,
    (
		-- Numero de frascos levantados OUTRO
		SELECT 
			CASE WHEN obs_out.value_coded = 165216 THEN 
				(SELECT 
						obs_reg_3.value_numeric AS _numero_frascos_regime_outro
					FROM obs obs_reg_3
					INNER JOIN concept cpt_reg_3 on cpt_reg_3.concept_id = obs_reg_3.concept_id
					WHERE obs_reg_3.voided=0 AND obs_reg_3.encounter_id = (
						SELECT ec.encounter_id FROM encounter ec
							WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
							AND ec.encounter_id  IN (
							-- consulta com resposta do grupo de analise
							SELECT enc.encounter_id FROM encounter enc 
							INNER JOIN obs on obs.encounter_id = enc.encounter_id
							INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
							WHERE enc.patient_id = pa.patient_id
							AND enc.encounter_type=80 
							AND enc.encounter_datetime between :startDate AND :endDate  
							AND enc.location_id =:location 
							AND enc.voided=0
							AND obs.voided=0
							AND obs.value_coded IN (165287,1982,6332,1377,1901)
							AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
					)  AND ec.encounter_id IN (
							-- Consulta que levantou pelo menos 1 regime de medicamento
							SELECT enc.encounter_id FROM encounter enc 
							INNER JOIN obs on obs.encounter_id = enc.encounter_id
							INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
							WHERE enc.patient_id = pa.patient_id
							AND enc.encounter_type=80 
							AND enc.encounter_datetime between :startDate AND :endDate  
							AND enc.location_id =:location 
							AND enc.voided=0
							AND obs.voided=0
							AND obs.value_coded IN (165214,165215,165216)
							AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
				)
				ORDER BY ec.encounter_datetime ASC limit 0,1
				-- END da Primeira consulta que satisfaz os criterios exigidos
			) 
			AND cpt_reg_3.uuid = '18d4175b-df7c-4659-a266-38e9ff60278e')
			ELSE 0
			END
        FROM obs obs_out
        INNER JOIN concept cpt on cpt.concept_id = obs_out.concept_id
		WHERE obs_out.voided=0 AND obs_out.encounter_id = (
				SELECT ec.encounter_id FROM encounter ec
				WHERE ec.encounter_type=80 AND ec.patient_id=pa.patient_id
				AND ec.encounter_id  IN (
						-- consulta com resposta do grupo de analise
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165287,1982,6332,1377,1901)
						AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
				) AND ec.encounter_id IN (
						-- Consulta que levantou pelo menos 1 regime de medicamento
						SELECT enc.encounter_id FROM encounter enc 
						INNER JOIN obs on obs.encounter_id = enc.encounter_id
						INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
						WHERE enc.patient_id = pa.patient_id
						AND enc.encounter_type=80 
						AND enc.encounter_datetime between :startDate AND :endDate  
						AND enc.location_id =:location 
						AND enc.voided=0
						AND obs.voided=0
						AND obs.value_coded IN (165214,165215,165216)
						AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
			)
				ORDER BY ec.encounter_datetime ASC limit 0,1
           -- END da Primeira consulta que satisfaz os criterios exigidos
        ) 
		AND cpt.uuid = '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
        AND obs_out.value_coded = 165216
           
    ) AS _NUMERO_FRASCOS_LEVANTADOS_OUTRO,
    -- Colunas da consulta de seguimento Numero 1
    (
		SELECT ec.encounter_datetime FROM encounter ec WHERE ec.encounter_id = (
						SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		)
    ) AS _VISITA_1_DATA_CONSULTA_PREP,
    (
		SELECT obs_s.obs_datetime FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		) AND obs_s.concept_id = 1040 AND obs_s.voided=0
    ) AS _VISITA_1_DATA_TESTE_HIV,
    (
		SELECT 
			CASE
			WHEN obs_s.value_coded = 664 THEN "NEGATIVO"
			WHEN obs_s.value_coded = 703 THEN "POSITIVO"
			WHEN obs_s.value_coded = 1138 THEN "INDETERMINADO"
			ELSE obs_s.value_coded
			END _resultado
        FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		) AND obs_s.concept_id = 1040  AND obs_s.voided=0
    ) AS _VISITA_1_RESULTADO_TESTE_HIV,
    (
		SELECT ec.encounter_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		) AND obs_s.concept_id = 165213 AND obs_s.value_coded IN (165214,165215,165224) AND obs_s.voided=0
    ) AS _VISITA_1_DATA_LVT_ARV,
    (
		SELECT obs_s.value_numeric FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		) AND obs_s.concept_id = 165217  AND obs_s.voided=0
    ) AS _VISITA_1_NUMERO_FRASCOS,
    (
		SELECT obs_s.value_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 0,1
		) AND obs_s.concept_id = 165228  AND obs_s.voided=0
    ) AS _VISITA_1_DATA_PROXIMA_CONSULTA_MARCADA,
    -- Colunas da consulta de seguimento Numero 2
    (
		SELECT ec.encounter_datetime FROM encounter ec WHERE ec.encounter_id = (
						SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		)
    ) AS _VISITA_2_DATA_CONSULTA_PREP,
    (
		SELECT obs_s.obs_datetime FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		) AND obs_s.concept_id = 1040 AND obs_s.voided=0
    ) AS _VISITA_2_DATA_TESTE_HIV,
    (
		SELECT 
			CASE
			WHEN obs_s.value_coded = 664 THEN "NEGATIVO"
			WHEN obs_s.value_coded = 703 THEN "POSITIVO"
			WHEN obs_s.value_coded = 1138 THEN "INDETERMINADO"
			ELSE obs_s.value_coded
			END _resultado
        FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		) AND obs_s.concept_id = 1040  AND obs_s.voided=0
    ) AS _VISITA_2_RESULTADO_TESTE_HIV,
    (
		SELECT ec.encounter_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		) AND obs_s.concept_id = 165213 AND obs_s.value_coded IN (165214,165215,165224) AND obs_s.voided=0
    ) AS _VISITA_2_DATA_LVT_ARV,
    (
		SELECT obs_s.value_numeric FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		) AND obs_s.concept_id = 165217  AND obs_s.voided=0
    ) AS _VISITA_2_NUMERO_FRASCOS,
    (
		SELECT obs_s.value_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 1,1
		) AND obs_s.concept_id = 165228  AND obs_s.voided=0
    ) AS _VISITA_2_DATA_PROXIMA_CONSULTA_MARCADA,
     -- Colunas da consulta de seguimento Numero 3
    (
		SELECT ec.encounter_datetime FROM encounter ec WHERE ec.encounter_id = (
						SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		)
    ) AS _VISITA_3_DATA_CONSULTA_PREP,
    (
		SELECT obs_s.obs_datetime FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		) AND obs_s.concept_id = 1040 AND obs_s.voided=0
    ) AS _VISITA_3_DATA_TESTE_HIV,
    (
		SELECT 
			CASE
			WHEN obs_s.value_coded = 664 THEN "NEGATIVO"
			WHEN obs_s.value_coded = 703 THEN "POSITIVO"
			WHEN obs_s.value_coded = 1138 THEN "INDETERMINADO"
			ELSE obs_s.value_coded
			END _resultado
        FROM obs obs_s WHERE obs_s.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		) AND obs_s.concept_id = 1040  AND obs_s.voided=0
    ) AS _VISITA_3_RESULTADO_TESTE_HIV,
    (
		SELECT ec.encounter_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		) AND obs_s.concept_id = 165213 AND obs_s.value_coded IN (165214,165215,165224) AND obs_s.voided=0
    ) AS _VISITA_3_DATA_LVT_ARV,
    (
		SELECT obs_s.value_numeric FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		) AND obs_s.concept_id = 165217  AND obs_s.voided=0
    ) AS _VISITA_3_NUMERO_FRASCOS,
    (
		SELECT obs_s.value_datetime FROM encounter ec 
        INNER JOIN obs obs_s ON obs_s.encounter_id = ec.encounter_id
        WHERE ec.encounter_id = (
				SELECT seg.encounter_id FROM encounter seg
						WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.patient_id = pa.patient_id
                        AND seg.encounter_datetime between :startDate AND :endDate order by seg.encounter_datetime  ASC limit 2,1
		) AND obs_s.concept_id = 165228  AND obs_s.voided=0
    ) AS _VISITA_3_DATA_PROXIMA_CONSULTA_MARCADA,
    (
		SELECT MIN(tabela_interrupccao._data_interr) FROM (
				(-- Pega a data de interrupcao na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, MIN(obs_inter.value_datetime) AS _data_interr
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)

				UNION

			(-- Pega a data de interrupcao na 1a consulta de seguimento
				SELECT 
					seg.patient_id AS pat_id, MIN(seg.encounter_datetime)  AS _data_interr
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)

				UNION

			(-- Pega a data de interrupcao na 2a consulta de seguimento
				SELECT 
					seg.patient_id AS pat_id, MIN(seg.encounter_datetime)  AS _data_interr
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)

				UNION

				(-- Pega a data de interrupcao na 3a consulta de seguimento
					SELECT 
						seg.patient_id AS pat_id, MIN(seg.encounter_datetime)  AS _data_interr
					FROM encounter seg 
					INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
					WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
					AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
					AND seg.encounter_datetime between :startDate AND :endDate 

					GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
			) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id group by tabela_interrupccao.pat_id
    ) AS _DATA_PREP_INTERROMPIDA,
    (
		SELECT tabela_interrupccao._motivo FROM (
				(-- Pega na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, obs_inter.value_datetime AS _data_interr,
                        (SELECT CASE WHEN obs_mtv.value_coded=1169 THEN "INFECTADO COM HIV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = ec.encounter_id) AS _motivo
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)
                
                UNION
              
			(-- Pega na 1a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=1169 THEN "INFECTADO COM HIV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)
                
                UNION
              
			(-- Pega na 2a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=1169 THEN "INFECTADO COM HIV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)
                
                UNION
              
			(-- Pega na 3a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=1169 THEN "INFECTADO COM HIV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
        ) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id order by tabela_interrupccao._data_interr ASC limit 0,1
    ) AS _MOTIVO_INFECTADO_HIV,
    (
		SELECT tabela_interrupccao._motivo FROM (
				(-- Pega na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, obs_inter.value_datetime AS _data_interr,
                        (SELECT CASE WHEN obs_mtv.value_coded=2015 THEN "EFEITOS SECUNDARIOS ARV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = ec.encounter_id) AS _motivo
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)
                
                UNION
              
			(-- Pega na 1a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=2015 THEN "EFEITOS SECUNDARIOS ARV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)
                
                UNION
              
			(-- Pega na 2a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=2015 THEN "EFEITOS SECUNDARIOS ARV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)
                
                UNION
              
			(-- Pega na 3a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=2015 THEN "EFEITOS SECUNDARIOS ARV" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
        ) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id order by tabela_interrupccao._data_interr ASC limit 0,1
    ) AS _MOTIVO_EFEITO_SECUNDARIO,
    (
		SELECT tabela_interrupccao._motivo FROM (
				(-- Pega na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, obs_inter.value_datetime AS _data_interr,
                        (SELECT CASE WHEN obs_mtv.value_coded=165226 THEN "SEM MAIS RISCOS SUBSTANCIAIS" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = ec.encounter_id) AS _motivo
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)
                
                UNION
              
			(-- Pega na 1a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165226 THEN "SEM MAIS RISCOS SUBSTANCIAIS" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)
                
                UNION
              
			(-- Pega na 2a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165226 THEN "SEM MAIS RISCOS SUBSTANCIAIS" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)
                
                UNION
              
			(-- Pega na 3a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165226 THEN "SEM MAIS RISCOS SUBSTANCIAIS" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
        ) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id order by tabela_interrupccao._data_interr ASC limit 0,1
    ) AS _MOTIVO_SEM_MAIS_RISCO,
    (
		SELECT tabela_interrupccao._motivo FROM (
				(-- Pega na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, obs_inter.value_datetime AS _data_interr,
                        (SELECT CASE WHEN obs_mtv.value_coded=165227 THEN "PREFERENCIA DO UTENTE" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = ec.encounter_id) AS _motivo
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)
                
                UNION
              
			(-- Pega na 1a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165227 THEN "PREFERENCIA DO UTENTE" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)
                
                UNION
              
			(-- Pega na 2a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165227 THEN "PREFERENCIA DO UTENTE" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)
                
                UNION
              
			(-- Pega na 3a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=165227 THEN "PREFERENCIA DO UTENTE" ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
        ) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id order by tabela_interrupccao._data_interr ASC limit 0,1
    ) AS _MOTIVO_PREFERENCIA_UTENTE,
    (
		SELECT tabela_interrupccao._motivo FROM (
				(-- Pega na Ficha inicial na parte do resumo
					SELECT 
						ec.patient_id AS pat_id, obs_inter.value_datetime AS _data_interr,
                        (SELECT CASE WHEN obs_mtv.value_coded=5622 THEN obs_mtv.comments ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = ec.encounter_id) AS _motivo
					FROM obs obs_inter
					INNER JOIN encounter ec ON ec.encounter_id = obs_inter.encounter_id

					WHERE obs_inter.voided = 0 AND obs_inter.concept_id = 1256 
					AND ec.encounter_id IN (
							-- Pacientes que Pertencem ao grupo de analise
							SELECT ec_g.encounter_id FROM encounter ec_g
							INNER JOIN obs obs_grupo on obs_grupo.encounter_id = ec_g.encounter_id
							WHERE obs_grupo.value_coded IN (165287,1982,6332,1377,1901) AND ec_g.encounter_datetime between :startDate AND :endDate 
							AND ec_g.location_id =:location
					) AND ec.encounter_id IN (
							-- Pacientes que Levantaram pelo menos 1 regime de medicamento
							SELECT ec_med.encounter_id FROM encounter ec_med
							INNER JOIN obs obs_lvt_med on obs_lvt_med.encounter_id = ec_med.encounter_id
							WHERE obs_lvt_med.value_coded IN (165214,165215,165216) AND ec_med.encounter_datetime between :startDate AND :endDate 
							AND ec_med.location_id =:location
					)
				AND ec.encounter_type=80 
				AND ec.encounter_datetime between :startDate AND :endDate 
				AND ec.location_id =:location
        
				GROUP BY ec.patient_id ORDER BY obs_inter.value_datetime)
                
                UNION
              
			(-- Pega na 1a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=5622 THEN obs_mtv.comments ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 0,1)
                
                UNION
              
			(-- Pega na 2a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=5622 THEN obs_mtv.comments ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 1,1)
                
                UNION
              
			(-- Pega na 3a consulta de seguimento
                SELECT 
					seg.patient_id AS pat_id ,seg.encounter_datetime  AS _data_interr,
                    (SELECT CASE WHEN obs_mtv.value_coded=5622 THEN obs_mtv.comments ELSE "" END FROM obs obs_mtv WHERE obs_mtv.concept_id=165225 AND obs_mtv.voided=0 AND obs_mtv.encounter_id = seg.encounter_id) AS _motivo
				FROM encounter seg 
				INNER JOIN obs obs_mtv ON obs_mtv.encounter_id = seg.encounter_id
				WHERE seg.voided=0 AND seg.encounter_type=81 AND seg.location_id =:location
				AND obs_mtv.concept_id = 165225 AND obs_mtv.value_coded IN (1169,2015,165226,165227,5622) AND obs_mtv.voided=0 
				AND seg.encounter_datetime between :startDate AND :endDate 

				GROUP BY seg.patient_id ORDER BY seg.encounter_datetime  ASC limit 2,1)
        ) AS tabela_interrupccao WHERE tabela_interrupccao.pat_id=pa.patient_id order by tabela_interrupccao._data_interr ASC limit 0,1
    ) AS _MOTIVO_OUTRO
    
FROM patient pa
INNER JOIN person pe on pe.person_id = pa.patient_id

where pa.patient_id IN (
	-- Pacientes que Pertencem ao grupo de analise
	SELECT enc.patient_id FROM encounter enc 
    INNER JOIN obs on obs.encounter_id = enc.encounter_id
    INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
    WHERE 
		enc.encounter_type=80 
		AND enc.encounter_datetime between :startDate AND :endDate  
        AND enc.location_id =:location 
        AND enc.voided=0
        AND obs.voided=0
        AND obs.value_coded IN (165287,1982,6332,1377,1901)
        AND (cpt.uuid= '279a7f80-b3cd-45c2-b16b-4706212416fd' || cpt.uuid= 'e1d80fbe-1d5f-11e0-b929-000c29ad1d07')
)  AND pa.patient_id IN (
	-- Pacientes que Levantaram pelo menos 1 regime de medicamento
	SELECT enc.patient_id FROM encounter enc 
    INNER JOIN obs on obs.encounter_id = enc.encounter_id
    INNER JOIN concept cpt on cpt.concept_id = obs.concept_id
    WHERE 
		enc.encounter_type=80 
		AND enc.encounter_datetime between :startDate AND :endDate 
        AND enc.location_id =:location 
        AND enc.voided=0
        AND obs.voided=0
        AND obs.value_coded IN (165214,165215,165216)
        AND cpt.uuid= '0bfae0d8-e2aa-48e4-b4a4-c157021b4f15'
) 