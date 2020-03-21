/** */
package org.openmrs.module.eptsreports.reporting.reports.listings;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openmrs.module.eptsreports.reporting.library.datasets.listings.PatientsWhoStartedTPIDataset;
import org.openmrs.module.eptsreports.reporting.reports.manager.EptsDataExportManager;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author Stélio Moiane */
@Component
public class SetupPatientsWhoStartedTPIList extends EptsDataExportManager {

  @Autowired private PatientsWhoStartedTPIDataset patientsWhoStartedTPIDataset;

  @Override
  public String getUuid() {
    return "ca0e7a74-190e-494d-b722-077d3b6017ef";
  }

  @Override
  public String getName() {
    return "LISTA DE PACIENTES QUE INICIARAM PROFILAXIA COM ISONIAZIDA (TPI) - V2.0";
  }

  @Override
  public String getDescription() {
    return "This report provides the list of patients who started TPI";
  }

  @Override
  public ReportDefinition constructReportDefinition() {
    ReportDefinition reportDefinition = new ReportDefinition();

    reportDefinition.setUuid(getUuid());
    reportDefinition.setName(getName());
    reportDefinition.setDescription(getDescription());
    reportDefinition.setParameters(patientsWhoStartedTPIDataset.getParameters());
    reportDefinition.addDataSetDefinition(
        "TPI",
        Mapped.mapStraightThrough(
            patientsWhoStartedTPIDataset.constructPatientsWhoStartedTPIDataset(
                patientsWhoStartedTPIDataset.getParameters())));

    return reportDefinition;
  }

  @Override
  public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
    ReportDesign reportDesign = null;
    try {
      reportDesign =
          createXlsReportDesign(
              reportDefinition,
              "PATIENTS_WHO_STARTED_TPI_LIST_REPORT.xls",
              "LISTA DE PACIENTES QUE INICIARAM TPI",
              getExcelDesignUuid(),
              null);
      Properties props = new Properties();
      props.put("repeatingSections", "sheet:1,row:3,dataset:TPI");
      props.put("sortWeight", "5000");
      reportDesign.setProperties(props);
    } catch (IOException e) {
      throw new ReportingException(e.toString());
    }

    return Arrays.asList(reportDesign);
  }

  @Override
  public String getVersion() {
    return "1.0-SNAPSHOT";
  }

  @Override
  public String getExcelDesignUuid() {
    return "4db0fac8-70cd-4b86-9dd2-14407d7b75ce";
  }
}
