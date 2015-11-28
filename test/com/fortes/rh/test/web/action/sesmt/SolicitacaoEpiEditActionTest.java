package com.fortes.rh.test.web.action.sesmt;

import java.util.Arrays;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemDevolucaoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoEpiEditAction;

public class SolicitacaoEpiEditActionTest extends MockObjectTestCase
{

	private SolicitacaoEpiEditAction action;
	private Mock solicitacaoEpiManager;
	private Mock solicitacaoEpiItemManager;
	private Mock solicitacaoEpiItemDevolucaoManager;
	private Mock solicitacaoEpiItemEntregaManager;

	protected void setUp() throws Exception
	{
		action = new SolicitacaoEpiEditAction();
		
		solicitacaoEpiManager = mock(SolicitacaoEpiManager.class);
		action.setSolicitacaoEpiManager((SolicitacaoEpiManager) solicitacaoEpiManager.proxy());
		
		solicitacaoEpiItemManager = mock(SolicitacaoEpiItemManager.class);
		action.setSolicitacaoEpiItemManager((SolicitacaoEpiItemManager) solicitacaoEpiItemManager.proxy());
		
		solicitacaoEpiItemDevolucaoManager = mock(SolicitacaoEpiItemDevolucaoManager.class);
		action.setSolicitacaoEpiItemDevolucaoManager((SolicitacaoEpiItemDevolucaoManager) solicitacaoEpiItemDevolucaoManager.proxy());
		
		solicitacaoEpiItemEntregaManager = mock(SolicitacaoEpiItemEntregaManager.class);
		action.setSolicitacaoEpiItemEntregaManager((SolicitacaoEpiItemEntregaManager) solicitacaoEpiItemEntregaManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
	}

	protected void tearDown() throws Exception
	{
		action = null;
		solicitacaoEpiManager = null;
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testPrepareEntrega() throws Exception
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findById").with(eq(solicitacaoEpi.getId())).will(returnValue(solicitacaoEpi));
		
		solicitacaoEpiItemManager.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).will(returnValue(Arrays.asList(solicitacaoEpi)));

		assertEquals("success", action.prepareEntrega());
	}
	
	public void testPrepareInsertDevolucao() throws Exception{

		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));

		assertEquals("success", action.prepareInsertDevolucao());
	}
	
	public void testPrepareUpdateDevolucao() throws Exception
	{
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(1L);
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("findById").with(eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(solicitacaoEpiItemDevolucao));
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));
		
		assertEquals("success", action.prepareUpdateDevolucao());
	}
	
	public void testInsertDevolucao() throws Exception
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(data);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemEntregaManager.expects(once()).method("getTotalEntregue").with(eq(solicitacaoEpiItem.getId()), eq(null)).will(returnValue(1));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("getTotalDevolvido").with(eq(solicitacaoEpiItem.getId()), eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(0));
		
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("save").with(eq(solicitacaoEpiItemDevolucao));
		assertEquals("success", action.insertDevolucao());
	}
	
	public void testInsertDevolucaoComDataInferiorADataDaSolicitacaoEPI() throws Exception
	{
		Date dataSolicitacaoEpi = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataDevolucao = DateUtil.criarDataMesAno(1, 1, 2014);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(dataSolicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(dataDevolucao, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));
		
		assertEquals("input", action.insertDevolucao());
		assertEquals("A data de devolução não pode ser anterior à data de solicitação", action.getActionWarnings().iterator().next());
	}
	
	public void testInsertDevolucaoComQuantidadeDevolvidaMaiorQueASolicitada() throws Exception
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(data);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemEntregaManager.expects(once()).method("getTotalEntregue").with(eq(solicitacaoEpiItem.getId()), eq(null)).will(returnValue(1));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("getTotalDevolvido").with(eq(solicitacaoEpiItem.getId()), eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(1));
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));
		
		assertEquals("input", action.insertDevolucao());
		assertEquals("O total de itens devolvidos não pode ser superior à quantidade de itens entregues", action.getActionWarnings().iterator().next());
	}
	
	public void testUpdateDevolucao() throws Exception
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(data);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemEntregaManager.expects(once()).method("getTotalEntregue").with(eq(solicitacaoEpiItem.getId()), eq(null)).will(returnValue(1));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("getTotalDevolvido").with(eq(solicitacaoEpiItem.getId()), eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(0));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("update").with(eq(solicitacaoEpiItemDevolucao));
		
		assertEquals("success", action.updateDevolucao());
	}
	
	public void testUpdateDevolucaoComDataInferiorADataDaSolicitacaoEPI() throws Exception
	{
		Date dataSolicitacaoEpi = DateUtil.criarDataMesAno(1, 1, 2015);
		Date dataDevolucao = DateUtil.criarDataMesAno(1, 1, 2014);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(dataSolicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(dataDevolucao, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("findById").with(eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(solicitacaoEpiItemDevolucao));
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));

		assertEquals("input", action.updateDevolucao());
		assertEquals("A data de devolução não pode ser anterior à data de solicitação", action.getActionWarnings().iterator().next());
	}
	
	public void testUpdateDevolucaoComQuantidadeDevolvidaMaiorQueASolicitada() throws Exception
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(data);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(1L);
		solicitacaoEpiItem.setQtdSolicitado(1);

		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(data, 1, solicitacaoEpiItem);
		
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpiItem(solicitacaoEpiItem);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findEntidadeComAtributosSimplesById").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemEntregaManager.expects(once()).method("getTotalEntregue").with(eq(solicitacaoEpiItem.getId()), eq(null)).will(returnValue(1));
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("getTotalDevolvido").with(eq(solicitacaoEpiItem.getId()), eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(1));
		
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("findById").with(eq(solicitacaoEpiItemDevolucao.getId())).will(returnValue(solicitacaoEpiItemDevolucao));
		solicitacaoEpiItemManager.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiItem.getId())).will(returnValue(solicitacaoEpiItem));

		assertEquals("input", action.updateDevolucao());
		assertEquals("O total de itens devolvidos não pode ser superior à quantidade de itens entregues", action.getActionWarnings().iterator().next());
	}
	

	public void testDeleteDevolucao() throws Exception
	{
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(1L);
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		action.setSolicitacaoEpiItemDevolucao(solicitacaoEpiItemDevolucao);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiItemDevolucaoManager.expects(once()).method("remove").with(eq(solicitacaoEpiItemDevolucao.getId()));
		solicitacaoEpiManager.expects(once()).method("findById").with(eq(solicitacaoEpi.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemManager.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).will(returnValue(Arrays.asList(solicitacaoEpi)));
		
		assertEquals("success",action.deleteDevolucao());
		assertEquals("Devolução do EPI excluída com sucesso",action.getActionSuccess().iterator().next());
	}

	public void testDeleteDevolucaoException() throws Exception
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		action.setSolicitacaoEpi(solicitacaoEpi);
		
		solicitacaoEpiManager.expects(once()).method("findById").with(eq(solicitacaoEpi.getId())).will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemManager.expects(once()).method("findBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).will(returnValue(Arrays.asList(solicitacaoEpi)));

		assertEquals("success",action.deleteDevolucao());
		assertEquals("Erro ao excluir devolução.",action.getActionErrors().iterator().next());
	}
}
