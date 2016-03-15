package com.fortes.rh.test.web.action.pesquisa;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.AspectoEditAction;

public class AspectoEditActionTest extends MockObjectTestCase
{
	private AspectoEditAction aspectoAction;
	private Mock aspectoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		aspectoManager = new Mock(AspectoManager.class);

		aspectoAction = new AspectoEditAction();
		aspectoAction.setAspectoManager((AspectoManager) aspectoManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		aspectoManager = null;
		aspectoAction = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", aspectoAction.execute());
	}

	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", aspectoAction.prepareInsert());
	}

    public void testPrepareUpdate() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Aspecto aspecto = AspectoFactory.getEntity(1L);

		aspectoAction.setAspecto(aspecto);
		aspectoAction.setEmpresaSistema(empresa);

		aspectoManager.expects(once()).method("findByIdProjection").with(eq(aspecto.getId())).will(returnValue(aspecto));

		assertEquals("success", aspectoAction.prepareUpdate());
		assertEquals(aspecto, aspectoAction.getAspecto());
		assertNull(aspectoAction.getActionErr());
    }

    public void testInsert() throws Exception
    {
    	Aspecto aspecto = AspectoFactory.getEntity(1L);

    	aspectoAction.setAspecto(aspecto);

		aspectoManager.expects(once()).method("save").with(eq(aspecto)).will(returnValue(aspecto));

    	assertEquals("success", aspectoAction.insert());
    	assertEquals(aspecto, aspectoAction.getAspecto());
    }

    public void testUpdate() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Aspecto aspecto = AspectoFactory.getEntity(1L);

		aspectoAction.setAspecto(aspecto);
		aspectoAction.setEmpresaSistema(empresa);

		aspectoManager.expects(once()).method("update").with(eq(aspecto));

		assertEquals("success", aspectoAction.update());
		assertEquals(aspecto, aspectoAction.getAspecto());
		assertNull(aspectoAction.getActionErr());
    }

    public void testGets() throws Exception
    {
    	Aspecto aspecto = AspectoFactory.getEntity();
    	aspecto.setId(1L);

    	aspectoAction.setAspecto(aspecto);

    	assertEquals(aspecto, aspectoAction.getAspecto());

    	aspectoAction.setAspecto(null);

    	assertNotNull(aspectoAction.getAspecto());

    	assertTrue(aspectoAction.getModel() instanceof Aspecto);

    	aspectoAction.setQuestionario(new Questionario());
    	aspectoAction.getQuestionario();
    	aspectoAction.setPergunta(new Pergunta());
    	aspectoAction.getPergunta();
    	aspectoAction.setPesquisa(new Pesquisa());
    	aspectoAction.getPesquisa();
    }
}