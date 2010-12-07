package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.ConfiguracaoPerformanceDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoPerformanceFactory;

public class ConfiguracaoPerformanceDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoPerformance>
{
	private ConfiguracaoPerformanceDao configuracaoPerformanceDao;
	private UsuarioDao usuarioDao;

	@Override
	public ConfiguracaoPerformance getEntity()
	{
		return ConfiguracaoPerformanceFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoPerformance> getGenericDao()
	{
		return configuracaoPerformanceDao;
	}
	
	public void testRemoveByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		ConfiguracaoPerformance configBox1 = new ConfiguracaoPerformance(usuario, "1", 2, false);
		configuracaoPerformanceDao.save(configBox1);
		ConfiguracaoPerformance configBox2 = new ConfiguracaoPerformance(usuario, "5", 1, true);
		configuracaoPerformanceDao.save(configBox2);
		
		configuracaoPerformanceDao.removeByUsuario(usuario.getId());
		assertNull(configuracaoPerformanceDao.findEntidadeComAtributosSimplesById(configBox1.getId()));
		assertNull(configuracaoPerformanceDao.findEntidadeComAtributosSimplesById(configBox2.getId()));
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		ConfiguracaoPerformance configBox1 = new ConfiguracaoPerformance(usuario, "1", 2, false);
		configuracaoPerformanceDao.save(configBox1);
		ConfiguracaoPerformance configBox2 = new ConfiguracaoPerformance(usuario, "5", 1, true);
		configuracaoPerformanceDao.save(configBox2);
		
		Collection<ConfiguracaoPerformance> configuracaoPerformances = configuracaoPerformanceDao.findByUsuario(usuario.getId());
		assertEquals(2, configuracaoPerformances.size());
	}

	public void setConfiguracaoPerformanceDao(ConfiguracaoPerformanceDao configuracaoPerformanceDao)
	{
		this.configuracaoPerformanceDao = configuracaoPerformanceDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
