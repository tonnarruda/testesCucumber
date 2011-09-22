package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Questionario;

public interface EntrevistaManager extends GenericManager<Entrevista>
{
	Collection<Entrevista> findToListByEmpresa(Long empresaId, int page, int pagingSize);
	void delete(Long entrevistaId, Long empresaId) throws Exception;
	Entrevista findByIdProjection(Long entrevistaId);
	void verificarEmpresaValida(Long entrevistaId, Long empresaId) throws Exception;
	void update(Entrevista entrevista, Questionario questionario, Long empresaId) throws Exception;
	Entrevista save(Entrevista entrevista, Questionario questionario, Empresa empresa) throws Exception;
	Entrevista findParaSerRespondida(Long id);
	void clonarEntrevista(Long entrevistaId, Long... empresasIds) throws Exception;
	Long getIdByQuestionario(Long questionarioId);
	Integer getCount(Long empresaId);
	Collection<Entrevista> findAllSelect(Long empresaId, Boolean ativa);
}