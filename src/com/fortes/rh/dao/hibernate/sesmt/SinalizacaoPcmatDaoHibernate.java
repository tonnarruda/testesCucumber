package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SinalizacaoPcmatDao;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

@Component
public class SinalizacaoPcmatDaoHibernate extends GenericDaoHibernate<SinalizacaoPcmat> implements SinalizacaoPcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<SinalizacaoPcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "sp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sp.id"), "id");
		p.add(Projections.property("sp.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("sp.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("sp.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
