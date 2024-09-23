package org.openmrs.module.eptsreports.reporting.calculation.dsd;

import java.util.Date;
import java.util.Map;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.reporting.library.queries.ResumoMensalQueries;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.stereotype.Component;

@Component
public class OnArtInitiatedArvDrugsMISAUDefinitionProcessor {

  public Map<Integer, Date> getResutls(EvaluationContext context) {

    return this.getPatientsOnARTMISAUDefinition(context);
  }

  private Map<Integer, Date> getPatientsOnARTMISAUDefinition(EvaluationContext context) {

    SqlQueryBuilder qb =
        new SqlQueryBuilder(
            ResumoMensalQueries.findPatientsWhoAreCurrentlyEnrolledOnArtMOHB13ForIITCalculation(),
            context.getParameterValues());

    return Context.getRegisteredComponents(EvaluationService.class)
        .get(0)
        .evaluateToMap(qb, Integer.class, Date.class, context);
  }
}
