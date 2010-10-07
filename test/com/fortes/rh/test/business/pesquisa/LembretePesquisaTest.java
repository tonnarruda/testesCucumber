package com.fortes.rh.test.business.pesquisa;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.LembretePesquisa;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class LembretePesquisaTest extends MockObjectTestCase
{
    LembretePesquisa lembretePesquisa = new LembretePesquisa();
    Mock questionarioManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        questionarioManager = new Mock(QuestionarioManager.class);
		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		super.tearDown();
	}
	
    public void testExecute()
    {
    	Exception exception = null;

		questionarioManager.expects(once()).method("enviaLembreteDeQuestionarioNaoLiberada");
		lembretePesquisa.execute();


		assertNull(exception);
    }

}
