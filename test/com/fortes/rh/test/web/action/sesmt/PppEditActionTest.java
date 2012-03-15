package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockAutenticador;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockStringUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.sesmt.PppEditAction;
import com.opensymphony.webwork.ServletActionContext;

public class PppEditActionTest extends MockObjectTestCase
{
	PppEditAction action;
	Mock colaboradorManager;
	Mock historicoColaboradorManager;
	Mock funcaoManager;

	protected void setUp() throws Exception
	{
		action = new PppEditAction();

		colaboradorManager = new Mock(ColaboradorManager.class);
		funcaoManager = new Mock(FuncaoManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);

		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
		action.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(Autenticador.class, MockAutenticador.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	public void testPrepareRelatorio() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setCnpj("teste");
		empresa.setRepresentanteLegal("representanteLegal");

		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		c1.setEmpresa(empresa);
		action.setColaborador(c1);

		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").with(eq(c1.getId())).will(returnValue(c1));
		historicoColaboradorManager.expects(once()).method("verifyDataHistoricoAdmissao").will(returnValue(true));
		assertEquals("success", action.prepareRelatorio());
	}
	
	 public void testList() throws Exception
	    {
			Mockit.redefineMethods(StringUtil.class, MockStringUtil.class);
	    	Colaborador c1 = new Colaborador();
	    	c1.setId(1L);
	    	c1.setNome("nome 1");

	    	Colaborador c2 = new Colaborador();
	    	c2.setId(2L);
	    	c2.setNome("nome 2");

	    	Collection<Colaborador> col = new ArrayList<Colaborador>();
	    	col.add(c1);
	    	col.add(c2);

	    	colaboradorManager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(1));
	    	colaboradorManager.expects(once()).method("findList").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(col));

	    	assertEquals(action.list(), "success");
	    	assertEquals(action.getColaboradors(), col);
	    }

	public void testGerarRelatorioPpp() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(32L);
		Date data = new Date();
		String[] respostas = new String[]{"S","S","S","N","N"};
		
		action.setColaborador(colaborador);
		action.setData(data);
		action.setRespostas(respostas);
		action.setResponsavel("Resp");
		action.setNit("12332");
		action.setObservacoes("...");
		
		funcaoManager.expects(once()).method("populaRelatorioPpp").with(new Constraint[]{eq(colaborador), eq(data), ANYTHING, ANYTHING, ANYTHING, eq(respostas)});
		
		action.gerarRelatorio();
	}
	
	public void testGerarRelatorioPppException() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(32L);
		colaborador.setEmpresa(EmpresaFactory.getEmpresa(2L));
		action.setColaborador(colaborador);

		PppRelatorioException exception = new PppRelatorioException();
		
		// PppRelatorioException
		funcaoManager.expects(once()).method("populaRelatorioPpp").will(throwException(exception));
		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").with(eq(colaborador.getId())).will(returnValue(colaborador));
		historicoColaboradorManager.expects(once()).method("verifyDataHistoricoAdmissao").will(returnValue(true));
		
		action.gerarRelatorio();
		
		// Exception comum.
		funcaoManager.expects(once()).method("populaRelatorioPpp").will(throwException(new Exception()));
		colaboradorManager.expects(once()).method("findByIdProjectionEmpresa").with(eq(colaborador.getId())).will(returnValue(colaborador));
		historicoColaboradorManager.expects(once()).method("verifyDataHistoricoAdmissao").will(returnValue(true));
		
		action.gerarRelatorio();
		
		assertEquals(2, action.getActionErrors().size());
	}

	public void testGetSet()
	{
		action.getParametros();
		action.getColaborador();
		action.getNit();
		action.getResponsavel();
		action.getDataSource();
		action.getData();
		action.getObservacoes();
		action.setNomeBusca("");
		action.getNomeBusca();
		action.setCpfBusca("111111111");
		action.getCpfBusca();
		action.getRespostas();
		
	}
}
