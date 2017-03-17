package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

@Component
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
		p.add(Projections.property("nch.id"), "nivelCompetenciaHistoricoId");
		p.add(Projections.property("nch.data"), "nivelCompetenciaHistoricoData");
		p.add(Projections.property("nc.id"), "nivelCompetenciaId");
		p.add(Projections.property("nc.descricao"), "nivelCompetenciaDescricao");

		criteria.setProjection(p);

		criteria.add(Expression.eq("nch.id", nivelCompetenciaHistoricoId));
		criteria.addOrder(Order.asc("chn.ordem"));
		criteria.addOrder(Order.asc("nc.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfigHistoricoNivel.class));

		return criteria.list();
	}

	public void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId) {
		String queryHQL = "DELETE FROM ConfigHistoricoNivel chn where chn.nivelCompetenciaHistorico.id = :nivelConfiguracaoHIstoricoId";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("nivelConfiguracaoHIstoricoId", nivelConfiguracaoHIstoricoId);
		query.executeUpdate();
	}

	public void removeNotIn(Long[] configHistoricoNiveisIds, Long nivelConfiguracaoHistoricoId) {
		String queryHQL = "DELETE FROM ConfigHistoricoNivel chn "
				+ "where chn.id not in(:configHistoricoNiveisIds) and chn.nivelCompetenciaHistorico.id = :nivelConfiguracaoHistoricoId ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("nivelConfiguracaoHistoricoId", nivelConfiguracaoHistoricoId);
		query.setParameterList("configHistoricoNiveisIds", configHistoricoNiveisIds);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfigHistoricoNivel> findByEmpresaAndDataDoNivelCompetenciaHistorico(Long empresaId, Date dataNivelCompetenciaHistorico) {

		DetachedCriteria subQueryHc = DetachedCriteria.forClass(NivelCompetenciaHistorico.class, "nch2")
				.setProjection(Projections.max("nch2.data"))
				.add(Restrictions.eqProperty("nch2.empresa.id", "nc.empresa.id"))
				.add(Restrictions.le("nch2.data", dataNivelCompetenciaHistorico));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("chn.id"), "id");
		p.add(Projections.property("chn.ordem"), "ordem");
		p.add(Projections.property("chn.percentual"), "percentual");
		p.add(Projections.property("nc.id"), "nivelCompetenciaId");
		p.add(Projections.property("nc.descricao"), "nivelCompetenciaDescricao");
		
		Criteria criteria = getSession().createCriteria(ConfigHistoricoNivel.class, "chn");
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetencia", "nc", CriteriaSpecification.INNER_JOIN);

		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		criteria.add(Subqueries.propertyEq("nch.data", subQueryHc));
		
		criteria.addOrder(Order.asc("chn.ordem"));
		criteria.addOrder(Order.asc("nc.descricao"));

		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfigHistoricoNivel.class));

		return criteria.list();
	}
}
