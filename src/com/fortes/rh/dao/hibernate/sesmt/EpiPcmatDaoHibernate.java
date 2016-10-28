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
import com.fortes.rh.dao.sesmt.EpiPcmatDao;
import com.fortes.rh.model.sesmt.EpiPcmat;

@Component
@SuppressWarnings("unchecked")
public class EpiPcmatDaoHibernate extends GenericDaoHibernate<EpiPcmat> implements EpiPcmatDao
{

	public Collection<EpiPcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ep");
		criteria.createCriteria("ep.epi", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ep.id"), "id");
		p.add(Projections.property("ep.atividades"), "atividades");
		p.add(Projections.property("e.id"), "epiId");
		p.add(Projections.property("e.nome"), "epiNome");
		p.add(Projections.property("e.descricao"), "epiDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ep.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
