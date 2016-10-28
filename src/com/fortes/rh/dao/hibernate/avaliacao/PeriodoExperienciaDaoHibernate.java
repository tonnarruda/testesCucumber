package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

@Component
public class PeriodoExperienciaDaoHibernate extends GenericDaoHibernate<PeriodoExperiencia> implements PeriodoExperienciaDao
{

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean ordenarDiasDesc, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");

		ProjectionList p = montaProjection();
		
		if (ativo != null)
			criteria.add(Expression.eq("p.ativo", ativo));
		
		criteria.add(Expression.eq("p.empresa.id", empresaId));
		
		if(ordenarDiasDesc)
			criteria.addOrder(Order.desc("p.dias"));
		else
			criteria.addOrder(Order.asc("p.dias"));
		
		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findAllSelectDistinctDias(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("p.dias")), "dias");
		
		criteria.add(Expression.eq("p.empresa.id", empresaId));
		criteria.add(Expression.eq("p.ativo", true));
		criteria.addOrder(Order.asc("p.dias"));
		
		criteria.setProjection(p);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PeriodoExperiencia.class));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findByIdsOrderDias(Long[] periodoExperienciaIds) 
	{
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.dias"), "dias");
		p.add(Projections.property("p.descricao"), "descricao");
		
		criteria.setProjection(p);
		criteria.add(Expression.in("p.id",  periodoExperienciaIds));
		
		criteria.addOrder(Order.asc("p.dias"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PeriodoExperiencia.class));

		return criteria.list();
	 }

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findAllAtivos() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.dias"), "dias");
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.notificarSomentePeriodosConfigurados"), "empresaNotificarSomentePeriodosConfigurados");
		
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");
		criteria.createCriteria("p.empresa", "e", Criteria.INNER_JOIN);
		criteria.add(Expression.eq("p.ativo", true));
		criteria.setProjection(p);
		
		criteria.addOrder(Order.asc("p.dias"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PeriodoExperiencia.class));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodoDaAvaliacaoId(Long empresaId, Long avaliacaoId) {
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");
		criteria.add(Expression.eq("p.empresa.id", empresaId));

		if(avaliacaoId != null){
			DetachedCriteria subquery =  DetachedCriteria.forClass(Avaliacao.class, "av")
			.setProjection(Projections.property("periodoExperiencia.id"))
			.add(Restrictions.eq("av.id", avaliacaoId));
			criteria.add(Expression.or(Expression.eq("p.ativo",true), Subqueries.propertyEq("p.id",subquery)));
		}
		else
			criteria.add(Expression.eq("p.ativo", true));
		
		criteria.addOrder(Order.asc("p.dias"));
		criteria.setProjection(montaProjection());
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PeriodoExperiencia.class));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(Long empresaId, Long colaboradorId) {
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");
		criteria.add(Expression.eq("p.empresa.id", empresaId));
		
		if(colaboradorId != null){
			DetachedCriteria subquery =  DetachedCriteria.forClass(ColaboradorPeriodoExperienciaAvaliacao.class, "cpa")
			.setProjection(Projections.property("cpa.periodoExperiencia.id"))
			.add(Restrictions.eq("cpa.colaborador.id", colaboradorId));
			criteria.add(Expression.or(Expression.eq("p.ativo",true), Subqueries.propertyIn("p.id",subquery)));
		}
		else
			criteria.add(Expression.eq("p.ativo", true));
		
		criteria.addOrder(Order.asc("p.dias"));
		criteria.setProjection(montaProjection());
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PeriodoExperiencia.class));
		return criteria.list();
	}

	private ProjectionList montaProjection() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.id(),"id");
		p.add(Projections.property("p.dias"),"dias");
		p.add(Projections.property("p.ativo"),"ativo");
		p.add(Projections.property("p.empresa.id"),"empresaId");
		p.add(Projections.property("p.descricao"),"descricao");
		return p;
	}
}