package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	
	public void testGetHashMap()
	{
		assertEquals(16, Operacao.getHashMap().size());
	}
	
	public void testGetDescricaoById()
	{
		assertEquals("", Operacao.getDescricaoById(1212122121));
		assertEquals("Selecione...", Operacao.getDescricaoById(0));
		assertEquals("Encerramento da solicitação de pessoal", Operacao.getDescricaoById(1));
		assertEquals("Alteração no status da solicitação de pessoal", Operacao.getDescricaoById(2));
		assertEquals("Enviar lembrete avaliação desempenho", Operacao.getDescricaoById(3));
		assertEquals("Liberar questionário", Operacao.getDescricaoById(4));
		assertEquals("Lembrete de pesquisa não respondida", Operacao.getDescricaoById(5));
		assertEquals("Lembrete automático de pesquisa não liberada", Operacao.getDescricaoById(6));
		assertEquals("Aviso de cadastro de candidato pelo módulo externo", Operacao.getDescricaoById(7));
		assertEquals("Aviso automático da quantidade de currículos cadastros por mês", Operacao.getDescricaoById(8));
		assertEquals("Aviso automático das avaliações do período de experiência a vencer", Operacao.getDescricaoById(9));
		assertEquals("Aviso automático de exames previstos", Operacao.getDescricaoById(10));
		assertEquals("Aviso automático de backup", Operacao.getDescricaoById(11));
		assertEquals("Contratação de Colaborador", Operacao.getDescricaoById(12));
		assertEquals("Cancelamento de Situação no AC Pessoal", Operacao.getDescricaoById(13));
		assertEquals("Exibir solicitações com canditados do modulo externo", Operacao.getDescricaoById(14));
		assertEquals("Desligar colaborador no AC", Operacao.getDescricaoById(15));
	}

	public void testGetMeioComunicacaoById()
	{
		assertEquals(null, Operacao.getMeioComunicacaosById(1212122121));
		assertEquals(1, Operacao.getMeioComunicacaosById(0).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(1).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(2).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(4).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(5).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(6).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(7).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(8).size());
		assertEquals(3, Operacao.getMeioComunicacaosById(9).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(10).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(11).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(12).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(13).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(14).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(15).size());
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
		assertEquals(12, Operacao.CONTRATAR_COLABORADOR.getId());
		assertEquals(13, Operacao.CANCELAR_SITUACAO_AC.getId());
		assertEquals(14, Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.getId());
		assertEquals(15, Operacao.DESLIGAR_COLABORADOR_AC.getId());
	}

	public void testSelecionarOperacao()
	{
		assertEquals(1, Operacao.SELECIONAR_OPERACAO.meioComunicação().size());
	}
	
	public void testCandidatosModuloExterno()
	{
		assertEquals(2, Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.meioComunicação().values().toArray()[1]);
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
	
	public void testCancelarSituacaoAC()
	{
		Operacao operacao = Operacao.CANCELAR_SITUACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}

	public void testDesligaColaboradorAC()
	{
		Operacao operacao = Operacao.DESLIGAR_COLABORADOR_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
	public void testSolicitacaoCandidatoModuloExterno()
	{
		Operacao operacao = Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
}
