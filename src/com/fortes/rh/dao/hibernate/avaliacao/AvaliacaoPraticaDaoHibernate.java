package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;

@Component
public class AvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<AvaliacaoPratica> implements AvaliacaoPraticaDao
{

	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoPratica> findByCertificacaoId(Long... certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(Certificacao.class, "ce");
		criteria.createCriteria("ce.avaliacoesPraticas", "ap", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ap.id"), "id");
		p.add(Projections.property("ap.titulo"), "titulo");
		p.add(Projections.property("ap.notaMinima"), "notaMinima");
		p.add(Projections.property("ce.id"), "certificacaoId");
		criteria.setProjection(p);

		criteria.add(Expression.in("ce.id", certificacaoId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AvaliacaoPratica.class));
		
		return criteria.list();
	}
}
