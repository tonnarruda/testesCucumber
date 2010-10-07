package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

public interface AvaliacaoTurmaDao extends GenericDao<AvaliacaoTurma>
{
	AvaliacaoTurma findByIdProjection(Long avaliacaoTurmaId) throws Exception;
	Collection<AvaliacaoTurma> findToList(Long empresaId, int page, int pagingSize);

	Long getIdByQuestionario(Long questionarioId);
	boolean verificaEmpresaDoQuestionario(Long entrevistaId, Long empresaId);
	Integer getCount(Long empresaId);
	Collection<AvaliacaoTurma> findAllSelect(Long empresaId, Boolean ativa);

}