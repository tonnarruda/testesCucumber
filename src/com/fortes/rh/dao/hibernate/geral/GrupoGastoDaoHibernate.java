package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.GrupoGasto;

@Component
public class GrupoGastoDaoHibernate extends GenericDaoHibernate<GrupoGasto> implements GrupoGastoDao
{

	public GrupoGasto findByIdProjection(Long grupoGastoId)
	{
		Criteria criteria = getSession().createCriteria(GrupoGasto.class, "g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("g.id", grupoGastoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoGasto.class));

		return (GrupoGasto) criteria.uniqueResult();
	}
}