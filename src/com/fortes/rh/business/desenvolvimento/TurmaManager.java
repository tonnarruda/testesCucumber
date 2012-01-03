package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;


public interface TurmaManager extends GenericManager<Turma>
{
	public void removeCascade(Long id) throws Exception;
	public Turma findByIdProjection(Long turmaId);
	public void salvarTudo(Turma turma, String[] diasCheck, Map<Long, String> turmaTipoDespesas)throws Exception;
	public void updateTudo(Turma turma, String[] diasCheck, Map<Long, String> despesas) throws Exception;
	public Collection<Turma> getTurmaFinalizadas(Long cursoId);
	public List filtroRelatorioByAreas(LinkedHashMap parametros);
	public List filtroRelatorioByColaborador(LinkedHashMap parametros);
	public Map<String, Object> getParametrosRelatorio(Empresa empresa, Map<String, Object> parametros);
	public Collection<Turma> findAllSelect(Long cursoId);
	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, char realizada);
	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, char realizada);
	public void updateRealizada(Long turmaId, boolean realizada)throws Exception;
	public Map<String, Object> getParametrosRelatorioSemTreinamento(Empresa empresa, Map<String, Object> parametros);
	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, char realizada, Long empresaId);
	public Boolean realizadaValue(char realizada);
	public Collection<Turma> findByIdProjection(Long[] ids);
	public boolean verificaAvaliacaoDeTurmaRespondida(Long turmaId);
	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId);
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long empresaId);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId);
	public Collection<Turma> findByCursos(Long[] cursoIds);
	public Collection<Turma> findByTurmasPeriodo(Long[] turmasCheck, Date dataIni, Date dataFim, Boolean realizada);
	//	public Collection<Colaborador> enviarEmailParticipantes(Long turmaId);
}