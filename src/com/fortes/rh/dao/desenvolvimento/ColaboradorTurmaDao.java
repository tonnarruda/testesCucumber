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

@SuppressWarnings("unchecked")
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
	Collection<ColaboradorTurma> findByTurma(Long turmaId, String colaboradorNome, Long empresaId, Long[] estabelecimentoIds, Integer page, Integer pagingSize);
	void updateColaboradorTurmaSetPrioridade(long colaboradorTurma, long prioridadeId);
	void updateColaboradorTurmaSetAprovacao(Long colaboradorTurmaId, boolean aprovacao) throws Exception;
	List findCustoRateado();
	Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds);
	Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds);
	Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds, Long[] colaboradorTurmaIds);
	Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId);
	Collection<ColaboradorTurma> findRelatorioSemIndicacaoDeTreinamento(Long empresaId, Long[] areas, Long[] estabelecimentos, Date data);
	Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds);
	Collection<Long> findColaboradoresSemAvaliacao(Long empresaId, Date dataIni, Date dataFim);
	Collection<ColaboradorTurma> findAprovadosReprovados(Long empresaId, Certificacao certificacao, Long cursoId, Long[] areaIds, Long[] estabelecimentoIds, String orderBy, boolean comHistColaboradorFuturo, Long... turmaIds);
	Collection<ColaboradorTurma> findAprovadosReprovados(Date dataIni, Date dataFim, Long[] empresaIds);
	Collection<ColaboradorTurma> findAprovadosReprovados(Long[] colaboradorTurmaIds);
	ColaboradorTurma findByColaboradorAndTurma(Long turmaId, Long colaboradorId);
	Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId);
	Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean turmaRealizada);
	Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes);
	Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cursoIds, Long[] turmaIds);
	Collection<Colaborador> findColaboradorByCursos(Long[] cursosIds);
}