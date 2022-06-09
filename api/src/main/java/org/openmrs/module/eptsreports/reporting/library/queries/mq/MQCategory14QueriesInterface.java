package org.openmrs.module.eptsreports.reporting.library.queries.mq;

import org.openmrs.module.eptsreports.reporting.utils.EptsQuerysUtils;
import org.openmrs.module.eptsreports.reporting.utils.WomanState;

public interface MQCategory14QueriesInterface {

  class QUERY {

    private static final String
        FIND_WOMAN_STATE_WHO_HAVE_MORE_THAN_3MONTHS_ON_ART_WITH_VIRALLOAD_REGISTERED_IN_THELAST12MONTHS =
            "MQ/FIND_WOMAN_STATE_WHO_HAVE_MORE_THAN_3MONTHS_ON_ART_WITH_VIRALLOAD_REGISTERED_IN_THELAST12MONTHS.sql";

    public static final String
        findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12Months =
            "select inicio_real.patient_id from (select patient_id,min(data_inicio) data_inicio from ("
                + "select p.patient_id,min(e.encounter_datetime) data_inicio from patient p inner join encounter e on p.patient_id=e.patient_id inner join obs o on o.encounter_id=e.encounter_id "
                + "where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and  e.encounter_datetime<=:endDate and e.location_id=:location group by p.patient_id "
                + "union "
                + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and o.concept_id=1190 and o.value_datetime is not null and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id "
                + "union "
                + "select pg.patient_id,min(date_enrolled) data_inicio from patient p "
                + "inner join patient_program pg on p.patient_id=pg.patient_id "
                + "where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endDate and location_id=:location group by pg.patient_id "
                + "union "
                + "SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location GROUP BY 	p.patient_id "
                + "union "
                + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id) inicio group by patient_id "
                + ") inicio_real "
                + "inner join ( "
                + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and "
                + "e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and  o.obs_datetime between date_add(date_add(:endDate, interval -12 MONTH), interval 1 day) and :endDate and e.location_id=:location group by p.patient_id) carga_viral on inicio_real.patient_id=carga_viral.patient_id "
                + "where carga_viral.data_carga>=date_add(inicio_real.data_inicio, INTERVAL 90 DAY )  ";

