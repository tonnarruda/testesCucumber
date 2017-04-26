package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
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
	Collection<ColaboradorTurma> findByTurma(Long turmaId, String colaboradorNome, Long empresaId, Long[] estabelecimentoIds, Long[] cargoIds, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize, Boolean aprovado);
	void updateColaboradorTurmaSetPrioridade(long colaboradorTurma, long prioridadeId);
	void updateColaboradorTurmaSetAprovacao(Long colaboradorTurmaId, boolean aprovacao) throws Exception;
	List findCustoRateado();
//	Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date data, String situacaoColaborador);
	Collection<ColaboradorTurma> findRelatorioSemTreinamentoAprovadosOrReprovados(Long[] empresasIds, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date data, String situacaoColaborador, Boolean aprovado);
	public Collection<ColaboradorTurma> findColabororesTurmaComTreinamento(Long[] empresasIds, Long[] areaIds, Long[] estabelecimentoIds, Long[] cursosIds);
	Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds, Boolean aprovado);
	Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Long[] cursoIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, String situacao, char aprovacaoFiltro);
	Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId);
	public Collection<ColaboradorTurma> findByTurmaPresenteNoDiaTurmaId(Long turmaId, Long diaTurmaId);
	Collection<ColaboradorTurma> findRelatorioSemIndicacaoDeTreinamento(Long empresaId, Long[] areas, Long[] estabelecimentos, Date data);
	Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds);
	Collection<Long> findColaboradoresSemAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	Collection<ColaboradorTurma> findAprovadosReprovados(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, String orderBy, boolean comHistColaboradorFuturo, String situacao);
	public HashMap findAprovadosReprovados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds);
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
	Collection<ColaboradorTurma> findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(Long certificacaoId, Long colaboradorCertificacaoId, Long... colaboradoresId);
	Colaborador verificaColaboradorCertificado(Long colaboradorId, Long certificacaoId);
	Collection<ColaboradorTurma> findByTurmaId(Long turmaId);
	Collection<ColaboradorTurma> findColaboradorTurmaByCertificacaoControleVencimentoPorCertificacao(Long certificacaoId);
	Collection<ColaboradorTurma> findColaboradorTurmaByCertificacaoControleVencimentoPorCurso(Long certificacaoId, int qtdCursos);
	boolean aprovarOrReprovarColaboradorTurma(Long colaboradorTurmaId, Long turmaId, Long cursoId);
	ColaboradorTurma findByProjection(Long colaboradorTurmaId);
	Collection<ColaboradorTurma> findAprovadosByTurma(Long turmaId);
	Collection<Colaborador> findColabodoresByTurmaId(Long turmaId);
	Collection<ColaboradorTurma> findByCursoLntId(Long cursoLntId);
	void updateCursoLnt(Long cursoId, Long colaboradorTurmaId, Long lntId);
	void removeCursoLnt(Long colaboradorTurmaId);
	void updateCursoLnt(Long colaboradorTurmaId, Long cursoLnt);
	Collection<ColaboradorTurma> findParticipantesCursoLnt(Long cursoLntId);
	void removeAllCursoLntByLnt(Long lntId);
	void removeCursoLntByParticipantesCursoLnt(Long[] participantesRemovidos);
}