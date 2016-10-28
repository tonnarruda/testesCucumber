package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.FaseDao;
import com.fortes.rh.model.sesmt.Fase;

@Component
public class FaseDaoHibernate extends GenericDaoHibernate<Fase> implements FaseDao
{
	@SuppressWarnings("unchecked")
	public Collection<Fase> findAllSelect(String descricao, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("descricao"), "descricao");
		criteria.setProjection(p);

		if (StringUtils.isNotBlank(descricao))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.descricao) ilike  normalizar(?)", "%" + descricao + "%", StandardBasicTypes.STRING));

		criteria.add(Expression.eq("empresa.id", empresaId));

		criteria.addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
