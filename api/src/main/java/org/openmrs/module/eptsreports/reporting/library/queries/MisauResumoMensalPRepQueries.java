/** */
package org.openmrs.module.eptsreports.reporting.library.queries;

public interface MisauResumoMensalPRepQueries {

  public class QUERY {

    public static final String findNumberOfUsersEligibleToPREP =
        "     		  	                        														"
            + "select distinct p.patient_id from patient p      																								"
            + "		inner join encounter e on e.patient_id = p.patient_id   																					"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																						"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																				"
            + "where p.voided = 0 and e.voided = 0 and o.voided = 0 																							"
            + "		and e.encounter_type = 80 and o.concept_id = 165289 and o.value_coded = 1065 																"
            + " 	and e.encounter_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 		";

    public static final String findNumberOfUsersWhoseInitiatedPrepForTheFirstTime =
        "                                                                           "
            + "select inicio_prep.patient_id 																													"
            + "from( 																																			"
            + "	select patient_id, min(data_inicio)  																											"
            + "	from ( 																																			"
            + "		select p.patient_id, min(o.obs_datetime) data_inicio 																						"
            + "		from patient p  																															"
            + "			inner join encounter e on e.patient_id = p.patient_id 																					"
            + "			inner join obs o on o.encounter_id = e.encounter_id 																					"
            + "			inner join patient_program pp on pp.patient_id = p.patient_id 																			"
            + "		where p.voided = 0 and e.voided = 0 and o.voided = 0 																						"
            + "			and e.encounter_type = 80 and o.concept_id = 165296 and o.value_coded = 1256 															"
            + "		 	and o.obs_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 	"
            + "		 	group by p.patient_id 																													"
            + "		union 																																		"
            + "		select p.patient_id, min(o.obs_datetime) data_inicio 																						"
            + "		from patient p  																															"
            + "			inner join encounter e on e.patient_id = p.patient_id 																					"
            + "			inner join obs o on o.encounter_id = e.encounter_id 																					"
            + "			inner join patient_program pp on pp.patient_id = p.patient_id 																			"
            + "		where p.voided = 0 and e.voided = 0 and o.voided = 0 																						"
            + "			and e.encounter_type = 80 and o.concept_id = 165211 																					"
            + "		 	and o.obs_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 	"
            + "		 	group by p.patient_id 																													"
            + "		) 																																			"
            + "	 inicio_prep group by patient_id 																												"
            + "	) 																																				"
            + "inicio_prep 																																		"
            + "left join ( 																																		"
            + "	select distinct max_estado.patient_id, max_estado.data_estado  																		 			"
            + "	from (                                          						 																 		"
            + "		select pg.patient_id,																											 			"
            + "			max(ps.start_date) data_estado																							 	 			"
            + "		from	patient p																												 			"
            + "			inner join patient_program pg on p.patient_id = pg.patient_id																 			"
            + "			inner join patient_state ps on pg.patient_program_id = ps.patient_program_id												 			"
            + "		where pg.voided=0 and ps.voided = 0 and p.voided = 0  																			 			"
            + "			and pg.program_id =25 and ps.start_date <= :endDate and location_id =:location group by pg.patient_id                                   "
            + "		)  																																	 		"
            + "	max_estado                                                                                                                         	 			"
            + "		inner join patient_program pp on pp.patient_id = max_estado.patient_id															 	 		"
            + "	    	inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado	         	 	"
            + "	where pp.program_id = 25 and ps.state = 76 and pp.voided = 0 and ps.voided = 0 "
            + "and pp.location_id = :location  and ps.start_date between :startDate and :endDate "
            + "	union 																																			"
            + "	select p.patient_id, e.encounter_datetime  																										"
            + "	from patient p  																																"
            + "		inner join encounter e on e.patient_id = p.patient_id 																						"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																						"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																				"
            + "	where p.voided = 0 and e.voided = 0 and o.voided = 0 																							"
            + "		and e.encounter_type = 80 and o.concept_id = 1594 and o.value_coded = 1369 																	"
            + "	 	and e.encounter_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided=0 and e.location_id = :location	 	"
            + " ) entradas on entradas.patient_id = inicio_prep.patient_id 																						"
            + "where entradas.patient_id is null 																												";

