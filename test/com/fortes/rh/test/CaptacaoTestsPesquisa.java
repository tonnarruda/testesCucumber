package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.business.captacao.AnuncioManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoIdiomaManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoSolicitacaoManagerTest;
import com.fortes.rh.test.business.captacao.ConhecimentoManagerTest;
import com.fortes.rh.test.business.captacao.DuracaoPreenchimentoVagaManagerTest;
import com.fortes.rh.test.business.captacao.EmpresaBdsManagerTest;
import com.fortes.rh.test.business.captacao.EtapaSeletivaManagerTest;
import com.fortes.rh.test.business.captacao.ExperienciaManagerTest;
import com.fortes.rh.test.business.captacao.FormacaoManagerTest;
import com.fortes.rh.test.business.captacao.HistoricoCandidatoManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoBDSManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoManagerTest;
import com.fortes.rh.test.dao.hibernate.captacao.AnuncioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoCurriculoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoIdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConhecimentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EmpresaBdsDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EtapaSeletivaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ExperienciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.FormacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.IdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.MotivoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoBDSDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoDaoHibernateTest;
import com.fortes.rh.test.web.action.captacao.CandidatoEditActionTest;
import com.fortes.rh.test.web.action.captacao.EmpresaBdsEditActionTest;
import com.fortes.rh.test.web.action.captacao.EmpresaBdsListActionTest;
import com.fortes.rh.test.web.action.captacao.EtapaSeletivaEditActionTest;
import com.fortes.rh.test.web.action.captacao.EtapaSeletivaListActionTest;
import com.fortes.rh.test.web.action.captacao.MotivoSolicitacaoEditActionTest;
import com.fortes.rh.test.web.action.captacao.MotivoSolicitacaoListActionTest;
import com.fortes.rh.test.web.action.captacao.SolicitacaoListActionTest;

public class CaptacaoTestsPesquisa extends TestSuite
{
    public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();

        //CAPTAÇÃO
        suite.addTestSuite(AnuncioManagerTest.class);
        suite.addTestSuite(AnuncioDaoHibernateTest.class);
        suite.addTestSuite(CandidatoDaoHibernateTest.class);
        suite.addTestSuite(CandidatoManagerTest.class);
        suite.addTestSuite(CandidatoEditActionTest.class);
        suite.addTestSuite(ConhecimentoDaoHibernateTest.class);
        suite.addTestSuite(ConhecimentoManagerTest.class);
        suite.addTestSuite(DuracaoPreenchimentoVagaManagerTest.class);
        suite.addTestSuite(ExperienciaManagerTest.class);
        suite.addTestSuite(ExperienciaDaoHibernateTest.class);
        suite.addTestSuite(FormacaoDaoHibernateTest.class);
        suite.addTestSuite(FormacaoManagerTest.class);
        suite.addTestSuite(IdiomaDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoManagerTest.class);
        suite.addTestSuite(SolicitacaoListActionTest.class);
        suite.addTestSuite(SolicitacaoBDSDaoHibernateTest.class);
        suite.addTestSuite(CandidatoIdiomaManagerTest.class);
        suite.addTestSuite(CandidatoIdiomaDaoHibernateTest.class);
        suite.addTestSuite(EtapaSeletivaDaoHibernateTest.class);
        suite.addTestSuite(EtapaSeletivaManagerTest.class);
        suite.addTestSuite(EtapaSeletivaListActionTest.class);
        suite.addTestSuite(EtapaSeletivaEditActionTest.class);
        suite.addTestSuite(EmpresaBdsListActionTest.class);
        suite.addTestSuite(EmpresaBdsEditActionTest.class);
        suite.addTestSuite(EmpresaBdsManagerTest.class);
        suite.addTestSuite(EmpresaBdsDaoHibernateTest.class);
        suite.addTestSuite(HistoricoCandidatoManagerTest.class);
        suite.addTestSuite(CandidatoSolicitacaoManagerTest.class);
        suite.addTestSuite(CandidatoSolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(MotivoSolicitacaoListActionTest.class);
        suite.addTestSuite(MotivoSolicitacaoEditActionTest.class);
        suite.addTestSuite(MotivoSolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoBDSManagerTest.class);
        suite.addTestSuite(CandidatoCurriculoDaoHibernateTest.class);

        return suite;
    }
}