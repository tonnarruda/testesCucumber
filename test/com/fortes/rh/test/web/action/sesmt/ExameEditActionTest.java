package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.ExameEditAction;


public class ExameEditActionTest extends MockObjectTestCase
{
	ExameEditAction action;
	Mock exameManager;

	protected void setUp() throws Exception
	{
		action = new ExameEditAction();

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		exameManager = new Mock(ExameManager.class);
		action.setExameManager((ExameManager) exameManager.proxy());
	}

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
        MockSecurityUtil.verifyRole = false;
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	public void testPrepareInsert() throws Exception
	{
		Exame exame = new Exame();
		action.setExame(exame);

		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		Exame exame = new Exame();
		exame.setId(1L);
		exame.setEmpresa(empresa);
		action.setExame(exame);

		exameManager.expects(once()).method("findByIdProjection").with(eq(exame.getId())).will(returnValue(exame));
		exameManager.expects(once()).method("findByIdProjection").with(eq(exame.getId())).will(returnValue(exame));

		assertEquals("success", action.prepareUpdate());
	}

	public void testPrepareUpdateEmpresaErrada() throws Exception
	{
		Exame exame = new Exame();
		exame.setId(1L);
		action.setExame(exame);

		exameManager.expects(once()).method("findByIdProjection").with(eq(exame.getId())).will(returnValue(exame));
		exameManager.expects(once()).method("findByIdProjection").with(eq(exame.getId())).will(returnValue(exame));

		assertEquals("error", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		Exame exame = new Exame();
		exame.setNome("Exame de Teste");
		exame.setPeriodicidade(1);
		exame.setEmpresa(new Empresa());
		action.setExame(exame);

		exameManager.expects(once()).method("save").with(ANYTHING);
		assertEquals("success", action.insert());
	}

	public void testInsertSemDados() throws Exception
	{
		Exame exame = new Exame();
		action.setExame(exame);

		exameManager.expects(never()).method("save").with(ANYTHING);
		assertEquals("error", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		Exame exame = new Exame();
		exame.setId(1l);
		exame.setNome("Exame de Teste");
		exame.setPeriodicidade(1);
		exame.setEmpresa(empresa);
		action.setExame(exame);

		Exame exameTmp = new Exame();
		exameTmp.setId(1l);
		exameTmp.setNome("Teste");
		exameTmp.setPeriodicidade(1);
		exameTmp.setEmpresa(empresa);

		// chamado em validaEmpresa()
		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exameTmp));

		exameManager.expects(once()).method("update").with(ANYTHING);
		assertEquals("success", action.update());
	}

	public void testUpdateEmpresaErrada() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1234L);

		Exame exame = new Exame();
		exame.setId(1L);
		exame.setNome("Exame de Teste");
		exame.setPeriodicidade(1);
		exame.setEmpresa(empresa);
		action.setExame(exame);

		Empresa empresaErrada = new Empresa();
		empresaErrada.setId(2L);
		Exame exameEmpresaErrada = new Exame();
		exameEmpresaErrada.setId(2L);
		exameEmpresaErrada.setNome("Teste");
		exameEmpresaErrada.setPeriodicidade(1);
		exameEmpresaErrada.setEmpresa(empresaErrada);

		// validaEmpresa()
		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exameEmpresaErrada));

		// prepare
		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exame));

		exameManager.expects(never()).method("update").with(ANYTHING);
		assertEquals("error", action.update());
	}

	public void testUpdateSemDados() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		Exame exame = new Exame();
		exame.setId(1L);
		exame.setEmpresa(empresa);
		action.setExame(exame);

		// validaEmpresa()
		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exame));

		// prepare
		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exame));

		exameManager.expects(never()).method("update").with(ANYTHING);
		assertEquals("error", action.update());
	}

	public void testGetSet(){
		action.getExame();
	}

}