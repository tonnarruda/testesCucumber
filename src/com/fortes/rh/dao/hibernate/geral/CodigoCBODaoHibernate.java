package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.CodigoCBO;

public class CodigoCBODaoHibernate extends GenericDaoHibernate<CodigoCBO> implements CodigoCBODao
{
	public Collection<String> buscaCodigosCBO(String codigo)
	{
		Criteria criteria = getSession().createCriteria(CodigoCBO.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.codigo"), "codigo");
		criteria.setProjection(p);
		
		criteria.add(Expression.ilike("c.codigo", codigo + "%"));
		criteria.addOrder(Order.asc("c.codigo"));

		return (Collection<String>)criteria.list();
	}
}
