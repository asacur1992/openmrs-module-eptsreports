/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.queries.data.quality;

/** The queries to pull the EC1 dataset patient listing include all columns */
public class Ec11Queries {

  /**
   * Get the query to be used to display the EC11 patient listing
   *
   * @return String
   */
  public static String getEc11CombinedQuery() {
    String query =
        "SELECT 	pe.person_id As patient_id, "
            + "		pid.identifier AS NID, "
            + "		concat(ifnull(pn.given_name,''),' ',ifnull(pn.middle_name,''),' ',ifnull(pn.family_name,'')) AS Name, "
            + "		DATE_FORMAT(pe.birthdate, '%d-%m-%Y') AS birthdate, "
            + "		IF(pe.birthdate_estimated = 1, 'Sim','Não') AS Estimated_dob, "
            + "		pe.gender AS Sex, DATE_FORMAT(pe.date_created, '%d-%m-%Y') AS First_entry_date, "
            + "		DATE_FORMAT(pe.date_changed, '%d-%m-%Y') AS Last_updated, "
            + "		DATE_FORMAT(pg.date_enrolled, '%d-%m-%Y') AS date_enrolled, "
            + "		case "
            + "			when ps.state = 9 then 'DROPPED FROM TREATMENT' "
            + "			when ps.state = 6 then 'ACTIVE ON PROGRAM' "
            + "			when ps.state = 10 then 'PATIENT HAS DIED' "
            + "			when ps.state = 8 then 'SUSPENDED TREATMENT' "
            + "			when ps.state = 7 then 'TRANSFERED OUT TO ANOTHER FACILITY' "
            + "			when ps.state = 29 then 'TRANSFERRED FROM OTHER FACILTY' "
            + "		end AS state, "
            + "		DATE_FORMAT(ps.start_date, '%d-%m-%Y') AS state_date, "
            + "		DATE_FORMAT(pedidoLaboratorio.DataPedido, '%d-%m-%Y') AS data_pedido_laboratorio, "
            + "		DATE_FORMAT(colheitaLaboratorio.DataColheita, '%d-%m-%Y') AS data_colheita_amostra, "
            + "		DATE_FORMAT(pedidoLaboratorio.encounter_datetime, '%d-%m-%Y') AS data_formulario_laboratorio, "
            + "		DATE_FORMAT(pedidoLaboratorio.date_created, '%d-%m-%Y') AS data_criacao_formulario, "
            + "		DATE_FORMAT(fsr.DataColheita, '%d-%m-%Y') AS data_do_FSR, "
            + "		DATE_FORMAT(fsr.date_created, '%d-%m-%Y') AS data_registo_FSR, "
            + "       l.name as location_name "
            + "FROM "
            + "person pe "
            + "inner join "
            + "( "
            + "			select patient_id, max(data_abandono) data_abandono from ( "
            + "			select maxEstado.patient_id, "
            + "				      		maxEstado.data_abandono "
            + "				      	from( "
            + "				         		select pg.patient_id, "
            + "				         			max(ps.start_date) data_abandono "
            + "				         		from patient p "
            + "				         			inner join patient_program pg on p.patient_id = pg.patient_id "
            + "				         			inner join patient_state ps on pg.patient_program_id = ps.patient_program_id "
            + "				         		where pg.voided = 0 and ps.voided = 0 and p.voided = 0 and pg.program_id = 2 "
            + "				         			and ps.start_date <= :endDate and pg.location_id = :location "
            + "				         			group by p.patient_id "
            + "				      	) "
            + "				      	maxEstado "
            + "				      		inner join patient_program pg2 on pg2.patient_id = maxEstado.patient_id "
            + "				      		inner join patient_state ps2 on pg2.patient_program_id = ps2.patient_program_id "
            + "				      	where pg2.voided = 0 and ps2.voided = 0 and pg2.program_id = 2 and ps2.start_date = maxEstado.data_abandono "
            + "				      		and pg2.location_id =:location and ps2.state = 9 "
            + "			union "
            + "	          select saida.patient_id,data_estado from "
            + "					( "
            + "					select patient_id, max(data_estado) data_estado from ( "
            + "					select p.patient_id, max(o.obs_datetime) data_estado from patient p "
            + "					inner join encounter e on p.patient_id=e.patient_id "
            + "					inner join obs  o on e.encounter_id=o.encounter_id "
            + "					where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type = 6 and "
            + "					o.concept_id = 6273 and o.obs_datetime>=:startDate and o.obs_datetime<=:endDate and e.location_id=:location "
            + "					group by p.patient_id "
            + "					union "
            + "					select p.patient_id, max(o.obs_datetime) data_estado from patient p "
            + "					inner join encounter e on p.patient_id=e.patient_id "
            + "					inner join obs  o on e.encounter_id=o.encounter_id "
            + "					where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type = 53 and "
            + "					o.concept_id = 6272 and o.obs_datetime>=:startDate and o.obs_datetime<=:endDate and e.location_id=:location "
            + "					group by p.patient_id "
            + "					) saida group by saida.patient_id "
            + "					) saida "
            + "					inner join encounter e on e.patient_id=saida.patient_id "
            + "					inner join obs o on o.encounter_id = e.encounter_id "
            + "					where o.concept_id in (6272,6273) and o.value_coded = 1707 "
            + "					and o.obs_datetime = saida.data_estado and o.voided = 0 "
            + "					and e.voided=0 and e.encounter_type in (6,53) "
            + "					group by saida.patient_id "
            + "	      		) abandono group by abandono.patient_id "
            + ") abandonedPrograma on pe.person_id = abandonedPrograma.patient_id "
            + "left join "
            + "( "
            + "	SELECT p.patient_id AS patient_id, o.obs_datetime as DataColheita "
            + "	FROM patient p "
            + "	INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + "	INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + "	WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 23821 "
            + "	AND e.encounter_type = 13 "
            + "	AND e.location_id IN (:location) "
            + "	AND o.value_datetime BETWEEN :startDate AND :endDate "
            + ") colheitaLaboratorio on pe.person_id = colheitaLaboratorio.patient_id "
            + "left join "
            + "( "
            + "	SELECT p.patient_id AS patient_id, o.obs_datetime as DataPedido, e.encounter_datetime, e.date_created "
            + "	FROM patient p "
            + "	INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + "	INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + "	WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 6246 "
            + "	AND e.encounter_type = 13 "
            + "	AND e.location_id IN (:location) "
            + "	AND o.value_datetime  BETWEEN :startDate AND :endDate "
            + ") pedidoLaboratorio on pe.person_id = pedidoLaboratorio.patient_id "
            + "left join "
            + "( "
            + "SELECT p.patient_id AS patient_id, o.value_datetime as DataColheita, e.date_created "
            + "	FROM patient p "
            + "	INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + "	INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + "	WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 23821 "
            + "	AND e.encounter_type = 51 "
            + "	AND e.location_id IN (:location) "
            + "	AND o.value_datetime BETWEEN :startDate AND :endDate "
            + ") fsr on pe.person_id = fsr.patient_id "
            + "left join "
            + "(	select pn1.* "
            + "	from person_name pn1 "
            + "	inner join "
            + "	( "
            + "		select person_id, min(person_name_id) id "
            + "		from person_name "
            + "		where voided = 0 "
            + "		group by person_id "
            + "	) pn2 "
            + "	where pn1.person_id = pn2.person_id and pn1.person_name_id = pn2.id "
            + ") pn on pn.person_id = pe.person_id "
            + "left join "
            + "(   select pid1.* "
            + "	from patient_identifier pid1 "
            + "	inner join "
            + "	( "
            + "		select patient_id, min(patient_identifier_id) id "
            + "		from patient_identifier "
            + "		where voided = 0 "
            + "		group by patient_id "
            + "	) pid2 "
            + "	where pid1.patient_id = pid2.patient_id and pid1.patient_identifier_id = pid2.id "
            + ") pid on pid.patient_id = pe.person_id "
            + "inner join  patient_program pg ON pe.person_id = pg.patient_id and pg.program_id = 2 "
            + " and pg.location_id IN (:location) "
            + "inner join  patient_state ps ON pg.patient_program_id = ps.patient_program_id and (ps.start_date IS NOT NULL AND ps.end_date IS NULL and ps.voided =0) "
            + " left join location l on l.location_id=pg.location_id "
            + "where 	pe.voided = 0 and "
            + "		( "
            + "			colheitaLaboratorio.patient_id is not null or "
            + "			pedidoLaboratorio.patient_id is not null or "
            + "			fsr.patient_id is not null "
            + "		) and "
            + "			( "
            + "				colheitaLaboratorio.DataColheita>abandonedPrograma.data_abandono or "
            + "				pedidoLaboratorio.DataPedido>abandonedPrograma.data_abandono or "
            + "				fsr.DataColheita>abandonedPrograma.data_abandono "
            + "			) "
            + "GROUP BY pe.person_id; ";
    return query;
  }
}
