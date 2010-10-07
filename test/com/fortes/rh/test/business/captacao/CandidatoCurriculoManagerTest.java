package com.fortes.rh.test.business.captacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoCurriculoManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.model.captacao.Candidato;

public class CandidatoCurriculoManagerTest extends MockObjectTestCase
{
	private CandidatoCurriculoManagerImpl candidatoCurriculoManager = new CandidatoCurriculoManagerImpl();
	private Mock candidatoCurriculoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        candidatoCurriculoDao = new Mock(CandidatoCurriculoDao.class);
        candidatoCurriculoManager.setDao((CandidatoCurriculoDao) candidatoCurriculoDao.proxy());
    }

    public void testRemoveCandidato() throws Exception
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	candidatoCurriculoDao.expects(once()).method("removeCandidato").with(ANYTHING);

    	Exception exception = null;

    	try
		{
    		candidatoCurriculoManager.removeCandidato(candidato);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

}
