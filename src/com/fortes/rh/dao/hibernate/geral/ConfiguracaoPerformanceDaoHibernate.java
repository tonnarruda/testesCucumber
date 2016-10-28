package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoPerformanceDao;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

@Component
public class ConfiguracaoPerformanceDaoHibernate extends GenericDaoHibernate<ConfiguracaoPerformance> implements ConfiguracaoPerformanceDao
{

	public void removeByUsuario(Long usuarioId) 
	{
		String hql = "delete ConfiguracaoPerformance where usuario.id = :usuarioId";
		Query query = getSession().createQuery(hql);
		query.setLong("usuarioId", usuarioId);

		query.executeUpdate();
	}

	public Collection<ConfiguracaoPerformance> findByUsuario(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("caixa"), "caixa");
		p.add(Projections.property("ordem"), "ordem");
		p.add(Projections.property("aberta"), "aberta");

		criteria.setProjection(p);

		criteria.add(Expression.eq("usuario.id", id));
		criteria.addOrder(Order.asc("ordem"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
