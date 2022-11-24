package org.openmrs.module.eptsreports.reporting.calculation.dsd;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.eptsreports.reporting.calculation.BaseFghCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.util.processor.DSDLastRecepcaoLevantamentoProcessor;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class DSDLastRecepcaoLevantamentoToBeIncludeCalculation extends BaseFghCalculation {

  @Override
  public CalculationResultMap evaluate(
      Collection<Integer> cohort, Map<String, Object> parameterValues, EvaluationContext context) {
    Map<Integer, Date> processorResult =
        Context.getRegisteredComponents(DSDLastRecepcaoLevantamentoProcessor.class)
            .get(0)
            .getResutlsToExclude(context);

    CalculationResultMap resultMap = new CalculationResultMap();
    for (Integer patientId : cohort) {
      if (processorResult.get(patientId) != null) {
        resultMap.put(patientId, new SimpleResult(processorResult.get(patientId), this));
      }
    }
    return resultMap;
  }
}
