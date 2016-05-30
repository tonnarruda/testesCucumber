package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.PpraEditAction;
import com.fortes.web.tags.CheckBox;

public class PpraEditActionTest extends MockObjectTestCase
{
	PpraEditAction action;
	Mock ambienteManager;
	Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		action = new PpraEditAction();

		ambienteManager = new Mock(AmbienteManager.class);
		action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
	    action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testPrepareRelatorio() throws Exception
	{
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		assertEquals("success", action.prepareRelatorio());
	}
	
	public void testGerarRelatorio() throws Exception
	{
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setData(new Date());
		action.setAmbienteCheck(new String[]{"50"});
		
		action.setGerarLtcat(true);
		action.setGerarPpra(true);
		
		Collection<PpraLtcatRelatorio> dataSource = new ArrayList<PpraLtcatRelatorio>();
		dataSource.add(new PpraLtcatRelatorio());
		
		ambienteManager.expects(once()).method("montaRelatorioPpraLtcat").will(returnValue(dataSource));
		
		assertEquals("success", action.gerarRelatorio());
		assertEquals(1, action.getDataSource().size());
	}
	
	public void testGerarRelatorioException() throws Exception
	{
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		ambienteManager.expects(once()).method("montaRelatorioPpraLtcat").will(throwException(new NullPointerException()));
		
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		
		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		ambienteManager.expects(once()).method("populaCheckBox").will(returnValue(ambienteCheckList));
		
		assertEquals("input", action.gerarRelatorio());
		assertEquals("Erro ao gerar relatório.", action.getActionErrors().toArray()[0]);
		assertEquals(1, action.getAmbienteCheckList().size());
	}
	public void testGerarRelatorioVazio() throws Exception
	{
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		ambienteManager.expects(once()).method("montaRelatorioPpraLtcat").will(throwException(new ColecaoVaziaException("Não existem dados para o filtro informado.")));
		
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		
		Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
		ambienteCheckList.add(new CheckBox());
		
		ambienteManager.expects(once()).method("populaCheckBox").will(returnValue(ambienteCheckList));
		
		assertEquals("input", action.gerarRelatorio());
		assertEquals("Não existem dados para o filtro informado.", action.getActionErrors().toArray()[0]);
		assertEquals(1, action.getAmbienteCheckList().size());
	}
	
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
