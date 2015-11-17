package com.fortes.rh.dao.hibernate.captacao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

public class NivelCompetenciaHistoricoDaoHibernate extends GenericDaoHibernate<NivelCompetenciaHistorico> implements NivelCompetenciaHistoricoDao
{
	public boolean existeDependenciaComCompetenciasDaFaixaSalarial(Long nivelConfiguracaoHistoricoId) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncf");
		criteria.add(Expression.eq("cncf.nivelCompetenciaHistorico.id", nivelConfiguracaoHistoricoId));

		return criteria.list().size() > 0;	
	}

	public Long findByData(Date date, Long empresaId) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(NivelCompetenciaHistorico.class, "nch2")
				.setProjection(Projections.max("nch2.data"))
				.add(Restrictions.eqProperty("nch2.empresa.id", "nch.empresa.id"))
				.add(Restrictions.le("nch2.data", date));
		
		Criteria criteria = getSession().createCriteria(NivelCompetenciaHistorico.class, "nch");
		criteria.setProjection(Projections.property("nch.id"));

		criteria.add(Subqueries.propertyEq("nch.data", subQueryHc));
		criteria.add(Expression.eq("nch.empresa.id", empresaId));
		
		return (Long) criteria.uniqueResult();
	}
}
