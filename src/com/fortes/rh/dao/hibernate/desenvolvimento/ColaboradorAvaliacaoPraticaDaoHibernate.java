package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cap.id"), "id");
		p.add(Projections.property("cap.data"), "data");
		p.add(Projections.property("cap.nota"), "nota");
		p.add(Projections.property("cap.avaliacaoPratica.id"), "avaliacaoPraticaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cap.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cap.certificacao.id", certificacaoId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));
		
		return criteria.list();
	}

	public void removeAllByColaboradorId(Long colaboradorId) 
	{
		Query query = getSession().createQuery("delete ColaboradorAvaliacaoPratica cap where cap.colaborador.id = :colaboradorId");
		query.setLong("colaboradorId", colaboradorId);

		query.executeUpdate();
	}
}
