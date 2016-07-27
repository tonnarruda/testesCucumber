package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AtividadeSegurancaPcmatDao;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;

public class AtividadeSegurancaPcmatDaoHibernate extends GenericDaoHibernate<AtividadeSegurancaPcmat> implements AtividadeSegurancaPcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<AtividadeSegurancaPcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "asp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("asp.id"), "id");
		p.add(Projections.property("asp.nome"), "nome");
		p.add(Projections.property("asp.data"), "data");
		p.add(Projections.property("asp.cargaHoraria"), "cargaHoraria");
		criteria.setProjection(p);

		criteria.add(Expression.eq("asp.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("asp.data"));
		criteria.addOrder(Order.asc("asp.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
