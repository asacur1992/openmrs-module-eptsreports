package org.openmrs.module.eptsreports.reporting.library.queries.mq;

import org.openmrs.module.eptsreports.reporting.utils.TypePTV;

public interface MQCategory9QueriesInterface {

  class QUERY {
    public static final String findPatientsFirstConsultationOnInclusionDate =
        "select firstConsultation.patient_id from (  "
            + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "where p.voided=0 and e.voided=0 and e.encounter_type=6 and e.location_id=:location  "
            + "group by p.patient_id "
            + ")firstConsultation  "
            + "Where firstConsultation.encounter_datetime between DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH) ";

    public static String getPatientsWhoArePregnantOrBreastfeeding(TypePTV typePTV) {
      String query =
          "select f.patient_id from (    "
              + "select  "
              + "f.patient_id, "
              + "f.encounter_datetime, "
              + "f.data_gravida, "
              + "f.data_lactante,   "
              + "if(f.data_lactante is null,1, if(f.data_gravida is null,2, if(f.data_gravida>=f.data_lactante,1,2))) decisao  from  (    "
              + "Select p.patient_id,p.encounter_datetime,gravida.data_gravida,lactante.data_lactante from  "
              + "(  select * from  (    "
              + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from patient p    "
              + "inner join encounter e on p.patient_id=e.patient_id    "
              + "where p.voided=0 and e.voided=0 and e.encounter_type=6 and e.location_id=:location    "
              + "group by p.patient_id   "
              + ")firstConsultation    "
              + "Where firstConsultation.encounter_datetime between DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)   "
              + ")p    "
              + "inner join   (    "
              + "Select p.patient_id,o.obs_datetime as data_gravida  from patient p    "
              + "inner join person pe on pe.person_id = p.patient_id    "
              + "inner join encounter e on p.patient_id=e.patient_id    "
              + "inner join obs o on e.encounter_id=o.encounter_id    "
              + "where p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and  o.concept_id=1982    "
              + "and e.encounter_datetime BETWEEN DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)    "
              + "and pe.voided = 0 and pe.gender = 'F'   "
              + "group by p.patient_id    "
              + ")gravida on gravida.patient_id=p.patient_id and p.encounter_datetime=gravida.data_gravida     "
              + "left join  (    "
              + "Select p.patient_id,o.obs_datetime as data_lactante from patient p    "
              + "inner join person pe on pe.person_id = p.patient_id    "
              + "inner join encounter e on p.patient_id=e.patient_id    "
              + "inner join obs o on e.encounter_id=o.encounter_id    "
              + "where p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and  o.concept_id=6332    "
              + "and e.encounter_datetime BETWEEN DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)    "
              + "and pe.gender = 'F' and pe.voided = 0    "
              + "group by p.patient_id    "
              + ") lactante on lactante.patient_id=gravida.patient_id and lactante.data_lactante=p.encounter_datetime  "
              + ")f   "
              + ")f ";

      switch (typePTV) {
        case PREGNANT:
          query = query + "where f.decisao = 1 ";
          break;

        case BREASTFEEDING:
          query = query + "where f.decisao = 2 ";
          break;
      }

      return query;
    }

    public static final String findPatientsWhoArePregnantDuringPreviousPeriod =
        "select pregnat.patient_id from (  "
            + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from person pe  "
            + "inner join patient p on pe.person_id=p.patient_id  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on e.encounter_id=o.encounter_id  "
            + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and pe.gender='F' and  "
            + "o.concept_id=1982 and o.value_coded=1065  "
            + "group by p.patient_id "
            + ")pregnat  "
            + "where pregnat.patient_id not in ( "
            + "Select p.patient_id from person pe  "
            + "inner join patient p on pe.person_id=p.patient_id  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on e.encounter_id=o.encounter_id  "
            + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and pe.gender='F' and "
            + "o.concept_id=1982 and o.value_coded=1065  "
            + "and e.encounter_datetime>=DATE_SUB(DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH),INTERVAL 1 DAY), INTERVAL 9 MONTH) and e.encounter_datetime<DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH),INTERVAL 1 DAY) "
            + "group by p.patient_id "
            + ") and pregnat.encounter_datetime between DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)";

    public static final String
        findPatientsWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9 =
            "select firstClinica.patient_id  from  (  "
                + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id  "
                + "where p.voided=0 and e.voided=0 and e.encounter_type=6 and e.location_id=:location  "
                + "group by p.patient_id "
                + ") firstClinica  "
                + "inner join obs obsCD4 on obsCD4.person_id=firstClinica.patient_id  "
                + "where obsCD4.obs_datetime=firstClinica.encounter_datetime  "
                + "and obsCD4.concept_id=23722 and obsCD4.value_coded=1695  and obsCD4.voided=0  "
                + "and firstClinica.encounter_datetime between  DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)  "
                + "and obsCD4.location_id=:location  "
                + "group by obsCD4.person_id  ";

