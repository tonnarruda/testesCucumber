package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.AreasFormacao;

public class AreasFormacaoTest extends TestCase
{
	public void testMap()
	{
		AreasFormacao areas = new AreasFormacao();
	
		assertEquals(74, areas.size());
	}
}
