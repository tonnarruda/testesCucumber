ALTER TABLE colaboradorquestionario ADD COLUMN configuracaonivelcompetenciacolaborador_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_cnccolaborador_fk FOREIGN KEY (configuracaonivelcompetenciacolaborador_id) REFERENCES configuracaonivelcompetenciacolaborador(id);--.go
update colaboradorquestionario cq set configuracaonivelcompetenciacolaborador_id = (select cncc.id from configuracaonivelcompetenciacolaborador cncc where cncc.avaliador_id = cq.avaliador_id and cncc.colaboradorquestionario_id = cq.id and cncc.data = cq.respondidaem);--.go