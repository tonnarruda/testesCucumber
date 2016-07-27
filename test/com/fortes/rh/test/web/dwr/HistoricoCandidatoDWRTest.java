package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.web.dwr.HistoricoCandidatoDWR;

public class HistoricoCandidatoDWRTest extends MockObjectTestCase
{
	private HistoricoCandidatoDWR historicoCandidatoDWR;
	private Mock candidatoSolicitacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
		historicoCandidatoDWR = new HistoricoCandidatoDWR();
		historicoCandidatoDWR.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
	}

	@SuppressWarnings("unchecked")
	public void testGetCandidatoAptoByEtapa()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);

		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva.setId(1L);

		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setEtapaSeletiva(etapaSeletiva);
		candidatoSolicitacao.setCandidatoContratado(true);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidatoSolicitacao);

		candidatoSolicitacaoManager.expects(once()).method("getCandidatoSolicitacaoEtapasEmGrupo").with(eq(solicitacao.getId()), eq(etapaSeletiva.getId())).will(returnValue(candidatoSolicitacaos));

		Map retorno = historicoCandidatoDWR.getCandidatoAptoByEtapa(etapaSeletiva.getId(), solicitacao.getId());

		assertEquals(1, retorno.size());
	}
}
