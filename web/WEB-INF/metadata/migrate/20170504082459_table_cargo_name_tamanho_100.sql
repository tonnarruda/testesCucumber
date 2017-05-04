DROP VIEW situacaosolicitacaoepi;--.go
ALTER TABLE cargo ALTER COLUMN nome TYPE character varying(100);--.go
ALTER TABLE faixasalarial ALTER COLUMN nomeacpessoal TYPE character varying(100);--.go

CREATE OR REPLACE VIEW situacaosolicitacaoepi AS 
 SELECT sub.solicitacaoepiid, sub.empresaid, sub.estabelecimentoid, sub.estabelecimentonome, sub.colaboradorid, sub.colaboradormatricula, sub.colaboradornome, sub.colaboradordesligado, sub.solicitacaoepidata, sub.cargonome, sub.qtdsolicitado, sub.qtdentregue, 
        CASE
            WHEN sub.qtdsolicitado <= sub.qtdentregue THEN 'E'::text
            WHEN sub.qtdentregue > 0 AND sub.qtdentregue < sub.qtdsolicitado THEN 'P'::text
            WHEN sub.qtdentregue = 0 THEN 'A'::text
            ELSE NULL::text
        END AS solicitacaoepisituacaoEntregue,
        sub.qtdDevolvida,
        CASE
            WHEN sub.qtdDevolvida != 0 and sub.qtdDevolvida = sub.qtdentregue THEN 'D'::text
            WHEN sub.qtdDevolvida > 0 AND sub.qtdDevolvida < sub.qtdentregue THEN 'DP'::text
            WHEN sub.qtdDevolvida = 0 and  sub.qtdentregue > 0 THEN 'S'::text
            ELSE NULL::text
        END AS solicitacaoepisituacaoDevolvido
        
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
  GROUP BY se.id, se.empresa_id, est.id, est.nome, c.matricula, c.id, c.nome, c.desligado, se.data, ca.id, ca.nome) 
  sub;--.go