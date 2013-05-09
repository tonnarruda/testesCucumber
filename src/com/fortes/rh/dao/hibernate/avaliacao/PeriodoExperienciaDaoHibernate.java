package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public class PeriodoExperienciaDaoHibernate extends GenericDaoHibernate<PeriodoExperiencia> implements PeriodoExperienciaDao
{

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean ordenarDiasDesc)
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new PeriodoExperiencia(p.id, p.dias, p.descricao, p.empresa.id) ");
		hql.append("from PeriodoExperiencia p ");
		hql.append("where p.empresa.id = :empresaId ");
		hql.append("order by p.dias ");
		
		if(ordenarDiasDesc)
			hql.append(" desc");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<PeriodoExperiencia> findAllSelectDistinctDias(Long empresaId) {
		Criteria criteria = getSession().createCriteria(PeriodoExperiencia.class, "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("p.dias")), "dias");
		
		criteria.add(Expression.eq("p.empresa.id", empresaId));
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
}