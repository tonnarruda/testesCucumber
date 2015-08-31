package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EventoAgenda;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.ProdutividadeRelatorio;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;

public interface HistoricoCandidatoManager extends GenericManager<HistoricoCandidato>
{
	Collection<HistoricoCandidato> findByCandidato(Candidato candidato);
	Collection<SolicitacaoHistoricoColaborador> montaMapaHistorico(Collection<HistoricoCandidato> historicoCandidatos);
	Collection<HistoricoCandidato> findByCandidato(Collection<CandidatoSolicitacao> candidatos);
	Collection<HistoricoCandidato> findList(CandidatoSolicitacao candidatoSolicitacao);
	Collection<HistoricoCandidato> findByPeriodo(Map parametros);
	Collection<ProdutividadeRelatorio> getProdutividade(String ano, Long empresaId);
	void save(HistoricoCandidato historicoCandidato, String[] candidatosCheck)throws Exception;
	void saveHistoricos(HistoricoCandidato historicoCandidato, String[] candidatosCheck, boolean blacklist) throws Exception;
	HistoricoCandidato findByIdProjection(Long historicoId);
	Collection<ProcessoSeletivoRelatorio> relatorioProcessoSeletivo(String ano, Long empresaId, Long cargoId, Long[] etapaIds);
	String[] findResponsaveis();
	boolean updateAgenda(Long id, Date data, String horaIni, String horaFim, String observacao);
	Collection<EventoAgenda> getEventos(String responsavel, Long empresaId);
	Collection<HistoricoCandidato> getEventos(Long empresaId, Date dataIni, Date dataFim);
	int findQtdAtendidos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	int findQtdEtapasRealizadas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacoesIds, Date dataIni, Date dataFim);
	void removeByCandidatoSolicitacao(Long candidatoSolicitcaoid);
}