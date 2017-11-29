package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoExameEditAction;

public class SolicitacaoExameEditActionTest extends MockObjectTestCase
{
	private SolicitacaoExameEditAction action;
	private Mock manager;
	private Mock exameManager;
	private Mock exameSolicitacaoExameManager;
	private Mock medicoCoordenadorManager;
	private Mock clinicaAutorizadaManager;

	@Override
	protected void setUp() throws Exception
	{
		action = new SolicitacaoExameEditAction();

		exameManager = mock(ExameManager.class);
		action.setExameManager((ExameManager) exameManager.proxy());
		
		manager = mock(SolicitacaoExameManager.class);
		action.setSolicitacaoExameManager((SolicitacaoExameManager) manager.proxy());
		
		exameSolicitacaoExameManager = mock(ExameSolicitacaoExameManager.class);
		action.setExameSolicitacaoExameManager((ExameSolicitacaoExameManager) exameSolicitacaoExameManager.proxy());
		
		medicoCoordenadorManager = mock(MedicoCoordenadorManager.class);
		action.setMedicoCoordenadorManager((MedicoCoordenadorManager) medicoCoordenadorManager.proxy());
		
		clinicaAutorizadaManager = mock(ClinicaAutorizadaManager.class);
		action.setClinicaAutorizadaManager((ClinicaAutorizadaManager) clinicaAutorizadaManager.proxy());
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L); 
		empresa.setCidade(CidadeFactory.getEntity(2L));
		action.setEmpresaSistema(empresa);
		
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	@Override
	protected void tearDown() throws Exception
	{
		action = null;
		exameManager = null;
		Mockit.restoreAllOriginalDefinitions();
	}
	
	public void testImprimirSolicitacaoExames() throws Exception
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		action.setSolicitacaoExame(solicitacaoExame);
		action.setGravarEImprimir(false);
		action.setTipoDeImpressao("inteira");
		Collection<SolicitacaoExameRelatorio> relatorios = new ArrayList<SolicitacaoExameRelatorio>();
		relatorios.add(new SolicitacaoExameRelatorio());
		
		manager.expects(once()).method("imprimirSolicitacaoExames").with(eq(solicitacaoExame.getId())).will(returnValue(relatorios));
		
		assertEquals("success_inteira",action.imprimirSolicitacaoExames());
	}
	
	public void testRelatorioAtendimentosMedicos()
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		action.setInicio(inicio);
		action.setFim(fim);
		
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(SolicitacaoExameFactory.getEntity(34L));
		
		manager.expects(once()).method("getRelatorioAtendimentos").will(returnValue(solicitacaoExames));
		
		assertEquals("success", action.relatorioAtendimentosMedicos());
		assertEquals(1,action.getParametros().get("TOTAL"));
	}
	public void testRelatorioAtendimentosColecaoVazia()
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		action.setInicio(inicio);
		action.setFim(fim);
		
		manager.expects(once()).method("getRelatorioAtendimentos").will(throwException(new ColecaoVaziaException("Não existem dados para o filtro informado.")));
		
		medicoCoordenadorManager.expects(once()).method("findByEmpresa").will(returnValue(new ArrayList<MedicoCoordenador>()));
		
		assertEquals("input", action.relatorioAtendimentosMedicos());
		assertEquals(1,action.getActionMessages().size());
	}
	public void testRelatorioAtendimentosException()
	{
		manager.expects(once()).method("getRelatorioAtendimentos").will(throwException(new NullPointerException()));
		
		medicoCoordenadorManager.expects(once()).method("findByEmpresa").will(returnValue(new ArrayList<MedicoCoordenador>()));
		
		assertEquals("input", action.relatorioAtendimentosMedicos());
		assertEquals(1,action.getActionErrors().size());
	}

	public void testGetSet(){
		action.setSolicitacaoExame(null);
		assertNotNull(action.getSolicitacaoExame());

		action.setNomeBusca("");
		action.getNomeBusca();
		action.getMotivos();
		assertEquals(MotivoSolicitacaoExame.DEMISSIONAL,action.getMotivoDEMISSIONAL());
		assertEquals(MotivoSolicitacaoExame.CONSULTA,action.getMotivoCONSULTA());
		assertEquals(MotivoSolicitacaoExame.ATESTADO,action.getMotivoATESTADO());
		action.getInicio();
		action.getFim();
		action.getModel();
		action.setCandidato(null);
		action.setColaborador(null);
		action.getCandidato();
		action.getColaborador();
		action.setExamesPara('C');
		action.getExamesPara();
		action.setMatriculaBusca("2131");
		action.getMatriculaBusca();
		action.getMedicoCoordenadors();
		action.getColaboradors();
		action.getCandidatos();
		action.getExames();
		action.isShowFilter();
		
		action.getSolicitacaoExame().setColaborador(new Colaborador());
		action.getSolicitacaoExame().getPessoa();
		action.getSolicitacaoExame().setCandidato(new Candidato());
		action.getSolicitacaoExame().getPessoa();
	}
}
