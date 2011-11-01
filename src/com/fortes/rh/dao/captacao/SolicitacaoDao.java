/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 * Requisito: RFA016
 */
package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;

public interface SolicitacaoDao extends GenericDao<Solicitacao>
{
	Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId);
	Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId);
	Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Boolean liberada, Boolean suspensa);
	Solicitacao getValor(Long solcitacaoId);
	Solicitacao findByIdProjection(Long solicitacaoId);
	Solicitacao findByIdProjectionForUpdate(Long solicitacaoId);
	void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId);
	Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId);
	void updateSuspendeSolicitacao(boolean suspender, String observacao, Long solicitacaoId);
	void migrarBairro(Long bairroId, Long bairroDestinoId);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao);
	Collection<Solicitacao> findAllByCandidato(Long candidatoId);
	Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Date dataIni, Date dataFim);
	Collection<FaixaSalarial> findQtdContratadosFaixa(Long empresaId, Date dataIni, Date dataFim);
}