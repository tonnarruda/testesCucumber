package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;

@Component
public class ConfiguracaoLimiteColaboradorDaoHibernate extends GenericDaoHibernate<ConfiguracaoLimiteColaborador> implements ConfiguracaoLimiteColaboradorDao
{

	public Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.descricao"), "descricao");
		p.add(Projections.property("ao.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.property("ao.nome"), "projectionAreaOrganizacionalNome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("ao.empresa.id", empresaId));
		criteria.addOrder(Order.asc("c.descricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoLimiteColaborador.class));

		return criteria.list();
	}

	public Collection<Long> findIdAreas(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "projectionAreaOrganizacionalId");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("ao.empresa.id", empresaId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		if(areaIds != null && areaIds.length > 0)
		{
			String hql = "delete ConfiguracaoLimiteColaborador where areaOrganizacional.id in (:areaIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("areaIds", areaIds, StandardBasicTypes.LONG);
			query.executeUpdate();		
		}
	}
}
