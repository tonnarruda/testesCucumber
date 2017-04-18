package com.fortes.rh.dao.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public interface CandidatoSolicitacaoDao extends GenericDao<CandidatoSolicitacao>
{
	Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos);
	Collection<CandidatoSolicitacao> findNaoAptos(Long solicitacaoId);
	void updateSolicitacaoCandidatos(Solicitacao solicitacao, Collection<Long> ids);
	Collection<Long> findCandidatosIdsAptosMover(Long[] candidatosSolicitacaoId, Solicitacao solicitacao);
	CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand);
	CandidatoSolicitacao findCandidatoSolicitacaoById(Long candidatoSolicitacaoId);
	Collection<CandidatoSolicitacao> findCandidatoSolicitacaoById(Long[] candidatoSolicitacaoIds);
	Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId);
	Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagem);
	void updateTriagem(Long[] candidatoSolicitacaoIdsSelecionados, boolean triagem);
	CandidatoSolicitacao getCandidatoSolicitacaoByCandidato(Long id);
	Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId);
	Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status);
	Collection<CandidatoSolicitacao> getCandidatoSolicitacaoEtapasEmGrupo(Long solicitacaoId, Long etapaSeletivaId);
	Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca, Character status, boolean semHistorico);
	Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Long[] etapaIds, Long empresaId, char statusSolicitacao, char situacaoCandidato, Date dataIni, Date dataFim);
	void setStatusByColaborador(char status, Long... colaboradoresIds);
	void removeByCandidato(Long candidatoId);
	Collection<ColaboradorQuestionario> findAvaliacoesCandidatoSolicitacao(Long solicitacaoId, Long candidatoId);
	public void updateStatusAndRemoveDataContratacaoOrPromocao(Long id, char status); 
	Collection<CandidatoSolicitacao> findColaboradorParticipantesDaSolicitacaoByAreas(Long[] areasOrganizacionaisId, String solicitacaoDescricaoBusca, String colaboradorNomeBusca, char statusBusca, Integer page, Integer pagingSize);
	void updateStatusAutorizacaoGestor(CandidatoSolicitacao candidatoSolicitacao);
	CandidatoSolicitacao findByHistoricoColaboradorId(Long historicoColaboradorId);
	void updateStatusParaIndiferenteEmSolicitacoesEmAndamento(Long candidatoId);
	public void updateStatusSolicitacoesEmAndamentoByColaboradorId(Character status, Long[] colaboradoresIds);
}