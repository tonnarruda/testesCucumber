package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.CodigoCBO;

public class CodigoCBODaoHibernate extends GenericDaoHibernate<CodigoCBO> implements CodigoCBODao
{
	public Collection<CodigoCBO> buscaCodigosCBO(String codigo, String descricao)
	{
		Criteria criteria = getSession().createCriteria(CodigoCBO.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.codigo"), "codigo");
		p.add(Projections.property("c.descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.ilike("c.codigo", codigo + "%"));
		criteria.add(Expression.ilike("c.descricao", "%" + descricao + "%"));
		
		criteria.addOrder(Order.asc("c.codigo"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CodigoCBO.class));

		return criteria.list();
	}

	public String findDescricaoByCodigo(String cboCodigo)
	{
		Criteria criteria = getSession().createCriteria(CodigoCBO.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.codigo", cboCodigo));
		
		return (String) criteria.uniqueResult();
	}
}
