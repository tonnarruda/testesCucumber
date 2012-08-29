package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;

public interface CursoDao extends GenericDao<Curso>
{
	public Curso findByIdProjection(Long cursoId);
	public Collection<Curso> findAllSelect(Long empresaId);
	public String getConteudoProgramatico(Long id);
	public Collection<Curso> findByCertificacao(Long certificacaoId);
	public IndicadorTreinamento findSomaCustoEHorasTreinamentos(Date dataIni, Date dataFim, Long empresaId, Boolean realizada);
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer countTreinamentos(Date dataIni, Date dataFim, Long empresaId, Boolean realizado);
	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId);
	public Integer getCount(Curso curso, Long empresaId);
	public Collection<Long> findTurmas(Long empresaId, Long[] cursoIds);
	public Collection<Curso> findCursosSemTurma(Long empresaId);
	public Collection<Curso> findByIdProjection(Long[] cursoIds);
}