package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;
import com.fortes.rh.model.sesmt.NaturezaLesao;

@Component
@SuppressWarnings("unchecked")
public class NaturezaLesaoDaoHibernate extends GenericDaoHibernate<NaturezaLesao> implements NaturezaLesaoDao
{
	public Collection<NaturezaLesao> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "nl");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("nl.id"), "id");
		p.add(Projections.property("nl.descricao"), "descricao");

		criteria.setProjection(p);
		
		criteria.add(Restrictions.eq("nl.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("nl.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
