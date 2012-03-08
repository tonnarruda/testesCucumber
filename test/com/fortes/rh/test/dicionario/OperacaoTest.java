package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	
	public void testQtdOperacoes()
	{
		assertEquals(16, Operacao.values().length);
	}

	public void testGetHashMapGrupos()
	{
		assertEquals(6, Operacao.getHashMapGrupos().size());
	}
	
	public void testGetDescricaoById()
	{
		assertEquals("", Operacao.getDescricaoById(1212122121));
		assertEquals("Aviso de cadastro de candidato pelo módulo externo", Operacao.getDescricaoById(1));
		assertEquals("Aviso automático da quantidade de currículos cadastros por mês", Operacao.getDescricaoById(2));
		assertEquals("Exibir solicitações com candidatos do módulo externo", Operacao.getDescricaoById(3));
		assertEquals("Encerramento da solicitação de pessoal", Operacao.getDescricaoById(4));
		assertEquals("Alteração no status da solicitação de pessoal", Operacao.getDescricaoById(5));
		assertEquals("Enviar lembrete avaliação desempenho", Operacao.getDescricaoById(6));
		assertEquals("Liberar pesquisa", Operacao.getDescricaoById(7));
		assertEquals("Lembrete automático de pesquisa não liberada", Operacao.getDescricaoById(8));
		assertEquals("Aviso automático das avaliações do período de experiência a vencer", Operacao.getDescricaoById(9));
		assertEquals("Aviso automático de exames previstos", Operacao.getDescricaoById(10));
		assertEquals("Liberar turma", Operacao.getDescricaoById(11));
		assertEquals("Contratação de Colaborador", Operacao.getDescricaoById(12));
		assertEquals("Cancelamento de Situação no AC Pessoal", Operacao.getDescricaoById(13));
		assertEquals("Desligar colaborador no AC", Operacao.getDescricaoById(14));
		assertEquals("Configuração do limite de colaboradores por cargo", Operacao.getDescricaoById(15));
		assertEquals("Aviso automático de backup", Operacao.getDescricaoById(16));
	}

	public void testGetMeioComunicacaoById()
	{
		assertEquals(null, Operacao.getMeioComunicacaosById(1212122121));
		assertEquals(2, Operacao.getMeioComunicacaosById(1).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(2).size());
		assertEquals(2, Operacao.getMeioComunicacaosById(4).size());
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
		assertEquals(2, Operacao.getMeioComunicacaosById(16).size());
	}

	
	public void testChave()
	{
		assertEquals(1, Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());
		assertEquals(2, Operacao.QTD_CURRICULOS_CADASTRADOS.getId());
		assertEquals(3, Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.getId());
		assertEquals(4, Operacao.ENCERRAMENTO_SOLICITACAO.getId());
		assertEquals(5, Operacao.ALTERAR_STATUS_SOLICITACAO.getId());
		assertEquals(6, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());
		assertEquals(7, Operacao.LIBERAR_QUESTIONARIO.getId());
		assertEquals(8, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());
		assertEquals(9, Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		assertEquals(10, Operacao.EXAMES_PREVISTOS.getId());
		assertEquals(11, Operacao.LIBERAR_TURMA.getId());
		assertEquals(12, Operacao.CONTRATAR_COLABORADOR.getId());
		assertEquals(13, Operacao.CANCELAR_SITUACAO_AC.getId());
		assertEquals(14, Operacao.DESLIGAR_COLABORADOR_AC.getId());
		assertEquals(15, Operacao.CONFIGURACAO_LIMITE_COLABORADOR.getId());
		assertEquals(16, Operacao.BACKUP_AUTOMATICO.getId());
	}

	public void testEncerrarSolicitacao()
	{
		assertEquals(2, Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAlterarStatusSolicitacao()
	{
		assertEquals(2, Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	
	public void testLiberarQuestionario()
	{
		assertEquals(2, Operacao.LIBERAR_QUESTIONARIO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_QUESTIONARIO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testLembreteAutomaticoPesquisaNaoLiberada()
	{
		assertEquals(2, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testCadastroCandidatoModuloExterno()
	{
		Operacao operacao = Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAvisoQtdCurriculosCadastrados()
	{
		Operacao operacao = Operacao.QTD_CURRICULOS_CADASTRADOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAvaliacaoPeriodoExperienciaVencendo()
	{
		Operacao operacao = Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(3,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testExamesPrevistos()
	{
		Operacao operacao = Operacao.EXAMES_PREVISTOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testBackupAutomatico()
	{
		Operacao operacao = Operacao.BACKUP_AUTOMATICO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testCancelarSituacaoAC()
	{
		Operacao operacao = Operacao.CANCELAR_SITUACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(3,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	public void testCandidatosModuloExterno()
	{
		assertEquals(2, Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testDesligaColaboradorAC()
	{
		Operacao operacao = Operacao.DESLIGAR_COLABORADOR_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testSolicitacaoCandidatoModuloExterno()
	{
		Operacao operacao = Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testConfiguracaoLimiteColaborador()
	{
		Operacao operacao = Operacao.CONFIGURACAO_LIMITE_COLABORADOR;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testLiberarTurma()
	{
		Operacao operacao = Operacao.LIBERAR_TURMA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
}
