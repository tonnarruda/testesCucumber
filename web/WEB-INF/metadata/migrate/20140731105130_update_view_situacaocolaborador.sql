DROP VIEW situacaocolaborador;--.go

CREATE VIEW situacaocolaborador AS 
 SELECT hc.id AS historicocolaboradorid, 
        GREATEST(hc.data, hfs_hc.data, hi_hfs_hc.data, hi_hc.data) AS data, 
        hc.tiposalario AS tipo, 
        COALESCE(hfs_hc.quantidade * hi_hfs_hc.valor, hfs_hc.valor, hc.quantidadeindice * hi_hc.valor, hc.salario) AS salario, 
        c.id AS cargo_id, 
        c.nome AS cargo_nome, 
        hc.faixasalarial_id, 
        fs_hc.nome AS faixasalarial_nome,  
        hc.estabelecimento_id, 
        e_hc.nome as estabelecimento_nome,  
        hc.areaorganizacional_id, 
        hc.colaborador_id, 
        hc.motivo 
   FROM historicocolaborador hc 
   LEFT JOIN estabelecimento e_hc ON e_hc.id = hc.estabelecimento_id  
   LEFT JOIN faixasalarial fs_hc ON fs_hc.id = hc.faixasalarial_id 
   LEFT JOIN faixasalarialhistorico hfs_hc ON hfs_hc.faixasalarial_id = fs_hc.id AND hc.tiposalario = 1 
   LEFT JOIN cargo c ON c.id = fs_hc.cargo_id 
   LEFT JOIN indice i_hfs_hc ON i_hfs_hc.id = hfs_hc.indice_id AND hfs_hc.tipo = 2 
   LEFT JOIN indicehistorico hi_hfs_hc ON i_hfs_hc.id = hi_hfs_hc.indice_id 
   LEFT JOIN indice i_hc ON i_hc.id = hc.indice_id AND hc.tiposalario = 2 
   LEFT JOIN indicehistorico hi_hc ON hi_hc.indice_id = i_hc.id 
   LEFT JOIN ( SELECT hc2.data, hc2.colaborador_id AS colabid, COALESCE(( SELECT min(hc3.data) AS min 
           FROM historicocolaborador hc3 
          WHERE hc3.data > hc2.data AND hc3.colaborador_id = hc2.colaborador_id), '2300-01-01'::date) AS dataproximo 
   FROM historicocolaborador hc2 
  WHERE hc2.status <> 3 
  ORDER BY hc2.colaborador_id, hc2.data) proximo ON proximo.data = hc.data AND proximo.colabid = hc.colaborador_id 
   LEFT JOIN ( SELECT hfs2.data, hfs2.faixasalarial_id AS faixaid, COALESCE(( SELECT min(fsh3.data) AS min 
           FROM faixasalarialhistorico fsh3 
          WHERE fsh3.data > hfs2.data AND fsh3.faixasalarial_id = hfs2.faixasalarial_id), '2300-01-01'::date) AS dataproximohistfaixa 
   FROM faixasalarialhistorico hfs2 
  WHERE hfs2.status <> 3 
  ORDER BY hfs2.faixasalarial_id, hfs2.data) proximafaixa ON proximafaixa.data = hfs_hc.data AND proximafaixa.faixaid = hfs_hc.faixasalarial_id 
   LEFT JOIN ( SELECT hc3.id AS histcolabid, COALESCE(( SELECT max(fsh.data) AS max 
           FROM faixasalarialhistorico fsh 
          WHERE fsh.data <= hc3.data AND fsh.faixasalarial_id = hc3.faixasalarial_id), '1900-01-01'::date) AS dataatualfaixa 
   FROM historicocolaborador hc3 
  WHERE hc3.tiposalario = 1 AND hc3.status <> 3 
  ORDER BY hc3.id) faixaatual ON faixaatual.histcolabid = hc.id 
   LEFT JOIN ( SELECT hfs3.id AS histfaixaid, COALESCE(( SELECT max(hi.data) AS max 
           FROM indicehistorico hi 
          WHERE hi.data <= hfs3.data AND hi.indice_id = hfs3.indice_id), '1900-01-01'::date) AS dataatualindicefaixa 
   FROM faixasalarialhistorico hfs3 
  WHERE hfs3.tipo = 2 AND hfs3.status <> 3 
  ORDER BY hfs3.id) indiceatualfaixa ON indiceatualfaixa.histfaixaid = hfs_hc.id 
   LEFT JOIN ( SELECT hc4.id AS histcolabid, COALESCE(( SELECT max(hi.data) AS max 
           FROM indicehistorico hi 
          WHERE hi.data <= hc4.data AND hi.indice_id = hc4.indice_id), '1900-01-01'::date) AS dataatualindice 
   FROM historicocolaborador hc4 
  WHERE hc4.tiposalario = 2 AND hc4.status <> 3 
  ORDER BY hc4.id) indiceatual ON indiceatual.histcolabid = hc.id 
  WHERE hc.status <> 3 AND (hfs_hc.data < proximo.dataproximo OR hfs_hc.data IS NULL) AND (hi_hfs_hc.data < proximo.dataproximo OR hi_hfs_hc.data IS NULL) AND (hi_hfs_hc.data < proximafaixa.dataproximohistfaixa OR hi_hfs_hc.data IS NULL) AND (hfs_hc.data >= faixaatual.dataatualfaixa OR hfs_hc.data IS NULL) AND (hi_hfs_hc.data >= indiceatualfaixa.dataatualindicefaixa OR hi_hfs_hc.data IS NULL) AND (hi_hc.data < proximo.dataproximo OR hi_hc.data IS NULL) AND (hi_hc.data >= indiceatual.dataatualindice OR hi_hc.data IS NULL) 
  ORDER BY hc.colaborador_id, hc.data, hfs_hc.data, hi_hfs_hc.data, hi_hc.data;--.go 

