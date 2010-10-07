package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.test.factory.geral.UsuarioMensagemFactory;
import com.fortes.rh.web.dwr.UsuarioMensagemDWR;

public class UsuarioMensagemDWRTest extends MockObjectTestCase
{
	UsuarioMensagemDWR usuarioMensagemDWR;
	Mock usuarioMensagemManager;
	
	protected void setUp() throws Exception
	{
		
		usuarioMensagemDWR = new UsuarioMensagemDWR();

		usuarioMensagemManager = new Mock(UsuarioMensagemManager.class);
		usuarioMensagemDWR.setUsuarioMensagemManager((UsuarioMensagemManager) usuarioMensagemManager.proxy());
	}
	
	public void testGravarMensagemLida()
	{
		Long usuarioMensagemId = 100L;
		Long empresaId = 1L;
		boolean lida = true;
		UsuarioMensagem usuarioMensagem = UsuarioMensagemFactory.getEntity(100L);
		
		usuarioMensagemManager.expects(once()).method("findByIdProjection").with(eq(usuarioMensagemId), eq(empresaId)).will(returnValue(usuarioMensagem));
		usuarioMensagemManager.expects(once()).method("update").with(eq(usuarioMensagem)).isVoid();
		
		usuarioMensagemDWR.gravarMensagemLida(usuarioMensagemId, empresaId, lida);
	}
	
	

}
