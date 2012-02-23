package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;

public interface CandidatoSolicitacaoManager extends GenericManager<CandidatoSolicitacao>
{
	CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand);
	public void insertCandidatos(String[] candidatosId, Solicitacao solicitacao, char status);
	public void moverCandidatos(Long[] candidatosSolicitacaoId, Solicitacao solicitacao) throws ColecaoVaziaException;
	public Collection<CandidatoSolicitacao> getCandidatosBySolicitacaoAberta(String[] etapaCheck, Long empresaId);
	Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos);
	public String[] getEmailNaoAptos(Long solicitacaoId, Empresa empresa) throws Exception;
	public Collection<CandidatoSolicitacao> verificaExisteColaborador(Collection<CandidatoSolicitacao> candidatoSolicitacaos,Long empresaId);
	CandidatoSolicitacao findCandidatoSolicitacaoById(Long candidatoSolicitacaoId);
	Collection<CandidatoSolicitacao> findCandidatoSolicitacaoById(Long[] candidatoSolicitacaoIds);
	Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca);
	Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId);
	Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagem);
	void updateTriagem(Long candidatoSolicitacaoid, boolean triagem);
	public Boolean isCandidatoSolicitacaoByCandidato(Long candidatoId);
	Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId);
	Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca);
	void setColaboradorQuestionarioId(Collection<CandidatoSolicitacao> candidatoSolicitacaos, Avaliacao avaliacao, Long solicitacaoId);
	public void setStatus(Long candidatoSolicitacaoId, char status);
	Collection<Integer> getIdF2RhCandidato(Long SolicitacaoId);
	void setStatusByColaborador(Long colaboradorId, char status);
	void removeCandidato(Long candidatoId);
}