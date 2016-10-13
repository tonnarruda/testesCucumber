package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.EleicaoManagerImpl;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class EleicaoManagerTest_Junit4
{
	private EleicaoManagerImpl eleicaoManager = new EleicaoManagerImpl();
	private EleicaoDao eleicaoDao;

	@Before
	public void setUp() throws Exception
    {
        eleicaoDao = mock(EleicaoDao.class);
        eleicaoManager.setDao(eleicaoDao);
    }

	@Test
	public void testMontaAtaDaEleicao() throws Exception
	{
		Eleicao eleicao = EleicaoFactory.getEntity(1L, 10, 0, 30);

		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(10, eleicao);
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(10, eleicao);

		Collection<CandidatoEleicao> candidatoEleicaos = Arrays.asList(candidatoEleicao, candidatoEleicao2);

		eleicao.setCandidatoEleicaos(candidatoEleicaos);
		eleicao.setTextoAtaEleicao("Texto da ata.");
		
		when(eleicaoDao.findByIdProjection(eq(eleicao.getId()))).thenReturn(eleicao);
		when(eleicaoDao.findImprimirResultado(eq(eleicao.getId()))).thenReturn(candidatoEleicaos);
		
		Eleicao eleicaoResultado = eleicaoManager.montaAtaDaEleicao(eleicao.getId());
		
		assertEquals(eleicao.getTextoAtaEleicao(), eleicaoResultado.getTextoAtaEleicao());
	}

	@Test
	public void testFindImprimirResultado() throws ColecaoVaziaException {
		Eleicao eleicao = EleicaoFactory.getEntity(1L, 10, 0, 30);

		CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(10, eleicao);
		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(10, eleicao);

		Collection<CandidatoEleicao> candidatoEleicaos = Arrays.asList(candidatoEleicao, candidatoEleicao2);

		eleicao.setCandidatoEleicaos(candidatoEleicaos);

		Eleicao resultado = null;

		when(eleicaoDao.findImprimirResultado(eq(eleicao.getId()))).thenReturn(candidatoEleicaos);
		resultado = eleicaoManager.findImprimirResultado(eleicao.getId());

		assertNotNull(resultado);
		assertEquals(eleicao.getSomaVotos(),resultado.getSomaVotos());
	}

	@Test(expected=ColecaoVaziaException.class)
	public void testFindImprimirResultadoException() throws ColecaoVaziaException{
		when(eleicaoDao.findImprimirResultado(eq(1L))).thenReturn(new ArrayList<CandidatoEleicao>());
		eleicaoManager.findImprimirResultado(1L);
	}
}