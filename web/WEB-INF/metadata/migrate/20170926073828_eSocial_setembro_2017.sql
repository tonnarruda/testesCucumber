
alter table empresa add column aderiuaoesocial boolean default false;--.go
alter table empresa add column dddCelularAndUFHabilitacaoAtualizados boolean default false;--.go


update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'pis,|,pis','') from  parametrosdosistema);--.go
update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'pis,|,pis','') from  parametrosdosistema);--.go
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',pis';--.go

update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'vinculo,|,vinculo','') from  parametrosdosistema);--.go
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',vinculo';--.go

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'vinculo,|,vinculo','') from  parametrosdosistema);--.go
update parametrosdosistema set camposcolaboradorobrigatorio = camposcolaboradorobrigatorio || ',vinculo';--.go


alter table colaborador add column dddCelular character varying(5); --.go
alter table candidato add column dddCelular character varying(5); --.go

alter table colaborador add column ufHab_id bigint; --.go
alter table candidato add column ufHab_id bigint; --.go

ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(60) USING SUBSTR(nome, 1, 60);--.go;

DROP VIEW situacaosolicitacaoepi;--.go

ALTER TABLE colaborador ALTER COLUMN nome TYPE character varying(70);--.go

CREATE OR REPLACE VIEW situacaosolicitacaoepi AS 
 SELECT sub.solicitacaoepiid, sub.empresaid, sub.estabelecimentoid, sub.estabelecimentonome, sub.colaboradorid, sub.colaboradormatricula, sub.colaboradornome, sub.colaboradordesligado, sub.solicitacaoepidata, sub.cargonome, sub.qtdsolicitado, sub.qtdentregue, 
        CASE
            WHEN sub.qtdsolicitado <= sub.qtdentregue THEN 'E'::text
            WHEN sub.qtdentregue > 0 AND sub.qtdentregue < sub.qtdsolicitado THEN 'P'::text
            WHEN sub.qtdentregue = 0 THEN 'A'::text
            ELSE NULL::text
        END AS solicitacaoepisituacaoentregue, sub.qtddevolvida, 
        CASE
            WHEN sub.qtddevolvida <> 0 AND sub.qtddevolvida = sub.qtdentregue THEN 'D'::text
            WHEN sub.qtddevolvida > 0 AND sub.qtddevolvida < sub.qtdentregue THEN 'DP'::text
            WHEN sub.qtddevolvida = 0 AND sub.qtdentregue > 0 THEN 'S'::text
            ELSE NULL::text
        END AS solicitacaoepisituacaodevolvido
   FROM ( SELECT se.id AS solicitacaoepiid, se.empresa_id AS empresaid, est.id AS estabelecimentoid, est.nome AS estabelecimentonome, c.id AS colaboradorid, c.matricula AS colaboradormatricula, c.nome AS colaboradornome, c.desligado AS colaboradordesligado, se.data AS solicitacaoepidata, ca.nome AS cargonome, ( SELECT sum(sei2.qtdsolicitado) AS sum
                   FROM solicitacaoepi_item sei2
                  WHERE sei2.solicitacaoepi_id = se.id) AS qtdsolicitado, COALESCE(sum(seie.qtdentregue), 0::bigint) AS qtdentregue, COALESCE(sum(seid.qtddevolvida), 0::bigint) AS qtddevolvida
           FROM solicitacaoepi se
      LEFT JOIN solicitacaoepi_item sei ON sei.solicitacaoepi_id = se.id
   LEFT JOIN solicitacaoepiitementrega seie ON seie.solicitacaoepiitem_id = sei.id
   LEFT JOIN solicitacaoepiitemdevolucao seid ON seid.solicitacaoepiitem_id = sei.id
   LEFT JOIN colaborador c ON se.colaborador_id = c.id
   LEFT JOIN historicocolaborador hc ON c.id = hc.colaborador_id
   LEFT JOIN estabelecimento est ON se.estabelecimento_id = est.id
   LEFT JOIN cargo ca ON se.cargo_id = ca.id
  WHERE hc.data = (( SELECT max(hc2.data) AS max
   FROM historicocolaborador hc2
  WHERE hc2.colaborador_id = c.id AND hc2.status = 1 AND hc2.data <= 'now'::text::date)) AND hc.status = 1
  GROUP BY se.id, se.empresa_id, est.id, est.nome, c.matricula, c.id, c.nome, c.desligado, se.data, ca.id, ca.nome) sub;--.go
  
ALTER TABLE colaborador ALTER COLUMN pai TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN mae TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN conjuge TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN ctpsnumero TYPE character varying(11);--.go
ALTER TABLE colaborador ALTER COLUMN rgorgaoemissor TYPE character varying(20);--.go
ALTER TABLE colaborador ALTER COLUMN numerohab TYPE character varying(12);--.go
ALTER TABLE colaborador ALTER COLUMN logradouro TYPE character varying(80);--.go
ALTER TABLE colaborador ALTER COLUMN complemento TYPE character varying(30);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(60) USING SUBSTR(bairro, 1, 60);--.go;

ALTER TABLE candidato ALTER COLUMN nome TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN pai TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN mae TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN conjuge TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN ctpsnumero TYPE character varying(11);--.go
ALTER TABLE candidato ALTER COLUMN rgorgaoemissor TYPE character varying(20);--.go
ALTER TABLE candidato ALTER COLUMN numerohab TYPE character varying(12);--.go
ALTER TABLE candidato ALTER COLUMN logradouro TYPE character varying(80);--.go
ALTER TABLE candidato ALTER COLUMN complemento TYPE character varying(30);--.go
ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(60);--.go

update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'fone','fone,ddd');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'fone','fone,ddd');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'fone','fone,ddd');--.go

update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'celular','celular,dddCelular');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'celular','celular,dddCelular');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'celular','celular,dddCelular');--.go