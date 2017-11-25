package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.PpraEditAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RelatorioUtil.class, Autenticador.class})
public class PpraEditActionTest 
{
	PpraEditAction action;
	AmbienteManager ambienteManager;
	EstabelecimentoManager estabelecimentoManager;

	@Before
	public void setUp() throws Exception
	{
		action = new PpraEditAction();

		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager(ambienteManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
	    action.setEstabelecimentoManager(estabelecimentoManager);

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		PowerMockito.mockStatic(RelatorioUtil.class);
		PowerMockito.mockStatic(Autenticador.class);
	}
	
	@Test
	public void testPrepareRelatorioComVersaoDemonstracao() throws Exception
	{
		when(Autenticador.isDemo()).thenReturn(true);
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());

		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), null, action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);

		assertEquals("success", action.prepareRelatorio());
		assertEquals("Este relatório não pode ser impresso na Versão Demonstração.", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void testPrepareRelatorioComEstabelecimentoNulo() throws Exception
	{
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());

		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), null, action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);

		assertEquals("success", action.prepareRelatorio());
	}
	
	@Test
	public void testPrepareRelatorio() throws Exception
	{
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());

		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), action.getEstabelecimento().getId(), action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);

		assertEquals("success", action.prepareRelatorio());
	}

	@Test
	public void testGerarRelatorioComVersaoDemonstracao() throws Exception
	{
		when(Autenticador.isDemo()).thenReturn(true);
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());

		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), null, action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);

		assertEquals("input", action.gerarRelatorio());
		assertEquals("Este relatório não pode ser impresso na Versão Demonstração.", action.getActionMessages().iterator().next());
	}
	
	@Test
	public void testGerarRelatorio() throws Exception
	{
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setData(new Date());
		action.setAmbienteCheck(new String[]{"50"});
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		action.setGerarLtcat(true);
		action.setGerarPpra(true);
		
		Collection<PpraLtcatRelatorio> dataSource = new ArrayList<PpraLtcatRelatorio>();
		dataSource.add(new PpraLtcatRelatorio());
		
		when(ambienteManager.montaRelatorioPpraLtcat(action.getEmpresaSistema(), action.getEstabelecimento(), action.getLocalAmbiente(), 
				action.getData(), action.getAmbienteCheck(), action.isGerarPpra(), action.isGerarLtcat(), action.isExibirComposicaoSesmt())).thenReturn(dataSource);
		
		assertEquals("success", action.gerarRelatorio());
		assertEquals(1, action.getDataSource().size());
	}
	
	@Test
	public void testGerarRelatorioException() throws Exception
	{
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		
		doThrow(new NullPointerException()).when(ambienteManager).montaRelatorioPpraLtcat(action.getEmpresaSistema(), action.getEstabelecimento(), action.getLocalAmbiente(), 
				action.getData(), action.getAmbienteCheck(), action.isGerarPpra(), action.isGerarLtcat(), action.isExibirComposicaoSesmt());
				
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		
		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), action.getEstabelecimento().getId(), action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);
		
		assertEquals("input", action.gerarRelatorio());
		assertEquals("Erro ao gerar relatório.", action.getActionErrors().toArray()[0]);
		assertEquals(1, action.getAmbienteCheckList().size());
	}
	
	@Test
	public void testGerarRelatorioVazio() throws Exception
	{
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		doThrow(new ColecaoVaziaException("Não existem dados para o filtro informado.")).when(ambienteManager).montaRelatorioPpraLtcat(action.getEmpresaSistema(), action.getEstabelecimento(), action.getLocalAmbiente(), 
				action.getData(), action.getAmbienteCheck(), action.isGerarPpra(), action.isGerarLtcat(), action.isExibirComposicaoSesmt());

		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());

		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		when(ambienteManager.populaCheckBox(action.getEmpresaSistema().getId(), action.getEstabelecimento().getId(), action.getLocalAmbiente(),action.getData())).thenReturn(ambienteCheckList);

		assertEquals("input", action.gerarRelatorio());
		assertEquals("Não existem dados para o filtro informado.", action.getActionMessages().toArray()[0]);
		assertEquals(1, action.getAmbienteCheckList().size());
	}
	
	@Test
	public void testGetSets()
	{
		action.getData();
		action.getEstabelecimentos();
		action.isGerarLtcat();
		action.isGerarPpra();
		action.getAmbienteCheck();
		action.getEstabelecimento();
		action.getParametros();
	}
}
