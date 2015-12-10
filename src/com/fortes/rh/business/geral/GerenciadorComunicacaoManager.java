package com.fortes.rh.business.geral;


import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.ws.TSituacao;

public interface GerenciadorComunicacaoManager extends GenericManager<GerenciadorComunicacao>
{
	void enviaMensagemCancelamentoContratacao(Colaborador colaborador, String mensagem);
	void enviaEmailCandidatosNaoAptos(Empresa empresa, Long solicitacaoId) throws Exception;
	void enviaEmailSolicitanteSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario);
	boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao);
	void enviarLembreteResponderAvaliacaoDesempenho();
	void enviarLembreteResponderAvaliacaoDesempenhoAoLiberar(Long avaliacaoDesempenhoId, Empresa empresa);
	void enviarLembreteAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Empresa empresa);
	void enviaEmailQuestionarioLiberado(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios);
	void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId);
	void enviaEmailQuestionario(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios);
	void enviaLembreteDeQuestionarioNaoLiberado();
	void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas, Date inicioMes, Date fimMes, Collection<Candidato> candidatos);
	void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores);
	void enviaLembreteExamesPrevistos(Collection<Empresa> empresas);
	void enviaMensagemLembretePeriodoExperiencia();
	void notificaBackup(String arquivoDeBackup);
	void enviarEmailContratacaoColaborador(String colaboradorNome, Empresa empresa) throws Exception;
	void enviaMensagemCancelamentoSituacao(TSituacao situacao, String mensagem, HistoricoColaborador historicoColaborador);
	boolean existeConfiguracaoParaCandidatosModuloExterno(Long emrpesaId);
	void enviaAvisoDesligamentoColaboradorAC(String codigoEmpresa, String grupoAC, Empresa empresa, String... codigosACColaboradores);
	void enviaEmailConfiguracaoLimiteColaborador(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa);
	void enviarAvisoEmailLiberacao(Turma turma, AvaliacaoTurma avaliacaoTurma, Long empresaId);
	void enviarAvisoEmail(Turma turma, Long empresaId);
	void enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(Long colaboradorAvaliadoId, Long avaliacaoId, Usuario usuario, Empresa empresa);
	void enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi();
	void enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi();
	void insereGerenciadorComunicacaoDefault(Empresa empresa);
	void enviaMensagemCancelamentoSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC);
	void enviaAvisoSolicitacaoDesligamento(Long colaboradorId, Long empresaId);
	void enviaAvisoOcorrenciaCadastrada(ColaboradorOcorrencia colaboradorOcorrencia, Long empresaId);
	void enviaAvisoDeAfastamento(Long colaboradorAfastamentoId, Empresa empresa);
	void enviaAvisoContratacao(HistoricoColaborador historicoColaborador);
	void enviarEmailTerminoContratoTemporarioColaborador() throws Exception;
	void enviaAvisoAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado, Long empresaId);
	void enviaEmailCartaoAniversariantes();
	void enviaMensagemCadastroSituacaoAC(String nomeColaborador, TSituacao situacao);
	void enviarEmailParaResponsaveisSolicitacaoPessoal(Solicitacao solicitacao, Empresa empresa, String[] emailsMarcados) throws Exception;
	void enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador(Long empresaId, Colaborador colaborador, Long tabelaReajusteColaboradorId) throws Exception;
	void enviaAvisoAprovacaoSolicitacaoDesligamento(Long colaboradorId, String colaboradorNome, Long solicitanteDemissaoId, Empresa empresa, boolean aprovado);
	void enviarEmailAoCriarAcessoSistema(String login, String senha, String email, Empresa empresa);
	void enviarNotificacaoCursosOuCertificacoesAVencer();
	void enviarMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia(ColaboradorQuestionario colaboradorQuestionario, Usuario usuario, Empresa empresa);
	public void enviaEmailAoInserirConfiguracaoCompetenciaFaixaSalarial(Collection<Competencia> competenciasInseridas, Collection<Competencia> competenciasExcluidas, FaixaSalarial faixaSalarial, Empresa empresa);
}
