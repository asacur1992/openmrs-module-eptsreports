package org.openmrs.module.eptsreports.reporting.library.queries;

public interface DSDQueriesInterface {
  class QUERY {

    public enum DSDDispensationInterval {
      QUARTERLY(1),

      SEMI_ANNUAL(2),

      ANNUAL(3);

      private Integer intervalValue;

      private DSDDispensationInterval(Integer intervalValue) {
        this.intervalValue = intervalValue;
      }

      public Integer getIntervalValue() {
        return this.intervalValue;
      }
    }

    public enum DSDModeTypeLevel1 {
      DD(1),

      DCA_APE(2),

      DCP(3),

      BM(4),

      CM(5),

      EH(6);

      private Integer value;

      private DSDModeTypeLevel1(Integer value) {
        this.value = value;
      }

      public Integer getIntervalValue() {
        return this.value;
      }
    }

    public enum DSDModelTypeLevel2 {
      GAAC(23724),

      AF(23725),

      CA(23726),

      TB(165317),

      CT(165318),

      SAAJ(165319),

      SMI(165320),

      DAH(165321),

      FR(23729);

      private Integer conceptId;

      private DSDModelTypeLevel2(Integer conceptId) {
        this.conceptId = conceptId;
      }

      public Integer getConceptId() {
        return this.conceptId;
      }
    }

    public static final String
        findPatientsWithAdverseDrugReactionsRequiringRegularMonitoringNotifiedInLast6Months =
            "Select p.patient_id From patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and concept_id=2015 and e.encounter_type in (6,9) and e.encounter_datetime "
                + "between (:endDate - INTERVAL 6 MONTH) AND :endDate and e.location_id=:location and o.value_coded "
                + "IN (23748,6293,23749,29,23750,23751,6299,23752) group by p.patient_id ";

    public static final String findPatientsWhoHaveBeenNotifiedOfKaposiSarcoma =
        "SELECT p.patient_id FROM patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + "WHERE o.concept_id=1406 AND o.value_coded = 507 AND encounter_type in (6,9) AND p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.location_id =:location AND e.encounter_datetime<=:endDate group by p.patient_id ";

    public static final String findPatientsWhoAreFastTrack =
        ""
            + "select ultima_consulta.patient_id  																										"
            + "from        																																"
            + "( 	 																																	"
            + "	select p.patient_id,max(e.encounter_datetime) data_consulta  																			"
            + "	from patient p   																														"
            + "		inner join encounter e on p.patient_id=e.patient_id   																				"
            + "		inner join obs o on o.encounter_id=e.encounter_id   																				"
            + "	where e.encounter_type in(6,9) and e.voided=0 and o.voided=0 and p.voided=0  and e.location_id=:location and o.obs_datetime<=:endDate  	"
            + "		group by p.patient_id    																											"
            + ")ultima_consulta   																														"
            + "	inner join encounter e on e.patient_id = ultima_consulta.patient_id  																	"
            + "	inner join  obs obs_seguimento on obs_seguimento.encounter_id=e.encounter_id 															"
            + "where  e.voided = 0 and obs_seguimento.voided=0 and e.encounter_type in (6,9) and e.encounter_datetime=ultima_consulta.data_consulta  	"
            + "	and obs_seguimento.concept_id=1410  and e.location_id=:location 																		"
            + "	and datediff(obs_seguimento.value_datetime,ultima_consulta.data_consulta) between 175 and 190 											";

