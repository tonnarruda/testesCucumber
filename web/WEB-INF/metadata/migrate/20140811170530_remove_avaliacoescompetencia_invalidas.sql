delete from configuracaonivelcompetencia where id not in 
( 
    select cnc.id from configuracaonivelcompetencia cnc  
    inner join configuracaonivelcompetenciacolaborador cncc on cnc.configuracaonivelcompetenciacolaborador_id = cncc.id 
    inner join configuracaonivelcompetencia cnc2 on cncc.faixasalarial_id = cnc2.faixasalarial_id and (cnc2.competencia_id = cnc.competencia_id and cnc2.tipocompetencia = cnc.tipocompetencia) 
    where cnc.configuracaonivelcompetenciacolaborador_id is not null 
) 
and configuracaonivelcompetenciacolaborador_id is not null;--.go