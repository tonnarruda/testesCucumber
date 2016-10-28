/*
 * autor: Moesio Medeiros
 * Data: 06/06/2006
 * Requisito: RFA014
 */

package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;

@Component
@SuppressWarnings("unchecked")
public class AreaInteresseDaoHibernate extends GenericDaoHibernate<AreaInteresse> implements AreaInteresseDao
{
	public Collection<AreaInteresse> findAreasInteresseByAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		Criteria criteria = getSession().createCriteria(AreaInteresse.class, "ai");
		criteria.createCriteria("areasOrganizacionais", "ao");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ai.id"), "id");
		p.add(Projections.property("ai.nome"), "nome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("ao.id", areaOrganizacional.getId()));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaInteresse.class));

		return criteria.list();
	}
	
	public Collection<AreaInteresse> findAllSelect(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(AreaInteresse.class, "ai");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ai.id"), "id");
		p.add(Projections.property("ai.nome"), "nome");

		criteria.setProjection(p);
		if(empresaIds != null && empresaIds.length > 0)
			criteria.add(Expression.in("ai.empresa.id", empresaIds));

		criteria.addOrder(Order.asc("ai.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaInteresse.class));

		return criteria.list();
	}

	public AreaInteresse findByIdProjection(Long areaInteresseId)
	{
		Criteria criteria = getSession().createCriteria(AreaInteresse.class, "ai");
		criteria.createCriteria("empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ai.id"), "id");
		p.add(Projections.property("ai.nome"), "nome");
		p.add(Projections.property("ai.observacao"), "observacao");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("ai.id", areaInteresseId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaInteresse.class));
		
		return (AreaInteresse) criteria.uniqueResult();
	}

	
	public Collection<AreaInteresse> findSincronizarAreasInteresse(Long empresaOrigemId) {
		
		Criteria criteria = getSession().createCriteria(AreaInteresse.class, "ai");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ai.id"), "id");
		p.add(Projections.property("ai.nome"), "nome");
		p.add(Projections.property("ai.observacao"), "observacao");

		criteria.setProjection(p);
		criteria.add(Expression.eq("ai.empresa.id", empresaOrigemId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaInteresse.class));

		return criteria.list();
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		if(areaIds != null && areaIds.length > 0)
		{
			String[] sql = new String[] {"delete from areainteresse_areaorganizacional where areasorganizacionais_id in ("+StringUtils.join(areaIds, ",")+");"};
			
			JDBCConnection.executeQuery(sql);
		}
	}
}