package org.openmrs.module.eptsreports.reporting.library.queries;

public interface CxCaSCRNQueries {

  class QUERY {
    public static final String findPatientsWithScreeningTestForCervicalCancerDuringReportingPeriod =
        "SELECT finalCCU.patient_id FROM  ( "
            + "SELECT p.patient_id,min(e.encounter_datetime) dataccu FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=2094 and o.value_coded in(2093,664,703) AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "union "
            + "SELECT p.patient_id,min(e.encounter_datetime) dataccu FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location "
            + "GROUP BY p.patient_id "
            + ")finalCCU "
            + "GROUP by finalCCU.patient_id  order by finalCCU.patient_id asc ";

    public static final String
        findPatientsWithScreeningTestForCervicalCancerPreviousReportingPeriod =
            "SELECT finalCCU.patient_id FROM  ( "
                + "SELECT p.patient_id,e.encounter_datetime dataccu FROM patient p  "
                + "INNER JOIN encounter e on p.patient_id=e.patient_id      "
                + "INNER JOIN obs o on o.encounter_id=e.encounter_id  "
                + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
                + "and o.concept_id=2094 and o.value_coded in(2093,664,703) AND e.encounter_datetime<:startDate and e.location_id=:location "
                + "GROUP BY p.patient_id  "
                + "union "
                + "SELECT p.patient_id,min(e.encounter_datetime) dataccu FROM patient p "
                + "INNER JOIN encounter e on p.patient_id=e.patient_id "
                + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
                + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
                + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime<:startDate and e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ")finalCCU "
                + "GROUP by finalCCU.patient_id order by finalCCU.patient_id asc";

    public static final String
        findPatientsWithScreeningTestForCervicalCancerDuringReportingPeriodByReusult(
            int concept, int answer) {
      String sql =
          "select patient_id from  ( "
              + "select * from  ( "
              + "select * from  ( "
              + "SELECT f.patient_id,f.dataccu, o.value_coded, 1 ord FROM  (   "
              + "SELECT p.patient_id,max(o.obs_datetime) dataccu FROM patient p    "
              + "INNER JOIN encounter e on p.patient_id=e.patient_id        "
              + "INNER JOIN obs o on o.encounter_id=e.encounter_id    "
              + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28   "
              + "and o.concept_id=%s and o.value_coded in (703)  "
              + "AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location  "
              + "group by p.patient_id "
              + ")f "
              + "inner join encounter e on e.patient_id=f.patient_id "
              + "inner join obs o on o.encounter_id=e.encounter_id "
              + "where e.voided=0 and o.voided=0 and e.encounter_type=28 and o.concept_id=%s and o.value_coded in(703) "
              + "and o.obs_datetime=f.dataccu "
              + "union "
              + "SELECT f.patient_id,f.dataccu, o.value_coded, 2 ord FROM  (   "
              + "SELECT p.patient_id,max(o.obs_datetime) dataccu FROM "
              + "patient p    "
              + "INNER JOIN encounter e on p.patient_id=e.patient_id        "
              + "INNER JOIN obs o on o.encounter_id=e.encounter_id    "
              + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28   "
              + "and o.concept_id=%s and o.value_coded in (664)  "
              + "AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location  "
              + "group by p.patient_id "
              + ")f "
              + "inner join encounter e on e.patient_id=f.patient_id "
              + "inner join obs o on o.encounter_id=e.encounter_id "
              + "where e.voided=0 and o.voided=0 and e.encounter_type=28 and o.concept_id=%s and o.value_coded in(664) "
              + "and o.obs_datetime=f.dataccu "
              + "union "
              + "SELECT f.patient_id,f.dataccu, o.value_coded, 3 ord "
              + "FROM  (   "
              + "SELECT p.patient_id,max(o.obs_datetime) dataccu FROM patient p    "
              + "INNER JOIN encounter e on p.patient_id=e.patient_id        "
              + "INNER JOIN obs o on o.encounter_id=e.encounter_id    "
              + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28   "
              + "and o.concept_id=%s and o.value_coded in (2093)  "
              + "AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location  "
              + "group by p.patient_id "
              + ")f "
              + "inner join encounter e on e.patient_id=f.patient_id "
              + "inner join obs o on o.encounter_id=e.encounter_id "
              + "where e.voided=0 and o.voided=0 and e.encounter_type=28 and o.concept_id=%s and o.value_coded in(2093) "
              + "and o.obs_datetime=f.dataccu "
              + ")f "
              + "order by patient_id, dataccu desc, ord "
              + ") allccu group by patient_id  "
              + ")finalCCU "
              + "where finalCCU.value_coded=%s ";

      return String.format(sql, concept, concept, concept, concept, concept, concept, answer);
    }

