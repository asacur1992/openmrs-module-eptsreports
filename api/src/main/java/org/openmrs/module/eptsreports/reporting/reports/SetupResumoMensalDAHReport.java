package org.openmrs.module.eptsreports.reporting.reports;

import static org.openmrs.module.reporting.evaluation.parameter.Mapped.mapStraightThrough;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openmrs.module.eptsreports.reporting.library.cohorts.GenericCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.datasets.DatimCodeDataSet;
import org.openmrs.module.eptsreports.reporting.library.datasets.LocationDataSetDefinition;
import org.openmrs.module.eptsreports.reporting.library.datasets.SismaCodeDataSet;
import org.openmrs.module.eptsreports.reporting.library.datasets.resumo.ResumoMensalDAHDataSetDefinition;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.PeriodIndicatorReportDefinition;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupResumoMensalDAHReport extends EptsDataExportManager {

  @Autowired private ResumoMensalDAHDataSetDefinition resumoMensalDAHDataSetDefinition;

  @Autowired protected GenericCohortQueries genericCohortQueries;

  @Autowired private DatimCodeDataSet datimCodeDataSet;

  @Autowired private SismaCodeDataSet sismaCodeDataSet;

  @Autowired
  public SetupResumoMensalDAHReport(
      ResumoMensalDAHDataSetDefinition resumoMensalDAHDataSetDefinition) {
    this.resumoMensalDAHDataSetDefinition = resumoMensalDAHDataSetDefinition;
  }

  @Override
  public String getVersion() {
    return "1.0-SNAPSHOT";
  }

  @Override
  public String getUuid() {
    return "cf55c79a-929b-11ee-b76f-136f10e32f96";
  }

  @Override
  public String getExcelDesignUuid() {
    return "d681b920-929b-11ee-9fef-eb6e8a206d88";
  }

  @Override
  public String getName() {
    return "Resumo Mensal de DAH";
  }

  @Override
  public String getDescription() {
    return "Este relatório apresenta os dados do resumo mensal da Unidade Sanitária para a doença avançada por HIV, provenientes da ferramenta Ficha de DAH e Ficha Mestra no sistema.";
  }

  @Override
  public PeriodIndicatorReportDefinition constructReportDefinition() {

    PeriodIndicatorReportDefinition rd =
        SetupResumoMensalReport.getDefaultPeriodIndicatorReportDefinition();

    rd.setUuid(getUuid());
    rd.setName(getName());
    rd.setDescription(getDescription());
    rd.addParameters(resumoMensalDAHDataSetDefinition.getParameters());

    rd.addDataSetDefinition("HF", mapStraightThrough(new LocationDataSetDefinition()));
    rd.addDataSetDefinition(
        "R",
        mapStraightThrough(resumoMensalDAHDataSetDefinition.constructResumoMensalDAHDataSet()));

    rd.addDataSetDefinition(
        "D",
        Mapped.mapStraightThrough(this.datimCodeDataSet.constructDataset(this.getParameters())));

    rd.addDataSetDefinition(
        "SC",
        Mapped.mapStraightThrough(this.sismaCodeDataSet.constructDataset(this.getParameters())));

    return rd;
  }

  @Override
  public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
    ReportDesign reportDesign = null;
    try {
      reportDesign =
          createXlsReportDesign(
              reportDefinition,
              "Resumo_Mensal_DAH.xls",
              "Resumo Mensal de DAH Report",
              getExcelDesignUuid(),
              null);
      Properties props = new Properties();
      props.put("sortWeight", "5000");
      reportDesign.setProperties(props);
    } catch (IOException e) {
      throw new ReportingException(e.toString());
    }

    return Arrays.asList(reportDesign);
  }

  public static PeriodIndicatorReportDefinition getDefaultPeriodIndicatorReportDefinition() {
    PeriodIndicatorReportDefinition rd = new PeriodIndicatorReportDefinition();
    rd.removeParameter(ReportingConstants.START_DATE_PARAMETER);
    rd.removeParameter(ReportingConstants.END_DATE_PARAMETER);
    rd.removeParameter(ReportingConstants.LOCATION_PARAMETER);

    return rd;
  }
}