    public static final String
        findPatientsWhoHaveMoreThan3MonthsOnArtWithViralLoadResultLessthan1000RegisteredInTheLast12Months =
            "select inicio_real.patient_id  from ( "
                + "select patient_id,min(data_inicio) data_inicio from ( "
                + "select p.patient_id,min(e.encounter_datetime) data_inicio from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id	"
                + "inner join obs o on o.encounter_id=e.encounter_id "
                + "where e.voided=0 and o.voided=0 and p.voided=0 and  e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and  e.encounter_datetime<=:endDate and e.location_id=:location group by p.patient_id "
                + "union "
                + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and o.concept_id=1190 and o.vaselect   mdsfinal.patient_id\n"
                + "            from  \n"
                + "            (  \n"
                + "            select carga_viral.patient_id, carga_viral.data_carga,max(mds.data_mdc) data_mds  \n"
                + "            from  \n"
                + "            (  \n"
                + "            select p.patient_id,max(o.obs_datetime) data_carga  \n"
                + "            from patient p  \n"
                + "            inner join encounter e on p.patient_id=e.patient_id  \n"
                + "            inner join obs o on e.encounter_id=o.encounter_id  \n"
                + "            where p.voided=0 and e.voided=0 and o.voided=0 and  \n"
                + "            e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and  \n"
                + "            o.obs_datetime between date_add(date_add(:endDate, interval -12 MONTH), interval 1 day) and :endDate and e.location_id=:location \n"
                + "            group by p.patient_id  \n"
                + "            ) carga_viral  \n"
                + "            inner join  \n"
                + "            (  \n"
                + "                select p.patient_id,max(e.encounter_datetime) data_mdc from patient p    \n"
                + "                join encounter e on p.patient_id=e.patient_id   \n"
                + "                join obs grupo on grupo.encounter_id=e.encounter_id    \n"
                + "                join obs o on o.encounter_id=e.encounter_id    \n"
                + "                join obs obsEstado on obsEstado.encounter_id=e.encounter_id   \n"
                + "                where  e.encounter_type in(6) and e.location_id=:location and o.concept_id=165174 and o.value_coded in(23724,23730,165179,165315,23888,23729,23720) and o.voided=0   \n"
                + "                and grupo.concept_id=165323  and grupo.voided=0 and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1557)  and obsEstado.voided=0  and grupo.voided=0  \n"
                + "                group by p.patient_id\n"
                + "\n"
                + "                union\n"
                + "\n"
                + "                select max_tipo_despensa.patient_id,max_tipo_despensa.data_mdc from   (   \n"
                + "                select max_tl.patient_id, max_tl.data_mdc from   (   \n"
                + "                select p.patient_id,max(o.obs_datetime) data_mdc from patient p    \n"
                + "                join encounter e on p.patient_id=e.patient_id     \n"
                + "                join obs o on o.encounter_id=e.encounter_id    \n"
                + "                where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 and o.concept_id=23739 and e.location_id=:location  \n"
                + "                group by p.patient_id  \n"
                + "                ) max_tl    \n"
                + "                join obs on obs.person_id=max_tl.patient_id and max_tl.data_mdc=obs.obs_datetime   \n"
                + "                where obs.concept_id=23739 and obs.value_coded in(23720,23888) and obs.voided=0 and obs.location_id=:location  \n"
                + "                ) max_tipo_despensa    \n"
                + "\n"
                + "\n"
                + "                union  \n"
                + "\n"
                + "                select f.patient_id,f.data_levantamento data_mdc from   \n"
                + "                (  \n"
                + "                select fila.patient_id,max(fila.data_levantamento) data_levantamento,obs_fila.value_datetime data_proximo_levantamento  from  \n"
                + "                (  \n"
                + "                Select p.patient_id,max(encounter_datetime) data_levantamento  from  patient p \n"
                + "                inner join encounter e on e.patient_id=p.patient_id  \n"
                + "                where  p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location\n"
                + "                group by p.patient_id \n"
                + "                )fila  \n"
                + "                inner join  obs obs_fila on obs_fila.person_id=fila.patient_id \n"
                + "                and obs_fila.voided=0  and obs_fila.obs_datetime=fila.data_levantamento  \n"
                + "                and obs_fila.concept_id=5096  \n"
                + "                and obs_fila.location_id=:location\n"
                + "                and (DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) >= 83 and DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) <= 97)  \n"
                + "                group by fila.patient_id  \n"
                + "                ) f  \n"
                + "                union  \n"
                + "                select f.patient_id,f.data_levantamento data_mdc from  \n"
                + "                (  \n"
                + "                select fila.patient_id,max(fila.data_levantamento) data_levantamento,obs_fila.value_datetime data_proximo_levantamento  from  (  \n"
                + "                Select p.patient_id,max(encounter_datetime) data_levantamento  from  patient p \n"
                + "                inner join encounter e on e.patient_id=p.patient_id  \n"
                + "                where  p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location\n"
                + "                group by p.patient_id \n"
                + "                )fila  \n"
                + "                inner join  obs obs_fila on obs_fila.person_id=fila.patient_id \n"
                + "                and obs_fila.voided=0  and obs_fila.obs_datetime=fila.data_levantamento  \n"
                + "                and obs_fila.concept_id=5096  \n"
                + "                and obs_fila.location_id=:location\n"
                + "                where (DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) >= 173 and DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) <= 187)  \n"
                + "                group by fila.patient_id  \n"
                + "                ) f      \n"
                + "           \n"
                + "            ) mds on mds.patient_id=carga_viral.patient_id  \n"
                + "              \n"
                + "            where mds.data_mdc BETWEEN date_add(carga_viral.data_carga, interval -12 MONTH) and carga_viral.data_carga  \n"
                + "           \n"
                + "            group by carga_viral.patient_id  \n"
                + "           \n"
                + "            ) mdsfinal  \n"
                + "lue_datetime is not null and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id "
                + "union "
                + "select pg.patient_id,min(date_enrolled) data_inicio from patient p "
                + "inner join patient_program pg on p.patient_id=pg.patient_id "
                + "where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endDate and location_id=:location group by pg.patient_id "
                + "union "
                + "SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location GROUP BY p.patient_id "
                + "union "
                + "Select p.patient_id,min(value_datetime) data_inicio from 	patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id) inicio group by patient_id) inicio_real "
                + "inner join ( "
                + "select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga,obs.concept_id,obs.value_coded from ( "
                + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and  o.obs_datetime between date_add(date_add(:endDate, interval -12 MONTH), interval 1 day) and :endDate and e.location_id=:location group by p.patient_id) ultima_carga "
                + "inner join obs on obs.person_id=ultima_carga.patient_id and obs.obs_datetime=ultima_carga.data_carga "
                + "where obs.voided=0 and ((obs.concept_id=856 and obs.value_numeric<1000) or (obs.concept_id=1305 and obs.value_coded in (1306,23814,23905,23906,23907,23908,23904)))  and obs.location_id=:location) carga_viral on inicio_real.patient_id=carga_viral.patient_id "
                + "where carga_viral.data_carga>=date_add(inicio_real.data_inicio, INTERVAL 90 DAY) ";

    public static final String findPatientsWhoAreActiveOnArtAndInAtleastOneDSDWithViralSupression =
        "select mdsfinal.patient_id from   (   "
            + "select carga_viral.patient_id, carga_viral.data_carga,max(mds.data_mdc) data_mds  from  ( "
            + "select p.patient_id,max(o.obs_datetime) data_carga   from patient p   "
            + "inner join encounter e on p.patient_id=e.patient_id   "
            + "inner join obs o on e.encounter_id=o.encounter_id   "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and   "
            + "e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and   "
            + "o.obs_datetime between date_add(date_add(:endDate, interval -12 MONTH), interval 1 day) and :endDate and e.location_id=:location "
            + "group by p.patient_id  "
            + ") carga_viral   "
            + "inner join   (   "
            + "select p.patient_id,max(e.encounter_datetime) data_mdc from patient p     "
            + "join encounter e on p.patient_id=e.patient_id    "
            + "join obs grupo on grupo.encounter_id=e.encounter_id     "
            + "join obs o on o.encounter_id=e.encounter_id     "
            + "join obs obsEstado on obsEstado.encounter_id=e.encounter_id    "
            + "where  e.encounter_type in(6) and e.location_id=:location and o.concept_id=165174 and o.value_coded in(23724,23730,165179,165315,23888,23729,23720) and o.voided=0    "
            + "and grupo.concept_id=165323  and grupo.voided=0 and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1557)  and obsEstado.voided=0  and grupo.voided=0   "
            + "group by p.patient_id "
            + "union "
            + "select max_tipo_despensa.patient_id,max_tipo_despensa.data_mdc from   (    "
            + "select max_tl.patient_id, max_tl.data_mdc from   (    "
            + "select p.patient_id,max(o.obs_datetime) data_mdc from patient p     "
            + "join encounter e on p.patient_id=e.patient_id      "
            + "join obs o on o.encounter_id=e.encounter_id     "
            + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 and o.concept_id=23739 and e.location_id=:location   "
            + "group by p.patient_id   "
            + ") max_tl     "
            + "join obs on obs.person_id=max_tl.patient_id and max_tl.data_mdc=obs.obs_datetime    "
            + "where obs.concept_id=23739 and obs.value_coded in(23720,23888) and obs.voided=0 and obs.location_id=:location  "
            + ") max_tipo_despensa     "
            + "union   "
            + "select f.patient_id,f.data_levantamento data_mdc from    "
            + "( "
            + "select fila.patient_id,max(fila.data_levantamento) data_levantamento,obs_fila.value_datetime data_proximo_levantamento  from   (   "
            + "Select p.patient_id,max(encounter_datetime) data_levantamento  from  patient p  "
            + "inner join encounter e on e.patient_id=p.patient_id   "
            + "where  p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location "
            + "group by p.patient_id  "
            + ")fila   "
            + "inner join  obs obs_fila on obs_fila.person_id=fila.patient_id  "
            + "and obs_fila.voided=0  and obs_fila.obs_datetime=fila.data_levantamento   "
            + "and obs_fila.concept_id=5096   "
            + "and obs_fila.location_id=:location "
            + "and (DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) >= 83 and DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) <= 97)   "
            + "group by fila.patient_id   "
            + ") f   "
            + "union   "
            + "select f.patient_id,f.data_levantamento data_mdc from   (   "
            + "select fila.patient_id,max(fila.data_levantamento) data_levantamento,obs_fila.value_datetime data_proximo_levantamento  from  (   "
            + "Select p.patient_id,max(encounter_datetime) data_levantamento  from  patient p  "
            + "inner join encounter e on e.patient_id=p.patient_id   "
            + "where  p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location "
            + "group by p.patient_id  "
            + ")fila   "
            + "inner join  obs obs_fila on obs_fila.person_id=fila.patient_id  "
            + "and obs_fila.voided=0  and obs_fila.obs_datetime=fila.data_levantamento   "
            + "and obs_fila.concept_id=5096   "
            + "and obs_fila.location_id=:location "
            + "where (DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) >= 173 and DATEDIFF(obs_fila.value_datetime,fila.data_levantamento) <= 187)   "
            + "group by fila.patient_id   "
            + ") f  "
            + ") mds on mds.patient_id=carga_viral.patient_id   "
            + "where mds.data_mdc BETWEEN date_add(carga_viral.data_carga, interval -12 MONTH) and carga_viral.data_carga   "
            + "group by carga_viral.patient_id   "
            + ") mdsfinal  ";

    public static final String
        findWomanStateWhoHaveMoreThan3MonthsOnArtWithViralLoadRegisteredInTheLast12Months(
            WomanState womanState) {

      String query =
          EptsQuerysUtils.loadQuery(
              FIND_WOMAN_STATE_WHO_HAVE_MORE_THAN_3MONTHS_ON_ART_WITH_VIRALLOAD_REGISTERED_IN_THELAST12MONTHS);
      switch (womanState) {
        case PREGNANT:
          query = String.format(query, 2);
          break;

        case BREASTFEEDING:
          query = String.format(query, 1);
          break;
      }
      return query;
    }
  }
}
