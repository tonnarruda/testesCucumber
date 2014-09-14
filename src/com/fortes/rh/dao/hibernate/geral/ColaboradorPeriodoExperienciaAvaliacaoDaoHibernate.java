package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernate extends GenericDaoHibernate<ColaboradorPeriodoExperienciaAvaliacao> implements ColaboradorPeriodoExperienciaAvaliacaoDao
{

	public void removeByColaborador(Long... colaboradorIds) 
	{
		String queryHQL = "delete from ColaboradorPeriodoExperienciaAvaliacao c where c.colaborador.id in (:colaboradorIds)";
		getSession().createQuery(queryHQL).setParameterList("colaboradorIds", colaboradorIds).executeUpdate();	
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorPeriodoExperienciaAvaliacao.class);
		
		criteria.add(Expression.eq("colaborador.id", colaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradoresComAvaliacaoVencidaHoje() 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorPeriodoExperienciaAvaliacao(c.id, c.nome, c.contato.email, c.dataAdmissao, e.emailRemetente, a.id, a.titulo, pe.dias, e.id) ");
		hql.append("from ColaboradorPeriodoExperienciaAvaliacao as cpea ");
		hql.append("join cpea.periodoExperiencia as pe ");
		hql.append("join cpea.colaborador as c ");
		hql.append("join cpea.avaliacao as a ");
		hql.append("join c.empresa as e ");
		
		hql.append("where c.desligado = false ");
		hql.append("and cpea.tipo = 'C' ");
		hql.append("and pe.ativo = true ");
		hql.append("and c.id not in (select cq.colaborador.id from ColaboradorQuestionario cq where cq.avaliacao.id = a.id and cq.colaborador.id = c.id) ");
		
		Query query = getSession().createQuery(hql.toString());

		return query.list();
	}

	public void removeByAvaliacao(Long avaliacaoId) 
	{
		String queryHQL = "delete from ColaboradorPeriodoExperienciaAvaliacao c where c.avaliacao.id = :avaliacaoId";
		getSession().createQuery(queryHQL).setLong("avaliacaoId", avaliacaoId).executeUpdate();	
	}
}