CREATE OR REPLACE FUNCTION atualizar_historico_portal() RETURNS TRIGGER AS $$ 
    DECLARE 
        linha RECORD; 
    BEGIN 
        IF (TG_OP = 'INSERT') THEN 
            linha := NEW; 
        ELSE  
            linha := OLD; 
        END IF; 

        IF (TG_TABLE_NAME = 'historicocolaborador') THEN 
            UPDATE colaborador SET atualizarhistoricoportal = true WHERE id = linha.colaborador_id; 

        ELSIF (TG_TABLE_NAME = 'faixasalarialhistorico') THEN 
            UPDATE colaborador SET atualizarhistoricoportal = true WHERE id IN ( 
                SELECT DISTINCT c.id FROM colaborador c 
                INNER JOIN historicocolaborador hc ON hc.colaborador_id = c.id  
                WHERE hc.tiposalario = 3 
                AND hc.faixasalarial_id = linha.faixasalarial_id 
            );

        ELSIF (TG_TABLE_NAME = 'indicehistorico') THEN 
            UPDATE colaborador SET atualizarhistoricoportal = true WHERE id IN ( 
                SELECT DISTINCT c.id FROM colaborador c 
                INNER JOIN historicocolaborador hc ON hc.colaborador_id = c.id  
                WHERE hc.tiposalario = 2 
                AND hc.indice_id = linha.indice_id 
            );
        END IF;
        RETURN NULL;
    END;
    $$ LANGUAGE plpgsql;--.go

DROP TRIGGER IF EXISTS tg_atualizar_historico_portal ON historicocolaborador;--.go

CREATE TRIGGER tg_atualizar_historico_portal 
  AFTER INSERT OR UPDATE OR DELETE ON historicocolaborador 
    FOR EACH ROW EXECUTE PROCEDURE atualizar_historico_portal();--.go

DROP TRIGGER IF EXISTS tg_atualizar_historico_portal ON faixasalarialhistorico;--.go

CREATE TRIGGER tg_atualizar_historico_portal 
  AFTER INSERT OR UPDATE OR DELETE ON faixasalarialhistorico 
    FOR EACH ROW EXECUTE PROCEDURE atualizar_historico_portal();--.go

DROP TRIGGER IF EXISTS tg_atualizar_historico_portal ON indicehistorico;--.go

CREATE TRIGGER tg_atualizar_historico_portal 
  AFTER INSERT OR UPDATE OR DELETE ON indicehistorico 
    FOR EACH ROW EXECUTE PROCEDURE atualizar_historico_portal();--.go