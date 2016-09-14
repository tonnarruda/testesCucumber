package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;

public class CandidatoManagerTest_Junit4 
{
	private CandidatoManagerImpl candidatoManager;
	private CandidatoDao candidatoDao;
	
	@Before
    public void setUp() throws Exception
    {
		candidatoManager = new CandidatoManagerImpl();
        candidatoDao = mock(CandidatoDao.class);
        
        candidatoManager.setDao(candidatoDao);
    }
	
	@Test
	public void testFindCandidatosIndicadosPor() throws ColecaoVaziaException
	{
		Collection<Candidato> candidatosIndicadosPor = Arrays.asList(CandidatoFactory.getCandidato());
		Long[] empresasIds = new Long[]{1L};
		when(candidatoDao.findCandidatosIndicadosPor(any(Date.class), any(Date.class), eq(empresasIds))).thenReturn(candidatosIndicadosPor);
		
		assertEquals(candidatosIndicadosPor, candidatoManager.findCandidatosIndicadosPor(new Date(), new Date(), empresasIds));
	}
	
	@Test(expected=ColecaoVaziaException.class)
	public void testFindCandidatosIndicadosPorColecaoVaziaException() throws ColecaoVaziaException
	{
		Long[] empresasIds = new Long[]{1L};
		when(candidatoDao.findCandidatosIndicadosPor(any(Date.class), any(Date.class), eq(empresasIds))).thenReturn(new ArrayList<Candidato>());
		candidatoManager.findCandidatosIndicadosPor(new Date(), new Date(), empresasIds);
	}
}
