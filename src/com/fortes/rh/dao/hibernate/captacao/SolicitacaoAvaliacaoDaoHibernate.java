package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

public class SolicitacaoAvaliacaoDaoHibernate extends GenericDaoHibernate<SolicitacaoAvaliacao> implements SolicitacaoAvaliacaoDao
{
	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		Query query = getSession().createQuery("delete from SolicitacaoAvaliacao sa where sa.solicitacao.id = :solicitacaoId");
		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId, Boolean responderModuloExterno) 
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoAvaliacao.class, "sa");
		
		criteria.add(Expression.eq("sa.solicitacao.id", solicitacaoId));

		if (responderModuloExterno != null)
			criteria.add(Expression.eq("sa.responderModuloExterno", responderModuloExterno));
		
		return criteria.list();
	}

	public void setResponderModuloExterno(Long solicitacaoId, Long[] solicitacaoAvaliacaoIds, boolean responderModuloExterno) 
	{
		StringBuilder hql = new StringBuilder("update SolicitacaoAvaliacao set responderModuloExterno = :responderModuloExterno where solicitacao.id = :solicitacaoId ");
		
		if (solicitacaoAvaliacaoIds != null && solicitacaoAvaliacaoIds.length > 0)
			hql.append("and id in (:solicitacaoAvaliacaoIds)");
		
		Query query = getSession().createQuery(hql.toString());
		query.setBoolean("responderModuloExterno", responderModuloExterno);
		query.setLong("solicitacaoId", solicitacaoId);

		if (solicitacaoAvaliacaoIds != null && solicitacaoAvaliacaoIds.length > 0)
			query.setParameterList("solicitacaoAvaliacaoIds", solicitacaoAvaliacaoIds, Hibernate.LONG);
			
		query.executeUpdate();
	}
}