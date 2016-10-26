package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.json.TurmaJson;

public interface TurmaDao extends GenericDao<Turma>
{
	public Turma findByIdProjection(Long turmaId);
	public Collection<Turma> getTurmaFinalizadas(Long cursoId);
	public List filtroRelatorioByAreas(LinkedHashMap parametros);
	public List filtroRelatorioByColaborador(LinkedHashMap parametros);
	public Collection<Turma> findAllSelect(Long cursoId);
	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId, String descricao);
	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, Boolean realizada, Long empresaId);
	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, Boolean realizada);
	public void updateRealizada(Long turmaId, boolean realizada)throws Exception;
	public void updateCusto(Long turmaId, double totalCusto);
	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, Boolean realizada, Long[] empresaIds, Long[] cursoIds);
	public Collection<Turma> findByIdProjection(Long[] ids);
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds);
	public Integer quantidadeParticipantesPresentes(Date dataIni, Date dataFim, Long[] empresasIds, Long[] areasIds, Long[] cursosIds, Long[] estabelecimentosIds);
	public Collection<Turma> findByEmpresaOrderByCurso(Long empresaId);
	public Collection<Turma> findByCursos(Long[] cursoIds);
	public Collection<Turma> findByTurmasPeriodo(Long[] turmaIds, Date dataIni, Date dataFim, Boolean realizada);
	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds);
	public Double somaCustos(Date dataIni, Date dataFim, Long[] empresaIds);
	public Collection<TurmaJson> getTurmasJson(String baseCnpj, Long turmaId, char realizada);
	public Collection<Turma> getTurmasByCursoNotTurmaId(Long cursoId, Long notTurmaId);
}