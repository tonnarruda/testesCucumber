package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Turma;

public interface TurmaDao extends GenericDao<Turma>
{
	public Turma findByIdProjection(Long turmaId);
	public Collection<Turma> getTurmaFinalizadas(Long cursoId);
	public List filtroRelatorioByAreas(LinkedHashMap parametros);
	public List filtroRelatorioByColaborador(LinkedHashMap parametros);
	public Collection<Turma> findAllSelect(Long cursoId);
	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId);
	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, Boolean realizada);
	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, Boolean realizada);
	public void updateRealizada(Long turmaId, boolean realizada)throws Exception;
	public void updateCusto(Long turmaId, double totalCusto);
	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, Boolean realizada, Long[] empresaIds);
	public Collection<Turma> findByIdProjection(Long[] ids);
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresaIds);
	public Collection<Turma> findByEmpresaOrderByCurso(Long empresaId);
	public Collection<Turma> findByCursos(Long[] cursoIds);
	public Collection<Turma> findByTurmasPeriodo(Long[] turmaIds, Date dataIni, Date dataFim, Boolean realizada);
	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds);
	public Double somaCustos(Date dataIni, Date dataFim, Long[] empresaIds);
}