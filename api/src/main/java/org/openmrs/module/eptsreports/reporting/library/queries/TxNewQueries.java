/** */
package org.openmrs.module.eptsreports.reporting.library.queries;

import org.openmrs.module.eptsreports.reporting.utils.CommunityType;

/** @author Stélio Moiane */
public interface TxNewQueries {

  class QUERY {

    public static final String findPatientsWithAProgramStateMarkedAsTransferedInInAPeriod =
        "select minState.patient_id from  ("
            + "SELECT p.patient_id, pg.patient_program_id, MIN(ps.start_date) as minStateDate  FROM patient p  "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id "
            + "WHERE pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and location_id=:location  and ps.start_date BETWEEN :startDate and :endDate "
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
                + "WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 AND obsData.value_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location GROUP BY p.patient_id "
                + ") tr GROUP BY tr.patient_id ";

    public static final String findPatientsWhoAreNewlyEnrolledOnART =
        "SELECT patient_id FROM "
            + "(SELECT patient_id, MIN(art_start_date) art_start_date FROM "
            + "(SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18,6,9) "
            + "AND o.concept_id=1255 AND o.value_coded=1256 AND e.encounter_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type IN (18,6,9,53) "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT pg.patient_id, MIN(date_enrolled) art_start_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id=pg.patient_id "
            + "WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled<=:endDate AND location_id=:location GROUP BY pg.patient_id "
            + "UNION SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "WHERE p.voided=0 AND e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 "
            + "AND o.concept_id=23866 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id) "
            + "art_start GROUP BY patient_id ) tx_new WHERE art_start_date BETWEEN :startDate AND :endDate";

    public static final String findPatientsWhoAreNewlyEnrolledOnArtByAgeAndGender =
        "SELECT patient_id FROM "
            + "(SELECT patient_id, MIN(art_start_date) art_start_date FROM "
            + "(SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18,6,9) "
            + "AND o.concept_id=1255 AND o.value_coded=1256 AND e.encounter_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type IN (18,6,9,53) "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT pg.patient_id, MIN(date_enrolled) art_start_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id=pg.patient_id "
            + "WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled<=:endDate AND location_id=:location GROUP BY pg.patient_id "
            + "UNION SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "WHERE p.voided=0 AND e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 "
            + "AND o.concept_id=23866 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endDate AND e.location_id=:location GROUP BY p.patient_id) "
            + "art_start GROUP BY patient_id ) tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id WHERE TIMESTAMPDIFF(year,birthdate,art_start_date) BETWEEN %d AND %d AND gender='%s' AND birthdate IS NOT NULL";

    public static final String findPatientsWithUnknownAgeByGender =
        "SELECT patient_id FROM patient "
            + "INNER JOIN person ON patient_id=person_id "
            + "WHERE patient.voided=0 AND person.voided=0 AND gender = '%s' AND birthdate IS NULL";

    public static final String findPatientsWhoAreNewlyEnrolledOnArtByAge =
        "SELECT patient_id FROM "
            + "(SELECT patient_id, MIN(art_start_date) art_start_date FROM "
            + "(SELECT p.patient_id, MIN(e.encounter_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 AND o.voided=0 AND p.voided=0 AND e.encounter_type in (18,6,9) "
            + "AND o.concept_id=1255 AND o.value_coded=1256 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type IN (18,6,9,53) "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT pg.patient_id, MIN(date_enrolled) art_start_date FROM patient p "
            + "INNER JOIN patient_program pg ON p.patient_id=pg.patient_id "
            + "WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled BETWEEN :startDate AND :endDate AND location_id=:location GROUP BY pg.patient_id "
            + "UNION SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "WHERE p.voided=0 AND e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location GROUP BY p.patient_id "
            + "UNION "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 "
            + "AND o.concept_id=23866 AND o.value_datetime is NOT NULL AND o.value_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location GROUP BY p.patient_id) "
            + "art_start GROUP BY patient_id ) tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id WHERE TIMESTAMPDIFF(year,birthdate,art_start_date) BETWEEN %d AND %d AND birthdate IS NOT NULL";

