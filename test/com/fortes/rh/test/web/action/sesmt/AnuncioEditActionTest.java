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
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.captacao.AnuncioEditAction;
import com.fortes.rh.web.action.sesmt.AgendaEditAction;

public class AnuncioEditActionTest extends MockObjectTestCase
{
	private AnuncioEditAction action;
	private Mock anuncioManager;
	private Mock solicitacaoManager;

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
		Anuncio anuncio = new Anuncio();
		anuncio.setId(1L);
		action.setAnuncio(anuncio);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		solicitacao.setDescricao("Solicitacao");
		
		action.setSolicitacao(solicitacao);
		
		solicitacaoManager.expects(once()).method("findByIdProjectionAreaFaixaSalarial").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
		
		anuncioManager.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		
		assertEquals("success", action.prepareInsert());
	}
}
