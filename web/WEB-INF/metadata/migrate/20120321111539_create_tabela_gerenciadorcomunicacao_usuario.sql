CREATE TABLE gerenciadorcomunicacao_usuario (
    gerenciadorcomunicacao_id bigint NOT NULL,
    usuarios_id bigint NOT NULL
);--go
ALTER TABLE ONLY gerenciadorcomunicacao_usuario ADD CONSTRAINT gerenciadorcomunicacao_usuario_gerenciadorcomunicacao_fk FOREIGN KEY (gerenciadorcomunicacao_id) REFERENCES gerenciadorcomunicacao(id);--go
ALTER TABLE ONLY gerenciadorcomunicacao_usuario ADD CONSTRAINT gerenciadorcomunicacao_usuario_usuario_fk FOREIGN KEY (usuarios_id) REFERENCES usuario(id);--go