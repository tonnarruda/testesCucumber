package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

@Component
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
			query.setParameterList("solicitacaoAvaliacaoIds", solicitacaoAvaliacaoIds, StandardBasicTypes.LONG);
			
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoAvaliacao> findAvaliacaoesNaoRespondidas(Long solicitacaoId, Long candidatoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new SolicitacaoAvaliacao(s.id, a.id, a.titulo) "); 
		hql.append("from Solicitacao s ");
		hql.append("inner join s.solicitacaoAvaliacaos sa "); 
		hql.append("inner join sa.avaliacao a ");
		hql.append("left join s.colaboradorQuestionarios cq with cq.candidato.id = :candidatoId and cq.solicitacao.id = s.id and cq.avaliacao.id = sa.avaliacao.id "); 
		hql.append("where s.id = :solicitacaoId ");
		hql.append("and cq.id is null ");
		hql.append("and sa.responderModuloExterno = true ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("candidatoId", candidatoId);
		query.setLong("solicitacaoId", solicitacaoId);
		
		return query.list();
	}
}