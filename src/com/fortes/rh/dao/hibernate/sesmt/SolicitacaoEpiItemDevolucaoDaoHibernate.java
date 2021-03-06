package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

@SuppressWarnings("unchecked")
public class SolicitacaoEpiItemDevolucaoDaoHibernate extends GenericDaoHibernate<SolicitacaoEpiItemDevolucao> implements SolicitacaoEpiItemDevolucaoDao
{
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		
		Criteria criteria = getSession().createCriteria(SolicitacaoEpiItemDevolucao.class, "seid");
		criteria.setProjection(Projections.sum("seid.qtdDevolvida"));
		
		criteria.add(Expression.eq("solicitacaoEpiItem.id", solicitacaoEpiItemId));
		
		if(solicitacaoEpiItemDevolucaoId != null)
			criteria.add(Expression.ne("seid.id", solicitacaoEpiItemDevolucaoId));
		
		return criteria.uniqueResult() != null ? (Integer) criteria.uniqueResult() : 0;
	}

	public Collection<SolicitacaoEpiItemDevolucao> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) {
		Criteria criteria = getSession().createCriteria(SolicitacaoEpiItemDevolucao.class, "seid");
		criteria.add(Expression.eq("seid.solicitacaoEpiItem.id", solicitacaoEpiItemId));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("seid.id"), "id");
		p.add(Projections.property("seid.qtdDevolvida"), "qtdDevolvida");
		p.add(Projections.property("seid.dataDevolucao"), "dataDevolucao");
		
		criteria.setProjection(p);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoEpiItemDevolucao.class));

		return criteria.list();
	}

	public Integer findQtdDevolvidaByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		Criteria criteria = getSession().createCriteria(SolicitacaoEpiItemDevolucao.class, "seid");
		criteria.setProjection(Projections.sum("seid.qtdDevolvida"));
		
		criteria.add(Expression.eq("solicitacaoEpiItem.id", solicitacaoEpiItemId));
		criteria.add(Expression.le("dataDevolucao", data));
		
		if(solicitacaoEpiItemDevolucaoId != null)
			criteria.add(Expression.ne("seid.id", solicitacaoEpiItemDevolucaoId));
		
		return criteria.uniqueResult() != null ? (Integer) criteria.uniqueResult() : 0;
	}
	
	public Collection<SolicitacaoEpiItemDevolucao> findQtdDevolvidaBySolicitacaoItemIds(Long[] solicitacaoEpiItensId) {
		Criteria criteria = getSession().createCriteria(SolicitacaoEpiItemDevolucao.class, "seid");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.groupProperty("solicitacaoEpiItem.id"), "solicitacaoEpiItemId");
		p.add(Projections.sum("seid.qtdDevolvida"), "qtdDevolvida");
		criteria.setProjection(p);
		
		criteria.add(Expression.in("solicitacaoEpiItem.id", solicitacaoEpiItensId));
		
		criteria.addOrder(Order.asc("solicitacaoEpiItem.id"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoEpiItemDevolucao.class));
		
		return criteria.list();
	}
}
