package com.fortes.rh.test.dicionario;

import java.util.TreeMap;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;

public class MeioComunicacaoTest extends TestCase
{
	
	public void testChave()
	{
		assertEquals(3, MeioComunicacao.values().length);
		
		assertEquals(new Integer(0), MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getId());
		assertEquals(new Integer(1), MeioComunicacao.CAIXA_MENSAGEM.getId());
		assertEquals(new Integer(2), MeioComunicacao.EMAIL.getId());
	}

	public void testDescricao()
	{
		assertEquals("Caixa de mensagem", MeioComunicacao.CAIXA_MENSAGEM.getDescricao());
		assertEquals("Email", MeioComunicacao.EMAIL.getDescricao());
	}

	public void testGetDescricaoById()
	{
		assertEquals("Caixa de mensagem", MeioComunicacao.getDescricaoById(1));
		assertEquals("Email", MeioComunicacao.getDescricaoById(2));
	}

	public void testSetMeiosDeComunicacoes()
	{
		TreeMap<Integer, String> meioComunicacao = new TreeMap<Integer, String>();
		MeioComunicacao.setMeiosDeComunicacoes(meioComunicacao);
		
		assertEquals(3, meioComunicacao.size());
		assertEquals(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getDescricao(), meioComunicacao.get(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getId()));
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), meioComunicacao.get(MeioComunicacao.EMAIL.getId()));
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), meioComunicacao.get(MeioComunicacao.CAIXA_MENSAGEM.getId()));
	}
}
