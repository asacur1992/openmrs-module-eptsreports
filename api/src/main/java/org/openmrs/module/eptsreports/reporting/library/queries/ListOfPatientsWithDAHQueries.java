package org.openmrs.module.eptsreports.reporting.library.queries;

public interface ListOfPatientsWithDAHQueries {

  class QUERY {

    public static final String findPatientsInMDSOfDAHTotal =
        "					 		select p.patient_id from patient p "
            + "	inner join encounter e on p.patient_id=e.patient_id "
            + "	where e.voided=0 and p.voided=0 and  e.encounter_type =90 "
            + "	and e.encounter_datetime < :startDate and e.location_id=:location ";
  }
}
