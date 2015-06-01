package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Hibernate;
import org.hibernate.Query;

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
}
