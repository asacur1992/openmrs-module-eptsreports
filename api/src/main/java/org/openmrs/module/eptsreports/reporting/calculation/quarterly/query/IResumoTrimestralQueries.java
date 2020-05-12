package org.openmrs.module.eptsreports.reporting.calculation.quarterly.query;

public interface IResumoTrimestralQueries {

  class QUERY {

    public static final String findPatientsWithAProgramStateMarkedAsTransferedInInAPeriod =
        "select minState.patient_id from  ("
            + "SELECT p.patient_id, pg.patient_program_id, MIN(ps.start_date) as minStateDate  FROM patient p  "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id "
            + "WHERE pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and location_id=:location  and ps.start_date BETWEEN :startDate -interval 1 year and :endDate "
            + "GROUP BY pg.patient_program_id) minState "
            + "inner join patient_state ps on ps.patient_program_id=minState.patient_program_id "
            + "where ps.start_date=minState.minStateDate and ps.state=29 and ps.voided=0 ";

    public static final String
        findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCard =
            "SELECT tr.patient_id from  ("
                + "SELECT p.patient_id, MIN(obsData.value_datetime) from patient p  "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id  "
                + "INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 "
                + "INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 "
                + "INNER JOIN obs obsData ON e.encounter_id=obsData.encounter_id AND obsData.voided=0 AND obsData.concept_id=23891 "
                + "WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 AND obsData.value_datetime BETWEEN :startDate -interval 1 year AND :endDate AND e.location_id=:location GROUP BY p.patient_id "
                + ") tr GROUP BY tr.patient_id ";
    public static final String findPatientsWhoAreNewlyEnrolledOnART =
        "SELECT patient_id FROM "
            + "(SELECT patient_id, MIN(art_start_date) art_start_date FROM "
            + "(SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18,6,9) "
            + "AND o.concept_id=1255 AND o.value_coded=1256 AND e.encounter_datetime<=:endDate -interval 1 year AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type IN (18,6,9,53) "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate -interval 1 year AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT pg.patient_id, MIN(date_enrolled) art_start_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id=pg.patient_id "
            + "WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled<=:endDate -interval 1 year AND location_id=:location GROUP BY pg.patient_id "
            + "UNION SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "WHERE p.voided=0 AND e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime<=:endDate -interval 1 year AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 "
            + "AND o.concept_id=23866 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id) "
            + "art_start GROUP BY patient_id ) tx_new WHERE art_start_date BETWEEN :startDate -interval 1 year AND :endDate -interval 1 year";

    public static String
        findPatientesWhoreMarkedAsTransferredOutInArtProgramOrInFichaClinicaOrInFichaResumo =
            "select transferidopara.patient_id from ( "
                + "select patient_id,max(data_transferidopara) data_transferidopara from ( "
                + "select pg.patient_id,max(ps.start_date) data_transferidopara from  patient p "
                + "inner join patient_program pg on p.patient_id=pg.patient_id "
                + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id where pg.voided=0 and ps.voided=0 and p.voided=0 and "
                + "pg.program_id=2 and ps.state=7 and ps.start_date<=:endDate and location_id=:location group by p.patient_id "
                + "union select p.patient_id,max(o.obs_datetime) data_transferidopara from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on o.encounter_id=e.encounter_id "
                + "where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 and o.value_coded=1706 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id "
                + "union select p.patient_id,max(e.encounter_datetime) data_transferidopara from  patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on o.encounter_id=e.encounter_id where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and "
                + "o.voided=0 and o.concept_id=6273 and o.value_coded=1706 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id) transferido group by patient_id ) transferidopara "
                + "inner join( select patient_id,max(encounter_datetime) encounter_datetime from( "
                + "select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p "
                + "inner join encounter e on e.patient_id=p.patient_id where  p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location and e.encounter_type in (18,6,9) "
                + "group by p.patient_id union "
                + "Select p.patient_id,max(value_datetime) encounter_datetime from  patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and o.concept_id=23866 and o.value_datetime is not null and o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id) consultaLev group by patient_id) consultaOuARV on transferidopara.patient_id=consultaOuARV.patient_id "
                + "where consultaOuARV.encounter_datetime <= transferidopara.data_transferidopara and transferidopara.data_transferidopara between :startDate -interval 1 year AND :endDate ";

