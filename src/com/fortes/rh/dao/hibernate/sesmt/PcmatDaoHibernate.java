package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.PcmatDao;

public class PcmatDaoHibernate extends GenericDaoHibernate<Pcmat> implements PcmatDao
{

	@SuppressWarnings("unchecked")
	public Collection<Pcmat> findAllSelect(String nomeObra, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Pcmat.class, "p");
		criteria.createCriteria("p.obra", "o", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.apartirDe"), "apartirDe");
		p.add(Projections.property("p.dataIniObra"), "dataIniObra");
		p.add(Projections.property("p.dataFimObra"), "dataFimObra");
		p.add(Projections.property("o.id"), "projectionIdObra");
		p.add(Projections.property("o.nome"), "projectionNomeObra");
		criteria.setProjection(p);
		
		if (StringUtils.isNotBlank(nomeObra))
			criteria.add(Restrictions.sqlRestriction("normalizar(o.nome) ilike  normalizar(?)", "%" + nomeObra + "%", Hibernate.STRING));

		criteria.add(Expression.eq("o.empresa.id", empresaId));

		criteria.addOrder(Order.asc("o.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Pcmat.class));

		return criteria.list();
	}
	
	
}
