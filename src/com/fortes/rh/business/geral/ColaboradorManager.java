package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DynaRecord;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.relatorio.CartaoAcompanhamentoExperienciaVO;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.security.spring.aop.callback.ColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;
import com.fortes.web.tags.CheckBox;

@Modulo("Colaborador")
public interface ColaboradorManager extends GenericManager<Colaborador>
{
	@Audita(operacao="Inserção", auditor=ColaboradorAuditorCallbackImpl.class)
	public boolean insert(Colaborador colaborador, Double salarioColaborador, Long idCandidato, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Solicitacao solicitacao, Empresa empresa) throws Exception;
	@Audita(operacao="Atualização", auditor=ColaboradorAuditorCallbackImpl.class)
	public void update(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Empresa empresa, boolean editarHistorico, Double salarioColaborador) throws Exception;
	@Audita(operacao="Remoção", auditor=ColaboradorAuditorCallbackImpl.class)
	public void remove(Colaborador colaborador, Empresa empresa) throws Exception;
	@Audita(operacao="Desligamento", auditor=ColaboradorAuditorCallbackImpl.class)
	public void desligaColaborador( Boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Long colaboradorId, boolean desligaByAC) throws Exception;
	@Audita(operacao="Religamento", auditor=ColaboradorAuditorCallbackImpl.class)
	public void religaColaborador(Long colaboradorId) throws Exception;
	//TODO Auditoria não ta passando
	@Audita(operacao="Desligamento no AC", auditor=ColaboradorAuditorCallbackImpl.class)
	public boolean desligaColaboradorAC(String codigoAC, Empresa empresa, Date dataDesligamento);
	@Audita(operacao="Solicitação Deslig", auditor=ColaboradorAuditorCallbackImpl.class)
	public void solicitacaoDesligamentoAc(Date dataSolicitacaoDesligamento, String observacaoDemissao, Long motivoId, Long colaboradorId, Empresa empresa) throws Exception;

