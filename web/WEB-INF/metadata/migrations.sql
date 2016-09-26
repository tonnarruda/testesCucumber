CREATE TABLE cartao (
    id bigint NOT NULL,
    imgUrl character varying(200),
    mensagem character varying(300),
    tipoCartao character varying(2),
    empresa_id bigint
);--.go
ALTER TABLE cartao ADD CONSTRAINT cartao_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE cartao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE cartao ADD CONSTRAINT cartao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

CREATE FUNCTION insere_cartoes() RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select id, imgAniversarianteUrl, mensagemCartaoAniversariante  from empresa 
    			where (imgAniversarianteUrl is not null and imgAniversarianteUrl != '' )  
				and (mensagemCartaoAniversariante is not null and mensagemCartaoAniversariante!= '')
	LOOP
		INSERT INTO cartao values (nextval('cartao_sequence'), mv.imgAniversarianteUrl, mv.mensagemCartaoAniversariante, 'A', mv.id);  
	END LOOP;
   RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go


select insere_cartoes();--.go
drop function insere_cartoes();--.go

alter table drop column imgAniversarianteUrl, drop column mensagemCartaoAniversariante; --.go