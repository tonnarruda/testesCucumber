CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS BOOLEAN AS $$  
BEGIN 
	return (select (
			(select (select Array(select distinct cursos_id from certificacao_curso where certificacaos_id = id_certificado order by cursos_id)) =
			(select Array(
							select distinct t.curso_id from colaboradorturma ct 
							inner join turma t on t.id = ct.turma_id 
							and t.dataprevfim = (select max(dataprevfim) from colaboradorturma ct2 inner join turma t2 on t2.id = ct2.turma_id 
										where t2.curso_id = t.curso_id and t2.realizada and ct2.colaborador_id = id_colaborador
										and dataprevfim >= validade_certificacao(id_certificado,id_colaborador)
									)
							where ct.colaborador_id = id_colaborador
							and t.realizada
							and t.curso_id in (select cursos_id from certificacao_curso where certificacaos_id = id_certificado)
							and ct.aprovado
							order by t.curso_id
						) 
			) 
		) 
	and 
	( 
		select (select Array(select distinct avaliacoespraticas_id from certificacao_avaliacaopratica where certificacao_id = id_certificado order by avaliacoespraticas_id)) = 
		(select Array( 
					select distinct caval.avaliacaopratica_id from colaboradoravaliacaopratica caval where caval.colaborador_id = id_colaborador
					and caval.certificacao_id = id_certificado
					and caval.nota >= (select aval.notaMinima from avaliacaopratica aval where aval.id = caval.avaliacaopratica_id)
					and caval.data > (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000'))
					order by caval.avaliacaopratica_id)))
	) 
	as situacao 
	);
	
END; 
$$ LANGUAGE plpgsql; --.go 


CREATE OR REPLACE FUNCTION validade_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS DATE AS $$  
BEGIN
	IF exists(select * from colaboradorcertificacao  cc inner join certificacao c on c.id = cc.certificacao_id where c.id = id_certificado and cc.colaborador_id = id_colaborador and c.periodicidade is null)
	THEN
		return '01/01/2500';
	ELSE       
		return coalesce((
			select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) 
			from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id 
			where cc.colaborador_id = id_colaborador And cc.certificacao_id = id_certificado 
			group by ce.periodicidade
		), '01/01/2000');
	END IF;
END; 
$$ LANGUAGE plpgsql; --.go 


CREATE OR REPLACE FUNCTION id_coalbCertificacao(idColaborador BIGINT, idCertificacao BIGINT, dataCert date) RETURNS BIGINT[]
AS $$ DECLARE
	BEGIN
		RAISE NOTICE 'colaborador is %',idColaborador;

		IF exists (select colaboradorcertificacao_id from colaboradoravaliacaopratica where colaboradorcertificacao_id  in (select id from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert))
		THEN
			return (select Array(
				select id from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert
				and not id in (	
						select min(id) from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert 
						and id in (select colaboradorcertificacao_id from colaboradoravaliacaopratica where colaboradorcertificacao_id  in (select id from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert))
														
						)
				and id not in (select colaboradorcertificacaoprerequisito_id from colaboradorcertificacao where colaboradorcertificacaoprerequisito_id is not null)
			));
		ELSE
			return (select Array(
				select id from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert
				and not id in (

								select min(id) from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert 
								and id in (select id from colaboradorcertificacao where colaborador_id = idColaborador and certificacao_id = idCertificacao and data = dataCert)
								
								
						)
				and id not in (select colaboradorcertificacaoprerequisito_id from colaboradorcertificacao where colaboradorcertificacaoprerequisito_id is not null)
			));
		END IF;
		
	END;
$$ LANGUAGE plpgsql;--.go

CREATE OR REPLACE FUNCTION ajusta_certificados() RETURNS integer AS $$
DECLARE
	mv RECORD;
	BEGIN
	FOR mv IN select colaborador_id as colId, certificacao_id as certId, data as data from colaboradorcertificacao where colaboradorcertificacaoprerequisito_id is null group by colaborador_id, certificacao_id, data having count(*) > 1
		LOOP
			delete from colaboradorcertificacao_colaboradorturma where colaboradorcertificacao_id in (select unnest(id_coalbCertificacao(mv.colId, mv.certId, mv.data)));
			delete from colaboradorcertificacao where id in (select unnest(id_coalbCertificacao(mv.colId, mv.certId, mv.data)));
			
		END LOOP;
	RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go


SELECT ajusta_certificados();--.go
DROP FUNCTION id_coalbCertificacao(idColaborador BIGINT, idCertificacao BIGINT, dataCert date); --.go
DROP FUNCTION ajusta_certificados(); --.go
