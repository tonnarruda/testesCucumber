/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 */
package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.geral.Empresa;

public interface SolicitacaoManager extends GenericManager<Solicitacao>
{
	public boolean removeCascade(Long id);
	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Boolean liberada, Boolean suspensa);
	public Solicitacao getValor(Long id);
	public void encerraSolicitacao(Solicitacao solicitacao, Empresa empresa) throws Exception;
	public Solicitacao findByIdProjection(Long solicitacaoId);
	public Solicitacao findByIdProjectionForUpdate(Long solicitacaoId);
	public void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId);
	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Long cargoId);
	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Long usuarioId, Long cargoId);
	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Long cargoId);
	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Long usuarioId, Long cargoId);
	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId);
	public void updateSuspendeSolicitacao(boolean suspender, String obsSuspensao, Long solicitacaoId);
	public void migrarBairro(Long bairroId, Long bairroDestinoId);
	void enviarEmailParaLiberadorSolicitacao(Solicitacao solicitacao, Empresa empresa, String[] emailsAvulsos) throws Exception;
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds);
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId);
	public void emailParaSolicitante(Usuario solicitante, Solicitacao solicitacao, Empresa empresa);
	public Solicitacao save(Solicitacao solicitacao, String[] emailsCheck);
	
}