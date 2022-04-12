/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.eptsreports.reporting.library.datasets;

import org.openmrs.module.eptsreports.reporting.library.cohorts.PrepNewCohortQueries;
import org.openmrs.module.eptsreports.reporting.library.indicators.EptsGeneralIndicator;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportUtils;
import org.openmrs.module.eptsreports.reporting.utils.PrepNewEligibilidadeSectorType;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author Abdul Sacur */
@Component
public class PrepNewLigacaoCPNeATSDataSet extends BaseDataSet {

  @Autowired private EptsGeneralIndicator eptsGeneralIndicator;

  @Autowired private PrepNewCohortQueries prepNew;

  /**
   * @param nomeSector
   * @param conceitoSectorInicio
   * @return
   */
  public DataSetDefinition constructDatset(
      final String nomeSector, final Integer conceitoSectorInicio) {
    CohortIndicatorDataSetDefinition definition = new CohortIndicatorDataSetDefinition();
    String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
    definition.setName("PrEP NEW Data Set Start Sector and Key Population");
    definition.addParameters(getParameters());

    /*
     * Metodo para adicionar e calcular de forma dinamica as desagregações do
     * conceito grupo ALVO PREP
     */
    addColumns(
        nomeSector,
        "SAAJ",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.SAAJ);

    addColumns(
        nomeSector,
        "TA",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.TRIAGEM_ADULTO);

    addColumns(
        nomeSector,
        "DC",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.DOENCAS_CRONICAS);

    addColumns(
        nomeSector,
        "CPN",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.CPN);

    addColumns(
        nomeSector,
        "CPF",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.CPF);

    addColumns(
        nomeSector,
        "OUTRO",
        conceitoSectorInicio,
        definition,
        mappings,
        PrepNewEligibilidadeSectorType.OUTRO);

    return definition;
  }
  /*
    public DataSetDefinition OtherDatset(final Integer conceitoSector) {
      CohortIndicatorDataSetDefinition definition = new CohortIndicatorDataSetDefinition();
      String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
      definition.setName("PrEP NEW Data Set Start Sector and Key Population");
      definition.addParameters(getParameters());

      definition.addColumn(
          "OUTRO",
          "PrEP_NEW_Outro",
          EptsReportUtils.map(
              eptsGeneralIndicator.getIndicator(
                  "PREP_NEW_Outro",
                  EptsReportUtils.map(
                      prepNew.getSectorClientsNewlyEnrolledInPrep(
                          23913, null, PrepNewKeyPopType.OTHER),
                      mappings)),
              mappings),
          "");

      return definition;
    }
  */
  private void addColumns(
      final String nomeSector,
      String prefixo,
      final Integer conceitoSector,
      CohortIndicatorDataSetDefinition definition,
      String mappings,
      final PrepNewEligibilidadeSectorType keypop) {

    definition.addColumn(
        nomeSector + prefixo,
        nomeDataSet(nomeSector, keypop),
        EptsReportUtils.map(
            eptsGeneralIndicator.getIndicator(
                nomeDataSet(nomeSector, keypop),
                EptsReportUtils.map(
                    prepNew.getSectorClientsNewlyEnrolledbyEligibility(conceitoSector, keypop),
                    mappings)),
            mappings),
        "");
  }

  private String nomeDataSet(final String nomeSector, final PrepNewEligibilidadeSectorType porta) {
    return "PrEP_NEW_"
        + nomeSector
        + "_"
        + porta.toString()
        + ": "
        + "Number of new clients in PrEP_NEW_"
        + nomeSector
        + "_"
        + porta.toString();
  }
}
