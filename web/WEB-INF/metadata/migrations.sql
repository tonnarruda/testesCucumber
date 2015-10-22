
alter table certificacao add column periodicidade integer;--.go


--Verificar id do papel quando for criar a migrate
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (646,'ROLE_AVALIACAO_PRATICA', 'Avaliação Prática', '/avaliacao/avaliacaoPratica/list.action', 8, true, 366);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 646);--.go
alter sequence papel_sequence restart with 647;--.go

CREATE TABLE avaliacaoPratica (
id bigint NOT NULL,
titulo character varying(100),
notaMinima double precision,
empresa_id bigint
);--.go

ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_pkey PRIMARY KEY(id);--.go
ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE avaliacaoPratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE certificacao_avaliacaopratica (
	certificacao_id bigint NOT NULL,
	avaliacoesPraticas_id bigint NOT NULL
); --.go

ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_certificacao_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_avaliacaoPraticas_fk FOREIGN KEY (avaliacoespraticas_id) REFERENCES avaliacaopratica(id);--.go
CREATE SEQUENCE certificacao_avaliacaopratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go


//consulta
WITH cursoCertificados AS (
		select distinct cc.cursos_id as cusosIds from certificacao  c
		inner join certificacao_avaliacaopratica cap on cap.certificacao_id = c.id
		inner join certificacao_curso cc on cc.certificacaos_id = c.id
		where c.id =  4295 and cap.avaliacoespraticas_id = 4	
		)

select col.id, col.nome from colaboradorturma  ct
inner join colaborador col on col.id = ct.colaborador_id
inner join turma t on t.id = ct.turma_id
inner join cursoCertificados on ct.curso_id = cursoCertificados.cusosIds
where dataprevfim between '01/01/2009' and '30/10/2015'
group by col.id, col.nome
having count(ct.curso_id) = (select count(*) from cursoCertificados)
order by col.id, col.nome


