package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public interface AproveitamentoAvaliacaoCursoManager extends GenericManager<AproveitamentoAvaliacaoCurso>
{
	void saveNotas(Long[] colaboradorTurmaIds, String[] notas, AvaliacaoCurso avaliacaoCurso, boolean controlaVencimentoPorCertificacao) throws Exception;
	void saveNotas(ColaboradorTurma colaboradorTurma, String[] notas, Long[] avaliacaoCursoIds, boolean controlaVencimentoPorCertificacao);

	Collection<AproveitamentoAvaliacaoCurso> findNotas(Long avaliacaoId, Long[] colaboradoresTurmaIds);

	Collection<Long> find(Long turmaId, int qtdAvaliacao, String wherePor, Boolean aprovado);

	Collection<Long> find(Long[] cursoIds, Integer qtdAvaliacao, boolean aprovado);

	Collection<Long> findColaboradores(Long id, Integer qtdAvaliacao, String wherePor, boolean aprovado);

	Collection<Long> findAprovadosComAvaliacao(Collection<Long> cursoIds, Date dataIni, Date dataFim);

	void remove(Long id, String[] avaliacaoCursoIds);

	Collection<Long> findReprovados(Date dataIni, Date dataFim, Long empresaId);

	void removeByTurma(Long turmaId);
	
	void removeByColaboradorTurma(Long colaboradorTurmaId);
	
	Collection<ColaboradorTurma> findColaboradorTurma(Long id, int qtdAvaliacao, String wherePor, Boolean aprovado);
	Collection<AproveitamentoAvaliacaoCurso> findByColaboradorCurso(Long colaboradorId, Long cursoId);
}