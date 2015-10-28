package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

public interface HistoricoColaboradorDao extends GenericDao<HistoricoColaborador>
{
	public Collection<HistoricoColaborador> findPromocaoByColaborador(Long colaboradorId);
	public HistoricoColaborador getHistoricoAtual(Long colaboradorId, int tipoBuscaHistoricoColaborador);
	public HistoricoColaborador getHistoricoContratacaoAguardando(Long colaboradorId);
	public Collection<HistoricoColaborador> findByCargosIds(int page, int pagingSize, Collection<Long> name, Long empresaId, Colaborador colaborador);
	public Collection<HistoricoColaborador> findByGrupoOcupacionalIds(int page, int pagingSize, Collection<Long> name, Long empresaId);
	public HistoricoColaborador getHistoricoAnterior(HistoricoColaborador historico);
	public Collection<HistoricoColaborador> findByColaboradorData(Long idColaborador, Date data);
	public HistoricoColaborador getHistoricoProximo(HistoricoColaborador hist);
	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor, Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId, Date dataTabela);
	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId, Integer statusRetornoAC);
	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId);
	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado);
	public void updateSituacaoByMovimentacao(String codigoEmpregado, String movimentacao, String valor, boolean atualizarTodasSituacoes, Long empresaId);
	public HistoricoColaborador findByIdProjectionMinimo(Long historicoColaboradorId);
	public String findColaboradorCodigoAC(Long historicoColaboradorId);
	public HistoricoColaborador findByIdProjection(Long historicoColaboradorId);
	public Long findReajusteByHistoricoColaborador(Long historicoColaboradorId);
	public Collection<HistoricoColaborador> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId);
	Collection<HistoricoColaborador> findPendenciasByHistoricoColaborador(Long empresaId, Integer... statusAc);
	public Collection<HistoricoColaborador> findHistoricoAprovado(Long historicoColaboradorId, Long colaboradorId);
	public HistoricoColaborador findByIdProjectionHistorico(Long historicoColaboradorId);
	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public HistoricoColaborador findAtualByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId);
	public void removeColaborador(Long colaboradorId);
	public HistoricoColaborador findHistoricoAdmissao(Long colaboradorId);
	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data);
	public Collection<HistoricoColaborador> findByData(Long colaboradorId, Date data);
	public Collection<HistoricoColaborador> findByCargoEstabelecimento(Date data, Long[] cargoIds, Long[] estabelecimentoIds, Date dataConsulta, Long[] areaOrganizacionalIds, Date dataAtualizacao, String vinculo, Long... empresasIds);
	public Collection<HistoricoColaborador> findByPeriodo(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao, char agruparPor, boolean imprimirDesligados);
	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId);
	public boolean updateAmbienteEFuncao(HistoricoColaborador historicoColaborador);
	public Collection<HistoricoColaborador> findHistoricoAdmitidos(Long empresaId, Date data);
	public void deleteSituacaoByMovimentoSalarial(Long movimentoSalarialId, Long idEmpresa);
	public Collection<HistoricoColaborador> findImprimirListaFrequencia(Estabelecimento estabelecimento, Date votacaoIni, Date votacaoFim);
	public void setMotivo(Long[] historicoColaboradorIds, String tipo);
	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataIni, Date dataFim, Double percentualDissidio, Long empresaId);
	public void ajustaMotivoContratado(Long colaboradorId);
	public void setaContratadoNoPrimeiroHistorico(Long colaboradorId);
	public Collection<SituacaoColaborador> getPromocoes(Long[] areasIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long... empresasIds);
	public List<RelatorioPromocoes> getRelatorioPromocoes(Long[] areaIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long... empresasIds);
	public List<SituacaoColaborador> getUltimasPromocoes(Long[] areasIds, Long[] estabelecimentosIds, Date dataBase, Long empresaId);
	public void deleteHistoricoColaborador(Long[] colaboradorIds) throws Exception;
	public void removeCandidatoSolicitacao(Long candidatoSolicitacaoId);
	public Collection<HistoricoColaborador> findByAreaGrupoCargo(Long[] empresaIds, Date dataHistorico, Long[] cargoIds, Long[] estabelecimentoIds, Long[] areaIds, Boolean areasAtivas, Long[] grupoOcupacionalIds, String vinculo);
	public void deleteHistoricosAguardandoConfirmacaoByColaborador(Long... colaboradoresIds);
	boolean existeHistoricoPorIndice(Long empresaId);
	public void updateStatusAc(int statusRetornoAC, Long... id);
	public Collection<HistoricoColaborador> findByEmpresaComHistorico(Long empresaId, Integer status, boolean dataSolDesligNotNull);
	public void updateStatusAcByEmpresaAndStatusAtual(int novoStatusAC, int statusACAtual, Long... colaboradoresIds);
	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Date dataSegundoHistoricoIndice, Long indiceId);
	public void updateArea(Long areaIdMae, Long areaId);
}