    public static final String
        findPregnantWomanWhithCD4OnFirstClinicalConsultationDuringInclusionDateNumeratorCategory9 =
            "select firstClinica.patient_id from  (   "
                + "select * from (   "
                + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from person pe   "
                + "inner join patient p on pe.person_id=p.patient_id   "
                + "inner join encounter e on p.patient_id=e.patient_id   "
                + "inner join obs o on e.encounter_id=o.encounter_id   "
                + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and pe.gender='F' and   "
                + "o.concept_id=1982 and o.value_coded=1065   "
                + "group by p.patient_id  "
                + ")pregnat   "
                + ") firstClinica   "
                + "inner join obs obsCD4 on obsCD4.person_id=firstClinica.patient_id   "
                + "where firstClinica.encounter_datetime between  DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH) "
                + "and obsCD4.concept_id in(23722) and obsCD4.value_coded=1695 and obsCD4.voided=0 and firstClinica.encounter_datetime=obsCD4.obs_datetime "
                + "and obsCD4.location_id=:location ";

    public static final String
        findPatientsWhithCD4On33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9 =
            "select firstClinica.patient_id  from  ( "
                + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id  "
                + "where p.voided=0 and e.voided=0 and e.encounter_type=6 and e.location_id=:location  "
                + "group by p.patient_id "
                + ") firstClinica "
                + "inner join obs obsCD4 on obsCD4.person_id=firstClinica.patient_id "
                + "where firstClinica.encounter_datetime between  DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH) and "
                + "obsCD4.obs_datetime > firstClinica.encounter_datetime and obsCD4.obs_datetime <=  DATE_ADD(firstClinica.encounter_datetime, INTERVAL 33 DAY) "
                + "and obsCD4.concept_id=23722 and obsCD4.value_coded=1695  and obsCD4.voided=0 "
                + "and obsCD4.location_id = :location ";

    public static final String
        findPatientsWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9 =
            "select firstClinica.patient_id  from  (  "
                + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from patient p   "
                + "inner join encounter e on p.patient_id=e.patient_id   "
                + "where p.voided=0 and e.voided=0 and e.encounter_type=6 and e.location_id=:location   "
                + "group by p.patient_id  "
                + ") firstClinica  "
                + "inner join encounter e on firstClinica.patient_id=e.patient_id  "
                + "inner join obs obsCD4 on obsCD4.encounter_id=e.encounter_id  "
                + "where firstClinica.encounter_datetime between  DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)  "
                + "and obsCD4.obs_datetime > firstClinica.encounter_datetime and obsCD4.obs_datetime <=  DATE_ADD(firstClinica.encounter_datetime, INTERVAL 33 DAY)  "
                + "and obsCD4.concept_id in(1695,703) and obsCD4.value_numeric is not null and obsCD4.voided=0  "
                + "and obsCD4.location_id=:location and e.encounter_type=6 ";

    public static final String
        findPregnantWomanWhithCD4ResultOn33DaysAfterFirstClinicalConsultationDuringInclusionDateNumeratorCategory9 =
            "select firstClinica.patient_id from  (    "
                + "select * from (    "
                + "Select p.patient_id, min(e.encounter_datetime) encounter_datetime from person pe    "
                + "inner join patient p on pe.person_id=p.patient_id    "
                + "inner join encounter e on p.patient_id=e.patient_id    "
                + "inner join obs o on e.encounter_id=o.encounter_id    "
                + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0  and e.encounter_type=6 and e.location_id=:location and pe.gender='F' and "
                + "o.concept_id=1982 and o.value_coded=1065    "
                + "group by p.patient_id   "
                + ")pregnat    "
                + ") firstClinica    "
                + "inner join obs obsCD4 on obsCD4.person_id=firstClinica.patient_id    "
                + "where firstClinica.encounter_datetime between  DATE_ADD(DATE_SUB(:endRevisionDate, INTERVAL 12 MONTH), INTERVAL 1 DAY) and DATE_SUB(:endRevisionDate, INTERVAL 9 MONTH)  "
                + "and obsCD4.concept_id in(1695,703) and obsCD4.value_numeric is not null  and obsCD4.voided=0  "
                + "and obsCD4.obs_datetime >firstClinica.encounter_datetime and obsCD4.obs_datetime<date_add(firstClinica.encounter_datetime, interval 33 day)  "
                + "and obsCD4.location_id=:location ";
  }
}
