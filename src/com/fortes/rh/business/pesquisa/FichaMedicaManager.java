package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Questionario;

public interface FichaMedicaManager extends GenericManager<FichaMedica>
{
	Collection<FichaMedica> findToListByEmpresa(Long empresaId, int page, int pagingSize);
	void delete(Long fichaMedicaId, Long empresaId) throws Exception;
	FichaMedica clonarFichaMedica(Long fichaMedicaId) throws Exception;
	Integer getCount(Long empresaId);
	FichaMedica findByIdProjection(Long entrevistaId);
	void update(FichaMedica fichaMedica, Questionario questionario, Long empresaId) throws Exception;
	FichaMedica save(FichaMedica fichaMedica, Questionario questionario, Empresa empresa) throws Exception;
	Long getIdByQuestionario(Long questionarioId);
	Collection<FichaMedica> findAllSelect(Long empresaId, Boolean ativa);
	Collection<FichaMedica> findByColaborador(Long empresaId, Long colaboradorId);
	FichaMedica findByQuestionario(Long questionarioId);
}