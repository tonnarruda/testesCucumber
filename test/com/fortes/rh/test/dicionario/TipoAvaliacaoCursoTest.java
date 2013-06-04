package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;

public class TipoAvaliacaoCursoTest extends TestCase
{
	private static final char TIPO_INVALIDO = '*';

	public void testMap()
	{
		TipoAvaliacaoCurso tipo = new TipoAvaliacaoCurso();
		assertEquals(3, tipo.size());
	}

	public void testGetDescSexoParaFicha()
	{
		assertEquals("", TipoAvaliacaoCurso.getDescricao(TIPO_INVALIDO));
		assertEquals("Nota", TipoAvaliacaoCurso.getDescricao(TipoAvaliacaoCurso.NOTA));
		assertEquals("Porcentagem (%)", TipoAvaliacaoCurso.getDescricao(TipoAvaliacaoCurso.PORCENTAGEM));
		assertEquals("Avaliação", TipoAvaliacaoCurso.getDescricao(TipoAvaliacaoCurso.AVALIACAO));
	}
}
