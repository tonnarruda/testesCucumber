update parametrosdosistema set appversao = '1.1.43.35';--.go


INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (505, 'ROLE_C&S_PAINEL_IND', 'Painel de Indicadores', '/cargosalario/historicoColaborador/painelIndicadoresCargoSalario.action', 4, true, 361);--.go
alter sequence papel_sequence restart with 506;--.go

update papel set nome = 'Análise das Etapas Seletivas' where id=48;--.go




//talvez
CREATE OR REPLACE FUNCTION public.last_agg ( anyelement, anyelement )
RETURNS anyelement AS $$
        SELECT $2;
$$ LANGUAGE SQL STABLE;

CREATE AGGREGATE public.last (
        sfunc    = public.last_agg,
        basetype = anyelement,
        stype    = anyelement
);--.go

--ja foi para o update
update parametrosdosistema set camposcandidatovisivel=replace(camposcandidatovisivel,'telefone','fone');--.go

update parametrosdosistema set camposcandidatoobrigatorio=replace(camposcandidatoobrigatorio,'telefone','fone');--.go