    public static final String findPatientsWhoWhereTransferredInPriorReportingPeriod =
        "														"
            + "select  max_estado.patient_id                                                                                                    "
            + "from(                                                                                                                             "
            + "  select pg.patient_id,                                                                                                           "
            + "      max(ps.start_date) data_estado                                                                                              "
            + "    from  patient p                                                                                                               "
            + "      inner join patient_program pg on p.patient_id = pg.patient_id                                                               "
            + "      inner join patient_state ps on pg.patient_program_id = ps.patient_program_id                                                "
            + "    where pg.voided=0 and ps.voided=0 and p.voided=0  and pg.program_id = 2                                                       "
            + "      and ps.start_date>=:startDate and  ps.start_date< :endDate and pg.location_id =:location group by pg.patient_id             "
            + "    )                                                                                                                             "
            + "max_estado                                                                                                                        "
            + "  inner join patient_program pp on pp.patient_id = max_estado.patient_id                                                          "
            + "  inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado         "
            + "where pp.program_id = 2 and ps.state = 29 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location                      "
            + "union                                                                                                                             "
            + "select transferido_de.patient_id                                                                                                  "
            + "from(                                                                                                                             "
            + "  select p.patient_id, max(obsOpenDate.value_datetime) data_estado from patient p                                                 "
            + "    inner join encounter e on p.patient_id=e.patient_id                                                                           "
            + "    inner join obs obsTransIn on e.encounter_id= obsTransIn.encounter_id                                                          "
            + "    inner join obs obsOpenDate on e.encounter_id= obsOpenDate.encounter_id                                                        "
            + "    inner join obs obsInTarv on e.encounter_id= obsInTarv.encounter_id                                                            "
            + "   where e.voided=0 and obsTransIn.voided=0 and p.voided=0  and obsOpenDate.voided =0 and obsInTarv.voided =0                     "
            + "    and e.encounter_type=53 and obsTransIn.concept_id=1369 and obsTransIn.value_coded=1065                                        "
            + "    and obsOpenDate.concept_id=23891 and obsOpenDate.value_datetime is not null                                                   "
            + "    and obsInTarv.concept_id=6300 and obsInTarv.value_coded=6276                                                                  "
            + "    and obsOpenDate.value_datetime >=:startDate and obsOpenDate.value_datetime < :endDate and e.location_id=:location group by p.patient_id  "
            + ") transferido_de                                                                                                                                ";

