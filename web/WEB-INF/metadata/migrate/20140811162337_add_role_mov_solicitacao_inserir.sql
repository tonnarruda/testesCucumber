update papel set ordem = ordem + 1 where papelmae_id = 21 and ordem >= 3; --.go
update papel set nome = 'Editar' where id = 612; --.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (623, 'ROLE_MOV_SOLICITACAO_INSERIR', 'Inserir', '#', 3, false, 21);--.go
CREATE FUNCTION ajusta_perfil_papel_faixa() RETURNS integer AS $$ 
DECLARE 
    mviews RECORD; 
BEGIN 
    FOR mviews IN 
		select perfil_id from perfil_papel where papeis_id = 612 
		LOOP 
		  insert into perfil_papel(perfil_id, papeis_id) values( mviews.perfil_id , 623); 
		END LOOP; 
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select ajusta_perfil_papel_faixa();--.go
drop function ajusta_perfil_papel_faixa();--.go
alter sequence papel_sequence restart with 624;--.go