    public static final String findPatientsWhoStartedARTAtComunnity =
        "SELECT patient_id FROM \n"
            + " (\n"
            + "	SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p \n"
            + "			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
            + "			INNER JOIN obs mdcMode ON mdcMode.encounter_id = e.encounter_id\n"
            + "			INNER JOIN obs mdcStatus ON mdcStatus.encounter_id  = e.encounter_id\n"
            + "				WHERE p.voided = 0 AND e.voided = 0 AND mdcMode.voided = 0 AND mdcStatus.voided = 0 \n"
            + "				AND mdcMode.concept_id = 165174 AND mdcStatus.concept_id = 165322 AND mdcStatus.value_coded = 1256\n"
            + "				AND e.encounter_type = 6 AND mdcMode.value_coded IN (165178, 165179, 165264, 165265, 23731) \n"
            + "				AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location \n"
            + "					GROUP BY e.patient_id\n"
            + "	UNION\n"
            + "\n"
            + "	SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p\n"
            + "		INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
            + "		INNER JOIN obs o ON o.encounter_id = e.encounter_id \n"
            + "			WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 165174 \n"
            + "			AND e.encounter_type = 18 AND o.value_coded IN (165183,165182,165181,165180,165179,165178)  \n"
            + "			AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id = :location \n"
            + "				GROUP BY e.patient_id \n"
            + "						        \n"
            + ")start_at_community GROUP BY patient_id;";

