package org.openmrs.module.eptsreports.reporting.calculation.util.processor;

import java.util.Date;
import java.util.Map;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.stereotype.Component;

@Component
public class DSDLastRecepcaoLevantamentoProcessor {

  public Map<Integer, Date> getResutls(EvaluationContext context) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            "Select p.patient_id,max(value_datetime) data_recepcao_levantou "
                + "	from 	patient p inner join encounter e on p.patient_id=e.patient_id "
                + "			inner join obs o on e.encounter_id=o.encounter_id "
                + "	where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and "
                + "			o.concept_id=23866 and o.value_datetime is not null and "
                + "			o.value_datetime between (:endDate - INTERVAL 3 MONTH) and :endDate "
                + "and e.location_id= :location "
                + "group by p.patient_id ",
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }

  public Map<Integer, Date> getResutlsToExclude(EvaluationContext context) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            "Select p.patient_id,value_datetime data_recepcao_levantou "
                + "	from 	patient p inner join encounter e on p.patient_id=e.patient_id "
                + "			inner join obs o on e.encounter_id=o.encounter_id "
                + "	where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and "
                + "			o.concept_id=23866 and o.value_datetime is not null and "
                + "			o.value_datetime (:endDate - INTERVAL 3 MONTH)  "
                + "and e.location_id= :location "
                + "group by p.patient_id ",
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }
}
