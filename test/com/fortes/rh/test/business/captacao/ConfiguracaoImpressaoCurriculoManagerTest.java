package com.fortes.rh.test.business.captacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoImpressaoCurriculoManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoImpressaoCurriculoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoImpressaoCurriculoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class ConfiguracaoImpressaoCurriculoManagerTest extends MockObjectTestCase
{
	private ConfiguracaoImpressaoCurriculoManagerImpl configuracaoImpressaoCurriculoManager = new ConfiguracaoImpressaoCurriculoManagerImpl();
	private Mock configuracaoImpressaoCurriculoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoImpressaoCurriculoDao = new Mock(ConfiguracaoImpressaoCurriculoDao.class);
        configuracaoImpressaoCurriculoManager.setDao((ConfiguracaoImpressaoCurriculoDao) configuracaoImpressaoCurriculoDao.proxy());
    }

	public void testFindByUsuario()
	{
		Long empresaId = 1L;
		Long usuarioId = 1L;
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = ConfiguracaoImpressaoCurriculoFactory.getEntity(1L);
		
		configuracaoImpressaoCurriculoDao.expects(once()).method("findByUsuario").with(eq(usuarioId), eq(empresaId)).will(returnValue(configuracaoImpressaoCurriculo));
		assertEquals(configuracaoImpressaoCurriculo, configuracaoImpressaoCurriculoManager.findByUsuario(usuarioId, empresaId));
	}
	
	public void testFindByUsuarioVazio()
	{
		Long empresaId = 1L;
		Long usuarioId = 1L;

		configuracaoImpressaoCurriculoDao.expects(once()).method("findByUsuario").with(eq(usuarioId), eq(empresaId)).will(returnValue(null));
		assertNotNull(configuracaoImpressaoCurriculoManager.findByUsuario(usuarioId, empresaId));
	}

	public void testSaveOrUpdate_save()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = ConfiguracaoImpressaoCurriculoFactory.getEntity();
		configuracaoImpressaoCurriculo.setUsuario(usuario);
		configuracaoImpressaoCurriculo.setEmpresa(empresa);
		
		configuracaoImpressaoCurriculoDao.expects(once()).method("findByUsuario").with(eq(usuario.getId()), eq(empresa.getId())).will(returnValue(null));
		configuracaoImpressaoCurriculoDao.expects(once()).method("save").with(ANYTHING);
		configuracaoImpressaoCurriculoManager.saveOrUpdate(configuracaoImpressaoCurriculo);
	}
	
	public void testSaveOrUpdate_update()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = ConfiguracaoImpressaoCurriculoFactory.getEntity();
		configuracaoImpressaoCurriculo.setUsuario(usuario);
		configuracaoImpressaoCurriculo.setEmpresa(empresa);

		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo2 = ConfiguracaoImpressaoCurriculoFactory.getEntity();
		configuracaoImpressaoCurriculo2.setExibirAssinatura1(true);
		configuracaoImpressaoCurriculo2.setUsuario(usuario);
		configuracaoImpressaoCurriculo2.setEmpresa(empresa);
		
		configuracaoImpressaoCurriculoDao.expects(once()).method("findByUsuario").with(eq(usuario.getId()), eq(empresa.getId())).will(returnValue(configuracaoImpressaoCurriculo2));
		configuracaoImpressaoCurriculoDao.expects(once()).method("update").with(ANYTHING);
		configuracaoImpressaoCurriculoManager.saveOrUpdate(configuracaoImpressaoCurriculo);
	}

	public void testSaveOrUpdate_notUpdate()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = ConfiguracaoImpressaoCurriculoFactory.getEntity();
		configuracaoImpressaoCurriculo.setUsuario(usuario);
		configuracaoImpressaoCurriculo.setEmpresa(empresa);
		
		configuracaoImpressaoCurriculoDao.expects(once()).method("findByUsuario").with(eq(usuario.getId()), eq(empresa.getId())).will(returnValue(configuracaoImpressaoCurriculo));
		configuracaoImpressaoCurriculoManager.saveOrUpdate(configuracaoImpressaoCurriculo);
	}
}
