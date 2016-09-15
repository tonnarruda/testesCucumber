package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Date;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.CandidatoSolicitacaoAuditorCallbackImpl;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

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

		Auditavel auditavel = callback.processa(this.mockaMetodoIntercepUpdateUpdateStatusAutorizacaoGestor());

		assertEquals("Candidato na Solicitacao Pessoal", auditavel.getModulo());
		assertEquals("Alteração do Status Aprovação do Responsável", auditavel.getOperacao());
		assertEquals("Raimundo", auditavel.getChave());
		
		String data = DateUtil.formataDiaMesAno(new Date());
		
		assertEquals("[DADOS ANTERIORES]\n{\n  \"Data\": \"Sem data\",\n  \"Status Aprovação\": \"Em análise\",\n  \"Obs do Gestor\": \"Sem Obs\"\n}\n\n"
				+ "[DADOS ATUALIZADOS]\n[{\n  \"Data\": \"" + data + "\",\n  \"Status Aprovação\": \"Autorizado\",\n  \"Obs do Gestor\": null\n}]", auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoIntercepUpdateUpdateStatusAutorizacaoGestor() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoUpdateStatusAutorizacaoGestor());
	}
	
	private MethodInvocation mockaMethodInvocationParaMetodoUpdateStatusAutorizacaoGestor() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(CandidatoSolicitacaoManager.class)
								.reflect().method("updateStatusAutorizacaoGestor")
								.withArgs(CandidatoSolicitacao.class);
				return m;
			}
			public Object[] getArguments() {
				return new Object[]{candidatoSolicitacaoAtual};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				return candidatoSolicitacaoManager;
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
	}
}
