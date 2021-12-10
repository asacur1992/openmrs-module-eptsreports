
/*Linha responsável por criar a Função de Visualização no OpenMRS e detalhar as funções do utilizador que terá essa função*/;
INSERT INTO `role` VALUES ('Visualização','O visualizador tem privilégios de executar relatórios, visualizar os dados e nunca os alterar.','91c66f65-7a2e-436b-909a-5a44cd0a4ed7');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


/*Linha responsável por atribuir os privilégios do utilizador que será atribuído a função de Visualizador*/;
INSERT INTO `role_privilege` VALUES ('Visualização','Get Forms'),('Visualização','Get Identifier Types'),('Visualização','Get Locations'),('Visualização','Get Patient Programs'),('Visualização','Get Programs'),('Visualização','Get Users'),('Visualização','Get Visits'),('Visualização','Patient Dashboard - View Demographics Section'),('Visualização','Patient Dashboard - View Graphs Section'),('Visualização','Patient Dashboard - View Overview Section'),('Visualização','Patient Dashboard - View Regimen Section'),('Visualização','Patient Dashboard - View Visits Section'),('Visualização','Patient Overview - View Programs'),('Visualização','Provider Management Dashboard - View Historical'),('Visualização','Provider Management Dashboard - View Patients'),('Visualização','Run Reports'),('Visualização','View Reports'),('Visualização','View Roles'),('Visualização','View Visits');


/*Esta Linha indica que o visualizador herda todos privilégios que o Provider possui*/;
INSERT INTO `role_role` VALUES ('Provider','Visualização');
