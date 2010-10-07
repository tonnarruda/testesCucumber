package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.ConfiguracaoImpressaoCurriculoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoImpressaoCurriculoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class ConfiguracaoImpressaoCurriculoDaoHibernateTest extends GenericDaoHibernateTest<ConfiguracaoImpressaoCurriculo>
{
	private ConfiguracaoImpressaoCurriculoDao configuracaoImpressaoCurriculoDao;
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;

	@Override
	public ConfiguracaoImpressaoCurriculo getEntity()
	{
		return ConfiguracaoImpressaoCurriculoFactory.getEntity();
	}

	@Override
	public GenericDao<ConfiguracaoImpressaoCurriculo> getGenericDao()
	{
		return configuracaoImpressaoCurriculoDao;
	}

	public void setConfiguracaoImpressaoCurriculoDao(ConfiguracaoImpressaoCurriculoDao configuracaoImpressaoCurriculoDao)
	{
		this.configuracaoImpressaoCurriculoDao = configuracaoImpressaoCurriculoDao;
	}
	
	public void testFindByUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = ConfiguracaoImpressaoCurriculoFactory.getEntity();
		configuracaoImpressaoCurriculo.setUsuario(usuario);
		configuracaoImpressaoCurriculo.setEmpresa(empresa);
		configuracaoImpressaoCurriculoDao.save(configuracaoImpressaoCurriculo);
		
		assertEquals("teste 01", configuracaoImpressaoCurriculo.getId(), configuracaoImpressaoCurriculoDao.findByUsuario(usuario.getId(), empresa.getId()).getId());
		assertEquals("teste 02", null, configuracaoImpressaoCurriculoDao.findByUsuario(usuario.getId(), 55990055L));
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}
