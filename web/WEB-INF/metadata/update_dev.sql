update parametrosdosistema set appversao = '1.1.47.39';--.go

update historicocolaborador h1 set motivo='C' where h1.data = (select min(h2.data) from historicocolaborador h2 where h1.colaborador_id=h2.colaborador_id);--.go

update historicocolaborador set motivo='P' where motivo='I';--.go