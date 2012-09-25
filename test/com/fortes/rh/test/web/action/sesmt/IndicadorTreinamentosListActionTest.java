package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.desenvolvimento.IndicadorTreinamentosListAction;
import com.opensymphony.webwork.ServletActionContext;

public class IndicadorTreinamentosListActionTest extends MockObjectTestCase
{
	
	private IndicadorTreinamentosListAction action;
	private Mock cursoManager;
	private Mock colaboradorTurmaManager;
	private Mock colaboradorPresencaManager;
	private Mock turmaManager;
	private Mock turmaTipoDespesaManager;
	private Mock empresaManager;

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

        turmaTipoDespesaManager = mock(TurmaTipoDespesaManager.class);
        action.setTurmaTipoDespesaManager((TurmaTipoDespesaManager)turmaTipoDespesaManager.proxy());
        
        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager)empresaManager.proxy());
        
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}
	
	public void testList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuarioLogado = UsuarioFactory.getEntity(2L);
		
		IndicadorTreinamento indicadorTreinamento =  new IndicadorTreinamento();
		action.setIndicadorTreinamento(indicadorTreinamento);
		
		boolean compartilharCandidato = true;
		Long empresaId = null;
		
		action.setEmpresasCheck(new Long[]{empresa.getId()});
		action.setUsuarioLogado(usuarioLogado);
		
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(eq(compartilharCandidato), eq(empresaId), eq(usuarioLogado.getId()), ANYTHING).will(returnValue(Arrays.asList(empresa)));
		cursoManager.expects(once()).method("findCustoMedioHora").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()}));
		cursoManager.expects(once()).method("findCustoPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()}));
		cursoManager.expects(once()).method("findHorasPerCapita").with(eq(indicadorTreinamento), ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()}));
		cursoManager.expects(once()).method("findQtdColaboradoresInscritosTreinamentos").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(new Integer(2)));
		turmaManager.expects(once()).method("quantidadeParticipantesPrevistos").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(new Integer(2)));
		turmaManager.expects(once()).method("somaCustosNaoDetalhados").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(new Double(0.0)));
		turmaTipoDespesaManager.expects(once()).method("somaDespesasPorTipo").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(new ArrayList<TipoDespesa>()));
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()}), ANYTHING).will(returnValue(new Integer(3)));
		cursoManager.expects(once()).method("countTreinamentos").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()}), ANYTHING).will(returnValue(new Integer(2)));
		
		HashMap<String, Integer> resultados = new HashMap<String, Integer>();
		resultados.put("qtdAprovados", 5);
		resultados.put("qtdReprovados", 1);
		colaboradorTurmaManager.expects(once()).method("getResultado").with(ANYTHING, ANYTHING, eq(new Long[]{empresa.getId()})).will(returnValue(resultados));
		
		assertEquals("success", action.list());
		
	}
}
