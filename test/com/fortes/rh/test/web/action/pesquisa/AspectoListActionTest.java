package com.fortes.rh.test.web.action.pesquisa;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.AspectoListAction;

public class AspectoListActionTest extends MockObjectTestCase
{
	private AspectoListAction aspectoListAction;
	private Mock aspectoManager;
	private Mock questionarioManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        aspectoListAction = new AspectoListAction();
        
        aspectoManager = new Mock(AspectoManager.class);
        aspectoListAction.setAspectoManager((AspectoManager) aspectoManager.proxy());
        
        questionarioManager = new Mock(QuestionarioManager.class);
        aspectoListAction.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        aspectoManager = null;
        aspectoListAction = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", aspectoListAction.execute());
    }

    public void testDelete() throws Exception
    {
    	aspectoListAction.setAspecto(AspectoFactory.getEntity(1L));
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	aspectoListAction.setQuestionario(questionario);

    	aspectoManager.expects(once()).method("remove").with(ANYTHING);
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success", aspectoListAction.delete());
    	assertNull(aspectoListAction.getActionErr());
    	assertEquals(questionario, aspectoListAction.getQuestionario());

    	aspectoManager.expects(once()).method("remove").with(ANYTHING);
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	
    	assertEquals("success", aspectoListAction.delete());
    }

    public void testList() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
		aspectoListAction.setQuestionario(questionario);

    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));

    	assertEquals("success", aspectoListAction.list());
    }

    public void testGets() throws Exception
    {
    	Aspecto aspecto = AspectoFactory.getEntity();
    	aspecto.setId(1L);

    	aspectoListAction.setAspecto(aspecto);

    	assertEquals(aspecto, aspectoListAction.getAspecto());

    	aspectoListAction.setAspecto(null);

    	assertTrue(aspectoListAction.getAspectos().isEmpty());

    	aspectoListAction.setPesquisa(PesquisaFactory.getEntity(1L));
    	assertTrue(aspectoListAction.getPesquisa().getId().equals(1L));

    	aspectoListAction.setPergunta(PerguntaFactory.getEntity(1L));
    	assertTrue(aspectoListAction.getPergunta().getId().equals(1L));

    }
}
