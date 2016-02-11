package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TAula;

@SuppressWarnings("unchecked")
public interface ColaboradorTurmaManager extends GenericManager<ColaboradorTurma>
{
	public Collection<ColaboradorTurma> filtrarColaboradores(int page, int pagingSize, String[] areasCheck, String[] cargosCheck, String[] estabelecimentosCheck, String[] gruposCheck, String[] colaboradoresCursosCheck, Turma turma, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId) throws ColecaoVaziaException;
	public Collection<ColaboradorTurma> filtroRelatorioMatriz(LinkedHashMap filtro);
	public Collection<Colaborador> getListaColaboradores(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(LinkedHashMap filtro);
	public void saveUpdate(String[] colaboradorTurma, String[] selectPrioridades, boolean validarCertificacao) throws Exception;
	public Collection<Curso> getListaCursos(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorCursoMatriz> montaMatriz(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> findColaboradoresByCursoTurmaIsNull(Long cursoId);
	public void updateTurmaEPrioridade(Long colaboradorTurnaId, Long turmaId, Long prioridadeId);
	public Collection<ColaboradorTurma> findByTurmaCurso(Long cursoId);
	public Collection<SomatorioCursoMatriz> getSomaPontuacao(Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs);
	public Collection<ColaboradorTurma> findByDNTColaboradores(DNT dnt, Collection<Colaborador> colaboradors);
	public boolean verifcaExisteNoCurso(Colaborador colaborador, Curso curso, DNT dnt);
	public Collection<ColaboradorTurma> findByTurma(Long turmaId, Long empresaId, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize, boolean controlaVencimentoPorCertificacao);
	public Collection<ColaboradorTurma> findByTurmaColaborador(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds, Integer page, Integer pagingSize);
	public Collection<Colaborador> montaExibicaoAprovadosReprovados(Long empresaId, Long turmaId);
	public Map<String, Object> getDadosTurma(Collection<ColaboradorTurma> colaboradorTurmas, Map<String, Object> parametros) throws Exception;
	public void saveUpdate(Collection<Long> colaboradoresId, boolean aprovado) throws Exception;
	public Collection<ColaboradorTurma> setCustoRateado(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> setFamiliaAreas(Collection<ColaboradorTurma> colaboradorTurmas, Long empresaId) throws Exception;
	public Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds);
	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Integer qtdMesesSemCurso, String situacaoColaborador, char aprovadoFiltro) throws Exception;
	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, char aprovado, String situacao) throws Exception;
	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId);
	public Collection<ColaboradorTurma> findByTurmaPresenteNoDiaTurmaId(Long turmaId, Long diaTurmaId);
	public Collection<ColaboradorTurma> findRelatorioSemIndicacaoTreinamento(Long empresaId, Long[] areaIds, Long[] estabelecimentoIds, int qtdMeses) throws ColecaoVaziaException;
	public String insereColaboradorTurmas(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Turma turma, DNT dnt, int filtrarPor, String[] selectPrioridades);
	public void remove(ColaboradorTurma colaboradorTurma);
	public Collection<ColaboradorTurma> montaColunas(Collection<ColaboradorTurma> colaboradorTurmas, boolean exibirNomeComercial, boolean exibirCargo, boolean exibirEstabelecimento, boolean exibirAssinatura, boolean exibirArea, boolean exibirCPF);
	public Collection<ColaboradorTurma> findRelatorioHistoricoTreinamentos(Long empresaId, Long[] colaboradoresCheck, Date dataIni, Date dataFim) throws ColecaoVaziaException, Exception;
	public Collection<Certificado> montaCertificados(Collection<Colaborador> colaboradores, Certificado certificado, Long empresaId);
	public Collection<Colaborador> findAprovadosByCertificacao(Certificacao certificacao, int qtdCursos, boolean controlarVencimentoPorCertificacao);
	public Collection<ColaboradorCertificacaoRelatorio> montaRelatorioColaboradorCertificacao(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds, Date dataInicio, Date dataFim, char tipoAgrupamento) throws Exception;
	public void saveColaboradorTurmaNota(Turma turma, Colaborador colaborador, Long[] avaliacaoCursoIds, String[] notas) throws Exception;
	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds) throws Exception;
	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId, Long avaliacaoCursoId, boolean controlaVencimentoPorCertificacao);
	public Double percentualFrequencia(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds);
	public void carregaResultados(Collection<ColaboradorTurma> colaboradorTurmas);
	public HashMap<String, Integer> getResultado(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds);
	public Collection<Colaborador> findAprovadosByTurma(Collection<Long> turmaId);
	public Collection<ColaboradorTurma> findAprovadosByTurma(Long turmaId);
	public Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean realizada);
	public String checaColaboradorInscritoEmOutraTurma(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Long turmaId);
	public Collection<Long> findIdEstabelecimentosByTurma(Long turmaid, Long empresaId);
	public Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes);
	public Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds,	Long[] areaIds, Long[] cursoIds, Long[] turmaIds, boolean considerarSomenteDiasPresente);
	public Collection<Colaborador> findColaboradorByCurso(Long[] cursosIds, Long[] turmasIds);
	public Collection<ColaboradorTurma> filtraAprovadoReprovado(Collection<ColaboradorTurma> colaboradorTurmaIds, char aprovado);
	public TAula[] getTreinamentosPrevistoParaTRU(String colaboradorCodigoAC, Empresa empresa, String periodoIni, String periodoFim);
	public TAula[] getTreinamentosRealizadosParaTRU(String colaboradorCodigoAC, Empresa empresa, String periodoIni, String periodoFim);
	public Collection<ColaboradorTurma> findCursosVencidosAVencer(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds, char filtroAgrupamento, char filtroSituacao, char filtroAprovado);
	public Collection<ColaboradorTurma> findCursosCertificacoesAVencer(Date dataReferencia, Long empresaId);
	public Collection<ColaboradorTurma> findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId);
	public String verificaColaboradorCertificado(Long[] colaboradoresId, Long cursoId);
	public Collection<ColaboradorTurma> findByTurmaId(Long turmaId);
	public boolean verificaAprovacao(Long cursoId, Long turmaId, Long colaboradorTurmaId, Double percentualMinimoFrequencia);
}