    public static String findPatientsWhoAreIncludedInDSDModel(DSDDispensationInterval dsdInterval) {

      String sql =
          ""
              + "select patient_id 																																		"
              + "from 																																						"
              + "( 																																						"
              + "	select * 																																				"
              + "	from 																																					"
              + "	( 																																						"
              + "		select * 																																			"
              + "		from 																																				"
              + "		( 																																					"
              + "			select ultimo_fila.patient_id,  																												"
              + "				max(data_proximo_levantamento.value_datetime) data_consulta, 																						"
              + "				if(datediff(max(data_proximo_levantamento.value_datetime), ultimo_fila.data_ultimo_levantamento) between 83 and 97,1, 						"
              + "				if(datediff(max(data_proximo_levantamento.value_datetime), ultimo_fila.data_ultimo_levantamento) between 175 and 190,2, 					"
              + "				if(datediff(max(data_proximo_levantamento.value_datetime), ultimo_fila.data_ultimo_levantamento) between 335 and 395,3,4))) as tipo_dispensa, "
              + "				1 as fonte, 																																"
              + "				1 as ordem_mdc 																																"
              + "			from 																																			"
              + "			( 																																				"
              + "				select p.patient_id, max(encounter_datetime) data_ultimo_levantamento   																	"
              + "				from patient p  																															"
              + "					inner join encounter e on e.patient_id=p.patient_id  																					"
              + "				where p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location  														"
              + "					and e.encounter_datetime <=:endDate   																									"
              + "					group by p.patient_id 																													"
              + "			) ultimo_fila 																																	"
              + "				inner join encounter e on e.patient_id = ultimo_fila.patient_id 																			"
              + "				inner join obs data_proximo_levantamento on data_proximo_levantamento.encounter_id = e.encounter_id 										"
              + "			where e.voided = 0 and data_proximo_levantamento.voided = 0 and date(e.encounter_datetime) = date(ultimo_fila.data_ultimo_levantamento) 		"
              + "				and data_proximo_levantamento.concept_id =5096 and e.location_id = :location  group by ultimo_fila.patient_id 								"
              + " 																																							"
              + "			union 																																			"
              + " 																																							"
              + "			select ultimo_mdc.patient_id, 																							 						"
              + "              		  ultimo_mdc.data_consulta, 																							 			"
              + "              		  case o.value_coded			 																					 				"
              + "					when 23730 then 1 																							 							"
              + "					when 23888 then 2 																							 							"
              + "					when 165314 then 3			 																				 							"
              + "				else 4 end as tipo_dispensa, 																					 							"
              + "				2  as fonte, 																									 							"
              + "				case o.value_coded			 																					 							"
              + "					when 23730 then 1 																							 							"
              + "					when 23888 then 1 																							 							"
              + "					when 165314 then 1			 																				 							"
              + "				else 2 end as ordem_mdc 																							 						"
              + "			from  																													 						"
              + "              	( 																														 				"
              + "				select p.patient_id, max(e.encounter_datetime) data_consulta  														 						"
              + "				from patient p  																									 						"
              + "					inner join encounter e on p.patient_id=e.patient_id  														 							"
              + "					inner join obs o on o.encounter_id=e.encounter_id  															 							"
              + "				where p.voided = 0 and e.voided=0 and o.voided=0 																							"
              + "					and e.encounter_type = 6 and e.encounter_datetime<=:endDate and e.location_id=:location  						"
              + "					group by p.patient_id  																								 					"
              + "			) ultimo_mdc 																												 					"
              + "				inner join encounter e on ultimo_mdc.patient_id=e.patient_id  																 				"
              + "				inner join obs grupo on grupo.encounter_id=e.encounter_id  																 					"
              + "				inner join obs o on o.encounter_id=e.encounter_id  																		 					"
              + "				inner join obs obsEstado on obsEstado.encounter_id=e.encounter_id  														 					"
              + "				where e.encounter_type=6 and e.location_id=:location   															 							"
              + "					and o.concept_id=165174 and o.voided=0  																			 					"
              + "					and grupo.concept_id=165323  and grupo.voided=0  																 						"
              + "					and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)  										 						"
              + "					and obsEstado.voided=0  and grupo.voided=0  																	 						"
              + "					and grupo.obs_id=o.obs_group_id and grupo.obs_id=obsEstado.obs_group_id   										 						"
              + "					and e.encounter_datetime=ultimo_mdc.data_consulta  																						"
              + "					group by ultimo_mdc.patient_id	 																										"
              + " 																																							"
              + "			union 																																			"
              + " 																																							"
              + "			select ultimo_tipo_dispensa.patient_id, 																							 			"
              + "              		  ultimo_tipo_dispensa.data_clinica, 																								"
              + "              		  case obsTipo.value_coded			 																				 				"
              + "					when 23720 then 1 																							 							"
              + "					when 23888 then 2			 																				 							"
              + "				  else null end as tipo_dispensa, 																						 					"
              + "				 3 as fonte, 																										 						"
              + "				 1 as ordem_mdc 																										 					"
              + "              	from  																													 				"
              + "               ( 																														 					"
              + "				select p.patient_id,max(e.encounter_datetime) data_clinica  														 						"
              + "				from patient p  																									 						"
              + "					inner join encounter e on p.patient_id=e.patient_id  														 							"
              + "					inner join obs o on o.encounter_id=e.encounter_id  															 							"
              + "				where e.voided=0 and o.voided=0 and p.voided=0   																 							"
              + "					and e.encounter_type =6 																					 							"
              + "					and e.encounter_datetime<=:endDate and e.location_id=:location  												 						"
              + "					group by p.patient_id  																								 					"
              + "              	) ultimo_tipo_dispensa 																												 	"
              + "    				inner join encounter e on e.patient_id=ultimo_tipo_dispensa.patient_id 																 	"
              + "    				inner join obs obsTipo on obsTipo.encounter_id=e.encounter_id 															 				"
              + "    			where  e.voided=0 and obsTipo.voided=0 and e.encounter_type=6 and ultimo_tipo_dispensa.data_clinica=e.encounter_datetime   					"
              + "    				and e.location_id=:location and obsTipo.concept_id=23739 																				"
              + "    				group by ultimo_tipo_dispensa.patient_id 	 																							"
              + "    				 																																		"
              + "		) todas_fontes 																																		"
              + "			order by patient_id, data_consulta desc, fonte, ordem_mdc 																							"
              + "	) primeira_fonte 																																		"
              + "		group by patient_id 																																"
              + ") dispensa 																																				"
              + "where dispensa.tipo_dispensa = %s 																														";

      return String.format(sql, dsdInterval.getIntervalValue());
    }

