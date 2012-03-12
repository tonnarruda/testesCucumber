package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;

public class SolicitacaoEpiItemEntregaDaoHibernate extends GenericDaoHibernate<SolicitacaoEpiItemEntrega> implements SolicitacaoEpiItemEntregaDao
{
	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"seie");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seie.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "qtdEntregue");
		p.add(Projections.property("seie.dataEntrega"), "dataEntrega");

		criteria.setProjection(p);

		criteria.add(Expression.eq("seie.solicitacaoEpiItem.id", solicitacaoEpiItemId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
