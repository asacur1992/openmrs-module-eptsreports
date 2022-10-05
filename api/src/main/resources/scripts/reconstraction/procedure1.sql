DROP PROCEDURE IF EXISTS `FillTEMPLOCATION`;
#
CREATE DEFINER=`root`@`localhost` PROCEDURE `FillTEMPLOCATION`()
    READS SQL DATA
BEGIN
    DECLARE eptsLocations varchar(200);

    truncate table temp_location;

    select property_value into eptsLocations  
    from global_property 
    where property='eptsreports.datareconstructionlocationids';

    WHILE LOCATE(',',eptsLocations) > 0 DO
        INSERT INTO temp_location(location_id) SELECT SUBSTRING_INDEX(eptsLocations,',',1);
        SET eptsLocations = REPLACE (eptsLocations, (SELECT LEFT(eptsLocations,LOCATE(',',eptsLocations))),'');
    END WHILE;
    IF eptsLocations <> '' THEN
        INSERT INTO temp_location(location_id) VALUES(eptsLocations);
    END IF;

END
#