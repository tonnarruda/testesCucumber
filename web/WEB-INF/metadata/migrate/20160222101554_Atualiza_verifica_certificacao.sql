CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS BOOLEAN AS $$  
BEGIN 
return (

		select (
				(select (select Array(select distinct cursos_id from certificacao_curso where certificacaos_id = id_certificado order by cursos_id)) =
		  		(select Array(
			  				select distinct cu.id from colaboradorturma ct inner join turma t on t.id = ct.turma_id 
			  				and t.dataprevfim = (select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada 
							and dataprevfim >= (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000')))
							inner join curso cu on cu.id = t.curso_id
							where ct.colaborador_id = id_colaborador
							and t.realizada
							and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = id_certificado)
							and verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia)
							order by cu.id
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