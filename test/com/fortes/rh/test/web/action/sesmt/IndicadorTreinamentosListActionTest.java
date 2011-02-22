package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import mockit.Mockit;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AgendaManager;
import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.desenvolvimento.IndicadorTreinamentosListAction;
import com.fortes.rh.web.action.sesmt.AgendaEditAction;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.opensymphony.webwork.ServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;

public class IndicadorTreinamentosListActionTest extends MockObjectTestCase
{
	
	private IndicadorTreinamentosListAction action;
	private Mock cursoManager;
	private Mock colaboradorTurmaManager;
	private Mock colaboradorPresencaManager;
	private Mock turmaManager;
	

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new IndicadorTreinamentosListAction();

		cursoManager = mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());
        
        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager)colaboradorTurmaManager.proxy());

        colaboradorPresencaManager = mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager((ColaboradorPresencaManager)colaboradorPresencaManager.proxy());

        turmaManager = mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager)turmaManager.proxy());
        
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}


	public void testGraficoCumprimentoPlanoTreinamento() throws Exception
	{
		
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		action.setEmpresaSistema(empresa);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		indicadorTreinamento.setDataIni(DateUtil.criarAnoMesDia(2011, 01, 20));
		indicadorTreinamento.setDataFim(DateUtil.criarAnoMesDia(2011, 01, 23));
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		
		cursoManager.expects(once()).method("countTreinamentos").with(eq(indicadorTreinamento.getDataIni()), eq(indicadorTreinamento.getDataFim()), eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(3)));
		cursoManager.expects(once()).method("countTreinamentos").with(eq(indicadorTreinamento.getDataIni()), eq(indicadorTreinamento.getDataFim()), eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(2)));
		assertEquals("success", action.graficoCumprimentoPlanoTreinamento());
		
		
	}
	
	public void testGraficoDesempenho() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		action.setEmpresaSistema(empresa);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		indicadorTreinamento.setDataIni(DateUtil.criarAnoMesDia(2011, 01, 20));
		indicadorTreinamento.setDataFim(DateUtil.criarAnoMesDia(2011, 01, 23));
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		HashMap<String, Integer> resultados = new HashMap<String, Integer>();
		resultados.put("qtdAprovados", 5);
		resultados.put("qtdReprovados", 1);
		colaboradorTurmaManager.expects(once()).method("getResultado").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(resultados));
		assertEquals("success", action.graficoDesempenho());
	}
	
	public void testGraficoVagasPorInscrito() throws Exception
	{

		Empresa empresa = new Empresa();
		empresa.setId(1L);
		action.setEmpresaSistema(empresa);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		indicadorTreinamento.setDataIni(DateUtil.criarAnoMesDia(2011, 01, 20));
		indicadorTreinamento.setDataFim(DateUtil.criarAnoMesDia(2011, 01, 23));
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		colaboradorTurmaManager.expects(once()).method("findQuantidade").with(eq(indicadorTreinamento.getDataIni()), eq(indicadorTreinamento.getDataFim()), eq(empresa.getId())).will(returnValue(new Integer(2)));
		turmaManager.expects(once()).method("quantidadeParticipantesPrevistos").with(eq(indicadorTreinamento.getDataIni()), eq(indicadorTreinamento.getDataFim()), eq(empresa.getId())).will(returnValue(new Integer(2)));
	
		assertEquals("success", action.graficoVagasPorInscrito());
		
	} 
	
	public void testList() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		action.setEmpresaSistema(empresa);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		cursoManager.expects(once()).method("findCustoMedioHora").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		cursoManager.expects(once()).method("findCustoPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		cursoManager.expects(once()).method("findHorasPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		colaboradorTurmaManager.expects(once()).method("findQuantidade").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(new Integer(2)));
		turmaManager.expects(once()).method("quantidadeParticipantesPrevistos").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(new Integer(2)));
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(3)));
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(2)));
		
		HashMap<String, Integer> resultados = new HashMap<String, Integer>();
		resultados.put("qtdAprovados", 5);
		resultados.put("qtdReprovados", 1);
		colaboradorTurmaManager.expects(once()).method("getResultado").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(resultados));
		
		assertEquals("success", action.list());
		
	}
	
	public void testImprimir() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		action.setEmpresaSistema(empresa);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		cursoManager.expects(once()).method("findCustoMedioHora").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		cursoManager.expects(once()).method("findCustoPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		cursoManager.expects(once()).method("findHorasPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(empresa.getId()));
		
		colaboradorTurmaManager.expects(once()).method("findQuantidade").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(new Integer(2)));
		turmaManager.expects(once()).method("quantidadeParticipantesPrevistos").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(new Integer(2)));
		
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(3)));
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(empresa.getId()), ANYTHING).will(returnValue(new Integer(2)));
		
		HashMap<String, Integer> resultados = new HashMap<String, Integer>();
		resultados.put("qtdAprovados", 5);
		resultados.put("qtdReprovados", 1);
		colaboradorTurmaManager.expects(once()).method("getResultado").with(ANYTHING, ANYTHING, eq(empresa.getId())).will(returnValue(resultados));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		
		assertEquals("success", action.imprimir());
	}
}
