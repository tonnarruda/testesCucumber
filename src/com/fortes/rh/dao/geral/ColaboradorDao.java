package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.relatorio.DataGrafico;

public interface ColaboradorDao extends GenericDao<Colaborador>
{
	public Collection<Colaborador> findByAreaOrganizacionalIds(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentosIds, Integer page, Integer pagingSize, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean somenteHistoricoAtualEPassado);
	public Collection<Colaborador> findByAreaOrganizacionalIds(Integer page, Integer pagingSize, Long[] ids, Long[] estabelecimentosIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean somenteHistoricoAtualEPassado);
	public Collection<Colaborador> findByAreaOrganizacionalIds(Long[] ids);
	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro);

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario);
	public Integer getCount(Map parametros, int tipoBuscaHistorico);
	public Collection findList(int page, int pagingSize, Map parametros, int tipoBuscaHistorico);
	public Colaborador findColaboradorPesquisa(Long id,Long empresaId);
	public Colaborador findByUsuario(Usuario usuario,Long empresaId);
	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId);
	public Collection<Colaborador> findbyCandidato(Long candidatoId,Long empresaId);
	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId);
	public boolean setCodigoColaboradorAC(String codigo, Long id);
	public Colaborador findByCodigoAC(String codigo, Empresa empresa);
	public Colaborador findColaboradorById(Long id);
	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId);
	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId);
	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds, String colaboradorNome, Long empresaId);
	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds);
	public Colaborador findByIdProjectionUsuario(Long colaboradorId);
	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentoIds, Collection<Long> areaOrganizacionalIds, Collection<Long> cargoIds, CamposExtras camposExtras, Long empresaId, String order, Date dataAdmissaoIni, Date dataAdmissaoFim, String sexo, String deficiencia, Integer[] tempoServicoIni, Integer[] tempoServicoFim, String situacao);
	public Colaborador findColaboradorByIdProjection(Long colaboradorId);
	public boolean atualizarUsuario(Long colaboradorId, Long usuarioId);
	public Colaborador findByIdProjectionEmpresa(Long colaboradorId);
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor);
	public List<Object[]> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim);

	public boolean desligaByCodigo(String codigoac, Empresa empresa, Date data);
	public void desligaColaborador(Boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Long colaboradorId);
	public void religaColaborador(Long colaboradorId);
	public Collection<Colaborador> findProjecaoSalarialByHistoricoColaborador(Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);
	public Collection<Colaborador> findProjecaoSalarialByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);

	void setRespondeuEntrevista(Long colaboradorId);
	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula);
	public boolean setMatriculaColaborador(Long colaboradorId, String matricula);
	public Colaborador findByIdComHistorico(Long colaboradorId, Integer statusRetornoAC);
	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor);
	public Collection<Colaborador> findAllSelect(String situacao, Long... empresaIds);
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado);
	public void updateInfoPessoais(Colaborador colaborador);
	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public Long findByUsuario(Long usuarioId);
	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC);
	public Integer getCountAtivos(Date dataIni, Date dataFim, Long[] empresaIds);
	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds);
	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos);
	public Colaborador findByIdHistoricoProjection(Long id);
	public Colaborador findByIdDadosBasicos(Long id, Integer statusRetornoAC);
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds);
	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos);
	public Integer countSemMotivos(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim);
	public void migrarBairro(String bairro, String bairroDestino);
	public Integer getCountAtivosByEstabelecimento(Long estabelecimentoId);
	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaIds, Collection<Long> estabelecimentoIds, String situacao);
	public Collection<String> findEmailsDeColaboradoresByPerfis(Long[] perfilIds, Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa, Long periodoExperienciaId);
	public Collection<Colaborador> findAdmitidos(Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos);
	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long id, Long colaboradorLogadoId);
	public Collection<Colaborador> findByCpf(String cpf, Long empresaId);
	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida);
	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId, String nomeComercial);
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados);
	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds);
	public Collection<Object> findComHistoricoFuturoSQL(Map parametros, Integer pagingSize, Integer page);
	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId, Long colaboradorId);
	public Collection<Colaborador> findColaboradoresEleicao(Long empresaId, Long estabelecimentosIds, Date data);
	public Collection<Colaborador> findAdmitidosNoPeriodo(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa);
	public Collection<Colaborador> findColabPeriodoExperiencia(Long empresaId, Date periodoIni, Date periodoFim, Long[] avaliacoesCheck, Long[] areasCheck, Long[] estabelecimentosCheck, Long[] colaboradorsCheck, boolean considerarAutoAvaliacao);
	public void setCandidatoNull(Long idCandidato);
	public Colaborador findByUsuarioProjection(Long usuarioId);
	public String[] findEmailsByPapel(Collection<Long> usuarioEmpresaIds);
	public Collection<Colaborador> findComAvaliacoesExperiencias(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck);
	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaId, Long[] areasIds);
	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] areasIds);
	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Empresa empresa, Long[] areasIds, boolean isAdmitidos);
//	public Integer countDemitidos(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds);	
	public Collection<TurnOver> countAdmitidosDemitidosPeriodoTurnover(Date dataIni, Date dataFim, Empresa empresa, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, boolean isAdmitidos);
	public Integer countAtivosPeriodo(Date dataIni, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> ocorrenciasIds, boolean consideraDataAdmissao, Long colaboradorId, boolean isAbsenteismo);
	public Integer countAtivosTurnover(Date dataIni, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, boolean consideraDataAdmissao);
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId);
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
	public String findCodigoACDuplicado(Long empresaId);
	public Collection<Colaborador> findParentesByNome(String nome, Long empresaId);
	public void atualizaDataSolicitacaoDesligamentoAc(Date dataSolicitacaoDesligamento, Long colaboradorId);
	public void removerMotivoDemissaoColaborador(Long colaboradorId);
	public Collection<Colaborador> findPendenciasSolicitacaoDesligamentoAC(Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDiasSemEpi(Collection<Integer> dias, Long empresaId);
	public Collection<Colaborador> findAguardandoEntregaEpi(Collection<Integer> diasLembrete, Long empresaId);
	public Collection<Colaborador> triar(Long empresaId, String escolaridade, String sexo, Date dataNascIni, Date dataNascFim, Long[] cargosIds, Long[] areasIds, Long[] competenciasIds, boolean exibeCompatibilidade);
	public Collection<Colaborador> findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
	public Collection<Colaborador> findParaLembreteTerminoContratoTemporario(Collection<Integer> diasLembretes, Long empresaId);
	public void auditaCancelarContratacaoNoAC(Colaborador colaborador, String mensagem);
}