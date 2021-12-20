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

public class Ec6Queries {
  /**
   * Get the combine query for EC6 patient listing report
   *
   * @return String
   */
  public static String getEc6CombinedQuery() {
    String query =
        "SELECT  "
            + "f_ec6.patient_id patient_id, "
            + "f_ec6.NID NID,"
            + "f_ec6.Name  Name, "
            + "f_ec6.birthdate birthdate, "
            + "f_ec6.Estimated_dob Estimated_dob, "
            + "f_ec6.Sex Sex, "
            + "f_ec6.First_entry_date First_entry_date, "
            + "f_ec6.Last_updated Last_updated, "
            + "f_ec6.date_enrolled date_enrolled, "
            + "f_ec6.state state, "
            + "f_ec6.state_date state_date, "
            + "DATE_FORMAT(f_ec6.encounter_date_fila,'%d-%m-%Y') AS encounter_date_fila, "
            + "IF(f_ec6.state_date<f_ec6.encounter_date_created_fila,f_ec6.encounter_date_created_fila,'') AS encounter_date_created_fila, "
            + "IF(f_ec6.state_date<f_ec6.encounter_date_recepcao,f_ec6.encounter_date_recepcao,'') AS encounter_date_recepcao, "
            + "IF(f_ec6.state_date<f_ec6.data_encounter_recepcao,f_ec6.data_encounter_recepcao,'') AS data_encounter_recepcao, "
            + "IF(f_ec6.state_date<f_ec6.date_created,f_ec6.date_created,'') AS date_created, "
            + "f_ec6.location_name location_name FROM ( "
            + "SELECT  trasferedOut.patient_id as patient_id, "
            + "pid.identifier AS NID, "
            + "concat(ifnull(pn.given_name,''),' ',ifnull(pn.middle_name,''),' ',ifnull(pn.family_name,'')) AS Name, "
            + "DATE_FORMAT(pe.birthdate, '%d-%m-%Y') AS birthdate, "
            + "IF(pe.birthdate_estimated = 1, 'Yes','No') AS Estimated_dob, "
            + "pe.gender AS Sex, "
            + "DATE_FORMAT(pe.date_created, '%d-%m-%Y %H:%i:%s') AS First_entry_date, "
            + "DATE_FORMAT(pe.date_changed, '%d-%m-%Y %H:%i:%s') AS Last_updated, "
            + "DATE_FORMAT(pg.date_enrolled, '%d-%m-%Y') AS date_enrolled, "
            + "case "
            + "when ps.state = 9 then 'DROPPED FROM TREATMENT' "
            + "when ps.state = 6 then 'ACTIVE ON PROGRAM' "
            + "when ps.state = 10 then 'PATIENT HAS DIED' "
            + "when ps.state = 8 then 'SUSPENDED TREATMENT' "
            + "when ps.state = 7 then 'TRANSFERED OUT TO ANOTHER FACILITY' "
            + "when ps.state = 29 then 'TRANSFERRED FROM OTHER FACILTY' "
            + "end AS state, "
            + "DATE_FORMAT(ps.start_date, '%d-%m-%Y') AS state_date, "
            + "CASE when MIN(date(fila.encounter_datetime))>date(ps.start_date) then fila.encounter_datetime when MIN(date(fila.encounter_datetime))=date(ps.start_date) then '' else null end as encounter_date_fila,  "
            + "DATE_FORMAT(fila.date_created, '%d-%m-%Y %H:%i:%s') AS encounter_date_created_fila, "
            + "DATE_FORMAT(recepcao.value_datetime, '%d-%m-%Y') AS encounter_date_recepcao, "
            + "DATE_FORMAT(recepcao.encounter_datetime, '%d-%m-%Y %H:%i:%s') AS data_encounter_recepcao, "
            + "DATE_FORMAT(recepcao.date_created, '%d-%m-%Y %H:%i:%s') AS date_created, "
            + "l.name AS location_name FROM  person pe  "
            + "LEFT JOIN  ( "
            + "SELECT p.patient_id patient_id, ps.start_date trasfered_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id = pg.patient_id AND pg.date_completed IS NULL "
            + "INNER JOIN patient_state ps ON pg.patient_program_id=ps.patient_program_id  "
            + "WHERE ps.state = 7  AND pg.voided = 0  AND ps.voided = 0  AND pg.program_id = 2 AND ps.start_date IS NOT NULL AND ps.end_date IS NULL "
            + ") trasferedOut  on pe.person_id=trasferedOut.patient_id "
            + "left join ( "
            + "select pid1.* from patient_identifier pid1 "
            + "inner join ( "
            + "select patient_id,min(patient_identifier_id) id from patient_identifier "
            + "where voided=0 group by patient_id "
            + ") pid2 "
            + "where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id "
            + ") pid on pid.patient_id=pe.person_id "
            + "left join( "
            + "select pn1.* from person_name pn1 inner join ( "
            + "select person_id,min(person_name_id) id from person_name "
            + "where voided=0 "
            + "group by person_id "
            + ") pn2 "
            + "where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id "
            + ") pn on pn.person_id=pe.person_id    "
            + "left join ( "
            + "Select p.patient_id, e.encounter_datetime, l.name  location_name, e.date_created from  patient p "
            + "inner join encounter e on p.patient_id = e.patient_id "
            + "inner join location l on l.location_id = e.location_id "
            + "where p.voided = 0 and e.voided = 0 and e.encounter_type = 18 and e.location_id IN (:location) "
            + "AND e.encounter_datetime between :startDate AND :endDate "
            + ") fila on fila.patient_id = pe.person_id "
            + "left join "
            + "( "
            + "SELECT  p.patient_id, o.value_datetime, e.encounter_datetime, l.name location_name, e.date_created date_created FROM  patient p "
            + "INNER JOIN encounter e on p.patient_id = e.patient_id "
            + "inner join obs o on e.encounter_id = o.encounter_id "
            + "inner join location l on l.location_id = e.location_id "
            + "where  p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 52 "
            + "AND o.concept_id = 23866 and o.value_datetime is not null and e.location_id IN (:location) "
            + "AND o.value_datetime between :startDate AND :endDate "
            + ") recepcao  on recepcao.patient_id = pe.person_id "
            + "left join  patient_program pg ON pe.person_id = pg.patient_id and pg.program_id = 2 and pg.location_id IN (:location) "
            + "inner join  patient_state ps ON pg.patient_program_id = ps.patient_program_id and ps.start_date IS NOT NULL AND ps.end_date IS NULL and ps.voided = 0 "
            + "left join location l on l.location_id=pid.location_id "
            + "where pe.voided = 0 and (trasferedOut.patient_id is not null) "
            + "and ( ( fila.encounter_datetime>trasferedOut.trasfered_date ) OR ( recepcao.value_datetime>trasferedOut.trasfered_date)) "
            + "GROUP BY pe.person_id "
            + ") f_ec6  "
            + "GROUP BY f_ec6.patient_id,f_ec6.encounter_date_fila,f_ec6.state_date ";
    return query;
  }