    // IM-ER Comunitario
    public static final String findPatientsWhoStartedARTAtComunnityCM =
        "               select patient_id                                                                                                                       \n"
            + "               from                                                                                                                                    \n"
            + "               (                                                                                                                                       \n"
            + "                   select *                                                                                                                            \n"
            + "                   from                                                                                                                                \n"
            + "                   (                                                                                                                                   \n"
            + "                       select *                                                                                                                        \n"
            + "                       from                                                                                                                            \n"
            + "                       (                                                                                                                               \n"
            + "                                                                                                                                                       \n"
            + "                           select ultimo_fila.patient_id,                                                                                              \n"
            + "                                 min(data_proximo_levantamento.obs_datetime) data_consulta,                                                          \n"
            + "                                 case modo_dispensa.value_coded                                                                                        \n"
            + "                                   when 165177 then 1                                                                                                  \n"
            + "                                   when 165179 then 2                                                                                                  \n"
            + "                                   when 165178 then 3                                                                                                  \n"
            + "                                   when 165181 then 4                                                                                                  \n"
            + "                                   when 165182 then 5\n"
            + "                                   when 165183 then 5\n"
            + "                                   when 165176 then 6                                                                                                  \n"
            + "                                 else 10 end as tipo_dispensa,                                                                                         \n"
            + "                                 1  as fonte,                                                                                                          \n"
            + "                           1  as ordem_mdc                                                                                                             \n"
            + "                           from                                                                                                                        \n"
            + "                           (                                                                                                                           \n"
            + "                               select p.patient_id, min(encounter_datetime) data_ultimo_levantamento                                                   \n"
            + "                               from patient p                                                                                                          \n"
            + "                                 inner join encounter e on e.patient_id=p.patient_id                                                                   \n"
            + "                               where p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location                                   \n"
            + "                                 and e.encounter_datetime between :startDate and  :endDate                                                            \n"
            + "                                 group by p.patient_id                                                                                                 \n"
            + "                           ) ultimo_fila                                                                                                               \n"
            + "                             inner join encounter e on e.patient_id = ultimo_fila.patient_id                                                           \n"
            + "                               inner join obs data_proximo_levantamento on data_proximo_levantamento.encounter_id = e.encounter_id                     \n"
            + "                               inner join obs modo_dispensa on modo_dispensa.encounter_id = e.encounter_id                                             \n"
            + "                           where e.voided = 0 and data_proximo_levantamento.voided = 0 and modo_dispensa.voided = 0 and e.encounter_type=18            \n"
            + "                             and date(e.encounter_datetime) = date(ultimo_fila.data_ultimo_levantamento) and modo_dispensa.concept_id =165174          \n"
            + "                               and data_proximo_levantamento.concept_id =5096 and e.location_id = :location group by ultimo_fila.patient_id            \n"
            + "                                                                                                                                                       \n"
            + "                            union                                                                                                                      \n"
            + "                                                                                                                                                       \n"
            + "                           select ultimo_mdc.patient_id,                                                                                               \n"
            + "                                  ultimo_mdc.data_consulta,                                                                                            \n"
            + "                                  case o.value_coded                                                                                                   \n"
            + "                                   when 165315 then 1                                                                                                  \n"
            + "                                   when 165179 then 2                                                                                                  \n"
            + "                                   when 165178 then 3                                                                                                  \n"
            + "                                   when 165264 then 4                                                                                                  \n"
            + "                                   when 165265 then 5                                                                                                  \n"
            + "                                   when 165316 then 6                                                                                                  \n"
            + "                                   else 10 end as tipo_dispensa,                                                                                       \n"
            + "                                   2  as fonte,                                                                                                        \n"
            + "                                   case o.value_coded                                                                                                  \n"
            + "                                   when 165315 then 1                                                                                                  \n"
            + "                                   when 165179 then 1                                                                                                  \n"
            + "                                   when 165178 then 1                                                                                                  \n"
            + "                                   when 165264 then 1                                                                                                  \n"
            + "                                   when 165265 then 1                                                                                                  \n"
            + "                                   when 165316 then 1                                                                                                  \n"
            + "                                 else 2 end as ordem_mdc                                                                                               \n"
            + "                           from                                                                                                                        \n"
            + "                           (                                                                                                                           \n"
            + "                               select p.patient_id, min(e.encounter_datetime) data_consulta                                                            \n"
            + "                               from patient p                                                                                                          \n"
            + "                                 inner join encounter e on p.patient_id=e.patient_id                                                                   \n"
            + "                                 inner join obs o on o.encounter_id=e.encounter_id                                                                     \n"
            + "                               where p.voided = 0 and e.voided=0 and o.voided=0                                                                        \n"
            + "                                 and e.encounter_type = 6 and e.encounter_datetime between :startDate and  :endDate and e.location_id=:location        \n"
            + "                                 group by p.patient_id                                                                                                 \n"
            + "                           ) ultimo_mdc                                                                                                                \n"
            + "                             inner join encounter e on ultimo_mdc.patient_id=e.patient_id                                                              \n"
            + "                         inner join obs grupo on grupo.encounter_id=e.encounter_id                                                                     \n"
            + "                            inner join obs o on o.encounter_id=e.encounter_id                                                                          \n"
            + "                             inner join obs obsEstado on obsEstado.encounter_id=e.encounter_id                                                         \n"
            + "                           where e.encounter_type=6 and e.location_id=:location                                                                        \n"
            + "                             and o.concept_id=165174 and o.voided=0                                                                                    \n"
            + "                               and grupo.concept_id=165323  and grupo.voided=0                                                                         \n"
            + "                               and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)                                                \n"
            + "                               and obsEstado.voided=0  and grupo.voided=0                                                                              \n"
            + "                               and grupo.obs_id=o.obs_group_id and grupo.obs_id=obsEstado.obs_group_id                                                 \n"
            + "                               and e.encounter_datetime=ultimo_mdc.data_consulta  and o.value_coded                                                    \n"
            + "                                                                                                                                                       \n"
            + "                       ) todas_fontes                                                                                                                  \n"
            + "                       order by patient_id,data_consulta desc, fonte, ordem_mdc                                                                        \n"
            + "                   ) primeira_fonte                                                                                                                    \n"
            + "                   group by patient_id                                                                                                                 \n"
            + "               ) dispensa                                                                                                                              \n"
            + "               where dispensa.tipo_dispensa = 5   ";

