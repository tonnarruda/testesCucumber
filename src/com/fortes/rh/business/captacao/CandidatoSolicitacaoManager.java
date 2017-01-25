package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.spring.aop.callback.CandidatoSolicitacaoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Candidato na Solicitacao Pessoal")
public interface CandidatoSolicitacaoManager extends GenericManager<CandidatoSolicitacao>
{
	CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand);
	void insertCandidatos(String[] candidatosId, Solicitacao solicitacao, char status, Empresa empresa, Usuario usuarioLogado);
	void moverCandidatos(Long[] candidatosSolicitacaoId, Solicitacao solicitacao) throws ColecaoVaziaException;
	Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(String[] etapaCheck, Long empresaId, char statusSolicitacao, char situacaoCandidato, Date dataIni, Date dataFim);
	Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos);
	String[] getEmailNaoAptos(Long solicitacaoId, Empresa empresa) throws Exception;
	Collection<CandidatoSolicitacao> verificaExisteColaborador(Collection<CandidatoSolicitacao> candidatoSolicitacaos,Long empresaId);
	CandidatoSolicitacao findCandidatoSolicitacaoById(Long candidatoSolicitacaoId);
	Collection<CandidatoSolicitacao> findCandidatoSolicitacaoById(Long[] candidatoSolicitacaoIds);
	Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status);
	Collection<CandidatoSolicitacao> getCandidatoSolicitacaoEtapasEmGrupo(Long solicitacaoId, Long etapaSeletivaId);
	Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId);
	Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagem);
	void updateTriagem(Long[] candidatoSolicitacaoIdsSelecionados, boolean triagem);
	Boolean isCandidatoSolicitacaoByCandidato(Long candidatoId);
	Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId);
	Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status);
	void updateStatusAndRemoveDataContratacaoOrPromocao(Long candidatoSolicitacaoId, char status);
	void setStatusAndDataContratacaoOrPromocao(Long candidatoSolicitacaoId, char status, Date dataContratacaoOrPromocao);
	void setStatusByColaborador(char status, Long... colaboradoresIds);
	void removeCandidato(Long candidatoId);
	Collection<ColaboradorQuestionario> findAvaliacoesCandidatoSolicitacao(Long solicitacaoId, Long candidatoId);
	Collection<CandidatoSolicitacao> findColaboradorParticipantesDaSolicitacaoByAreas(Collection<AreaOrganizacional> areasOrganizacionais, String colaboradorNomeBusca, String solicitacaoDescricaoBusca, char statusBusca, Integer page, Integer pagingSize);
	@Audita(operacao="Alteração do Status Aprovação do Responsável", auditor=CandidatoSolicitacaoAuditorCallbackImpl.class)
	void updateStatusAutorizacaoGestor(CandidatoSolicitacao candidatoSolicitacao);
	CandidatoSolicitacao findByHistoricoColaboradorId(Long historicoColaboradorId);
	void updateStatusCandidatoAoCancelarContratacao(CandidatoSolicitacao candidatoSolicitacao, Long colaboradorId);
	public void updateStatusSolicitacoesEmAndamentoByColaboradorId(Character status, Long... colaboradorId);
}