package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

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
}
