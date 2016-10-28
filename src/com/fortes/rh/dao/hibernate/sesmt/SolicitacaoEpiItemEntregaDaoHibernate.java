package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

@Component
public class SolicitacaoEpiItemEntregaDaoHibernate extends GenericDaoHibernate<SolicitacaoEpiItemEntrega> implements SolicitacaoEpiItemEntregaDao
{
	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"seie");
		criteria.createCriteria("seie.solicitacaoEpiItem", "sei", Criteria.LEFT_JOIN);
		criteria.createCriteria("seie.epiHistorico", "eh", Criteria.LEFT_JOIN);
		criteria.createCriteria("sei.epi", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seie.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "qtdEntregue");
		p.add(Projections.property("seie.dataEntrega"), "dataEntrega");
		p.add(Projections.property("e.nome"), "epiNome");
		p.add(Projections.property("eh.CA"), "CA");

		criteria.setProjection(p);

		criteria.add(Expression.eq("sei.id", solicitacaoEpiItemId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public int getTotalEntregue(Long solicitacaoEpiItemId, Long solicitacaoEpiItemEntregaId) 
	{
		String hql = "select sum(seie.qtdEntregue) from SolicitacaoEpiItemEntrega as seie where seie.solicitacaoEpiItem.id = :solicitacaoEpiItemId";
		if (solicitacaoEpiItemEntregaId != null)
			hql += " and seie.id <> :solicitacaoEpiItemEntregaId";
		
		Query query = getSession().createQuery(hql);
		query.setLong("solicitacaoEpiItemId", solicitacaoEpiItemId);

		if (solicitacaoEpiItemEntregaId != null)
			query.setLong("solicitacaoEpiItemEntregaId", solicitacaoEpiItemEntregaId);
		
		return query.uniqueResult() != null ? (Integer) query.uniqueResult() : 0;
	}

	public SolicitacaoEpiItemEntrega findByIdProjection(Long solicitacaoEpiItemEntregaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"seie");
		criteria.createCriteria("seie.solicitacaoEpiItem", "sei");
		criteria.createCriteria("seie.epiHistorico", "eh", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seie.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "qtdEntregue");
		p.add(Projections.property("seie.dataEntrega"), "dataEntrega");
		p.add(Projections.property("sei.id"), "projectionSolicitacaoEpiItemId");
		p.add(Projections.property("eh.id"), "projectionEpiHistoricoId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("seie.id", solicitacaoEpiItemEntregaId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (SolicitacaoEpiItemEntrega) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpi(Long solicitacaoEpiId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"seie");
		criteria.createCriteria("seie.solicitacaoEpiItem", "sei");
		criteria.createCriteria("sei.solicitacaoEpi", "se");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seie.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "qtdEntregue");
		p.add(Projections.property("seie.dataEntrega"), "dataEntrega");

		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
