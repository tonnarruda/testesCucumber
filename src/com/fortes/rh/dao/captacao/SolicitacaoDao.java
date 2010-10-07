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

public interface SolicitacaoDao extends GenericDao<Solicitacao>
{
	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId);
	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Usuario usuario, Long cargoId);
	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Boolean liberada, Boolean suspensa);
	public Solicitacao getValor(Long solcitacaoId);
	public Solicitacao findByIdProjection(Long solicitacaoId);
	public Solicitacao findByIdProjectionForUpdate(Long solicitacaoId);
	public void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId);
	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId);
	public void updateSuspendeSolicitacao(boolean suspender, String observacao, Long solicitacaoId);
	public void migrarBairro(Long bairroId, Long bairroDestinoId);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId);
}