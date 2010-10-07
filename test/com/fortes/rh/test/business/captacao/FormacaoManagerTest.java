package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.FormacaoManagerImpl;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

public class FormacaoManagerTest extends MockObjectTestCase
{
	private FormacaoManagerImpl formacaoManager = new FormacaoManagerImpl();
	private Mock formacaoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        formacaoDao = new Mock(FormacaoDao.class);
        formacaoManager.setDao((FormacaoDao) formacaoDao.proxy());
    }


    public void testRemoveCandidato()
    {
    	formacaoDao.expects(once()).method("removeCandidato").with(ANYTHING);
    	formacaoManager.removeCandidato(new Candidato());
    }

    public void testRemoveColaborador()
    {
    	formacaoDao.expects(once()).method("removeColaborador").with(ANYTHING);
    	formacaoManager.removeColaborador(new Colaborador());
    }

    public void testFindInCandidatos()
    {
    	Long[] candidatoIds = new Long[0];
    	Collection<Experiencia> exps = new ArrayList<Experiencia>();

    	formacaoDao.expects(once()).method("findInCandidatos").with(ANYTHING).will(returnValue(exps));
    	assertEquals(exps, formacaoManager.findInCandidatos(candidatoIds));
    }

    public void testMontaExperienciasBDS()
    {
    	Collection<Formacao> formacaos = new ArrayList<Formacao>();

    	Formacao formacao = new Formacao();
    	formacaos.add(formacao);

    	formacaoDao.expects(once()).method("save").with(ANYTHING);

    	formacaoManager.montaFormacaosBDS(formacaos, new Candidato());

    }

    public void testFindByColaborador()
    {
    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();

    	formacaoDao.expects(once()).method("findByColaborador").with(ANYTHING).will(returnValue(experiencias));

    	assertEquals(experiencias, formacaoManager.findByColaborador(1L));
    }

    public void testFindByCandidato()
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	Collection<Formacao> formacaos = new ArrayList<Formacao>();

    	formacaoDao.expects(once()).method("findByCandidato").with(eq(candidato.getId())).will(returnValue(formacaos));

    	Collection<Formacao> formacaosReturn = formacaoManager.findByCandidato(candidato.getId());

    	assertEquals(formacaos, formacaosReturn);
    }

}