    public static String findPatientsWhoWereSuspendTreatment =
        "select suspenso1.patient_id from ( "
            + "select patient_id,max(data_suspencao) data_suspencao from ( "
            + "select pg.patient_id,max(ps.start_date) data_suspencao from  patient p "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id where pg.voided=0 "
            + "and ps.voided=0 and p.voided=0 and pg.program_id=2 and ps.state=8 and ps.start_date<=:endDate and location_id=:location "
            + "group by p.patient_id union "
            + " select p.patient_id,max(o.obs_datetime) data_suspencao from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and o.voided=0 and o.concept_id=6272 "
            + "and o.value_coded=1709 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id "
            + "union select  p.patient_id,max(e.encounter_datetime) data_suspencao from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate and o.voided=0 and o.concept_id=6273 "
            + "and o.value_coded=1709 and e.encounter_type=6 and  e.location_id=:location group by p.patient_id ) suspenso group by patient_id) suspenso1 "
            + "inner join ( select patient_id,max(encounter_datetime) encounter_datetime from ( "
            + "select p.patient_id,max(e.encounter_datetime) encounter_datetime from  patient p "
            + "inner join encounter e on e.patient_id=p.patient_id where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and  "
            + "e.location_id=:location and e.encounter_type=18 group by p.patient_id union "
            + "Select  p.patient_id,max(value_datetime) encounter_datetime from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where  p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and o.concept_id=23866 "
            + "and o.value_datetime is not null and o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id"
            + ") consultaLev group by patient_id) consultaOuARV on suspenso1.patient_id=consultaOuARV.patient_id "
            + "where consultaOuARV.encounter_datetime<suspenso1.data_suspencao and suspenso1.data_suspencao between :startDate -interval 1 year  AND :endDate ";

    public static String findPatientsWhoDiedDuringTreatment =
        "select obito.patient_id from ( "
            + "select patient_id,max(data_obito) data_obito from ( "
            + "select pg.patient_id,max(ps.start_date) data_obito from 	patient p "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id "
            + "where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and ps.state=10 "
            + "and ps.start_date<=:endDate and location_id=:location "
            + "group by p.patient_id union select p.patient_id,max(o.obs_datetime) data_obito from	patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.obs_datetime<=:endDate and "
            + "o.voided=0 and o.concept_id=6272 and o.value_coded=1366 and e.encounter_type=53 and  e.location_id=:location group by p.patient_id "
            + "union select p.patient_id,max(e.encounter_datetime) data_obito from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id where e.voided=0 and p.voided=0 and e.encounter_datetime<=:endDate "
            + "and o.voided=0 and o.concept_id=6273 and o.value_coded=1366 and e.encounter_type=6 and  e.location_id=:location "
            + "group by p.patient_id union  "
            + "Select person_id,death_date from person p where p.dead=1 and p.death_date<=:endDate )transferido "
            + "group by patient_id) obito inner join ( "
            + "select patient_id,max(encounter_datetime) encounter_datetime from ( "
            + "select p.patient_id,max(e.encounter_datetime) encounter_datetime from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location and e.encounter_type in (18,6,9) "
            + "group by p.patient_id union "
            + "select p.patient_id,max(value_datetime) encounter_datetime from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and "
            + "o.concept_id=23866 and o.value_datetime is not null and o.value_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id ) consultaLev group by patient_id ) "
            + "consultaOuARV on obito.patient_id=consultaOuARV.patient_id "
            + "where consultaOuARV.encounter_datetime<obito.data_obito and obito.data_obito between :startDate -interval 1 year AND :endDate ";

