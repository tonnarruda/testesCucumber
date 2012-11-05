package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
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
	public Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId) 
	{
		Criteria criteria = getSession().createCriteria(SolicitacaoAvaliacao.class, "sa");
		
		criteria.add(Expression.eq("sa.solicitacao.id", solicitacaoId));
		
		return criteria.list();
	}
}