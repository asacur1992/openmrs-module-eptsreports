update concept set class_id = 11 where concept_id = 165217;
insert into concept_numeric (concept_id,hi_normal,low_normal,allow_decimal) values (165217,120.0,0.0,false) on duplicate key  update  concept_id = 165217;
update concept set datatype_id = 3 where concept_id in (165297, 165298, 165299);
