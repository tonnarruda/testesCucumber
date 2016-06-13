
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (673,'ROLE_MOV_ORDEM_DE_SERVICO', 'Ordem de Serviço(OS)', '/sesmt/ordemDeServico/listColaboradores.action', 11, true, 386);--.go
alter sequence papel_sequence restart with 674;--.go


CREATE TABLE ordemDeServico (
id bigint NOT NULL,
descricao character varying(100),
numeroCilindro int,
ativo boolean,
empresa_id bigint
);--.go

ALTER TABLE ordemDeServico ADD CONSTRAINT ordemDeServico_pkey PRIMARY KEY(id);--.go
ALTER TABLE ordemDeServico ADD CONSTRAINT ordemDeServico_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE ordemDeServico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
