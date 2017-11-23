ALTER TABLE medicocoordenador ADD COLUMN estabelecimentoResponsavel CHARACTER VARYING(6) DEFAULT 'TODOS';--.go

CREATE TABLE medicocoordenador_estabelecimento (
    medicocoordenador_id bigint NOT NULL,
    estabelecimentos_id bigint NOT NULL
);--.go

CREATE TABLE engenheiroresponsavel_estabelecimento (
    engenheiroresponsavel_id bigint NOT NULL,
    estabelecimentos_id bigint NOT NULL
);--.go
