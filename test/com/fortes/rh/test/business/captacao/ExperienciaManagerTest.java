package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ExperienciaManagerImpl;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.geral.Colaborador;

public class ExperienciaManagerTest extends MockObjectTestCase
{
	private ExperienciaManagerImpl experienciaManager = new ExperienciaManagerImpl();
	private Mock experienciaDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        experienciaDao = new Mock(ExperienciaDao.class);
        experienciaManager.setDao((ExperienciaDao) experienciaDao.proxy());
    }

    public void testRemoveCandidato()
    {
    	experienciaDao.expects(once()).method("removeCandidato").with(ANYTHING);
    	experienciaManager.removeCandidato(new Candidato());
    }

    public void testRemoveColaborador()
    {
    	experienciaDao.expects(once()).method("removeColaborador").with(ANYTHING);
    	experienciaManager.removeColaborador(new Colaborador());
    }

    public void testFindInCandidatos()
    {
    	Long[] candidatoIds = new Long[0];
    	Collection<Experiencia> exps = new ArrayList<Experiencia>();

    	experienciaDao.expects(once()).method("findInCandidatos").with(ANYTHING).will(returnValue(exps));
    	assertEquals(exps, experienciaManager.findInCandidatos(candidatoIds));
    }

    public void testMontaExperienciasBDS()
    {
    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();

    	Experiencia experiencia = new Experiencia();
    	experiencias.add(experiencia);

    	experienciaDao.expects(once()).method("save").with(ANYTHING);

    	experienciaManager.montaExperienciasBDS(experiencias, new Candidato());

    }

    public void testFindByColaborador()
    {
    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();

    	experienciaDao.expects(once()).method("findByColaborador").with(ANYTHING).will(returnValue(experiencias));

    	assertEquals(experiencias, experienciaManager.findByColaborador(1L));
    }

    public void testFindByCandidato()
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	Collection<Experiencia> experiencias = new ArrayList<Experiencia>();

    	experienciaDao.expects(once()).method("findByCandidato").with(eq(candidato.getId())).will(returnValue(experiencias));

    	Collection<Experiencia> retorno = experienciaManager.findByCandidato(candidato.getId());

    	assertEquals(experiencias, retorno);
    }
}
