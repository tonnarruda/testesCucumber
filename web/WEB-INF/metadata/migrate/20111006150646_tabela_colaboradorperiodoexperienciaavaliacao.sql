CREATE TABLE colaboradorperiodoexperienciaavaliacao (
    id bigint NOT NULL,
    colaborador_id bigint,
    periodoexperiencia_id bigint,	
    avaliacao_id bigint
);--.go
ALTER TABLE colaboradorperiodoexperienciaavaliacao ADD CONSTRAINT colaboradorperiodoexperienciaavaliacao_pkey PRIMARY KEY (id);--.go
ALTER TABLE colaboradorperiodoexperienciaavaliacao ADD CONSTRAINT colaboradorperiodoexperienciaavaliacao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE colaboradorperiodoexperienciaavaliacao ADD CONSTRAINT colaboradorperiodoexperienciaavaliacao_periodoexperiencia_fk FOREIGN KEY (periodoexperiencia_id) REFERENCES periodoexperiencia(id);--.go
ALTER TABLE colaboradorperiodoexperienciaavaliacao ADD CONSTRAINT colaboradorperiodoexperienciaavaliacao_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go
CREATE SEQUENCE colaboradorperiodoexperienciaavaliacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go