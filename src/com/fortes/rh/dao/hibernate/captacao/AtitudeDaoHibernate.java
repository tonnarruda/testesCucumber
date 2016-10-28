package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;

@Component
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
	
	public Collection<Atitude> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId)
	{
		if(areaOrganizacionalIds == null || areaOrganizacionalIds.length == 0)
			return new ArrayList();

		Criteria criteria = getSession().createCriteria(Atitude.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		criteria.setProjection(p);

		criteria.createCriteria("a.areaOrganizacionals","ao");
		criteria.add(Expression.in("ao.id", areaOrganizacionalIds));
		criteria.add(Expression.eq("empresa.id", empresasId));

		criteria.addOrder(Order.asc("a.nome"));
		criteria.setProjection(Projections.distinct(p));
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
	
	public Collection<Atitude> findSincronizarAtitudes(Long empresaOrigemId)
	{
		Criteria criteria = getSession().createCriteria(Atitude.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("a.observacao"), "observacao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.empresa.id", empresaOrigemId));

		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Atitude.class));

		return criteria.list();
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception
	{
		if (areaIds != null && areaIds.length > 0) {
			String[] sql = new String[] { "delete from atitude_areaorganizacional where areaorganizacionals_id in (" + StringUtils.join(areaIds, ",") + ");" };
			JDBCConnection.executeQuery(sql);
		}
	}

	public Atitude findByIdProjection(Long atitudeId)
	{
		Criteria criteria = CHAHelperDaoHibernate.montafindByIdProjection(atitudeId, getSession(), getEntityClass());
		
		return (Atitude) criteria.uniqueResult();
	}
}
