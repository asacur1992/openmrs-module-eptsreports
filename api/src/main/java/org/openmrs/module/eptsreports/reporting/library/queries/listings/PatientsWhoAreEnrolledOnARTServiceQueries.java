/** */
package org.openmrs.module.eptsreports.reporting.library.queries.listings;

/** @author Stélio Moiane */
public interface PatientsWhoAreEnrolledOnARTServiceQueries {

  class QUERY {
    public static final String findPatientsWhoAreEnrolledOnARTService =
        "select *\n"
            + "from\n"
            + "	(select 	inscricao.patient_id,\n"
            + "			inscricao.data_abertura,\n"
            + "			pe.gender,\n"
            + "			pe.dead,\n"
            + "			pe.death_date,\n"
            + "			timestampdiff(year,pe.birthdate,inscricao.data_abertura) idade_abertura,\n"
            + "			timestampdiff(year,pe.birthdate,:endDate) idade_actual,			\n"
            + "			pad3.county_district as 'Distrito',\n"
            + "			pad3.address2 as 'PAdministrativo',\n"
            + "			pad3.address6 as 'Localidade',\n"
            + "			pad3.address5 as 'Bairro',\n"
            + "			pad3.address1 as 'PontoReferencia',\n"
            + "			concat(ifnull(pn.given_name,''),' ',ifnull(pn.middle_name,''),' ',ifnull(pn.family_name,'')) as 'NomeCompleto',			\n"
            + "			pid.identifier as NID,\n"
            + "			transferido.data_transferido_de,\n"
            + "			if(transferido.program_id is null,null,if(transferido.program_id=1,'PRE-TARV','TARV')) as transferido_de,\n"
            + "			inicio_real.data_inicio,\n"
            + "			estadio.data_estadio,\n"
            + "			estadio.valor_estadio,\n"
            + "			seguimento.data_seguimento,\n"
            + "			if(inscrito_cuidado.date_enrolled is null,'NAO','SIM') inscrito_programa,\n"
            + "			inscrito_cuidado.date_enrolled data_inscricao_programa,\n"
            + "			proveniencia.referencia,\n"
            + "			pat.value as telefone,\n"
            + "			contacto.data_aceita,\n"
            + "			diagnostico.data_diagnostico\n"
            + "			\n"
            + "			\n"
            + "	from		\n"
            + "			(				\n"
            + "				select patient_id,min(data_abertura) data_abertura\n"
            + "				from \n"
            + "				(\n"
            + "					select 	p.patient_id,min(e.encounter_datetime) data_abertura \n"
            + "					from 	patient p \n"
            + "							inner join encounter e on e.patient_id=p.patient_id \n"
            + "					where 	e.voided=0 and p.voided=0 and e.encounter_type in (5,7) and \n"
            + "							e.encounter_datetime<=:endDate and e.location_id = :location\n"
            + "					group by p.patient_id\n"
            + "\n"
            + "					union\n"
            + "\n"
            + "					select 	pg.patient_id,min(date_enrolled) data_abertura\n"
            + "					from 	patient p \n"
            + "							inner join patient_program pg on p.patient_id=pg.patient_id\n"
            + "					where 	pg.voided=0 and p.voided=0 and program_id=1 and date_enrolled<=:endDate and location_id=:location\n"
            + "					group by p.patient_id\n"
            + "\n"
            + "					union\n"
            + "\n"
            + "					Select 	p.patient_id,min(o.value_datetime) data_abertura\n"
            + "					from 	patient p\n"
            + "							inner join encounter e on p.patient_id=e.patient_id\n"
            + "							inner join obs o on e.encounter_id=o.encounter_id\n"
            + "					where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=53 and \n"
            + "							o.concept_id=23891 and o.value_datetime is not null and \n"
            + "							o.value_datetime<=:endDate and e.location_id=:location\n"
            + "					group by p.patient_id\n"
            + "				) allInscrito\n"
            + "				group by patient_id				\n"
            + "			) inscricao\n"
            + "			inner join person pe on pe.person_id=inscricao.patient_id and inscricao.data_abertura between :startDate and :endDate\n"
            + "			left join \n"
            + "			(	select pad1.*\n"
            + "				from person_address pad1\n"
            + "				inner join \n"
            + "				(\n"
            + "					select person_id,min(person_address_id) id \n"
            + "					from person_address\n"
            + "					where voided=0\n"
            + "					group by person_id\n"
            + "				) pad2\n"
            + "				where pad1.person_id=pad2.person_id and pad1.person_address_id=pad2.id\n"
            + "			) pad3 on pad3.person_id=inscricao.patient_id				\n"
            + "			left join 			\n"
            + "			(	select pn1.*\n"
            + "				from person_name pn1\n"
            + "				inner join \n"
            + "				(\n"
            + "					select person_id,min(person_name_id) id \n"
            + "					from person_name\n"
            + "					where voided=0\n"
            + "					group by person_id\n"
            + "				) pn2\n"
            + "				where pn1.person_id=pn2.person_id and pn1.person_name_id=pn2.id\n"
            + "			) pn on pn.person_id=inscricao.patient_id			\n"
            + "			left join\n"
            + "			(       select pid1.*\n"
            + "					from patient_identifier pid1\n"
            + "					inner join\n"
            + "									(\n"
            + "													select patient_id,min(patient_identifier_id) id\n"
            + "													from patient_identifier\n"
            + "													where voided=0\n"
            + "													group by patient_id\n"
            + "									) pid2\n"
            + "					where pid1.patient_id=pid2.patient_id and pid1.patient_identifier_id=pid2.id\n"
            + "			) pid on pid.patient_id=inscricao.patient_id\n"
            + "			left join \n"
            + "			(	\n"
            + "				select patient_id,max(data_transferido_de) data_transferido_de,program_id\n"
            + "				from \n"
            + "					(\n"
            + "						select 	pg.patient_id,max(ps.start_date) data_transferido_de,pg.program_id\n"
            + "						from 	patient p \n"
            + "								inner join patient_program pg on p.patient_id=pg.patient_id\n"
            + "								inner join patient_state ps on pg.patient_program_id=ps.patient_program_id\n"
            + "						where 	pg.voided=0 and ps.voided=0 and p.voided=0 and \n"
            + "								pg.program_id in (1,2) and ps.state in (28,29) and \n"
            + "								ps.start_date between :startDate and :endDate and location_id=:location	\n"
            + "						group by pg.patient_id\n"
            + "						\n"
            + "						union\n"
            + "						\n"
            + "						Select 	p.patient_id,max(obsRegisto.value_datetime) data_transferido_de,if(obsTarv.value_coded=6276,2,1) program_id\n"
            + "						from 	patient p\n"
            + "								inner join encounter e on p.patient_id=e.patient_id\n"
            + "								inner join obs obsTrans on e.encounter_id=obsTrans.encounter_id and obsTrans.voided=0 and obsTrans.concept_id=1369 and obsTrans.value_coded=1065\n"
            + "								inner join obs obsTarv on e.encounter_id=obsTarv.encounter_id and obsTarv.voided=0 and obsTarv.concept_id=6300 and obsTarv.value_coded in (6276,6275) \n"
            + "								inner join obs obsRegisto on e.encounter_id=obsRegisto.encounter_id and obsRegisto.voided=0 and obsRegisto.concept_id=23891\n"
            + "						where 	p.voided=0 and e.voided=0 and e.encounter_type=53 and  \n"
            + "								obsRegisto.value_datetime between :startDate and :endDate and e.location_id=:location\n"
            + "					) maxTransferido\n"
            + "				group by patient_id			\n"
            + "			) transferido on transferido.patient_id=inscricao.patient_id and transferido.data_transferido_de<=inscricao.data_abertura\n"
            + "			left join 		\n"
            + "			(		Select patient_id,min(data_inicio) data_inicio\n"
            + "					from\n"
            + "					(	\n"
            + "					\n"
            + "						/*Patients on ART who initiated the ARV DRUGS: ART Regimen Start Date*/\n"
            + "				\n"
            + "						Select 	p.patient_id,min(e.encounter_datetime) data_inicio\n"
            + "						from 	patient p \n"
            + "								inner join encounter e on p.patient_id=e.patient_id	\n"
            + "								inner join obs o on o.encounter_id=e.encounter_id\n"
            + "						where 	e.voided=0 and o.voided=0 and p.voided=0 and \n"
            + "								e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and \n"
            + "								e.encounter_datetime<=:endDate and e.location_id=:location\n"
            + "						group by p.patient_id\n"
            + "				\n"
            + "						union\n"
            + "				\n"
            + "						/*Patients on ART who have art start date: ART Start date*/\n"
            + "						Select 	p.patient_id,min(value_datetime) data_inicio\n"
            + "						from 	patient p\n"
            + "								inner join encounter e on p.patient_id=e.patient_id\n"
            + "								inner join obs o on e.encounter_id=o.encounter_id\n"
            + "						where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and \n"
            + "								o.concept_id=1190 and o.value_datetime is not null and \n"
            + "								o.value_datetime<=:endDate and e.location_id=:location\n"
            + "						group by p.patient_id\n"
            + "\n"
            + "						union\n"
            + "\n"
            + "						/*Patients enrolled in ART Program: OpenMRS Program*/\n"
            + "						select 	pg.patient_id,min(date_enrolled) data_inicio\n"
            + "						from 	patient p inner join patient_program pg on p.patient_id=pg.patient_id\n"
            + "						where 	pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endDate and location_id=:location\n"
            + "						group by pg.patient_id\n"
            + "						\n"
            + "						union\n"
            + "						\n"
            + "						\n"
            + "						/*Patients with first drugs pick up date set in Pharmacy: First ART Start Date*/\n"
            + "						  SELECT 	e.patient_id, MIN(e.encounter_datetime) AS data_inicio \n"
            + "						  FROM 		patient p\n"
            + "									inner join encounter e on p.patient_id=e.patient_id\n"
            + "						  WHERE		p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endDate and e.location_id=:location\n"
            + "						  GROUP BY 	p.patient_id\n"
            + "					  \n"
            + "						union\n"
            + "						\n"
            + "						/*Patients with first drugs pick up date set: Recepcao Levantou ARV*/\n"
            + "						Select 	p.patient_id,min(value_datetime) data_inicio\n"
            + "						from 	patient p\n"
            + "								inner join encounter e on p.patient_id=e.patient_id\n"
            + "								inner join obs o on e.encounter_id=o.encounter_id\n"
            + "						where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and \n"
            + "								o.concept_id=23866 and o.value_datetime is not null and \n"
            + "								o.value_datetime<=:endDate and e.location_id=:location\n"
            + "						group by p.patient_id				\n"
            + "						\n"
            + "						\n"
            + "					) inicio\n"
            + "				group by patient_id				\n"
            + "			) inicio_real on inscricao.patient_id=inicio_real.patient_id		\n"
            + "			left join\n"
            + "			(	select 	o.person_id patient_id,o.obs_datetime data_estadio,\n"
            + "						case o.value_coded\n"
            + "						when 1204 then 'I'\n"
            + "						when 1205 then 'II'\n"
            + "						when 1206 then 'III'\n"
            + "						when 1207 then 'IV'\n"
            + "						else 'OUTRO' end as valor_estadio\n"
            + "				from 	obs o,				\n"
            + "						(	select 	p.patient_id,min(encounter_datetime) as encounter_datetime\n"
            + "							from 	patient p\n"
            + "									inner join encounter e on p.patient_id=e.patient_id\n"
            + "									inner join obs o on o.encounter_id=e.encounter_id\n"
            + "							where 	encounter_type in (6,9) and e.voided=0 and\n"
            + "									encounter_datetime between :startDate and :endDate and e.location_id=:location\n"
            + "									and p.voided=0 and o.voided=0 and o.concept_id=5356\n"
            + "							group by patient_id\n"
            + "						) d\n"
            + "				where 	o.person_id=d.patient_id and o.obs_datetime=d.encounter_datetime and o.voided=0 and \n"
            + "						o.concept_id=5356 and o.location_id=:location and o.value_coded in (1204,1205,1206,1207)\n"
            + "				group by d.patient_id\n"
            + "			)estadio on estadio.patient_id=inscricao.patient_id\n"
            + "			left join \n"
            + "			(	select patient_id,min(encounter_datetime) data_seguimento\n"
            + "				from encounter\n"
            + "				where voided=0 and encounter_type in (6,9) and encounter_datetime between :startDate and :endDate\n"
            + "				group by patient_id\n"
            + "			) seguimento on seguimento.patient_id=inscricao.patient_id\n"
            + "			left join\n"
            + "			(\n"
            + "				select 	pg.patient_id,date_enrolled\n"
            + "				from 	patient p inner join patient_program pg on p.patient_id=pg.patient_id\n"
            + "				where 	pg.voided=0 and p.voided=0 and program_id=1 and date_enrolled between :startDate and :endDate and location_id=:location\n"
            + "			) inscrito_cuidado on inscrito_cuidado.patient_id=inscricao.patient_id		\n"
            + "			left join\n"
            + "			(	select 	p.patient_id,\n"
            + "						case o.value_coded\n"
            + "						when 1595 then 'INTERNAMENTO'\n"
            + "						when 1596 then 'CONSULTA EXTERNA'\n"
            + "						when 1414 then 'PNCT'\n"
            + "						when 1597 then 'ATS'\n"
            + "						when 1987 then 'SAAJ'\n"
            + "						when 1598 then 'PTV'\n"
            + "						when 1872 then 'CCR'\n"
            + "						when 1275 then 'CENTRO DE SAUDE'\n"
            + "						when 1984 then 'HR'\n"
            + "						when 1599 then 'PROVEDOR PRIVADO'\n"
            + "						when 1932 then 'PROFISSIONAL DE SAUDE'\n"
            + "						when 1387 then 'LABORATÓRIO'\n"
            + "						when 1386 then 'CLINICA MOVEL'\n"
            + "						when 6245 then 'ATSC'\n"
            + "						when 1699 then 'CUIDADOS DOMICILIARIOS'\n"
            + "						when 2160 then 'VISITA DE BUSCA'\n"
            + "						when 1985 then 'CPN'\n"
            + "						when 6288 then 'SMI'\n"
            + "						when 5484 then 'APOIO NUTRICIONAL'\n"
            + "						when 6155 then 'MEDICO TRADICIONAL'\n"
            + "						when 1044 then 'PEDIATRIA'\n"
            + "						when 6303 then 'VGB'\n"
            + "						when 6304 then 'ATIP'\n"
            + "						when 6305 then 'OBC'\n"
            + "						when 21275 then 'CLÍNICA'\n"
            + "						else 'OUTRO' end as referencia\n"
            + "				from 	patient p\n"
            + "						inner join encounter e on p.patient_id=e.patient_id\n"
            + "						inner join obs o on o.encounter_id=e.encounter_id\n"
            + "				where 	encounter_type in (5,7,53) and e.voided=0 and\n"
            + "						encounter_datetime between :startDate and :endDate and e.location_id=:location\n"
            + "						and p.voided=0 and o.voided=0 and o.concept_id in (23783,1594)					\n"
            + "			)proveniencia on proveniencia.patient_id=inscricao.patient_id\n"
            + "			left join person_attribute pat on pat.person_id=inscricao.patient_id and pat.person_attribute_type_id=9 and pat.value is not null and pat.value<>'' and pat.voided=0\n"
            + "			left join\n"
            + "			(	select 	p.patient_id,min(encounter_datetime) as data_aceita\n"
            + "				from 	patient p\n"
            + "						inner join encounter e on p.patient_id=e.patient_id\n"
            + "						inner join obs o on o.encounter_id=e.encounter_id\n"
            + "				where 	encounter_type in (34,35) and e.voided=0 and\n"
            + "						encounter_datetime<=:endDate and e.location_id=:location\n"
            + "						and p.voided=0 and o.voided=0 and o.concept_id=6309 and o.value_coded=6307\n"
            + "				group by patient_id\n"
            + "			) contacto on contacto.patient_id=inscricao.patient_id\n"
            + "			left join\n"
            + "			(	\n"
            + "				select patient_id,min(data_diagnostico) data_diagnostico\n"
            + "				from \n"
            + "				(\n"
            + "					select 	p.patient_id,min(o.value_datetime) as data_diagnostico\n"
            + "					from 	patient p\n"
            + "							inner join encounter e on p.patient_id=e.patient_id\n"
            + "							inner join obs o on o.encounter_id=e.encounter_id\n"
            + "					where 	encounter_type in (5,7) and e.voided=0 and o.value_datetime<=:endDate and e.location_id=:location\n"
            + "							and p.voided=0 and o.voided=0 and o.concept_id=6123\n"
            + "					group by patient_id\n"
            + "					\n"
            + "					union\n"
            + "					\n"
            + "					Select 	p.patient_id,min(o.value_datetime) data_diagnostico\n"
            + "					from 	patient p\n"
            + "							inner join encounter e on p.patient_id=e.patient_id\n"
            + "							inner join obs o on e.encounter_id=o.encounter_id\n"
            + "					where 	p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=53 and \n"
            + "							o.concept_id=22772 and o.value_datetime is not null and \n"
            + "							o.value_datetime<=:endDate and e.location_id=:location\n"
            + "					group by p.patient_id\n"
            + "				) diag\n"
            + "				group by patient_id				\n"
            + "			) diagnostico on diagnostico.patient_id=inscricao.patient_id\n"
            + "	)inscritos\n"
            + "group by patient_id";
  }
}
