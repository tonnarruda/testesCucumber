package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;

public class ConfigHistoricoNivelDaoHibernateTest extends GenericDaoHibernateTest<ConfigHistoricoNivel>
{
	private ConfigHistoricoNivelDao configHistoricoNivelDao;

	@Override
	public ConfigHistoricoNivel getEntity()
	{
		return ConfigHistoricoNivelFactory.getEntity();
	}

	@Override
	public GenericDao<ConfigHistoricoNivel> getGenericDao()
	{
		return configHistoricoNivelDao;
	}

	public void setConfigHistoricoNivelDao(ConfigHistoricoNivelDao configHistoricoNivelDao)
	{
		this.configHistoricoNivelDao = configHistoricoNivelDao;
	}
}
