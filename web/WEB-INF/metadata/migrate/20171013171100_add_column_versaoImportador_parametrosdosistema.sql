alter table parametrosdosistema add column versaoImportador character varying(10); --.go
update parametrosdosistema SET versaoImportador='1.64.0'; --.go