package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	
	public void testGetHashMap()
	{
		assertEquals(13, Operacao.getHashMap().size());
	}
	
	public void testGetDescricaoById()
	{
		assertEquals("", Operacao.getDescricaoById(1212122121));
		assertEquals("Selecione...", Operacao.getDescricaoById(0));
		assertEquals("Encerramento da solicitação de pessoal", Operacao.getDescricaoById(1));
		assertEquals("Ateração no status da solicitação de pessoal", Operacao.getDescricaoById(2));
		assertEquals("Enviar lembrete avaliação desempenho", Operacao.getDescricaoById(3));
		assertEquals("Liberar questionário", Operacao.getDescricaoById(4));
		assertEquals("Lembrete de pesquisa não respondida", Operacao.getDescricaoById(5));
		assertEquals("Lembrete automático de pesquisa não liberada", Operacao.getDescricaoById(6));
		assertEquals("Aviso de cadastro de candidato pelo módulo externo", Operacao.getDescricaoById(7));
		assertEquals("Aviso automático da quantidade de currículos cadastros por mês", Operacao.getDescricaoById(8));
		assertEquals("Aviso automático das avaliações do período de experiência a vencer", Operacao.getDescricaoById(9));
		assertEquals("Aviso automático de exames previstos", Operacao.getDescricaoById(10));
		assertEquals("Aviso automático de backup", Operacao.getDescricaoById(11));
	}

	public void testGetMeioComunicacaoById()
	{
		assertEquals(null, Operacao.getMeioComunicacaoById(1212122121));
		assertEquals(0, Operacao.getMeioComunicacaoById(0).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(1).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(2).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(4).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(5).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(6).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(7).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(8).size());
		assertEquals(3, Operacao.getMeioComunicacaoById(9).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(10).size());
		assertEquals(2, Operacao.getMeioComunicacaoById(11).size());
	}

	
	public void testChave()
	{
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.getId());
		assertEquals(1, Operacao.ENCERRAMENTO_SOLICITACAO.getId());
		assertEquals(2, Operacao.ALTERAR_STATUS_SOLICITACAO.getId());
		assertEquals(3, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());
		assertEquals(4, Operacao.LIBERAR_QUESTIONARIO.getId());
		assertEquals(5, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId());
		assertEquals(6, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());
		assertEquals(7, Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());
		assertEquals(8, Operacao.QTD_CURRICULOS_CADASTRADOS.getId());
		assertEquals(9, Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		assertEquals(10, Operacao.EXAMES_PREVISTOS.getId());
		assertEquals(11, Operacao.BACKUP_AUTOMATICO.getId());
	}

	public void testSelecionarOperacao()
	{
		assertEquals(0, Operacao.SELECIONAR_OPERACAO.meioComunicação().size());
	}
	
	public void testEncerrarSolicitacao()
	{
		assertEquals(2, Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().values().toArray()[1]);
	}
	
	public void testAlterarStatusSolicitacao()
	{
		assertEquals(2, Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().values().toArray()[1]);
	}
	
	
	public void testLiberarQuestionario()
	{
		assertEquals(2, Operacao.LIBERAR_QUESTIONARIO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_QUESTIONARIO.meioComunicação().values().toArray()[1]);
	}
	
	public void testLembreteQuestionarioNaoRespondida()
	{
		assertEquals(2, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.meioComunicação().values().toArray()[1]);
	}
	
	public void testLembreteAutomaticoPesquisaNaoLiberada()
	{
		assertEquals(2, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().values().toArray()[1]);
	}
	
	public void testCadastroCandidatoModuloExterno()
	{
		Operacao operacao = Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
	public void testAvisoQtdCurriculosCadastrados()
	{
		Operacao operacao = Operacao.QTD_CURRICULOS_CADASTRADOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
	public void testAvaliacaoPeriodoExperienciaVencendo()
	{
		Operacao operacao = Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
	}
	
	public void testExamesPrevistos()
	{
		Operacao operacao = Operacao.EXAMES_PREVISTOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
	public void testBackupAutomatico()
	{
		Operacao operacao = Operacao.BACKUP_AUTOMATICO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
}
