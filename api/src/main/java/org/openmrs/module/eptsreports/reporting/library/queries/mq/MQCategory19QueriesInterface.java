package org.openmrs.module.eptsreports.reporting.library.queries.mq;

public interface MQCategory19QueriesInterface {

  class QUERY {

    public static final String findAllPatientsWhoarePresumptiveTB =
        "select final.patient_id from "
            + "( "
            + "select p.patient_id,min(e.encounter_datetime) data_presuntivo_tb "
            + "from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "where  e.voided=0  "
            + "and o.voided=0 "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23758  "
            + "and o.value_coded=1065  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + "union "
            + "select  p.patient_id,min(e.encounter_datetime) data_presuntivo_tb "
            + "from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "where  e.voided=0  "
            + "and o.voided=0 "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=1766  "
            + "and o.value_coded in(1763,1764,1762,1760,23760,1765)      "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")final "
            + "group by final.patient_id ";

    public static final String findAllPatientsWhoHaveGeneXpertRequest =
        "select final.patient_id from ( "
            + "select  p.patient_id,min(e.encounter_datetime) data_presuntivo_tb  "
            + "from  patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0  "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23722  "
            + "and o.value_coded=23723  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")final ";
    public static final String findAllPatientsWhoHaveTBDiagnosticActive =
        "select final.patient_id from ( "
            + "select  p.patient_id,min(e.encounter_datetime) data_presuntivo_tb  "
            + "from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id   "
            + "where  e.voided=0  "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23761  "
            + "and o.value_coded=1065  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ") final ";

    public static final String
        findAllPatientsWhoHaveTBDiagnosticActiveAndTheSomeDateHaveTBTratment =
            "select tbDiagnostic.patient_id from  "
                + "( "
                + "select  p.patient_id,min(e.encounter_datetime) data_tb_diagnostic  from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id  "
                + "inner join obs o on o.encounter_id=e.encounter_id  "
                + "where  e.voided=0  "
                + "and o.voided=0  "
                + "and p.voided=0  "
                + "and e.encounter_type=6  "
                + "and o.concept_id=23761  "
                + "and o.value_coded=1065  "
                + "and e.location_id=:location   "
                + "and e.encounter_datetime>=:startInclusionDate  "
                + "and e.encounter_datetime<=:endRevisionDate "
                + "group by p.patient_id "
                + ")tbDiagnostic "
                + "left join "
                + "( "
                + "select  p.patient_id,min(o.obs_datetime) data_tb_treatment "
                + "from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on o.encounter_id=e.encounter_id "
                + "where  e.voided=0   "
                + "and o.voided=0  "
                + "and p.voided=0  "
                + "and e.encounter_type=6  "
                + "and o.concept_id=1268  "
                + "and o.value_coded=1256  "
                + "and e.location_id=:location   "
                + "and e.encounter_datetime>=:startInclusionDate  "
                + "and e.encounter_datetime<=:endRevisionDate "
                + "group by p.patient_id "
                + ")tbTreatment on tbDiagnostic.patient_id=tbTreatment.patient_id "
                + "where tbDiagnostic.data_tb_diagnostic=tbTreatment.data_tb_treatment ";

    public static final String findAllPatientsWhoHavePresumptiveTBAndGeneXpertRequest =
        "select presuntivo.patient_id from "
            + "( "
            + "select p.patient_id,min(e.encounter_datetime) data_presuntivo_tb  "
            + "from   patient p  "
            + "inner  join encounter e on p.patient_id=e.patient_id "
            + "inner  join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0  "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23758  "
            + "and o.value_coded=1065   "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + "union "
            + "select  p.patient_id,min(e.encounter_datetime) data_presuntivo_tb "
            + "from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "where  e.voided=0 "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=1766 "
            + "and o.value_coded in(1763,1764,1762,1760,23760,1765) "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")presuntivo "
            + "left join "
            + "( "
            + "select  p.patient_id,min(e.encounter_datetime) data_genexpert "
            + "from    patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id  "
            + "where  e.voided=0 "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23722  "
            + "and o.value_coded=23723  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")geneXpertRequest on presuntivo.patient_id=geneXpertRequest.patient_id "
            + "where presuntivo.data_presuntivo_tb=geneXpertRequest.data_genexpert "
            + "group by presuntivo.patient_id ";

    public static final String findAllPatientsWithGeneXpertResultOnTheSameDateGeneXpertRequest =
        "select geneXertRequest.patient_id from "
            + "( "
            + "select  p.patient_id,min(e.encounter_datetime) data_genexpert_request  "
            + "from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0 "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23722  "
            + "and o.value_coded=23723  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")geneXertRequest "
            + "left join "
            + "( "
            + "select  p.patient_id,e.encounter_datetime data_genexpert_result "
            + "from  patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0  "
            + "and o.voided=0 "
            + "and p.voided=0 "
            + "and e.encounter_type=6 "
            + "and o.concept_id=23723 "
            + "and o.value_coded in(703,664) "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + ")geneXertResult on geneXertRequest.patient_id=geneXertResult.patient_id "
            + "where  geneXertResult.data_genexpert_result=geneXertRequest.data_genexpert_request ";

    public static final String findAllPatientsWithGeneXpertResultAfterGeneXpertRequest =
        "select geneXertRequest.patient_id from "
            + "( "
            + "select  p.patient_id,min(e.encounter_datetime) data_genexpert_request  "
            + "from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0 "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23722  "
            + "and o.value_coded=23723  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")geneXertRequest "
            + "left join "
            + "( "
            + "select  p.patient_id,e.encounter_datetime data_genexpert_result "
            + "from  patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0  "
            + "and o.voided=0 "
            + "and p.voided=0 "
            + "and e.encounter_type=6 "
            + "and o.concept_id=23723 "
            + "and o.value_coded in(703,664) "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + ")geneXertResult on geneXertRequest.patient_id=geneXertResult.patient_id "
            + "where  geneXertResult.data_genexpert_result>geneXertRequest.data_genexpert_request ";

    public static final String findAllPatientsWithGeneXpertResultAfterSevenDaysGeneXpertRequest =
        "select geneXertRequest.patient_id from "
            + "( "
            + "select  p.patient_id,min(e.encounter_datetime) data_genexpert_request  "
            + "from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0 "
            + "and o.voided=0  "
            + "and p.voided=0  "
            + "and e.encounter_type=6  "
            + "and o.concept_id=23722  "
            + "and o.value_coded=23723  "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + "group by p.patient_id "
            + ")geneXertRequest "
            + "left join "
            + "( "
            + "select  p.patient_id,e.encounter_datetime data_genexpert_result "
            + "from  patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id  "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where  e.voided=0  "
            + "and o.voided=0 "
            + "and p.voided=0 "
            + "and e.encounter_type=6 "
            + "and o.concept_id=23723 "
            + "and o.value_coded in(703,664) "
            + "and e.location_id=:location   "
            + "and e.encounter_datetime>=:startInclusionDate  "
            + "and e.encounter_datetime<=:endRevisionDate "
            + ")geneXertResult on geneXertRequest.patient_id=geneXertResult.patient_id "
            + "where  geneXertResult.data_genexpert_result>=geneXertRequest.data_genexpert_request and geneXertResult.data_genexpert_result <= date_add(geneXertRequest.data_genexpert_request, interval 7 day) ";
  }
}
