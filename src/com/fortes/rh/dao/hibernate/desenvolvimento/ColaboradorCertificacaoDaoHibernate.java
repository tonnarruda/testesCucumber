package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;

public class ColaboradorCertificacaoDaoHibernate extends GenericDaoHibernate<ColaboradorCertificacao> implements ColaboradorCertificacaoDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");

		criteria.setProjection(p);

		criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id" , certificacaoId));

		criteria.addOrder(Order.asc("cc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}

	public Collection<Colaborador> colabNaCertificacaoNaoCertificadosByCertificacaoId(Long certificacaoId) 
	{
/*
 * select * from colaborador where id in (
	select colaborador_id from
	(select distinct colaborador_id, ct.curso_id from colaboradorturma ct
	where ct.curso_id in (select cursos_id from certificacao_curso  where certificacaos_id = 47)
	and ct.colaborador_id not in (select colaborador_id from colaboradorcertificacao where certificacao_id = 47)
	group by ct.colaborador_id, ct.curso_id) as colabNasTurmas
	group by colaborador_id
	having count(colaborador_id) = (select count(distinct cursos_id) from certificacao_curso  where certificacaos_id = 47)
) and desligado = false;
 * 
 * */
		
		return null;
	}
}
