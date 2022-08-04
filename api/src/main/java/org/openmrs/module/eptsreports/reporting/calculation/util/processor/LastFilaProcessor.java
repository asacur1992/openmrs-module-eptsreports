package org.openmrs.module.eptsreports.reporting.calculation.util.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.stereotype.Component;

@Component
public class LastFilaProcessor {

  public Map<Integer, Date> getLastLevantamentoOnFila(EvaluationContext context) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            "Select p.patient_id,max(encounter_datetime) data_fila from patient p "
                + "			inner join encounter e on e.patient_id=p.patient_id "
                + "	where 	p.voided=0 and e.voided=0 and e.encounter_type=18 and "
                + "			e.location_id= :location and e.encounter_datetime<= :endDate group by p.patient_id",
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }

  public Map<Integer, Date> getLastTipoDeLevantamentoOnFichaClinicaMasterCard(
      EvaluationContext context, Integer conceptId, Integer valueCodedId) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            String.format(
                "select max_ficha.patient_id, max_ficha.data_levantamento from( "
                    + " (Select p.patient_id,max(e.encounter_datetime) data_levantamento "
                    + "	from 	patient p inner join encounter e on p.patient_id=e.patient_id "
                    + "			inner join obs o on o.encounter_id=e.encounter_id "
                    + "	where 	e.voided=0 and o.voided=0 and p.voided=0 and "
                    + "			e.encounter_type =6 and o.concept_id=%s and o.value_coded=%s and "
                    + "			e.encounter_datetime<=:endDate and e.location_id=:location "
                    + "	group by p.patient_id ) max_ficha "
                    + "inner join patient patient_without_fila on patient_without_fila.patient_id = max_ficha.patient_id) ",
                conceptId, valueCodedId),
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }

  public Map<Integer, Date> getLastMarkedInModelosDiferenciadosDeCuidadosOnFichaClinicaMasterCard(
      EvaluationContext context, Integer conceptId) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            String.format(
                "select max_ficha.patient_id, max_ficha.data_mdc from "
                    + "( select p.patient_id,max(e.encounter_datetime) data_mdc from patient p  "
                    + "join encounter e on p.patient_id=e.patient_id  "
                    + "join obs grupo on grupo.encounter_id=e.encounter_id  "
                    + "join obs o on o.encounter_id=e.encounter_id "
                    + "join obs obsEstado on obsEstado.encounter_id=e.encounter_id  "
                    + "where  e.encounter_type in(6) and e.location_id=:location and o.concept_id=165174 and o.value_coded in(%s) and o.voided=0  "
                    + "and grupo.concept_id=165323  and grupo.voided=0 and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)  "
                    + "and obsEstado.voided=0  and grupo.voided=0  "
                    + "and grupo.obs_id=o.obs_group_id and grupo.obs_id=obsEstado.obs_group_id  and e.encounter_datetime<=:endDate "
                    + "group by p.patient_id "
                    + ") max_ficha "
                    + " inner join patient patient_without_fila on patient_without_fila.patient_id = max_ficha.patient_id ",
                conceptId),
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }

  public List<Object[]> getMaxFilaWithProximoLevantamento(EvaluationContext context) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            "select pickup.patient_id, pickup.last_levantamento, max(obs_proximo_levantamento.value_datetime) proximo_levantamento "
                + "from ( "
                + "select maxpkp.patient_id,maxpkp.last_levantamento,e.encounter_id from ( "
                + "SELECT p.patient_id, MAX(e.encounter_datetime) last_levantamento FROM patient p "
                + "INNER JOIN encounter e ON e.patient_id = p.patient_id "
                + "WHERE p.voided = 0  AND e.voided = 0  AND e.encounter_type = 18  and e.location_id =:location  and date(e.encounter_datetime) <=:endDate "
                + "GROUP BY p.patient_id "
                + ") maxpkp "
                + "inner join encounter e on e.patient_id=maxpkp.patient_id "
                + "where date(e.encounter_datetime)=date(maxpkp.last_levantamento) and e.encounter_type=18 and e.location_id=:location and e.voided=0 "
                + "order by maxpkp.patient_id "
                + ") pickup "
                + "inner join obs obs_proximo_levantamento on pickup.encounter_id=obs_proximo_levantamento.encounter_id "
                + "where obs_proximo_levantamento.concept_id = 5096  and obs_proximo_levantamento.voided = 0 "
                + "group by pickup.patient_id ",
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToList(qb, context);
  }
}
