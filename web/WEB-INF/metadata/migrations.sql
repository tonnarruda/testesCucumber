
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (685, 'ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA','Campos padrões do sistema', '/geral/parametrosDoSistema/listCampos.action', 1, true, 503);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (686, 'ROLE_CONFIG_CAMPOS_EXTRAS','Campos extras', '/geral/parametrosDoSistema/listCamposExtras.action', 2, true, 503);--.go
update papel set papelmae_id = 685 where id in(678,679,680);--.go
update papel set codigo = 'ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA_PARA_COLABORADOR'  where id = 678; --.go
update papel set codigo = 'ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA_PARA_CANDIDATO'  where id = 679; --.go
update papel set codigo = 'ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA_PARA_CANDIDATO_EXTERNO'  where id = 680; --.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (687, 'ROLE_CONFIG_CAMPOS_EXTRAS_PARA_COLABORADOR','Cadastro de Colaborador', '#', 1, false, 686);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (688, 'ROLE_CONFIG_CAMPOS_EXTRAS_PARA_CANDIDATO', 'Cadastro de Candidato', '#', 2, false, 686);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (689, 'ROLE_CONFIG_CAMPOS_EXTRAS_PARA_CANDIDATO_EXT','Cadastro de Candidato (Externo)', '#', 3, false, 686);--.go
alter sequence papel_sequence restart with 690;--.go

insert into perfil_papel(perfil_id, papeis_id) select distinct perfil_id, 685 from perfil_papel where papeis_id in(678,679,680);--.go
insert into perfil_papel(perfil_id, papeis_id) select distinct perfil_id, 687 from perfil_papel where papeis_id in(678);--.go
insert into perfil_papel(perfil_id, papeis_id) select distinct perfil_id, 688 from perfil_papel where papeis_id in(679);--.go
insert into perfil_papel(perfil_id, papeis_id) select distinct perfil_id, 689 from perfil_papel where papeis_id in(680);--.go

insert into perfil_papel(perfil_id, papeis_id) select distinct perfil_id, 686 from perfil_papel where papeis_id in(687,688,689);--.go

CREATE TABLE ConfiguracaoCampoExtraVisivelObrigadotorio (
    id bigint NOT NULL,
    camposExtrasVisiveis text,
    camposExtrasObrigatorios text,
    tipoConfiguracaoCampoExtra character varying(20),
    empresa_id bigint
);--.go
ALTER TABLE ConfiguracaoCampoExtraVisivelObrigadotorio ADD CONSTRAINT ConfiguracaoCampoExtraVisivelObrigadotorio_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE ConfiguracaoCampoExtraVisivelObrigadotorio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
 
ALTER TABLE ConfiguracaoCampoExtraVisivelObrigadotorio ADD CONSTRAINT ConfigCampoExtraVisivelObrigadotorio_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

CREATE FUNCTION verifica_existencia_campo_extra() RETURNS integer AS $$
DECLARE
	camposExtras varchar[] := array['texto1','texto2','texto3','texto4','texto5','texto6','texto7','texto8','textolongo1','textolongo2','data1','data2','data3','valor1','valor2','numero1'];
