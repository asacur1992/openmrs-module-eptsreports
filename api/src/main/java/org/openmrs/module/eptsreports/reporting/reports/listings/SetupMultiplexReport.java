/** */
package org.openmrs.module.eptsreports.reporting.reports.listings;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openmrs.module.eptsreports.reporting.library.datasets.listings.MultiplexDataset;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author Abdul Sacur */
@Service
public class SetupMultiplexReport extends EptsDataExportManager {

  @Autowired private MultiplexDataset dataset;

  @Override
  public String getUuid() {
    return "dab9d120-f8e0-41a9-a453-f2230c6b6798";
  }

  @Override
  public String getName() {
    return "MULTIPLEX LISTA AVALIACAO";
  }

  @Override
  public String getDescription() {
    return "MULTIPLEX LISTA AVALIACAO";
  }

  @Override
  public ReportDefinition constructReportDefinition() {
    final ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setUuid(this.getUuid());
    reportDefinition.setName(this.getName());
    reportDefinition.setDescription(this.getDescription());
    reportDefinition.setParameters(this.dataset.getParameters());
    reportDefinition.addDataSetDefinition(
        "MULT", Mapped.mapStraightThrough(this.dataset.loadData(this.dataset.getParameters())));
    return reportDefinition;
  }

  @Override
  public String getVersion() {
    return "1.0";
  }

  @Override
  public String getExcelDesignUuid() {
    return "61952210-0184-4dcf-a849-bb4a78d470fb";
  }

  @Override
  public List<ReportDesign> constructReportDesigns(final ReportDefinition reportDefinition) {
    ReportDesign reportDesign = null;

    try {
      reportDesign =
          this.createXlsReportDesign(
              reportDefinition,
              "MULTIPLEX_LIST.xls",
              "Lista Pacientes na Coorte Multiplex",
              this.getExcelDesignUuid(),
              null);
      final Properties props = new Properties();
      props.put("repeatingSections", "sheet:1,row:6,dataset:MULT");
      props.put("sortWeight", "5000");
      reportDesign.setProperties(props);

    } catch (final IOException e) {
      throw new ReportingException(e.toString());
    }

    return Arrays.asList(reportDesign);
  }
}