    public static final String findPatientsWhoAreIncludedInDSDModel(DSDModeTypeLevel1 dsdMode) {

      String sql =
          ""
              + "select patient_id                                                                                                                       "
              + "from                                                                                                                                    "
              + "(                                                                                                                                       "
              + "    select *                                                                                                                            "
              + "    from                                                                                                                                "
              + "    (                                                                                                                                   "
              + "        select *                                                                                                                        "
              + "        from                                                                                                                            "
              + "        (                                                                                                                               "
              + "                                                                                                                                        "
              + "            select ultimo_fila.patient_id,                                                                                              "
              + "                  max(data_proximo_levantamento.value_datetime) data_consulta,                                                          "
              + "                  case modo_dispensa.value_coded                                                                                        "
              + "                    when 165177 then 1                                                                                                  "
              + "                    when 165179 then 2                                                                                                  "
              + "                    when 165178 then 3                                                                                                  "
              + "                    when 165181 then 4                                                                                                  "
              + "                    when 165182 then 5                                                                                                  "
              + "                    when 165176 then 6                                                                                                  "
              + "                  else 10 end as tipo_dispensa,                                                                                         "
              + "                  1  as fonte,                                                                                                          "
              + "            1  as ordem_mdc                                                                                                             "
              + "            from                                                                                                                        "
              + "            (                                                                                                                           "
              + "                select p.patient_id, max(encounter_datetime) data_ultimo_levantamento                                                   "
              + "                from patient p                                                                                                          "
              + "                  inner join encounter e on e.patient_id=p.patient_id                                                                   "
              + "                where p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location                                     "
              + "                  and e.encounter_datetime <=:endDate                                                                                   "
              + "                  group by p.patient_id                                                                                                 "
              + "            ) ultimo_fila                                                                                                               "
              + "              inner join encounter e on e.patient_id = ultimo_fila.patient_id                                                           "
              + "                inner join obs data_proximo_levantamento on data_proximo_levantamento.encounter_id = e.encounter_id                     "
              + "                inner join obs modo_dispensa on modo_dispensa.encounter_id = e.encounter_id                                             "
              + "            where e.voided = 0 and data_proximo_levantamento.voided = 0 and modo_dispensa.voided = 0 and e.encounter_type=18            "
              + "              and date(e.encounter_datetime) = date(ultimo_fila.data_ultimo_levantamento) and modo_dispensa.concept_id =165174          "
              + "                and data_proximo_levantamento.concept_id =5096 and e.location_id = :location group by ultimo_fila.patient_id            "
              + "                                                                                                                                        "
              + "             union                                                                                                                      "
              + "                                                                                                                                        "
              + "            select ultimo_mdc.patient_id,                                                                                               "
              + "                   ultimo_mdc.data_consulta,                                                                                            "
              + "                   case o.value_coded                                                                                                   "
              + "                    when 165315 then 1                                                                                                  "
              + "                    when 165179 then 2                                                                                                  "
              + "                    when 165178 then 3                                                                                                  "
              + "                    when 165264 then 4                                                                                                  "
              + "                    when 165265 then 5                                                                                                  "
              + "                    when 165316 then 6                                                                                                  "
              + "                    else 10 end as tipo_dispensa,                                                                                       "
              + "                    2  as fonte,                                                                                                        "
              + "                    case o.value_coded                                                                                                  "
              + "                    when 165315 then 1                                                                                                  "
              + "                    when 165179 then 1                                                                                                  "
              + "                    when 165178 then 1                                                                                                  "
              + "                    when 165264 then 1                                                                                                  "
              + "                    when 165265 then 1                                                                                                  "
              + "                    when 165316 then 1                                                                                                  "
              + "                  else 2 end as ordem_mdc                                                                                               "
              + "            from                                                                                                                        "
              + "            (                                                                                                                           "
              + "                select p.patient_id, max(e.encounter_datetime) data_consulta                                                            "
              + "                from patient p                                                                                                          "
              + "                  inner join encounter e on p.patient_id=e.patient_id                                                                   "
              + "                  inner join obs o on o.encounter_id=e.encounter_id                                                                     "
              + "                where p.voided = 0 and e.voided=0 and o.voided=0                                                                        "
              + "                  and e.encounter_type = 6 and e.encounter_datetime<=:endDate and e.location_id=:location                               "
              + "                  group by p.patient_id                                                                                                 "
              + "            ) ultimo_mdc                                                                                                                "
              + "              inner join encounter e on ultimo_mdc.patient_id=e.patient_id                                                              "
              + "          inner join obs grupo on grupo.encounter_id=e.encounter_id                                                                     "
              + "             inner join obs o on o.encounter_id=e.encounter_id                                                                          "
              + "              inner join obs obsEstado on obsEstado.encounter_id=e.encounter_id                                                         "
              + "            where e.encounter_type=6 and e.location_id=:location                                                                        "
              + "              and o.concept_id=165174 and o.voided=0                                                                                    "
              + "                and grupo.concept_id=165323  and grupo.voided=0                                                                         "
              + "                and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)                                                "
              + "                and obsEstado.voided=0  and grupo.voided=0                                                                              "
              + "                and grupo.obs_id=o.obs_group_id and grupo.obs_id=obsEstado.obs_group_id                                                 "
              + "                and e.encounter_datetime=ultimo_mdc.data_consulta  and o.value_coded                                                    "
              + "                                                                                                                                        "
              + "        ) todas_fontes                                                                                                                  "
              + "        order by patient_id,data_consulta desc, fonte, ordem_mdc                                                                        "
              + "    ) primeira_fonte                                                                                                                    "
              + "    group by patient_id                                                                                                                 "
              + ") dispensa                                                                                                                              "
              + "where dispensa.tipo_dispensa = %s                                                                                                       ";

      return String.format(sql, dsdMode.getIntervalValue());
    }

