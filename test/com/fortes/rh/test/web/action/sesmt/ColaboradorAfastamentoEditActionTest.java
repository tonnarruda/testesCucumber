package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ColaboradorAfastamentoFactory;
import com.fortes.rh.web.action.sesmt.ColaboradorAfastamentoEditAction;

public class ColaboradorAfastamentoEditActionTest extends MockObjectTestCase
{
	private ColaboradorAfastamentoEditAction action;
	private Mock manager;
	private Mock afastamentoManager;
	private Mock colaboradorManager;
	private Mock cidManager;
	private Mock gerenciadorComunicacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ColaboradorAfastamentoManager.class);
		action = new ColaboradorAfastamentoEditAction();
		action.setColaboradorAfastamentoManager((ColaboradorAfastamentoManager) manager.proxy());

		afastamentoManager = mock(AfastamentoManager.class);
		action.setAfastamentoManager((AfastamentoManager) afastamentoManager.proxy());

		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		action.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());

		cidManager = mock(CidManager.class);
		action.setCidManager((CidManager) cidManager.proxy());

		action.setColaboradorAfastamento(ColaboradorAfastamentoFactory.getEntity());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(3L));
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	public void testPrepareUpdate() throws Exception
	{
		ColaboradorAfastamento colaboradorAfastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(colaboradorAfastamento);
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(colaboradorAfastamento));
		cidManager.expects(once()).method("findDescricaoByCodigo");
		
		afastamentoManager.expects(once()).method("findAll");
		assertEquals("success", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);
		gerenciadorComunicacaoManager.expects(once()).method("enviaAvisoDeAfastamento").withAnyArguments().isVoid();
		manager.expects(once()).method("save").with(eq(afastamento)).will(returnValue(afastamento));
		manager.expects(once()).method("possuiAfastamentoNestePeriodo").with(eq(afastamento), eq(false)).will(returnValue(true));

		assertEquals("success", action.insert());
	}
	public void testInsertException() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity();
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("save").with(eq(afastamento)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula");
		manager.expects(once()).method("possuiAfastamentoNestePeriodo").with(eq(afastamento), eq(false)).will(returnValue(true));

		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("update").with(eq(afastamento)).isVoid();
		manager.expects(once()).method("possuiAfastamentoNestePeriodo").with(eq(afastamento), eq(true)).will(returnValue(true));

		assertEquals("success", action.update());
	}
	public void testUpdateException() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("possuiAfastamentoNestePeriodo").with(eq(afastamento), eq(true)).will(returnValue(true));

		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(afastamento));
		afastamentoManager.expects(once()).method("findAll");
		cidManager.expects(once()).method("findDescricaoByCodigo");

		assertEquals("input", action.update());
	}

	public void testFiltrarColaboradores() throws Exception
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf("493.034.123-87");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setPessoal(pessoal);

		Collection<Colaborador> colecao = new ArrayList<Colaborador>();
		colecao.add(colaborador);

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(new Constraint[]{ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING, eq(null), eq(StatusRetornoAC.CONFIRMADO)}).will(returnValue(colecao));
		afastamentoManager.expects(once()).method("findAll");

		assertEquals("success", action.filtrarColaboradores());
	}

	public void testFiltrarColaboradoresSemColaborador() throws Exception
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf("493.034.123-87");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setPessoal(pessoal);

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(new Constraint[]{ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING, eq(null), eq(StatusRetornoAC.CONFIRMADO)}).will(returnValue(new ArrayList<Colaborador>()));

		assertEquals("input", action.filtrarColaboradores());
	}

	public void testGetSet() throws Exception
	{
		action.setColaboradorAfastamento(null);
		assertNotNull(action.getColaboradorAfastamento());
		assertTrue(action.getColaboradorAfastamento() instanceof ColaboradorAfastamento);

		action.setColaborador(null);
		action.getColaborador();
		action.getColaboradors();
		action.getAfastamentos();
	}
}