    public static String findPatientsInComunnityDispensationByType(
        final CommunityType comunityType) {

      String query =
          "SELECT externo.patient_id FROM (\n"
              + "		\n"
              + "SELECT fila.patient_id    FROM \n"
              + "               	( \n"
              + "               		SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p \n"
              + "               			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
              + "               			INNER JOIN obs o ON o.encounter_id = e.encounter_id \n"
              + "               				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 \n"
              + "               				AND e.encounter_type = 6 AND o.value_coded = 1256 \n"
              + "               				AND e.encounter_datetime <= :endDate AND e.location_id = :location \n"
              + "               					GROUP BY e.patient_id) last_encounter\n"
              + "               		INNER JOIN( \n"
              + "               			\n"
              + "               		SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p \n"
              + "               			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
              + "               			INNER JOIN obs o ON o.encounter_id = e.encounter_id \n"
              + "               				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 165174 \n"
              + "               				AND e.encounter_type = 18 AND o.value_coded IN (165183,165182,165181,165180,165179,165178,165265,165264)  \n"
              + "               				AND e.encounter_datetime <= :endDate AND e.location_id = :location \n"
              + "							GROUP BY e.patient_id ) fila ON fila.patient_id = last_encounter.patient_id\n"
              + "									WHERE fila.min_date = last_encounter.min_date  group by fila.patient_id ) externo\n"
              + "                                    \n"
              + "                                    UNION \n"
              + "   SELECT externo2.patient_id FROM (\n"
              + "		\n"
              + "SELECT fila.patient_id    FROM \n"
              + "               	( \n"
              + "               		SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p \n"
              + "               			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
              + "               			INNER JOIN obs o ON o.encounter_id = e.encounter_id \n"
              + "               				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0  AND o.concept_id = 165174 \n"
              + "               				AND e.encounter_type = 6 AND o.value_coded IN (165183,165182,165181,165180,165179,165178,165265,165264)  \n"
              + "               				AND e.encounter_datetime <= :endDate AND e.location_id = :location \n"
              + "               					GROUP BY e.patient_id) last_encounter\n"
              + "               		INNER JOIN( \n"
              + "               			\n"
              + "               		SELECT e.patient_id, MIN(e.encounter_datetime) min_date FROM patient p \n"
              + "               			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
              + "               			INNER JOIN obs o ON o.encounter_id = e.encounter_id \n"
              + "               				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 165174 \n"
              + "               				AND e.encounter_type = 18 AND o.value_coded NOT IN (165182,165183,165180,165181,165178,165179)  \n"
              + "               				AND e.encounter_datetime <= :endDate AND e.location_id = :location \n"
              + "							GROUP BY e.patient_id ) fila ON fila.patient_id = last_encounter.patient_id\n"
              + "									WHERE fila.min_date = last_encounter.min_date  group by fila.patient_id ) externo2 ";

      switch (comunityType) {
        case COMMUNITY_DISPENSE_PROVIDER:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165178");
          break;

        case COMMUNITY_DISPENSE_APE:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165179");
          break;

        case DAILY_MOBILE_BRIGADES:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165180");
          break;

        case NIGHT_MOBILE_BRIGADES:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165181");
          break;

        case DAILY_MOBILE_CLINICS:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165182");
          break;

        case NIGHT_MOBILE_CLINICS:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165183");
          break;

        case MOBILE_CLINICS:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165265");
          break;

        case MOBILE_BRIGADES:
          query =
              query.replace(
                  "IN (165183,165182,165181,165180,165179,165178,165265,165264)", "=165264");
          break;

        default:
          query = query + "";
          break;
      }

      return query;
    }

