package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	
	public void testGetHashMap()
	{
		assertEquals(9, Operacao.getHashMap().size());
	}
	
	public void testGetDescricaoById()
	{
		assertEquals("", Operacao.getDescricaoById(1212122121));
		assertEquals("Selecione...", Operacao.getDescricaoById(0));
		assertEquals("Encerramento da solicitação de pessoal", Operacao.getDescricaoById(1));
		assertEquals("Liberar solicitação", Operacao.getDescricaoById(2));
		assertEquals("Ateração no status da solicitação de pessoal", Operacao.getDescricaoById(3));
		assertEquals("Enviar lembrete avaliação desempenho", Operacao.getDescricaoById(4));
		assertEquals("Liberar questionário", Operacao.getDescricaoById(5));
		assertEquals("Lembrete de pesquisa não respondida", Operacao.getDescricaoById(6));
		assertEquals("Lembrete automático de pesquisa não liberada", Operacao.getDescricaoById(7));
		assertEquals("Aviso de cadastro de candidato pelo módulo externo", Operacao.getDescricaoById(8));
	}

	public void testGetMeioComunicacaoById()
	{
		assertEquals(null, Operacao.getMeioComunicacaoById(1212122121));
		assertEquals(0, Operacao.getMeioComunicacaoById(0).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(1).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(2).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(3).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(4).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(5).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(6).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(7).size());
		assertEquals(1, Operacao.getMeioComunicacaoById(8).size());
	}
	
	public void testGetEnviarParaById()
	{
		assertEquals(null, Operacao.getEnviarParaById(1212122121));
		assertEquals(0, Operacao.getEnviarParaById(0).size());
		assertEquals(1, Operacao.getEnviarParaById(1).size());
		assertEquals(1, Operacao.getEnviarParaById(2).size());
		assertEquals(1, Operacao.getEnviarParaById(3).size());
		assertEquals(1, Operacao.getEnviarParaById(4).size());
		assertEquals(1, Operacao.getEnviarParaById(5).size());
		assertEquals(1, Operacao.getEnviarParaById(6).size());
		assertEquals(1, Operacao.getEnviarParaById(7).size());
		assertEquals(1, Operacao.getEnviarParaById(8).size());
	}
	
	public void testChave()
	{
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.getId());
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.getId());
		assertEquals(2, Operacao.LIBERAR_SOLICITACAO.getId());
		assertEquals(3, Operacao.ALTEREAR_STATUS_SOLICITACAO.getId());
		assertEquals(4, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());
		assertEquals(5, Operacao.LIBERAR_QUESTIONARIO.getId());
		assertEquals(6, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId());
		assertEquals(7, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());
		assertEquals(8, Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());
	}

	public void testSelecionarOperacao()
	{
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.meioComunicação().size());
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.enviarPara().size());
	}
	
	public void testEncerrarSolicitacao()
	{
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().size());
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().values().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.enviarPara().size());
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.enviarPara().values().size());
		assertEquals(EnviarPara.CANDIDATO_NAO_APTO.getDescricao(), Operacao.ENCERRAMENTO_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testAlterarStatusSolicitacao()
	{
		assertEquals(1, Operacao.ALTEREAR_STATUS_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ALTEREAR_STATUS_SOLICITACAO.meioComunicação().values().toArray()[0]);

		assertEquals(1, Operacao.ALTEREAR_STATUS_SOLICITACAO.enviarPara().size());
		assertEquals(EnviarPara.SOLICITANTE_SOLICITACAO.getDescricao(), Operacao.ALTEREAR_STATUS_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testLiberadorSolicitacao()
	{
		assertEquals(1, Operacao.LIBERAR_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_SOLICITACAO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.LIBERAR_SOLICITACAO.enviarPara().size());
		assertEquals(EnviarPara.LIBERADOR_SOLICITACAO.getDescricao(), Operacao.LIBERAR_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testLembreteAvaliacaoDesempenho()
	{
		assertEquals(1, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.enviarPara().size());
		assertEquals(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getDescricao(), Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.enviarPara().values().toArray()[0]);
	}
	
	public void testLiberarQuestionario()
	{
		assertEquals(1, Operacao.LIBERAR_QUESTIONARIO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_QUESTIONARIO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.enviarPara().size());
		assertEquals(EnviarPara.COLABORADOR.getDescricao(), Operacao.LIBERAR_QUESTIONARIO.enviarPara().values().toArray()[0]);
	}
	
	public void testLembreteQuestionarioNaoRespondida()
	{
		assertEquals(1, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.enviarPara().size());
		assertEquals(EnviarPara.COLABORADOR.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.enviarPara().values().toArray()[0]);
	}
	
	public void testLiberarSolicitacao()
	{
		assertEquals(1, Operacao.LIBERAR_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_SOLICITACAO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.LIBERAR_SOLICITACAO.enviarPara().size());
		assertEquals(EnviarPara.LIBERADOR_SOLICITACAO.getDescricao(), Operacao.LIBERAR_SOLICITACAO.enviarPara().values().toArray()[0]);
	}
	
	public void testLembreteAutomaticoPesquisaNaoLiberada()
	{
		assertEquals(1, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.enviarPara().size());
		assertEquals(EnviarPara.RESPONSAVEL_RH.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.enviarPara().values().toArray()[0]);
	}
	
	public void testCadastroCandidatoModuloExterno()
	{
		Operacao operacao = Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO;
		
		assertEquals(1, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[0]);
		
		assertEquals(1, operacao.enviarPara().size());
		assertEquals(EnviarPara.RESPONSAVEL_RH.getDescricao(), operacao.enviarPara().values().toArray()[0]);
	}
	
}
