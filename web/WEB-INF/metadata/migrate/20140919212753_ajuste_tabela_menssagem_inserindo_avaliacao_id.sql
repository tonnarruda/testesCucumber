CREATE FUNCTION atualiza_mensagem_com_avaliacaoId() RETURNS integer AS $$
	DECLARE
	    MV RECORD;
	    AVALIACAOID integer;
	BEGIN
	    FOR MV IN
			select id, link from mensagem  where link ilike '%colaboradorQuestionario.avaliacao.id=%'
			LOOP
				AVALIACAOID := (select cast((SELECT regexp_replace(MV.link,'(.*)(colaboradorQuestionario.avaliacao.id=)', '')) as integer));
				iF exists(select id from avaliacao where id = AVALIACAOID) THEN
					update mensagem set avaliacao_id = AVALIACAOID where id = MV.id;
				ELSE
					delete from usuariomensagem where mensagem_id = MV.id;	
					delete from mensagem where id = MV.id;	
				END IF;
			END LOOP;
	    RETURN 1;
	END;
$$ LANGUAGE plpgsql;--.go
select atualiza_mensagem_com_avaliacaoId();--.go
drop function atualiza_mensagem_com_avaliacaoId();--.go