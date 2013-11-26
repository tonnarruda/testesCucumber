package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public class RiscoFasePcmatDaoHibernate extends GenericDaoHibernate<RiscoFasePcmat> implements RiscoFasePcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfp");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfp.id"), "id");
		p.add(Projections.property("rfp.risco.id"), "riscoId");
		p.add(Projections.property("rfp.fasePcmat.id"), "fasePcmatId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("rfp.fasePcmat.id", fasePcmatId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
