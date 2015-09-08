/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 * Requisito: RFA016
 */
package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;

public interface SolicitacaoDao extends GenericDao<Solicitacao>
{
	Integer getCount(char visualizar, Long empresaId, Usuario usuario, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim);
	Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, Long empresaId, Usuario usuario, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim);
	Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Character status, Boolean suspensa);
	Solicitacao getValor(Long solcitacaoId);
	Solicitacao findByIdProjection(Long solicitacaoId);
	Solicitacao findByIdProjectionForUpdate(Long solicitacaoId);
	void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId, String observacaoLiberador);
	Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId);
	void updateSuspendeSolicitacao(boolean suspender, String observacao, Long solicitacaoId);
	void updateStatusSolicitacao(Solicitacao solicitacao);
	void migrarBairro(Long bairroId, Long bairroDestinoId);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds, Long empresaId);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido);
	Collection<Solicitacao> findAllByCandidato(Long candidatoId);
	Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim, char dataStatusAprovacaoSolicitacao);
	Collection<FaixaSalarial> findQtdContratadosFaixa(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solisolicitacaoIds, Date dataIni, Date dataFim);
	Collection<AreaOrganizacional> findQtdContratadosArea(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	Collection<MotivoSolicitacao> findQtdContratadosMotivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	Collection<Solicitacao> findByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds);
	Collection<Solicitacao> getNomesColabSubstituidosSolicitacaoEncerrada(Long empresaId);
}