    public static final String findNumberOfUsersWhoseRestartedPREP =
        "																							"
            + "select prep_restart.patient_id  																													"
            + "from ( 																																			"
            + "	select distinct p.patient_id  																													"
            + "	from patient p  																																"
            + "		inner join encounter e on e.patient_id = p.patient_id 																						"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																						"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																				"
            + "	where p.voided = 0 and e.voided = 0 and o.voided = 0 																							"
            + "		and e.encounter_type = 80 and o.concept_id = 165296 and o.value_coded = 1705 																"
            + "	 	and o.obs_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 				"
            + ") prep_restart 																																	"
            + "left join ( 																																		"
            + "	select distinct max_estado.patient_id, max_estado.data_estado  																		 			"
            + "	from (                                          						 																		"
            + "		select pg.patient_id,																											 			"
            + "			max(ps.start_date) data_estado																							 	 			"
            + "		from	patient p																												 			"
            + "			inner join patient_program pg on p.patient_id = pg.patient_id																 			"
            + "			inner join patient_state ps on pg.patient_program_id = ps.patient_program_id												 			"
            + "		where pg.voided=0 and ps.voided = 0 and p.voided = 0  																			 			"
            + "			and pg.program_id =25 and ps.start_date <= :endDate and location_id =:location group by pg.patient_id                        		"
            + "		)  																																			"
            + "	max_estado                                                                                                                         	 			"
            + "		inner join patient_program pp on pp.patient_id = max_estado.patient_id															 			"
            + "	    	inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado	        		"
            + "	where pp.program_id = 25 and ps.state = 76 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location 		 							"
            + "	union 																																			"
            + "	select p.patient_id, e.encounter_datetime  																										"
            + "	from patient p  																																"
            + "		inner join encounter e on e.patient_id = p.patient_id 																						"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																						"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																				"
            + "	where p.voided = 0 and e.voided = 0 and o.voided = 0 																							"
            + "		and e.encounter_type = 80 and o.concept_id = 1594 and o.value_coded = 1369 																	"
            + "	 	and e.encounter_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 		"
            + ") entradas on entradas.patient_id = prep_restart.patient_id 																						"
            + "where entradas.patient_id is null																												";

    public static final String findNumberOfUsersWhoReceivePrepREgimen =
        "																							"
            + "select distinct p.patient_id  																														"
            + "from patient p  																																		"
            + "		inner join encounter e on e.patient_id = p.patient_id 																							"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																							"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																					"
            + "where p.voided = 0 and e.voided = 0 and o.voided = 0 																								"
            + "		and e.encounter_type = 80 and o.concept_id = 165213 and o.value_coded in (165214,165215,165216) 												"
            + " 	and e.encounter_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 			"
            + "union 																																				"
            + "select distinct p.patient_id  																														"
            + "from patient p  																																		"
            + "		inner join encounter e on e.patient_id = p.patient_id 																							"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																							"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																					"
            + "where p.voided = 0 and e.voided = 0 and o.voided = 0 																								"
            + "	and e.encounter_type = 81 and o.concept_id = 165213 and o.value_coded in (165214,165215) 															"
            + " and e.encounter_datetime between :startDate and :endDate and pp.program_id = 25 and pp.voided is false and e.location_id = :location 				";

