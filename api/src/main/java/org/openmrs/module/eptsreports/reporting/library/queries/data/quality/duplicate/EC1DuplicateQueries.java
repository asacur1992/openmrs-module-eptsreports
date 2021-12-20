package org.openmrs.module.eptsreports.reporting.library.queries.data.quality.duplicate;

public interface EC1DuplicateQueries {
  class QUERY {

    public static String findPatiendsWithDuplicatedNID =
        "                                                                                                                  "
            + "    select distinct patient.patient_id, 	                                                                                                                                     "
            + "																 		                                                                                                         "
            + "                           	location.name locationName,  																													 "
            + "                           	patient_identifier.patient_identifier_id, 																										 "
            + "                           	patient_identifier.identifier,    	 																											 "
            + "                            		concat(ifnull(person_name.given_name,''),' ',ifnull(person_name.middle_name,''),' ',ifnull(person_name.family_name,'')) as nomeCompleto, 	 "
            + "                            		person.birthdate, 							 																								 "
            + "                            		if(person.birthdate_estimated = true, 'Sim','Não') birthdate_estimated,   															 		 "
            + "                            		person.gender , 																 															 "
            + "                            		patient.date_created, 																 														 "
            + "                            		patient.date_changed, 																														 "
            + "                            		patient_identifier.date_created dataCriacaoNID, 																							 "
            + "                            		if(patient_identifier.preferred = true, 'Sim','Não') nidPreferido, 																			 "
            + "                            		patient_program.date_enrolled	 																											 "
            + "                           from 																																				 "
            + "                           ( 																																					 "
            + "             				   select distinct patient.patient_id, patient_identifier.identifier from( 															                 "
            + "             select identifier  from patient_identifier pi 													 	                                                             "
            + "             	join patient on patient.patient_id = pi.patient_id								 				 	                                                             "
            + "             where pi.voided = 0 and  pi.identifier_type =2 and patient.voided =0	 								                                                             "
            + "             																					                                                                                 "
            + "             group by pi.identifier  having count(*) >= 2 															                                                         "
            + "             ) NID 																								                                                             "
            + "             inner join patient_identifier on patient_identifier.identifier = NID.identifier    					                                                             "
            + "             inner join patient on patient.patient_id =patient_identifier.patient_id          						                                                         "
            + "             where patient.voided =0 and patient_identifier.voided =0  order by patient_identifier.identifier																	 "
            + "					                                                                                                                                                             "
            + "             		     ) patientNID 																																		 	 "
            + "                            inner join patient_identifier on patient_identifier.identifier = patientNID.identifier   															 "
            + "                            inner join patient on patient.patient_id =patient_identifier.patient_id																			 "
            + "                            inner join person  on person.person_id =patient.patient_id  																						 "
            + "                            inner join person_name on person_name.person_id = patient.patient_id		 																		 "
            + "                            left join location on (location.location_id =patient_identifier.location_id and location.retired =0) 												 "
            + "                            left join patient_program on (patient_program.patient_id = patient.patient_id and patient_program.program_id=2 and patient_program.voided =0)		 "
            + "                            where patient_identifier.voided = 0 																												 "
            + "                            and patient.voided =0 																															 "
            + "                            and person.voided = 0 																															 "
            + "                           group by patient_identifier.patient_identifier_id order by patient_identifier.identifier, patient_identifier.date_created desc                   	 "
            + "                                                                                                                                                                               ";

    public static String getEc1Total =
        "																											"
            + "   select distinct patient.patient_id from( 			                       "
            + "             select identifier  from patient_identifier pi 												   "
            + "             	join patient on patient.patient_id = pi.patient_id								 		       "
            + "             where pi.voided = 0 and  pi.identifier_type =2 and patient.voided =0	 						   "
            + "             																					               "
            + "             group by pi.identifier  having count(*) >= 2 												   "
            + "             ) NID 																						   "
            + "             inner join patient_identifier on patient_identifier.identifier = NID.identifier    			   "
            + "             inner join patient on patient.patient_id =patient_identifier.patient_id          			   "
            + "             where patient.voided =0 and patient_identifier.voided =0  order by patient_identifier.identifier  ";
  }
}