    public static final String findPatientWithScreeningTypeVisitAsRescreenedAfterPreviousNegative =
        "select rastreioperiodo.patient_id from ( "
            + "Select 	p.patient_id from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + "union "
            + "SELECT p.patient_id FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + ") rastreioperiodo "
            + "inner join  ( "
            + "select maxRastreioAnterior.patient_id from  ( "
            + "select patient_id, max(dataRastreio) dataRastreio from ( "
            + "Select negativeResut.patient_id, negativeResut.dataRastreio from ( "
            + "Select p.patient_id,max(o.obs_datetime) dataRastreio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded = 664 and "
            + "e.encounter_type=28 and e.encounter_datetime<:startDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "SELECT p.patient_id,max(o.obs_datetime) dataRastreio FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime < :startDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") negativeResut "
            + " left join "
            + "( "
            + "Select p.patient_id,o.obs_datetime dataRastreio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime<:startDate and e.location_id=:location "
            + ") positiveOrCancer on "
            + "positiveOrCancer.patient_id = negativeResut.patient_id "
            + "where ((negativeResut.dataRastreio > positiveOrCancer.dataRastreio) or positiveOrCancer.patient_id is null) "
            + ") maxRastreioAnterior group by patient_id "
            + ") maxRastreioAnterior "
            + ")rastreioNegatiVoAnterior on rastreioNegatiVoAnterior.patient_id = rastreioperiodo.patient_id ";

