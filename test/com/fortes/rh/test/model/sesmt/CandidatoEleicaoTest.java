package com.fortes.rh.test.model.sesmt;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class CandidatoEleicaoTest extends TestCase
{
	public void testEquals()
	{
		Eleicao eleicao1 = EleicaoFactory.getEntity(1L);
		Eleicao eleicao2 = EleicaoFactory.getEntity(2L);
		Colaborador candidato1 = ColaboradorFactory.getEntity(1L);
		Colaborador candidato2 = ColaboradorFactory.getEntity(2L);
		
		CandidatoEleicao candidatoEleicao1 = CandidatoEleicaoFactory.getEntity(1L);
		candidatoEleicao1.setEleicao(eleicao1);
		candidatoEleicao1.setCandidato(candidato1);

		CandidatoEleicao candidatoEleicao2 = CandidatoEleicaoFactory.getEntity(1L);
		candidatoEleicao2.setEleicao(eleicao1);
		candidatoEleicao2.setCandidato(candidato1);
		
		assertTrue(candidatoEleicao1.equals(candidatoEleicao2));
		
		candidatoEleicao1.setEleicao(eleicao2);
		assertFalse(candidatoEleicao1.equals(candidatoEleicao2));

		candidatoEleicao1.setCandidato(candidato2);
		assertFalse(candidatoEleicao1.equals(candidatoEleicao2));

		CandidatoEleicao candidatoEleicao3 = CandidatoEleicaoFactory.getEntity(3L);
		CandidatoEleicao candidatoEleicao4 = CandidatoEleicaoFactory.getEntity(4L);
		assertFalse(candidatoEleicao3.equals(candidatoEleicao4));
	}
}