    public static final String findPatientsWhoAreIncludedInDSDModel(
        DSDModelTypeLevel2 dsdModelType) {
      String sql =
          ""
              + "select ultima_ficha.patient_id							 																										"
              + "from   																																							"
              + "(   																																								"
              + "	select p.patient_id,max(e.encounter_datetime) data_consulta  																									"
              + "	from patient p   																																				"
              + "		inner join encounter e on p.patient_id=e.patient_id   																										"
              + "		inner join obs o on o.encounter_id=e.encounter_id   																										"
              + "	where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0  and e.location_id=:location and o.obs_datetime<=:endDate  								"
              + "		group by p.patient_id    																																	"
              + ")ultima_ficha 																																					"
              + "	inner join encounter e on ultima_ficha.patient_id=e.patient_id    																								"
              + "	inner join obs grupo on grupo.encounter_id=e.encounter_id    																									"
              + "	inner join obs mdc on mdc.encounter_id=e.encounter_id   																										"
              + "	inner join obs obsEstado on obsEstado.encounter_id=e.encounter_id    																							"
              + "where e.voided = 0 and grupo.voided = 0 and mdc.voided = 0 and obsEstado.voided = 0 and e.encounter_type = 6 and e.location_id=:location  						"
              + "	and mdc.concept_id=165174 and mdc.value_coded = %s and grupo.concept_id=165323  and obsEstado.concept_id=165322  and obsEstado.value_coded in(1256,1257)      	"
              + "	and grupo.obs_id=mdc.obs_group_id and grupo.obs_id=obsEstado.obs_group_id  and e.encounter_datetime = ultima_ficha.data_consulta 								";
      return String.format(sql, dsdModelType.getConceptId());
    }

    public static final String findPatientsByAgeRange() {

      return "select patient.patient_id from patient                         		"
          + "	inner join person on person.person_id = patient.patient_id		"
          + "where patient.voided = 0 and person.voided = 0 					"
          + "	and floor(datediff(:endDate,person.birthdate)/365) 			    ";
    }
  }
}
