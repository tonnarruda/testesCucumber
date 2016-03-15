package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.captacao.AnuncioEditAction;
import com.opensymphony.webwork.ServletActionContext;

public class AnuncioEditActionTest extends MockObjectTestCase
{
	private AnuncioEditAction action;
	private Mock anuncioManager;
	private Mock solicitacaoManager;
	private Mock solicitacaoAvaliacaoManager;
	private Solicitacao solicitacao = new Solicitacao();

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new AnuncioEditAction();

		anuncioManager = new Mock(AnuncioManager.class);
		action.setAnuncioManager((AnuncioManager) anuncioManager.proxy());

		solicitacaoManager = mock(SolicitacaoManager.class);
        action.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
        
        solicitacaoAvaliacaoManager = mock(SolicitacaoAvaliacaoManager.class);
        action.setSolicitacaoAvaliacaoManager((SolicitacaoAvaliacaoManager) solicitacaoAvaliacaoManager.proxy());
        
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testPrepareInsert() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		solicitacaoManager.expects(once()).method("findByIdProjectionAreaFaixaSalarial").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
		solicitacaoAvaliacaoManager.expects(once()).method("findBySolicitacaoId").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<SolicitacaoAvaliacao>()));
		
		assertEquals("success", action.prepareInsert());
	}

	public void testAnunciar() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(true));
		assertEquals("success2", action.anunciar());

		anuncioManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(false));
		assertEquals("success", action.anunciar());
	}

	public void testPrepareUpdate() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("findBySolicitacao").with(ANYTHING).will(returnValue(anuncio));
		solicitacaoAvaliacaoManager.expects(atLeastOnce()).method("findBySolicitacaoId").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<SolicitacaoAvaliacao>()));
		
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testInsert() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("save").with(eq(anuncio)).will(returnValue(anuncio));
		solicitacaoAvaliacaoManager.expects(once()).method("setResponderModuloExterno").with(ANYTHING, ANYTHING);
		assertEquals("successemail", action.insert());

		action.setAcao("I");
		anuncioManager.expects(once()).method("save").with(eq(anuncio)).will(returnValue(anuncio));
		solicitacaoAvaliacaoManager.expects(once()).method("setResponderModuloExterno").with(ANYTHING, ANYTHING);
		assertEquals("successimprime", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("update").with(eq(anuncio));
		solicitacaoAvaliacaoManager.expects(once()).method("setResponderModuloExterno").with(ANYTHING, ANYTHING);
		assertEquals("successemail", action.update());
		
		action.setAcao("I");
		anuncioManager.expects(once()).method("update").with(eq(anuncio));
		solicitacaoAvaliacaoManager.expects(once()).method("setResponderModuloExterno").with(ANYTHING, ANYTHING);
		assertEquals("successimprime", action.update());
	}

	public void testImprimir() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		anuncio.setSolicitacao(solicitacao);
		
		anuncioManager.expects(once()).method("findById").with(eq(anuncio.getId())).will(returnValue(anuncio));
		assertEquals("success", action.imprimir());
	}

	public void testEmail() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		assertEquals("success", action.email());
	}

//	public void testEnviaEmail() throws Exception
//	{
//		Anuncio anuncio = anuncio();
//		solicitacao(anuncio);
//		
//		String[] emails = new String[1];
//		
//		anuncioManager.expects(once()).method("findById").with(eq(anuncio.getId())).will(returnValue(anuncio));
//		anuncioManager.expects(once()).method("montaEmails").with(ANYTHING, ANYTHING).will(returnValue(emails));
//		
//		assertEquals("success", action.enviaEmail());
//	}
	
//	public void testEnviaEmailException() throws Exception
//	{
//		Anuncio anuncio = anuncio();
//		solicitacao(anuncio);
//		
//		anuncioManager.expects(once()).method("findById").with(eq(anuncio.getId())).will(returnValue(anuncio));
//		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
//		assertEquals("input", action.enviaEmail());
//	}

	private Anuncio anuncio() {
		Anuncio anuncio = new Anuncio();
		anuncio.setId(2L);
		action.setAnuncio(anuncio);
		return anuncio;
	}

	private void solicitacao(Anuncio anuncio) {
		solicitacao.setId(1L);
		solicitacao.setDescricao("Solicitacao");
		solicitacao.setAnuncio(anuncio);
		solicitacao.setRemuneracao(10.0);
		action.setSolicitacao(solicitacao);
	}
	
}
