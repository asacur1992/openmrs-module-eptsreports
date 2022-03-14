package org.openmrs.module.eptsreports.reporting.library.queries.mq;

import org.openmrs.module.eptsreports.reporting.utils.EptsQuerysUtils;

public interface MQCategory14QueriesInterface {

  class QUERY {

    private static final String
        FIND_WOMAN_STATE_WHO_HAVE_MORE_THAN_3MONTHS_ON_ART_WITH_VIRALLOAD_REGISTERED_IN_THELAST12MONTHS =
            "MQ/FIND_WOMAN_STATE_WHO_HAVE_MORE_THAN_3MONTHS_ON_ART_WITH_VIRALLOAD_REGISTERED_IN_THELAST12MONTHS.sql";

    public enum WomanState {
      PREGNANT,
      BREASTFEEDING;
    }

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
                + "where carga_viral.data_carga>=date_add(inicio_real.data_inicio, interval 3 MONTH )  ";

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
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and o.concept_id=1190 and o.value_datetime is not null and  o.value_datetime<=:endDate and e.location_id=:location group by p.patient_id "
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
                + "where carga_viral.data_carga>=date_add(inicio_real.data_inicio, interval 3 MONTH) ";

    public static final String findPatientsWhoAreActiveOnArtAndInAtleastOneDSDWithViralSupression =
        "select mdsfinal.patient_id  "
            + "from  "
            + "(  "
            + "select carga_viral.patient_id, carga_viral.data_carga,max(mds.data_mds) data_mds  "
            + "from  "
            + "(  "
            + "select p.patient_id,max(o.obs_datetime) data_carga  "
            + "from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on e.encounter_id=o.encounter_id  "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and  "
            + "e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and  "
            + "o.obs_datetime between date_add(date_add(:endDate, interval -12 MONTH), interval 1 day) and :endDate and e.location_id=:location  "
            + "group by p.patient_id  "
            + ") carga_viral  "
            + "inner join  "
            + "(  "
            + "select p.patient_id,e.encounter_id,o.concept_id,o.obs_datetime data_mds  "
            + "from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 and  "
            + "o.concept_id in (23724,23730,23888,23729,165177,23739) and e.location_id=:location and  "
            + "o.obs_datetime between date_add(:endDate, interval -30 MONTH) and :endDate  "
            + "union  "
            + "select p.patient_id,e.encounter_id,o.concept_id,o.obs_datetime data_mds  "
            + "from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "inner join obs obsModo on e.encounter_id=obsModo.encounter_id  "
            + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 and  "
            + "o.concept_id=23731 and e.location_id=:location and  "
            + "o.obs_datetime between date_add(:endDate, interval -30 MONTH) and :endDate and  "
            + "obsModo.concept_id=165174 and obsModo.value_coded=165179 and obsModo.voided=0  "
            + ") mds on mds.patient_id=carga_viral.patient_id  "
            + "where mds.data_mds BETWEEN date_add(carga_viral.data_carga, interval -12 MONTH) and carga_viral.data_carga  "
            + "group by carga_viral.patient_id  "
            + ") mdsfinal  "
            + "inner join obs obsTipoMDS on mdsfinal.patient_id=obsTipoMDS.person_id and  "
            + "obsTipoMDS.obs_datetime=mdsfinal.data_mds and obsTipoMDS.concept_id in (23724,23730,23888,23729,165177,23739,23731) and  "
            + "obsTipoMDS.voided=0 and obsTipoMDS.value_coded in (1256,1257,23730,23888) and obsTipoMDS.location_id=:location  ";

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
