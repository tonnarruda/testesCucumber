package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.business.pesquisa.AspectoManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorQuestionarioManagerTest;
import com.fortes.rh.test.business.pesquisa.LembretePesquisaTest;
import com.fortes.rh.test.business.pesquisa.PerguntaManagerTest;
import com.fortes.rh.test.business.pesquisa.PesquisaManagerTest;
import com.fortes.rh.test.business.pesquisa.QuestionarioManagerTest;
import com.fortes.rh.test.business.pesquisa.RespostaManagerTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.AspectoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.ColaboradorQuestionarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.ColaboradorRespostaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.PerguntaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.PesquisaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.QuestionarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.RespostaDaoHibernateTest;
import com.fortes.rh.test.web.action.pesquisa.AspectoEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.AspectoListActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorQuestionarioEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorQuestionarioListActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PesquisaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.QuestionarioListActionTest;

public class UnitTestsPesquisa extends TestSuite
{
    public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(ColaboradorRespostaDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorRespostaListActionTest.class);
        suite.addTestSuite(PerguntaDaoHibernateTest.class);
        suite.addTestSuite(PerguntaManagerTest.class);
        suite.addTestSuite(PerguntaListActionTest.class);
        suite.addTestSuite(PerguntaEditActionTest.class);
        suite.addTestSuite(RespostaDaoHibernateTest.class);
        suite.addTestSuite(RespostaManagerTest.class);
        suite.addTestSuite(AspectoDaoHibernateTest.class);
        suite.addTestSuite(AspectoManagerTest.class);
        suite.addTestSuite(AspectoEditActionTest.class);
        suite.addTestSuite(AspectoListActionTest.class);
        suite.addTestSuite(QuestionarioDaoHibernateTest.class);
        suite.addTestSuite(QuestionarioManagerTest.class);
        suite.addTestSuite(PesquisaDaoHibernateTest.class);
        suite.addTestSuite(PesquisaManagerTest.class);
        suite.addTestSuite(QuestionarioListActionTest.class);
        suite.addTestSuite(PesquisaEditActionTest.class);
        suite.addTestSuite(LembretePesquisaTest.class);
        suite.addTestSuite(ColaboradorQuestionarioDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorQuestionarioManagerTest.class);
        suite.addTestSuite(ColaboradorQuestionarioListActionTest.class);
        suite.addTestSuite(ColaboradorQuestionarioEditActionTest.class);

        return suite;
    }
}