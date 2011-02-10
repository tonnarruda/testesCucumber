update parametrosdosistema set appversao = '1.1.38.29';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (498, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Relatorio de investimento de T&D', '/desenvolvimento/turma/relatorioInvestimento.action', 12, true, 368);--.go
alter sequence papel_sequence restart with 499; --.go

update papel set nome = 'Acompanhamento do Período de Experiência e Avaliação de Desempenho' where id = 490;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=498 WHERE atualizaPapeisIdsAPartirDe is null;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (499, 'ROLE_CAD_FAIXA_SALARIAL', 'Exibir Faixa Salarial de Cargos', '', 5, false, 11); --.go
alter sequence papel_sequence restart with 500; --.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=498 WHERE atualizaPapeisIdsAPartirDe is null;--.go


CREATE FUNCTION ajusta_perfil_papel_faixa() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select distinct perfil_id as perfilId, (select count(*) from perfil_papel where papeis_id = 11) as qtd from perfil_papel where papeis_id = 11 order by perfil_id
		LOOP
		  IF mviews.qtd != 0 THEN
			EXECUTE ''insert into perfil_papel(perfil_id, papeis_id) values(''|| mviews.perfilId ||'', ''|| 499 ||'')'';
		  END IF;
		END LOOP;
    RETURN 1;
END;
' LANGUAGE plpgsql; --.go

select ajusta_perfil_papel_faixa(); --.go
drop function ajusta_perfil_papel_faixa(); --.go

alter table empresa add column logoCertificadoUrl varchar(200);--.go