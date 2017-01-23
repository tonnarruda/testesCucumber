package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.cargosalario.HistoricoColaboradorListAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class HistoricoColaboradorListActionTest
{
	private HistoricoColaboradorListAction action;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaManager empresaManager;

	@Before
	public void setUp () throws Exception
	{
		action = new HistoricoColaboradorListAction();

		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		empresaManager = mock(EmpresaManager.class);
		
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setHistoricoColaboradorManager(historicoColaboradorManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		action.setEmpresaManager(empresaManager);
		
		action.setAgruparPor('C');
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	@Test
	public void testPrepareRelatorioSituacoes()
	{
		when(areaOrganizacionalManager.populaCheckOrderDescricao(1L)).thenReturn(new ArrayList<CheckBox>());
		when(estabelecimentoManager.populaCheckBox(1L)).thenReturn(new ArrayList<CheckBox>());
		assertEquals("success",action.prepareRelatorioSituacoes());
	}
	
	@Test
	public void testRelatorioSituacoes() throws ColecaoVaziaException, Exception
	{
		when(historicoColaboradorManager.montaRelatorioSituacoes(1L, null, null, new Long[]{}, new Long[]{}, "T", 'C', false)).thenReturn(new ArrayList<HistoricoColaborador>());
		assertEquals("successAgruparPorColaborador",action.relatorioSituacoes());
	}
	
	@Test
	public void testRelatorioSituacoesColecaoVaziaException() throws ColecaoVaziaException, Exception
	{
		when(historicoColaboradorManager.montaRelatorioSituacoes(1L, null, null, new Long[]{}, new Long[]{}, "T", 'C', false)).thenThrow(new ColecaoVaziaException("Não existem dados para o filtro informado."));
		when(areaOrganizacionalManager.populaCheckOrderDescricao(1L)).thenReturn(new ArrayList<CheckBox>());
		when(estabelecimentoManager.populaCheckBox(1L)).thenReturn(new ArrayList<CheckBox>());
		
		assertEquals("input",action.relatorioSituacoes());
	}
	
	@Test
	public void testRelatorioSituacoesException() throws ColecaoVaziaException, Exception
	{
		when(historicoColaboradorManager.montaRelatorioSituacoes(1L, null, null, new Long[]{}, new Long[]{}, "T", 'C', false)).thenThrow(new Exception());
		when(areaOrganizacionalManager.populaCheckOrderDescricao(1L)).thenReturn(new ArrayList<CheckBox>());
		when(estabelecimentoManager.populaCheckBox(1L)).thenReturn(new ArrayList<CheckBox>());
		
		assertEquals("input",action.relatorioSituacoes());
	}
	
	@Test
	public void testImprimirRelatorioPromocoes() throws Exception
	{
		RelatorioPromocoes relatorioPromocoes = new RelatorioPromocoes(new Date(), "A");
		List<RelatorioPromocoes> relatorioPromocoesCollection = java.util.Arrays.asList(relatorioPromocoes);
		when(historicoColaboradorManager.getPromocoes(new Long[]{}, new Long[]{}, null, null, new Long[]{1L})).thenReturn(relatorioPromocoesCollection);
		assertEquals(Action.SUCCESS,action.imprimirRelatorioPromocoes());
	}
	
	@Test
	public void testImprimirRelatorioPromocoesVazio() throws Exception
	{
		List<RelatorioPromocoes> relatorioPromocoesCollection = new ArrayList<RelatorioPromocoes>();
		when(historicoColaboradorManager.getPromocoes(new Long[]{}, new Long[]{}, null, null, new Long[]{1L})).thenReturn(relatorioPromocoesCollection);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		assertEquals(Action.INPUT,action.imprimirRelatorioPromocoes());
	}
	
	@Test
	public void testGetSet()
	{
		action.setColaborador(null);
		action.getColaborador();
		action.getParametros();
		action.getAreasCheck();
		action.setAreasCheck(null);
		action.getAreasCheckList();
		action.getEstabelecimentosCheck();
		action.setEstabelecimentosCheck(null);
		action.getEstabelecimentosCheckList();
		action.getDataFim();
		action.getDataIni();
		action.setDataIni(new Date());
		action.setDataFim(new Date());
	}
}
