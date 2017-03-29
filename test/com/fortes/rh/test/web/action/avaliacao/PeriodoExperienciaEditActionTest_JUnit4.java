package com.fortes.rh.test.web.action.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.PeriodoExperienciaEditAction;
import com.opensymphony.xwork.Action;


public class PeriodoExperienciaEditActionTest_JUnit4
{
	private PeriodoExperienciaEditAction action;
	private PeriodoExperienciaManager manager;
	private ColaboradorManager colaboradorManager;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AvaliacaoManager avaliacaoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaManager empresaManager;

	@Before
	public void setUp() throws Exception
	{
		manager = mock(PeriodoExperienciaManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		avaliacaoDesempenhoManager = mock(AvaliacaoDesempenhoManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		avaliacaoManager = mock(AvaliacaoManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		empresaManager = mock(EmpresaManager.class);

		action = new PeriodoExperienciaEditAction();
		action.setPeriodoExperienciaManager(manager);
		action.setColaboradorManager(colaboradorManager);
		action.setAvaliacaoDesempenhoManager(avaliacaoDesempenhoManager);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setAvaliacaoManager(avaliacaoManager);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		action.setPeriodoExperiencia(new PeriodoExperiencia());
		action.setEmpresaManager(empresaManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	@Test
	public void testImprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia() throws Exception
	{
		iniciaImprImeRelatorioPeriodoDeAcompanhamentoDeExperiencia(false); 
		
		assertEquals(Action.SUCCESS, action.imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia());
	}
	
	@Test
	public void testImprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaExibirTituloAvaliacao() throws Exception
	{
		iniciaImprImeRelatorioPeriodoDeAcompanhamentoDeExperiencia(true); 
		action.setExibirTituloAvaliacao(true);
		
		assertEquals("successTitulo", action.imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia());
	}

	private void iniciaImprImeRelatorioPeriodoDeAcompanhamentoDeExperiencia(boolean exibirTituloAvaliacao) throws Exception {
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		periodoExperiencia.setDescricao("9 Dias");
		periodoExperiencia.setDias(30);

		Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
		periodoExperiencias.add(periodoExperiencia);
		
		action.setPeriodoCheck(new String[]{"1"});
		
		List<AcompanhamentoExperienciaColaborador> acompanhamentos = new ArrayList<AcompanhamentoExperienciaColaborador>();
		
		when(manager.findByIdsOrderDias(new Long[]{1L})).thenReturn(periodoExperiencias);
		when(colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(null, null, action.getEmpresaSistema(), null, null, periodoExperiencias, exibirTituloAvaliacao)).thenReturn(acompanhamentos);
		when(manager.findRodapeDiasDoPeriodoDeExperiencia(periodoExperiencias)).thenReturn("Rodapé");
	}
	
	@Test
	public void testImprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaExeption() throws Exception
	{
		Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
		periodoExperiencias.add(PeriodoExperienciaFactory.getEntity(1L));

		when(manager.findByIdsOrderDias(new Long[]{})).thenReturn(periodoExperiencias);
		when(colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(null, null, action.getEmpresaSistema(), null, null, periodoExperiencias, false)).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));
		
		assertEquals("input", action.imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia());
	}
}
