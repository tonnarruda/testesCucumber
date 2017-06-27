package com.fortes.rh.test.web.action.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.AvaliacaoExperienciaEditAction;
import com.fortes.web.tags.CheckBox;

public class AvaliacaoExperienciaEditActionTest {
	private AvaliacaoExperienciaEditAction action;
	private AvaliacaoManager manager;
	private PerguntaManager perguntaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
	public void setUp() throws Exception {
		manager = mock(AvaliacaoManager.class);
		action = new AvaliacaoExperienciaEditAction();
		action.setAvaliacaoManager(manager);

		perguntaManager = mock(PerguntaManager.class);
		action.setPerguntaManager(perguntaManager);

		empresaManager = mock(EmpresaManager.class);
		action.setEmpresaManager(empresaManager);

		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);

		action.setAvaliacaoExperiencia(new Avaliacao());

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	@Test
	public void testImprimeResultado() throws Exception {
		action.setAgruparPorAspectos(true);
		Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacaoExperiencia(avaliacaoExperiencia);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);

		Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

		when(manager.findById(avaliacaoExperiencia.getId())).thenReturn(avaliacaoExperiencia);
		when(perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoExperiencia.getId(), new Long[0], new Long[0], true)).thenReturn(perguntas);
		when(manager.montaResultado(perguntas, null, null, null, null, avaliacaoExperiencia, null, null, false, null)).thenReturn(new ArrayList<ResultadoQuestionario>());

		assertEquals("success", action.imprimeResultado());
		assertNotNull(action.getPerguntas());
		assertNotNull(action.getTipoPergunta());
	}

	@Test
	public void testImprimeResultadoSemPerguntas() throws Exception {
		action.setAgruparPorAspectos(true);
		action.setExibirCabecalho(false);
		Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacaoExperiencia(avaliacaoExperiencia);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setCompartilharColaboradores(true);

		when(manager.findById(avaliacaoExperiencia.getId())).thenReturn(avaliacaoExperiencia);
		when(perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoExperiencia.getId(), new Long[0], new Long[0], true)).thenReturn(new ArrayList<Pergunta>());
		when(manager.findAllSelect(0, 0, 1l, true, TipoModeloAvaliacao.DESEMPENHO, "")).thenReturn(new ArrayList<Avaliacao>());
		when(manager.findAllSelect(0, 0, 1l, false, TipoModeloAvaliacao.DESEMPENHO, "")).thenReturn(new ArrayList<Avaliacao>());
		when(areaOrganizacionalManager.populaCheckOrderDescricao(1l)).thenReturn(new ArrayList<CheckBox>());
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametrosDoSistema);
		when(empresaManager.findEmpresasPermitidas(false, 1l, 1l, "")).thenReturn(new ArrayList<Empresa>());

		assertEquals("input", action.imprimeResultado());
	}

	@Test
	public void testImprimeResultadoException() throws Exception {
		action.setAgruparPorAspectos(true);
		Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacaoExperiencia(avaliacaoExperiencia);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);

		Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setCompartilharColaboradores(true);

		when(manager.findById(avaliacaoExperiencia.getId())).thenReturn(avaliacaoExperiencia);
		when(perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoExperiencia.getId(), new Long[0], new Long[0], true)).thenReturn(perguntas);
		
		when(manager.montaResultado(perguntas, new Long[] { 1L }, new Long[] {}, null, null, avaliacaoExperiencia, new Long[] { empresa.getId() }, 'D', false, new Long[] {})).thenThrow(new Exception());
		
		when(manager.findAllSelect(0, 0, 1l, true, TipoModeloAvaliacao.DESEMPENHO, "")).thenReturn(new ArrayList<Avaliacao>());
		when(manager.findAllSelect(0, 0, 1l, false, TipoModeloAvaliacao.DESEMPENHO, "")).thenReturn(new ArrayList<Avaliacao>());
		when(empresaManager.findEmpresasPermitidas(false, 1l, 1l, "")).thenReturn(new ArrayList<Empresa>());
		when(areaOrganizacionalManager.populaCheckOrderDescricao(1l)).thenReturn(new ArrayList<CheckBox>());
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametrosDoSistema);

		assertEquals("input", action.imprimeResultado());
	}

	@Test
	public void testGetSet() throws Exception {
		action.setAvaliacaoExperiencia(null);

		assertNotNull(action.getAvaliacaoExperiencia());
		assertTrue(action.getAvaliacaoExperiencia() instanceof Avaliacao);

		action.isPreview();
		action.getPerguntas();
		action.getTipoPergunta();
		action.getAreasCheck();
		action.getAreasCheckList();
		action.setAreasCheck(null);
		action.getAspectosCheck();
		action.setAspectosCheck(null);
		action.getPerguntasCheck();
		action.setPerguntasCheck(null);
		action.setPeriodoIni(null);
		action.setPeriodoFim(null);
		action.getAspectosCheckList();
		action.getPerguntasCheckList();
		action.isAgruparPorAspectos();
		action.isExibirCabecalho();
		action.getPeriodoIni();
		action.getPeriodoFim();
		action.getResultados();
		action.getDataSource();
		action.getParametros();
		action.getUrlVoltar();
		action.getAvaliacaoExperienciasAtivas();
		action.getAvaliacaoExperienciasInativas();
	}
}
