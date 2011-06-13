package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

public interface HistoricoColaboradorDao extends GenericDao<HistoricoColaborador>
{
	public Collection<HistoricoColaborador> findPromocaoByColaborador(Long colaboradorId);
	public HistoricoColaborador getHistoricoAtual(Long colaboradorId, int tipoBuscaHistoricoColaborador);
	public Collection<HistoricoColaborador> findByCargosIds(int page, int pagingSize, Collection<Long> name, Long empresaId, Colaborador colaborador);
	public Collection<HistoricoColaborador> findByGrupoOcupacionalIds(int page, int pagingSize, Collection<Long> name, Long empresaId);
	public HistoricoColaborador getHistoricoAnterior(HistoricoColaborador historico);
	public Collection<HistoricoColaborador> findByColaboradorData(Long idColaborador, Date data);
	public HistoricoColaborador getHistoricoProximo(HistoricoColaborador hist);
	public void atualizarHistoricoAnterior(HistoricoColaborador historicoAnterior);
	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor, Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId, Date dataTabela);
	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId);
	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId);
	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado);
	public HistoricoColaborador findByIdProjectionMinimo(Long historicoColaboradorId);
	public String findColaboradorCodigoAC(Long historicoColaboradorId);
	public HistoricoColaborador findByIdProjection(Long historicoColaboradorId);
	public Long findReajusteByHistoricoColaborador(Long historicoColaboradorId);
	public Collection<HistoricoColaborador> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId);
	public void updateHistoricoAnterior(Long historicoColaboradorId);
	Collection<HistoricoColaborador> findPendenciasByHistoricoColaborador(Long empresaId);
	public Collection<HistoricoColaborador> findHistoricoAprovado(Long historicoColaboradorId, Long colaboradorId);
	public HistoricoColaborador findByIdProjectionHistorico(Long historicoColaboradorId);
	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public HistoricoColaborador findAtualByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId);
	public void removeColaborador(Long colaboradorId);
	public HistoricoColaborador findHistoricoAdmissao(Long colaboradorId);
	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data);
	public Collection<HistoricoColaborador> findByData(Long colaboradorId, Date data);
	public Collection<HistoricoColaborador> findByCargoEstabelecimento(Date data, Long[] cargoIds, Long[] estabelecimentoIds, Date dataConsulta, Long[] areaOrganizacionalIds, Date dataAtualizacao, Long empresaId);
	public Collection<HistoricoColaborador> findByPeriodo(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao);
	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId);
	public boolean updateAmbienteEFuncao(HistoricoColaborador historicoColaborador);
	public Collection<HistoricoColaborador> findHistoricoAdmitidos(Long empresaId, Date data);
	public void deleteSituacaoByMovimentoSalarial(Long movimentoSalarialId, Long idEmpresa);
	public Collection<HistoricoColaborador> findImprimirListaFrequencia(Estabelecimento estabelecimento, Date votacaoIni, Date votacaoFim);
	public void setMotivo(Long[] historicoColaboradorIds, String tipo);
	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataBase, Double percentualDissidio, Long empresaId);
	public void ajustaMotivoContratado(Long colaboradorId);
	public void setaContratadoNoPrimeiroHistorico(Long colaboradorId);
}