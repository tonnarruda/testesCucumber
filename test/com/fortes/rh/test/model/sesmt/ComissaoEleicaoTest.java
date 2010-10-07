package com.fortes.rh.test.model.sesmt;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;

public class ComissaoEleicaoTest extends TestCase
{
	public void testEquals()
	{
		Eleicao eleicao1 = EleicaoFactory.getEntity(1L);
		Eleicao eleicao2 = EleicaoFactory.getEntity(2L);
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		
		ComissaoEleicao comissaoEleicao1 = ComissaoEleicaoFactory.getEntity(1L);
		comissaoEleicao1.setEleicao(eleicao1);
		comissaoEleicao1.setColaborador(colaborador1);

		ComissaoEleicao comissaoEleicao2 = ComissaoEleicaoFactory.getEntity(1L);
		comissaoEleicao2.setEleicao(eleicao1);
		comissaoEleicao2.setColaborador(colaborador1);
		
		assertTrue(comissaoEleicao1.equals(comissaoEleicao2));
		
		comissaoEleicao1.setEleicao(eleicao2);
		assertFalse(comissaoEleicao1.equals(comissaoEleicao2));

		comissaoEleicao1.setColaborador(colaborador2);
		assertFalse(comissaoEleicao1.equals(comissaoEleicao2));

		ComissaoEleicao comissaoEleicao3 = ComissaoEleicaoFactory.getEntity(3L);
		ComissaoEleicao comissaoEleicao4 = ComissaoEleicaoFactory.getEntity(4L);
		assertFalse(comissaoEleicao3.equals(comissaoEleicao4));
	}
}
