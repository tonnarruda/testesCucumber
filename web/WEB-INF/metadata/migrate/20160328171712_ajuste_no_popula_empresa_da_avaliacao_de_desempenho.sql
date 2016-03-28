CREATE FUNCTION insere_empresa_em_avaliacaodesempenho() RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select id, empresa_id, exiberesultadoautoavaliacao from avaliacao
	LOOP
		update avaliacaodesempenho set empresa_id = mv.empresa_id, exiberesultadoautoavaliacao = mv.exiberesultadoautoavaliacao where avaliacao_id = mv.id;  	
	END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go

SELECT insere_empresa_em_avaliacaodesempenho();--.go
DROP FUNCTION insere_empresa_em_avaliacaodesempenho();--.go