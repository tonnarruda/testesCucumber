package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.Empresa;

public interface CursoDao extends GenericDao<Curso>
{
	public Curso findByIdProjection(Long cursoId);
	public Collection<Curso> findAllSelect(Long empresaId);
	public String getConteudoProgramatico(Long id);
	public Collection<Curso> findByCertificacao(Long certificacaoId);
	public Double somaCustosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds);
	public IndicadorTreinamento findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cursoIds);
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds);
	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer countTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Boolean realizado);
	public Integer findCargaHorariaTotalTreinamento(Long[] cursosIds, Long[] empresasIds, Long[] estabelecimentosIds, Long[] areasOrganizacionaisIds, Date dataInicio, Date dataFim, boolean realizada);
	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId);
	public Empresa findEmpresaByCurso(Long cursoId);
	public Integer getCount(Curso curso, Long empresaId);
	public Collection<Curso> findCursosSemTurma(Long empresaId);
	public Collection<Curso> findByIdProjection(Long[] cursoIds);
	public Collection<Curso> findByCompetencia(Long competenciaId, Character tipoCompetencia);
	public Collection<Curso> findAllByEmpresasParticipantes(Long... empresasIds);
	public boolean existeEmpresasNoCurso(Long empresaId, Long cursoId);
	public Collection<Empresa> findEmpresasParticipantes(Long cursoId);
	public boolean existeAvaliacaoAlunoRespondida(Long cursoId, char tipoAvaliacaoCurso);
	public Collection<Curso> findByEmpresaIdAndCursosId(Long[] empresasIds, Long... cursosIds);
	public Collection<Curso> somaDespesasPorCurso(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds);
	public Collection<Curso> findByHistoricoFuncaoId(Long historicoFuncaoId);
	public boolean existePresenca(Long cursoId);
	public IndicadorTreinamento findIndicadorTreinamentoCustos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cursoIds);
	public Double findQtdHorasRatiada(Date dataIni, Date dataFim, Long[] empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cursosIds, boolean turmasComHorasNoDia);
	public Integer findCargaHorariaTreinamentoRatiada(Long[] cursosIds, Long[] empresasIds, Long[] estabelecimentosIds, Long[] areasIds, Date dataInicio, Date dataFim, boolean realizada, boolean turmasComHorasNoDia);
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId);
	public boolean existeTurmaRealizada(Long cursoId);
	public void removeVinculoComConhecimento(Long conhecimentoId);
}