package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCriterioDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;

public class ConfiguracaoNivelCompetenciaCriterioDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetenciaCriterio> implements ConfiguracaoNivelCompetenciaCriterioDao {
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaCriterio> findByConfiguracaoNivelCompetencia(Long configuracaoNivelCompetenciaId, Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(ConfigHistoricoNivel.class, "chn2")
				.setProjection(Projections.property("chn2.id"))
				.add(Restrictions.eqProperty("chn2.nivelCompetenciaHistorico.id", "nch.id"))
				.add(Restrictions.isNull("chn2.percentual"));
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaCriterio.class, "cncc");
		criteria.createCriteria("cncc.nivelCompetencia", "n", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("n.configHistoricoNiveis", "chn", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("nch.configuracaoNivelCompetenciaFaixaSalariais", "cncf", CriteriaSpecification.INNER_JOIN);
		criteria.add(Subqueries.notExists(subQueryHc));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("criterioId"), "criterioId");
		p.add(Projections.property("criterioDescricao"), "criterioDescricao");
		p.add(Projections.property("configuracaoNivelCompetencia.id"), "configuracaoNivelCompetenciaId");
		p.add(Projections.property("n.id"), "nivelCompetenciaId");
		p.add(Projections.property("n.descricao"), "nivelCompetenciaDescricao");
		p.add(Projections.property("chn.ordem"), "nivelCompetenciaOrdem");
		p.add(Projections.property("chn.percentual"), "nivelCompetenciaPercentual");
		criteria.setProjection(p);
		
		criteria.add(Restrictions.eq("configuracaoNivelCompetencia.id", configuracaoNivelCompetenciaId));
		criteria.add(Restrictions.eq("cncf.id", configuracaoNivelCompetenciaFaixaSalarialId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}
	
	public void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) {
		StringBuilder hql = new StringBuilder("delete from ConfiguracaoNivelCompetenciaCriterio ");
			hql.append("where configuracaoNivelCompetencia.id in (  ");
			hql.append("	select id from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelCompetenciaColaboradorId ");
			hql.append(")  ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("configuracaoNivelCompetenciaColaboradorId", configuracaoNivelCompetenciaColaboradorId);

		query.executeUpdate();
	}
}