    public static String findPatientsWhoAbandonedArtTreatment =
        "Select B7.patient_id from (select patient_id,max(data_levantamento) data_levantamento, "
            + "max(data_proximo_levantamento) data_proximo_levantamento, date_add(max(data_proximo_levantamento), INTERVAL 60 day) data_proximo_levantamento60 "
            + "from(select p.patient_id,max(o.value_datetime) data_levantamento, date_add(max(o.value_datetime), INTERVAL 30 day)  data_proximo_levantamento "
            + "from patient p inner join encounter e on p.patient_id = e.patient_id "
            + "inner join obs o on o.encounter_id = e.encounter_id "
            + "where  e.voided = 0 and p.voided = 0 and o.value_datetime <= :endDate and o.voided = 0 and o.concept_id = 23866 and e.encounter_type=52 and e.location_id=:location "
            + "group by p.patient_id union "
            + "select fila.patient_id, fila.data_levantamento,obs_fila.value_datetime data_proximo_levantamento from ( "
            + "select p.patient_id,max(e.encounter_datetime) as data_levantamento from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id where encounter_type=18 and e.encounter_datetime <=:endDate and e.location_id=:location and e.voided=0 and p.voided=0 group by p.patient_id)fila "
            + "inner join obs obs_fila on obs_fila.person_id=fila.patient_id "
            + "where obs_fila.voided=0 and obs_fila.concept_id=5096 and fila.data_levantamento=obs_fila.obs_datetime) maxFilaRecepcao group by patient_id "
            + "having date_add(max(data_proximo_levantamento), INTERVAL 60 day )<:endDate )B7 ";

    public static String getPatientsWhoStillInFirstLine =
        "select table_result.patient_id from ( "
            + "select ultima_consulta.patient_id,ultima_consulta.data_ultima_linha "
            + "       , obs_primeira_linha.value_coded primeira_linha_value "
            + "       , obs_outra_linha.value_coded outra_linha_value from "
            + "  ( select e.patient_id, max(e.encounter_datetime) data_ultima_linha "
            + "   from encounter e join obs o on o.encounter_id = e.encounter_id "
            + "   where e.encounter_type = 6 and e.voided = 0 and o.voided = 0 "
            + "    and e.encounter_datetime <= :endDate and e.location_id = :location group by e.patient_id ) ultima_consulta "
            + "    left join obs obs_primeira_linha  on  "
            + "        (obs_primeira_linha.person_id = ultima_consulta.patient_id "
            + "         and obs_primeira_linha.voided=0 "
            + "         and obs_primeira_linha.obs_datetime = ultima_consulta.data_ultima_linha "
            + "         and obs_primeira_linha.concept_id =21151  "
            + "         and obs_primeira_linha.value_coded =21150 "
            + "         and obs_primeira_linha.location_id =:location) left join obs obs_outra_linha  on "
            + "        (obs_outra_linha.person_id = ultima_consulta.patient_id "
            + "         and obs_outra_linha.voided=0 "
            + "         and obs_outra_linha.obs_datetime = ultima_consulta.data_ultima_linha "
            + "         and obs_outra_linha.concept_id =21151 and obs_outra_linha.location_id =:location "
            + "         and obs_outra_linha.value_coded <> 21150) group by patient_id) table_result "
            + "         where (primeira_linha_value is not null and outra_linha_value is null) "
            + "            or (primeira_linha_value is null and outra_linha_value is null) ";

    public static String findPatientsWithRegisteredViralLoad =
        "select encounter.patient_id from  encounter "
            + " join obs on obs.encounter_id = encounter.encounter_id "
            + " where encounter.encounter_type =6 and obs.voided = 0 and encounter.voided =0 "
            + " and obs.concept_id in (856,1305) "
            + " and encounter.encounter_datetime  <= :endDate and encounter.location_id =:location";

    public static String findPatientsWhoAreInSecondLine =
        "select table_result.patient_id from ( "
            + "select ultima_consulta.patient_id,ultima_consulta.data_ultima_linha from "
            + "( select e.patient_id, max(e.encounter_datetime) data_ultima_linha "
            + " from encounter e join obs o on o.encounter_id = e.encounter_id "
            + " where e.encounter_type = 6 and e.voided = 0 and o.voided = 0 "
            + "  and e.encounter_datetime <=:endDate and e.location_id = :location group by e.patient_id) ultima_consulta "
            + "   inner join obs obs_outra_linha  on "
            + "      (obs_outra_linha.person_id = ultima_consulta.patient_id "
            + "       and obs_outra_linha.voided=0 "
            + "       and obs_outra_linha.obs_datetime = ultima_consulta.data_ultima_linha "
            + "       and obs_outra_linha.concept_id =21151 and obs_outra_linha.location_id =:location "
            + "       and obs_outra_linha.value_coded =21148) group by patient_id) table_result";
  }
}
