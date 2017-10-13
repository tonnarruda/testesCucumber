ALTER TABLE funcao add COLUMN codigocbo character varying(6);--.go
ALTER TABLE funcao ADD COLUMN codigoac character varying(12);--.go
ALTER TABLE funcao ADD CONSTRAINT unique_empresaId_codigoac UNIQUE(empresa_id,codigoac);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (800, 'ROLE_RELACIONADOR_FUNCAO_FP', 'Relacionar Função com Fortes Pessoal', '/sesmt/funcao/prepareRelacionarFuncaoFP.action', 19, true, 37);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 800);--.go

ALTER SEQUENCE papel_sequence RESTART WITH 801;--.go
alter table parametrosdosistema add column versaoImportador character varying(20); --.go
update parametrosdosistema SET versaoImportador='1.64.0'; --.go