  public static String getEc6Total() {
    String query =
        "SELECT "
            + "pe.person_id as patient_id  FROM  person pe "
            + "LEFT JOIN  ( "
            + "SELECT p.patient_id patient_id, ps.start_date trasfered_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id=pg.patient_id AND pg.date_completed IS NULL "
            + "INNER JOIN patient_state ps ON pg.patient_program_id=ps.patient_program_id "
            + "WHERE  ps.state=7  AND pg.voided=0  AND ps.voided=0  AND pg.program_id=2 AND ps.start_date IS NOT NULL AND ps.end_date IS NULL "
            + ") trasferedOut  on pe.person_id=trasferedOut.patient_id "
            + "left join ( "
            + "select pid1.* from patient_identifier pid1 "
            + "inner join ( "
            + "select patient_id,min(patient_identifier_id) id from patient_identifier "
            + "where voided=0 group by patient_id "
            + ") pid2 "
            + "where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id "
            + ") pid on pid.patient_id=pe.person_id "
            + "left join( "
            + "select pn1.* from person_name pn1 inner join ( "
            + "select person_id,min(person_name_id) id from person_name "
            + "where voided=0 "
            + "group by person_id "
            + ") pn2 "
            + "where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id "
            + ") pn on pn.person_id=pe.person_id "
            + "left join ( "
            + "Select p.patient_id, e.encounter_datetime, l.name  location_name, e.date_created from  patient p "
            + "inner join encounter e on p.patient_id = e.patient_id "
            + "inner join location l on l.location_id = e.location_id "
            + "where p.voided = 0 and e.voided = 0 and e.encounter_type = 18 and e.location_id IN (:location) "
            + "AND e.encounter_datetime between :startDate AND :endDate "
            + ") fila on fila.patient_id = pe.person_id "
            + "left join "
            + "( "
            + "SELECT  p.patient_id, o.value_datetime, e.encounter_datetime, l.name location_name, e.date_created date_created FROM  patient p "
            + "INNER JOIN encounter e on p.patient_id = e.patient_id "
            + "inner join obs o on e.encounter_id = o.encounter_id "
            + "inner join location l on l.location_id = e.location_id "
            + "where  p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 52 "
            + "AND o.concept_id = 23866 and o.value_datetime is not null and e.location_id IN (:location) "
            + "AND o.value_datetime between :startDate AND :endDate "
            + ") recepcao  on recepcao.patient_id = pe.person_id "
            + "left join  patient_program pg ON pe.person_id = pg.patient_id and pg.program_id = 2 and pg.location_id IN (:location) "
            + "inner join  patient_state ps ON pg.patient_program_id = ps.patient_program_id and ps.start_date IS NOT NULL AND ps.end_date IS NULL and ps.voided = 0 "
            + "left join location l on l.location_id=pid.location_id "
            + "where pe.voided = 0 and (trasferedOut.patient_id is not null) "
            + "and ( ( fila.encounter_datetime>trasferedOut.trasfered_date ) OR ( recepcao.value_datetime>trasferedOut.trasfered_date)) "
            + "GROUP BY pe.person_id ";
    return query;
  }
}
