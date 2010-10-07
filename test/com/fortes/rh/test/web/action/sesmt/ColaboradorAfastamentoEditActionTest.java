package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
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
		action.setColaboradorAfastamento(ColaboradorAfastamentoFactory.getEntity(1L));
		manager.expects(once()).method("findById");
		afastamentoManager.expects(once()).method("findAll");
		assertEquals("success", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);
		manager.expects(once()).method("save").with(eq(afastamento)).will(returnValue(afastamento));

		assertEquals("success", action.insert());
	}
	public void testInsertException() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity();
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("save").with(eq(afastamento)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula");

		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("update").with(eq(afastamento)).isVoid();

		assertEquals("success", action.update());
	}
	public void testUpdateException() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));

		manager.expects(once()).method("findById");
		afastamentoManager.expects(once()).method("findAll");

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

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING).will(returnValue(colecao));
		afastamentoManager.expects(once()).method("findAll");

		assertEquals("success", action.filtrarColaboradores());

	}
	public void testFiltrarColaboradoresSemColaborador() throws Exception
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf("493.034.123-87");
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setPessoal(pessoal);

		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(action.getEmpresaSistema().getId()), ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

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