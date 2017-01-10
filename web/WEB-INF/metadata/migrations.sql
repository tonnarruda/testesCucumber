
CREATE OR REPLACE FUNCTION deixarTodasAsMensagensComoLidas() RETURNS integer AS $$   
DECLARE  
mv RECORD;  
BEGIN  
	FOR mv IN  
		select id as usuarioId from usuario 
	LOOP 	 
		insert into usuarionoticia (id, usuario_id, noticia_id) select nextval('usuarionoticia_sequence'), mv.usuarioId, id from noticia where publicada = true;   
	END LOOP;  
	RETURN 1;  
END;  
$$ LANGUAGE plpgsql;--.go

select deixarTodasAsMensagensComoLidas();--.go
drop function deixarTodasAsMensagensComoLidas();--.go