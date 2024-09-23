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
package org.openmrs.module.eptsreports.reporting.library.queries;

public interface PMTCTEIDQueries {

  class QUERY {

    public static final String
        findNumberOfInfantesWhoHadVirologicHIVTestBy12MonthsOfAgeDuringReportingPeriod =
            "																					  "
                + "select ccr_colheita_amostra.patient_id                                                                                                                                                                      "
                + "from(                                                                                                                                                                                  "
                + "    select ccr_admissao.patient_id, ccr_colheita_amostra.data_colheita                                                                                                                 "
                + "    from (                                                                                                                                                                             "
                + "                                                                                                                                                                                       "
                + "        select p.patient_id, min(pg.date_enrolled) data_admissao                                                                                                                       "
                + "            from patient p                                                                                                                                                             "
                + "            inner join patient_identifier pi on pi.patient_id = p.patient_id                                                                                                           "
                + "                inner join patient_program pg on pg.patient_id = p.patient_id                                                                                                          "
                + "            where p.voided = 0 and pi.voided = 0  and  pg.voided = 0 and pg.program_id = 6                                                                                             "
                + "            		and pi.identifier_type = 9 and pi.location_id = :location and pg.location_id = :location and pg.date_enrolled <= :endDate                                             "
                + "                 group by p.patient_id                                                                                                                                                                      "
                + "        union                                                                                                                                                                          "
                + "                                                                                                                                                                                       "
                + "        select p.patient_id, e.encounter_datetime data_admissao                                                                                                                        "
                + "        from patient p                                                                                                                                                                 "
                + "            inner join encounter e on e.patient_id = p.patient_id                                                                                                                      "
                + "        where p.voided = 0 and  e.voided = 0                                                                                                                                           "
                + "            and e.encounter_type = 92 and e.location_id = :location and e.encounter_datetime <= :endDate                                                                               "
                + "                                                                                                                                                                                       "
                + "    ) ccr_admissao                                                                                                                                                                     "
                + "    inner join(                                                                                                                                                                        "
                + "                                                                                                                                                                                       "
                + "                select p.patient_id, min(data_colheita_amostra.value_datetime) data_colheita                                                                                           "
                + "               from patient p                                                                                                                                                          "
                + "                    inner join encounter e on e.patient_id = p.patient_id                                                                                                              "
                + "                inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id                                                                            "
                + "                    inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id                                                          "
                + "                where p.voided = 0 and  e.voided = 0 and data_colheita_amostra.voided = 0 and diagnostico_precoce_infantil.voided = 0  and e.encounter_type = 13 and e.location_id = :location "
                + "                    	and diagnostico_precoce_infantil.concept_id = 165502                            "
                + "                    	and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime between :startDate and :endDate                                             "
                + " 					group by p.patient_id                                                                                                                                             "
                + "                                                                                                                                                                                       "
                + "    ) ccr_colheita_amostra on ccr_colheita_amostra.patient_id = ccr_admissao.patient_id                                                                                                "
                + ")ccr_colheita_amostra                                                                                                                                                                  "
                + "		inner join encounter e on e.patient_id = ccr_colheita_amostra.patient_id   																	  											  "
                + "		inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id  										  											  "
                + "		inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id  							                                              "
                + "		inner join person pe on pe.person_id = ccr_colheita_amostra.patient_id                                                                                                                    "
                + "where pe.voided = 0 and e.voided = 0 and data_colheita_amostra.voided = 0 and diagnostico_precoce_infantil.voided = 0                                                                  "
                + "		and diagnostico_precoce_infantil.concept_id = 165502 and diagnostico_precoce_infantil.value_coded in (165503, 165506, 165507, 165510) 											  "
                + "		and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime = ccr_colheita_amostra.data_colheita                    										      "
                + "		and diagnostico_precoce_infantil.obs_datetime = data_colheita_amostra.obs_datetime                                                    											  "
                + "		and pe.birthdate is not null and timestampdiff(day,pe.birthdate,ccr_colheita_amostra.data_colheita) <= 365                                    											  ";

