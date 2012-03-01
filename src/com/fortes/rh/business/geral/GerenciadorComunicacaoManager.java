package com.fortes.rh.business.geral;


import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
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
	void enviaEmailQuestionarioNaoRespondido(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios);
	void enviaLembreteDeQuestionarioNaoLiberado();
	void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId);
	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas, Date inicioMes, Date fimMes, Collection<Candidato> candidatos);
	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores);
	void enviaLembreteExamesPrevistos(Collection<Empresa> empresas);
	void enviaMensagemLembretePeriodoExperiencia();
	void notificaBackup(String arquivoDeBackup);
	void enviarEmailContratacaoColaborador(String colaboradorNome, Empresa empresa) throws Exception;
	void enviaMensagemCancelamentoSituacao(TSituacao situacao, String mensagem, HistoricoColaborador historicoColaborador);
	boolean existeConfiguracaoParaCandidatosModuloExterno(Long emrpesaId);
}
