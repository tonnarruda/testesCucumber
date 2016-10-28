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
import com.fortes.rh.dao.sesmt.AreaVivenciaPcmatDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

@Component
public class AreaVivenciaPcmatDaoHibernate extends GenericDaoHibernate<AreaVivenciaPcmat> implements AreaVivenciaPcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<AreaVivenciaPcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "avp");
		criteria.createCriteria("avp.areaVivencia", "av");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("avp.id"), "id");
		p.add(Projections.property("avp.descricao"), "descricao");
		p.add(Projections.property("av.id"), "areaVivenciaId");
		p.add(Projections.property("av.nome"), "areaVivenciaNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("avp.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("av.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
