package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;

public class MotivoExtintorManutencaoTest extends TestCase
{
	public void testTipoExtintor()
	{
		MotivoExtintorManutencao motivo = new MotivoExtintorManutencao();

		assertEquals(5, motivo.size());
		assertEquals("Prazo de recarga vencido", motivo.get("1"));
		assertEquals("Prazo do teste hidrostático vencido", motivo.get("2"));
		assertEquals("Usado em treinamento", motivo.get("3"));
		assertEquals("Usado em incêndio", motivo.get("4"));
		assertEquals("Outros (especifique abaixo)", motivo.get("0"));
	}
}