    public static final String getPMTCTPatienstByAge =
        "																																			  "
            + "select ccr_colheita.patient_id                                                                                                                                                                      "
            + "from(                                                                                                                                                                                  "
            + "    select p.patient_id, min(data_colheita_amostra.value_datetime) data_colheita                                                                                                       "
            + "    from patient p                                                                                                                                                                     "
            + "        inner join encounter e on e.patient_id = p.patient_id                                                                                                                          "
            + "        inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id                                                                                    "
            + "         inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id                                                                     "
            + "    where p.voided = 0 and  e.voided = 0 and data_colheita_amostra.voided = 0  and diagnostico_precoce_infantil.voided = 0 and e.encounter_type = 13 and e.location_id = :location     "
            + "    		and diagnostico_precoce_infantil.concept_id = 165502 			                                        																	  "
            + "        	and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime between :startDate and :endDate                                                         "
            + "			group by p.patient_id                                                          																								  "
            + "                                                                                                                                                                                       "
            + ") ccr_colheita                                                                                                                                                                         "
            + "		inner join encounter e on e.patient_id = ccr_colheita.patient_id   																	  											  "
            + "		inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id  										  											  "
            + "		inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id  							                                              "
            + "		inner join person pe on pe.person_id = ccr_colheita.patient_id                                                                                                                    "
            + "where pe.voided = 0 and e.voided = 0 and data_colheita_amostra.voided = 0 and diagnostico_precoce_infantil.voided = 0                                                                  "
            + "		and diagnostico_precoce_infantil.concept_id = 165502 and diagnostico_precoce_infantil.value_coded in (165503, 165506, 165507, 165510) 											  "
            + "		and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime = ccr_colheita.data_colheita                    										      "
            + "		and diagnostico_precoce_infantil.obs_datetime = data_colheita_amostra.obs_datetime                                                    											  "
            + "		and pe.birthdate is not null and timestampdiff(%s, pe.birthdate, ccr_colheita.data_colheita) %s    	                                											  ";

    public static final String findRestulTestByFirstOrSencodTest =
        "             																													  "
            + "select ccr_colheita.patient_id                                                                                                                                                                      "
            + "from(                                                                                                                                                                                  "
            + "    select p.patient_id, min(data_colheita_amostra.value_datetime) data_colheita                                                                                                       "
            + "    from patient p                                                                                                                                                                     "
            + "        inner join encounter e on e.patient_id = p.patient_id                                                                                                                          "
            + "        inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id                                                                                    "
            + "         inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id                                                                     "
            + "    where p.voided = 0 and  e.voided = 0 and data_colheita_amostra.voided = 0  and diagnostico_precoce_infantil.voided = 0 and e.encounter_type = 13 and e.location_id = :location     "
            + "        and diagnostico_precoce_infantil.concept_id = 165502                                                                                                                           "
            + "        and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime between :startDate and :endDate                                                          "
            + "        group by p.patient_id                                                                                                                                                          "
            + "                                                                                                                                                                                       "
            + ") ccr_colheita                                                                                                                                                                         "
            + "		inner join encounter e on e.patient_id = ccr_colheita.patient_id   																	  											  "
            + "		inner join obs data_colheita_amostra on data_colheita_amostra.encounter_id = e.encounter_id  										  											  "
            + "		inner join obs diagnostico_precoce_infantil on diagnostico_precoce_infantil.encounter_id = e.encounter_id  							                                              "
            + "		inner join person pe on pe.person_id = ccr_colheita.patient_id                                                                                                                    "
            + "where pe.voided = 0 and e.voided = 0 and data_colheita_amostra.voided = 0 and diagnostico_precoce_infantil.voided = 0                                                                  "
            + "		and diagnostico_precoce_infantil.concept_id = 165502 and diagnostico_precoce_infantil.value_coded in (%s, %s) 											  						  "
            + "		and data_colheita_amostra.concept_id = 23821 and data_colheita_amostra.value_datetime = ccr_colheita.data_colheita                    										      "
            + "		and diagnostico_precoce_infantil.obs_datetime = data_colheita_amostra.obs_datetime                                                    											  "
            + "		and pe.birthdate is not null and timestampdiff(day,pe.birthdate,ccr_colheita.data_colheita) <= 365                                    											  ";
  }
}
