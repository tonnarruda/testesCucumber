package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;

/**
 * @author Tiago Lopes
 */
public interface SolicitacaoExameDao extends GenericDao<SolicitacaoExame>
{
	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, Long[] exameIds, ResultadoExame resultadoExame);
	public Collection<SolicitacaoExame> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, TipoPessoa vinculo, String nomeBusca, String matriculaBusca, String motivo, Long[] exameIds, ResultadoExame resultadoExame);
	public Collection<SolicitacaoExameRelatorio> findImprimirSolicitacaoExames(Long solicitacaoExameId);
	public Collection<SolicitacaoExame> findByCandidatoOuColaborador(TipoPessoa vinculo, Long candidatoOuColaboradorId, String motivo);
	void transferirCandidatoToColaborador(Long empresaId, Long candidatoId, Long colaboradorId);
	void transferirColaboradorToCandidato(Long empresaId, Long candidatoId, Long colaboradorId);
	public Collection<SolicitacaoExame> findAtendimentosMedicos(Date inicio, Date fim, String[] motivos, MedicoCoordenador medicoCoordenador, Long empresaId, boolean agruparPorMotivo, boolean ordenarPorNome);
	public SolicitacaoExame findByIdProjection(Long id);
	public void removeByCandidato(Long candidatoId);
	public void removeByColaborador(Long colaboradorId);
	public Integer findProximaOrdem(Date data);
	public void ajustaOrdem(Date data, Integer ordemInicial, Integer ordemFinal, Integer ajuste);
}