    public static final String findPatientsInComunnityDispensation =
        "SELECT patient_id FROM\n"
            + "(\n"
            + "	SELECT patient_id, MAX(max_date) max_date FROM\n"
            + "	(\n"
            + "		SELECT e.patient_id, MAX(e.encounter_datetime) max_date FROM patient p \n"
            + "			INNER JOIN encounter e ON e.patient_id = p.patient_id \n"
            + "			INNER JOIN obs mdcMode ON mdcMode.encounter_id = e.encounter_id\n"
            + "			INNER JOIN obs mdcStatus ON mdcStatus.encounter_id  = e.encounter_id\n"
            + "				WHERE p.voided = 0 AND e.voided = 0 AND mdcMode.voided = 0 AND mdcStatus.voided = 0 \n"
            + "				AND mdcMode.concept_id = 165174 AND mdcStatus.concept_id = 165322 AND mdcStatus.value_coded IN (1256,1257)\n"
            + "				AND e.encounter_type IN (6,35) AND mdcMode.value_coded IN (165178, 165179, 165264, 165265, 23731) \n"
            + "				AND e.encounter_datetime <= :endDate AND e.location_id = :location \n"
            + "					GROUP BY e.patient_id\n"
            + "\n"
            + "		UNION\n"
            + "		\n"
            + "		SELECT e.patient_id, MAX(e.encounter_datetime) max_date FROM patient p\n"
            + "			INNER JOIN encounter e ON e.patient_id = p.patient_id\n"
            + "			INNER JOIN obs o ON o.encounter_id = e.encounter_id\n"
            + "				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 165174\n"
            + "				AND e.encounter_type = 18 AND o.value_coded IN (165183,165182,165181,165180,165179,165178) \n"
            + "				AND e.encounter_datetime <= :endDate AND e.location_id = :location\n"
            + "					GROUP BY e.patient_id\n"
            + "					\n"
            + "	)max_community_dispensation GROUP BY patient_id\n"
            + ")community_dispensation";

