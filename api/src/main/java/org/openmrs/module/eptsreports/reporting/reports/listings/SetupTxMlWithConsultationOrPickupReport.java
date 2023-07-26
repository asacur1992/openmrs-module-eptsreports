/** */
package org.openmrs.module.eptsreports.reporting.reports.listings;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openmrs.module.eptsreports.reporting.library.datasets.listings.TxMlWithConsultationOrPickupDataset;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author Stélio Moiane */
@Service
public class SetupTxMlWithConsultationOrPickupReport extends EptsDataExportManager {

  @Autowired private TxMlWithConsultationOrPickupDataset dataset;

  @Override
  public String getUuid() {
    return "a748f6cd-6297-49e8-84da-1ef2de8098b4";
  }

  @Override
  public String getName() {
    return "TX_ML COM CONSULTA OU LEVANTAMENTO ARV";
  }

  @Override
  public String getDescription() {
    return "Prior TX_ML with consultation or drug pick up";
  }

  @Override
  public ReportDefinition constructReportDefinition() {
    final ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setUuid(this.getUuid());
    reportDefinition.setName(this.getName());
    reportDefinition.setDescription(this.getDescription());
    reportDefinition.setParameters(this.dataset.getParameters());
    reportDefinition.addDataSetDefinition(
        "LFU", Mapped.mapStraightThrough(this.dataset.loadData(this.dataset.getParameters())));
    return reportDefinition;
  }

  @Override
  public String getVersion() {
    return "1.0";
  }

  @Override
  public String getExcelDesignUuid() {
    return "09bd8baf-dcc7-4008-ae7a-6b71ecd28d46";
  }

  @Override
  public List<ReportDesign> constructReportDesigns(final ReportDefinition reportDefinition) {
    ReportDesign reportDesign = null;

    try {
      reportDesign =
          this.createXlsReportDesign(
              reportDefinition,
              "LOST_OF_FOLLOW_UP_WITH_CONSULT_OR_DRUG_PICK_UP.xls",
              "Pacientes TX_ML com consulta ou levantamento ARV",
              this.getExcelDesignUuid(),
              null);
      final Properties props = new Properties();
      props.put("repeatingSections", "sheet:1,row:8,dataset:LFU");
      props.put("sortWeight", "5000");
      reportDesign.setProperties(props);

    } catch (final IOException e) {
      throw new ReportingException(e.toString());
    }

    return Arrays.asList(reportDesign);
  }
}
