package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;

public class AtitudeDaoHibernate extends GenericDaoHibernate<Atitude> implements AtitudeDao
{
	public Collection<Atitude> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Atitude.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Atitude.class));

		return criteria.list();
	}
	
	public Collection<Atitude> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Atitude(a.id, a.nome) ");
		hql.append("from Cargo as c ");
		hql.append("join c.atitudes as a ");
		hql.append("	where c.id = :cargoId ");
		hql.append("order by a.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}
}
