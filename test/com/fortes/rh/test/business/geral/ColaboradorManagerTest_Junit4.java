package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class ColaboradorManagerTest_Junit4
{
	private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
    private ColaboradorDao colaboradorDao;
    private CandidatoManager candidatoManager;
    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private MensagemManager mensagemManager;

    @Before
    public void setUp() throws Exception
    {
        colaboradorDao = mock(ColaboradorDao.class);
        candidatoManager = mock(CandidatoManager.class);
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        mensagemManager = mock(MensagemManager.class);
        
        colaboradorManager.setDao(colaboradorDao);
        colaboradorManager.setCandidatoManager(candidatoManager);
        colaboradorManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        colaboradorManager.setMensagemManager(mensagemManager);
    }
    
    @Test
	public void testReligaColaborador() 
	{
    	Long colaboradorId = 1L;
    	Exception exception = null;

    	try {
			colaboradorManager.religaColaborador(colaboradorId);
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
	}
    
    @Test
	public void testReligaColaboradorAC() 
	{
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	String codigoAC = "000001";
    	String empresaCodigoAC =	"0002";
    	String grupoAC = "0001"; 
    	
    	when(colaboradorDao.findByCodigoAC(codigoAC, empresaCodigoAC, grupoAC)).thenReturn(colaborador);
		colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC);
    	assertEquals(colaborador.getId(), colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC));
	}
    
}