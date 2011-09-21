INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (522, 'ROLE_INFORM_CANDIDATO', 'Informações do Candidato', '--', 15, false, 357); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (523, 'ROLE_INFORM_CANDIDATO_CURRICULO', 'Visualizar Currículo', '--', 1, false, 522); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (524, 'ROLE_INFORM_CANDIDATO_HISTORICO', 'Visualizar Histórico', '--', 2, false, 522); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (525, 'ROLE_INFORM_CANDIDATO_COMPETENCIA', 'Visualizar Competência', '--', 3, false, 522); --.go
alter sequence papel_sequence restart with 526;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=522 WHERE atualizaPapeisIdsAPartirDe is null;--.go

CREATE FUNCTION ajusta_perfil_papel_faixa() RETURNS integer AS '
DECLARE
	mpapeis RECORD;
BEGIN
    FOR mpapeis IN
	select id as papeisId from papel where id in (522,523,524,525)
	    LOOP
		DECLARE
		    mviews RECORD;
		BEGIN
		    FOR mviews IN
				select distinct perfil_id as perfilId from perfil_papel where (papeis_id = 2 or papeis_id = 496) order by perfil_id
				LOOP
				  EXECUTE ''insert into perfil_papel(perfil_id, papeis_id) values(''|| mviews.perfilId ||'', ''|| mpapeis.papeisId ||'')'';
				END LOOP;
		END;
	     END LOOP;
RETURN 1;
END;
' LANGUAGE plpgsql;--.go
select ajusta_perfil_papel_faixa();--.go
drop function ajusta_perfil_papel_faixa();--.go
