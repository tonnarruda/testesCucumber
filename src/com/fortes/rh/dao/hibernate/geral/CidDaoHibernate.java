package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CidDao;
import com.fortes.rh.model.geral.Cid;

public class CidDaoHibernate extends GenericDaoHibernate<Cid> implements CidDao
{
	public Collection<Cid> buscaCids(String codigo, String descricao)
	{
		Criteria criteria = getSession().createCriteria(Cid.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.codigo"), "codigo");
		p.add(Projections.property("c.descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.ilike("c.codigo", "%" + codigo + "%"));
		criteria.add(Restrictions.sqlRestriction("normalizar(this_.descricao) ilike  normalizar(?)", "%" + descricao + "%", Hibernate.STRING));
		
		criteria.addOrder(Order.asc("c.codigo"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cid.class));

		return criteria.list();
	}

	public String findDescricaoByCodigo(String codigo)
	{
		Criteria criteria = getSession().createCriteria(Cid.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.codigo", codigo));
		
		return (String) criteria.uniqueResult();
	}
}
