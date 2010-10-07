package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Entrevista;

public interface EntrevistaDao extends GenericDao<Entrevista>
{
	Entrevista findByIdProjection(Long entrevistaId) throws Exception;
	Collection<Entrevista> findToList(Long empresaId, int page, int pagingSize);
	Long getIdByQuestionario(Long questionarioId);
	boolean verificaEmpresaDoQuestionario(Long entrevistaId, Long empresaId);
	Integer getCount(Long empresaId);
	Collection<Entrevista> findAllSelect(Long empresaId, Boolean ativa);

}