package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.web.tags.CheckBox;

public interface CursoManager extends GenericManager<Curso>
{
	public Curso findByIdProjection(Long cursoId);
	public Collection<Curso> findAllSelect(Long empresaId);
	public String getConteudoProgramatico(Long id);
	public Collection<Curso> findByCertificacao(Long certificacaoId);
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds);
	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer countTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Boolean realizado);
	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	public void update(Curso curso, Empresa empresa, String[] avaliacaoCursoIds)throws Exception;
	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId);
	public Integer getCount(Curso curso, Long empresaId);
	public Curso saveClone(Curso curso, Long empresaId);
	public Collection<Curso> findCursosSemTurma(Long empresaId);
	public String somaCargaHoraria(Collection<Turma> turmas);
	public Collection<Curso> findByIdProjection(Long[] cursoIds);
	public IndicadorTreinamento findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds);
	public IndicadorTreinamento montaIndicadoresTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds);
	public Collection<CheckBox> populaCheckOrderDescricao(Long empresaId);
	public Collection<Curso> populaCursos(Long[] cursosCheckIds);
	public Collection<Curso> findByCompetencia(Long conhecimentoId, Character tipoCompetencia);
	public Collection<Curso> findAllByEmpresaParticipante(Long empresaId);
	public boolean existeEmpresasNoCurso(Long empresaId, Long cursoId);
	public Collection<Empresa> findAllEmpresasParticipantes(Long cursoId);
}