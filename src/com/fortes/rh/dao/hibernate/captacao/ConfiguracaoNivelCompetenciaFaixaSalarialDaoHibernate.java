package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public class ConfiguracaoNivelCompetenciaFaixaSalarialDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetenciaFaixaSalarial> implements ConfiguracaoNivelCompetenciaFaixaSalarialDao
{
	public void deleteByFaixaSalarial(Long[] faixaIds)
	{
		if (faixaIds != null && faixaIds.length > 0) {
			String hql = "delete ConfiguracaoNivelCompetenciaFaixaSalarial where faixaSalarial.id in (:faixaIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("faixaIds", faixaIds, Hibernate.LONG);
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> findProximasConfiguracoesAposData(Long faixaSalarialId, Date dataConfiguracaoExcluir)
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncfs.data"), "data");

		criteria.setProjection(p);
		
		criteria.add(Expression.gt("cncfs.data", dataConfiguracaoExcluir));
		criteria.add(Expression.eq("cncfs.faixaSalarial.id", faixaSalarialId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaFaixaSalarial.class));	
		
		return criteria.list();
	}

	public boolean existByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList.add(Projections.property("cncfs.id"), "id");
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs");
		criteria.add(Expression.eq("cncfs.nivelCompetenciaHistorico.id", nivelCompetenciaHistoricoId));
		criteria.setProjection(projectionList);
		criteria.setMaxResults(1);
		
		return criteria.list().size() > 0;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial findByFaixaSalarialIdAndData(Long faixaSalarialId, Date data) {
		DetachedCriteria subQuery = DetachedCriteria.forClass(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncf2")
				.setProjection(Projections.max("cncf2.data"))
				.add(Restrictions.eq("cncf2.faixaSalarial.id", faixaSalarialId))
				.add(Restrictions.le("cncf2.data", data != null ? data : new Date()));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncf.id"), "id");
		p.add(Projections.property("cncf.data"), "data");
		p.add(Projections.property("cncf.nivelCompetenciaHistorico.id"), "nivelCompetenciaHistoricoId");

		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncf");
		criteria.add(Expression.eq("cncf.faixaSalarial.id", faixaSalarialId));
		criteria.add(Subqueries.propertyEq("cncf.data", subQuery));

		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaFaixaSalarial.class));
		return (ConfiguracaoNivelCompetenciaFaixaSalarial)criteria.uniqueResult();
	}
}
