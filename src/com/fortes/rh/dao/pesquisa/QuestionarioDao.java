package com.fortes.rh.dao.pesquisa;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Questionario;

public interface QuestionarioDao extends GenericDao<Questionario>
{
	Questionario findByIdProjection(Long questionarioId);
	boolean checarQuestionarioLiberado(Questionario questionario);
	void aplicarPorAspecto(Long questionarioId, boolean aplicarPorAspecto);
	void liberarQuestionario(Long questionarioId);
	Collection<Questionario> findQuestionarioNaoLiberados(Date questionarioInicio);
	Collection<Questionario> findQuestionarioPorUsuario(Long usuarioId);
	Collection<Questionario> findQuestionario(Long colaboradorId);
}