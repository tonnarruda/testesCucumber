package com.fortes.rh.test.web.action.sesmt;

import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.IndicadorTreinamentosListAction;
import com.opensymphony.webwork.ServletActionContext;

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
}
