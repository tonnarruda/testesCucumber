package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.ProntuarioManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ProntuarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.ProntuarioEditAction;

public class ProntuarioEditActionTest extends MockObjectTestCase
{
	ProntuarioEditAction action;
	Mock prontuarioManager;
	Mock colaboradorManager;

	protected void setUp() throws Exception
	{
		action = new ProntuarioEditAction();

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		prontuarioManager = new Mock(ProntuarioManager.class);
		action.setProntuarioManager((ProntuarioManager) prontuarioManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
	}

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	private void preparaList()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);

		Collection<Prontuario> prontuarios = new ArrayList<Prontuario>();
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();

		prontuarioManager.expects(once()).method("findByColaborador").with(eq(colaborador)).will(returnValue(prontuarios));
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(new Constraint[]{eq(colaborador), ANYTHING, eq(null), eq(null), ANYTHING}).will(returnValue(colaboradors));
		colaboradorManager.expects(once()).method("getNome").with(eq(colaborador.getId())).will(returnValue("teste"));
	}

	public void testList() throws Exception
	{
		preparaList();

		assertEquals("success", action.list());
		assertNotNull(action.getColaboradors());
		assertNotNull(action.getProntuarios());
		assertEquals("teste", action.getColaboradorNome());
	}

	public void testDelete() throws Exception
	{
		Prontuario prontuario = ProntuarioFactory.getEntity(1L);
		action.setProntuario(prontuario);

		prontuarioManager.expects(once()).method("remove").with(eq(prontuario.getId())).isVoid();
		preparaList();

		assertEquals("success", action.delete());
	}

	public void testDeleteException() throws Exception
	{
		Prontuario prontuario = ProntuarioFactory.getEntity(1L);
		action.setProntuario(prontuario);

		prontuarioManager.expects(once()).method("remove").with(eq(prontuario.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(prontuario.getId(),""))));
		preparaList();

		assertEquals("success", action.delete());
	}

	public void testPrepareInsert() throws Exception
	{
		Prontuario prontuario = ProntuarioFactory.getEntity(1L);
		action.setProntuario(prontuario);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);

		prontuarioManager.expects(once()).method("findById").with(eq(prontuario.getId())).will(returnValue(prontuario));
		colaboradorManager.expects(once()).method("getNome").with(eq(colaborador.getId())).will(returnValue("teste"));

		assertEquals("success", action.prepareInsert());
		assertEquals("teste", action.getColaboradorNome());
	}

	public void testPrepareUpdate() throws Exception
	{
		Prontuario prontuario = ProntuarioFactory.getEntity(1L);
		action.setProntuario(prontuario);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);

		prontuarioManager.expects(once()).method("findById").with(eq(prontuario.getId())).will(returnValue(prontuario));
		colaboradorManager.expects(once()).method("getNome").with(eq(colaborador.getId())).will(returnValue("teste"));

		assertEquals("success", action.prepareUpdate());
		assertEquals("teste", action.getColaboradorNome());
	}

	public void testInsert() throws Exception
	{
		Prontuario prontuario = new Prontuario();
		action.setProntuario(prontuario);

		prontuarioManager.expects(once()).method("save").with(eq(prontuario)).isVoid();
		preparaList();

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Prontuario prontuario = ProntuarioFactory.getEntity(1L);
		action.setProntuario(prontuario);

		prontuarioManager.expects(once()).method("update").with(eq(prontuario)).isVoid();
		preparaList();
		assertEquals("success", action.update());
	}

	public void testGetSet()
	{
		action.getProntuario();
		action.getColaborador();
	}
}