BEGIN
	FOR i IN array_lower(camposExtras, 1) .. array_upper(camposExtras, 1)
	LOOP
		IF(coalesce( (select substring((select camposColaboradorVisivel from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_visivel((select substring((select camposColaboradorVisivel from parametrosdosistema), camposExtras[i])), 'colaborador');
		END IF;
		IF(coalesce( (select substring((select camposCandidatoVisivel from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_visivel((select substring((select camposCandidatoVisivel from parametrosdosistema), camposExtras[i])), 'candidato');
		END IF;
		IF(coalesce( (select substring((select camposCandidatoExternoVisivel from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_visivel((select substring((select camposCandidatoExternoVisivel from parametrosdosistema), camposExtras[i])), 'candidatoExterno');
		END IF;

		IF(coalesce( (select substring((select camposColaboradorObrigatorio from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_obrigatorio((select substring((select camposColaboradorObrigatorio from parametrosdosistema), camposExtras[i])), 'colaborador');
		END IF;
		IF(coalesce( (select substring((select camposCandidatoObrigatorio from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_obrigatorio((select substring((select camposCandidatoObrigatorio from parametrosdosistema), camposExtras[i])), 'candidato');
		END IF;
		IF(coalesce( (select substring((select camposCandidatoExternoObrigatorio from parametrosdosistema), camposExtras[i])), '') != '')
			THEN PERFORM atualiza_configuracao_campoextra_obrigatorio((select substring((select camposCandidatoExternoObrigatorio from parametrosdosistema), camposExtras[i])), 'candidatoExterno');
		END IF;
		
		update parametrosdosistema set camposcolaboradorvisivel = (select regexp_replace(camposColaboradorVisivel, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		update parametrosdosistema set camposColaboradorObrigatorio = (select regexp_replace(camposColaboradorObrigatorio, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		update parametrosdosistema set camposCandidatoVisivel = (select regexp_replace(camposCandidatoVisivel, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		update parametrosdosistema set camposCandidatoObrigatorio = (select regexp_replace(camposCandidatoObrigatorio, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		update parametrosdosistema set camposCandidatoExternoVisivel = (select regexp_replace(camposCandidatoExternoVisivel, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		update parametrosdosistema set camposCandidatoExternoObrigatorio = (select regexp_replace(camposCandidatoExternoObrigatorio, camposExtras[i]||',|,'||camposExtras[i],'') from parametrosdosistema);
		
	END LOOP;		
RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go

CREATE FUNCTION atualiza_configuracao_campoextra_visivel(campoextra character varying, tipoconfiguracao character varying) RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select empresa_id from configuracaocampoextra cce join empresa e on e.id = cce.empresa_id 
		where empresa_id is not null and cce.nome = campoextra and 
		(('colaborador' = tipoconfiguracao and e.campoExtraColaborador = true) or ('candidato' = tipoconfiguracao and e.campoExtraCandidato = true) OR ('candidatoExterno' = tipoconfiguracao and e.campoExtraCandidato = true) )    				
		and
		(('colaborador' = tipoconfiguracao and cce.ativocolaborador = true) or ('candidato' = tipoconfiguracao and cce.ativocandidato = true) OR ('candidatoExterno' = tipoconfiguracao and cce.ativocandidato = true) ) 
	LOOP
		if( (select exists (select id from configuracaocampoextravisivelobrigadotorio where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao)) = true)
		then
			update configuracaocampoextravisivelobrigadotorio set camposextrasvisiveis = (select camposextrasvisiveis from configuracaocampoextravisivelobrigadotorio 
												   where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao)
												   || ',' || campoextra where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao; 
		else
			insert into configuracaocampoextravisivelobrigadotorio(id, camposextrasvisiveis,tipoconfiguracaocampoextra, empresa_id)  values (nextval('configuracaocampoextravisivelobrigadotorio_sequence'), 
																			campoextra, tipoconfiguracao, mv.empresa_id); 
		end if;
	END LOOP;
   RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go

CREATE FUNCTION atualiza_configuracao_campoextra_obrigatorio(campoextra character varying, tipoconfiguracao character varying) RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select empresa_id from configuracaocampoextra cce join empresa e on e.id = cce.empresa_id 
		where empresa_id is not null and cce.nome = campoextra and 
		(('colaborador' = tipoconfiguracao and e.campoExtraColaborador = true) or ('candidato' = tipoconfiguracao and e.campoExtraCandidato = true) OR ('candidatoExterno' = tipoconfiguracao and e.campoExtraCandidato = true) )    				
		and
		(('colaborador' = tipoconfiguracao and cce.ativocolaborador = true) or ('candidato' = tipoconfiguracao and cce.ativocandidato = true) OR ('candidatoExterno' = tipoconfiguracao and cce.ativocandidato = true) ) 
	LOOP
		if((select exists (select id from configuracaocampoextravisivelobrigadotorio where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao)) = true)
		then
			if((select coalesce(camposextrasobrigatorios, '') from configuracaocampoextravisivelobrigadotorio where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao) != '')
			then
				update configuracaocampoextravisivelobrigadotorio set camposextrasobrigatorios = (select camposextrasobrigatorios from configuracaocampoextravisivelobrigadotorio 
												   where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao)
												   || ',' || campoextra where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao; 
			else
				update configuracaocampoextravisivelobrigadotorio set camposextrasobrigatorios = campoextra where empresa_id = mv.empresa_id and tipoconfiguracaocampoextra = tipoconfiguracao; 
			end if;
		else
			insert into configuracaocampoextravisivelobrigadotorio(id, camposextrasobrigatorios,tipoconfiguracaocampoextra, empresa_id)  values (nextval('configuracaocampoextravisivelobrigadotorio_sequence'), 
																			campoextra, tipoconfiguracao, mv.empresa_id); 
		end if;
	END LOOP;
   RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go

select verifica_existencia_campo_extra(); 
drop function atualiza_configuracao_campoextra_visivel(character varying,character varying);--.go
drop function atualiza_configuracao_campoextra_obrigatorio(character varying,character varying);--.go
drop FUNCTION verifica_existencia_campo_extra();--.go

update parametrosdosistema set camposCandidatoTabs = (select regexp_replace(camposCandidatoTabs, 'abaExtra,|,abaExtra', '') from parametrosdosistema);--.go
update parametrosdosistema set camposCandidatoExternoTabs = (select regexp_replace(camposCandidatoExternoTabs, 'abaExtra,|,abaExtra', '') from parametrosdosistema);--.go
update parametrosdosistema set camposColaboradorTabs = (select regexp_replace(camposColaboradorTabs, 'abaExtra,|,abaExtra', '') from parametrosdosistema);--.go