    public static final String findPatientsInComunnityCMDispensation =
        "	               select patient_id                                                                                                                       \n"
            + "               from                                                                                                                                    \n"
            + "               (                                                                                                                                       \n"
            + "                   select *                                                                                                                            \n"
            + "                   from                                                                                                                                \n"
            + "                   (                                                                                                                                   \n"
            + "                       select *                                                                                                                        \n"
            + "                       from                                                                                                                            \n"
            + "                       (                                                                                                                               \n"
            + "                                                                                                                                                       \n"
            + "                           select ultimo_fila.patient_id,                                                                                              \n"
            + "                                 max(data_proximo_levantamento.value_datetime) data_consulta,                                                          \n"
            + "                                 case modo_dispensa.value_coded                                                                                        \n"
            + "                                   when 165177 then 1                                                                                                  \n"
            + "                                   when 165179 then 2                                                                                                  \n"
            + "                                   when 165178 then 3                                                                                                  \n"
            + "                                   when 165181 then 4                                                                                                  \n"
            + "                                   when 165182 then 5\n"
            + "                                   when 165183 then 5\n"
            + "                                   when 165176 then 6                                                                                                  \n"
            + "                                 else 10 end as tipo_dispensa,                                                                                         \n"
            + "                                 1  as fonte,                                                                                                          \n"
            + "                           1  as ordem_mdc                                                                                                             \n"
            + "                           from                                                                                                                        \n"
            + "                           (                                                                                                                           \n"
            + "                               select p.patient_id, max(encounter_datetime) data_ultimo_levantamento                                                   \n"
            + "                               from patient p                                                                                                          \n"
            + "                                 inner join encounter e on e.patient_id=p.patient_id                                                                   \n"
            + "                               where p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location                                   \n"
            + "                                 and e.encounter_datetime <=:endDate                                                                                   \n"
            + "                                 group by p.patient_id                                                                                                 \n"
            + "                           ) ultimo_fila                                                                                                               \n"
            + "                             inner join encounter e on e.patient_id = ultimo_fila.patient_id                                                           \n"
            + "                               inner join obs data_proximo_levantamento on data_proximo_levantamento.encounter_id = e.encounter_id                     \n"
            + "                               inner join obs modo_dispensa on modo_dispensa.encounter_id = e.encounter_id                                             \n"
            + "                           where e.voided = 0 and data_proximo_levantamento.voided = 0 and modo_dispensa.voided = 0 and e.encounter_type=18            \n"
            + "                             and date(e.encounter_datetime) = date(ultimo_fila.data_ultimo_levantamento) and modo_dispensa.concept_id =165174          \n"
            + "                               and data_proximo_levantamento.concept_id =5096 and e.location_id = :location group by ultimo_fila.patient_id            \n"
            + "                                                                                                                                                       \n"
            + "                            union                                                                                                                      \n"
            + "                                                                                                                                                       \n"
            + "                           select ultimo_mdc.patient_id,                                                                                               \n"
            + "                                  ultimo_mdc.data_consulta,                                                                                            \n"
            + "                                  case o.value_coded                                                                                                   \n"
            + "                                   when 165315 then 1                                                                                                  \n"
            + "                                   when 165179 then 2                                                                                                  \n"
            + "                                   when 165178 then 3                                                                                                  \n"
            + "                                   when 165264 then 4                                                                                                  \n"
            + "                                   when 165265 then 5                                                                                                  \n"
            + "                                   when 165316 then 6                                                                                                  \n"
            + "                                   else 10 end as tipo_dispensa,                                                                                       \n"
            + "                                   2  as fonte,                                                                                                        \n"
            + "                                   case o.value_coded                                                                                                  \n"
            + "                                   when 165315 then 1                                                                                                  \n"
            + "                                   when 165179 then 1                                                                                                  \n"
            + "                                   when 165178 then 1                                                                                                  \n"
            + "                                   when 165264 then 1                                                                                                  \n"
            + "                                   when 165265 then 1                                                                                                  \n"
            + "                                   when 165316 then 1                                                                                                  \n"
            + "                                 else 2 end as ordem_mdc                                                                                               \n"
            + "                           from                                                                                                                        \n"
            + "                           (                                                                                                                           \n"
            + "                               select p.patient_id, max(e.encounter_datetime) data_consulta                                                            \n"
            + "                               from patient p                                                                                                          \n"
            + "                                 inner join encounter e on p.patient_id=e.patient_id                                                                   \n"
            + "                                 inner join obs o on o.encounter_id=e.encounter_id                                                                     \n"
            + "                               where p.voided = 0 and e.voided=0 and o.voided=0                                                                        \n"
            + "                                 and e.encounter_type = 6 and e.encounter_datetime<=:endDate and e.location_id=:location                             \n"
            + "                                 group by p.patient_id                                                                                                 \n"
            + "                           ) ultimo_mdc                                                                                                                \n"
            + "                             inner join encounter e on ultimo_mdc.patient_id=e.patient_id                                                              \n"
            + "                         inner join obs grupo on grupo.encounter_id=e.encounter_id                                                                     \n"
            + "                            inner join obs o on o.encounter_id=e.encounter_id                                                                          \n"
            + "                             inner join obs obsEstado on obsEstado.encounter_id=e.encounter_id                                                         \n"
            + "                           where e.encounter_type=6 and e.location_id=:location                                                                      \n"
            + "                             and o.concept_id=165174 and o.voided=0                                                                                    \n"
            + "                               and grupo.concept_id=165323  and grupo.voided=0                                                                         \n"
            + "                               and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)                                                \n"
            + "                               and obsEstado.voided=0  and grupo.voided=0                                                                              \n"
            + "                               and grupo.obs_id=o.obs_group_id and grupo.obs_id=obsEstado.obs_group_id                                                 \n"
            + "                               and e.encounter_datetime=ultimo_mdc.data_consulta  and o.value_coded                                                    \n"
            + "                                                                                                                                                       \n"
            + "                       ) todas_fontes                                                                                                                  \n"
            + "                       order by patient_id,data_consulta desc, fonte, ordem_mdc                                                                        \n"
            + "                   ) primeira_fonte                                                                                                                    \n"
            + "                   group by patient_id                                                                                                                 \n"
            + "               ) dispensa                                                                                                                              \n"
            + "               where dispensa.tipo_dispensa = 5   			";
  }
}
