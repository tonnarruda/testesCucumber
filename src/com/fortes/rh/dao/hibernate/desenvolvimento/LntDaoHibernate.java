package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusLnt;

public class LntDaoHibernate extends GenericDaoHibernate<Lnt> implements LntDao
{
	@SuppressWarnings("unchecked")
	public Collection<Lnt> findAllLnt(String descricao, char status, int page, int pagingSize)
	{
		Criteria criteria = getSession().createCriteria(Lnt.class, "lnt");
	
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("lnt.id"), "id");
		p.add(Projections.property("lnt.descricao"), "descricao");
		p.add(Projections.property("lnt.dataInicio"), "dataInicio");
		p.add(Projections.property("lnt.dataFim"), "dataFim");
		p.add(Projections.property("lnt.dataFinalizada"), "dataFinalizada");
		criteria.setProjection(p);
		
		if (descricao != null && !descricao.trim().isEmpty())
			criteria.add(Expression.ilike("lnt.descricao", descricao, MatchMode.ANYWHERE));

		defineCondicaoPorStatus(status, criteria);

		if (page != 0 && pagingSize != 0) {
			criteria.setMaxResults(pagingSize);
			criteria.setFirstResult((page - 1) * pagingSize);
		}

		criteria.addOrder(Order.desc("lnt.dataInicio"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Lnt.class));

		return criteria.list();
	}
	
	private void defineCondicaoPorStatus(char status, Criteria criteria)
	{
		if(status == StatusLnt.NAO_INICIADA) {
			criteria.add(Expression.gt("lnt.dataInicio", new Date()));
		} else if(status == StatusLnt.EM_PLANEJAMENTO) {
			criteria.add(Expression.le("lnt.dataInicio", new Date()));
			criteria.add(Expression.ge("lnt.dataFim", new Date()));
		} else if(status == StatusLnt.EM_ANALISE) {
			criteria.add(Expression.lt("lnt.dataFim", new Date()));
			criteria.add(Expression.isNull("lnt.dataFinalizada"));
		} else if(status == StatusLnt.FINALIZADA) { 
			criteria.add(Expression.isNotNull("lnt.dataFinalizada"));
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Lnt> findLntsNaoFinalizadas(Date dataInicio)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("lnt.id"), "id");
		p.add(Projections.property("lnt.descricao"), "descricao");
		p.add(Projections.property("lnt.dataInicio"), "dataInicio");
		p.add(Projections.property("lnt.dataFim"), "dataFim");
		p.add(Projections.property("lnt.dataFinalizada"), "dataFinalizada");

		Criteria criteria = getSession().createCriteria(Lnt.class, "lnt");
		
		if(dataInicio != null)
			criteria.add(Expression.eq("lnt.dataInicio", dataInicio));
		
		criteria.add(Expression.isNull("lnt.dataFinalizada"));

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Lnt.class));

		return criteria.list();
	}

	public void finalizar(Long lntId)
	{
		Query query = getSession().createQuery("update Lnt set dataFinalizada = current_date where id = :lntId");
		query.setLong("lntId", lntId);

		query.executeUpdate();
	}
	
	public void reabrir(Long lntId)
	{
		Query query = getSession().createQuery("update Lnt set dataFinalizada = null where id = :lntId");
		query.setLong("lntId", lntId);

		query.executeUpdate();
	}

	@SuppressWarnings({ "unchecked" })
	public Collection<Lnt> findPossiveisLntsColaborador(Long cursoId, Long colaboradorId) {
		Criteria criteria = getSession().createCriteria(ParticipanteCursoLnt.class, "pl");
		criteria.createCriteria("pl.cursoLnt", "cl");
		criteria.createCriteria("cl.lnt", "l");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("l.id"), "id");
		p.add(Projections.property("l.descricao"), "descricao");
		
		criteria.add(Expression.eq("pl.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cl.curso.id", cursoId));
		criteria.add(Expression.isNotNull("l.dataFinalizada"));

		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.desc("l.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Lnt.class));

		return criteria.list();
	}

	public Long findLntColaboradorParticpa(Long cursoId, Long colaboradorId) {
		Criteria criteria = getSession().createCriteria(ParticipanteCursoLnt.class, "pl");
		criteria.createCriteria("pl.cursoLnt", "cl");
		criteria.createCriteria("cl.lnt", "l");
		criteria.createCriteria("cl.colaboradorTurmas", "ct");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("l.id"), "id");
		
		criteria.add(Expression.eqProperty("pl.colaborador.id", "ct.colaborador.id"));
		criteria.add(Expression.eq("pl.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cl.curso.id", cursoId));
		criteria.add(Expression.isNotNull("l.dataFinalizada"));

		criteria.setMaxResults(1);
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if(criteria.uniqueResult() == null)
			return null;
		
		return (Long) criteria.uniqueResult();
	}
}
