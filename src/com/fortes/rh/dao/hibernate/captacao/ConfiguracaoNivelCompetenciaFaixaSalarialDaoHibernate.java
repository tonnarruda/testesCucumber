package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs");
		criteria.add(Expression.eq("cncfs.nivelCompetenciaHistorico.id", nivelCompetenciaHistoricoId));
		
		return criteria.list().size() > 0;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial findByFaixaSalarialIdAndData(Long faixaSalarialId, Date data) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.max("cncfs.data"), "data");
		p.add(Projections.property("cncfs.nivelCompetenciaHistorico.id"), "nivelCompetenciaHistoricoId");
		p.add(Projections.groupProperty("cncfs.nivelCompetenciaHistorico.id"));
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs");
		criteria.setProjection(p);
		
		if(data != null)
			criteria.add(Expression.le("cncfs.data", data));
		
		criteria.add(Expression.eq("cncfs.faixaSalarial.id", faixaSalarialId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaFaixaSalarial.class));	
		return (ConfiguracaoNivelCompetenciaFaixaSalarial)criteria.uniqueResult();
	}

}
