package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public class ConfigHistoricoNivelDaoHibernate extends GenericDaoHibernate<ConfigHistoricoNivel> implements ConfigHistoricoNivelDao
{
	@SuppressWarnings("unchecked")
	public Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		Criteria criteria = getSession().createCriteria(ConfigHistoricoNivel.class, "chn");
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetencia", "nc", CriteriaSpecification.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("chn.id"), "id");
		p.add(Projections.property("chn.ordem"), "ordem");
		p.add(Projections.property("chn.percentual"), "percentual");
		p.add(Projections.property("nch.data"), "nivelCompetenciaHistoricoData");
		p.add(Projections.property("nc.descricao"), "nivelCompetenciaDescricao");

		criteria.setProjection(p);

		criteria.add(Expression.eq("nch.id", nivelCompetenciaHistoricoId));
		criteria.addOrder(Order.asc("chn.ordem"));
		criteria.addOrder(Order.asc("nc.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfigHistoricoNivel.class));

		return criteria.list();
	}
}
