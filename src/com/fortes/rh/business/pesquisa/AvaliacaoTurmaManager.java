package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.Questionario;

public interface AvaliacaoTurmaManager extends GenericManager<AvaliacaoTurma>
{
	Collection<AvaliacaoTurma> findToListByEmpresa(Long empresaId, int page, int pagingSize);
	void delete(Long avaliacaoTurmaId, Long empresaId) throws Exception;
	AvaliacaoTurma clonarAvaliacaoTurma(Long avaliacaoTurmaId) throws Exception;
	Integer getCount(Long empresaId);
	AvaliacaoTurma findByIdProjection(Long entrevistaId);
	void update(AvaliacaoTurma avaliacaoTurma, Questionario questionario, Long empresaId) throws Exception;
	AvaliacaoTurma save(AvaliacaoTurma avaliacaoTurma, Questionario questionario, Empresa empresa) throws Exception;
	Long getIdByQuestionario(Long questionarioId);
	Collection<AvaliacaoTurma> findAllSelect(Long empresaId, Boolean ativa);
}