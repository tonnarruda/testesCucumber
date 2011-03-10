package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;

public class TipoAvaliacaoCursoTest extends TestCase
{
	private static final char TIPO_INVALIDO = '*';

	public void testMap()
	{
		TipoAvaliacaoCurso apto = new TipoAvaliacaoCurso();
	
		assertEquals(2, apto.size());
	}

	public void testGetDescSexoParaFicha()
	{
		assertEquals("", TipoAvaliacaoCurso.getDescricao(TIPO_INVALIDO));
		assertEquals("Nota", TipoAvaliacaoCurso.getDescricao(TipoAvaliacaoCurso.NOTA));
		assertEquals("Porcentagem (%)", TipoAvaliacaoCurso.getDescricao(TipoAvaliacaoCurso.PORCENTAGEM));
	}
}
