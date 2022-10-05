DROP PROCEDURE IF EXISTS `SWAPCONCEPTSANDDATA`;
/
CREATE DEFINER=`root`@`localhost` PROCEDURE `SWAPCONCEPTSANDDATA`()
    READS SQL DATA
BEGIN

DECLARE uuidFound varchar(200);
DECLARE concept165325UUID varchar(200);

SET concept165325UUID = 'b856b79b-2e8e-4764-ae8b-c8b509cdda76';

BEGIN
    
    select uuid into uuidFound from concept where concept_id = 165325;

    IF uuidFound <> concept165325UUID THEN
        insert into concept (concept_id,datatype_id,class_id,creator,date_created,uuid) VALUES (200000,4,4,1,now(),uuid());

        update concept_name set concept_id = 200000 where concept_id = 165324;
        update concept_name set concept_id = 165324 where concept_id = 165322;
        update concept_name set concept_id = 165322 where concept_id = 200000;

        update concept_description set concept_id = 200000 where concept_id = 165324;
        update concept_description set concept_id = 165324 where concept_id = 165322;   
        update concept_description set concept_id = 165322 where concept_id = 200000;

        update concept_answer set concept_id = 200000 where concept_id = 165324;
        update concept_answer set concept_id = 165324 where concept_id = 165322;    
        update concept_answer set concept_id = 165322 where concept_id = 200000;

        update concept_set set concept_id = 165322 where concept_id =165324;

        update concept set uuid = 'fef178f2-d4c9-4035-9989-11c9afe81ea3#' where concept_id =165324;
        update concept set uuid = 'fef178f2-d4c9-4035-9989-11c9afe81ea3' where concept_id =165322;
        update concept set uuid = '4387180e-695f-4c99-8182-33e51907062a' where concept_id =165324;  

        update concept_name set concept_id = 200000 where concept_id = 165325;
        update concept_name set concept_id = 165325 where concept_id = 165323;
        update concept_name set concept_id = 165323 where concept_id = 200000;

        update concept_description set concept_id = 200000 where concept_id = 165325;
        update concept_description set concept_id = 165325 where concept_id = 165323;   
        update concept_description set concept_id = 165323 where concept_id = 200000;

        update concept_answer set concept_id = 165325 where concept_id = 165323;

        update concept_set set concept_set = 165323 where concept_set =165325;
        update concept_set set concept_id = 165322 where concept_id =165324;

        update concept set uuid = 'bebcfbe3-bb5b-4c5c-a41e-808fc4457fc3#' where concept_id =165325;
        update concept set uuid = 'bebcfbe3-bb5b-4c5c-a41e-808fc4457fc3' where concept_id =165323;
        update concept set uuid = 'b856b79b-2e8e-4764-ae8b-c8b509cdda76' where concept_id =165325;  

        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165322;
        update concept set class_id =10, datatype_id =4, is_set =1 where concept_id =165323;
        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165324;
        update concept set class_id =7, datatype_id =2, is_set =0 where concept_id =165325;

        update obs set concept_id = 200000 where concept_id = 165324;
        update obs set concept_id = 165324 where concept_id = 165322;
        update obs set concept_id = 165322 where concept_id = 200000;

        update obs set concept_id = 200000 where concept_id = 165325;
        update obs set concept_id = 165325 where concept_id = 165323;
        update obs set concept_id = 165323 where concept_id = 200000;

        update obs set value_coded = 200000 where value_coded = 165324;
        update obs set value_coded = 165324 where value_coded = 165322;
        update obs set value_coded = 165322 where value_coded = 200000;

        update obs set value_coded = 200000 where value_coded = 165325;
        update obs set value_coded = 165325 where value_coded = 165323;
        update obs set value_coded = 165323 where value_coded = 200000;

        delete from concept where concept_id = 200000;
    
    END IF;
END;

END
/