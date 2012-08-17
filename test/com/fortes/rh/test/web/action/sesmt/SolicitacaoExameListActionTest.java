package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoExameListAction;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoExameListActionTest extends MockObjectTestCase
{

	private SolicitacaoExameListAction action;
	private Mock manager;
	private Mock exameManager;
	private Mock exameSolicitacaoExameManager;

	protected void setUp() throws Exception
	{
		action = new SolicitacaoExameListAction();

		exameManager = mock(ExameManager.class);
		action.setExameManager((ExameManager) exameManager.proxy());
		
		manager = mock(SolicitacaoExameManager.class);
		action.setSolicitacaoExameManager((SolicitacaoExameManager) manager.proxy());
		
		exameSolicitacaoExameManager = mock(ExameSolicitacaoExameManager.class);
		action.setExameSolicitacaoExameManager((ExameSolicitacaoExameManager) exameSolicitacaoExameManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
	}

	@Override
	protected void tearDown() throws Exception
	{
		action = null;
		exameManager = null;
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testList() throws Exception
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(23L);
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(solicitacaoExame);
		
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = new ArrayList<ExameSolicitacaoExame>();
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExame);
		
		action.setResultado(ResultadoExame.NORMAL.toString());
		
		exameManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
		manager.expects(once()).method("getCount").will(returnValue(1));
		manager.expects(once()).method("findAllSelect").will(returnValue(solicitacaoExames));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").will(returnValue(exameSolicitacaoExames));

		assertEquals("success", action.list());
		assertEquals(1, action.getSolicitacaoExames().size());
		assertFalse(solicitacaoExame.isSemExames());
	}

	public void testDelete() throws Exception
	{
		SolicitacaoExame solicitacaoExame = new SolicitacaoExame();
		solicitacaoExame.setId(1L);
		action.setSolicitacaoExame(solicitacaoExame);

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		manager.expects(once()).method("remove").with(eq(solicitacaoExame.getId()));
		manager.expects(once()).method("ajustaOrdemDoList").with(ANYTHING, ANYTHING);
		
		assertEquals("success",action.delete());
	}

	public void testDeleteException() throws Exception
	{
		Exception exception = null;

		action.setSolicitacaoExameManager(null);

		try
		{
			action.delete();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}


	public void testGetSet(){
		action.getExamesCheck();
		action.getExamesCheckList();
		action.setSolicitacaoExame(null);
		assertNotNull(action.getSolicitacaoExame());
		action.setDataIni(null);
		action.setDataFim(null);
		action.getDataFim();
		action.getDataIni();
		action.setNomeBusca("");
		action.getNomeBusca();
		action.getMotivos();
		action.getMotivo();
		action.setMotivo("");
		action.setExamesCheck(new String[0]);
		action.setResultado("");
		action.getResultado();
		action.setMatriculaBusca("123");
		assertEquals("123",action.getMatriculaBusca());
		action.setVinculo("C");
		action.getVinculo();
		assertNotNull(action.getVinculos());
		action.getExameSolicitacaoExames();
		assertEquals(MotivoSolicitacaoExame.CONSULTA, action.getMotivoCONSULTA());
		assertEquals(MotivoSolicitacaoExame.ATESTADO,action.getMotivoATESTADO());
	}
}