    public static final String findNumberOfUseresCurrentlyOnPrep =
        "																									"
            + "select inicio_prep.patient_id 																																				"
            + "from ( 																																										"
            + "	select inicio_prep.patient_id, inicio_prep.data_inicio																														"
            + "	from( 																																			 							"
            + "		select patient_id, min(data_inicio) data_inicio																											 				"
            + "		from ( 																																			 						"
            + "			select p.patient_id, min(o.obs_datetime) data_inicio 																						 						"
            + "			from patient p  																															 						"
            + "				inner join encounter e on e.patient_id = p.patient_id 																					 						"
            + "				inner join obs o on o.encounter_id = e.encounter_id 																					 						"
            + "				inner join patient_program pp on pp.patient_id = p.patient_id 																			 						"
            + "			where p.voided = 0 and e.voided = 0 and o.voided = 0 																						 						"
            + "				and e.encounter_type = 80 and o.concept_id = 165296 and o.value_coded = 1256 																					"
            + "			 	and o.obs_datetime between (:startDate- INTERVAL 1 MONTH)  and (:endDate - INTERVAL 1 MONTH)  and pp.program_id = 25 and pp.voided is false 				"
            + "				and e.location_id = :location 	 																																"
            + "			 	group by p.patient_id 																													 						"
            + "			union 																																		 						"
            + "			select p.patient_id, min(o.obs_datetime) data_inicio 																						 						"
            + "			from patient p  																															 						"
            + "				inner join encounter e on e.patient_id = p.patient_id 																					 						"
            + "				inner join obs o on o.encounter_id = e.encounter_id 																					 						"
            + "				inner join patient_program pp on pp.patient_id = p.patient_id 																			 						"
            + "			where p.voided = 0 and e.voided = 0 and o.voided = 0 																						 						"
            + "				and e.encounter_type = 80 and o.concept_id = 165211 																											"
            + "			 	and o.obs_datetime between (:startDate- INTERVAL 1 MONTH)  and (:endDate - INTERVAL 1 MONTH)  and pp.program_id = 25 and pp.voided is false        		"
            + "			 	and e.location_id = :location 	 																																"
            + "			 	group by p.patient_id 																													 						"
            + "			) 																																			 						"
            + "		 inicio_prep group by patient_id 																												 						"
            + "		) 																																				 						"
            + "	inicio_prep 																																		 						"
            + "	left join ( 																																		 						"
            + "		select distinct max_estado.patient_id, max_estado.data_estado  																		 			 						"
            + "		from (                                          						 																 		 						"
            + "			select pg.patient_id,																											 			 						"
            + "				max(ps.start_date) data_estado																							 	 			 						"
            + "			from	patient p																												 			 						"
            + "				inner join patient_program pg on p.patient_id = pg.patient_id																 			 						"
            + "				inner join patient_state ps on pg.patient_program_id = ps.patient_program_id												 			 						"
            + "			where pg.voided=0 and ps.voided = 0 and p.voided = 0  																			 			 						"
            + "				and pg.program_id =25 and ps.start_date <= (:endDate - INTERVAL 1 MONTH)  and location_id =:location group by pg.patient_id                                    	"
            + "			)  																																	 		 						"
            + "		max_estado                                                                                                                         	 			 						"
            + "			inner join patient_program pp on pp.patient_id = max_estado.patient_id															 	 		 						"
            + "		    	inner join patient_state ps on ps.patient_program_id = pp.patient_program_id and ps.start_date = max_estado.data_estado	         	 							"
            + "		where pp.program_id = 25 and ps.state = 76 and pp.voided = 0 and ps.voided = 0 and pp.location_id = :location  and ps.start_date between (:startDate- INTERVAL 1 MONTH) "
            + "      	and	(:endDate - INTERVAL 1 MONTH)   																																"
            + "		union 																																			 						"
            + "		select p.patient_id, e.encounter_datetime  																										 						"
            + "		from patient p  																																 						"
            + "			inner join encounter e on e.patient_id = p.patient_id 																						 						"
            + "			inner join obs o on o.encounter_id = e.encounter_id 																						 						"
            + "			inner join patient_program pp on pp.patient_id = p.patient_id 																				 						"
            + "		where p.voided = 0 and e.voided = 0 and o.voided = 0 																							 						"
            + "			and e.encounter_type = 80 and o.concept_id = 1594 and o.value_coded = 1369 																	   						"
            + "		 	and e.encounter_datetime between (:startDate- INTERVAL 1 MONTH)  and (:endDate - INTERVAL 1 MONTH)  and pp.program_id = 25 and pp.voided is false 					"
            + "		 	and "
            + "e.location_id = :location	 	 																																			"
            + "	) entradas on entradas.patient_id = inicio_prep.patient_id 																						 							"
            + "	where entradas.patient_id is null 																																			"
            + "   )  																																										"
            + "inicio_prep 																																									"
            + "inner join ( 																																								"
            + "	select p.patient_id, e.encounter_datetime																					 												"
            + "	from patient p  																															 								"
            + "		inner join encounter e on e.patient_id = p.patient_id 																					 								"
            + "		inner join obs o on o.encounter_id = e.encounter_id 																					 								"
            + "		inner join patient_program pp on pp.patient_id = p.patient_id 																			 								"
            + "	where p.voided = 0 and e.voided = 0 and o.voided = 0 																						 								"
            + "		and e.encounter_type = 81 and o.concept_id = 165217 and o.value_numeric >=2 																					 		"
            + "	 	and e.encounter_datetime between :startDate and :endDate  and pp.program_id = 25 and pp.voided is false and e.location_id = :location 	 								"
            + "	 	group by p.patient_id 																													 								"
            + "	) 																																											"
            + "seguimento_prep on seguimento_prep.patient_id = inicio_prep.patient_id 																										"
            + "where seguimento_prep.encounter_datetime between inicio_prep.data_inicio and (inicio_prep.data_inicio + INTERVAL 33 DAY) 													";
  }
}