package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.FichaMedica;

public interface FichaMedicaDao extends GenericDao<FichaMedica>
{
	FichaMedica findByIdProjection(Long fichaMedicaId) throws Exception;
	Collection<FichaMedica> findToList(Long empresaId, int page, int pagingSize);

	Long getIdByQuestionario(Long questionarioId);
	boolean verificaEmpresaDoQuestionario(Long entrevistaId, Long empresaId);
	Integer getCount(Long empresaId);
	Collection<FichaMedica> findAllSelect(Long empresaId, Boolean ativa);
	Collection<FichaMedica> findByColaborador(Long empresaId, Long colaboradorId);
	FichaMedica findByQuestionario(Long questionarioId);
}