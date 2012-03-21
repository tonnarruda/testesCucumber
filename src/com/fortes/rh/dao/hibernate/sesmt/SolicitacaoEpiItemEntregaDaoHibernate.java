package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public class SolicitacaoEpiItemEntregaDaoHibernate extends GenericDaoHibernate<SolicitacaoEpiItemEntrega> implements SolicitacaoEpiItemEntregaDao
{
	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) 
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new SolicitacaoEpiItemEntrega(se.id, se.qtdEntregue, se.dataEntrega, eh.CA)");
		hql.append("  from SolicitacaoEpiItemEntrega se ");
		hql.append("  left join se.solicitacaoEpiItem si ");
		hql.append("  left join si.epi e ");
		hql.append("  left join e.epiHistoricos eh ");
		hql.append(" where (eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                   where eh2.epi.id = e.id");
		hql.append("                     and eh2.data <= se.dataEntrega) ");
		hql.append("   or  eh.data is null) ");
		hql.append("   and se.solicitacaoEpiItem.id = :solicitacaoEpiItemId  ");
		
		hql.append(" order by se.dataEntrega ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("solicitacaoEpiItemId", solicitacaoEpiItemId);

		return query.list();
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

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seie.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "qtdEntregue");
		p.add(Projections.property("seie.dataEntrega"), "dataEntrega");
		p.add(Projections.property("sei.id"), "projectionSolicitacaoEpiItemId");

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
