package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AgendaManager;
import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.captacao.AnuncioEditAction;
import com.fortes.rh.web.action.sesmt.AgendaEditAction;
import com.opensymphony.webwork.ServletActionContext;

public class AnuncioEditActionTest extends MockObjectTestCase
{
	private AnuncioEditAction action;
	private Mock anuncioManager;
	private Mock solicitacaoManager;
	private Solicitacao solicitacao = new Solicitacao();

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new AnuncioEditAction();

		anuncioManager = new Mock(AnuncioManager.class);
		action.setAnuncioManager((AnuncioManager) anuncioManager.proxy());

		solicitacaoManager = mock(SolicitacaoManager.class);
        action.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
	}

	public void testPrepareInsert() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);;
		
		solicitacaoManager.expects(once()).method("findByIdProjectionAreaFaixaSalarial").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		
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
		
		anuncioManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(anuncio));
		anuncioManager.expects(once()).method("findBySolicitacao").with(ANYTHING).will(returnValue(anuncio));
		assertEquals("success", action.prepareUpdate());
	}
	
	public void testInsert() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("save").with(eq(anuncio)).will(returnValue(anuncio));
		assertEquals("successemail", action.insert());

		action.setAcao("I");
		anuncioManager.expects(once()).method("save").with(eq(anuncio)).will(returnValue(anuncio));
		assertEquals("successimprime", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("update").with(eq(anuncio));
		assertEquals("successemail", action.update());
		
		action.setAcao("I");
		anuncioManager.expects(once()).method("update").with(eq(anuncio));
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

	public void testEnviaEmail() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("findById").with(eq(anuncio.getId())).will(returnValue(anuncio));
		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		
		assertEquals("input", action.enviaEmail());
	}
	
	public void testEnviaEmailException() throws Exception
	{
		Anuncio anuncio = anuncio();
		solicitacao(anuncio);
		
		anuncioManager.expects(once()).method("findById").with(eq(anuncio.getId())).will(returnValue(anuncio));
		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		assertEquals("input", action.enviaEmail());
	}

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
