package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.UsuarioMensagemEditAction;

public class UsuarioMensagemEditActionTest extends MockObjectTestCase
{
	private UsuarioMensagemEditAction action;
	private Mock manager;
	private Mock usuarioManager;
	private Mock mensagemManager;

	protected void setUp()
	{
		action = new UsuarioMensagemEditAction();

		manager = new Mock(UsuarioMensagemManager.class);
		action.setUsuarioMensagemManager((UsuarioMensagemManager)manager.proxy());

		usuarioManager = new Mock(UsuarioManager.class);
		action.setUsuarioManager((UsuarioManager)usuarioManager.proxy());

		mensagemManager = new Mock(MensagemManager.class);
		action.setMensagemManager((MensagemManager)mensagemManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	public void testPrepareInsert() throws Exception
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(usuarioMensagem));

		Collection<Usuario> usuariosCheckList = new ArrayList<Usuario>();
		usuariosCheckList.add(usuario);

		usuarioManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(usuariosCheckList));

		assertEquals("success", action.prepareInsert());

	}

	public void testPrepareUpdate() throws Exception
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(usuarioMensagem));

		Collection<Usuario> usuariosCheckList = new ArrayList<Usuario>();
		usuariosCheckList.add(usuario);

		usuarioManager.expects(once()).method("populaCheckOrderNome").with(ANYTHING).will(returnValue(usuariosCheckList));


		assertEquals("success", action.prepareUpdate());

	}

	public void testInsert() throws Exception
	{
		Mensagem mensagem = MensagemFactory.getEntity(1L);

		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("salvaMensagem").with(ANYTHING, ANYTHING, ANYTHING);

		assertEquals("success", action.insert());
	}

	public void testInsertInput() throws Exception
	{
		Mensagem mensagem = MensagemFactory.getEntity(1L);

		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("salvaMensagem").with(ANYTHING, ANYTHING, ANYTHING).will(throwException(new Exception("Erro")));

		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("update").with(ANYTHING);

		assertEquals("success", action.update());
	}

	public void testLeituraUsuarioMensagemPopup() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("countMensagens").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(2));
		manager.expects(atLeastOnce()).method("getAnteriorOuProximo").with(new Constraint[] { eq(usuarioMensagem.getId()), eq(usuario.getId()), eq(empresa.getId()), ANYTHING, ANYTHING }).will(returnValue(null));
		manager.expects(once()).method("findByIdProjection").with(ANYTHING, ANYTHING).will(returnValue(usuarioMensagem));

		assertEquals("success", action.leituraUsuarioMensagemPopup());
	}

	public void testGetsSets()
	{
		action.getModel();

		action.getUsuarioMensagem();

		action.getUsuariosCheck();

		action.getUsuariosCheckList();

		action.setUsuariosCheck(null);

		action.setUsuariosCheckList(null);

		action.getEmpresaEmp();

		action.setEmpresaEmp(new Empresa());

		action.getUsuarioRem();

		action.setUsuarioRem(new Usuario());

		action.getMensagemNova();

		action.setMensagemNova(new Mensagem());

		action.getMensagemStatus();

		action.setMensagemStatus("ok");
	}

}