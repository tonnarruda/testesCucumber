package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;

public class CandidatoSolicitacaoManagerTest_Junit4 
{
	private CandidatoSolicitacaoManagerImpl candidatoSolicitacaoManager = new CandidatoSolicitacaoManagerImpl();
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;

	@Before
    public void setUp() throws Exception
    {
        candidatoSolicitacaoDao = mock(CandidatoSolicitacaoDao.class);
        candidatoSolicitacaoManager.setDao(candidatoSolicitacaoDao);
    }
	
	@Test
	public void atualizaCandidatoSolicitacaoAoReligarColaborador() 
	{
		Long colaboradorId = 1L;
    	Exception exception = null;

    	try {
			candidatoSolicitacaoManager.atualizaCandidatoSolicitacaoAoReligarColaborador(colaboradorId);
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
	}
   
}
