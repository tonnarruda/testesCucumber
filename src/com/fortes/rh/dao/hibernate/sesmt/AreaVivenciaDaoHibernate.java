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
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.model.sesmt.AreaVivencia;

@Component
@SuppressWarnings("unchecked")
public class AreaVivenciaDaoHibernate extends GenericDaoHibernate<AreaVivencia> implements AreaVivenciaDao
{
	public Collection<AreaVivencia> findAllSelect(String nome, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("nome"), "nome");
		criteria.setProjection(p);

		if (StringUtils.isNotBlank(nome))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nome + "%", StandardBasicTypes.STRING));

		criteria.add(Expression.eq("empresa.id", empresaId));

		criteria.addOrder(Order.asc("nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public AreaVivencia findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "av");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("av.id"), "id");
		p.add(Projections.property("av.nome"), "nome");
		p.add(Projections.property("av.empresa.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("av.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (AreaVivencia) criteria.uniqueResult();
	}
}
