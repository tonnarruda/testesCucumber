package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;

import dbunit.DbUnitManager;

public class ConfiguracaoCampoExtraDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCampoExtra>
{
	private ConfiguracaoCampoExtraDao configuracaoCampoExtraDao;
	private EmpresaDao empresaDao;
	
	private static final String dataSet = "test/dbunit/dataset/ConfiguracaoCampoExtraDaoHibernateTest.xml";
	
	DbUnitManager dbUnitManager;

	public void setDbUnitManager(DbUnitManager dbUnitManager) {
		this.dbUnitManager = dbUnitManager;
	}
	
	@Override
	public void testRemove() throws Exception {
		dadoQueJaExistemTresRegistrosCadastradosNoBanco();
		configuracaoCampoExtraDao.remove(-9997L);
		assertQueEntidadeFoiRemovida();
	}

	private void assertQueEntidadeFoiRemovida() {
		Exception ex = null;
		try {
			configuracaoCampoExtraDao.findById(-9997L);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex = e;
		}
		assertNotNull(ex);
		assertTrue(ex instanceof HibernateObjectRetrievalFailureException);
	}
	
	public void testFindAllSelect() {
		// dado que
		dadoQueJaExistemTresRegistrosCadastradosNoBanco();
		// quando
		Collection<ConfiguracaoCampoExtra> campos = configuracaoCampoExtraDao.findAllSelect(null);
		// entao
		assertEquals("total de registros encontrados", 3, campos.size());
	}
	
	public void testFindAllNomes()
	{
		String[] nomes = configuracaoCampoExtraDao.findAllNomes();
		assertEquals(16, nomes.length);
	}
	
	//TODO BACALHAU: Tá imundo. O teste depende do banco. Refatorar!! 
//	public void testFindDistinctTodosIguais() 
//	{
//		Collection<ConfiguracaoCampoExtra> campos = configuracaoCampoExtraDao.findDistinct();
//		assertEquals(32, campos.size());
//	}
//
//	public void testFindDistinctComEmpresaDiferente() 
//	{
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresaDao.save(empresa);
//		
//		ConfiguracaoCampoExtra configuracaoCampoExtra = ConfiguracaoCampoExtraFactory.getEntity();
//		configuracaoCampoExtra.setTitulo("babau");
//		configuracaoCampoExtra.setEmpresa(empresa);
//		configuracaoCampoExtraDao.save(configuracaoCampoExtra);
//		
//		Collection<ConfiguracaoCampoExtra> campos = configuracaoCampoExtraDao.findDistinct();
//		assertEquals(33, campos.size());
//	}

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

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	
}
