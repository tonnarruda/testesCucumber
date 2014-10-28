CREATE OR REPLACE FUNCTION monta_familia_area(area_id BIGINT) RETURNS TABLE(area_nome TEXT) AS $$ 
DECLARE 
BEGIN 
    RETURN QUERY  
    WITH RECURSIVE areaorganizacional_recursiva AS ( 
        SELECT id, nome, areamae_id, CAST(nome AS TEXT) AS nomeHierarquico 
        FROM areaorganizacional WHERE id = area_id 
        UNION ALL 
        SELECT ao.id, ao.nome, ao.areamae_id, CAST((ao.nome || ' > ' || ao_r.nomeHierarquico) AS TEXT) AS nomeHierarquico 
        FROM areaorganizacional ao 
        INNER JOIN areaorganizacional_recursiva ao_r ON ao.id = ao_r.areamae_id 
    )
    SELECT nomeHierarquico FROM areaorganizacional_recursiva ORDER BY LENGTH(nomehierarquico) DESC LIMIT 1; 
END; 
$$ LANGUAGE plpgsql; --.go