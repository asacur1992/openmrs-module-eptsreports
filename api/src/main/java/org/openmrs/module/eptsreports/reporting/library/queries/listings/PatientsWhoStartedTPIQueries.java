/** */
package org.openmrs.module.eptsreports.reporting.library.queries.listings;

/** @author Stélio Moiane */
public interface PatientsWhoStartedTPIQueries {

  class QUERY {
    public static final String findPatientsWhoStartedTPI =
        "select *\n"
            + "from \n"
            + "\n"
            + "(Select 	inicio_tpi.patient_id,\n"
            + "		inicio_tpi.data_inicio_tpi,\n"
            + "		terminou_tpi.data_final_tpi,\n"
            + "		concat(ifnull(pn.given_name,''),' ',ifnull(pn.middle_name,''),' ',ifnull(pn.family_name,'')) nome,\n"
            + "		pid.identifier as nid,\n"
            + "		seguimento.ultimo_seguimento,\n"
            + "		if(obs.value_coded is not null,if(obs.value_coded in (1065,1257),'Sim','Não'),'SI') recebeu_profilaxia,\n"
            + "		date_add(date_add(inicio_tpi.data_inicio_tpi, interval 6 month), interval -1 day) data_completa_6meses,\n"
            + "		inicio_tarv.data_inicio data_inicio_tarv\n"
            + "from \n"
            + "(	select inicio_tpi.patient_id,min(inicio_tpi.data_inicio_tpi) data_inicio_tpi\n"
            + "	from \n"
            + "	(	select 	p.patient_id,min(o.value_datetime) data_inicio_tpi\n"
            + "		from	patient p\n"
            + "				inner join encounter e on p.patient_id=e.patient_id\n"
            + "				inner join obs o on o.encounter_id=e.encounter_id\n"
            + "		where 	e.voided=0 and p.voided=0 and o.value_datetime between :startDate and :endDate and\n"
            + "				o.voided=0 and o.concept_id=6128 and e.encounter_type in (6,9,53) and e.location_id=:location\n"
            + "		group by p.patient_id\n"
            + "		\n"
            + "		union \n"
            + "		\n"
            + "		select 	p.patient_id,min(e.encounter_datetime) data_inicio_tpi\n"
            + "		from	patient p\n"
            + "				inner join encounter e on p.patient_id=e.patient_id\n"
            + "				inner join obs o on o.encounter_id=e.encounter_id\n"
            + "		where 	e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and :endDate and\n"
            + "				o.voided=0 and o.concept_id=6122 and o.value_coded=1256 and e.encounter_type in (6,9) and  e.location_id=:location\n"
            + "		group by p.patient_id	\n"
            + "		\n"
            + "	) inicio_tpi\n"
            + "	group by inicio_tpi.patient_id\n"
            + ") inicio_tpi\n"
            + "left join \n"
            + "(	\n"
            + "\n"
            + "	select patient_id, max(data_final_tpi) data_final_tpi\n"
            + "	from \n"
            + "		(\n"
            + "			select 	p.patient_id,max(o.value_datetime) data_final_tpi\n"
            + "			from	patient p\n"
            + "					inner join encounter e on p.patient_id=e.patient_id\n"
            + "					inner join obs o on o.encounter_id=e.encounter_id\n"
            + "			where 	e.voided=0 and p.voided=0 and o.value_datetime between :startDate and curdate() and\n"
            + "					o.voided=0 and o.concept_id=6129 and e.encounter_type in (6,9,53) and e.location_id=:location\n"
            + "			group by p.patient_id\n"
            + "			\n"
            + "			union \n"
            + "			\n"
            + "			select 	p.patient_id,max(e.encounter_datetime) data_final_tpi\n"
            + "			from	patient p\n"
            + "					inner join encounter e on p.patient_id=e.patient_id\n"
            + "					inner join obs o on o.encounter_id=e.encounter_id\n"
            + "			where 	e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and\n"
            + "					o.voided=0 and o.concept_id=6122 and o.value_coded=1267 and e.encounter_type=6 and  e.location_id=:location\n"
            + "			group by p.patient_id\n"
            + "		) endTPI\n"
            + "	group by patient_id\n"
            + "	\n"
            + ") terminou_tpi on inicio_tpi.patient_id=terminou_tpi.patient_id and inicio_tpi.data_inicio_tpi<terminou_tpi.data_final_tpi\n"
            + "left join \n"
            + "(	Select patient_id,min(data_inicio) data_inicio\n"
            + "		from\n"
            + "			(	\n"
            + "			\n"
            + "				Select 	p.patient_id,min(e.encounter_datetime) data_inicio\n"
            + "				from 	patient p \n"
            + "						inner join encounter e on p.patient_id=e.patient_id	\n"
            + "						inner join obs o on o.encounter_id=e.encounter_id\n"
            + "				where 	e.voided=0 and o.voided=0 and p.voided=0 and \n"
            + "						e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and \n"
            + "						e.encounter_datetime<=:endDate and e.location_id=:location\n"
            + "				group by p.patient_id\n"
            + "		\n"
            + "				union\n"
            + "		\n"
            + "				Select 	p.patient_id,min(value_datetime) data_inicio\n"
            + "				from 	patient p\n"
            + "						inner join encounter e on p.patient_id=e.patient_id\n"
            + "						inner join obs o on e.encounter_id=o.encounter_id\n"
            + "				where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and \n"
            + "						o.concept_id=1190 and o.value_datetime is not null and \n"
            + "						o.value_datetime<=:endDate and e.location_id=:location\n"
            + "				group by p.patient_id\n"
            + "\n"
            + "				union\n"
            + "\n"
            + "				select 	pg.patient_id,min(date_enrolled) data_inicio\n"
            + "				from 	patient p inner join patient_program pg on p.patient_id=pg.patient_id\n"
            + "				where 	pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endDate and location_id=:location\n"
            + "				group by pg.patient_id\n"
            + "				\n"
            + "				union\n"
            + "				\n"
            + "				  SELECT 	e.patient_id, MIN(e.encounter_datetime) AS data_inicio \n"
            + "				  FROM 		patient p\n"
            + "							inner join encounter e on p.patient_id=e.patient_id\n"
            + "				  WHERE		p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location\n"
            + "				  GROUP BY 	p.patient_id\n"
            + "			  \n"
            + "				union\n"
            + "				\n"
            + "				Select 	p.patient_id,min(value_datetime) data_inicio\n"
            + "				from 	patient p\n"
            + "						inner join encounter e on p.patient_id=e.patient_id\n"
            + "						inner join obs o on e.encounter_id=o.encounter_id\n"
            + "				where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and \n"
            + "						o.concept_id=23866 and o.value_datetime is not null and \n"
            + "						o.value_datetime<=:endDate and e.location_id=:location\n"
            + "				group by p.patient_id\n"
            + "				\n"
            + "			) inicio_real\n"
            + "		group by patient_id\n"
            + ")inicio_tarv on inicio_tpi.patient_id=inicio_tarv.patient_id\n"
            + "inner join \n"
            + "(	select  p.patient_id,max(encounter_datetime) ultimo_seguimento\n"
            + "	from	patient p\n"
            + "			inner join encounter e on p.patient_id=e.patient_id\n"
            + "	where 	e.voided=0 and p.voided=0 and e.encounter_datetime between :startDate and curdate() and\n"
            + "			e.encounter_type in (6,9) and e.location_id=:location\n"
            + "	group by p.patient_id\n"
            + ") seguimento on inicio_tpi.patient_id=seguimento.patient_id \n"
            + "left join obs on obs.person_id=seguimento.patient_id and obs.obs_datetime=seguimento.ultimo_seguimento and obs.voided=0 and obs.concept_id = 6122 and obs.location_id=:location\n"
            + "left join \n"
            + "(	select pid1.*\n"
            + "	from patient_identifier pid1\n"
            + "	inner join \n"
            + "		(\n"
            + "			select patient_id,min(patient_identifier_id) id \n"
            + "			from patient_identifier\n"
            + "			where voided=0\n"
            + "			group by patient_id\n"
            + "		) pid2\n"
            + "	where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id\n"
            + ") pid on pid.patient_id=inicio_tpi.patient_id\n"
            + "left join \n"
            + "(	select pn1.*\n"
            + "	from person_name pn1\n"
            + "	inner join \n"
            + "		(\n"
            + "			select person_id,min(person_name_id) id \n"
            + "			from person_name\n"
            + "			where voided=0\n"
            + "			group by person_id\n"
            + "		) pn2\n"
            + "	where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id\n"
            + ") pn on pn.person_id=inicio_tpi.patient_id\n"
            + ") tpi\n"
            + "group by patient_id";
  }
}
