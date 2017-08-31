package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.PppRelatorioManager;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
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
import com.opensymphony.xwork.Action;

public class PppEditActionTest
{
	PppEditAction action;
	ColaboradorManager colaboradorManager;
	HistoricoColaboradorManager historicoColaboradorManager;
	PppRelatorioManager pppRelatorioManager;
	EmpresaManager empresaManager;

	@Before
	public void setUp() throws Exception
	{
		action = new PppEditAction();

		colaboradorManager = mock(ColaboradorManager.class);
		pppRelatorioManager = mock(PppRelatorioManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		empresaManager = mock(EmpresaManager.class);

		action.setColaboradorManager(colaboradorManager);
		action.setPppRelatorioManager(pppRelatorioManager);
		action.setHistoricoColaboradorManager(historicoColaboradorManager);
		action.setEmpresaManager(empresaManager);
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(Autenticador.class, MockAutenticador.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@Test
	public void testPrepareRelatorio() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setCnpj("teste");
		empresa.setRepresentanteLegal("representanteLegal");

		Colaborador c1 = new Colaborador();
		c1.setId(1L);
		c1.setEmpresa(empresa);
		action.setColaborador(c1);

		when(colaboradorManager.findByIdProjectionEmpresa(c1.getId())).thenReturn(c1);
		when(historicoColaboradorManager.verifyDataHistoricoAdmissao(c1.getId())).thenReturn(true);
		when(empresaManager.getCnae(empresa.getId())).thenReturn(empresa);
		
		assertEquals("success", action.prepareRelatorio());
	}
	
	@Test
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
    	
    	when(colaboradorManager.getCount(any(Map.class))).thenReturn(1);
    	when(colaboradorManager.findList(anyInt(), anyInt(), any(Map.class))).thenReturn(col);

    	assertEquals(action.list(), "success");
    	assertEquals(action.getColaboradors(), col);
    }

	@Test
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
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Collection<PppRelatorio> pppRelatorios = Arrays.asList(new PppRelatorio());
		
		when(pppRelatorioManager.populaRelatorioPpp(eq(colaborador), eq(action.getEmpresaSistema()), eq(data), eq(action.getNit()), eq(action.getCnae()), eq(action.getResponsavel()), eq(action.getObservacoes()), eq(respostas), eq(action.getEmpresaSistema().getId()))).thenReturn(pppRelatorios);
		
		assertEquals("success", action.gerarRelatorio());
		assertEquals(1, action.getDataSource().size());
	}
	
	@Test
	public void testGerarRelatorioPppRelatorioException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		action.setData(new Date());
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(32L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);

		when(pppRelatorioManager.populaRelatorioPpp(eq(colaborador), eq(action.getEmpresaSistema()), eq(action.getData()), eq(action.getNit()), eq(action.getCnae()), eq(action.getResponsavel()), eq(action.getObservacoes()), eq(action.getRespostas()), eq(empresa.getId()))).thenThrow(new PppRelatorioException());
		when(colaboradorManager.findByIdProjectionEmpresa(colaborador.getId())).thenReturn(colaborador);
		when(historicoColaboradorManager.verifyDataHistoricoAdmissao(colaborador.getId())).thenReturn(true);
		when(empresaManager.getCnae(empresa.getId())).thenReturn(empresa);
		
		assertEquals(Action.INPUT, action.gerarRelatorio());
		assertEquals(2, action.getActionWarnings().size());
		assertEquals("Existem pendências para a geração desse relatório. Verifique as informações abaixo antes de prosseguir: <br>", action.getActionWarnings().toArray()[0]);
	}
	
	@Test
	public void testGerarRelatorioPppException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		action.setData(new Date());
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(32L);
		colaborador.setEmpresa(empresa);
		action.setColaborador(colaborador);

		when(pppRelatorioManager.populaRelatorioPpp(eq(colaborador), eq(action.getEmpresaSistema()), eq(action.getData()), eq(action.getNit()), eq(action.getCnae()), eq(action.getResponsavel()), eq(action.getObservacoes()), eq(action.getRespostas()), eq(empresa.getId()))).thenThrow(new Exception());
		when(colaboradorManager.findByIdProjectionEmpresa(colaborador.getId())).thenReturn(colaborador);
		when(historicoColaboradorManager.verifyDataHistoricoAdmissao(colaborador.getId())).thenReturn(true);
		when(empresaManager.getCnae(empresa.getId())).thenReturn(empresa);
		
		assertEquals(Action.INPUT, action.gerarRelatorio());
		assertEquals(1, action.getActionErrors().size());
		assertEquals("Erro ao gerar relatório.", action.getActionErrors().toArray()[0]);
	}

	@Test
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
