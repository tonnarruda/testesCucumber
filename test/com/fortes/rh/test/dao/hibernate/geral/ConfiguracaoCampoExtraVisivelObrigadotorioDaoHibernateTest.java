package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraVisivelObrigadotorioDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class ConfiguracaoCampoExtraVisivelObrigadotorioDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoCampoExtraVisivelObrigadotorio>{

	private ConfiguracaoCampoExtraVisivelObrigadotorioDao configuracaoCampoExtraVisivelObrigadotorioDao;
	private EmpresaDao empresaDao;
	
	@Override
	public GenericDao<ConfiguracaoCampoExtraVisivelObrigadotorio> getGenericDao() {
		return configuracaoCampoExtraVisivelObrigadotorioDao;
	}

	@Override
	public ConfiguracaoCampoExtraVisivelObrigadotorio getEntity() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		return ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
	}
	
	public void testFindByEmpresaId() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio conf1 = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo());
		configuracaoCampoExtraVisivelObrigadotorioDao.save(conf1);
		
		assertEquals(conf1.getId(), configuracaoCampoExtraVisivelObrigadotorioDao.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()).getId());
		assertNull(configuracaoCampoExtraVisivelObrigadotorioDao.findByEmpresaId(empresa.getId(), TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo()));
	}

	public void testFindCollectionByEmpresaId() {
		String[] tiposConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()};

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio conf1 = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		configuracaoCampoExtraVisivelObrigadotorioDao.save(conf1);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio conf2 = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo());
		configuracaoCampoExtraVisivelObrigadotorioDao.save(conf2);
		
		assertEquals(1, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), tiposConfiguracao).size());
		assertEquals(2, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), null).size());
	}

	public void testRemoveByEmpresaAndTipoConfig() {
		String[] tiposConfiguracao = new String[]{TipoConfiguracaoCampoExtra.CANDIDATO.getTipo(), TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()};

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio conf1 = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		configuracaoCampoExtraVisivelObrigadotorioDao.save(conf1);
		
		ConfiguracaoCampoExtraVisivelObrigadotorio conf2 = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.CANDIDATO.getTipo());
		configuracaoCampoExtraVisivelObrigadotorioDao.save(conf2);
		
		assertEquals(2, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), new String[]{}).size());
		assertEquals(2, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), tiposConfiguracao).size());
	
		configuracaoCampoExtraVisivelObrigadotorioDao.removeByEmpresaAndTipoConfig(empresa.getId(), tiposConfiguracao);
		
		assertEquals(0, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), new String[]{}).size());
		assertEquals(0, configuracaoCampoExtraVisivelObrigadotorioDao.findCollectionByEmpresaId(empresa.getId(), tiposConfiguracao).size());
	}
	
	public void setConfiguracaoCampoExtraVisivelObrigadotorioDao(ConfiguracaoCampoExtraVisivelObrigadotorioDao configuracaoCampoExtraVisivelObrigadotorioDao) {
		this.configuracaoCampoExtraVisivelObrigadotorioDao = configuracaoCampoExtraVisivelObrigadotorioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}