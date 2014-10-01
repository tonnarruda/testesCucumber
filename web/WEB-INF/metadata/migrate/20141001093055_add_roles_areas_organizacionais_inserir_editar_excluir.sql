insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (625, 'ROLE_CAD_AREA_INSERIR', 'Inserir', '#', 1, false, 9);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (626, 'ROLE_CAD_AREA_EDITAR', 'Editar', '#', 2, false, 9);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (627, 'ROLE_CAD_AREA_EXCLUIR', 'Excluir', '#', 3, false, 9);--.go

CREATE FUNCTION ajusta_perfil_papel_faixa() RETURNS integer AS $$ 
DECLARE 
    mviews RECORD; 
BEGIN 
    FOR mviews IN 
		select perfil_id from perfil_papel where papeis_id = 9 
		LOOP 
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 625);
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 626); 
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 627); 
		END LOOP; 
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select ajusta_perfil_papel_faixa();--.go
drop function ajusta_perfil_papel_faixa();--.go
alter sequence papel_sequence restart with 628;--.go