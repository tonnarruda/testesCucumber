DROP VIEW situacaosolicitacaoepi;--.go 

CREATE OR REPLACE VIEW situacaosolicitacaoepi AS 
 SELECT sub.solicitacaoepiid, sub.empresaid, sub.estabelecimentoid, sub.estabelecimentonome, sub.colaboradormatricula, sub.colaboradornome, sub.solicitacaoepidata, sub.cargonome, sub.qtdsolicitado, sub.qtdentregue,  
        CASE 
            WHEN sub.qtdsolicitado <= sub.qtdentregue THEN 'E' 
            WHEN sub.qtdentregue > 0 AND sub.qtdentregue < sub.qtdsolicitado THEN 'P' 
            WHEN sub.qtdentregue = 0 THEN 'A' 
            ELSE NULL 
        END AS solicitacaoepisituacao 
   FROM ( SELECT se.id as solicitacaoepiid, se.empresa_id AS empresaid, est.id AS estabelecimentoid, est.nome AS estabelecimentonome, c.matricula AS colaboradormatricula, c.nome AS colaboradornome, se.data as solicitacaoepidata, ca.nome AS cargonome, ( SELECT sum(sei2.qtdsolicitado) AS sum 
                   FROM solicitacaoepi_item sei2 
                  WHERE sei2.solicitacaoepi_id = se.id) AS qtdsolicitado, COALESCE(sum(seie.qtdentregue), 0::bigint) AS qtdentregue 
           FROM solicitacaoepi se 
      LEFT JOIN solicitacaoepi_item sei ON sei.solicitacaoepi_id = se.id 
   LEFT JOIN solicitacaoepiitementrega seie ON seie.solicitacaoepiitem_id = sei.id 
   LEFT JOIN colaborador c ON se.colaborador_id = c.id 
   LEFT JOIN historicocolaborador hc ON c.id = hc.colaborador_id 
   LEFT JOIN estabelecimento est ON hc.estabelecimento_id = est.id 
   LEFT JOIN cargo ca ON se.cargo_id = ca.id 
  WHERE hc.data = (SELECT max(hc2.data) AS max FROM historicocolaborador hc2 WHERE hc2.colaborador_id = c.id and hc2.status = 1 AND hc2.data <= current_date) 
   AND hc.status = 1 
   AND c.desligado = false 
  GROUP BY se.id, se.empresa_id, est.id, est.nome, c.matricula, c.id, c.nome, se.data, ca.id, ca.nome) sub;--.go

ALTER TABLE situacaosolicitacaoepi OWNER TO postgres;--.go