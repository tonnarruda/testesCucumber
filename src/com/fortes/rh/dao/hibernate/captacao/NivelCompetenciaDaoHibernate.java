package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaDaoHibernate extends GenericDaoHibernate<NivelCompetencia> implements NivelCompetenciaDao
{
	public Collection<NivelCompetencia> findAllSelect(Long empresaId) {
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class,"nc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("nc.id"), "id");
		p.add(Projections.property("nc.ordem"), "ordem");
		p.add(Projections.property("nc.descricao"), "descricao");

		criteria.setProjection(p);

		if(empresaId != null)
			criteria.add(Expression.eq("nc.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetencia.class));
		criteria.addOrder(Order.asc("nc.ordem"));

		return criteria.list();
	}
}
