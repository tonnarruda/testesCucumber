package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;
import com.fortes.rh.model.sesmt.EpcPcmat;

public class EpcPcmatDaoHibernate extends GenericDaoHibernate<EpcPcmat> implements EpcPcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<EpcPcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ep");
		criteria.createCriteria("ep.epc", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ep.id"), "id");
		p.add(Projections.property("ep.descricao"), "descricao");
		p.add(Projections.property("e.id"), "epcId");
		p.add(Projections.property("e.descricao"), "epcDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ep.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("e.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
