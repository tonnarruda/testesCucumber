package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.ConfiguracaoRelatorioDinamicoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoRelatorioDinamicoFactory;

public class ConfiguracaoRelatorioDinamicoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoRelatorioDinamico>
{
	private ConfiguracaoRelatorioDinamicoDao configuracaoRelatorioDinamicoDao;
	private UsuarioDao usuarioDao;
	
	@Override
	public ConfiguracaoRelatorioDinamico getEntity()
	{
		return ConfiguracaoRelatorioDinamicoFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoRelatorioDinamico> getGenericDao()
	{
		return configuracaoRelatorioDinamicoDao;
	}

	public void setConfiguracaoRelatorioDinamicoDao(ConfiguracaoRelatorioDinamicoDao configuracaoRelatorioDinamicoDao)
	{
		this.configuracaoRelatorioDinamicoDao = configuracaoRelatorioDinamicoDao;
	}
	
	public void testRemoveByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		ConfiguracaoRelatorioDinamico config1 = new ConfiguracaoRelatorioDinamico();
		config1.setUsuario(usuario);
		configuracaoRelatorioDinamicoDao.save(config1);
		
		ConfiguracaoRelatorioDinamico config2 = new ConfiguracaoRelatorioDinamico();
		config2.setUsuario(usuario);
		configuracaoRelatorioDinamicoDao.save(config2);
		
		configuracaoRelatorioDinamicoDao.removeByUsuario(usuario.getId());
		assertNull(configuracaoRelatorioDinamicoDao.findEntidadeComAtributosSimplesById(config1.getId()));
		assertNull(configuracaoRelatorioDinamicoDao.findEntidadeComAtributosSimplesById(config2.getId()));
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		ConfiguracaoRelatorioDinamico config1 = new ConfiguracaoRelatorioDinamico();
		config1.setUsuario(usuario);
		configuracaoRelatorioDinamicoDao.save(config1);
		
		ConfiguracaoRelatorioDinamico config2 = configuracaoRelatorioDinamicoDao.findByUsuario(usuario.getId());
		
		assertEquals(config1.getId(), config2.getId());
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
