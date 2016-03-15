package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.PesquisaEditAction;

public class PesquisaEditActionTest extends MockObjectTestCase
{
	private PesquisaEditAction pesquisaAction;
	private Mock pesquisaManager;
	private Mock colaboradorQuestionarioManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		pesquisaManager = new Mock(PesquisaManager.class);

		pesquisaAction = new PesquisaEditAction();
		pesquisaAction.setPesquisaManager((PesquisaManager) pesquisaManager.proxy());
		
		colaboradorQuestionarioManager =  mock(ColaboradorQuestionarioManager.class);
		pesquisaAction.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		pesquisaManager = null;
		pesquisaAction = null;
        MockSecurityUtil.verifyRole = false;
		
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", pesquisaAction.execute());
	}

	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", pesquisaAction.prepareInsert());
	}

    public void testPrepareUpdate() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Questionario questionario = QuestionarioFactory.getEntity(10L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

		pesquisaAction.setPesquisa(pesquisa);
		pesquisaAction.setEmpresaSistema(empresa);

		pesquisaManager.expects(once()).method("verificarEmpresaValida").with(eq(pesquisa.getId()),eq(empresa.getId()));
		pesquisaManager.expects(once()).method("findByIdProjection").with(eq(pesquisa.getId())).will(returnValue(pesquisa));
		
		Collection<ColaboradorQuestionario> colabQuestionarios = new ArrayList<ColaboradorQuestionario>();
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(232L);
		colaboradorQuestionario.setRespondida(true);
		colabQuestionarios.add(colaboradorQuestionario);
		
		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(pesquisa.getQuestionario().getId())).will(returnValue(colabQuestionarios));

		assertEquals("success", pesquisaAction.prepareUpdate());
		assertNull(pesquisaAction.getActionErr());
    }

    public void testPrepareUpdateExcepiton() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

    	pesquisaAction.setPesquisa(pesquisa);
    	pesquisaAction.setEmpresaSistema(empresa);

    	pesquisaManager.expects(once()).method("verificarEmpresaValida").with(eq(pesquisa.getId()),eq(empresa.getId())).will(throwException(new Exception("error")));

    	assertEquals("input", pesquisaAction.prepareUpdate());
    	assertNotNull(pesquisaAction.getActionErr());
    }

    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	pesquisaAction.setPesquisa(pesquisa);
    	pesquisaAction.setEmpresaSistema(empresa);

		pesquisaManager.expects(once()).method("save").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa)).will(returnValue(pesquisa));

    	assertEquals("success", pesquisaAction.insert());
    	assertEquals(pesquisa, pesquisaAction.getPesquisa());
    }

    public void testInsertException()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	pesquisaAction.setPesquisa(pesquisa);
    	pesquisaAction.setEmpresaSistema(empresa);

    	pesquisaManager.expects(once()).method("save").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa)).will(throwException(new Exception("error")));

    	assertEquals("input", pesquisaAction.insert());
    }

    public void testUpdate() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

		pesquisaAction.setPesquisa(pesquisa);
		pesquisaAction.setEmpresaSistema(empresa);

		pesquisaManager.expects(once()).method("update").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa.getId()));

		assertEquals("success", pesquisaAction.update());
		assertNull(pesquisaAction.getActionErr());
    }

    public void testUpdateException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	pesquisaAction.setPesquisa(pesquisa);
    	pesquisaAction.setEmpresaSistema(empresa);

    	pesquisaManager.expects(once()).method("update").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa.getId())).will(throwException(new Exception("error")));

    	assertEquals("input", pesquisaAction.update());
    	assertNotNull(pesquisaAction.getActionErr());
    }

    public void testGravar() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

		pesquisaAction.setPesquisa(pesquisa);
		pesquisaAction.setEmpresaSistema(empresa);

		pesquisaManager.expects(once()).method("update").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa.getId()));

		assertEquals("success", pesquisaAction.gravar());
		assertNull(pesquisaAction.getActionErr());
    }

    public void testGravarException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	pesquisaAction.setPesquisa(pesquisa);
    	pesquisaAction.setEmpresaSistema(empresa);

    	pesquisaManager.expects(once()).method("update").with(eq(pesquisa), eq(pesquisa.getQuestionario()), eq(empresa.getId())).will(throwException(new Exception("error")));

    	assertEquals("input", pesquisaAction.gravar());
    	assertNotNull(pesquisaAction.getActionErr());
    }

    public void testGetsSets() throws Exception
    {
    	Pesquisa pesquisa = PesquisaFactory.getEntity();
    	pesquisa.setId(1L);

    	pesquisaAction.setPesquisa(pesquisa);

    	assertEquals(pesquisa, pesquisaAction.getPesquisa());

    	pesquisaAction.setPesquisa(null);

    	assertNotNull(pesquisaAction.getPesquisa());

    	assertTrue(pesquisaAction.getModel() instanceof Pesquisa);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	pesquisaAction.setQuestionario(questionario);

    	pesquisaAction.getQuestionario();
    }
}