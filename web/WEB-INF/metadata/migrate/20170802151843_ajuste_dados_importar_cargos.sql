update cargo set grupoocupacional_id = null where id in (select c.id from cargo c inner join grupoocupacional go on c.grupoocupacional_id = go.id where c.empresa_id <> go.empresa_id);--.go

CREATE OR REPLACE FUNCTION ajustaDadosImportarCargosoEntreEmpresas() RETURNS integer AS $$
DECLARE 
    mv RECORD; 
BEGIN 
	FOR mv IN 
		select cargo_id, areasOrganizacionais_id from cargo_areaorganizacional  ca inner join cargo c on c.id = ca.cargo_id inner join areaorganizacional a on a.id = ca.areasorganizacionais_id where  c.empresa_id <> a.empresa_id
	LOOP 
		delete from cargo_areaorganizacional where cargo_id = mv.cargo_id and areasOrganizacionais_id = mv.areasOrganizacionais_id;
	END LOOP; 

	FOR mv IN 
		select cargo_id, atitudes_id from cargo_atitude ca inner join cargo c on c.id = ca.cargo_id inner join atitude a on a.id = ca.atitudes_id where  c.empresa_id <> a.empresa_id
	LOOP 
		delete from cargo_atitude where cargo_id = mv.cargo_id and atitudes_id = mv.atitudes_id;
	END LOOP;

	FOR mv IN 
		select cargo_id, conhecimentos_id from cargo_conhecimento cc inner join cargo c on c.id = cc.cargo_id inner join conhecimento co on co.id = cc.conhecimentos_id where  c.empresa_id <> co.empresa_id
	LOOP 
		delete from cargo_conhecimento where cargo_id = mv.cargo_id and conhecimentos_id = mv.conhecimentos_id;
	END LOOP;

	FOR mv IN 
		select cargo_id, habilidades_id from cargo_habilidade ch inner join cargo c on c.id = ch.cargo_id inner join habilidade h on h.id = ch.habilidades_id where c.empresa_id <> h.empresa_id
	LOOP 
		delete from cargo_habilidade where cargo_id = mv.cargo_id and habilidades_id = mv.habilidades_id;
	END LOOP;

        RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select ajustaDadosImportarCargosoEntreEmpresas();--.go
drop function ajustaDadosImportarCargosoEntreEmpresas();--.go