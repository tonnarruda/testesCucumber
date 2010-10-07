package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.ColaboradorOcorrenciaRelatorio;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.web.action.geral.OcorrenciaEditAction;

public class OcorrenciaEditActionTest extends MockObjectTestCase
{
	private OcorrenciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(OcorrenciaManager.class);
		action = new OcorrenciaEditAction();
		action.setOcorrenciaManager((OcorrenciaManager) manager.proxy());
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}
	
	public void testPrepareInsert() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(1L);
		action.setOcorrencia(ocorrencia);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new Ocorrencia()));
		assertEquals("success", action.prepareInsert());
		assertNotNull(action.getOcorrencia());
		assertTrue(action.isEmpresaIntegradaComAC());
	}

	public void testAdicionaNaCollecao() throws Exception{

		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setId(1L);

		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setId(2L);

		Ocorrencia o1 = OcorrenciaFactory.getEntity();
		o1.setPontuacao(3);

		Ocorrencia o2 = OcorrenciaFactory.getEntity();
		o2.setPontuacao(2);

		Ocorrencia o3 = OcorrenciaFactory.getEntity();
		o3.setPontuacao(1);

		Ocorrencia o4 = OcorrenciaFactory.getEntity();
		o4.setPontuacao(3);

		Ocorrencia o5 = OcorrenciaFactory.getEntity();
		o5.setPontuacao(1);

		ColaboradorOcorrencia co1 = new ColaboradorOcorrencia();
		co1.setColaborador(c1);
		co1.setOcorrencia(o1);

		ColaboradorOcorrencia co2 = new ColaboradorOcorrencia();
		co2.setColaborador(c1);
		co2.setOcorrencia(o2);

		ColaboradorOcorrencia co3 = new ColaboradorOcorrencia();
		co3.setColaborador(c2);
		co3.setOcorrencia(o3);

		ColaboradorOcorrencia co4 = new ColaboradorOcorrencia();
		co4.setColaborador(c1);
		co4.setOcorrencia(o4);

		ColaboradorOcorrencia co5 = new ColaboradorOcorrencia();
		co5.setColaborador(c2);
		co5.setOcorrencia(o5);

		ArrayList<ColaboradorOcorrenciaRelatorio> ocorrenciasRelatorio = new ArrayList<ColaboradorOcorrenciaRelatorio>();

		action.adicionaNaCollecao(ocorrenciasRelatorio, co1);
		action.adicionaNaCollecao(ocorrenciasRelatorio, co2);
		action.adicionaNaCollecao(ocorrenciasRelatorio, co3);
		action.adicionaNaCollecao(ocorrenciasRelatorio, co4);
		action.adicionaNaCollecao(ocorrenciasRelatorio, co5);

		assertEquals("Test 1", 2, ocorrenciasRelatorio.size());
		assertEquals("Test 2", c1.getId(), ocorrenciasRelatorio.get(0).getColaborador().getId());
		assertEquals("Test 3", c2.getId(), ocorrenciasRelatorio.get(1).getColaborador().getId());
		assertEquals("Test 4", 3, ocorrenciasRelatorio.get(0).getOcorrencias().size());
		assertEquals("Test 5", 2, ocorrenciasRelatorio.get(1).getOcorrencias().size());

	}

	public void testMontaRelatorio() throws Exception{

		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setId(1L);

		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setId(2L);

		Ocorrencia o1 = OcorrenciaFactory.getEntity();
		o1.setPontuacao(3);

		Ocorrencia o2 = OcorrenciaFactory.getEntity();
		o2.setPontuacao(2);

		Ocorrencia o3 = OcorrenciaFactory.getEntity();
		o3.setPontuacao(1);

		Ocorrencia o4 = OcorrenciaFactory.getEntity();
		o4.setPontuacao(3);

		Ocorrencia o5 = OcorrenciaFactory.getEntity();
		o5.setPontuacao(1);

		ColaboradorOcorrencia co1 = new ColaboradorOcorrencia();
		co1.setColaborador(c1);
		co1.setOcorrencia(o1);

		ColaboradorOcorrencia co2 = new ColaboradorOcorrencia();
		co2.setColaborador(c1);
		co2.setOcorrencia(o2);

		ColaboradorOcorrencia co3 = new ColaboradorOcorrencia();
		co3.setColaborador(c1);
		co3.setOcorrencia(o3);

		ColaboradorOcorrencia co4 = new ColaboradorOcorrencia();
		co4.setColaborador(c2);
		co4.setOcorrencia(o4);

		ColaboradorOcorrencia co5 = new ColaboradorOcorrencia();
		co5.setColaborador(c2);
		co5.setOcorrencia(o5);

		Collection<ColaboradorOcorrencia> colaboradoresOcorrencias = new ArrayList<ColaboradorOcorrencia>();

		colaboradoresOcorrencias.add(co1);
		colaboradoresOcorrencias.add(co2);
		colaboradoresOcorrencias.add(co3);
		colaboradoresOcorrencias.add(co4);
		colaboradoresOcorrencias.add(co5);

		Collection<ColaboradorOcorrenciaRelatorio> ocorrenciasRelatorio = action.montaRelatorio(colaboradoresOcorrencias);

		ColaboradorOcorrenciaRelatorio[] ocorrenciaRelatorioArray = ocorrenciasRelatorio.toArray(new ColaboradorOcorrenciaRelatorio[]{});

		assertEquals("Test 1", 3, ocorrenciaRelatorioArray[0].getOcorrencias().size());
		assertEquals("Test 2", 2, ocorrenciaRelatorioArray[1].getOcorrencias().size());

		assertEquals("Test 3", 6, ocorrenciaRelatorioArray[0].getQtdPontos());
		assertEquals("Test 4", 4, ocorrenciaRelatorioArray[1].getQtdPontos());

		assertEquals("Test 5", c1.getId(), ocorrenciaRelatorioArray[0].getColaborador().getId());
		assertEquals("Test 6", c2.getId(), ocorrenciaRelatorioArray[1].getColaborador().getId());
	}

}