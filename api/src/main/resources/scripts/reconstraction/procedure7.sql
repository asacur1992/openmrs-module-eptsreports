DROP PROCEDURE IF EXISTS `DataReconstruct`;
#
CREATE DEFINER=`root`@`localhost` PROCEDURE `DataReconstruct`()
    READS SQL DATA
BEGIN

CALL SWAPCONCEPTSANDDATA();
CALL FillTEMPLOCATION();
CALL DataRecontructMDS();
CALL DataRecontructTPTFichaClinica();
CALL DataRecontructTPTFichaResumo();
CALL DataRecontructTPTSeguimento();

END
#