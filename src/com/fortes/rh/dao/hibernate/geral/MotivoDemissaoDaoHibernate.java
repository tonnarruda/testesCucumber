package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.MotivoDemissao;

@Component
public class MotivoDemissaoDaoHibernate extends GenericDaoHibernate<MotivoDemissao> implements MotivoDemissaoDao
{
	public Collection<MotivoDemissao> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(MotivoDemissao.class,"m");
		criteria.createCriteria("m.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.motivo"), "motivo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("emp.id", empresaId));

		criteria.addOrder(Order.asc("m.motivo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MotivoDemissao.class));
		
		return criteria.list();
	}
}