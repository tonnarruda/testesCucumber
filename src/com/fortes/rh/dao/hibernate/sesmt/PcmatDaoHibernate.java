package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Pcmat;

@Component
public class PcmatDaoHibernate extends GenericDaoHibernate<Pcmat> implements PcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<Pcmat> findByObra(Long obraId) 
	{
		Criteria criteria = getSession().createCriteria(Pcmat.class, "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.aPartirDe"), "aPartirDe");
		p.add(Projections.property("p.dataIniObra"), "dataIniObra");
		p.add(Projections.property("p.dataFimObra"), "dataFimObra");
		p.add(Projections.property("p.obra.id"), "projectionIdObra");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("p.obra.id", obraId));

		criteria.addOrder(Order.asc("p.aPartirDe"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Pcmat.class));

		return criteria.list();
	}

	public Pcmat findUltimoHistorico(Long pcmatId, Long obraId) 
	{
		DetachedCriteria subQueryHist = DetachedCriteria.forClass(Pcmat.class, "ph")
				.setProjection(Projections.max("ph.aPartirDe"))
				.add(Restrictions.eqProperty("ph.obra.id", "p.obra.id"));

		if(pcmatId != null)
			subQueryHist.add(Expression.ne("ph.id", pcmatId));
		
		Criteria criteria = getSession().createCriteria(Pcmat.class, "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.aPartirDe"), "aPartirDe");
		criteria.setProjection(p);
		
		criteria.add(Property.forName("p.aPartirDe").eq(subQueryHist));
		criteria.add(Expression.eq("p.obra.id", obraId));
		
		if(pcmatId != null)
			criteria.add(Expression.ne("p.id", pcmatId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Pcmat.class));

		return (Pcmat) criteria.uniqueResult();
	}
}
