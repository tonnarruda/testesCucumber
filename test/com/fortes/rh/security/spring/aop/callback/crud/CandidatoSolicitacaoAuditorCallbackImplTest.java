package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.CandidatoSolicitacaoAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class CandidatoSolicitacaoAuditorCallbackImplTest {

	AuditorCallback callback;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private CandidatoSolicitacao candidatoSolicitacaoAtual;
	
	@Before
	public void setUp() {
		candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
		callback = new CandidatoSolicitacaoAuditorCallbackImpl();
	}
	
	@Test
	public void testUpdateStatusAutorizacaoGestor() throws Throwable {
		CandidatoSolicitacao candidatoSolicitacaoAnterior = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacaoAnterior.setStatusAutorizacaoGestor(StatusAprovacaoSolicitacao.ANALISE);
		
		candidatoSolicitacaoAtual = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacaoAtual.setColaboradorNome("Raimundo");
		candidatoSolicitacaoAtual.setStatusAutorizacaoGestor(StatusAprovacaoSolicitacao.APROVADO);
		
		when(candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoAnterior.getId())).thenReturn(candidatoSolicitacaoAnterior);

		MethodInvocation updateStatusAutorizacaoGestor = new MethodInvocationDefault<CandidatoSolicitacaoManager>("updateStatusAutorizacaoGestor", CandidatoSolicitacaoManager.class, new Object[]{candidatoSolicitacaoAtual}, candidatoSolicitacaoManager);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(updateStatusAutorizacaoGestor));

		assertEquals("Candidato na Solicitacao Pessoal", auditavel.getModulo());
		assertEquals("Alteração do Status Aprovação do Responsável", auditavel.getOperacao());
		assertEquals("Raimundo", auditavel.getChave());
		
		String data = DateUtil.formataDiaMesAno(new Date());
		
		assertEquals("[DADOS ANTERIORES]\n{\n  \"Data\": \"Sem data\",\n  \"Status Aprovação\": \"Em análise\",\n  \"Obs do Gestor\": \"Sem Obs\"\n}\n\n"
				+ "[DADOS ATUALIZADOS]\n[{\n  \"Data\": \"" + data + "\",\n  \"Status Aprovação\": \"Autorizado\",\n  \"Obs do Gestor\": null\n}]", auditavel.getDados());
	}
	
	@Test
	public void testUpdateStatusAutorizacaoGestorComDataEAutorizacaoGestor() throws Throwable {
		CandidatoSolicitacao candidatoSolicitacaoAnterior = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacaoAnterior.setStatusAutorizacaoGestor(StatusAprovacaoSolicitacao.ANALISE);
		candidatoSolicitacaoAnterior.setDataAutorizacaoGestor(DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")));;
		candidatoSolicitacaoAnterior.setObsAutorizacaoGestor("Autorizado");
		
		candidatoSolicitacaoAtual = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacaoAtual.setColaboradorNome("Raimundo");
		candidatoSolicitacaoAtual.setStatusAutorizacaoGestor(StatusAprovacaoSolicitacao.APROVADO);
		
		when(candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoAnterior.getId())).thenReturn(candidatoSolicitacaoAnterior);
		
		MethodInvocation updateStatusAutorizacaoGestor = new MethodInvocationDefault<CandidatoSolicitacaoManager>("updateStatusAutorizacaoGestor", CandidatoSolicitacaoManager.class, new Object[]{candidatoSolicitacaoAtual}, candidatoSolicitacaoManager);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(updateStatusAutorizacaoGestor));
		
		assertEquals("Candidato na Solicitacao Pessoal", auditavel.getModulo());
		assertEquals("Alteração do Status Aprovação do Responsável", auditavel.getOperacao());
		assertEquals("Raimundo", auditavel.getChave());
		
		String data = DateUtil.formataDiaMesAno(new Date());
		
		assertEquals("[DADOS ANTERIORES]\n{\n  \"Data\": \"08/12/2016\",\n  \"Status Aprovação\": \"Em análise\",\n  \"Obs do Gestor\": \"Autorizado\"\n}\n\n"
				+ "[DADOS ATUALIZADOS]\n[{\n  \"Data\": \"" + data + "\",\n  \"Status Aprovação\": \"Autorizado\",\n  \"Obs do Gestor\": null\n}]", auditavel.getDados());
	}
	
}
