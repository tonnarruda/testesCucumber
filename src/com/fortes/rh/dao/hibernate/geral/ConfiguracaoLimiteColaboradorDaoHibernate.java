package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public class ConfiguracaoLimiteColaboradorDaoHibernate extends GenericDaoHibernate<ConfiguracaoLimiteColaborador> implements ConfiguracaoLimiteColaboradorDao
{

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findLimiteByArea(long areaId) 
	{
		Criteria criteria = getSession().createCriteria(QuantidadeLimiteColaboradoresPorCargo.class, "qtdLimite");


		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(QuantidadeLimiteColaboradoresPorCargo.class));

		return criteria.list();
	}
}
