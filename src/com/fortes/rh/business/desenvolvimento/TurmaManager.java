package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;


public interface TurmaManager extends GenericManager<Turma>
{
	public void removeCascade(Long turmaId) throws Exception;
	public Turma findByIdProjection(Long turmaId);
	public void salvarTurmaDiasCusto(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim, String despesaJSON)throws Exception;
	public void updateTurmaDias(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim) throws Exception;
	public Collection<Turma> getTurmaFinalizadas(Long cursoId);
	public List filtroRelatorioByAreas(LinkedHashMap parametros);
	public List filtroRelatorioByColaborador(LinkedHashMap parametros);
	public Map<String, Object> getParametrosRelatorio(Empresa empresa, Map<String, Object> parametros);
	public Collection<Turma> findAllSelect(Long cursoId);
	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, char realizada, Long empresaId);
	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, char realizada);
	public void updateRealizada(Long turmaId, boolean realizada)throws Exception;
	public void updateCusto(Long turmaId, double totalCusto);
	public Map<String, Object> getParametrosRelatorioSemTreinamento(Empresa empresa, Map<String, Object> parametros);
	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, char realizada, Long[] empresaIds, Long[] cursoIds);
	public Boolean realizadaValue(char realizada);
	public Collection<Turma> findByIdProjection(Long[] ids);
	public boolean verificaAvaliacaoDeTurmaRespondida(Long turmaId);
	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId);
	public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds);
	public Integer quantidadeParticipantesPresentes(Date dataIni, Date dataFim, Long[] empresasIds, Long[] areasIds, Long[] cursosIds);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId);
	public Collection<Turma> findByCursos(Long[] cursoIds);
	public Collection<Turma> findByTurmasPeriodo(Long[] turmasCheck, Date dataIni, Date dataFim, Boolean realizada);
	//	public Collection<Colaborador> enviarEmailParticipantes(Long turmaId);
	public Double somaCustosNaoDetalhados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds);
	public Double getPercentualInvestimento(Double somaCustos, Date dataIni, Date dataFim, Long[] empresaIds);
	public void salvarTurmaDiasCustosColaboradoresAvaliacoes(Turma turma, String[] dias, String custos, Collection<ColaboradorTurma> colaboradorTurmas, Long[] avaliacaoTurmasCheck) throws Exception;
	public void inserir(Turma turma, String[] dias, String custos, Long[] avaliacaoTurmaIds, String[] horasIni, String[] horasFim) throws Exception;
	public void atualizar(Turma turma, String[] dias, String[] horasIni, String[] horasFim, String[] colaboradorTurma, String[] selectPrioridades, Long[] avaliacaoTurmaIds, boolean atualizaAvaliacao) throws Exception;
	public Turma setAssinaturaDigital(boolean manterAssinaturaDigital, Turma turma, File assinaturaDigital, String local);
}