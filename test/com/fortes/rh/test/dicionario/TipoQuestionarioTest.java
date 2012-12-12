package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoQuestionario;

public class TipoQuestionarioTest extends TestCase
{
	public void testTipoPergunta()
	{
		TipoQuestionario tipo = new TipoQuestionario();

		assertEquals(5, tipo.size());
		assertEquals("Avaliação", tipo.get(0));
		assertEquals("Entrevista", tipo.get(1));
		assertEquals("Pesquisa", tipo.get(2));
		assertEquals("Questionário de Avaliação de Curso", tipo.get(3));
		assertEquals("Ficha Médica", tipo.get(4));
	}

	public void testGetDescricao()
	{
		assertEquals("avaliação", TipoQuestionario.getDescricao(0));
		assertEquals("entrevista", TipoQuestionario.getDescricao(1));
		assertEquals("pesquisa", TipoQuestionario.getDescricao(2));
		assertEquals("avaliação da turma", TipoQuestionario.getDescricao(3));
		assertEquals("ficha médica", TipoQuestionario.getDescricao(4));
	}

	public void testGetDescricaoMaisc()
	{
		assertEquals("Avaliação", TipoQuestionario.getDescricaoMaisc(0));
		assertEquals("Entrevista de Desligamento", TipoQuestionario.getDescricaoMaisc(1));
		assertEquals("Pesquisa", TipoQuestionario.getDescricaoMaisc(2));
		assertEquals("Avaliação da Turma", TipoQuestionario.getDescricaoMaisc(3));
		assertEquals("Ficha Médica", TipoQuestionario.getDescricaoMaisc(4));
	}

	public void testGetUrlVoltarList()
	{
		assertEquals("../entrevista/list.action", TipoQuestionario.getUrlVoltarList(1, null));
	}

	public void testGet()
	{
		assertEquals(0, TipoQuestionario.getAVALIACAO());
		assertEquals(1, TipoQuestionario.getENTREVISTA());
		assertEquals(2, TipoQuestionario.getPESQUISA());
		assertEquals(3, TipoQuestionario.getAVALIACAOTURMA());
		assertEquals(4, TipoQuestionario.getFICHAMEDICA());
	}
}
