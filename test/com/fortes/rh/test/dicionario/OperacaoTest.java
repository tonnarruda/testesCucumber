package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	public void testChave()
	{
		assertEquals(3, Operacao.values().length);
		
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.getId());
		assertEquals(1, Operacao.ENCERRAR_SOLICITACAO.getId());
		assertEquals(2, Operacao.LIBERAR_SOLICITACAO.getId());
	}

	public void testSelecionarOperacao()
	{
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.meioComunicação().size());
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.enviarPara().size());
	}
	
	public void testEncerrarSolicitacao()
	{
		assertEquals(1, Operacao.ENCERRAR_SOLICITACAO.meioComunicação().size());
		assertEquals(1, Operacao.ENCERRAR_SOLICITACAO.meioComunicação().values().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAR_SOLICITACAO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.ENCERRAR_SOLICITACAO.enviarPara().size());
		assertEquals(1, Operacao.ENCERRAR_SOLICITACAO.enviarPara().values().size());
		assertEquals(EnviarPara.CANDIDATO_NAO_APTO.getDescricao(), Operacao.ENCERRAR_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testLiberarSolicitacao()
	{
		assertEquals(2, Operacao.LIBERAR_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), Operacao.LIBERAR_SOLICITACAO.meioComunicação().values().toArray()[0]);

		assertEquals(2, Operacao.LIBERAR_SOLICITACAO.enviarPara().size());
		assertEquals(EnviarPara.AVULSO.getDescricao(), Operacao.LIBERAR_SOLICITACAO.enviarPara().values().toArray()[1]);
		assertEquals(EnviarPara.USUARIO.getDescricao(), Operacao.LIBERAR_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testGetHashMap()
	{
		assertEquals(3, Operacao.getHashMap().size());
	}
	
	public void testGetDescricaoById()
	{
		assertEquals("", Operacao.getDescricaoById(1212122121));
		assertEquals("Selecione...", Operacao.getDescricaoById(0));
		assertEquals("Encerrar Solicitação de Pessoal", Operacao.getDescricaoById(1));
		assertEquals("Liberar Solicitação", Operacao.getDescricaoById(2));
	}

	public void testGetMeioComunicacaoById()
	{
		assertEquals(null, Operacao.getMeioComunicacaoById(1212122121));
		assertEquals(0, Operacao.getMeioComunicacaoById(0).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(1).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(2).size());
	}
	
	public void testGetEnviarParaById()
	{
		assertEquals(null, Operacao.getEnviarParaById(1212122121));
		assertEquals(0, Operacao.getEnviarParaById(0).size());
		assertEquals(1, Operacao.getEnviarParaById(1).size());
		assertEquals(2, Operacao.getEnviarParaById(2).size());
	}
	
}
