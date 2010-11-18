package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.geral.Empresa;

public interface CursoManager extends GenericManager<Curso>
{
	public Curso findByIdProjection(Long cursoId);
	public Collection<Curso> findAllSelect(Long empresaId);
	public String getConteudoProgramatico(Long id);
	public Collection<Curso> findByCertificacao(Long certificacaoId);
	public void findCustoMedioHora(IndicadorTreinamento indicadorTreinamento, Date dataIni, Date dataFim, Long empresaId);
	public void findCustoPerCapita(IndicadorTreinamento indicadorTreinamento, Date dataIni, Date dataFim, Long empresaId);
	public void findHorasPerCapita(IndicadorTreinamento indicadorTreinamento, Date dataIni, Date dataFim, Long empresaId);
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId);
	public Integer countTreinamentos(Date dataIni, Date dataFim, Long empresaId, Boolean realizado);
	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	public void update(Curso curso, Empresa empresa, String[] avaliacaoCursoIds)throws Exception;
	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId);
	public Integer getCount(Curso curso, Long empresaId);
	public Collection<Long> findTurmas(Long empresaId, Long[] cursoIds);
	public Curso saveClone(Curso curso, Long empresaId);
	public Collection<Curso> findCursosSemTurma(Long empresaId);
}