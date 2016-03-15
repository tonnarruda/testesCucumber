package com.fortes.rh.test.web.action.geral;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.UsuarioMensagemListAction;

public class UsuarioMensagemListActionTest extends MockObjectTestCase
{
	private UsuarioMensagemListAction action;
	private Mock manager;

	protected void setUp()
	{
		action = new UsuarioMensagemListAction();
		manager = new Mock(UsuarioMensagemManager.class);

		action.setUsuarioMensagemManager((UsuarioMensagemManager)manager.proxy());

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

	public void testDelete() throws Exception
	{
		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);

		action.setUsuarioMensagem(usuarioMensagem);

		manager.expects(once()).method("delete").with(eq(usuarioMensagem), ANYTHING);

		assertEquals("success", action.delete());
	}

	public void testGetsSets()
	{
		action.getUsuarioMensagem();
	}

}