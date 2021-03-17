package org.openmrs.module.eptsreports.reporting.library.queries;

public interface List1Queries {

  class QUERY {
    public static final String finPatientsWhoInitieted3hp =
        "select inicio_tpi.patient_id from  ( "
            + "select inicio.patient_id,inicio.data_inicio_tpi from  (	 "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=1719 and o.value_coded=23954 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join ( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 4 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=1719 and o.value_coded=23954 and e.encounter_type in (6,9) and  e.location_id=:location "
            + ") inicioAnterior on inicio.patient_id=inicioAnterior.patient_id and "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 4 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join  ( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 4 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 4 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ")inicio "
            + "left join  "
            + "( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(o.value_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio "
            + "left join  "
            + "( "
            + "select p.patient_id,o.value_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union  "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join  "
            + "( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null	 "
            + ") inicio_tpi "
            + "group by inicio_tpi.patient_id ";

    public static final String finPatientsWhoInitietedINH =
        "select tpt.*, "
            + "timestampdiff(day,data_completa_6meses,data_final_inh) diferencaFinalEsperada from ( "
            + "Select  "
            + "inicio_tpi.patient_id, "
            + "inicio_tpi.data_inicio_tpi, "
            + "pe.gender, "
            + "if(pe.birthdate is not null,timestampdiff(year,pe.birthdate,:endDate),'N/A') idade_actual, "
            + "concat(ifnull(pn.given_name,''),' ',ifnull(pn.middle_name,''),' ',ifnull(pn.family_name,'')) nome, "
            + "pid.identifier as nid, "
            + "seguimento.ultimo_seguimento, "
            + "if(obs.concept_id is not null,'Sim','Não') recebeu_profilaxia, "
            + "date_add(inicioinh.data_inicio_inh, interval 173 day) data_completa_6meses, "
            + "inicio_tarv.data_inicio data_inicio_tarv, "
            + "ini3hpclinica.data_inicio_3hpclinica, "
            + "ini3hpfilt.data_inicio_3hpfilt, "
            + "final3hpfilt.data_final_3hpfilt, "
            + "final3hpfilt.tipoDispensa3hp, "
            + "iniinhfilt.data_inicio_inhfilt, "
            + "iniinhresumo.data_inicio_inhresumo, "
            + "iniinhseguimento.data_inicio_inhSeguimento, "
            + "finalinhfilt.data_final_inhfilt, "
            + "finalinhfilt.tipoDispensainh, "
            + "finalinhresumo.data_final_inhresumo, "
            + "finalinhseguimento.data_final_inhSeguimento, "
            + "inicioinh.data_inicio_inh, "
            + "finalinh.data_final_inh, "
            + "gravidaLactante.decisao as estadoMulher from  ( "
            + "select inicio_tpi.patient_id, inicio_tpi.data_inicio_tpi from  ( "
            + "select inicio.patient_id,inicio.data_inicio_tpi from  (	 "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=1719 and o.value_coded=23954 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join ( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 4 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=1719 and o.value_coded=23954 and e.encounter_type in (6,9) and  e.location_id=:location "
            + ") inicioAnterior on inicio.patient_id=inicioAnterior.patient_id and "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 4 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join  ( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 4 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 4 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ")inicio "
            + "left join  "
            + "( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(o.value_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio "
            + "left join  "
            + "( "
            + "select p.patient_id,o.value_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null "
            + "union  "
            + "select inicio.patient_id,inicio.data_inicio_tpi from ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio  "
            + "left join  "
            + "( "
            + "select p.patient_id,e.encounter_datetime data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between (:startDate - INTERVAL 7 MONTH) and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + ") inicioAnterior on inicioAnterior.patient_id=inicio.patient_id and  "
            + "inicioAnterior.data_inicio_tpi between (inicio.data_inicio_tpi - INTERVAL 7 MONTH) and (inicio.data_inicio_tpi - INTERVAL 1 day) "
            + "where inicioAnterior.patient_id is null	 "
            + ") inicio_tpi "
            + "group by inicio_tpi.patient_id "
            + ") inicio_tpi "
            + "inner join person pe on pe.person_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "Select patient_id,min(data_inicio) data_inicio from (	"
            + "Select p.patient_id,min(e.encounter_datetime) data_inicio from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id	 "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and o.voided=0 and p.voided=0 and  "
            + "e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and  "
            + "e.encounter_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and  "
            + "o.concept_id=1190 and o.value_datetime is not null and  "
            + "o.value_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "select pg.patient_id,min(date_enrolled) data_inicio from patient p "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endDate and location_id=:location "
            + "group by pg.patient_id "
            + "union "
            + "SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM 	patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "union "
            + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  "
            + "o.concept_id=23866 and o.value_datetime is not null and  "
            + "o.value_datetime<=:endDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") inicio_real "
            + "group by patient_id "
            + ")inicio_tarv on inicio_tpi.patient_id=inicio_tarv.patient_id "
            + "left join  ( "
            + "select  p.patient_id,max(encounter_datetime) ultimo_seguimento from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and "
            + "e.encounter_type in (6,9) and e.location_id=:location "
            + "group by p.patient_id "
            + ") seguimento on inicio_tpi.patient_id=seguimento.patient_id  "
            + "left join obs on obs.person_id=seguimento.patient_id and obs.obs_datetime=seguimento.ultimo_seguimento and obs.voided=0 and  "
            + "obs.concept_id in (6122,1719) and obs.value_coded in (23954,23955,1257,1256) and obs.location_id=:location "
            + "left join ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_3hpclinica from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=1719 and o.value_coded=23954 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") ini3hpclinica on ini3hpclinica.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_3hpfilt from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") ini3hpfilt on ini3hpfilt.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select final3hpfilt.patient_id,final3hpfilt.data_final_3hpfilt, "
            + "if(obsDispensa.concept_id is null,'',if(obsDispensa.value_coded=1098,'Mensal','Trimestral')) tipoDispensa3hp from  ( "
            + "select p.patient_id,max(e.encounter_datetime) data_final_3hpfilt from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (23954,23984) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") final3hpfilt  "
            + "left join obs obsDispensa on final3hpfilt.patient_id=obsDispensa.person_id and  "
            + "obsDispensa.voided=0 and obsDispensa.concept_id=23986 and obsDispensa.obs_datetime=final3hpfilt.data_final_3hpfilt and  "
            + "obsDispensa.location_id=:location "
            + ") final3hpfilt on final3hpfilt.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_inhfilt from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") iniinhfilt on iniinhfilt.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select p.patient_id,min(o.value_datetime) data_inicio_inhresumo from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type=53 and e.location_id=:location "
            + "group by p.patient_id "
            + ") iniinhresumo on iniinhresumo.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select patient_id,min(data_inicio_tpi) data_inicio_inhSeguimento from  ( "
            + "select p.patient_id,min(o.value_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9) and e.location_id=:location "
            + "group by p.patient_id "
            + "union  "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") iniSeguimento "
            + "group by patient_id "
            + ")iniinhseguimento on iniinhseguimento.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select finalinhfilt.patient_id,finalinhfilt.data_final_inhfilt, "
            + "if(obsDispensa.concept_id is null,'',if(obsDispensa.value_coded=1098,'Mensal','Trimestral')) tipoDispensainh from  ( "
            + "select p.patient_id,max(e.encounter_datetime) data_final_inhfilt from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + ") finalinhfilt  "
            + "left join obs obsDispensa on finalinhfilt.patient_id=obsDispensa.person_id and  "
            + "obsDispensa.voided=0 and obsDispensa.concept_id=23986 and obsDispensa.obs_datetime=finalinhfilt.data_final_inhfilt and  "
            + "obsDispensa.location_id=:location "
            + ") finalinhfilt on finalinhfilt.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select p.patient_id,max(o.value_datetime) data_final_inhresumo from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=6129 and e.encounter_type=53 and e.location_id=:location "
            + "group by p.patient_id "
            + ") finalinhresumo on finalinhresumo.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select patient_id,max(data_fim_tpi) data_final_inhSeguimento from  ( "
            + "select p.patient_id,max(o.value_datetime) data_fim_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=6129 and e.encounter_type in (6,9) and e.location_id=:location "
            + "group by p.patient_id "
            + "union  "
            + "select p.patient_id,max(e.encounter_datetime) data_fim_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1267 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") finalSeguimento "
            + "group by patient_id "
            + ")finalinhseguimento on finalinhseguimento.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select patient_id,max(data_fim_tpi) data_final_inh from  ( "
            + "select p.patient_id,max(o.value_datetime) data_fim_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=6129 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + "group by p.patient_id "
            + "union  "
            + "select p.patient_id,max(e.encounter_datetime) data_fim_tpi from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1267 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") finalSeguimento "
            + "group by patient_id "
            + ")finalinh on finalinh.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select patient_id,min(data_inicio_inh) data_inicio_inh from  ( "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_inh from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=23985 and o.value_coded in (656,23982) and e.encounter_type=60 and  e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "select p.patient_id,min(o.value_datetime) data_inicio_inh from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location "
            + "group by p.patient_id "
            + "union  "
            + "select p.patient_id,min(e.encounter_datetime) data_inicio_inh from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and "
            + "o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location "
            + "group by p.patient_id "
            + ") inicioinh "
            + "group by patient_id "
            + ")inicioinh on inicioinh.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select patient_id,decisao from  ( "
            + "select inicio_real.patient_id, "
            + "gravida_real.data_gravida, "
            + "lactante_real.data_parto, "
            + "if(max(gravida_real.data_gravida) is null and max(lactante_real.data_parto) is null,null, "
            + "if(max(gravida_real.data_gravida) is null,'Lactante', "
            + "if(max(lactante_real.data_parto) is null,'Gravida', "
            + "if(max(lactante_real.data_parto)>max(gravida_real.data_gravida),'Lactante','Gravida')))) decisao from (	 "
            + "select p.patient_id  from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id  "
            + "where e.voided=0 and p.voided=0 and e.encounter_type in (5,7) and e.encounter_datetime<=curdate() and e.location_id = :location "
            + "union "
            + "select pg.patient_id from patient p "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "where pg.voided=0 and p.voided=0 and program_id in (1,2) and date_enrolled<=curdate() and location_id=:location "
            + "union "
            + "Select p.patient_id from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=53 and  "
            + "o.concept_id=23891 and o.value_datetime is not null and  "
            + "o.value_datetime<=curdate() and e.location_id=:location "
            + ")inicio_real "
            + "left join  ( "
            + "Select p.patient_id,e.encounter_datetime data_gravida from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1982 and value_coded=1065 and  "
            + "e.encounter_type in (5,6) and e.encounter_datetime  between :startDate and curdate() and e.location_id=:location "
            + "union "
            + "Select p.patient_id,e.encounter_datetime data_gravida from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1279 and  "
            + "e.encounter_type in (5,6) and e.encounter_datetime between :startDate and curdate() and e.location_id=:location "
            + "union "
            + "Select p.patient_id,e.encounter_datetime data_gravida from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=1600 and  "
            + "e.encounter_type in (5,6) and e.encounter_datetime between :startDate and curdate() and e.location_id=:location	 "
            + "union "
            + "Select p.patient_id,e.encounter_datetime data_gravida from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6331 and  "
            + "e.encounter_type in (5,6) and e.encounter_datetime between :startDate and curdate() and e.location_id=:location		 "
            + "union "
            + "select pp.patient_id,pp.date_enrolled data_gravida from patient_program pp  "
            + "where pp.program_id=8 and pp.voided=0 and  "
            + "pp.date_enrolled between :startDate and curdate() and pp.location_id=:location "
            + "union "
            + "Select p.patient_id,obsART.value_datetime data_gravida from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsART on e.encounter_id=obsART.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=1982 and o.value_coded=1065 and  "
            + "e.encounter_type=53 and obsART.value_datetime between :startDate and curdate() and e.location_id=:location and  "
            + "obsART.concept_id=1190 and obsART.voided=0 "
            + "union "
            + "Select p.patient_id,o.value_datetime data_gravida from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=1465 and  "
            + "e.encounter_type=6 and o.value_datetime between :startDate and curdate() and e.location_id=:location "
            + ") gravida_real on gravida_real.patient_id=inicio_real.patient_id   "
            + "left join  "
            + "( "
            + "Select p.patient_id,o.value_datetime data_parto from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where  p.voided=0 and e.voided=0 and o.voided=0 and concept_id=5599 and  "
            + "e.encounter_type in (5,6) and o.value_datetime between :startDate and curdate() and e.location_id=:location	 "
            + "union "
            + "Select p.patient_id, e.encounter_datetime data_parto from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6332 and value_coded=1065 and "
            + "e.encounter_type=6 and e.encounter_datetime between :startDate and curdate() and e.location_id=:location "
            + "union "
            + "Select p.patient_id, obsART.value_datetime data_parto from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsART on e.encounter_id=obsART.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and o.concept_id=6332 and o.value_coded=1065 and  "
            + "e.encounter_type=53 and e.location_id=:location and  "
            + "obsART.value_datetime between :startDate and curdate() and  "
            + "obsART.concept_id=1190 and obsART.voided=0 "
            + "union "
            + "Select p.patient_id, e.encounter_datetime data_parto from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=6334 and value_coded=6332 and  "
            + "e.encounter_type in (5,6) and e.encounter_datetime between :startDate and curdate() and e.location_id=:location "
            + "union "
            + "select pg.patient_id,ps.start_date data_parto from patient p  "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id "
            + "where pg.voided=0 and ps.voided=0 and p.voided=0 and  "
            + "pg.program_id=8 and ps.state=27 and ps.end_date is null and  "
            + "ps.start_date between :startDate and curdate() and location_id=:location "
            + ") lactante_real on lactante_real.patient_id=inicio_real.patient_id "
            + "where lactante_real.data_parto is not null or gravida_real.data_gravida is not null "
            + "group by inicio_real.patient_id "
            + ") gravidaLactante		 "
            + "inner join person pe on pe.person_id=gravidaLactante.patient_id		 "
            + "where pe.voided=0 and pe.gender='F' "
            + ")gravidaLactante on gravidaLactante.patient_id=inicio_tpi.patient_id "
            + "left join ( "
            + "select pid1.* from patient_identifier pid1 "
            + "inner join  ( "
            + "select patient_id,min(patient_identifier_id) id  from patient_identifier "
            + "where voided=0 "
            + "group by patient_id "
            + ") pid2 "
            + "where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id "
            + ") pid on pid.patient_id=inicio_tpi.patient_id "
            + "left join  ( "
            + "select pn1.* from person_name pn1 "
            + "inner join  ( "
            + "select person_id,min(person_name_id) id  from person_name "
            + "where voided=0 "
            + "group by person_id "
            + ") pn2 "
            + "where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id "
            + ") pn on pn.person_id=inicio_tpi.patient_id "
            + ") tpt "
            + "group by patient_id ";
  }
}
