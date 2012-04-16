package com.fortes.rh.business.geral;


import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.ws.TSituacao;

public interface GerenciadorComunicacaoManager extends GenericManager<GerenciadorComunicacao>
{
	void enviaEmailCandidatosNaoAptos(Empresa empresa, Long solicitacaoId) throws Exception;
	void enviaEmailSolicitanteSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario);
	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao);
	public void enviarLembreteAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Empresa empresa);
	void enviaEmailQuestionarioLiberado(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios);
	void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId);
	void enviaEmailQuestionario(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios);
	void enviaLembreteDeQuestionarioNaoLiberado();
	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas, Date inicioMes, Date fimMes, Collection<Candidato> candidatos);
	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores);
	void enviaLembreteExamesPrevistos(Collection<Empresa> empresas);
	void enviaMensagemLembretePeriodoExperiencia();
	void notificaBackup(String arquivoDeBackup);
	void enviarEmailContratacaoColaborador(String colaboradorNome, Empresa empresa) throws Exception;
	void enviaMensagemCancelamentoSituacao(TSituacao situacao, String mensagem, HistoricoColaborador historicoColaborador);
	boolean existeConfiguracaoParaCandidatosModuloExterno(Long emrpesaId);
	void enviaMensagemDesligamentoColaboradorAC(String codigo, String empCodigo, String grupoAC, Empresa empresa);
	public void enviaEmailConfiguracaoLimiteColaborador(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa);
	void enviarAvisoEmailLiberacao(Turma turma, Long empresaId);
	void enviarAvisoEmail(Turma turma, Long empresaId);
	void enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(Long colaboradorAvaliadoId, Long avaliacaoId, Usuario usuario, Empresa empresa);
	void enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi();
	void enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi();
	void insereGerenciadorComunicacaoDefault(Empresa empresa);
	void enviaMensagemCancelamentoContratacao(Colaborador colaborador, String mensagem);
	void enviaMensagemCancelamentoSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem);
}
