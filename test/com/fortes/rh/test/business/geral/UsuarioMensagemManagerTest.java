package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManagerImpl;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioMensagemFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.webwork.ServletActionContext;

public class UsuarioMensagemManagerTest extends MockObjectTestCase
{
	private UsuarioMensagemManagerImpl usuarioMensagemManager = new UsuarioMensagemManagerImpl();
	private Mock usuarioMensagemDao = null;
	private Mock mensagemManager = null;
	private Mock transactionManager = null;
	private Mock usuarioEmpresaManager = null;

    protected void setUp() throws Exception
    {
        super.setUp();

        usuarioMensagemDao = new Mock(UsuarioMensagemDao.class);
        usuarioMensagemManager.setDao((UsuarioMensagemDao) usuarioMensagemDao.proxy());

        mensagemManager = new Mock(MensagemManager.class);
        usuarioMensagemManager.setMensagemManager((MensagemManager) mensagemManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        usuarioMensagemManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
        
        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        usuarioMensagemManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());

        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testListaUsuarioMensagem()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		usuarioMensagemDao.expects(once()).method("listaUsuarioMensagem").with(ANYTHING, ANYTHING).will(returnValue(usuarioMensagems));

		Collection<UsuarioMensagem> retorno = usuarioMensagemManager.listaUsuarioMensagem(usuario.getId(), empresa.getId());

		assertEquals(usuarioMensagems.size(), retorno.size());
	}

	public void testFindByIdProjection()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		usuarioMensagemDao.expects(once()).method("findByIdProjection").with(ANYTHING, ANYTHING).will(returnValue(usuarioMensagem));

		UsuarioMensagem retorno = usuarioMensagemManager.findByIdProjection(usuarioMensagem.getId(), empresa.getId());

		assertEquals(usuarioMensagem.getId(), retorno.getId());
	}

	public void testPossuiMensagemNaoLida()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		usuarioMensagemDao.expects(once()).method("possuiMensagemNaoLida").with(ANYTHING,ANYTHING).will(returnValue(true));

		boolean retorno = usuarioMensagemManager.possuiMensagemNaoLida(usuario.getId(), empresa.getId());

		assertEquals(true, retorno);
	}

	public void testSalvaMensagem() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		String [] usuariosCheck = {"1"};

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		usuarioMensagemDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		usuarioMensagemManager.salvaMensagem(empresa, mensagem, usuariosCheck);

	}

	public void testSalvaMensagemException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		String [] usuariosCheck = null;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		transactionManager.expects(once()).method("rollback").with(ANYTHING);

		Exception exception = null;

		try
		{
			usuarioMensagemManager.salvaMensagem(empresa, mensagem, usuariosCheck);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveMensagemAndUsuarioMensagem() throws Exception
	{
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		usuarioEmpresas.add(usuarioEmpresa);

		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);

		usuarioMensagemManager.saveMensagemAndUsuarioMensagem("Msg", "Chico Bagulhoso", "link", usuarioEmpresas, null, TipoMensagem.INDIFERENTE);
	}

	public void testSaveMensagemAndUsuarioMensagemRespAreaOrganizacional() throws Exception
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);
		usuarioEmpresa.setUsuario(usuario);

		Usuario usuario2 = UsuarioFactory.getEntity(2L);
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(2L);
		usuarioEmpresa2.setUsuario(usuario2);
		
		Usuario usuarioFora = UsuarioFactory.getEntity(3L);
		UsuarioEmpresa usuarioEmpresaFora = UsuarioEmpresaFactory.getEntity(3L);
		usuarioEmpresaFora.setUsuario(usuarioFora);
		
		Mensagem mensagem = MensagemFactory.getEntity(1L);
		
		Collection<UsuarioEmpresa> todosUsuarioEmpresasComRolePeriodoExperiencia = new ArrayList<UsuarioEmpresa>();
		todosUsuarioEmpresasComRolePeriodoExperiencia.add(usuarioEmpresa);
		todosUsuarioEmpresasComRolePeriodoExperiencia.add(usuarioEmpresa2);
		todosUsuarioEmpresasComRolePeriodoExperiencia.add(usuarioEmpresaFora);
		
		Collection<UsuarioEmpresa> usuariosResponsaveisAreaOrganizacionais = new ArrayList<UsuarioEmpresa>();
		usuariosResponsaveisAreaOrganizacionais.add(usuarioEmpresa);
		usuariosResponsaveisAreaOrganizacionais.add(usuarioEmpresa2);
		
		usuarioEmpresaManager.expects(once()).method("findUsuarioResponsavelAreaOrganizacional").with(ANYTHING).will(returnValue(usuariosResponsaveisAreaOrganizacionais));
		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);
		
		usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional("Msg", "Chico Bagulhoso", "link", todosUsuarioEmpresasComRolePeriodoExperiencia, new ArrayList<Long>());
	}
	
	public void testDeleteUm() throws Exception
	{
		UsuarioMensagem usuarioMensagem = UsuarioMensagemFactory.getEntity(1L);
		usuarioMensagemDao.expects(once()).method("remove").with(ANYTHING);
		usuarioMensagemManager.delete(usuarioMensagem, null);
	}

	public void testDeleteVarios() throws Exception
	{
		Long[] usuarioMensagemIds = new Long[]{1L, 2L};
		usuarioMensagemDao.expects(once()).method("remove").with(ANYTHING);
		usuarioMensagemManager.delete(null, usuarioMensagemIds);
	}

	public void testGetAnteriorOuProximo()
	{
		usuarioMensagemDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));
		usuarioMensagemDao.expects(once()).method("findAnteriorOuProximo").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(2L));
		assertEquals(new Long(2), usuarioMensagemManager.getAnteriorOuProximo(1L, 1L, 1L, 'P'));
	}
}
