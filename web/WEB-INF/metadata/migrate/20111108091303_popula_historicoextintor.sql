INSERT INTO historicoextintor (extintor_id, estabelecimento_id, localizacao, data) 
SELECT 
e.id, 
e.estabelecimento_id, 
e.localizacao, 
LEAST ( CURRENT_DATE, 
       (SELECT MIN(em.saida) FROM extintormanutencao em WHERE em.extintor_id = e.id), 
       (SELECT MIN(ei.data) FROM extintorinspecao ei WHERE ei.extintor_id = e.id)
      ) 
FROM extintor e;--.go