package org.openmrs.module.eptsreports.reporting.reports.tuberculosis.report;

// TODO: Nao vai entrar na release de Marco 2021 pq ainda nao tem documento de especificacao de
// requisitos
// @Component
public class SetupPatientsWithTuberculosisScreeningPositiveReport {

  // extends EptsDataExportManager {
  //
  //	private PatientsWithPositiveTuberculosisScreeningDataSet
  // patientsWithPositiveTuberculosisScreeningDataSet;
  //
  //	@Autowired
  //	private TxRttDataset txRttDataset;
  //
  //	@Autowired
  //	public SetupPatientsWithTuberculosisScreeningPositiveReport(
  //			PatientsWithPositiveTuberculosisScreeningDataSet
  // patientsWithPositiveTuberculosisScreeningDataSet) {
  //		this.patientsWithPositiveTuberculosisScreeningDataSet =
  // patientsWithPositiveTuberculosisScreeningDataSet;
  //	}
  //
  //	@Override
  //	public String getExcelDesignUuid() {
  //		return "eb11ef50-6214-11eb-ae93-0242ac130002";
  //	}
  //
  //	@Override
  //	public String getUuid() {
  //		return "644ef020-6215-11eb-ae93-0242ac130002";
  //	}
  //
  //	@Override
  //	public String getName() {
  //		return "LISTA DE RASTREIO DE TB POSITIVO - PEPFAR";
  //	}
  //
  //	@Override
  //	public String getDescription() {
  //		return "O Relatório devolve a Lista de Pacientes com Rastreio TB Positivo";
  //	}
  //
  //	@Override
  //	public ReportDefinition constructReportDefinition() {
  //		ReportDefinition rd = new ReportDefinition();
  //		rd.setUuid(getUuid());
  //		rd.setName(getName());
  //		rd.setDescription(getDescription());
  //		rd.setParameters(this.txRttDataset.getParameters());
  //
  //		rd.addDataSetDefinition("TB1",
  // Mapped.mapStraightThrough(patientsWithPositiveTuberculosisScreeningDataSet
  //				.patientsWithPositiveTuberculosisScreeningDataSet(this.txRttDataset.getParameters())));
  //
  //		return rd;
  //	}
  //
  //	@Override
  //	public String getVersion() {
  //		return "1.0-SNAPSHOT";
  //	}
  //
  //	@Override
  //	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
  //		ReportDesign reportDesign = null;
  //		try {
  //			reportDesign = createXlsReportDesign(reportDefinition, "ListaRastreioTBPositivo.xls",
  //					"Lista de Rastreio TB Positivo", getExcelDesignUuid(), null);
  //			Properties props = new Properties();
  //			props.put("repeatingSections", "sheet:1,row:7,dataset:TB1");
  //			props.put("sortWeight", "5000");
  //			reportDesign.setProperties(props);
  //		} catch (IOException e) {
  //			throw new ReportingException(e.toString());
  //		}
  //
  //		return Arrays.asList(reportDesign);
  //	}
}
