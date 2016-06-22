package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.security.spring.aop.callback.HistoricoColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface HistoricoColaboradorManager extends GenericManager<HistoricoColaborador> {

	@Audita(operacao="Inserção", auditor=HistoricoColaboradorAuditorCallbackImpl.class)
	public void insertHistorico(HistoricoColaborador historicoColaborador, Empresa empresaSistema) throws Exception;
	
	@Audita(operacao="Atualização", auditor=HistoricoColaboradorAuditorCallbackImpl.class)
	public void updateHistorico(HistoricoColaborador historicoColaborador, Empresa empresa) throws Exception;
	
	@Audita(operacao="Remoção", auditor=HistoricoColaboradorAuditorCallbackImpl.class)
	public void removeHistoricoAndReajuste(Long historicoColaboradorId, Long colaboradorId, Empresa empresa, boolean removerDoAC) throws Exception;
	
	public void saveHistoricoColaboradorNoAc(Collection<HistoricoColaborador> historicosColaboradores, Empresa empresa) throws Exception;
	
	public Collection<HistoricoColaborador> getByColaboradorId(Long id);

	public HistoricoColaborador findByIdProjection(Long historicoColaboradorId);

	public Collection findPromocaoByColaborador(Long colaboradorId);

	public HistoricoColaborador getHistoricoAtual(Long colaboradorId);
	
	public HistoricoColaborador getHistoricoContratacaoAguardando(Long colaboradorId);

	public Collection<Colaborador> findByGrupoOcupacionalIds(int page, int pagingSize, Long[] longs, Long empresaId);

	public Collection<Colaborador> findByCargosIds(int page, int pagingSize, Long[] longs, Colaborador colaborador, Long empresaId);

	public Collection<HistoricoColaborador> inserirPeriodos(Collection<HistoricoColaborador> historicos);

	public Collection<HistoricoColaborador> findByColaboradorData(Long id, Date data);

	public Collection<HistoricoColaborador> findDistinctAmbienteFuncao(Collection<HistoricoColaborador> historicoColaboradors);

	public Collection<HistoricoColaborador> findDistinctFuncao(Collection<HistoricoColaborador> historicos);

	public HistoricoColaborador getHistoricoAnterior(HistoricoColaborador hist);

	public boolean existeHistoricoData(HistoricoColaborador historicoColaborador);

	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor, Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds,
			Long empresaId, Date date);

	public HistoricoColaborador ajustaTipoSalario(HistoricoColaborador historico, int salarioPropostoPor, Indice indice, Double quantidadeIndice, Double salarioColaborador);

	public String montaTipoSalario(Double quantidadeIndice, int tipoSalario, String indiceNome);

	public Collection<HistoricoColaborador> progressaoColaborador(Long colaboradorId, Long empresaId) throws Exception;

	void montaAreaOrganizacional(Collection<HistoricoColaborador> historicoColaboradors, Long... empresaIds) throws Exception;

	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId, Integer statusRetornoAC);

	public Collection<HistoricoColaborador> findByColaborador(Long colaboradorId, Long empresaId) throws Exception;

	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId);

	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado);

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa);

	public Collection<TSituacao> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId, Empresa empresa);

	public String findColaboradorCodigoAC(Long historicoColaboradorId);

	Collection<PendenciaAC> findPendenciasByHistoricoColaborador(Long empresaId);
	
	Collection<HistoricoColaborador> findPendenciasByHistoricoColaborador(Long empresaId, Integer... statusAC);

	public HistoricoColaborador ajustaAmbienteFuncao(HistoricoColaborador historicoColaborador);

	public boolean existeHistoricoAprovado(Long historicoColaboradorId, Long colaboradorId);

	public HistoricoColaborador getHistoricoAtualOuFuturo(Long colaboradorId);

	public HistoricoColaborador prepareSituacao(TSituacao situacao) throws Exception;

	public HistoricoColaborador updateSituacao(TSituacao situacao) throws Exception;
	
	public void updateSituacaoByMovimentacao(String codigoEmpregado, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, Long empresaId);
	
	public TSituacao bindSituacao(HistoricoColaborador historicoColaborador, String empresaCodigoAC);

	public HistoricoColaborador bindSituacao(TSituacao situacao, HistoricoColaborador historicoColaborador) throws Exception;
	
	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);

	public HistoricoColaborador cancelarSituacao(TSituacao situacao, String mensagem) throws Exception;

	public void removeHistoricoAndReajusteAC(HistoricoColaborador historico) throws Exception;

	public boolean verificaDataPrimeiroHistorico(Colaborador colaborador);

	public boolean verificaPrimeiroHistoricoAdmissao(boolean editarHistorico, HistoricoColaborador historicoColaborador, Colaborador colaborador);

	public void removeColaborador(Long colaboradorId);

	public boolean verifyDataHistoricoAdmissao(Long colaboradorId);

	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data);

	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId);

	public Collection<HistoricoColaborador> relatorioColaboradorCargo(Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, Integer qtdMeses, char opcaoFiltro, String[] areaOrganizacionalCheck, Boolean exibeColabAdmitido, Integer qtdMesesDesatualizacao, String vinculo, boolean exibirSalarioVariavel, Long... empresasIds) throws Exception,ColecaoVaziaException;

	public Collection<HistoricoColaborador> montaRelatorioSituacoes(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao, char agruparPor, boolean imprimirDesligados) throws ColecaoVaziaException, Exception;

	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId);
	
	public void updateAmbientesEFuncoes(Collection<HistoricoColaborador> historicoColaboradors) throws Exception;
	
	//@Audita(operacao="Atualização do Ambiente e Função")
	public boolean updateAmbienteEFuncao(HistoricoColaborador historicoColaborador) throws Exception;

	public Collection<HistoricoColaborador> getHistoricosComAmbienteEFuncao(Long colaboradorId);

	public Double getValorTotalFolha(Long empresaId, Date data);

	public void deleteSituacaoByMovimentoSalarial(Long movimentoSalarialId, Long empresaId);

	public Collection<HistoricoColaborador> findImprimirListaFrequencia(Estabelecimento estabelecimento, Date votacaoIni, Date votacaoFim);

	public void setMotivo(Long[] historicoColaboradorIds, String tipoMotivo);

	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataIni, Date dataFim, Double percentualDissidio, Long empresaId, String[] cargosIds, String[] areasIds, String[] estabelecimentosIds);

	public void ajustaMotivoContratado(Long colaboradorId);

	public List<RelatorioPromocoes> getPromocoes(Long[] arrayStringToArrayLong, Long[] arrayStringToArrayLong2, Date dataIni, Date dataFim, Long... empresasIds);
	
	public List<SituacaoColaborador> getColaboradoresSemReajuste(Long[] areasIds, Long[] estabelecimentosIds, Date data, Long empresaId, int mesesSemReajuste);
	
	public Map<Character, Collection<Object[]>> montaPromocoesHorizontalEVertical(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds);
	
	public Map<Character, Collection<DataGrafico>>  montaPromocoesHorizontalEVerticalPorArea(Date dataIni, Date dataFim, Long empresaId, boolean somenteAreasFilhas, Long... areasIds);

	public void deleteHistoricoColaborador(Long[] colaboradorIds) throws Exception;
	
	public void removeCandidatoSolicitacao(Long candidatoSolicitacaoId);

	public Collection<HistoricoColaborador> relatorioColaboradorGrupoOcupacional(Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, String[] areaOrganizacionalsCheck, Boolean areasAtivas, String[] gruposCheck, String vinculo, Long... empresaIds) throws Exception;
	
	public Collection<HistoricoColaborador> filtraHistoricoColaboradorParaPPP(Collection<HistoricoColaborador> todosHistoricos) throws Exception;

	public void deleteHistoricosAguardandoConfirmacaoByColaborador(Long... colaboradoresIds);

	public boolean existeHistoricoPorIndice(Long empresaId);

	public void updateStatusAc(int statusRetornoAC, Long... id);

	public Collection<HistoricoColaborador> findByEmpresaComHistorico(Long empresaId, Integer status);
	
	public void updateStatusAcByEmpresaAndStatusAtual(int novoStatusAC, int statusACAtual, Long... colaboradoresIds);

	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Long indiceId);

	public void reenviaAguardandoConfirmacao(Empresa empresa) throws Exception;
	
	public void updateArea(Long areaIdMae, Long areaId) ;

	public boolean existeHistoricoComFuncao(Long colaboradorId, Date data);
}