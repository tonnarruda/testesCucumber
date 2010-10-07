package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;

public class ConfiguracaoCampoExtraDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCampoExtra>
{
	private ConfiguracaoCampoExtraDao configuracaoCampoExtraDao;

	@Override
	public ConfiguracaoCampoExtra getEntity()
	{
		return ConfiguracaoCampoExtraFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoCampoExtra> getGenericDao()
	{
		return configuracaoCampoExtraDao;
	}

	public void setConfiguracaoCampoExtraDao(ConfiguracaoCampoExtraDao configuracaoCampoExtraDao)
	{
		this.configuracaoCampoExtraDao = configuracaoCampoExtraDao;
	}
}
