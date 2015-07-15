package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public interface AproveitamentoAvaliacaoCursoDao extends GenericDao<AproveitamentoAvaliacaoCurso>
{
	Collection<AproveitamentoAvaliacaoCurso> findNotas(Long avaliacaoId, Long[] colaboradoresIds);
	AproveitamentoAvaliacaoCurso findByColaboradorTurmaAvaliacaoId(Long colaboradorTurmaId, Long avaliacaoCursoId);
	Collection<Long> find(Long turmaId, int qtdAvaliacao, String wherePor, Boolean aprovado);
	Collection<Long> find(Long[] cursoIds, Integer qtdAvaliacao, boolean aprovado);
	Collection<Long> findColaboradores(Long id, Integer qtdAvaliacao, String wherePor, boolean aprovado);
	Collection<Long> findAprovadosComAvaliacao(Collection<Long> cursoIds, Date dataIni, Date dataFim);
	Collection<Long> findReprovados(Date dataIni, Date dataFim, Long empresaId);
	void removeByTurma(Long turmaId);
	void removeByColaboradorTurma(Long colaboradorTurmaId);
	void remove(Long curosId, String[] avaliacaoCursoIds);
	Collection<ColaboradorTurma> findColaboradorTurma(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado);
	Collection<AproveitamentoAvaliacaoCurso> findByColaboradorCurso(Long colaboradorId, Long cursoId);
}