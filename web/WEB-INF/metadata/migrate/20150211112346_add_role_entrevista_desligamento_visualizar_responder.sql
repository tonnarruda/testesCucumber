insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (630, 'ROLE_COLAB_LIST_ENTREVISTA_VISUALIZAR', 'Visualizar', '#', 1, false, 547);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (631, 'ROLE_COLAB_LIST_ENTREVISTA_RESPONDER', 'Responder', '#', 2, false, 547);--.go

CREATE FUNCTION ajusta_perfil() RETURNS integer AS $$ 
DECLARE 
    mviews RECORD; 
BEGIN 
    FOR mviews IN 
		select perfil_id from perfil_papel where papeis_id = 547 
		LOOP 
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 630);
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 631); 
		END LOOP; 
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select ajusta_perfil();--.go
drop function ajusta_perfil();--.go
alter sequence papel_sequence restart with 632;--.go