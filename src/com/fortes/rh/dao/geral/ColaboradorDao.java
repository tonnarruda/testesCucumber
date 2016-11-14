package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorJsonVO;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.json.ColaboradorJson;
import com.fortes.rh.model.relatorio.DataGrafico;

public interface ColaboradorDao extends GenericDao<Colaborador>
{
	public Collection<Colaborador> findByAreaOrganizacionalIds(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentosIds, Collection<Long> cargosIds, Integer page, Integer pagingSize, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean somenteHistoricoAtualEPassado, boolean somenteDesligados, Long notUsuarioId);
	public Collection<Colaborador> findByAreaOrganizacionalIds(Integer page, Integer pagingSize, Long[] areasIds, Long[] cargosIds, Long[] estabelecimentosIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean somenteHistoricoAtualEPassado, boolean somenteDesligados, Long notUsuarioId);
	public Collection<Colaborador> findByAreaOrganizacionalIds(Long[] ids);
	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro);

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario);
	public Collection<Colaborador> findComAnoDeEmpresa(Long empresaId, Date data);
	public Integer getCount(Map parametros, int tipoBuscaHistorico);
	public Collection findList(int page, int pagingSize, Map parametros, int tipoBuscaHistorico);
	public Colaborador findColaboradorPesquisa(Long id,Long empresaId);
	public Colaborador findByUsuario(Usuario usuario,Long empresaId);
	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId);
	public Colaborador findByCandidato(Long candidatoId,Long empresaId);
	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId);
	public boolean setCodigoColaboradorAC(String codigo, Long id);
	public Colaborador findByCodigoAC(String codigoAC, Empresa empresa);
	public Colaborador findColaboradorById(Long id);
	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId);
	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId);
	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId);
	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds);
	public Colaborador findByIdProjectionUsuario(Long colaboradorId);
	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentoIds, Collection<Long> areaOrganizacionalIds, Collection<Long> cargoIds, CamposExtras camposExtras, String order, Date dataAdmissaoIni, Date dataAdmissaoFim, String sexo, String deficiencia, Integer[] tempoServicoIni, Integer[] tempoServicoFim, String situacao, Character enviadoParaAC, Long... empresasIds);
	public Colaborador findColaboradorByIdProjection(Long colaboradorId);
	public boolean atualizarUsuario(Long colaboradorId, Long usuarioId);
	public Colaborador findByIdProjectionEmpresa(Long colaboradorId);
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor, String vinculo);
	public List<Object[]> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String vinculo);

	public boolean desligaByCodigo(Empresa empresa, Date data, String... codigosAC);
	public void desligaColaborador(Boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Character gerouSubstituicao, Long... colaboradoresIds);
	public void religaColaborador(Long colaboradorId);
	public Collection<Colaborador> findProjecaoSalarialByHistoricoColaborador(Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);
	public Collection<Colaborador> findProjecaoSalarialByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);

	void setRespondeuEntrevista(Long colaboradorId);
	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula);
	public boolean setMatriculaColaborador(Long colaboradorId, String matricula);
	public Colaborador findByIdComHistorico(Long colaboradorId, Integer statusRetornoAC);
	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor);
	public Collection<Colaborador> findAllSelect(String situacao, Long notUsuarioId, Long... empresaIds);
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado);
	public Collection<Colaborador> findComNotaDoCurso(Collection<Long> colaboradorIds, Long turmaId);
	public void updateInfoPessoais(Colaborador colaborador);
	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public Long findByUsuario(Long usuarioId);
	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC);
	public Integer getCountAtivosQualquerStatus(Date dataBase, Long[] empresaIds, Long[] areasIds, Long[] estabelecimentosIds);
	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds);
	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Boolean somenteAtivos, String[] colabsNaoHomonimoHa, Integer statusHistoricoColaborador, Long[] empresaIds);
	public Colaborador findByIdHistoricoProjection(Long id);
	public Colaborador findByIdDadosBasicos(Long id, Integer statusRetornoAC);
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds);
	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos);
	public Colaborador findColaboradorByDataHistorico(Long colaboradorId, Date dataHistorico);
	public Integer countSemMotivos(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String vinculo);
	public void migrarBairro(String bairro, String bairroDestino);
	public Integer getCountAtivosByEstabelecimento(Long estabelecimentoId);
	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaIds, Collection<Long> estabelecimentoIds, String situacao, Long notUsuarioId, boolean consideraSoIntegradosComAC);
	public Collection<String> findEmailsDeColaboradoresByPerfis(Long[] perfilIds, Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa, Long periodoExperienciaId);
	public Collection<Colaborador> findAdmitidos(String vinculo, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos);
	public Collection<Colaborador> findByNomeCpfMatriculaComHistoricoComfirmado(Colaborador colaborador, Long id, Long[] areasIds);
	public Collection<Colaborador> findByCpf(String cpf, Long empresaId, Long colaboradorId, Boolean desligado);
	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida);
	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId, String nomeComercial);
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados, Long empresaId, Long[] areaId, Long[] cargoId);
	public Integer qtdTotalDiasDaTurmaVezesColaboradoresInscritos(Date dataPrevIni, Date dataPrevFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds);
	public Collection<Object> findComHistoricoFuturoSQL(Map parametros, Integer pagingSize, Integer page, Long usuarioLogadoId);
	public Collection<Colaborador> findColaboradoresEleicao(Long empresaId, Long estabelecimentosIds, Date data);
	public Collection<Colaborador> findAdmitidosNoPeriodo(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa);
	public Collection<Colaborador> findColabPeriodoExperiencia(Date periodoIni, Date periodoFim, Long[] avaliacoesCheck, Long[] areasCheck, Long[] estabelecimentosCheck, Long[] colaboradorsCheck, boolean considerarAutoAvaliacao, boolean agruparPorArea, Long... empresasIds);
	public void setCandidatoNull(Long idCandidato);
	public Colaborador findByUsuarioProjection(Long usuarioId, Boolean ativo);
	public String[] findEmailsByPapel(Collection<Long> usuarioEmpresaIds);
	public Collection<Colaborador> findComAvaliacoesExperiencias(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck);
	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens);
	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos);
	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens);
	public Collection<Ocorrencia> getOcorrenciasByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens);
	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens);
	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds);
	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Empresa empresa, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, boolean isAdmitidos);
	public Collection<TurnOver> countAdmitidosDemitidosPeriodoTurnover(Date dataIni, Date dataFim, Empresa empresa, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, boolean isAdmitidos);
	public Integer countAtivosPeriodo(Date dataIni, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, Collection<Long> ocorrenciasIds, boolean consideraDataAdmissao, Long colaboradorId, boolean isAbsenteismo);
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId);
	public Collection<ColaboradorJsonVO> getColaboradoresJsonVO(Long[] areaOrganizacionalIds);
	public Collection<Colaborador> findByAvaliacoes(Long... avaliacaoIds);
	public Collection<Colaborador> findAniversariantesByEmpresa(Long empresaId, int dia, int mes);
	public Collection<Colaborador> findByEstabelecimentoDataAdmissao(Long estabelecimentoId, Date dataAdmissao, Long empresaId);
	public Collection<Colaborador> findColaboradoresByIds(Long[] colaboradoresIds);
	public int findQtdVagasPreenchidas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	public Collection<Colaborador> findSemCodigoAC(Long empresaId);
	public Collection<Colaborador> findByQuestionarioNaoRespondido(Long questionarioId);
	public int qtdDemitidosEm90Dias(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte);
	public int qtdAdmitidosPeriodoEm90Dias(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte);
	public Colaborador findFuncaoAmbiente(Long colaboradorId);
	public Collection<Colaborador> findParentesByNome(Long colaboradorId, String nome, Long empresaId);
	public void atualizaSolicitacaoDesligamento(Date dataSolicitacaoDesligamento, Date dataSolicitacaoDesligamentoAC, String observacaoDemissao, Long motivoId, Character gerouSubstituicao, Long solicitanteDemissaoId, Long colaboradorId);
	public Collection<Colaborador> findPendenciasSolicitacaoDesligamentoAC(Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDiasSemEpi(Collection<Integer> dias, Long empresaId);
	public Collection<Colaborador> findAguardandoEntregaEpi(Collection<Integer> diasLembrete, Long empresaId);
	public Collection<Colaborador> triar(Long[] empresaIds, String escolaridade, String sexo, Date dataNascIni, Date dataNascFim, String[] faixasCheck, Long[] areasIds, Long[] competenciasIds, boolean exibeCompatibilidade, boolean opcaoTodasEmpresas);
	public Collection<Colaborador> findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
	public Collection<Colaborador> findParaLembreteTerminoContratoTemporario(Collection<Integer> diasLembretes, Long empresaId);
	public Collection<Colaborador> findHabilitacaAVencer(Collection<Integer> diasLembrete, Long empresaId);
	public Collection<TurnOver> countDemitidosTempoServico(Empresa empresa, Date dataIni, Date dataFim, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos);
	public Integer countColaboradoresPorTempoServico(Empresa empresa, Integer tempoServicoIniEmMeses, Integer tempoServicoFimEmMeses, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos);
	public Collection<Colaborador> findDemitidosTurnover(Empresa empresa, Date dataIni, Date dataFim, Integer[] tempoServicoIni, Integer[] tempoServicoFim, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos);
	public Collection<Colaborador> findByEmpresaAndStatusAC(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, int statusAC, boolean semCodigoAc, boolean comNaoIntegraAC, String situacaoColaborador, boolean primeiroHistorico, String... order);
	public void desvinculaCandidato(Long candidatoId);
	public Collection<Colaborador> findAguardandoDesligamento(Long empresaId, Long[] areasIdsPorResponsavel, Long colaboradorId);
	public void removeComDependencias(Long id);
	public Collection<Usuario> findUsuarioByAreaEstabelecimento(Long[] areasIds, Long[] estabelecimentosIds);
	public Collection<Colaborador> findColaboradoresByCodigoAC(Long empresaId,	boolean joinComHistorico, String... codigosACColaboradores);
	public int countColaboradoresComHistoricos(Long empresaId);
	public void setSolicitacao(Long colaboradorId, Long solicitacaoId);
	public Colaborador findColaboradorComTodosOsDados(Long id);
	public Collection<Colaborador> findByEstadosCelularOitoDigitos(Long[] ufId);
	public void setDataSolicitacaoDesligamentoACByDataDesligamento(Long empresaId);
	public Collection<Colaborador> listColaboradorComDataSolDesligamentoAC(Long empresaId);
	public Integer countDemitidosPeriodo(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, boolean reducaoDeQuadro);
	public boolean existeColaboradorAtivo(String cpf, Date data);
	public Collection<Colaborador> findColaboradorComESemOrdemDeServico(Colaborador colaborador, HistoricoColaborador historicoColaborador, Long[] areaIds, String situacao, String filtroOrdemDeServico, int page, int pagingSize);
	public Colaborador findComDadosBasicosParaOrdemDeServico( Long colaboradorId, Date dataOrdemDeServico);
	public void updateRespondeuEntrevistaDesligamento(Long colaboradorId, boolean respondeuEntrevistaDesligamento);
	public Collection<ColaboradorJson> getColaboradoresJson(String baseCnpj, Long colaboradorId);
}