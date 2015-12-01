package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TAula;

@SuppressWarnings("rawtypes")
public interface ColaboradorTurmaDao extends GenericDao<ColaboradorTurma>
{
	Collection<ColaboradorTurma> filtroRelatorioMatriz(HashMap filtro);
	Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(HashMap filtro);
	Collection<ColaboradorTurma> findColaboradoresByCursoTurmaIsNull(Long cursoId);
	Collection<ColaboradorTurma> findByColaboradorAndTurma(int page, int pagingSize, Long[] colaboradoresIds, Long cursoId, Colaborador colaborador);
	void updateTurmaEPrioridade(Long colaboradorTurnaId, Long turmaId, Long prioridadeId);
	Collection<ColaboradorTurma> findByTurmaCurso(Long cursoId);
	Collection<ColaboradorTurma> findByDNTColaboradores(DNT dnt, Collection<Colaborador> colaboradors);
	Empresa findEmpresaDoColaborador(ColaboradorTurma colaboradorTurma);
	Collection<Long> findIdEstabelecimentosByTurma(Long turmaId, Long empresaId);
	Collection<ColaboradorTurma> findByTurma(Long turmaId, String colaboradorNome, Long empresaId, Long[] estabelecimentoIds, Long[] cargoIds, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize);
	void updateColaboradorTurmaSetPrioridade(long colaboradorTurma, long prioridadeId);
	void updateColaboradorTurmaSetAprovacao(Long colaboradorTurmaId, boolean aprovacao) throws Exception;
	List findCustoRateado();
	Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds);
	Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date data, String situacaoColaborador);
	Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds, Long[] colaboradorTurmaIds);
	Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId);
	Collection<ColaboradorTurma> findRelatorioSemIndicacaoDeTreinamento(Long empresaId, Long[] areas, Long[] estabelecimentos, Date data);
	Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds);
	Collection<Long> findColaboradoresSemAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	Collection<ColaboradorTurma> findAprovadosReprovados(Long empresaId, Certificacao certificacao, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, String orderBy, boolean comHistColaboradorFuturo, String situacao, Long... turmaIds);
	Collection<ColaboradorTurma> findAprovadosReprovados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds);
	Collection<ColaboradorTurma> findAprovadosReprovados(Long[] colaboradorTurmaIds);
	ColaboradorTurma findByColaboradorAndTurma(Long turmaId, Long colaboradorId);
	Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId, Long avaliacaoCursoId);
	Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean turmaRealizada);
	Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes);
	Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cursoIds, Long[] turmaIds, boolean considerarSomenteDiasPresente);
	Collection<Colaborador> findColaboradorByCursos(Long[] cursosIds, Long[] turmasIds);
	TAula[] findColaboradorTreinamentosParaTRU(String colaboradorCodigoAC,Long empresaId, String periodoIni, String periodoFim, boolean considerarPresenca);
	Collection<ColaboradorTurma> findTurmaRealizadaByCodigoAc(String colaboradorCodigoAC, Date dataIni, Date dataFim);
	Collection<ColaboradorTurma> findCursosVencidosAVencer(Date dataIni, Long[] empresasIds, Long[] cursosIds, char filtroAgrupamento, char filtroSituacao, char filtroAprovado);
	Collection<ColaboradorTurma> findCursosCertificacoesAVencer(Date dataReferencia, Long empresaId);
	Collection<ColaboradorTurma> findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId);
	Colaborador verificaColaboradorCertificado(Long colaboradorId, Long cursoId, Long certificacaoId);
}