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
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.spring.aop.callback.SolicitacaoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface SolicitacaoManager extends GenericManager<Solicitacao>
{
	@Audita(operacao="Altera Status", auditor=SolicitacaoAuditorCallbackImpl.class)
	void updateStatusSolicitacao(Solicitacao solicitacao);

	@Audita(operacao="Inserção", auditor=SolicitacaoAuditorCallbackImpl.class)
	Solicitacao save(Solicitacao solicitacao, String[] emailsMarcados, Long[] avaliacaoIds);
	
	@Audita(operacao="Atualização", auditor=SolicitacaoAuditorCallbackImpl.class)
	void updateSolicitacao(Solicitacao solicitacao, Long[] avaliacaoIds, Empresa empresa, Usuario usuario) throws Exception;

	@Audita(operacao="Encerramento", auditor=SolicitacaoAuditorCallbackImpl.class)
	void encerraSolicitacao(Solicitacao solicitacao, Empresa empresa) throws Exception;

	@Audita(operacao="Encerramento", auditor=SolicitacaoAuditorCallbackImpl.class)
	void encerrarSolicitacaoAoPreencherTotalVagas(Solicitacao solicitacao, Empresa empresa) throws Exception;
	
	boolean removeCascade(Long id);
	Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Character status, Boolean suspensa);
	Solicitacao getValor(Long id);
	Solicitacao findByIdProjection(Long solicitacaoId);
	Solicitacao findByIdProjectionForUpdate(Long solicitacaoId);
	void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId);
	Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, boolean visualiazarTodasAsSolicitacoes, Date dataEncerramentoIni, Date dataEncerramentoFim);
	Integer getCount(char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, boolean visualiazarTodasAsSolicitacoes, Date dataEncerramentoIni, Date dataEncerramentoFim);
	Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId);
	void updateSuspendeSolicitacao(boolean suspender, String obsSuspensao, Long solicitacaoId);
	void migrarBairro(Long bairroId, Long bairroDestinoId);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds, Long empresaId, boolean considerarContratacaoFutura);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds);
	List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido);
	void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario);
	Collection<Solicitacao> findAllByCandidato(Long candidatoId);
	Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim, char dataStatusAprovacaoSolicitacao);
	Collection<DataGrafico> findQtdContratadosPorFaixa(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	Collection<DataGrafico> findQtdContratadosPorArea(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	Collection<DataGrafico> findQtdContratadosPorMotivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	Collection<Solicitacao> findByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds);
	Collection<String> getNomesColabSubstituidosSolicitacaoEncerrada(Long empresaId);
	void atualizaStatusSolicitacaoByColaborador(Colaborador colaborador, char status, boolean disponibilizarCandidato);
	public double calculaIndicadorVagasPreenchidasNoPrazo(Long empresaId,Long[] estabelecimentosIds, Long[] areasIds, Long[] solicitacoesIds, Date dataDe,  Date dataAte);
}