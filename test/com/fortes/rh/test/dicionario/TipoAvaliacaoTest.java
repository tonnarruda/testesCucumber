package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.TipoAvaliacao;

public class TipoAvaliacaoTest extends TestCase
{
	public void testMap()
	{
		TipoAvaliacao tipo = new TipoAvaliacao();
	
		assertEquals(3, tipo.size());
	}
	
	public void testGetAvaliacoes() {
		TipoAvaliacao tipo = new TipoAvaliacao();
		assertEquals("Pesquisa", tipo.get(TipoAvaliacao.PESQUISA));
		assertEquals("Avaliação", tipo.get(TipoAvaliacao.AVALIACAO));
		assertEquals("Entrevista", tipo.get(TipoAvaliacao.ENTREVISTA));
	}

}
