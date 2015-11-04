package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(Certificacao.class, "ct");
		criteria.createCriteria("ct.avaliacoesPraticas", "ap", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.colaboradorAvaliacaoPraticas", "cap", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ap.notaMinima"), "avaliacaoPraticaNotaMinima");
		p.add(Projections.property("ap.titulo"), "avaliacaoPraticaTitulo");
		p.add(Projections.property("cap.nota"), "nota");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("ce.id", certificacaoId));
		
		criteria.addOrder(Order.asc("ap.titulo"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));
		
		return criteria.list();
	}
}