	public void cancelarContratacaoNoAC(Colaborador colaborador, HistoricoColaborador historicoColaborador, String mensagem) throws Exception;
	public Collection<Colaborador> findByAreasOrganizacionalIds(Long[] idsLong);
	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario);
	public Integer getCount(Map parametros);
	public Collection findList(int page, int pagingSize, Map parametros);
	public Collection<Colaborador> findByAreasOrganizacionalIds(Integer page, Integer pagingSize, Long[] longs, Long[] estabelecimentosIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean somenteHistoricoAtualEPassado);
	public Colaborador findColaboradorPesquisa(Long id,Long empresaId);
	public void saveDetalhes(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias);
	public Long religaColaboradorAC(String codigoAC, String empCodigo, String grupoAC);
	public Colaborador findColaboradorUsuarioByCpf(String cpf,Long empresaId);
	public void enviaEmailEsqueciMinhaSenha(Colaborador colaborador, Empresa empresa);
	public String recuperaSenha(String cpf,Empresa empresa);
	public Collection<Colaborador> findBycandidato(Long candidatoId,Long empresaId);
	public boolean candidatoEhColaborador(Long id,Long empresaId);
	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro);
	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId);
	public boolean setCodigoColaboradorAC(String codigo, Long id);
	public Colaborador findByCodigoAC(String codigo, Empresa empresa);
	public Colaborador findColaboradorById(Long id);
	public Colaborador findByUsuario(Usuario usuario,Long empresaId);
	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId);
	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds);
	public Collection<Colaborador> getColaboradoresIntegraAc(Collection<Colaborador> colaboradores);
	public Colaborador findByIdProjectionUsuario(Long colaboradorId);
	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, CamposExtras camposExtras, Long empresaId, String order, Date dataAdmissaoIni, Date dataAdmissaoFim, String sexo, String deficiencia, Integer[] tempoServicoIni, Integer[] tempoServicoFim);
	public Colaborador findColaboradorByIdProjection(Long colaboradorId);
	void atualizarUsuario(Long colaboradorId, Long usuarioId) throws Exception;
	public Colaborador findByIdProjectionEmpresa(Long colaboradorId);
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor)throws Exception;
	public Collection<MotivoDemissaoQuantidade> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)throws Exception;
	public Collection<Colaborador> getColaboradoresByEstabelecimentoAreaGrupo(char filtrarPor, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, String colaboradorNome, Long empresaId);
	public Collection<Colaborador> findProjecaoSalarial(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId) throws Exception;

	Collection<Colaborador> ordenaPorEstabelecimentoArea(Long empresaId, Collection<Colaborador> colaboradors) throws Exception;
	public void verificaColaboradoresSemCodigoAC(Collection<ReajusteColaborador> reajustes) throws Exception;
	void verificaColaboradoresDesligados(Collection<ReajusteColaborador> reajustes) throws Exception;

	void respondeuEntrevista(Long colaboradorId);
	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula);
	public boolean setMatriculaColaborador(Long colaboradorId, String matricula);
	public Collection<Colaborador> findListComHistoricoFuturo(int page, int pagingSize, Map parametros);
	public Integer getCountComHistoricoFuturo(Map parametros);
	public Colaborador findByIdComHistoricoConfirmados(Long colaboradorId);
	public Colaborador findByIdComHistorico(Long colaboradorId);
	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor);
	public Collection<Colaborador> findAllSelect(String situacao, Long... empresaIds);
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado);
	
	public void updateInfoPessoais(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Empresa empresa) throws Exception;
	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId);

	public Colaborador updateEmpregado(TEmpregado empregado) throws Exception;
	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public Long findByUsuario(Long usuarioId);
	public Integer getCountAtivos(Date dataIni, Date dataFim, Long[] empresaIds);
	public File getFoto(Long id) throws Exception;
	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds) throws Exception;
	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos);
	public String getNome(Long id);
	public Colaborador findByIdHistoricoProjection(Long id);
	public Collection<CheckBox> populaCheckBox(Long empresaId);
	public Colaborador findByIdDadosBasicos(Long id, Integer statusRetornoAC);
	public Collection<Colaborador> findByAreaOrganizacionalIdsNome(Collection<Long> areasIds, Colaborador colaborador);
	
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds);
	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos);
	
	public void migrarBairro(String bairro, String bairroDestino);
	public Integer getCountAtivosEstabelecimento(Long estabelecimentoId);
	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds, String situacao);
	public void validaQtdCadastros() throws Exception;
	public Collection<String> findEmailsDeColaboradoresByPerfis(Collection<Perfil> perfis, Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa, Long periodoExperienciaId) throws Exception;
	public Collection<Colaborador> findAdmitidos(Long[] empresaIds, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos) throws ColecaoVaziaException, Exception;
	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long empresaId, Long colaboradorLogadoId);
	public Long verificaColaboradorLogadoVerAreas();
	public Collection<Colaborador> setFamiliaAreas(Collection<Colaborador> colaboradores, Long... empresaId) throws Exception;
	public Collection<Colaborador> findByCpf(String cpf, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida);
	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId, String nomeComercial);
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados);
	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds);
	public Integer getCountComHistoricoFuturoSQL(Map parametros);
	public Collection<Colaborador> findComHistoricoFuturoSQL(int page, int pagingSize, Map parametros) throws Exception;
	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId, Long colaboradorId);
	public Collection<Colaborador> getAvaliacoesExperienciaPendentes(Date periodoIni, Date periodoFim, Empresa empresaSistema, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa, Integer diasDeAcompanhamento, Collection<PeriodoExperiencia> periodoExperiencias) throws Exception;
	public List<AcompanhamentoExperienciaColaborador> getAvaliacoesExperienciaPendentesPeriodo(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Collection<PeriodoExperiencia> periodoExperiencias) throws Exception;
	public Collection<Colaborador> findColabPeriodoExperiencia(Long empresaId, Date periodoIni, Date periodoFim, String[] avaliacaoCheck, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck) throws Exception;
	public Collection<DynaRecord> preparaRelatorioDinamico(Collection<Colaborador> colaboradores, Collection<String> colunasMarcadas, Integer[] tempoServicoIni, Integer[] tempoServicoFim);
	public Colaborador findByUsuarioProjection(Long usuarioId);
	public String[] findEmailsByUsuarios(Collection<Long> usuarioEmpresaIds);
	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] areasIds);
	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens);
	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] areasIds);
	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, boolean isAdmitidos);
	public Collection<TurnOver> montaTurnOver(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, int filtrarPor) throws Exception;
	public Collection<DataGrafico> montaSalarioPorArea(Date dataBase, Long empresaId, AreaOrganizacional area);
	public Collection<Object[]> montaGraficoEvolucaoFolha(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds) throws Exception;
	public int countAtivosPeriodo(Date dataIni, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> ocorrenciasId, boolean considerarDataAdmissao, Long colaboradorId, boolean isAbsenteismo);
	public Collection<Object[]> montaGraficoTurnover(Collection<TurnOver> turnOvers);
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId);
	public Collection<Colaborador> findColabPeriodoExperienciaAgrupadoPorModelo(Long id, Date periodoIni, Date periodoFim, Long avaliacaoId, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck, boolean considerarAutoAvaliacao) throws Exception;
	public Collection<Colaborador> findByAvaliacoes(Long... avaliacaoIds);
	public Colaborador removeColaboradorDependencias(Colaborador colaborador);
	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC);
	public void enviaEmailAniversariantes(Collection<Empresa> empresas) throws Exception;
	public Collection<Colaborador> findByEstabelecimentoDataAdmissao(Long estabelecimentoId, Date dataAdmissao, Long empresaId);
	public Collection<CartaoAcompanhamentoExperienciaVO> montaCartoesPeriodoExperiencia(Long[] colaboradoresIds, Long[] dias, String observacao) throws Exception;
	public int findQtdVagasPreenchidas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	public Collection<Colaborador> findSemCodigoAC(Long empresaId);
	public Collection<Colaborador> findByQuestionarioNaoRespondido(Long questionarioId);
	public double calculaIndiceProcessoSeletivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte);
	public void deleteColaborador(Long[] colaboradorIds) throws Exception;
	public Colaborador findFuncaoAmbiente(Long colaboradorId);
	public String findCodigoACDuplicado(Long empresaId);
	public void setCandidatoNull(Long candidatoId);
	public Collection<Colaborador> findParentesByNome(String nome, Long empresaId);
	public Collection<Object> montaParentesByNome(Collection<Colaborador> colaboradores);
	public String avisoQtdCadastros() throws Exception;
	public Collection<PendenciaAC> findPendencias(Long empresaId);
	public void cancelarSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC) throws Exception;
	public String getVinculo(String admissaoTipo, Integer admissaoVinculo, Integer admissaoCategoria);
	public Collection<Colaborador> findAdmitidosHaDiasSemEpi(Collection<Integer> dias, Long empresaId);
	public Collection<Colaborador> findAguardandoEntregaEpi(Collection<Integer> diasLembrete, Long empresaId);
	public boolean pertenceEmpresa(Long colaboradorId, Long empresaId);
	public Collection<Colaborador> triar(Long solicitacaoId, Long empresaId, String escolaridade, String sexo, String idadeMin, String idadeMax, String[] cargosCheck, String[] areasCheck, boolean exibeCompatibilidade, Integer percentualMinimo) throws Exception;
	public void insertColaboradoresSolicitacao(Long[] colaboradoresIds, Solicitacao solicitacao, char statusCandidatoSolicitacao) throws Exception;
	public Collection<Colaborador> ordenaByMediaPerformance(Collection<Colaborador> colaboradores);
	public Collection<Colaborador> insereGrupoPorTempoServico(Collection<Colaborador> colaboradores, Integer[] tempoServicoIni, Integer[] tempoServicoFim);
	public Collection<Colaborador> findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
	public Collection<Colaborador> findParaLembreteTerminoContratoTemporario(Collection<Integer> diasLembretes, Long empresaId);
}
