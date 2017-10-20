package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.ParteCorpoAtingidaDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;

public class ParteCorpoAtingidaDaoHibernate extends GenericDaoHibernate<ParteCorpoAtingida> implements ParteCorpoAtingidaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ParteCorpoAtingida> findAllSelect() {
		Criteria criteria = getSession().createCriteria(ParteCorpoAtingida.class,"p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.codigo"), "codigo");
		p.add(Projections.property("p.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("p.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ParteCorpoAtingida.class));
		
		return criteria.list();
	}
	
}
