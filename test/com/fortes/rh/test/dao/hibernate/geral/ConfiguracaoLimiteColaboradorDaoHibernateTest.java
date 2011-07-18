package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;

public class ConfiguracaoLimiteColaboradorDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoLimiteColaborador>
{
	private ConfiguracaoLimiteColaboradorDao configuracaoLimiteColaboradorDao;

	@Override
	public ConfiguracaoLimiteColaborador getEntity()
	{
		return ConfiguracaoLimiteColaboradorFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoLimiteColaborador> getGenericDao()
	{
		return configuracaoLimiteColaboradorDao;
	}

	public void setConfiguracaoLimiteColaboradorDao(ConfiguracaoLimiteColaboradorDao configuracaoLimiteColaboradorDao)
	{
		this.configuracaoLimiteColaboradorDao = configuracaoLimiteColaboradorDao;
	}
}
