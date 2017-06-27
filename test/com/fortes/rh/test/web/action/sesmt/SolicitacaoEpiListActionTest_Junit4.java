package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoEpiListAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RelatorioUtil.class)
public class SolicitacaoEpiListActionTest_Junit4
{
	private SolicitacaoEpiListAction action;
	private SolicitacaoEpiManager solicitacaoEpiManager;
	private EstabelecimentoManager estabelecimentoManager;
	private TipoEPIManager tipoEPIManager;
	private SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager;
	
	@Before
	public void setUp() throws Exception
	{
		action = new SolicitacaoEpiListAction();
		
		solicitacaoEpiManager = mock(SolicitacaoEpiManager.class);
		action.setSolicitacaoEpiManager(solicitacaoEpiManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		tipoEPIManager = mock(TipoEPIManager.class);
		action.setTipoEPIManager(tipoEPIManager);
		
		solicitacaoEpiItemDevolucaoManager = mock(SolicitacaoEpiItemDevolucaoManager.class);
		action.setSolicitacaoEpiItemDevolucaoManager(solicitacaoEpiItemDevolucaoManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		PowerMockito.mockStatic(RelatorioUtil.class);
	}
	
	@Test
	public void testListSemSolicitacoesASeremListadas() throws Exception
	{
		when(estabelecimentoManager.populaCheckBox(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<CheckBox>());
		when(tipoEPIManager.findCollectionTipoEPI(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<TipoEPI>());
		when(solicitacaoEpiManager.findAllSelect(anyInt(), anyInt(), anyLong(), any(Date.class), any(Date.class), any(Colaborador.class), anyString(), anyLong(), anyString(), any(String[].class), anyChar())).thenReturn(new SolicitacaoEpiVO());
		assertEquals("success", action.list());
		assertEquals("Nenhuma solicitação de EPIs a ser listada.", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void testList() throws Exception
	{
		SolicitacaoEpiVO solicitacaoEpiVO = new SolicitacaoEpiVO();
		solicitacaoEpiVO.setQtdSolicitacaoEpis(1);
		solicitacaoEpiVO.setSolicitacaoEpis(Arrays.asList(new SolicitacaoEpi()));

		when(estabelecimentoManager.populaCheckBox(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<CheckBox>());
		when(tipoEPIManager.findCollectionTipoEPI(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<TipoEPI>());
		when(solicitacaoEpiManager.findAllSelect(anyInt(), anyInt(), anyLong(), any(Date.class), any(Date.class), any(Colaborador.class), anyString(), anyLong(), anyString(), any(String[].class), anyChar())).thenReturn(solicitacaoEpiVO);
		
		assertEquals("success", action.list());
		assertEquals(0, action.getActionMessages().size());
	}
	
	@Test
	public void testRelatorioVencimentoEpiAgrupadoPorEpi() throws ColecaoVaziaException
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpi> dataSourceSolicitacaoEpi = Arrays.asList(new SolicitacaoEpi());

		when(solicitacaoEpiManager.findRelatorioVencimentoEpi(1L, null, 'E', false, null, null, null)).thenReturn(dataSourceSolicitacaoEpi);
		when(solicitacaoEpiItemDevolucaoManager.removeItensDevolvidos(dataSourceSolicitacaoEpi)).thenReturn(dataSourceSolicitacaoEpi);
		when(RelatorioUtil.getParametrosRelatorio("EPIs com Prazo a Vencer em " + DateUtil.formataDiaMesAno(null), action.getEmpresaSistema(), null)).thenReturn(new HashMap<String, Object>());
		
		assertEquals("success", action.relatorioVencimentoEpi());
	}
	
	@Test
	public void testRelatorioVencimentoEpiAgrupadoPorColaborador() throws ColecaoVaziaException
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpi> dataSourceSolicitacaoEpi = Arrays.asList(new SolicitacaoEpi());

		when(solicitacaoEpiManager.findRelatorioVencimentoEpi(1L, null, 'C', false, null, null, null)).thenReturn(dataSourceSolicitacaoEpi);
		when(solicitacaoEpiItemDevolucaoManager.removeItensDevolvidos(dataSourceSolicitacaoEpi)).thenReturn(dataSourceSolicitacaoEpi);
		when(RelatorioUtil.getParametrosRelatorio("EPIs com Prazo a Vencer em " + DateUtil.formataDiaMesAno(null), action.getEmpresaSistema(), null)).thenReturn(new HashMap<String, Object>());
		
		assertEquals("success_agrupar_colaborador", action.relatorioVencimentoEpi());
	}
}
