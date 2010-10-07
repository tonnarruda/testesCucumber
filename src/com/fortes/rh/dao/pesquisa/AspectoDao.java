package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Aspecto;

public interface AspectoDao extends GenericDao<Aspecto>
{
	Aspecto findByIdProjection(Long aspectoId);
	Collection<Aspecto> findByQuestionario(Long questionarioId);
	Aspecto findByNomeQuestionario(String aspectoNome, Long questionarioId);
	void removerAspectosDoQuestionario(Long questionarioId);
	Collection<String> getNomesByAvaliacao(Long avaliacaoId);
	Aspecto findByNomeAvaliacao(String nome, Long avaliacaoId);
}