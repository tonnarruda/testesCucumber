package com.fortes.rh.test.dicionario;

import java.util.HashMap;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

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

	public void testSetEmail()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		MeioComunicacao.setEmail(meioComunicacao);
		
		assertEquals(1, meioComunicacao.size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), meioComunicacao.get(MeioComunicacao.EMAIL.getId()));
	}
	
	public void testSetMeiosDeComunicacoes()
	{
		HashMap<Integer, String> meioComunicacao = new HashMap<Integer, String>();
		MeioComunicacao.setMeiosDeComunicacoes(meioComunicacao);
		
		assertEquals(3, meioComunicacao.size());
		assertEquals(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getDescricao(), meioComunicacao.get(MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO.getId()));
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), meioComunicacao.get(MeioComunicacao.EMAIL.getId()));
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), meioComunicacao.get(MeioComunicacao.CAIXA_MENSAGEM.getId()));
	}
	
	
	public void testGetEnviarParaById()
	{
		MeioComunicacao email = MeioComunicacao.getMeioComunicacaoById(2);
		
		assertEquals(null, MeioComunicacao.getEnviarParaById(1212122121));
		assertEquals(0, email.getEnviarPara(Operacao.SELECIONAR_OPERACAO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.ALTERAR_STATUS_SOLICITACAO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.ENCERRAMENTO_SOLICITACAO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.EXAMES_PREVISTOS.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId()).size());
		assertEquals(1, email.getEnviarPara(Operacao.QTD_CURRICULOS_CADASTRADOS.getId()).size());
	}
}
