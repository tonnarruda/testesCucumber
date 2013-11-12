package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.sesmt.Obra;

public class ObraDaoHibernate extends GenericDaoHibernate<Obra> implements ObraDao
{
	@SuppressWarnings("unchecked")
	public Collection<Obra> findAllSelect(String nome, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Obra.class, "o");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.nome"), "nome");
		p.add(Projections.property("o.endereco"), "endereco");
		criteria.setProjection(p);
		
		if (StringUtils.isNotBlank(nome))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nome + "%", Hibernate.STRING));

		criteria.add(Expression.eq("o.empresa.id", empresaId));

		criteria.addOrder(Order.asc("o.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Obra.class));

		return criteria.list();
	}
}
