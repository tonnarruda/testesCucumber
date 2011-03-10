package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;

import dbunit.DbUnitManager;

public class ConfiguracaoCampoExtraDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCampoExtra>
{
	private ConfiguracaoCampoExtraDao configuracaoCampoExtraDao;
	
	private static final String dataSet = "test/dbunit/dataset/ConfiguracaoCampoExtraDaoHibernateTest.xml";
	
	DbUnitManager dbUnitManager;

	public void setDbUnitManager(DbUnitManager dbUnitManager) {
		this.dbUnitManager = dbUnitManager;
	}
	
	public void testFindAllSelect() {
		// dado que
		dadoQueJaExistemTresRegistrosCadastradosNoBanco();
		// quando
		Collection<ConfiguracaoCampoExtra> campos = configuracaoCampoExtraDao.findAllSelect();
		// entao
		assertEquals("total de registros encontrados", 3, campos.size());
	}

	private void dadoQueJaExistemTresRegistrosCadastradosNoBanco() {
		dbUnitManager.cleanAndInsert(dataSet);		
	}

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