    public static final String findPatientWithScreeningTypeVisitAsRescreenedAfterPreviousPositive =
        " select rastreioperiodo.patient_id from ( "
            + "Select 	p.patient_id from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + "union "
            + "SELECT p.patient_id FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + ") rastreioperiodo "
            + "inner join  ( "
            + "select maxRastreioAnterior.patient_id from  ( "
            + "Select p.patient_id,max(o.obs_datetime) dataRastreio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime<:startDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") maxRastreioAnterior "
            + "inner join encounter e on e.patient_id=maxRastreioAnterior.patient_id "
            + "inner join obs obsRastreioPositivo on e.encounter_id=obsRastreioPositivo.encounter_id "
            + "where obsRastreioPositivo.voided=0 and obsRastreioPositivo.obs_datetime=maxRastreioAnterior.dataRastreio and "
            + "obsRastreioPositivo.concept_id=2094 and obsRastreioPositivo.value_coded in (703,2093) and "
            + "e.voided=0 and e.encounter_type=28 and e.location_id=:location "
            + ") rastreioPositivoanterior on rastreioperiodo.patient_id=rastreioPositivoanterior.patient_id ";

    public static final String findpatientwithScreeningTypeVisitAsPostTreatmentFollowUp =
        "select rastreioperiodo.patient_id from  ( "
            + "Select 	p.patient_id,min(e.encounter_datetime) dataRastreioPeriodo from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "SELECT p.patient_id,min(e.encounter_datetime) dataccu FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 AND e.encounter_datetime between  :startDate and :endDate and e.location_id=:location "
            + "GROUP BY p.patient_id "
            + ") rastreioperiodo "
            + "inner join  ( "
            + "select maxRastreioAnterior.patient_id,dataRastreio dataRastreioPositivo from  ( "
            + "Select p.patient_id,max(o.obs_datetime) dataRastreio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and "
            + "e.encounter_type=28 and e.encounter_datetime<:startDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") maxRastreioAnterior "
            + "inner join obs obsRastreioPositivo on maxRastreioAnterior.patient_id=obsRastreioPositivo.person_id "
            + "where obsRastreioPositivo.voided=0 and obsRastreioPositivo.obs_datetime=maxRastreioAnterior.dataRastreio and "
            + "obsRastreioPositivo.concept_id=2094 and obsRastreioPositivo.value_coded=703 "
            + ") rastreioPositivoAtivoanterior on rastreioperiodo.patient_id=rastreioPositivoAtivoanterior.patient_id "
            + "inner join encounter e on e.patient_id=rastreioperiodo.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where e.voided=0 and o.voided=0 and e.encounter_type=28 and ( "
            + "(o.concept_id = 2117 and o.value_coded = 1065 and e.encounter_datetime>=rastreioPositivoAtivoanterior.dataRastreioPositivo and e.encounter_datetime<=rastreioperiodo.dataRastreioPeriodo and e.encounter_datetime<=:endDate ) or "
            + "(o.concept_id = 2149 and o.value_coded in (23974,23972,23970,23973) and o.obs_datetime>=rastreioPositivoAtivoanterior.dataRastreioPositivo and o.obs_datetime<=rastreioperiodo.dataRastreioPeriodo and e.encounter_datetime<=:endDate ) or "
            + "(o.concept_id=23967 and o.value_datetime>=rastreioPositivoAtivoanterior.dataRastreioPositivo and o.value_datetime<=rastreioperiodo.dataRastreioPeriodo and e.encounter_datetime<=:endDate ) or "
            + "(o.concept_id=1185 and o.value_coded in(23974, 165439) and o.obs_datetime>=rastreioPositivoAtivoanterior.dataRastreioPositivo and o.obs_datetime<=rastreioperiodo.dataRastreioPeriodo and e.encounter_datetime<=:endDate )"
            + ") and "
            + "e.location_id=:location; ";

    public static final String findpatientwithCxCaPositive =
        "select rastreioPeriodo.patient_id from ( "
            + "Select p.patient_id,min(o.obs_datetime) dataRastreio from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2094 and value_coded in (703,664,2093) and  "
            + "e.encounter_type=28 and e.encounter_datetime between :startDate and :endDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") rastreioPeriodo "
            + "inner join encounter rastreio on rastreioPeriodo.patient_id=rastreio.patient_id "
            + "inner join obs obsRastreio on obsRastreio.encounter_id=rastreio.encounter_id "
            + "where obsRastreio.voided=0 and obsRastreio.obs_datetime=rastreioPeriodo.dataRastreio and  "
            + "obsRastreio.concept_id=2094 and obsRastreio.value_coded=703	and  "
            + "rastreio.voided=0 and rastreio.location_id=:location and rastreio.encounter_type=28 ";

    public static final String findPatientsWithLastViaResultEqualHPVNegative =
        "select hpvNegative.patient_id from ( "
            + "SELECT p.patient_id,max(o.obs_datetime) dataccu FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=165436 and o.value_coded = 664 "
            + "AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") hpvNegative "
            + "left join ( "
            + "SELECT p.patient_id,max(o.obs_datetime) dataccu FROM patient p "
            + "INNER JOIN encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o on o.encounter_id=e.encounter_id "
            + "WHERE e.voided=0 and o.voided=0 and p.voided=0 AND e.encounter_type=28 "
            + "and o.concept_id=2094 and o.value_coded in (664, 703, 2093) "
            + "AND e.encounter_datetime>=:startDate and e.encounter_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") positiveOrCancer "
            + "on positiveOrCancer.patient_id = hpvNegative.patient_id "
            + "where (hpvNegative.dataccu > positiveOrCancer.dataccu or positiveOrCancer.patient_id is null) ";
  }
}
