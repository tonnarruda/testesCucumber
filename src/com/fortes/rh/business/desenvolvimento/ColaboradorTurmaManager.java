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

@SuppressWarnings("unchecked")
public interface ColaboradorTurmaManager extends GenericManager<ColaboradorTurma>
{
	public Collection<ColaboradorTurma> filtrarColaboradores(int page, int pagingSize, String[] areasCheck, String[] cargosCheck, String[] estabelecimentosCheck, String[] gruposCheck, String[] colaboradoresCursosCheck, Turma turma, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId) throws ColecaoVaziaException;
	public Collection<ColaboradorTurma> filtroRelatorioMatriz(LinkedHashMap filtro);
	public Collection<Colaborador> getListaColaboradores(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(LinkedHashMap filtro);
	public void saveUpdate(String[] colaboradorTurma, String[] selectPrioridades) throws Exception;
	public Collection<Curso> getListaCursos(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorCursoMatriz> montaMatriz(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> findColaboradoresByCursoTurmaIsNull(Long cursoId);
	public void updateTurmaEPrioridade(Long colaboradorTurnaId, Long turmaId, Long prioridadeId);
	public Collection<ColaboradorTurma> findByTurmaCurso(Long cursoId);
	public Collection<SomatorioCursoMatriz> getSomaPontuacao(Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs);
	public Collection<ColaboradorTurma> findByDNTColaboradores(DNT dnt, Collection<Colaborador> colaboradors);
	public boolean verifcaExisteNoCurso(Colaborador colaborador, Curso curso, DNT dnt);
	public Collection<ColaboradorTurma> findByTurma(Long turmaId, Long empresaId, Integer page, Integer pagingSize);
	public Collection<ColaboradorTurma> findByTurmaColaborador(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Integer page, Integer pagingSize);
	public Collection<Colaborador> montaExibicaoAprovadosReprovados(Long empresaId, Long turmaId);
	public Map<String, Object> getDadosTurma(Collection<ColaboradorTurma> colaboradorTurmas, Map<String, Object> parametros) throws Exception;
	public void saveUpdate(Collection<Long> colaboradoresId, boolean aprovado) throws Exception;
	public Collection<ColaboradorTurma> setCustoRateado(Collection<ColaboradorTurma> colaboradorTurmasLista);
	public Collection<ColaboradorTurma> setFamiliaAreas(Collection<ColaboradorTurma> colaboradorTurmas, Long empresaId) throws Exception;
	public Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds);
	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds) throws Exception;
	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds, char aprovado) throws Exception;
	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId);
	public Collection<ColaboradorTurma> findRelatorioSemIndicacaoTreinamento(Long empresaId, Long[] areaIds, Long[] estabelecimentoIds, int qtdMeses) throws ColecaoVaziaException;
	public String insereColaboradorTurmas(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Turma turma, DNT dnt, int filtrarPor, String[] selectPrioridades);
	public void remove(ColaboradorTurma colaboradorTurma);
	public Collection<ColaboradorTurma> montaColunas(Collection<ColaboradorTurma> colaboradorTurmas, boolean exibirNomeComercial, boolean exibirCargo, boolean exibirEstabelecimento, boolean exibirAssinatura, boolean exibirArea);
	public Collection<ColaboradorTurma> findRelatorioHistoricoTreinamentos(Long empresaId, Long[] colaboradoresCheck, Date dataIni, Date dataFim) throws ColecaoVaziaException, Exception;
	public Collection<Certificado> montaCertificados(Collection<Colaborador> colaboradores, Certificado certificado, Long empresaId);
	public Collection<Colaborador> findAprovadosByCertificacao(Certificacao certificacao, int qtdCursos);
	public Collection<ColaboradorCertificacaoRelatorio> montaRelatorioColaboradorCertificacao(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds) throws Exception;
	public void saveColaboradorTurmaNota(Turma turma, Colaborador colaborador, Long[] avaliacaoCursoIds, String[] notas) throws Exception;
	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds) throws Exception;
	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId);
	public Double percentualFrequencia(Date dataIni, Date dataFim, Long[] empresaIds);
	public void carregaResultados(Collection<ColaboradorTurma> colaboradorTurmas);
	public Collection<ColaboradorTurma> findAprovadosReprovados(Date dataIni, Date dataFim, Long[] empresaIds);
	public HashMap<String, Integer> getResultado(Date dataIni, Date dataFim, Long[] empresaIds);
	public Collection<Colaborador> findAprovadosByTurma(Collection<Long> turmaId);
	public Collection<ColaboradorTurma> findAprovadosByTurma(Long turmaId);
	public Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean realizada);
	public String checaColaboradorInscritoEmOutraTurma(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Long turmaId);
	public Collection<Long> findIdEstabelecimentosByTurma(Long turmaid, Long empresaId);
	public Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes);
	public Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds,	Long[] areaIds, Long[] cursoIds, Long[] turmaIds);
	public Collection<Colaborador> findColaboradorByCurso(Long[] cursosIds);
	public Collection<ColaboradorTurma> filtraAprovadoReprovado(Collection<ColaboradorTurma> colaboradorTurmaIds, char aprovado);
}