CREATE FUNCTION ajusta_ordem_solicitacao_exame() RETURNS integer AS '
DECLARE
    rec RECORD;
    dat DATE;
    ord INTEGER;
BEGIN
    ord := 0;

    FOR rec IN select * from solicitacaoexame order by data, id
    LOOP
		IF dat <> rec.data THEN ord := 0; END IF;
		update solicitacaoexame set ordem = (ord + 1) where id = rec.id;
		dat := rec.data;
		ord := ord + 1;
    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql; --.go

select ajusta_ordem_solicitacao_exame(); --.go
drop function ajusta_ordem_solicitacao_exame(); --.go