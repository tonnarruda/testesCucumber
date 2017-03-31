package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernate extends GenericDaoHibernate<ColaboradorPeriodoExperienciaAvaliacao> implements ColaboradorPeriodoExperienciaAvaliacaoDao
{

	public void removeByColaborador(Character tipoColaboradorPeriodoExperienciaAvaliacao, Long... colaboradorIds) 
	{
		String queryHQL = "delete from ColaboradorPeriodoExperienciaAvaliacao c where c.colaborador.id in (:colaboradorIds) ";
		
		if(tipoColaboradorPeriodoExperienciaAvaliacao != null)
			queryHQL += " and c.tipo = :tipoColaboradorPeriodoExperienciaAvaliacao ";
		
		Query query = getSession().createQuery(queryHQL);
		query.setParameterList("colaboradorIds", colaboradorIds);
		
		if(tipoColaboradorPeriodoExperienciaAvaliacao != null)
			query.setCharacter("tipoColaboradorPeriodoExperienciaAvaliacao", tipoColaboradorPeriodoExperienciaAvaliacao);	
		
		query.executeUpdate();	
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
	public Collection<ColaboradorPeriodoExperienciaAvaliacao> findColaboradoresComAvaliacaoNaoRespondida() 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorPeriodoExperienciaAvaliacao(c.id, c.nome, c.contato.email, c.dataAdmissao, e.emailRemetente, a.id, a.titulo, pe.dias, e.id, fs.nome, ca.nome) ");
		hql.append("from ColaboradorPeriodoExperienciaAvaliacao as cpea ");
		hql.append("join cpea.periodoExperiencia as pe ");
		hql.append("join cpea.colaborador as c ");
		hql.append("left join c.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("join cpea.avaliacao as a ");
		hql.append("join c.empresa as e ");
		
		hql.append("where (c.dataDesligamento >= current_date or c.dataDesligamento is null) ");
		
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 "); 
		hql.append("		where hc2.colaborador.id = c.id ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");
		
		hql.append("and cpea.tipo = 'C' ");
		hql.append("and pe.ativo = true ");
		hql.append("and c.id not in (select cq.colaborador.id from ColaboradorQuestionario cq where cq.avaliacao.id = a.id and cq.colaborador.id = c.id and cq.avaliacaoDesempenho.id is null) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public void removeByAvaliacao(Long avaliacaoId) 
	{
		String queryHQL = "delete from ColaboradorPeriodoExperienciaAvaliacao c where c.avaliacao.id = :avaliacaoId";
		getSession().createQuery(queryHQL).setLong("avaliacaoId", avaliacaoId).executeUpdate();	
	}
}