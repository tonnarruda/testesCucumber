package com.fortes.rh.test.web.action.avaliacao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.AvaliacaoDesempenhoEditAction;
import com.opensymphony.xwork.Action;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RelatorioUtil.class)
public class AvaliacaoDesempenhoEditActionTest_JUnit4
{
	private AvaliacaoDesempenhoEditAction action;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;

	@Before
	public void setUp() throws Exception
	{
		action = new AvaliacaoDesempenhoEditAction();

		avaliacaoDesempenhoManager = Mockito.mock(AvaliacaoDesempenhoManager.class);
		action.setAvaliacaoDesempenhoManager(avaliacaoDesempenhoManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

    	PowerMockito.mockStatic(RelatorioUtil.class);
	}
	
	@Test
	public void testPrepareAnaliseDesempenhoCompetenciaOrganizacaoComAvaliacaoDesempenho() {
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		avaliacaoDesempenhos.add(AvaliacaoDesempenhoFactory.getEntity(1L));
		
		Mockito.when(avaliacaoDesempenhoManager.findComCompetencia(action.getEmpresaSistema().getId())).thenReturn(avaliacaoDesempenhos);
		
		Assert.assertEquals(Action.SUCCESS, action.prepareAnaliseDesempenhoCompetenciaOrganizacao());
	}
	
	@Test
	public void testPrepareAnaliseDesempenhoCompetenciaOrganizacaoSemAvaliacaoDesempenho() {
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		
		Mockito.when(avaliacaoDesempenhoManager.findComCompetencia(action.getEmpresaSistema().getId())).thenReturn(avaliacaoDesempenhos);
		
		Assert.assertEquals(Action.INPUT, action.prepareAnaliseDesempenhoCompetenciaOrganizacao());
		Assert.assertFalse(action.getActionWarnings().isEmpty());
	}
	
	@Test
	public void testPrepareAnaliseDesempenhoCompetenciaOrganizacaoSemAvaliacaoDesempenhoException() {
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = null;
		
		Mockito.when(avaliacaoDesempenhoManager.findComCompetencia(action.getEmpresaSistema().getId())).thenReturn(avaliacaoDesempenhos);
		
		Assert.assertEquals(Action.INPUT, action.prepareAnaliseDesempenhoCompetenciaOrganizacao());
		Assert.assertTrue(action.getActionWarnings().isEmpty());
	}
	
	@Test
	public void testImprimeAnaliseDesempenhoCompetenciaOrganizacao() {
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos = new ArrayList<AnaliseDesempenhoOrganizacao>();
		analiseDesempenhoOrganizacaos.add(new AnaliseDesempenhoOrganizacao("Comp 1", "Agrup 1", 1d));
		action.setCompetenciasCheck(new Long[]{});
		
		Mockito.when(avaliacaoDesempenhoManager.findAnaliseDesempenhoOrganizacao(any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),anyString(), anyLong())).thenReturn(analiseDesempenhoOrganizacaos);
		
		Assert.assertEquals(Action.SUCCESS, action.imprimeAnaliseDesempenhoCompetenciaOrganizacao());
	}
	
	@Test
	public void testImprimeAnaliseDesempenhoCompetenciaOrganizacaoSemDadosParaImprimir() {
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos = new ArrayList<AnaliseDesempenhoOrganizacao>();
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		
		Mockito.when(avaliacaoDesempenhoManager.findAnaliseDesempenhoOrganizacao(any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),anyString(), anyLong())).thenReturn(analiseDesempenhoOrganizacaos);
		Mockito.when(avaliacaoDesempenhoManager.findComCompetencia(action.getEmpresaSistema().getId())).thenReturn(avaliacaoDesempenhos);

		Assert.assertEquals(Action.INPUT, action.imprimeAnaliseDesempenhoCompetenciaOrganizacao());
		Assert.assertFalse(action.getActionMessages().isEmpty());
		Assert.assertTrue(action.getActionErrors().isEmpty());
	}
	
	@Test
	public void testImprimeAnaliseDesempenhoCompetenciaOrganizacaoException() {
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos = null;
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		action.setCompetenciasCheck(new Long[]{});
		
		Mockito.when(avaliacaoDesempenhoManager.findAnaliseDesempenhoOrganizacao(any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),anyString(), anyLong())).thenReturn(analiseDesempenhoOrganizacaos);
		Mockito.when(avaliacaoDesempenhoManager.findComCompetencia(action.getEmpresaSistema().getId())).thenReturn(avaliacaoDesempenhos);
		
		Assert.assertEquals(Action.INPUT, action.imprimeAnaliseDesempenhoCompetenciaOrganizacao());
		Assert.assertTrue(action.getActionMessages().isEmpty());
		Assert.assertFalse(action.getActionErrors().isEmpty());
	}
	
}
