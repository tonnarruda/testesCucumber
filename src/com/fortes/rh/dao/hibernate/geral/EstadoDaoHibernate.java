package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Estado;

@Component
public class EstadoDaoHibernate extends GenericDaoHibernate<Estado> implements EstadoDao
{
	public Estado findBySigla(String sigla)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");

		criteria.add(Expression.eq("e.sigla", sigla));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Estado) criteria.uniqueResult();
	}
}